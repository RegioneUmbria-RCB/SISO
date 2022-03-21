package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

import java.math.BigDecimal;
import java.util.HashMap;

public class SitTTarOggetto extends TabellaDwhMultiProv {
	
	private String desClsRsu;
	private String sez;
	private String foglio;
	private String numero;
	private String sub;
	private BigDecimal supTot;
	private DataDwh datIni;
	private DataDwh datFin;
	private String tipOgg;
	private String desTipOgg;
	private String desInd;
	private Relazione idExtVia = new Relazione(SitTTarVia.class, new ChiaveEsterna());
	private String numCiv;
	private String espCiv;
	private String scala;
	private String piano;
	private String interno;	
	private DataDwh tmsAgg;
	private DataDwh tmsBon;
	private BigDecimal nCompFam;
	
	private HashMap<String, String> hashValues;
	public static final String HASH_ID_ORIG_KEY = "HASH_ID_ORIG";
	
	public String getDesClsRsu() {
		return desClsRsu;
	}

	public void setDesClsRsu(String desClsRsu) {
		this.desClsRsu = desClsRsu;
	}

	public String getSez() {
		return sez;
	}

	public void setSez(String sez) {
		this.sez = sez;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public BigDecimal getSupTot() {
		return supTot;
	}

	public void setSupTot(BigDecimal supTot) {
		this.supTot = supTot;
	}

	public DataDwh getDatIni() {
		return datIni;
	}

	public void setDatIni(DataDwh datIni) {
		this.datIni = datIni;
	}

	public DataDwh getDatFin() {
		return datFin;
	}

	public void setDatFin(DataDwh datFin) {
		this.datFin = datFin;
	}

	public String getTipOgg() {
		return tipOgg;
	}

	public void setTipOgg(String tipOgg) {
		this.tipOgg = tipOgg;
	}

	public String getDesTipOgg() {
		return desTipOgg;
	}

	public void setDesTipOgg(String desTipOgg) {
		this.desTipOgg = desTipOgg;
	}

	public String getDesInd() {
		return desInd;
	}

	public void setDesInd(String desInd) {
		this.desInd = desInd;
	}

	public Relazione getIdExtVia() {
		return idExtVia;
	}

	public void setIdExtVia(ChiaveEsterna idExtVia)
	{
		Relazione r = new Relazione(SitTTarVia.class, idExtVia);
		this.idExtVia = r;
	}

	public String getNumCiv() {
		return numCiv;
	}

	public void setNumCiv(String numCiv) {
		this.numCiv = numCiv;
	}

	public String getEspCiv() {
		return espCiv;
	}

	public void setEspCiv(String espCiv) {
		this.espCiv = espCiv;
	}

	public String getScala() {
		return scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public DataDwh getTmsAgg() {
		return tmsAgg;
	}

	public void setTmsAgg(DataDwh tmsAgg) {
		this.tmsAgg = tmsAgg;
	}

	public DataDwh getTmsBon() {
		return tmsBon;
	}

	public void setTmsBon(DataDwh tmsBon) {
		this.tmsBon = tmsBon;
	}
	
	public BigDecimal getNCompFam() {
		return nCompFam;
	}

	public void setNCompFam(BigDecimal nCompFam) {
		this.nCompFam = nCompFam;
	}

	public void putHashValue(String key, String value) {
		if (hashValues == null) {
			hashValues = new HashMap<String, String>();
		}
		hashValues.put(key, value);
	}

	public String getValueForCtrHash() {
		String hashIdOrig = null;
		if (hashValues != null) {
			hashIdOrig = hashValues.get(HASH_ID_ORIG_KEY);		
		}
		return (hashIdOrig == null ? "" : hashIdOrig) +
		desClsRsu +
		this.sez +
		this.foglio +
		this.numero +
		this.sub +
		this.supTot +
		this.datIni.getDataFormattata() +
		this.datFin.getDataFormattata() +
		this.tipOgg +
		this.desTipOgg +
		this.desInd +
		(String)this.idExtVia.getRelazione().getValore() +
		this.numCiv +
		this.espCiv +
		this.scala +
		this.piano +
		this.interno +	
		this.tmsAgg.getDataFormattata() +
		this.tmsBon.getDataFormattata() +
		this.nCompFam;
	}
	
}