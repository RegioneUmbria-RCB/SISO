package it.webred.rulengine.brick.loadDwh.load.docfa.dto;

import java.io.Serializable;
import java.sql.Connection;

public class DocfaPack implements Serializable {
	
	private Connection conn; 
	private String prefissotabella;
	private String nometabella;
	private String nomeFileConf;
	private String processID;
	private Long dataEstrazione;
	private String pkListQuery;
	
	
	public DocfaPack(Connection conn, String prefissotabella, String nometabella, String nomeFileConf,
			String processID, Long dataEstrazione, String pkListQuery) {
		super();
		this.conn = conn;
		this.prefissotabella = prefissotabella;
		this.nometabella = nometabella;
		this.nomeFileConf = nomeFileConf;
		this.processID = processID;
		this.dataEstrazione = dataEstrazione;
		this.pkListQuery = pkListQuery;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getNometabella() {
		return nometabella;
	}

	public void setNometabella(String nometabella) {
		this.nometabella = nometabella;
	}

	public String getNomeFileConf() {
		return nomeFileConf;
	}

	public void setNomeFileConf(String nomeFileConf) {
		this.nomeFileConf = nomeFileConf;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public Long getDataEstrazione() {
		return dataEstrazione;
	}

	public void setDataEstrazione(Long dataEstrazione) {
		this.dataEstrazione = dataEstrazione;
	}

	public String getPkListQuery() {
		return pkListQuery;
	}

	public void setPkListQuery(String pkListQuery) {
		this.pkListQuery = pkListQuery;
	}

	public String getPrefissotabella() {
		return prefissotabella;
	}

	public void setPrefissotabella(String prefissotabella) {
		this.prefissotabella = prefissotabella;
	}
	
	
}
