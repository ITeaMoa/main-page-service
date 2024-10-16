package com.iteamoa.mainpage.repository;

import com.iteamoa.mainpage.entity.LikeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository {
    List<LikeEntity> queryLikedFeed(String userId);
}
