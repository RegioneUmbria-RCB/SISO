package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FSE_CHK_DIMENSIONE_AZIENDA")
public class FseChkDimensioneAzienda  implements Serializable {
	private static final long serialVersionUID = 939092284687981102L;
	
	@Id
	private String id;
	private String descrizione;
	
	@Column(name ="DESCRIZIONE")
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	@Column(name ="ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

}
