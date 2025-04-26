package com.iteamoa.mainpage.dto;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import com.iteamoa.mainpage.constant.StatusType;
import com.iteamoa.mainpage.entity.ApplicationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
    private String pk;
    private String sk;
    private String creatorId;
    private DynamoDbEntityType entityType;
    private String part;
    private StatusType status;
    private String feedType;
    private LocalDateTime timestamp;

    public static ApplicationDto toApplicationDto(ApplicationEntity applicationEntity) {
        return new ApplicationDto(
                applicationEntity.getPk(),
                applicationEntity.getSk(),
                applicationEntity.getCreatorId(),
                DynamoDbEntityType.APPLICATION,
                applicationEntity.getPart(),
                applicationEntity.getStatus(),
                applicationEntity.getFeedType(),
                applicationEntity.getTimestamp()
        );
    }


}
