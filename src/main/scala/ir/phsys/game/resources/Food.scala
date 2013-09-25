package ir.phsys.game.resources

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/11/13
 *         Time: 2:47 PM
 */
object Food {
    def apply() = new Food
}

class Food private() extends AbstractResource {
    def *(value: Int) = {
        new Food {
            override def getAmount = value
        }
    }
}

