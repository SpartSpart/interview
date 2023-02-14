package imlicit

import models.{Question, QuestionResult, Result, User}
import spray.json.DefaultJsonProtocol

trait ObjectJsonFormat extends DefaultJsonProtocol {
  implicit val userFormatted = jsonFormat4(User)
  implicit val questionFomatted = jsonFormat3(Question)
  implicit val questionFormatted = jsonFormat4(QuestionResult)
  implicit val resultFormatted = jsonFormat2(Result)
}


