package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_TIPO_DIARIO database table.
 * 
 */
@Entity
@Table(name="CS_TB_TIPO_DIARIO")
@NamedQuery(name="CsTbTipoDiario.findAll", query="SELECT c FROM CsTbTipoDiario c")
public class CsTbTipoDiario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_TIPO_DIARIO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_TIPO_DIARIO_ID_GENERATOR")
	private Long id;

	private String abilitato;

	private String descrizione;

	@Column(name="MACRO_TIPO")
	private String macroTipo;

	private String tooltip;

	public CsTbTipoDiario() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public String getMacroTipo() {
		return this.macroTipo;
	}

	public void setMacroTipo(String macroTipo) {
		this.macroTipo = macroTipo;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}