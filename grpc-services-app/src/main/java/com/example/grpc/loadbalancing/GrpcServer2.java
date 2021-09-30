package com.example.grpc.loadbalancing;

import com.wipro.service.bank.BankServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer2 {
    public static void main(String[] args) {
        Server server = ServerBuilder
                .forPort(7575)
                .addService(new BankServiceImpl())
                .build();
        //start the server
        try {
            server.start();
            System.out.println("gRPC Server-2 is Ready!");
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
