package com.iteamoa.mainpage.dto;

import com.iteamoa.mainpage.entity.ItemEntity;
import com.iteamoa.mainpage.utils.Comment;
import com.iteamoa.mainpage.utils.KeyConverter;
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
    private Long pk;
    private String sk;
    private String entityType;
    private Long creatorId;
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

    public static FeedDto toFeedDto(ItemEntity itemEntity){
        return new FeedDto(
                KeyConverter.toLongId(itemEntity.getPk()),
                KeyConverter.toStringType(itemEntity.getSk()),
                itemEntity.getEntityType(),
                KeyConverter.toLongId(itemEntity.getCreatorId()),
                itemEntity.getTitle(),
                itemEntity.getRecruitmentNum(),
                itemEntity.getDeadline(),
                itemEntity.getPlace(),
                itemEntity.getPeriod(),
                itemEntity.getTags(),
                itemEntity.getLikesCount(),
                itemEntity.getContent(),
                itemEntity.getComments(),
                itemEntity.getPostStatus(),
                itemEntity.getTimestamp(),
                itemEntity.getSavedFeed()
        );
    }

}
