package com.example.grpc.client;

import com.wipro.service.GreetingServiceGrpc;
import com.wipro.service.HelloRequest;
import com.wipro.service.HelloResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClientApp {
    public static void main(String[] args) {
        //Channel Object
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8080")
                .usePlaintext()// no need of ssl
                .build();
        //Stub Object : Stub is Client Proxy, which is handling all low communication
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
        //Construct Request Payload
        HelloRequest request = HelloRequest.newBuilder().setName("Ram").build();
        //Invoke remote call - RPC and get server response
        HelloResponse helloResponse = stub.greeting(request);
        //log the response
        System.out.println(helloResponse);
        //close the channel
        channel.shutdown();

    }
}
