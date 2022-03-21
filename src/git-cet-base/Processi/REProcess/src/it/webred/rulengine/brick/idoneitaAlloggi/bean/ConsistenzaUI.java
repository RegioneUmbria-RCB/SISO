package it.webred.rulengine.brick.idoneitaAlloggi.bean;

import java.io.Serializable;

public class ConsistenzaUI implements Serializable {

	private static final long serialVersionUID = 1229800769902495162L;
	
	private String foglio;
	private String mappale;
	private String sub;
	private double supTarsu;
	private String dicTarsuCFPi;
	private String dicTarsuDenominazione;
	private double consistenza;
	private double superficie;
	private double supC340;
	private String infoDocfa; // dato visualizzato in lista. Conterrà fornitura/protocollo dell'ultimo docfa per u.i.  
	
	private String prefisso = "";
	private String nomeVia = "";
	private String civico = "";
	private int idCivico = 0;
	private DatiDocfa datiDocfa = null;
	
	public ConsistenzaUI() {

	}//-------------------------------------------------------------------------

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getMappale() {
		return mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public double getSupTarsu() {
		return supTarsu;
	}

	public void setSupTarsu(double supTarsu) {
		this.supTarsu = supTarsu;
	}

	public String getDicTarsuCFPi() {
		return dicTarsuCFPi;
	}

	public void setDicTarsuCFPi(String dicTarsuCFPi) {
		this.dicTarsuCFPi = dicTarsuCFPi;
	}

	public String getDicTarsuDenominazione() {
		return dicTarsuDenominazione;
	}

	public void setDicTarsuDenominazione(String dicTarsuDenominazione) {
		this.dicTarsuDenominazione = dicTarsuDenominazione;
	}

	public double getConsistenza() {
		return consistenza;
	}

	public void setConsistenza(double consistenza) {
		this.consistenza = consistenza;
	}

	public double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(double superficie) {
		this.superficie = superficie;
	}

	public double getSupC340() {
		return supC340;
	}

	public void setSupC340(double supC340) {
		this.supC340 = supC340;
	}

	public String getInfoDocfa() {
		return infoDocfa;
	}

	public void setInfoDocfa(String infoDocfa) {
		this.infoDocfa = infoDocfa;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public int getIdCivico() {
		return idCivico;
	}

	public void setIdCivico(int idCivico) {
		this.idCivico = idCivico;
	}

	public DatiDocfa getDatiDocfa() {
		return datiDocfa;
	}

	public void setDatiDocfa(DatiDocfa datiDocfa) {
		this.datiDocfa = datiDocfa;
	}
	
}

