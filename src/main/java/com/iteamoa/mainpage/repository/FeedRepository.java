package com.iteamoa.mainpage.repository;

import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.entity.FeedEntity;
import com.iteamoa.mainpage.entity.LikeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository{
    void save(FeedDto feedDto);
    FeedEntity fine(String pk, String sk);
    void delete(String pk, String sk);
    List<FeedEntity> queryMostLikedFeed(String feedType);
    List<FeedEntity> queryPostedFeed(String feedType);
}
