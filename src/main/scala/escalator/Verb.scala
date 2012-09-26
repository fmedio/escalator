package escalator

import javax.servlet.http.Cookie

abstract class Verb[T](implicit m: scala.reflect.Manifest[T]) {
  val reifier = new Reifier[T](m)

  def execute(cookies: Array[Cookie], bag: MultiMap[String, String]): Resource = {
    try {
      execute(cookies, reifier.make(bag))
    } catch {
      case e: MalformedQueryException => new TextResource(text = "Oopsies the query was malformed")
      case e: Throwable => throw e
    }
  }

  def execute(cookies: Array[Cookie], t: T): Resource

  var log: Boolean = true
}
