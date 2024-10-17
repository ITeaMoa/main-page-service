package com.iteamoa.mainpage.service;

import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.dto.QueryDto;
import com.iteamoa.mainpage.entity.FeedEntity;
import com.iteamoa.mainpage.repository.FeedRepository;
import com.iteamoa.mainpage.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MainService {
    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;

    public FeedDto searchFeed(FeedDto feedDto) throws NoSuchElementException{
        FeedEntity feedEntity = feedRepository.fine(feedDto.getPk(), feedDto.getSk());
        if (feedEntity == null) {
            return null;
        }
        return FeedDto.toFeedDto(feedEntity);
    }

    public void saveFeed(FeedDto feedDto) throws IllegalArgumentException{
        if (feedDto.getPk() == null || feedDto.getSk() == null) {
            throw new IllegalArgumentException("Pk or SK cannot be null");
        }
        feedRepository.save(feedDto);
    }

    public void deleteFeed(FeedDto feedDto) throws IllegalArgumentException{
        if (feedDto.getPk() == null || feedDto.getSk() == null) {
            throw new IllegalArgumentException("Pk or SK cannot be null");
        }
        feedRepository.delete(feedDto.getPk(), feedDto.getSk());
    }

    public List<FeedDto> mostLikedFeed(QueryDto query) {
        List<FeedEntity> feedEntities = feedRepository.queryMostLikedFeed(query.getFeedType());
        List<FeedDto> feedDTOs = new ArrayList<>();

        for (FeedEntity feedEntity : feedEntities)
            feedDTOs.add(FeedDto.toFeedDto(feedEntity));

        return feedDTOs;
    }

    public List<FeedDto> postedFeed(QueryDto query) {
        List<FeedEntity> feedEntities = feedRepository.queryPostedFeed(query.getFeedType());
        List<FeedDto> feedDTOs = new ArrayList<>();
        for (FeedEntity feedEntity : feedEntities) {
            feedDTOs.add(FeedDto.toFeedDto(feedEntity));
        }
        return feedDTOs;
    }

    public List<FeedDto> searchTag(QueryDto query) {
        List<FeedEntity> feedEntities = feedRepository.queryPostedFeed(query.getFeedType());
        List<FeedDto> feedDTOs = new ArrayList<>();
        for (FeedEntity feedEntity : feedEntities) {
            boolean exist = true;
            List<String> feedTags = feedEntity.getTags();

            for(String tag : query.getTags()) {
                if (!feedTags.contains(tag)){
                    exist = false;
                    break;
                }
            }
            if (exist) {
                feedDTOs.add(FeedDto.toFeedDto(feedEntity));
            }
        }

        return feedDTOs;
    }

    public List<FeedDto> keywordSearch(QueryDto query) {
        List<FeedEntity> feedEntities = feedRepository.queryPostedFeed(query.getFeedType());
        List<FeedDto> feedDTOs = new ArrayList<>();

        for (FeedEntity feedEntity : feedEntities) {
            String feedTitle = feedEntity.getTitle().toLowerCase();
            if (feedTitle.contains(query.getKeyword().toLowerCase()))
                feedDTOs.add(FeedDto.toFeedDto(feedEntity));
        }

        return feedDTOs;
    }

    public void saveLike(LikeDto likeDto) {
        likeRepository.save(likeDto);
    }
}
