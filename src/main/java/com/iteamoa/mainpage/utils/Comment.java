package com.iteamoa.mainpage.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Comment {
    private String userId;
    private String comment;
    private LocalDateTime commentTime;

    @Override
    public String toString() {
        return "{userId:" + userId +
                ", comment:" + comment +
                ", commentTime:" + commentTime + "}";
    }
}
