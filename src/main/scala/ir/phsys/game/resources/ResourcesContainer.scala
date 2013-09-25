package ir.phsys.game.resources

import ir.phsys.game.exception.ResourceNotEnoughException
import grizzled.slf4j.Logger
import ir.phsys.game.configuration.Configurations


/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/6/13
 *         Time: 6:45 PM
 */

object ResourcesContainer {

  private var resources = ResourceMap()
  private val logger = Logger[this.type]
  $init$()

  def $init$() = {

    resources = ResourceMap(
      Wood() * Configurations.getIntProperty("initial.wood"),
      Food() * Configurations.getIntProperty("initial.food"),
      Coin() * Configurations.getIntProperty("initial.coin"),
      Metal() * Configurations.getIntProperty("initial.metal"),
      Oil() * Configurations.getIntProperty("initial.oil"),
      Knowledge() * Configurations.getIntProperty("initial.knowledge")
    )
  }

  def +=(res: AbstractResource) = {
    this.synchronized {
      resources += res
      logger.info(resources)
    }
  }

  def +=(res: ResourceMap) = {
    this.synchronized {
      resources add res
      logger.info(resources)
    }
  }

  def getResourceCount(res: AbstractResource) = resources(res)

  def -=(need: ResourceMap) = {
    this.synchronized {
      try {
        resources -= need
      } catch {
        case x: ResourceNotEnoughException => {
          logger.warn("Insufficient resource!")
          throw x
        }
      }
    }
  }

  def isResourceEnough(need: ResourceMap): Boolean = resources.meets(need)
}