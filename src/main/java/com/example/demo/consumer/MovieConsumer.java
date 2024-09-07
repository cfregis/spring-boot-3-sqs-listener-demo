package com.example.demo.consumer;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieConsumer {

    @Autowired
    private MovieRepository moviesRepository;

    @SqsListener( value = "${aws.queueName}" )
    public void listen(String message) {

        log.info("Message received on listen method at {}", message);
        try {
            Movie movie = new ObjectMapper().readValue(message, Movie.class);
            moviesRepository.createMovie(movie);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
