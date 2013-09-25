package ir.phsys.game.building

import ir.phsys.game.exception.{IncorrectUnitException, CannotUpgradeException}
import ir.phsys.game.unit.{PopulationContainer, UnitStatus, Population, GameUnit}
import ir.phsys.game.technology.UpgradeLevel
import scala.collection.mutable
import ir.phsys.game.unit.UnitStatus.{REPAIRING, READY}
import ir.phsys.executor.AsyncExecutor
import ir.phsys.game.resources.ResourcesContainer
import ir.phsys.game.configuration.Configurations

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/3/13
 *         Time: 4:06 PM
 */
abstract class AbstractBlock(blockType: String, name: String)
  extends GameUnit(name) with PropertyExtractor with Cloneable{

  protected var currentLevel: UpgradeLevel = _
  protected val units = mutable.Buffer[Population]()
  private val unitTypes: List[Population] = UNITS
  private val upgradeList: List[UpgradeLevel] = UPGRADES

  private var remindedList: List[UpgradeLevel] = upgradeList

  def getUnitCount: Int = units.length

  def getUnitCount(unit: Population): Int = units.filter(p => p.isInstanceOf[Unit]).length

  def getCurrentLevel: UpgradeLevel = currentLevel

  private[game] final def createUnit(unit: Population) = {
    if (unitTypes.contains(unit)) {
      units += unit
      PopulationContainer.increasePopulation()
    } else {
      throw new IncorrectUnitException
    }
  }


  private[game] def upgrade(f: Int => Unit): Unit = remindedList match {
    case null => {
      remindedList = upgradeList
      upgrade(f)
    }
    case Nil => throw new CannotUpgradeException()
    case x :: xs => status match {
      case READY | REPAIRING => {
        ResourcesContainer -= x.cost
        status = UnitStatus.UPGRADING

        AsyncExecutor.async {
          waitUntil(Caller.Updater, x.duration, UnitStatus.READY) {
            f
          }
          currentLevel = x
          remindedList = xs
        }

      }
      case _ => throw new CannotUpgradeException("Object is not in ready state")
    }
  }

  def getUpgradeList = upgradeList

  def getBlockType: String = blockType

  override def clone(): AnyRef = super.clone()
}

object AbstractBlock {
  private val subUpdateProps = Configurations.getSubProperties("block.")

  val BLOCKS: List[AbstractBlock] = subUpdateProps match {
    case Nil => Nil
    case xs => xs.map(p => {
      new AbstractBlock(p.substring(0, p.indexOf('.')), "") {}
    })
  }
}


//abstract class BlockTypes {
//
//}
//
//object BlockTypes {
//
//
//  case object CITY_TOWN extends BlockTypes
//
//  case object DOCK extends BlockTypes
//
//  case object LIBRARY extends BlockTypes
//
//  case object MARKET extends BlockTypes
//
//  case object MILITARY extends BlockTypes
//
//  case object LumberMill extends BlockTypes
//
//}
