package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="AR_RP_TB_TIPO_STRUTTURA")
@NamedQuery(name="TipoStruttura.findAll", query="SELECT a FROM TipoStruttura a")
public class TipoStruttura implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	
	private String code;
	
	private String descrizione;

	@Column(name = "TIPO_FUNZIONE")
	private String tipoFunzione;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTipoFunzione() {
		return tipoFunzione;
	}

	public void setTipoFunzione(String tipoFunzione) {
		this.tipoFunzione = tipoFunzione;
	}

}
