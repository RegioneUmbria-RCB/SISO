package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the R_TRACCIA_FORNITURE database table.
 * 
 */
@Entity
@Table(name="R_TRACCIA_FORNITURE")
public class RTracciaForniture implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RTracciaForniturePK id;

    @Temporal( TemporalType.DATE)
	@Column(name="\"DATA\"")
	private Date data;

	@Column(name="NOME_FILE")
	private String nomeFile;

	private String processid;

    public RTracciaForniture() {
    }

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public RTracciaForniturePK getId() {
		return id;
	}

	public void setId(RTracciaForniturePK id) {
		this.id = id;
	}

}