package com.example.grpc.types;

import com.wipro.models.Address;
import com.wipro.models.Person;

public class ComplexType {
    public static void main(String[] args) {
        Address address = Address.newBuilder()
                .setState("Tamil Nadu")
                .setStreet("7th Street")
                .setCity("Coimbatore")
                .build();
        Person person = Person.newBuilder()
                .setName("Subramanian")
                .setAge(18)
                .setAddress(address)
                .build();
        System.out.println(person);
    }
}
