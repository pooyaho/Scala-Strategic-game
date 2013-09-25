package ir.phsys.game.utils

/**
 * @author : Pooya husseini
 *         Email : info@pooya-hfp.ir
 *         Date: 8/4/13
 *         Time: 7:30 PM
 */
import scala.language.experimental.macros


object MacroUtils {

    import scala.reflect.macros.Context

    def values = macro MacroUtils.values_impl

    def values_impl(c: Context) = {
        import c.universe._

        val objs = c.enclosingClass.collect {
            case ModuleDef(mods, name, _) if mods hasFlag Flag.CASE => Ident(name)
        }

        c.Expr[List[Any]](
            Apply(Select(reify(List).tree, newTermName("apply")), objs.toList)
        )

    }
}