package it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.CompartecipazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrUtilsBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SpesaDTO;
import it.webred.cs.data.DataModelCostanti.TipoStatoErogazione;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEseg;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.Costanti.TipoPermessoErogazioneInterventi;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoUtils;
import it.webred.cs.jsf.bean.erogazioneIntervento.InterventoErogazAttrBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.utilities.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
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
	private List<SoggettoErogazioneBean> beneficiari;
	private SoggettoErogazioneBean beneficiarioRiferimento;
	
	private CsOOperatoreSettore opSettore;

	private boolean renderBtnAvviaErog = false;
	private boolean renderBtnEroga = false;
	private boolean renderBtnEliminaErog = false;
	private boolean interventoDaErogare = false;

	private Long idIntervento = null;
	private Long idTipoIntervento = null;
	private Long idTipoInterventoCustom=null;
	private String lineaFinanziamento;
	private CsOSettore settoreTitolare;
	private CsOSettore settoreErogante;
	
	private ErogazioneMasterDTO master;
	
	private CsIInterventoEseg ultimoCsIInterventoEseg;
	
	private List<ErogazioneDettaglioDTO> lstInterventiEseguiti;
	
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");

	public ErogInterventoRowBean(ErogazioneMasterDTO intervento, String permesso) {
		setupRowBean(intervento, permesso);
	}

	private List<IntEsegAttrBean> getAttributiByTipoInterventoId( Long id ) {
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);
		bDto.setObj( id ); 
		
		List<IntEsegAttrBean> lstAttr = new LinkedList<IntEsegAttrBean>();
		CsCfgIntEseg cfg = interventoService.findConfigIntErogByTipoIntervento(bDto);
		
		if(cfg!=null){
			lstAttr = IntEsegAttrUtilsBean.Initialize(cfg.getCsCfgIntEsegAttUms());
		}else
			this.addWarning("Nessuna configurazione per il tipo Intervento:"+id, "");
		
		return lstAttr;
	}
	
	private List<ErogazioneDettaglioDTO> getListaDtoErogazioni(List<CsIInterventoEseg> lstEseg){
		List<ErogazioneDettaglioDTO> lst = new LinkedList<ErogazioneDettaglioDTO>();
		for(CsIInterventoEseg es : lstEseg){
			ErogazioneDettaglioDTO d = new ErogazioneDettaglioDTO();

			d.setSpesa(new SpesaDTO(es.getSpesa(),es.getPercGestitaEnte(),es.getValoreGestitaEnte(), false));
			d.setCompartecipazione(new CompartecipazioneDTO(es.getCompartUtenti(), es.getCompartSsn(), es.getCompartAltre(), es.getNoteAltreCompart(), false));
			d.setDataErogazione(es.getDataEsecuzione());
			d.setStatoErogazione(es.getStato());
			d.setDescrizione(es.getNote());
			d.setIdInterventoEseg(es.getId());
			
			lst.add(d);
		}
		return lst;
	}
	
	private void setupRowBean(ErogazioneMasterDTO erogazione, String permesso) {
        
		List<CsIInterventoEseg> lstEseg = new LinkedList<CsIInterventoEseg>();
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);
		int attrCount = 0;
		this.setMaster(erogazione);
		this.idIntervento = erogazione.getIdIntervento();
		if(this.idIntervento!=null){
			this.idRow="I"+this.idIntervento;
			bDto.setObj(idIntervento);
			lstEseg = interventoService.getInterventoEsegByIntervento(bDto);
		}else{
			this.idRow="E"+erogazione.getIdInterventoEsegMaster();
			bDto.setObj(erogazione.getIdInterventoEsegMaster());
			lstEseg = interventoService.getInterventoEsegByMasterId(bDto);
		}
		this.lstInterventiEseguiti = this.getListaDtoErogazioni(lstEseg);
		
		this.ultimoCsIInterventoEseg = null;
		if (lstInterventiEseguiti != null && !lstInterventiEseguiti.isEmpty() )
			this.ultimoCsIInterventoEseg = lstEseg.get(0);
	
		this.diarioId = erogazione.getDiarioId();
		
		this.beneficiarioRiferimento = erogazione.getBeneficiarioRiferimento(); 
		this.beneficiari = erogazione.getBeneficiari();
		
		this.idTipoIntervento = erogazione.getTipoIntervento().getId();
		this.idTipoInterventoCustom = erogazione.getIdTipoInterventoCustom();
		
		this.lineaFinanziamento = erogazione.getLineaFinanziamento()!=null ? erogazione.getLineaFinanziamento() : "";
		this.lineaFinanziamento += erogazione.getCodFinanziamento()!=null ? "(cod. "+erogazione.getCodFinanziamento()+")" : "";

		if(this.ultimoCsIInterventoEseg!=null){
			this.opSettore = this.ultimoCsIInterventoEseg.getCsIInterventoEsegMast().getCsOOperatoreSettore();
			this.settoreTitolare = this.getSettoreTitolare();
			this.settoreErogante = this.getSettoreErogante();
		}
		
		this.richiestaIntervento = erogazione.getDataUltimoFlg()!=null;
		this.infoRichiestaIntervento = new ArrayList<SelectItem>();
		
		if(erogazione.getDataRichiestaIntervento()!=null)
			this.infoRichiestaIntervento.add(new SelectItem("Data richiesta", ddMMyyyy.format(erogazione.getDataRichiestaIntervento())));
		if( erogazione.getStatoUltimoFlg()!=null)
			this.infoRichiestaIntervento.add(new SelectItem("Stato richiesta", erogazione.getStatoUltimoFlg()));
		if(erogazione.getDataUltimoFlg()!=null)
			this.infoRichiestaIntervento.add(new SelectItem("Data foglio amministrativo", ddMMyyyy.format(erogazione.getDataUltimoFlg())));
		
		attrCount = getAttributiByTipoInterventoId(idTipoIntervento).size();
		
		setupBtnRendering(permesso);
		setupDettagliTotaleErogazione(lstEseg, attrCount);	 //commentato mk_osmosit
	
}
	
	
/*	private void setupRowBean(CsIIntervento csIIntervento, String permesso) {

		List<CsFlgIntervento> csFlgInterventos = new LinkedList<CsFlgIntervento>(csIIntervento.getCsFlgInterventos());
		List<CsIInterventoEseg> lstInterventiEseguiti = new LinkedList<CsIInterventoEseg>();
		
		int attrCount = 0;
		if (csFlgInterventos.size() > 0) {
			BaseDTO bDto = new BaseDTO();
			fillEnte(bDto);
			bDto.setObj(csIIntervento.getId());
			CsFlgIntervento curCsFlgIntervento = csFlgInterventos.get(0);
			lstInterventiEseguiti = interventoService.getInterventoEsegByIntervento(bDto);
			
			this.ultimoCsIInterventoEseg = null;
			if (lstInterventiEseguiti != null && !lstInterventiEseguiti.isEmpty() )
				this.ultimoCsIInterventoEseg = lstInterventiEseguiti.get(0);
				
			this.diarioId = curCsFlgIntervento.getDiarioId();
			this.idIntervento = csIIntervento.getId();
			
			this.soggettoErogazione = new SoggettoErogazioneBean(curCsFlgIntervento.getCsDDiario().getCsACaso().getCsASoggetto());
			this.tipoIntervento = curCsFlgIntervento.getCsIIntervento().getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento();
			this.idTipoIntervento = this.tipoIntervento.getId();
			this.idTipoInterventoCustom = null;
			if( csIIntervento.getCsIInterventoCustom() != null ) idTipoInterventoCustom = csIIntervento.getCsIInterventoCustom().getId();
			
			if (ultimoCsIInterventoEseg != null) {
				this.dataUltimaErogazione = ultimoCsIInterventoEseg.getDataEsecuzione();
				this.statoUltimaErogazione = ultimoCsIInterventoEseg.getStato().getNome();
				this.opSettore = ultimoCsIInterventoEseg.getCsOOperatoreSettore();
			}

			this.richiestaIntervento = curCsFlgIntervento.getCsDDiario().getDtAmministrativa()!=null;
			this.infoRichiestaIntervento = new ArrayList<SelectItem>();
			
			String statoUltimoFlg = "-";
			if ("A".equals(curCsFlgIntervento.getFlagAttSospC()))
			{
				String value = curCsFlgIntervento.getTipoAttivazione();
				statoUltimoFlg = "Attivazione" + (StringUtils.isNotEmpty(value) ? " ( " + StringUtils.trimToEmpty(value) + " )" : "");
			}
			if ("S".equals(curCsFlgIntervento.getFlagAttSospC()))
			{
				String value = curCsFlgIntervento.getDescrSospensione();
				statoUltimoFlg = "Sospensione" + (StringUtils.isNotEmpty(value) ? " ( " + StringUtils.trimToEmpty(value) + " )" : "");
			}
			if ("C".equals(curCsFlgIntervento.getFlagAttSospC()))
			{   
				CsTbMotivoChiusuraInt motivo = curCsFlgIntervento.getCsTbMotivoChiusuraInt();
				String value = motivo!=null ? motivo.getDescrizione() : null;
				statoUltimoFlg = "Chiusura" + (StringUtils.isNotEmpty(value) ? " ( " + StringUtils.trimToEmpty(value) + " )" : "");
			}
			
			this.infoRichiestaIntervento.add(new SelectItem("Data richiesta", ddMMyyyy.format(csIIntervento.getDtIns())));
			this.infoRichiestaIntervento.add(new SelectItem("Stato richiesta", statoUltimoFlg));
			this.infoRichiestaIntervento.add(new SelectItem("Data foglio amministrativo", ddMMyyyy.format(curCsFlgIntervento.getCsDDiario().getDtAmministrativa())));
	
			attrCount = getAttributiByTipoInterventoId(tipoIntervento.getId()).size();
		}
		
		setupBtnRendering(permesso);
		setupDettagliTotaleErogazione(lstInterventiEseguiti, attrCount);
	}
	
	private void setupRowBean(DatiAggregatiErogazioneDTO aggregato, String permesso) {//TODO: mk_osmosit
	        this.datiAgregatiErogazioneDTO = aggregato;
		    ErogazioneDTO erogazione= aggregato.getErogazioni().get(0);

			lstInterventiEseguiti = new LinkedList<CsIInterventoEseg>();
			BaseDTO bDto = new BaseDTO();
			fillEnte(bDto);
			int attrCount = 0;
			
			this.idIntervento = erogazione.getIdIntervento();
			if(this.idIntervento!=null){
				this.idRow=this.idIntervento;
				bDto.setObj(idIntervento);
				lstInterventiEseguiti = interventoService.getInterventoEsegByIntervento(bDto);
			}else{
				this.idRow=erogazione.getIdInterventoEsegMaster();
				bDto.setObj(erogazione.getIdInterventoEsegMaster());
				lstInterventiEseguiti = interventoService.getInterventoEsegByMasterId(bDto);
			}
			
			this.ultimoCsIInterventoEseg = null;
			if (lstInterventiEseguiti != null && !lstInterventiEseguiti.isEmpty() )
				this.ultimoCsIInterventoEseg = lstInterventiEseguiti.get(0);
		
				
			this.diarioId = erogazione.getDiarioId();
			
			if(erogazione.getSoggetto()!=null)
				this.soggettoErogazione = new SoggettoErogazioneBean(erogazione.getSoggetto());
			else
				this.soggettoErogazione = new SoggettoErogazioneBean(erogazione.getNome(), erogazione.getCognome(), erogazione.getCf());

			this.tipoIntervento = erogazione.getTipoIntervento();
			this.idTipoIntervento = this.tipoIntervento.getId();
			this.idTipoInterventoCustom = null;

			this.dataUltimaErogazione = erogazione.getDataUltimaErogazione();
			this.statoUltimaErogazione = erogazione.getStatoUltimaErogazione();
			if(this.ultimoCsIInterventoEseg!=null)
				this.opSettore = this.ultimoCsIInterventoEseg.getCsIInterventoEsegMast().getCsOOperatoreSettore();
			
			this.richiestaIntervento = erogazione.getDataUltimoFlg()!=null;
			this.infoRichiestaIntervento = new ArrayList<SelectItem>();
			
			if(erogazione.getDataRichiestaIntervento()!=null)
				this.infoRichiestaIntervento.add(new SelectItem("Data richiesta", ddMMyyyy.format(erogazione.getDataRichiestaIntervento())));
			if( erogazione.getStatoUltimoFlg()!=null)
				this.infoRichiestaIntervento.add(new SelectItem("Stato richiesta", erogazione.getStatoUltimoFlg()));
			if(erogazione.getDataUltimoFlg()!=null)
				this.infoRichiestaIntervento.add(new SelectItem("Data foglio amministrativo", ddMMyyyy.format(erogazione.getDataUltimoFlg())));
			
			attrCount = getAttributiByTipoInterventoId(tipoIntervento.getId()).size();
			
			setupBtnRendering(permesso);
			this.dettaglioTotaleErogazione = aggregato.getTotaleErogato(); 
			this.tipoInterventoCustom = aggregato.getTipoInterventoCustom(); 
			//setupDettagliTotaleErogazione(lstInterventiEseguiti, attrCount);	 //commentato mk_osmosit
		
	}

	private void setupRowBean(CsIInterventoEseg csIInterventoEseg, String permesso) {
		this.idIntervento = null;
		this.ultimoCsIInterventoEseg = csIInterventoEseg;

		this.richiestaIntervento=false;
		if (csIInterventoEseg.getCaso() != null)
			this.soggettoErogazione = new SoggettoErogazioneBean(csIInterventoEseg.getCaso().getCsASoggetto());
		else
			this.soggettoErogazione = new SoggettoErogazioneBean(csIInterventoEseg.getSoggettoNome(), csIInterventoEseg.getSoggettoCognome(), csIInterventoEseg.getSoggettoCodiceFiscale());

		this.tipoIntervento = csIInterventoEseg.getCsIInterventoEsegMast().getCsCTipoIntervento();
		this.idTipoIntervento = this.tipoIntervento.getId();
		this.idTipoInterventoCustom = null;
		//TODO frida manca la tabella tipocustom(sinonimo)
		if( csIInterventoEseg.getCsIInterventoEsegMast().getCsIInterventoCustom() != null ) {
			idTipoInterventoCustom = csIInterventoEseg.getCsIInterventoEsegMast().getCsIInterventoCustom().getId();
			this.idTipoInterventoCustom=idTipoInterventoCustom;//TODO frida questo l'ho aggiunto
			//problema nel recupero degli attributi per i tipi interventi non censiti nella CS_CFG_INT_ESEG_ATT_UM
		}
			

		this.dataUltimaErogazione = ultimoCsIInterventoEseg.getDataEsecuzione();
		this.statoUltimaErogazione = ultimoCsIInterventoEseg.getStato().getNome();
		this.opSettore = ultimoCsIInterventoEseg.getCsIInterventoEsegMast().getCsOOperatoreSettore();

		List<CsIInterventoEseg> lstIntEseg = Arrays.asList(csIInterventoEseg);
		int attrCount = getAttributiByTipoInterventoId(tipoIntervento.getId()).size();

		setupBtnRendering(permesso);
		setupDettagliTotaleErogazione(lstIntEseg, attrCount);
	}*/

	private void setupBtnRendering(String permesso) {
		renderBtnEliminaErog = ultimoCsIInterventoEseg != null && ultimoCsIInterventoEseg.getCsIInterventoEsegMast().getCsIIntervento() == null;
		interventoDaErogare = idIntervento != null && ultimoCsIInterventoEseg == null;

		renderBtnEroga = false;
		renderBtnAvviaErog = false;

		if (TipoPermessoErogazioneInterventi.PERMESSO_EROGATIVO.equals(permesso)) {
			if (ultimoCsIInterventoEseg != null)
				renderBtnEroga = ultimoCsIInterventoEseg.getStato().getErogazionePossibile();
		}else if (TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO.equals(permesso)) {
			if (ultimoCsIInterventoEseg == null) renderBtnAvviaErog = true;
			else renderBtnEroga = true;
		}
		
	}

	protected Boolean canCalcTotale( CsIInterventoEsegValore val ) {
		List<IntEsegAttrBean> lstAttr = getAttributiByTipoInterventoId(val.getCsIInterventoEseg().getCsIInterventoEsegMast().getCsCTipoIntervento().getId());
		
		for(IntEsegAttrBean att : lstAttr )
			for(CsCfgAttrUnitaMisura attUm : att.getAttrUnitaMisuras() )
			if( attUm.getId() == val.getCsAttributoUnitaMisura().getId() )
				return att.getCalcTotale();
		
		return false;
	}
	protected void setupDettagliTotaleErogazione(List<CsIInterventoEseg> listIntEseg, int attrCount) {

		dettaglioTotaleErogazione = "-";
		if (listIntEseg == null || listIntEseg.size() == 0)
			return;

		ErogazioneInterventoUtils.SumDTO[] dtos = new ErogazioneInterventoUtils.SumDTO[attrCount];
		//TODO frida valutare se togliere il controllo (if) su dtos 
		if(dtos.length>0){
		for( int i = 0; i < dtos.length; i++ ) dtos[i] = new ErogazioneInterventoUtils.SumDTO();
		
		for (CsIInterventoEseg intEseg : listIntEseg) {
			for (int i = 0; i < intEseg.getCsIInterventoEsegValores().size(); i++) {
				Boolean calcTotale = canCalcTotale( intEseg.getCsIInterventoEsegValores().get(i) );
				InterventoErogazAttrBean valBean = new InterventoErogazAttrBean(intEseg.getCsIInterventoEsegValores().get(i), calcTotale);
				if( valBean.getAttr().getCalcTotale() )
					dtos[i] = ErogazioneInterventoUtils.sum(valBean, dtos[i] );
			}
		}

		List<String> lst = new LinkedList<String>();
		for (int i = 0; i < dtos.length; i++) {
			if( StringUtils.isNotEmpty( dtos[i].getLabel() ) )
				lst.add(String.format("<strong>%s</strong> = %s", dtos[i].getLabel(), dtos[i].getTotaleUnitaMisura()));
		}
		dettaglioTotaleErogazione = CommonUtils.Join("<br/>", lst.toArray());
		}		

	}

	public boolean isRenderBtnEliminaErog() {
		return renderBtnEliminaErog;
	}

	public boolean isInterventoDaErogare() {
		return interventoDaErogare;
	}

	public CsOOperatoreSettore getOpSettore() {
		return opSettore;
	}

	public void setOpSettore(CsOOperatoreSettore opSettore) {
		this.opSettore = opSettore;
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
		return beneficiari;
	}

	public void setBeneficiari(List<SoggettoErogazioneBean> beneficiari) {
		this.beneficiari = beneficiari;
	}

	public SoggettoErogazioneBean getBeneficiarioRiferimento() {
		return beneficiarioRiferimento;
	}

	public Long getIdIntervento() {
		return idIntervento;
	}

	public CsIInterventoEseg getUltimoCsIInterventoEseg() {
		return ultimoCsIInterventoEseg;
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

	public void setIdIntervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}

	public void setIdTipoIntervento(Long idTipoIntervento) {
		this.idTipoIntervento = idTipoIntervento;
	}

	public void setUltimoCsIInterventoEseg(CsIInterventoEseg ultimoCsIInterventoEseg) {
		this.ultimoCsIInterventoEseg = ultimoCsIInterventoEseg;
	}

	public String getIdRow() {
		return idRow;
	}

	public void setIdRow(String idRow) {
		this.idRow = idRow;
	}

	public CsOSettore getSettoreTitolare() {
		return settoreTitolare;
	}

	public CsOSettore getSettoreErogante() {
		return settoreErogante;
	}

	public void setSettoreTitolare(CsOSettore settoreTitolare) {
		this.settoreTitolare = settoreTitolare;
	}

	public void setSettoreErogante(CsOSettore settoreErogante) {
		this.settoreErogante = settoreErogante;
	}

}
