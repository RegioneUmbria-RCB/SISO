package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.util.Date;

public class CsLoadDocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2812105986020148454L;

	private long id;

	private byte[] documento;

	private String tipo;

	private Date dtIns;

	private String nome;

	private String usrIns;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsrIns() {
		return usrIns;
	}

	public void setUsrIns(String usrIns) {
		this.usrIns = usrIns;
	}

}
