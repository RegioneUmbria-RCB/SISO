package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CAT_D_TOPONIMI database table.
 * 
 */
@Entity
@Table(name="CAT_D_TOPONIMI")
public class CatDToponimi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PK_ID")
	private long pkId;

	private String descr;

    public CatDToponimi() {
    }

	public long getPkId() {
		return this.pkId;
	}

	public void setPkId(long pkId) {
		this.pkId = pkId;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}