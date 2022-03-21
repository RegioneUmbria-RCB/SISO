package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the CS_A_CASO database table.
 * 
 */
@Entity
@Table(name="CS_A_CASO")
public class CsACasoBASIC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_CASO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_CASO_ID_GENERATOR")
	private Long id;

	private String note;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
	private Long identificativo;
	
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

	

	public CsACasoBASIC() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}