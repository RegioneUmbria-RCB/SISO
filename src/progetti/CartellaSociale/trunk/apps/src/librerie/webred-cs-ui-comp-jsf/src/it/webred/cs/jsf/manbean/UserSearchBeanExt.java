package it.webred.cs.jsf.manbean;

import it.webred.siso.ws.ricerca.dto.*;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.bean.UserSearchExtInput;
import it.webred.cs.jsf.interfaces.IUserSearchExt;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ejb.utility.ClientUtility;




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class UserSearchBeanExt extends UserSearchBean implements IUserSearchExt{

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	private UserSearchExtInput params=new UserSearchExtInput();
	private List<DatiUserSearchBean> lstSoggetti;
	protected Integer maxResult = 60;
    public boolean searchButtonPressed;
	
	@Override
	public void clearParameters(){
		this.searchButtonPressed=false; 
		this.params.clearCampi();
		lstSoggetti = new ArrayList<DatiUserSearchBean>();
		selSoggetto=null;
		RequestContext.getCurrentInstance().update("inputSearch");
		RequestContext.getCurrentInstance().update("outputSearch");
	}
	
	@Override
	public void loadListaSoggetti() {
		this.searchButtonPressed=false; 	
		lstSoggetti = new ArrayList<DatiUserSearchBean>();
		
		List<String> msg = new ArrayList<String>();
		if(params.isRicercaCf())
			msg.addAll(params.validaCodiceFiscale());
		else if(params.isRicercaDatiAnagrafici()){
			String validaTipo = null;
			
			if(isAnagrafeSigessAbilitata()) validaTipo = DataModelCostanti.TipoRicercaSoggetto.SIGESS;
			else if (isAnagrafeSanitariaUmbriaAbilitata()) validaTipo = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA;
			else if (isAnagrafeSanitariaMarcheAbilitata()) validaTipo = DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE;

			msg.addAll(params.validaAnnoNascita(validaTipo));
			msg.addAll(params.validaCognome());
			if(isAnagrafeSigessAbilitata())
				msg.addAll(params.validaSesso());
		}
	
		if(!msg.isEmpty()){
			this.addWarning("Impossibile avviare la ricerca: impostare i parametri di ricerca obbligatori", msg);
			return;
		}
		
			TreeMap<String, DatiUserSearchBean> mappaSoggetti = new TreeMap<String, DatiUserSearchBean>();
		
		 	if(lstSoggetti.size() < maxResult)
				this.ricercaInAnagrafeInterna(mappaSoggetti);
		
			if (lstSoggetti.size() < maxResult)
				this.ricercaInAnagrafeEsterna(mappaSoggetti, DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA);
			
			if(lstSoggetti.size() < maxResult)
				this.ricercaInAnagrafeEsterna(mappaSoggetti, DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE);
			
			if(lstSoggetti.size() <maxResult)
				this.ricercaInAnagrafeEsterna(mappaSoggetti, DataModelCostanti.TipoRicercaSoggetto.SIGESS);
			
			Iterator<String> iter = mappaSoggetti.keySet().iterator();
			while(iter.hasNext()){
				String key = iter.next();
				DatiUserSearchBean s = mappaSoggetti.get(key);
				lstSoggetti.add(s);
			}
			
			this.searchButtonPressed=true;
	}
	
	private void ricercaInAnagrafeInterna(TreeMap<String,DatiUserSearchBean> listAutocomplete){
			
		if(isAnagrafeComunaleInternaAbilitata()){
			try {	
				
				AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
				RicercaSoggettoAnagrafeDTO rsDto = new RicercaSoggettoAnagrafeDTO();
				fillEnte(rsDto);
				rsDto.setCognome(params.getCognome());
				rsDto.setNome(params.getNome());
				rsDto.setCodFis(params.getCodiceFiscale());
				rsDto.setSesso(params.getDatiSesso().getSesso());
				rsDto.setDtRif(new Date());
				rsDto.setAnnoNascitaDa(params.getAnnoNascitaDa());
				rsDto.setAnnoNascitaA(params.getAnnoNascitaA());
				
				rsDto.setMaxResult(this.maxResult);
				
				List<SitDPersona> list = anagrafeService.searchSISOAnagrafiche(rsDto);
				
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
						String key = !StringUtils.isBlank(s.getCodfisc()) ? s.getCodfisc().toUpperCase() : Integer.toString(itemLabel.hashCode());
						if(!listAutocomplete.containsKey(key))
							listAutocomplete.put(key, sDto);
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
			
	protected void ricercaInAnagrafeEsterna(TreeMap<String, DatiUserSearchBean> listAutocomplete, String tipoRicerca){			
		int maxResultAnagReg=maxResult-listAutocomplete.size();
		
		if(CsUiCompBaseBean.isAnagrafeAbilitata(tipoRicerca)){
			try {
			
				List <PersonaDettaglio> listAnagReg= new ArrayList <PersonaDettaglio>();
					
				RicercaAnagraficaParams rab= new RicercaAnagraficaParams(tipoRicerca, false);
				fillEnte(rab);
				rab.setCognome(params.getCognome());
				rab.setNome(params.getNome());
				rab.setSesso(params.getDatiSesso().getSesso());
				rab.setCf(params.getCodiceFiscale());
				rab.setAnnoNascitaDa(params.getAnnoNascitaDa());
				rab.setAnnoNascitaA(params.getAnnoNascitaA());
				
				RicercaAnagraficaResult res = ricercaPerDatiAnagrafici(rab);
				if(StringUtils.isBlank(res.getMessaggio()) && res.getElencoAssisiti()!=null)
					listAnagReg.addAll(res.getElencoAssisiti());
				if(!StringUtils.isBlank(res.getMessaggio()))
					this.addWarning("", res.getMessaggio());
				
				for(int i=0; i<maxResultAnagReg;i++){
					if(i>=listAnagReg.size()){
						break;
					}
					
					
					PersonaDettaglio s=listAnagReg.get(i);
					DatiUserSearchBean sDto = new DatiUserSearchBean();
					sDto.setSoggetto(s);
					String itemLabel = s.getCognome().toUpperCase() + " "+ s.getNome().toUpperCase();
					if (s.getDataNascita() != null)
						itemLabel += " nato il: " + sdf.format(s.getDataNascita()); 
					
					if(s.isDefunto()){
						itemLabel += " - defunto";
						itemLabel += s.getDataMorte()!=null ? " il: " + sdf.format(s.getDataMorte()) : "";
					}
					
					sDto.setItemLabel(itemLabel);		
					String identificativo = s.getIdentificativo()!=null ? s.getIdentificativo() : "@" +s.getCodfisc();
					sDto.setId(tipoRicerca + identificativo);
					String key = !StringUtils.isBlank(s.getCodfisc()) ? s.getCodfisc().toUpperCase() : Integer.toString(itemLabel.hashCode());
						
					if(!listAutocomplete.containsKey(key))
						listAutocomplete.put(key, sDto);
				}
					
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
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

	public UserSearchExtInput getParams() {
		return params;
	}

	public void setParams(UserSearchExtInput params) {
		this.params = params;
	}

	@Override
	public List<DatiUserSearchBean> getLstSoggetti() {
		return lstSoggetti;
	}

	public void setLstSoggetti(List<DatiUserSearchBean> lstSoggetti) {
		this.lstSoggetti = lstSoggetti;
	}
	
	public void onChangeTipoRicerca(){
		this.params.clearCampi();
		lstSoggetti = new ArrayList<DatiUserSearchBean>();
		selSoggetto=null;
	}

	public boolean isSearchButtonPressed() {
		return searchButtonPressed;
	}

	public void setSearchButtonPressed(boolean searchButtonPressed) {
		this.searchButtonPressed = searchButtonPressed;
	}
	
	
	
}
