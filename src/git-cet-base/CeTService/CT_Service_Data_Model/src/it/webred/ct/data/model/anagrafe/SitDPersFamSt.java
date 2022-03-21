package it.webred.ct.data.model.anagrafe;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SIT_D_PERS_FAM_ST database table.
 * 
 */
@Entity
@Table(name="SIT_D_PERS_FAM_ST")
@NamedQuery(name="SitDPersFamSt.findAll", query="SELECT s FROM SitDPersFamSt s")
public class SitDPersFamSt implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitDPersFamStPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_DATO")
	private Date dtFineDato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_DATO")
	private Date dtInizioDato;

	private String processid;

	@Column(name="RELAZ_PAR")
	private String relazPar;

	public SitDPersFamSt() {
	}

	public SitDPersFamStPK getId() {
		return this.id;
	}

	public void setId(SitDPersFamStPK id) {
		this.id = id;
	}

	public Date getDtFineDato() {
		return this.dtFineDato;
	}

	public void setDtFineDato(Date dtFineDato) {
		this.dtFineDato = dtFineDato;
	}

	public Date getDtInizioDato() {
		return this.dtInizioDato;
	}

	public void setDtInizioDato(Date dtInizioDato) {
		this.dtInizioDato = dtInizioDato;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getRelazPar() {
		return this.relazPar;
	}

	public void setRelazPar(String relazPar) {
		this.relazPar = relazPar;
	}

}