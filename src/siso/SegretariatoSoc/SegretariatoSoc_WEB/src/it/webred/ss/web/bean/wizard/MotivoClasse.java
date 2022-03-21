package it.webred.ss.web.bean.wizard;

import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsMotivazioniSchede;

import java.util.ArrayList;
import java.util.List;

public class MotivoClasse {
	
	private int idClassificazione;     //Id Classificazione
	
	private List<String> motiviSelected;  
	private List<SsMotivazione> motiviDescrizione;
	
    public MotivoClasse(int id){
    	idClassificazione = id;
    	motiviSelected = new ArrayList<String>();
		motiviDescrizione = new ArrayList<SsMotivazione>();
    }
	
	public boolean initFromModel(List<SsMotivazioniSchede> results){
		motiviSelected = new ArrayList<String>();
		motiviDescrizione = new ArrayList<SsMotivazione>();
    	
    	for(SsMotivazioniSchede motivoScheda: results){
    		if(motivoScheda.getMotivazione().getClassificazione().getId().longValue()==idClassificazione){
    			motiviSelected.add(motivoScheda.getMotivazione().getId()+"");
    			motiviDescrizione.add(motivoScheda.getMotivazione());
    		}
    	}
    	return true;
	}
	
	public List<String> getMotiviSelected() {
		return motiviSelected;
	}


	public void setMotiviSelected(List<String> motiviSelected) {
		this.motiviSelected = motiviSelected;
	}

	public List<SsMotivazione> getMotiviDescrizione() {
		return motiviDescrizione;
	}

	public void setMotiviDescrizione(List<SsMotivazione> motiviDescrizione) {
		this.motiviDescrizione = motiviDescrizione;
	}

	public int getIdClassificazione() {
		return idClassificazione;
	}


	public void setIdClassificazione(int idClassificazione) {
		this.idClassificazione = idClassificazione;
	}
	
	public void refreshDescrizioni(List<SsMotivazione> list){
		motiviDescrizione = new ArrayList<SsMotivazione>();
		if(list!=null){
			for(String sel : motiviSelected){
				 for(SsMotivazione motivo: list){
					 if(sel.equals(Long.toString(motivo.getId())))
						 motiviDescrizione.add(motivo);
				 }	 
			 } 
		}
		
	}

}
