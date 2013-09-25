package ir.phsys.game.resources

import org.scalatest.FunSuite

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 9/21/13
 *         Time: 4:46 PM
 */
class ResourceMapTest extends FunSuite {


  test("Test Resource map implicitly conversion") {
    var a: Any = new ResourceMap()
    a = "food*10,wood*100"

    println(a)
  }

  test("Test Decomposition of a type") {

  }
}
