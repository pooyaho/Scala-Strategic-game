package ir.phsys.game.resources

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/11/13
 *         Time: 2:46 PM
 */
object Knowledge {
    def apply() = new Knowledge
}

class Knowledge private() extends AbstractResource {
    def *(value: Int) = {
        new Knowledge {
            override def getAmount = value
        }
    }
}

