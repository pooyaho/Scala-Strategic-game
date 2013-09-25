package ir.phsys.game.unit

import ir.phsys.game.resources.{ResourceMap, ResourcesContainer}
import ir.phsys.game.exception.{UnitAlreadyCreatedException, UnitAlreadyDestroyedException}
import ir.phsys.executor.AsyncExecutor._
import grizzled.slf4j.Logger

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/3/13
 *         Time: 5:07 PM
 */
object GameUnit {
  val HEALTH_MAX_VALUE = 100
}

abstract class GameUnit(name: String) {
  protected val log = Logger[this.type]
  protected val HEALTH_MAX_VALUE = 100
  protected var health = 100
  val destroyDuration: Int
  val creationDuration: Int
  val creationCost: ResourceMap
  val producingValue: ResourceMap

  def getId = name

  //    protected val threadStatusHashMap = new TrieMap[String, (Long, Boolean)]()

  @volatile
  protected var status: UnitStatus = UnitStatus.NEED_TO_CREATE

  @volatile
  protected var destroying = false

   def destroy(f: Int => Unit): Unit = status match {
    case UnitStatus.DESTROYED => {
      throw new UnitAlreadyDestroyedException
    }
    case UnitStatus.DESTROYING =>
    case UnitStatus.PREPARED_TO_DESTROY => {
      destroying = true
      status = UnitStatus.DESTROYING
      async {
        waitUntil(Caller.Destroyer, destroyDuration, UnitStatus.DESTROYED) {
          f
        }
      }
    }
  }

  def getStatus = status

   def create(f: Int => Unit): Unit = status match {
    case UnitStatus.DESTROYED => throw new UnitAlreadyDestroyedException
    case UnitStatus.NEED_TO_CREATE => {
      ResourcesContainer -= creationCost
      status = UnitStatus.CONSTRUCTION
      async {
        waitUntil(Caller.Creator, creationDuration, UnitStatus.READY) {
          f
        }
      }
    }
    case _ => throw new UnitAlreadyCreatedException
  }

  def getHealthRate: Int = health

  def increaseHealth(): Unit = increaseHealth(1)

   def increaseHealth(value: Int): Unit = {
    if (health >= HEALTH_MAX_VALUE) {
      status = UnitStatus.READY
    } else {
      status = UnitStatus.REPAIRING
      health += value
    }
  }

  def decreaseHealth(): Unit = decreaseHealth(1)

   def decreaseHealth(value: Int): Unit = {
    if (health > 0) {
      status = UnitStatus.DESTROYING
      health -= value
      if (health <= 0) {
        health = 0
        status = UnitStatus.DESTROYED
      }
    } else {
      throw new UnitAlreadyDestroyedException
    }
  }

   def waitUntil(caller: Caller, seconds: Int, finalState: UnitStatus)(f: Int => Unit): Unit = {

    var timeElapse = 0
    var percent: Int = 0

    f(percent)

    while (timeElapse < seconds) {
      val inDestroyState = isInDestroyState
      log.info(s"Status $status")
      if (inDestroyState && caller == Caller.Creator) {
        log.info("Good bye! they have killed me")
        return
      }
      timeElapse += 1
      percent = ((timeElapse.asInstanceOf[Float] / seconds) * 100).toInt

      Thread.sleep(1000)
      f(percent)
    }
    if (status != null) {
      status = finalState
    }

  }

   def prepareToDestroy() {
    status = UnitStatus.PREPARED_TO_DESTROY
  }

   def isInDestroyState: Boolean = {
    status == UnitStatus.DESTROYED ||
      status == UnitStatus.DESTROYING ||
      status == UnitStatus.PREPARED_TO_DESTROY
  }

   abstract class Caller

   object Caller {

    object Creator extends Caller

    object Destroyer extends Caller

    object Updater extends Caller

  }

}