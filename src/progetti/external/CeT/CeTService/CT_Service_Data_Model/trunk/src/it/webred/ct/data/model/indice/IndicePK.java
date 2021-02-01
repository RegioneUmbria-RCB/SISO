package it.webred.ct.data.model.indice;

import java.io.Serializable;

public class IndicePK implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ctrHash;
	private long fkEnteSorgente;
	private String idDwh;
	private long progEs;

	public String getCtrHash() {
		return ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public long getFkEnteSorgente() {
		return fkEnteSorgente;
	}

	public void setFkEnteSorgente(long fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}

	public String getIdDwh() {
		return idDwh;
	}

	public void setIdDwh(String idDwh) {
		this.idDwh = idDwh;
	}

	public long getProgEs() {
		return progEs;
	}

	public void setProgEs(long progEs) {
		this.progEs = progEs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if(obj instanceof IndicePK){
			
		IndicePK pk = (IndicePK) obj;
		return ctrHash.equals(pk.getCtrHash())
				&& fkEnteSorgente == pk.getFkEnteSorgente()
				&& idDwh.equals(pk.getIdDwh()) && progEs == pk.getProgEs();
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ctrHash.hashCode();
		hash = hash * prime + ((int) (this.fkEnteSorgente ^ (this.fkEnteSorgente >>> 32)));
		hash = hash * prime + this.idDwh.hashCode();
		hash = hash * prime + ((int) (this.progEs ^ (this.progEs >>> 32)));
		
		return hash;
    }

}
