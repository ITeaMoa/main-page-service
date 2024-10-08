package com.iteamoa.mainpage.entity;


import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Setter
public abstract class BaseEntity {
    private String pk;
    private String sk;

    public BaseEntity() { }
    public BaseEntity(String pk, String sk) {
        this.pk = pk;
        this.sk = sk;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("Pk")
    @DynamoDbSecondaryPartitionKey(indexNames = "LikesCountIndex")
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("Sk")
    public String getSk() {
        return sk;
    }

}
