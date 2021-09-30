package com.example.grpc.client;

import com.wipro.service.Balance;
import com.wipro.service.BankServiceGrpc;
import com.wipro.service.DepositRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

class BalanceResponse implements StreamObserver<Balance> {
    CountDownLatch countDownLatch;

    public BalanceResponse(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(Balance balance) {
        System.out.println("Balance After Deposit : " + balance.getAmount());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("There is a problem in deposit");
        this.countDownLatch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("Server has finished depositing");
        this.countDownLatch.countDown();
    }
}

public class ClientStreamingCashDepositRequest {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8080")
                .usePlaintext()// no need of ssl
                .build();
        //Pause the Thread
        CountDownLatch countDownLatch = new CountDownLatch(1);

        //NonBlocking/async
        BankServiceGrpc.BankServiceStub bankServiceStub = BankServiceGrpc.newStub(channel);
        //request Sending
        StreamObserver<DepositRequest> streamObserver = bankServiceStub.
                cashDeposit(new BalanceResponse(countDownLatch));
        //stream the request
        for (int i = 0; i < 10; i++) {
            DepositRequest depositRequest = DepositRequest.newBuilder().setAccountNumber(8).setAmount(10).build();
            streamObserver.onNext(depositRequest);
        }
        streamObserver.onCompleted();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.shutdown();

    }
}
