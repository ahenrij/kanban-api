package fr.istic.taa.jaxrs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    interface ENC_TYPE {
        String MD5 = "MD5";
        String SHA1 = "SHA1";
    }

    public static String hash(String password) {
        return encrypt(password, ENC_TYPE.SHA1);
    }

    private static String encrypt(String input, String encType) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(encType);
            md.update(input.getBytes());
            byte[] mdBytes = md.digest();

            //convert the byte to hex format
            for (byte mdByte : mdBytes) {
                sb.append(Integer.toString((mdByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return sb.toString();
    }
}
