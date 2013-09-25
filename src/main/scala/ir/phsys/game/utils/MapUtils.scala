package ir.phsys.game.utils

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/27/13
 *         Time: 9:17 PM
 */
object MapUtils {

    def findNearbyBlocks(i: Int, j: Int, rows: Int, columns: Int): Set[(Int, Int)] = {
        val aboveBlocks = Set((i - 1, j - 1), (i - 1, j), (i - 1, j + 1))
        val belowBlocks = Set((i + 1, j - 1), (i + 1, j), (i + 1, j + 1))
        val leftBlocks = Set((i - 1, j - 1), (i, j - 1), (i + 1, j - 1))
        val rightBlocks = Set((i + 1, j + 1), (i, j + 1), (i - 1, j + 1))

        val result = aboveBlocks ++ belowBlocks ++ leftBlocks ++ rightBlocks ++ Nil

        result.filterNot(p => p._1 < 0 || p._1 >= rows || p._2 < 0 || p._2 >= columns)
    }

    def bulkChangeValueOfArray[B](array: Array[Array[B]], value: B, locations: Set[(Int, Int)]) = {
        locations.foreach(x => {
            array(x._1)(x._2) = value
        })
    }
}