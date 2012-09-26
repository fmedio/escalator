package escalator

import java.io.OutputStream
import javax.servlet.http.Cookie

trait Resource {
  def contentType: String
  def statusCode: Int
  def render(outputStream: OutputStream)
  def extraHeaders: Map[String, String]
  def cookies: Array[Cookie] = Array[Cookie]()
}
