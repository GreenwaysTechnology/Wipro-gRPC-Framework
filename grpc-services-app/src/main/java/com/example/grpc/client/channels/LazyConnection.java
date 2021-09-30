package com.example.grpc.client.channels;

import com.wipro.service.GreetingServiceGrpc;
import com.wipro.service.HelloRequest;
import com.wipro.service.HelloResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class LazyConnection {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8080")
                .usePlaintext()// no need of ssl
                .build();
        //Stub Object : Stub is Client Proxy, which is handling all low communication
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
        //Construct Request Payload
        HelloRequest request = HelloRequest.newBuilder().setName("Ram").build();
        //Invoke remote call - RPC and get server response
//        HelloResponse helloResponse = stub.greeting(request);
        System.out.println("Channel is created and stub also created");
        //
        try {
            Thread.sleep(5000);
            //call after 5secs
            HelloResponse helloResponse = stub.greeting(request);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
