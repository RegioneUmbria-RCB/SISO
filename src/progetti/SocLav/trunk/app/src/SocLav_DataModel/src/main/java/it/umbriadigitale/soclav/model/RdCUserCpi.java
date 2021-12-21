package it.umbriadigitale.soclav.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.umbriadigitale.soclav.model.anpal.RdCTbCpi;

@Entity
@Table(name="RDC_USER_CPI")
public class RdCUserCpi implements Serializable {
	
	@EmbeddedId
	private RdCUserCpiPK id;
	
	@ManyToOne
	@JoinColumn(name="USERNAME", insertable = false, updatable = false)
	private RdCUser user;
	
	@ManyToOne
	@JoinColumn(name="COD_CPI", insertable = false, updatable = false)
	private RdCTbCpi cpi;
	
	private Boolean abilitato;
	
	public RdCUserCpiPK getId() {
		return id;
	}

	public void setId(RdCUserCpiPK id) {
		this.id = id;
	}

	public RdCUser getUser() {
		return user;
	}

	public void setUser(RdCUser user) {
		this.user = user;
	}

	public RdCTbCpi getCpi() {
		return cpi;
	}

	public void setCpi(RdCTbCpi cpi) {
		this.cpi = cpi;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
	
}