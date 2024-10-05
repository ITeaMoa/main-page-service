package com.iteamoa.mainpage.repository;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.entity.FeedEntity;
import com.iteamoa.mainpage.utils.KeyConverter;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Objects;
import java.util.Optional;

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


}
