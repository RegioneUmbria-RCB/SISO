package it.umbriadigitale.utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Handshaking {
   
    //private SimpleDateFormat sdf = new SimpleDateFormat("ddMMMMyyyy");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private Calendar today = Calendar.getInstance(Locale.ITALY);
    private int gg = today.get(Calendar.DAY_OF_MONTH);
   
    private String secret = null;
   
    public Handshaking() {
        super();
        secret = sdf.format(today.getTime());
//        System.out.println("AMP="+secret);
    }//-------------------------------------------------------------------------

    public static void main(String[] args) throws Exception{
    	Handshaking h = new Handshaking();
		String hs = h.getHandshakeString();
		System.out.println("f5cca57d71ce586fc52d1378757b63b8\n"+hs);
    }//-------------------------------------------------------------------------
   
    public String getHandshakeString(){
        String salt = null;
        String ret = null;
        try {
            salt = secret;//getSalt();
            String randomOTPubK = getSecurePassword("GitNews", salt);
            ret = getCryptoro(randomOTPubK);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ret;
    }//-------------------------------------------------------------------------
   
    private String getCryptoro(String passwordToHash) throws NoSuchAlgorithmException{
        String generatedPassword = null;
       
        String pwdToHash = "";
        int resto = gg % 2;
        if (resto == 0){
//            giorno pari prendo dall'inizio stringa fino a gg
            if (passwordToHash != null && passwordToHash.length()>gg){
                pwdToHash = passwordToHash.substring(0, gg);
            }else{
                pwdToHash = passwordToHash;
            }           
        }else{
//            giorno dispari prendo da gg fino alla fine
            if (passwordToHash != null && passwordToHash.length()>gg){
                pwdToHash = passwordToHash.substring(gg);
            }else{
                pwdToHash = passwordToHash;
            }           
        }

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(pwdToHash.getBytes(), 0, pwdToHash.length());
        generatedPassword = new BigInteger(1, m.digest()).toString(16);
       
        return generatedPassword;
    }//-------------------------------------------------------------------------

    private String getSecurePassword(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }//-------------------------------------------------------------------------
   
   
}