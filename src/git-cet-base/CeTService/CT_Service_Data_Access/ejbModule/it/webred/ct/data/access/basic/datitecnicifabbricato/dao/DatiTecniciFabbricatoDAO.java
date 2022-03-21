package it.webred.ct.data.access.basic.datitecnicifabbricato.dao;

import java.util.List;

import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.datitecnicifabbricato.DatiTecniciFabbricatoService;
import it.webred.ct.data.access.basic.datitecnicifabbricato.dto.RicercaDatiTecniciDTO;
import it.webred.ct.data.access.basic.pgt.dto.RicercaPgtDTO;
import it.webred.ct.data.model.datitecnicifabbricato.CertificazioneEnergetica;
import it.webred.ct.data.model.datitecnicifabbricato.CollaudoStatico;
import it.webred.ct.data.model.datitecnicifabbricato.DichiarazioneConformita;
import it.webred.ct.data.model.datitecnicifabbricato.DocumentiTecniciFabbricato;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public interface DatiTecniciFabbricatoDAO  {
	public List<CertificazioneEnergetica> getListaCertificazioniEnergeticheByFP(RicercaOggettoCatDTO ro) ;
	public List<CollaudoStatico> getListaCollaudoStaticoByFP(RicercaOggettoCatDTO ro) ;
	public List<DichiarazioneConformita> getListaDichiarazioneConformitaByFP(RicercaOggettoCatDTO ro) ;
	public List<DocumentiTecniciFabbricato> getListaDocumentiByIdDati(RicercaDatiTecniciDTO rd) ;
	public CertificazioneEnergetica getCertificazioneEnergeticaById(Long seq);
}
