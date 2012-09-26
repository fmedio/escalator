package escalator

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class WebDateFormatter {
  def format(time: DateTime): String = {
    var format = "EE, dd MMM yyyy HH:mm:ss ZZ"
    var pattern = DateTimeFormat.forPattern(format)
    pattern.print(time)
  }

}
