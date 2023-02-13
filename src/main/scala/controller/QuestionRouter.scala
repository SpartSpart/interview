package controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.Multipart
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import imlicit.ObjectJsonFormat
import models.Question
import service.{QuestionService, ResultService}

object QuestionRouter extends ObjectJsonFormat with SprayJsonSupport {
  val route: Route = cors() {
    get {
      path("api" / "question" / "results") {
        {
          complete(QuestionService.getAllQuestionsFromResults)
        }
      }
    } ~
      post {
        path("api" / "question" / "upload") {

          entity(as[Multipart.FormData]) {
            request =>
              QuestionService.addQuestionsFromFile(request)
          }
        }
      } ~
      post {
        path("api" / "question" / "add") {

          entity(as[Question]) {

            question => {
              complete(QuestionService.addQuestion(question))

            }
          }
        }
      } ~
      get {
        path("api" / "question" / "getall") {


          complete(QuestionService.getAllQuestions())

        }
      }
  }
}