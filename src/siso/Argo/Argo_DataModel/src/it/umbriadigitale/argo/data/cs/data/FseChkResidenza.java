package it.umbriadigitale.argo.data.cs.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FSE_CHK_RESIDENZA")
public class FseChkResidenza {

	@Id
	private String codice;
	
	@Column(name="DESCRIZIONE_SISO")
	private String descrizione;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
