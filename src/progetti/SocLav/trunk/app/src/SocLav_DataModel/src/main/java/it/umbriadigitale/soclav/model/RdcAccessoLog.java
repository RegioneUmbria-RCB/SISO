package it.umbriadigitale.soclav.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="RDC_ACCESSO_LOG")
public class RdcAccessoLog {

	@Id
	@SequenceGenerator(name = "RDC_ID_GENERATOR", sequenceName = "RDC_SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RDC_ID_GENERATOR")
	private long id;
	
	@Column (name="OPERATORE_USERNAME")
	private String operatoreUsername;
	
	@Column (name="OPERATORE_ENTE")
	private String operatoreEnte;
	
	private Date data;
	
	private String tipo;
	
	@Column(name="CF_VISUALIZZATO")
	private String cfVisualizzato;
	
	@ManyToOne
	@JoinColumn(name="OPERATORE_USERNAME", insertable = false, updatable = false)
	private RdCUser user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOperatoreUsername() {
		return operatoreUsername;
	}

	public void setOperatoreUsername(String operatoreUsername) {
		this.operatoreUsername = operatoreUsername;
	}

	public String getOperatoreEnte() {
		return operatoreEnte;
	}

	public void setOperatoreEnte(String operatoreEnte) {
		this.operatoreEnte = operatoreEnte;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCfVisualizzato() {
		return cfVisualizzato;
	}

	public void setCfVisualizzato(String cfVisualizzato) {
		this.cfVisualizzato = cfVisualizzato;
	}

	public RdCUser getUser() {
		return user;
	}

	public void setUser(RdCUser user) {
		this.user = user;
	}
}
