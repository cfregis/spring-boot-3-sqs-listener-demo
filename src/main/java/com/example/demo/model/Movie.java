package com.example.demo.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Movie {

    @Getter(onMethod_ = @__({ @DynamoDbAttribute("title"), @DynamoDbPartitionKey}))
    private String title;

    @Getter(onMethod_ = @__({ @DynamoDbAttribute("year"), @DynamoDbSortKey}))
    private Integer year;

    @Getter(onMethod_ = @__({ @DynamoDbAttribute("info")}))
    private String info;

    @Getter(onMethod_ = @__({ @DynamoDbAttribute("genre")}))
    private String genre;

    @Getter(onMethod_ = @__({ @DynamoDbAttribute("country")}))
    private String country;

    @Getter(onMethod_ = @__({ @DynamoDbAttribute("duration")}))
    private Integer duration;

    @Getter(onMethod_ = @__({ @DynamoDbAttribute("language")}))
    private String language;
}
