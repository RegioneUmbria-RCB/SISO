package it.webred.rulengine.brick.idoneitaAlloggi.bean;

import java.io.Serializable;

public class Titolare implements Serializable {
	
	private static final long serialVersionUID = -3420468966879513889L;
	
	private String codFisc;
	private String denom;
	private String tipoTitolo;
	
	public Titolare() {
		// TODO Auto-generated constructor stub
	}//-------------------------------------------------------------------------

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getDenom() {
		return denom;
	}

	public void setDenom(String denom) {
		this.denom = denom;
	}

	public String getTipoTitolo() {
		return tipoTitolo;
	}

	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	

}

