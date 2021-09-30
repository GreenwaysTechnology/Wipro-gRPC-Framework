package com.example.grpc.loadbalancing;

import com.wipro.service.bank.BankServiceImpl;
import com.wipro.service.bank.TransferService;
import com.wipro.service.greeting.GreetingServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer1 {
    public static void main(String[] args) {
        Server server = ServerBuilder
                .forPort(6565)
                //deploy the service
                .addService(new GreetingServiceImpl())
                .addService(new BankServiceImpl())
                .addService(new TransferService())
                .build();
        //start the server
        try {
            server.start();
            System.out.println("gRPC Server-1 is Ready!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Keep the main thread alive
        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
