package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SIT_LOAD_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="CS_LOAD_DOCUMENTO")
@NamedQuery(name="CsLoadDocumento.findAll", query="SELECT c FROM CsLoadDocumento c")
public class CsLoadDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_LOAD_DOCUMENTO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_LOAD_DOCUMENTO_ID_GENERATOR")
	private long id;

	@Lob
	private byte[] documento;
	
	private String tipo;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	private String nome;

	@Column(name="USR_INS")
	private String usrIns;

	public CsLoadDocumento() {
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}