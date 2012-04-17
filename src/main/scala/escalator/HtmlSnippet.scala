package escalator

import java.io.{PrintWriter, OutputStream}
import javax.servlet.http.HttpServletResponse

class HtmlSnippet extends Resource {
  def contentType: String = "text/html;charset=UTF-8"

  def statusCode: Int = HttpServletResponse.SC_OK

  private var _contents: Iterable[Any] = List()

  def contents(children: Any*) {
    _contents = children
  }

  def render(outputStream: OutputStream) {
    val writer: PrintWriter = new PrintWriter(outputStream)
    new ContentFactory().makeContents(_contents).foreach(elt => elt.render(writer))
    writer.flush()
  }

  def extraHeaders: Map[String, String] = Map()
}
