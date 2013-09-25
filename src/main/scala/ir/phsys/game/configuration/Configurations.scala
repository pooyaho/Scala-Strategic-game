package ir.phsys.game.configuration

import ir.phsys.game.utils.ConfigurationUtil
import ir.phsys.game.resources._
import scala.collection.JavaConverters._

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/19/13
 *         Time: 2:40 PM
 */
object Configurations {
  private val props = ConfigurationUtil.extractProps("/config.properties")

  def getStringProperty(key: String): String = {
    props.getProperty(key)
  }

  def getIntProperty(key: String): Int = {
    props.getProperty(key).toInt
  }

  def getResourceMapProperty(key: String): ResourceMap = {
    val str = getStringProperty(key)
    val resMap = ResourceMap()
    val Pattern = """\s*([a-zA-Z]*)\s*\*\s*(\d*)\s*""".r

    str.split(',').foreach(token => token match {
      case Pattern(x, y) => {
        val multiplier = y.toInt
        x.toLowerCase match {
          case "food" => resMap += Food() * multiplier
          case "wood" => resMap += Wood() * multiplier
          case "knowledge" => resMap += Knowledge() * multiplier
          case "metal" => resMap += Metal() * multiplier
          case "oil" => resMap += Oil() * multiplier
          case "coin" => resMap += Coin() * multiplier
        }
      }
      case _ => throw new IllegalArgumentException(token)
    })
    resMap
  }

  def getSubProperties(key: String): List[String] = {
    val filter = props.stringPropertyNames().asScala.filter(_.startsWith(key))
    if (filter.isEmpty) Nil
    filter.map(_.substring(key.length + 1)).map(p => s"$key.${p.substring(0, p.indexOf('.'))}").toList
  }
}