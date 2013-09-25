package ir.phsys.executor

import org.scalatest.FunSuite

import scala.concurrent._
import concurrent.ExecutionContext.Implicits.global

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 8/31/13
 *         Time: 4:52 PM
 */
class Job$Test extends FunSuite {

  test("Test jobs") {
    val a = Job {
      active => {
        for (i <- 1 to 100 if active()) {
          blocking {
            Thread.sleep(10) // doing actual heavy work here
            println(s"A $i")
          }
        }

      }
    }

    Job {
      active => {
        for (i <- 1 to 100 if active()) {
          blocking {
            Thread.sleep(1000) // doing actual heavy work here
            println(s"B $i")
          }
        }
      }
    }

    while (!a.result.isCompleted) {}
    println("A has finished!")

  }


}