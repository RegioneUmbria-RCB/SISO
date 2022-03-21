package it.webred.ct.data.access.basic.datitecnicifabbricato;

import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.datitecnicifabbricato.dto.RicercaDatiTecniciDTO;
import it.webred.ct.data.model.datitecnicifabbricato.CertificazioneEnergetica;
import it.webred.ct.data.model.datitecnicifabbricato.CollaudoStatico;
import it.webred.ct.data.model.datitecnicifabbricato.DichiarazioneConformita;
import it.webred.ct.data.model.datitecnicifabbricato.DocumentiTecniciFabbricato;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface DatiTecniciFabbricatoService {
	public List<CertificazioneEnergetica> getListaCertificazioniEnergeticheByFP(RicercaOggettoCatDTO ro) throws DatiTecniciFabbricatoException;
	public List<CollaudoStatico> getListaCollaudoStaticoByFP(RicercaOggettoCatDTO ro) throws DatiTecniciFabbricatoException;
	public List<DichiarazioneConformita> getListaDichiarazioneConformitaByFP(RicercaOggettoCatDTO ro) throws DatiTecniciFabbricatoException;
	public List<DocumentiTecniciFabbricato> getListaDocumentiByIdDati(RicercaDatiTecniciDTO rd) throws DatiTecniciFabbricatoException;
	public CertificazioneEnergetica getCertificazioneEnergeticaById(Long uid) throws DatiTecniciFabbricatoException;
}
