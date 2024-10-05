package com.iteamoa.mainpage.entity;

import com.iteamoa.mainpage.config.CommentListConverter;
import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.utils.Comment;
import com.iteamoa.mainpage.utils.KeyConverter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@DynamoDbBean
public class FeedEntity extends BaseEntity{
    private String entityType;
    private String creatorId;
    private String title;
    private String recruitmentNum;
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

    public FeedEntity() { }
    public FeedEntity(FeedDto feedDto) {
        super(
                KeyConverter.toPk(DynamoDbEntityType.FEED, feedDto.getPk()),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, feedDto.getSk())
        );
        this.entityType = feedDto.getEntityType();
        this.creatorId = feedDto.getCreatorId();
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
        this.timestamp = feedDto.getTimestamp();
        this.savedFeed = feedDto.isSavedFeed();
    }

    @DynamoDbAttribute("entityType")
    public String getEntityType(){
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
    public String getRecruitmentNum(){
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

    @DynamoDbAttribute("timestamp")
    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    @DynamoDbAttribute("savedFeed")
    public boolean getSavedFeed(){
        return savedFeed;
    }


}

