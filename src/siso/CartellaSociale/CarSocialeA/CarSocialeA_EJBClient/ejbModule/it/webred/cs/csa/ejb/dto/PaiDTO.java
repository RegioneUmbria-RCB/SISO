package it.webred.cs.csa.ejb.dto;

import java.util.List;

import it.webred.cs.data.model.*;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class PaiDTO extends CeTBaseObject {

	private static final long serialVersionUID = 6770900322548216309L;

	private CsDPai pai;

	private Long idRelazione;

	private Long casoId;

	private Long responsabileId;

	private CsOOperatoreSettore opSettore;

	public PaiDTO(CsDPai pai) {
		this.pai = pai;
	}
	public CsDPai getPai() {
		return pai;
	}

	public void setPai(CsDPai pai) {
		this.pai = pai;
	}

	public Long getIdRelazione() {
		return idRelazione;
	}

	public void setIdRelazione(Long idRelazione) {
		this.idRelazione = idRelazione;
	}

	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}

	public Long getResponsabileId() {
		return responsabileId;
	}

	public void setResponsabileId(Long responsabileId) {
		this.responsabileId = responsabileId;
	}

	public CsOOperatoreSettore getOpSettore() {
		return opSettore;
	}

	public void setOpSettore(CsOOperatoreSettore opSettore) {
		this.opSettore = opSettore;
	}

}
