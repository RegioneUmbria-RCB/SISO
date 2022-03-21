package it.siso.isee.obj;

import java.io.Serializable;

/***
 * Elemento Obbligatorio che è ripetuto tante volte quanti immobili possiede il soggetto.
 *
 */
public class Patrimonio implements Serializable {

	/***
	 *  F – Fabbricato
		TE – Terreno Edificabile
		TA – Terreno Agricolo
	 */
	private String tipoPatrimonio;
	private String codiceComuneStato;
	//Percentuale di possesso dell’immobile
	//Valore Massimo:100
	private Float quotaPosseduta;
	//Valore ai fini IMU (o IVIE se estero) della quota posseduta.
	private Integer valoreImu;
	//Quota capitale residua del mutuo.
	private Integer mutuoResiduo;
	//Flag che indica se trattasi di casa di abitazione.
	private boolean flagCasaAbitazione;
	public String getTipoPatrimonio() {
		return tipoPatrimonio;
	}
	public void setTipoPatrimonio(String tipoPatrimonio) {
		this.tipoPatrimonio = tipoPatrimonio;
	}
	public String getCodiceComuneStato() {
		return codiceComuneStato;
	}
	public void setCodiceComuneStato(String codiceComuneStato) {
		this.codiceComuneStato = codiceComuneStato;
	}
	public Float getQuotaPosseduta() {
		return quotaPosseduta;
	}
	public void setQuotaPosseduta(Float quotaPosseduta) {
		this.quotaPosseduta = quotaPosseduta;
	}
	public Integer getValoreImu() {
		return valoreImu;
	}
	public void setValoreImu(Integer valoreImu) {
		this.valoreImu = valoreImu;
	}
	public Integer getMutuoResiduo() {
		return mutuoResiduo;
	}
	public void setMutuoResiduo(Integer mutuoResiduo) {
		this.mutuoResiduo = mutuoResiduo;
	}
	public boolean isFlagCasaAbitazione() {
		return flagCasaAbitazione;
	}
	public void setFlagCasaAbitazione(boolean flagCasaAbitazione) {
		this.flagCasaAbitazione = flagCasaAbitazione;
	}
	
	
}
