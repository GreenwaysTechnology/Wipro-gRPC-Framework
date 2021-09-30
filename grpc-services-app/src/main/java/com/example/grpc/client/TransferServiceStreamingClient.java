package com.example.grpc.client;

import com.wipro.service.TransferRequest;
import com.wipro.service.TransferServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class TransferServiceStreamingClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8080")
                .usePlaintext()// no need of ssl
                .build();
        TransferServiceGrpc.TransferServiceStub transferServiceStub = TransferServiceGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);
        TransferStreamingResponse response = new TransferStreamingResponse(latch);
        StreamObserver<TransferRequest> requestStreamObserver = transferServiceStub.transfer(response);

        //stream the request
        for (int i = 0; i < 100; i++) {
            TransferRequest transferrequest = TransferRequest.newBuilder()
                    .setFromAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setToAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setAmount(ThreadLocalRandom.current().nextInt(1, 21))
                    .build();
            requestStreamObserver.onNext(transferrequest);
        }
        requestStreamObserver.onCompleted();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
