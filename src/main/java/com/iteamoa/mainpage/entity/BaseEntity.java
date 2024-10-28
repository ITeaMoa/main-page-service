package com.iteamoa.mainpage.entity;


import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
public abstract class BaseEntity {
    private String pk;
    private String sk;
    private LocalDateTime timestamp;

    public BaseEntity() { }
    public BaseEntity(String pk, String sk, LocalDateTime timestamp) {
        this.pk = pk;
        this.sk = sk;
        this.timestamp = Objects.requireNonNullElseGet(timestamp, LocalDateTime::now);
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("Pk")
    @DynamoDbSecondaryPartitionKey(indexNames = {"LikesCountIndex", "LikeFeedIndex"})
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("Sk")
    @DynamoDbSecondaryPartitionKey(indexNames = {"MostLikedFeedIndex", "PostedFeedIndex"})
    public String getSk() {
        return sk;
    }

    @DynamoDbAttribute("timestamp")
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
}
