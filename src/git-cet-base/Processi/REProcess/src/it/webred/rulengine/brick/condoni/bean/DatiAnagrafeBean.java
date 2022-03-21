package it.webred.rulengine.brick.condoni.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class DatiAnagrafeBean implements Serializable {
	private long codiceCondono;
	//private long pkIdContribuente; // è il valore di PK_ID_CONTRIBUENTI  SU SIT_T_CONTRIBUENTI
	private String pIva;
	private String codFis;
	
	public long getCodiceCondono() {
		return codiceCondono;
	}
	public void setCodiceCondono(long codiceCondono) {
		this.codiceCondono = codiceCondono;
	}
	private ArrayList<Long> pkIdsContrib;  
	/*public long getPkIdContribuente() {
		return pkIdContribuente;
	}
	public void setPkIdContribuente(long pkIdContribuente) {
		this.pkIdContribuente = pkIdContribuente;
	}*/
	
	public String getPIva() {
		return pIva;
	}
	
	public void setPIva(String iva) {
		pIva = iva;
	}
	public String getCodFis() {
		return codFis;
	}
	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}
	public ArrayList<Long> getPkIdsContrib() {
		return pkIdsContrib;
	}
	public void setPkIdsContrib(ArrayList<Long> pkIdsContrib) {
		this.pkIdsContrib = pkIdsContrib;
	}
}
