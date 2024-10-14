package com.iteamoa.mainpage.controller;

import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.QueryDto;
import com.iteamoa.mainpage.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class FeedController {

    private final MainService mainService;

    @GetMapping("/test1")
    public ResponseEntity<?> getFeed(@RequestBody FeedDto feedDto) {
        FeedDto result = mainService.searchFeed(feedDto);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/test2")
    public ResponseEntity<?> saveFeed(@RequestBody FeedDto feedDto) {
        try{
            mainService.saveFeed(feedDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/test3")
    public ResponseEntity<?> deleteFeed(@RequestBody FeedDto feedDto) {
        try{
            mainService.deleteFeed(feedDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/liked")
    public ResponseEntity<?> mostLikedFeedTask(@RequestBody QueryDto query) {
        return ResponseEntity.ok(mainService.mostLikedFeed(query));
    }

    @GetMapping()
    public ResponseEntity<?> postedFeedTask(@RequestBody QueryDto query) {
        return ResponseEntity.ok(mainService.postedFeed(query));
    }

    @GetMapping("/search-tags")
    public ResponseEntity<?> tagSearchTask(@RequestBody QueryDto query) {
        return ResponseEntity.ok(mainService.searchTag(query));
    }

    @GetMapping("/search-keyword")
    public ResponseEntity<?> keywordSearchTask(@RequestBody QueryDto query) {
        return ResponseEntity.ok(mainService.keywordSearch(query));
    }

}
