package com.iteamoa.mainpage.service;

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
public class MainService {
    private final ItemRepository itemRepository;

    public FeedDto searchFeed(FeedDto feedDto) throws NoSuchElementException{
        ItemEntity itemEntity = itemRepository.searchFeed(feedDto.getPk(), feedDto.getSk());
        if (itemEntity == null) {
            return null;
        }
        return FeedDto.toFeedDto(itemEntity);
    }

    public void saveFeed(FeedDto feedDto) throws IllegalArgumentException{
        if (feedDto.getPk() == 0 || feedDto.getSk() == null) {
            throw new IllegalArgumentException("Pk or SK cannot be null");
        }
        itemRepository.saveFeed(feedDto);
    }

    public void deleteFeed(FeedDto feedDto) throws IllegalArgumentException{
        if (feedDto.getPk() == 0 || feedDto.getSk() == null) {
            throw new IllegalArgumentException("Pk or SK cannot be null");
        }
        itemRepository.deleteFeed(feedDto.getPk(), feedDto.getSk());
    }

    public List<FeedDto> mostLikedFeed(QueryDto query) {
        List<ItemEntity> itemEntities = itemRepository.queryMostLikedFeed(query.getFeedType());
        List<FeedDto> feedDTOs = new ArrayList<>();

        for (ItemEntity itemEntity : itemEntities)
            feedDTOs.add(FeedDto.toFeedDto(itemEntity));

        return feedDTOs;
    }

    public List<FeedDto> postedFeed(QueryDto query) {
        List<ItemEntity> itemEntities = itemRepository.queryPostedFeed(query.getFeedType());
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

    public void saveLike(LikeDto likeDto) throws IllegalArgumentException{
        if (likeDto.getPk() == 0 || likeDto.getSk() == 0) {
            throw new IllegalArgumentException("Pk or SK cannot be null");
        }
        itemRepository.saveLikeFeed(likeDto);
    }

    public List<LikeDto> likeFeed(Long userId) {
        List<ItemEntity> itemEntities = itemRepository.queryLikeFeed(userId);
        List<LikeDto> likeDTOs = new ArrayList<>();
        for (ItemEntity itemEntity : itemEntities)
            likeDTOs.add(LikeDto.toLikeDto(itemEntity));

        return likeDTOs;
    }

    public void deleteLike(LikeDto likeDto) {
        if (likeDto.getPk() == 0 || likeDto.getSk() == 0) {
            throw new IllegalArgumentException("Pk or SK cannot be null");
        }
        itemRepository.deleteLikeFeed(likeDto);
    }
}
