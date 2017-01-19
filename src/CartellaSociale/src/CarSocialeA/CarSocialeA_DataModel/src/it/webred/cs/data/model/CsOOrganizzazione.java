package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CS_O_ORGANIZZAZIONE database table.
 * 
 */
@Entity
@Table(name="CS_O_ORGANIZZAZIONE")
@NamedQuery(name="CsOOrganizzazione.findAll", query="SELECT c FROM CsOOrganizzazione c")
public class CsOOrganizzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_O_ORGANIZZAZIONE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_O_ORGANIZZAZIONE_ID_GENERATOR")
	private Long id;

	private String abilitato;

	private String email;

	private String nome;

	private String tooltip;
	
	private String belfiore;

/*	//bi-directional many-to-one association to CsItStep
	@OneToMany(mappedBy="csOOrganizzazione1")
	private List<CsItStep> csItSteps1;

	//bi-directional many-to-one association to CsItStep
	@OneToMany(mappedBy="csOOrganizzazione2")
	private List<CsItStep> csItSteps2;

	//bi-directional many-to-one association to CsOSettore
	@OneToMany(mappedBy="csOOrganizzazione")
	private List<CsOSettore> csOSettores;

	//bi-directional many-to-one association to CsAlert
	@OneToMany(mappedBy="csOOrganizzazione1")
	private List<CsAlert> csAlerts1;

	//bi-directional many-to-one association to CsAlert
	@OneToMany(mappedBy="csOOrganizzazione2")
	private List<CsAlert> csAlerts2;*/

	public CsOOrganizzazione() {
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

/*	public List<CsItStep> getCsItSteps1() {
		return this.csItSteps1;
	}

	public void setCsItSteps1(List<CsItStep> csItSteps1) {
		this.csItSteps1 = csItSteps1;
	}

	public CsItStep addCsItSteps1(CsItStep csItSteps1) {
		getCsItSteps1().add(csItSteps1);
		csItSteps1.setCsOOrganizzazione1(this);

		return csItSteps1;
	}

	public CsItStep removeCsItSteps1(CsItStep csItSteps1) {
		getCsItSteps1().remove(csItSteps1);
		csItSteps1.setCsOOrganizzazione1(null);

		return csItSteps1;
	}

	public List<CsItStep> getCsItSteps2() {
		return this.csItSteps2;
	}

	public void setCsItSteps2(List<CsItStep> csItSteps2) {
		this.csItSteps2 = csItSteps2;
	}

	public CsItStep addCsItSteps2(CsItStep csItSteps2) {
		getCsItSteps2().add(csItSteps2);
		csItSteps2.setCsOOrganizzazione2(this);

		return csItSteps2;
	}

	public CsItStep removeCsItSteps2(CsItStep csItSteps2) {
		getCsItSteps2().remove(csItSteps2);
		csItSteps2.setCsOOrganizzazione2(null);

		return csItSteps2;
	}

	public List<CsOSettore> getCsOSettores() {
		return this.csOSettores;
	}

	public void setCsOSettores(List<CsOSettore> csOSettores) {
		this.csOSettores = csOSettores;
	}

	public CsOSettore addCsOSettore(CsOSettore csOSettore) {
		getCsOSettores().add(csOSettore);
		csOSettore.setCsOOrganizzazione(this);

		return csOSettore;
	}

	public CsOSettore removeCsOSettore(CsOSettore csOSettore) {
		getCsOSettores().remove(csOSettore);
		csOSettore.setCsOOrganizzazione(null);

		return csOSettore;
	}

	public List<CsAlert> getCsAlerts1() {
		return this.csAlerts1;
	}

	public void setCsAlerts1(List<CsAlert> csAlerts1) {
		this.csAlerts1 = csAlerts1;
	}

	public CsAlert addCsAlerts1(CsAlert csAlerts1) {
		getCsAlerts1().add(csAlerts1);
		csAlerts1.setCsOOrganizzazione1(this);

		return csAlerts1;
	}

	public CsAlert removeCsAlerts1(CsAlert csAlerts1) {
		getCsAlerts1().remove(csAlerts1);
		csAlerts1.setCsOOrganizzazione1(null);

		return csAlerts1;
	}

	public List<CsAlert> getCsAlerts2() {
		return this.csAlerts2;
	}

	public void setCsAlerts2(List<CsAlert> csAlerts2) {
		this.csAlerts2 = csAlerts2;
	}

	public CsAlert addCsAlerts2(CsAlert csAlerts2) {
		getCsAlerts2().add(csAlerts2);
		csAlerts2.setCsOOrganizzazione2(this);

		return csAlerts2;
	}

	public CsAlert removeCsAlerts2(CsAlert csAlerts2) {
		getCsAlerts2().remove(csAlerts2);
		csAlerts2.setCsOOrganizzazione2(null);

		return csAlerts2;
	}*/

}