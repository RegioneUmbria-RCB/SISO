package it.umbriadigitale.ldap;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUser;


public class UserAssocMappabile extends UserAssoc{
	
	
	public UserAssocMappabile() {
		AmUser u = new AmUser();
		u.setName("alupi");
		u.setEmail("alupi@monza.it");
		AmAnagrafica a = new AmAnagrafica();
		a.setAmUser(u);
		a.setNome("a");
		a.setCognome("lupi");
		this.anag = a;
		this.mappable = true;
		this.mapped	  = false;
	}

	//--- delegate method ------
	/*
	public String getGitUserName(){
		return "Mappabile GitUserName";
	}
	public String getGitCf(){
		return "Mappabile GitCf";
	}
	public String getGitNominativo(){
		return "Mappabile GitNominativo";
	}
	public String getGitMail(){
		return "Mappabile GitMail";
	}
	*/
	public String getLdpUserName(){
		return "Mappabile LdpUserName";
	}
	public String getLdpCF(){
		return "Mappabile LdpCF";
	}
	public String getLdpNominativo(){
		return "Mappabile LdpNominativo";
	}
	public String getLdpMail(){
		return "Mappabile LdpMail";
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
	/*
	public String getUserName(){
		return "Mappabile UserName";
	}
	public String getCf(){
		return "Mappabile Cf";
	}
	public String getNominativo(){
		return "Mappabile Nominativo";
	}
	public String getMail(){
		return "Mappabile Mail";
	}
	*/
	
	
	
}
