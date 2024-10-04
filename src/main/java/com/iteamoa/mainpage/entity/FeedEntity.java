package com.iteamoa.mainpage.entity;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.utils.KeyConverter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Setter
@DynamoDbBean
public class FeedEntity extends BaseEntity{
    private String title;

    public FeedEntity() { }
    public FeedEntity(String Pk, String Sk, String title) {
        super(
                KeyConverter.toPk(DynamoDbEntityType.FEED, Pk),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, Sk)
        );
        this.title = title;
    }

    public FeedEntity toFeedEntity(FeedDto feedDto){
        return new FeedEntity(
                feedDto.getPk(),
                feedDto.getSk(),
                feedDto.getTitle()
        );
    }

    @DynamoDbAttribute("title")
    public String getTitle(){
        return title;
    }

}

