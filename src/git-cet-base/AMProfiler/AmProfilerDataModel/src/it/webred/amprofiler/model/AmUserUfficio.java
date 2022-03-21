package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the AM_USER_UFFICIO database table.
 * 
 */
@Entity
@Table(name="AM_USER_UFFICIO")
public class AmUserUfficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FK_AM_USER")
	private String fkAmUser;

	private String direzione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_UPDATE")
	private Date dtUpdate;

	private String email;

	private String settore;

	private String telefono;

	public AmUserUfficio() {
	}

	public String getFkAmUser() {
		return this.fkAmUser;
	}

	public void setFkAmUser(String fkAmUser) {
		this.fkAmUser = fkAmUser;
	}

	public String getDirezione() {
		return this.direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtUpdate() {
		return this.dtUpdate;
	}

	public void setDtUpdate(Date dtUpdate) {
		this.dtUpdate = dtUpdate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSettore() {
		return this.settore;
	}

	public void setSettore(String settore) {
		this.settore = settore;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}