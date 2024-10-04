package com.iteamoa.mainpage.utils;

import com.iteamoa.mainpage.constant.DynamoDbEntityType;
import software.amazon.awssdk.enhanced.dynamodb.Key;

public class KeyConverter {
    static final String delimiter = "#";

    public static String toPk(DynamoDbEntityType type, Long id){
        return type.getPrefix() + delimiter + id;
    }

    public static String toPk(DynamoDbEntityType type, String id){
        return type.getPrefix() + delimiter + id;
    }

    public static Long toLongId(String key){
        return Long.parseLong(key.split(delimiter)[1]);
    }

    public static Key toKey(String Pk, String Sk){
        return Key.builder()
                .partitionValue(Pk)
                .sortValue(Sk)
                .build();
    }

    public static String toSeperatedId(String key){
        return key.split(delimiter)[1];
    }

}
