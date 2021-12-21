package it.umbriadigitale.soclav.service.dto.sap.lavoratore.esperienza;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class EsperienzaLavoro implements Serializable {
		
	private Azienda azienda;
	private Date datainizio;
	private Date datafine;
	private String codprofessione;
	
	private TipoRapporto tiporapporto;
	private ModalitaLavoro modalitalavoro;
	private LuogoLavoro luogolavoro;
	
	private String descodprofessione;
	
	public Azienda getAzienda() {
		return azienda;
	}
	public void setAzienda(Azienda azienda) {
		this.azienda = azienda;
	}
	public String getCodprofessione() {
		return codprofessione;
	}
	public void setCodprofessione(String codprofessione) {
		this.codprofessione = codprofessione;
	}
	public TipoRapporto getTiporapporto() {
		return tiporapporto;
	}
	public void setTiporapporto(TipoRapporto tiporapporto) {
		this.tiporapporto = tiporapporto;
	}
	public LuogoLavoro getLuogolavoro() {
		return luogolavoro;
	}
	public void setLuogolavoro(LuogoLavoro luogolavoro) {
		this.luogolavoro = luogolavoro;
	}
	public Date getDatainizio() {
		return datainizio;
	}
	public void setDatainizio(Date datainizio) {
		this.datainizio = datainizio;
	}
	public Date getDatafine() {
		return datafine;
	}
	public void setDatafine(Date datafine) {
		this.datafine = datafine;
	}
	public ModalitaLavoro getModalitalavoro() {
		return modalitalavoro;
	}
	public void setModalitalavoro(ModalitaLavoro modalitalavoro) {
		this.modalitalavoro = modalitalavoro;
	}
	public String getDescodprofessione() {
		return descodprofessione;
	}
	public void setDescodprofessione(String descodprofessione) {
		this.descodprofessione = descodprofessione;
	}
	
	
}
