package escalator

import java.io.OutputStream
import javax.servlet.http.Cookie

class HttpRedirect(private val to: String, private val cookieJar: Array[Cookie] = Array[Cookie]()) extends Resource {
  def contentType = "text/plain"

  def statusCode = 302

  def render(outputStream: OutputStream) { }

  def extraHeaders = Map("Location" -> to)

  override def cookies = cookieJar
}
