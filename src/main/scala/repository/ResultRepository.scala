package repository

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import com.google.gson.Gson
import com.mongodb.spark.MongoSpark
import models.{Question, QuestionResult, Result, User}
import org.apache.spark._
import org.apache.spark.sql.{Dataset, Encoders, SparkSession}
import org.bson.Document

import scala.runtime.Nothing$


object ResultRepository {

  val spark = SparkSession.builder()
    .appName("Test")
    .master("local[*]")
    .config("spark.mongodb.input.uri", "mongodb://localhost:27020/interview.result")
    .config("spark.mongodb.output.uri", "mongodb://localhost:27020/interview.result")
    .getOrCreate()

  val resultEncoder = Encoders.bean(Result.getClass)
  val encoder = org.apache.spark.sql.Encoders.product[Result]

  def getAllResultsFromDB: sql.DataFrame = {
     spark.read.format ("com.mongodb.spark.sql.DefaultSource")
    .option ("database", "interview")
    .option ("collection", "result")
    .load ()
  }

  def getAllResults = {
    getAllResultsFromDB.as(encoder).collect().map(row => Result(row.user, row.question.toArray))
  }

  def saveResult(result: Result): Unit = {

    val gson = new Gson
    val jsonString = gson.toJson(result)

    //    val rdd = spark.sparkContext.parallelize(Seq[Result](result))

    val document = spark.sparkContext.parallelize((1 to 1).map(_ => Document.parse(jsonString)))

    MongoSpark.save(document)

  }

  def getAllQuestionsFromResults: Set[String] = {
    val allResults = getAllResultsFromDB.as(encoder).collect().map(row => Result(row.user, row.question.toArray))

    allResults.toList.flatMap(element => element.question.map(q => q.description)).toSet
  }



  def getResultByUserName(userName: String): Result = {
    val allResults = getAllResultsFromDB.as(encoder).collect().map(row => Result(row.user, row.question.toArray))

    if (allResults.exists(r => r.user.name.equals(userName))) {
      allResults.find(r => r.user.name.equals(userName)).get
    }
    else {
      throw new Exception("User was not found")
    }

  }

  def getAllAvailableUsers(): List[User] = {
    val allResults = getAllResultsFromDB.as(encoder).collect().map(row => Result(row.user, row.question.toArray))
    allResults.map(result => result.user).toList
  }

}
