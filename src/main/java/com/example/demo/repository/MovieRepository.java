package com.example.demo.repository;

import com.example.demo.model.Movie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class MovieRepository {

    final private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private <T> DynamoDbTable<T> getMappedTable(Class<T> type){
        return dynamoDbEnhancedClient.table("movie", TableSchema.fromBean(type));
    }

    public Movie createMovie(Movie movie){
        getMappedTable(Movie.class).putItem(movie);
        return movie;
    }
}