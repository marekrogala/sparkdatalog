package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.Aggregation

case class StaticEvaluationContext(aggregations: Map[String, Aggregation])
