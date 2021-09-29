package com.example.grpc.client;

import com.google.common.util.concurrent.Uninterruptibles;
import com.wipro.service.BankServiceGrpc;
import com.wipro.service.Money;
import com.wipro.service.WithDrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class BankingServerStreamingClient {


    public static void main(String[] args) {

        //blockingStreaming();
        asyncStreaming();

    }

    private static void asyncStreaming() {
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8080")
                .usePlaintext()// no need of ssl
                .build();
        //NonBlocking Stub:Async Stub
        BankServiceGrpc.BankServiceStub bankServiceStub = BankServiceGrpc.newStub(channel);

        WithDrawRequest withdrawRequest = WithDrawRequest.newBuilder().setAccountNumber(7).setAmount(40).build();

        System.out.println("Start");
        //Nonblocking results to be emitted into callback interface
        bankServiceStub.withdraw(withdrawRequest, new StreamObserver<Money>() {
            @Override
            public void onNext(Money money) {
                System.out.println("Received-->" + money.getMoney());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Publisher has completed streaming");
            }
        });

        System.out.println("End");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

    }

    private static void blockingStreaming() {
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8080")
                .usePlaintext()// no need of ssl
                .build();
        //Stub
        BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        WithDrawRequest withdrawRequest = WithDrawRequest.newBuilder().setAccountNumber(7).setAmount(40).build();
        System.out.println("start");
        bankServiceBlockingStub.withdraw(withdrawRequest).forEachRemaining(money -> {
            System.out.println("Received -->" + money.getMoney());
        });

        System.out.println("end");
    }
}
