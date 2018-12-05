import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Server extends App {
  val host = "0.0.0.0"
  val port = 9000

  implicit val system: ActorSystem = ActorSystem("hello-world")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  import akka.http.scaladsl.server.Directives._
  def route = path("hello") {
    get {
      complete("Hello, World!")
    }
  }

  val bindingFuture = Http().bindAndHandle(route, host, port)

  bindingFuture.onComplete {
    case Success(serverBinding) => println(s"listening to ${serverBinding.localAddress}")
    case Failure(error) => println(s"error: ${error.getMessage}")
  }
}
