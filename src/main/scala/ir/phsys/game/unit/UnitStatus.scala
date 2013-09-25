package ir.phsys.game.unit

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/28/13
 *         Time: 3:26 PM
 */
abstract class UnitStatus {}

object UnitStatus {

    case object READY extends UnitStatus

    case object REPAIRING extends UnitStatus

    case object DAMAGED extends UnitStatus

    case object CONSTRUCTION extends UnitStatus

    case object UPGRADING extends UnitStatus

    case object DESTROYING extends UnitStatus

    case object DESTROYED extends UnitStatus

    case object NEED_TO_CREATE extends UnitStatus

    case object PREPARED_TO_DESTROY extends UnitStatus


}
