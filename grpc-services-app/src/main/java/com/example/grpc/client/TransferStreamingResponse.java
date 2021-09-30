package com.example.grpc.client;

import com.wipro.service.TransferResponse;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class TransferStreamingResponse implements StreamObserver<TransferResponse> {

    private CountDownLatch latch;

    public TransferStreamingResponse(CountDownLatch latch) {
        this.latch = latch;
    }


    @Override
    public void onNext(TransferResponse transferResponse) {
        System.out.println("Status : " + transferResponse.getStatus());
        transferResponse.getAccountsList()
                .stream()
                .map(account -> account.getAccountNumber() + " " + account.getAmount())
                .forEach(System.out::println);
        System.out.println("......................");
    }

    @Override
    public void onError(Throwable throwable) {
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("Transfer done!!");
        this.latch.countDown();
    }
}
