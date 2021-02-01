package it.webred.amprofiler.ejb.perm;


import it.webred.amprofiler.ejb.AmProfilerBaseService;
import it.webred.amprofiler.ejb.dto.PermessiDTO;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmGroup;
import it.webred.amprofiler.model.AmUser;
import it.webred.amprofiler.model.perm.Ente;
import it.webred.amprofiler.model.perm.PermAccesso;
import it.webred.amprofiler.model.perm.PermEnte;
import it.webred.amprofiler.model.perm.Permission;
import it.webred.amprofiler.model.perm.PwdUpdDate;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;
import it.webred.utilities.CryptoroUtils;
import it.webred.utils.GenericTuples;
import it.webred.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.util.DigestUtils;

/**
 * Session Bean implementation class LoginBean
 */
@Stateless
public class LoginBean extends AmProfilerBaseService implements LoginBeanService {

	@EJB
	UserService userService;
	


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
	
	public boolean isPwdValida(String username, int numGiorni)
	{
		boolean pwdValida = false;

		try {
			Query q = em.createNamedQuery("Login.isPwdValida");
			q.setParameter("username", username);
			
			PwdUpdDate data = (PwdUpdDate) q.getSingleResult();
					
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
			
		} catch (Throwable t) {
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
	public CeTToken getToken(String username, String password, String ente) 
	throws LoginServiceException {
		CeTToken token = new CeTToken();
		
		try {
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
			
			boolean pwdEqual = md5pwd.equals(pwd);

			// il sessionid nel token ce lo mette l'interceptor!
			// non questo metodo!
			token.setEnte(ente);
			
			return token;
		}
		catch(Throwable t) {
			throw new LoginServiceException(t);
		}
	}
	
	


}
