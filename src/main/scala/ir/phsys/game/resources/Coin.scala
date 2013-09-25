package ir.phsys.game.resources

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/11/13
 *         Time: 2:47 PM
 */
object Coin {
    def apply() = new Coin
}

class Coin private() extends AbstractResource {
    def *(value: Int) = {
        new Coin {
            override def getAmount = value
        }
    }
}