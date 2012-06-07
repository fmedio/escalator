package escalator

import java.io.Writer
import org.apache.commons.lang.StringEscapeUtils

class Text(val s: String) extends Writable {
  def render(writer: Writer) {
    writer.write(StringEscapeUtils.escapeXml(s))
  }
}


class UnsecureText(val s: String) extends Writable {
  def render(writer: Writer) {
    writer.write(s)
  }
}
