package com.iteamoa.mainpage.entity;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.utils.KeyConverter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalDateTime;

@DynamoDbBean
public class LikeEntity extends BaseEntity {
    private String entityType;
    private String feedId;
    private LocalDateTime timestamp;

    public LikeEntity() {}
    public LikeEntity(LikeDto likeDto) {
        super(
                KeyConverter.toPk(DynamoDbEntityType.USER, likeDto.getPk()),
                KeyConverter.toPk(DynamoDbEntityType.LIKE, likeDto.getSk())
        );
        this.entityType = likeDto.getEntityType();
        this.feedId = likeDto.getFeedId();
        this.timestamp = likeDto.getTimestamp();
    }

    @DynamoDbAttribute("entityType")
    public String getEntityType() {
        return entityType;
    }

    @DynamoDbAttribute("feedId")
    public String getFeedId() {
        return feedId;
    }

    @DynamoDbAttribute("timestamp")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
