package tech.ydb.core.grpc;

import com.google.common.base.Preconditions;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.google.common.net.HostAndPort;

import io.grpc.MethodDescriptor;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import tech.ydb.OperationProtos;
import tech.ydb.core.Result;
import tech.ydb.core.rpc.OperationTray;
import tech.ydb.core.rpc.StreamControl;
import tech.ydb.core.rpc.StreamObserver;


/**
 * @author Sergey Polovko
 * @author Evgeniy Pshenitsin
 * @author Nikolay Perfilov
 */
public interface GrpcTransport extends AutoCloseable {

    public String getEndpointByNodeId(int nodeId);

    public <ReqT, RespT> CompletableFuture<Result<RespT>> unaryCall(
            MethodDescriptor<ReqT, RespT> method,
            ReqT request,
            GrpcRequestSettings settings);
    
    public <ReqT, RespT> StreamControl serverStreamCall(
            MethodDescriptor<ReqT, RespT> method,
            ReqT request,
            StreamObserver<RespT> observer,
            GrpcRequestSettings settings);
    
    public OperationTray getOperationTray();
    public String getDatabase();
    
    @Override
    public void close();

    public static GrpcTransportBuilder forHost(String host, int port) {
        return new GrpcTransportBuilder(null, null, Collections.singletonList(HostAndPort.fromParts(host, port)));
    }

    public static GrpcTransportBuilder forHosts(HostAndPort... hosts) {
        Preconditions.checkNotNull(hosts, "hosts is null");
        Preconditions.checkArgument(hosts.length > 0, "empty hosts array");
        return new GrpcTransportBuilder(null, null, Arrays.asList(hosts));
    }

    public static GrpcTransportBuilder forHosts(List<HostAndPort> hosts) {
        Preconditions.checkNotNull(hosts, "hosts is null");
        Preconditions.checkArgument(!hosts.isEmpty(), "empty hosts list");
        return new GrpcTransportBuilder(null, null, hosts);
    }

    public static GrpcTransportBuilder forEndpoint(String endpoint, String database) {
        Preconditions.checkNotNull(endpoint, "endpoint is null");
        Preconditions.checkNotNull(database, "database is null");
        return new GrpcTransportBuilder(endpoint, database, null);
    }

    // [<protocol>://]<host>[:<port>]/?database=<database-path>
    public static GrpcTransportBuilder forConnectionString(String connectionString) {
        Preconditions.checkNotNull(connectionString, "connection string is null");
        String endpoint;
        String database;
        String scheme;
        try {
            URI uri = new URI(connectionString.contains("://") ? connectionString : "grpc://" + connectionString);
            endpoint = uri.getAuthority();
            Preconditions.checkNotNull(endpoint, "no endpoint in connection string");
            Map<String, List<String>> params = new QueryStringDecoder(uri).parameters();
            List<String> databaseList = params.get("database");
            Preconditions.checkArgument(databaseList != null && !databaseList.isEmpty(), "no database in connection string");
            database = databaseList.get(0);
            scheme = uri.getScheme();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse connection string '" + connectionString +
                    "'. Expected format: [<protocol>://]<host>[:<port>]/?database=<database-path>", e);
        }
        GrpcTransportBuilder builder = new GrpcTransportBuilder(endpoint, database, null);
        if (scheme.equals("grpcs")) {
            builder.withSecureConnection();
        } else if (!scheme.equals("grpc")) {
            throw new IllegalArgumentException("Unknown protocol '" + scheme + "' in connection string");
        }
        return builder;
    }
}
