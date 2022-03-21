package it.webred.cs.jsf.bean.colloquio;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.PresaInCaricoDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.colloquio.ListaDatiColloquioDTO;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import org.apache.commons.lang3.StringUtils;

public class ColloquioRowBean extends CsUiCompBaseBean {

	private static final long serialVersionUID = 1L;
	private ListaDatiColloquioDTO dati;
	
	private boolean abilitato4riservato;
	
	private String opModifica;

	protected boolean isResponsabileCaso(){
		CsOOperatoreSettore currentOpSettore = getCurrentOpSettore();

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		boolean bIsResponsabileCaso = false;
		if( dati.getCasoId() != null ){
			dto.setObj(dati.getCasoId());
			PresaInCaricoDTO pic = iterService.getLastPICByCaso(dto);
			
			if( pic != null ){
				bIsResponsabileCaso &= pic.getResponsabile().getId().equals(currentOpSettore.getCsOOperatore().getId());
				bIsResponsabileCaso &= pic.getSettore().getCodice().equals(currentOpSettore.getCsOSettore().getId());
			}
			else
				bIsResponsabileCaso = false;
		}
		return bIsResponsabileCaso;
	
	}
	
	protected boolean isResponsabileUfficio(){
		String belfiore = getUser().getCurrentEnte();
		CsOOperatoreSettore currentOpSettore = getCurrentOpSettore();
		
		boolean is = currentOpSettore.getAmGroup().contains("CSOCIALE_RESPO_SETTORE_" + belfiore);
		return is;
		
	}
	
	protected boolean isGestoreDiario(){
		CsOOperatoreSettore currentOpSettore = getCurrentOpSettore();
		boolean isInserimento = false;
		boolean isModifica = false;
		
		isInserimento = currentOpSettore.getCsOOperatore().getUsername().equals(dati.getUserIns());
		isModifica = currentOpSettore.getCsOOperatore().getUsername().equals(dati.getUserMod());
		
		return isInserimento||isModifica;
	}

	public void Initialize( ListaDatiColloquioDTO collBasic) {
		dati = collBasic;
		
		String userMod = !StringUtils.isBlank(collBasic.getUserMod()) ?  collBasic.getUserMod() : collBasic.getUserIns();
		if(!StringUtils.isBlank(userMod))		
			opModifica = super.getCognomeNomeUtente(userMod); 
	
		abilitato4riservato = true;
		if( collBasic!=null && collBasic.getDiarioId() != null ) { 
			if(collBasic.isRiservato())
				abilitato4riservato = isResponsabileCaso()||isResponsabileUfficio()||isGestoreDiario();
		}
	}

	public boolean isAbilitato4riservato() {
		return abilitato4riservato;
	}

	public String getOpModifica() {
		return opModifica;
	}
	
	public Long getDiarioId(){
		return dati.getDiarioId();
	}
	
	public boolean isRiservato(){
		return dati.isRiservato();
	}

	public ListaDatiColloquioDTO getDati() {
		return dati;
	}

	public void setDati(ListaDatiColloquioDTO dati) {
		this.dati = dati;
	}

	public void setAbilitato4riservato(boolean abilitato4riservato) {
		this.abilitato4riservato = abilitato4riservato;
	}

	public void setOpModifica(String opModifica) {
		this.opModifica = opModifica;
	}
	
	public String getUsernameOp(){
		return !StringUtils.isBlank(dati.getUserMod()) ? dati.getUserMod() : dati.getUserIns();
	}
	
}
