package com.iteamoa.mainpage.controller;

import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestBody FeedDto feedDto) {
        FeedDto result = feedService.searchFeed(feedDto);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/test2")
    public ResponseEntity<?> test2(@RequestBody FeedDto feedDto) {
        try{
            feedService.saveFeed(feedDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/test3")
    public ResponseEntity<?> test3(@RequestBody FeedDto feedDto) {
        try{
            feedService.deleteFeed(feedDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
