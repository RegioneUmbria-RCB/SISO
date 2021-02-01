package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.interfaces.IUserSearch;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ejb.utility.ClientUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.NamingException;

@ManagedBean
@ViewScoped
public class UserSearchBean extends CsUiCompBaseBean implements IUserSearch{

	protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	
	protected String widgetVar = "userSearchVar";
	protected DatiUserSearchBean selSoggetto;
	protected Integer maxResult = 15;
	
	@Override
	public List<DatiUserSearchBean> getLstSoggetti(String query) {
			
		List<DatiUserSearchBean> listAutocomplete = new ArrayList<DatiUserSearchBean>();
	
		//rsDto.setMaxResult(maxResult);
		if(query!=null && query.length()>=3){
		
				 if(listAutocomplete.size() < maxResult)
					this.ricercaInAnagrafeInterna(query, listAutocomplete.size(), listAutocomplete);
	
			/*	if (listAutocomplete.size() < maxResult)
					this.ricercaInAnagrafeSanitariaUmbria(query, listAutocomplete);
			*/
		}else
			this.addWarningFromProperties("search.utente.size");
			
		return listAutocomplete;
	}
	
	
	private void ricercaInAnagrafeInterna(String query, int listSize, List<DatiUserSearchBean> listAutocomplete){
		
		if(isAnagrafeComunaleInternaAbilitata()){
			try {
				AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
				RicercaSoggettoAnagrafeDTO rsDto = new RicercaSoggettoAnagrafeDTO();
				fillEnte(rsDto);
				rsDto.setDenom(query);
				List<SitDPersona> list = anagrafeService.getListaPersoneByDenominazione(rsDto);
				int contatore = 0;
				for(SitDPersona s: list){
					boolean emigrato = s.getDataEmi()!=null && (s.getDataImm()==null || s.getDataImm().before(s.getDataEmi()));
					if(!emigrato){
						DatiUserSearchBean sDto = new DatiUserSearchBean();
						sDto.setSoggetto(s);	
						String itemLabel = s.getCognome().toUpperCase() + " " + s.getNome().toUpperCase();
						if(s.getDataNascita() != null)
							itemLabel += " nato il: " + sdf.format(s.getDataNascita());
						
						if(s.getDataMor()!=null)
							itemLabel += " morto il: " + sdf.format(s.getDataMor());
						
						
						sDto.setItemLabel(itemLabel);
						sDto.setId(s.getId());
						listAutocomplete.add(sDto);
						contatore++;
					}
					if(contatore>=maxResult) break;
				}
				
			} catch (NamingException e) {
				addError("general", "caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}else logger.info("Ricerca in anagrafe interna non abilitata: impostare il parametro 'smartwelfare.ricercaSoggetto.tipo'");
	}
	
	@Override
	public void handleChangeUser(AjaxBehaviorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	@Override
	public DatiUserSearchBean getSelSoggetto(){
		return this.selSoggetto;
	}
	
	public void setSelSoggetto(DatiUserSearchBean selSoggetto) {
		this.selSoggetto = selSoggetto;
	}

	@Override
	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}
	
	public boolean isCorrectType(Object userVar)
	{
		boolean result= true;
		try {
			DatiUserSearchBean	provaConversione=(DatiUserSearchBean)userVar;
		} catch (Exception e) {
			result=false;
		}
		
		return result;
	}
	
	
}
