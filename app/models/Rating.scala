package models

import java.time.Instant
import java.util.UUID

case class Rating(id: UUID, pubId: UUID, dateTime: Instant, opinions: Rating.Opinions)

object Rating {
  sealed abstract case class Scale(r: Int)

  object Scale {
    def apply(r: Int): Either[IllegalArgumentException, Scale] = {
      if (r < 0 || r > 5) {
        Left(new IllegalArgumentException("All ratings must be an integer between 0 and 5."))
      } else {
        Right(new Scale(r) {})
      }
    }
  }

  case class Opinions(
    trendiness: Rating.Scale,
    inverseGrottiness: Rating.Scale,
    busyness: Rating.Scale,
    welcoming: Rating.Scale,
    easyToGetService: Rating.Scale,
    enoughSeating: Rating.Scale,
    cocktailSelection: Rating.Scale,
    wineSelection: Rating.Scale,
    beerSelection: Rating.Scale,
    spiritSelection: Rating.Scale,
    valueForMoney: Rating.Scale
  )
}
