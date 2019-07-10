import java.util.UUID

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import models.PublicHouse
import models.PublicHouse.Address
import models.PublicHouse.Feature.Garden
import org.scanamo.{Scanamo, Table}
import org.scanamo.error.DynamoReadError
import org.scanamo.ops.ScanamoOps

object Playground extends App {

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
  println(doStuff)

  val reader: ScanamoOps[List[Either[DynamoReadError, PublicHouse]]] = table.scan

  println(Scanamo(dynamo).exec(reader))


}
