package it.webred.cs.csa.web.manbean.fascicolo.interventi;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SiruDominioDTO;
import it.webred.cs.csa.ejb.dto.SiruResultDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.enumeratori.SiruEnum;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.ProgettoErogazioni;
import it.webred.cs.data.DataModelCostanti.ProgettoErogazioni.FLAG_RES_DOM;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIInterventoPrFse;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbFormaGiuridica;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbIngMercato;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.CsTbSettoreImpiego;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.ComuneResidenzaMan;
import it.webred.cs.jsf.manbean.comuneNazione.ComuneGenericMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.familiariConviventi.FamiliariManBaseBean;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatiProgettoBean extends CsUiCompBaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb ("AccessTableInterventoSessionBean");
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb("AccessTableSchedaSessionBean");
	protected AccessTableDominiSiruSessionBeanRemote  dominiSiruService= (AccessTableDominiSiruSessionBeanRemote)getCarSocialeEjb("AccessTableDominiSiruSessionBean");
	protected AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote) getCarSocialeEjb ("AccessTableIndirizzoSessionBean");
	protected AccessTableOperatoreSessionBeanRemote opService = (AccessTableOperatoreSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
	// SISO-945
	 	
	private Long idTipoIntervento;
	private Long idTipoIntrCustom;
	private Long idCatSociale;
	
	private CsADatiSociali datiSociali;
	
	//SISO-972 
	private boolean renderAttivita = false;
	
	protected List<CsOSettore> listaSettori;
	
	protected CsCTipoIntervento tipoIntervento;
	protected CsCTipoInterventoCustom tipoIntCustom;
	
	private List<ArFfProgetto> listaProgetti= new ArrayList<ArFfProgetto>();  //SISO-522
	private CsIInterventoPr csIInterventoPr;
	private String codiceForm;
	
	private List<ArFfProgettoAttivita> listaSottocorsi= new ArrayList<ArFfProgettoAttivita>();//SISO-790
	private List<ArFfProgettoAttivita> copiaListaSottocorsi=new ArrayList<ArFfProgettoAttivita>(); 
	private Long idProgettoSelezionato;
	
	
	//SISO-790
	
	//SISO-1131
	private CsTbProgettoAltro selectedProgettoAltro;	
	private boolean abilitaMenuProgettiAltro = false;
	
	protected boolean solaLettura = false;
	private boolean stampaPor = false;
	private final String NON_RILEVATO="Non rilevato";
	
	private ComuneGenericMan comuneMan;
	
	//SISO-972
	private int numFSE = 0;	
	
	private List<SelectItem> listaSettoriGestGroup;
	
	private List<VLineaFin>  listaOrigineFin= new ArrayList<VLineaFin>();
	private List<SelectItem> lstConLavorativa;
	private List<SelectItem> lstTitoliStudio;
	private List<SelectItem> lstIngMercato;
	private List<SelectItem> lstSettoreImpiego;
	private List<CsTbIngMercato> lstIngMercatoTooltip;

	private List<SelectItem> lstGruppoVulnerabile;
	private List<SelectItem> lstFormeGiuridiche;
	
	private List<SelectItem> lstAteco; //SISO-850
	
	private boolean lavoroFromDatiSociali=false;
	private boolean studioFromDatiSociali=false;
	private boolean datiFromCartella = false;
	
	
	private ComuneResidenzaMan domicilioComuneMan;
	//SISO-945
	private ComuneNazioneNascitaMan comuneNazioneNascitaBean = null;
	// FINE SISO-945
 
	//SISO-1131
	private String arFfProgettoAltro;
	//FINE SISO-1131
	private final DatiProgettoBeanTooltipText tooltipText = new DatiProgettoBeanTooltipText();
	 

	public int getNumFSE() {
		return numFSE;
	}

	public void setNumFSE(int numFSE) {
		this.numFSE = numFSE;
	}

	private List<SelectItem> listaSettoriTitGroup;
	public ComuneNazioneNascitaMan getComuneNazioneNascitaBean() {
		return comuneNazioneNascitaBean;
	}

	public void setComuneNazioneNascitaBean(
			ComuneNazioneNascitaMan comuneNazioneNascitaBean) {
		this.comuneNazioneNascitaBean = comuneNazioneNascitaBean;
	}

	public Long getIdProgettoSelezionato() {//SERVE PER IL CONTROLLO DELLA SELEZIONE IN FglInterventoBEan alla Stampa del POR
		return idProgettoSelezionato;
	}
	
    //SISO-1131
	/**
	 * @return the selectedProgettoAltro
	 */
	public CsTbProgettoAltro getSelectedProgettoAltro() {
		return selectedProgettoAltro;
	}

	/**
	 * @param selectedProgettoAltro the selectedProgettoAltro to set
	 */
	public void setSelectedProgettoAltro(CsTbProgettoAltro selectedProgettoAltro) {
		this.selectedProgettoAltro = selectedProgettoAltro;
	}
	
	
	//FINE SISO-1131
	public List<SelectItem> getLstTitoliStudio() {
		
		if(lstTitoliStudio == null){
			lstTitoliStudio = new ArrayList<SelectItem>();
			lstTitoliStudio.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbTitoloStudio> lst = confService.getTitoliStudio(bo);
			if (lst != null) {
				for (CsTbTitoloStudio obj : lst) {
					lstTitoliStudio.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstTitoliStudio;
	}

	

	public void setLstTitoliStudio(List<SelectItem> lstTitoliStudio) {
		this.lstTitoliStudio = lstTitoliStudio;
	}

	//inizio SISO-790
		public List<ArFfProgettoAttivita> getListaSottocorsi() {
			return listaSottocorsi;
		}

		public void setListaSottocorsi(List<ArFfProgettoAttivita> listaSottocorsi) {
			this.listaSottocorsi = listaSottocorsi;
		}

		public List<ArFfProgettoAttivita> getCopiaListaSottocorsi() {
			return copiaListaSottocorsi;
		}

		public void setCopiaListaSottocorsi(
				List<ArFfProgettoAttivita> copiaListaSottocorsi) {
			this.copiaListaSottocorsi = copiaListaSottocorsi;
		}

		//fine SISO-790

	//SISO-972 datiProgettoBean.loadDatiProgetto(progetto,tabProgettoSolaLettura,this.idTipoIntevento, this.idTipoIntrCustom, catSocialeID);
		
	public void loadDatiProgetto(CsIInterventoPr progetto, boolean solalettura, Long tipoIntId, Long tipoInCustomId, Long catSocialeID, SoggettoErogazioneBean soggettoErogazione) {
			
			 this.loadDatiProgetto(progetto, solalettura, tipoIntId, tipoInCustomId, catSocialeID );
			 //this.loadDatiResidenza(soggettoErogazione);
			 this.loadDatiSociali(soggettoErogazione);
			 this.onChangeAttivita(soggettoErogazione);
	}	
	//SISO-972 fine
	
	
	public void loadDatiProgetto(CsIInterventoPr progetto, boolean solalettura, Long tipoIntId, Long tipoInCustomId, Long catSocialeID) {
		
		if(progetto!=null){
			this.csIInterventoPr = progetto;
			
			//this.selUnitaMisura = this.csIQuota.getCsTbUnitaMisura()!=null ? this.csIQuota.getCsTbUnitaMisura().getId() : null;
		}else
			this.csIInterventoPr = new CsIInterventoPr();
		
		if(!this.getServizioGestibileComeAmbito()) 
			csIInterventoPr.setServizioAmbito(false);
		
		this.solaLettura = solalettura;
		
		//Imposto al settore corrente, se non valorizzato
		if(this.getSelSettoreTitolareId()==null){
			this.setSelSettoreTitolareId(getCurrentOpSettore().getCsOSettore().getId());
			//Richiamare FglInterventoBean.cmbSettoreOnChange per aggiornare il campo "Gestione della Spesa" in TAB EROGAZIONI
			FglInterventoBean fb = (FglInterventoBean)getReferencedBean("fglInterventoBean");
			fb.cmbSettoreOnChange();
		}
		
		this.idTipoIntervento=tipoIntId;
		this.idTipoIntrCustom=tipoInCustomId;
		this.idCatSociale = catSocialeID;
		
		loadListaOrigineFinanziamenti();
		
		if(this.csIInterventoPr.getCsTbIngMercato()!=null && DataModelCostanti.TipiIngMercato.OCCUPATO.equalsIgnoreCase(this.csIInterventoPr.getCsTbIngMercato().getId())){
			this.comuneMan = new ComuneGenericMan("Sede aziendale");
			if(this.csIInterventoPr.getCsIInterventoPrFse()!=null)
				this.comuneMan.setComune(this.getComune(this.csIInterventoPr.getCsIInterventoPrFse().getAzComuneCod()));
		}
		stampaPor = false;
		
		this.codiceForm = null;
		
		//SISO_1131
		
		this.setSelectedProgettoAltro(this.csIInterventoPr.getCsTbProgettoAltro()!=null? this.csIInterventoPr.getCsTbProgettoAltro(): new CsTbProgettoAltro());
		
		
		
		if(this.csIInterventoPr.getCsIInterventoPrFse()!=null){
			this.codiceForm = "FSE";

			String jsonDomicilio = this.getCsIInterventoPr().getCsIInterventoPrFse().getComuneDomicilio();
			String jsonNascita = this.getCsIInterventoPr().getCsIInterventoPrFse().getComuneNascita();
			if(!StringUtils.isBlank(this.getModuloPorRegionale())) stampaPor = true;
			
			ObjectMapper mapper = new ObjectMapper();
			
			//residenzaComuneMan = new ComuneResidenzaMan();
			domicilioComuneMan = new ComuneResidenzaMan();
			//SISO - 945
			comuneNazioneNascitaBean = new ComuneNazioneNascitaMan();
			//SISO - 945 fine
			try {
				//residenzaComuneMan.setComune(mapper.readValue(jsonResidenza, ComuneBean.class));
				domicilioComuneMan.setComune(mapper.readValue(jsonDomicilio, ComuneBean.class));
				//SISO - 945
				if(jsonNascita != null){
					ComuneBean nComune = mapper.readValue(jsonNascita, ComuneBean.class);
					comuneNazioneNascitaBean.getComuneNascitaMan().setComune(nComune);
				}
				//SISO - 945 fine
 
				if(this.getCsIInterventoPr().getCsIInterventoPrFse().getStatoNascitaCod() != null &&
						this.getCsIInterventoPr().getCsIInterventoPrFse().getStatoNascitaDes() != null){
					AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(this.getCsIInterventoPr().getCsIInterventoPrFse().getStatoNascitaCod(),
									this.getCsIInterventoPr().getCsIInterventoPrFse().getStatoNascitaDes());
					
 
					this.comuneNazioneNascitaBean.setNazioneValue();
					this.comuneNazioneNascitaBean.getNazioneMan().setNazione(amTabNazioni);
				}
			/*	if(this.csIInterventoPr.getCsIInterventoPrFse().getCsTbGrVulnerabile() != null){
					this.comunicaVulnerabilita = "SI"; 
				}else if (this.csIInterventoPr.getCsIInterventoPrFse().getCsTbGrVulnerabile() == null && this.csIInterventoPr.getCsIInterventoPrFse().getComunicaVul() != null){
					this.comunicaVulnerabilita = this.getCsIInterventoPr().getCsIInterventoPrFse().getComunicaVul();
				}else{
					this.comunicaVulnerabilita="";
				}*/
				 
				//SISO - 945 fine
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
	}
	
	public void loadDatiSociali(SoggettoErogazioneBean soggettoErogazione){
		datiSociali = null;
		if(isAccessoEsternoDatiCartella()){
			if (soggettoErogazione!=null && !StringUtils.isBlank(soggettoErogazione.getCodiceFiscale())) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(soggettoErogazione.getCodiceFiscale());
				datiSociali = schedaService.findDatiSocialiAttiviBySoggettoCf(dto);
			}else
				this.addWarning("Recupero dati sociali", "Non è stato possibile precaricare i dati esistenti in cartella sociale: codice fiscale non valorizzato");	
		}
	}
	
	public void valorizzaLavoroDatiSociali(){
		lavoroFromDatiSociali = false;
		studioFromDatiSociali = false;
		
		if(datiSociali==null) this.addWarning("", "Dati Sociali non presenti per il soggetto");
		
		//if(this.csIInterventoPr.getCsTbCondLavoro()==null){
			this.csIInterventoPr.setCsTbCondLavoro(new CsTbCondLavoro());
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			if(datiSociali!=null && datiSociali.getCondLavorativaId()!=null){
				dto.setObj(datiSociali.getCondLavorativaId().toString());
				CsTbCondLavoro lavoro = confService.getCondLavoroById(dto);
				csIInterventoPr.setCsTbCondLavoro(lavoro);
				lavoroFromDatiSociali = true;
			}else{
				dto.setObj(this.NON_RILEVATO);
				CsTbCondLavoro lavoro = confService.getCondLavoroByDescrizione(dto);
				csIInterventoPr.setCsTbCondLavoro(lavoro);
			}
		//}
		
		//if(this.csIInterventoPr.getCsTbIngMercato()==null){
			if(this.csIInterventoPr.getCsTbCondLavoro()!=null)
				this.csIInterventoPr.setCsTbIngMercato(csIInterventoPr.getCsTbCondLavoro().getCsTbIngMercato());
			else
				this.csIInterventoPr.setCsTbIngMercato(new CsTbIngMercato());
			this.onChangeIngMercato();
	
			this.csIInterventoPr.setCsTbTitoloStudio(new CsTbTitoloStudio());
			if(datiSociali!=null && datiSociali.getTitoloStudioId()!=null){
				this.csIInterventoPr.getCsTbTitoloStudio().setId(datiSociali.getTitoloStudioId().longValue());
				studioFromDatiSociali=true;
			}else{ 
				dto.setObj(this.NON_RILEVATO);
				CsTbTitoloStudio titolo = confService.getTitoloStudioByDescrizione(dto);
				this.csIInterventoPr.setCsTbTitoloStudio(titolo);
			}
				
			
			if(datiSociali!=null && datiSociali.getSettImpiegoId()!=null){
				this.csIInterventoPr.setCsTbSettoreImpiego(new CsTbSettoreImpiego());
				this.csIInterventoPr.getCsTbSettoreImpiego().setId(datiSociali.getSettImpiegoId().longValue());
				//studioFromDatiSociali=true;
			}else this.csIInterventoPr.setCsTbSettoreImpiego(null);
	}
	
	public List<SelectItem> getLstSettoreImpiego() {
		
		if(lstSettoreImpiego == null){
			lstSettoreImpiego = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbSettoreImpiego> lst = confService.getSettoreImpiego(bo);
			if (lst != null) {
				for (CsTbSettoreImpiego obj : lst) {
					lstSettoreImpiego.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstSettoreImpiego;
	}
	
	public List<SelectItem> getLstConLavorativa() {
		if(lstConLavorativa == null){
			lstConLavorativa = new ArrayList<SelectItem>();
			lstIngMercato = new ArrayList<SelectItem>();
			lstIngMercatoTooltip = new ArrayList<CsTbIngMercato>();
			
			CeTBaseObject  xo = new CeTBaseObject();
			fillEnte(xo);
			TreeMap<String, List<CsTbCondLavoro>> tree = confService.getMappaCondLavoro(xo);
			for(String str : tree.keySet()){
				List<CsTbCondLavoro> lst = tree.get(str);
				if (lst != null && !lst.isEmpty()) {
					
					CsTbIngMercato ingMercato = lst.get(0).getCsTbIngMercato();
					if(ingMercato.getDescrizione()!=null && !ingMercato.getDescrizione().isEmpty())
						lstIngMercatoTooltip.add(ingMercato);
					lstIngMercato.add(new SelectItem(ingMercato.getId(), ingMercato.getDescrizione()));
					
					String labelGroup = ingMercato.getDescrizione();
					SelectItemGroup gr = new SelectItemGroup(labelGroup);
					List<SelectItem> siList = new ArrayList<SelectItem>();
					for (CsTbCondLavoro obj : lst) {
						SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
						boolean valorePresetted = getCondLavoroId()!=null && obj.getId()==getCondLavoroId();
						if("0".equals(obj.getAbilitato()) && !valorePresetted)
							si.setDisabled(true);
						if(labelGroup==null || labelGroup.trim().isEmpty())
							lstConLavorativa.add(si);
						else
						    siList.add(si);
					}
					if(labelGroup!=null && !labelGroup.trim().isEmpty()){
						gr.setSelectItems(siList.toArray(new SelectItem[siList.size()]));
						lstConLavorativa.add(gr);
					}
				}		
			}
		}
		return lstConLavorativa;
	}
	
	
	private CsTbCondLavoro valorizzaCondLavoro(Long id){
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(id!=null){
    		d.setObj(id.toString());
    		return confService.getCondLavoroById(d);
    	}
    	return null;
    }
	
	private CsTbSettoreImpiego valorizzaSettImpiego(Long id){
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(id!=null){
    		d.setObj(id);
    		return confService.getSettoreImpiegoById(d);
    	}
    	return null;
    }
	
	private CsTbGVulnerabile valorizzaGVulnerabile(String id){
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(id!=null){
    		d.setObj(id.toString());
    		return confService.getGrVulnerabileById(d);
    	}
    	return null;
    }

	private void popolaLuogoDiNascitaFse(){
		try{
			
			ObjectMapper mapper = new ObjectMapper();
			//resetto i valori per poi valorizzare correttamente
			this.csIInterventoPr.getCsIInterventoPrFse().setComuneNascita(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setStatoNascitaCod(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setStatoNascitaDes(null);
			
			if(comuneNazioneNascitaBean.isComune()){
				ComuneBean comune = this.comuneNazioneNascitaBean.getComuneNascitaMan().getComune();
				this.csIInterventoPr.getCsIInterventoPrFse().setComuneNascita(comune!=null ? mapper.writeValueAsString(comune) : null);
			
			}else{	
				AmTabNazioni naz = this.comuneNazioneNascitaBean.getNazioneNascitaMan().getNazione();
				if(naz!=null){
					this.csIInterventoPr.getCsIInterventoPrFse().setStatoNascitaCod(naz.getCodIstatNazione());
					this.csIInterventoPr.getCsIInterventoPrFse().setStatoNascitaDes(naz.getNazione());
				}
			}
		} catch (JsonProcessingException e) {
				logger.error("Errore popolamento Comune/Nazione residenza", e);
		}
	}
	
	public boolean salva(SoggettoErogazioneBean soggettoErogazione) {
		boolean bOk = true;	
	
		if(this.getCsIInterventoPr()!=null){
			if(this.selectedProgettoAltro !=null){
				//SISO-1131 devo salvare nuovo progetto altro.
				this.getCsIInterventoPr().setCsTbProgettoAltro(null);
				if (selectedProgettoAltro.getId() == 0 && selectedProgettoAltro.getDescrizione()!=null){
					//salvataggio
					BaseDTO dto = new BaseDTO();
				    fillEnte(dto);
					dto.setObj(this.selectedProgettoAltro);
					selectedProgettoAltro = interventoService.salvaProgettoAltro(dto);
					
				}
				if (selectedProgettoAltro.getId() > 0){
				this.getCsIInterventoPr().setCsTbProgettoAltro(selectedProgettoAltro);
				}
			}
			try{
				//SISO-945 
				if(this.getCsIInterventoPr().getCsIInterventoPrFse() != null){
					this.popolaLuogoDiNascitaFse();
					//SISO-962 Inizio
					//this.getCsIInterventoPr().getCsIInterventoPrFse().setViaResidenza(soggettoErogazione.getViaResidenza());
					//this.getCsIInterventoPr().getCsIInterventoPrFse().setComuneResidenza(soggettoErogazione.getJsonComuneResidenza());
					//SISO-962 Fine
				}
				//SISO - 945 Fine
				
			/*	SPOSTATO NEL BLOCCO DI VALIDAZIONE
			 * 
			 * if(this.csIInterventoPr.getCsTbIngMercato()!=null && this.getCsIInterventoPr().getCsTbIngMercato().getId()==null)
					this.csIInterventoPr.setCsTbIngMercato(null);
				
				if(this.csIInterventoPr.getCsTbTitoloStudio()!=null && this.getCsIInterventoPr().getCsTbTitoloStudio().getId()<=0)
					this.csIInterventoPr.setCsTbTitoloStudio(null);*/

				
				
				
				//SISO-817
				BaseDTO dto = new BaseDTO();
			    fillEnte(dto);
				dto.setObj(this.csIInterventoPr);
				dto.setObj2(soggettoErogazione);
				csIInterventoPr = interventoService.salvaProgettoIntervento(dto);
				
			}catch (Exception e) {
				logger.error("Errore salvataggio dati progetto", e);
				this.addError("Errore salvataggio dati progetto", "");
				bOk = false;
			}
		
		}else bOk = false;
		return bOk;
		
	}

	public boolean isSolaLettura() {
		return solaLettura;
	}

	public CsIInterventoPr getCsIInterventoPr() {
		return csIInterventoPr;
	}

	public void setCsIInterventoPr(CsIInterventoPr csIInterventoPr) {
		this.csIInterventoPr = csIInterventoPr;
	}

	public void setCsIProgettoPr(CsIInterventoPr csIInterventoPr) {
		this.csIInterventoPr = csIInterventoPr;
	}

	public void setLstConLavor(List<SelectItem> lstConLavorativa) {
		this.lstConLavorativa = lstConLavorativa;
	}
	//inizio SISO-522 
	public List<ArFfProgetto> getListaProgetti() {
		return listaProgetti;
	}

	public void setListaProgetti(List<ArFfProgetto> listaProgetti) {
		this.listaProgetti = listaProgetti;
	}

	public Long getCondLavoroId() {
		return this.getCsIInterventoPr().getCsTbCondLavoro()!=null ? this.getCsIInterventoPr().getCsTbCondLavoro().getId() : null;
	}

	public void setCondLavoroId(Long condLavoroId) {
		CsTbCondLavoro lav = this.valorizzaCondLavoro(condLavoroId);
		this.getCsIInterventoPr().setCsTbCondLavoro(lav);
	}
	
	public void onChangeCondLavoro(){
		CsTbCondLavoro lav = this.getCsIInterventoPr().getCsTbCondLavoro();
		this.getCsIInterventoPr().setCsTbIngMercato(lav!=null ? lav.getCsTbIngMercato() : null);
		this.onChangeIngMercato();
		
		//SISO-850
		getLstAteco();
		
		if(!this.isOccupato() && !this.isPensionato())
			this.csIInterventoPr.setCsTbSettoreImpiego(null);
	}

	public List<SelectItem> getLstIngMercato() {
		return lstIngMercato;
	}

	public String getDescOrgTitolareId() {
		//CsOSettore s = this.getSettore(this.selSettoreTitolareId);
		CsOSettore s = this.csIInterventoPr.getSettoreTitolare();
		return  (s!=null && s.getCsOOrganizzazione()!=null) ? "- Org. "+s.getCsOOrganizzazione().getNome() : "";
	}
	



	/* == SISO-663 SM == */
	public String getDescOrgGestoreId() {
		// CsOSettore s = this.getSettore(this.selSettoreTitolareId);
		CsOSettore s = this.csIInterventoPr.getSettoreGestore();
		return (s != null && s.getCsOOrganizzazione() != null) ? "- Org. " + s.getCsOOrganizzazione().getNome() : "";
	}
	/* --===-- */

	//SISO-1131
	
	
	public boolean isAbilitaMenuProgettiAltro() {
//		return abilitaMenuProgettiAltro
		if (csIInterventoPr!=null && csIInterventoPr.getFfProgettoDescrizione() != null){
			this.abilitaMenuProgettiAltro = (!csIInterventoPr.getFfProgettoDescrizione().isEmpty() && csIInterventoPr.getFfProgettoDescrizione().equalsIgnoreCase("ALTRO"));
		}
		else{
			this.abilitaMenuProgettiAltro = false;
		}
		return this.abilitaMenuProgettiAltro;
	}

	

	public void setAbilitaMenuProgettiAltro(boolean abilitaMenuProgettiAltro) {
		this.abilitaMenuProgettiAltro = abilitaMenuProgettiAltro;
	}

	//FINE SISO-1131
	protected VLineaFin getOriginFinanziamentoSelezionato() {
		if (this.getOrigineFinanziamentoId() != null) {			
			for (VLineaFin dOrg : listaOrigineFin) {
				if (dOrg.getId().longValue() == this.getOrigineFinanziamentoId().longValue())
					return dOrg;
			}
		}
		return null;
	}	
	

	private void loadListaOrigineFinanziamenti(){
			BaseDTO bdto = new BaseDTO();		
			fillEnte(bdto);		
			listaOrigineFin= interventoService.findAllOrigineFinanziamenti(bdto); 

			listaProgetti = new ArrayList<ArFfProgetto>();
			CsOSettore titolare = this.csIInterventoPr.getSettoreTitolare();
			if (titolare!=null) {  
				//bdto.setObj(getSelSettoreTitolareId);
				//CsOSettore csOSettore =  confService.getSettoreById(bdto); 	// (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");  
				
				bdto.setObj(titolare.getCsOOrganizzazione().getCodRouting());
				listaProgetti = interventoService.findProgettiByBelfioreOrganizzazione(bdto);
				
				//inizio SISO-790
				listaSottocorsi= interventoService.findSottocorsi(bdto);
				
				LoadListaSottocorsi(listaProgetti, copiaListaSottocorsi);
				//fine SISO-790
			}
			
		}
	
	//inizio SISO-790
		public void LoadListaSottocorsi(List<ArFfProgetto> lstProgetti, List<ArFfProgettoAttivita> lstSottocorsi ){
			lstSottocorsi.clear();
			lstSottocorsi.addAll(listaSottocorsi);
			if (lstProgetti != null) {
				for (ArFfProgetto prog : lstProgetti) {
						if(prog.getDescrizione().equals(this.csIInterventoPr.getFfProgettoDescrizione())){
							idProgettoSelezionato=prog.getId();
						}
					}
				}
				
				for(Iterator<ArFfProgettoAttivita> iter = lstSottocorsi.listIterator(); iter.hasNext();){
					ArFfProgettoAttivita sottocorso =iter.next();
					if(!(sottocorso.getProgettoId().equals(idProgettoSelezionato))){
							iter.remove();
						}
					}
				
		}
		//fine SISO - 790	
	   	
	public List<VLineaFin> getListaOrigineFin() {
			return listaOrigineFin;
		}
	
	public void onChangeOrigineFinanziamento(SoggettoErogazioneBean soggettoErogazione) {
		
		VLineaFin lineaSelected = null;
		int i = 0;
		while (i<this.listaOrigineFin.size() && lineaSelected==null) {
			VLineaFin linea = this.listaOrigineFin.get(i);
			if (linea.getId().equals(this.getOrigineFinanziamentoId()) )
				lineaSelected = linea;
			i++;
		}
		if (lineaSelected!=null && lineaSelected.getProgettoId()!=null) {
			int j=0;
			boolean trovato = false;
			while (j<this.listaProgetti.size() && !trovato){
				ArFfProgetto progetto = this.listaProgetti.get(j);
				logger.debug("onChangeOrigineFinanziamento: progetto"+ progetto.getId() + "  " + lineaSelected.getProgettoId());
				if (progetto.getId() == lineaSelected.getProgettoId().longValue()) {
					this.csIInterventoPr.setFfProgettoDescrizione(progetto.getDescrizione());
					this.onChangeProgetto(soggettoErogazione);
					trovato = true;
				}
				j++;
			}
		}
	}

	public Long getOrigineFinanziamentoId() {
		return csIInterventoPr!=null ? this.csIInterventoPr.getFfOrigineId() : null;
	}

	public void setOrigineFinanziamentoId(Long origineFinanziamentoId) {
		if(origineFinanziamentoId!=null && origineFinanziamentoId>0)
			this.csIInterventoPr.setFfOrigineId(origineFinanziamentoId); 
		else 
			this.csIInterventoPr.setFfOrigineId(null); 
	}

	public String getCodFinanziamento() {
		return this.csIInterventoPr.getCodiceFin1();
	}

	public void setCodFinanziamento(String codFinanziamento) {
		this.csIInterventoPr.setCodiceFin1(codFinanziamento);
	}

	public Long getSelSettoreTitolareId() {
		if(this.csIInterventoPr.getSettoreTitolare()==null) 
			this.csIInterventoPr.setSettoreTitolare(new CsOSettore());
		return this.csIInterventoPr.getSettoreTitolare().getId();
	}

	public void setSelSettoreTitolareId(Long selSettoreTitolareId) {
		CsOSettore settore = this.getSettore(selSettoreTitolareId);
		this.csIInterventoPr.setSettoreTitolare(settore!=null? settore: new CsOSettore() );		
	}
	



	/* SISO-663 SM */

	public CsOSettore getSettoreTitolare() {
		if (this.csIInterventoPr.getSettoreTitolare() == null)
			this.csIInterventoPr.setSettoreTitolare(new CsOSettore());
		return csIInterventoPr.getSettoreTitolare();
	}

	public CsOSettore getSettoreGestore() {
		if (this.csIInterventoPr.getSettoreGestore() == null)
			this.csIInterventoPr.setSettoreGestore(new CsOSettore());
		return csIInterventoPr.getSettoreGestore();
	}

	public Long getSelSettoreGestoreId() {
		if (this.csIInterventoPr.getSettoreGestore() == null)
			this.csIInterventoPr.setSettoreGestore(new CsOSettore());
		return this.csIInterventoPr.getSettoreGestore().getId();
	}

	public void setSelSettoreGestoreId(Long selSettoreGestoreId) {
		CsOSettore settore = this.getSettore(selSettoreGestoreId);
		this.csIInterventoPr.setSettoreGestore(settore != null ? settore : new CsOSettore());
	}
	/* -=- */
	
	public String getGruppoVulnerabileId(){
		if(this.csIInterventoPr.getCsIInterventoPrFse().getCsTbGrVulnerabile()==null) 
			this.csIInterventoPr.getCsIInterventoPrFse().setCsTbGrVulnerabile(new CsTbGVulnerabile());
		return this.csIInterventoPr.getCsIInterventoPrFse().getCsTbGrVulnerabile().getId();
		
	}
	public void setGruppoVulnerabileId(String idGruppo){
		
		CsTbGVulnerabile g = valorizzaGVulnerabile(idGruppo);
		this.csIInterventoPr.getCsIInterventoPrFse().setCsTbGrVulnerabile(g!=null? g: new CsTbGVulnerabile() );
		
	}
	
	public Long getTitoloStudioId(){
		if(this.csIInterventoPr.getCsTbTitoloStudio()==null) this.csIInterventoPr.setCsTbTitoloStudio(new CsTbTitoloStudio());
		return  csIInterventoPr.getCsTbTitoloStudio().getId();
	}
	
	public void setTitoloStudioId(Long id){
		CsTbTitoloStudio g = valorizzaTitoloStudio(id);
		this.csIInterventoPr.setCsTbTitoloStudio(g!=null? g: new CsTbTitoloStudio() );
	}
	
	private CsTbTitoloStudio valorizzaTitoloStudio(Long id) {
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(id!=null){
    		d.setObj(id);
    		return confService.getTitoloStudioById(d);
    	}
    	return null;
	}


	
	
	public void setProgettoAltroId(Long id){
		CsTbProgettoAltro pa = valorizzaProgettoAltro(id);
		this.csIInterventoPr.setCsTbProgettoAltro(pa!=null? pa: new CsTbProgettoAltro());
	}
	
	private CsTbProgettoAltro valorizzaProgettoAltro(Long id) {
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(id!=null){
    		d.setObj(id);
    		return confService.getProgettoAltroById(d);
    	}
    	return null;
	}
	
	
	public List<CsTbProgettoAltro> loadListaProgettoAltro(String query) {
		List<CsTbProgettoAltro> result= new ArrayList<CsTbProgettoAltro>();
		
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(!query.isEmpty()){
    		d.setObj(query);
		result = interventoService.findProgettiAltroPerDesc(d);
		}
		return result;
	}
	
	public void onProgettoAltroSelect(javax.faces.event.AjaxBehaviorEvent event) {
		this.getCsIInterventoPr().setCsTbProgettoAltro(selectedProgettoAltro);

		if(selectedProgettoAltro!=null) {
			
		}
	}
	public Long getIdSettoreImpiego(){
		return  csIInterventoPr.getCsTbSettoreImpiego()!=null ? csIInterventoPr.getCsTbSettoreImpiego().getId() : null;
	}
	
	public void setIdSettoreImpiego(Long settImpiegoId) {
		CsTbSettoreImpiego lav = this.valorizzaSettImpiego(settImpiegoId);
		this.getCsIInterventoPr().setCsTbSettoreImpiego(lav);
	}
	
	public String getIngMercatoId(){
		return  csIInterventoPr.getCsTbIngMercato()!=null ? csIInterventoPr.getCsTbIngMercato().getId() : null;
	}
	
	public void setIngMercatoId(String id){
		CsTbIngMercato g = valorizzaIngProgetto(id);
		this.csIInterventoPr.setCsTbIngMercato(g!=null? g: new CsTbIngMercato() );
	}
	
	private CsTbIngMercato valorizzaIngProgetto(String id) {
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(id!=null){
    		d.setObj(id.toString());
    		return confService.getIngMercatoById(d);
    	}
    	return null;
	}

	public String getFseAzFormaGiuridicaId(){
		//if(this.csIInterventoPr.getCsIInterventoPrFse().getAzFormaGiuridica()==null) this.csIInterventoPr.getCsIInterventoPrFse().setAzFormaGiuridica(new CsTbFormaGiuridica());
		if(this.csIInterventoPr.getCsIInterventoPrFse().getAzFormaGiuridica()==null) return null;
		return  csIInterventoPr.getCsIInterventoPrFse().getAzFormaGiuridica().getId();
	}
	
	public void setFseAzFormaGiuridicaId(String id){
		CsTbFormaGiuridica g = valorizzaFormaGiuridica(id);
		this.csIInterventoPr.getCsIInterventoPrFse().setAzFormaGiuridica(g);
	}
	
	private CsTbFormaGiuridica valorizzaFormaGiuridica(String id) {
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(id!=null){
    		d.setObj(id.toString());
    		return confService.getFormaGiuridicaById(d);
    	}
    	return null;
	}

	public List<SelectItem> getListaSettoriTitGroup() {
		return listaSettoriTitGroup;
	}

	public void setListaSettoriTitGroup(List<SelectItem> listaSettoriTitGroup) {
		this.listaSettoriTitGroup = listaSettoriTitGroup;
	}
	
	/* SISO-663 SM */
	public List<SelectItem> getListaSettoriGestGroup() {
		return listaSettoriGestGroup;
	}

	public void setListaSettoriGestGroup(List<SelectItem> listaSettoriGestGroup) {
		this.listaSettoriGestGroup = listaSettoriGestGroup;
	}
	/* --=-- */

	protected CsOSettore getSettore(Long idSettore) {
		if (idSettore != null) {
			for (CsOSettore s : listaSettori) {
				if (s.getId().longValue() == idSettore.longValue())
					return s;
			}
		}
		return null;
	}
	
	public List<CsOSettore> getListaSettori() {
		return listaSettori;
	}

	public void setListaSettori(List<CsOSettore> listaSettori) {
		this.listaSettori = listaSettori;
	}
	
	public void onChangeProgetto(SoggettoErogazioneBean soggettoErogazione){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		
		codiceForm = null;
		String progetto = this.getCsIInterventoPr().getFfProgettoDescrizione();
		if(!StringUtils.isEmpty(progetto)){
			
			dto.setObj(progetto);
			dto.setObj2(this.idTipoIntervento);
			dto.setObj3(this.idTipoIntrCustom);
			dto.setObj4(this.idCatSociale);
			codiceForm = confService.findCodFormProgetto(dto);
			
			if (!progetto.equalsIgnoreCase("ALTRO")){
				this.setSelectedProgettoAltro(new CsTbProgettoAltro());
				this.csIInterventoPr.setCsTbProgettoAltro(new CsTbProgettoAltro());
			}
		}
		
		if(datiSociali==null) loadDatiSociali(soggettoErogazione);
	
		if(this.isRenderFSE()){
			
			//SISO-972
			/**
			 * Recuperare eventuali FSE precedenti per il soggetto
			 * */
		 
			//interventoService.getListaInterventiByCaso(BaseDTO dto)
			//FINE SISO-972
			//residenzaComuneMan = new ComuneResidenzaMan();
			domicilioComuneMan = new ComuneResidenzaMan();
			if(this.csIInterventoPr.getCsIInterventoPrFse()==null) 
				this.csIInterventoPr.setCsIInterventoPrFse(new CsIInterventoPrFse());
				
			//SISO-846
			if(this.csIInterventoPr.getCsIInterventoPrFse().getFlagResDom()==null)
				this.csIInterventoPr.getCsIInterventoPrFse().setFlagResDom(ProgettoErogazioni.FLAG_RES_DOM.RESIDENZA.getCodice());
			
			aggiornaDatiDaCartella(); //Sovrascrive i valori solo se sono a NULL
			
		}		
		else{
			this.csIInterventoPr.setCsIInterventoPrFse(null);
			//residenzaComuneMan = null;
			domicilioComuneMan = null;
		}
		onChangeAttivita(soggettoErogazione);
		//inizio SISO-790
		LoadListaSottocorsi(listaProgetti, copiaListaSottocorsi);
	    //fine SISO-790
	}
	
	
	//SISO-972
	
	public void onChangeAttivita(SoggettoErogazioneBean soggettoErogazione){
		numFSE = 0;
	 	if(this.isRenderFSE()){
	 		BaseDTO dto = new BaseDTO();
	 		
			//SISO-972
			/**
			 * Recuperare eventuali FSE precedenti per il soggetto
			 * */
			try {
				boolean allineamentoEseg = false;
				
			   String codiceFiscale = soggettoErogazione.getCodiceFiscale();
				if (!StringUtils.isBlank(codiceFiscale) && !StringUtils.isBlank(this.csIInterventoPr.getnSottocorsoAttivita())) {
				
					fillEnte(dto);
					dto.setObj(codiceFiscale);
					dto.setObj2(this.csIInterventoPr.getnSottocorsoAttivita());
					dto.setObj3(this.csIInterventoPr.getFfProgettoDescrizione());
						//String cf, String attivita, String progetto
					List<CsIInterventoEsegMastSogg> lsti = interventoService.getBeneficiariErogazione(dto);
					
					for(CsIInterventoEsegMastSogg csInterventoMastSogg : lsti){
						
						Long masterId = csInterventoMastSogg.getId().getMasterId();
						 dto = new BaseDTO();
						 fillEnte(dto);
						 dto.setObj(masterId);
						 CsIInterventoPr prEsistente  = interventoService.getProgettoByMasterId(dto);
						
						if(!allineamentoEseg){
							this.csIInterventoPr.getCsIInterventoPrFse().setAzCF(prEsistente.getCsIInterventoPrFse().getAzCF());
							this.csIInterventoPr.getCsIInterventoPrFse().setAzCodAteco(prEsistente.getCsIInterventoPrFse().getAzCodAteco() );
							this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneCod(prEsistente.getCsIInterventoPrFse().getAzComuneCod());
							
							this.comuneMan = new ComuneGenericMan("Sede aziendale");
							if(this.csIInterventoPr.getCsIInterventoPrFse().getAzComuneCod() != null){
								if(this.csIInterventoPr.getCsIInterventoPrFse()!=null)
									this.comuneMan.setComune(this.getComune(this.csIInterventoPr.getCsIInterventoPrFse().getAzComuneCod()));
							}
							
							this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneDes(prEsistente.getCsIInterventoPrFse().getAzComuneDes());
							this.csIInterventoPr.getCsIInterventoPrFse().setAzDescDimensioni(prEsistente.getCsIInterventoPrFse().getAzDescDimensioni());
							this.csIInterventoPr.getCsIInterventoPrFse().setAzFormaGiuridica(prEsistente.getCsIInterventoPrFse().getAzFormaGiuridica());
							this.csIInterventoPr.getCsIInterventoPrFse().setAzPIVA(prEsistente.getCsIInterventoPrFse().getAzPIVA());
							this.csIInterventoPr.getCsIInterventoPrFse().setAzProv(prEsistente.getCsIInterventoPrFse().getAzProv());
							this.csIInterventoPr.getCsIInterventoPrFse().setAzRagioneSociale(prEsistente.getCsIInterventoPrFse().getAzRagioneSociale());
							this.csIInterventoPr.getCsIInterventoPrFse().setAzVia(prEsistente.getCsIInterventoPrFse().getAzVia());
							this.csIInterventoPr.getCsIInterventoPrFse().setCellulare(prEsistente.getCsIInterventoPrFse().getCellulare());
							//this.csIInterventoPr.getCsIInterventoPrFse().setCittadinanza(prEsistente.getCsIInterventoPrFse().getCittadinanza());
							
							if(prEsistente.getCsIInterventoPrFse().getComuneDomicilio() != null){
								this.csIInterventoPr.getCsIInterventoPrFse().setComuneDomicilio(prEsistente.getCsIInterventoPrFse().getComuneDomicilio());
								ObjectMapper mapper = new ObjectMapper();
								domicilioComuneMan = new ComuneResidenzaMan();
								domicilioComuneMan.setComune(mapper.readValue(prEsistente.getCsIInterventoPrFse().getComuneDomicilio(), ComuneBean.class));
							 
								
							}
							if(prEsistente.getCsIInterventoPrFse().getComuneNascita()  != null){
								this.csIInterventoPr.getCsIInterventoPrFse().setComuneNascita(prEsistente.getCsIInterventoPrFse().getComuneNascita());
								 comuneNazioneNascitaBean = new ComuneNazioneNascitaMan();
								
								ObjectMapper mapper = new ObjectMapper();
								this.comuneNazioneNascitaBean
								.getComuneNascitaMan().setComune(mapper.readValue(prEsistente.getCsIInterventoPrFse().getComuneNascita(), ComuneBean.class));
							 
								
							}
							if(prEsistente.getCsIInterventoPrFse().getStatoNascitaCod()   != null){
								this.csIInterventoPr.getCsIInterventoPrFse().setStatoNascitaCod(prEsistente.getCsIInterventoPrFse().getStatoNascitaCod());
								this.csIInterventoPr.getCsIInterventoPrFse().setStatoNascitaDes(prEsistente.getCsIInterventoPrFse().getStatoNascitaDes());
								
								 comuneNazioneNascitaBean = new ComuneNazioneNascitaMan();
								
								 AmTabNazioni amTabNazioni = 
										 CsUiCompBaseBean.getNazioneByIstat(prEsistente.getCsIInterventoPrFse().getStatoNascitaCod(), prEsistente.getCsIInterventoPrFse().getStatoNascitaDes());
									this.comuneNazioneNascitaBean.setNazioneValue();
									this.comuneNazioneNascitaBean.getNazioneMan().setNazione(amTabNazioni);
								
							}
//										if(prEsistente.getCsIInterventoPrFse().getComuneResidenza() != null && (this.residenzaComuneMan == null || residenzaComuneMan.getComune() == null)){
//											this.csIInterventoPr.getCsIInterventoPrFse().setComuneResidenza(prEsistente.getCsIInterventoPrFse().getComuneResidenza());
//											ObjectMapper mapper = new ObjectMapper();
//											residenzaComuneMan = new ComuneResidenzaMan();
//											residenzaComuneMan.setComune(mapper.readValue(prEsistente.getCsIInterventoPrFse().getComuneResidenza(), ComuneBean.class));
//											
//										}
							this.csIInterventoPr.getCsIInterventoPrFse().setComunicaVul(prEsistente.getCsIInterventoPrFse().getComunicaVul());
							this.csIInterventoPr.getCsIInterventoPrFse().setCsTbGrVulnerabile(prEsistente.getCsIInterventoPrFse().getCsTbGrVulnerabile());
							this.csIInterventoPr.getCsIInterventoPrFse().setEmail(prEsistente.getCsIInterventoPrFse().getEmail());
							this.csIInterventoPr.getCsIInterventoPrFse().setFlagAltroCorso(prEsistente.getCsIInterventoPrFse().getFlagAltroCorso() );
							this.csIInterventoPr.getCsIInterventoPrFse().setFlagResDom(prEsistente.getCsIInterventoPrFse().getFlagResDom());
							this.csIInterventoPr.getCsIInterventoPrFse().setIban(prEsistente.getCsIInterventoPrFse().getIban());
							this.csIInterventoPr.getCsIInterventoPrFse().setLavoroDescOrario(prEsistente.getCsIInterventoPrFse().getLavoroDescOrario());
							this.csIInterventoPr.getCsIInterventoPrFse().setLavoroDescTipo(prEsistente.getCsIInterventoPrFse().getLavoroDescTipo() );
							this.csIInterventoPr.getCsIInterventoPrFse().setLavoroDurataRicerca(prEsistente.getCsIInterventoPrFse().getLavoroDurataRicerca());
							this.csIInterventoPr.getCsIInterventoPrFse().setTelefono(prEsistente.getCsIInterventoPrFse().getTelefono() );
							this.csIInterventoPr.getCsIInterventoPrFse().setTitoloStudioAnno(prEsistente.getCsIInterventoPrFse().getTitoloStudioAnno());
							this.csIInterventoPr.getCsIInterventoPrFse().setViaDomicilio(prEsistente.getCsIInterventoPrFse().getViaDomicilio() );
							
							//this.csIInterventoPr.getCsIInterventoPrFse().setViaResidenza(   prEsistente.getCsIInterventoPrFse().getViaResidenza());
							//this.csIInterventoPr.getCsIInterventoPrFse().setViaResidenza(   this.csIInterventoEsegMastSogg.getViaResidenza());
							allineamentoEseg = true;
							
						}
								
						numFSE++;
					    this.renderAttivita = true;
							 
					}
				}
			}catch(Exception ex){
				addErrorFromProperties("caricamento.error");
				logger.error(ex.getMessage(), ex);
			}
			
 	
		 	if(numFSE == 0){
		 		
		 		//this.csIInterventoPr.setCsIInterventoPrFse(new CsIInterventoPrFse());
		 		 this.resetDatiFSE();
		 		//this.residenzaComuneMan = new ComuneResidenzaMan();
	 			this.domicilioComuneMan = new ComuneResidenzaMan();
	 			this.comuneMan = new ComuneGenericMan("Sede aziendale");
	 			if(this.comuneNazioneNascitaBean == null){
	 				this.comuneNazioneNascitaBean = new ComuneNazioneNascitaMan();
	 			}
	 			
		 		if(!StringUtils.isEmpty(this.csIInterventoPr.getnSottocorsoAttivita()))
		 			 renderAttivita = true;
		 		else
		 			 renderAttivita = false;	
		 	}
	 	}else{
	 		this.csIInterventoPr.setCsIInterventoPrFse(null);
			//residenzaComuneMan = null;
			//domicilioComuneMan = null;
			renderAttivita = false;
			
	 	}
	 	if(renderAttivita && this.isModuloPorMarche()){
	 		this.stampaPor = true;
	 	}
	}

	//FINE SISO-972

	public String getCodiceForm() {
		return codiceForm;
	}

	public void setCodiceForm(String codiceForm) {
		this.codiceForm = codiceForm;
	}
	
	public List<SelectItem> getLstGruppoVulnerabile() {
		if(lstGruppoVulnerabile == null){
			lstGruppoVulnerabile = new ArrayList<SelectItem>();
			lstGruppoVulnerabile.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbGVulnerabile> lst = confService.getGruppiVulnerab(bo);
			if (lst != null) {
				for (CsTbGVulnerabile p : lst) {
					SelectItem fa = new SelectItem(p.getId(), p.getDescrizione());
					fa.setDisabled(!"1".equals(p.getAbilitato()));
					lstGruppoVulnerabile.add(fa);
				}
			}		
		}
		return lstGruppoVulnerabile;
	}
	
	public List<SelectItem> getLstFormeGiuridiche() {
		if(lstFormeGiuridiche == null){
			lstFormeGiuridiche = new ArrayList<SelectItem>();
			lstFormeGiuridiche.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbFormaGiuridica> lst = confService.getFormeGiuridiche(bo);
			if (lst != null) {
				for (CsTbFormaGiuridica p : lst) {
					SelectItem fa = new SelectItem(p.getId(), p.getDescrizione());
					fa.setDisabled(!p.getAbilitato());
					lstFormeGiuridiche.add(fa);
				}
			}		
		}
		return lstFormeGiuridiche;
	}
	
	public boolean isRenderFSE(){
		return "FSE".equalsIgnoreCase(this.codiceForm);
	}
	//SISO-972
	public boolean isRenderFSEAttivita(){
		return renderAttivita;
	}
	//SISO-972 fine
	public void onChangeIngMercato(){
		if(this.isRenderFSE()){
			if(this.csIInterventoPr.getCsIInterventoPrFse()==null) 
				this.csIInterventoPr.setCsIInterventoPrFse(new CsIInterventoPrFse());
			
//			if(this.isOccupato()){
//				this.comuneMan = new ComuneGenericMan("Sede aziendale");
//			}else resetDatiInMercato02();
			//SISO-FIX
			if(!this.isOccupato()){
				resetDatiInMercato02();
			}
			//SISO-FIX
			if(!this.isRicercaPrimaOccupazione() && !this.isDisoccupato())
				this.csIInterventoPr.getCsIInterventoPrFse().setLavoroDurataRicerca(null);

			if(!this.isInattivo())
				this.csIInterventoPr.getCsIInterventoPrFse().setFlagAltroCorso(null);
		}
	}
	
	private void resetDatiInMercato02(){
		if(this.csIInterventoPr.getCsIInterventoPrFse()!=null){
			this.csIInterventoPr.getCsIInterventoPrFse().setLavoroDescTipo(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setLavoroDescOrario(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzRagioneSociale(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzPIVA(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzCF(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzVia(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneCod(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneDes(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzProv(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzFormaGiuridica(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzDescDimensioni(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzCodAteco(null);
			
			//this.comuneMan = null;
			this.comuneMan = new ComuneGenericMan("Sede aziendale");
		 
		}
	}
	private void resetDatiFSE(){
		if(this.csIInterventoPr.getCsIInterventoPrFse()!=null){
			this.csIInterventoPr.getCsIInterventoPrFse().setLavoroDescTipo(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setLavoroDescOrario(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzRagioneSociale(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzPIVA(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzCF(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzVia(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneCod(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneDes(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzProv(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzFormaGiuridica(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzDescDimensioni(null);
			this.csIInterventoPr.getCsIInterventoPrFse().setAzCodAteco(null);
			
			this.comuneMan = new ComuneGenericMan("Sede aziendale");
		}
	}
	public boolean isDurataRicercaLavoro(){
	 if(isRicercaPrimaOccupazione())
		 return true;
	 if(isDisoccupato())
		 return true;
	 return false;
	}
	
	public boolean isPensionato(){
		boolean val = this.csIInterventoPr.getCsTbCondLavoro()!=null &&
				"PENSIONATO".equalsIgnoreCase(this.csIInterventoPr.getCsTbCondLavoro().getDescrizione());
		return val;
	}
	
	public boolean isOccupato(){
		boolean val = this.csIInterventoPr.getCsTbIngMercato()!=null && !this.isModuloPorMarche() &&
				DataModelCostanti.TipiIngMercato.OCCUPATO.equalsIgnoreCase(this.csIInterventoPr.getCsTbIngMercato().getId());
		return val;
	}
	
	public boolean isInattivo(){
		boolean val =  this.csIInterventoPr.getCsTbIngMercato()!=null && 
				DataModelCostanti.TipiIngMercato.INATTIVO.equalsIgnoreCase(this.csIInterventoPr.getCsTbIngMercato().getId());
		return val;
	}
	
	public boolean isDisoccupato(){
		boolean val =  this.csIInterventoPr.getCsTbIngMercato()!=null && 
				DataModelCostanti.TipiIngMercato.DISOCCUPATO.equalsIgnoreCase(this.csIInterventoPr.getCsTbIngMercato().getId());
		return val;
	}
	public boolean isRicercaPrimaOccupazione(){
		boolean val =  this.csIInterventoPr.getCsTbIngMercato()!=null && 
				DataModelCostanti.TipiIngMercato.CERCA_PRIMA_OCCUPAZIONE.equalsIgnoreCase(this.csIInterventoPr.getCsTbIngMercato().getId());
		return val;
	}
	
	public ComuneBean getComune(String id){
		try {
			AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
			AmTabComuni comune = bean.getComuneByIstat(id);
			if(comune!=null)
				return new ComuneBean(comune.getCodIstatComune(),comune.getDenominazione(), comune.getSiglaProv());
			
		} catch (NamingException e) {
			logger.error(e);
		}
		return null;
	}

	public ComuneGenericMan getComuneMan() {
		return comuneMan;
	}

	public void setComuneMan(ComuneGenericMan comuneMan) {
		this.comuneMan = comuneMan;
	}
	
	public boolean isLavoroFromDatiSociali() {
		return lavoroFromDatiSociali;
	}

	public void setLavoroFromDatiSociali(boolean lavoroFromDatiSociali) {
		this.lavoroFromDatiSociali = lavoroFromDatiSociali;
	}

	public boolean isStudioFromDatiSociali() {
		return studioFromDatiSociali;
	}

	public void setStudioFromDatiSociali(boolean studioFromDatiSociali) {
		this.studioFromDatiSociali = studioFromDatiSociali;
	}



	public ComuneResidenzaMan getDomicilioComuneMan() {
		return domicilioComuneMan;
	}

	public void setDomicilioComuneMan(ComuneResidenzaMan domicilioComuneMan) {
		this.domicilioComuneMan = domicilioComuneMan;
	}

	public void addMessage(FacesMessage.Severity tipoMessaggio,String summary) {
        FacesMessage message = new FacesMessage(tipoMessaggio, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public boolean validaDatiProgetto(SoggettoErogazioneBean soggettoErogazione) {
		boolean res = true;
		
		/*Valorizzo i campi PR con i dati finali, altrimenti la validazione SIRU non funziona correttamente*/
		if(this.csIInterventoPr.getCsTbIngMercato()!=null && this.getCsIInterventoPr().getCsTbIngMercato().getId()==null)
			this.csIInterventoPr.setCsTbIngMercato(null);
		
		if(this.csIInterventoPr.getCsTbTitoloStudio()!=null && this.getCsIInterventoPr().getCsTbTitoloStudio().getId()<=0)
			this.csIInterventoPr.setCsTbTitoloStudio(null);
		/*END*/
		
		if(this.csIInterventoPr.getFfProgettoDescrizione()==null || this.csIInterventoPr.getFfProgettoDescrizione().isEmpty()){
			addErrorCampiObbligatori("Progetti", "Progetto");
			res = false;
		}

		/* == SISO-663 SM == */
		if (csIInterventoPr.getSettoreTitolare().getId() == null) {
			addErrorCampiObbligatori("Progetti", "Settore titolare");
			res = false;
		}
		//SISO-1136 Commentata la gestione del settore gestore
		if (csIInterventoPr.getSettoreGestore().getId() == null)
			this.csIInterventoPr.setSettoreGestore(null);
//		if (csIInterventoPr.getSettoreGestore().getId() == null) {
//			addErrorCampiObbligatori("Progetti", "Settore gestore");
//			res = false;
//		}
		
		if(this.isFonteFinanziamentoObbligatorio() && (csIInterventoPr.getFfOrigineId()==null || csIInterventoPr.getFfOrigineId()==0)){
			addErrorCampiObbligatori("Progetti", "Fonte finanziamento");
			res=false;
		}
		
		
		if(this.isRenderFSE()){ 
			
			/*Valorizzo i campi finali con quelli temporanei della classe, altimenti nel metodo validaSiru trovo tutto a NULL*/
			popolaLuogoDiNascitaFse();
			
			//Validazione campi
			if(csIInterventoPr.getnSottocorsoAttivita().isEmpty() ) {
				addErrorCampiObbligatori("Progetti", "n°sottocorso/attività");
				res = false;
			}
			//SISO-846
			
			if(this.isRenderFSEAttivita()){
				ObjectMapper mapper = new ObjectMapper();
				ComuneBean comuneDomicilio = this.domicilioComuneMan.getComune();
				//this.csIInterventoPr.getCsIInterventoPrFse().setComuneResidenza(mapper.writeValueAsString(rc));
				try{
					this.csIInterventoPr.getCsIInterventoPrFse().setComuneDomicilio(mapper.writeValueAsString(comuneDomicilio));
				} catch (JsonProcessingException e) {
					logger.error(e);
				}
					
				if(this.getCsIInterventoPr().getCsIInterventoPrFse().getComunicaVul() == null){
					addError("Progetti","Selezionare se l'utente comunica o meno la condizione di vulnerabilità");
					res = false;
				}else if(this.getCsIInterventoPr().getCsIInterventoPrFse().getComunicaVul() && 
						(this.getCsIInterventoPr().getCsIInterventoPrFse().getCsTbGrVulnerabile() == null ||
						 this.getCsIInterventoPr().getCsIInterventoPrFse().getCsTbGrVulnerabile().getId() == null)){
					addError("Progetti","indicare la condizione di vulnerabilità");
					res = false;
				}else if(!this.getCsIInterventoPr().getCsIInterventoPrFse().getComunicaVul() && 
						 this.getCsIInterventoPr().getCsIInterventoPrFse().getCsTbGrVulnerabile() != null &&
						 this.getCsIInterventoPr().getCsIInterventoPrFse().getCsTbGrVulnerabile().getId()!=null){
					addError("Progetti","Valori incompatibili: se l'utente non intende comunicare la condizione di vulnerabilità, non deve essere espresso alcun valore.");
					res = false;
				}
			
				if(this.isOccupato()){
					ComuneBean azComune = comuneMan.getComune();
					this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneCod(azComune!=null ? azComune.getCodIstatComune() : null);
					this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneDes(azComune!=null ? azComune.getDenominazione(): null);
					this.csIInterventoPr.getCsIInterventoPrFse().setAzProv(azComune!=null ? azComune.getSiglaProv(): null);
				}
				
				//SISO-962 Inizio
				if( csIInterventoPr.getCsIInterventoPrFse() != null)
				{
					if(soggettoErogazione.getCodiceNazioneResidenzaEstero() != null){
						addError("Progetti","Attenzione: Per i progetti di tipo FSE è obbligatoria la residenza in Italia.");
						res = false;
					}
				}
				
				if((csIInterventoPr.getCsIInterventoPrFse().getFlagResDom()!=null && ProgettoErogazioni.FLAG_RES_DOM.DOMICILIO.getCodice().equalsIgnoreCase(csIInterventoPr.getCsIInterventoPrFse().getFlagResDom())) && 
					(csIInterventoPr.getCsIInterventoPrFse().getViaDomicilio() == null ||
					 csIInterventoPr.getCsIInterventoPrFse().getViaDomicilio().isEmpty() ||
				     this.getDomicilioComuneMan().comune==null)){
					addError("Progetti","Attenzione: è obbligatorio valorizzare via e comune di domicilio");
					res = false;
				}
				
				//VALIDAZIONE SIRU
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(csIInterventoPr);
				dto.setObj2(soggettoErogazione);
				SiruResultDTO validazione =interventoService.validaSiru(dto);
				
				if(validazione!= null && !validazione.getErrori().isEmpty()){
					addWarning("Validazione campi Progetto", validazione.getErrori());
					res = false;
				}else
					csIInterventoPr.setCsIInterventoPrFseSiru(validazione.getSiru());
				
				//SISO-996
				if(isControlloResDomPOR()){
					/*Verifico che la regione del settore titolare sia diversa da quella di residenza o domicilio*/
					boolean stessaRegione = true;
					
					String belfiore = getSettoreTitolare().getCsOOrganizzazione().getCodCatastale();
					AmTabComuni comuneTitolare = luoghiService.getComuneItaByBelfiore(belfiore);
					String regioneTitolare = comuneTitolare!=null ? comuneTitolare.getCodIstatRegione() : null;
					if(StringUtils.isEmpty(regioneTitolare)){
						/*Potrebbe essere un'organizzazione con cod.fiscale (es.comunità montana)
						 * recupero la zona sociale corrente
						 * */
						AccessTableOperatoreSessionBeanRemote opService =
						(AccessTableOperatoreSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
						CsOZonaSoc zona = opService.findZonaSocAbilitata(dto);
						if(zona!=null && StringUtils.isEmpty(zona.getCodIstatRegione()))
							regioneTitolare = zona.getCodIstatRegione();
						else
							addMessage(FacesMessage.SEVERITY_ERROR,"Errore di configurazione: cod.istat regione non impostato per la zona sociale corrente. Contattare l'assistenza.");
						
				
					}
					
					AmTabComuni comuneDom = null;
					AmTabComuni comuneRes = null;
					
					ComuneBean comuneResidenza = null;
					if(soggettoErogazione.getJsonComuneResidenza()!=null){
						try{
							comuneResidenza = mapper.readValue(soggettoErogazione.getJsonComuneResidenza(), ComuneBean.class);
						} catch (IOException e) {
							logger.error(e);
						}
					}
				
					if(comuneResidenza != null){
						 comuneRes = luoghiService.getComuneItaByIstat(comuneResidenza.getCodIstatComune());
						 stessaRegione = regioneTitolare.equals(comuneRes.getCodIstatRegione());
					}	
					if(comuneDomicilio!=null && comuneDomicilio.getCodIstatComune()!=null && !stessaRegione){
						 comuneDom = luoghiService.getComuneItaByIstat(comuneDomicilio.getCodIstatComune());
						 stessaRegione = regioneTitolare.equals(comuneDom.getCodIstatRegione());
					}	
					if(!stessaRegione){
						addMessage(FacesMessage.SEVERITY_ERROR,"Per progetti finanziati su fondi POR, la regione di residenza o domicilio del beneficiario deve essere uguale alla regione del Comune Titolare");
						res = false;
					}
					
				}
			}
		}
		//SISO-1131
		if (abilitaMenuProgettiAltro) {
			if (this.selectedProgettoAltro != null ) {
				if (this.selectedProgettoAltro.getDescrizione().isEmpty()){
					addError("Progetti",
							"Attenzione: è obbligatorio specificare il progetto Altro");
			    	res = false;
				}
			
			} else {
				addError("Progetti",
						"Attenzione: è obbligatorio specificare il progetto Altro");
				res = false;
			}
		}
		//
		
		/* --===-- */
		return res;
		
	}
	
	public void setIdSettoreGestoreComeIdSettoreTitolare() {
		setSelSettoreGestoreId(getSelSettoreTitolareId());
	}
	
	public DatiProgettoBeanTooltipText getTooltipText() {
		return tooltipText;
	}

	public List<CsTbIngMercato> getLstIngMercatoTooltip() {
		return lstIngMercatoTooltip;
	}

	public void setLstIngMercatoTooltip(List<CsTbIngMercato> lstIngMercatoTooltip) {
		this.lstIngMercatoTooltip = lstIngMercatoTooltip;
	}

	//SISO-850
	public List<SelectItem> getLstAteco() {
	        if(lstAteco == null){
			lstAteco = new ArrayList<SelectItem>();
			List<SiruDominioDTO> lsAteco= dominiSiruService.findAll(SiruEnum.SISO_ATECO.toString());
			lsAteco= dominiSiruService.findAll(SiruEnum.SISO_ATECO.toString());
			
			if (lsAteco != null) {
				for (SiruDominioDTO obj : lsAteco) {
					lstAteco.add(new SelectItem(obj.getCodice(), obj.getCodice()+ " "+obj.getDescrizione()));
				}
			}		
		}
		
		return lstAteco;
	}
	//SISO-850

	public boolean isDatiFromCartella() {
		return datiFromCartella;
	}

	public void setDatiFromCartella(boolean datiFromCartella) {
		this.datiFromCartella = datiFromCartella;
	}
	
	public List<SelectItem> getLstFlagResDom(){
		List<SelectItem> lst = new ArrayList<SelectItem>();
		for(FLAG_RES_DOM s : DataModelCostanti.ProgettoErogazioni.FLAG_RES_DOM.values())
				lst.add(new SelectItem(s.getCodice(),s.getDescrizione()));
		return lst;
	}
	
	public boolean isStampaPor() {//RIGA1503
		return stampaPor;
	}

	public void setStampaPor(boolean stampaPor) {
		this.stampaPor = stampaPor;
	}

	public void onChangeFlagVulnerabilita(){
		if(this.csIInterventoPr.getCsIInterventoPrFse().getComunicaVul()){
			if(this.csIInterventoPr.getCsIInterventoPrFse().getCsTbGrVulnerabile()==null)
			   this.csIInterventoPr.getCsIInterventoPrFse().setCsTbGrVulnerabile(new CsTbGVulnerabile());
		}else
			this.csIInterventoPr.getCsIInterventoPrFse().setCsTbGrVulnerabile(null);
	}

	private void aggiornaDatiDaCartella(){
		if(datiSociali!=null){
			//valorizzaLavoroDatiSociali(datiSociali);  //caricati in modo esplicito tramite pulsante SISO-1090
			
			CsASoggettoLAZY s = datiSociali.getCsASoggetto();
			
			//SISO-945
		    CsAAnagrafica csaAnagrafica =  s.getCsAAnagrafica();
		    comuneNazioneNascitaBean = new ComuneNazioneNascitaMan();
		    
		    if(StringUtils.isBlank(this.csIInterventoPr.getCsIInterventoPrFse().getComuneNascita())){
		    	//siso - 945
				 
			    	if (csaAnagrafica.getComuneNascitaCod() != null) {
						ComuneBean comuneBean = getComuneBean(
								csaAnagrafica.getComuneNascitaCod(),
								csaAnagrafica.getComuneNascitaDes(),
								csaAnagrafica.getProvNascitaCod());
						this.comuneNazioneNascitaBean.getComuneNascitaMan().setComune(comuneBean);
					}else{
						AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(csaAnagrafica.getStatoNascitaCod(),csaAnagrafica.getStatoNascitaDes());
						this.comuneNazioneNascitaBean.setNazioneValue();
						this.comuneNazioneNascitaBean.getNazioneMan().setNazione(amTabNazioni);
					}
					 
				//siso - 945 fine
			    this.datiFromCartella = true;
		    }
		    //SISO-945 FINE
		
		    //SISO-846
			if(StringUtils.isBlank(this.csIInterventoPr.getCsIInterventoPrFse().getCellulare())){
				this.csIInterventoPr.getCsIInterventoPrFse().setCellulare(s.getCsAAnagrafica().getCell());
				this.datiFromCartella = true;
			}if(StringUtils.isBlank(this.csIInterventoPr.getCsIInterventoPrFse().getTelefono())){
				this.csIInterventoPr.getCsIInterventoPrFse().setTelefono(s.getCsAAnagrafica().getTel());
				this.datiFromCartella = true;
			}if(StringUtils.isBlank(this.csIInterventoPr.getCsIInterventoPrFse().getEmail())){
				this.csIInterventoPr.getCsIInterventoPrFse().setEmail(s.getCsAAnagrafica().getEmail());
				this.datiFromCartella = true;
			}
			
			//recupero gruppi vulnerabili da familiari
			if(this.csIInterventoPr.getCsIInterventoPrFse().getCsTbGrVulnerabile()==null){
				IFamConviventi fam;
				try {
					fam = FamiliariManBaseBean.initByModel(datiSociali.getFamiliariConviventi());
					this.csIInterventoPr.getCsIInterventoPrFse().setCsTbGrVulnerabile(fam.getGruppoVulnerabile()); 
					this.datiFromCartella=true;
				} catch (Exception e) {
					logger.error("Errore recuper scheda familiari conviventi per DATI SOCIALI["+datiSociali.getId()+"]");
				}
			}
			
			
			//Recupero dalla cartella indirizzo e domicilio
			if(//this.csIInterventoPr.getCsIInterventoPrFse().getViaResidenza()==null || 
			this.csIInterventoPr.getCsIInterventoPrFse().getViaDomicilio()==null ||
			//this.csIInterventoPr.getCsIInterventoPrFse().getComuneResidenza()==null ||
			this.csIInterventoPr.getCsIInterventoPrFse().getComuneDomicilio()==null){
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(s.getAnagraficaId());
				List<CsAIndirizzo> listaIndirizzi = indirizzoService.getIndirizziBySoggetto(dto);
				int i = 0;
				boolean domicilioTrovato = false;
				boolean residenzaTrovata = false;
				while((!domicilioTrovato || !residenzaTrovata) && i<listaIndirizzi.size()){
					CsAIndirizzo ind = listaIndirizzi.get(i);
	/*					if(DataModelCostanti.TipoIndirizzo.RESIDENZA.equalsIgnoreCase(ind.getCsTbTipoIndirizzo().getDescrizione()) 
							&& (ind.getDataFineApp()==null || ind.getDataFineApp().after(new Date()))){
						residenzaTrovata = true;
						if(this.csIInterventoPr.getCsIInterventoPrFse().getViaResidenza()==null){
							this.csIInterventoPr.getCsIInterventoPrFse().setViaResidenza(ind.getCsAAnaIndirizzo().getLabelIndirizzo());
							this.datiFromCartella = true;
						}
						if(this.residenzaComuneMan.getComune()==null){
							ComuneBean cbean = new ComuneBean(ind.getCsAAnaIndirizzo().getComCod(), ind.getCsAAnaIndirizzo().getComDes(), ind.getCsAAnaIndirizzo().getProv());
							this.residenzaComuneMan.setComune(cbean);
							this.datiFromCartella = true;
						}
					}else */
						if(DataModelCostanti.TipoIndirizzo.DOMICILIO.equalsIgnoreCase(ind.getCsTbTipoIndirizzo().getDescrizione()) 
							&& (ind.getDataFineApp()==null || ind.getDataFineApp().after(new Date()))){
						domicilioTrovato = true;
						if(this.csIInterventoPr.getCsIInterventoPrFse().getViaDomicilio()==null){
							this.csIInterventoPr.getCsIInterventoPrFse().setViaDomicilio(ind.getCsAAnaIndirizzo().getLabelIndirizzo());
							this.datiFromCartella = true;
						}if(this.domicilioComuneMan.getComune()==null){
							ComuneBean cbean = getComuneBean(ind.getCsAAnaIndirizzo().getComCod(), ind.getCsAAnaIndirizzo().getComDes(), ind.getCsAAnaIndirizzo().getProv());
							this.domicilioComuneMan.setComune(cbean);
							this.datiFromCartella = true;
						}
					}
					i++;
				}
			}
		}
	}
	
	
	public void onChangeBeneficiarioRiferimento(SoggettoErogazioneBean soggettoErogazione,ComuneNazioneNascitaMan comuneNazioneNascitaMan) {
		this.comuneNazioneNascitaBean=comuneNazioneNascitaMan;
		loadDatiSociali(soggettoErogazione);
		
		//Resetto i dati provenienti dalla cartella poichè è cambiato il soggetto di riferimento per il quale erano stati configurati
		if(datiSociali!=null){
			if(this.csIInterventoPr!=null && this.csIInterventoPr.getCsIInterventoPrFse()!=null) {
				this.csIInterventoPr.getCsIInterventoPrFse().setTelefono(null);
				this.csIInterventoPr.getCsIInterventoPrFse().setEmail(null);
				this.csIInterventoPr.getCsIInterventoPrFse().setViaDomicilio(null);
				this.csIInterventoPr.getCsIInterventoPrFse().setComuneDomicilio(null);
				this.csIInterventoPr.getCsIInterventoPrFse().setComuneNascita(null);
				this.csIInterventoPr.getCsIInterventoPrFse().setStatoNascitaCod(null);
				this.csIInterventoPr.getCsIInterventoPrFse().setStatoNascitaDes(null);
				this.csIInterventoPr.getCsIInterventoPrFse().setCsTbGrVulnerabile(null);
			}
			valorizzaLavoroDatiSociali();
		}
		
	}
	
	public Boolean getServizioGestibileComeAmbito() {
		boolean servizioGestibileComeAmbito = false;
		CsOSettore settoreTitolare = getSettoreTitolare();
		if (settoreTitolare != null && settoreTitolare.getCsOOrganizzazione()!=null) {
			CsOOrganizzazione enteDelSettore = settoreTitolare.getCsOOrganizzazione();
			servizioGestibileComeAmbito = enteDelSettore.getFlagCapofila();
		}
		return servizioGestibileComeAmbito;
	}

	public void setServizioGestitoComeAmbito(boolean b) {
		this.csIInterventoPr.setServizioAmbito(b);
	}

	public boolean getServizioGestitoComeAmbito() {
		return this.csIInterventoPr.getServizioAmbito();
	}

}