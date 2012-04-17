package escalator

import java.io.Writer

trait Writeable {
  def render(writer: Writer)
}
