package escalator

import java.io.Writer

trait Writable {
  def render(writer: Writer)
}
