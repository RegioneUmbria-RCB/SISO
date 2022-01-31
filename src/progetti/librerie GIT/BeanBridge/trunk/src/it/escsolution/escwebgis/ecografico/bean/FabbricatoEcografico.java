package it.escsolution.escwebgis.ecografico.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;

public class FabbricatoEcografico extends EscObject  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String pkSequFabbricato;  
	private String pkSequStoFabbricato;      
	private String ukCodiFabbricato;     
	private String codiceEcografico;    
	private String fkComuniBelf;     
	private String comune;   
	private String ukParticellaCt;     
	private String sezioneCt;    
	private String foglioCt;    
	private String particellaCt;    
	private String statoAccatastamento;   
	private String superficie;   
	private String altezza;   
	private String volume;   
	private String nPianiFt;     
	private String nPianiEt;     
	private String destinazioneUso;    
	private String statoCostruito;    
	private String statoConservazione;    
	private String annoCostruzione;    
	private String origine;   
	private String note;   
	private String xcentroid;
	private String ycentroid;
	private String nConcEdilizia;     
	private String nConcEdiliziaRil; 
	private String codiceCensimento;    
	private String dataRilievo;    
	private String causaNonRilevazione;     
	private String noteRilievo;
	
	private String latitudine;
	private String longitudine;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPkSequFabbricato() {
		return pkSequFabbricato;
	}
	public void setPkSequFabbricato(String pkSequFabbricato) {
		this.pkSequFabbricato = pkSequFabbricato;
	}
	public String getPkSequStoFabbricato() {
		return pkSequStoFabbricato;
	}
	public void setPkSequStoFabbricato(String pkSequStoFabbricato) {
		this.pkSequStoFabbricato = pkSequStoFabbricato;
	}
	public String getUkCodiFabbricato() {
		return ukCodiFabbricato;
	}
	public void setUkCodiFabbricato(String ukCodiFabbricato) {
		this.ukCodiFabbricato = ukCodiFabbricato;
	}
	public String getCodiceEcografico() {
		return codiceEcografico;
	}
	public void setCodiceEcografico(String codiceEcografico) {
		this.codiceEcografico = codiceEcografico;
	}
	public String getFkComuniBelf() {
		return fkComuniBelf;
	}
	public void setFkComuniBelf(String fkComuniBelf) {
		this.fkComuniBelf = fkComuniBelf;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getUkParticellaCt() {
		return ukParticellaCt;
	}
	public void setUkParticellaCt(String ukParticellaCt) {
		this.ukParticellaCt = ukParticellaCt;
	}
	public String getSezioneCt() {
		return sezioneCt;
	}
	public void setSezioneCt(String sezioneCt) {
		this.sezioneCt = sezioneCt;
	}
	public String getFoglioCt() {
		return foglioCt;
	}
	public void setFoglioCt(String foglioCt) {
		this.foglioCt = foglioCt;
	}
	public String getParticellaCt() {
		return particellaCt;
	}
	public void setParticellaCt(String particellaCt) {
		this.particellaCt = particellaCt;
	}
	public String getStatoAccatastamento() {
		return statoAccatastamento;
	}
	public void setStatoAccatastamento(String statoAccatastamento) {
		this.statoAccatastamento = statoAccatastamento;
	}
	public String getSuperficie() {
		return superficie;
	}
	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}
	public String getAltezza() {
		return altezza;
	}
	public void setAltezza(String altezza) {
		this.altezza = altezza;
	}
	public String getVolume() {
		return volume;
	}
	public String getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}
	public String getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getnPianiFt() {
		return nPianiFt;
	}
	public void setnPianiFt(String nPianiFt) {
		this.nPianiFt = nPianiFt;
	}
	public String getnPianiEt() {
		return nPianiEt;
	}
	public void setnPianiEt(String nPianiEt) {
		this.nPianiEt = nPianiEt;
	}
	public String getDestinazioneUso() {
		return destinazioneUso;
	}
	public void setDestinazioneUso(String destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}
	public String getStatoCostruito() {
		return statoCostruito;
	}
	public void setStatoCostruito(String statoCostruito) {
		this.statoCostruito = statoCostruito;
	}
	public String getStatoConservazione() {
		return statoConservazione;
	}
	public void setStatoConservazione(String statoConservazione) {
		this.statoConservazione = statoConservazione;
	}
	public String getAnnoCostruzione() {
		return annoCostruzione;
	}
	public void setAnnoCostruzione(String annoCostruzione) {
		this.annoCostruzione = annoCostruzione;
	}
	public String getOrigine() {
		return origine;
	}
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getnConcEdilizia() {
		return nConcEdilizia;
	}
	public void setnConcEdilizia(String nConcEdilizia) {
		this.nConcEdilizia = nConcEdilizia;
	}
	public String getnConcEdiliziaRil() {
		return nConcEdiliziaRil;
	}
	public void setnConcEdiliziaRil(String nConcEdiliziaRil) {
		this.nConcEdiliziaRil = nConcEdiliziaRil;
	}
	public String getCodiceCensimento() {
		return codiceCensimento;
	}
	public void setCodiceCensimento(String codiceCensimento) {
		this.codiceCensimento = codiceCensimento;
	}
	public String getDataRilievo() {
		return dataRilievo;
	}
	public void setDataRilievo(String dataRilievo) {
		this.dataRilievo = dataRilievo;
	}
	public String getCausaNonRilevazione() {
		return causaNonRilevazione;
	}
	public void setCausaNonRilevazione(String causaNonRilevazione) {
		this.causaNonRilevazione = causaNonRilevazione;
	}
	public String getNoteRilievo() {
		return noteRilievo;
	}
	public void setNoteRilievo(String noteRilievo) {
		this.noteRilievo = noteRilievo;
	}
	public String getXcentroid() {
		return xcentroid;
	}
	public void setXcentroid(String xcentroid) {
		this.xcentroid = xcentroid;
	}
	public String getYcentroid() {
		return ycentroid;
	}
	public void setYcentroid(String ycentroid) {
		this.ycentroid = ycentroid;
	}
	

}
