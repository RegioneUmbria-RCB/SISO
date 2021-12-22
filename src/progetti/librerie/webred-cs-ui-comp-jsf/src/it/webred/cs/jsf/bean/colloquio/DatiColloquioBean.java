package it.webred.cs.jsf.bean.colloquio;

import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class DatiColloquioBean extends CsUiCompBaseBean {
	
	private CsDColloquio colloquio;
	private String opAnagrafica;
	private boolean riservato;
	private boolean abilitato4riservato;
	
	private Long diarioDoveSelected;
	private Long diarioConChiSelected;
	private Long diarioTipoSelected;
	
		
	public void Update( ) throws Exception
	{		 
    /*		if( colloquio.getDiarioId() != null ) 
		{ 
			if(this.isRiservato())
			{
				abilitato4riservato = isResponsabileCaso()||isResponsabileUfficio()||isGestoreDiario();
				
				if( !abilitato4riservato )
					campoTesto = "Riservato"; 
			}
			else
				abilitato4riservato = true;
		}
		else
			abilitato4riservato = true;*/
	}
	
	public void Initialize(CsDColloquio csColl, String usernameOp, boolean riservato, boolean abilitato4riservato) throws Exception
	{
		colloquio = csColl;
		opAnagrafica = CsUiCompBaseBean.getCognomeNomeUtente(usernameOp);
		
		this.abilitato4riservato = abilitato4riservato;
		this.riservato = riservato;
		
		diarioTipoSelected = colloquio.getCsCTipoColloquio()==null ? null : colloquio.getCsCTipoColloquio().getId();
		diarioDoveSelected = colloquio.getCsCDiarioDove() == null ? null : colloquio.getCsCDiarioDove().getId();
		diarioConChiSelected = colloquio.getCsCDiarioConchi()==null ? null : colloquio.getCsCDiarioConchi().getId();

		Update();
	}

	public CsDColloquio getColloquio() {
		return colloquio;
	}
	
	public String getOpAnagrafica() {
		return opAnagrafica;
	}

	public void setOpAnagrafica(String opAnagrafica) {
		this.opAnagrafica = opAnagrafica;
	}

	public void setRiservato(boolean riservato) {
		this.riservato = riservato;
	}

	public Long getDiarioDoveSelected() {
		return diarioDoveSelected;
	}
	
	public void setDiarioDoveSelected(Long diarioDoveSelected) {
		this.diarioDoveSelected = diarioDoveSelected;
	}
	
	public Long getDiarioConChiSelected() {
		return diarioConChiSelected;
	}
	
	public void setDiarioConChiSelected(Long diarioConChiSelected) {
		this.diarioConChiSelected = diarioConChiSelected;
	}
	
	public Long getDiarioTipoSelected() {
		return diarioTipoSelected;
	}
	
	public void setDiarioTipoSelected(Long diarioTipoSelected) {
		this.diarioTipoSelected = diarioTipoSelected;
	}

	public boolean isRiservato() {
		return riservato;
	}

	public boolean isAbilitato4riservato() {
		return abilitato4riservato;
	}
	
}
