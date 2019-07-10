package models

import java.util.UUID

import enumeratum._
import models.PublicHouse.Feature
import org.scanamo.DynamoFormat
import org.scanamo.semiauto._

case class PublicHouse(ItemId: UUID, name: String, address: PublicHouse.Address, features: List[Feature])

object PublicHouse {

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

  implicit val dynamoFormat : DynamoFormat[PublicHouse] = deriveDynamoFormat[PublicHouse]

}