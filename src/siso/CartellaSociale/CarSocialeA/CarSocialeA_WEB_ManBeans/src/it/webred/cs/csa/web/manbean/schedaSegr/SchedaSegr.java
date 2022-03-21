package it.webred.cs.csa.web.manbean.schedaSegr;

import it.webred.cs.csa.ejb.dto.udc.DatiSchedaAccessoDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.interfaces.IIterInfoStato;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class SchedaSegr extends DatiSchedaAccessoDTO{
	
	private boolean showStatoCartella = true;
	private IIterInfoStato lastIterStepInfo;
	
	@Override
	public boolean equals(Object scheda){
		return this.id.equals(((SchedaSegr)scheda).id);
	}

	public IIterInfoStato getLastIterStepInfo() {
		return lastIterStepInfo;
	}

	public void setLastIterStepInfo(IIterInfoStato lastIterStepInfo) {
		this.lastIterStepInfo = lastIterStepInfo;
	}
	
	public boolean isShowStatoCartella() {
		return showStatoCartella;
	}

	public void setShowStatoCartella(boolean showStatoCartella) {
		this.showStatoCartella = showStatoCartella;
	}

	public boolean isCanOpenCartella(){
		boolean canOpen = true;
		if(esistente){
			if(this.lastIterStepInfo!=null && lastIterStepInfo.getEnteSegnalato()!=null)
				canOpen = CsUiCompBaseBean.getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getNome().equals(lastIterStepInfo.getEnteSegnalato());
			else if(lastIterStepInfo!=null && lastIterStepInfo.getEnteSegnalante()!=null)
				canOpen = CsUiCompBaseBean.getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getNome().equals(lastIterStepInfo.getEnteSegnalante());
		}
		return canOpen;
	}

	public String getTooltipEntraCartellaButton(){
		String tooltip = "Entra nella cartella";
		if(esistente){
			tooltip = "Entra nella cartella preesistente";
			if(!isCanOpenCartella()) tooltip = "Non è possibile accedere ad una cartella attualmente gestita da altro ente";
		}
		return tooltip;
	}

	public boolean isProvenienzaUDC(){
		return DataModelCostanti.SchedaSegr.PROVENIENZA_SS.equalsIgnoreCase(this.provenienza);
	}
		
	public boolean isRenderNuovaCartella(){
		return !this.isSoggettoAssociato() && !this.isEsistente() && !this.isRespinta();
	}
	
	public boolean isRenderVista(){
		boolean render = this.isPropostaPIC() && this.isEsistente() && !this.isVista() && !this.isRespinta();
		return render;
	}
	
	public boolean isRenderRespinta(){
		boolean render = this.isPropostaPIC() && (!this.isSoggettoAssociato() || this.isEsistente()) && !this.isRespinta() && !this.isVista(); 
		return render;
	}
	
}
