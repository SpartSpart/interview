//import scala.collection.immutable.IndexedSeq
//import org.mongodb.scala._
//import org.mongodb.scala.connection.ClusterSettings
//import org.mongodb.scala.model.Aggregates._
//import org.mongodb.scala.model.Filters._
//import org.mongodb.scala.model.Projections._
//import org.mongodb.scala.model.Sorts._
//import org.mongodb.scala.model.Updates._
//import org.mongodb.scala.model._
//
//import scala.compat.java8.FunctionConverters.enrichAsJavaFunction
//
//case class MongoSparkCustom2() extends PersonJsonFormat {
//  val mongoClient: MongoClient = MongoClient()
//
//  // Use a Connection String
//  val mongoClient: MongoClient = MongoClient("mongodb://localhost")
//
//  // or provide custom MongoClientSettings
//  val clusterSettings: ClusterSettings = ClusterSettings.builder().hosts(List(new ServerAddress("localhost")).asJava).build()
//  val settings: MongoClientSettings = MongoClientSettings.builder().clusterSettings(clusterSettings).build()
//  val mongoClient: MongoClient = MongoClient(settings)
//
//  val database: MongoDatabase = mongoClient.getDatabase("mydb")
//
//  def getUsers {
//
//    val users = spark.read.format("com.mongodb.spark.sql.DefaultSource") //load("example.json")
//      .option("database", "interview")
//      .option("collection", "users")
//      .load()
//
//    users.toString()
//    //users.show()
////    users.printSchema()
////
////    spark.close()
//  }
//
//  def writeUser(user: models.User): Unit = {
//  //  val document = s"{name: '${user.name}', age: '${user.age}'}"
//
//    val gson = new Gson
//    val jsonString = gson.toJson(user)
//
//    val d = spark.sparkContext.parallelize((1 to 1).map(i => Document.parse(jsonString)))
//
//    MongoSpark.save(d)
//
//  }
//
//}
//
