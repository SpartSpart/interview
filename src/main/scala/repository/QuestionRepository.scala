package repository

import akka.http.scaladsl.model.Multipart
import com.google.gson.Gson
import com.mongodb.spark.MongoSpark
import models.Result
import org.apache.spark.sql.{Encoders, SparkSession}
import org.bson.Document


object QuestionRepository {
  val spark = SparkSession.builder()
    .appName("Test")
    .master("local[*]")
    .config("spark.mongodb.input.uri", "mongodb://localhost:27020/interview.question")
    .config("spark.mongodb.output.uri", "mongodb://localhost:27020/interview.question")
    .getOrCreate()

  val resultEncoder = Encoders.bean(Result.getClass)
  val encoder = org.apache.spark.sql.Encoders.product[Result]


  def getDataset (tempFilePath: String) = {
    spark.read
      .option("multiline", true)
      .json(tempFilePath)
  }

  def addData (tempFilePath: String)  = {
    getDataset(tempFilePath).write.format("com.mongodb.spark.sql.DefaultSource")
      .option("database", "interview")
      .option("collection", "question")
      .mode("append")
      .save()
  }


  def getAllQuestions: Set[String] = {
    val results = spark.read.format("com.mongodb.spark.sql.DefaultSource")
      .option("database", "interview")
      .option("collection", "result")
      .load()

    val allResults = results.as(encoder).collect().map(row=>Result(row.user, row.question.toArray))

    allResults.toList.flatMap(element=> element.question.map(q=>q.description)).toSet
  }

}
