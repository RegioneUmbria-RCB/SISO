package it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.CompartecipazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SpesaDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoUtils;
import it.webred.cs.jsf.bean.erogazioneIntervento.InterventoErogazAttrBean;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoUtils.SumDTO;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.utilities.CommonUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

public class ErogInterventoRowBean extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
    private String idRow;
    
    private boolean richiestaIntervento;
    private List<SelectItem> infoRichiestaIntervento;
   
	private String dettaglioTotaleErogazione;
	private Long diarioId;
	//private List<SoggettoErogazioneBean> beneficiari;
	private List<SoggettoErogazioneBean> altriBeneficiari;
	private SoggettoErogazioneBean beneficiarioRiferimento;

	private boolean renderBtnAvviaErog = false;
	private boolean renderBtnEroga = false;
	private boolean renderBtnEliminaErog = false;
	private boolean interventoDaErogare = false;
	private boolean renderBtnStampaPor = false;

	private Long idIntervento = null;
	private Long idTipoIntervento = null;
	private Long idTipoInterventoCustom=null;
	private Long idCatSociale=null;
	private String lineaFinanziamento;
	private String settoreTitolare;
	private boolean servizioAmbito;
	private String settoreErogante;
	/* == SISO-663+664 SM == */
	private String settoreGestore;
	private String descrizioneProgetto;
	/* --===-- */
	
	private ErogazioneMasterDTO master;
	
	private ErogazioneDettaglioDTO ultimoCsIInterventoEseg;
	
	private List<ErogazioneDettaglioDTO> lstInterventiEseguiti;
	
	private String sommaUnitaMisura;
	//SISO-748
	private Long diarioPaiId;
	
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");

	public ErogInterventoRowBean(ErogazioneMasterDTO intervento, boolean setupDettaglioErogazione) {
		setupRowBean(intervento, setupDettaglioErogazione);
	}
	
	private void setupRowBean(ErogazioneMasterDTO erogazione, boolean setupDettaglioErogazione) {
        logger.debug("setupRowBean ["+erogazione.getIdInterventoEsegMaster()+"]");
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);
		this.master = erogazione;
		this.idIntervento = erogazione.getIdIntervento();
		if(this.idIntervento!=null){
			this.idRow="I"+this.idIntervento;
		}else{
			this.idRow="E"+erogazione.getIdInterventoEsegMaster();
		}
		
		this.lstInterventiEseguiti = erogazione.getListaErogazioniDettaglio();
		this.sommaUnitaMisura = erogazione.getSommaUnitaMisura();
		
		this.ultimoCsIInterventoEseg = erogazione.getLastErogazioneDettaglio();
	
		this.diarioId = erogazione.getDiarioId();
		
		this.beneficiarioRiferimento = erogazione.getBeneficiarioRiferimento(); 
		this.altriBeneficiari = erogazione.getBeneficiariNonRiferimento();
		//this.beneficiari = erogazione.getBeneficiari();
		
		this.idTipoIntervento = erogazione.getTipoIntervento().getId();
		this.idTipoInterventoCustom = erogazione.getIdTipoInterventoCustom();
		this.idCatSociale = erogazione.getIdCategoriaSociale()!=null ? erogazione.getIdCategoriaSociale().longValue() : null;
		
		this.lineaFinanziamento = erogazione.getLineaFinanziamento()!=null ? erogazione.getLineaFinanziamento() : "";
		this.lineaFinanziamento += erogazione.getCodFinanziamento()!=null ? "(cod. "+erogazione.getCodFinanziamento()+")" : "";

		this.settoreTitolare = erogazione.getSettoreTitolare();
		this.settoreGestore = erogazione.getSettoreGestore();
		this.settoreErogante = erogazione.getSettoreErogante();
		this.servizioAmbito = erogazione.getServizioAmbito();
		this.renderBtnStampaPor = erogazione.getStampaPOR();
		this.descrizioneProgetto = erogazione.getDescrizioneProgetto();
		
		this.richiestaIntervento = erogazione.getDataUltimoFlg()!=null;
		this.infoRichiestaIntervento = new ArrayList<SelectItem>();
		
		if(erogazione.getDataRichiestaIntervento()!=null){
			this.infoRichiestaIntervento.add(new SelectItem("Data richiesta", ddMMyyyy.format(erogazione.getDataRichiestaIntervento())));
		}
		if( erogazione.getStatoUltimoFlg()!=null){
			this.infoRichiestaIntervento.add(new SelectItem("Stato richiesta", erogazione.getStatoUltimoFlg()));
		}
		if(erogazione.getDataUltimoFlg()!=null){
			this.infoRichiestaIntervento.add(new SelectItem("Data foglio amministrativo", ddMMyyyy.format(erogazione.getDataUltimoFlg())));
		}
		
		//SISO-748
		if(erogazione.getDiarioPaiId() != null){
			this.setDiarioPaiId(erogazione.getDiarioPaiId());
		}
		
		setupBtnRendering();
		if(setupDettaglioErogazione)
			setupDettagliTotaleErogazione(erogazione);	
}
	
	//SISO-748
	public String getLabelConcatAll(){
		String toReturn = master.getTipoIntervento().getDescrizione();
		
		if(master.getTipoInterventoCustom() != null){
			toReturn += "," + master.getTipoInterventoCustom();
		}
		
		if(master.getStatoUltimaErogazione() != null){
			toReturn += "," + master.getStatoUltimaErogazione();
		}
		
		return toReturn;
	}
	
	private void setupBtnRendering() {
		boolean foglioNonPresente = idIntervento==null || idIntervento<=0;
		renderBtnEliminaErog = ultimoCsIInterventoEseg != null && foglioNonPresente && CsUiCompBaseBean.isPermessoAutorizzativo();
		interventoDaErogare = idIntervento != null && ultimoCsIInterventoEseg == null;

		renderBtnEroga = false;
		renderBtnAvviaErog = false;

		if (CsUiCompBaseBean.isPermessoErogativo()) {
			if (ultimoCsIInterventoEseg != null)
				renderBtnEroga = ultimoCsIInterventoEseg.getStatoErogazione().getErogazionePossibile();
		}else if (CsUiCompBaseBean.isPermessoAutorizzativo()) {
			if (ultimoCsIInterventoEseg == null) renderBtnAvviaErog = true;
			else renderBtnEroga = true;
		}
		
	}

	protected Boolean canCalcTotale( CsIInterventoEsegValore val, HashMap<Long, ErogStatoCfgDTO> mappaStatiTipoIntervento) {
		CsIInterventoEseg eseg = val.getCsIInterventoEseg();
		List<IntEsegAttrBean> lstAttr = new ArrayList<IntEsegAttrBean>();
		if(!mappaStatiTipoIntervento.isEmpty() && eseg.getStato()!=null) 
			lstAttr = mappaStatiTipoIntervento.get(eseg.getStato().getId()).getListaAttributi();
	
		Boolean canCalc = null;
		int i = 0;
		while( canCalc==null && i<lstAttr.size()){
			IntEsegAttrBean uu = lstAttr.get(i);
			canCalc = uu.getCalcTotale_ifMatchingAttUM(val.getCsAttributoUnitaMisura().getId());
			i++;
		}
		
		return canCalc!= null ? canCalc : false;
	}
	protected void setupDettagliTotaleErogazione(ErogazioneMasterDTO erogazione) {

		dettaglioTotaleErogazione = "-";
		if (erogazione.getListaErogazioniDettaglio() == null || erogazione.getListaErogazioniDettaglio().size() == 0)
			return;

		//Recupero configurazione attributi per tipo intervento e calcolo dettagli
		Long tipoInterventoCorrente = (idTipoInterventoCustom!=null ? idTipoInterventoCustom : idTipoIntervento);
		HashMap<Long, ErogStatoCfgDTO> mappaStatiTipoIntervento = erogazione.getMappaStatiTipoIntervento();
		if(mappaStatiTipoIntervento.isEmpty())
			this.addWarning("Nessuna configurazione per il tipo Intervento:"+tipoInterventoCorrente, "");
		
		HashMap<String, SumDTO> mappaSomme = new HashMap<String, SumDTO>();
		
		for (ErogazioneDettaglioDTO intEseg : erogazione.getListaErogazioniDettaglio()) {
			for (int i = 0; i < intEseg.getValori().size(); i++) {
				CsIInterventoEsegValore valore = intEseg.getValori().get(i);
				Boolean calcTotale = canCalcTotale(valore, mappaStatiTipoIntervento);
				InterventoErogazAttrBean valBean = new InterventoErogazAttrBean(valore, calcTotale);
				if( valBean.getAttr().getCalcTotale() ){
					SumDTO sold = mappaSomme.get(valBean.getLabel());
					if(sold==null) sold = new SumDTO();
					SumDTO sum = ErogazioneInterventoUtils.sum(valBean, sold );
					mappaSomme.put(valBean.getLabel(), sum);
				    	
				}
			}
		 }
	
	    Iterator<String> it = mappaSomme.keySet().iterator();
		List<String> lst = new LinkedList<String>();
		while(it.hasNext()){
		    String label = it.next();
			if( StringUtils.isNotEmpty(label) )
				lst.add(String.format("<strong>%s</strong> = %s", label,mappaSomme.get(label).getTotaleUnitaMisura()));
		}
		dettaglioTotaleErogazione = CommonUtils.Join("<br/>", lst.toArray());
	}

	public boolean isRenderBtnEliminaErog() {
		return renderBtnEliminaErog;
	}

	public boolean isInterventoDaErogare() {
		return interventoDaErogare;
	}

	public void setInterventoService(AccessTableInterventoSessionBeanRemote interventoService) {
		this.interventoService = interventoService;
	}

	public String getNomeRowDiaglogButton() {
		if (renderBtnAvviaErog)
			return "Avvia";
		if (renderBtnEroga)
			return "Eroga";

		return "Errore: Azione non supportata";
	}

	public boolean isRenderedRowDiaglogButton() {
		return renderBtnAvviaErog | renderBtnEroga;
	}

	public boolean isInterventoEseguitoSenzaRichiesta() {
		return idIntervento == null;
	}

	public boolean isRenderBtnAvviaErog() {
		return renderBtnAvviaErog;
	}

	public boolean isRenderBtnEroga() {
		return renderBtnEroga;
	}

	public String getDettaglioTotaleErogazione() {
		return dettaglioTotaleErogazione;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public Long getIdTipoIntervento() {
		return idTipoIntervento;
	}

	public boolean isRichiestaIntervento() {
		return richiestaIntervento;
	}

	public void setRichiestaIntervento(boolean richiestaIntervento) {
		this.richiestaIntervento = richiestaIntervento;
	}

	public List<SelectItem> getInfoRichiestaIntervento() {
		return infoRichiestaIntervento;
	}

	public void setInfoRichiestaIntervento(List<SelectItem> infoRichiestaIntervento) {
		this.infoRichiestaIntervento = infoRichiestaIntervento;
	}

	public List<ErogazioneDettaglioDTO> getLstInterventiEseguiti() {
		return lstInterventiEseguiti;
	}

	public void setLstInterventiEseguiti(List<ErogazioneDettaglioDTO> lstInterventiEseguiti) {
		this.lstInterventiEseguiti = lstInterventiEseguiti;
	}

	public String getLineaFinanziamento() {
		return lineaFinanziamento;
	}

	public void setLineaFinanziamento(String lineaFinanziamento) {
		this.lineaFinanziamento = lineaFinanziamento;
	}

	public ErogazioneMasterDTO getMaster() {
		return master;
	}

	public void setMaster(ErogazioneMasterDTO master) {
		this.master = master;
	}

	public Long getIdTipoInterventoCustom() {
		return idTipoInterventoCustom;
	}

	public void setIdTipoInterventoCustom(Long idTipoInterventoCustom) {
		this.idTipoInterventoCustom = idTipoInterventoCustom;
	}

	public List<SoggettoErogazioneBean> getBeneficiari() {
		List<SoggettoErogazioneBean> lst = new ArrayList<SoggettoErogazioneBean>();
		lst.add(beneficiarioRiferimento);
		lst.addAll(altriBeneficiari);
		return lst;
	}

	public List<SoggettoErogazioneBean> getAltriBeneficiari() {
		return altriBeneficiari;
	}

	public void setAltriBeneficiari(List<SoggettoErogazioneBean> altriBeneficiari) {
		this.altriBeneficiari = altriBeneficiari;
	}

	public SoggettoErogazioneBean getBeneficiarioRiferimento() {
		return beneficiarioRiferimento;
	}

	public Long getIdIntervento() {
		return idIntervento;
	}
	
	public AccessTableInterventoSessionBeanRemote getInterventoService() {
		return interventoService;
	}

	public void setDettaglioTotaleErogazione(String dettaglioTotaleErogazione) {
		this.dettaglioTotaleErogazione = dettaglioTotaleErogazione;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public void setBeneficiarioRiferimento(SoggettoErogazioneBean beneficiarioRiferimento) {
		this.beneficiarioRiferimento = beneficiarioRiferimento;
	}

	public void setRenderBtnAvviaErog(boolean renderBtnAvviaErog) {
		this.renderBtnAvviaErog = renderBtnAvviaErog;
	}

	public void setRenderBtnEroga(boolean renderBtnEroga) {
		this.renderBtnEroga = renderBtnEroga;
	}

	public void setRenderBtnEliminaErog(boolean renderBtnEliminaErog) {
		this.renderBtnEliminaErog = renderBtnEliminaErog;
	}

	public void setInterventoDaErogare(boolean interventoDaErogare) {
		this.interventoDaErogare = interventoDaErogare;
	}

	public boolean isRenderBtnStampaPor() {
		return renderBtnStampaPor;
	}

	public void setRenderBtnStampaPor(boolean renderBtnStampaPor) {
		this.renderBtnStampaPor = renderBtnStampaPor;
	}

	public void setIdIntervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}

	public void setIdTipoIntervento(Long idTipoIntervento) {
		this.idTipoIntervento = idTipoIntervento;
	}

	public String getIdRow() {
		return idRow;
	}

	public void setIdRow(String idRow) {
		this.idRow = idRow;
	}

	 public String getSettoreTitolare() {
		return settoreTitolare;
	}

	public void setSettoreTitolare(String settoreTitolare) {
		this.settoreTitolare = settoreTitolare;
	}

	public String getSettoreErogante() {
		return settoreErogante;
	}

	public void setSettoreErogante(String settoreErogante) {
		this.settoreErogante = settoreErogante;
	}

	public void setSettoreGestore(String settoreGestore) {
		this.settoreGestore = settoreGestore;
	}

	public Long getIdCatSociale() {
		return idCatSociale;
	}

	public void setIdCatSociale(Long idCatSociale) {
		this.idCatSociale = idCatSociale;
	}

	//INIZIO SISO-556
	public boolean isDataErogazioneARendered(){
		boolean res = false;
		
		for (ErogazioneDettaglioDTO erogazioneDettaglioDTO : lstInterventiEseguiti) {
			if (erogazioneDettaglioDTO.getDataErogazioneA() != null) {
				res = true;
			}
		}
		
		return res;
	}
	
	public ErogazioneDettaglioDTO getUltimoCsIInterventoEseg() {
		return ultimoCsIInterventoEseg;
	}

	public void setUltimoCsIInterventoEseg(
			ErogazioneDettaglioDTO ultimoCsIInterventoEseg) {
		this.ultimoCsIInterventoEseg = ultimoCsIInterventoEseg;
	}

	public String getSettoreGestore() {
		return settoreGestore;
	}

	/* == SISO-664 SM == */
	public String getDescrizioneProgetto() { return descrizioneProgetto; }
	public void setDescrizioneProgetto(String descrizioneProgetto) { this.descrizioneProgetto = descrizioneProgetto; }
	/* --===-- */

	public String getSommaUnitaMisura() {
		return sommaUnitaMisura;
	}

	public void setSommaUnitaMisura(String sommaUnitaMisura) {
		this.sommaUnitaMisura = sommaUnitaMisura;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}

	public boolean isServizioAmbito() {
		return servizioAmbito;
	}

	public void setServizioAmbito(boolean servizioAmbito) {
		this.servizioAmbito = servizioAmbito;
	}
}
