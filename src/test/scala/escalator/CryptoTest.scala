package escalator

import org.scalatest.FunSuite
import org.apache.commons.codec.binary.Base64
import javax.crypto.{KeyGenerator, BadPaddingException}

class CryptoTest extends FunSuite {
  val kgen: KeyGenerator = KeyGenerator.getInstance("AES")
  kgen.init(128)
  val keySpec = kgen.generateKey
  val plainText: String = "1"
  val crypto: Crypto = new Crypto(keySpec)

  test("Encrypt") {
    val result = crypto.encrypt(plainText)

    assert(plainText != result)
    System.out.println(result)

    val decrypted = crypto.decrypt(result)
    assert(plainText ===  decrypted)
  }

  test("Decrypt Garbage") {
    val garbage = Base64.encodeBase64URLSafeString("0123456789012345".getBytes)
    intercept[BadPaddingException] {
      crypto.decrypt(garbage)
    }
  }
}
