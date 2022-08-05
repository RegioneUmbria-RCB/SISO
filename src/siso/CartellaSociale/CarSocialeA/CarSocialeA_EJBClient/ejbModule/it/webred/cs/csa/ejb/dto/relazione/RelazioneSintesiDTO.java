package it.webred.cs.csa.ejb.dto.relazione;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class RelazioneSintesiDTO implements Serializable {
	
	private Long diarioId;
	private Date dtAmministrativa;
	private String situazioneAmbientale;
	private String situazioneParentale;
	private String situazioneSanitaria;
	private String proposta;
	private String descMacroAttivita;
	private String descMicroAttivita;
	private int tipoFormMicroAttivita;
	private List<Long> paiCollegati;
	
	public Long getDiarioId() {
		return diarioId;
	}
	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}
	public Date getDtAmministrativa() {
		return dtAmministrativa;
	}
	public void setDtAmministrativa(Date dtAmministrativa) {
		this.dtAmministrativa = dtAmministrativa;
	}
	public String getSituazioneAmbientale() {
		return situazioneAmbientale;
	}
	public void setSituazioneAmbientale(String situazioneAmbientale) {
		this.situazioneAmbientale = situazioneAmbientale;
	}
	public String getSituazioneParentale() {
		return situazioneParentale;
	}
	public void setSituazioneParentale(String situazioneParentale) {
		this.situazioneParentale = situazioneParentale;
	}
	public String getSituazioneSanitaria() {
		return situazioneSanitaria;
	}
	public void setSituazioneSanitaria(String situazioneSanitaria) {
		this.situazioneSanitaria = situazioneSanitaria;
	}
	public String getProposta() {
		return proposta;
	}
	public void setProposta(String proposta) {
		this.proposta = proposta;
	}
	public String getDescMacroAttivita() {
		return descMacroAttivita;
	}
	public void setDescMacroAttivita(String descMacroAttivita) {
		this.descMacroAttivita = descMacroAttivita;
	}
	public List<Long> getPaiCollegati() {
		return paiCollegati;
	}
	public void setPaiCollegati(List<Long> paiCollegati) {
		this.paiCollegati = paiCollegati;
	}
	public String getDescMicroAttivita() {
		return descMicroAttivita;
	}
	public void setDescMicroAttivita(String descMicroAttivita) {
		this.descMicroAttivita = descMicroAttivita;
	}
	public int getTipoFormMicroAttivita() {
		return tipoFormMicroAttivita;
	}
	public void setTipoFormMicroAttivita(int tipoFormMicroAttivita) {
		this.tipoFormMicroAttivita = tipoFormMicroAttivita;
	}
	public String getSituazioneAmbientaleTrunc(){
		return this.truncate(this.situazioneAmbientale);
	}
	public String getSituazioneParentaleTrunc(){
		return this.truncate(this.situazioneParentale);
	}
	public String getSituazioneSanitariaTrunc(){
		return this.truncate(this.situazioneSanitaria);
	}
	public String getPropostaTrunc(){
		return this.truncate(this.proposta);
	}
	public String getAttivita(){
		String s = "";
		s = this.descMacroAttivita!=null ? this.descMacroAttivita : "...";
		s+= " "+(this.descMicroAttivita!=null ? this.descMicroAttivita : "...");
		return s;
	}
	
	private String truncate(String s){
		if(s!=null && s.length()>20)
			s = s.substring(0, 20).concat("...");
		return s;
	}
	
	public boolean isRelatedToPai(Long paiDiarioId){
		if(paiDiarioId!=null){
			for (Long curr : this.paiCollegati){
				if(curr.equals(paiDiarioId.longValue()))
					return true;
			}
		}
		return false;
	}
	
	public String getLabelConcatAll(){
		String s = !StringUtils.isBlank(this.situazioneAmbientale) ? this.situazioneAmbientale +", ": "";
		s+= !StringUtils.isBlank(this.situazioneParentale) ? this.situazioneParentale +", ": "";
		s+= !StringUtils.isBlank(this.situazioneSanitaria) ? this.situazioneSanitaria +", ": "";
		s+= !StringUtils.isBlank(this.proposta) ? this.proposta: "";
		
		return s;
	}
}
