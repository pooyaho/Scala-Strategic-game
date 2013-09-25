package ir.phsys.game.technology

import ir.phsys.game.resources.ResourceMap

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/3/13
 *         Time: 8:47 PM
 */
abstract class UpgradeLevel(name: String) {

  def getName = name

  val duration: Int

  val cost: ResourceMap
}
