package com.example.grpc.client;

import com.google.common.util.concurrent.Uninterruptibles;
import com.wipro.service.BankServiceGrpc;
import com.wipro.service.Money;
import com.wipro.service.WithDrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class ResponseMoneyHandler implements StreamObserver<Money> {
    private CountDownLatch countDownLatch;
    public ResponseMoneyHandler(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(Money money) {
        System.out.println("Received-->" + money.getMoney());
    }
    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
        countDownLatch.countDown();
    }
    @Override
    public void onCompleted() {
        System.out.println("Publisher has completed streaming");
        countDownLatch.countDown();
    }
}

public class BankingServerStreamingAsync {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8080")
                .usePlaintext()// no need of ssl
                .build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //NonBlocking Stub:Async Stub
        BankServiceGrpc.BankServiceStub bankServiceStub = BankServiceGrpc
                .newStub(channel);

        //Request Payload
        WithDrawRequest withdrawRequest = WithDrawRequest.newBuilder()
                .setAccountNumber(7).setAmount(40).build();
        System.out.println("Start");
        //Nonblocking results to be emitted into callback interface
        bankServiceStub.withdraw(withdrawRequest, new ResponseMoneyHandler(countDownLatch));
        System.out.println("End");

        //stop main thread for some time: which is used to stop main thread for data arrival
       // Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
