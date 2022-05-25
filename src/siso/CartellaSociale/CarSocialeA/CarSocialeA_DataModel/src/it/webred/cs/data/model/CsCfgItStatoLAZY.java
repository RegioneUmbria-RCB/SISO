package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CS_CFG_IT_STATO database table.
 * 
 */
@Entity
@Table(name="CS_CFG_IT_STATO")
public class CsCfgItStatoLAZY implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_IT_STATO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_IT_STATO_ID_GENERATOR")
	private Long id;

	private String nome;

	@Column(name="DATI_LABEL")
	private String datiLabel;

	@Column(name="SEZIONE_ATTRIBUTI_LABEL")
	private String sezioneAttributiLabel;
	
	@Column(name="CSS_CLASS_STATO")
	private String cssClassStato;
	
	@Column(name="SEGNALATO_DA_LABEL")
	private String segnalatoDaLabel;
	
	@Column(name="SEGNALATO_A_LABEL")
	private String segnalatoALabel;
	
	@Column(name="OGGETTO_EMAIL")
	private String oggettoEmail;
	
	@Column(name="CORPO_EMAIL")
	private String corpoEmail;

	//bi-directional many-to-one association to CsCfgItStatoAttr	
  //bi-directional many-to-one association to CsOOperatoreSettore
  	@OneToMany(mappedBy="csCfgItStato")
  	private List<CsCfgItStatoAttrLAZY> csCfgItStatoAttrs;
  	
	
	//bi-directional many-to-one association to CsCfgItStatoAttr
	@OneToMany(mappedBy="csCfgItStatoDa")
	private List<CsCfgItTransizione> csCfgItTransiziones;

	
	
	public CsCfgItStatoLAZY() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSezioneAttributiLabel() {
		return sezioneAttributiLabel;
	}

	public void setSezioneAttributiLabel(String sezioneAttributiLabel) {
		this.sezioneAttributiLabel = sezioneAttributiLabel;
	}

	public String getCssClassStato() {
		return cssClassStato;
	}

	public void setCssClassStato(String cssClassStato) {
		this.cssClassStato = cssClassStato;
	}

	public String getSegnalatoDaLabel() {
		return segnalatoDaLabel;
	}

	public void setSegnalatoDaLabel(String segnalatoDaLabel) {
		this.segnalatoDaLabel = segnalatoDaLabel;
	}

	public String getSegnalatoALabel() {
		return segnalatoALabel;
	}

	public void setSegnalatoALabel(String segnalatoALabel) {
		this.segnalatoALabel = segnalatoALabel;
	}



	public List<CsCfgItStatoAttrLAZY> getCsCfgItStatoAttrs() {
		return csCfgItStatoAttrs;
	}

	public void setCsCfgItStatoAttrs(List<CsCfgItStatoAttrLAZY> csCfgItStatoAttrs) {
		this.csCfgItStatoAttrs = csCfgItStatoAttrs;
	}

	public String getOggettoEmail() {
		return oggettoEmail;
	}

	public void setOggettoEmail(String oggettoEmail) {
		this.oggettoEmail = oggettoEmail;
	}

	public String getCorpoEmail() {
		return corpoEmail;
	}

	public void setCorpoEmail(String corpoEmail) {
		this.corpoEmail = corpoEmail;
	}

	public List<CsCfgItTransizione> getCsCfgItTransiziones() {
		return csCfgItTransiziones;
	}

	public void setCsCfgItTransiziones(List<CsCfgItTransizione> csItTransiziones) {
		this.csCfgItTransiziones = csItTransiziones;
	}

	public String getDatiLabel() {
		return datiLabel;
	}

	public void setDatiLabel(String datiLabel) {
		this.datiLabel = datiLabel;
	}
	
}