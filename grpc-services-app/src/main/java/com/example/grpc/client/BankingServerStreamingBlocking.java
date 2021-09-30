package com.example.grpc.client;
import com.wipro.service.BankServiceGrpc;
import com.wipro.service.WithDrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BankingServerStreamingBlocking {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8080")
                .usePlaintext()// no need of ssl
                .build();
        //Stub
        BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub = BankServiceGrpc
                .newBlockingStub(channel);
        WithDrawRequest withdrawRequest = WithDrawRequest.newBuilder()
                .setAccountNumber(7).setAmount(40).build();

        System.out.println("start");
        bankServiceBlockingStub.withdraw(withdrawRequest).forEachRemaining(money -> {
            System.out.println("Received -->" + money.getMoney());
        });

        System.out.println("end");
    }
}
