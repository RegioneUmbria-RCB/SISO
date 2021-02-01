package it.webred.ct.data.access.basic.anagrafe;

import it.webred.ct.data.access.basic.anagrafe.dto.AnagraficaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.AttrPersonaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.ComuneProvinciaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.DatiCivicoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.DatiPersonaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaIndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaLuogoDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.SoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.StatoCivile;
import it.webred.ct.data.access.basic.common.CommonDataIn;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.model.anagrafe.SitComune;
import it.webred.ct.data.model.anagrafe.SitDCivicoV;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.anagrafe.SitDVia;
import it.webred.ct.data.model.anagrafe.SitProvincia;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AnagrafeService {
	public SitDPersona getPersonaById(RicercaSoggettoAnagrafeDTO rs);

	public DatiPersonaDTO getDatiPersonaById(RicercaSoggettoAnagrafeDTO rs);

	public List<SitDPersona> getListaPersoneByCF(RicercaSoggettoAnagrafeDTO rs);

	public List<SitDPersona> getListaPersoneByDatiAna(
			RicercaSoggettoAnagrafeDTO rs);

	public String getIdPersonaByCF(RicercaSoggettoAnagrafeDTO rs);

	public List<SitDPersona> getVariazioniPersonaByIdExt(
			RicercaSoggettoAnagrafeDTO rs);

	public AnagraficaDTO getPersonaFamigliaByCF(RicercaSoggettoAnagrafeDTO rs);
	
	public List<SitDPersona> getFamigliaByCogNomDtNascita(RicercaSoggettoAnagrafeDTO rs);
	
	public 	List<SitDPersona> getFamigliaByCF(RicercaSoggettoAnagrafeDTO rs);
	
	public IndirizzoAnagrafeDTO getIndirizzoPersona(
			RicercaSoggettoAnagrafeDTO rs);

	public List<SoggettoAnagrafeDTO> searchSoggetto(
			RicercaSoggettoAnagrafeDTO rs);

	public List<ComponenteFamigliaDTO> getListaCompFamiglia(
			RicercaSoggettoAnagrafeDTO rs);
	
	public List<ComponenteFamigliaDTO> getListaCompFamigliaInfoAggiuntiveByCf(
			RicercaSoggettoAnagrafeDTO rs);

	public List<ComponenteFamigliaDTO> getResidentiAlCivico(
			RicercaIndirizzoAnagrafeDTO ri);

	//
	public IndirizzoAnagrafeDTO getIndirizzo(RicercaIndirizzoAnagrafeDTO ri);

	public List<AnagraficaDTO> getAnagrafeByCF(RicercaSoggettoDTO rs)
			throws AnagrafeException;

	// comuni e provincie
	public List<SitComune> getComuni(RicercaLuogoDTO rl);

	public SitComune getComune(RicercaLuogoDTO rl);
	
	public 	SitComune belfioreToComune(RicercaLuogoDTO rl);

	public List<SitProvincia> getProvincie(RicercaLuogoDTO rl);
	
	public SitProvincia getProvincia(RicercaLuogoDTO rl);
	
	public AttrPersonaDTO getAttributiPersonaByCF(RicercaSoggettoAnagrafeDTO rs);
	
	//Dati Anagrafe per Civico
	public String getIdCivicoByIndirizzo(RicercaCivicoDTO rc);
	
	public SitDVia getViaByPrefissoDescr(RicercaCivicoDTO rc);
	
	public DatiCivicoAnagrafeDTO getDatiCivicoAnagrafe(RicercaCivicoDTO rc);
	
	public Long getNumeroFamiglieResidentiAlCivico(RicercaCivicoDTO rc);

	public boolean checkResidenza(RicercaLuogoDTO rs);

	public ComuneProvinciaDTO getDescrizioneComuneProvByIdExt(CommonDataIn dataIn);
	
	public boolean verificaResidenzaByCFAllaData(RicercaSoggettoAnagrafeDTO rs);
	
	public boolean verificaResidenzaByCogNomDtNascAllaData(RicercaSoggettoAnagrafeDTO rs);

	public String getRelazioneParentelaByIdPersona(RicercaSoggettoAnagrafeDTO rs);
	
	public List<Object[]> getIndirizzoResidenzaByCodFisc(RicercaSoggettoAnagrafeDTO rs);

	public List<Object[]> getIndirizzoResidenzaByNomeCiv(RicercaCivicoDTO rc);
	
	public List<SitDVia> getIndirizziLike(RicercaIndirizzoAnagrafeDTO ri);

	public List<SitDCivicoV> getCivicoByIdExtDVia(RicercaIndirizzoAnagrafeDTO ri);

	public List<StatoCivile> getListaStatoCivile(CeTBaseObject cet);

	public List<SitDPersona> getListaPersoneByDenominazione(RicercaSoggettoAnagrafeDTO rs);

	public ComponenteFamigliaDTO fillInfoAggiuntiveComponente(ComponenteFamigliaDTO componente);

}
