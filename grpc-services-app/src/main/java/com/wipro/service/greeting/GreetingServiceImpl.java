package com.wipro.service.greeting;

import com.wipro.service.GreetingServiceGrpc;
import com.wipro.service.HelloRequest;
import com.wipro.service.HelloResponse;
import io.grpc.stub.StreamObserver;

public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greeting(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        //Service Biz logic
        String name = request.getName();
        //Response
        HelloResponse response = HelloResponse.newBuilder().setGreeting("Hello " + name).build();
        //writing response into channel
        responseObserver.onNext(response);
        //close the channel or ensure that the response has been committed
        responseObserver.onCompleted();
    }
}
