package tech.ydb.topic;

import java.util.concurrent.CompletableFuture;

import javax.annotation.WillNotClose;

import tech.ydb.core.Status;
import tech.ydb.core.grpc.GrpcTransport;
import tech.ydb.topic.impl.GrpcTopicRpc;
import tech.ydb.topic.impl.TopicClientImpl;
import tech.ydb.topic.settings.CreateTopicSettings;


/**
 * @author Nikolay Perfilov
 */
public interface TopicClient extends AutoCloseable {

    static Builder newClient(@WillNotClose GrpcTransport transport) {
        return TopicClientImpl.newClient(GrpcTopicRpc.useTransport(transport));
    }

    /**
     * Create topic.
     *
     * Parent directories must be already present.
     * @param path  path to topic
     * @param settings  topic settings
     * @return operation status
     */
    CompletableFuture<Status> createTopic(String path, CreateTopicSettings settings);

    /**
     * Drop topic.
     *
     * Parent directories must be already present.
     * @param path  path to topic
     * @return operation status
     */
    CompletableFuture<Status> dropTopic(String path);

    @Override
    void close();

    /**
     * BUILDER
     */
    interface Builder {

        TopicClient build();
    }
}
