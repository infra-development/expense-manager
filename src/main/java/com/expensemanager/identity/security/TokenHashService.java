package com.expensemanager.identity.security;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class TokenHashService {

    private static final String HASH_ALGORITHM = "SHA-256";

    public String hash(String token) {

        try {

            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);

            byte[] hash = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException ex) {

            throw new IllegalStateException(
                    "SHA-256 algorithm is unavailable.",
                    ex
            );
        }
    }
}