package com.example.grpc.interceptors;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

class ServerConstants {
    public static final Metadata.Key<String> TOKEN = Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER);
}

public class AuthInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        headers.get(ServerConstants.TOKEN);
        return null;
    }
}
