package it.webred.ct.proc.ario.bean;

import java.io.Serializable;
import java.util.Hashtable;

public class HashParametriConfBean implements Serializable{

	private Hashtable<String, Object> tabelleDroppate = new Hashtable<String, Object>();
	private Hashtable<String, Object> criterioLascoSogg = new Hashtable<String, Object>();
	private Hashtable<String, Object> codiceOrigSogg = new Hashtable<String, Object>();
	private Hashtable<String, Object> codiceOrigVie = new Hashtable<String, Object>();
	private Hashtable<String, Object> codiceOrigCiv = new Hashtable<String, Object>();
	private Hashtable<String, Object> sezioneInAggrOgg = new Hashtable<String, Object>();
	
	public Hashtable<String, Object> getTabelleDroppate() {
		return tabelleDroppate;
	}
	public void setTabelleDroppate(Hashtable<String, Object> tabelleDroppate) {
		this.tabelleDroppate = tabelleDroppate;
	}
	public Hashtable<String, Object> getCriterioLascoSogg() {
		return criterioLascoSogg;
	}
	public void setCriterioLascoSogg(Hashtable<String, Object> criterioLascoSogg) {
		this.criterioLascoSogg = criterioLascoSogg;
	}
	public Hashtable<String, Object> getCodiceOrigSogg() {
		return codiceOrigSogg;
	}
	public void setCodiceOrigSogg(Hashtable<String, Object> codiceOrigSogg) {
		this.codiceOrigSogg = codiceOrigSogg;
	}
	public Hashtable<String, Object> getCodiceOrigVie() {
		return codiceOrigVie;
	}
	public void setCodiceOrigVie(Hashtable<String, Object> codiceOrigVie) {
		this.codiceOrigVie = codiceOrigVie;
	}
	public Hashtable<String, Object> getCodiceOrigCiv() {
		return codiceOrigCiv;
	}
	public void setCodiceOrigCiv(Hashtable<String, Object> codiceOrigCiv) {
		this.codiceOrigCiv = codiceOrigCiv;
	}
	public Hashtable<String, Object> getSezioneInAggrOgg() {
		return sezioneInAggrOgg;
	}
	public void setSezioneInAggrOgg(Hashtable<String, Object> sezioneInAggrOgg) {
		this.sezioneInAggrOgg = sezioneInAggrOgg;
	}
	
	
	
	
}
