package it.webred.cs.data.model.view;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="V_SS_SCHEDE_UDC_DIFF")
public class VSsSchedeUdcDiff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ID_SCHEDA")
	private BigDecimal idScheda;
	
	@Id
	private String cf;

	private String cognome;

	private String nome;
	
	private Date dt;

	public BigDecimal getIdScheda() {
		return idScheda;
	}

	public void setIdScheda(BigDecimal idScheda) {
		this.idScheda = idScheda;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
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

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}