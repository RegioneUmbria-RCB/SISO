package it.webred.ct.data.model.pgt;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PGT_SQL_PALETTE database table.
 * 
 */
@Entity
@Table(name="PGT_SQL_PALETTE")
public class PgtSqlPalette implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descr;

	private String rgb;

    public PgtSqlPalette() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getRgb() {
		return this.rgb;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}

}