package it.escsolution.escwebgis.concessioni.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;

public class StoricoConcessioniCatasto extends EscObject implements Serializable {

	private String id;
	private String idExt;
	private String idOrig;
	private String codEnt;
	private String foglio;
	private String particella;
	private String subalterno;	
	private String tipo;
	private String sezione;
	private boolean docfa;
	private boolean cessataCatasto;
	private boolean concCollegate;
	private StoricoConcessioniIndirizzo indirizzo;
	private String assenzaCatasto;
	private String dataValiditaCatasto;
	
	public String getChiave(){ 
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdExt() {
		return idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getIdOrig() {
		return idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getCodEnt() {
		return codEnt;
	}

	public void setCodEnt(String codEnt) {
		this.codEnt = codEnt;
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

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public boolean isDocfa() {
		return docfa;
	}

	public void setDocfa(boolean docfa) {
		this.docfa = docfa;
	}

	public boolean isCessataCatasto() {
		return cessataCatasto;
	}

	public void setCessataCatasto(boolean cessataCatasto) {
		this.cessataCatasto = cessataCatasto;
	}

	public boolean isConcCollegate() {
		return concCollegate;
	}

	public void setConcCollegate(boolean concCollegate) {
		this.concCollegate = concCollegate;
	}

	public StoricoConcessioniIndirizzo getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(StoricoConcessioniIndirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getAssenzaCatasto() {
		return assenzaCatasto;
	}

	public void setAssenzaCatasto(String assenzaCatasto) {
		this.assenzaCatasto = assenzaCatasto;
	}

	public String getDataValiditaCatasto() {
		return dataValiditaCatasto;
	}

	public void setDataValiditaCatasto(String dataValiditaCatasto) {
		this.dataValiditaCatasto = dataValiditaCatasto;
	}
	
}
