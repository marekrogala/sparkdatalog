package pl.appsilon.marek.sparkdatalog.util

object Timed {
  def apply[A](name: String, f: => A): A = {
    print(name + "...")
    System.out.flush()

    val start = System.nanoTime
    val result = f
    println("" + (System.nanoTime - start) / 1e6 + " ms")
    System.out.flush()
    result
  }
}


object TimedN {
  def apply[A](f: () => A, n: Int) = {
    val start = System.nanoTime
    val result = for(i <- 1 to n) f()
    println("TIME  --> " + (System.nanoTime - start) / 1e6 + " ms")
  }
}
