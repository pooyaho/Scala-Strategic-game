package ir.phsys.game.exception

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/3/13
 *         Time: 4:47 PM
 */
class ResourceNotFoundException(message: String) extends Exception(message) {
    def this() = this(null)
}
