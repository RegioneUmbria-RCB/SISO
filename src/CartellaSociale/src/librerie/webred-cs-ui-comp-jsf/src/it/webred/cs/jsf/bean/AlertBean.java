package it.webred.cs.jsf.bean;

import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;

import java.io.Serializable;
import java.util.ArrayList;




public class AlertBean extends IterBaseBean implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	
	protected Long id;
	protected String  tipo;
	protected String labelTipo;
	protected boolean visibile;
	protected boolean letto;
	protected String descrizione;
	protected String titoloDescrizione;
	protected String url;
	protected String tooltip;
	protected boolean disabled;
	protected String descriSettOrg;



	public AlertBean(CsAlertBASIC csa, long idSettoreOperatore, long idOp, boolean hasPermessoSettore, ArrayList<CsOOperatoreSettore> tempLista) {
		this.id = csa.getId();
		this.tipo = csa.getTipo();
		this.labelTipo = csa.getLabelTipo();
		this.visibile = csa.getVisibile();
		this.letto = csa.getLetto();
		this.descrizione = csa.getDescrizione();
		this.titoloDescrizione = csa.getTitoloDescrizione();
		this.url = csa.getUrl();
		this.disabled = false;
		this.tooltip = "";
		this.descriSettOrg="";		
		
		if(csa.getCsOSettore2() != null && csa.getCsOOrganizzazione2() != null){
		  for(CsOOperatoreSettore tst : tempLista ){
			 if  (tst.getCsOSettore().getId() ==  csa.getCsOSettore2().getId()){
				    descriSettOrg = " Ufficio: " + csa.getCsOSettore2().getNome()  + " - " + csa.getCsOOrganizzazione2().getNome();
			 }
		 }
		}
		
		if( csa.getCsOOperatore2() != null ){
			//l'alert deve essere letto solo dall'operatore a cui è stato segnalato, in caso di operatore diverso lo disabilito
			disabled = csa.getCsOOperatore2().getId() != idOp;
			/*TODO nome cognome*/tooltip = "Operatore: " + csa.getCsOOperatore2().getUsername();
		}
		else if( csa.getCsOSettore2() != null )
		{
			//se qui l'alert è stato segnalato ad un settore
			//deve essere letto solo dall'operatore che ha permesso di VISUALIZZA_NOTIFICA_SETTORE
			disabled = ! ( csa.getCsOSettore2().getId() == idSettoreOperatore && hasPermessoSettore );
			tooltip = "Ufficio: " + csa.getCsOSettore2().getNome();
		}
		else
		{
			//se qui l'alert è stato segnalato ad un ente
			//l'alert è sempre abilitato perche vedo al massimo solo gli alert dell'ente a cui appartengo
			disabled = false;
		}
	}


	public String getTipo() {
		return tipo;
	}

	public String getLabelTipo() {
		return labelTipo;
	}

	public boolean isVisibile() {
		return visibile;
	}
	
	public boolean isLetto() {
		return letto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getUrl() {
		return url;
	}

	public Long getId() {
		return id;
	}

	public String getTitoloDescrizione() {
		return titoloDescrizione;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public String getTooltip() {
		return tooltip;
	}
	public String getDescriSettOrg() {
		return descriSettOrg;
	}

	public void setDescriSettOrg(String descriSettOrg) {
		this.descriSettOrg = descriSettOrg;
	}
}
