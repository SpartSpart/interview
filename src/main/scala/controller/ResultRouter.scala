package controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import imlicit.ObjectJsonFormat
import models.{Result}
import service.{ResultService}

object ResultRouter extends ObjectJsonFormat with SprayJsonSupport {
  val route: Route = cors() {
    post {
      path("api" / "result") {
        entity(as[Result]) {
          result => {
            try {
              ResultService.saveResult(result)
               complete("Result for " + result.user.name + " saved successfully" )
            }
            catch {
              case e: Exception => complete(500 -> e.getMessage)
            }
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


