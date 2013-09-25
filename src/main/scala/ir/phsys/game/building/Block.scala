package ir.phsys.game.building

import scala.beans.BeanProperty
import ir.phsys.game.resources.ResourceMap
import ir.phsys.game.utils.ImplicitClasses._
import scala.collection.mutable
import ir.phsys.game.exception.NotDefinedPropertyException


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 9/9/13
 *         Time: 6:39 PM
 */
class Block {

  class BHashMap[A, B] extends mutable.HashMap[A, B] {
    override def default(key: A) = throw new NotDefinedPropertyException()
  }

  //  private val resourceProps = new BHashMap[String, ResourceMap]()
  private val properties = new BHashMap[String, String]()
  //  private val upgradeProps = new BHashMap[String, List[_]]()
  private val upgrades = mutable.Buffer[Upgrade]()

  def getProperties = properties

  //  stringProps +=(
  //    "name" -> "",
  //    "creationDuration" -> "",
  //    "destructionDuration" -> "",
  //    "creationCost" -> ""
  //    )

  //  resourceProps += (
  //    "creationCost" -> ResourceMap()
  //    )
  //  upgradeProps += (
  //    "upgradeList" -> List[Upgrade]()
  //    )

  def setCreationCost(token: String): Unit = {
    //    ("creationCost") = token.extractResourceMap()
  }


  def addUpgrade(upgrade: Upgrade): Unit = {
    //    properties("upgradeList") = properties("upgradeList") :+ upgrade
  }

  //  def getUpgradeList = properties("upgradeList")

  def setProperty(tuple: (String, String), prefix: String = "") = {
    properties(prefix + tuple._1) = tuple._2
  }

}

class Upgrade {
  @BeanProperty var name: String = _
  @BeanProperty var duration: String = _
  private var cost: ResourceMap = _

  def getCost = cost

  def setCost(cost: String) = {
    this.cost = cost.extractResourceMap()
  }
}

abstract class PropertyType {

}

object PropertyTypes {

  object CREATION extends PropertyType

  object DESTRUCTION extends PropertyType

}