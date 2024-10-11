package com.iteamoa.mainpage.controller;

import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.QueryDto;
import com.iteamoa.mainpage.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/test1")
    public ResponseEntity<?> getFeed(@RequestBody FeedDto feedDto) {
        FeedDto result = feedService.searchFeed(feedDto);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/test2")
    public ResponseEntity<?> saveFeed(@RequestBody FeedDto feedDto) {
        try{
            feedService.saveFeed(feedDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/test3")
    public ResponseEntity<?> deleteFeed(@RequestBody FeedDto feedDto) {
        try{
            feedService.deleteFeed(feedDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/liked")
    public ResponseEntity<?> mostLikedFeedTask(@RequestBody QueryDto query) {
        return ResponseEntity.ok(feedService.mostLikedFeed(query));
    }

    @GetMapping()
    public ResponseEntity<?> postedFeedTask(@RequestBody QueryDto query) {
        return ResponseEntity.ok(feedService.postedFeed(query));
    }

    @GetMapping("/search-tags")
    public ResponseEntity<?> tagSearchTask(@RequestBody QueryDto query) {
        return ResponseEntity.ok(feedService.searchTag(query));
    }

    @GetMapping("/search-keyword")
    public ResponseEntity<?> keywordSearchTask(@RequestBody QueryDto query) {
        return ResponseEntity.ok(feedService.keywordSearch(query));
    }

}
