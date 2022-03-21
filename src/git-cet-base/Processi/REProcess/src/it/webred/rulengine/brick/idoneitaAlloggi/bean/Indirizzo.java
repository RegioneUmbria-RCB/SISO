package it.webred.rulengine.brick.idoneitaAlloggi.bean;

import java.io.Serializable;

public class Indirizzo implements Serializable {
	
	private static final long serialVersionUID = -7304975410488671579L;
	
	int pkidStra;
	int pkidCivi;
	String chiave;
	String prefisso;
	String nomeVia;
	String civico;
	String codNazionale;
	String indirizzoCompleto;
	String foglio;
	String particella;
	String latitudine;
	String longitudine;
	boolean fp;
	
	public Indirizzo() {
		// TODO Auto-generated constructor stub
	}//-------------------------------------------------------------------------

	public int getPkidStra() {
		return pkidStra;
	}

	public void setPkidStra(int pkidStra) {
		this.pkidStra = pkidStra;
	}

	public int getPkidCivi() {
		return pkidCivi;
	}

	public void setPkidCivi(int pkidCivi) {
		this.pkidCivi = pkidCivi;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getPrefisso() {
		return prefisso;
	}

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	public String getNomeVia() {
		return nomeVia;
	}

	public void setNomeVia(String nomeVia) {
		this.nomeVia = nomeVia;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCodNazionale() {
		return codNazionale;
	}

	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}

	public String getIndirizzoCompleto() {
		return indirizzoCompleto;
	}

	public void setIndirizzoCompleto(String indirizzoCompleto) {
		this.indirizzoCompleto = indirizzoCompleto;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
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

	public boolean isFp() {
		return fp;
	}

	public void setFp(boolean fp) {
		this.fp = fp;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	

}

