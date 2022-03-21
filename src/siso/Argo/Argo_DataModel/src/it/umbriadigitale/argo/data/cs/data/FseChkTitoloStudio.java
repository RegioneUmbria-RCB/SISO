package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FSE_CHK_TITOLO_STUDIO")
public class FseChkTitoloStudio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3292552074302201658L;
	@Id
	private String titoloStudio;
	private String descrizioneTitoloStudio;
	private String isced;
	
	@Column(name ="TITOLO_STUDIO")
	public String getTitoloStudio() {
		return titoloStudio;
	}
	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = titoloStudio;
	}
	@Column(name ="DESCRIZIONE_TITOLO_STUDIO")
	public String getDescrizioneTitoloStudio() {
		return descrizioneTitoloStudio;
	}
	public void setDescrizioneTitoloStudio(String descrizioneTitoloStudio) {
		this.descrizioneTitoloStudio = descrizioneTitoloStudio;
	}
	@Column(name ="ISCED")
	public String getIsced() {
		return isced;
	}
	public void setIsced(String isced) {
		this.isced = isced;
	}
	
	
}
