package com.iteamoa.mainpage.entity;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.constant.StatusType;
import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.utils.KeyConverter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

@Setter
@DynamoDbBean
public class ApplicationEntity extends BaseEntity {
    private String part;
    private StatusType status;
    private String feedType;

    public ApplicationEntity() {}
    public ApplicationEntity(ApplicationDto applicationDto) {
        super(
                KeyConverter.toPk(DynamoDbEntityType.USER, applicationDto.getPk()),
                KeyConverter.toPk(DynamoDbEntityType.APPLICATION, applicationDto.getSk()),
                DynamoDbEntityType.APPLICATION,
                applicationDto.getTimestamp(),
                KeyConverter.toPk(DynamoDbEntityType.USER, applicationDto.getPk())
        );
        this.part = applicationDto.getPart();
        this.status = applicationDto.getStatus();
        this.feedType = applicationDto.getFeedType();
    }


    @DynamoDbAttribute("part")
    @DynamoDbSecondarySortKey(indexNames =  {"application-index"})
    public String getPart() {
        return part;
    }

    @DynamoDbAttribute("status")
    public StatusType getStatus() {
        return status;
    }

    @DynamoDbAttribute("feedType")
    public String getFeedType() {
        return feedType;
    }

}
