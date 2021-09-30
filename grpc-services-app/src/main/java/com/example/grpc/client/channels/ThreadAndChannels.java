package com.example.grpc.client.channels;

import com.wipro.service.BankServiceGrpc;
import com.wipro.service.Money;
import com.wipro.service.WithDrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

class ResponseMoneyHandlerThread implements StreamObserver<Money> {
    private CountDownLatch countDownLatch;
    public ResponseMoneyHandlerThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(Money money) {
        System.out.println("Received-->" + money.getMoney() + "Running on " + Thread.currentThread().getName());
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

public class ThreadAndChannels {
    public static void main(String[] args) {
    //Server streaming
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
                .setAccountNumber(3).setAmount(10).build();
        System.out.println("Start--> " + Thread.currentThread().getName());
        //Nonblocking results to be emitted into callback interface
        bankServiceStub.withdraw(withdrawRequest, new ResponseMoneyHandlerThread(countDownLatch));
        System.out.println("End --> " + Thread.currentThread().getName());

        bankServiceStub.withdraw(withdrawRequest, new ResponseMoneyHandlerThread(countDownLatch));
        System.out.println("End --> " + Thread.currentThread().getName());


        //stop main thread for some time: which is used to stop main thread for data arrival
        // Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
