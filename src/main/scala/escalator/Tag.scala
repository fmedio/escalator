package escalator

import java.io.Writer
import org.apache.commons.lang.StringEscapeUtils


class Tag(val name: String, seq: List[Any]) extends Writeable {
  def this(name: String, children: Any*) = this(name, children.toList)

  private val attributes: MultiMap[String, String] = new MultiMap[String, String]()
  private var contents = new ContentFactory().makeContents(seq)


  def attr(kvps: (String, String)*): this.type = {
    kvps.foreach(kvp => attributes += kvp)
    this
  }

  def id(name: String): this.type = {
    attributes += ("id" -> name)
    this
  }

  def css(name: String): this.type = {
    attributes += ("class" -> name)
    this
  }

  def add(children: Any*): this.type = {
    contents ++= new ContentFactory().makeContents(children)
    this
  }

  def render(writer: Writer) {
    writer.write('<')
    writer.write(name)

    attributes
      .iterator
      .map(kvp => " " + kvp._1 + "=\"" + StringEscapeUtils.escapeXml(kvp._2) + "\"")
      .foreach(s => writer.write(s))

    if (contents.size == 0) {
      writer.write(" />")
    } else {
      writer.write('>')
      contents.foreach(c => c.render(writer))
      writer.write("</")
      writer.write(name)
      writer.write(">")
    }
  }
}

