package service

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import models.{Result, User}
import repository.{ResultRepository, UserRepository}

object ResultService {

  def getAllResults = {
    ResultRepository.getAllResults
  }

  def saveResult(result: Result): Unit = {
    ResultRepository.saveResult(result)
  }

  def getAllQuestions:Set[String]={
    ResultRepository.getAllQuestions
  }

  def getResultByUserName(userName: String): ToResponseMarshallable = {
    ResultRepository.getResultByUserName(userName)
  }
}
