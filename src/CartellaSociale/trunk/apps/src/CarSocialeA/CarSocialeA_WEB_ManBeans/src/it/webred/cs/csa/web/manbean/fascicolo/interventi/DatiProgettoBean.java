package it.webred.cs.csa.web.manbean.fascicolo.interventi;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SiruDominioDTO;
import it.webred.cs.csa.ejb.dto.SiruResultDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.enumeratori.SiruEnum;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIInterventoPrFse;
import it.webred.cs.data.model.CsIInterventoPrFseSiru;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbFormaGiuridica;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbIngMercato;
import it.webred.cs.data.model.CsTbTipoContributo;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.jsf.manbean.ComuneResidenzaMan;
import it.webred.cs.jsf.manbean.comuneNazione.ComuneGenericMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;

public class DatiProgettoBean extends CsUiCompBaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb ("AccessTableConfigurazioneSessionBean");
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb ("AccessTableInterventoSessionBean");

	protected AccessTableDominiSiruSessionBeanRemote  dominiSiruService= (AccessTableDominiSiruSessionBeanRemote)getCarSocialeEjb("AccessTableDominiSiruSessionBean");
	
	private Long idTipoIntervento;
	private Long idTipoIntrCustom;
	private Long idCatSociale;
	
	protected List<CsOSettore> listaSettori;
	
	protected CsCTipoIntervento tipoIntervento;
	protected CsCTipoInterventoCustom tipoIntCustom;
	
	private List<ArFfProgetto> listaProgetti= new ArrayList<ArFfProgetto>();  //SISO-522
	private CsIInterventoPr csIInterventoPr;
	private String codiceForm;
	
	private List<ArFfProgettoAttivita> listaSottocorsi= new ArrayList<ArFfProgettoAttivita>();//SISO-790
	private List<ArFfProgettoAttivita> copiaListaSottocorsi=new ArrayList<ArFfProgettoAttivita>(); 
	private Long idProgettoSelezionato;
	private CsIInterventoPrFse csIInterventoPrFse;
	//SISO-790
	
	protected boolean solaLettura = false;
	
	private ComuneGenericMan comuneMan;
	

	private List<SelectItem> listaSettoriTitGroup;
	private List<SelectItem> listaSettoriGestGroup;
	
	private List<VLineaFin>  listaOrigineFin= new ArrayList<VLineaFin>();
	private List<SelectItem> lstConLavorativa;
	private List<SelectItem> lstTitoliStudio;
	private List<SelectItem> lstIngMercato;
	private List<CsTbIngMercato> lstIngMercatoTooltip;

	private List<SelectItem> lstGruppoVulnerabile;
	private List<SelectItem> lstFormeGiuridiche;
	
	private List<SelectItem> lstAteco; //SISO-850
	
	private boolean lavoroFromDatiSociali=false;
	private boolean studioFromDatiSociali=false;
	
	private ComuneResidenzaMan residenzaComuneMan;
	private ComuneResidenzaMan domicilioComuneMan;
	
	//SISO-817
	private SoggettoErogazioneBean soggettoErogazione;
	
	private final DatiProgettoBeanTooltipText tooltipText = new DatiProgettoBeanTooltipText();

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
		
	  public CsIInterventoPrFse getCsIInterventoPrFse() {
			return csIInterventoPrFse;
		}

		public void setCsIInterventoPrFse(CsIInterventoPrFse csIInterventoPrFse) {
			this.csIInterventoPrFse = csIInterventoPrFse;
		}

		//fine SISO-790

	
	public void loadDatiProgetto(CsIInterventoPr progetto, boolean solalettura, CsADatiSociali datiSociali, Long tipoIntId, Long tipoInCustomId, Long catSocialeID) {
		
		if(progetto!=null){
			this.csIInterventoPr = progetto;
			
			//this.selUnitaMisura = this.csIQuota.getCsTbUnitaMisura()!=null ? this.csIQuota.getCsTbUnitaMisura().getId() : null;
		}else
			this.csIInterventoPr = new CsIInterventoPr();
		
		
		if(this.csIInterventoPr.getCsTbCondLavoro()==null){
			this.csIInterventoPr.setCsTbCondLavoro(new CsTbCondLavoro());
			if(datiSociali!=null && datiSociali.getCondLavorativaId()!=null){
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(datiSociali.getCondLavorativaId());
				CsTbCondLavoro lavoro = confService.getCondLavoroById(dto);
				csIInterventoPr.setCsTbCondLavoro(lavoro);
				lavoroFromDatiSociali = true;
			}
		}
		
		if(this.csIInterventoPr.getCsTbIngMercato()==null){
			if(this.csIInterventoPr.getCsTbCondLavoro()!=null)
				this.csIInterventoPr.setCsTbIngMercato(csIInterventoPr.getCsTbCondLavoro().getCsTbIngMercato());
			else
				this.csIInterventoPr.setCsTbIngMercato(new CsTbIngMercato());
		}
		
		
		if(this.csIInterventoPr.getCsTbTitoloStudio()==null){
			this.csIInterventoPr.setCsTbTitoloStudio(new CsTbTitoloStudio());
			if(datiSociali!=null && datiSociali.getTitoloStudioId()!=null){
				this.csIInterventoPr.getCsTbTitoloStudio().setId(datiSociali.getTitoloStudioId().longValue());
				studioFromDatiSociali=true;
			}
		}
		this.solaLettura = solalettura;
		
		//Imposto al settore corrente, se non valorizzato
		if(this.getSelSettoreTitolareId()==null){
			this.setSelSettoreTitolareId(getCurrentOpSettore().getCsOSettore().getId());
			//Richiamare FglInterventoBean.cmbSettoreOnChange per aggiornare il campo "Gestione della Spesa" in TAB EROGAZIONI
			FglInterventoBean fb = (FglInterventoBean)this.getReferencedBean("fglInterventoBean");
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
		
		this.codiceForm = null;
		if(this.csIInterventoPr.getCsIInterventoPrFse()!=null){
			this.codiceForm = "FSE";
			String jsonResidenza = this.getCsIInterventoPr().getCsIInterventoPrFse().getComuneResidenza();
			String jsonDomicilio = this.getCsIInterventoPr().getCsIInterventoPrFse().getComuneDomicilio();
			
			
			ObjectMapper mapper = new ObjectMapper();
			
			residenzaComuneMan = new ComuneResidenzaMan();
			domicilioComuneMan = new ComuneResidenzaMan();
			try {
				residenzaComuneMan.setComune(mapper.readValue(jsonResidenza, ComuneBean.class));
				domicilioComuneMan.setComune(mapper.readValue(jsonDomicilio, ComuneBean.class));
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	
	private CsTbGVulnerabile valorizzaGVulnerabile(String id){
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(id!=null){
    		d.setObj(id.toString());
    		return confService.getGrVulnerabileById(d);
    	}
    	return null;
    }

	
	public boolean salva() {
		boolean bOk = true;	
	
		if(this.getCsIInterventoPr()!=null){
			
			try{
				
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
				
				bdto.setObj(titolare.getCsOOrganizzazione().getBelfiore());
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
	
	public void onChangeOrigineFinanziamento() {
		boolean trovato = false;
		VLineaFin lineaSelected = null;
		for (VLineaFin linea : this.getListaOrigineFin()) {
			if (linea.getId().equals(this.getOrigineFinanziamentoId()) ) {
				lineaSelected = linea;
			}
		}
		if (lineaSelected!=null && lineaSelected.getProgettoId()!=null) {
			for (ArFfProgetto progetto : this.getListaProgetti()) {
				logger.debug(progetto.getId() + "  " + lineaSelected.getProgettoId());
				if (progetto.getId() == lineaSelected.getProgettoId().longValue()) {
					this.csIInterventoPr.setFfProgettoDescrizione(progetto.getDescrizione());
					trovato = true;
					break;
				}
			}
		}
 
		if (!trovato) this.csIInterventoPr.setFfProgettoDescrizione(null);
		else this.onChangeProgetto();
	}

	public Long getOrigineFinanziamentoId() {
		return this.csIInterventoPr.getFfOrigineId();
	}

	public void setOrigineFinanziamentoId(Long origineFinanziamentoId) {
		this.csIInterventoPr.setFfOrigineId(origineFinanziamentoId); 
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

	public void onChangeOrigineFinanziamento(AjaxBehaviorEvent event){ 
		onChangeOrigineFinanziamento();  
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
	
	public void onChangeProgetto(){
		String progetto = this.getCsIInterventoPr().getFfProgettoDescrizione();
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(progetto);
		dto.setObj2(this.idTipoIntervento);
		dto.setObj3(this.idTipoIntrCustom);
		dto.setObj4(this.idCatSociale);
		codiceForm = this.confService.findCodFormProgetto(dto);
		
		if(this.isRenderFSE()){
			residenzaComuneMan = new ComuneResidenzaMan();
			domicilioComuneMan = new ComuneResidenzaMan();
			if(this.csIInterventoPr.getCsIInterventoPrFse()==null) 
				this.csIInterventoPr.setCsIInterventoPrFse(new CsIInterventoPrFse());
			//SISO-846
			if(this.csIInterventoPr.getCsIInterventoPrFse().getFlagResDom()==null){
				this.csIInterventoPr.getCsIInterventoPrFse().setFlagResDom("RES");
			}
			
		}else{
			this.csIInterventoPr.setCsIInterventoPrFse(null);
			residenzaComuneMan = null;
			domicilioComuneMan = null;
		}
		//inizio SISO-790
		LoadListaSottocorsi(listaProgetti, copiaListaSottocorsi);
	    //fine SISO-790
	}
	

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
	
	public void onChangeIngMercato(){
		if(this.isRenderFSE()){
			if(this.isOccupato()){
				this.comuneMan = new ComuneGenericMan("Sede aziendale");
			}else resetDatiInMercato02();
			
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
			
			this.comuneMan = null;
		}
	}

	public boolean isOccupato(){
		return this.csIInterventoPr.getCsTbIngMercato()!=null && 
				DataModelCostanti.TipiIngMercato.OCCUPATO.equalsIgnoreCase(this.csIInterventoPr.getCsTbIngMercato().getId());
	}
	
	public boolean isInattivo(){
		return this.csIInterventoPr.getCsTbIngMercato()!=null && 
				DataModelCostanti.TipiIngMercato.INATTIVO.equalsIgnoreCase(this.csIInterventoPr.getCsTbIngMercato().getId());
	}
	
	public boolean isDisoccupato(){
		return this.csIInterventoPr.getCsTbIngMercato()!=null && 
				DataModelCostanti.TipiIngMercato.DISOCCUPATO.equalsIgnoreCase(this.csIInterventoPr.getCsTbIngMercato().getId());
	}
	public boolean isRicercaPrimaOccupazione(){
		return this.csIInterventoPr.getCsTbIngMercato()!=null && 
				DataModelCostanti.TipiIngMercato.CERCA_PRIMA_OCCUPAZIONE.equalsIgnoreCase(this.csIInterventoPr.getCsTbIngMercato().getId());
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

	public ComuneResidenzaMan getResidenzaComuneMan() {
		return residenzaComuneMan;
	}

	public void setResidenzaComuneMan(ComuneResidenzaMan residenzaComuneMan) {
		this.residenzaComuneMan = residenzaComuneMan;
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
	
	public boolean validaDatiProgetto() {
		boolean res = true;
		
		/*Valorizzo i campi PR con i dati finali, altrimenti la validazione SIRU non funziona correttamente*/
		if(this.csIInterventoPr.getCsTbIngMercato()!=null && this.getCsIInterventoPr().getCsTbIngMercato().getId()==null)
			this.csIInterventoPr.setCsTbIngMercato(null);
		
		if(this.csIInterventoPr.getCsTbTitoloStudio()!=null && this.getCsIInterventoPr().getCsTbTitoloStudio().getId()<=0)
			this.csIInterventoPr.setCsTbTitoloStudio(null);
		/*END*/
		
		if(this.csIInterventoPr.getFfProgettoDescrizione()==null || this.csIInterventoPr.getFfProgettoDescrizione().isEmpty()){
			addError("","'Progetto' è un campo obbligatorio");
			res = false;
		}

		/* == SISO-663 SM == */
		if (csIInterventoPr.getSettoreTitolare().getId() == null) {
			addError("","Il campo 'settore titolare' è obbligatorio");
			res = false;
		}
		if (csIInterventoPr.getSettoreGestore().getId() == null) {
			addError("","Il campo 'settore gestore' è obbligatorio");
			res = false;
		}
		
		if(this.isRenderFSE()){ 
			
			/*Valorizzo i campi finali con quelli temporanei della classe, altimenti nel metodo validaSiru trovo tutto a NULL*/
			ComuneBean rc = this.residenzaComuneMan.getComune();
			ComuneBean dc = this.domicilioComuneMan.getComune();
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				this.csIInterventoPr.getCsIInterventoPrFse().setComuneResidenza(mapper.writeValueAsString(rc));
				this.csIInterventoPr.getCsIInterventoPrFse().setComuneDomicilio(mapper.writeValueAsString(dc));
			} catch (JsonProcessingException e) {
				logger.error(e);
			}
			
			if(this.isOccupato()){
				ComuneBean azComune = comuneMan.getComune();
				this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneCod(azComune!=null ? azComune.getCodIstatComune() : null);
				this.csIInterventoPr.getCsIInterventoPrFse().setAzComuneDes(azComune!=null ? azComune.getDenominazione(): null);
				this.csIInterventoPr.getCsIInterventoPrFse().setAzProv(azComune!=null ? azComune.getSiglaProv(): null);
			}
			
			//Validazione campi
			//TODO: SISO-790 Verifica obbligatorietà campo N_SOTTOCORSO_ATTIVITA
			if(csIInterventoPr.getnSottocorsoAttivita().isEmpty() ) {
				addError("","Il 'n°sottocorso/attività' è obbligatorio");
				res = false;
			}
			//SISO-846
			
			if(csIInterventoPr.getCsIInterventoPrFse().getViaResidenza() != null && csIInterventoPr.getCsIInterventoPrFse().getViaResidenza().isEmpty() || this.getResidenzaComuneMan().comune==null)
			{
				addError("","Attenzione: valorizzare via e comune di residenza");
				res = false;
			}
			if(csIInterventoPr.getCsIInterventoPrFse().getFlagResDom().equals("DOM") && 
				(csIInterventoPr.getCsIInterventoPrFse().getViaDomicilio() != null && csIInterventoPr.getCsIInterventoPrFse().getViaDomicilio().isEmpty() ||
			    this.getDomicilioComuneMan().comune==null))
			{
				addError("","Attenzione: valorizzare via e comune di domicilio");
				res = false;
					
			}
			
			//VALIDAZIONE SIRU
			SiruResultDTO valdiazione =interventoService.validaSiru(csIInterventoPr,soggettoErogazione);
			if(valdiazione!= null && !valdiazione.getErrori().isEmpty()){
				addWarning("Codifica dati SIRU", valdiazione.getErrori());
				res = false;
			}else{
				csIInterventoPr.setCsIInterventoPrFseSiru(valdiazione.getSiru());
			}
		}
		
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
	
	public SoggettoErogazioneBean getSoggettoErogazione() {
		return soggettoErogazione;
	}

	public void setSoggettoErogazione(SoggettoErogazioneBean soggettoErogazione) {
		this.soggettoErogazione = soggettoErogazione;
	}
	
	
}