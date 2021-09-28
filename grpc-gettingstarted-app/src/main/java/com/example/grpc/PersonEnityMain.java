package com.example.grpc;

import com.google.protobuf.Int32Value;
import com.wipro.models.Person;

public class PersonEnityMain {
    public static void main(String[] args) {
//        //access Person
        Person person = Person.newBuilder().setName("Subramanian")
                .setAge(13)
                .build();
        System.out.println(person.toString());

    }
}
