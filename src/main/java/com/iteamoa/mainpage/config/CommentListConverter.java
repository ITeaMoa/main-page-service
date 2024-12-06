package com.iteamoa.mainpage.config;

import com.iteamoa.mainpage.utils.Comment;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentListConverter implements AttributeConverter<List<Comment>> {

    public AttributeValue transformFrom(List<Comment> input) {
        List<AttributeValue> serializedComments = input.stream()
                .map(comment -> AttributeValue.builder()
                        .m(Map.of(
                                "userId", AttributeValue.builder().s(comment.getUserId()).build(),
                                "comment", AttributeValue.builder().s(comment.getComment()).build(),
                                "timestamp", AttributeValue.builder().s(comment.getTimestamp().toString()).build(),
                                "nickname", AttributeValue.builder().s(comment.getUserId()).build()
                        ))
                        .build())
                .collect(Collectors.toList());

        return AttributeValue.builder()
                .l(serializedComments)
                .build();
    }

    @Override
    public List<Comment> transformTo(AttributeValue input) {
        return input.l().stream()
                .map(attr -> {
                    Map<String, AttributeValue> attributes = attr.m();
                    return new Comment(
                            Optional.ofNullable(attributes.get("userId")).map(AttributeValue::s).orElse(""),
                            Optional.ofNullable(attributes.get("comment")).map(AttributeValue::s).orElse(""),
                            LocalDateTime.parse(Optional.ofNullable(attributes.get("timestamp")).map(AttributeValue::s).orElse("")),
                            Optional.ofNullable(attributes.get("nickname")).map(AttributeValue::s).orElse("")
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public EnhancedType<List<Comment>> type() {
        return EnhancedType.listOf(Comment.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.L;
    }
}

