package escalator

class ContentFactory {
  def makeContents(list: Iterable[Any]): Iterable[Writeable] = {
    list.flatMap(elt => {
      elt match {
        case tag: Tag => Iterable(tag)
        case iterable: Iterable[Any] => makeContents(iterable)
        case iterator: Iterator[Any] => makeContents(iterator.toStream)
        case _ => Iterable(new Text(elt.toString))
      }
    })
  }
}
