package it.webred.ct.data.access.basic.segnalazionequalificata.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.segnalazionequalificata.SegnalazioneQualificataServiceException;
import it.webred.ct.data.access.basic.segnalazionequalificata.SegnalazioniDataIn;
import it.webred.ct.data.access.basic.segnalazionequalificata.dto.PraticaSegnalazioneDTO;
import it.webred.ct.data.access.basic.segnalazionequalificata.dto.RicercaPraticaSegnalazioneDTO;
import it.webred.ct.data.model.segnalazionequalificata.SOfAmbitoAccertamento;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratAllegato;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratAmbito;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratAmbitoPK;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratFonte;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratFontePK;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratica;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class SegnalazioneQualificataJPAImpl extends CTServiceBaseDAO implements SegnalazioneQualificataDAO{
	
	private static final long serialVersionUID = 1L;
	

	@Override
	public List<SOfAmbitoAccertamento> getAmbitiAccertamento(SegnalazioniDataIn dataIn) throws SegnalazioneQualificataServiceException{
		try {
			logger.info("Estrazione Ambiti Accertamento...");
			Query q = manager_diogene.createNamedQuery("SOfAmbitoAccertamento.getAmbitiAccertamento");
			List<SOfAmbitoAccertamento>  lista = q.getResultList();
			logger.info("Risultati: " + lista.size());
			return lista;
			
		} catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
	}
	
	@Override
	public List<String> getListaOperatori(SegnalazioniDataIn dataIn) throws SegnalazioneQualificataServiceException{
		try {
			logger.info("Estrazione Operatori...");
			Query q = manager_diogene.createNamedQuery("SOfPratica.getOperatori");
			List<String>  lista =  q.getResultList();
			logger.info("Risultati: " + lista.size());
			return lista;
			
		} catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
		
	}
	
	@Override
	public PraticaSegnalazioneDTO getPraticaById(RicercaPraticaSegnalazioneDTO ro) throws SegnalazioneQualificataServiceException{
		PraticaSegnalazioneDTO rep = new PraticaSegnalazioneDTO();
		long idPra = ro.getIdPra();
		try {
			
			logger.info("Estrazione Pratica Segnalazione [Id:"+idPra+"]...");
			
			SOfPratica pratica = manager_diogene.find(SOfPratica.class, idPra);
			rep.setPratica(pratica);
			
			//Estrazione Ambiti Accertamento
			List<SOfAmbitoAccertamento> ambiti = this.getAmbitiAccertamentoByIdPra(ro);
			for(SOfAmbitoAccertamento ambito : ambiti){
				rep.getAmbitiAccertamento().add(Long.toString(ambito.getId()));
			}
			
			//Estrazione Fonti
			List<String> fonti = this.getFontiByIdPra(ro);
			rep.setFonti(fonti);
			
			//Estrazione Allegati
			rep.setAllegati(this.getListaAllegatiPratica(ro));
				
		} catch(NoResultException nre){
			
		}catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
		return rep;
	}
	
	public List<SOfAmbitoAccertamento> getAmbitiAccertamentoByIdPra(RicercaPraticaSegnalazioneDTO ro){
		List<SOfAmbitoAccertamento> ambiti = new ArrayList<SOfAmbitoAccertamento>();
		long idPra = ro.getIdPra();
		try {
			logger.info("Estrazione Ambiti Pratica [Id:"+idPra+"]...");
			Query qa = manager_diogene.createNamedQuery("SOfAmbitoAccertamento.getAmbitiAccertamentoPratica");
			qa.setParameter("idPratica", idPra);
			ambiti = (List<SOfAmbitoAccertamento>) qa.getResultList();
			logger.info("Risultati: " + ambiti.size());
			
		}catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
		return ambiti;
	}
	
	public List<String> getFontiByIdPra(RicercaPraticaSegnalazioneDTO ro){
		List<String> fonti = new ArrayList<String>();
		long idPra = ro.getIdPra();
		try {
			logger.info("Estrazione Fonti Pratica [Id:" + idPra + "]...");
			Query qf = manager_diogene.createNamedQuery("SOfPratFonti.getFonti");
			qf.setParameter("idPratica", idPra);
			fonti = (List<String>) qf.getResultList();
			logger.info("Risultati: " + fonti.size());

		} catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
		return fonti;
	}
	
	@Override
	public List<Object> getSuggestionCognomi(SegnalazioniDataIn dataIn) throws SegnalazioneQualificataServiceException{
		try {
			
			String value = (String) dataIn.getObj1();
			String tipo = (String)dataIn.getObj2();
			
			Query q;
			logger.info("Estrazione Cognomi [Tipo:"+tipo+",Param:"+value+"]...");
			
			if("ACC".equals(tipo))
				 q = manager_diogene.createNamedQuery("SOfPratica.getAccCognomi");
			else
				 q = manager_diogene.createNamedQuery("SOfPratica.getResCognomi");
			
			q.setParameter("cognome", value.toUpperCase());
			q.setMaxResults(10);
			List<Object>  lista =  q.getResultList();
			logger.info("Risultati: " + lista.size());
			return lista;
			
		} catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
	}
	
	@Override
	public List<Object> getSuggestionDenominazione(SegnalazioniDataIn dataIn) throws SegnalazioneQualificataServiceException{
		try {
			
			String value = (String) dataIn.getObj1();
			logger.info("Estrazione Denominazioni [Param:"+value+"]...");
			Query q = manager_diogene.createNamedQuery("SOfPratica.getDenominazioni");
			q.setParameter("denominazione", value.toUpperCase());
			q.setMaxResults(10);
			List<Object>  lista =  q.getResultList();
			logger.info("Risultati: " + lista.size());
			return lista;
			
		} catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
		
	}
	
	@Override
	public List<Object> getSuggestionNomi(SegnalazioniDataIn dataIn) throws SegnalazioneQualificataServiceException{
		
		try {
			String value = (String) dataIn.getObj1();
			String tipo = (String)dataIn.getObj2();
			
			Query q;
			logger.info("Estrazione Nomi [Tipo:"+tipo+",Param:"+value+"]...");
			if("ACC".equals(tipo))
			    q = manager_diogene.createNamedQuery("SOfPratica.getAccNomi");
			else
				q = manager_diogene.createNamedQuery("SOfPratica.getResNomi");
			q.setParameter("nome", value.toUpperCase());
			q.setMaxResults(10);
			
			List<Object>  lista =  q.getResultList();
			logger.info("Risultati: " + lista.size());
			return lista;
			
		} catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
	}
	
	@Override
	public List<Object> getSuggestionCodFisc(SegnalazioniDataIn dataIn) throws SegnalazioneQualificataServiceException{
		
		try {
			
			String value = (String) dataIn.getObj1();
			String tipo = (String)dataIn.getObj2();
			
			Query q;
			logger.info("Estrazione Codici Fiscali [Tipo:"+tipo+",Param:"+value+"]...");
			if("ACC".equals(tipo))
				q = manager_diogene.createNamedQuery("SOfPratica.getAccCodiFisc");
			else
				q = manager_diogene.createNamedQuery("SOfPratica.getResCodiFisc");
			q.setParameter("codifisc", value.toUpperCase());
			q.setMaxResults(10);
			List<Object>  lista =  q.getResultList();
			logger.info("Risultati: " + lista.size());
			return lista;
			
		} catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
	}
	
	@Override
	public List<SOfPratica> search(RicercaPraticaSegnalazioneDTO rs)throws SegnalazioneQualificataServiceException{
		ArrayList<SOfPratica> reps = new ArrayList<SOfPratica>();
		
		try {

			String sql = (new PraticaSegnalazioneQueryBuilder(rs)).createQuery(false);
			logger.info("Ricerca Pratiche...");
			logger.info("SQL[" + sql + "]");
			Query q = manager_diogene.createQuery(sql);
			if (rs.getStartm() != 0 || rs.getNumberRecord() != 0) {
				q.setFirstResult(rs.getStartm());
				q.setMaxResults(rs.getNumberRecord());
			}

			reps = (ArrayList<SOfPratica>) q.getResultList();
			logger.info("Risultati:" + reps.size());
			
		} catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
		
		return reps;
		
	}
	
	@Override
	public Long searchCount(RicercaPraticaSegnalazioneDTO rs)
		throws SegnalazioneQualificataServiceException{
		
		try {

			String sql = (new PraticaSegnalazioneQueryBuilder(rs)).createQuery(true);
			logger.info("Conteggio Risultati...");
			logger.info("SQL[" + sql + "]");
			Query q = manager_diogene.createQuery(sql);
			Long l = (Long) q.getSingleResult();
			return l == null? new Long(0): l;
			
		} catch (Throwable t) {
			throw new SegnalazioneQualificataServiceException(t);
		}
	}
	
	@Override
	public long creaPraticaSegnalazione(PraticaSegnalazioneDTO dto)
			throws SegnalazioneQualificataServiceException {
		try {
			SOfPratica pratica = dto.getPratica();
			logger.info("CREAZIONE PRATICA SEGNALAZIONE QUALIFICATA:"+ pratica.getId());
			logger.info("OPER ID:"+ pratica.getOperatoreId());
			logger.info("TIPO SOGG: "+pratica.getAccTipoSogg());
			logger.info("RES CODFISC:"+ pratica.getResCodiFisc());
			logger.info("RES:"+ pratica.getResCognome()+" "+pratica.getResNome());
			pratica.setAccDataInizio(getCurrentDate());
			
			/*Query q = manager_diogene.createNativeQuery("INSERT INTO S_OF_AMBITO_ACCERTAMENTO (ID, OPERATORE_ID, " +
					"ACC_TIPO_SOGG, RES_CODI_FISC, RES_COGNOME, RES_NOME) " +
					"VALUES(300,'"+pratica.getOperatoreId()+"','"+pratica.getAccTipoSogg()+"','"+
					pratica.getResCodiFisc()+"','"+pratica.getResCognome()+"','"+pratica.getResNome()+"')",SOfPratica.class);
			q.executeUpdate();			*/
			
			manager_diogene.persist(pratica);
			logger.info("ID:"+ pratica.getId());
			return pratica.getId();
			
		} catch (Throwable t) {
			logger.error("Errore Inserimento Pratica", t);
			throw new SegnalazioneQualificataServiceException(t);
		}
	}

	@Override
	public void modificaPraticaSegnalazione(PraticaSegnalazioneDTO dto)
			throws SegnalazioneQualificataServiceException {
		try {
			logger.info("MODIFICA PRATICA SEGNALAZIONE QUALIFICATA");

			SOfPratica pratica = dto.getPratica();
			
			manager_diogene.merge(pratica);
			
			Long idPra = pratica.getId();
			
			RicercaPraticaSegnalazioneDTO rps = new RicercaPraticaSegnalazioneDTO(dto.getUserId());
			rps.setIdPra(idPra);
			rps.setEnteId(dto.getEnteId());
			
			SegnalazioniDataIn dataIn = new SegnalazioniDataIn();
			dataIn.setEnteId(dto.getEnteId());
			dataIn.setRicercaPratica(rps);
			
			this.rimuoviFontiPratica(rps);
			this.rimuoviAmbitiPratica(rps);

			// Re-inserisco le nuove fonti
			List<String> fonti = dto.getFonti();
			for (String f : fonti) {
				logger.info("Inserimento fonte[IdPra,fonte]: [" +idPra + ","+ f +"]");
				SOfPratFontePK fontePk = new SOfPratFontePK();
				fontePk.setFkFonte(f);
				fontePk.setFkPratica(idPra);
				SOfPratFonte fonte = new SOfPratFonte();
				fonte.setId(fontePk);
				manager_diogene.persist(fonte);
			}
			

			// Re-inserisco i nuovi ambiti
			List<String> ambiti = dto.getAmbitiAccertamento();
			for (String a : ambiti) {
				logger.info("Inserimento ambito[IdPra,idAmbito]: [" +idPra + ","+ a +"]");
				SOfPratAmbitoPK ambitoPk = new SOfPratAmbitoPK();
				ambitoPk.setFkAmbito(new Long(a));
				ambitoPk.setFkPratica(idPra);
				SOfPratAmbito ambito = new SOfPratAmbito();
				ambito.setId(ambitoPk);
				manager_diogene.persist(ambito);
			}
			
		} catch (Throwable t) {
			logger.error("", t);
			
			throw new SegnalazioneQualificataServiceException(t);
		}
	}

	private int rimuoviFontiPratica(RicercaPraticaSegnalazioneDTO rps)throws SegnalazioneQualificataServiceException {
		int deleted = 0;
		long idPra = rps.getIdPra();
		try {
			logger.info("RIMOZIONE FONTI-PRATICA");
			
			Query q = manager_diogene.createNamedQuery("SOfPratFonte.deleteByIdPra");
			q.setParameter("idPra", idPra);
			deleted = q.executeUpdate();
			logger.info("Record fonti pratica eliminati [" + deleted + "]");
			
		} catch (Throwable t) {
			logger.error("", t);
			
			throw new SegnalazioneQualificataServiceException(t);
		}

		return deleted;
	}

	
	private int rimuoviAmbitiPratica(RicercaPraticaSegnalazioneDTO rps)
			throws SegnalazioneQualificataServiceException {
		int deleted = 0;
		long idPra = rps.getIdPra();
		try {
			logger.info("RIMOZIONE AMBITI-PRATICA");
			Query q = manager_diogene.createNamedQuery("SOfPratAmbito.deleteByIdPra");
			q.setParameter("idPra", idPra);
			deleted = q.executeUpdate();
			logger.info("Record ambiti pratica eliminati [" + deleted + "]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new SegnalazioneQualificataServiceException(t);
		}

		return deleted;
	}

	@Override
	public void cancellaPraticaSegnalazione(RicercaPraticaSegnalazioneDTO ro)
			throws SegnalazioneQualificataServiceException {

		Long idPra = ro.getIdPra();

		try {
			logger.info("RIMOZIONE PRATICA SEGNALAZIONE QUALIFICATA");
			Query q = manager_diogene.createNamedQuery("SOfPratica.deleteByID");
			q.setParameter("idPra", idPra);
			int deleted = q.executeUpdate();
			logger.info("Record pratica eliminati [" + deleted + "]");

			// this.cancellaListaAllegatiPratica(idPra);

		} catch (Throwable t) {
			logger.error("", t);
			throw new SegnalazioneQualificataServiceException(t);
		}
	}

	@Override
	public void inserisciAllegato(SOfPratAllegato allegato)
			throws SegnalazioneQualificataServiceException {
		try {
			logger.info("CREAZIONE ALLEGATO PRATICA SEGNALAZIONE QUALIFICATA");
			allegato.setDataInserimento(getCurrentDate());
			manager_diogene.persist(allegato);
		} catch (Throwable t) {
			logger.error("", t);
			throw new SegnalazioneQualificataServiceException(t);
		}
	}
	
	@Override
	public void rimuoviAllegato(RicercaPraticaSegnalazioneDTO ro){
		
		Long idAll = ro.getIdAll();
		
		try {
			logger.debug("RIMOZIONE ALLEGATO [id: " + idAll + "]");
			Query q = manager_diogene.createNamedQuery("SOfPratAllegato.deleteByID");
			q.setParameter("idAll", idAll);
			int deleted = q.executeUpdate();
			logger.debug("Record allegato eliminati ["+deleted+"]");
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new SegnalazioneQualificataServiceException(t);
		}

	}
	
	@Override
	public void cancellaListaAllegatiPratica(RicercaPraticaSegnalazioneDTO ro){
		
		try {
			logger.debug("RIMOZIONE LISTA ALLEGATI PRATICA [idPra: " + ro.getIdPra() + "]");
			Query q = manager_diogene.createNamedQuery("SOfPratAllegato.deleteByIdPra");
			q.setParameter("idPra", ro.getIdPra());
			int deleted = q.executeUpdate();
			logger.debug("Record allegati eliminati ["+deleted+"]");
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new SegnalazioneQualificataServiceException(t);
		}

	}
	
	@Override
	public SOfPratAllegato getAllegato(RicercaPraticaSegnalazioneDTO ro) {
		Long idAll = ro.getIdAll();
		try {
			logger.debug("ESTRAZIONE ALLEGATO [id: " + idAll + "]");
			return (SOfPratAllegato) manager_diogene.find(SOfPratAllegato.class, idAll);
		}
		catch(Throwable t) {
			logger.error("",t);
			throw new SegnalazioneQualificataServiceException(t);
		}
		
	}
	
	@Override
	public List<SOfPratAllegato> getListaAllegatiPratica(RicercaPraticaSegnalazioneDTO ro){
		List<SOfPratAllegato> allegati = new ArrayList<SOfPratAllegato>();
		long idPra = ro.getIdPra();
		
		try {
			logger.info("Estrazione Allegati Pratica [Id:"+idPra+"]...");
			Query qall = manager_diogene.createNamedQuery("SOfPratAllegato.getAllegatiPratica");
			qall.setParameter("idPratica", idPra);
			allegati = (List<SOfPratAllegato>) qall.getResultList();
			logger.info("Result size ["+allegati.size()+"]");
		} catch (Throwable t) {
			logger.error("",t);
			throw new SegnalazioneQualificataServiceException(t);
		}
		
		return allegati;	
	}
}
