package com.example.grpc;

import com.wipro.models.Person;

public class PersonEnityMain {
    public static void main(String[] args) {
      //access Person
        Person person = Person.newBuilder().setName("Subramanian").setAge(40).build();
        System.out.println(person.toString());

    }
}
