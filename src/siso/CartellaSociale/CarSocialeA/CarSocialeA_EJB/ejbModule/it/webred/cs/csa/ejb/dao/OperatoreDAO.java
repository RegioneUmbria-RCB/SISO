package it.webred.cs.csa.ejb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoriSearchCriteria;
import it.webred.cs.data.model.CsODecodificaRuoli;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.CsRelSettCatsocEsclusiva;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettCsocTipoInterPK;
import it.webred.cs.data.model.CsRelSettoreStruttura;
import it.webred.cs.data.model.view.CsAmAnagraficaOperatore;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

@Named
public class OperatoreDAO extends CarSocialeBaseDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*ORGANIZZAZIONI*/
	public CsOOrganizzazione getOrganizzazioneById(Long obj) {
		if(obj!=null)
			return em.find(CsOOrganizzazione.class, obj);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsOOrganizzazione> getOrganizzazioni() {

		try {

			Query q = em.createNamedQuery("CsOOrganizzazione.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsOOrganizzazione> getOrganizzazioniAccesso() {

		try {
			Query q = em.createNamedQuery("CsOOrganizzazione.findAllAbilAccesso");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsOOrganizzazione> getOrganizzazioniAll() {
		Query q = em.createNamedQuery("CsOOrganizzazione.findAll");
		return q.getResultList();
	}

	public void salvaOrganizzazione(CsOOrganizzazione org) {
		em.persist(org);
	}

	public void updateOrganizzazione(CsOOrganizzazione org) {
		em.merge(org);
	}

	public void eliminaOrganizzazione(Long id) {
		Query q = em.createNamedQuery("CsOOrganizzazione.eliminaById");
		q.setParameter("id", id);
		q.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<CsOOrganizzazione> getOrganizzazioneByCodCatastale(String codice) {
		try {
			if (codice != null) {
				Query q = em.createNamedQuery("CsOOrganizzazione.findByCodCatastale");
				q.setParameter("codice", codice);
				return q.getResultList();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public CsOOrganizzazione getOrganizzazioneByCodFittizio(String codice) {
		try {
			if (codice != null) {
				Query q = em.createNamedQuery("CsOOrganizzazione.findByCodFittizio");
				q.setParameter("codice", codice);
				List<CsOOrganizzazione> lst = q.getResultList();
				if(!lst.isEmpty()) return (CsOOrganizzazione)lst.get(0);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}



	/* SISO-663 SM */

	public CsOOrganizzazione getOrganizzazioneCapofila() {
		try {
			TypedQuery<CsOOrganizzazione> q = em.createNamedQuery("CsOOrganizzazione.findCapofila", CsOOrganizzazione.class);
			List<CsOOrganizzazione> lst = q.getResultList();
			if(lst.size()>0)
				return lst.get(0);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/* -=- */



	/*SETTORI*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(long idOrganizzazione) {
		Query q = em.createNamedQuery("CsOSettore.findSettoreBASICByOrganizzazione");
		q.setParameter("idOrganizzazione", idOrganizzazione);
		List results = q.getResultList();
		return results;
	}

	public void salvaSettore(CsOSettore cs) {
		em.persist(cs);
	}

	public void updateSettore(Object cs) {
		em.merge(cs);
	}

	public void eliminaSettore(Long id) {
		Query q = em.createNamedQuery("CsRelSettoreCsocTipoInter.eliminaBySettoreId");
		q.setParameter("id", id);
		q.executeUpdate();
		
		Query q1 = em.createNamedQuery("CsRelSettoreCatsoc.eliminaBySettoreId");
		q1.setParameter("id", id);
		q1.executeUpdate();
		
		Query q2 = em.createNamedQuery("CsOSettore.eliminaById");
		q2.setParameter("id", id);
		q2.executeUpdate();
	}
	
	public CsOSettore findSettoreById(long id) throws Exception {
			CsOSettore settore = em.find(CsOSettore.class, id);
			return settore;
	}
	
	//SISO-812
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOSettoreBASIC> findSettoreBASICSecondoLivelloByOrganizzazione(long idOrganizzazione) throws Exception {
		Query q = em.createNamedQuery("CsOSettore.findSettoreBASICSecondoLivelloByOrganizzazione").setParameter("idOrganizzazione", idOrganizzazione);
		List results = q.getResultList();
		return results;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOSettoreBASIC> findSettoreBASICSenzaSecondoLivelloByOrganizzazione(long idOrganizzazione) throws Exception {
		Query q = em.createNamedQuery("CsOSettore.findSettoreBASICSenzaSecondoLivelloByOrganizzazione").setParameter("idOrganizzazione", idOrganizzazione);
		List results = q.getResultList();
		return results;
	}
		
	@SuppressWarnings("unchecked")
	public List<CsOSettore> findSettoriById(List<Long> listaIdSettori) {
		List<CsOSettore> lst = new ArrayList<CsOSettore>();
		try {
			if(!listaIdSettori.isEmpty()){
				Query q = em.createNamedQuery("CsOSettore.findSettoriById");
				q.setParameter("listaIdSettori", listaIdSettori);
				lst = (List<CsOSettore>) q.getResultList();
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return lst;
	}
	


	@SuppressWarnings("unchecked")
	public List<CsOSettore> getSettoreAll() {
		try {

			Query q = em.createNamedQuery("CsOSettore.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsOSettore> getSettoreRiunione() {
		try {

			Query q = em.createNamedQuery("CsOSettore.findPerRiunione");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsOSettore> getSettoriDatiSociali() {
		try {

			Query q = em.createNamedQuery("CsOSettore.findSettoriInvianteInviatoInCarico");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public CsOSettore getSettoreById(Long id) {
		try{
			return em.find(CsOSettore.class, id);
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
		}		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<SelectItem> getListaRiunioneCon(boolean riunioneCon) {
		
		
		try {
			
			List<SelectItem> RiunioneConList = new ArrayList<SelectItem>();
			List<CsOSettore> lst = getSettoreAll();
			LinkedHashMap<String,List<SelectItem>> mappa = new LinkedHashMap<String,List<SelectItem>>();
			
			if (lst != null) {
				for (CsOSettore obj : lst) {
					//String belfioreOrg = obj.getCsOOrganizzazione().getBelfiore();
					//boolean comuneValido = belfioreOrg==null || belfioreOrg.equals(bo.getEnteId());	
					boolean comuneValido = true;
					boolean aggiungi = (riunioneCon && obj.getFlgRiunioneCon() != null);

					if (comuneValido && aggiungi) {
						String nomeOrg = obj.getCsOOrganizzazione().getNome();
						SelectItem si = new SelectItem(obj, obj.getNome());
						si.setDisabled(!obj.getAbilitato());
						List<SelectItem> gi = mappa.get(nomeOrg);
						if(gi==null)
							gi = new ArrayList<SelectItem>();

						gi.add(si);
						mappa.put(nomeOrg, gi);
					}				
				}
				
				Iterator<String> iter = mappa.keySet().iterator();
				while(iter.hasNext()){
					String s = (String) iter.next();
					mappa.get(s);
					SelectItemGroup g = new SelectItemGroup(s);
					List<SelectItem> lstIt = mappa.get(s);
					g.setSelectItems(lstIt.toArray(new SelectItem[lstIt.size()]));

					RiunioneConList.add(g);
				}
			}		

			return RiunioneConList;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	// #ROMACAPITALE inizio
	@SuppressWarnings("unchecked")
	public List<Long> findIdSettoriByInterventoISTATandInterventoCustom(Long idInterventoISTAT, Long idInterventoCUSTOM) {
		try {
			Query q = em.createNamedQuery("CsRelIntSettore.findIdSettoriByInterventoISTATandInterventoCustom");
			q.setParameter("idInterventoISTAT", idInterventoISTAT);
			q.setParameter("idInterventoCUSTOM", idInterventoCUSTOM);
			return q.getResultList();
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findIdSettoriByInterventoCustom(Long idInterventoCUSTOM) {
		try {
			Query q = em.createNamedQuery("CsRelIntSettore.findIdSettoriByInterventoCustom");
			q.setParameter("idInterventoCUSTOM", idInterventoCUSTOM);
			return q.getResultList();
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public CsRelSettoreStruttura findSettoreByIdStruttura(Long idStruttura) {
		try {
				Query q = em.createNamedQuery("CsRelSettoreStruttura.findSettoreByIdStruttura");
				q.setParameter("idStruttura", idStruttura);
				return (CsRelSettoreStruttura) q.getSingleResult();
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public CsOSettore findSettoreByRelStruttura(Long idStruttura) {
		try {	
			Query q = em.createNamedQuery("CsRelSettoreStruttura.findSettoreByStruttura");
			q.setParameter("idStruttura", idStruttura);
			return (CsOSettore) q.getSingleResult();
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsRelSettCatsocEsclusiva> findRelSettCatsocEsclusivaByTipoDiarioId(Long tipoDiarioId) {
		Query q = em.createNamedQuery("CsRelSettCatsocEsclusiva.findRelSettCatsocEsclusivaByTipoDiarioId");
		q.setParameter("tipoDiarioId", tipoDiarioId);
		return q.getResultList();
	}

	@SuppressWarnings({ "rawtypes" })
	public CsRelSettCatsocEsclusiva findRelSettCatsocEsclusivaByIds(Long tipoDiarioId, Long categoriaSocialeId, Long settoreId) {
		Query q = em.createNamedQuery("CsRelSettCatsocEsclusiva.findRelSettCatsocEsclusivaByIds");
		q.setParameter("tipoDiarioId", tipoDiarioId);
		q.setParameter("categoriaSocialeId", categoriaSocialeId);
		q.setParameter("settoreId", settoreId);
		List l = q.getResultList();
		if (l.size() > 0)
			return (CsRelSettCatsocEsclusiva) l.get(0);

		return null;
	}
	
	   
	public CsRelSettCsocTipoInter findRelSettCsocTipoInterById(CsRelSettCsocTipoInterPK obj) {
		return em.find(CsRelSettCsocTipoInter.class, obj);

	}
		
	
	/*OPERATORI*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoId(Long id) {
		Query q = em.createNamedQuery("CsOOperatore.findOperatoriByTipoId");
		q.setParameter("id", id);
		return q.getResultList();	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoIdSettore(Long idTipo, Long idSettore) {
		Query q = em.createNamedQuery("CsOOperatore.findOperatoriByTipoIdSettore");
		q.setParameter("idTipo", idTipo);
		q.setParameter("idSettore", idSettore);
		return q.getResultList();
			
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatore> getOperatoriByTipoDescrizione(String descrizione) {
			
		Query q = em.createNamedQuery("CsOOperatore.findOperatoriByTipoDescrizione");
		q.setParameter("descrizione", descrizione);
		List result = q.getResultList();
		if( result != null && result.size() > 0 )
			return result;
		
		return null;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatore> getOperatoriAll() {
			
		Query q = em.createNamedQuery("CsOOperatore.findAll");
		return q.getResultList();

	}
	
	@SuppressWarnings("rawtypes")
	public CsOOperatoreTipoOperatore getTipoByOperatoreSettore(Long idOpSet, Date datFinApp) {
			
		Query q = em.createNamedQuery("CsOOperatore.findTipoByOperatoreSettore");
		q.setParameter("idOpSet", idOpSet);
		q.setParameter("dataFineApp",  datFinApp);
		List result = q.getResultList();
		if( result != null && result.size() > 0 )
			return (CsOOperatoreTipoOperatore)result.get(0);
		
		return null;

	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatore> getOperatoriByCatSocialeOrg(Long idCatSociale, String codRouting) {
	    Query q = em.createNamedQuery("CsOOperatore.findOperatoriByCatSocialeOrg");
		q.setParameter("idCatSociale", idCatSociale);
		q.setParameter("codRouting", codRouting);
		List result = q.getResultList();
		if( result != null && result.size() > 0 )
			return result;
		
		return null;

	}
	
	@SuppressWarnings("rawtypes")
	public CsOOperatoreSettore findOperatoreSettoreById(long idOp, long idSettore, Date date) {
		Query q = em.createNamedQuery("CsOOperatoreSettore.getOperatoreSettoreById");
		q.setParameter("idOp", idOp);
		q.setParameter("date", date);
		q.setParameter("idSettore", idSettore);
		List result = q.getResultList();
		
		if( result != null && result.size() > 0 )
			return (CsOOperatoreSettore)result.get(0);
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public CsOOperatoreSettore findOperatoreSettoreById(long idOpSettore)  {
		
		CsOOperatoreSettore op = em.find(CsOOperatoreSettore.class, idOpSettore); 
		return op;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<CsOOperatoreSettore> findOperatoreSettoreByOperatore(long idOp, Date date) throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreSettoreByOperatore");
		q.setParameter("idOp", idOp);
		q.setParameter("date", date);
		List<CsOOperatoreSettore> results = (List<CsOOperatoreSettore>)q.getResultList();
		for(CsOOperatoreSettore os : results){
			os.getAlertConfig().size();
			os.getTipoOperatore().size();
		}
		return results;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<CsOOperatoreSettore> findOperatoreSettoreByUsername(String idOp, Date date) throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreSettoreByUsername").setParameter("username", idOp).setParameter("date", date);
		List results = q.getResultList();
		return results;
	}

	@SuppressWarnings("rawtypes")
	@AuditSaltaValidazioneSessionID
	public CsOOperatore findOperatoreByUsername(String username) throws Exception {
		CsOOperatore o = null;
		if( StringUtils.isEmpty( username ) )
			return null;
		
		try{
			Query q = em.createNamedQuery("CsOOperatore.getOperatoreByUsername");
			q.setParameter("username", username);
			List result = q.getResultList();
			if( result != null && result.size() > 0 ){
				o = (CsOOperatore)result.get(0);
				o.getCsOOperatoreSettores().size();
			}
			
		}catch(Exception t){
			logger.error(t.getMessage(),t);
			throw(t);
		}
		return o;
	}
	
	public CsOOperatore findOperatoreById(long idOp) throws Exception {
		
		Query op = em.createNamedQuery("CsOOperatore.getOperatoreById");
		op.setParameter("idOp", idOp);

		return (CsOOperatore) op.getSingleResult();
	}
	
	public CsOOperatoreBASIC findOperatoreBASICById(long idOp) throws Exception {
		
		CsOOperatoreBASIC operatore = em.find(CsOOperatoreBASIC.class, idOp);
		return operatore;
		
	}
	
	public CsOOperatoreBASIC findOperatoreBASICByUserName(String userName) throws Exception {
		CsOOperatoreBASIC o = null;
		try{
			if(!StringUtils.isBlank(userName)){
				Query op = em.createNamedQuery("CsOOperatoreBASIC.getOperatoreBASICByUserName");
				op.setParameter("username", userName);
		
				o =  (CsOOperatoreBASIC) op.getSingleResult();
			}
		}catch(Exception e){
			logger.error(e.getMessage()+"["+userName+"]", e);
		}
		return o;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CsOOperatoreSettore> findOperatoreSettori() throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreSettore.findAll");
		List results = q.getResultList();
		return results;
	}

	private List<KeyValueDTO> findListaOperatoreSettore(long idSettore, boolean keyOperatoreId){
		List<KeyValueDTO> lstOperatori = new ArrayList<KeyValueDTO>();
		Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreSettoreAnagraficaBySettore");
		q.setParameter("idSettore", idSettore);
		List<Object[]> result =  q.getResultList();
		Date today = new Date();
		for(Object[] res : result){
			Long idOperatoreSettore = (Long)res[0];
			Long idOperatore = (Long)res[1];
			CsOOperatoreAnagrafica ana = (CsOOperatoreAnagrafica)res[2];
			Date dataInizio = (Date)res[3];
			Date dataFine = (Date)res[4];
			Boolean opAbilitato = res[5]!=null ? (Boolean)res[5] : Boolean.FALSE;
			
			boolean abilitato = today.after(dataInizio) && today.before(dataFine) && opAbilitato;
			
			if(ana!=null){
				Long identificativo = keyOperatoreId ? idOperatore : idOperatoreSettore;
				KeyValueDTO si = new KeyValueDTO(identificativo, ana.getDenominazione());
				si.setAbilitato(abilitato);
				lstOperatori.add(si);
			}
		}
		
		Collections.sort(lstOperatori, new Comparator<KeyValueDTO>() {
			@Override
			public int compare(final KeyValueDTO object1, final KeyValueDTO object2) {
				return object1.getDescrizione().compareTo(object2.getDescrizione());
			}
		});
		
		return lstOperatori;
	}
	
	public List<KeyValueDTO> findListaOperatoreBySettore(long idSettore) throws Exception {
		return findListaOperatoreSettore(idSettore,true);
	}
	
	public List<KeyValueDTO> findListaOperatoreSettoreBySettore(Long idSettore) {
		return findListaOperatoreSettore(idSettore,false);
	}
	
	public List<CsOOperatoreSettore> findRespSettoreAttivo(long idSettore, String enteId) throws Exception {
		String gruppo = "CSOCIALE_RESPO_SETTORE_" + enteId;
		
		Query q = em.createNamedQuery("CsOOperatoreSettore.findRespSettoreAttivo");
		q.setParameter("idSettore", idSettore);
		q.setParameter("gruppo", gruppo);
		List results = q.getResultList();
		return results;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CsOZonaSoc findZonaSocAbilitata() {
		try{
		Query q = em.createNamedQuery("CsOZonaSoc.findAllAbil");
		List results = q.getResultList();
		if(results!=null && results.size()>0)
			return (CsOZonaSoc)results.get(0);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public CsOOperatoreTipoOperatore findCsOOperatoreTipoOpById(Long obj) {
		return em.find(CsOOperatoreTipoOperatore.class, obj);
	}
	
	public CsOOperatore insertOrUpdateOperatore(CsOOperatore op) throws Exception {
		op = em.merge(op);
		return op;
	}
	
	public CsOOperatoreTipoOperatore insertOrUpdateTipoOperatore(CsOOperatoreTipoOperatore tipoOp) throws Exception {
		tipoOp = em.merge(tipoOp);
		return tipoOp;
	}
	
	public CsOOperatoreSettore insertOrUpdateOperatoreSettore(CsOOperatoreSettore opSet) throws Exception {
		opSet = em.merge(opSet);
		return opSet;
	}
	
	public void deleteTipoOperatore(CsOOperatoreTipoOperatore tipoOp) throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreTipoOperatore.eliminaTipoOperatoreById");
		q.setParameter("id", tipoOp.getId());
		q.executeUpdate();
	}
	
	public void deleteOperatoreSettore(CsOOperatoreSettore opSet) throws Exception {
		try{
		
			Query q = em.createNamedQuery("CsACasoOpeTipoOpe.eliminaCasoOpeTipoOpeByIdOperatoreSettore");
			q.setParameter("idOpSet", opSet.getId());
			q.executeUpdate();
			
			q = em.createNamedQuery("CsOOperatoreTipoOperatore.eliminaTipoOperatoreByIdOperatoreSettore");
			q.setParameter("idOpSet", opSet.getId());
			q.executeUpdate();
			
			deleteConfigAlertOpSettore(opSet.getId());
			
			q = em.createNamedQuery("CsOOperatoreSettore.eliminaOperatoreSettoreById");
			q.setParameter("id", opSet.getId());
			q.executeUpdate();
			
		}catch(Exception e){
			logger.error(e);
			throw(e);
		}
	}

	public void resetFirmaTuttiRespSettore(String amGroup, Long settoreId) {
		String s = "UPDATE CS_O_OPERATORE_SETTORE SET FIRMA=0 WHERE AM_GROUP like '%"+amGroup+"%' and SETTORE_ID=?";
		Query q = em.createNativeQuery(s);
		q.setParameter(1, settoreId);
		q.executeUpdate();
	}

	public CsOSettoreBASIC findSettoreBASICById(Long idSettore) {
			CsOSettoreBASIC settore = em.find(CsOSettoreBASIC.class, idSettore);
			return settore;
	}

	public void salvaConfigurazioneAlert(CsOOpsettoreAlertConfig c) {
		em.merge(c);
	}

	public void deleteConfigAlertOpSettore(Long opSettoreId) throws Exception {
		try{
			
			Query q = em.createNamedQuery("CsOOpsettoreAlertConfig.eliminaByIdOperatoreSettore");
			q.setParameter("idOpSettore", opSettoreId);
			q.executeUpdate();
			
		}catch(Exception e){
			logger.error(e);
			throw(e);
		}
		
	}
	
	public void setEmailConfigAlertOpSettore(Long opSettoreId, Boolean abilita) throws Exception {
		try{
			
			Query q = em.createNamedQuery("CsOOpsettoreAlertConfig.setEmailByIdOperatoreSettore");
			q.setParameter("idOpSettore", opSettoreId);
			q.setParameter("abilita", abilita);
			q.executeUpdate();
			
		}catch(Exception e){
			logger.error(e);
			throw(e);
		}
		
	}
	
	public List<String> findTipiOperatore(List<Long> idOperatoreSettore) throws Exception {
		List<String> lst = new ArrayList<String>();
		try {
			Query q = em.createNamedQuery("CsOOperatoreTipoOperatore.findTipiOperatoreByOperatoreSettore");
			q.setParameter("idOperatoreSettore", idOperatoreSettore);
			lst = (List<String>) q.getResultList();
		} catch (Exception e) {
			logger.error(e);
			throw (e);
		}
		return lst;
	}

	public String findLabelRuolo(String obj) {
		CsODecodificaRuoli d = em.find(CsODecodificaRuoli.class, obj);
		return d!=null ? d.getLabelRuolo() : null;
	}

	public LinkedHashMap<String, String> getDecodificaRuoli(String belfiore) {
		LinkedHashMap<String, String> hmRuoli = new LinkedHashMap<String, String>();
		Query q = em.createNamedQuery("CsODecodificaRuoli.findAll");
		List<CsODecodificaRuoli> ruoli = (List<CsODecodificaRuoli>) q.getResultList();
		if(belfiore!=null && !ruoli.isEmpty()){
			for(CsODecodificaRuoli ruolo : ruoli)
				hmRuoli.put(ruolo.getPrefissoGruppo() + belfiore, ruolo.getLabelRuolo());
		}
		
		return hmRuoli;
	}

		
	public List<CsAmAnagraficaOperatore> searchUtentiAmPerCs(OperatoriSearchCriteria cet, boolean count) {
		try{
			
			String s = "SELECT c FROM CsAmAnagraficaOperatore c WHERE 1=1 ";
			
			if(!StringUtils.isBlank(cet.getCognome())) 
				s+= "AND UPPER(c.cognome) LIKE '%'|| :cognome ||'%'";
			if(!StringUtils.isBlank(cet.getNome())) 
				s+= "AND UPPER(c.nome) LIKE '%'|| :nome ||'%'";
			if(!StringUtils.isBlank(cet.getCodiceFiscale())) 
				s+= "AND UPPER(c.codiceFiscale) LIKE '%'|| :cf ||'%'";
			if(!StringUtils.isBlank(cet.getUsername())) 
				s+= "AND UPPER(c.username) LIKE '%'|| :username ||'%'";
			if(cet.getAbilitato()!=null)
				s+= "AND c.abilitato = :abilitato "; 
			if(cet.getEnti()!=null && !cet.getEnti().isEmpty()){
				s+="AND (";
				String entis = "";
				for(String ente : cet.getEnti())
					entis+=" OR c.enti like '%"+ente+"%'";
				entis = entis.replaceFirst("OR", "");
				s+=entis+") ";
			}
				   s+= "ORDER BY cognome, nome ";
			
			Query q = em.createQuery(s);
			
			if(!StringUtils.isBlank(cet.getCognome())) 
				q.setParameter("cognome", cet.getCognome().toUpperCase());
			if(!StringUtils.isBlank(cet.getNome())) 
				q.setParameter("nome", cet.getNome().toUpperCase());
			if(!StringUtils.isBlank(cet.getCodiceFiscale())) 
				q.setParameter("cf", cet.getCodiceFiscale().toUpperCase());
			if(!StringUtils.isBlank(cet.getUsername())) 
				q.setParameter("username", cet.getUsername().toUpperCase());
			if(cet.getAbilitato()!=null)
				q.setParameter("abilitato", cet.getAbilitato().booleanValue());
			
			
			if(!count){
				q.setFirstResult(cet.getFirst());
				q.setMaxResults(cet.getPageSize());
			}
			
			return q.getResultList();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	
	}
			
	public List<KeyValueDTO> findOperatoreIdAnagraficaBySettore(Long idSettore) {
		List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
		Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreIdAnagraficaBySettore");
		q.setParameter("idSettore", idSettore);
		List<Object[]> lst = q.getResultList();
		Date today = new Date();
		for(Object[] o :lst){
			Long idOperatore = (Long)o[0];
			Date dataInizio = (Date)o[1];
			Date dataFine = (Date)o[2];
			Boolean abilitato = (Boolean)o[3];
			CsOOperatoreAnagrafica ana = (CsOOperatoreAnagrafica)o[4];
			if(ana!=null){
				KeyValueDTO val = new KeyValueDTO(idOperatore, ana.getDenominazione());
				val.setAbilitato(abilitato && today.after(dataInizio) && today.before(dataFine));
				lstOut.add(val);
			}
		}
		return lstOut;
	}
	

	public List<CsOOperatoreSettore> findOperatoreSettoreByCodStruttura(String tipo2Livello, String codRouting){
		
		Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreSettoreByCodStruttura");
		q.setParameter("tipo2livello", tipo2Livello);
		q.setParameter("codRouting",  codRouting);
		List <CsOOperatoreSettore> lst = q.getResultList();
		
		return lst;

	}
	
     public CsOOperatoreSettore findOperatoreSettore2LivByIdOperatore(String tipo2Livello, Long idOperatore, String codRouting, Date dtRif){
    	 CsOOperatoreSettore os = null;
    	 try{
			Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreSettore2LivByIdOperatore");
			q.setParameter("tipo2livello", tipo2Livello);
			q.setParameter("codRouting",  codRouting);
			q.setParameter("idOperatore",  idOperatore);
			q.setParameter("dtRif", dtRif);
			os = (CsOOperatoreSettore) q.getSingleResult();
		}catch(Exception e){
			logger.error("findOperatoreSettore2LivByIdOperatore"+e.getMessage(), e);
			throw new CarSocialeServiceException (e);
		}
    	return os;
	}
     
     public CsOOperatoreSettore findOperatoreSettore2LivByUsername(String tipo2Livello, String username, String codRouting, Date dtRif){
    	 CsOOperatoreSettore os = null;
    	 try{
			Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreSettore2LivByUsername");
			q.setParameter("tipo2livello", tipo2Livello);
			q.setParameter("codRouting",  codRouting);
			q.setParameter("username",  username);
			q.setParameter("dtRif", dtRif);
			os = (CsOOperatoreSettore) q.getSingleResult();
		}catch(Exception e){
			logger.error("findOperatoreSettore2LivByUsername"+e.getMessage(), e);
			throw new CarSocialeServiceException (e);
		}
    	return os;
	}

}