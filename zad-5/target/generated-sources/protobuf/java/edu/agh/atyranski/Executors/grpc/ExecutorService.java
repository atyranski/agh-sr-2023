// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ExecutorService.proto

package edu.agh.atyranski.Executors.grpc;

public final class ExecutorService {
  private ExecutorService() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_edu_agh_atyranski_Executors_grpc_ExecutionRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_edu_agh_atyranski_Executors_grpc_ExecutionRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_edu_agh_atyranski_Executors_grpc_ExecutionResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_edu_agh_atyranski_Executors_grpc_ExecutionResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025ExecutorService.proto\022 edu.agh.atyrans" +
      "ki.Executors.grpc\"E\n\020ExecutionRequest\022\016\n" +
      "\006method\030\001 \001(\t\022\023\n\013jarLocation\030\002 \001(\t\022\014\n\004da" +
      "ta\030\003 \001(\t\"!\n\021ExecutionResponse\022\014\n\004data\030\001 " +
      "\001(\t2\206\001\n\020ExecutionService\022r\n\007execute\0222.ed" +
      "u.agh.atyranski.Executors.grpc.Execution" +
      "Request\0323.edu.agh.atyranski.Executors.gr" +
      "pc.ExecutionResponseB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_edu_agh_atyranski_Executors_grpc_ExecutionRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_edu_agh_atyranski_Executors_grpc_ExecutionRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_edu_agh_atyranski_Executors_grpc_ExecutionRequest_descriptor,
        new java.lang.String[] { "Method", "JarLocation", "Data", });
    internal_static_edu_agh_atyranski_Executors_grpc_ExecutionResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_edu_agh_atyranski_Executors_grpc_ExecutionResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_edu_agh_atyranski_Executors_grpc_ExecutionResponse_descriptor,
        new java.lang.String[] { "Data", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
