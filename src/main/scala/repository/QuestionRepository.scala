package repository

import akka.http.scaladsl.model.Multipart
import com.google.gson.Gson
import models.{Question, Result}
import org.apache.spark.sql.{Encoders, SparkSession}
import org.bson.Document
import com.mongodb.spark.MongoSpark

import repository.UserRepository.spark


object QuestionRepository {

  val spark = SparkSession.builder()
    .appName("Test")
    .master("local[*]")
    .config("spark.mongodb.input.uri", "mongodb://localhost:27020/interview.question")
    .config("spark.mongodb.output.uri", "mongodb://localhost:27020/interview.question")
    .getOrCreate()

  val resultEncoder = Encoders.bean(Result.getClass)
  val resultEncoder2 = org.apache.spark.sql.Encoders.product[Result]
  val questionEncoder = org.apache.spark.sql.Encoders.product[Question]
  val gson = new Gson


  def getDataset(tempFilePath: String) = {
    spark.read
      .option("multiline", true)
      .json(tempFilePath)
  }

  def addData(tempFilePath: String) = {
    val a = getDataset(tempFilePath)

   // a.foreach(a.collect().foreach(e=>e.))
    getDataset(tempFilePath).write.format("com.mongodb.spark.sql.DefaultSource")
      .option("database", "interview")
      .option("collection", "question")
      .mode("overwrite")
      .save()
  }


  def getAllQuestionsFromResult: Set[String] = {
    val results = spark.read.format("com.mongodb.spark.sql.DefaultSource")
      .option("database", "interview")
      .option("collection", "result")
      .load()

    val allResults = results.as(resultEncoder2).collect().map(row => Result(row.user, row.question.toArray))

    allResults.toList.flatMap(element => element.question.map(q => q.description)).toSet
  }

  def addQuestion(question: Question): String = {

    val jsonString = gson.toJson(question)

    val document = spark.sparkContext.parallelize((1 to 1).map(i => Document.parse(jsonString)))

    MongoSpark.save(document)

    document.id.toString

  }

  def getAllQuestions(): List[Question] = {
    val questions = spark.read.format("com.mongodb.spark.sql.DefaultSource")
      .option("database", "interview")
      .option("collection", "question")
      .load()

    val a = questions.as(questionEncoder).collect().map(row => Question(row.theme, row.description, row.answer)).toList




    a
  }

  def distinctBy[L, E](list: List[L])(f: L => E): List[L] =
    list.foldLeft((Vector.empty[L], Set.empty[E])) {
      case ((acc, set), item) =>
        val key = f(item)
        if (set.contains(key)) (acc, set)
        else (acc :+ item, set + key)
    }._1.toList


}
