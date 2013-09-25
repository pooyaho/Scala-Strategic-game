package ir.phsys.game.utils

import ir.phsys.game.resources._
import scala.collection.immutable.HashMap
import scala.util.parsing.combinator._
import ir.phsys.game.configuration.TestDsl


/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/13/13
 *         Time: 2:39 PM
 */
object ImplicitClasses {

  implicit class ExtractPackageName(clazz: Class[_]) {
    def extractClassSimpleName(): String = {
      val str = clazz.getName
      val index = str.indexOf('$')

      if (index < 0) {
        str
      } else {
        str.substring(0, index)
      }
    }
  }

  implicit class AddApplyToImmutableMap(map: HashMap[String, Int]) {
    def apply(key: AbstractResource): Int = {
      map(key.getClass.extractClassSimpleName())
    }
  }

  implicit class ExtractResourceMap(str: String) {

    def extractResourceMap(): ResourceMap = {
      val resMap = ResourceMap()
      val Pattern = """([a-zA-Z]*)\s*\*\s*(\d*)""".r
      str.split(',').foreach(_ match {
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
      })
      resMap
    }
  }



}
