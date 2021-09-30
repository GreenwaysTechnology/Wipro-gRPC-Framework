package com.example.grpc.loadbalancing;

import com.wipro.service.Balance;
import com.wipro.service.BalanceCheckRequest;
import com.wipro.service.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class NGINXClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8585)
                .usePlaintext()// no need of ssl
                .build();
        BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);

        BalanceCheckRequest balanceRequest = BalanceCheckRequest.newBuilder().setAccountNumber(10).build();
        Balance balance = bankServiceBlockingStub.getBalance(balanceRequest);
        System.out.println("Balance Received-->" + balance.getAmount() + " " + Thread.currentThread().getName());
        //close the channel
        channel.shutdown();
    }
}
