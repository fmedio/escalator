package escalator

import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.server.{Connector, Server}
import org.eclipse.jetty.servlet.{ServletHolder, ServletContextHandler}

class WebApp(val webDir: String,
             val httpPort: Int,
             forOhFour: () => Verb[Any],
             error: Throwable => Verb[Any],
             routes: Map[String, () => Verb[_ <: Any]]) {
  def start() {
    val server: Server = new Server
    val connector: Connector = new SelectChannelConnector
    connector.setPort(httpPort)
    connector.setHost("0.0.0.0")
    server.setConnectors(Array[Connector](connector))
    val contextHandler: ServletContextHandler = new ServletContextHandler
    contextHandler.setContextPath("/")
    server.setHandler(contextHandler)

    val dispatcher = new Dispatcher(webDir, forOhFour, error, routes)
    contextHandler.addServlet(new ServletHolder(dispatcher), "/")
    server.setHandler(contextHandler)
    server.start
    Log.info(this, "WebApp started on port " + httpPort)
    server.join
  }
}




