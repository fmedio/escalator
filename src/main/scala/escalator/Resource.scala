package escalator

import java.io.OutputStream

trait Resource {
  def contentType: String

  def statusCode: Int

  def render(outputStream: OutputStream)

  def extraHeaders: Map[String, String]
}
