package ir.phsys.game.unit

import ir.phsys.game.resources.ResourceMap


/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/3/13
 *         Time: 6:52 PM
 */
abstract class Population(name: String) extends GameUnit(name) {

  val destroyDuration: Int = 0
  val creationCost: ResourceMap
  val creationDuration: Int

}




