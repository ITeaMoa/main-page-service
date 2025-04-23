package mainpage.repository;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.entity.LikeEntity;
import com.iteamoa.mainpage.utils.KeyConverter;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LikeRepository {
    private final DynamoDbTable<LikeEntity> likeTable;
    private final DynamoDbIndex<LikeEntity> likeIndex;

    public LikeRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        this.likeTable = enhancedClient.table("IM_MAIN_TB", TableSchema.fromBean(LikeEntity.class));
        this.likeIndex = likeTable.index("Like-index");
    }

    public void saveLike(LikeEntity likeEntity) {
        likeTable.putItem(likeEntity);
    }

    public void deleteLike(LikeEntity likeEntity) {
        likeTable.deleteItem(likeEntity);
    }

    public LikeEntity getLike(LikeEntity likeEntity) {
        return likeTable.getItem(likeEntity);
    }

    public List<LikeEntity> getLikeFeed(String pk) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k
                .partitionValue(KeyConverter.toPk(DynamoDbEntityType.USER, pk))  // PK 조건 설정
                .sortValue(DynamoDbEntityType.LIKE.getType())
        );

        final SdkIterable<Page<LikeEntity>> pagedResult = likeIndex.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject());

        return pagedResult.stream()
                .flatMap(page -> page.items().stream())
                .filter(LikeEntity::getUserStatus)
                .collect(Collectors.toList());
    }
}
