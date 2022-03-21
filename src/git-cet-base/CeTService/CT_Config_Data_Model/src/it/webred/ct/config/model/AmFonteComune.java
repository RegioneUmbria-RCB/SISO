package it.webred.ct.config.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the AM_FONTE_COMUNE database table.
 * 
 */
@Entity
@Table(name="AM_FONTE_COMUNE")
public class AmFonteComune implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AmFonteComunePK id;

	//uni-directional many-to-one association to AmComune
    @ManyToOne
	@JoinColumn(name="FK_AM_FONTE",  insertable=false, updatable=false)
	private AmFonte amFonte;

	//bi-directional many-to-one association to AmKeyValueExt
	/*@OneToMany(mappedBy="amFonteComune")
	private Set<AmKeyValueExt> amKeyValueExts;*/

    public AmFonteComune() {
    }

	public AmFonteComunePK getId() {
		return this.id;
	}

	public void setId(AmFonteComunePK id) {
		this.id = id;
	}
	
	public AmFonte getAmFonte() {
		return this.amFonte;
	}

	public void setAmFonte(AmFonte amFonte) {
		this.amFonte = amFonte;
	}
	
	/*public Set<AmKeyValueExt> getAmKeyValueExts() {
		return this.amKeyValueExts;
	}

	public void setAmKeyValueExts(Set<AmKeyValueExt> amKeyValueExts) {
		this.amKeyValueExts = amKeyValueExts;
	}*/
	
}