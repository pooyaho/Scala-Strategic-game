package ir.phsys.game.resources

import org.scalatest.FunSuite
import ir.phsys.game.exception.ResourceNotEnoughException

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/13/13
 *         Time: 9:00 AM
 */

class ResourcesContainer$Test extends FunSuite {
    val resource = ResourceMap(
        Wood() * 1000,
        Food() * 1000,
        Metal() * 1000,
        Coin() * 1000,
        Knowledge() * 1000,
        Oil() * 1000)
    val req = ResourceMap(Wood() * 10, Metal() * 100, Food() * 25)

    test("Test + operator") {
        assert(resource(Wood()) == 1000)
        assert(req(Food()) == 25)

        val result = resource - req

        assert(result(Wood()) == 990)
        assert(result(Metal()) == 900)
        assert(result(Food()) == 975)

        assert(result(Food()) == 975)
        assert(result(Knowledge()) == 1000)

        result -= req

        assert(result(Wood()) == 980)
        assert(result(Metal()) == 800)
        assert(result(Food()) == 950)

//        try {
//            result -= resource
//        }catch {
//            case x:ResourceNotEnoughException=>{
//                println("It is correct")
//            }
//
//        }
    }
}