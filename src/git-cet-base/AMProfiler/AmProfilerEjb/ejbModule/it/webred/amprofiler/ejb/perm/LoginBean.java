package it.webred.amprofiler.ejb.perm;


import it.webred.amprofiler.ejb.AmProfilerBaseService;
import it.webred.amprofiler.ejb.dto.AmBaseDTO;
import it.webred.amprofiler.ejb.dto.PermessiDTO;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmGroup;
import it.webred.amprofiler.model.AmTracciaAccessi;
import it.webred.amprofiler.model.AmUser;
import it.webred.amprofiler.model.perm.Ente;
import it.webred.amprofiler.model.perm.PermAccesso;
import it.webred.amprofiler.model.perm.PermEnte;
import it.webred.amprofiler.model.perm.Permission;
import it.webred.ct.config.audit.AuditService;
import it.webred.ct.config.model.AmAudit;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;
import it.webred.utilities.CryptoroUtils;
import it.webred.utils.GenericTuples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Session Bean implementation class LoginBean
 */
@Stateless
public class LoginBean extends AmProfilerBaseService implements LoginBeanService {

	@EJB
	UserService userService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/AuditServiceBean")
	AuditService auditService;

	@AuditConsentiAccessoAnonimo
	public HashMap getPermissions(String username, String ente) {
		HashMap<String, String> result = new HashMap<String, String>();
		
		try {
			//System.out.println("Get Permission");
			Query q = em.createNamedQuery("Login.getPermission");
			//System.out.println("Query q["+q+"]");
			q.setParameter("username", username);
			q.setParameter("ente", ente);
			
			List<Permission> tmp = q.getResultList();
			
			
			if (tmp != null) {
				for (int i=0; i < tmp.size(); i++) {
					Permission p = tmp.get(i);
					result.put(p.getPermission(), p.getPermission());
				}
			}
		}
		catch(Throwable t) {
			throw new LoginServiceException(t);
		}
		
		return result;
	}
	
	public HashMap getPermissionsByAmInstanceComune(PermessiDTO dto) {
		HashMap<String, String> result = new HashMap<String, String>();
		

		
		return result;
	}
	
	@AuditConsentiAccessoAnonimo
	public HashMap getPermissionsByGroup(String gruppo, String ente) {
		HashMap<String, String> result = new HashMap<String, String>();
		
		try {
			//System.out.println("Get Permission");
			Query q = em.createNamedQuery("Login.getPermissionByGroup");
			//System.out.println("Query q["+q+"]");
			q.setParameter("gruppo", gruppo);
			q.setParameter("ente", ente);
			
			List<Permission> tmp = q.getResultList();
			
			
			if (tmp != null) {
				for (int i=0; i < tmp.size(); i++) {
					Permission p = tmp.get(i);
					result.put(p.getPermission(), p.getPermission());
				}
			}
		}
		catch(Throwable t) {
			throw new LoginServiceException(t);
		}
		
		return result;
	}

	@AuditConsentiAccessoAnonimo
	public PermAccesso getPermissionAccesso(String username, String ente){
		PermAccesso result = new PermAccesso();
		
		try {
			Query q = em.createNamedQuery("Login.getPermissionAccessoUtente");
			q.setParameter("username", username);
			
			List<PermAccesso> tmp = q.getResultList();
			
			if (tmp != null && tmp.size()>0 && tmp.get(0) != null) {
				result = tmp.get(0);
			}else{
				q = em.createNamedQuery("Login.getPermissionAccessoGruppo");
				q.setParameter("username", username);
				q.setParameter("ente", ente);
				
				tmp = q.getResultList();
				
				if (tmp != null && tmp.size()>0 && tmp.get(0) != null){ 
					result = tmp.get(0);
				}
			}
		}
		catch(Throwable t) {
			throw new LoginServiceException(t);
		}
		
		return result;
	}
	
	public Boolean isPwdValida(String username, int durata)
	{
		Boolean pwdValida = Boolean.FALSE;

		try {
			Query q = em.createNamedQuery("Login.isPwdValida");
			q.setParameter("username", username);
			q.setParameter("durata", new Double(durata));
			
			Integer val = (Integer) q.getSingleResult();
			
			pwdValida = val!=null && val.intValue()==1;
			
			/*PwdUpdDate data = (PwdUpdDate) q.getSingleResult();
			
			Date dtUpdPwd = new Date(data.getData().getTime());
			Calendar calUpdPwd = Calendar.getInstance();
			calUpdPwd.setTime(dtUpdPwd);
			calUpdPwd.add(Calendar.DAY_OF_YEAR, numGiorni);
			Calendar calOggi = Calendar.getInstance();
			calOggi.set(Calendar.HOUR_OF_DAY, 0);
			calOggi.set(Calendar.MINUTE, 0);
			calOggi.set(Calendar.SECOND, 0);
			calOggi.set(Calendar.MILLISECOND, 0);
			pwdValida = new Boolean(calOggi.getTime().getTime() <= calUpdPwd.getTime().getTime());
		*/
		}catch(NoResultException nre){
			logger.warn("Password scaduta per l'utente: "+username);
		}catch (Throwable t) {
			throw new LoginServiceException(t);
		}
		
		return pwdValida;
	}
	
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List getEnte(String username) {
		List<String> result = new ArrayList<String>();
		
		try {
			Query q = em.createNamedQuery("Login.getEnte");
			q.setParameter("username", username);
			List<PermEnte> tmp = q.getResultList();
			if (tmp != null) {
				for (int i=0; i < tmp.size(); i++) {
					PermEnte ep = tmp.get(i);
					if(ep != null) {
						result.add(ep.getEnte());	
					}
				}
			}
		}
		catch(Throwable t) {
			throw new LoginServiceException(t);
		}
		
		return result;
	}
	
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List getEntiByUtente(String username) {
		List<GenericTuples.T2<String, String>> result = new ArrayList<GenericTuples.T2<String, String>>();
		
		try {
			Query q = em.createNamedQuery("Login.getEntiByUtente");
			q.setParameter("username", username);
			List<Ente> tmp = q.getResultList();
			if (tmp != null) {
				for (int i=0; i < tmp.size(); i++) {
					Ente e = tmp.get(i);
					if(e != null) {
						result.add(new GenericTuples().t2(e.getEnte(), e.getDescrizione()));
						
					}
				}
			}
		}
		catch(Throwable t) {
			throw new LoginServiceException(t);
		}
		
		return result;
	}
	
	@AuditConsentiAccessoAnonimo
	public List<AmGroup> getGruppi(String username, String ente) {
		
		try {
			Query q = em.createNamedQuery("Login.getGruppi");
			q.setParameter("username", username);
			q.setParameter("ente", ente);
			return q.getResultList();
		}
		catch(Throwable t) {
			throw new LoginServiceException(t);
		}
	}
	
	@AuditConsentiAccessoAnonimo
	public boolean appartieneAGruppoLike(String username, String ente, String descGruppo) {
		boolean appartiene = false;
		try {
			Query q = em.createNamedQuery("Login.appartieneAGruppo");
			q.setParameter("username", username);
			q.setParameter("ente", ente);
			q.setParameter("descGruppo", descGruppo+"%");
			List<AmGroup> lst =  q.getResultList();
			appartiene = lst!=null &&  !lst.isEmpty();
		}
		catch(Throwable t) {
			throw new LoginServiceException(t);
		}
		return appartiene;
	}
	
	@AuditConsentiAccessoAnonimo
	public CeTToken getToken(String username, String password, String ente) 
	throws LoginServiceException {
		CeTToken token = new CeTToken();
		
		
			AmUser user=null;
			Query q = em.createNamedQuery("AmUser.getUserByUsername");
			q.setParameter("username", username.toUpperCase());
			List<AmUser> userList = (List<AmUser>) q.getResultList();
			if (userList.size() > 0) {
				user = userList.get(0);
				user.getAmGroups().size();
			}
			
			if (user==null) 
				throw new LoginServiceException("Utente " + username + " sconosciuto" );
			if (password==null) 
				throw new LoginServiceException("password non valida" );

			
			String dc = user.getDisableCause();
			if (dc!=null)
				throw new LoginServiceException("Utente " + username + " disabilitato" );
				
			//todo: il controllo sulla coppia utente - ente
			
    		boolean pwdValida = isPwdValida(username, 90); 
			
			String pwd = user.getPwd();
			String md5pwd = CryptoroUtils.getMD5Pwd(password);
			//da verificare il perchè di questa anomalia..
			if( pwd.length() - md5pwd.length() == 1) {
				md5pwd = "0".concat(md5pwd);
			}
			 
			boolean pwdEqual = md5pwd.equals(pwd);
			if(pwdEqual) {
				token.setEnte(ente);
			}else {
				throw new LoginServiceException("Login/Password non valide !" );
			}
			// il sessionid nel token ce lo mette l'interceptor!
			// non questo metodo!
			
			
			return token;
		
	}

	@Override
	@AuditConsentiAccessoAnonimo
	public void salvaTracciaAccessi(AmBaseDTO dto) {
		try{
			AmTracciaAccessi traccia = (AmTracciaAccessi)dto.getObj1();
		    if(traccia.getId()==null){
		    	String sql = "SELECT NVL(MAX(ID),0) FROM AM_TRACCIA_ACCESSI";
		    	Query q = em.createNativeQuery(sql);
		    	List<BigDecimal> ids = (List<BigDecimal>) q.getResultList();
		    	if(!ids.isEmpty()) traccia.setId(ids.get(0).add(BigDecimal.ONE));
		    	else traccia.setId(BigDecimal.ONE);
		    }
		    
		    traccia.setTimeAccesso(new Date());
			em.persist(traccia);
		}
		catch(Throwable t) {
			logger.error(t.getMessage());
			throw new LoginServiceException(t);
		}
	}

	@Override
	@AuditConsentiAccessoAnonimo
	public void aggiornaTracciaAccessiBySessionID(AmBaseDTO dto) {
		try{
			AmTracciaAccessi traccia = (AmTracciaAccessi)dto.getObj1();
			AmTracciaAccessi tracciaLocal = this.findTracciaAccessiBySessionId(traccia.getSessionId());
			if(tracciaLocal!=null && tracciaLocal.getId()!=null){
				if (traccia.getEnte() != null && !traccia.getEnte().equals("")) tracciaLocal.setEnte(traccia.getEnte());
				if (traccia.getPrik() != null && !traccia.getPrik().equals("")) tracciaLocal.setPrik(traccia.getPrik());
				if (traccia.getPubk() != null && !traccia.getPubk().equals("")) tracciaLocal.setPubk(traccia.getPubk());
				if (traccia.getUsata() != null && !traccia.getUsata().equals("")) tracciaLocal.setUsata(traccia.getUsata());
		    }
		    //per il momento la data di aggiornamento non deve essere tracciata
		    //traccia.setTimeAccesso(new Date());
			em.persist(tracciaLocal);
		}
		catch(Throwable t) {
			logger.error(t.getMessage());
			throw new LoginServiceException(t);
		}
	}
	
	
//	@Override
//	@AuditConsentiAccessoAnonimo
//	public void aggiornaTracciaAccessiByPubPk(AmBaseDTO dto) {
//		try{
//			AmTracciaAccessi traccia = (AmTracciaAccessi)dto.getObj1();
//			AmTracciaAccessi tracciaLocal = this.findTracciaAccessiByPublPk(traccia.getPubk());
//			if(tracciaLocal.getId()!=null){
//				if (traccia.getEnte() != null && !traccia.getEnte().equals("")) tracciaLocal.setEnte(traccia.getEnte());
//				if (traccia.getPrik() != null && !traccia.getPrik().equals("")) tracciaLocal.setPrik(traccia.getPrik());
//				if (traccia.getPubk() != null && !traccia.getPubk().equals("")) tracciaLocal.setPubk(traccia.getPubk());
//				if (traccia.getUsata() != null && !traccia.getUsata().equals("")) tracciaLocal.setUsata(traccia.getUsata());
//		    }
//		    //per il momento la data di aggiornamento non deve essere tracciata
//		    //traccia.setTimeAccesso(new Date());
//			em.persist(tracciaLocal);
//		}
//		catch(Throwable t) {
//			logger.error(t.getMessage());
//			throw new LoginServiceException(t);
//		}
//	}
	@Override
	@AuditConsentiAccessoAnonimo
	public void aggiornaTracciaAccessiByPriK(AmBaseDTO dto) {
		try{
			AmTracciaAccessi traccia = (AmTracciaAccessi)dto.getObj1();
			AmTracciaAccessi tracciaLocal = this.findTracciaAccessiByPriK(traccia.getPrik());
			if(tracciaLocal.getId()!=null){
				if (traccia.getEnte() != null && !traccia.getEnte().equals("")) tracciaLocal.setEnte(traccia.getEnte());
				if (traccia.getPrik() != null && !traccia.getPrik().equals("")) tracciaLocal.setPrik(traccia.getPrik());
				if (traccia.getPubk() != null && !traccia.getPubk().equals("")) tracciaLocal.setPubk(traccia.getPubk());
				if (traccia.getUsata() != null && !traccia.getUsata().equals("")) tracciaLocal.setUsata(traccia.getUsata());
		    }
		    //per il momento la data di aggiornamento non deve essere tracciata
		    //traccia.setTimeAccesso(new Date());
			em.persist(tracciaLocal);
		}
		catch(Throwable t) {
			logger.error(t.getMessage());
			throw new LoginServiceException(t);
		}
	}
	public AmTracciaAccessi findTracciaAccessiBySessionId(String sessionId) {
		try{
			Query q = em.createNamedQuery("Login.findTracciaAccessiBySessionID");
			q.setParameter("sessionId", sessionId);
			List<AmTracciaAccessi> lst = q.getResultList();
			if(!lst.isEmpty()) return lst.get(0);
		}
		catch(Throwable t) {
			logger.error(t.getMessage());
			throw new LoginServiceException(t);
		}
		return null;
	}
	@Override
	@AuditConsentiAccessoAnonimo
	public AmTracciaAccessi findTracciaAccessiByPriK(String priK) {
		try{
			Query q = em.createNamedQuery("Login.findTracciaAccessiByPriK");
			q.setParameter("prik", priK);
			List<AmTracciaAccessi> lst = q.getResultList();
			if(!lst.isEmpty()) return lst.get(0);
		}
		catch(Throwable t) {
			logger.error(t.getMessage());
			throw new LoginServiceException(t);
		}
		return null;
	}
	public List<AmTracciaAccessi> findTracciaAccessiByLastAccessUser(String userName) {
		List<AmTracciaAccessi> lst = null;
		try{
		    if(userName!=null){
//		    	String sql = " SELECT * FROM ( SELECT ROWNUM AS N, T.* FROM ( "
//		    			+ " SELECT * FROM AM_TRACCIA_ACCESSI WHERE USER_NAME = '" + userName + "' ORDER BY TIME_ACCESSO DESC "
//		    			+ " ) T ) WHERE N = 1 ";
		    	
		    	String sql = " SELECT * FROM ( "
		    			+ " SELECT a.*, ROW_NUMBER() OVER ( order by TIME_ACCESSO desc ) rn "
		    			+ " FROM AM_TRACCIA_ACCESSI a  where USER_NAME = :username "
		    			+ " ) WHERE rn BETWEEN 1 AND 1 ";
		    	
		    	//String sql = " SELECT * FROM AM_TRACCIA_ACCESSI where USER_NAME = '"+userName+"' ORDER BY TIME_ACCESSO DESC ";
		    	logger.info(sql);
		    	Query q = em.createNativeQuery(sql, AmTracciaAccessi.class);
		    	q.setParameter("username", userName);
		    	q.setMaxResults(1);
		    	lst = (List<AmTracciaAccessi>) q.getResultList();
		    }
		}
		catch(Throwable t) {
			logger.error(t.getMessage());
			throw new LoginServiceException(t);
		}
		return lst;
	}//-------------------------------------------------------------------------

//	@Override
//	@AuditConsentiAccessoAnonimo
//	public AmTracciaAccessi findTracciaAccessiByPubPk(String pubPk) {
//		 	AmTracciaAccessi t = findTracciaAccessiByPublPk(pubPk);
//		 	
//		return t;
//	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	public String findUtenteBySessionId(String sessionId) {
		String username = null;
		AmTracciaAccessi t = findTracciaAccessiBySessionId(sessionId);
		username = t!=null ? t.getUserName() : null;
		
		if(username==null){
			AmAudit audit = auditService.findAuditBySessionId(sessionId);
			username = audit!=null ? audit.getUserId() : null;
		}
		
		return username;
	}//-------------------------------------------------------------------------
	
}
