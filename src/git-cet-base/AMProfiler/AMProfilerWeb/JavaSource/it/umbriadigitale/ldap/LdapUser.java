package it.umbriadigitale.ldap;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

public class LdapUser {
	
	public LdapUser(Attributes adUser) {
		super();
		this.adUser = adUser;
	}
	
	public LdapUser(String user, String nominativo, String mail) {
		super();
		this.user = user;
		this.nominativo = nominativo;
		this.mail = mail;
	}


	private Attributes adUser;
	
	private String user;
	private String nominativo;
	private String mail;
	private String principal;
	
	public String getUser() {
		if(user==null){
			Attribute attr = adUser.get("samaccountname");
			if(attr!=null){
				String tmp = attr.toString().trim();
				user = tmp.substring(tmp.indexOf(":")+1).trim();
			}else{
				return "";
			}
		}
		return user;
	}
	public String getNominativo() {
		if(nominativo==null){
			String nome="";
			String cognome="";
			Attribute attrNome = adUser.get("givenName");
			if(attrNome!=null){
				String tmp = attrNome.toString().trim();
				nome = tmp.substring(tmp.indexOf(":")+1).trim();
			}
			Attribute attrCognome = adUser.get("sn");
			if(attrCognome!=null){
				String tmp = attrCognome.toString().trim();
				cognome = tmp.substring(tmp.indexOf(":")+1).trim();
			}
			nominativo = nome+" "+cognome;
		}
		return nominativo;
	}
	public String getMail() {
		if(mail==null){
			Attribute attr = adUser.get("mail");
			if(attr!=null){
				String tmp = attr.toString().trim();
				mail = tmp.substring(tmp.indexOf(":")+1).trim();
			}else{
				return "";
			}
		}
		return mail;
	}
	public String getPrincipal() {
		if(principal==null){
			Attribute attr = adUser.get("userPrincipalName");
			if(attr!=null){
				String tmp = attr.toString().trim();
				principal = tmp.substring(tmp.indexOf(":")+1).trim();
			}else{
				return "";
			}
		}
		return principal;
	}
	
	
	
	
	
}
