package controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives.{as, complete, entity, get, pathPrefix, post}
import akka.http.scaladsl.server.Route
import imlicit.ObjectJsonFormat
import models.User
import service.UserService
import akka.http.scaladsl.server.Directives._


object UserRouter extends ObjectJsonFormat with SprayJsonSupport {
  val route: Route = pathPrefix("api" / "user") {
    {
      post {
        entity(as[User]) {
          //p => complete(models.User("UUID", 99))

          user => {
            UserService.writeUser(user)
            complete(user.name)
          }
        }
      }
    } ~ get {
      {

        complete(UserService.readUsers)
        //complete("GETUSERS")
      }
    }
  }
}
