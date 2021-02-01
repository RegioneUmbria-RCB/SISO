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
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoUtils;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoUtils.SumDTO;
import it.webred.cs.jsf.bean.erogazioneIntervento.InterventoErogazAttrBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.utilities.CommonUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	
	private CsOOperatoreSettore opSettore;

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
	private CsOSettore settoreTitolare;
	private boolean servizioAmbito;
	private CsOSettore settoreErogante;
	/* == SISO-663+664 SM == */
	private CsOSettore settoreGestore;
	private String descrizioneProgetto;
	/* --===-- */
	
	private ErogazioneMasterDTO master;
	
	private CsIInterventoEseg ultimoCsIInterventoEseg;
	
	private List<ErogazioneDettaglioDTO> lstInterventiEseguiti;
	
	private String sommaUnitaMisura;
	//SISO-748
	private Long diarioPaiId;
	
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");

	public ErogInterventoRowBean(ErogazioneMasterDTO intervento) {
		setupRowBean(intervento);
	}

	private List<IntEsegAttrBean> getAttributiByTipoInterventoId( Long id, CsCfgIntEsegStato stato ) {
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);
		bDto.setObj(id); 
		
		List<IntEsegAttrBean> lstAttr = new LinkedList<IntEsegAttrBean>();
		HashMap<Long, ErogStatoCfgDTO> mappa = interventoService.findConfigIntEsegByTipoIntervento(bDto);
			
		if(mappa.isEmpty())
			this.addWarning("Nessuna configurazione per il tipo Intervento:"+id, "");
		else{
			if(stato!=null) lstAttr = mappa.get(stato.getId()).getListaAttributi();
		}
		
		return lstAttr;
	}
	
	private List<ErogazioneDettaglioDTO> getListaDtoErogazioni(List<CsIInterventoEseg> lstEseg){
		List<ErogazioneDettaglioDTO> lst = new LinkedList<ErogazioneDettaglioDTO>();
		BigDecimal sommaEntita = BigDecimal.ZERO;
		BigDecimal sommaEntitaOreMinuti = BigDecimal.ZERO;//SIO-806 per calcolare i ValQuota dell'unità di misura ore/minuti
		
		this.sommaUnitaMisura = "";
		for(CsIInterventoEseg es : lstEseg){
			ErogazioneDettaglioDTO d = new ErogazioneDettaglioDTO();
			
			d.setSpesa(new SpesaDTO(es.getSpesa(),es.getPercGestitaEnte(),es.getValoreGestitaEnte(), false));
			d.setCompartecipazione(new CompartecipazioneDTO(es.getCompartUtenti(), es.getCompartSsn(), es.getCompartAltre(), es.getNoteAltreCompart(), false));
			d.setDataErogazione(es.getDataEsecuzione());
			d.setDataErogazioneA(es.getDataEsecuzioneA()); //SISO-556
			d.setStatoErogazione(es.getStato());
			d.setDescrizione(es.getNote());
			d.setIdInterventoEseg(es.getId());
			//SISO-859 Sommo tutti i valori di valQuota che non sono € per tutte le precedenti erogazioni
			if (es.getCsIValQuota() != null && !es.getCsIInterventoEsegMast().getCsIQuota().isValuta() && !es.getCsIInterventoEsegMast().getCsIQuota().isOreMinuti()){
				BigDecimal valQuota =  es.getCsIValQuota().getValQuota() != null ?   es.getCsIValQuota().getValQuota() : BigDecimal.ZERO ;
				sommaEntita = sommaEntita.add(valQuota);
				if(sommaUnitaMisura.isEmpty())
					this.sommaUnitaMisura = es.getCsIInterventoEsegMast().getCsIQuota().getCsTbUnitaMisura().getValore();
				
				d.setEntita(valQuota.intValue() >0 ? valQuota.toString().concat("  ").concat(sommaUnitaMisura) : null);
			}else if(es.getCsIInterventoEsegMast().getCsIQuota().isOreMinuti()) {
				this.sommaUnitaMisura = es.getCsIInterventoEsegMast().getCsIQuota().getCsTbUnitaMisura().getValore();
				BigDecimal valQuota =  es.getCsIValQuota().getValQuota() != null ?   es.getCsIValQuota().getValQuota() : BigDecimal.ZERO ;
				
				sommaEntitaOreMinuti = sommaEntitaOreMinuti.add(valQuota);
				
				int ore = valQuota.intValue();
				BigDecimal minutiValQuota = valQuota.remainder(BigDecimal.ONE);
				BigDecimal convMinutiValQuota = (minutiValQuota.multiply(new BigDecimal(60))).setScale(2, RoundingMode.HALF_UP);
				//
				int minuti = convMinutiValQuota.intValue();
				
				String strDate = StringUtils.leftPad(String.valueOf(ore), 2, "0")  + ":" + StringUtils.leftPad(String.valueOf(minuti),  2, "0");
					
				
				d.setEntita(valQuota.intValue() >0 ? strDate.concat("  ").concat(sommaUnitaMisura) : null);
				
				
			}
			
			lst.add(d);
		}
		
		
		if(sommaEntita.compareTo(BigDecimal.ZERO) != 0  ) {
			this.sommaUnitaMisura = "Tot. " + sommaUnitaMisura.concat("  ").concat(sommaEntita.toString());
		}else if (sommaEntitaOreMinuti.compareTo(BigDecimal.ZERO) != 0  ) {
			//SISO_806 devo converitire la somma dei Valquota per OreMinuti
			int ore = sommaEntitaOreMinuti.intValue();
			
			BigDecimal minutiDaSomma = sommaEntitaOreMinuti.remainder(BigDecimal.ONE);
			BigDecimal convMinuti = (minutiDaSomma.multiply(new BigDecimal(60))).setScale(0, BigDecimal.ROUND_HALF_UP);
			int minuti = convMinuti.intValue();
			
			String oreMinutiString = String.valueOf(ore) + ":" +  String.valueOf(minuti)  ;
			
			this.sommaUnitaMisura = "Tot. " + sommaUnitaMisura.concat("  ").concat(oreMinutiString);
		}
			
		return lst;
	}
	
	private void setupRowBean(ErogazioneMasterDTO erogazione) {
        
		List<CsIInterventoEseg> lstEseg = new LinkedList<CsIInterventoEseg>();
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);
		HashMap<Long, ErogStatoCfgDTO> mappaAttributi = new HashMap<Long, ErogStatoCfgDTO>();
		this.master = erogazione;
		this.idIntervento = erogazione.getIdIntervento();
		Long idMaster = erogazione.getIdInterventoEsegMaster();
		if(this.idIntervento!=null){
			this.idRow="I"+this.idIntervento;
			if(idMaster==null){
				bDto.setObj(idIntervento);
				CsIInterventoEsegMast master = interventoService.getCsIInterventoEsegMastByByInterventoId(bDto);
				idMaster = master!=null ? master.getId() : null;
			}
			//lstEseg = interventoService.getInterventoEsegByIntervento(bDto);
		}else{
			this.idRow="E"+erogazione.getIdInterventoEsegMaster();
			idMaster = erogazione.getIdInterventoEsegMaster();
			//lstEseg = interventoService.getInterventoEsegByMasterId(bDto);
		}
		
		bDto.setObj(idMaster);
		bDto.setObj2(true); //loadDettagli
		lstEseg = interventoService.getInterventoEsegByMasterId(bDto);
		this.lstInterventiEseguiti = this.getListaDtoErogazioni(lstEseg);
		
		this.ultimoCsIInterventoEseg = null;
		if (lstInterventiEseguiti != null && !lstInterventiEseguiti.isEmpty() )
			this.ultimoCsIInterventoEseg = lstEseg.get(0);
	
		this.diarioId = erogazione.getDiarioId();
		
		this.beneficiarioRiferimento = erogazione.getBeneficiarioRiferimento(); 
		this.altriBeneficiari = erogazione.getBeneficiariNonRiferimento();
		//this.beneficiari = erogazione.getBeneficiari();
		
		this.idTipoIntervento = erogazione.getTipoIntervento().getId();
		this.idTipoInterventoCustom = erogazione.getIdTipoInterventoCustom();
		this.idCatSociale = erogazione.getIdCategoriaSociale()!=null ? erogazione.getIdCategoriaSociale().longValue() : null;
		
		this.lineaFinanziamento = erogazione.getLineaFinanziamento()!=null ? erogazione.getLineaFinanziamento() : "";
		this.lineaFinanziamento += erogazione.getCodFinanziamento()!=null ? "(cod. "+erogazione.getCodFinanziamento()+")" : "";

		if(this.ultimoCsIInterventoEseg!=null){
			this.opSettore = this.ultimoCsIInterventoEseg.getCsIInterventoEsegMast().getCsOOperatoreSettore();
			
			CsIInterventoPr progetto = ultimoCsIInterventoEseg.getCsIInterventoEsegMast().getCsIInterventoPr();
			if(progetto!=null) {
				this.settoreTitolare = progetto.getSettoreTitolare();
				this.servizioAmbito = progetto.getServizioAmbito();
				/* == SISO-663+664 SM == */
				this.settoreGestore = progetto.getSettoreGestore();
				this.descrizioneProgetto = progetto.getFfProgettoDescrizione();
				/* --===-- */
				//SISO-964 Modello POR solo per progetti FSE
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(this.descrizioneProgetto);
				dto.setObj2(this.idTipoIntervento);
				dto.setObj3(this.idTipoInterventoCustom);
				dto.setObj4(this.idCatSociale);
				String	codiceForm = confService.findCodFormProgetto(dto);
				
				if(!StringUtils.isBlank(this.getModuloPorRegionale())) 
					this.setRenderBtnStampaPor("FSE".equalsIgnoreCase(codiceForm));
			}
			this.settoreErogante = this.ultimoCsIInterventoEseg.getCsIInterventoEsegMast().getSettoreErogante();
			
			//Attributi dell'ultimo stato
			//attrCount = getAttributiByTipoInterventoId((idTipoInterventoCustom!=null ? idTipoInterventoCustom : idTipoIntervento), ultimoCsIInterventoEseg.getStato()).size();
		    
			bDto.setObj((idTipoInterventoCustom!=null ? idTipoInterventoCustom : idTipoIntervento)); 
			List<IntEsegAttrBean> lstAttr = new LinkedList<IntEsegAttrBean>();
			mappaAttributi = interventoService.findConfigIntEsegByTipoIntervento(bDto);
		}
		
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
		setupDettagliTotaleErogazione(lstEseg, mappaAttributi);	 //commentato mk_osmosit
	
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

	private void setupBtnRendering() {
		boolean foglioNonPresente = idIntervento==null || idIntervento<=0;
		renderBtnEliminaErog = ultimoCsIInterventoEseg != null && foglioNonPresente && CsUiCompBaseBean.isPermessoAutorizzativo();
		interventoDaErogare = idIntervento != null && ultimoCsIInterventoEseg == null;

		renderBtnEroga = false;
		renderBtnAvviaErog = false;

		if (CsUiCompBaseBean.isPermessoErogativo()) {
			if (ultimoCsIInterventoEseg != null)
				renderBtnEroga = ultimoCsIInterventoEseg.getStato().getErogazionePossibile();
		}else if (CsUiCompBaseBean.isPermessoAutorizzativo()) {
			if (ultimoCsIInterventoEseg == null) renderBtnAvviaErog = true;
			else renderBtnEroga = true;
		}
		
	}

	protected Boolean canCalcTotale( CsIInterventoEsegValore val) {
		CsIInterventoEseg eseg = val.getCsIInterventoEseg();
		Long tipoInterventoId = eseg.getCsIInterventoEsegMast().getCsCTipoIntervento().getId();
		Long tipoInterventoCustom = eseg.getCsIInterventoEsegMast().getCsIInterventoCustom()!=null ?  eseg.getCsIInterventoEsegMast().getCsIInterventoCustom().getId() : null;
		List<IntEsegAttrBean> lstAttr = getAttributiByTipoInterventoId(tipoInterventoCustom!=null ? tipoInterventoCustom : tipoInterventoId, eseg.getStato());
		
		
		Boolean canCalc = null;
		int i = 0;
		while( canCalc==null && i<lstAttr.size()){
			IntEsegAttrBean uu = lstAttr.get(i);
			canCalc = uu.getCalcTotale_ifMatchingAttUM(val.getCsAttributoUnitaMisura().getId());
			i++;
		}
		
		return canCalc!= null ? canCalc : false;
	}
	protected void setupDettagliTotaleErogazione(List<CsIInterventoEseg> listIntEseg, HashMap<Long, ErogStatoCfgDTO>  mappa) {

		dettaglioTotaleErogazione = "-";
		if (listIntEseg == null || listIntEseg.size() == 0)
			return;

		HashMap<String, SumDTO> mappaSomme = new HashMap<String, SumDTO>();
		
		for (CsIInterventoEseg intEseg : listIntEseg) {
			for (int i = 0; i < intEseg.getCsIInterventoEsegValores().size(); i++) {
				Boolean calcTotale = canCalcTotale( intEseg.getCsIInterventoEsegValores().get(i) );
				InterventoErogazAttrBean valBean = new InterventoErogazAttrBean(intEseg.getCsIInterventoEsegValores().get(i), calcTotale);
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
	 //FINE SISO-556
	
	/* == SISO-663 SM == */
	public CsOSettore getSettoreGestore() { return settoreGestore; }
	public void setSettoreGestore(CsOSettore settoreGestore) { this.settoreGestore = settoreGestore; }
	/* --===-- */

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
