package mainpage.repository;

import com.iteamoa.mainpage.entity.ApplicationEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class ApplicationRepository {
    private final DynamoDbTable<ApplicationEntity> applicationTable;

    public ApplicationRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.applicationTable = enhancedClient.table("IM_MAIN_TB", TableSchema.fromBean(ApplicationEntity.class));
    }

    public ApplicationEntity getApplication(ApplicationEntity applicationEntity) {
        return applicationTable.getItem(applicationEntity);
    }

    public void saveApplication(ApplicationEntity applicationEntity) {
        applicationTable.putItem(applicationEntity);
    }

    public void deleteApplication(ApplicationEntity applicationEntity) {
        applicationTable.deleteItem(applicationEntity);
    }

}
