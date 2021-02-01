package it.webred.cs.data.model;


import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the AR_FF_PROGETTO database table.

 * 
 */
@Entity
@Table(name="AR_FF_PROGETTO")
@NamedQuery(name="ArFfProgetto.findAll", query="SELECT a FROM ArFfProgetto a")
public class ArFfProgetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String abilitato;

	@Column(name="CODICE_MEMO")
	private String codiceMemo;

	private String descrizione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	//bi-directional many-to-one association to ArFfProgettoOrg
	@OneToMany(mappedBy="arFfProgetto")
	private List<ArFfProgettoOrg> arFfProgettoOrgs;

	public ArFfProgetto() {
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

	public String getCodiceMemo() {
		return this.codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public List<ArFfProgettoOrg> getArFfProgettoOrgs() {
		return this.arFfProgettoOrgs;
	}

	public void setArFfProgettoOrgs(List<ArFfProgettoOrg> arFfProgettoOrgs) {
		this.arFfProgettoOrgs = arFfProgettoOrgs;
	}

	public ArFfProgettoOrg addArFfProgettoOrg(ArFfProgettoOrg arFfProgettoOrg) {
		getArFfProgettoOrgs().add(arFfProgettoOrg);
		arFfProgettoOrg.setArFfProgetto(this);

		return arFfProgettoOrg;
	}

	public ArFfProgettoOrg removeArFfProgettoOrg(ArFfProgettoOrg arFfProgettoOrg) {
		getArFfProgettoOrgs().remove(arFfProgettoOrg);
		arFfProgettoOrg.setArFfProgetto(null);

		return arFfProgettoOrg;
	}

}