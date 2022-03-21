package it.webred.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CryptoroUtils {

	private String name;

	public CryptoroUtils(String name) {
		this.name = name;
	}
	public CryptoroUtils() {
	}
	public String getChiavePRIK() throws Exception {
		String salt = getSalt();
		String randomOTPubK = getSecurePassword(name, salt);
		System.out.println("OTP " + randomOTPubK);
		String generatedOTPriK = getCryptoro(randomOTPubK);
		return generatedOTPriK;
	}

	private static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}// -------------------------------------------------------------------------

	private static String getSecurePassword(String passwordToHash, String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}// -------------------------------------------------------------------------

	private static String getCryptoro(String passwordToHash) throws Exception {
		String generatedPassword = null;

		Date oggi = new Date(System.currentTimeMillis());
		GregorianCalendar gc = new GregorianCalendar(Locale.ITALY);

		gc.setTime(oggi);
		int gg = gc.get(GregorianCalendar.DAY_OF_MONTH);
		String pwdToHash = "";
		int resto = gg % 2;
		if (resto == 0) {
			/*
			 * giorno pari prendo dall'inizio stringa fino a gg
			 */
			if (passwordToHash != null && passwordToHash.length() > gg) {
				pwdToHash = passwordToHash.substring(0, gg);
			} else {
				pwdToHash = passwordToHash;
			}
		} else {
			/*
			 * giorno dispari prendo da gg fino alla fine
			 */
			if (passwordToHash != null && passwordToHash.length() > gg) {
				pwdToHash = passwordToHash.substring(gg);
			} else {
				pwdToHash = passwordToHash;
			}
		}

//		MessageDigest m = MessageDigest.getInstance("MD5");
//		m.update(pwdToHash.getBytes(), 0, pwdToHash.length());
		generatedPassword =getMD5Pwd(pwdToHash); //new BigInteger(1, m.digest()).toString(16);

		return generatedPassword;
	}// ----------------

	public static String getMD5Pwd(String decodedPwd) {
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		m.update(decodedPwd.getBytes(), 0, decodedPwd.length());
		String bytesCurMD5Pwd = new BigInteger(1, m.digest()).toString(16);
		return bytesCurMD5Pwd;
	}
}
