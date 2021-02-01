package it.webred.amprofiler.ejb.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.webred.amprofiler.ejb.AmProfilerBaseService;
import it.webred.amprofiler.model.AmGroup;
import it.webred.amprofiler.model.AmUser;
import it.webred.amprofiler.model.AmUserGroup;
import it.webred.amprofiler.model.AmUserGroupPK;
import it.webred.amprofiler.model.AmUserUfficio;
import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaSearchCriteria;
import it.webred.amprofiler.ejb.anagrafica.dto.QueryBuilder;
//import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.wsClient.*;

import javax.ejb.Stateless;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.persistence.Query;

@Stateless
public class UserServiceBean extends AmProfilerBaseService implements
		UserService {

	
	@Override
	public AmUser getUserByName(String username) {

		AmUser user = null;

		try {

			Query q = em.createNamedQuery("AmUser.getUserByUsername");
			q.setParameter("username", username.toUpperCase());
			List<AmUser> userList = (List<AmUser>) q.getResultList();
			if (userList.size() > 0) {
				user = userList.get(0);
				user.getAmGroups().size();
			}

		} catch (Throwable t) {
			throw new UserServiceException(t);
		}

		return user;
	}

	
	@Override
	public List<AmUser> getUsersByEnteInizialiGruppo(String belfiore, String gruppo) {
		List<AmUser> users = new ArrayList<AmUser>();

		try {
			Query q = em.createNamedQuery("AmUser.getUsersByEnteInizialiGruppo");
			q.setParameter("ente", belfiore);
			q.setParameter("gruppo", gruppo + '%');

			users = q.getResultList();

		} catch (Throwable t) {
			throw new UserServiceException(t);
		}

		return users;
	}
	
	
	@Override
	public List<AmUser> getUsersByEnte(String belfiore) {
		List<AmUser> users = new ArrayList<AmUser>();

		try {
			Query q = em.createNamedQuery("AmUser.getUsersByEnte");
			q.setParameter("ente", belfiore);

			users = q.getResultList();

		} catch (Throwable t) {
			throw new UserServiceException(t);
		}

		return users;
	}

	@Override
	public List<AmUser> findAmUser(String amApplicationName,
			List<String> gruppi, AnagraficaSearchCriteria criteria,
			int fromIndex, int maxResults) {

		String sql = "select u "
				+ "from VComuniUtentiInstance v, AmUser u, "
				+ (gruppi != null && gruppi.size() > 0 ? " AmGroup g, AmUserGroup ug, "
						: "")
				+ "AmAnagrafica anag "
				+ "where v.id.fkAmApplication = '"
				+ amApplicationName
				+ "' "
				+ (gruppi != null && gruppi.size() > 0 ? "and v.id.fkAmComune = g.fkAmComune and g.name = ug.id.fkAmGroup and ug.id.fkAmUser = u.name "
						: "")
				+ "and v.id.fkAmUser = u.name and anag.amUser.name = v.id.fkAmUser ";
		if (gruppi != null && gruppi.size() > 0)
			sql += "and g.name in (:gruppi) ";

		sql += new QueryBuilder(criteria).getSQLCriteria();
		Query q = em.createQuery(sql);

		if (gruppi != null && gruppi.size() > 0)
			q.setParameter("gruppi", gruppi);

		q.setFirstResult(fromIndex);
		q.setMaxResults(maxResults).getResultList();

		return q.getResultList();
	}

	@Override
	public boolean createUser(AmUser user) {

		boolean esito = false;

		try {

			user.setDtIns(new Date());
			user.setDtUpdPwd(new Date());
			em.persist(user);
			esito = true;

		} catch (Throwable t) {
			throw new UserServiceException(t);
		}
		return esito;
	}

	@Override
	public boolean updateUser(AmUser user) {

		boolean esito = false;

		try {

			em.merge(user);
			esito = true;

		} catch (Throwable t) {
			throw new UserServiceException(t);
		}
		return esito;
	}

	@Override
	public boolean deleteUser(String username) {

		boolean esito = false;

		try {

			Query q = em.createNamedQuery("AmAnagrafica.deleteUserByUserName");
			q.setParameter("username", username);
			q.executeUpdate();
			
			q = em.createNamedQuery("AmUser.deleteUserByUserName");
			q.setParameter("username", username);
			q.executeUpdate();
			esito = true;

		} catch (Throwable t) {
			throw new UserServiceException(t);
		}
		return esito;
	}

	public boolean deleteUserByIstance(String username, String istance, String comune) {

		boolean esito = false;

		try {
			// controllo se l'utente appartiene solo all'istanza indicata, in
			// caso positivo lo elimino
			String sql = "select DISTINCT ISTANZA "
					+ "from ( "
					+ "select distinct air.ID ID_AIR,  i.NAME ISTANZA  , AI.FK_AM_ITEM,  u.NAME uname, u.DISABLE_CAUSE udiscause, ic.FK_AM_COMUNE comune, a.FLG_DATA_ROUTER dr "
					+ "from AM_AI_ROLE air "
					+ "LEFT JOIN AM_USER_AIR uair on uair.FK_AM_AI_ROLE=air.ID  "
					+ "LEFT JOIN AM_USER u on uair.FK_AM_USER=u.NAME "
					+ "LEFT JOIN AM_INSTANCE_COMUNE ic1 on uair.fk_am_comune = ic1.fk_am_comune "
					+ "LEFT JOIN AM_APPLICATION_ITEM ai ON air.FK_AM_APPLICATION_ITEM = ai.ID "
					+ "LEFT JOIN AM_APPLICATION a ON ai.FK_AM_APPLICATION = a.NAME "
					+ "LEFT JOIN AM_INSTANCE i ON ai.FK_AM_APPLICATION = i.FK_AM_APPLICATION "
					+ "LEFT JOIN AM_INSTANCE_COMUNE ic ON i.NAME = ic.FK_AM_INSTANCE "
					+ "where (ic1.fk_am_comune = ic.fk_am_comune "
					+ "and ic1.fk_am_instance = ic.fk_am_instance) "
					+ "UNION "
					+ "select distinct air.ID ID_AIR, i.NAME ISTANZA , AI.FK_AM_ITEM,  u.NAME uname, u.DISABLE_CAUSE udiscause, ic.FK_AM_COMUNE comune, a.FLG_DATA_ROUTER dr "
					+ "from AM_AI_ROLE air "
					+ "LEFT JOIN AM_GROUP_AIR gair on gair.FK_AM_AI_ROLE=air.ID "
					+ "LEFT JOIN AM_GROUP g on gair.FK_AM_GROUP=g.NAME "
					+ "LEFT JOIN AM_USER_GROUP ug on ug.FK_AM_GROUP=g.NAME "
					+ "LEFT JOIN AM_USER u on ug.FK_AM_USER=u.NAME "
					+ "LEFT JOIN AM_APPLICATION_ITEM ai ON air.FK_AM_APPLICATION_ITEM = ai.ID "
					+ "LEFT JOIN AM_APPLICATION a ON ai.FK_AM_APPLICATION = a.NAME "
					+ "LEFT JOIN AM_INSTANCE i ON ai.FK_AM_APPLICATION = i.FK_AM_APPLICATION "
					+ "LEFT JOIN AM_INSTANCE_COMUNE ic ON i.NAME = ic.FK_AM_INSTANCE "
					+ ") sel , " + "AM_PERMISSION_AIR PAIR, AM_PERMISSION P "
					+ "where PAIR.FK_AM_AI_ROLE = sel.id_air "
					+ "and PAIR.FK_AM_PERMISSION = P.NAME "
					+ "AND P.FK_AM_ITEM =  SEL.FK_AM_ITEM "
					+ "and udiscause IS NULL "
					+ "and uname= '" + username + "' "
					+ "and (dr = 'N' or comune = '"+ comune +"')";
			Query q = em.createNativeQuery(sql);
			List<String> listaIstanze = q.getResultList();
			
			// cerco connessioni fra permessi dell'istanza e utente
			sql = "select distinct air.ID from AM_AI_ROLE air "
					+ "LEFT JOIN AM_USER_AIR uair on uair.FK_AM_AI_ROLE=air.ID  "
					+ "LEFT JOIN AM_USER u on uair.FK_AM_USER=u.NAME "
					+ "LEFT JOIN AM_INSTANCE_COMUNE ic1 on uair.fk_am_comune = ic1.fk_am_comune "
					+ "LEFT JOIN AM_APPLICATION_ITEM ai ON air.FK_AM_APPLICATION_ITEM = ai.ID "
					+ "LEFT JOIN AM_APPLICATION a ON ai.FK_AM_APPLICATION = a.NAME "
					+ "LEFT JOIN AM_INSTANCE i ON ai.FK_AM_APPLICATION = i.FK_AM_APPLICATION "
					+ "LEFT JOIN AM_INSTANCE_COMUNE ic ON i.NAME = ic.FK_AM_INSTANCE "
					+ "where (ic1.fk_am_comune = ic.fk_am_comune  "
					+ "and ic1.fk_am_instance = ic.fk_am_instance) "
					+ "and i.NAME = '" + istance + "' " + "and u.NAME = '"
					+ username + "'";
			q = em.createNativeQuery(sql);
			List<BigDecimal> listaAmAiRoleId = q.getResultList();

			if (listaAmAiRoleId != null && listaAmAiRoleId.size() > 0) {
				for (BigDecimal id : listaAmAiRoleId) {
					q = em.createNamedQuery("AmUser.deleteUserAirByAmAiRole");
					q.setParameter("id", id);
					q.executeUpdate();
				}
			}

			// cerco connessioni fra permessi dell'istanza e gruppo
			sql = "select distinct g.NAME "
					+ "from AM_AI_ROLE air "
					+ "LEFT JOIN AM_GROUP_AIR gair on gair.FK_AM_AI_ROLE=air.ID "
					+ "LEFT JOIN AM_GROUP g on gair.FK_AM_GROUP=g.NAME "
					+ "LEFT JOIN AM_USER_GROUP ug on ug.FK_AM_GROUP=g.NAME "
					+ "LEFT JOIN AM_USER u on ug.FK_AM_USER=u.NAME "
					+ "LEFT JOIN AM_APPLICATION_ITEM ai ON air.FK_AM_APPLICATION_ITEM = ai.ID "
					+ "LEFT JOIN AM_APPLICATION a ON ai.FK_AM_APPLICATION = a.NAME "
					+ "LEFT JOIN AM_INSTANCE i ON ai.FK_AM_APPLICATION = i.FK_AM_APPLICATION "
					+ "LEFT JOIN AM_INSTANCE_COMUNE ic ON i.NAME = ic.FK_AM_INSTANCE "
					+ "where i.NAME = '" + istance + "'" + "and u.NAME = '"
					+ username + "'";
			q = em.createNativeQuery(sql);
			List<String> listaGroup = q.getResultList();

			if (listaGroup != null && listaGroup.size() > 0) {
				for (String group : listaGroup) {
					q = em.createNamedQuery("AmUser.deleteUserGroupByGroup");
					q.setParameter("group", group);
					q.setParameter("user", username);
					q.executeUpdate();
				}
			}

			if (listaIstanze != null && listaIstanze.size() == 1 && listaIstanze.get(0).equals(istance)) {
				deleteUser(username);
			}
			
			esito = true;

		} catch (Throwable t) {
			throw new UserServiceException(t);
		}
		return esito;
	}

	@Override
//	@AuditConsentiAccessoAnonimo(enabled=true)
	public AmUserUfficio getDatiUfficio(String username) {
		AmUserUfficio uff = new AmUserUfficio();
		Query q;
		try{
			q = em.createNamedQuery("AmUserUfficio.getUfficioByUser");
			q.setParameter("user", username);
			List<AmUserUfficio> lista = q.getResultList();
			
			if(lista.size()>0)
				uff = lista.get(0);
			
		} catch (Throwable t) {
			throw new UserServiceException(t);
		}
		
		return uff;
		
	}

	@Override
	public boolean saveDatiUfficio(AmUserUfficio dati) {
		boolean saved = false;
		try{
			AmUserUfficio ufficioOld = this.getDatiUfficio(dati.getFkAmUser());
			if(ufficioOld.getFkAmUser()!=null){
				dati.setDtIns(ufficioOld.getDtIns());
				dati.setDtUpdate(new Date());
				em.merge(dati);
			}else{
				dati.setDtIns(new Date());
				em.persist(dati);
			}
			
			saved =  true;
			
		} catch (Throwable t) {
			throw new UserServiceException(t);
		}
		return saved;
	}
	
	public void creaGruppo(String nomeGruppo, String ente){
		AmGroup gruppo = new AmGroup();
		gruppo.setName(nomeGruppo);
		gruppo.setFkAmComune(ente);
		em.persist(gruppo);
		logger.debug("Creato Gruppo: "+nomeGruppo);
	}
	
	
	@Override
	public boolean verificaAggiornaAvvocato(String username, String ente) {
		boolean isAvvocato = false;
		String nomeGruppo = "AVVOCATI_"+ente.toUpperCase();
		
		AmUserGroupPK pk = new AmUserGroupPK();
		pk.setFkAmGroup(nomeGruppo);
		pk.setFkAmUser(username);
		
		AmUserGroup ug = null;
		
		try {
					
			AmGroup gruppo = em.find(AmGroup.class, nomeGruppo);
			if(gruppo==null)
				this.creaGruppo(nomeGruppo, ente);
			
			if(em.find(AmGroup.class, nomeGruppo)!=null){
				ug = em.find(AmUserGroup.class, pk);
				isAvvocato = WsAvvocaturaClient.isAvvocato(username);
				
				//Se esiste nel gruppo e non è avvocato lo rimuovo
				if(ug!=null && !isAvvocato){
					em.remove(ug);
					logger.debug("Rimosso Avvocato "+username+" dal gruppo "+nomeGruppo);
				}else if(ug==null && isAvvocato){
					ug = new AmUserGroup();
					ug.setId(pk);
					em.persist(ug);
					logger.debug("Inserito Avvocato "+username+" nel gruppo "+nomeGruppo);
				}
			}else
				logger.warn("Gruppo "+nomeGruppo+" non creato");
			
		} catch(WsAvvocaturaException ws){
			logger.error(ws.getMessage()+" Soggetto:"+username);
			if(ug!=null){
				em.remove(ug);
				logger.debug("Rimosso Avvocato "+username+" dal gruppo "+nomeGruppo);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new UserServiceException(e);
		}
		
		return isAvvocato;
	}

	@Override
	public int verificaGruppoAvvocati(String ente) {
		String nomeGruppo = "AVVOCATI_"+ente.toUpperCase();
		int rimossi = 0;
		int numErrori = 0;
		try{
			
			Query q = em.createNamedQuery("AmUserGroup.getListaUtenti");
			q.setParameter("group", nomeGruppo);
			
			List<AmUserGroup> utenti = q.getResultList();
			for(AmUserGroup ug : utenti){
				AmUserGroupPK pk = ug.getId();
				String cf = pk.getFkAmUser();
				
				boolean isAvvocato = false;
				try{
					isAvvocato = WsAvvocaturaClient.isAvvocato(cf);
					
					if(!isAvvocato){
						em.remove(ug);
						logger.debug("Rimosso Avvocato "+cf+" dal gruppo "+nomeGruppo);
						rimossi++;
					}
					
				} catch(WsAvvocaturaException ws){
					logger.error(ws.getMessage()+" Soggetto:"+cf);
					numErrori++;
				}
				
				if(numErrori>10)
					throw new UserServiceException("Controllo AM_USER_GROUP su "+nomeGruppo+" interrotto: troppi errori di connessione al WebService");
				
			}
			
		} catch (Exception e) {
			throw new UserServiceException(e);
		}
		
		return rimossi;
	}
	
	
	@Override
	public List<AmGroup> getGruppiUtente(String uname) {
		List<AmGroup> lista = new ArrayList<AmGroup>();
		Query q;
		try{
			q = em.createNamedQuery("AmUserGroup.getGruppiUtente");
			q.setParameter("username", uname);
			lista = q.getResultList();
			
		} catch (Throwable t) {
			throw new UserServiceException(t);
		}
		
		return lista;
	}
	
	@Override
	public AmGroup getGroupByName(String name) {
		AmGroup group = null;
		Query q;
		try{
			q = em.createNamedQuery("AmGroup.getGroupByName");
			q.setParameter("name", name);
			List<AmGroup> lista = q.getResultList();
			
			if(lista.size()>0)
				group = lista.get(0);
			
		} catch (Throwable t) {
			throw new UserServiceException(t);
		}
		
		return group;
	}
	//
	@Override
	public List<Object[]> getUtentiByApplicazioneEnte(String applicazione, String ente){
		String sql="select au.name,au.dt_ins, au.dt_upd_pwd, ap.fk_am_application applicazione,"+
				" ap.fk_am_item contesto, air.fk_am_role ruolo, apa.FK_AM_PERMISSION permesso"+
				" from am_user au"+
				" left join am_user_air aua"+
				" on au.name=aua.fk_am_user"+
				" left join am_ai_role air"+
				" on aua.fk_am_ai_role=air.id"+
				" left join am_application_item ap"+
				" on AIR.FK_AM_APPLICATION_ITEM=AP.id"+
				" left join am_permission_air apa on apa.fk_am_ai_role = air.id"+
				" where aua.fk_am_comune='"+ente+"'"+
				" and AP.FK_AM_APPLICATION ='"+applicazione+"'"+
				" order by name";
		try {
			
			return em.createNativeQuery(sql).getResultList();
		} catch (Throwable t) {
			throw new UserServiceException(t);
		}
		
	}
	
	
}
