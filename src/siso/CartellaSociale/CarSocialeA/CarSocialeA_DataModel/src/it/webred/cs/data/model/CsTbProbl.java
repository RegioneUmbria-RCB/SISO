package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the CS_TB_PROB database table.
 * 
 */
@Entity
@Table(name="CS_TB_PROB")
@NamedQuery(name="CsTbProbl.findAll", query="SELECT c FROM CsTbProbl c")
public class CsTbProbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_PROB_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_PROB_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	
	@Column(name="FLAG_MAT_IMM")
	private String flagMatImm;
	
	@Column(name="TIPO", nullable=false)
	private String tipo;
	
	
	public CsTbProbl() {
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
	 * @return the flagMatImm
	 */
	public String getFlagMatImm() {
		return flagMatImm;
	}

	/**
	 * @param flagMatImm the flagMatImm to set
	 */
	public void setFlagMatImm(String flagMatImm) {
		this.flagMatImm = flagMatImm;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	

}