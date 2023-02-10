package service

import models.{Result, User}
import repository.UserRepository

object UserService {


  def readUsers = {
    UserRepository.getUsers
  }

  def writeUser(user: User): Unit = {
    UserRepository.writeUser(user)
  }

}
