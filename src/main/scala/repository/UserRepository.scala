package repository

import com.google.gson.Gson
import com.mongodb.spark.MongoSpark
import models.{Result, User}
import org.apache.spark.sql.SparkSession
import org.bson.Document

object UserRepository {

  val spark = SparkSession.builder()
    .appName("Test")
    .master("local[*]")
    .config("spark.mongodb.input.uri", "mongodb://localhost:27020/interview.users")
    .config("spark.mongodb.output.uri", "mongodb://localhost:27020/interview.users")
    .getOrCreate()


  def getUsers = {

    val users = spark.read.format("com.mongodb.spark.sql.DefaultSource") //load("example.json")
      .option("database", "interview")
      .option("collection", "users")
      .load()


    users.collect().map(row => User(row.getAs("id"), row.getAs("name"), row.getAs("age"), row.getAs("date")))
    //    users.collect().map(row=>models.User(Integer.parseInt(row.get(2).toString),row.get(3).toString, Integer.parseInt(row.get(1).toString)))


    //    users.toString()
    //    users.show()
    //    users.printSchema()
    //
    //    spark.close()
  }

  def writeUser(user: User): Unit = {
    val gson = new Gson
    val jsonString = gson.toJson(user)

    val d = spark.sparkContext.parallelize((1 to 1).map(i => Document.parse(jsonString)))

    MongoSpark.save(d)

  }

//  def writeResult(result: Result): Unit = {
//    val gson2 = new Gson
//    val jsonString = gson2.toJson(result)
//
//    val d = spark.sparkContext.parallelize((1 to 1).map(i => Document.parse(jsonString)))
//
//    MongoSpark.save(d)

//  }

}
