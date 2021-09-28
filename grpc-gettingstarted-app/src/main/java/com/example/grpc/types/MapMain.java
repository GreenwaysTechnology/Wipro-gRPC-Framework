package com.example.grpc.types;

import com.wipro.models.Address;
import com.wipro.models.Person;

import java.util.Arrays;

public class MapMain {
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
                .addSkills("Java")
                .addSkills("Javascript")
                .addAllSkills(Arrays.asList("Microservices","gRPC"))
                .putValues("id","1")
                .build();
        System.out.println(person);
    }
}
