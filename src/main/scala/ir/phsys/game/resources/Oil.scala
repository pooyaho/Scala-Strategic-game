package ir.phsys.game.resources

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/11/13
 *         Time: 2:47 PM
 */
object Oil {
    def apply() = new Oil
}

class Oil private()extends AbstractResource {
    def *(value: Int) = {
        new Oil {
            override def getAmount = value
        }
    }

}
