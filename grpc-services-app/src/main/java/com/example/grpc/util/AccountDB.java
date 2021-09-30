package com.example.grpc.util;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountDB {
    //generate some account no and its balances
    private final static Map<Integer, Integer> MAP = IntStream
            .rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toMap(Function.identity(), value -> value * 10));

    public static Integer getBalance(int accountNumber) {
        return MAP.get(accountNumber);
    }

    public static Integer addBalance(int accountNumber, int amount) {
        return MAP.computeIfPresent(accountNumber, (k, v) -> v + amount);
    }

    public static Integer deductBalance(int accountNumber, int amount) {
        return MAP.computeIfPresent(accountNumber, (k, v) -> v - amount);
    }

    public static void printAccountDetails() {
        System.out.println(MAP);
    }
}
