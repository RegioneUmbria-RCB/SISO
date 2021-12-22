package it.webred.cs.csa.web.manbean.amministrazione;

import java.util.List;

import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsOOperatore;

public class IterSoggettoLAZY {

	private CsASoggettoLAZY soggettoLAZY;
	private String inputCF;
	private CsItStep  itStep;
	private List<CsASoggettoCategoriaSoc> listaCsSoggettoCatSocLazy;
	 
	public CsASoggettoLAZY getSoggettoLAZY() {
		return soggettoLAZY;
	}
	public void setSoggettoLAZY(CsASoggettoLAZY soggettoLAZY) {
		this.soggettoLAZY = soggettoLAZY;
	}
	public List<CsASoggettoCategoriaSoc> getListaCsSoggettoCatSocLazy() {
		return listaCsSoggettoCatSocLazy;
	}
	public void setListaCsSoggettoCatSocLazy(
			List<CsASoggettoCategoriaSoc> listaCsSoggettoCatSocLazy) {
		this.listaCsSoggettoCatSocLazy = listaCsSoggettoCatSocLazy;
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
		while(i<this.listaCsSoggettoCatSocLazy.size() && !trovato){
			CsASoggettoCategoriaSoc cs = this.listaCsSoggettoCatSocLazy.get(i);
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
		 if(this.itStep.getCsOOrganizzazione2() != null ){
			 return "Segnalato a :" + this.itStep.getCsOOrganizzazione2().getNome();
		 }
		 else{
			 return this.itStep.getCsOOrganizzazione1().getNome();
		 }
		 
	}
	
	public String getOperatore(){
		 if(this.itStep.getCsOOperatore2() != null ){
			 	 return "Segnalato a :" + getDenominazione(this.itStep.getCsOOperatore2());
		 }
		 else{
			 return   getDenominazione(this.itStep.getCsOOperatore1());
		 }
		 
	}
	
	private String getDenominazione(CsOOperatore operatore){
		String denominazione = "";
		if(operatore != null){
			if(operatore.getCsOOperatoreAnagrafica() != null){
				if(operatore.getCsOOperatoreAnagrafica().getNome() != null)
					denominazione += operatore.getCsOOperatoreAnagrafica().getNome();
				if(operatore.getCsOOperatoreAnagrafica().getCognome() != null)
					denominazione += (" ").concat(operatore.getCsOOperatoreAnagrafica().getCognome());
			}
		}
		return denominazione;
	}
	
	public String getSettore(){
		 if(this.itStep.getCsOSettore2() != null ){
			 return "Segnalato a :" + this.itStep.getCsOSettore2().getNome();
		 }
		 else{
			 //return getCategoriaPrevalente();
			 return this.itStep.getCsOSettore1().getNome();
		 }
		 
	}
//	public String getCategorie(){
//		 if(this.itStep.get != null ){
//			 return "Segnalato a :" + this.itStep.getCsOSettore2().getNome();
//		 }
//		 else{
//			 return getCategoriaPrevalente();
//			  
//		 }
//		 
//	}
	public String getCategorieSecondarie(){
		String catSoc = "";
		for(CsASoggettoCategoriaSoc soggCat: this.listaCsSoggettoCatSocLazy) {
			if(soggCat.getPrevalente().intValue()!=1)
			    catSoc += ", " + soggCat.getCsCCategoriaSociale().getTooltip();
		}
		if(catSoc.length() > 1)
		   return catSoc.substring(2);
		else
		   return null;
	}
	 
}
