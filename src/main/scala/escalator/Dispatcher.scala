package escalator

import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse, HttpServlet}
import org.eclipse.jetty.io.EofException
import java.util.zip.GZIPOutputStream
import java.io.BufferedOutputStream

class Dispatcher(val webDir: String,
                 val fourOhFour: () => Verb[Any],
                 val ohNoes: Throwable => Verb[Any],
                 val verbs: Map[String, () => Verb[_ <: Any]]) extends HttpServlet {

  override def service(req: HttpServletRequest, resp: HttpServletResponse) {
    val verb: Verb[_ <: Any] = findVerb(req)
    val factory: ParameterFactory = new ParameterFactory()
    val parameters: MultiMap[String, String] = factory.getParameters(req)
    val before = System.currentTimeMillis()
    val cookies: Array[Cookie] = req.getCookies()
    val resource: Resource = {
      try {
        verb.execute(cookies, parameters)
      } catch {
        case t: Throwable => ohNoes(t).execute(cookies, new Nil())
      }
    }
    val after = System.currentTimeMillis() - before
    if (verb.log) {
      Log.debug(this, "Served " + verb.getClass.getName + " in " + after  + "ms")
    }

    try {
      resp.setStatus(resource.statusCode)
      resp.setContentType(resource.contentType)
      resp.setHeader("Content-Encoding", "gzip")
      resource.extraHeaders.foreach({case (k, v) => resp.setHeader(k, v)})
      val stream = new GZIPOutputStream(new BufferedOutputStream(resp.getOutputStream), 1024 * 1024)
      resource.render(stream)
      stream.close()
    } catch {
      case t: EofException => {
        return
      }
      case t: Throwable => {
        Log.info(this, "Error while rendering", t)
      }
    }
  }


  private def findVerb(request: HttpServletRequest): Verb[_ <: Any] = {
    val name: String = request.getRequestURI
      .replaceAll("^" + request.getServletPath + "/", "")
    //.replaceAll("^/", "")

    if (Extensions.isStaticResource(name)) return new HandleStaticFiles(webDir, fourOhFour, request)
    verbs.getOrElse(name, fourOhFour)()
  }
}
