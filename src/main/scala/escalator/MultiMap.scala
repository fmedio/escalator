package escalator

import collection.mutable.{HashMap, Map}
import collection.Iterator

class MultiMap[K, V](kvps: (K, V)*) extends Map[K, V] {
  private val bag: Map[K, List[V]] = new HashMap[K, List[V]]
  kvps.foreach(elt => this += (elt._1 -> elt._2))

  override def get(key: K): Option[V] = {
    getValues(key).headOption
  }

  def getValues(key: K): List[V] = {
    bag.getOrElse(key, List())
  }

  def -=(key: K): this.type = {
    throw new UnsupportedOperationException
  }

  def +=(kv: (K, V)): this.type = {
    bag += (kv._1 -> (kv._2 :: bag.getOrElse(kv._1, List())))
    this
  }

  def iterator: Iterator[(K, V)] = {
    bag.iterator.flatMap(elt => elt._2.map(v => (elt._1, v)))
  }
}
