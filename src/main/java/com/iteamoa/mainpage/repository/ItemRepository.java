package com.iteamoa.mainpage.repository;

import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository {
    ItemEntity getFeed(String pk, String sk);
    void updateFeed(FeedDto feedDto);

    List<ItemEntity> queryMostLikedFeed(String feedType);
    List<ItemEntity> queryPostedFeed(String feedType);

    void saveLikeFeed(LikeDto likeDto);
    void deleteLikeFeed(LikeDto likeDto);

    List<ItemEntity> queryLikeFeed(String pk);
    ItemEntity getLike(String pk, String sk);

    ItemEntity getApplication(ApplicationDto applicationDto);
    void saveApplication(ApplicationDto applicationDto);
    void deleteApplication(ItemEntity itemEntity);
}
