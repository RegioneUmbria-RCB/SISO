package it.umbriadigitale.ldap;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUser;


public class UserAssocNonMappabile extends UserAssoc{
	
	
	public UserAssocNonMappabile() {
		AmUser u = new AmUser();
		u.setName("assisi4");
		u.setEmail("test@test.it");
		AmAnagrafica a = new AmAnagrafica();
		a.setAmUser(u);
		a.setNome("Non mappabile");
		a.setCognome("Nominativo");
		this.anag = a;
		this.mappable = false;
		this.mapped	  = false;
	}

	//--- delegate method ------
	
	public String getGitUserName(){
		return "Non mappabile GitUserName";
	}
	public String getGitCf(){
		return "Non mappabile GitCf";
	}
	public String getGitNominativo(){
		return "Non mappabile GitNominativo";
	}
	public String getGitMail(){
		return "Non mappabile GitMail";
	}
	
	public String getLdpUserName(){
		return "Non mappabile LdpUserName";
	}
	public String getLdpCF(){
		return "Non mappabile LdpCF";
	}
	public String getLdpNominativo(){
		return "Non mappabile LdpNominativo";
	}
	public String getLdpMail(){
		return "Non mappabile LdpMail";
	}
	
	// ---- mapping method ----

	public boolean isMappable() {
		return mappable;
	}
	public boolean isMapped() {
		return mapped;
	}
	public boolean isMapping() {
		return mapping;
	}
	
	public String getUserName(){
		return "Non mappabile UserName";
	}
	public String getCf(){
		return "Non mappabile Cf";
	}
	public String getNominativo(){
		return "Non mappabile Nominativo";
	}
	public String getMail(){
		return "Non mappabile Mail";
	}
	
	
	
	
}
