package it.webred.cs.data.model.affido;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="CS_TB_PAI_AFFIDO")
@IdClass(CsTbPaiAffidoChiave.class)
public class CsTbPaiAffido {
	
	@Id
	@Column(name="DOMINIO")
	private String dominio;
	
	@Id
	@Column(name="CODICE")
	private String codice;
	
	@Column(name="DESCRIZIONE")
	private String descrizione;
	

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

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
