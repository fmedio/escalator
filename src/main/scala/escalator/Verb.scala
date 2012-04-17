package escalator

abstract class Verb[T](implicit m: scala.reflect.Manifest[T]) {
  val reifier = new Reifier[T](m)

  val f: (MultiMap[String, String] => Resource) = (bag) => {
    try {
      execute(reifier.make(bag))
    } catch {
      case e: MalformedQueryException => new TextResource(text = "Oopsies the query was malformed")
      case e: Throwable => throw e
    }
  }

  def execute(arg: T): Resource
}
