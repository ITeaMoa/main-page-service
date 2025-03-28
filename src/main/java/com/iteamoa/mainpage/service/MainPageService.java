package com.iteamoa.mainpage.service;

import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.dto.QueryDto;
import com.iteamoa.mainpage.entity.ItemEntity;
import com.iteamoa.mainpage.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {
    private final ItemRepository itemRepository;

    public List<FeedDto> mostLikedFeed(String feedType) {
        return itemRepository.queryMostLikedFeed(feedType).stream()
                .filter(ItemEntity::getUserStatus)
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

    public List<FeedDto> postedFeed(String feedType) {
        return itemRepository.queryPostedFeed(feedType).stream()
                .filter(ItemEntity::getUserStatus)
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

    public List<FeedDto> searchTag(QueryDto query) {
        List<ItemEntity> itemEntities = itemRepository.queryPostedFeed(query.getFeedType());

        return itemEntities.stream()
                .filter(ItemEntity::getUserStatus)
                .filter(feedEntity -> containsAllTags(feedEntity.getTags(), query.getTags()))
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

    private boolean containsAllTags(List<String> feedTags, List<String> queryTags) {
        return queryTags.stream().allMatch(feedTags::contains);
    }

    public List<FeedDto> keywordSearch(QueryDto query) {
        return itemRepository.queryPostedFeed(query.getFeedType()).stream()
                .filter(ItemEntity::getUserStatus)
                .filter(itemEntity -> itemEntity.getTitle().toLowerCase().contains(query.getKeyword().toLowerCase()))
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

    public List<LikeDto> likeFeed(String userId) {
        return itemRepository.queryLikeFeed(userId).stream()
                .filter(ItemEntity::getUserStatus)
                .map(LikeDto::toLikeDto)
                .collect(Collectors.toList());
    }

    public void saveLike(LikeDto likeDto) throws Exception{
        Objects.requireNonNull(likeDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(likeDto.getSk(), "Sk cannot be null");
        Objects.requireNonNull(likeDto.getFeedType(), "FeedType cannot be null");

        ItemEntity feed = Objects.requireNonNull(itemRepository.getFeed(likeDto.getSk(), likeDto.getFeedType()));
        Optional.ofNullable(itemRepository.getLike(likeDto.getPk(), likeDto.getSk()))
                .ifPresent(item -> {
                    throw new RuntimeException("Already liked");
                });
        feed.setLikesCount(feed.getLikesCount()+1);
        itemRepository.updateFeed(FeedDto.toFeedDto(feed));
        itemRepository.saveLikeFeed(likeDto);
    }

    public void deleteLike(LikeDto likeDto) throws Exception{
        Objects.requireNonNull(likeDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(likeDto.getSk(), "Sk cannot be null");
        Objects.requireNonNull(likeDto.getFeedType(), "FeedType cannot be null");

        ItemEntity feed = Objects.requireNonNull(itemRepository.getFeed(likeDto.getSk(), likeDto.getFeedType()));
        Optional.ofNullable(itemRepository.getLike(likeDto.getPk(), likeDto.getSk()))
                .orElseThrow(() -> new Exception("No like exists"));
        feed.setLikesCount(feed.getLikesCount()-1);
        itemRepository.updateFeed(FeedDto.toFeedDto(feed));
        itemRepository.deleteLikeFeed(likeDto);
    }

    public void saveApplication(ApplicationDto applicationDto) throws Exception {
        Objects.requireNonNull(applicationDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(applicationDto.getSk(), "Sk cannot be null");
        Objects.requireNonNull(applicationDto.getPart(), "Part cannot be null");

        ItemEntity feed = Objects.requireNonNull(itemRepository.getFeed(applicationDto.getSk(), applicationDto.getFeedType()));
        feed.getRecruitmentRoles().merge(applicationDto.getPart(), 1, Integer::sum);
        itemRepository.updateFeed(FeedDto.toFeedDto(feed));
        itemRepository.saveApplication(applicationDto);
    }

    public void deleteApplication(ApplicationDto applicationDto) throws Exception {
        Objects.requireNonNull(applicationDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(applicationDto.getSk(), "Sk cannot be null");

        ItemEntity feed = Objects.requireNonNull(itemRepository.getFeed(applicationDto.getSk(), applicationDto.getFeedType()));
        ItemEntity application = itemRepository.getApplication(applicationDto);
        feed.getRecruitmentRoles().merge(application.getPart(), -1, Integer::sum);
        itemRepository.updateFeed(FeedDto.toFeedDto(feed));
        itemRepository.deleteApplication(application);
    }
}
