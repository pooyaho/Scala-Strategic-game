package ir.phsys.game.resources

import org.scalatest.FunSuite
import ir.phsys.game.utils.ImplicitClasses
import ImplicitClasses._

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/13/13
 *         Time: 2:42 PM
 */
class ImplicitClassesTest extends FunSuite {

    test("Test class simple name extractor") {
        val e=new ResourceMap().getClass.extractClassSimpleName()
        assert(e == "ir.phsys.game.resources.ResourceMap")
    }

}
