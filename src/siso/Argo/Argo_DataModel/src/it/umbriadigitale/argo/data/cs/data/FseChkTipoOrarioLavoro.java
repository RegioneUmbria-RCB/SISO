package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="FSE_CHK_TIPO_ORARIO_LAVORO")
public class FseChkTipoOrarioLavoro implements Serializable {
	private static final long serialVersionUID = -3209229375833463087L;

	@Id
	private String id;
	private String descrizione;
	private Boolean attivo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name ="DESCRIZIONE")
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	@Column(name ="ATTIVO")
	public Boolean getAttivo() {
		return attivo;
	}
	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}
	
	
}
