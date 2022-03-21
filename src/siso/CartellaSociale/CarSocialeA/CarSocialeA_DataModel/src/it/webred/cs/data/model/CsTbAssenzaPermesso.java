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

/**
 * The persistent class for the CS_TB_ASSENZA_PERMESSO database table.
 * 
 */

//SISO-792
@Entity
@Table(name="CS_TB_ASSENZA_PERMESSO")
@NamedQuery(name="CsTbAssenzaPermesso.findAll", query="SELECT c FROM CsTbAssenzaPermesso c where c.abilitato = 1 order by c.gruppo, c.ordInGruppo, to_number(c.abilitato) desc")
public class CsTbAssenzaPermesso implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
	@SequenceGenerator(name="CS_TB_ASSENZA_PERMESSO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_ASSENZA_PERMESSO_ID_GENERATOR")
	private long id;
    
    private String abilitato;

	private String descrizione;

	private String tooltip;
	
	private Integer gruppo;
	
	@Column(name="ORD_IN_GRUPPO")
	private Integer ordInGruppo;

	public long getId() {
		return id;
	}

	public String getAbilitato() {
		return abilitato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getTooltip() {
		return tooltip;
	}

	public Integer getGruppo() {
		return gruppo;
	}

	public Integer getOrdInGruppo() {
		return ordInGruppo;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public void setGruppo(Integer gruppo) {
		this.gruppo = gruppo;
	}

	public void setOrdInGruppo(Integer ordInGruppo) {
		this.ordInGruppo = ordInGruppo;
	}
	
	

}
