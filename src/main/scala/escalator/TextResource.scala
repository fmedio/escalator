package escalator

import java.io.OutputStream

class TextResource(val contentType: String = "text/plain",
                   val statusCode: Int = 200,
                   val extraHeaders: Map[String, String] = Map(),
                   val text: String) extends Resource {
  def render(outputStream: OutputStream) {
    outputStream.write(text.getBytes)
  }
}
