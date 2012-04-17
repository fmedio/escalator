package escalator

import javax.servlet.ServletOutputStream
import javax.servlet.http.{HttpServletRequest, HttpServletResponse, HttpServlet}

class Dispatcher(val webDir: String,
                 val fourOhFour: () => Verb[Any],
                 val ohNoes: Throwable => Verb[Any],
                 val verbs: Map[String, () => Verb[_ <: Any]]) extends HttpServlet {


  override def service(req: HttpServletRequest, resp: HttpServletResponse) {
    val verb: Verb[_ <: Any] = findVerb(req)
    val factory: ParameterFactory = new ParameterFactory()
    val parameters: MultiMap[String, String] = factory.getParameters(req)
    val resource: Resource = {
      try {
        verb.f(parameters)
      } catch {
        case t: Throwable => ohNoes(t).execute(new Nil())
      }
    }

    try {
      resp.setStatus(resource.statusCode)
      resp.setContentType(resource.contentType)
      val stream: ServletOutputStream = resp.getOutputStream
      resource.render(stream)
      stream.close()
    } catch {
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
