package databaseUtils;

import log.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    private static final String pepper = "134F@!!9hTn4-@+*dfs*12";

    public String encrypt(String s) {
        final int maxLen = 32;
        final int radix = 16;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.logger.error("Cant encrypt password");
        }
        byte[] messageDigest = md.digest((s+pepper).getBytes());

        BigInteger no = new BigInteger(1, messageDigest);
        StringBuilder hashtext = new StringBuilder(no.toString(radix));
        while (hashtext.length() < maxLen) {
            hashtext.insert(0, "0");
        }
        return hashtext.toString();
    }
}
