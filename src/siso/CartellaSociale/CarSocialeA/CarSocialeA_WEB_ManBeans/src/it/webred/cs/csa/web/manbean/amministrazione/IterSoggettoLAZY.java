package it.webred.cs.csa.web.manbean.amministrazione;

import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsItStep;

import java.text.SimpleDateFormat;
import java.util.List;

public class IterSoggettoLAZY {
	
	private final String NON_TROVATO = "NON TROVATO";
	private CsASoggettoLAZY soggetto;
	private String inputCF;
	private CsItStep itStep;
	private List<CsASoggettoCategoriaSoc> listaCatSociali;
	
	public IterSoggettoLAZY(CsASoggettoLAZY soggetto){
		this.soggetto = soggetto;
	}
	
	public List<CsASoggettoCategoriaSoc> getListaCatSociali() {
		return listaCatSociali;
	}

	public void setListaCatSociali(List<CsASoggettoCategoriaSoc> listaCatSociali) {
		this.listaCatSociali = listaCatSociali;
	}
	
	public String getInputCF() {
		return inputCF;
	}
	public void setInputCF(String inputCF) {
		this.inputCF = inputCF;
	}
	public CsItStep getItStep() {
		return itStep;
	}
	public void setItStep(CsItStep itStep) {
		this.itStep = itStep;
	}
	 
	public String getCategoriaPrevalente(){
		boolean trovato = false;
		int i=0;
		CsASoggettoCategoriaSoc prevalente=null;
		while(i<this.listaCatSociali.size() && !trovato){
			CsASoggettoCategoriaSoc cs = this.listaCatSociali.get(i);
			if(cs.getPrevalente().intValue()==1){
				trovato=true;
				prevalente = cs;
			}
			i++;
		}
		
		if(prevalente!=null)
			return prevalente.getCsCCategoriaSociale().getTooltip();
		else 
			return null;
		
	}
	
	public String getOrganizzazione(){
		 if(this.itStep.getCsOOrganizzazione1() != null ){
			 return "Segnalato a: " + this.itStep.getCsOOrganizzazione1().getNome();
		 }
		 else{
			 return this.itStep.getCsOOrganizzazione2().getNome();
		 }
		 
	}
	
	public String getOperatore(){
		String operatore = ""; 
		if(this.itStep.getCsOOperatore2() != null )
			 operatore =  "Segnalato a: " + this.itStep.getCsOOperatore2().getDenominazione();
		 else if(this.itStep.getCsOOperatore1()!=null)
			 operatore = this.itStep.getCsOOperatore1().getDenominazione();
		return operatore;
	}
	
	public String getSettore(){
		 if(this.itStep.getCsOSettore2() != null )
			 return "Segnalato a :" + this.itStep.getCsOSettore2().getNome();
		 else
			 return this.itStep.getCsOSettore1().getNome();
	}
	
	public String getCategorieSecondarie(){
		String catSoc = "";
		for(CsASoggettoCategoriaSoc soggCat: this.listaCatSociali) {
			if(soggCat.getPrevalente().intValue()!=1)
			    catSoc += ", " + soggCat.getCsCCategoriaSociale().getTooltip();
		}
		if(catSoc.length() > 1)
		   return catSoc.substring(2);
		else
		   return null;
	}
	
	public String getStyle(){
		return this.soggetto==null ?  "background-color: red !important;" :"";
	}
	
	public String getDenominazione(){
		return this.soggetto!=null ? this.soggetto.getCsAAnagrafica().getDenominazione() : NON_TROVATO;
	}
	
	public Long getCasoId(){
		return this.getCaso().getId();
	}
	
	public String getCf(){
		return this.soggetto!=null ? this.soggetto.getCsAAnagrafica().getCf() : NON_TROVATO;
	} 
	
	public String getDataNascita(){
		SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
		return this.soggetto!=null ? ddMMyyyy.format(this.soggetto.getCsAAnagrafica().getDataNascita()) : NON_TROVATO;
	} 
	
	public String getNomeStatoIter(){
		return this.itStep!=null ? this.itStep.getCsCfgItStato().getNome() : NON_TROVATO;
	}
	
	public CsACaso getCaso(){
		return this.soggetto!=null ? this.soggetto.getCsACaso() : null;
	}
	
}
