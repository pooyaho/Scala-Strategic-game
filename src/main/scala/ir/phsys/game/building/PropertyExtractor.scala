package ir.phsys.game.building

import ir.phsys.game.configuration.Configurations
import ir.phsys.game.technology.UpgradeLevel
import ir.phsys.game.resources.ResourceMap
import ir.phsys.game.unit.Population

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 9/1/13
 *         Time: 12:52 PM
 */
trait PropertyExtractor {
  //  private val log = Logger[PropertyExtractor]

  def getBlockType: String

  private val subUpdateProps = Configurations.getSubProperties(getBlockType + ".upgrade")

  val UPGRADES: List[UpgradeLevel] = subUpdateProps match {
    case Nil => Nil
    case xs => xs.map(p => {
      //    log.info("Upgrade key is " + p)
      new UpgradeLevel(p.substring(0, p.indexOf('.'))) {
        val cost: ResourceMap =
          Configurations.getResourceMapProperty(p + ".cost")

        val duration: Int =
          Configurations.getIntProperty(p + ".duration")
      }
    })
  }


  private val subUnitProps = Configurations.getSubProperties(getBlockType + ".unit")

  val UNITS: List[Population] = subUnitProps match {
    case Nil => Nil
    case xs => xs.map(p => {
      //    log.info("Unit key is " + p)
      new Population(p.substring(0, p.indexOf('.'))) {
        val creationCost: ResourceMap =
          Configurations.getResourceMapProperty(p + ".creation.cost")

        val creationDuration: Int =
          Configurations.getIntProperty(p + ".creation.duration")
        val producingValue: ResourceMap = ResourceMap()
      }
    })
  }

  lazy val creationCost: ResourceMap = Configurations.getResourceMapProperty(getBlockType + ".creation.cost")
  lazy val creationDuration: Int = Configurations.getIntProperty(getBlockType + ".creation.duration")
  lazy val destroyDuration: Int = Configurations.getIntProperty(getBlockType + ".destroy.duration")
  lazy val producingValue: ResourceMap = Configurations.getResourceMapProperty(getBlockType + ".produce")

}