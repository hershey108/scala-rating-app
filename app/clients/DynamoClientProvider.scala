package clients

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder

class DynamoClientProvider {

  lazy val dynamo: AmazonDynamoDBAsync = AmazonDynamoDBAsyncClientBuilder.standard().build()

}
