package pl.appsilon.marek.sparkdatalog.eval

case class StateShard(relations: Map[String, RelationInstance], delta: Map[String, RelationInstance] = Map()) {
  def deltaEmpty: Boolean = delta.forall(_._2.isEmpty)

  def delted(oldShard: Option[StateShard]) = {
    val oldRelations = oldShard.map(_.relations).getOrElse(Map())
    val delta = relations.map({ case (name, instance) =>
      name -> oldRelations.get(name).map(instance.subtract).getOrElse(instance)
    }).toMap
    copy(delta = delta)
  }

  def merge(instance: RelationInstance, context: StaticEvaluationContext): StateShard = {
    // println("merging " + instance.toString + " into " + this.relations)
    StateShard(relations + (instance.name ->
      relations.get(instance.name).map(_.merge(instance, context.aggregations.get(instance.name))).getOrElse(instance)))
  }

  def merge(newRelations: Map[String, Iterable[RelationInstance]], context: StaticEvaluationContext): StateShard = {
    val keys = newRelations.keySet ++ relations.keys
    val mergedRelations: Map[String, RelationInstance] = keys.toSeq.map({ key =>
      key -> ((relations.get(key), newRelations.get(key)) match {
        case (Some(left), None) => left
        case (None, Some(right)) => RelationInstance.createCombined(right, context)
        case (Some(left), Some(right)) => RelationInstance.createCombined(left +: right.toSeq, context)
        case _ => ???
      })
    })(collection.breakOut)
    StateShard(mergedRelations)
  }

  def ++(other: StateShard): StateShard = StateShard(relations ++ other.relations)    // TODO: merge a nie zastap

  override def toString = "StateShard(relations: " + relations + "\n\t delta: " + delta
}

object StateShard {
  def fromRelationInstance(relation: RelationInstance, context: StaticEvaluationContext) = StateShard(Map()).merge(relation, context)
  def fromRelationInstances(relations: Map[String, Iterable[RelationInstance]], context: StaticEvaluationContext): StateShard = {
    val mergedRelations: Map[String, RelationInstance] = relations.mapValues({ instances =>
        RelationInstance.createCombined(instances, context)
      })
    StateShard(mergedRelations)
  }
  def fromRelationInstances(relations: Iterable[RelationInstance], context: StaticEvaluationContext): StateShard = {
    fromRelationInstances(relations.groupBy(_.name), context)
  }
}
