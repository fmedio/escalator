package escalator

import escalator.Tag
import java.io.StringWriter
import java.util.ArrayList
import collection.JavaConversions
import org.scalatest.FunSuite
import org.scalatest.FunSuite.test
import org.scalatest.FunSuite.test
import org.scalatest.FunSuite.test
import org.scalatest.FunSuite.test
import org.scalatest.FunSuite.test
import org.scalatest.FunSuite.test

class TagTest extends FunSuite {
  test("Attributes") {
    val writer: StringWriter = new StringWriter()
    new Tag("foo", "<poop").attr("k" -> "v", "k1" -> "v1").render(writer)
    assert("<foo k1=\"v1\" k=\"v\">&lt;poop</foo>" === writer.toString)
  }

  test("NoAttributes") {
    val writer: StringWriter = new StringWriter()
    new Tag("foo", "<poop").render(writer)
    assert("<foo>&lt;poop</foo>" === writer.toString)
  }

  test("AttributesChildren") {
    val writer: StringWriter = new StringWriter()
    new Tag("a").attr("href" -> "foo").add("bar").render(writer)
    assert("<a href=\"foo\">bar</a>" === writer.toString)
  }

  test("AttributesNoChildren") {
    val writer: StringWriter = new StringWriter()
    new Tag("foo").attr("k" -> "v").render(writer)
    assert("<foo k=\"v\" />" === writer.toString)
  }

  test("NoAttributesNoChildren") {
    val writer: StringWriter = new StringWriter()
    new Tag("foo").render(writer)
    assert("<foo />" === writer.toString)
  }

  test("Children from java collection") {
    val writer: StringWriter = new StringWriter()
    val list = new ArrayList[Any]()
    list.add(new Tag("bar"))
    list.add("hello")
    var foo = new Tag("foo").add(JavaConversions.asScalaBuffer(list))
    foo.render(writer)
    assert("<foo><bar />hello</foo>" === writer.toString)
  }
}
