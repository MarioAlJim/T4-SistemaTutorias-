package com.teamfour.sistutorias.dataaccess;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SHA512 {
    public  static String getSHA512(String password){
        String encryptedPassword= null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(password.getBytes("utf8"));
            encryptedPassword = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException nsaException) {
            Logger.getLogger(SHA512.class.getName()).log(Level.SEVERE, null, nsaException);
        } catch (UnsupportedEncodingException ueException) {
            Logger.getLogger(SHA512.class.getName()).log(Level.SEVERE, null, ueException);
        }
        return encryptedPassword;
    }
}