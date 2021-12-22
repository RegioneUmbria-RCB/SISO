package it.webred.cs.csa.ejb.dto.erogazioni;

import java.io.Serializable;
import java.math.BigDecimal;

public class CompartecipazioneDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean calcolata;
	private BigDecimal utenti;
	private BigDecimal ssn;
	private BigDecimal altre;
	private String     note;
	
	public CompartecipazioneDTO(BigDecimal utenti, BigDecimal ssn, BigDecimal altre, String note, boolean calcolata){
		this.utenti=utenti!=null ? utenti : BigDecimal.ZERO;
		this.ssn=ssn!=null ? ssn : BigDecimal.ZERO;
		this.altre=altre!=null ? altre : BigDecimal.ZERO;
		this.note=note;
		this.calcolata=calcolata;
	}
	
	public BigDecimal getUtenti() {
		return utenti;
	}
	public BigDecimal getSsn() {
		return ssn;
	}
	public BigDecimal getAltre() {
		return altre;
	}
	public String getNote() {
		return note;
	}
	public void setUtenti(BigDecimal utenti) {
		this.utenti = utenti;
	}
	public void setSsn(BigDecimal ssn) {
		this.ssn = ssn;
	}
	public void setAltre(BigDecimal altre) {
		this.altre = altre;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getDescrizione(){
		String txt="";
		String euroSymbol = new String(" \u20ac ");
		
		if(utenti != null && utenti.compareTo(BigDecimal.ZERO)>0)
			txt += "<strong>Utenti</strong>= "+utenti+euroSymbol+"  ";
		
		if(ssn != null && ssn.compareTo(BigDecimal.ZERO)>0)
			txt += "<strong>S.S.N.</strong> = "+ssn+euroSymbol+"  ";
		
		if(altre != null && altre.compareTo(BigDecimal.ZERO)>0)
			txt+= "<strong>Altre</strong>= "+ altre +euroSymbol+"  ";
		
	    txt+= note!=null ? "<br/><strong>Note </strong>: "+note : "<br/>";	
		return txt;
	}
}
