package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.interfaces.IUserSearch;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.client.anag.client.*;
import it.webred.siso.ws.client.anag.exception.*;
import java.net.URL;
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

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	
	private String widgetVar = "userSearchVar";
	//private String idSoggetto;
	private DatiUserSearchBean selSoggetto;
	private Integer maxResult = 15;
	
	@Override
	public List<DatiUserSearchBean> getLstSoggetti(String query) {
		
		List<DatiUserSearchBean> listAutocomplete = new ArrayList<DatiUserSearchBean>();
		RicercaSoggettoAnagrafeDTO rsDto = new RicercaSoggettoAnagrafeDTO();
		fillEnte(rsDto);
		rsDto.setDenom(query);
		//rsDto.setMaxResult(maxResult);
		try {
			
			AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			
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

			if (list.size() < maxResult)
				this.ricercaInAnagrafeSanitaria(query, list.size(), listAutocomplete);
			
		} catch (NamingException e) {
			addError("general", "caricamento.error");
			logger.error(e.getMessage(), e);
		}
		
		return listAutocomplete;
	}

	private void ricercaInAnagrafeSanitaria(String query, int listSize, List<DatiUserSearchBean> listAutocomplete){
		
		try {
			AnagRegUser anagReguser = getAnagRegUser();
			URL wsdlLocation = getAnagRegWebServiceWSDLLocation();
			if(anagReguser!=null && wsdlLocation!=null){
				List <PersonaFindResult> listAnagReg=new ArrayList <PersonaFindResult>();
				
				AnagrafeClient anag = new AnagrafeClient(wsdlLocation);
				
				RicercaAnagraficaBean rb = new RicercaAnagraficaBean();
				rb.setUsername(anagReguser.getUsername());
				rb.setPassword(anagReguser.getPassword());
				anag.openSession(rb);
				String[] queryArray=query.split(" ");
				for(int i=0;i<queryArray.length;i++){
					String subQuery1="";
					String subQuery2="";
					for(int j=0; j<=i;j++){
						subQuery1=(subQuery1+" "+queryArray[j]).trim();
					}
					for(int k=i+1; k< queryArray.length;k++){
						subQuery2=(subQuery2+" "+queryArray[k]).trim();
					}
					RicercaAnagraficaBean rab= new RicercaAnagraficaBean();
					rab.setCognomePaziente(subQuery1);
					rab.setNomePaziente(subQuery2);
					List <PersonaFindResult> l1=anag.findCognomeNome(rab);
					if(l1!=null && !l1.isEmpty()){
						listAnagReg.addAll(l1);
					}
					rab.setCognomePaziente(subQuery2);
					rab.setNomePaziente(subQuery1);
					List <PersonaFindResult> l2=anag.findCognomeNome(rab);
					if(l2!=null && !l2.isEmpty()){
						listAnagReg.addAll(l2);
					}
					
					
				}
				int maxResultAnagReg=maxResult-listSize;
				for(int i=0; i<maxResultAnagReg;i++){
					if(i>=listAnagReg.size()){
						break;
					}
					PersonaFindResult s=listAnagReg.get(i);
					DatiUserSearchBean sDto = new DatiUserSearchBean();
					sDto.setSoggetto(s);
					String itemLabel = s.getCognome().toUpperCase() + " "
							+ s.getNome().toUpperCase();
					if (s.getDataNascita() != null)
						itemLabel += " nato il: " + sdf.format(s.getDataNascita())+". *Ricavato da anagrafe sanitaria regionale*";
					sDto.setItemLabel(itemLabel);
					sDto.setId("SANITARIA"+s.getIdPaziente());
					listAutocomplete.add(sDto);
					
				}
				anag.closeSession();
			}
			
		} 
		catch (AnagrafeException e) {
			logger.error(e.getMessage(), e);
		} catch (AnagrafeSessionException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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

/*	@Override
	public String getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}*/

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
