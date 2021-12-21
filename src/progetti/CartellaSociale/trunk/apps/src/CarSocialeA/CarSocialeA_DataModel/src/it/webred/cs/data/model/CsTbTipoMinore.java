package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CS_TB_TIPO_MINORE")
@NamedQuery(name="CsTbTipoMinore.findAll", query="SELECT c FROM CsTbTipoMinore c")
public class CsTbTipoMinore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@SequenceGenerator(name="CS_TB_TIPO_MINORE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_TIPO_MINORE_ID_GENERATOR")
	private long id;
	

	@Column(name = "TIPO_MINORE")
	private String tipoMinore;

	private String tooltip;
	
	private boolean abilitato;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipoMinore() {
		return tipoMinore;
	}

	public void setTipoMinore(String tipoMinore) {
		this.tipoMinore = tipoMinore;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
	
	
	
}
