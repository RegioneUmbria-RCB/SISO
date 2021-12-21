package it.webred.cs.csa.ejb.dto.pai.pti;

import java.io.Serializable;
import java.util.Date;

public class ArCsPaiPTIDocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1462965766212956396L;

	private Long id;

	private Long idPaiPTI;

	private String codRouting;

	private byte[] documento;

	private String tipo;

	private Date dtIns;

	private String nome;

	private String tipoDocumento;

	private String usrIns;

	private Date validoDa;

	private Date validoA;

	private Boolean flgAppropriato;

	private Boolean flgNotifica;

	private String note;

	private ArCsPaiInfoSinteticheDTO arCsPaiInfoSinteticheDTO;

	public ArCsPaiPTIDocumentoDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaiPTI() {
		return idPaiPTI;
	}

	public void setIdPaiPTI(Long idPaiPTI) {
		this.idPaiPTI = idPaiPTI;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getUsrIns() {
		return usrIns;
	}

	public void setUsrIns(String usrIns) {
		this.usrIns = usrIns;
	}

	public Date getValidoDa() {
		return validoDa;
	}

	public void setValidoDa(Date validoDa) {
		this.validoDa = validoDa;
	}

	public Date getValidoA() {
		return validoA;
	}

	public void setValidoA(Date validoA) {
		this.validoA = validoA;
	}

	public Boolean getFlgAppropriato() {
		return flgAppropriato;
	}

	public void setFlgAppropriato(Boolean flgAppropriato) {
		this.flgAppropriato = flgAppropriato;
	}

	public Boolean getFlgNotifica() {
		return flgNotifica;
	}

	public void setFlgNotifica(Boolean flgNotifica) {
		this.flgNotifica = flgNotifica;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ArCsPaiInfoSinteticheDTO getArCsPaiInfoSinteticheDTO() {
		return arCsPaiInfoSinteticheDTO;
	}

	public void setArCsPaiInfoSinteticheDTO(ArCsPaiInfoSinteticheDTO arCsPaiInfoSinteticheDTO) {
		this.arCsPaiInfoSinteticheDTO = arCsPaiInfoSinteticheDTO;
	}

}
