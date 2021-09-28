package com.example.grpc.types;

import com.wipro.models.Person;

public class DefaultValue {
    public static void main(String[] args) {
        Person person=Person.newBuilder().build();
        System.out.println("Age : " + person.getAge());
        System.out.println("FirstName : " + person.getName().trim());
        System.out.println("Feeback : " + person.getFeeback());

    }
}
