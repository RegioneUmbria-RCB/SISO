package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The primary key class for the LOAD_CAT_UIU_IND database table.
 * 
 */

public class LoadCatUiuIndPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CODI_FISC_LUNA")
	private String codiFiscLuna;

	private String sezione;

	@Column(name="ID_IMM")
	private BigDecimal idImm;

	private BigDecimal progressivo;

	private BigDecimal seq;


    public LoadCatUiuIndPK() {
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
	public BigDecimal getIdImm() {
		return this.idImm;
	}
	public void setIdImm(BigDecimal idImm) {
		this.idImm = idImm;
	}
	public BigDecimal getProgressivo() {
		return this.progressivo;
	}
	public void setProgressivo(BigDecimal progressivo) {
		this.progressivo = progressivo;
	}
	public BigDecimal getSeq() {
		return this.seq;
	}
	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LoadCatUiuIndPK)) {
			return false;
		}
		LoadCatUiuIndPK castOther = (LoadCatUiuIndPK)other;
		return 
			this.codiFiscLuna.equals(castOther.codiFiscLuna)
			&& this.sezione.equals(castOther.sezione)
			&& (this.idImm == castOther.idImm)
			&& (this.progressivo == castOther.progressivo)
			&& (this.seq == castOther.seq)
		;

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codiFiscLuna.hashCode();
		hash = hash * prime + this.sezione.hashCode();
		hash = hash * prime + ((int) (this.idImm.hashCode() ^ (this.idImm.hashCode() >>> 32)));
		hash = hash * prime + ((int) (this.progressivo.hashCode() ^ (this.progressivo.hashCode() >>> 32)));
		hash = hash * prime + ((int) (this.seq.hashCode() ^ (this.seq.hashCode() >>> 32)));
		
		return hash;
    }
}