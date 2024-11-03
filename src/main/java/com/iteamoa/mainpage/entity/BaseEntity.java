package com.iteamoa.mainpage.entity;


import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
public abstract class BaseEntity {
    private String pk;
    private String sk;
    private LocalDateTime timestamp;

    public BaseEntity() {}
    public BaseEntity(String pk, String sk) {
        this.pk = pk;
        this.sk = sk;
        this.timestamp = Objects.requireNonNullElseGet(timestamp, LocalDateTime::now);
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("Pk")
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("Sk")
    @DynamoDbSecondaryPartitionKey(indexNames = {"MostLikedFeed-index", "PostedFeed-index"})
    public String getSk() {
        return sk;
    }

    @DynamoDbAttribute("timestamp")
    @DynamoDbSecondarySortKey(indexNames = "PostedFeed-index")
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
}
