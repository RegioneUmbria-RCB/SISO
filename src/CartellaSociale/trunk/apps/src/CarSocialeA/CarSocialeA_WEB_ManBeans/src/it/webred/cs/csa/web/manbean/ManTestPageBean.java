package it.webred.cs.csa.web.manbean;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.dto.utility.KeyValuePairBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.utilities.CommonUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.naming.NamingException;

@ManagedBean
@ViewScoped
public class ManTestPageBean extends CsUiCompBaseBean {

	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;

	@ManagedProperty( value="#{listaCasoIter}")
	private List<KeyValuePairBean<CsACaso, IterInfoStatoMan>> listaCasoIter;  
	
	private String nomeNuovoCaso;
	
	public IterDialogMan getIterDialogMan() {
		return iterDialogMan;
	}

	public void setIterDialogMan(IterDialogMan iterDialogMan) {
		this.iterDialogMan = iterDialogMan;
	}

	public List<KeyValuePairBean<CsACaso, IterInfoStatoMan>> getListaCasoIter() {
		return listaCasoIter;
	}

	public void setListaCasoIter(
			List<KeyValuePairBean<CsACaso, IterInfoStatoMan>> listaCasoIter) {
		this.listaCasoIter = listaCasoIter;
	}

	public String getNomeNuovoCaso() {
		return nomeNuovoCaso;
	}

	public void setNomeNuovoCaso(String nomeNuovoCaso) {
		this.nomeNuovoCaso = nomeNuovoCaso;
	}

	public void onPreRenderView(ComponentSystemEvent event){
			//loadLastIterStepByCaso();
	}
	
	@PostConstruct
	public void onPostConstruct() throws Exception{
		loadLastIterStepByCaso();

		String sIdCaso = getRequest().getParameter("IDCASO");
		if( CommonUtils.isNotEmptyString(sIdCaso) )
			iterDialogMan.openDialog(Long.parseLong(sIdCaso));
	}

	public void closeDialog() throws Exception{
		loadLastIterStepByCaso();
	}
	
	public void newCaso() throws Exception {
		try {
			
			AccessTableCasoSessionBeanRemote casoSessionBean = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
			AccessTableIterStepSessionBeanRemote iterSessionBean = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");

			CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
			
			IterDTO itDto = new IterDTO();
			fillEnte(itDto);
			itDto.setNomeOperatore(itDto.getUserId());
			CsACaso caso = casoSessionBean.newCaso( itDto );
			
			itDto.setIdCaso(caso.getId());
			itDto.setIdSettore(opSettore.getCsOSettore().getId());
			itDto.setAlertUrl("");
			itDto.setNotificaSettoreSegnalante(true);
			iterSessionBean.newIter(itDto);

		} catch (NamingException e) {
			logger.error(e);
		}
	}
	
	protected void loadLastIterStepByCaso() throws Exception  {
		
		try {
			AccessTableCasoSessionBeanRemote casoSessionBean = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
			AccessTableIterStepSessionBeanRemote iterSessionBean = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");

			IterDTO itDto = new IterDTO();
			fillEnte(itDto);
			listaCasoIter = new LinkedList<KeyValuePairBean<CsACaso,IterInfoStatoMan>>();
			List<CsACaso> listaCaso = casoSessionBean.findAll(itDto);

			for( CsACaso csCaso : listaCaso ) {
				
				itDto.setIdCaso(csCaso.getId());
				CsIterStepByCasoDTO itStep = iterSessionBean.getLastIterStepByCasoDTO(itDto);	
				if( itStep != null )
				{
					IterInfoStatoMan casoInfo = new IterInfoStatoMan(); 
					casoInfo.initialize( itStep);
					
					listaCasoIter.add( new KeyValuePairBean<CsACaso,IterInfoStatoMan>(csCaso, casoInfo) );
				}
			}
		} 
		catch (NamingException e) {
			logger.error(e);
		}
	}
	
}
