package mainpage.entity;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.utils.KeyConverter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@DynamoDbBean
public abstract class BaseEntity {
    private String pk;
    private String sk;
    private DynamoDbEntityType entityType;
    private LocalDateTime timestamp;
    private String creatorId;
    private Boolean userStatus;

    public BaseEntity() {}
    public BaseEntity(String pk, String sk, DynamoDbEntityType entityType, LocalDateTime timestamp, String creatorId) {
        this.pk = pk;
        this.sk = sk;
        this.entityType = entityType;
        this.timestamp = Objects.requireNonNullElseGet(timestamp, LocalDateTime::now);
        this.creatorId = creatorId;
        this.userStatus = true;
    }

    public BaseEntity(String pk, String sk, String creatorId, DynamoDbEntityType entityTpe) {
        this.pk = KeyConverter.toPk(DynamoDbEntityType.MESSAGE, pk);
        this.sk = KeyConverter.toPk(DynamoDbEntityType.TIMESTAMP, sk);
        this.creatorId = creatorId;
        this.entityType = entityTpe;
        this.userStatus = true;
        this.timestamp = LocalDateTime.parse(sk);
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

    @DynamoDbAttribute("entityType")
    @DynamoDbSecondarySortKey(indexNames = "Like-index")
    public DynamoDbEntityType getEntityType(){
        return entityType;
    }

    @DynamoDbAttribute("timestamp")
    @DynamoDbSecondarySortKey(indexNames = "PostedFeed-index")
    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    @DynamoDbAttribute("creatorId")
    @DynamoDbSecondaryPartitionKey(indexNames = {"SearchByCreator-index", "CreatorId-index"})
    public String getCreatorId(){
        return creatorId;
    }

    @DynamoDbAttribute("userStatus")
    public Boolean getUserStatus(){
        return userStatus;
    }
}
