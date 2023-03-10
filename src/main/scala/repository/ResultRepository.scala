package repository

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import com.google.gson.Gson
import com.mongodb.spark.MongoSpark
import models.{Result, User}
import org.apache.spark._
import org.apache.spark.sql.{Dataset, Encoders, SparkSession}
import org.bson.Document

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
    val resultDataFrame = spark.read.format("com.mongodb.spark.sql.DefaultSource")
      .option("database", "interview")
      .option("collection", "result")
      .load()
    resultDataFrame
  }

  def getAllResults = {
   getAllResultsFromDB.as(encoder).collect().map(row => Result(row.user, row.question.toArray))
  }


  def saveResult(result: Result): Unit = {

    val gson = new Gson
    val jsonString = gson.toJson(result)


    val document = spark.sparkContext.parallelize((1 to 1).map(_ => Document.parse(jsonString)))
    if (recordExists(result))
      throw new Exception("Record is already exist in database")
    else
      MongoSpark.save(document)

  }

  def getAllQuestionsFromResults: Set[String] = {
    val allResults = getAllResults

    allResults.toList.flatMap(element => element.question.map(q => q.description)).toSet
  }


  def getResultByUserName(userName: String): Result = {
    val allResults = getAllResults

    if (allResults.exists(r => r.user.name.equals(userName))) {
      allResults.find(r => r.user.name.equals(userName)).get
    }
    else {
      throw new Exception("User was not found")
    }

  }

  def getAllAvailableUsers(): List[User] = {
    val allResults = getAllResults
    allResults.map(result => result.user).toList
  }

  def recordExists(result: Result): Boolean = {
    val allResults = getAllResults
    allResults.foreach(res => {
      if (res.user.name == result.user.name && res.user.date == result.user.date)
        return true;
    })
    false
  }


}
