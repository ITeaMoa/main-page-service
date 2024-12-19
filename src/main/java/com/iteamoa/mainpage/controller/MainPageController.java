package com.iteamoa.mainpage.controller;

import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.dto.QueryDto;
import com.iteamoa.mainpage.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/liked")
    public ResponseEntity<?> mostLikedFeedTask(@RequestParam String feedType) {
        return ResponseEntity.ok(mainPageService.mostLikedFeed(feedType));
    }

    @GetMapping()
    public ResponseEntity<?> postedFeedTask(@RequestParam String feedType) {
        return ResponseEntity.ok(mainPageService.postedFeed(feedType));
    }

    @GetMapping("/search-tags")
    public ResponseEntity<?> tagSearchTask(@RequestParam String feedType, @RequestParam List<String> tags) {
        QueryDto query = new QueryDto();
        query.setFeedType(feedType);
        query.setTags(tags);

        return ResponseEntity.ok(mainPageService.searchTag(query));
    }

    @GetMapping("/search-keyword")
    public ResponseEntity<?> keywordSearchTask(@RequestParam String feedType, @RequestParam String keyword) {
        QueryDto query = new QueryDto();
        query.setFeedType(feedType);
        query.setKeyword(keyword);

        return ResponseEntity.ok(mainPageService.keywordSearch(query));
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeTask(@RequestBody LikeDto likeDto) {
        try{
            mainPageService.saveLike(likeDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/like")
    public ResponseEntity<?> deleteLikeTask(@RequestBody LikeDto likeDto) {
        try{
            mainPageService.deleteLike(likeDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/like")
    public ResponseEntity<?> likeFeedTask(@RequestParam("userId") String userId){
        return ResponseEntity.ok(mainPageService.likeFeed(userId));
    }

    @PostMapping("/application")
    public ResponseEntity<?> applicationFeed(@RequestBody ApplicationDto applicationDto) {
        try{
            mainPageService.saveApplication(applicationDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/application")
    public ResponseEntity<?> deleteApplicationTask(@RequestBody ApplicationDto applicationDto) {
        try{
            mainPageService.deleteApplication(applicationDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
