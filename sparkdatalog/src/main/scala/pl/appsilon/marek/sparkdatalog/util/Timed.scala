package pl.appsilon.marek.sparkdatalog.util

object Timed {
  def apply[A](name: String, f: () => A): A = {
    val start = System.nanoTime
    val result = f()
    println("TIME " + name + " --> " + (System.nanoTime - start) / 1e6 + " ms")
    result
  }
}

object NTimed {
  def apply[A](name: String, f: () => A): A = {
    f()
  }
}
