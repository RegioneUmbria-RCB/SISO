package it.webred.AMProfiler.services;

import it.webred.AMProfiler.servlet.BaseAction;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService()
public class AuthWS {
	
	private final static Integer NUM_PAR = 3;

	@WebMethod()
	public String sayHello(String name) throws Exception{
	    System.out.println("Hello: " + name);
	    /*
	     * name = APPLICATIVO CHIAMANTE;BELFIORE_ENTE;UTENTE;
	     */
	    
	    String ragioneAccesso = "";
	    String belfiore = "";
	    String userName = "";
	    
	    String pratica = "ESTERNA AL GIT";
	    String fkAmApp = "diogene";
	    
	    String randomOTPubK = "";
	    String generatedOTPriK = "";
	    
	    if (name != null && !name.trim().equalsIgnoreCase("")){
		    String[] aryInfo = name.split(";");	  
		    if (aryInfo != null && aryInfo.length==NUM_PAR){
		    	ragioneAccesso = aryInfo[0];
		    	belfiore = aryInfo[1];
		    	userName = aryInfo[2];
		    	
		   	 // salvo accesso come traccia
		 		Connection con = null;
		 		PreparedStatement st = null;
		 		try {
		 			con = BaseAction.apriConnessione();
		 			con.setAutoCommit(false);
		 			/*
		 			 * Recupero max(id) per insert in AM_TRACCIA_ACCESSI
		 			 */
		 			String sql = "SELECT NVL(MAX(ID), 0) + 1 AS NEWID FROM AM_TRACCIA_ACCESSI";
					st = con.prepareStatement(sql);
					ResultSet rs = st.executeQuery();
					int newId = 0;
					while (rs.next()) {
						newId = rs.getInt("NEWID");
					}
					rs.close();
					st.cancel();
					/*
					 * Informazioni da tracciare:
					 * ente, usr, chiave pubblica, chiave privata, data di accesso, usata
					 */
					/*
					 * Generazione delle chiavi pubblica e privata
					 */
				    String salt = getSalt();
				    randomOTPubK = getSecurePassword(name, salt);
			        System.out.println("OTP " + randomOTPubK);
			        /*
			         * processa la randomOTP con un algoritmo ben preciso ed inserisci 
			         * nel DB in corrispondenza del nome utente il risultato del processo:
			         *  questa chiave autenticherà la sessione del SIT
			         */
			        generatedOTPriK = getCryptoro(randomOTPubK);
		        
			        sql = "INSERT INTO AM_TRACCIA_ACCESSI (USER_NAME, RAGIONE_ACCESSO, FK_AM_ITEM, PRATICA, ID, ENTE, PUBK, PRIK, USATA) "
							+ "VALUES (?,?,?,?,?,?,?,?,?)";
					st = con.prepareStatement(sql);

					int paramIndex = 0;
					st.setString(++paramIndex, userName);
					st.setString(++paramIndex, ragioneAccesso);
					st.setString(++paramIndex, fkAmApp);
					st.setString(++paramIndex, pratica);
					st.setInt(++paramIndex, newId);
					st.setString(++paramIndex, belfiore);
					st.setString(++paramIndex, randomOTPubK);
					st.setString(++paramIndex, generatedOTPriK);
					st.setBoolean(++paramIndex, false);
					st.executeUpdate();
			        
			        st.cancel();
			
					con.commit();
			
				} catch (Exception e) {
					try {
						e.printStackTrace();
						BaseAction.rollback(con);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					BaseAction.chiudiConnessione(con, st);
				}
		    	
		    }
	    }

	    return randomOTPubK;
	}//-------------------------------------------------------------------------
	
	private static String getSalt() throws NoSuchAlgorithmException	{
	    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	    byte[] salt = new byte[16];
	    sr.nextBytes(salt);
	    return salt.toString();
	}//-------------------------------------------------------------------------
	
	private static String getSecurePassword(String passwordToHash, String salt){
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
	
	private static String getCryptoro(String passwordToHash) throws Exception{
        String generatedPassword = null;
        
        Date oggi = new Date(System.currentTimeMillis());
        GregorianCalendar gc = new GregorianCalendar(Locale.ITALY);
        
        gc.setTime(oggi);
        int gg = gc.get(GregorianCalendar.DAY_OF_MONTH);
        String pwdToHash = "";
        int resto = gg % 2;
        if (resto == 0){
        	/*
        	 * giorno pari prendo dall'inizio stringa fino a gg
        	 */
            if (passwordToHash != null && passwordToHash.length()>gg){
            	pwdToHash = passwordToHash.substring(0, gg);
            }else{
            	pwdToHash = passwordToHash;
            }        	
        }else{
        	/*
        	 * giorno dispari prendo da gg fino alla fine
        	 */
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
	
}
