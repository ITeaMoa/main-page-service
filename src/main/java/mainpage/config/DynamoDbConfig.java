package mainpage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfig {

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
    public AwsCredentialsProvider awsCredentialsProvider() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(
                accessKey,
                secretKey
        );
        return StaticCredentialsProvider.create(awsCredentials);
    }

}
