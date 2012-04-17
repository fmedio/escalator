package escalator

import java.io.Writer
import org.apache.commons.lang.StringEscapeUtils

class Text(val s: String) extends Writeable {
  def render(writer: Writer) {
    writer.write(StringEscapeUtils.escapeXml(s))
  }
}
