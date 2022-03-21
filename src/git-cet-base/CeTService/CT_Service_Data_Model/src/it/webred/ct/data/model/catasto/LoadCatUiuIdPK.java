package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the LOAD_CAT_UIU_ID database table.
 * 
 */
@Embeddable
public class LoadCatUiuIdPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CODI_FISC_LUNA")
	private String codiFiscLuna;

	private String sezione;

	@Column(name="ID_IMM")
	private long idImm;

	private long progressivo;

	private long seq;

	private long todelete;

    public LoadCatUiuIdPK() {
    }
	public String getCodiFiscLuna() {
		return this.codiFiscLuna;
	}
	public void setCodiFiscLuna(String codiFiscLuna) {
		this.codiFiscLuna = codiFiscLuna;
	}
	public String getSezione() {
		return this.sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public long getIdImm() {
		return this.idImm;
	}
	public void setIdImm(long idImm) {
		this.idImm = idImm;
	}
	public long getProgressivo() {
		return this.progressivo;
	}
	public void setProgressivo(long progressivo) {
		this.progressivo = progressivo;
	}
	public long getSeq() {
		return this.seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public long getTodelete() {
		return this.todelete;
	}
	public void setTodelete(long todelete) {
		this.todelete = todelete;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LoadCatUiuIdPK)) {
			return false;
		}
		LoadCatUiuIdPK castOther = (LoadCatUiuIdPK)other;
		return 
			this.codiFiscLuna.equals(castOther.codiFiscLuna)
			&& this.sezione.equals(castOther.sezione)
			&& (this.idImm == castOther.idImm)
			&& (this.progressivo == castOther.progressivo)
			&& (this.seq == castOther.seq)
			&& (this.todelete == castOther.todelete);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codiFiscLuna.hashCode();
		hash = hash * prime + this.sezione.hashCode();
		hash = hash * prime + ((int) (this.idImm ^ (this.idImm >>> 32)));
		hash = hash * prime + ((int) (this.progressivo ^ (this.progressivo >>> 32)));
		hash = hash * prime + ((int) (this.seq ^ (this.seq >>> 32)));
		hash = hash * prime + ((int) (this.todelete ^ (this.todelete >>> 32)));
		
		return hash;
    }
}