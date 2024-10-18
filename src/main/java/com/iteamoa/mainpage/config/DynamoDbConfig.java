package com.iteamoa.mainpage.config;

import com.iteamoa.mainpage.entity.FeedEntity;
import com.iteamoa.mainpage.entity.LikeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    @Value("${aws.endpoint}")
    private String endpoint;

    @Value("${aws.table}")
    private String table;

    @Bean
    protected DynamoDbClient dynamoDbClient() {
        return DynamoDbClient
                .builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .endpointOverride(URI.create(endpoint))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(@Autowired DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient
                .builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public DynamoDbTable<FeedEntity> FeedTable(DynamoDbEnhancedClient client){
        return client.table(table, TableSchema.fromBean(FeedEntity.class));
    }

}
