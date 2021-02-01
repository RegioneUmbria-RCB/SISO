package it.umbriadigitale.ldap;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
//import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

import it.webred.AMProfiler.servlet.BaseAction;
import it.webred.amprofiler.model.AmAnagrafica;

public class LdapFacade {
	public static int n=1;
	
	private static final Logger LOG = Logger.getLogger(LdapFacade.class.getName());
	protected SearchControls searchControls = new SearchControls(SearchControls.SUBTREE_SCOPE, 0, 0, null, false, false);
	
	protected String ldapAdServer = "ldap://10.63.5.2:389";
	protected String ldapSearchBase = "dc=comune,dc=milano,dc=local";
	protected DirContext ctx;
    
	protected String ldapUsername = "filippo.mazzini@comune.milano.it";
	protected String ldapPassword = "Ab8!Ab8!";

    
	public LdapFacade(){
		super();
		Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        if(ldapUsername != null) {
            env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
        }
        if(ldapPassword != null) {
            env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        }
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapAdServer);
//        env.put(Context.REFERRAL, "follow");

        //ensures that objectSID attribute values
        //will be returned as a byte[] instead of a String
        env.put("java.naming.ldap.attributes.binary", "objectSID");
        
        // the following is helpful in debugging errors
        //env.put("com.sun.jndi.ldap.trace.ber", System.err);
        
        try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			LOG.error("new LdapFacade(): "+e.getMessage(), e);
			e.printStackTrace();
		}
	}
    public LdapFacade(String ldapIpServer, String ldapSearchBase, String ldapUsername, String ldapPassword) {
		super();
		this.ldapAdServer = "ldap://"+ldapIpServer;
		this.ldapSearchBase = ldapSearchBase;
		this.ldapUsername = ldapUsername;
		this.ldapPassword = ldapPassword;
		
		Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        if(ldapUsername != null) {
            env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
        }
        if(ldapPassword != null) {
            env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        }
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapAdServer);

        //ensures that objectSID attribute values
        //will be returned as a byte[] instead of a String
        env.put("java.naming.ldap.attributes.binary", "objectSID");
        
        // the following is helpful in debugging errors
        //env.put("com.sun.jndi.ldap.trace.ber", System.err);
        
        try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			LOG.error("new LdapFacade(): "+e.getMessage(), e);
			e.printStackTrace();
		}
	}
    
    /**
     * Cerca di trovare un singolo user Ldap corrispondente. Viene usata nella prima scansione degli utenti GIT.
     * Prima cerca un user con nome cognome identico, se non lo trova, prova con la mail ed in ultima istanza con lo username.
     * @param a L'AmAnagrafica di cui cercare l'user Ldap
     * @return una nuova istanza di UserAssoc in tutti i casi perchè direttamente usabile per comporre la lista da proporre a video che si aspetta 3 tipi di UserAssoc:<br>
     * verde l'utente GIT risulta già associato perchè nella tabella Am_user la colonna old_user è valorizzata.<br>
     * giallo ha trovato un user Ldap compatibile ed è necessaria conferma.<br>
     * rosso non è stato trovato nessun utente compatibile, è necessario entrare nel dettaglio della ricerca per visualizzare tutti gli utenti compatibili e/o svolgere ulteriori ricerche.
     */
    public UserAssoc getUserAssoc(AmAnagrafica a) {
    	if(ctx==null){
    		LOG.error("Context Ldap null!");
    		throw new NullPointerException("Context Ldap null!");
//    		if("test@test.it".equals(a.getAmUser().getEmail())){
//    			return new UserAssoc(a, new LdapUser(a.getAmUser().getName()+n, a.getNome()+" "+a.getCognome()+n++, a.getAmUser().getEmail()));
//    		}else{
//    			return new UserAssoc(a, false, false, false);
//    		}
    	}else{
    		UserAssoc ua = null;
        	SearchResult srLdapUser=null;
        	try {
    			if(srLdapUser==null)
    				srLdapUser = findByNominativo(a.getNome(), a.getCognome());//nominativo
    			if(srLdapUser==null)
    				srLdapUser = findByMail(a.getAmUser().getEmail());//mail
    			if(srLdapUser==null)
    				srLdapUser = findByUserName(a.getAmUser().getName());//user
    			
    		} catch (Exception e) {
    			LOG.error(e.getMessage(), e);
    			e.printStackTrace();
    		}
        	if(srLdapUser!=null){
        		Attributes attr = srLdapUser.getAttributes();
        		if(LOG.isInfoEnabled())LOG.info("Attributes Ldap trovato!");
        		ua = new UserAssoc(a, new LdapUser(attr));
        	}else{
        		if(LOG.isInfoEnabled())LOG.info("Attributes Ldap NON trovato!");
        		ua = new UserAssoc(a, false, false, false);
        	}
    		return ua;
    	}
	}
    /**
     * Trova tutti gli user Ldap che potrebbero essere compatibili mettendo in like separatamente nome, cognome, mail, user, e li wrappa in un LdapUser.
     * Basta che uno di questi sia contenuto ed il nominativo viene messo in lista. 
     * In più questi parametri possono essere anche specificati dall'utente e gli user trovati vengono accodati a quelli già in linea con AmAnagrafica.
     * @param a L'AmAnagrafica di cui cercare gli user Ldap da cui verrà preso nome, cognome, mail, user per il confronto
     * @param nome parametro custom ulteriore che viene aggiunto in OR se presente
     * @param cognome parametro custom ulteriore che viene aggiunto in OR se presente
     * @param user parametro custom ulteriore che viene aggiunto in OR se presente
     * @param mail parametro custom ulteriore che viene aggiunto in OR se presente
     * @return Una lista di LdapUser con uno qualsiasi dei parametri sopra elencati trovato in like
     */
    public List<LdapUser> getUserAssoc(AmAnagrafica a, String nome, String cognome, String user, String mail) {
    	List<LdapUser> ua = new ArrayList<LdapUser>();
    	if(ctx==null){
    		LOG.error("Context Ldap null!");
    		throw new NullPointerException("Context Ldap null!");
//    		String uname = a.getAmUser().getName();
//    		String nominativo = a.getNome()+" "+a.getCognome();
//    		String umail = a.getAmUser().getEmail();
//    		if("test@test.it".equals(umail)){
//    			ua.add(new LdapUser(uname+"TEST", nominativo+"TEST", umail));
//    		}
//    		ua.add(new LdapUser(uname+"1", nominativo+"1", umail+"1"));
//    		ua.add(new LdapUser(uname+"2", nominativo+"2", umail+"2"));
//    		ua.add(new LdapUser(uname+"3", nominativo+"3", umail+"3"));
//    		return ua;
    	}else{
    		List<Attributes> ldapUser=null;
        	try {
				ldapUser = findByCustomParamiter(a, nome, cognome, user, mail);    			
    		} catch (Exception e) {
    			LOG.error(e.getMessage(), e);
    			e.printStackTrace();
    		}
        	if(ldapUser!=null && ldapUser.size()>0){
        		if(LOG.isInfoEnabled())LOG.info("Attributes Ldap trovati: "+ldapUser.size()+"!");
        		for(Attributes attr : ldapUser){
        			ua.add(new LdapUser(attr));
        		}
        	}else{
        		if(LOG.isInfoEnabled())LOG.info("Attributes Ldap non trovati!");
        	}
    		return ua;
    	}
	}

    /**
     * Trova tutti gli user Ldap che potrebbero essere compatibili mettendo in like separatamente nome, cognome, mail, user.
     * Basta che uno di questi sia contenuto ed il nominativo viene messo in lista. 
     * In più questi parametri possono essere anche specificati dall'utente e gli user trovati vengono accodati a quelli già in linea con AmAnagrafica.
     * @param a L'AmAnagrafica di cui cercare gli user Ldap da cui verrà preso nome, cognome, mail, user per il confronto
     * @param nome parametro custom ulteriore che viene aggiunto in OR se presente
     * @param cognome parametro custom ulteriore che viene aggiunto in OR se presente
     * @param user parametro custom ulteriore che viene aggiunto in OR se presente
     * @param mail parametro custom ulteriore che viene aggiunto in OR se presente
     * @return Una lista di Attributes (che sono i contenitori delle informazioni) con uno qualsiasi dei parametri sopra elencati trovato in like
     */
    private List<Attributes> findByCustomParamiter(AmAnagrafica a, String nome, String cognome, String user, String mail) throws NamingException {
		String criterioNome 	= "";
		String criterioCognome 	= "";
		String criterioUser 	= "";
		String criterioMail 	= "";
		
		String criterioCustomNome 	= "";
		String criterioCustomCognome= "";
		String criterioCustomUser 	= "";
		String criterioCustomMail 	= "";

		// --- TUTTI GLI ATTRIBUTI CERCATI PER LIKE ---
		if(a.getNome()!=null && !a.getNome().equals(""))
			criterioNome 	+= "(givenName=*"+a.getNome()+"*)";
		if(a.getCognome()!=null && !a.getCognome().equals(""))
			criterioCognome += "(sn=*"+a.getCognome()+"*)";
		if(a.getAmUser().getName()!=null && !a.getAmUser().getName().equals(""))
			criterioUser 	+= "(sAMAccountName=*"+a.getAmUser().getName()+"*)";
		if(a.getAmUser().getEmail()!=null && !a.getAmUser().getEmail().equals(""))
			criterioMail 	+= "(mail=*"+a.getAmUser().getEmail()+"*)";     
		
		if(nome!=null && !nome.equals(""))
			criterioCustomNome += "(givenName=*"+nome+"*)";
		if(cognome!=null && !cognome.equals(""))
			criterioCustomCognome += "(sn=*"+cognome+"*)";
		if(user!=null && !user.equals(""))
			criterioCustomUser += "(sAMAccountName=*"+user+"*)";
		if(mail!=null && !mail.equals(""))
			criterioCustomCognome += "(mail=*"+mail+"*)";
		
		String searchFilter = "(&(objectClass=user)(|"+ criterioNome+
														criterioCognome+
														criterioUser+
														criterioMail+
														criterioCustomNome+
														criterioCustomCognome+
														criterioCustomUser+
														criterioCustomMail+"))";
		
        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);
        List<Attributes> listAttr = new ArrayList<Attributes>();
        SearchResult searchResult = null;
        while(results.hasMoreElements()) {
            searchResult = results.nextElement();
            listAttr.add(searchResult.getAttributes());
            if(LOG.isInfoEnabled())LOG.info("searchResult: "+searchResult+"; attributes: "+searchResult.getAttributes()+";");
        }
        return listAttr;
	}
	
	private SearchResult findByNominativo(String nome, String cognome) throws NamingException {
		String searchFilter = "(&(objectClass=user)(givenName="+nome+")(sn="+cognome+"))";
        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);
        SearchResult searchResult = null;
        if(results.hasMoreElements()) {//anche se sono più di uno non m'interessa prendo il 1°
             searchResult = results.nextElement();
        }
        return searchResult;
	}
	private SearchResult findByMail(String email) throws NamingException {
		String searchFilter = "(&(objectClass=user)(mail=" + email + "))";
        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);
        SearchResult searchResult = null;
        if(results.hasMoreElements()) {//anche se sono più di uno non m'interessa prendo il 1°
             searchResult = results.nextElement();
        }
        return searchResult;
	}
	private SearchResult findByUserName(String accountName) throws NamingException {
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";
        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);
        SearchResult searchResult = null;
        if(results.hasMoreElements()) {//anche se sono più di uno non m'interessa prendo il 1°
             searchResult = results.nextElement();
        }
        return searchResult;
    }
    
	
	public static void associaUser(UserAssoc ua, String ldapName) throws Exception{
		AmAnagrafica a = ua.getAnag();
		Connection con = null;
		Statement st = null;		
		try {
			con = BaseAction.apriConnessione();
			con.setAutoCommit(false);
			st = con.createStatement();
			LdapFacade.disableConstraint(st);
			LdapFacade.sincronizzaDB(st, a, a.getAmUser().getName(), ldapName);
			ua.setMapped(true);
			LdapFacade.enableConstraint(st);
			con.commit();
		} catch (Exception e) {
			BaseAction.rollback(con);
			throw e;
		}finally{
			BaseAction.chiudiConnessione(con, st);
		}
	}
	public static void associaUser(List<UserAssoc> assocList) throws Exception{
		Connection con = null;
		Statement st = null;		
		try {
			con = BaseAction.apriConnessione();
			con.setAutoCommit(false);
			st = con.createStatement();
			LdapFacade.disableConstraint(st);
			for(UserAssoc ua : assocList){
				LdapFacade.sincronizzaDB(st, ua.getAnag(), ua.getGitUserName(), ua.getLdapu().getPrincipal());
				ua.setMapped(true);
				if(LOG.isInfoEnabled())LOG.info("Updatato DB per user git: "+ua.getGitUserName());
			}
			LdapFacade.enableConstraint(st);
			con.commit();
		} catch (Exception e) {
			BaseAction.rollback(con);
			throw e;
		}finally{
			BaseAction.chiudiConnessione(con, st);
		}
	}
	
	protected static void sincronizzaDB(Statement st, AmAnagrafica a, String gitUser, String ldapName) throws SQLException{
		a.getAmUser().setName(ldapName);
		st.executeUpdate("UPDATE AM_ANAGRAFICA SET FK_AM_USER = '"+ldapName+"' WHERE ID = "+a.getId());
		if(LOG.isInfoEnabled())LOG.info("Update AM_ANAGRAFICA FK_AM_USER");
		st.executeUpdate("UPDATE AM_USER SET LDAP_NAME = '"+ldapName+"',NAME = '"+ldapName+"',OLD_NAME = '"+gitUser+"' WHERE NAME = '"+gitUser+"'");
		if(LOG.isInfoEnabled())LOG.info("Update AM_USER LDAP_NAME,NAME,OLD_NAME");
		st.executeUpdate("UPDATE am_user_air SET fk_am_user = '"+ldapName+"' WHERE fk_am_user = '"+gitUser+"'");
		if(LOG.isInfoEnabled())LOG.info("Update am_user_air fk_am_user");
		st.executeUpdate("UPDATE am_audit SET user_id = '"+ldapName+"' WHERE user_id = '"+gitUser+"'");
		if(LOG.isInfoEnabled())LOG.info("Update am_audit user_id");
		st.executeUpdate("UPDATE am_traccia_accessi SET user_name = '"+ldapName+"' WHERE user_name = '"+gitUser+"'");
		if(LOG.isInfoEnabled())LOG.info("Update am_traccia_accessi user_name");
		st.executeUpdate("UPDATE am_user_group SET fk_am_user = '"+ldapName+"' WHERE fk_am_user = '"+gitUser+"'");
		if(LOG.isInfoEnabled())LOG.info("Update am_user_group fk_am_user");
		st.executeUpdate("UPDATE am_user_ufficio SET fk_am_user = '"+ldapName+"' WHERE fk_am_user = '"+gitUser+"'");
		if(LOG.isInfoEnabled())LOG.info("Update am_user_ufficio fk_am_user");
//		st.executeUpdate("UPDATE DBTOTALE_F704_GIT.SIT_LOG_ACCESSI SET UTENTE = '"+ldapName+"' WHERE UTENTE = '"+gitUser+"'");
//		if(LOG.isInfoEnabled())LOG.info("Update DBTOTALE_F704_GIT.SIT_LOG_ACCESS UTENTE");//TODO: capire se è essenziale aggiornarlo, perchè la tabella è molto vecchia e raggiungerla complica il tutto dato che il nome dello schema cambia da installazione a installazione
	}
	
	protected static void disableConstraint(Statement st) throws SQLException{
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI disable constraint CHK_TA_TIME"           );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_TRACCIA_ACCESSI CHK_TA_TIME");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI disable constraint CHK_TA_PRATICA"        );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_TRACCIA_ACCESSI CHK_TA_PRATICA");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI disable constraint CHK_TA_RAGIONE"        );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_TRACCIA_ACCESSI CHK_TA_RAGIONE");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI disable constraint CHK_TA_UNAME"          );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_TRACCIA_ACCESSI CHK_TA_UNAME");
//			st.executeUpdate("alter table AM_AUDIT_DECODE disable constraint SYS_C0024054"             );//nel DB di produzione a Milano questi vincoli non ci sono Filippo Mazzini dice che non ricorda ma comanda la produzione
//			st.executeUpdate("alter table AM_AUDIT_DECODE disable constraint SYS_C0024053"             );//nel DB di produzione a Milano questi vincoli non ci sono Filippo Mazzini dice che non ricorda ma comanda la produzione
		st.executeUpdate("alter table AM_USER_UFFICIO disable constraint FK_USER_UFFICIO_USER"     );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_USER_UFFICIO FK_USER_UFFICIO_USER");
		st.executeUpdate("alter table AM_USER_GROUP disable constraint FK_AM_USER_GROUP_AM_USER"   );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_USER_GROUP FK_AM_USER_GROUP_AM_USER");
		st.executeUpdate("alter table AM_USER_AIR disable constraint FK_AM_USER_AIR_AM_USER"       );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_USER_AIR FK_AM_USER_AIR_AM_USER");
		st.executeUpdate("alter table AM_ANAGRAFICA disable constraint FK_AM_ANAGRAFICA_AM_USER"   );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_ANAGRAFICA FK_AM_ANAGRAFICA_AM_USER");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI disable constraint AM_TRACCIA_ACCESSI_R01");
		if(LOG.isInfoEnabled())LOG.info("Disable AM_TRACCIA_ACCESSI AM_TRACCIA_ACCESSI_R01");
		st.executeUpdate("alter table AM_USER_GROUP disable constraint FK_AM_USER_GROUP_AM_GROUP"  );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_USER_GROUP FK_AM_USER_GROUP_AM_GROUP");
		st.executeUpdate("alter table AM_USER_AIR disable constraint FK_AM_USER_AIR_AM_COMUNE"     );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_USER_AIR FK_AM_USER_AIR_AM_COMUNE");
		st.executeUpdate("alter table AM_USER_AIR disable constraint FK_AM_USER_AIR_AM_AI_ROLE"    );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_USER_AIR FK_AM_USER_AIR_AM_AI_ROLE");
		st.executeUpdate("alter table AM_ANAGRAFICA disable constraint PK_AM_ANAGRAFICA"           );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_ANAGRAFICA PK_AM_ANAGRAFICA");
		st.executeUpdate("alter table AM_AUDIT disable constraint PK_AM_AUDIT"                     );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_AUDIT PK_AM_AUDIT");
		st.executeUpdate("alter table AM_AUDIT_DECODE disable constraint AM_AUDIT_DECODE_PK"       );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_AUDIT_DECODE AM_AUDIT_DECODE_PK");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI disable constraint AM_TRACCIA_ACCESSI_PK" );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_TRACCIA_ACCESSI AM_TRACCIA_ACCESSI_PK");
		st.executeUpdate("alter table AM_USER disable constraint PK_AM_USER"                       );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_USER PK_AM_USER");
		st.executeUpdate("alter table AM_USER_UFFICIO disable constraint PK_AM_USER_UFFICIO"       );
		if(LOG.isInfoEnabled())LOG.info("Disable AM_USER_UFFICIO PK_AM_USER_UFFICIO");
	}
	protected static void enableConstraint(Statement st) throws SQLException{
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI enable constraint CHK_TA_TIME"           );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_TRACCIA_ACCESSI CHK_TA_TIME");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI enable constraint CHK_TA_PRATICA"        );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_TRACCIA_ACCESSI CHK_TA_PRATICA");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI enable constraint CHK_TA_RAGIONE"        );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_TRACCIA_ACCESSI CHK_TA_RAGIONE");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI enable constraint CHK_TA_UNAME"          );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_TRACCIA_ACCESSI CHK_TA_UNAME");
//			st.executeUpdate("alter table AM_AUDIT_DECODE enable constraint SYS_C0024054"             );//nel DB di produzione a Milano questi vincoli non ci sono Filippo Mazzini dice che non ricorda ma comanda la produzione
//			st.executeUpdate("alter table AM_AUDIT_DECODE enable constraint SYS_C0024053"             );//nel DB di produzione a Milano questi vincoli non ci sono Filippo Mazzini dice che non ricorda ma comanda la produzione
		st.executeUpdate("alter table AM_USER enable constraint PK_AM_USER"                       );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_USER PK_AM_USER");
		st.executeUpdate("alter table AM_AUDIT enable constraint PK_AM_AUDIT"                     );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_AUDIT PK_AM_AUDIT");
		st.executeUpdate("alter table AM_ANAGRAFICA enable constraint PK_AM_ANAGRAFICA"           );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_ANAGRAFICA PK_AM_ANAGRAFICA");
		st.executeUpdate("alter table AM_USER_UFFICIO enable constraint PK_AM_USER_UFFICIO"       );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_USER_UFFICIO PK_AM_USER_UFFICIO");
		st.executeUpdate("alter table AM_USER_UFFICIO enable constraint FK_USER_UFFICIO_USER"     );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_USER_UFFICIO FK_USER_UFFICIO_USER");
		st.executeUpdate("alter table AM_USER_GROUP enable constraint FK_AM_USER_GROUP_AM_USER"   );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_USER_GROUP FK_AM_USER_GROUP_AM_USER");
		st.executeUpdate("alter table AM_USER_AIR enable constraint FK_AM_USER_AIR_AM_USER"       );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_USER_AIR FK_AM_USER_AIR_AM_USER");
		st.executeUpdate("alter table AM_ANAGRAFICA enable constraint FK_AM_ANAGRAFICA_AM_USER"   );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_ANAGRAFICA FK_AM_ANAGRAFICA_AM_USER");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI enable constraint AM_TRACCIA_ACCESSI_R01");
		if(LOG.isInfoEnabled())LOG.info("Enable AM_TRACCIA_ACCESSI AM_TRACCIA_ACCESSI_R01");
		st.executeUpdate("alter table AM_USER_GROUP enable constraint FK_AM_USER_GROUP_AM_GROUP"  );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_USER_GROUP FK_AM_USER_GROUP_AM_GROUP");
		st.executeUpdate("alter table AM_USER_AIR enable constraint FK_AM_USER_AIR_AM_COMUNE"     );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_USER_AIR FK_AM_USER_AIR_AM_COMUNE");
		st.executeUpdate("alter table AM_USER_AIR enable constraint FK_AM_USER_AIR_AM_AI_ROLE"    );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_USER_AIR FK_AM_USER_AIR_AM_AI_ROLE");
		st.executeUpdate("alter table AM_AUDIT_DECODE enable constraint AM_AUDIT_DECODE_PK"       );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_AUDIT_DECODE AM_AUDIT_DECODE_PK");
		st.executeUpdate("alter table AM_TRACCIA_ACCESSI enable constraint AM_TRACCIA_ACCESSI_PK" );
		if(LOG.isInfoEnabled())LOG.info("Enable AM_TRACCIA_ACCESSI AM_TRACCIA_ACCESSI_PK");
	}
	


}