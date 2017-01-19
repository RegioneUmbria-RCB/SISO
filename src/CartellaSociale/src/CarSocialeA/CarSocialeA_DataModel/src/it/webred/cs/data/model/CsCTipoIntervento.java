package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the CS_C_TIPO_INTERVENTO database table.
 * 
 */
@Entity
@Table(name="CS_C_TIPO_INTERVENTO")
@NamedQuery(name="CsCTipoIntervento.findAll", query="SELECT c FROM CsCTipoIntervento c")
public class CsCTipoIntervento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_C_TIPO_INTERVENTO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_C_TIPO_INTERVENTO_ID_GENERATOR")
	private Long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;
	
	private String tipo;
	
	//bi-directional many-to-one association to VArTClasse
	@ManyToOne
	@JoinColumn(name="CODICE_MEMO", referencedColumnName="CODICE_MEMO")
	private ArTClasse ArTClasse;

/*	//bi-directional many-to-one association to CsRelCatsocTipoInter
	@OneToMany(mappedBy="csCTipoIntervento")
	private List<CsRelCatsocTipoInter> csRelCatsocTipoInters;

	//bi-directional many-to-many association to CsDRelazione
	@ManyToMany(mappedBy="csCTipoInterventos")
	private List<CsDRelazione> csDRelaziones;*/

	public CsCTipoIntervento() {
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

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

/*	public List<CsRelCatsocTipoInter> getCsRelCatsocTipoInters() {
		return this.csRelCatsocTipoInters;
	}

	public void setCsRelCatsocTipoInters(List<CsRelCatsocTipoInter> csRelCatsocTipoInters) {
		this.csRelCatsocTipoInters = csRelCatsocTipoInters;
	}

	public CsRelCatsocTipoInter addCsRelCatsocTipoInter(CsRelCatsocTipoInter csRelCatsocTipoInter) {
		getCsRelCatsocTipoInters().add(csRelCatsocTipoInter);
		csRelCatsocTipoInter.setCsCTipoIntervento(this);

		return csRelCatsocTipoInter;
	}

	public CsRelCatsocTipoInter removeCsRelCatsocTipoInter(CsRelCatsocTipoInter csRelCatsocTipoInter) {
		getCsRelCatsocTipoInters().remove(csRelCatsocTipoInter);
		csRelCatsocTipoInter.setCsCTipoIntervento(null);

		return csRelCatsocTipoInter;
	}

	public List<CsDRelazione> getCsDRelaziones() {
		return this.csDRelaziones;
	}

	public void setCsDRelaziones(List<CsDRelazione> csDRelaziones) {
		this.csDRelaziones = csDRelaziones;
	}
*/
	

	public ArTClasse getVArTClasse() {
		return this.ArTClasse;
	}

	public void setVArTClasse(ArTClasse ArTClasse) {
		this.ArTClasse = ArTClasse;
	}
	//FINE MOD-RL

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