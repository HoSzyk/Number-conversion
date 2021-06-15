import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import Services.NumberConversionService
import akka.http.scaladsl.model._

object HttpServerRouting {

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val route = {
      pathPrefix("api") {
        get {
          concat(
            pathPrefix("convert") {
              path(Segment / IntNumber) { (conversionType, number) =>
                pathEndOrSingleSlash {
                  complete(
                    NumberConversionService.convert(conversionType, number).map {
                      case Left(x) => StatusCodes.OK -> x
                      case Right(x) => StatusCodes.BadRequest -> x
                    }
                  )
                }
              }
            },
            path("status") {
              complete(StatusCodes.OK)
            }
          )
        }
      }
    }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
