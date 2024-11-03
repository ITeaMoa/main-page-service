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
    private long pk;
    private long sk;
    private String entityType;
    private LocalDateTime timestamp;

    public static LikeDto toLikeDto(ItemEntity itemEntity) {
        return new LikeDto(
                KeyConverter.toLongId(itemEntity.getPk()),
                KeyConverter.toLongId(itemEntity.getSk()),
                itemEntity.getEntityType(),
                itemEntity.getTimestamp()
        );
    }
}
