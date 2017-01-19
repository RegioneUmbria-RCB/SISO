package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the CS_CFG_IT_STATO_ATTR database table.
 * 
 */
@Entity
@Table(name="CS_CFG_INT_ESEG_STATO")
@NamedQuery(name="CsCfgIntEsegStato.findAll", query="SELECT c FROM CsCfgIntEsegStato c")
public class CsCfgIntEsegStato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_INT_ESEG_STATO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_INT_ESEG_STATO_ID_GENERATOR")
	private long id;

	private String nome;

	private String tipo;

	@Column(name="EROGAZIONE_POSSIBILE")
	private Boolean erogazionePossibile;
	
	private Integer ordine;

	 @ManyToMany
		@JoinTable(
			name="CS_CFG_INT_ESEG_STATO_INT"
			, joinColumns={
				@JoinColumn(name="STATO_ID")
				}
			, inverseJoinColumns={
				@JoinColumn(name="CFG_INT_ESEG_ID")
				}
			)
	private List<CsCfgIntEseg> csCfgIntEseg;
	
	public CsCfgIntEsegStato() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getErogazionePossibile() {
		return erogazionePossibile;
	}

	public void setErogazionePossibile(Boolean erogazionePossibile) {
		this.erogazionePossibile = erogazionePossibile;
	}

	public List<CsCfgIntEseg> getCsCfgIntEseg() {
		return csCfgIntEseg;
	}

	public void setCsCfgIntEseg(List<CsCfgIntEseg> csCfgIntEseg) {
		this.csCfgIntEseg = csCfgIntEseg;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

}