package it.webred.ct.data.model.pgt;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PGT_SQL_VIS_LAYER database table.
 * 
 */
@Embeddable
public class PgtSqlVisLayerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_TMPL")
	private long idTmpl;

	@Column(name="ID_LAYER")
	private long idLayer;

	@Column(name="STND_LAYER")
	private String stndLayer;

	public PgtSqlVisLayerPK() {
	}
	public long getIdTmpl() {
		return this.idTmpl;
	}
	public void setIdTmpl(long idTmpl) {
		this.idTmpl = idTmpl;
	}
	public long getIdLayer() {
		return this.idLayer;
	}
	public void setIdLayer(long idLayer) {
		this.idLayer = idLayer;
	}
	public String getStndLayer() {
		return this.stndLayer;
	}
	public void setStndLayer(String stndLayer) {
		this.stndLayer = stndLayer;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PgtSqlVisLayerPK)) {
			return false;
		}
		PgtSqlVisLayerPK castOther = (PgtSqlVisLayerPK)other;
		return 
			(this.idTmpl == castOther.idTmpl)
			&& (this.idLayer == castOther.idLayer)
			&& this.stndLayer.equals(castOther.stndLayer);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idTmpl ^ (this.idTmpl >>> 32)));
		hash = hash * prime + ((int) (this.idLayer ^ (this.idLayer >>> 32)));
		hash = hash * prime + this.stndLayer.hashCode();
		
		return hash;
	}
}