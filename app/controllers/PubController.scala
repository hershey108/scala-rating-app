package controllers

import cats.instances.either._
import cats.instances.option._
import cats.instances.list._
import cats.instances.future._
import cats.syntax.traverse._
import clients.DynamoClientProvider
import javax.inject.Inject
import models.PublicHouse
import org.scanamo.ScanamoAsync
import org.scanamo.Table
import play.api.mvc.AbstractController
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import play.api.mvc.Request
import org.scanamo.error.DynamoReadError
import org.scanamo.ops.ScanamoOps
import org.scanamo.syntax._
import org.scanamo.syntax.attributeExists
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class PubController @Inject()(cc: ControllerComponents, dynamoProvider: DynamoClientProvider)(
  implicit executionContext: ExecutionContext
) extends AbstractController(cc) {
//  implicit val dynamoReadErrorWrites: Writes[DynamoReadError] = Json.writes[DynamoReadError]

  type OnError[A] = Either[DynamoReadError, A]
  val table: Table[PublicHouse] = Table[PublicHouse]("things_and_ratings")

  val getPubs = Action.async { implicit request: Request[AnyContent] =>
    val program: ScanamoOps[List[Either[DynamoReadError, PublicHouse]]] =
      table.filter(attributeExists('name)).scan()

    val pubs: Future[List[Either[DynamoReadError, PublicHouse]]] =
      ScanamoAsync(dynamoProvider.dynamo).exec(program)
    pubs.map(
      p =>
        p.sequence[OnError, PublicHouse] match {
          case Left(dre)   => InternalServerError(dre.toString)
          case Right(pubs) => Ok(Json.toJson(pubs))
        }
    )
  }

  val getPub = Action.async { implicit req =>
    val a: Future[Option[Either[DynamoReadError, PublicHouse]]] =
      req.getQueryString("pubId").flatTraverse { pid =>
        val program = table.get('ItemId -> pid)

        ScanamoAsync(dynamoProvider.dynamo).exec(program)
      }

    a.map {
      case None             => NotFound("Pub not found. Select a real pub, dammit!")
      case Some(Left(dre))  => InternalServerError(dre.toString)
      case Some(Right(pub)) => Ok(Json.toJson(pub))
    }
  }
}
