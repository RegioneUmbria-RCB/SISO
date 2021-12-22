package it.webred.cs.csa.ejb.dto.relazione;

import it.webred.cs.data.model.CsDRelSal;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsDTriage;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class SaveRelazioneDTO extends CeTBaseObject {
	
	private CsDRelazione relazione;
	private Long casoId;
	private CsOOperatoreSettore opSettore;
	private CsDTriage triage;
	private CsDRelSal sal;
	
	public CsDRelazione getRelazione() {
		return relazione;
	}
	public void setRelazione(CsDRelazione relazione) {
		this.relazione = relazione;
	}
	public Long getCasoId() {
		return casoId;
	}
	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	public CsDTriage getTriage() {
		return triage;
	}
	public void setTriage(CsDTriage triage) {
		this.triage = triage;
	}
	public CsDRelSal getSal() {
		return sal;
	}
	public void setSal(CsDRelSal sal) {
		this.sal = sal;
	}
	public CsOOperatoreSettore getOpSettore() {
		return opSettore;
	}
	public void setOpSettore(CsOOperatoreSettore opSettore) {
		this.opSettore = opSettore;
	}
}
