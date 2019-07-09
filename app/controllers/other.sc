import java.util.UUID

import enumeratum.{Enum, EnumEntry}
val nt = 1

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBAsyncClientBuilder, AmazonDynamoDBClientBuilder}
import org.scanamo._
import org.scanamo.syntax._
import org.scanamo.error.DynamoReadError
import org.scanamo.ops.ScanamoOps
import org.scanamo.auto._
import org.scanamo.DynamoFormat._

val dynamo: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("ftsl-nonprod")).build()
dynamo.describeTable("things_and_ratings")


case class Pub(id: UUID, name: String, address: Pub.Address, features: List[Pub.Feature])

object Pub {

  case class Address(streetAddress1: String,
                     streetAddress2: String,
                     town: String,
                     postcode: String,
                     country: String)

  sealed trait Feature extends EnumEntry

  object Feature extends Enum[Feature] {
    final case object Garden extends Feature
    final case object StreetDrinking extends Feature
    final case object Food extends Feature
    final case object Quiz extends Feature

    val values = findValues
  }

}


val theMall: Pub = Pub(
  id = UUID.randomUUID(),
  name = "The Mall",
  address = Pub.Address(
    streetAddress1 = "123 mall road",
    streetAddress2 = "Mall town",
    town = "London",
    postcode = "MALL",
    country = "UK"
  ),
  features = List(
    Pub.Feature.Garden
  )

)



//implicit val f: DynamoFormat[Pub.Feature] = DynamoFormat.coercedXmap[Pub.Feature, String, Throwable](b => Pub.Feature.withName(b))(_.entryName)
//implicit val b: DynamoFormat[UUID] = DynamoFormat.coercedXmap[UUID, String, Throwable](b => UUID.fromString(b))(_.toString)

case class Tom(what: Pub.Address)

val table: Table[Pub] = Table[Pub]("things_and_ratings")


//val program: ScanamoOps[Option[Either[DynamoReadError, Pub]]] = table.put(theMall)