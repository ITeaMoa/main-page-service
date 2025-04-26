package com.iteamoa.mainpage.dto;

import com.iteamoa.mainpage.entity.LikeEntity;
import com.iteamoa.mainpage.utils.KeyConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private String pk;
    private String sk;
    private String creatorId;
    private String entityType;
    private String feedType;
    private LocalDateTime timestamp;

    public static LikeDto toLikeDto(LikeEntity likeEntity) {
        return new LikeDto(
                KeyConverter.toStringId(likeEntity.getPk()),
                KeyConverter.toStringId(likeEntity.getSk()),
                likeEntity.getCreatorId(),
                likeEntity.getEntityType().getType(),
                likeEntity.getFeedType(),
                likeEntity.getTimestamp()
        );
    }
}
