package ir.phsys.game.utils

import org.scalatest.FunSuite
import ir.phsys.game.utils.ImplicitClasses._
import ir.phsys.game.resources.{Food, ResourceMap, Wood}

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/19/13
 *         Time: 5:25 PM
 */
class ImplicitClasses$Test extends FunSuite {
    test("Test extract resources") {
        assert("wood*100,food*10".extractResourceMap() == ResourceMap(Wood() * 100, Food() * 10))
    }
}
