package it.webred.ct.service.gestioneDBDoc.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SIT_LOAD_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="SIT_LOAD_DOCUMENTO")
public class SitLoadDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Lob
	private byte[] documento;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	private String nome;

	@Column(name="USR_INS")
	private String usrIns;

	public SitLoadDocumento() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getDocumento() {
		return this.documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsrIns() {
		return this.usrIns;
	}

	public void setUsrIns(String usrIns) {
		this.usrIns = usrIns;
	}

}