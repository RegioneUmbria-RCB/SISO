package it.webred.cs.csa.web.manbean.redditoCittadinanza;

import java.io.Serializable;

import it.webred.cs.data.model.view.CsRdcAnagraficaGepi;
import it.webred.cs.jsf.interfaces.IIterInfoStato;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class DatiRdC implements Serializable{
	
	private Long id;

	private CsRdcAnagraficaGepi anagrafica;
	private IIterInfoStato lastIterStepInfo;
	private boolean showStatoCartella = true;
	
	public DatiRdC(CsRdcAnagraficaGepi s) {
		this.anagrafica = s;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object scheda){
		return this.id.equals(((DatiRdC)scheda).id);
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

	public CsRdcAnagraficaGepi getAnagrafica() {
		return anagrafica;
	}


	public void setAnagrafica(CsRdcAnagraficaGepi anagrafica) {
		this.anagrafica = anagrafica;
	}


	public boolean isCanOpenCartella(){
		boolean canOpen = true;
		if(isFlgCartellaEsistente()){
			if(this.lastIterStepInfo!=null && lastIterStepInfo.getEnteSegnalato()!=null)
				canOpen = CsUiCompBaseBean.getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getNome().equals(lastIterStepInfo.getEnteSegnalato());
			else if(lastIterStepInfo!=null && lastIterStepInfo.getEnteSegnalante()!=null)
				canOpen = CsUiCompBaseBean.getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getNome().equals(lastIterStepInfo.getEnteSegnalante());
		}
		return canOpen;
	}

	public String getTooltipEntraCartellaButton(){
		String tooltip = "Entra nella cartella";
		if(isFlgCartellaEsistente()){
			tooltip = "Entra nella cartella preesistente";
			if(!isCanOpenCartella()) tooltip = "Non è possibile accedere ad una cartella attualmente gestita da altro ente";
		}
		return tooltip;
	}
	
	public boolean isFlgCartellaEsistente(){
		return anagrafica.getCsASoggetto()!=null;
	}
	
}
