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
    private String creatorId;
    private Boolean userStatus;

    public BaseEntity() {}
    public BaseEntity(String pk, String sk, LocalDateTime timestamp, String creatorId) {
        this.pk = pk;
        this.sk = sk;
        this.timestamp = Objects.requireNonNullElseGet(timestamp, LocalDateTime::now);
        this.creatorId = creatorId;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("Pk")
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("Sk")
    @DynamoDbSecondaryPartitionKey(indexNames = {"MostLikedFeed-index", "PostedFeed-index", "Application-index"})
    @DynamoDbSecondarySortKey(indexNames = {"SearchByCreator-index"})
    public String getSk() {
        return sk;
    }

    @DynamoDbAttribute("timestamp")
    @DynamoDbSecondarySortKey(indexNames = "PostedFeed-index")
    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    @DynamoDbAttribute("creatorId")
    @DynamoDbSecondaryPartitionKey(indexNames = {"UserStatus-index", "SearchByCreator-index"})
    public String getCreatorId(){
        return creatorId;
    }

    @DynamoDbAttribute("userStatus")
    @DynamoDbSecondarySortKey(indexNames = {"UserStatus-index"})
    public boolean getUserStatus(){
        return userStatus;
    }

}
