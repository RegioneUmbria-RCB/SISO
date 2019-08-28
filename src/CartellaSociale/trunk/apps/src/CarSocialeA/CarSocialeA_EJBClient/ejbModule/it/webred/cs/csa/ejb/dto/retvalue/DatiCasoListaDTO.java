package it.webred.cs.csa.ejb.dto.retvalue;

import it.webred.cs.data.model.CsASoggettoLAZY;

import java.io.Serializable;
import java.util.Date;


public class DatiCasoListaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private CsASoggettoLAZY soggetto;
	private Date dataApertura;
	
	public CsASoggettoLAZY getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
	}
	public Date getDataApertura() {
		return dataApertura;
	}
	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}
	
}
