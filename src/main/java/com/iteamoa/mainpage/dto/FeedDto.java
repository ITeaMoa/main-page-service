package com.iteamoa.mainpage.dto;

import com.iteamoa.mainpage.entity.FeedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedDto {
    private String pk;
    private String sk;
    private String title;

    public static FeedDto toFeedDto(FeedEntity feedEntity){
        return new FeedDto(
                feedEntity.getPk(),
                feedEntity.getSk(),
                feedEntity.getTitle()
        );
    }

}
