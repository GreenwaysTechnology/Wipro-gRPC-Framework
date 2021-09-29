package com.example.grpc.types;

import com.wipro.models.SearchResponse;

public class NestedTypes {
    public static void main(String[] args) {
        SearchResponse searchResponse = SearchResponse
                .newBuilder()
                .setResults(SearchResponse.Result.newBuilder().setTitle("something").build())
                .build();
        System.out.println(searchResponse);
    }
}
