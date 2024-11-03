package com.iteamoa.mainpage.dto;

import com.iteamoa.mainpage.constant.StatusType;
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
public class ApplicationDto {
    private String pk;
    private String sk;
    private String entityType;
    private String part;
    private StatusType status;
    private LocalDateTime timestamp;

    public static ApplicationDto toApplicationDto(ItemEntity itemEntity) {
        return new ApplicationDto(
                KeyConverter.toStringId(itemEntity.getPk()),
                KeyConverter.toStringId(itemEntity.getSk()),
                itemEntity.getEntityType().getType(),
                itemEntity.getPart(),
                itemEntity.getStatus(),
                itemEntity.getTimestamp()
        );
    }
}
