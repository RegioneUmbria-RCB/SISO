package it.webred.cs.jsf.bean;

import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

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



	public AlertBean(CsAlertBASIC csa, long idSettoreOperatore, long idOp, boolean hasPermessoSettore, boolean hasPermessoEnte, ArrayList<CsOOperatoreSettore> tempLista) {
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
		
		tooltip = csa.getDescrizione();
		
		if(csa.getCsOSettore2() != null){
		  for(CsOOperatoreSettore tst : tempLista ){
			 if  (tst.getCsOSettore().getId().equals(csa.getCsOSettore2().getId())){
				   descriSettOrg = "  "+ csa.getCsOSettore2().getNome();
				   descriSettOrg+= csa.getCsOOrganizzazione2()!=null ? " di " + csa.getCsOOrganizzazione2().getNome() : "";
			 }
		  }
		}
		
		if( csa.getCsOpSettore2() != null ){
			//l'alert deve essere letto solo dall'operatore a cui è stato segnalato, in caso di operatore diverso lo disabilito
			disabled = csa.getCsOpSettore2().getCsOOperatore().getId() != idOp;
			tooltip = "Segnalato a Operatore: " + getDenominazioneOperatore(csa.getCsOpSettore2().getCsOOperatore());
		}
		else if( csa.getCsOSettore2() != null )
		{
			//se qui l'alert è stato segnalato ad un settore
			//deve essere letto solo dall'operatore che ha permesso di VISUALIZZA_NOTIFICA_SETTORE
			disabled = ! ( csa.getCsOSettore2().getId() == idSettoreOperatore && hasPermessoSettore );
			tooltip = "Segnalato a ufficio: " + csa.getCsOSettore2().getNome();
		}
		else
		{
			//se qui l'alert è stato segnalato ad un ente, lo visualizzo se ho il permesso attivo
			disabled = !hasPermessoEnte;
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
