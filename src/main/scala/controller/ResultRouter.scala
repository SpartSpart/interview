package controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import imlicit.ObjectJsonFormat
import models.{Result, User}
import service.{ResultService, UserService}


object ResultRouter extends ObjectJsonFormat with SprayJsonSupport {
  val route: Route = cors(){
    post {
      path("api" / "result") {
        entity(as[Result]) {
          result => {
            ResultService.saveResult(result)
            complete(result.user.name)
            complete("POST")
          }
        }
      }
    } ~
      get {
        path("api" / "result") {
          {
            complete(ResultService.getAllResults)
          }
        }
      } ~
      get {
        path("api" / "result" / Segment) {
          {
            userName: String => {
              try {
                complete(ResultService.getResultByUserName(userName))
              }
              catch {
                case e: Exception => complete(e)
              }
            }
          }
        }
      } ~
      get {
        path("api" / "user") {
          {
            complete(ResultService.getAllUsers())
          }
        }
      }
  }
}


