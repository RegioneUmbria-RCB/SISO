package it.webred.rulengine.brick.pregeo.bean;

import java.sql.Date;

public class PregeoFornituraBean {
	
	private Long idFor = null;
	private Date dataFornitura = null;
	private String nomeFileZip = "";
	private String contenutoZip = "";
	private Date dataInserimento = null;
	
	public Long getIdFor() {
		return idFor;
	}
	public void setIdFor(Long idFor) {
		this.idFor = idFor;
	}
	public Date getDataFornitura() {
		return dataFornitura;
	}
	public void setDataFornitura(Date dataFor) {
		this.dataFornitura = dataFor;
	}
	public String getNomeFileZip() {
		return nomeFileZip;
	}
	public void setNomeFileZip(String nomeFileZip) {
		this.nomeFileZip = nomeFileZip;
	}
	public String getContenutoZip() {
		return contenutoZip;
	}
	public void setContenutoZip(String contenutoZip) {
		this.contenutoZip = contenutoZip;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	
	

}

