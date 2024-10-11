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

@Repository
public class FeedCoreRepository implements FeedRepository {
    private final DynamoDbTable<FeedEntity> table;

    public FeedCoreRepository(DynamoDbTable<FeedEntity> table) {
        this.table = table;
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
    public List<FeedEntity> queryMostLikedFeed() {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue("FEEDTYPE#STUDY"));
        final DynamoDbIndex<FeedEntity> mostLikedFeedIndex = table.index("MostLikedFeedIndex");

        final SdkIterable<Page<FeedEntity>> pagedResult = mostLikedFeedIndex.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject("Pk", "Sk", "creatorId", "title", "tags", "deadline", "recruitmentNum", "likesCount", "postStatus"));

        List<FeedEntity> top3MostLikedFeed = new ArrayList<>();
        pagedResult.forEach(page -> {
            for (FeedEntity feed : page.items()) {
                if (feed.getPostStatus())
                    top3MostLikedFeed.add(feed);

                if (top3MostLikedFeed.size() == 3)
                    return;
            }
        });

        return top3MostLikedFeed;
    }
    
    @Override
    public List<FeedEntity> queryPostedFeed(){
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue("FEEDTYPE#PROJECT"));
        final DynamoDbIndex<FeedEntity> postedFeedIndex = table.index("PostedFeedIndex");
        final SdkIterable<Page<FeedEntity>> pagedResult = postedFeedIndex.query(q->q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject("Pk", "Sk", "creatorId", "title", "tags", "deadline", "recruitmentNum", "likesCount", "postStatus", "timestamp"));

        List<FeedEntity> postedFeeds = new ArrayList<>();
        pagedResult.forEach(page -> {
            for (FeedEntity feed : page.items()) {
                if (feed.getPostStatus())
                    postedFeeds.add(feed);
            }
        });

        return postedFeeds;
    }
}
