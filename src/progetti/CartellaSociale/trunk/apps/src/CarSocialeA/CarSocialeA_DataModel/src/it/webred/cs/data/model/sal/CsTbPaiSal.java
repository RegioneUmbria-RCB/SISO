package it.webred.cs.data.model.sal;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CS_TB_PAI_SAL database table.
 * 
 */
@Entity
@Table(name="CS_TB_PAI_SAL")
@NamedQuery(name="CsTbPaiSal.findAll", query="SELECT c FROM CsTbPaiSal c")
public class CsTbPaiSal implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsTbPaiSalPK id;

	private String descrizione;

	public CsTbPaiSal() {
	}

	public CsTbPaiSalPK getId() {
		return this.id;
	}

	public void setId(CsTbPaiSalPK id) {
		this.id = id;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}