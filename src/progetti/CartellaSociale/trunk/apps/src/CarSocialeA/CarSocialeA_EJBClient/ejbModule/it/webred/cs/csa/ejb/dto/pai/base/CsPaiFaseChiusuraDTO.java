package it.webred.cs.csa.ejb.dto.pai.base;

import java.io.Serializable;

public class CsPaiFaseChiusuraDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long tipoPai;

	private long fase;

	private long motivoChiusura;

	public long getTipoPai() {
		return tipoPai;
	}

	public void setTipoPai(long tipoPai) {
		this.tipoPai = tipoPai;
	}

	public long getFase() {
		return fase;
	}

	public void setFase(long fase) {
		this.fase = fase;
	}

	public long getMotivoChiusura() {
		return motivoChiusura;
	}

	public void setMotivoChiusura(long motivoChiusura) {
		this.motivoChiusura = motivoChiusura;
	}

	
}
