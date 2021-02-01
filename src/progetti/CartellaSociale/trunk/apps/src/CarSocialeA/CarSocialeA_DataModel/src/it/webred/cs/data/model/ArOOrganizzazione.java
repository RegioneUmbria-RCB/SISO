package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the AR_O_ORGANIZZAZIONE database table.
 * SISO-575
 */
@Entity
@Table(name="AR_O_ORGANIZZAZIONE")
@NamedQuery(name="ArOOrganizzazione.findAll", query="SELECT a FROM ArOOrganizzazione a")
public class ArOOrganizzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String abilitato;

	private String belfiore;

	private String email;

	private String nome;

	@Column(name="PIVA_CF")
	private String pivaCf;

	private String tooltip;

	@Column(name="ZONA_NOME")
	private String zonaNome;

	//bi-directional many-to-one association to ArFfProgettoOrg
	@OneToMany(mappedBy="arOOrganizzazione")
	private List<ArFfProgettoOrg> arFfProgettoOrgs;

	public ArOOrganizzazione() {
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

	public String getBelfiore() {
		return this.belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
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

	public String getPivaCf() {
		return this.pivaCf;
	}

	public void setPivaCf(String pivaCf) {
		this.pivaCf = pivaCf;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getZonaNome() {
		return this.zonaNome;
	}

	public void setZonaNome(String zonaNome) {
		this.zonaNome = zonaNome;
	}

	public List<ArFfProgettoOrg> getArFfProgettoOrgs() {
		return this.arFfProgettoOrgs;
	}

	public void setArFfProgettoOrgs(List<ArFfProgettoOrg> arFfProgettoOrgs) {
		this.arFfProgettoOrgs = arFfProgettoOrgs;
	}

	public ArFfProgettoOrg addArFfProgettoOrg(ArFfProgettoOrg arFfProgettoOrg) {
		getArFfProgettoOrgs().add(arFfProgettoOrg);
		arFfProgettoOrg.setArOOrganizzazione(this);

		return arFfProgettoOrg;
	}

	public ArFfProgettoOrg removeArFfProgettoOrg(ArFfProgettoOrg arFfProgettoOrg) {
		getArFfProgettoOrgs().remove(arFfProgettoOrg);
		arFfProgettoOrg.setArOOrganizzazione(null);

		return arFfProgettoOrg;
	}

}