import controller.{QuestionRouter, ResultRouter}
import imlicit.ObjectJsonFormat
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._

case class Person(name: String, age: Int)

case class PersonAdded(name: String)

object Main extends ObjectJsonFormat with SprayJsonSupport {

  implicit val system = ActorSystem(Behaviors.empty, "Test")

  def main(args: Array[String]): Unit = {
    Http().newServerAt("localhost", 8083).bind(ResultRouter.route ~ QuestionRouter.route)
  }
}
