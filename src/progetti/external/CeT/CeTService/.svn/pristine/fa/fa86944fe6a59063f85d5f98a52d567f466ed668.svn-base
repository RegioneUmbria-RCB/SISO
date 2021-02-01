package it.webred.ct.data.access.basic.versamenti.bp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VersamentoTarBpDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String ccBeneficiario;
	private Date dtAccettazione;
	private Date dtAccredito;
	private String tipoDoc;
	private String importo;
	private String prov;
	private String ufficio;
	private String sportello;
	private String tipoAccettazione;
	private boolean sostitutivo;
	private String numBollettino;
	
	private String numRataRif;
	
	private String descTipoDoc;
	private String descTipoAccettazione;
	
	public String getDescTipoDoc(){
		descTipoDoc = "";
		if(tipoDoc!=null){
			if("247".equalsIgnoreCase(tipoDoc))
				descTipoDoc="Premarcati MAV";
			if("896".equalsIgnoreCase(tipoDoc))
				descTipoDoc="Premarcati Fatturatori";
			if("674".equalsIgnoreCase(tipoDoc))
				descTipoDoc="Premarcati non Fatturatori";
			if("451".equalsIgnoreCase(tipoDoc))
				descTipoDoc="Bianchi personalizzati";
			if("123".equalsIgnoreCase(tipoDoc))
				descTipoDoc="Bianchi";
		}
		return descTipoDoc;
	}
	
	public String getDescTipoAccettazione(){
		descTipoAccettazione = "";
		if(tipoAccettazione!=null){
			if("CC".equalsIgnoreCase(tipoAccettazione))
				descTipoAccettazione="Bollettino Cartaceo";
			if("AV".equalsIgnoreCase(tipoAccettazione))
				descTipoAccettazione="AVDS";
			if("DP".equalsIgnoreCase(tipoAccettazione))
				descTipoAccettazione="Dematerializzato Premarcato";
			if("DI".equalsIgnoreCase(tipoAccettazione))
				descTipoAccettazione="Dematerializzato con Immagine";
		}
		
		return descTipoAccettazione;
	}

	public String getCcBeneficiario() {
		return ccBeneficiario;
	}

	public void setCcBeneficiario(String ccBeneficiario) {
		this.ccBeneficiario = ccBeneficiario;
	}

	public Date getDtAccettazione() {
		return dtAccettazione;
	}

	public void setDtAccettazione(Date dtAccettazione) {
		this.dtAccettazione = dtAccettazione;
	}

	public Date getDtAccredito() {
		return dtAccredito;
	}

	public void setDtAccredito(Date dtAccredito) {
		this.dtAccredito = dtAccredito;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getSportello() {
		return sportello;
	}

	public void setSportello(String sportello) {
		this.sportello = sportello;
	}

	public String getTipoAccettazione() {
		return tipoAccettazione;
	}

	public void setTipoAccettazione(String tipoAccettazione) {
		this.tipoAccettazione = tipoAccettazione;
	}

	public boolean isSostitutivo() {
		return sostitutivo;
	}

	public void setSostitutivo(boolean sostitutivo) {
		this.sostitutivo = sostitutivo;
	}

	public String getNumBollettino() {
		return numBollettino;
	}

	public void setNumBollettino(String numBollettino) {
		this.numBollettino = numBollettino;
	}

	public String getNumRataRif() {
		return numRataRif;
	}

	public void setNumRataRif(String numRataRif) {
		this.numRataRif = numRataRif;
	}
	
}
