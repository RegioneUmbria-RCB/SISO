package it.umbriadigitale.argo.ejb.client.cs.dto.siru;

import java.io.Serializable;

public class SiruJsonTokenDTO implements Serializable{
	/* {"access_token":"e676de9a-2c4e-388a-b175-4c50e43c385d",
	 	"scope":"am_application_scope default",
	 	"token_type":"Bearer",
	 	"expires_in":3600}*/

	private static final long serialVersionUID = 1L;
	public String access_token;
	public String scope;
	public String token_type;
	public String expires_in;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
}
