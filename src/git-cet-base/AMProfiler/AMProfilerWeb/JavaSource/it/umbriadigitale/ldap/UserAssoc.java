package it.umbriadigitale.ldap;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.webred.amprofiler.model.AmAnagrafica;

public class UserAssoc implements Comparable<UserAssoc>{
	
	private static final Logger LOG = Logger.getLogger(UserAssoc.class.getName());
	
	protected UserAssoc() {
		super();
	}
	public UserAssoc(AmAnagrafica anag, LdapUser ldapu) {
		super();
		this.anag = anag;
		this.nominativo = buildNominativo();
		
		if(ldapu!=null){
			this.ldapu = ldapu;
			
			this.userAssoc 		= getGitUserName()!=null 	&& getLdpUserName()!=null	? getGitUserName()	 .equalsIgnoreCase(getLdpUserName()) 	: false;
			if(LOG.isInfoEnabled())LOG.info("GitUserName<"+getGitUserName()+"> - LdpUserName<"+getLdpUserName()+">; this.userAssoc: "+this.userAssoc);
			this.nominativoAssoc= getGitNominativo()!=null 	&& getLdpNominativo()!=null	? getGitNominativo() .equalsIgnoreCase(getLdpNominativo())	: false;
			if(LOG.isInfoEnabled())LOG.info("GitNominativo<"+getGitNominativo()+"> - LdpNominativo<"+getLdpNominativo()+">; this.nominativoAssoc: "+this.nominativoAssoc);
			this.mailAssoc 		= getGitMail()!=null 		&& getLdpMail()!=null		? getGitMail()		 .equalsIgnoreCase(getLdpMail()) 		: false;
			if(LOG.isInfoEnabled())LOG.info("GitMail<"+getGitMail()+"> - LdpMail<"+getLdpMail()+">; this.mailAssoc: "+this.mailAssoc);
			if(LOG.isInfoEnabled())LOG.info("userAssoc||nominativoAssoc||mailAssoc: "+(userAssoc||nominativoAssoc||mailAssoc));
		}else{
			this.userAssoc=true;
			this.mappable = true;
			this.mapped=true;
		}
	}
	public UserAssoc(AmAnagrafica anag, boolean userAssoc, boolean mappable, boolean mapped) {
		super();
		this.anag = anag;
		this.nominativo = buildNominativo();
		
		this.userAssoc	= userAssoc;
		this.mappable 	= mappable;
		this.mapped		= mapped;
	}

	protected AmAnagrafica anag;
	protected LdapUser ldapu;
	
	protected Boolean mappable;
	protected Boolean mapped;
	protected boolean mapping;
	
	protected boolean userAssoc;
	protected boolean cfAssoc;
	protected boolean nominativoAssoc;
	protected boolean mailAssoc;
	
	public AmAnagrafica getAnag() {
		return anag;
	}
	public LdapUser getLdapu() {
		return ldapu;
	}
	
	
	//--- delegate method ------
	private String nominativo;
	
	public String getGitUserName(){
		return anag.getAmUser().getName();
	}
	public String getGitCf(){
		return anag.getCodiceFiscale();
	}
	public String getGitNominativo(){
		return nominativo;
	}
	public String getGitMail(){
		return anag.getAmUser().getEmail();
	}
	
	public String getLdpUserName(){
		if(ldapu!=null)
			return ldapu.getUser();
		else
			return "";
	}
	public String getLdpNominativo(){
		if(ldapu!=null)
			return ldapu.getNominativo();
		else
			return "";
	}
	public String getLdpMail(){
		if(ldapu!=null)
			return ldapu.getMail();
		else
			return "";
	}
	
	// ---- mapping method ----

	public boolean isMappable() {
		if(mappable==null)
			mappable = (userAssoc||nominativoAssoc||mailAssoc);
		return mappable;
	}
	public boolean isMapped() {
		if(mapped==null)
			mapped= anag.getAmUser().getOldName()!=null;
		return mapped;
	}
	public void setMapped(boolean mapped) {
		this.mapped = mapped;
	}
	public boolean isMapping() {
		return mapping;
	}
	public void setMapping(boolean mapping) {
		this.mapping = mapping;
	}
	
	
	public boolean isUserAssoc() {
		return userAssoc;
	}
	public boolean isNominativoAssoc() {
		return nominativoAssoc;
	}
	public boolean isMailAssoc() {
		return mailAssoc;
	}

	public String getUserName(){
		if(userAssoc)
			return getGitUserName();
		else
			return "";
	}
	public String getCf(){
		if(cfAssoc)
			return getGitCf();
		else
			return "";
	}
	public String getNominativo(){
		if(nominativoAssoc){
			return getGitNominativo();
		}else
			return "";
	}
	public String getMail(){
		if(mailAssoc)
			return anag.getAmUser().getEmail();
		else
			return "";
	}
	
	// ----utility --------
	
	@Override
	public int compareTo(UserAssoc o) {
		if((this.isMapped() == o.isMapped()) && this.isMapped()){
			return this.getGitUserName().compareTo(o.getGitUserName());	//tutti e 2 verdi
		}else if(this.isMapped()){
			return 1;													//this è verde (quindi andare giù)
		}else if(o.isMapped()){
			return -1;													//o    è verde (quindi andare sù)
		}
		//nessuno dei 2 è verde
		if((this.isMappable() == o.isMappable()) && this.isMappable()){
			return this.getGitUserName().compareTo(o.getGitUserName());	//tutti e 2 gialli
		}else if(this.isMappable()){
			return -1;													//this è giallo (quindi andare sù)
		}else if(o.isMappable()){
			return 1;													//o    è giallo (quindi andare giù)
		}
		return this.getGitUserName().compareTo(o.getGitUserName());		//tutti e 2 rossi
	}
	
	public String buildNominativo(){
		return anag.getCognome()!=null && anag.getNome()!=null? anag.getNome()+" "+anag.getCognome() : null;
	}
	
 	public static List<UserAssoc> assoc(List<AmAnagrafica> anags, LdapFacade ldap){
 		List<UserAssoc> listAssoc = new ArrayList<UserAssoc>();
 		for(AmAnagrafica a : anags){
			listAssoc.add(ldap.getUserAssoc(a));
 		}
		return listAssoc;
	}
	
	
	
	
	
	
	
	
}
