package ir.phsys.dsl

import scala.util.parsing.combinator.JavaTokenParsers
import ir.phsys.game.building.Block
import java.io.FileReader

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 9/9/13
 *         Time: 5:14 PM
 */
object GameDefinitionDslRules extends JavaTokenParsers {

  var blocksList = List[Block]()

  def main(args: Array[String]) {
    println(parseAll(Rules.root, new FileReader
                                     ("/home/pooya/projects/Strategic-game-core/src/main/resources/gamedef.def")))
    println(blocksList(0).getProperties)
  }

  object Rules {
    def root = rep(block) ^^ {
      case blocks => {
        blocks.foreach {

          case blockName ~ definition => {
            val myBlock = new Block {
              setProperty(("name", blockName.asInstanceOf[String]))
            }
            definition match {
              case (creation ~ destruction ~ upgrades ~ unit) => {

                creation.foreach {
                  case x => myBlock.setProperty(convertProperty(x), "creation")
                  //                  setProperty(_, myBlock)
                  //                  case duration ~ cost => {
                  //
                  //                    myBlock.setCreationDuration(duration.asInstanceOf[String])
                  //                    myBlock.setCreationCost(cost2String(cost))
                  //                  }
                }
                destruction.foreach {
                  case x => myBlock.setProperty(convertProperty(x), "destruction")
                  //                  setProperty(_, myBlock)
                  //                  case d => myBlock.setDestructionDuration(d)
                }
                upgrades match {
                  case x => x.foreach {
                    case y => /*setProperty(y, myBlock)*/
                    //                      case name ~ (duration ~ cost) => {
                    //                        val upgrade = new Upgrade
                    //                        upgrade.setCost(cost2String(cost))
                    //                        upgrade.setDuration(duration.asInstanceOf[String])
                    //                        upgrade.setName(name.asInstanceOf[String])
                    //
                    //                        myBlock.addUpgrade(upgrade)
                    //                      }
                  }
                }
                unit.foreach {
                  case x => //setProperty(x, myBlock)
                  //                      case x => println(x)
                }

              }
            }
            blocksList = blocksList :+ myBlock
          }
        }
      }
    }

    def convertProperty(parser: Any): (String, String) = parser match {
      case k ~ "=" ~ v => {
        v match {
          case cost: List[_] => (k.asInstanceOf[String], cost2String(cost))
          case x => (k.asInstanceOf[String], x.asInstanceOf[String])
        }
      }
    }


    def block = "block" ~> ident ~ ("{" ~> definition <~ "}")

    def definition = creation ~ destruction ~ upgrades ~ allUnitsDef

    def allUnitsDef = "units" ~ "{" ~> rep(allUnitsDefBody) <~ "}"

    def allUnitsDefBody = ident ~ "{" ~> rep(unitDef) <~ "}"

    def unitDef = ("" ~> ident) ~ "{" ~> unitDefBody <~ "}"

    def unitDefBody = creation ~ destruction

    //    def levelUnitDef = ident ~ "{" ~ rep(unitDef) ~ "}"
    def creation = "creation" ~ "{" ~> configProperties <~ "}"

    def configProperties = rep(ident ~ "=" ~ value)

    def cost2String(cost: List[_]): String = {
      var str = ""
      cost.foreach {
        case f ~ v => str += s"$f*$v,"
      }
      str
    }

    //    def units = "units" ~ "{" ~ "}"
    def destruction = "destruction" ~ "{" ~> configProperties <~ "}"

    def upgrades = "upgrades" ~ "{" ~> rep(ident ~ ("{" ~> configProperties <~ "}")) <~ "}"

    //    def duration = "duration" ~ "=" ~> wholeNumber
    //    def cost = "cost" ~ "=" ~> repsep(resource, ",")
    def resources = repsep(("wood" | "metal" | "food") ~ ("*" ~> wholeNumber), ",")

    def value = stringLiteral | wholeNumber | floatingPointNumber | resources

  }

}

