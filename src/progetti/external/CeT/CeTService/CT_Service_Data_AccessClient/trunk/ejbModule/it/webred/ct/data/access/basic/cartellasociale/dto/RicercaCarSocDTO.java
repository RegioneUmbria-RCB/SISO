package it.webred.ct.data.access.basic.cartellasociale.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RicercaCarSocDTO extends CeTBaseObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codFisc;

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	
	
}
