// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: kikimr/public/api/grpc/ydb_discovery_v1.proto

package tech.ydb.discovery.v1;

public final class YdbDiscoveryV1 {
  private YdbDiscoveryV1() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n-kikimr/public/api/grpc/ydb_discovery_v" +
      "1.proto\022\020Ydb.Discovery.V1\032,kikimr/public" +
      "/api/protos/ydb_discovery.proto2\265\001\n\020Disc" +
      "overyService\022Z\n\rListEndpoints\022#.Ydb.Disc" +
      "overy.ListEndpointsRequest\032$.Ydb.Discove" +
      "ry.ListEndpointsResponse\022E\n\006WhoAmI\022\034.Ydb" +
      ".Discovery.WhoAmIRequest\032\035.Ydb.Discovery" +
      ".WhoAmIResponseB\035\n\033tech.ydb.discov" +
      "ery.v1b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          tech.ydb.discovery.DiscoveryProtos.getDescriptor(),
        }, assigner);
    tech.ydb.discovery.DiscoveryProtos.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
