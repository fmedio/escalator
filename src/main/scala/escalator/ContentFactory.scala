package escalator


class ContentFactory {
  def makeContents(list: Iterable[Any]): Iterable[Writable] = {
    list.flatMap(elt => {
      elt match {
        case writable: Writable => Iterable(writable)
        case iterable: Iterable[Any] => makeContents(iterable)
        case iterator: Iterator[Any] => makeContents(iterator.toStream)
        case _ => Iterable(new Text(elt.toString))
      }
    })
  }
}
