package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CS_CFG_IT_TRANSIZIONE database table.
 * 
 */
@Entity
@Table(name="CS_CFG_IT_TRANSIZIONE")
public class CsCfgItTransizioneLAZY implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_IT_TRANSIZIONE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_IT_TRANSIZIONE_ID_GENERATOR")
	private long id;

	private String nome;

	@Column(name="STATO_OPERATORE")
	private String statoOperatore;

	@Column(name="STATO_ORGANIZZAZIONE")
	private String statoOrganizzazione;

	@Column(name="STATO_SETTORE")
	private String statoSettore;

	//bi-directional many-to-one association to CsCfgItRuoloTransizione
	@OneToMany(mappedBy="csCfgItTransizione", fetch = FetchType.LAZY)
	private List<CsCfgItRuoloTransizioneLAZY> csCfgItRuoloTransiziones;

	//bi-directional many-to-one association to CsCfgItStato
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name="CFG_IT_STATO_A_ID")
	private CsCfgItStatoLAZY csCfgItStatoA;

	//bi-directional many-to-one association to CsCfgItStato
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CFG_IT_STATO_DA_ID")
	private CsCfgItStatoLAZY csCfgItStatoDa;

	public CsCfgItTransizioneLAZY() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatoOperatore() {
		return this.statoOperatore;
	}

	public void setStatoOperatore(String statoOperatore) {
		this.statoOperatore = statoOperatore;
	}

	public String getStatoOrganizzazione() {
		return this.statoOrganizzazione;
	}

	public void setStatoOrganizzazione(String statoOrganizzazione) {
		this.statoOrganizzazione = statoOrganizzazione;
	}

	public String getStatoSettore() {
		return this.statoSettore;
	}

	public void setStatoSettore(String statoSettore) {
		this.statoSettore = statoSettore;
	}

	public List<CsCfgItRuoloTransizioneLAZY> getCsCfgItRuoloTransiziones() {
		return this.csCfgItRuoloTransiziones;
	}

	public void setCsCfgItRuoloTransiziones(List<CsCfgItRuoloTransizioneLAZY> csCfgItRuoloTransiziones) {
		this.csCfgItRuoloTransiziones = csCfgItRuoloTransiziones;
	}

	public CsCfgItRuoloTransizioneLAZY addCsCfgItRuoloTransizione(CsCfgItRuoloTransizioneLAZY csCfgItRuoloTransizione) {
		getCsCfgItRuoloTransiziones().add(csCfgItRuoloTransizione);
		csCfgItRuoloTransizione.setCsCfgItTransizione(this);

		return csCfgItRuoloTransizione;
	}

	public CsCfgItRuoloTransizione removeCsCfgItRuoloTransizione(CsCfgItRuoloTransizione csCfgItRuoloTransizione) {
		getCsCfgItRuoloTransiziones().remove(csCfgItRuoloTransizione);
		csCfgItRuoloTransizione.setCsCfgItTransizione(null);

		return csCfgItRuoloTransizione;
	}

	public CsCfgItStatoLAZY getCsCfgItStatoA() {
		return this.csCfgItStatoA;
	}

	public void setCsCfgItStatoA(CsCfgItStatoLAZY csCfgItStatoA) {
		this.csCfgItStatoA = csCfgItStatoA;
	}

	public CsCfgItStatoLAZY getCsCfgItStatoDa() {
		return this.csCfgItStatoDa;
	}

	public void setCsCfgItStatoDa(CsCfgItStatoLAZY csCfgItStatoDa) {
		this.csCfgItStatoDa = csCfgItStatoDa;
	}

}