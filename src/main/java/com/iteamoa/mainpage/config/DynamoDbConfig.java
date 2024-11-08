package com.iteamoa.mainpage.config;

import com.iteamoa.mainpage.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    @Value("${aws.table}")
    private String table;

    @Value("${aws.accesskey}")
    private String accessKey;

    @Value("${aws.secretkey}")
    private String secretKey;

    @Bean
    protected DynamoDbClient dynamoDbClient(AwsCredentialsProvider awsCredentialsProvider) {
        return DynamoDbClient
                .builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient
                .builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(
                accessKey,
                secretKey
        );
        return StaticCredentialsProvider.create(awsCredentials);
    }

    @Bean
    public DynamoDbTable<ItemEntity> FeedTable(DynamoDbEnhancedClient client){
        return client.table(table, TableSchema.fromBean(ItemEntity.class));
    }

}
