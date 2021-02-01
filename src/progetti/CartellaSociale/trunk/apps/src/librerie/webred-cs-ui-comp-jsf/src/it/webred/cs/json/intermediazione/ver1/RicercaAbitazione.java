package it.webred.cs.json.intermediazione.ver1;

import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.jsf.bean.ComuneBean;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RicercaAbitazione extends JsonBaseBean {

	private String[] tipo;
	private String[] motivi;
	private String motivoAltro;
	
	private BigDecimal canoneLocazioneAttuale;
	private Intermediario intermediario;
	
	private ComuneBean comunePreferito;
	private String zonaPreferita;
	private String numVani;
	private String numPersone;
	private String[] caratteristiche;
	private BigDecimal canoneLocazioneMax;
	

	public RicercaAbitazione(){
		comunePreferito = new ComuneBean();
		intermediario = new Intermediario();
	} 
	
	public String getMotivoAltro() {
		return motivoAltro;
	}

	public ComuneBean getComunePreferito() {
		return comunePreferito;
	}
	public String getZonaPreferita() {
		return zonaPreferita;
	}
	public String getNumVani() {
		return numVani;
	}
	public String getNumPersone() {
		return numPersone;
	}

	public void setMotivoAltro(String motivoAltro) {
		this.motivoAltro = motivoAltro;
	}

	public void setComunePreferito(ComuneBean comunePreferito) {
		this.comunePreferito = comunePreferito;
	}
	public void setZonaPreferita(String zonaPreferita) {
		this.zonaPreferita = zonaPreferita;
	}
	public void setNumVani(String numVani) {
		this.numVani = numVani;
	}
	public void setNumPersone(String numPersone) {
		this.numPersone = numPersone;
	}

	public String[] getTipo() {
		return tipo;
	}
	public String[] getMotivi() {
		return motivi;
	}
	public void setTipo(String[] tipo) {
		this.tipo = tipo;
	}
	public void setMotivi(String[] motivi) {
		this.motivi = motivi;
	}
	public String[] getCaratteristiche() {
		return caratteristiche;
	}
	public void setCaratteristiche(String[] caratteristiche) {
		this.caratteristiche = caratteristiche;
	}	
	
	public BigDecimal getCanoneLocazioneAttuale() {
		return canoneLocazioneAttuale;
	}

	public void setCanoneLocazioneAttuale(BigDecimal canoneLocazioneAttuale) {
		this.canoneLocazioneAttuale = canoneLocazioneAttuale;
	}

	public BigDecimal getCanoneLocazioneMax() {
		return canoneLocazioneMax;
	}

	public void setCanoneLocazioneMax(BigDecimal canoneLocazioneMax) {
		this.canoneLocazioneMax = canoneLocazioneMax;
	}

	public Intermediario getIntermediario() {
		return intermediario;
	}

	public void setIntermediario(Intermediario intermediario) {
		this.intermediario = intermediario;
	}

	@Override
	public List<String> checkObbligatorieta() {
		List<String> messages = new LinkedList<String>();
		//TODO: Implementare controlli
		return messages;
	}
	
}
