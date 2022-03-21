package it.webred.ss.data.model;


import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the SS_INTERVENTO_ECONOMICO_TIPO database table.
 * 
 */
@Entity
@Table(name="SS_INTERVENTO_ECONOMICO_TIPO")
@NamedQuery(name="SsInterventoEconomicoTipo.findAll", query="SELECT c FROM SsInterventoEconomicoTipo c")
public class SsInterventoEconomicoTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String tipo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}