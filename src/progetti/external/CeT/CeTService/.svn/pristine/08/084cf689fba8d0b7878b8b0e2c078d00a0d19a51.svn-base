package it.webred.ct.data.access.basic.anagrafe.dao;

import it.webred.ct.data.access.basic.anagrafe.AnagrafeException;
import it.webred.ct.data.access.basic.anagrafe.dto.AnagraficaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.ComuneProvinciaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.DatiCivicoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaIndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaLuogoDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.SoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.AttrPersonaDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.model.anagrafe.SitComune;
import it.webred.ct.data.model.anagrafe.SitDCivicoV;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.anagrafe.SitDPersonaCivico;
import it.webred.ct.data.model.anagrafe.SitDStaciv;
import it.webred.ct.data.model.anagrafe.SitDVia;
import it.webred.ct.data.model.anagrafe.SitProvincia;
import it.webred.ct.data.model.common.SitEnte;

import java.util.Date;
import java.util.List;

public interface AnagrafeDAO {
	public SitDPersona getPersonaById(RicercaSoggettoAnagrafeDTO rs)
			throws AnagrafeException;

	public List<SitDPersona> getListaPersoneByCF(RicercaSoggettoAnagrafeDTO rs)
			throws AnagrafeException;

	public List<SitDPersona> getListaPersoneByCFAllaData(
			RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException;

	public List<SitDPersona> getListaPersoneByDatiAna(
			RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException;

	public List<SitDPersona> getListaPersoneByDatiAnaAllaData(
			RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException;

	public List<SitDPersona> getVariazioniPersonaByIdExt(
			RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException;

	public AnagraficaDTO getPersonaFamigliaByCF(RicercaSoggettoAnagrafeDTO rs);

	public List<SitDPersona> getFamigliaByCF(RicercaSoggettoAnagrafeDTO rs);
	
	public List<SitDPersona> getFamigliaByCogNomDtNascita(RicercaSoggettoAnagrafeDTO rs);

	public List<SitDPersonaCivico> getListaPersCivByIdExt(
			RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException;

	public List<SitDCivicoV> getListaCiviciByIdExt(
			RicercaIndirizzoAnagrafeDTO ri) throws AnagrafeException;

	public List<SitDVia> getListaVieByIdExt(RicercaIndirizzoAnagrafeDTO ri)
			throws AnagrafeException;

	public List<SoggettoAnagrafeDTO> searchSoggetto(
			RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException;

	public List<ComponenteFamigliaDTO> getCompFamigliaByIdExtDPersona(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException;

	public List<ComponenteFamigliaDTO> getCompFamigliaByCodFis(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException;
	
	public List<ComponenteFamigliaDTO> getResidentiAlCivico(
			RicercaIndirizzoAnagrafeDTO ri) throws AnagrafeException;

	public IndirizzoAnagrafeDTO getIndirizzo(RicercaIndirizzoAnagrafeDTO ri)
			throws AnagrafeException;

	public List<SitComune> getComuni(RicercaLuogoDTO rl)
			throws AnagrafeException;
	
	public SitComune belfioreToComune(RicercaLuogoDTO rl) throws AnagrafeException;
	
	public SitComune descrizioneToComune(RicercaLuogoDTO rl) throws AnagrafeException;

	public SitComune getComune(RicercaLuogoDTO rl) throws AnagrafeException;

	public List<SitProvincia> getProvincie(RicercaLuogoDTO rl)
			throws AnagrafeException;

	public SitProvincia getProvincia(RicercaLuogoDTO rl)throws AnagrafeException;

	List<AnagraficaDTO> getAnagrafeByCF(RicercaSoggettoDTO rs)throws AnagrafeException;
	
	public void fillInfoAggiuntive(AnagraficaDTO anagrafica);

	public AttrPersonaDTO getAttributiPersonaByCF(RicercaSoggettoAnagrafeDTO rs);

	public String getIdCivicoByIndirizzo(RicercaCivicoDTO rc);

	public SitDVia getViaByPrefissoDescr(RicercaCivicoDTO rc);

	public DatiCivicoAnagrafeDTO getDatiCivicoAnagrafe(RicercaCivicoDTO rc);

	public Long getNumeroFamiglieResidentiAlCivico(String idCivico, Date dtRif);

	public ComuneProvinciaDTO getDescrizioneComuneProvByIdExt(String idExtComune);

	public boolean verificaResidenzaByCFAllaData(RicercaSoggettoAnagrafeDTO rs);
	
	public boolean verificaResidenzaByCogNomDtNascAllaData(RicercaSoggettoAnagrafeDTO rs);

	public SitEnte getEnteByDescrizione(RicercaLuogoDTO rl) throws AnagrafeException;

	public String getRelazioneParentelaByIdPersona(String id);
	
	public List<Object[]> getIndirizzoResidenzaByCodFisc(RicercaSoggettoAnagrafeDTO rs);
	
	public List<Object[]> getIndirizzoResidenzaByNomeCiv(RicercaCivicoDTO rc);
	
	public List<SitDVia> getIndirizziLike(RicercaIndirizzoAnagrafeDTO ri);

	public List<SitDCivicoV> getCivicoByIdExtDVia(RicercaIndirizzoAnagrafeDTO ri);
	
	public List<SitDStaciv> getListaStatoCivile();

	public List<SitDPersona> getListaPersoneByDenominazione(RicercaSoggettoAnagrafeDTO rs) throws AnagrafeException;
}
