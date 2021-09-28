package com.example.grpc.types;

import com.google.protobuf.FloatValue;
import com.google.protobuf.Int32Value;
import com.wipro.models.Product;

public class WrapperTypes {
    public static void main(String[] args) {
        Product product = Product.newBuilder()
                .setId(Int32Value.newBuilder().setValue(1).build())
                .setPrice(FloatValue.newBuilder().setValue(12.45f).build())
                .setName("Phone")
                .build();
        System.out.println(product);
    }
}
