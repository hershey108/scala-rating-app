package models

import java.util.UUID

import enumeratum._
import models.PublicHouse.Feature
import org.scanamo.DynamoFormat
import org.scanamo.semiauto._
import play.api.libs.json.JsString
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Writes

case class PublicHouse(
  ItemId: UUID,
  name: String,
  address: PublicHouse.Address,
  features: List[Feature]
)

object PublicHouse {

  case class Address(
    streetAddress1: String,
    streetAddress2: String,
    town: String,
    postcode: String,
    country: String
  )

  sealed trait Feature extends EnumEntry

  object Feature extends Enum[Feature] {
    final case object Garden extends Feature
    final case object StreetDrinking extends Feature
    final case object Food extends Feature
    final case object Quiz extends Feature

    val values = findValues
  }

  implicit val dynamoFormat: DynamoFormat[PublicHouse] = deriveDynamoFormat[PublicHouse]

  implicit val publicHouseFeatureWrites: Writes[PublicHouse.Feature] = new Writes[Feature] {
    override def writes(o: Feature): JsValue = JsString(o.toString)
  }

  implicit val publicHouseAddressWrites: Writes[PublicHouse.Address] =
    Json.writes[PublicHouse.Address]
  implicit val publicHouseWrites: Writes[PublicHouse] = Json.writes[PublicHouse]

}
