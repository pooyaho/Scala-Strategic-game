package ir.phsys.game.resources

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/11/13
 *         Time: 2:46 PM
 */
object Wood {
    def apply() = new Wood
}

class Wood private() extends AbstractResource {
    def *(value: Int) = {
        new Wood {
            override def getAmount = value
        }
    }
}