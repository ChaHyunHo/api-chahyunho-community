package org.duckdns.apichahyunhocommunity.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "jasypt.encryptor.password=REMOVED_SECRET"
})
public class JasyptEncryptorTest {

  @Test
  void testEncryptionAndDecryption() {
    StringEncryptor encryptor = createEncryptor();

    String plainText = "REDACTED";
    String encrypted = encryptor.encrypt(plainText);
    String decrypted = encryptor.decrypt(encrypted);

    System.out.println("🔒 Encrypted: " + encrypted);
    System.out.println("🔓 Decrypted: " + decrypted);

    assert plainText.equals(decrypted);
  }

  private StringEncryptor createEncryptor() {
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(
        "REMOVED_SECRET");
    config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
    config.setKeyObtentionIterations("1000");
    config.setPoolSize("1");
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName(RandomSaltGenerator.class.getName());
    config.setIvGeneratorClassName(RandomIvGenerator.class.getName());
    config.setStringOutputType("base64");

    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    encryptor.setConfig(config);
    return encryptor;
  }
}
