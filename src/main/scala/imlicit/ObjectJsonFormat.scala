package imlicit

import models.{QuestionResult, Result, User}
import spray.json.DefaultJsonProtocol

trait ObjectJsonFormat extends DefaultJsonProtocol {
  implicit val userFormatted = jsonFormat3(User)
  implicit val questionFormatted = jsonFormat4(QuestionResult)
  implicit val resultFormatted = jsonFormat2(Result)
}


