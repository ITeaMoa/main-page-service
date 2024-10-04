package com.iteamoa.mainpage.service;

import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.entity.FeedEntity;
import com.iteamoa.mainpage.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
