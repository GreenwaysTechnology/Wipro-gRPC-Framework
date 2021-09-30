package com.wipro.service.bank;

import com.wipro.service.TransferRequest;
import com.wipro.service.TransferResponse;
import com.wipro.service.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;


public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {
    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferStreamingRequest(responseObserver);
    }
}
