package com.example.grpc.server;

import com.wipro.service.bank.BankServiceImpl;
import com.wipro.service.greeting.GreetingServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GRPCServerMain {
    public static void main(String[] args) {
        Server server = ServerBuilder
                .forPort(8080)
                //deploy the service
                .addService(new GreetingServiceImpl())
                .addService(new BankServiceImpl())
                .build();
        //start the server
        try {
            server.start();
            System.out.println("gRPC Server is Ready!");
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
