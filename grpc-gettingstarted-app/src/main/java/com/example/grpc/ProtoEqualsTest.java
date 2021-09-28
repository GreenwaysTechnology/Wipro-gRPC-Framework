package com.example.grpc;

import com.wipro.models.Person;

public class ProtoEqualsTest {
    public static void main(String[] args) {
        Person person1 = Person.newBuilder().setName("Subramanian").setAge(18).build();
        Person person2 = Person.newBuilder().setName("Subramanian").setAge(42).build();
        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

    }
}
