package repository

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import com.google.gson.Gson
import com.mongodb.spark.MongoSpark
import models.{QuestionResult, Result, User}
import org.apache.spark._
import org.apache.spark.sql.{Dataset, Encoders, SparkSession}
import org.bson.Document


object ResultRepository {
  def getResultByUserName(userName: String): ToResponseMarshallable = ???

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

    val results = spark.read.format("com.mongodb.spark.sql.DefaultSource")
      .option("database", "interview")
      .option("collection", "result")
      .load()

    results.as(encoder).collect().map(row => Result(row.user, row.question.toArray))

  }

  def saveResult(result: Result): Unit = {

    val gson = new Gson
    val jsonString = gson.toJson(result)

    //    val rdd = spark.sparkContext.parallelize(Seq[Result](result))

    val document = spark.sparkContext.parallelize((1 to 1).map(_ => Document.parse(jsonString)))

    MongoSpark.save(document)

  }

  def getAllQuestions: Set[String] = {
    val results = spark.read.format("com.mongodb.spark.sql.DefaultSource")
      .option("database", "interview")
      .option("collection", "result")
      .load()

    val allResults = results.as(encoder).collect().map(row => Result(row.user, row.question.toArray))

    allResults.toList.flatMap(element => element.question.map(q => q.description)).toSet
  }



  def getResultByUserName(userName: String): ToResponseMarshallable = {
    val allResults = results.as(encoder).collect().map(row => Result(row.user, row.question.toArray))
  }

}
