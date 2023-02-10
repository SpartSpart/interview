package controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import imlicit.ObjectJsonFormat
import akka.http.scaladsl.server.Directives._

object MainRouter {
  val routes = UserRouter.route ~ ResultRouter.route ~ QuestionRouter.route

}
