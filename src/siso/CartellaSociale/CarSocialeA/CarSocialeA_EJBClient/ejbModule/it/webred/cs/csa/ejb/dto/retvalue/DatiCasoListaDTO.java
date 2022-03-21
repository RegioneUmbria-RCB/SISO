package it.webred.cs.csa.ejb.dto.retvalue;

import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.listaCasi.OperatoreListaCasiDTO;
import it.webred.cs.csa.ejb.dto.listaCasi.UnitaOrganizzativaDTO;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class DatiCasoListaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Date dataApertura;
	private String residenza;
	
	private Long anagraficaId;
	private Long casoId;
	private Long identificativo;
	private String denominazione;
	private Date dataNascita;
	private String cf;
	private Boolean existsInterventi;
	
	/*Oggetti caricati fuori dal DAO tramite ForkJoinTask*/
	private List<OperatoreListaCasiDTO> operatori;
	private CsIterStepByCasoDTO lastIterStep;
	private List<UnitaOrganizzativaDTO> listaAccessoFascicolo;
	private List<InterventoBaseDTO> interventiProgrammati;
	private List<ErogazioneBaseDTO> erogazioni;
	private Boolean nucleoBeneficiarioRdC;
	private List<CsASoggettoCategoriaSoc> listaCatSociale;
	private Boolean datiEsterniFound;
	
	
	public Date getDataApertura() {
		return dataApertura;
	}
	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}
	public String getResidenza() {
		return residenza;
	}
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}
	public Long getAnagraficaId() {
		return anagraficaId;
	}
	public void setAnagraficaId(Long anagraficaId) {
		this.anagraficaId = anagraficaId;
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
	public Boolean getExistsInterventi() {
		return existsInterventi;
	}
	public void setExistsInterventi(Boolean existsInterventi) {
		this.existsInterventi = existsInterventi;
	}
	public List<OperatoreListaCasiDTO> getOperatori() {
		return operatori;
	}
	public void setOperatori(List<OperatoreListaCasiDTO> operatori) {
		this.operatori = operatori;
	}
	public CsIterStepByCasoDTO getLastIterStep() {
		return lastIterStep;
	}
	public void setLastIterStep(CsIterStepByCasoDTO lastIterStep) {
		this.lastIterStep = lastIterStep;
	}
	public List<UnitaOrganizzativaDTO> getListaAccessoFascicolo() {
		return listaAccessoFascicolo;
	}
	public void setListaAccessoFascicolo(List<UnitaOrganizzativaDTO> listaAccessoFascicolo) {
		this.listaAccessoFascicolo = listaAccessoFascicolo;
	}
	public List<InterventoBaseDTO> getInterventiProgrammati() {
		return interventiProgrammati;
	}
	public void setInterventiProgrammati(List<InterventoBaseDTO> interventiProgrammati) {
		this.interventiProgrammati = interventiProgrammati;
	}
	public List<ErogazioneBaseDTO> getErogazioni() {
		return erogazioni;
	}
	public void setErogazioni(List<ErogazioneBaseDTO> erogazioni) {
		this.erogazioni = erogazioni;
	}
	public Boolean getNucleoBeneficiarioRdC() {
		return nucleoBeneficiarioRdC;
	}
	public void setNucleoBeneficiarioRdC(Boolean nucleoBeneficiarioRdC) {
		this.nucleoBeneficiarioRdC = nucleoBeneficiarioRdC;
	}
	public List<CsASoggettoCategoriaSoc> getListaCatSociale() {
		return listaCatSociale;
	}
	public void setListaCatSociale(List<CsASoggettoCategoriaSoc> listaCatSociale) {
		this.listaCatSociale = listaCatSociale;
	}
	public Boolean getDatiEsterniFound() {
		return datiEsterniFound;
	}
	public void setDatiEsterniFound(Boolean datiEsterniFound) {
		this.datiEsterniFound = datiEsterniFound;
	}
}
