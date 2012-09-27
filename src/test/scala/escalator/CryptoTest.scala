package escalator

import org.scalatest.FunSuite
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64
import javax.crypto.BadPaddingException

class CryptoTest extends FunSuite {
  val keyBytes = Array.range(0, 16).map(i => i.toByte)
  var keyspec = new SecretKeySpec(keyBytes, "AES")
  val plainText: String = "foo poop hello"
  val crypto: Crypto = new Crypto(keyspec)

  test("Encrypt") {
    var result = crypto.encrypt(plainText)

    assert(plainText != result)
    System.out.println(result)

    var decrypted = crypto.decrypt(result)
    assert(plainText ===  decrypted)
  }

  test("Decrypt Garbage") {
    var garbage = Base64.encodeBase64URLSafeString("0123456789012345".getBytes)
    intercept[BadPaddingException] {
      crypto.decrypt(garbage)
    }
  }
}
