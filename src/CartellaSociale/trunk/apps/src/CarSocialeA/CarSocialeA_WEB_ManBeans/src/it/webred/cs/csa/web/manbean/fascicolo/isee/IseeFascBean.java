package it.webred.cs.csa.web.manbean.fascicolo.isee;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.bean.IseeBean;
import it.webred.cs.jsf.interfaces.IIsee;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.isee.IIseeJson;
import it.webred.cs.json.isee.ver1.ManIseeBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public class IseeFascBean extends FascicoloCompBaseBean implements IIsee {
		
	//private List<IseeBean> listaIsee;
	private IIseeJson manIsee;
	private int idxSelected;
	private List<SelectItem> listaTipologie;
	private List<SelectItem> listaAnni;
	private List<IIseeJson> listaIsee;
	
	AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	@Override
	public void initializeData() {
		listaIsee = new ArrayList<IIseeJson>();
		try{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);
			for(CsDIsee jpa : diarioService.findIseeByCaso(dto)){
				
				ManIseeBean manSchedaValutazione;
				String defaultVersion = "1";
				manSchedaValutazione = (ManIseeBean) WebredClassFactory.newInstance( "", IIseeJson.class, defaultVersion );
	
				//Initialize scheda barthel
				manSchedaValutazione.init(jpa);
				listaIsee.add(manSchedaValutazione);
				//listaIsee.add(this.riversaJpaToIsee(jpa));
			}
				
			// loadSchedeJson();
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	@Override
	protected void initializeData(Object data) {
		this.initializeData();
	}
	
	//Metodo di prova per JsonController multiversione
	private void loadSchedeJson() throws Exception{
	
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(this.getCsASoggetto().getAnagraficaId());
		dto.setObj2(DataModelCostanti.TipoDiario.ISEE_ID);
		List<CsDValutazione> schede = diarioService.getSchedeValutazioneSoggetto(dto);

		String jsonString = ""; String className = ""; 
		for(CsDValutazione isee : schede){
			jsonString = isee.getCsDDiario().getCsDClob().getDatiClob();
			className = isee.getVersioneScheda();

			IIseeJson manSchedaValutazione;
			
			//la versione di default è utile se si vuole comunque instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			manSchedaValutazione = (IIseeJson) WebredClassFactory.newInstance( className, IIseeJson.class, defaultVersion );

			//Initialize scheda barthel
			manSchedaValutazione.init( null, isee );
			listaIsee.add(manSchedaValutazione);
		
		}
		
	}
	
	@Override
	public void nuovo() {
		String defaultVersion = "1"; //La vers2 è stata creata solo per provare il funzionamento del JsonController
		try {
			manIsee = (IIseeJson) WebredClassFactory.newInstance( "", IIseeJson.class, defaultVersion );
			manIsee.setIdCaso(idCaso);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void carica() {
		manIsee = listaIsee.get(idxSelected);
		
	}
	
	public void handleChangeDataISEE(javax.faces.event.AjaxBehaviorEvent event) throws ParseException {
		Date data = ((Date)((javax.faces.component.UIInput)event.getComponent()).getValue());
		if(data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			int anno = c.get(Calendar.YEAR);
			String sd = "15/01/"+(anno+1);
			Date scadenza = sdf.parse(sd);
			((IseeBean)manIsee.getSelected()).setDataScadenzaIsee(scadenza);
		}
	}
	
	
	
	
	@Override
	public void salva() {
		if(manIsee.save())
			initializeData();
	}
	

	
	@Override
	public void elimina() {
		
		if(manIsee.elimina())
			initializeData();
	}
	
	/*private boolean validaIsee() {
		
		boolean ok = true;
		
		List<String> messages = ((IseeBean)manIsee.getSelected()).checkObbligatorieta();
		if( messages.size() > 0 ) {
			addWarning(StringUtils.join(messages, "<br/>"), "");
			ok &= false;
		}
				
		return ok;
	}*/
	
	/*public List<IseeBean> getListaIsee() {
		return listaIsee;
	}

	public void setListaIsee(List<IseeBean> listaIsee) {
		this.listaIsee = listaIsee;
	}
*/
	public List<SelectItem> getListaTipologie() {
		if(listaTipologie == null) {
			listaTipologie = new ArrayList<SelectItem>();
			listaTipologie.add(new SelectItem("Familiare", "Familiare"));
			listaTipologie.add(new SelectItem("Individuale", "Individuale"));
		}
		return listaTipologie;
	}

	public void setListaTipologie(List<SelectItem> listaTipologie) {
		this.listaTipologie = listaTipologie;
	}

/*	public IseeBean getIsee() {
		return isee;
	}

	public void setIsee(IseeBean isee) {
		this.isee = isee;
	}*/

	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	public List<SelectItem> getListaAnni() {
		if(listaAnni == null) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			listaAnni = new ArrayList<SelectItem>();
			listaAnni.add(new SelectItem(String.valueOf(year), String.valueOf(year)));
			listaAnni.add(new SelectItem(String.valueOf(year-1), String.valueOf(year-1)));
			listaAnni.add(new SelectItem(String.valueOf(year-2), String.valueOf(year-2)));
			listaAnni.add(new SelectItem(String.valueOf(year-3), String.valueOf(year-3)));
			listaAnni.add(new SelectItem(String.valueOf(year-4), String.valueOf(year-4)));
			listaAnni.add(new SelectItem(String.valueOf(year-5), String.valueOf(year-5)));
		}
		return listaAnni;
	}

	public void setListaAnni(List<SelectItem> listaAnni) {
		this.listaAnni = listaAnni;
	}

	public List<IIseeJson> getListaIsee() {
		return listaIsee;
	}

	public void setListaIsee(List<IIseeJson> listaManIsee) {
		this.listaIsee = listaManIsee;
	}

	@Override
	public <T extends JsonBaseBean> T  getIsee() {
		return manIsee.getSelected();
	};

}
