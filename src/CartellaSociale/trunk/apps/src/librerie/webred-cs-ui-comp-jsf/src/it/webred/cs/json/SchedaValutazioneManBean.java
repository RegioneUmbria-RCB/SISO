package it.webred.cs.json;

import it.webred.classfactory.VersionClassComparator;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.model.SelectItem;

public abstract class SchedaValutazioneManBean extends CsUiCompBaseBean implements Serializable, ISchedaValutazione {

	private static final long serialVersionUID = 1L;
	protected AccessTableDiarioSessionBeanRemote diarioService = 
			(AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");

	protected AccessTableCasoSessionBeanRemote casoService = 
			(AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
	
	protected AccessTableConfigurazioneSessionBeanRemote confService = 
			(AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	private Long   idCaso;
	private Date   dtModifica;
	private String opModifica;
	
	
	@Override
	public String getVersionLowerCase() {
		String vers = VersionClassComparator.getVersionLowerCase( this.getClass() );
		logger.debug(this.getClass()+" versione:" + vers);
		return vers;
	}
	
	protected CsOOperatoreBASIC getOperResponsabileCaso(Long idCaso) throws Exception {
		CsOOperatoreBASIC operatoreResponsabile = null;
		
	/*	IterDTO itDto = new IterDTO();
		fillEnte(itDto);
		itDto.setIdCaso(idCaso);
		CsACaso csACaso = casoService.findCasoById(itDto);*/
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idCaso);
		operatoreResponsabile = casoService.findResponsabileBASIC(dto);
		
		return operatoreResponsabile;
	}
	
	public Long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
		setIdCasoController(idCaso);
	}
	

	public abstract void setIdCasoController(Long idCaso);
	
	protected void abilitaSelectItem(SelectItem si, boolean disabilita, Object valoreCorrente){
		
		if(disabilita){ //TODO:Verifica valore
			if(valoreCorrente!=null && valoreCorrente.toString().equals(si.getValue().toString()))
				disabilita=false;
		}
	
		si.setDisabled(disabilita);
	}
	
	public void valUltimModifica(CsDDiario d){
		dtModifica = null;
		opModifica = null;
		if(d!=null){
			dtModifica = this.getDataUltimaModifica(d);
			opModifica = this.getOpUltimaModifica(d);
		}
	}
	
	@Override
	public Date getDtModifica() {
		return dtModifica;
	}

	@Override
	public String getOpModifica() {
		return opModifica;
	}

}
