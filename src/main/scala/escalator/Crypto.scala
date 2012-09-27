package escalator

import javax.crypto.{SecretKey, Cipher}
import org.apache.commons.codec.binary.Base64

class Crypto(private val spec: SecretKey) {
  def decrypt(cipherText: String): String = {
    val cipher = Cipher.getInstance("AES")
    var cipherBytes = Base64.decodeBase64(cipherText)
    cipher.init(Cipher.DECRYPT_MODE, spec)
    var decryptedBytes = cipher.doFinal(cipherBytes)
    new String(decryptedBytes)
  }

  def encrypt(plainText: String): String = {
    val cipher = Cipher.getInstance("AES")
    cipher.init(Cipher.ENCRYPT_MODE, spec)
    var encrypted = cipher.doFinal(plainText.getBytes())
    Base64.encodeBase64URLSafeString(encrypted)
  }


}
