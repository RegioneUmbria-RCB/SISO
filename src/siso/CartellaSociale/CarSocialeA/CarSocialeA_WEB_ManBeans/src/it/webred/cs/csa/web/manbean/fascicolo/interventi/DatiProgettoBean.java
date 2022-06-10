package it.webred.cs.csa.web.manbean.fascicolo.interventi;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArConfigurazioneService;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArAttivitaDTO;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneEnteSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.cartella.DatiSocialiSintesiDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruDominioDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruResultDTO;
import it.webred.cs.csa.ejb.dto.siru.StampaFseDTO;
import it.webred.cs.csa.ejb.enumeratori.SiruEnum;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.ProgettoErogazioni;
import it.webred.cs.data.DataModelCostanti.ProgettoErogazioni.FLAG_RES_DOM;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIInterventoPrFse;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbDurataRicLavoro;
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
import it.webred.jsf.bean.ComuneBean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatiProgettoBean extends CsUiCompBaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb ("AccessTableInterventoSessionBean");
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb("AccessTableSchedaSessionBean");
	protected AccessTableDominiSiruSessionBeanRemote  dominiSiruService= (AccessTableDominiSiruSessionBeanRemote)getCarSocialeEjb("AccessTableDominiSiruSessionBean");
	
	private Long idTipoIntervento;
	private Long idTipoIntrCustom;
	private Long idCatSociale;
	
	private DatiSocialiSintesiDTO datiSociali;
	
	//SISO-972 
	private boolean renderAttivita = false;
	
	protected CsCTipoIntervento tipoIntervento;
	protected CsCTipoInterventoCustom tipoIntCustom;
	
	private List<SelectItem> listaProgetti;  //SISO-522
	private CsIInterventoPr csIInterventoPr;
	private String codiceForm;
	
	private List<SelectItem> listaSottocorsi=new ArrayList<SelectItem>(); 
	
	
	//SISO-790
	
	//SISO-1131
	private CsTbProgettoAltro selectedProgettoAltro;	
	private boolean abilitaMenuProgettiAltro = false;
	
	protected boolean solaLettura = false;
	private boolean stampaPor = false;
	
	private ComuneGenericMan comuneMan; //Sede Aziendale
	private ComuneResidenzaMan domicilioComuneMan;
	private ComuneNazioneNascitaMan comuneNazioneNascitaBean = null;
	
	//SISO-972
	private int numFSE = 0;	
	
	private List<SelectItem> listaSettoriGestGroup;
	
	private List<VLineaFin>  listaOrigineFin= new ArrayList<VLineaFin>();
	private List<SelectItem> lstConLavorativa;
	private List<SelectItem> lstTitoliStudio;
	private List<SelectItem> lstIngMercato;
	private List<SelectItem> lstSettoreImpiego;
	private List<CsTbIngMercato> lstIngMercatoTooltip;

	private List<SelectItem> lstDurataRicLavoro;
	private List<SelectItem> lstGruppoVulnerabile;
	private List<SelectItem> lstFormeGiuridiche;
	
	private List<SelectItem> lstAteco; //SISO-850
	
	private boolean lavoroFromDatiSociali=false;
	private boolean studioFromDatiSociali=false;
	private boolean datiFromCartella = false;
	
	
	
 
	//SISO-1131
	private String arFfProgettoAltro;
	//FINE SISO-1131
	private final DatiProgettoBeanTooltipText tooltipText = new DatiProgettoBeanTooltipText();
	private ConfigurazioneFseDTO mappaCampiFse;
	
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

	public void setComuneNazioneNascitaBean(ComuneNazioneNascitaMan comuneNazioneNascitaBean) {
		this.comuneNazioneNascitaBean = comuneNazioneNascitaBean;
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
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<KeyValueDTO> lst = confService.getTitoliStudio(bo);
			lstTitoliStudio = convertiLista(lst);
		}
		
		return lstTitoliStudio;
	}

	

	public void setLstTitoliStudio(List<SelectItem> lstTitoliStudio) {
		this.lstTitoliStudio = lstTitoliStudio;
	}

	//inizio SISO-790
		public List<SelectItem> getListaSottocorsi() {
			return listaSottocorsi;
		}

		public void setListaSottocorsi(List<SelectItem> listaSottocorsi) {
			this.listaSottocorsi = listaSottocorsi;
		}

		//fine SISO-790

	public void loadDatiProgetto(CsIInterventoPr progetto, boolean solalettura, Long tipoIntId, Long tipoInCustomId, Long catSocialeID, String beneficiarioCF, String valSoggettoAttuatore) {
		if(progetto!=null){
			 this.loadDatiProgetto(progetto, solalettura, tipoIntId, tipoInCustomId, catSocialeID, valSoggettoAttuatore );
			 this.loadDatiSociali(beneficiarioCF);
			 this.onChangeAttivita(beneficiarioCF);
		}
	}	
	//SISO-972 fine

	private void loadDatiProgetto(CsIInterventoPr progetto, boolean solalettura, Long tipoIntId, Long tipoInCustomId, Long catSocialeID, String valSoggettoAttuatore) {
		
		this.csIInterventoPr = progetto;
		
		if(!this.getServizioGestibileComeAmbito()) 
			csIInterventoPr.setServizioAmbito(false);
		
		this.solaLettura = solalettura;
		
		this.idTipoIntervento=tipoIntId;
		this.idTipoIntrCustom=tipoInCustomId;
		this.idCatSociale = catSocialeID;
		
		loadListaOrigineFinanziamenti(csIInterventoPr.getSettoreTitolare());
		
		if(this.isOccupato() && this.mappaCampiFse!=null && this.mappaCampiFse.getAzComune().isAbilitato()){
			this.comuneMan = new ComuneGenericMan("Sede aziendale");
			if(this.csIInterventoPr.getCsIInterventoPrFse()!=null)
				this.comuneMan.setComune(this.getComune(this.csIInterventoPr.getCsIInterventoPrFse().getAzComuneCod()));
		}
		
		stampaPor = false;
		
		//this.codiceForm = null;
		
		//SISO_1131
		
		this.setSelectedProgettoAltro(this.csIInterventoPr.getCsTbProgettoAltro()!=null? this.csIInterventoPr.getCsTbProgettoAltro(): new CsTbProgettoAltro());
		
		if(this.csIInterventoPr.getCsIInterventoPrFse()!=null){
			this.codiceForm = "FSE";
			
			boolean regMarche = this.isModuloPorMarche();
			if(regMarche && this.mappaCampiFse.getSoggettoAttuatore().isAbilitato() && !StringUtils.isBlank(valSoggettoAttuatore) && 
			   StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getSoggettoAttuatore())){
				csIInterventoPr.getCsIInterventoPrFse().setSoggettoAttuatore(valSoggettoAttuatore);
				this.addWarning("", "Il campo soggetto attuatore è stato prevalorizzato dal sistema, salvare il dato");
			}

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
				domicilioComuneMan.setComune(mapper.readValue(jsonDomicilio, ComuneBean.class));
				
				ComuneBean nComune = null;
				AmTabNazioni nNazione = null;
				if(jsonNascita != null)
					nComune = mapper.readValue(jsonNascita, ComuneBean.class);
				
				String statoCod = this.getCsIInterventoPr().getCsIInterventoPrFse().getStatoNascitaCod();
				String statoDes = this.getCsIInterventoPr().getCsIInterventoPrFse().getStatoNascitaDes();
				if(statoCod != null && statoDes != null)
					nNazione = CsUiCompBaseBean.getNazioneByIstat(statoCod, statoDes);
				
				comuneNazioneNascitaBean.init(nComune, nNazione);
				 
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
	
	public void loadDatiSociali(String cf){
		datiSociali = null;
		if(isAccessoEsternoDatiCartella()){
			if (!StringUtils.isBlank(cf)) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(cf);
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
			if(datiSociali!=null && datiSociali.getCondLavoro()!=null){
				csIInterventoPr.setCsTbCondLavoro(datiSociali.getCondLavoro());
				lavoroFromDatiSociali = true;
			}else
				csIInterventoPr.setCsTbCondLavoro(datiSociali.getCondLavoroDefault());
		//}
		
		//if(this.csIInterventoPr.getCsTbIngMercato()==null){
			if(this.csIInterventoPr.getCsTbCondLavoro()!=null)
				this.csIInterventoPr.setCsTbIngMercato(csIInterventoPr.getCsTbCondLavoro().getCsTbIngMercato());
			else
				this.csIInterventoPr.setCsTbIngMercato(new CsTbIngMercato());
			this.onChangeIngMercato();
	
			this.csIInterventoPr.setCsTbTitoloStudio(new CsTbTitoloStudio());
			if(datiSociali!=null && datiSociali.getTitoloStudio()!=null){
				this.csIInterventoPr.setCsTbTitoloStudio(datiSociali.getTitoloStudio());
				studioFromDatiSociali=true;
			}else
				this.csIInterventoPr.setCsTbTitoloStudio(datiSociali.getTitoloStudioDefault());
				
			if(datiSociali!=null)
				this.csIInterventoPr.setCsTbSettoreImpiego(datiSociali.getSettImpiego());
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
					if(!StringUtils.isBlank(ingMercato.getDescrizione()))
						lstIngMercatoTooltip.add(ingMercato);
					lstIngMercato.add(new SelectItem(ingMercato.getId(), ingMercato.getDescrizione()));
					
					String labelGroup = ingMercato.getDescrizione();
					SelectItemGroup gr = new SelectItemGroup(labelGroup);
					List<SelectItem> siList = new ArrayList<SelectItem>();
					for (CsTbCondLavoro obj : lst) {
						SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
						boolean valorePresetted = getCondLavoroId()!=null && obj.getId()==getCondLavoroId();
						boolean abilitato = obj.getAbilitato()!=null ? obj.getAbilitato().booleanValue() : Boolean.FALSE;
						if(!abilitato && !valorePresetted)
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
		return csIInterventoPr!=null ? csIInterventoPr : new CsIInterventoPr();
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
	public List<SelectItem> getListaProgetti() {
		return listaProgetti;
	}

	public void setListaProgetti(List<SelectItem> listaProgetti) {
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
		if (csIInterventoPr!=null && csIInterventoPr.getProgetto()!=null){
			String progDescrizione = csIInterventoPr.getProgetto().getDescrizione();
			this.abilitaMenuProgettiAltro = (!StringUtils.isBlank(progDescrizione) &&  progDescrizione.equalsIgnoreCase("ALTRO"));
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
	

	private void loadListaOrigineFinanziamenti(CsOSettore titolare){
			BaseDTO bdto = new BaseDTO();		
			fillEnte(bdto);		
			listaOrigineFin= confService.findAllOrigineFinanziamenti(bdto); 

			listaProgetti = new ArrayList<SelectItem>();
			if (titolare!=null) {  
				bdto.setObj(titolare.getCsOOrganizzazione().getCodRouting());
				bdto.setObj3(this.getIdProgettoSel());
				List<KeyValueDTO> lstArPr = confService.findProgettiByBelfioreOrganizzazione(bdto);
				listaProgetti = convertiLista(lstArPr);
				
				//inizio SISO-790				
				loadListaSottocorsi();
				//fine SISO-790
			}
			
		}
	
	//inizio SISO-790
	public void loadListaSottocorsi(){
		listaSottocorsi.clear();
		ArFfProgetto progetto = this.csIInterventoPr.getProgetto();
		Long idProgettoSel =  progetto!=null ? progetto.getId() : null;
		String progettoDesc = progetto!=null ? progetto.getDescrizione() : null;
		loadCodiceForm(progettoDesc);
		loadMappaCampiFse(idProgettoSel);
		ArConfigurazioneService arConfService = (ArConfigurazioneService) getArgoEjb( "ArConfigurazioneServiceBean");
		List<ArAttivitaDTO> lst = arConfService.getListaAttivita(idProgettoSel);
		for(ArAttivitaDTO k : lst){
			SelectItem si = new SelectItem(k.getId(), k.getDescrizione());
			si.setDisabled(!k.getAbilitato());
			listaSottocorsi.add(si);
		}
	}
		//fine SISO - 790	
	   	
	public List<VLineaFin> getListaOrigineFin() {
			return listaOrigineFin;
		}
	
	public void onChangeOrigineFinanziamento(String cf) {
		
		VLineaFin lineaSelected = null;
		int i = 0;
		while (i<this.listaOrigineFin.size() && lineaSelected==null) {
			VLineaFin linea = this.listaOrigineFin.get(i);
			if (linea.getId().equals(this.getOrigineFinanziamentoId()) )
				lineaSelected = linea;
			i++;
		}
		if (lineaSelected!=null && lineaSelected.getProgettoId()!=null) {
			ArFfProgetto progFin = getProgetto(lineaSelected.getProgettoId().longValue());
			if(progFin!=null){
				this.csIInterventoPr.setProgetto(progFin);
				this.onChangeProgetto(cf);
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
		CsOSettore settore = super.findSettoreById(selSettoreTitolareId);
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
		CsOSettore settore = super.findSettoreById(selSettoreGestoreId);
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
	
	public void onChangeProgetto(String soggettoErogazioneCF){
		loadListaSottocorsi();
		
		ArFfProgetto progetto = this.getCsIInterventoPr().getProgetto();
		if(progetto!=null && !"ALTRO".equalsIgnoreCase(progetto.getDescrizione())){
			this.setSelectedProgettoAltro(new CsTbProgettoAltro());
			this.csIInterventoPr.setCsTbProgettoAltro(new CsTbProgettoAltro());
		}
		
		if(datiSociali==null && !StringUtils.isBlank(soggettoErogazioneCF)) loadDatiSociali(soggettoErogazioneCF);
	
		if(this.isRenderFSE()){
			
			domicilioComuneMan = new ComuneResidenzaMan();
			if(this.csIInterventoPr.getCsIInterventoPrFse()==null) 
				this.csIInterventoPr.setCsIInterventoPrFse(new CsIInterventoPrFse());
				
			//SISO-846
			if(this.mappaCampiFse!=null && this.mappaCampiFse.getPagResDom().isAbilitato()){
				if(this.csIInterventoPr.getCsIInterventoPrFse().getFlagResDom()==null)
					this.csIInterventoPr.getCsIInterventoPrFse().setFlagResDom(ProgettoErogazioni.FLAG_RES_DOM.RESIDENZA.getCodice());
			}else
				this.csIInterventoPr.getCsIInterventoPrFse().setFlagResDom(null);

			aggiornaDatiDaCartella(); //Sovrascrive i valori solo se sono a NULL
			
		}else{
			this.csIInterventoPr.setCsIInterventoPrFse(null);
			//residenzaComuneMan = null;
			domicilioComuneMan = null;
		}
		onChangeAttivita(soggettoErogazioneCF);
	}
	
	private void loadCodiceForm(String progetto){
		codiceForm = null;
		if(!StringUtils.isEmpty(progetto)){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(progetto);
			dto.setObj2(this.idTipoIntervento);
			dto.setObj3(this.idTipoIntrCustom);
			dto.setObj4(this.idCatSociale);
			codiceForm = confService.findCodFormProgetto(dto);
		}
	}
	
	private void loadMappaCampiFse(Long idProgetto){
		mappaCampiFse = null;
		if(this.isRenderFSE() && idProgetto!=null && idProgetto>0){
			CsOSettore titolare = this.csIInterventoPr.getSettoreTitolare();
			if (titolare!=null) {  
				BaseDTO bdto = new BaseDTO();
				fillEnte(bdto);
				bdto.setObj(idProgetto);
				//bdto.setObj2(titolare.getCsOOrganizzazione().getCodRouting());
				mappaCampiFse = confService.loadCampiFse(bdto);
			}
		}
	}
	
	//SISO-972
	
	public void onChangeAttivita(String codiceFiscale){
		numFSE = 0;
	 	if(this.isRenderFSE()){
	 		BaseDTO dto = new BaseDTO();
	 		
			//SISO-972
			/**
			 * Recuperare eventuali FSE precedenti per il soggetto
			 * */
			try {
				boolean allineamentoEseg = false;
				
			   	ArFfProgettoAttivita attivita = this.csIInterventoPr.getProgettoAttivita();
				ArFfProgetto progetto = this.csIInterventoPr.getProgetto();
				if (!StringUtils.isBlank(codiceFiscale) && attivita!=null) {
					
					fillEnte(dto);
					dto.setObj(codiceFiscale);
					dto.setObj2(attivita!=null ? attivita.getId() : null);
					dto.setObj3(progetto!=null ? progetto.getId() : null);
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
								this.comuneNazioneNascitaBean.getComuneNascitaMan().setComune(mapper.readValue(prEsistente.getCsIInterventoPrFse().getComuneNascita(), ComuneBean.class));
							 
								
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
							this.csIInterventoPr.getCsIInterventoPrFse().setDurataRicLavoroId(prEsistente.getCsIInterventoPrFse().getDurataRicLavoroId());
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
	 			
		 		if(this.csIInterventoPr.getProgettoAttivita()!=null)
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
			if(this.mappaCampiFse.getSoggettoAttuatore().isAbilitato() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getSoggettoAttuatore())){
				csIInterventoPr.getCsIInterventoPrFse().setSoggettoAttuatore(getZonaSociale());
			}
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
	
	public List<SelectItem> getLstDurataRicLavoro() {
		if (lstDurataRicLavoro == null) {
			lstDurataRicLavoro = new ArrayList<SelectItem>();
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<KeyValueDTO> lst = confService.getDurataRicLavoro(cet);;
			this.lstDurataRicLavoro = convertiLista(lst);
		}
		return lstDurataRicLavoro;
	}
	
	public List<SelectItem> getLstGruppoVulnerabile() {
		if(lstGruppoVulnerabile == null){
			lstGruppoVulnerabile = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<KeyValueDTO> lst = confService.getGruppiVulnerabili(bo);
			lstGruppoVulnerabile = convertiLista(lst);
		}
		return lstGruppoVulnerabile;
	}
	
	public List<SelectItem> getLstFormeGiuridiche() {
		if(lstFormeGiuridiche == null){
			lstFormeGiuridiche = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<KeyValueDTO> lst = confService.getFormeGiuridiche(bo);
			lstFormeGiuridiche = convertiLista(lst);
		}
		return lstFormeGiuridiche;
	}
	
	public boolean isRenderFSE(){
		return DataModelCostanti.TipoProgetto.FSE.equalsIgnoreCase(this.codiceForm);
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
				this.csIInterventoPr.getCsIInterventoPrFse().setDurataRicLavoroId(null);

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
/*	public boolean isDurataRicercaLavoro(){
	 if(isRicercaPrimaOccupazione())
		 return true;
	 if(isDisoccupato())
		 return true;
	 return false;
	}*/
	
	public boolean isPensionato(){
		boolean val = this.csIInterventoPr.getCsTbCondLavoro()!=null &&
				"PENSIONATO".equalsIgnoreCase(this.csIInterventoPr.getCsTbCondLavoro().getDescrizione());
		return val;
	}
	
	public boolean isOccupato(){
		boolean val = this.csIInterventoPr.getCsTbIngMercato()!=null && //!this.isModuloPorMarche() &&
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
		AmTabComuni comune = luoghiService.getComuneItaByIstat(id);
		if(comune!=null)
			return new ComuneBean(comune);
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
		
		if(this.csIInterventoPr.getProgetto()==null || csIInterventoPr.getProgetto().getId()==null || csIInterventoPr.getProgetto().getId()==0){
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
			if(csIInterventoPr.getProgettoAttivita()==null) {
				addErrorCampiObbligatori("Progetti", "n°sottocorso/attività");
				res = false;
			}
			//SISO-846
			
			if(this.isRenderFSEAttivita()){
				
				res = res && this.validaCampiObbligatoriFse();
				
				ObjectMapper mapper = new ObjectMapper();
				ComuneBean comuneDomicilio = this.domicilioComuneMan.getComune();
				//this.csIInterventoPr.getCsIInterventoPrFse().setComuneResidenza(mapper.writeValueAsString(rc));
				try{
					this.csIInterventoPr.getCsIInterventoPrFse().setComuneDomicilio(mapper.writeValueAsString(comuneDomicilio));
				} catch (JsonProcessingException e) {
					logger.error(e);
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
				if(res){
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(csIInterventoPr);
					dto.setObj2(soggettoErogazione);
					dto.setObj3(this.mappaCampiFse);
					SiruResultDTO validazione =interventoService.validaSiru(dto);
					
					if(validazione!= null && !validazione.getErrori().isEmpty()){
						addWarning("Validazione campi Progetto", validazione.getErrori());
						res = false;
					}else
						csIInterventoPr.setCsIInterventoPrFseSiru(validazione.getSiruInterventi());
				}
				
				//SISO-996
				if(isControlloResDomPOR()){
					/*Verifico che la regione del settore titolare sia diversa da quella di residenza o domicilio*/
					boolean stessaRegione = false;
					
					String belfiore = getSettoreTitolare().getCsOOrganizzazione().getCodCatastale();
					AmTabComuni comuneTitolare = luoghiService.getComuneItaByBelfiore(belfiore);
					String regioneTitolare = comuneTitolare!=null ? comuneTitolare.getCodIstatRegione() : null;
					if(StringUtils.isBlank(regioneTitolare)){
						/*Potrebbe essere un'organizzazione con cod.fiscale (es.comunità montana)
						 * recupero la zona sociale corrente
						 * */
						AccessTableConfigurazioneEnteSessionBeanRemote opService =
						(AccessTableConfigurazioneEnteSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneEnteSessionBean");
						CeTBaseObject cet = new CeTBaseObject();
						fillEnte(cet);
						CsOZonaSoc zona = opService.findZonaSocAbilitata(cet);
						if(zona!=null && !StringUtils.isBlank(zona.getCodIstatRegione()))
							regioneTitolare = zona.getCodIstatRegione();
						else
							addError("Progetti","Errore di configurazione: cod.istat regione non impostato per la zona sociale corrente. Contattare l'assistenza.");
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
				
					if(comuneResidenza != null && !StringUtils.isBlank(regioneTitolare)){
						 comuneRes = luoghiService.getComuneItaByIstat(comuneResidenza.getCodIstatComune());
						 stessaRegione = regioneTitolare.equals(comuneRes.getCodIstatRegione());
					}	
					if(comuneDomicilio!=null && !StringUtils.isBlank(regioneTitolare) && 
					   comuneDomicilio.getCodIstatComune()!=null && !stessaRegione){
						 comuneDom = luoghiService.getComuneItaByIstat(comuneDomicilio.getCodIstatComune());
						 stessaRegione = regioneTitolare.equals(comuneDom.getCodIstatRegione());
					}	
					if(!stessaRegione){
						addError("Progetti","Per progetti finanziati su fondi POR, la regione di residenza o domicilio del beneficiario deve essere uguale alla regione del Comune Titolare");
						res = false;
					}
					
				}
			}
		}
		//SISO-1131
		if (abilitaMenuProgettiAltro) {
			if (this.selectedProgettoAltro != null ) {
				if (this.selectedProgettoAltro.getDescrizione().isEmpty()){
					addError("Progetti", "Attenzione: è obbligatorio specificare il progetto Altro");
			    	res = false;
				}
			
			} else {
				addError("Progetti", "Attenzione: è obbligatorio specificare il progetto Altro");
				res = false;
			}
		}
		
		return res;
		
	}
		
	private boolean validaCampiObbligatoriFse(){
		boolean ret = true;
		
		String telefono = this.csIInterventoPr.getCsIInterventoPrFse().getTelefono();
		String cellulare = this.csIInterventoPr.getCsIInterventoPrFse().getCellulare(); 
		String email = this.getCsIInterventoPr().getCsIInterventoPrFse().getEmail();
		
		if(this.isModuloPorMarche()){
			if(StringUtils.isBlank(telefono) && StringUtils.isBlank(cellulare) && StringUtils.isBlank(email)){
				addError("Progetti", "Informazioni insufficienti: inserire almeno un recapito tra: telefono fisso, cellulare, email");
				ret = false;
			}else{
				Matcher mTel = DataModelCostanti.patternNumTelFisso.matcher(telefono);
				 if(!StringUtils.isBlank(telefono) && !mTel.matches()){
					 addError("Progetti","Formato non corretto per il campo numero di telefono: " + telefono);
					 ret = false;
				 }
				 
				 Matcher mCel = DataModelCostanti.patternNumTelMobile.matcher(cellulare);
				 if(!StringUtils.isBlank(cellulare) && !mCel.matches()){
					 addError("Progetti","Formato non corretto per il campo numero di cellulare: "+cellulare);
					 ret = false;
				 }
				 
				 if(!StringUtils.isBlank(email)){
					EmailValidator validator = EmailValidator.getInstance();
					boolean valido = validator.isValid(email);
					if(!valido){
						addError("Progetti","Formato non corretto per il campo e-mail: "+email);
						ret=false;
					}
				}
			}
		}
		
		if(!StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getViaDomicilio()) && 
				( domicilioComuneMan.getComune()==null || StringUtils.isBlank(domicilioComuneMan.getComune().getCodIstatComune()))){
			addError("Progetti","Attenzione: è stata inserita la via di domicilio, senza specificare il comune");
			ret = false;
		}
		
		if(this.csIInterventoPr.getCsTbIngMercato()==null ) {
			addError("Progetti","Attenzione: Per i progetti di tipo FSE il campo 'Condizione nel mercato del lavoro in ingresso' è obbligatorio");
			ret = false;
		}else if(StringUtils.isBlank(this.csIInterventoPr.getCsTbIngMercato().getDescrizione())){
			addError("Progetti","Attenzione: Il campo 'Condizione nel mercato del lavoro in ingresso' non ha un valore valido per progetti di tipo FSE");
			ret = false;
		}
		
		if(this.isModuloPorMarche()){
		  if(csIInterventoPr.getCsTbTitoloStudio() == null || csIInterventoPr.getCsTbTitoloStudio().getId() == 0){
			  addError("Progetti","Attenzione: Per i progetti di tipo FSE il campo 'Titolo di studio' è obbligatorio");
				ret = false;
			}else if(csIInterventoPr.getCsTbTitoloStudio()!=null && DataModelCostanti.NON_RILEVATO.equalsIgnoreCase(csIInterventoPr.getCsTbTitoloStudio().getDescrizione())){
				addError("Progetti","Attenzione: Valore 'Non rilevato' non ammesso per 'Titolo di Studio' per i progetti di tipo FSE");
				ret = false;
			}
		}
		
		if(this.isOccupato()) {
			
			if(this.mappaCampiFse.getAzComune().isAbilitato()){
				ComuneBean azComune = comuneMan.getComune();
				this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneCod(azComune!=null ? azComune.getCodIstatComune() : null);
				this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneDes(azComune!=null ? azComune.getDenominazione(): null);
				this.csIInterventoPr.getCsIInterventoPrFse().setAzProv(azComune!=null ? azComune.getSiglaProv(): null);
			}
			
			
			if(mappaCampiFse.getLavoroTipo().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getLavoroDescTipo())) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getLavoroTipo().getLabel());
				ret = false;
			}
			if(mappaCampiFse.getLavoroOrario().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getLavoroDescTipo())) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getLavoroOrario().getLabel());
				ret = false;
			}
			if(mappaCampiFse.getAzRagioneSociale().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getAzRagioneSociale())) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getAzRagioneSociale().getLabel());
				ret = false;
			}
			if((mappaCampiFse.getAzPi().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getAzPIVA())) && 
			   (mappaCampiFse.getAzCf().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getAzCF()))) {
				String error = mappaCampiFse.getAzPi().getLabel()+" o "+mappaCampiFse.getAzCf().getLabel();
				addErrorCampiObbligatori("Progetti", error);
				ret = false;
			}
	
			if(mappaCampiFse.getAzVia().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getAzVia())) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getAzVia().getLabel());
				ret = false;
			} 

			if(mappaCampiFse.getAzComune().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getAzComuneDes())) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getAzComune().getLabel());
				ret = false;
			} 

			if(mappaCampiFse.getAzCodAteco().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getAzCodAteco())) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getAzCodAteco().getLabel());
				ret = false;
			} 

			if(mappaCampiFse.getAzFormaGiuridica().isValida() && csIInterventoPr.getCsIInterventoPrFse().getAzFormaGiuridica()==null) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getAzFormaGiuridica().getLabel());
				ret = false;
			} 

			if(mappaCampiFse.getAzDimensione().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getAzDescDimensioni())) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getAzDimensione().getLabel());
				ret = false;
			} 
		} else if(this.isDisoccupato()) {
			if(mappaCampiFse.getDurataRicercaLavoro().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getDurataRicLavoroId())) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getDurataRicercaLavoro().getLabel());
				ret = false;
			} 
			
		} else if(this.isInattivo()) {
			
		} else if(this.isRicercaPrimaOccupazione()) {
			if(mappaCampiFse.getDurataRicercaLavoro().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getDurataRicLavoroId())) {
				addErrorCampiObbligatori("Progetti", mappaCampiFse.getDurataRicercaLavoro().getLabel());
				ret = false;
			} 
		}
		
		BigDecimal annoTitolo = csIInterventoPr.getCsIInterventoPrFse().getTitoloStudioAnno();
		if(mappaCampiFse.getAnnoTitoloStudio().isValida() && (annoTitolo == null || annoTitolo.longValue()<1800)){
			addErrorCampiObbligatori("Progetti", mappaCampiFse.getAnnoTitoloStudio().getLabel());
			ret = false;
		}
		
		if(mappaCampiFse.getPagIban().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getIban())){
			addErrorCampiObbligatori("Progetti", mappaCampiFse.getPagIban().getLabel());
			ret = false;
		}
		
		if(mappaCampiFse.getPagResDom().isValida() && csIInterventoPr.getCsIInterventoPrFse().getFlagResDom()==null){
			addErrorCampiObbligatori("Progetti", mappaCampiFse.getPagResDom().getLabel());
			ret = false;
		}
		
		if(mappaCampiFse.getInattivoAltroCorso().isValida() && this.csIInterventoPr.getCsIInterventoPrFse().getFlagAltroCorso()==null){
			addErrorCampiObbligatori("Progetti", mappaCampiFse.getInattivoAltroCorso().getLabel());
			ret = false;
		}
		
		if(this.getCsIInterventoPr().getCsIInterventoPrFse().getComunicaVul() == null){
			addError("Progetti","Selezionare se l'utente comunica o meno la condizione di vulnerabilità");
			ret = false;
		}else if(this.getCsIInterventoPr().getCsIInterventoPrFse().getComunicaVul() && 
				(this.getCsIInterventoPr().getCsIInterventoPrFse().getCsTbGrVulnerabile() == null ||
				 this.getCsIInterventoPr().getCsIInterventoPrFse().getCsTbGrVulnerabile().getId() == null)){
			addError("Progetti","indicare la condizione di vulnerabilità");
			ret = false;
		}else if(!this.getCsIInterventoPr().getCsIInterventoPrFse().getComunicaVul() && 
				 this.getCsIInterventoPr().getCsIInterventoPrFse().getCsTbGrVulnerabile() != null &&
				 this.getCsIInterventoPr().getCsIInterventoPrFse().getCsTbGrVulnerabile().getId()!=null){
			addError("Progetti","Valori incompatibili: se l'utente non intende comunicare la condizione di vulnerabilità, non deve essere espresso alcun valore.");
			ret = false;
		}
		
		if(mappaCampiFse.getDataSottoscrizione().isValida() && this.getCsIInterventoPr().getCsIInterventoPrFse().getDtSottoscrizione() == null){
			addErrorCampiObbligatori("Progetti",mappaCampiFse.getDataSottoscrizione().getLabel());
			ret = false;
		}
		
		if(mappaCampiFse.getSoggettoAttuatore().isValida() && StringUtils.isBlank(csIInterventoPr.getCsIInterventoPrFse().getSoggettoAttuatore())) {
			addErrorCampiObbligatori("Progetti", mappaCampiFse.getSoggettoAttuatore().getLabel());
			ret = false;
		}
	
		return ret;
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
					lstAteco.add(new SelectItem(obj.getCodiceSiru(), obj.getCodiceSiru()+ " "+obj.getDescrizione()));
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
			
			//SISO-945
		    comuneNazioneNascitaBean = new ComuneNazioneNascitaMan();
		    
		    if(StringUtils.isBlank(this.csIInterventoPr.getCsIInterventoPrFse().getComuneNascita())){
		    	//siso - 945
		    	comuneNazioneNascitaBean = getComuneNazioneNascitaMan(
		    			datiSociali.getComuneNascitaCod(),
						datiSociali.getComuneNascitaDes(),
						datiSociali.getProvNascitaCod(),
						datiSociali.getStatoNascitaCod(),
						datiSociali.getStatoNascitaDes());
		    	
				//siso - 945 fine
			    this.datiFromCartella = true;
		    }
		    //SISO-945 FINE
		
		    //SISO-846
			if(StringUtils.isBlank(this.csIInterventoPr.getCsIInterventoPrFse().getCellulare())){
				this.csIInterventoPr.getCsIInterventoPrFse().setCellulare(datiSociali.getCel());
				this.datiFromCartella = true;
			}
			if(StringUtils.isBlank(this.csIInterventoPr.getCsIInterventoPrFse().getTelefono())){
				this.csIInterventoPr.getCsIInterventoPrFse().setTelefono(datiSociali.getTel());
				this.datiFromCartella = true;
			}
			if(StringUtils.isBlank(this.csIInterventoPr.getCsIInterventoPrFse().getEmail())){
				this.csIInterventoPr.getCsIInterventoPrFse().setEmail(datiSociali.getEmail());
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
					logger.error("Errore recuper scheda familiari conviventi per DATI SOCIALI["+datiSociali.getIdDatiSociali()+"]");
				}
			}
			
			
			//Recupero dalla cartella indirizzo e domicilio
			if(this.csIInterventoPr.getCsIInterventoPrFse().getViaDomicilio()==null || 
			   this.csIInterventoPr.getCsIInterventoPrFse().getComuneDomicilio()==null){
				
				if(this.csIInterventoPr.getCsIInterventoPrFse().getViaDomicilio()==null && datiSociali.getIndirizzoDomicilio()!=null){
						this.csIInterventoPr.getCsIInterventoPrFse().setViaDomicilio(datiSociali.getIndirizzoDomicilio());
						this.datiFromCartella = true;
				}
				if(this.domicilioComuneMan.getComune()==null){
						ComuneBean cbean = getComuneBean(datiSociali.getComuneDomicilioCod(), datiSociali.getComuneDomicilioDes(), datiSociali.getProvDomicilioCod());
						this.domicilioComuneMan.setComune(cbean);
						this.datiFromCartella = true;
				}
			}
		}
	}
	
	
	public void onChangeBeneficiarioRiferimento(String cf ,ComuneNazioneNascitaMan comuneNazioneNascitaMan) {
		this.comuneNazioneNascitaBean=comuneNazioneNascitaMan;
		loadDatiSociali(cf);
		
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
		return csIInterventoPr.getServizioAmbito();
	}
	
	public boolean isRenderSezAzienda(){
		boolean render = false;
		if(this.isOccupato() && this.mappaCampiFse!=null)
			render = this.mappaCampiFse.isRenderSezAzienda();
		return render;
	}
	
	public boolean isRenderSezLavoro(){
		boolean render = false;
		if(this.isOccupato() && this.mappaCampiFse!=null)
			render = this.mappaCampiFse.isRenderSezLavoro();
		return render;
	}

	public boolean isRenderAltroCorso(){
		boolean render = false;
		if(this.isInattivo() && this.mappaCampiFse!=null)
			render = this.mappaCampiFse.getInattivoAltroCorso().isAbilitato();	
		return render;
	}
	
	public boolean isRenderDurataRicLavoro(){
		boolean render = false;
		if(this.mappaCampiFse!=null && (this.isDisoccupato() || this.isRicercaPrimaOccupazione()))
			render = this.mappaCampiFse.getDurataRicercaLavoro().isAbilitato();
		return render;
	}
	
	public ConfigurazioneFseDTO getMappaCampiFse() {
		return mappaCampiFse;
	}

	public void valorizzaStampaFse(StampaFseDTO stampaFseDTO) {
		stampaFseDTO.setTelefono(this.getCsIInterventoPr().getCsIInterventoPrFse().getTelefono());
		stampaFseDTO.setCellulare(this.getCsIInterventoPr().getCsIInterventoPrFse().getCellulare());
		stampaFseDTO.setEmail(this.getCsIInterventoPr().getCsIInterventoPrFse().getEmail());
		stampaFseDTO.setLuogoNascita(comuneNazioneNascitaBean.getDescrizioneLuogoDiNascita());
		
		if(this.domicilioComuneMan.getComune() != null){
			stampaFseDTO.setDomicilioCap(this.domicilioComuneMan.getComune().getCap());
			stampaFseDTO.setDomicilioSiglaProv(this.domicilioComuneMan.getComune().getSiglaProv());
			stampaFseDTO.setDomicilioComune(this.domicilioComuneMan.getComune().getDenominazione());
		}
		stampaFseDTO.setViaDomicilio(this.getCsIInterventoPr().getCsIInterventoPrFse().getViaDomicilio());
	
		//Dati Dichiarati
		stampaFseDTO.setComunicaVul(this.getCsIInterventoPr().getCsIInterventoPrFse().getComunicaVul());
		
		CsTbGVulnerabile gVulnerabile = this.getCsIInterventoPr().getCsIInterventoPrFse().getCsTbGrVulnerabile();
		if(gVulnerabile!=null){
			stampaFseDTO.setIdVulnerabile(gVulnerabile.getId());
			stampaFseDTO.setDescrizioneVulnerabile(gVulnerabile.getTooltip());
		}
		
		stampaFseDTO.setDurataRicercaLavoro(this.isRenderDurataRicLavoro());
		String idDurataRicLavoro = this.getCsIInterventoPr().getCsIInterventoPrFse().getDurataRicLavoroId();
		CsTbDurataRicLavoro ricLavoro = null;
		if(!StringUtils.isBlank(idDurataRicLavoro)){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(new Long(idDurataRicLavoro));
			ricLavoro = confService.findDurataRicLavoroById(dto);
		}
		stampaFseDTO.setLavoroDurataRicerca(ricLavoro!=null ? ricLavoro.getTooltip() : "");
		
		CsTbIngMercato ingMercato = this.getCsIInterventoPr().getCsTbIngMercato();
		stampaFseDTO.setCondLavoro(ingMercato!=null ? ingMercato.getTooltip() : null);
		
		CsTbTitoloStudio tbTit = this.getCsIInterventoPr().getCsTbTitoloStudio();
		String codIstat = tbTit!=null && !StringUtils.isBlank(tbTit.getCodIstat()) ? tbTit.getCodIstat() : "";
		String tooltip = tbTit!=null && !StringUtils.isBlank(tbTit.getTooltip()) ? tbTit.getTooltip() : "";
		String titolo = tbTit!=null ? (codIstat+" "+tbTit.getDescrizione()).trim() : null;
		stampaFseDTO.setTitoloStudio(titolo);
		stampaFseDTO.setTitoloStudioTooltip(tooltip);
		
		Date dtsottoscrizione = this.getCsIInterventoPr().getCsIInterventoPrFse().getDtSottoscrizione();
		if(dtsottoscrizione!=null)
			stampaFseDTO.setDtSottoscrizione(ddMMyyyy.format(dtsottoscrizione));
		stampaFseDTO.setSoggettoAttuatore(this.getCsIInterventoPr().getCsIInterventoPrFse().getSoggettoAttuatore());
		
		//Dati progetto
		String progDesc = this.csIInterventoPr.getProgetto().getDescrizione();
		stampaFseDTO.setFfProgettoDescrizione(progDesc.replaceFirst(DataModelCostanti.patternFSE, ""));
		stampaFseDTO.setCodProgetto(this.getCsIInterventoPr().getProgetto().getCodiceMemo());
		stampaFseDTO.setCodAttivita(this.getCsIInterventoPr().getProgettoAttivita().getCodice());
		
	}

	public Long getIdProgettoSel() {
		return this.csIInterventoPr.getProgetto()!=null ? this.csIInterventoPr.getProgetto().getId() : null;
	}

	public void setIdProgettoSel(Long idProgettoSel) {
		ArFfProgetto p = this.getProgetto(idProgettoSel);
		this.csIInterventoPr.setProgetto(p);
	}

	public Long getIdProgettoAttivitaSel() {
		return this.csIInterventoPr.getProgettoAttivita()!=null ? this.csIInterventoPr.getProgettoAttivita().getId() : null;
	}

	public void setIdProgettoAttivitaSel(Long idProgettoAttivitaSel) {
		ArFfProgettoAttivita p = null;
		if(idProgettoAttivitaSel!=null && idProgettoAttivitaSel>0){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idProgettoAttivitaSel);
			p  = confService.getProgettoAttivitaById(dto);
		}
		this.csIInterventoPr.setProgettoAttivita(p);
	}
	
	private ArFfProgetto getProgetto(Long id){
		ArFfProgetto p = null;
		if(id!=null && id>0){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(id);
			p  = confService.getProgettiById(dto);
		}
		return p;
	}

	public void onSettoreChange() {
		setIdSettoreGestoreComeIdSettoreTitolare();
		CsOSettore settoreTitolare = this.getSettoreTitolare();
		if (settoreTitolare == null || getCsIInterventoPr().getServizioAmbito()==null || !settoreTitolare.getCsOOrganizzazione().getFlagCapofila()) 
			setServizioGestitoComeAmbito(false);
	}
	
}