package com.example.grpc.types;

import com.wipro.models.Address;
import com.wipro.models.Feedback;
import com.wipro.models.Person;

import java.util.Arrays;

public class EnumMain {
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
                .setFeeback(Feedback.VERYGOOD)
                .build();
        System.out.println(person  + " " + person.getFeeback());

    }
}
