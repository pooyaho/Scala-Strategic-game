package ir.phsys.executor

import scala.concurrent.{Future, future, promise}
import concurrent.ExecutionContext


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 8/31/13
 *         Time: 4:47 PM
 */

case class Cancelled() extends RuntimeException

object Job {
  def apply[A](fun: (() => Boolean) => A)(implicit ctx: ExecutionContext): Job[A] =
    new Job[A] {
      private val p = promise[A]()

      def result = p.future

      def cancel(): Unit = p.tryFailure(Cancelled())

      p tryCompleteWith future {
        fun(() => !p.isCompleted)
      }

      //            def getId(): Int = {
      //
      //            }
    }
}

trait Job[A] {
  def result: Future[A]

  def cancel(): Unit
}