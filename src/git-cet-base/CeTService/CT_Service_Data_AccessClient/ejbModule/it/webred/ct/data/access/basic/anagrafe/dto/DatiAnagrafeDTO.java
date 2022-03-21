package it.webred.ct.data.access.basic.anagrafe.dto;

import java.util.Date;

public class DatiAnagrafeDTO{

	private String codFisc;
	private String cog;
	private String nom;
	private Date datNas;
	private String comNas;
	private String provNas;
	private String codRel;
	
	private String sesso;
	private String comRes;
	private String provRes;
	private String indRes;
	private String iban;
	private String belfiore;

	public String getCog() {
		return cog;
	}
	public String getNom() {
		return nom;
	}
	public String getComNas() {
		return comNas;
	}
	public String getProvNas() {
		return provNas;
	}
	public void setCog(String cog) {
		this.cog = cog;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setComNas(String comNas) {
		this.comNas = comNas;
	}
	public void setProvNas(String provNas) {
		this.provNas = provNas;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	
	public String getCodRel() {
		return codRel;
	}
	public void setCodRel(String codRel) {
		this.codRel = codRel;
	}
	
	public Date getDatNas() {
		return datNas;
	}
	public void setDatNas(Date datNas) {
		this.datNas = datNas;
	}
	
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getComRes() {
		return comRes;
	}
	public void setComRes(String comRes) {
		this.comRes = comRes;
	}
	public String getProvRes() {
		return provRes;
	}
	public void setProvRes(String provRes) {
		this.provRes = provRes;
	}
	public String getIndRes() {
		return indRes;
	}
	public void setIndRes(String indRes) {
		this.indRes = indRes;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getBelfiore() {
		return belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public String stampaRecord(){
		String r =  codFisc +"|"+ cog+"|"+nom+"|"+datNas+"|"+comNas+"|"+provNas+"|"+codRel+"|"+sesso+"|"+comRes+"|"+provRes+"|"+indRes+"|"+iban+"|"+belfiore;
		return r;
	}
	
}
