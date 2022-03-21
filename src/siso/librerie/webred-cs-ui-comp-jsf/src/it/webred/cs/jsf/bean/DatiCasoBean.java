package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.listaCasi.OperatoreListaCasiDTO;
import it.webred.cs.csa.ejb.dto.listaCasi.UnitaOrganizzativaDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.data.DataModelCostanti.IterStatoInfo;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatiCasoBean {
	
	private OperatoreListaCasiDTO responsabile;
	private IterInfoStatoMan lastIterStepInfo;
	private String nInterventi;
	private List<InterventoBaseDTO> listaInterventi;
	private List<ErogazioneBaseDTO> listaErogazioni;
	private List<CsASoggettoCategoriaSoc> listaCatSociale;
	private Date dataApertura;
	private List<OperatoreListaCasiDTO> lstOperatori;
	private boolean nucleoBeneficiarioRdC;
	private List<UnitaOrganizzativaDTO> lstAccessoFascicolo;
	
	//Dati Soggetto
	private Long anagraficaId;
	private Long casoId;
	private Long identificativo;
	private String denominazione;
	private Date dataNascita;
	private String cf;
	private String residenza;
	//SISO-1532
	private boolean datiEsterniFound = false;
	
	public DatiCasoBean(DatiCasoListaDTO dc){
		this.anagraficaId = dc.getAnagraficaId();
		this.casoId = dc.getCasoId();
		this.identificativo = dc.getIdentificativo();
		this.denominazione = dc.getDenominazione();
		this.dataNascita = dc.getDataNascita();
		this.cf = dc.getCf();
		this.residenza = dc.getResidenza();
		
		this.lstOperatori = new ArrayList<OperatoreListaCasiDTO>();
		
		lastIterStepInfo = new IterInfoStatoMan();
		if( dc.getLastIterStep() != null ) {
			lastIterStepInfo.initialize(dc.getLastIterStep());
			if(dc.getLastIterStep().getIdStatoIter()!=IterStatoInfo.APERTO)
				dataApertura = dc.getDataApertura();
		}
		
		int i = 0;
		this.setResponsabile(null);
		while(i<dc.getOperatori().size()){
			OperatoreListaCasiDTO ope  = dc.getOperatori().get(i);
			if(ope.isResponsabile()) {
				setResponsabile(ope);
			}
			else if(!ope.getDataFineApp().before(new Date())) {
				lstOperatori.add(ope);
				}
			i++;
		}
		
		lstAccessoFascicolo = dc.getListaAccessoFascicolo();
		
		nInterventi = Integer.toString(dc.getInterventiProgrammati().size());
		listaInterventi = dc.getInterventiProgrammati();
		listaErogazioni = dc.getErogazioni();
		listaCatSociale = dc.getListaCatSociale();
		nucleoBeneficiarioRdC = dc.getNucleoBeneficiarioRdC();
		datiEsterniFound = dc.getDatiEsterniFound();
		
	}
	

	public boolean isDatiEsterniFound() {
		return datiEsterniFound;
	}


	public void setDatiEsterniFound(boolean datiEsterniFound) {
		this.datiEsterniFound = datiEsterniFound;
	}



	public IterInfoStatoMan getLastIterStepInfo() {
		return lastIterStepInfo;
	}

	public void setLastIterStepInfo(IterInfoStatoMan lastIterStepInfo) {
		this.lastIterStepInfo = lastIterStepInfo;
	}

	public String getnInterventi() {
		return nInterventi;
	}

	public void setnInterventi(String nInterventi) {
		this.nInterventi = nInterventi;
	}

	public List<CsASoggettoCategoriaSoc> getListaCatSociale() {
		return listaCatSociale;
	}

	public void setListaCatSociale(List<CsASoggettoCategoriaSoc> listaCatSociale) {
		this.listaCatSociale = listaCatSociale;
	}
	
	public String getCategoriaPrevalente(){
		boolean trovato = false;
		int i=0;
		CsASoggettoCategoriaSoc prevalente=null;
		while(i<this.listaCatSociale.size() && !trovato){
			CsASoggettoCategoriaSoc cs = this.listaCatSociale.get(i);
			if(cs.getPrevalente().intValue()==1){
				trovato=true;
				prevalente = cs;
			}
			i++;
		}
		
		if(prevalente!=null)
			return prevalente.getCsCCategoriaSociale().getTooltip();
		else 
			return null;
		
	}
	
	public String getCategorieSecondarie(){
		String catSoc = "";
		for(CsASoggettoCategoriaSoc soggCat: this.listaCatSociale) {
			if(soggCat.getPrevalente().intValue()!=1)
			    catSoc += ", " + soggCat.getCsCCategoriaSociale().getTooltip();
		}
		if(catSoc.length() > 1)
		   return catSoc.substring(2);
		else
		   return null;
	}

	public Date getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}

	public List<InterventoBaseDTO> getListaInterventi() {
		return listaInterventi;
	}

	public List<ErogazioneBaseDTO> getListaErogazioni() {
		return listaErogazioni;
	}

	public void setListaInterventi(List<InterventoBaseDTO> listaInterventi) {
		this.listaInterventi = listaInterventi;
	}

	public void setListaErogazioni(List<ErogazioneBaseDTO> listaErogazioni) {
		this.listaErogazioni = listaErogazioni;
	}

	public boolean isNucleoBeneficiarioRdC() {
		return nucleoBeneficiarioRdC;
	}

	public void setNucleoBeneficiarioRdC(boolean nucleoBeneficiarioRdC) {
		this.nucleoBeneficiarioRdC = nucleoBeneficiarioRdC;
	}
	public List<UnitaOrganizzativaDTO> getLstAccessoFascicolo() {
		return lstAccessoFascicolo;
	}

	public void setLstAccessoFascicolo(List<UnitaOrganizzativaDTO> lstAccessoFascicolo) {
		this.lstAccessoFascicolo = lstAccessoFascicolo;
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}

	public Long getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Long getAnagraficaId() {
		return anagraficaId;
	}

	public void setAnagraficaId(Long anagraficaId) {
		this.anagraficaId = anagraficaId;
	}


	public OperatoreListaCasiDTO getResponsabile() {
		return responsabile;
	}


	public void setResponsabile(OperatoreListaCasiDTO responsabile) {
		this.responsabile = responsabile;
	}


	public List<OperatoreListaCasiDTO> getLstOperatori() {
		return lstOperatori;
	}


	public void setLstOperatori(List<OperatoreListaCasiDTO> lstOperatori) {
		this.lstOperatori = lstOperatori;
	}
	
}
