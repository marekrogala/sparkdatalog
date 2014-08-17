name := "s2g"

version := "1.0.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
)

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.0.2"

libraryDependencies += "org.apache.spark" %% "spark-graphx" % "1.0.2"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

mainClass in (Compile, run) := Some("s2g.S2G")


