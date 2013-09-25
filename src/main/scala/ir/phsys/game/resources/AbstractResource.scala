package ir.phsys.game.resources

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/6/13
 *         Time: 6:54 PM
 */
abstract class AbstractResource {
    def getAmount: Int = 1

    def *(value: Int): AbstractResource


    //    val amount: Int
    //def this() = this(1)
}

