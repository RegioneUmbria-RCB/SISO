package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CS_REL_INT_SETTORE database table.
 * 
 */

@Entity
@Table(name="CS_REL_INT_SETTORE")
@NamedQueries(value={
		@NamedQuery(name = "CsRelIntSettore.findIdSettoriByInterventoISTATandInterventoCustom", query="select s.idSettore from CsRelIntSettore s where s.idIntervento = :idInterventoISTAT and s.idInterventoCustom = :idInterventoCUSTOM "),
	    @NamedQuery(name = "CsRelIntSettore.findIdSettoriByInterventoCustom", query="select s.idSettore from CsRelIntSettore s where s.idInterventoCustom = :idInterventoCUSTOM ") 
	})
public class CsRelIntSettore implements Serializable {	
	private static final long serialVersionUID = 1L;

	public CsRelIntSettore() {
	}
   
	@Id	
	@SequenceGenerator(name="CS_REL_INT_SETTORE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_REL_INT_SETTORE_ID_GENERATOR")
	private Long id;

	@Column(name="ID_INTERVENTO")
	private Long idIntervento; //intervento ISTAT

	@Column(name="ID_SETTORE")
	private Long idSettore;

	@Column(name="ID_INTERVENTOCUSTOM")
	private Long idInterventoCustom; //intervento CUSTOM (4° livello) che per ROMACAPITALE è anche uguale all'organizzazione (ma con id diversi perchè l'organizzazione è stata inserita con sequenceID!!!)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId_intervento() {
		return idIntervento;
	}

	public void setId_intervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}

	public Long getId_settore() {
		return idSettore;
	}

	public void setId_settore(Long idSettore) {
		this.idSettore = idSettore;
	}
	
	public Long getIdInterventoCustom() {
		return idInterventoCustom;
	}

	public void setIdInterventoCustom(Long idInterventoCustom) {
		this.idInterventoCustom = idInterventoCustom;
	}
}