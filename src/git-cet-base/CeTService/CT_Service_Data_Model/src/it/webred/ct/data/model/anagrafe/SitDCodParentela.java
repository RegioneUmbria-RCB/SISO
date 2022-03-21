package it.webred.ct.data.model.anagrafe;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SIT_D_COD_PARENTELA database table.
 * 
 */
@Entity
@Table(name="SIT_D_COD_PARENTELA")
public class SitDCodParentela implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="\"VALUE\"")
	private String value;

    public SitDCodParentela() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}