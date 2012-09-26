package escalator

import org.scalatest.FunSuite
import org.joda.time.DateTime

class WebDateFormatterTest extends FunSuite {
  test("Format") {
    var result = new WebDateFormatter().format(new DateTime(2005, 9, 20, 2, 12))
    assert("Tue, 20 Sep 2005 02:12:00 -07:00" === result)
  }
}
