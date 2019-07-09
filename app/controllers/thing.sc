import java.util.UUID
val nt = 1

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBAsyncClientBuilder, AmazonDynamoDBClientBuilder}
import models.Pub
import models.Pub.Address
import models.Pub.Feature.Garden
import org.scanamo._
import org.scanamo.syntax._
import org.scanamo.error.DynamoReadError
import org.scanamo.ops.ScanamoOps
import org.scanamo.auto._
import org.scanamo.DynamoFormat._

val dynamo: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("ftsl-nonprod")).build()
dynamo.describeTable("things_and_ratings")



val theMall: Pub = Pub(
  id = UUID.randomUUID(),
  name = "The Mall",
  address = Address(
    streetAddress1 = "123 mall road",
    streetAddress2 = "Mall town",
    town = "London",
    postcode = "MALL",
    country = "UK"
  ),
  features = List(
    Garden
  )

)

implicit val f: DynamoFormat[Pub.Feature] = DynamoFormat.coercedXmap[Pub.Feature, String, Throwable](b => Pub.Feature.withName(b))(_.entryName)
//implicit val b: DynamoFormat[UUID] = DynamoFormat.coercedXmap[UUID, String, Throwable](b => UUID.fromString(b))(_.toString)


val table: Table[Pub] = Table[Pub]("things_and_ratings")


//val program: ScanamoOps[Option[Either[DynamoReadError, Pub]]] = table.put(theMall)