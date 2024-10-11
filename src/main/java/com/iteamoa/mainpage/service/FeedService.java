package com.iteamoa.mainpage.service;

import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.entity.FeedEntity;
import com.iteamoa.mainpage.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

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

    public List<FeedDto> mostLikedFeed() {
        List<FeedEntity> feedEntities = feedRepository.queryMostLikedFeed();
        List<FeedDto> feedDTOs = new ArrayList<>();
        for (FeedEntity feedEntity : feedEntities) {
            FeedDto feedDto = FeedDto.toFeedDto(feedEntity);
            feedDTOs.add(feedDto);
        }
        return feedDTOs;
    }

    public List<FeedDto> postedFeed() {
        List<FeedEntity> feedEntities = feedRepository.queryPostedFeed();
        List<FeedDto> feedDTOs = new ArrayList<>();
        for (FeedEntity feedEntity : feedEntities) {
            FeedDto feedDto = FeedDto.toFeedDto(feedEntity);
            feedDTOs.add(feedDto);
        }
        return feedDTOs;
    }

    public List<FeedDto> searchTag(FeedDto feedDto) {
        List<FeedEntity> feedEntities = feedRepository.queryPostedFeed();
        List<FeedDto> feedDTOs = new ArrayList<>();
        System.out.println(feedDto.getTags());
        for (FeedEntity feedEntity : feedEntities) {
            boolean exist = true;
            List<String> feedTags = feedEntity.getTags();

            for(String tag : feedDto.getTags()) {
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

    public List<FeedDto> keywordSearch(String keyword) {
        List<FeedEntity> feedEntities = feedRepository.queryPostedFeed();
        List<FeedDto> feedDTOs = new ArrayList<>();

        for (FeedEntity feedEntity : feedEntities) {
            String feedTitle = feedEntity.getTitle().toLowerCase();
            if (feedTitle.contains(keyword.toLowerCase()))
                feedDTOs.add(FeedDto.toFeedDto(feedEntity));
        }

        return feedDTOs;
    }
}
