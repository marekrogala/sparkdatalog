name := "sparkDatalog"

version := "1.2.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
)

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.2.1"

libraryDependencies += "org.apache.spark" %% "spark-graphx" % "1.2.1"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.2.1"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "log4j" % "log4j" % "1.2.17"

libraryDependencies += "net.sf.squirrel-sql.thirdparty-non-maven" % "java-cup" % "0.11a"

mainClass in (Compile, run) := Some("pl.appsilon.marek.sparkdatalogexample.SparkDatalogExample")


