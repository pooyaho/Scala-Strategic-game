package ir

import org.fusesource.scalate.test.TemplateTestSupport
import org.fusesource.scalate.TemplateEngine
import java.io.File

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 9/4/13
 *         Time: 12:25 PM
 */
class TestMustache extends TemplateTestSupport {

  test("Test template generator") {
    def rootDir = new File(baseDir, "src/main/resources/")
    debug("Using rootDir: %s", rootDir)
    val engine = new TemplateEngine(Some(rootDir))
    //    engine.layoutStrategy = new DefaultLayoutStrategy(engine, "mylayout.mustache")
    val a = engine.layout("message.ssp",Map("n"->12))
    println(a)
  }
}