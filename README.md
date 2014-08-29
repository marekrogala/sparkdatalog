SparkDatalog
============

A Datalog API for Spark, which allows for mix Datalog queries into Spark computations.


Example
-------
```scala
// Assuming we have the following RDDs (for example read from HDFS):
val edgesRdd = sc.parallelize(Seq((1, 2, 1), (1, 3, 5), (2, 3, 1)))
val sourceRdd = sc.parallelize(Seq(0))

// Compute shortests paths from the source node using Spark Datalog API:

//   1. Create a Database from Relations built from RDDs.    
val database = Database(
  Relation.ternary("Edge", edgesRdd),
  Relation.unary("IsSource", sourceRdd))

//   2. Execute a Datalog query on the database, producing a new Database.
val query = """
    |declare Path(int v, int dist aggregate Min).
    |Path(x, d) :- IsSource(s), Edge(s, x, d).
    |Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.
  """.stripMargin
val resultDatabase: Database = database.datalog(query)

//   3. Retrieve the result from the new Database.
val resultPathsRdd: RDD[Seq[Int]] = resultDatabase("Path")


// We can now save the paths RDD to distributed storage or perform further computations on it.
// We can of course also print it to stdout:
print(resultPathsRdd.collect().map("Path(" + _.mkString(", ") + ")").mkString("\n"))
```


Quick start
-----------
Check out SparkDatalog source with the example project:
```
git clone git@github.com:marekrogala/sparkdatalog.git
```

Run the example program:
```
cd sparkdatalog\sparkdatalog
sbt run
```








