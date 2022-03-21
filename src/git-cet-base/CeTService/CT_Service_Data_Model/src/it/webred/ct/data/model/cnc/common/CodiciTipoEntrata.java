package it.webred.ct.data.model.cnc.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="S_CNC_COD_TIPO_ENTRATA")
public class CodiciTipoEntrata  implements Serializable {
	@Id
	private String codice;
	
	@Column(name="DESCR")
	private String descrizione;

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}   
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
