package com.iteamoa.mainpage.constant;

import lombok.Getter;

@Getter
public enum DynamoDbEntityType {
    USER("USER"),
    FEED("FEED"),
    INFO("INFO"),
    PROFILE("PROFILE"),
    FEEDTYPE("FEEDTYPE"),
    MESSAGE("MESSAGE"),
    NOTIFICATION("NOTIFICATION"),
    APPLICATION("APPLICATION"),
    LIKE("LIKE"),
    SAVEDFEED("SAVEDFEED");

    private final String prefix;

    DynamoDbEntityType(String prefix) {
        this.prefix = prefix;
    }

}
