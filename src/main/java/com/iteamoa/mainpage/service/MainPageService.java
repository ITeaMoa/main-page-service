package com.iteamoa.mainpage.service;

import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.dto.QueryDto;
import com.iteamoa.mainpage.entity.ItemEntity;
import com.iteamoa.mainpage.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MainPageService {
    private final ItemRepository itemRepository;

    public List<FeedDto> mostLikedFeed(String feedType) {
        List<ItemEntity> itemEntities = itemRepository.queryMostLikedFeed(feedType);

        List<FeedDto> feedDTOs = new ArrayList<>();
        for (ItemEntity itemEntity : itemEntities)
            feedDTOs.add(FeedDto.toFeedDto(itemEntity));

        return feedDTOs;
    }

    public List<FeedDto> postedFeed(String feedType) {
        List<ItemEntity> itemEntities = itemRepository.queryPostedFeed(feedType);

        List<FeedDto> feedDTOs = new ArrayList<>();
        for (ItemEntity itemEntity : itemEntities) {
            feedDTOs.add(FeedDto.toFeedDto(itemEntity));
        }

        return feedDTOs;
    }

    public List<FeedDto> searchTag(QueryDto query) {
        List<ItemEntity> itemEntities = itemRepository.queryPostedFeed(query.getFeedType());
        List<FeedDto> feedDTOs = new ArrayList<>();
        for (ItemEntity feedEntity : itemEntities) {
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
        List<ItemEntity> itemEntities = itemRepository.queryPostedFeed(query.getFeedType());
        List<FeedDto> feedDTOs = new ArrayList<>();

        for (ItemEntity itemEntity : itemEntities) {
            String feedTitle = itemEntity.getTitle().toLowerCase();
            if (feedTitle.contains(query.getKeyword().toLowerCase()))
                feedDTOs.add(FeedDto.toFeedDto(itemEntity));
        }

        return feedDTOs;
    }

    public List<LikeDto> likeFeed(String userId) {
        List<ItemEntity> itemEntities = itemRepository.queryLikeFeed(userId);
        List<LikeDto> likeDTOs = new ArrayList<>();
        for (ItemEntity itemEntity : itemEntities)
            likeDTOs.add(LikeDto.toLikeDto(itemEntity));

        return likeDTOs;
    }

    public void saveLike(LikeDto likeDto) throws Exception{
        if (likeDto.getPk() == null || likeDto.getSk() == null || likeDto.getFeedType() == null) {
            throw new Exception("Pk or SK cannot be null");
        }
        ItemEntity feed = itemRepository.getFeed(likeDto.getSk(), likeDto.getFeedType());
        if(feed == null)
            throw new Exception("No feed exits");
        feed.setLikesCount(feed.getLikesCount()+1);
        itemRepository.updateFeed(FeedDto.toFeedDto(feed));

        itemRepository.saveLikeFeed(likeDto);
    }

    public void deleteLike(LikeDto likeDto) throws Exception{
        if (likeDto.getPk() == null || likeDto.getSk() == null || likeDto.getFeedType() == null) {
            throw new Exception("Pk or SK cannot be null");
        }

        ItemEntity feed = itemRepository.getFeed(likeDto.getSk(), likeDto.getFeedType());
        if(feed == null)
            throw new Exception("No feed exits");
        feed.setLikesCount(feed.getLikesCount()-1);
        itemRepository.updateFeed(FeedDto.toFeedDto(feed));

        itemRepository.deleteLikeFeed(likeDto);
    }

    public void saveApplication(ApplicationDto applicationDto) throws Exception {
        if (applicationDto.getPk() == null || applicationDto.getSk() == null || applicationDto.getPart() == null) {
            throw new Exception("Pk or SK cannot be null");
        }

        ItemEntity feed = itemRepository.getFeed(applicationDto.getSk(), applicationDto.getFeedType());
        if(feed == null)
            throw new Exception("No feed exits");
        feed.getRecruitmentRoles().merge(applicationDto.getPart(), 1, Integer::sum);
        itemRepository.updateFeed(FeedDto.toFeedDto(feed));

        itemRepository.saveApplication(applicationDto);
    }

    public void deleteApplication(ApplicationDto applicationDto) throws Exception {
        if (applicationDto.getPk() == null || applicationDto.getSk() == null) {
            throw new IllegalArgumentException("Pk or SK cannot be null");
        }

        ItemEntity feed = itemRepository.getFeed(applicationDto.getSk(), applicationDto.getFeedType());
        ItemEntity application = itemRepository.getApplication(applicationDto);
        if(feed == null)
            throw new Exception("No feed exits");
        feed.getRecruitmentRoles().merge(application.getPart(), -1, Integer::sum);
        itemRepository.updateFeed(FeedDto.toFeedDto(feed));

        itemRepository.deleteApplication(application);
    }
}
