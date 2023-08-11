package com.yja.myapp.auth.utill;


import at.favre.lib.crypto.bcrypt.BCrypt;


public class HashUtil {
    // bcrypt 해시생성
    public String createHash(String cipherText){
        // hashToString() : salit와 함께 해시를 생성해준다.
        return BCrypt.withDefaults().hashToString(12,cipherText.toCharArray());
    }

    // password문자열을 받아서 salt와 hasgh가 맞는지 확인

    // ciphertext(암호화 안 된 문자열)
    // plaintext(구조가 없는 문자열)
    public boolean verifyHash(String cipherText, String hash){
       // 해시르 검증할때는 이미 있는 salt값으로
       // cipher text를 결합하여 맞는지 확인
       return BCrypt.verifyer().verify(cipherText.toCharArray(), hash).verified;

       //
    }
}
