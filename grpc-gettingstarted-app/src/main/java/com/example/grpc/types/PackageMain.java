package com.example.grpc.types;

import com.google.protobuf.FloatValue;
import com.wipro.models.Product;
import com.wipro.payment.PaymentMode;

public class PackageMain {
    public static void main(String[] args) {
        Product product = Product.newBuilder()
                .setPayment(PaymentMode.NETBANKING)
                .build();
        System.out.println(product);
    }
}
