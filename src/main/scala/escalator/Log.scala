package escalator

import org.apache.log4j.Logger

object Log {

  def debug(source: Object, message: String) {
    val logger: Logger = Logger.getLogger(source.getClass.getName)
    logger.debug(message)
  }

  def error(source: Object, message: String, throwable: Throwable) {
    val logger: Logger = Logger.getLogger(source.getClass.getName)
    logger.error(message, throwable)
  }

  def info(source: Object, message: String, throwable: Throwable) {
    val logger: Logger = Logger.getLogger(source.getClass.getName)
    logger.info(message, throwable)
  }

  def info(source: Object, message: String) {
    val logger: Logger = Logger.getLogger(source.getClass.getName)
    logger.info(message)
  }
}
