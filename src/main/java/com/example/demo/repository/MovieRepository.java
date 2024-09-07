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

    public Movie getMovieById(String id, Integer year){

        return getMappedTable(Movie.class).getItem(Key.builder()
                .partitionValue(id)
                .sortValue(year)
                .build());
    }

    public List<Movie> getMovieList(){
        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
//                .filterExpression(filterExpression)
                .build();
        PageIterable<Movie> returnedList = getMappedTable(Movie.class).scan(scanEnhancedRequest);
        return returnedList.items().stream().toList();
    }

    public Movie updateMovie(Movie movie){
        Movie load = getMovieById(movie.getTitle(), movie.getYear());
        // map these two entity
        load.setCountry(movie.getCountry());
        load.setDuration(movie.getDuration());
        load.setGenre(movie.getGenre());
        load.setLanguage(movie.getLanguage());
        load.setYear(movie.getYear());
        getMappedTable(Movie.class).putItem(load);

        return load;
    }

    public String deleteMovie(String id, Integer year){
        Movie load = this.getMovieById(id, year);
        if(load != null){
            getMappedTable(Movie.class).deleteItem(load);
            return load.getTitle() + " " + load.getYear() + " get deleted !";
        } else {
            return null;
        }

    }
}