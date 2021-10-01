package com.example.grpc.loadbalancing;

import com.wipro.service.Balance;
import com.wipro.service.BalanceCheckRequest;
import com.wipro.service.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolverRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ClientSideLoadBalancingClient {
      public ClientSideLoadBalancingClient(){
          List<String> instances = new ArrayList<>();
          instances.add("localhost:6565");
          instances.add("localhost:7575");
          ServiceRegistry.register("bank-service", instances);
          NameResolverRegistry.getDefaultRegistry().register(new TempNameResolverProvider());

      }
    public static void main(String[] args) {
        //Prepare List of Servers for load balancing
        List<String> instances = new ArrayList<>();
        instances.add("localhost:6565");
        instances.add("localhost:7575");
        ServiceRegistry.register("bank-service", instances);
        NameResolverRegistry.getDefaultRegistry().register(new TempNameResolverProvider());

        ManagedChannel managedChannel = ManagedChannelBuilder
                //.forAddress("localhost", 8585)
                .forTarget("bank-service")
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .build();

        BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        //
        for (int i = 0; i < 100; i++) {
            BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(ThreadLocalRandom.current().nextInt(1, 11))
                    .build();
            Balance balance = bankServiceBlockingStub.getBalance(balanceCheckRequest);
            System.out.println(
                    "Received : " + balance.getAmount()
            );
        }
    }
}
