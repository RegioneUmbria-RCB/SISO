package it.umbriadigitale.soclav.util;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Token {

	
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

    public  String get(HashMap<String,String> listaClaim) throws UnsupportedEncodingException {
    	com.auth0.jwt.JWTCreator.Builder builder = JWT.create();
    	
    	builder = builder.withIssuer("auth0");
        
		for (Map.Entry<String, String> entry : listaClaim.entrySet()) {
			builder = builder.withClaim(entry.getKey(), entry.getValue());
		}
		builder = builder.withClaim("random", String.valueOf(UUID.randomUUID()));

	    builder = builder.withExpiresAt(new Date(System.currentTimeMillis() + (4 * 1000)));
		return builder.sign(Algorithm.HMAC256(Token.SECRET));
		
    }
    
    public TokenClaim verify(String token) throws JWTVerificationException, UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Token.SECRET))
                .withIssuer("auth0")
                .acceptExpiresAt(4)
                .build();
        
        TokenClaim tc = new TokenClaim();
        try {
        	
        DecodedJWT decoded = verifier.verify(token);
        Claim claim = decoded.getClaim("login");
        Claim claimEnte = decoded.getClaim("ente");
        tc.setLogin(claim.asString());
        tc.setEnte(claimEnte.asString());
        tc.setEsito(0);
        tc.setMessaggio("Token valido");
        return tc;
        }catch(TokenExpiredException tee) {
        	System.out.println("token [" + token  + "] Scaduto !!!");
        	tc.setEsito(-1);
        	tc.setMessaggio("token [" + token  + "] Scaduto !!!");
        }
        return tc;
    }
    public TokenClaim verifyNotExpired(String token) throws JWTVerificationException, UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Token.SECRET))
                .withIssuer("auth0")
                 .build();
        
        TokenClaim tc = new TokenClaim();
        try {
        	
        DecodedJWT decoded = verifier.verify(token);
        Claim claim = decoded.getClaim("login");
        Claim claimEnte = decoded.getClaim("ente");
        tc.setLogin(claim.asString());
        tc.setEnte(claimEnte.asString());
        tc.setEsito(0);
        tc.setMessaggio("Token valido");
        return tc;
        }catch(TokenExpiredException tee) {
        	System.out.println("token [" + token  + "] Scaduto !!!");
        	tc.setEsito(-1);
        	tc.setMessaggio("token [" + token  + "] Scaduto !!!");
        }
        return tc;
    }
}
