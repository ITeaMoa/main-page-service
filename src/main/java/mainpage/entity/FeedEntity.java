package mainpage.entity;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.dto.FeedDto;
import com.iteamoa.mainpage.entity.BaseEntity;
import com.iteamoa.mainpage.utils.Comment;
import com.iteamoa.mainpage.utils.KeyConverter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Setter
@DynamoDbBean
public class FeedEntity extends BaseEntity {
    private String nickname;
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
    private Map<String, Integer> roles;
    private Map<String, Integer> recruitmentRoles;

    public FeedEntity() {}
    public FeedEntity(FeedDto feedDto){
        super(
                KeyConverter.toPk(DynamoDbEntityType.FEED, feedDto.getPk()),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, feedDto.getSk()),
                DynamoDbEntityType.FEED,
                feedDto.getTimestamp(),
                KeyConverter.toPk(DynamoDbEntityType.USER, feedDto.getCreatorId())
        );
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
        this.roles = feedDto.getRoles();
        this.recruitmentRoles = feedDto.getRecruitmentRoles();
    }

    @DynamoDbAttribute("nickname")
    public String getNickname() {
        return nickname;
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
    public List<Comment> getComments(){
        return comments;
    }

    @DynamoDbAttribute("postStatus")
    public boolean getPostStatus(){
        return postStatus;
    }

    @DynamoDbAttribute("savedFeed")
    public boolean getSavedFeed(){
        return savedFeed;
    }

    @DynamoDbAttribute("roles")
    public Map<String, Integer> getRoles(){
        return roles;
    }

    @DynamoDbAttribute("recruitmentRoles")
    public Map<String, Integer> getRecruitmentRoles(){
        return recruitmentRoles;
    }

}
