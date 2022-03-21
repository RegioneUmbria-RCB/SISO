package it.webred.WsSitClient.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CivicoDTO extends ViaDTO{

	private static final long serialVersionUID = 1L;
	
	private String idc;
	private String coordx;
	private String coordy;
	private String numCompleto;
	private String numero;
	private String lettera;
	private String barra;
	private String codStato;
	private String descStato;
	
	private Date dataApplicazione;
	private Date dataSoppressione;
	
	private String residenziale;
	private String zdn;
	private String cap;
	private String url;
	
	public String getCoordx() {
		return coordx;
	}
	public void setCoordx(String coordx) {
		this.coordx = coordx;
	}
	public String getCoordy() {
		return coordy;
	}
	public void setCoordy(String coordy) {
		this.coordy = coordy;
	}
	public String getNumCompleto() {
		return numCompleto;
	}
	public void setNumCompleto(String numCompleto) {
		this.numCompleto = numCompleto;
	}	
	public String getLettera() {
		return lettera;
	}
	public void setLettera(String lettera) {
		this.lettera = lettera;
	}
	public String getBarra() {
		return barra;
	}
	public void setBarra(String barra) {
		this.barra = barra;
	}
	public String getCodStato() {
		return codStato;
	}
	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}
	public String getDescStato() {
		return descStato;
	}
	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}
	public String getResidenziale() {
		return residenziale;
	}
	public void setResidenziale(String residenziale) {
		this.residenziale = residenziale;
	}
	public String getZdn() {
		return zdn;
	}
	public void setZdn(String zdn) {
		this.zdn = zdn;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIdc() {
		return idc;
	}
	public void setIdc(String idc) {
		this.idc = idc;
	}
	public Date getDataApplicazione() {
		return dataApplicazione;
	}
	public void setDataApplicazione(Date dataApplicazione) {
		this.dataApplicazione = dataApplicazione;
	}
	public Date getDataSoppressione() {
		return dataSoppressione;
	}
	public void setDataSoppressione(Date dataSoppressione) {
		this.dataSoppressione = dataSoppressione;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String printCivico(){
		return idc+"|"+coordx+"|"+coordy+"|"+numCompleto+"|"+numero+"|"+lettera+"|"+barra+"|"+
			   codStato+"|"+descStato+"|"+residenziale+"|"+zdn+"|"+cap+"|"+url;
		
	}
	
	
	
}
