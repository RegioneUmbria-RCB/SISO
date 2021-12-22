package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the CS_TB_PROGETTO_ALTRO database table.
 * 
 */
@Entity
@Table(name="CS_TB_PROGETTO_ALTRO")
@NamedQuery(name="CsTbProgettoAltro.findAll", query="SELECT c FROM CsTbProgettoAltro c")
public class CsTbProgettoAltro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_PROGETTO_ALTRO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_PROGETTO_ALTRO_ID_GENERATOR")
	private long id;

	private String descrizione;
	
	private boolean abilitato;
	
	@Column(name="USER_INS")
	private String userIns;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	public CsTbProgettoAltro() {
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

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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

