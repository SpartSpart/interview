val akkaVersion = "2.7.0"
val akkaHttpVersion = "10.4.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
//  "org.mongodb.scala" %% "mongo-scala-driver" % "4.8.0",
  "org.mongodb.spark" %% "mongo-spark-connector" % "3.0.1",
  "org.apache.spark" %% "spark-core" % "3.0.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.slf4j" % "slf4j-simple" % "1.6.4",
//  "me.vican.jorge" %% "dijon" % "0.6.0",
//  "net.liftweb" %% "lift-json" % "2.5.1"
  "com.lihaoyi" %% "requests" % "0.6.9"
)
