package it.webred.AMProfiler.util;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class Token {

	private HashMap<String,String> listaClaim;
	
	public Token() {
		listaClaim = new HashMap<String, String>(); 
	}
	
	public void addClaimToToekn(String key, String value) {
		this.listaClaim.put(key, value);
	}
	//private static String SECRET = "c3bff416-993f-4760-9275-132b00256944";
	private static String SECRET = "abc5dje22as-8574f-1225-7654-1234567";
    public  String get(String name, String value) throws UnsupportedEncodingException {
        return JWT.create()
                .withIssuer("auth0")
                .withClaim(name, value)
                .withClaim("random", String.valueOf(UUID.randomUUID()))
                //.withClaim("login", "CPRMRA50A01F704P")
                //.withExpiresAt(new Date(System.currentTimeMillis() + (4 * 60 * 60 * 1000)))
                .withExpiresAt(new Date(System.currentTimeMillis() + (4 * 1000)))
                .sign(Algorithm.HMAC256(Token.SECRET));
    }

    public  String get() throws UnsupportedEncodingException {
    	com.auth0.jwt.JWTCreator.Builder builder = JWT.create();
    	
    	builder = builder.withIssuer("auth0");
        
		for (Map.Entry<String, String> entry : listaClaim.entrySet()) {
			builder = builder.withClaim(entry.getKey(), entry.getValue());
		}
		builder = builder.withClaim("random", String.valueOf(UUID.randomUUID()));

	    builder = builder.withExpiresAt(new Date(System.currentTimeMillis() + (4 * 1000)));
		return builder.sign(Algorithm.HMAC256(Token.SECRET));
		
    }
    public  String getNotExpired() throws UnsupportedEncodingException {
    	com.auth0.jwt.JWTCreator.Builder builder = JWT.create();
    	
    	builder = builder.withIssuer("auth0");
        
		for (Map.Entry<String, String> entry : listaClaim.entrySet()) {
			builder = builder.withClaim(entry.getKey(), entry.getValue());
		}
		builder = builder.withClaim("random", String.valueOf(UUID.randomUUID()));
        return builder.sign(Algorithm.HMAC256(Token.SECRET));
		
    }
   
    
}