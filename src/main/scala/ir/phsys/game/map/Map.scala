package ir.phsys.game.map

import ir.phsys.game.exception.{BlockAlreadyExistsException, EmptyLocationException, InvalidLocationException}
import scala.util.Random
import ir.phsys.game.utils.MapUtils
import grizzled.slf4j.Logger
import ir.phsys.game.unit.UnitStatus
import ir.phsys.game.resources.ResourcesContainer
import ir.phsys.executor.AsyncExecutor._
import ir.phsys.game.building.AbstractBlock

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir

 *         Date: 8/3/13
 *         Time: 3:33 PM
 */
class Map(rows: Int, columns: Int) {

  $init$()

  private val logger = Logger[this.type]

  private lazy val terrainMap = Array.ofDim[TerrainBlockType](rows, columns)
  private lazy val revealedMap = Array.ofDim[Boolean](rows, columns)
  private lazy val blockStructureMap = Array.ofDim[AbstractBlock](terrainMap.length, terrainMap(0).length)

  private var i: Int = -1
  private var j: Int = -1

  lazy val AVAILABLE_BLOCKS = AbstractBlock.BLOCKS
  //  val AVAILABLE_BLOCKS = AbstractBlock.BLOCKS


  def generateRandomMap() = {
    val terrainTypes = TerrainBlockType.values
    terrainMap.foreach(_.transform(_ => {
      val r = Random.nextInt(terrainTypes.length)
      terrainTypes(r)
    }))
    //        (0 until width).foreach(i => {
    //            (0 until height).foreach(j => {
    //                val r = Random.nextInt(terrainTypes.length)
    //                terrainMap(i)(j) = terrainTypes(r)
    //            })
    //        })
    generateReveledMap()
  }


  def $init$() = {
    asyncLoop(1000) {
      blockStructureMap.foreach(p => {
        p.filter(_ != null).foreach(item => {
          logger.info(item)
          ResourcesContainer += item.producingValue
        })

      })
    }
  }

  private def generateReveledMap(): Unit = {
    (0 until rows).foreach(i => {
      (0 until columns).foreach(j => {
        if (terrainMap(i)(j).isInstanceOf[TerrainBlockType.PLAIN.type]) {
          MapUtils
            .bulkChangeValueOfArray(revealedMap, true, MapUtils.findNearbyBlocks(i, j, rows, columns))
          return
        }
      })
    })
  }


  def getRows = rows

  def getColumns = columns

  def apply(i: Int, j: Int): Map = {
    if (i >= rows || j >= columns || i < 0 || j < 0) {
      throw new InvalidLocationException
    } else {
      //            new MapLocation(i, j, terrainMap, revealedMap)
      this.i = i
      this.j = j
    }
    this
  }

  def createBuilding(index: Int): Unit = {
    //  def createBuilding(block: BlockTypes) = {
    //    block match {
    //      case BlockTypes.CITY_TOWN => createBuilding(CityTown())
    //      case BlockTypes.DOCK => createBuilding(Dock())
    //      case BlockTypes.LIBRARY => createBuilding(Library())
    //      case BlockTypes.MARKET => createBuilding(Market())
    //      case BlockTypes.MILITARY => createBuilding(Military())
    //
    //      case BlockTypes.LumberMill=> createBuilding(LumberMill())
    //    }
    createBuilding(AbstractBlock.BLOCKS(index))
  }

  def createBuilding(block: AbstractBlock): Unit = {
    synchronized {
      if (blockStructureMap(i)(j) == null || blockStructureMap(i)(j).getStatus == UnitStatus.DESTROYED) {
        blockStructureMap(i)(j) = block.clone().asInstanceOf[AbstractBlock]
        block.create(showProgress)
      } else {
        throw new BlockAlreadyExistsException
      }
    }
  }

  def getBuilding = {
    blockStructureMap(i)(j)
  }


  /**
   * My policy is not destroying anything, just set its status to destroyed and then you can create in it;s place
   */
  def destroyBuilding() = {
    if (blockStructureMap(i)(j) == null) {
      throw new EmptyLocationException
    } else {
      blockStructureMap(i)(j).prepareToDestroy()
      blockStructureMap(i)(j).destroy(showProgress)
    }
  }

  def revealMap(): Unit = {
    val blocks: Set[(Int, Int)] = findNearbyBlocks()
    MapUtils.bulkChangeValueOfArray(revealedMap, true, blocks)
  }

  private def findNearbyBlocks(): Set[(Int, Int)] = {
    MapUtils.findNearbyBlocks(i, j, terrainMap.length, terrainMap(0).length)
  }

  def updateBuilding() = {

    blockStructureMap(i)(j).upgrade(showProgress)
  }

  override def toString: String = {
    val str = new StringBuffer()

    (0 until terrainMap.length).foreach(i => {
      (0 until terrainMap(0).length).foreach(j => {
        str.append("%-30s".format(s"[${terrainMap(i)(j)},[${blockStructureMap(i)(j)}]]"))
      })
    })

    str.toString
  }

  //    class BuildBlock(block: Block) extends Actor {
  //        def act() {
  //            loop {
  //                react {
  //                    case (i: Int, j: Int) => {
  //                        if (blockStructureMap(i)(j) == null) {
  //                            blockStructureMap(i)(j) = block
  //                            block.create(showProgress)
  //                        } else {
  //                            throw new BlockAlreadyExistsException
  //                        }
  //                        return
  //                    }
  //                }
  //            }
  //        }
  //    }

  def showProgress(percent: Int) = {
    logger.info(percent)
  }
}