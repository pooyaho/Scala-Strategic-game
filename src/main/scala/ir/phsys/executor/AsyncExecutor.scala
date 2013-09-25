package ir.phsys.executor

import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 8/31/13
 *         Time: 4:50 PM
 */
object AsyncExecutor {

  def asyncWithCallback(callback: => Unit)(fn: => Unit): Unit = future {
    fn
    callback
  }

  def async(fn: => Unit): Unit = future {
    fn
  }

  def asyncLoop(delay: Int)(fn: => Unit): Unit = future {
    while (true) {
      fn
      Thread.sleep(delay)
    }
  }
}