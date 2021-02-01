package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.util.Date;

public class DiarioAnagraficaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6026294953973014605L;

	private Long id;
	
	private Long diarioId;
	
	private Long anagraficaId;
	
	private String cognome;
	
	private String nome;
	
	private String cf;
	
	private String note;
	
	private String userIns;
	
	private Date dataIns;
	
	private String userMod;
	
	private Date dataMod;
	
	private Boolean selezionato = false;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public Long getAnagraficaId() {
		return anagraficaId;
	}

	public void setAnagraficaId(Long anagraficaId) {
		this.anagraficaId = anagraficaId;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDataIns() {
		return dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public String getUserMod() {
		return userMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}

	public Date getDataMod() {
		return dataMod;
	}

	public void setDataMod(Date dataMod) {
		this.dataMod = dataMod;
	}

	public Boolean getSelezionato() {
		return selezionato;
	}

	public void setSelezionato(Boolean selezionato) {
		this.selezionato = selezionato;
	}

}
