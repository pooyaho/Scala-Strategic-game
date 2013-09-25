package ir.phsys.game.map

import org.scalatest.FunSuite
import ir.phsys.game.exception.{BlockAlreadyExistsException, InvalidLocationException}
import grizzled.slf4j.Logger
import ir.phsys.executor.AsyncExecutor
import ir.phsys.game.resources.ResourcesContainer
import ir.phsys.game.unit.UnitStatus
import ir.phsys.game.building.AbstractBlock


/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/18/13
 *         Time: 10:46 AM
 */
class MapTest extends FunSuite {
  val log = Logger[this.type]
  val map = new Map(10, 10)

  test("Test dimensions") {
    assert(map.getColumns == 10)
    assert(map.getRows == 10)
  }

  test("Test location") {
    intercept[InvalidLocationException] {
      map(-1, -1)
    }
    intercept[InvalidLocationException] {
      map(10, 10)
    }

  }

  test("Creating and destroying buildings") {
    val unknown_block = map.AVAILABLE_BLOCKS(0)
    val unknown_block2 = map.AVAILABLE_BLOCKS(1)
    map(0, 0).createBuilding(unknown_block)
    val building: AbstractBlock = map(0, 0).getBuilding
    //        assert(building.isInstanceOf[CityTown])
    while (building.getStatus != UnitStatus.READY) {}
    while (!ResourcesContainer.isResourceEnough(unknown_block2.creationCost)) {}

    intercept[BlockAlreadyExistsException] {
      map(0, 0).createBuilding(unknown_block2)
    }

    map(0, 1).createBuilding(unknown_block2)
    while (building.getStatus != UnitStatus.READY) {}
    while (true) {}
    map(0, 0).updateBuilding()

    while (building.getStatus != UnitStatus.READY) {}
    val b = map(0, 0).getBuilding

    assert(b.getCurrentLevel == b.getUpgradeList.head)
    //
    map(0, 0).updateBuilding()
    while (building.getStatus != UnitStatus.READY) {}
    val c = map(0, 0).getBuilding

    assert(c.getCurrentLevel == c.getUpgradeList.tail.head)
    //
    map(0, 0).updateBuilding()
    while (building.getStatus != UnitStatus.READY) {}
    //
    map(0, 0).updateBuilding()
    while (building.getStatus != UnitStatus.READY) {}
    //
    map(0, 0).destroyBuilding()
    while (map(0, 0).getBuilding.getStatus != UnitStatus.DESTROYED) {}

    assert(map(0, 0).getBuilding.getStatus == UnitStatus.DESTROYED)
    log.info("Everything is Ok!")
    //        val delBuilding: Block = map(0, 0).getBuilding
    //        assert(delBuilding.isInDestroyState )
  }

  test("scala object initiation") {
    object A {
      print("in A")

      def a = 10
    }
    A
    println(A.a)
  }

  test("Test generated map") {
    map.generateRandomMap()

    println(map)
    val ANSI_RESET = "\u001B[0m"
    println(ANSI_RESET)
    println(Console.BLUE + " Hello!")
    println(27.toChar + "[2J")
  }

  test("Test async method") {
    def showHello() = println("Goodbye world!")
    AsyncExecutor.async {
      println("A")
    }

  }
}