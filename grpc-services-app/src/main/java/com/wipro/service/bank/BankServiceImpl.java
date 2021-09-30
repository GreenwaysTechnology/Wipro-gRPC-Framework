package com.wipro.service.bank;

import com.example.grpc.util.AccountDB;
import com.wipro.service.*;
import io.grpc.Status;
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

        //verification whether i have enough money to withdraw.
        if (balance < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("No Balance : You have only " + balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }

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

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamRequest(responseObserver);
    }

    //Handler class to Read stream of bytes which was sent by client:Inner class
    public class CashStreamRequest implements StreamObserver<DepositRequest> {

        private StreamObserver<Balance> balanceStreamObserver;
        private int accountBalance;

        public CashStreamRequest(StreamObserver<Balance> balanceStreamObserver) {
            this.balanceStreamObserver = balanceStreamObserver;
        }

        //when a chunk is available
        @Override
        public void onNext(DepositRequest depositRequest) {
            System.out.println("Streaming ..... " + depositRequest.getAmount());
            int accountNumber = depositRequest.getAccountNumber();
            int amount = depositRequest.getAmount();
            this.accountBalance = AccountDB.addBalance(accountNumber, amount);
        }

        @Override
        public void onError(Throwable throwable) {

        }

        //once the request stream is completed, send balance amount
        @Override
        public void onCompleted() {
            System.out.println("Client Streaming is Over...Now Ready send Balance Response");
            Balance balance = Balance.newBuilder().setAmount(this.accountBalance).build();
            this.balanceStreamObserver.onNext(balance);
            this.balanceStreamObserver.onCompleted();

        }
    }

}
