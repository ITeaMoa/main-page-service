package com.iteamoa.mainpage.dto;

import com.iteamoa.mainpage.entity.FeedEntity;
import com.iteamoa.mainpage.utils.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedDto {
    private String pk;
    private String sk;
    private String entityType;
    private String creatorId;
    private String title;
    private int recruitmentNum;
    private LocalDateTime deadline;
    private String place;
    private int period;
    private List<String> tags;
    private int likesCount;
    private String content;
    private List<Comment> comments;
    private boolean postStatus;
    private LocalDateTime timestamp;
    private boolean savedFeed;

    public static FeedDto toFeedDto(FeedEntity feedEntity){
        return new FeedDto(
                feedEntity.getPk(),
                feedEntity.getSk(),
                feedEntity.getEntityType(),
                feedEntity.getCreatorId(),
                feedEntity.getTitle(),
                feedEntity.getRecruitmentNum(),
                feedEntity.getDeadline(),
                feedEntity.getPlace(),
                feedEntity.getPeriod(),
                feedEntity.getTags(),
                feedEntity.getLikesCount(),
                feedEntity.getContent(),
                feedEntity.getComments(),
                feedEntity.getPostStatus(),
                feedEntity.getTimestamp(),
                feedEntity.getSavedFeed()
        );
    }

}
