package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CS_TB_TIPO_SGT_BENEFICIARIO database table.
 * 
 */
@Entity
@Table(name = "CS_TB_TIPO_SGT_BENEFICIARIO")
@NamedQuery(name = "CsTbTipoSgtBeneficiario.findAll", query = "SELECT c FROM CsTbTipoSgtBeneficiario c")
public class CsTbTipoSgtBeneficiario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_TB_TIPO_SGT_BENEFICIARIO_ID_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_TB_TIPO_SGT_BENEFICIARIO_ID_GENERATOR")
	private long id;

	@Column(name = "RAGIONE_SOCIALE")
	private String ragioneSociale;

	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;

	private boolean abilitato;

	@Column(name = "USER_INS")
	private String userIns;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS")
	private Date dtIns;

	public CsTbTipoSgtBeneficiario() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

}