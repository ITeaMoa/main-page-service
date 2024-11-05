package com.iteamoa.mainpage.repository;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.entity.ItemEntity;
import com.iteamoa.mainpage.utils.KeyConverter;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemCoreRepository implements ItemRepository {
    private final DynamoDbTable<ItemEntity> table;

    public ItemCoreRepository(DynamoDbTable<ItemEntity> table) {
        this.table = table;
    }

    @Override
    public void saveFeed(FeedDto feedDto){
        table.putItem(new ItemEntity(feedDto));
    }

    @Override
    public ItemEntity getFeed(String pk, String sk) {
        return table.getItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.FEED, pk),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, sk)
        ));
    }

    @Override
    public void deleteFeed(String pk, String sk) {
        table.deleteItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.FEED, pk),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, sk)
        ));
    }

    @Override
    public void updateFeed(FeedDto feedDto) {
        table.updateItem(new ItemEntity(feedDto));
    }

    @Override
    public List<ItemEntity> queryMostLikedFeed(String feedType) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, feedType)
        ));
        final DynamoDbIndex<ItemEntity> mostLikedFeedIndex = table.index("MostLikedFeed-index");

        final SdkIterable<Page<ItemEntity>> pagedResult = mostLikedFeedIndex.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject("Pk", "Sk", "entityType", "creatorId", "title", "tags", "deadline", "recruitmentNum", "likesCount", "postStatus"));

        List<ItemEntity> top3MostLikedFeed = new ArrayList<>();
        pagedResult.forEach(page -> {
            for (ItemEntity feed : page.items()) {
                if (feed.getPostStatus())
                    top3MostLikedFeed.add(feed);

                if (top3MostLikedFeed.size() == 3)
                    return;
            }
        });

        return top3MostLikedFeed;
    }
    
    @Override
    public List<ItemEntity> queryPostedFeed(String feedType){
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, feedType)
        ));
        final DynamoDbIndex<ItemEntity> postedFeedIndex = table.index("PostedFeed-index");
        final SdkIterable<Page<ItemEntity>> pagedResult = postedFeedIndex.query(q->q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject("Pk", "Sk", "entityType", "creatorId", "title", "tags", "deadline", "recruitmentNum", "likesCount", "postStatus", "timestamp"));
        List<ItemEntity> postedFeeds = new ArrayList<>();
        pagedResult.forEach(page -> {
            for (ItemEntity feed : page.items()) {
                if (feed.getPostStatus())
                    postedFeeds.add(feed);
            }
        });

        return postedFeeds;
    }

    @Override
    public void saveLikeFeed(LikeDto likeDto){
        table.putItem(new ItemEntity(likeDto));
    }

    @Override
    public void deleteLikeFeed(LikeDto likeDto) {
        table.deleteItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.USER, likeDto.getPk()),
                KeyConverter.toPk(DynamoDbEntityType.LIKE, likeDto.getSk())
        ));
    }

    @Override
    public List<ItemEntity> queryLikeFeed(String pk){
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k
                .partitionValue(KeyConverter.toPk(DynamoDbEntityType.USER, pk))  // PK 조건 설정
                .sortValue("Like")
        );
        final DynamoDbIndex<ItemEntity> likeFeedIndex = table.index("Like-index");
        final SdkIterable<Page<ItemEntity>> pagedResult = likeFeedIndex.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject());

        List<ItemEntity> likedFeeds = new ArrayList<>();
        pagedResult.forEach(page -> likedFeeds.addAll(page.items()));
        return likedFeeds;
    }

    @Override
    public void saveApplication(ApplicationDto applicationDto){
        table.putItem(new ItemEntity(applicationDto));
    }

    @Override
    public void deleteApplication(ApplicationDto applicationDto) {
        table.deleteItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.USER, applicationDto.getPk()),
                KeyConverter.toPk(DynamoDbEntityType.APPLICATION, applicationDto.getSk())
        ));
    }
}
