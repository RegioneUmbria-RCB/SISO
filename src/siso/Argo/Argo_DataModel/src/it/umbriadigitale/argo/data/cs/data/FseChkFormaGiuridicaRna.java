package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FSE_CHK_FORMA_GIURIDICA_RNA")
public class FseChkFormaGiuridicaRna implements Serializable{
	private static final long serialVersionUID = 939092284687981102L;
	
	@Id
	private String codice;
	private String descrizione;
	
	@Column(name ="CODICE")
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	@Column(name ="DESCRIZIONE")
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	

}
