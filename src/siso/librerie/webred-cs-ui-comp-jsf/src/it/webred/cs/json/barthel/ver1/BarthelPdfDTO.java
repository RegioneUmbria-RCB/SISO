package it.webred.cs.json.barthel.ver1;

import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.dto.utility.StringIntPairBean;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BarthelPdfDTO extends BasePdfDTO{
	
	private String operatore=EMPTY_VALUE;
	private String dataValutazione=EMPTY_VALUE;
	
	/*Dati Barthel*/
	private String alimentazione;
	private String bagnoDoccia;
	private String igienePersonale;
	private String abbigliamento;
	private String continenzaIntestinale;
	private String continenzaUrinaria;
	private String usoGabinetto;
	private String lettoCarrozzina;
	private String deambulazione; 
	private String scale;
	private String usoCarrozzina;
	private String punteggioBarthel;
	private String intervistaABarthel;
	
	private String protesiAusili=EMPTY_VALUE;

	
	/*Dati IADL*/
	private String usoTelefono;
	private String fareAcquisti;
	private String preparazioneCibo;
	private String governoCasa;
	private String biancheria;
	private String mezziTrasporto;
	private String usoFarmaci;
	private String gestioneDenaro;
	private String punteggioIADL;
	private String intervistaAIADL;
	
	private String patologiePsichiche=EMPTY_VALUE;
	
	
	
	public String getAlimentazione() {
		return alimentazione;
	}

	public String getBagnoDoccia() {
		return bagnoDoccia;
	}

	public String getIgienePersonale() {
		return igienePersonale;
	}

	public String getAbbigliamento() {
		return abbigliamento;
	}

	public String getContinenzaIntestinale() {
		return continenzaIntestinale;
	}

	public String getContinenzaUrinaria() {
		return continenzaUrinaria;
	}

	public String getUsoGabinetto() {
		return usoGabinetto;
	}

	public String getLettoCarrozzina() {
		return lettoCarrozzina;
	}

	public String getDeambulazione() {
		return deambulazione;
	}

	public String getScale() {
		return scale;
	}

	public String getUsoCarrozzina() {
		return usoCarrozzina;
	}

	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}

	public void setBagnoDoccia(String bagnoDoccia) {
		this.bagnoDoccia = bagnoDoccia;
	}

	public void setIgienePersonale(String igienePersonale) {
		this.igienePersonale = igienePersonale;
	}

	public void setAbbigliamento(String abbigliamento) {
		this.abbigliamento = abbigliamento;
	}

	public void setContinenzaIntestinale(String continenzaIntestinale) {
		this.continenzaIntestinale = continenzaIntestinale;
	}

	public void setContinenzaUrinaria(String continenzaUrinaria) {
		this.continenzaUrinaria = continenzaUrinaria;
	}

	public void setUsoGabinetto(String usoGabinetto) {
		this.usoGabinetto = usoGabinetto;
	}

	public void setLettoCarrozzina(String lettoCarrozzina) {
		this.lettoCarrozzina = lettoCarrozzina;
	}

	public void setDeambulazione(String deambulazione) {
		this.deambulazione = deambulazione;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public void setUsoCarrozzina(String usoCarrozzina) {
		this.usoCarrozzina = usoCarrozzina;
	}

	public String getPunteggioBarthel() {
		return punteggioBarthel;
	}

	public void setPunteggioBarthel(String punteggioBarthel) {
		this.punteggioBarthel = punteggioBarthel;
	}

	public String getIntervistaABarthel() {
		return intervistaABarthel;
	}

	public String getUsoTelefono() {
		return usoTelefono;
	}

	public String getFareAcquisti() {
		return fareAcquisti;
	}

	public String getPreparazioneCibo() {
		return preparazioneCibo;
	}

	public String getGovernoCasa() {
		return governoCasa;
	}

	public String getBiancheria() {
		return biancheria;
	}

	public String getMezziTrasporto() {
		return mezziTrasporto;
	}

	public String getUsoFarmaci() {
		return usoFarmaci;
	}

	public String getGestioneDenaro() {
		return gestioneDenaro;
	}

	public String getIntervistaAIADL() {
		return intervistaAIADL;
	}

	public void setIntervistaABarthel(String intervistaABarthel) {
		this.intervistaABarthel = intervistaABarthel;
	}

	public void setUsoTelefono(String usoTelefono) {
		this.usoTelefono = usoTelefono;
	}

	public void setFareAcquisti(String fareAcquisti) {
		this.fareAcquisti = fareAcquisti;
	}

	public void setPreparazioneCibo(String preparazioneCibo) {
		this.preparazioneCibo = preparazioneCibo;
	}

	public void setGovernoCasa(String governoCasa) {
		this.governoCasa = governoCasa;
	}

	public void setBiancheria(String biancheria) {
		this.biancheria = biancheria;
	}

	public void setMezziTrasporto(String mezziTrasporto) {
		this.mezziTrasporto = mezziTrasporto;
	}

	public void setUsoFarmaci(String usoFarmaci) {
		this.usoFarmaci = usoFarmaci;
	}

	public void setGestioneDenaro(String gestioneDenaro) {
		this.gestioneDenaro = gestioneDenaro;
	}

	public void setIntervistaAIADL(String intervistaAIADL) {
		this.intervistaAIADL = intervistaAIADL;
	}

	public String getPunteggioIADL() {
		return punteggioIADL;
	}

	public void setPunteggioIADL(String punteggioIADL) {
		this.punteggioIADL = punteggioIADL;
	}

	public String getPatologiePsichiche() {
		return patologiePsichiche;
	}

	public void setPatologiePsichiche(String patologiePsichiche) {
		this.patologiePsichiche = patologiePsichiche;
	}

	public String getProtesiAusili() {
		return protesiAusili;
	}

	public void setProtesiAusili(String protesiAusili) {
		this.protesiAusili = protesiAusili;
	}

	public String getDataValutazione() {
		return dataValutazione;
	}

	public void setDataValutazione(String dataValutazione) {
		this.dataValutazione = dataValutazione;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}	
	
}
