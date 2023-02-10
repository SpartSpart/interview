package controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.Multipart
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import imlicit.ObjectJsonFormat
import service.{QuestionService, ResultService}

object QuestionRouter extends ObjectJsonFormat with SprayJsonSupport {
  val route: Route = {
    path("api" / "question") {
      get {
        {
          complete(QuestionService.getAllQuestions)
        }
      }
    } ~
      path("api" / "question" / "upload") {
        post {

          entity(as[Multipart.FormData]) {
            request =>
              QuestionService.addQuestionsFromFile(request)
          }
        }
      }
  }
}