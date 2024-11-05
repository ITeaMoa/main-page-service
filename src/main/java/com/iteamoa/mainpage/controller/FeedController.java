package com.iteamoa.mainpage.controller;

import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.dto.QueryDto;
import com.iteamoa.mainpage.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> mostLikedFeedTask(@RequestParam String feedType) {
        return ResponseEntity.ok(mainService.mostLikedFeed(feedType));
    }

    @GetMapping()
    public ResponseEntity<?> postedFeedTask(@RequestParam String feedType) {
        return ResponseEntity.ok(mainService.postedFeed(feedType));
    }

    @GetMapping("/search-tags")
    public ResponseEntity<?> tagSearchTask(@RequestParam String feedType, @RequestParam List<String> tags) {
        QueryDto query = new QueryDto();
        query.setFeedType(feedType);
        query.setTags(tags);
        System.out.println(tags);

        return ResponseEntity.ok(mainService.searchTag(query));
    }

    @GetMapping("/search-keyword")
    public ResponseEntity<?> keywordSearchTask(@RequestParam String feedType, @RequestParam String keyword) {
        QueryDto query = new QueryDto();
        query.setFeedType(feedType);
        query.setKeyword(keyword);

        return ResponseEntity.ok(mainService.keywordSearch(query));
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeTask(@RequestBody LikeDto likeDto) {
        try{
            mainService.saveLike(likeDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/like")
    public ResponseEntity<?> deleteLikeTask(@RequestBody LikeDto likeDto) {
        try{
            mainService.deleteLike(likeDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/like")
    public ResponseEntity<?> likeFeedTask(@RequestParam("userId") String userId){
        return ResponseEntity.ok(mainService.likeFeed(userId));
    }

    @PostMapping("/application")
    public ResponseEntity<?> applicationFeed(@RequestParam String feedType, @RequestBody ApplicationDto applicationDto) {
        try{
            mainService.saveApplication(applicationDto, feedType);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/application")
    public ResponseEntity<?> deleteApplicationTask(@RequestBody ApplicationDto applicationDto) {
        try{
            mainService.deleteApplication(applicationDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
