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
    ResultRepository.getAllQuestionsFromResults
  }

  def getResultByUserName(userName: String): Result = {
    ResultRepository.getResultByUserName(userName)
  }

  def getAllUsers(): List[User] = {
    ResultRepository.getAllAvailableUsers()
  }
}
