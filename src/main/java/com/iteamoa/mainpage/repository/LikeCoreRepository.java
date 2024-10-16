package com.iteamoa.mainpage.repository;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.entity.LikeEntity;
import com.iteamoa.mainpage.utils.KeyConverter;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;

public class LikeCoreRepository implements LikeRepository {
    private final DynamoDbTable<LikeEntity> table;

    public LikeCoreRepository(DynamoDbTable<LikeEntity> table) {
        this.table = table;
    }

    @Override
    public List<LikeEntity> queryLikedFeed(String userId){
        QueryConditional queryConditional = QueryConditional.sortBeginsWith(
                KeyConverter.toKey(
                        KeyConverter.toPk(DynamoDbEntityType.USER, userId),
                        "LIKE#"
                )
        );

        final SdkIterable<Page<LikeEntity>> pagedResult = table.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false) // 최신 순으로 정렬
                .attributesToProject("Pk", "Sk", "feedId", "userId", "timestamp"));

        // 결과를 저장할 리스트
        List<LikeEntity> likedFeeds = new ArrayList<>();

        // 페이지 결과 처리
        pagedResult.forEach(page -> likedFeeds.addAll(page.items()));

        return likedFeeds;
    }
}
