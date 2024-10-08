package com.iteamoa.mainpage.repository;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.entity.FeedEntity;
import com.iteamoa.mainpage.utils.KeyConverter;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class FeedCoreRepository implements FeedRepository {
    private final DynamoDbTable<FeedEntity> table;
    private final DynamoDbEnhancedClient enhancedClient;

    public FeedCoreRepository(DynamoDbTable<FeedEntity> table, DynamoDbEnhancedClient enhancedClient) {
        this.table = table;
        this.enhancedClient = enhancedClient;
    }

    @Override
    public void save(FeedDto feedDto){
        table.putItem(new FeedEntity(feedDto));
    }

    @Override
    public FeedEntity fine(String pk, String sk) {
        return table.getItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.FEED, pk),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, sk)
        ));
    }

    @Override
    public void delete(String pk, String sk) {
        table.deleteItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.FEED, pk),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, sk)
        ));
    }

    @Override
    public List<FeedEntity> queryMostLikedFeed(){

        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue("T"));
        final DynamoDbIndex<FeedEntity> mostLikedFeedIndex = table.index("MostLikedFeedIndex");

        final SdkIterable<Page<FeedEntity>> pagedResult = mostLikedFeedIndex.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .limit(3)
                .attributesToProject("Pk", "Sk", "creatorId", "title", "tags", "deadline", "recruitmentNum", "likesCount"));
        List<FeedEntity> top3MostLikedFeed = new ArrayList<>();
        pagedResult.forEach(page -> top3MostLikedFeed.addAll(page.items()));

        return top3MostLikedFeed;
    }

}
