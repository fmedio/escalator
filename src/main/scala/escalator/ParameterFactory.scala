package escalator

import java.util.Enumeration
import javax.servlet.http.HttpServletRequest


class ParameterFactory {
  def getParameters(servletRequest: HttpServletRequest): MultiMap[String, String] = {
    val parameters: MultiMap[String, String] = new MultiMap[String, String]
    val names: java.util.Enumeration[_] = servletRequest.getParameterNames
    while (names.hasMoreElements) {
      val name: String = names.nextElement.asInstanceOf[String]
      for (value <- servletRequest.getParameterValues(name)) {
        parameters.put(name, value)
      }
    }
    parameters
  }

  def getHeaders(servletRequest: HttpServletRequest): MultiMap[String, String] = {
    val result: MultiMap[String, String] = new MultiMap[String, String]
    val headers: Enumeration[_] = servletRequest.getHeaderNames
    while (headers.hasMoreElements) {
      val headerName: String = headers.nextElement.asInstanceOf[String]
      val headerValues: Enumeration[_] = servletRequest.getHeaders(headerName)
      while (headerValues.hasMoreElements) {
        val headerValue: String = headerValues.nextElement.asInstanceOf[String]
        result.put(headerName, headerValue)
      }
    }

    result
  }
}
