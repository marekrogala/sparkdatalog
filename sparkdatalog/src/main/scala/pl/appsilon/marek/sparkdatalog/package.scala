package pl.appsilon.marek

package object sparkdatalog {
  type Valuation = Map[String, Int]
  type Fact = Seq[Int]

  def keyForFact: (sparkdatalog.Fact) => Long = _(0)
}
