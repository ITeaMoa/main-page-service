package mainpage.entity;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.entity.BaseEntity;
import com.iteamoa.mainpage.utils.KeyConverter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Setter
@DynamoDbBean
public class LikeEntity extends BaseEntity {
    private String feedType;

    public LikeEntity() {}
    public LikeEntity(LikeDto likeDto) {
        super(KeyConverter.toPk(DynamoDbEntityType.USER, likeDto.getPk()),
        KeyConverter.toPk(DynamoDbEntityType.LIKE, likeDto.getSk()),
        DynamoDbEntityType.LIKE,
        likeDto.getTimestamp(),
        KeyConverter.toPk(DynamoDbEntityType.USER, likeDto.getPk())
        );
        this.feedType = likeDto.getFeedType();
    }

    @DynamoDbAttribute("feedType")
    public String getFeedType() {
        return feedType;
    }

}
