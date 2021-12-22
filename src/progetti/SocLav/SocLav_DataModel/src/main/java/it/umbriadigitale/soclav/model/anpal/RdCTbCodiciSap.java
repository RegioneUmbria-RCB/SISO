package it.umbriadigitale.soclav.model.anpal;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="V_RDC_TB_CODICI_SAP")
public class RdCTbCodiciSap implements Serializable {
	
	@EmbeddedId
	private RdCTbCodiciSapPK id;
	
	private String descrizione;

	public RdCTbCodiciSapPK getId() {
		return id;
	}

	public void setId(RdCTbCodiciSapPK id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
