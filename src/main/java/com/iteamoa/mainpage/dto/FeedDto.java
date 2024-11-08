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
import java.util.Map;

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
    private Map<String, Integer> Roles;
    private Map<String, Integer> recruitmentRoles;

    public static FeedDto toFeedDto(ItemEntity itemEntity){
        return new FeedDto(
                KeyConverter.toStringId(itemEntity.getPk()),
                KeyConverter.toStringId(itemEntity.getSk()),
                itemEntity.getEntityType().getType(),
                KeyConverter.toStringId(itemEntity.getCreatorId()),
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
                itemEntity.getSavedFeed(),
                itemEntity.getRoles(),
                itemEntity.getRecruitmentRoles()

        );
    }

}
