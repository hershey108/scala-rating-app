val nt = -1

import java.util.UUID
import models.PublicHouse
import models.PublicHouse.Address
import models.PublicHouse.Feature._
import models.PublicHouse.dynamoFormat

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBAsyncClientBuilder, AmazonDynamoDBClientBuilder}
import org.scanamo._
import org.scanamo.syntax._
import org.scanamo.error.DynamoReadError
import org.scanamo.ops.ScanamoOps
import org.scanamo.DynamoFormat._

val dynamo: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("ftsl-nonprod")).build()
dynamo.describeTable("things_and_ratings")



val theMall: PublicHouse = PublicHouse(
  ItemId = UUID.randomUUID(),
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

//implicit val f: DynamoFormat[models.PublicHouse.Feature] = DynamoFormat.coercedXmap[models.PublicHouse.Feature, String, Throwable](b => PublicHouse.Feature.withName(b))(_.entryName)
//implicit val b: DynamoFormat[UUID] = DynamoFormat.coercedXmap[UUID, String, Throwable](b => UUID.fromString(b))(_.toString)


val table: Table[PublicHouse] = Table[PublicHouse]("things_and_ratings")
val program: ScanamoOps[Option[Either[DynamoReadError, PublicHouse]]] = table.put(theMall)

val doStuff: Option[Either[DynamoReadError, PublicHouse]] = Scanamo(dynamo).exec(program)