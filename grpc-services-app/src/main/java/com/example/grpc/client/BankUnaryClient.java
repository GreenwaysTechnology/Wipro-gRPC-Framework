    package com.example.grpc.client;

    import com.wipro.service.Balance;
    import com.wipro.service.BalanceCheckRequest;
    import com.wipro.service.BankServiceGrpc;
    import io.grpc.ManagedChannel;
    import io.grpc.ManagedChannelBuilder;

    public class BankUnaryClient {
        public static void main(String[] args) {
            ManagedChannel channel = ManagedChannelBuilder
                    .forTarget("localhost:8080")
                    .usePlaintext()// no need of ssl
                    .build();
            BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
            BalanceCheckRequest balanceRequest = BalanceCheckRequest.newBuilder().setAccountNumber(10).build();
            Balance balance = bankServiceBlockingStub.getBalance(balanceRequest);
            System.out.println("Balance Received-->" + balance.getAmount());
            //close the channel
            channel.shutdown();
        }
    }
