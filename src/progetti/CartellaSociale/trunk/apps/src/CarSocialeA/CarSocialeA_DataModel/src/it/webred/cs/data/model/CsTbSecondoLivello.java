package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the CS_TB_SECONDO_LIVELLO database table.
 * 
 */

@Entity
@Table(name="CS_TB_SECONDO_LIVELLO")
@NamedQuery(name="CsTbSecondoLivello.findAll", query="SELECT c FROM CsTbSecondoLivello c")
public class CsTbSecondoLivello implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CS_TB_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_ID_GENERATOR")
	private Long id;
	
	@Column(name="DESCRIZIONE")
	private String descrizione;
	
	@Column(name="ABILITATO")
	private Boolean abilitato;
	
    public Long getId() {
		return id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
}
