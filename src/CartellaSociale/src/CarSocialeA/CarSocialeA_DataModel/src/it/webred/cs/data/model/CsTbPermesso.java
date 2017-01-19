package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_PERMESSO database table.
 * 
 */
@Entity
@Table(name="CS_TB_PERMESSO")
@NamedQuery(name="CsTbPermesso.findAll", query="SELECT c FROM CsTbPermesso c order by c.gruppo, c.ordInGruppo, to_number(c.abilitato) desc")
public class CsTbPermesso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_PERMESSO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_PERMESSO_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	private Integer gruppo;
	
	@Column(name="ORD_IN_GRUPPO")
	private Integer ordInGruppo;

	public CsTbPermesso() {
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

	public Integer getGruppo() {
		return gruppo;
	}

	public Integer getOrdInGruppo() {
		return ordInGruppo;
	}

	public void setGruppo(Integer gruppo) {
		this.gruppo = gruppo;
	}

	public void setOrdInGruppo(Integer ordInGruppo) {
		this.ordInGruppo = ordInGruppo;
	}
}