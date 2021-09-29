package com.wipro.service.bank;

import com.example.grpc.util.AccountDB;
import com.wipro.service.*;
import io.grpc.stub.StreamObserver;

public class BankServiceImpl extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        //sending
        int accountNumber = request.getAccountNumber();
        //Balance balance = Balance.newBuilder().setAmount(accountNumber * 1000).build();
        Balance balance = Balance.newBuilder().setAmount(AccountDB.getBalance(accountNumber)).build();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();

    }
    //with draw logic

    @Override
    public void withdraw(WithDrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount();
        int balance = AccountDB.getBalance(accountNumber);
        //start sending money in chunks/stream
        for (int i = 0; i < (amount / 10); i++) {
            Money money = Money.newBuilder().setMoney(amount).build();
            //streaming... calling onNext method n- no times
            responseObserver.onNext(money);
            //deduct  money from account
            AccountDB.deductBalance(accountNumber, 10);

            //simulate delay of sending money
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        //stream completed ; inform that no more data
        responseObserver.onCompleted();
    }
}
