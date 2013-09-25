package ir.phsys.game.map

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/3/13
 *         Time: 9:04 PM
 */
abstract class TerrainBlockType(value: Int) {
    def getValue = value
}

object TerrainBlockType {

    val values = MOUNTAIN :: SEA :: TIMBER :: PLAIN :: METAL :: Nil

    case object MOUNTAIN extends TerrainBlockType(1)

    case object SEA extends TerrainBlockType(2)

    case object TIMBER extends TerrainBlockType(3)

    case object PLAIN extends TerrainBlockType(4)

    case object METAL extends TerrainBlockType(5)

}