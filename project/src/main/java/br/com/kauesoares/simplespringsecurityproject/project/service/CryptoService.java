package br.com.kauesoares.simplespringsecurityproject.project.service;

import br.com.kauesoares.simplespringsecurityproject.project.config.properties.RSAProperties;
import br.com.kauesoares.simplespringsecurityproject.project.integration.s3.AWSCloudUtil;
import br.com.kauesoares.simplespringsecurityproject.project.messages.MessageFactory;
import br.com.kauesoares.simplespringsecurityproject.project.messages.Messages;
import br.com.kauesoares.simplespringsecurityproject.project.messages.exception.ServiceException;
import br.com.kauesoares.simplespringsecurityproject.project.messages.exception.UnprocessableEntityException;
import br.com.kauesoares.simplespringsecurityproject.project.util.CacheUtil;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Scope("singleton")
public class CryptoService {

    private final RSAProperties rsaProperties;
    private final AWSCloudUtil awsCloudUtil;

    private static PublicKey publicKey;
    private static PrivateKey privateKey;

    public PublicKey getPublicKey() {
        if (publicKey == null) {
            try {
                S3Object s3Object = this.awsCloudUtil.getFile(rsaProperties.getPublicKeyPath());

                BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
                String stringContent = reader.lines().collect(Collectors.joining());

                byte[] keyBytes = Base64.getDecoder().decode(stringContent);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                publicKey = keyFactory.generatePublic(keySpec);
            } catch (Exception e) {
                log.error(MessageFactory.getLogMessage(
                        Messages.ENCRYPT_ERROR,
                        "service.RSAEncryptionService.getPublicKey",
                        e.getMessage()
                ));
                throw new ServiceException(Messages.ENCRYPT_ERROR);
            }
        }

        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        if (privateKey == null) {
            try {
                S3Object s3Object = this.awsCloudUtil.getFile(rsaProperties.getPrivateKeyPath());

                BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
                String stringContent = reader.lines().collect(Collectors.joining());

                byte[] keyBytes = Base64.getDecoder().decode(stringContent);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                privateKey = keyFactory.generatePrivate(keySpec);
            } catch (Exception e) {
                log.error(MessageFactory.getLogMessage(
                        Messages.DECRYPT_ERROR,
                        "service.RSAEncryptionService.getPrivateKey",
                        e.getMessage()
                ));
                throw new ServiceException(Messages.DECRYPT_ERROR);
            }
        }

        return privateKey;
    }

    @Cacheable("rsa-encode-" + CacheUtil.HOURS_IN_A_WEEK)
    public String encode(String plainText) {
        try {
            PublicKey publicKey = this.getPublicKey();
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] cipherText = encryptCipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            log.error(MessageFactory.getLogMessage(
                    Messages.ENCRYPT_ERROR,
                    "service.RSAEncryptionService.encode",
                    e.getMessage()
            ));
            throw new UnprocessableEntityException(Messages.ENCRYPT_ERROR);
        }
    }

    @Cacheable("rsa-decode-" + CacheUtil.HOURS_IN_A_WEEK)
    public String decode(String encodedText) {
        try {
            PrivateKey privateKey = this.getPrivateKey();
            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] plainText = decryptCipher.doFinal(Base64.getDecoder().decode(encodedText));
            return new String(plainText);
        } catch (Exception e) {
            log.error(MessageFactory.getLogMessage(
                    Messages.DECRYPT_ERROR,
                    "service.RSAEncryptionService.decode",
                    e.getMessage()
            ));
            throw new UnprocessableEntityException(Messages.DECRYPT_ERROR);
        }
    }

}
