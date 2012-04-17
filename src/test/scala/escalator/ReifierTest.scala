package escalator

import org.scalatest.FunSuite

class Foo {
  var id: Int = 0
  var name: String = ""
}

class Bar {
  var foo: Foo = new Foo()
  var panda: String = ""
}

class ReifierTest extends FunSuite {
  test("Make") {
    val bag: MultiMap[String, String] = new MultiMap[String, String]("id" -> "42", "name" -> "Panda")
    val m: Manifest[Foo] = manifest[Foo]
    val foo = new Reifier[Foo](m).make(bag)
    assert(42 === foo.id)
    assert("Panda" === foo.name)
  }

  test("Make base type") {
    val bag: MultiMap[String, String] = new MultiMap[String, String]("id" -> "42")
    val m: Manifest[Long] = manifest[Long]
    val foo = new Reifier[Long](m).make(bag)
    assert(42l === foo)
  }

  test("Recursive") {
    val bag = new MultiMap[String, String]("id" -> "42", "name" -> "Victor", "panda" -> "Happy")
    val m: Manifest[Bar] = manifest[Bar]
    val bar = new Reifier[Bar](m).make(bag)
    assert(42l === bar.foo.id)
    assert("Victor" === bar.foo.name)
    assert("Happy" === bar.panda)
  }
}
