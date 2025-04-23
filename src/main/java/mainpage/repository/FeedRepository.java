package mainpage.repository;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.entity.FeedEntity;
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
public class FeedRepository {
    private final DynamoDbTable<FeedEntity> feedTable;
    private final DynamoDbIndex<FeedEntity> mostLikedFeedIndex;
    private final DynamoDbIndex<FeedEntity> postedFeedIndex;

    public FeedRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.feedTable = enhancedClient.table("IM_MAIN_TB", TableSchema.fromBean(FeedEntity.class));
        this.mostLikedFeedIndex = feedTable.index("MostLikedFeed-index");
        this.postedFeedIndex = feedTable.index("PostedFeed-index");
    }

    public void updateFeed(FeedEntity feedEntity) {
        feedTable.updateItem(feedEntity);
    }

    public FeedEntity getFeed(String pk, String sk) {
        return feedTable.getItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.FEED, pk),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, sk)
        ));
    }

    public List<FeedEntity> getMostLikedFeeds(String feedType) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, feedType)
        ));

        final SdkIterable<Page<FeedEntity>> pagedResult = mostLikedFeedIndex.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject());

        return pagedResult.stream()
                .flatMap(page -> page.items().stream())
                .filter(FeedEntity::getPostStatus)
                .filter(FeedEntity::getUserStatus)
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<FeedEntity> getPostedFeed(String feedType) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, feedType)
        ));

        final SdkIterable<Page<FeedEntity>> pagedResult = postedFeedIndex.query(q->q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject());

        return pagedResult.stream()
                .flatMap(page -> page.items().stream())
                .filter(FeedEntity::getPostStatus)
                .filter(FeedEntity::getUserStatus)
                .collect(Collectors.toList());
    }
}
