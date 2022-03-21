package it.webred.ct.data.access.basic.datitecnicifabbricato.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.datitecnicifabbricato.DatiTecniciFabbricatoException;
import it.webred.ct.data.access.basic.datitecnicifabbricato.dto.RicercaDatiTecniciDTO;
import it.webred.ct.data.access.basic.pgt.PgtServiceException;
import it.webred.ct.data.model.datitecnicifabbricato.CertificazioneEnergetica;
import it.webred.ct.data.model.datitecnicifabbricato.CollaudoStatico;
import it.webred.ct.data.model.datitecnicifabbricato.DichiarazioneConformita;
import it.webred.ct.data.model.datitecnicifabbricato.DocumentiTecniciFabbricato;
import it.webred.ct.data.model.pgt.PgtSqlLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class DatiTecniciFabbricatoJPAImpl extends CTServiceBaseDAO 	implements DatiTecniciFabbricatoDAO {

	private static final long serialVersionUID = 1L;

	@Override
	public List<CertificazioneEnergetica> getListaCertificazioniEnergeticheByFP(RicercaOggettoCatDTO ro) {
		List<CertificazioneEnergetica> lista= new ArrayList<CertificazioneEnergetica>();
		logger.debug("getListaCertificazioniEnergeticheByFP - PARAMETRI[SEZIONE:"+ ro.getSezione()+"];[FOGLIO:"+ ro.getFoglio()+"];[PARTICELLA:"+ ro.getParticella()+"]");
		try {
			Query q =null;
			if (ro.getSezione()!=null && !ro.getSezione().equals("")){
				q= manager_diogene.createNamedQuery("CertificazioneEnergetica.getListaBySezFabbricato");
				q.setParameter("sezione", ro.getSezione());
			}				
			else 
				q= manager_diogene.createNamedQuery("CertificazioneEnergetica.getListaByFabbricato");
			q.setParameter("foglio", ro.getFoglio());
			q.setParameter("particella", ro.getParticella());
			lista = (List<CertificazioneEnergetica>)q.getResultList();
			if (lista.size() > 0)
				logger.warn("LISTA CERTIFICAZIONI ENERGETICHE N. ELE: " + lista.size());
		} catch (Throwable t) {
			logger.error("", t);
			throw new DatiTecniciFabbricatoException(t);
		}
		return lista;
	}
	
	@Override
	public CertificazioneEnergetica getCertificazioneEnergeticaById(Long seq) {
		CertificazioneEnergetica  ce = new CertificazioneEnergetica();
		logger.debug("getCertificazioneEnergeticaById - PARAMETRI[ID:"+ seq+"]");
		try {
			Query q =null;
			if (seq!=null){
				q= manager_diogene.createNamedQuery("CertificazioneEnergetica.getCertificazioneEnergeticaById");
				q.setParameter("uid", seq);
			}				
			ce = (CertificazioneEnergetica)q.getSingleResult();
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new DatiTecniciFabbricatoException(t);
		}
		return ce;
	}//-------------------------------------------------------------------------

	@Override
	public List<CollaudoStatico> getListaCollaudoStaticoByFP(RicercaOggettoCatDTO ro) {
		List<CollaudoStatico> lista= new ArrayList<CollaudoStatico>();
		logger.debug("getListaCollaudoStaticoByFP - PARAMETRI[SEZIONE:"+ ro.getSezione()+"];[FOGLIO:"+ ro.getFoglio()+"];[PARTICELLA:"+ ro.getParticella()+"]");
		try {
			Query q =null;
			if (ro.getSezione()!=null && !ro.getSezione().equals("")){
				q= manager_diogene.createNamedQuery("CollaudoStatico.getListaBySezFabbricato");
				q.setParameter("sezione", ro.getSezione());
			}				
			else 
				q= manager_diogene.createNamedQuery("CollaudoStatico.getListaByFabbricato");
			q.setParameter("foglio", ro.getFoglio());
			q.setParameter("particella", ro.getParticella());
			lista = (List<CollaudoStatico>)q.getResultList();
			if (lista.size() > 0)
				logger.warn("LISTA COLLAUDO STATICO N. ELE: " + lista.size());
		} catch (Throwable t) {
			logger.error("", t);
			throw new DatiTecniciFabbricatoException(t);
		}
		return lista;
	}

	@Override
	public List<DichiarazioneConformita> getListaDichiarazioneConformitaByFP(RicercaOggettoCatDTO ro) {
		List<DichiarazioneConformita> lista= new ArrayList<DichiarazioneConformita>();
		logger.debug("getListaDichiarazioneConformitaByFP - PARAMETRI[SEZIONE:"+ ro.getSezione()+"];[FOGLIO:"+ ro.getFoglio()+"];[PARTICELLA:"+ ro.getParticella()+"]");
		try {
			Query q =null;
			if (ro.getSezione()!=null && !ro.getSezione().equals("")){
				q= manager_diogene.createNamedQuery("DichiarazioneConformita.getListaBySezFabbricato");
				q.setParameter("sezione", ro.getSezione());
			}				
			else 
				q= manager_diogene.createNamedQuery("DichiarazioneConformita.getListaByFabbricato");
			q.setParameter("foglio", ro.getFoglio());
			q.setParameter("particella", ro.getParticella());
			lista = (List<DichiarazioneConformita>)q.getResultList();
			if (lista.size() > 0)
				logger.warn("LISTA DICHIARAZIONE CONFORMITA N. ELE: " + lista.size());
		} catch (Throwable t) {
			logger.error("", t);
			throw new DatiTecniciFabbricatoException(t);
		}
		return lista;
	}

	@Override
	public List<DocumentiTecniciFabbricato> getListaDocumentiByIdDati(RicercaDatiTecniciDTO rd) {
		List<DocumentiTecniciFabbricato> lista= new ArrayList<DocumentiTecniciFabbricato>();
		logger.debug("getListaDocumentiByIdDati - PARAMETRI[ID_DATI:"+ rd.getIdDati()+"];[tipo_dati:"+ rd.getTipoDati()+"]");
		try {
			Query q = manager_diogene.createNamedQuery("DocumentiTecniciFabbricato.getListaByIdDati");
			q.setParameter("idDati", rd.getIdDati());
			q.setParameter("tipoDati", rd.getTipoDati());
			lista = (List<DocumentiTecniciFabbricato>)q.getResultList();
			if (lista.size() > 0)
				logger.warn("LISTA DOCUMENTI - N. ELE: " + lista.size());
		} catch (Throwable t) {
			logger.error("", t);
			throw new DatiTecniciFabbricatoException(t);
		}
		return lista;
	}

}
