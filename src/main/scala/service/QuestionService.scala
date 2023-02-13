package service

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.Multipart
import akka.http.scaladsl.server.Directives.{complete, extractRequestContext, fileUpload, onSuccess}
import akka.stream.scaladsl.FileIO
import models.{Question, Result}
import repository.{QuestionRepository, ResultRepository}

import java.io.File
import java.nio.file.Paths
import scala.reflect.io.Directory
import scala.util.{Failure, Success}

object QuestionService {

  val tempLocalFilePath = "c:\\test"

  def addQuestionsFromFile(request: Multipart.FormData) = {
    extractRequestContext {
      ctx => {

        implicit val materializer = ctx.materializer
        implicit val ec = ctx.executionContext

        val tempFolder = new Directory(new File(tempLocalFilePath))
        tempFolder.createDirectory()

        fileUpload("file") {
          case (fileInfo, fileStream) =>
            val sink = FileIO.toPath(Paths.get(tempLocalFilePath) resolve fileInfo.fileName)
            val writeResult = fileStream.runWith(sink)

            onSuccess(writeResult) { result =>
              result.status match {
                case Success(_) => complete({
                  QuestionRepository.addData(tempLocalFilePath +"\\" +fileInfo.fileName)
                  tempFolder.deleteRecursively()
                  s"Successfully written ${result.count} bytes"
                })
                case Failure(e) => {
                  tempFolder.deleteRecursively()
                  throw e
                }
              }
            }
        }

      }

    }
  }

  def getAllQuestionsFromResults:Set[String]={
    ResultRepository.getAllQuestionsFromResults
  }

  def addQuestion(question: Question): String = {
    QuestionRepository.addQuestion(question)
  }

  def getAllQuestions(): List[Question] = {
    QuestionRepository.getAllQuestions()
  }

}
