package ir.phsys.game.utils

import java.io.IOException

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/18/13
 *         Time: 11:50 AM
 */
object ConfigurationUtil {
    //    private val pickJarBasedOn: Class[_]

    def extractProps(propFileName: String): java.util.Properties = {
        val props = new java.util.Properties
        val stream = this.getClass.getResourceAsStream(propFileName)
        if (stream ne null) {
            quietlyDispose(props load stream, stream close())
        }
        props
    }

    private def quietlyDispose(action: => Unit, disposal: => Unit) = {
        try {
            action
        }
        finally {
            try {
                disposal
            }
            catch {
                case _: IOException =>
            }
        }

    }
}