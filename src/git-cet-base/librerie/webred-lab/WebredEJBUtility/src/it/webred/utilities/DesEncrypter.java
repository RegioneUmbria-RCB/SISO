package it.webred.utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author Alessandro Feriani
 * 
 */
public class DesEncrypter {

	Cipher ecipher;
	Cipher dcipher;
	Integer baseCodingType = 32;

	public DesEncrypter(SecretKey key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		setup(key);
	}

	public DesEncrypter(String keyValue) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {

		DESKeySpec keySpec = new DESKeySpec(keyValue.getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(keySpec);

		setup(key);

	}

	protected void setup(SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		ecipher = Cipher.getInstance("DES");
		dcipher = Cipher.getInstance("DES");
		ecipher.init(Cipher.ENCRYPT_MODE, key);
		dcipher.init(Cipher.DECRYPT_MODE, key);
	}

	public String encrypt(String str) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {

		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");

		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);

		// Encode bytes to base64 to get a string
		return baseEncode(enc);
	}

	public String decrypt(String str) throws IOException, IllegalBlockSizeException, BadPaddingException {

		// Decode base64 to get bytes
		byte[] dec = baseDecode(str);

		// Decrypt
		byte[] utf8 = dcipher.doFinal(dec);

		// Decode using utf-8
		return new String(utf8, "UTF8");
	}

	protected byte[] baseDecode(String dec) throws IOException {
		if (baseCodingType == 64)
			return new sun.misc.BASE64Decoder().decodeBuffer(dec);
		else
			return new BASE32Encoder().decode(dec);
	}

	protected String baseEncode(byte[] enc) {
		if (baseCodingType == 64)
			return new sun.misc.BASE64Encoder().encode(enc);
		else
			return new BASE32Encoder().encode(enc);
	}

	public void setBaseCodingType(Integer baseEncoderType) {
		if (!(baseEncoderType == 32 || baseEncoderType == 64))
			throw new RuntimeException("BaseEncoderType " + baseEncoderType.toString() + " not supported");

		baseCodingType = baseEncoderType;
	}
}
