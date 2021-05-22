package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashingAlgo {
    public String getAdminhash() {
        adminhash = "dcf59903d3e24553ed892ef63dd98190bdedd66711c15ce936317f992b33707843cb3fb16b993e6bef67d1d73c447216faf199fcc7a1d451422c826df8c68462";
        return adminhash;
    }

    //PBKDF2Hash: Password-based Key Derivation Function 2
    private String adminhash;
    public static byte[] hashPassword( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return res;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
}
