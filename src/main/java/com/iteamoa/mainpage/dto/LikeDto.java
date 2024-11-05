package com.iteamoa.mainpage.dto;

import com.iteamoa.mainpage.entity.ItemEntity;
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
    private String entityType;
    private String feedType;
    private LocalDateTime timestamp;

    public static LikeDto toLikeDto(ItemEntity itemEntity) {
        return new LikeDto(
                KeyConverter.toStringId(itemEntity.getPk()),
                KeyConverter.toStringId(itemEntity.getSk()),
                itemEntity.getEntityType().getType(),
                itemEntity.getFeedType(),
                itemEntity.getTimestamp()
        );
    }
}
