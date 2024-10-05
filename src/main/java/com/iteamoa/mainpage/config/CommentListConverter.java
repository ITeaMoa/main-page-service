package com.iteamoa.mainpage.config;

import com.iteamoa.mainpage.utils.Comment;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import javax.management.Attribute;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentListConverter implements AttributeConverter<List<Comment>> {

    @Override
    public AttributeValue transformFrom(List<Comment> input) {
        List<String> serializedComments = input.stream()
                .map(Comment::toString) // Comment 객체를 문자열로 직렬화하는 방법 구현
                .toList();
        return AttributeValue.builder()
                .l(serializedComments.stream()
                        .map(comment -> AttributeValue.builder().s(comment).build()) // 문자열을 AttributeValue로 변환
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public List<Comment> transformTo(AttributeValue input) {
        for(AttributeValue attribute : input.l()) {
            System.out.println(attribute.s());
        }

        return input.l().stream()
                .map(attr -> {
                    Map<String, AttributeValue> attributes = attr.m();
                    return new Comment(
                            Optional.ofNullable(attributes.get("userId")).map(AttributeValue::s).orElse(""),
                            Optional.ofNullable(attributes.get("comment")).map(AttributeValue::s).orElse(""),
                            LocalDateTime.parse(Optional.ofNullable(attributes.get("commentTime")).map(AttributeValue::s).orElse("1970-01-01T00:00"))
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

