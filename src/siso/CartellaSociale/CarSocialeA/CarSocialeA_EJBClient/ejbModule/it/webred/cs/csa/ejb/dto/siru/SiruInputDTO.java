package it.webred.cs.csa.ejb.dto.siru;

import it.webred.cs.data.model.CsTbIngMercato;

import java.io.Serializable;
import java.util.Date;

public class SiruInputDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String codiceFiscale;
	private String sesso;
	private Date dataNascita;
	
	private String idTitoloStudio;
	private CsTbIngMercato csTbIngMercato;
	private String comuneNascitaCod;
	private String statoNascitaCod;
	private String flagResDom;
	private String codIstatComuneResidenza;
	private String codIstatComuneDomicilio;
	private String grpVulnerabilita;
	private String cittadinanza;

	private String azCodAteco;
	private String descDimAzienda;
	private String azFormaGiuridica;
	private String descOrarioLavoro;
	private String descTipoLavoro;
	private String azPi;
	private String azCf;
	private String azRagioneSociale;
	private String azVia;
	private String durataRicLavoroId;
	private String azComuneCod;

	public void setAzCodAteco(String azCodAteco) {
		this.azCodAteco = azCodAteco;
	}
	
	public void setDescDimAzienda(String descDimAzienda) {
		this.descDimAzienda = descDimAzienda;
	}
	public void setAzFormaGiuridica(String azFormaGiuridica) {
		this.azFormaGiuridica = azFormaGiuridica;
	}
	public void setDescOrarioLavoro(String descOrarioLavoro) {
		this.descOrarioLavoro = descOrarioLavoro;
	}
	public void setDescTipoLavoro(String descTipoLavoro) {
		this.descTipoLavoro = descTipoLavoro;
	}
	public void setAzPi(String azPi) {
		this.azPi = azPi;
	}
	public void setAzCf(String azCf) {
		this.azCf = azCf;
	}
	public void setAzRagioneSociale(String azRagioneSociale) {
		this.azRagioneSociale = azRagioneSociale;
	}
	public void setAzVia(String azVia) {
		this.azVia = azVia;
	}
	public void setAzComuneCod(String azComuneCod) {
		this.azComuneCod = azComuneCod;
	}
	
	public String getGrpVulnerabilita() {
		return grpVulnerabilita;
	}
	public void setGrpVulnerabilita(String grpVulnerabilita) {
		this.grpVulnerabilita = grpVulnerabilita;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public void setIdTitoloStudio(String idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
	}
	public void setCsTbIngMercato(CsTbIngMercato csTbIngMercato) {
		this.csTbIngMercato = csTbIngMercato;
	}
	public void setComuneNascitaCod(String comuneNascitaCod) {
		this.comuneNascitaCod = comuneNascitaCod;
	}
	public void setStatoNascitaCod(String statoNascitaCod) {
		this.statoNascitaCod = statoNascitaCod;
	}
	public void setFlagResDom(String flagResDom) {
		this.flagResDom = flagResDom;
	}
	
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public String getIdTitoloStudio() {
		return idTitoloStudio;
	}
	public CsTbIngMercato getCsTbIngMercato() {
		return csTbIngMercato;
	}
	public String getComuneNascitaCod() {
		return comuneNascitaCod;
	}
	public String getStatoNascitaCod() {
		return statoNascitaCod;
	}
	public String getFlagResDom() {
		return flagResDom;
	}
	
	public String getGrVulnerabilita() {
		return grpVulnerabilita;
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public String getAzCodAteco() {
		return azCodAteco;
	}
	public String getDescDimAzienda() {
		return descDimAzienda;
	}
	public String getAzFormaGiuridica() {
		return azFormaGiuridica;
	}
	public String getDescOrarioLavoro() {
		return descOrarioLavoro;
	}
	public String getDescTipoLavoro() {
		return descTipoLavoro;
	}
	public String getAzPi() {
		return azPi;
	}
	public String getAzCf() {
		return azCf;
	}
	public String getAzRagioneSociale() {
		return azRagioneSociale;
	}
	public String getAzVia() {
		return azVia;
	}
	public String getAzComuneCod() {
		return azComuneCod;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCodIstatComuneResidenza() {
		return codIstatComuneResidenza;
	}

	public void setCodIstatComuneResidenza(String codIstatComuneResidenza) {
		this.codIstatComuneResidenza = codIstatComuneResidenza;
	}

	public String getCodIstatComuneDomicilio() {
		return codIstatComuneDomicilio;
	}

	public void setCodIstatComuneDomicilio(String codIstatComuneDomicilio) {
		this.codIstatComuneDomicilio = codIstatComuneDomicilio;
	}

	public String getDurataRicLavoroId() {
		return durataRicLavoroId;
	}

	public void setDurataRicLavoroId(String durataRicLavoroId) {
		this.durataRicLavoroId = durataRicLavoroId;
	}
	
}
