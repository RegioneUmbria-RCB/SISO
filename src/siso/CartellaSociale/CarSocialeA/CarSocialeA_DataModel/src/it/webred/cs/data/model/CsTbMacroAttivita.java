package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the CS_TB_MACRO_ATTIVITA database table.
 * 
 */
@Entity
@Table(name="CS_TB_MACRO_ATTIVITA")
@NamedQuery(name="CsTbMacroAttivita.findAll", query="SELECT c FROM CsTbMacroAttivita c")
public class CsTbMacroAttivita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_MACRO_ATTIVITA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_MACRO_ATTIVITA_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;

	@Column(name="FLAG_CON_CHI")
	private Boolean flagConChi;
	
	@Column(name="FLAG_RIUNIONE_CON")
	private Boolean flagRiunioneCon;
	
	@OneToMany(mappedBy="macroAttivita", fetch=FetchType.LAZY)
	private List<CsTbMicroAttivita> lstMicroAttivita;
	
	public CsTbMacroAttivita() {
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

	/**
	 * @return the flagRiunioneCon
	 */
	public Boolean getFlagRiunioneCon() {
		return flagRiunioneCon;
	}

	/**
	 * @param flagRiunioneCon the flagRiunioneCon to set
	 */
	public void setFlagRiunioneCon(Boolean flagRiunioneCon) {
		this.flagRiunioneCon = flagRiunioneCon;
	}

	/**
	 * @return the flagConChi
	 */
	public Boolean getFlagConChi() {
		return flagConChi;
	}

	/**
	 * @param flagConChi the flagConChi to set
	 */
	public void setFlagConChi(Boolean flagConChi) {
		this.flagConChi = flagConChi;
	}
	
	public List<CsTbMicroAttivita> getLstMicroAttivita() {
		return lstMicroAttivita;
	}

	public void setLstMicroAttivita(List<CsTbMicroAttivita> lstMicroAttivita) {
		this.lstMicroAttivita = lstMicroAttivita;
	}

	@Override
	public boolean equals(Object other) {	
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof CsTbMacroAttivita))return false;
	    CsTbMacroAttivita otherMyClass = (CsTbMacroAttivita)other;
	    
	    return (id==otherMyClass.id);			
	}

}