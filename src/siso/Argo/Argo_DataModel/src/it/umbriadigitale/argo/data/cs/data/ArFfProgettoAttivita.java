package it.umbriadigitale.argo.data.cs.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "AR_FF_PROGETTO_ATTIVITA")
public class ArFfProgettoAttivita implements java.io.Serializable {

	@Id
	@SequenceGenerator(name="AR_PROG_ATT_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_PROG_ATT_ID_GENERATOR")
	private Long id;
	private String codice;
	private String descrizione;
	
	@Column(name="USER_INS")
	private String usrIns;
	
	@Column(name="DT_INS")
	private Date dtIns;
	
	private Boolean abilitato;
	
	@Column(name="PROGETTO_ID")
	private Long progettoId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getUsrIns() {
		return usrIns;
	}

	public void setUsrIns(String usrIns) {
		this.usrIns = usrIns;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public Long getProgettoId() {
		return progettoId;
	}

	public void setProgettoId(Long progettoId) {
		this.progettoId = progettoId;
	}
}
