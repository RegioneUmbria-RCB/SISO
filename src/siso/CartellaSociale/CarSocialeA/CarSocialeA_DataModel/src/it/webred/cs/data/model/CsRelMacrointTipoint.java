package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CS_REL_MACROINT_TIPOINT")
public class CsRelMacrointTipoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsRelMacrointTipointPK id;
	
	//uni-directional many-to-one association to CsCTipoIntervento
	@ManyToOne
	@JoinColumn(name="TIPO_INTERVENTO_ID", insertable=false, updatable=false)
	private CsCTipoIntervento csCTipoIntervento;

	//uni-directional many-to-one association to CsDRelazione
	@ManyToOne
	@JoinColumn(name="MACRO_INT_ID", insertable=false, updatable=false)
	private CsTbMacroIntervento csTbMacroIntervento;


	public CsRelMacrointTipointPK getId() {
		return id;
	}

	public void setId(CsRelMacrointTipointPK id) {
		this.id = id;
	}

	public CsCTipoIntervento getCsCTipoIntervento() {
		return csCTipoIntervento;
	}



	public void setCsCTipoIntervento(CsCTipoIntervento csCTipoIntervento) {
		this.csCTipoIntervento = csCTipoIntervento;
	}



	public CsTbMacroIntervento getCsTbMacroIntervento() {
		return csTbMacroIntervento;
	}



	public void setCsTbMacroIntervento(CsTbMacroIntervento csTbMacroIntervento) {
		this.csTbMacroIntervento = csTbMacroIntervento;
	}



	public boolean equals(Object other) {	
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof CsRelMacrointTipoint))return false;
	    CsRelMacrointTipoint otherMyClass = (CsRelMacrointTipoint)other;
	    
	    return (id.equals(otherMyClass.id));			
	}

}