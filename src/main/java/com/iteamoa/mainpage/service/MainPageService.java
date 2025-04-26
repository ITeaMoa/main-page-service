package com.iteamoa.mainpage.service;

import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.entity.ApplicationEntity;
import com.iteamoa.mainpage.entity.FeedEntity;
import com.iteamoa.mainpage.entity.LikeEntity;
import com.iteamoa.mainpage.repository.ApplicationRepository;
import com.iteamoa.mainpage.repository.FeedRepository;
import com.iteamoa.mainpage.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {
    private final LikeRepository likeRepository;
    private final FeedRepository feedRepository;
    private final ApplicationRepository applicationRepository;

    public List<FeedDto> getMostLikedFeed(String feedType) {
        return feedRepository.getMostLikedFeeds(feedType).stream()
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

    public List<FeedDto> getPostedFeed(String feedType) {
        return feedRepository.getPostedFeed(feedType).stream()
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

    public List<FeedDto> getSearchTag(String feedType, List<String> tags) {
        List<FeedEntity> feedEntities = feedRepository.getPostedFeed(feedType);
        return feedEntities.stream()
                .filter(feedEntity -> containsAllTags(feedEntity.getTags(), tags))
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

    private boolean containsAllTags(List<String> feedTags, List<String> tags) {
        return tags.stream().allMatch(feedTags::contains);
    }

    public List<FeedDto> getKeywordSearch(String feedType, String keyword) {
        List<FeedEntity> feedEntities = feedRepository.getPostedFeed(feedType);
        return feedEntities.stream()
                .filter(feedEntity -> feedEntity.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

    public List<LikeDto> likeFeed(String userId) {
        return likeRepository.getLikeFeed(userId).stream()
                .map(LikeDto::toLikeDto)
                .collect(Collectors.toList());
    }

    public void saveLike(LikeDto likeDto) throws Exception {
        Objects.requireNonNull(likeDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(likeDto.getSk(), "Sk cannot be null");
        Objects.requireNonNull(likeDto.getFeedType(), "FeedType cannot be null");

        FeedEntity feedEntity = Objects.requireNonNull(feedRepository.getFeed(likeDto.getSk(), likeDto.getFeedType()));
        Optional.ofNullable(likeRepository.getLike(new LikeEntity(likeDto)))
                .ifPresent(item -> {
                    throw new RuntimeException("Already liked");
                });
        feedEntity.setLikesCount(feedEntity.getLikesCount()+1);
        feedRepository.updateFeed(feedEntity);
        likeRepository.saveLike(new LikeEntity(likeDto));
    }

    public void deleteLike(LikeDto likeDto) throws Exception {
        Objects.requireNonNull(likeDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(likeDto.getSk(), "Sk cannot be null");
        Objects.requireNonNull(likeDto.getFeedType(), "FeedType cannot be null");

        FeedEntity feedEntity = Objects.requireNonNull(feedRepository.getFeed(likeDto.getSk(), likeDto.getFeedType()));
        Optional.ofNullable(likeRepository.getLike(new LikeEntity(likeDto)))
                .orElseThrow(() -> new Exception("No like exists"));
        feedEntity.setLikesCount(feedEntity.getLikesCount()-1);
        feedRepository.updateFeed(feedEntity);
        likeRepository.saveLike(new LikeEntity(likeDto));
    }

    public void saveApplication(ApplicationDto applicationDto) throws Exception {
        Objects.requireNonNull(applicationDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(applicationDto.getSk(), "Sk cannot be null");
        Objects.requireNonNull(applicationDto.getPart(), "Part cannot be null");

        FeedEntity feedEntity = Objects.requireNonNull(feedRepository.getFeed(applicationDto.getSk(), applicationDto.getFeedType()));
        feedEntity.getRecruitmentRoles().merge(applicationDto.getPart(), 1, Integer::sum);
        feedRepository.updateFeed(feedEntity);
        applicationRepository.saveApplication(new ApplicationEntity(applicationDto));
    }

    public void deleteApplication(ApplicationDto applicationDto) throws Exception {
        Objects.requireNonNull(applicationDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(applicationDto.getSk(), "Sk cannot be null");

        FeedEntity feedEntity = Objects.requireNonNull(feedRepository.getFeed(applicationDto.getSk(), applicationDto.getFeedType()));
        ApplicationEntity applicationEntity = applicationRepository.getApplication(new ApplicationEntity(applicationDto));
        feedEntity.getRecruitmentRoles().merge(applicationEntity.getPart(), -1, Integer::sum);
        feedRepository.updateFeed(feedEntity);
        applicationRepository.deleteApplication(applicationEntity);
    }
}
