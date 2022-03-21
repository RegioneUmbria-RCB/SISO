package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the CS_TB_MICRO_ATTIVITA database table.
 * 
 */
@Entity
@Table(name="CS_TB_MICRO_ATTIVITA")
@NamedQuery(name="CsTbMicroAttivita.findAll", query="SELECT c FROM CsTbMicroAttivita c")
public class CsTbMicroAttivita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_MICRO_ATTIVITA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_MICRO_ATTIVITA_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	//uni-directional many-to-one association to CsTbMacroAttivita
	@ManyToOne
	@JoinColumn(name="MACRO_ATT_ID", insertable=false, updatable=false)
	private CsTbMacroAttivita macroAttivita;

	@Column(name="FLAG_TIPO_FORM")
	private String flagTipoForm;
	
	public CsTbMicroAttivita() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public CsTbMacroAttivita getMacroAttivita() {
		return macroAttivita;
	}

	public void setMacroAttivita(CsTbMacroAttivita macroAttivita) {
		this.macroAttivita = macroAttivita;
	}

	/**
	 * @return the flagTipoForm
	 */
	public String getFlagTipoForm() {
		return flagTipoForm;
	}

	/**
	 * @param flagTipoForm the flagTipoForm to set
	 */
	public void setFlagTipoForm(String flagTipoForm) {
		this.flagTipoForm = flagTipoForm;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
	
	@Override
	public boolean equals(Object other) {	
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof CsTbMicroAttivita))return false;
	    CsTbMicroAttivita otherMyClass = (CsTbMicroAttivita)other;
	    
	    return (id==otherMyClass.id);			
	}
}