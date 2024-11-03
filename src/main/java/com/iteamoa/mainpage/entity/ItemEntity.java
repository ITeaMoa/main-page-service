package com.iteamoa.mainpage.entity;

import com.iteamoa.mainpage.config.CommentListConverter;
import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.constant.StatusType;
import com.iteamoa.mainpage.dto.ApplicationDto;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.dto.LikeDto;
import com.iteamoa.mainpage.utils.Comment;
import com.iteamoa.mainpage.utils.KeyConverter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@DynamoDbBean
public class ItemEntity extends BaseEntity{
    private DynamoDbEntityType entityType;

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
    private boolean savedFeed;

    private String part;
    private StatusType status;

    public ItemEntity() {}
    public ItemEntity(LikeDto likeDto) {
        super(
                KeyConverter.toPk(DynamoDbEntityType.USER, likeDto.getPk()),
                KeyConverter.toPk(DynamoDbEntityType.LIKE, likeDto.getSk())
        );
        this.entityType = DynamoDbEntityType.LIKE;
    }

    public ItemEntity(FeedDto feedDto) {
        super(
                KeyConverter.toPk(DynamoDbEntityType.FEED, feedDto.getPk()),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, feedDto.getSk())
        );
        this.entityType = DynamoDbEntityType.FEED;
        this.creatorId = KeyConverter.toPk(DynamoDbEntityType.USER, feedDto.getCreatorId());
        this.title = feedDto.getTitle();
        this.recruitmentNum = feedDto.getRecruitmentNum();
        this.deadline = feedDto.getDeadline();
        this.place = feedDto.getPlace();
        this.period = feedDto.getPeriod();
        this.tags = feedDto.getTags();
        this.likesCount = feedDto.getLikesCount();
        this.content = feedDto.getContent();
        this.comments = feedDto.getComments();
        this.postStatus = feedDto.isPostStatus();
        this.savedFeed = feedDto.isSavedFeed();
    }

    public ItemEntity(ApplicationDto applicationDto) {
        super(
            KeyConverter.toPk(DynamoDbEntityType.USER, applicationDto.getPk()),
            KeyConverter.toPk(DynamoDbEntityType.APPLICATION, applicationDto.getSk())
        );
        this.entityType = DynamoDbEntityType.APPLICATION;
        this.part = applicationDto.getPart();
        this.status = StatusType.PENDING;
    }

    @DynamoDbAttribute("entityType")
    @DynamoDbSecondarySortKey(indexNames = "Like-index")
    public DynamoDbEntityType getEntityType(){
        return entityType;
    }

    @DynamoDbAttribute("creatorId")
    public String getCreatorId(){
        return creatorId;
    }

    @DynamoDbAttribute("title")
    public String getTitle(){
        return title;
    }

    @DynamoDbAttribute("recruitmentNum")
    public int getRecruitmentNum(){
        return recruitmentNum;
    }

    @DynamoDbAttribute("deadline")
    public LocalDateTime getDeadline(){
        return deadline;
    }

    @DynamoDbAttribute("place")
    public String getPlace(){
        return place;
    }

    @DynamoDbAttribute("period")
    public int getPeriod(){
        return period;
    }

    @DynamoDbAttribute("tags")
    public List<String> getTags(){
        return tags;
    }

    @DynamoDbAttribute("likesCount")
    @DynamoDbSecondarySortKey(indexNames = "MostLikedFeed-Index")
    public int getLikesCount(){
        return likesCount;
    }

    @DynamoDbAttribute("content")
    public String getContent(){
        return content;
    }

    @DynamoDbAttribute("comments")
    @DynamoDbConvertedBy(CommentListConverter.class)
    public List<Comment> getComments(){ return comments;}

    @DynamoDbAttribute("postStatus")
    public boolean getPostStatus(){
        return postStatus;
    }

    @DynamoDbAttribute("savedFeed")
    public boolean getSavedFeed(){
        return savedFeed;
    }

    @DynamoDbAttribute("part")
    public String getPart(){
        return part;
    }

    @DynamoDbAttribute("status")
    public StatusType getStatus(){
        return status;
    }

}

