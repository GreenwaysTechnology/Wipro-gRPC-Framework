package com.example.grpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.wipro.models.Person;

import java.io.IOException;

public class ProtoBuffVsJsonTesting {
    public static void main(String[] args) {
        //Json Representation
        JPerson jPerson = new JPerson();
        jPerson.setAge(18);
        jPerson.setName("Subramanian");

        Runnable jsonProcessor = () -> {
            //serialization and deserialization
            ObjectMapper objectMapper = new ObjectMapper();
            //serialization
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(jPerson);
                //deserialization
                objectMapper.readValue(bytes, JPerson.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        //proto buff seralization
        Runnable protoProcess = ()->{
            Person person = Person.newBuilder().setName("Subramanian").setAge(18).build();
            byte[] bytes = person.toByteArray();
            try {
                Person person1 = Person.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }

        };

        for (int i=0;i<5;i++) {
            runPerformanceTest(jsonProcessor, "JSON");
            runPerformanceTest(protoProcess, "PROTO");
        }


    }

    private static void runPerformanceTest(Runnable runnable, String method) {
        long time1 = System.currentTimeMillis();
        for (long i = 0; i < 1_00_000; i++) {
            runnable.run();
        }
        long time2 = System.currentTimeMillis();
        long diff = time2 - time1;
        System.out.println(method + " : " + diff + " ms ");

    }
}
