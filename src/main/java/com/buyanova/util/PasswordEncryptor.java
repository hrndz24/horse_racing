package com.buyanova.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum PasswordEncryptor {
    INSTANCE;

    private static final String HASHING_ALGORITHM = "SHA-1";

    private Logger logger = LogManager.getLogger(PasswordEncryptor.class);

    public String encryptPassword(String password) {
        byte[] bytesEncoded = new byte[0];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
            messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
            bytesEncoded = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.warn("Password hashing algorithm not found", e);
        }
        BigInteger hashedPassword = new BigInteger(1, bytesEncoded);
        return hashedPassword.toString(36);
    }
}
