package it.webred.ct.data.access.aggregator.catastoDocfaConcedilizie.dto;

import it.webred.ct.data.access.basic.c336.dto.SearchC336PraticaDTO;
import it.webred.ct.data.access.basic.catasto.dto.CatastoSearchCriteria;
import it.webred.ct.data.access.basic.catasto.dto.ParametriCatastaliDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaSoggettoCatDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.RicercaConcEdilizieDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.RicercaSoggettoConcEdilizieDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class SearchCriteriaDTO extends CeTBaseObject {
	
	private static final long serialVersionUID = 1L;	
	
	private Integer startm;
	private Integer numberRecord;
	
	private ParametriCatastaliDTO uiu;
	
	private CatastoSearchCriteria catOggetto;
	
	private RicercaOggettoDocfaDTO dcfOggetto;
	private RicercaSoggettoDTO dcfDichiarante;
	
	private RicercaConcEdilizieDTO concEdiOggetto;
	private RicercaSoggettoConcEdilizieDTO concEdiSoggetto;
	
	private SearchC336PraticaDTO c336Pratica;
		
	private Boolean faiRicercaInFabbrMaiDich;
	
	private Boolean faiRicercaInFabbrExRurali;
	
	private String tipoRicerca; 
	
	private Boolean faiRicercaInDocfa;
	
	private Boolean faiRicercaInConEdi;
	
	private Boolean faiRicercaInC336Pratica;
	
	public SearchCriteriaDTO(){
		setUiu(new ParametriCatastaliDTO());
		
		catOggetto = new CatastoSearchCriteria();
		
		dcfOggetto =new RicercaOggettoDocfaDTO();
		dcfDichiarante = new RicercaSoggettoDTO();
		
		concEdiOggetto = new RicercaConcEdilizieDTO();
		concEdiSoggetto = new RicercaSoggettoConcEdilizieDTO();
		
		c336Pratica = new SearchC336PraticaDTO();
	}
	
	public CatastoSearchCriteria getCatOggetto() {
		return catOggetto;
	}
	public void setCatOggetto(CatastoSearchCriteria catOggetto) {
		this.catOggetto = catOggetto;
	}
	public RicercaOggettoDocfaDTO getDcfOggetto() {
		return dcfOggetto;
	}
	public void setDcfOggetto(RicercaOggettoDocfaDTO dcfOggetto) {
		this.dcfOggetto = dcfOggetto;
	}
	public RicercaSoggettoDTO getDcfDichiarante() {
		return dcfDichiarante;
	}
	public void setDcfDichiarante(RicercaSoggettoDTO dcfDichiarante) {
		this.dcfDichiarante = dcfDichiarante;
	}
	public RicercaConcEdilizieDTO getConcEdiOggetto() {
		return concEdiOggetto;
	}
	public void setConcEdiOggetto(RicercaConcEdilizieDTO concEdiOggetto) {
		this.concEdiOggetto = concEdiOggetto;
	}
	public RicercaSoggettoConcEdilizieDTO getConcEdiSoggetto() {
		return concEdiSoggetto;
	}
	public void setConcEdiSoggetto(RicercaSoggettoConcEdilizieDTO concEdiSoggetto) {
		this.concEdiSoggetto = concEdiSoggetto;
	}
		
	public void clearConcEdiSoggetto(){
		concEdiSoggetto = new RicercaSoggettoConcEdilizieDTO();
	}

	public void setUiu(ParametriCatastaliDTO uiu) {
		this.uiu = uiu;
	}

	public ParametriCatastaliDTO getUiu() {
		return uiu;
	}

	public Integer getStartm() {
		return startm;
	}

	public void setStartm(Integer startm) {
		this.startm = startm;
	}

	public Integer getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(Integer numberRecord) {
		this.numberRecord = numberRecord;
	}

	
	public Boolean getFaiRicercaInFabbrMaiDich() {
		return faiRicercaInFabbrMaiDich;
	}

	public void setFaiRicercaInFabbrMaiDich(Boolean faiRicercaInFabbrMaiDich) {
		this.faiRicercaInFabbrMaiDich = faiRicercaInFabbrMaiDich;
	}

	public String getTipoRicerca() {
		return tipoRicerca;
	}

	public void setTipoRicerca(String tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}

	public SearchC336PraticaDTO getC336Pratica() {
		return c336Pratica;
	}

	public void setC336Pratica(SearchC336PraticaDTO c336Pratica) {
		this.c336Pratica = c336Pratica;
	}

	public Boolean getFaiRicercaInFabbrExRurali() {
		return faiRicercaInFabbrExRurali;
	}

	public void setFaiRicercaInFabbrExRurali(Boolean faiRicercaInFabbrExRurali) {
		this.faiRicercaInFabbrExRurali = faiRicercaInFabbrExRurali;
	}

	public Boolean getFaiRicercaInDocfa() {
		return faiRicercaInDocfa;
	}

	public void setFaiRicercaInDocfa(Boolean faiRicercaInDocfa) {
		this.faiRicercaInDocfa = faiRicercaInDocfa;
	}

	public Boolean getFaiRicercaInConEdi() {
		return faiRicercaInConEdi;
	}

	public void setFaiRicercaInConEdi(Boolean faiRicercaInConEdi) {
		this.faiRicercaInConEdi = faiRicercaInConEdi;
	}

	public Boolean getFaiRicercaInC336Pratica() {
		return faiRicercaInC336Pratica;
	}

	public void setFaiRicercaInC336Pratica(Boolean faiRicercaInC336Pratica) {
		this.faiRicercaInC336Pratica = faiRicercaInC336Pratica;
	}
	
}
