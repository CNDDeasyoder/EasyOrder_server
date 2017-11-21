package gameloft.com.easyorder_server;

/**
 * Created by thinhle on 11/21/17.
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: Lam Ngoc Khuong
 * Website: http://ngockhuong.com - Programming knowledge sharing
 */
public class MD5 {

    public String md5(String str){
        MessageDigest md;
        String result = "";
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            BigInteger bi = new BigInteger(1, md.digest());

            result = bi.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
