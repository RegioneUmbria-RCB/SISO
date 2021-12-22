package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CS_CFG_IT_RUOLO_TRANSIZIONE database table.
 * 
 */
@Entity
@Table(name="CS_CFG_IT_RUOLO_TRANSIZIONE")
@NamedQuery(name="CsCfgItRuoloTransizione.findAll", query="SELECT c FROM CsCfgItRuoloTransizione c")
public class CsCfgItRuoloTransizione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_IT_RUOLO_TRANSIZIONE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_IT_RUOLO_TRANSIZIONE_ID_GENERATOR")
	private long id;

	@Column(name="NOME_RUOLO")
	private String nomeRuolo;

	//bi-directional many-to-one association to CsCfgItTransizione
	@ManyToOne
	@JoinColumn(name="CFG_IT_TRANSIZIONE_ID")
	private CsCfgItTransizione csCfgItTransizione;

	public CsCfgItRuoloTransizione() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeRuolo() {
		return this.nomeRuolo;
	}

	public void setNomeRuolo(String nomeRuolo) {
		this.nomeRuolo = nomeRuolo;
	}

	public CsCfgItTransizione getCsCfgItTransizione() {
		return this.csCfgItTransizione;
	}

	public void setCsCfgItTransizione(CsCfgItTransizione csCfgItTransizione) {
		this.csCfgItTransizione = csCfgItTransizione;
	}

}