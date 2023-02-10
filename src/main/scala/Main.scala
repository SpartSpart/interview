import controller.{QuestionRouter, ResultRouter, UserRouter}
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
    //Http().bindAndHandle(UserRouter.route, config.getString("http.interface"), config.getInt("http.port"))
    Http().newServerAt("localhost", 8083).bind(UserRouter.route ~ ResultRouter.route ~ QuestionRouter.route)
  }
}
