package escalator


class Reifier[T](m: scala.reflect.Manifest[T]) {
  def make(bag: MultiMap[String, String]): T = {
    val klass: Class[_] = m.erasure

    // Try and parse a primitive
    if (bag.size != 0) {
      val value = bag(bag.keys.head)
      val parsed = parse(value, klass)
      if (parsed.isDefined) return parsed.get.asInstanceOf[T]
    }

    parseRecursive(klass, bag).asInstanceOf[T]
  }


  private def parse(s: String, to: Class[_]): Option[Any] = {
    if (to == classOf[String]) return Some(s)
    if (to == classOf[Int]) return Some(Integer.parseInt(s))
    if (to == classOf[Long]) return Some(java.lang.Long.parseLong(s))
    if (to == classOf[Float]) return Some(java.lang.Float.parseFloat(s))
    if (to == classOf[Long]) return Some(java.lang.Double.parseDouble(s))
    None
  }

  def parseRecursive(klass: Class[_], bag: MultiMap[String, String]): Any = {
    val instance = klass.newInstance()

    klass.getDeclaredFields
      .map(f => (f.getName, bag.get(f.getName)))
      .map(elt => {
      val methodName = elt._1 + "_$eq";
      val method = klass.getMethods.filter(m => methodName.equals(m.getName)).head
      val argType: Class[_] = method.getParameterTypes()(0)

      val value: Any = foo(argType, elt._2.getOrElse(""), bag)

      try {
        method.invoke(instance, value.asInstanceOf[java.lang.Object])
      } catch {
        case e: Throwable => throw new MalformedQueryException
      }
    })

    instance
  }

  def foo(argType: Class[_], s: String, bag: MultiMap[String, String]): Any = {
    if (argType == classOf[String]) return s
    if (argType == classOf[Int]) return java.lang.Integer.parseInt(s)
    if (argType == classOf[Long]) return java.lang.Long.parseLong(s)
    if (argType == classOf[Float]) return java.lang.Float.parseFloat(s)
    if (argType == classOf[Long]) return java.lang.Double.parseDouble(s)
    parseRecursive(argType, bag)
  }
}
