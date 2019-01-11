package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the CS_CFG_PARAMETRI database table.
 * 
 */
@Entity
@Table(name="CS_CFG_PARAMETRI")
@NamedQuery(name="CsCfgParametri.findAll", query="SELECT c FROM CsCfgParametri c")
public class CsCfgParametri implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsCfgParametriPK id;
	
	private String valore;

	public CsCfgParametri() {
	}

	public CsCfgParametriPK getId() {
		return id;
	}

	public void setId(CsCfgParametriPK id) {
		this.id = id;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}