package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SITIDECO database table.
 * 
 */
@Embeddable
public class SitidecoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String tablename;

	private String fieldname;

	private String code;

    public SitidecoPK() {
    }
	public String getTablename() {
		return this.tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getFieldname() {
		return this.fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitidecoPK)) {
			return false;
		}
		SitidecoPK castOther = (SitidecoPK)other;
		return 
			this.tablename.equals(castOther.tablename)
			&& this.fieldname.equals(castOther.fieldname)
			&& this.code.equals(castOther.code);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.tablename.hashCode();
		hash = hash * prime + this.fieldname.hashCode();
		hash = hash * prime + this.code.hashCode();
		
		return hash;
    }
}