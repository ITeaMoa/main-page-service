package com.iteamoa.mainpage.dto;

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
    private String feedId;
    private LocalDateTime timestamp;
}
