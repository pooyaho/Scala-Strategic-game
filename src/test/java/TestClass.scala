import grizzled.slf4j.Logger
import org.scalatest.FunSuite
import scala.util.Random

class TestClass extends FunSuite {

  test("Test random in Scala") {
    var x = 0
    do {
      x = scala.util.Random.nextInt(5)
      println(x)
    } while (x != 0)
  }

  test("Test arrays") {
    val a = Array.ofDim[Int](4, 4)

    a.foreach(_.transform(_ => Random.nextInt()))

    a.zipWithIndex.foreach(i => {
      i._1.zipWithIndex.foreach(j => print(s"(${i._2},${j._2}) => ${j._1} \t"))
      println()
    })
  }

  var (width, height) = (4, 4)

  test("Test nearby blocks") {
    findNearbyBlocks(3, 3) foreach (x => println(x))
  }

  def findNearbyBlocks(i: Int, j: Int) = {
    val aboveBlocks = Set((i - 1, j - 1), (i - 1, j), (i - 1, j + 1))
    val belowBlocks = Set((i + 1, j - 1), (i + 1, j), (i + 1, j + 1))
    val leftBlocks = Set((i - 1, j - 1), (i, j - 1), (i + 1, j - 1))
    val rightBlocks = Set((i + 1, j + 1), (i, j + 1), (i - 1, j + 1))

    val result = aboveBlocks ++ belowBlocks ++ leftBlocks ++ rightBlocks ++ Nil

    result.filterNot(p => p._1 < 0 || p._1 >= width || p._2 < 0 || p._2 >= height)
  }

  def c: Int = 6

  val b: Int = 9

  var a = 12

  def quickSort(list: List[Int]): List[Int] = list match {
    case Nil => Nil
    case x :: xs => {
      val (smaller, rest) = xs.partition(_ < x)
      quickSort(smaller) ::: x :: quickSort(rest)
    }

  }

  test("Test Logger") {
    val logger = Logger[this.type]

    logger.warn("warning")
    logger.info("info")
    logger.trace("trace")
    logger.debug("debug")
    logger.error("error")

  }

  test("test cast class") {
    class A {
      //5692 l12345678
      def hi = println("HIIIIIIIIIIIIIIIIIII")
    }
    trait B {
      def getName: String
    }
    //    object A{
    //
    //    }
    implicit def toB(a: Object): B = new B {
      def getName = "Hi"
    }

    implicit def toA(b: B): A = new A {
      def getName = "Hi"

      override def hi = println("Overrided hi!")
    }
    val a = new A

    val b: B = a

    val anotherA: A = b
    println(b.getName)

    println(headofany(List(1, 2, 3).map(a*2)))
  }

  def headofany(x: Any) = x match {
    case xs: List[a] => xs.head
    // it has type a... but what is a?
  }
}