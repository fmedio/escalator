package escalator

import java.io.{OutputStream, InputStream, FileInputStream, File}
import org.apache.commons.io.IOUtils
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

class HandleStaticFiles(val webDir: String,
                        val fourOhFour: () => Verb[Any],
                        val request: HttpServletRequest) extends Verb[Any] {


  def execute(arg: Any): Resource = {
    val resource: String = request.getRequestURI
    val mimeType: String = Extensions.mimeType(resource)
    val file: File = new File(webDir, resource)
    if (!file.exists) {
      return fourOhFour().execute(new Nil())
    }
    new StreamResource(mimeType, new FileInputStream(file))
  }
}

object Extensions {
  val mimeTypes: Map[String, String] = Map(
    ".gif" -> "image/gif",
    ".pdf" -> "application/pdf",
    ".doc" -> "application/vnd.ms-word",
    ".jpg" -> "image/jpg",
    ".jpeg" -> "image/jpeg",
    ".png" -> "image/png",
    ".ico" -> "image/bmp",
    ".css" -> "text/css",
    ".js" -> "text/javascript",
    ".txt" -> "text/plain",
    ".html" -> "text/html",
    ".gz" -> "application/gzip"
  )

  def getExtension(resource: String): String = {
    val offset: Int = resource.lastIndexOf('.')
    if (offset < 0) return ""
    resource.substring(offset).toLowerCase
  }

  def isStaticResource(path: String): Boolean = {
    mimeTypes.contains(getExtension(path))
  }

  def mimeType(path: String): String = {
    mimeTypes.getOrElse(getExtension(path), "application/octet-stream")
  }
}

class StreamResource(val contentType: String, stream: InputStream) extends Resource {
  def statusCode: Int = HttpServletResponse.SC_OK

  def render(outputStream: OutputStream) {
    IOUtils.copy(stream, outputStream)
  }

  def extraHeaders: Map[String, String] = {
    Map()
  }
}