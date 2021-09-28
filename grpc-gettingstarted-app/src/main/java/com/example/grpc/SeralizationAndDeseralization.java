package com.example.grpc;

import com.wipro.models.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SeralizationAndDeseralization {
    public static void main(String[] args) {
        Person person = Person.newBuilder().setName("Subramanian").setAge(18).build();
        //File to store the binary.
        Path path = Paths.get("person.ser");
        try {
            Files.write(path, person.toByteArray());
            System.out.println("Serialized");

            //Read Bytes from the disk/network
            byte[] bytes = Files.readAllBytes(path);
            //deseralize the object
            Person parsedPerson = Person.parseFrom(bytes);
            System.out.println("Deserialization done");
            System.out.println(parsedPerson);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
