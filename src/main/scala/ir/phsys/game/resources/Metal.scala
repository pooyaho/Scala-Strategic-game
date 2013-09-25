package ir.phsys.game.resources

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/11/13
 *         Time: 2:46 PM
 */
object Metal {
    def apply() = new Metal
}

class Metal private() extends AbstractResource {
    def *(value: Int) = {
        new Metal {
            override def getAmount = value
        }
    }
}