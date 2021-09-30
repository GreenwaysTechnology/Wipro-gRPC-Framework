package com.wipro.service.bank;

import com.example.grpc.util.AccountDB;
import com.wipro.service.Account;
import com.wipro.service.TransferRequest;
import com.wipro.service.TransferResponse;
import com.wipro.service.TransferStatus;
import io.grpc.stub.StreamObserver;

public class TransferStreamingRequest implements StreamObserver<TransferRequest> {

    private StreamObserver<TransferResponse> transferResponseStreamObserver;

    public TransferStreamingRequest(StreamObserver<TransferResponse> transferResponseStreamObserver) {
        this.transferResponseStreamObserver = transferResponseStreamObserver;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        int fromAccount = transferRequest.getFromAccount();
        int toAccount = transferRequest.getToAccount();
        int amount = transferRequest.getAmount();
        int balance = AccountDB.getBalance(fromAccount);
        TransferStatus status = TransferStatus.FAILED;
        if (balance >= amount && fromAccount != toAccount) {
            AccountDB.deductBalance(fromAccount, amount);
            AccountDB.addBalance(toAccount, amount);
            status = TransferStatus.SUCCESS;
        }
        Account fromAccountInfo = Account.newBuilder().setAccountNumber(fromAccount).setAmount(AccountDB.getBalance(fromAccount)).build();
        Account toAccountInfo = Account.newBuilder().setAccountNumber(toAccount).setAmount(AccountDB.getBalance(toAccount)).build();
        //Response
        TransferResponse transferResponse = TransferResponse.newBuilder()
                .setStatus(status)
                .addAccounts(fromAccountInfo)
                .addAccounts(toAccountInfo)
                .build();
        this.transferResponseStreamObserver.onNext(transferResponse);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
     AccountDB.printAccountDetails();
     this.transferResponseStreamObserver.onCompleted();
    }
}
