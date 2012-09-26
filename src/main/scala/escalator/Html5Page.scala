package escalator

import java.io.{PrintWriter, OutputStream}
import java.util.ArrayList
import collection.JavaConversions
import javax.servlet.http.HttpServletResponse


trait Html5Page extends Resource {
  private var _title: String = ""
  private val _body = new ArrayList[Any]
  private val _head = new ArrayList[Any]
  private val _css = new ArrayList[String]
  private val _js = new ArrayList[String]

  def contentType = "text/html;charset=UTF-8"

  def statusCode = HttpServletResponse.SC_OK


  def css(s: String) {
    _css.add(s)
  }

  def js(s: String) {
    _js.add(s)
  }

  def title(s: String) {
    _title = s
  }

  def body(children: Any*) {
    children.foreach(c => {
      _body.add(c)
    })
  }

  def head(children: Any*) {
    children.foreach(c => {
      _head.add(c)
    })
  }

  def render(outputStream: OutputStream) {
    val writer = new PrintWriter(outputStream)
    writer.write("<!DOCTYPE html>\n")

    val headTag = new Tag("head",
      JavaConversions.asScalaBuffer(_css).map(s => new Tag("link").attr("rel" -> "stylesheet", "type" -> "text/css", "href" -> s)),
      JavaConversions.asScalaBuffer(_js).map(s => new Tag("script", "").attr("type" -> "text/javascript", "src" -> s)),
      new Tag("title", _title)
    )

    headTag.add(JavaConversions.asScalaBuffer(_head))

    new Tag("html",
      headTag,
      new Tag("body", JavaConversions.asScalaBuffer(_body))
    ).attr(("lang", "en")).render(writer)
    writer.flush()
  }

  def extraHeaders = Map()
}
