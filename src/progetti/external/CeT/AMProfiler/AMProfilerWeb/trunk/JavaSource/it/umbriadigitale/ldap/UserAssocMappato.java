package it.umbriadigitale.ldap;


public class UserAssocMappato extends UserAssoc{
	
	
	public UserAssocMappato() {
		this.mappable = true;
		this.mapped	  = true;
	}

	//--- delegate method ------
	
	public String getGitUserName(){
		return "Mappato GitUserName";
	}
	public String getGitCf(){
		return "Mappato GitCf";
	}
	public String getGitNominativo(){
		return "Mappato GitNominativo";
	}
	public String getGitMail(){
		return "Mappato GitMail";
	}
	
	public String getLdpUserName(){
		return "Mappato LdpUserName";
	}
	public String getLdpCF(){
		return "Mappato LdpCF";
	}
	public String getLdpNominativo(){
		return "Mappato LdpNominativo";
	}
	public String getLdpMail(){
		return "Mappato LdpMail";
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
		return "Mappato UserName";
	}
	public String getCf(){
		return "Mappato Cf";
	}
	public String getNominativo(){
		return "Mappato Nominativo";
	}
	public String getMail(){
		return "Mappato Mail";
	}
	
	
	
	
}
