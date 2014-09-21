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

  def merge(instance: RelationInstance, context: StaticEvaluationContext): StateShard =
    StateShard(relations + (instance.name ->
      relations.get(instance.name).map(_.merge(instance, context.aggregations.get(instance.name))).getOrElse(instance)))

  def ++(other: StateShard): StateShard = StateShard(relations ++ other.relations)    // TODO: merge a nie zastap

  override def toString = "StateShard(relations: " + relations + "\n\t delta: " + delta
}
