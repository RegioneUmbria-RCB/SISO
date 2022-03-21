package it.webred.cs.json;

import it.webred.classfactory.VersionClassComparator;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.model.SelectItem;

public abstract class SchedaValutazioneManBean extends CsUiCompBaseBean implements Serializable, ISchedaValutazione {

	private static final long serialVersionUID = 1L;
	protected AccessTableDiarioSessionBeanRemote diarioService = 
			(AccessTableDiarioSessionBeanRemote) getCarSocialeEjb("AccessTableDiarioSessionBean");

	protected AccessTableCasoSessionBeanRemote casoService = 
			(AccessTableCasoSessionBeanRemote) getCarSocialeEjb("AccessTableCasoSessionBean");
	
	protected AccessTableAlertSessionBeanRemote alertService = 
			(AccessTableAlertSessionBeanRemote) getCarSocialeEjb("AccessTableAlertSessionBean");
	
	private Long   idCaso;
	private Date   dtModifica;
	private String opModifica;
	private Date currentDate = new Date();
	
	
	@Override
	public String getVersionLowerCase() {
		String vers = VersionClassComparator.getVersionLowerCase( this.getClass() );
		//logger.debug(this.getClass()+" versione:" + vers);
		return vers;
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
			dtModifica = getDataUltimaModifica(d);
			opModifica = getOpUltimaModifica(d);
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

	@Override
	public Date getCurrentDate() {
		return currentDate;
	}

}
