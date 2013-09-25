package ir.phsys.game.unit

import ir.phsys.game.exception.{EmptyPopulationException, PopulationExceedException}

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 9/1/13
 *         Time: 2:21 PM
 */
object PopulationContainer {



  private var population: Int = 0
  private val POPULATION_LIMIT: Int = 200

  def increasePopulation() = this.synchronized {
    population match {
      case x if population >= POPULATION_LIMIT => {
        throw new PopulationExceedException()
      }
      case _ => population += 1
    }
  }

  def decreasePopulation() = this.synchronized {
    population match {
      case x if x <= 0 => throw new EmptyPopulationException
      case _ => population -= 1
    }
  }
}