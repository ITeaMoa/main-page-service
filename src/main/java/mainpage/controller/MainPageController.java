package mainpage.controller;

import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/liked")
    public ResponseEntity<?> getMostLikedFeed(@RequestParam String feedType) {
        return ResponseEntity.ok(mainPageService.getMostLikedFeed(feedType));
    }

    @GetMapping()
    public ResponseEntity<?> getPostedFeed(@RequestParam String feedType) {
        return ResponseEntity.ok(mainPageService.getPostedFeed(feedType));
    }

    @GetMapping("/search-tags")
    public ResponseEntity<?> getTagSearch(@RequestParam String feedType, @RequestParam List<String> tags) {
        return ResponseEntity.ok(mainPageService.getSearchTag(feedType, tags));
    }

    @GetMapping("/search-keyword")
    public ResponseEntity<?> getKeywordSearch(@RequestParam String feedType, @RequestParam String keyword) {
        return ResponseEntity.ok(mainPageService.getKeywordSearch(feedType, keyword));
    }

    @PostMapping("/like")
    public ResponseEntity<String> saveLike(@RequestBody LikeDto likeDto) {
        try{
            mainPageService.saveLike(likeDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/like")
    public ResponseEntity<?> deleteLike(@RequestBody LikeDto likeDto) {
        try{
            mainPageService.deleteLike(likeDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/like")
    public ResponseEntity<?> getLike(@RequestParam("userId") String userId){
        return ResponseEntity.ok(mainPageService.likeFeed(userId));
    }

    @PostMapping("/application")
    public ResponseEntity<?> saveApplication(@RequestBody ApplicationDto applicationDto) {
        try{
            mainPageService.saveApplication(applicationDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/application")
    public ResponseEntity<?> deleteApplication(@RequestBody ApplicationDto applicationDto) {
        try{
            mainPageService.deleteApplication(applicationDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Connected successfully");
    }
}
