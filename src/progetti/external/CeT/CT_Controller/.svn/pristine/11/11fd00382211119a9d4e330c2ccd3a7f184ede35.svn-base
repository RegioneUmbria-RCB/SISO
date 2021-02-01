package it.webred.ct.controller.ejb.utilities;

import it.webred.ct.controller.ejbclient.utilities.ControllerUtilitiesService;
import it.webred.ct.controller.ejbclient.utilities.ControllerUtilitiesServiceException;
import it.webred.ct.controller.ejbclient.utilities.dto.GestioneUtilitiesDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.LogFunzioniDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.InputFunzioneDTO;
import it.webred.ct.controller.ejbclient.utilities.dto.RicercaLogFunzioniDTO;
import it.webred.ct.rulengine.controller.model.RConnection;
import it.webred.ct.rulengine.controller.model.RLogFunzioni;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.AbstractTabellaDwhDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.type.impl.TypeFactory;
import it.webred.utils.GenericTuples.T2;
import it.webred.utils.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

@Stateless
public class ControllerUtilitiesServiceBean implements ControllerUtilitiesService {
	
	private static final long serialVersionUID = 1L;
	
	private final BigDecimal STATO_INIZIO = new BigDecimal(126);
	private final BigDecimal STATO_ERRORE = new BigDecimal(125);
	private final BigDecimal STATO_OK = new BigDecimal(124);
	
	private final String F_RIC_HASH = "Ricalcolo Hash";
	
	protected Logger log = Logger.getLogger(ControllerUtilitiesServiceBean.class.getName());
	
	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;
	
	public List<LogFunzioniDTO> getListaLogFunzioni(RicercaLogFunzioniDTO ric){
		
		Query q;
		List<LogFunzioniDTO> listaDTO = new ArrayList<LogFunzioniDTO>();
		
		if(ric.getNome()==null)
			q = manager.createNamedQuery("RLogFunzioni.getListaLog");
		else{
		    q = manager.createNamedQuery("RLogFunzioni.getListaLogByNomeFunzione");
		    q.setParameter("nomeFunzione", ric.getNome());
		}
		
		List<RLogFunzioni> result = q.getResultList();
		for(RLogFunzioni rlog : result){
			
			LogFunzioniDTO log = new LogFunzioniDTO();
			log.setNomeFunzione(rlog.getNomeFunzione());
			log.setNote(rlog.getNote());
			log.setDataInizio(rlog.getDataIni());
			log.setDataFine(rlog.getDataFine());
			log.setStato(rlog.getIdStato().toString());
			
			String p = rlog.getParametri();
			String[] params = p.split("@");
			
			log.setParametri(params);
			
			listaDTO.add(log);
			
		}
		
		
		return listaDTO;
	}
	
	
	public void updateFineLogFunzione(GestioneUtilitiesDTO dto) {
		
		RLogFunzioni funzLog = manager.find(RLogFunzioni.class, dto.getId());
		
		if(funzLog!=null){
			
			log.debug("Funzione Log["+ dto.getId() +"] trovata!!!");
			
			funzLog.setIdStato(dto.getStato().equalsIgnoreCase("STATO_OK") ? this.STATO_OK : this.STATO_ERRORE);
			funzLog.setDataFine(new Timestamp(System.currentTimeMillis()));
			funzLog.setNote(dto.getNote());
		
			manager.merge(funzLog);
		
		}else{
			log.debug("Funzione Log ["+ dto.getId() +"]  non trovata!!!");
		}
		
	}

	public Long inserisciNuovoLogFunzione(GestioneUtilitiesDTO dto) {
		RLogFunzioni funzLog = new RLogFunzioni();
		
		Set<String> set = dto.getParametri().keySet();
		Iterator<String> i = set.iterator();
		String s = "";
		while(i.hasNext()){
			
			String chiave = (String) i.next();
			Object valore = dto.getParametri().get(chiave);
		
			s += chiave+":"+valore.toString();
			
			if(i.hasNext())
				s += "@";
			
		}
		
		funzLog.setNomeFunzione(dto.getNomeFunzione());
		funzLog.setIdStato(this.STATO_INIZIO);
		funzLog.setDataIni(new Timestamp(System.currentTimeMillis()));
		funzLog.setParametri(s);
		funzLog.setOperatore(dto.getOperatore());
		
		manager.persist(funzLog);
		
		return funzLog.getId();
	}
	
	
	private Connection getConnection(InputFunzioneDTO input){
		
		RConnection rconn = input.getConnessione();
		String driver="oracle.jdbc.driver.OracleDriver";
		String nomeUtente=rconn.getUserName();
		String pwdUtente=rconn.getPassword();
		String url=rconn.getConnString();
		
		log.debug("Connessione: " + rconn.getName());
		
		Connection conn = null;

		try {
			
			Class.forName(driver);
			conn= DriverManager.getConnection(url, nomeUtente, pwdUtente);
			
		}catch(Exception e){ 
			String message = "Impossibile aprire la connessione";
			log.debug(message);
			throw new ControllerUtilitiesServiceException(message); 
		}
		
		return conn;
		
	}
	
	@TransactionTimeout(900)
	public void ricalcolaHash(InputFunzioneDTO input) {
		
	
		String belfiore = input.getBelfiore();
		String nomeClasse = this.convertiTabellaToClasse(input.getNomeTabella()); 
		String packClasse = "it.webred.rulengine.dwh.table."+nomeClasse;
		
		log.debug("Richiesto ricalcolo Hash per la tabella " + input.getNomeTabella() + " - Classe: " + nomeClasse);
		log.debug("tabella: " + input.getBelfiore());
		log.debug("ente: " + input.getBelfiore());
		
		
	    Connection conn = this.getConnection(input);

		if (conn == null) 
			log.debug("connessione ko ");
		else{
			
			log.debug("Eseguirà il ricalcolo Hash....");
			try {
				executeRicalcolaHash(conn, packClasse, belfiore);
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage(),e);
				String message = "Classe non trovata";
				throw new ControllerUtilitiesServiceException(message, e); 
			}
			
		}	
		
		
		
	}
	
	
	private String convertiTabellaToClasse(String tab){
		String nomeJava = StringUtils.nomeCampo2JavaName(tab);
		
		return nomeJava.substring(0,1).toUpperCase() + nomeJava.substring(1); 
		
	}
	
	private void executeRicalcolaHash(Connection conn, String packClasse, String belfiore) throws ClassNotFoundException{
	
			log.warn("ATTENZIONE!! VERIFICA NECESSITA OPERAZIONI UNATANTUM IN CORSO");
			ResultSet rs = null;
			PreparedStatement ps = null;
			
			Class<? extends TabellaDwh> tabellaDwhClass = (Class<? extends TabellaDwh> )Class.forName(packClasse);
			
			
			try {
					String sqlPlain = AbstractTabellaDwhDao.getPlainSelect(tabellaDwhClass);
					String sql = sqlPlain + " where rownum <=1";
					ResultSetHandler h = new MapHandler();
					QueryRunner run = new QueryRunner();
					HashMap<String, Method> campiWithSet = new HashMap<String, Method>();
					TabellaDwh t = tabellaDwhClass.newInstance();
					Map result = new HashMap();
		            result = (Map) run.query(conn, sql,h);
		            if (result==null) {
		        		//ricalcoloHash.put(tabellaDwhClass, processId);
		            	return;
		            }
				// vado a settare i campi sul bean della tabella per calcolare l'Hash
	    		T2<DynaBean, HashMap<String, Object>> dyn = DwhUtils.getDynaInfoTable(t, true);
	    		Set<Entry<String, Object>> sets = dyn.secondObj.entrySet();
	    		Iterator it = sets.iterator();
	    		while (it.hasNext()) {
	    			Map.Entry entry = (Map.Entry) it.next();
	    			String setMethod = (String) entry.getKey();
	    			String fieldUpperFirst = setMethod.substring(3);
	    			String campo = StringUtils.JavaName2NomeCampo(fieldUpperFirst);
	    			
	    			Object value = result.get(campo);
	
	
	            	Method gmeth = t.getClass().getMethod("get" + fieldUpperFirst, new Class[0]);
	            	Class<?> tipoDatoSetMethod =gmeth.getReturnType();
	            	if (Relazione.class.getName().equals(tipoDatoSetMethod.getName()))
	            		tipoDatoSetMethod = ChiaveEsterna.class;
	            	if (RelazioneToMasterTable.class.getName().equals(tipoDatoSetMethod.getName()))
	            		tipoDatoSetMethod = ChiaveEsterna.class;
					// non partecipa al calcolo dell'hash
					if (TipoXml.class.getName().equals(tipoDatoSetMethod.getName()))
						continue;
	
	    			Object o = null;
	    			try {
	                	o = TypeFactory.getType(value, tipoDatoSetMethod.getName());
	    			} catch (Exception e) {
	    				//provo con identificativo invece che ChiaveEsrerna
	    				if (tipoDatoSetMethod.getName().equals(ChiaveEsterna.class.getName())) {
	    					tipoDatoSetMethod = Identificativo.class;
	    				}
	    				
	                	o = TypeFactory.getType(value, tipoDatoSetMethod.getName());
	    			}
	
	    			
	    			Method smeth =null;
	    			// l'unico meto do set con 2 parametri e' quello di fkentesorgente
	    			if ("setFkEnteSorgente".equals(setMethod))
	    				smeth = t.getClass().getMethod(setMethod,tipoDatoSetMethod, belfiore.getClass() );
	    			else
	    				smeth = t.getClass().getMethod(setMethod,tipoDatoSetMethod);
	    				
	    			
	    			campiWithSet.put(campo, smeth);
	    			// non invoco il set per quei campi ch non mi serve valorizzare
	    			if (TabellaDwh.notModificableFields.containsKey(StringUtils.nomeCampo2JavaName(campo)))
	    				continue;
	
	    			if (smeth.getParameterTypes().length > 1)
	    				smeth.invoke(t,o,belfiore);
	    			else
	    				smeth.invoke(t,o);
	    				
	    		}
				t.setCtrHash();
	
				// se necessario scateno ricalcolo hash !!!!!!!!!
			//	if (!t.getCtrHash().getValore().equals(result.get("CTR_HASH"))) {
					//CommandUtil.saveRCommandAck(getRCommandLaunch(processId.getValore()), new ApplicationAck(tabellaDwhClass.getName() + " RICALCOLO HASH ATTIVATO, ATTENDERE ...") ,this.getBeanCommand());
	
					log.warn("RICALCOLO HASH ATTIVATO!");
					ps = conn.prepareStatement(sqlPlain);
					rs = ps.executeQuery();
					String sqlUpd = AbstractTabellaDwhDao.getUpdateCtrHash(tabellaDwhClass);
					double count=0;
					double tot=0;
					while (rs.next()) {
						TabellaDwh row = tabellaDwhClass.newInstance();
						Iterator itcampi = campiWithSet.entrySet().iterator();
						while (itcampi.hasNext()) {
							String nomeCampo = null;
							try {
								Map.Entry entry = (Map.Entry) itcampi.next();
				    			nomeCampo = (String) entry.getKey();
				    			Method set = (Method) entry.getValue();
								Object valorecampo = rs.getObject(nomeCampo);
				    			if (set.getParameterTypes().length > 1)
				    				// fkentesorgente ha 2 parametri in set
									set.invoke(row,TypeFactory.getType(valorecampo, set.getParameterTypes()[0].getName()), belfiore);
				    			else
									set.invoke(row,TypeFactory.getType(valorecampo, set.getParameterTypes()[0].getName()));
				    			
							} catch (Exception e) {
								log.error("Errore nel reperire i campi per calcolo HASH:" +tabellaDwhClass.getName() + "." + nomeCampo );
								throw e;
							}
						}
						row.setCtrHash();
						// effettuo l'update del ctr_hash ricalcolato
						PreparedStatement upd =  null;
						try {
							upd = conn.prepareStatement(sqlUpd);
							upd.setString(1, row.getCtrHash().getValore());
							upd.setString(2, row.getId().getValore());
							upd.executeUpdate();
							
						} catch (Exception e) {
							log.error("Errore update HASH:" +tabellaDwhClass.getName() );
							throw e;
						} finally {
							DbUtils.close(upd);
						}
	
						count+=1;
						tot+=1;
						if (count==1000) {
							log.debug(tabellaDwhClass.getName() +  " Hash Ricalcolati:" + tot);
							
							count=0;
						}
						
					}
			//} else 
			//		log.debug("RICALCOLO HASH NON NECESSARIO!");
				
				log.warn("RICALCOLO HASH COMPLETATO!");
				
			} catch (Exception e) {
				log.error("ERRORE GRAVE CERCANDO DI GESTIRE GLI hash !" , e);
				throw new ControllerUtilitiesServiceException("ERRORE GRAVE CERCANDO DI GESTIRE GLI hash!", e);
			}
		
		finally
	    {
	    	try { if (rs != null) rs.close(); } catch (Exception e) {}
	    	try { if (ps != null) ps.close(); } catch (Exception e) {}
	    	try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }    
	
	}
	
	public void svuotaTabella(InputFunzioneDTO in) throws SQLException{
		
		String tabella = in.getNomeTabella();
		Connection conn = this.getConnection(in);
		
		
		
		if(conn!=null){
			PreparedStatement ps = null;
			
			try{
				String sql = "DELETE FROM " + tabella.toUpperCase();
				ps = conn.prepareStatement(sql);
				ps.execute();
				
			}catch(Exception e){
				String message = "Errore durante lo svutamento della tabella " + tabella + " in " + in.getConnessione().getName();
				throw new ControllerUtilitiesServiceException(message, e);
			}finally{
				try { if (ps != null) ps.close(); } catch (Exception e) {}
				try { if (conn != null) conn.close(); } catch (Exception e) {}
			}
			
		}
		
	}
	
}








