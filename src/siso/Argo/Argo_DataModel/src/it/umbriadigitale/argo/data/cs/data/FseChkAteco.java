package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FSE_CHK_ATECO")
public class FseChkAteco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 939092284687981102L;
	
	@Id
	private String codAtecoAnno;
	private String descrizioneCodiceAteco;
	
	@Column(name ="DESCRIZIONE_CODICE_ATECO")
	public String getDescrizioneCodiceAteco() {
		return descrizioneCodiceAteco;
	}
	public void setDescrizioneCodiceAteco(String descrizioneCodiceAteco) {
		this.descrizioneCodiceAteco = descrizioneCodiceAteco;
	}
	
	@Column(name ="COD_ATECO_ANNO")
	public String getCodAtecoAnno() {
		return codAtecoAnno;
	}
	public void setCodAtecoAnno(String codAtecoAnno) {
		this.codAtecoAnno = codAtecoAnno;
	}
	
	
	
	

}
