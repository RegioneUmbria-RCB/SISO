package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CONS_DECO_TAB database table.
 * 
 */
@Entity
@Table(name="CONS_DECO_TAB")
public class ConsDecoTab implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConsDecoTabPK id;

	private String datatype;

	private String description;

    public ConsDecoTab() {
    }

	public ConsDecoTabPK getId() {
		return this.id;
	}

	public void setId(ConsDecoTabPK id) {
		this.id = id;
	}
	
	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}