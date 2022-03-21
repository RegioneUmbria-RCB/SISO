package it.webred.ct.data.access.basic.tarsu.dto;

import it.webred.ct.data.access.basic.catasto.dto.CatastoBaseDTO;
import it.webred.ct.data.model.tarsu.VTTarCiviciAll;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RicercaOggettoTarsuDTO extends CatastoBaseDTO implements Serializable {
	
	private String sql;
	int startm;
	int numberRecord;
	boolean loadSoggetti;
	private String idExtOgg;
	private String sezione;
	private String foglio;
	private String particella;
	private String unimm;
	private String chiave;
	private Date dtRif;
	
	private List<VTTarCiviciAll> listaCivTarsu;
	private String provenienza;
	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getStartm() {
		return startm;
	}

	public void setStartm(int startm) {
		this.startm = startm;
	}

	public int getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(int numberRecord) {
		this.numberRecord = numberRecord;
	}

	public boolean isLoadSoggetti() {
		return loadSoggetti;
	}

	public void setLoadSoggetti(boolean loadSoggetti) {
		this.loadSoggetti = loadSoggetti;
	}

	public String getIdExtOgg() {
		return idExtOgg;
	}

	public void setIdExtOgg(String idExtOgg) {
		this.idExtOgg = idExtOgg;
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

	public String getUnimm() {
		return unimm;
	}

	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public List<VTTarCiviciAll> getListaCivTarsu() {
		return listaCivTarsu;
	}

	public void setListaCivTarsu(List<VTTarCiviciAll> listaCivTarsu) {
		this.listaCivTarsu = listaCivTarsu;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public Date getDtRif() {
		return dtRif;
	}

	public void setDtRif(Date dtRif) {
		this.dtRif = dtRif;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	

}
