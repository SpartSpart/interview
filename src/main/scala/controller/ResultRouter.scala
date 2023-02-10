package controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import imlicit.ObjectJsonFormat
import models.{Result, User}
import service.{ResultService, UserService}


object ResultRouter extends ObjectJsonFormat with SprayJsonSupport {
  val route: Route = {
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
              complete(ResultService.getResultByUserName(userName))
            }

          }
        }
      }
  }
}

