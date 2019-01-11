package it.webred.cs.csa.web.manbean.scheda.sociali;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaList;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.abitazione.AbitazioneManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.FamiliariManBaseBean;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class DatiSocialiBean extends SchedaValiditaBaseBean implements IDatiValiditaList{
	
	private String nomeTab = "Dati Sociali";
	
	@Override
	public Object getTypeClass() {
		return new CsADatiSociali();
	}
	
	@Override
	public String getTypeComponent() {
		return "pnlDatiSociali";
	}
	
	@Override
	public void nuovo() {
		DatiSocialiComp comp = new DatiSocialiComp(this.getSoggettoId(), casoId);
		comp.setDataInizio(new Date());
		
		try{
			comp.setAbilitaInfoStranieri(isVisPanelStranieri());
			comp.setRenderProblematicaSogg(isVisProblematicaSoggetto());
			verificaCittadinanza(comp);
			
			//Se è la prima pre-compilare i dati a partire dalla scheda del segretariato
		    if(this.listaComponenti==null || listaComponenti.isEmpty())
		    	this.popolaDatiSocialiDaSegretariato(comp);
		    
		}catch(NamingException ne){
			logger.error("Errore recupero dati da "+super.getLabelSegrSociale());
		}
		
		listaComponenti.add(0, comp);
		super.nuovo();

	}
	
	private void verificaCittadinanza(DatiSocialiComp comp){
	  try{	
		AccessTableSoggettoSessionBeanRemote soggettoService = 
				(AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
		
		//Imposto obbligatorietà del pannello stranieri
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj(soggettoId);
		CsAAnagrafica anagrafica = soggettoService.getAnagraficaById(dto);
		if(anagrafica!=null)
		/*	comp.setStranieriRequired(isVisPanelStranieri() && !DataModelCostanti.CITTADINANZA_ITA.equals(anagrafica.getCittadinanza()) && 
                                  	  !DataModelCostanti.CITTADINANZA_ITA.equals(anagrafica.getCittadinanza2())); */
			comp.setStranieriRequired(isVisPanelStranieri() && !DataModelCostanti.CITTADINANZA_ITA.equals(anagrafica.getCittadinanza()));
	  }catch(Exception e){
		  logger.error("Errore verificaCittadinanzaItaliana per soggetto:"+soggettoId);
	  }
		
	}
	
	/**Recupera dal segretariato l'ultima scheda inserita (lista schede segretariato per il soggetto)
	e popola i dati sociali a partire da quelli presenti in segretariato*/
	private void popolaDatiSocialiDaSegretariato(DatiSocialiComp comp) throws NamingException{
		AccessTableSchedaSegrSessionBeanRemote schedaSegrService = (AccessTableSchedaSegrSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
		SsSchedaSessionBeanRemote ssSchedaSegrService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
		
		SchedaSegrDTO dto = new SchedaSegrDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setIdAnagrafica(soggettoId);
		
		List<CsSsSchedaSegr> list = schedaSegrService.getSchedeSegr(dto);
		it.webred.ss.ejb.dto.BaseDTO bDto = new it.webred.ss.ejb.dto.BaseDTO();
		CsUiCompBaseBean.fillEnte(bDto);
		
		if(list.size()>0){
			CsSsSchedaSegr csScheda = list.get(0);
			long schedaId = csScheda.getId();
			bDto.setObj(schedaId);
			SsScheda ssScheda = ssSchedaSegrService.readScheda(bDto);
    		bDto.setObj(ssScheda.getSegnalato());
    		SsSchedaSegnalato segnalato = ssSchedaSegrService.readSegnalatoById(bDto);
    		//bDto.setObj(ssScheda.getSegnalante().getId());
    		
    		if(ssScheda.getAccesso()!=null) 
    			comp.setIdInviante(ssScheda.getAccesso().getInviato_da()!=null ? new BigDecimal(ssScheda.getAccesso().getInviato_da()) : null);
    		
    		 if(segnalato!=null){
    			 comp.valorizzaFormazioneLavoroDaUDC(segnalato);
    			 
                if(CsUiCompBaseBean.isVisPanelStranieri()){
	    			if(comp.isStranieriRequired()){
				   		IStranieri stranieri = this.nuovaDaSchedaJsonStranieri(schedaId);
				   		//duplicare il bean prima di assegnare!!
			    		if(stranieri!=null){
			    			stranieri.setIdSchedaUdc(null);
			    			stranieri.setIdCaso(casoId);
			    			comp.setStranieriMan(stranieri);
			    		}
	    			}
	    			
	    			/*ABITAZIONE*/
	    			IAbitazione abitazione = this.nuovaDaSchedaJsonAbitazione(schedaId);
	    			//duplicare il bean prima di assegnare!!
	    			if(abitazione!=null){
	    				abitazione.setIdSchedaUdc(null);
	    				abitazione.setIdCaso(casoId);
	    				comp.setAbitazioneMan(abitazione);
	    			}
	    			
	    			/*FAMILIARI CONVIVENTI*/
	    			IFamConviventi famConviventi = this.nuovaDaSchedaJsonFamiliari(schedaId);
	    			//duplicare il bean prima di assegnare!!
	    			if(famConviventi!=null){
	    				famConviventi.setIdSchedaUdc(null);
	    				famConviventi.setIdCaso(casoId);
	    				comp.setFamConviventiMan(famConviventi);
	    			}
	    			
    		    }else{
    			   //Temporaneamente recupero i dati precedentemente inseriti via UDC (vecchi campi) - andrà rimossa
 /*   		      comp.setCodStatus(segnalato.getStatus() != null ? new BigDecimal(segnalato.getStatus()):null);
       			  comp.setCodPermesso(segnalato.getPermesso() != null ? new BigDecimal(segnalato.getPermesso()):null);
       			  comp.setStranieroNonAcc(segnalato.getStranieroNAcc() != null ? new Boolean(segnalato.getStranieroNAcc()) : false);
     			  comp.setIdTipologiaFam(segnalato.getTipologia_familiare() != null ? new BigDecimal(segnalato.getTipologia_familiare()): null);*/

    		    }
    			
    			 //TODO: Valorizzare gli altri parametri
    		 }
		}
		
		
	}
	
	@Override
	public CsADatiSociali getCsFromComponente(Object obj) {
		
		DatiSocialiComp comp = (DatiSocialiComp) obj;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		
		CsADatiSociali cs = new CsADatiSociali();
		if(comp.getId() != null){
			//update
			dto.setObj(comp.getId());
			cs = schedaService.getDatiSocialiById(dto);
		} else {
			//nuovo
			CsASoggettoLAZY sogg = new CsASoggettoLAZY();
			sogg.setAnagraficaId(soggettoId);
			cs.setCsASoggetto(sogg);
		}
		
		cs.setIdCaricoA(comp.getIdCaricoA());
		cs.setAutosufficienza(comp.getAutosufficienza());
		
		//cs.setPatologia(comp.getPatologia());
		/*if(comp.getPatologia().equals("Altro") && comp.getPatologiaAltro()!=null)
			cs.setPatologia("Altro:"+comp.getPatologiaAltro());*/
		//cs.setInterventiSuNucleo(comp.isInterventiNucleo()?"1":"0");
		cs.setInvianteId(comp.getIdInviante());
		cs.setInviatoAId(comp.getIdInviatoA());
/*		if(comp.getnMinori() != null)
			cs.setnMinori(new BigDecimal(comp.getnMinori()));*/
		cs.setProblematicaId(comp.getIdProblematica());
		cs.setProblematicaNucleoId((comp.getIdProblematicaNucleo()!=null && comp.getIdProblematicaNucleo().intValue()>0) ?comp.getIdProblematicaNucleo() : null);
		
		//cs.setStesuraRelazioniPerId(comp.getIdStesuraRelPer());
		//cs.setTipoInterventi(comp.getInterventiTipo());
		
		/*JSON FAMILIARI CONVIVENTI*/
		/*cs.setTipologiaFamiliareId(comp.getIdTipologiaFam());
		cs.setGrVulnerabileId(comp.getIdGrVulnerabile());*/
		
		//cs.setTipoContrattoId(comp.getIdTipoContratto());
		
		comp.valorizzaFormazioneLavoroJpa(cs);

		//TODO STRANIERI 
/*		cs.setStranieroNonAcc(comp.getStranieroNonAcc()?"1":"0");
		cs.setStatus(comp.getCodStatus());
		cs.setPermesso(comp.getCodPermesso());*/
		/*end stranieri*/
		
		
		/*Dati Tutela*/
		ComponenteAltroMan sostegno = comp.getSostegno();
		cs.setSostegnoDenominazione(sostegno.getCompDenominazione());
		cs.setSostegnoCitta(sostegno.getCompCitta());
		cs.setSostegnoTel(sostegno.getCompTelefono());	
		cs.setSostegnoIndirizzo(sostegno.getCompIndirizzo());
		cs.setSostegnoComponente(sostegno.getComponenteSel());
		
		ComponenteAltroMan curatela = comp.getCuratela();
		cs.setCuratelaDenominazione(curatela.getCompDenominazione());
		cs.setCuratelaCitta(curatela.getCompCitta());
		cs.setCuratelaTel(curatela.getCompTelefono());	
		cs.setCuratelaIndirizzo(curatela.getCompIndirizzo());
		cs.setCuratelaComponente(curatela.getComponenteSel());
		
		ComponenteAltroMan tutela = comp.getTutela();
		cs.setTutelaDenominazione(tutela.getCompDenominazione());
		cs.setTutelaCitta(tutela.getCompCitta());
		cs.setTutelaTel(tutela.getCompTelefono());	
		cs.setTutelaIndirizzo(tutela.getCompIndirizzo());
		cs.setTutelaComponente(tutela.getComponenteSel());
		
		cs.setAffidamento(comp.isAffServSociali());
		
		cs.setDataInizioApp(comp.getDataInizio());
		if(comp.getDataInizio() == null)
			cs.setDataInizioApp(new Date());
		cs.setDataFineApp(comp.getDataFine());
		if(comp.getDataFine() == null)
			cs.setDataFineApp(DataModelCostanti.END_DATE);
			
		return cs;
		
	}
		
	@Override
	public DatiSocialiComp getComponenteFromCs(Object obj) {
		
		CsADatiSociali cs = (CsADatiSociali) obj;
		
		DatiSocialiComp comp = new DatiSocialiComp(cs.getCsASoggetto().getAnagraficaId(), casoId);
		comp.setAbilitaInfoStranieri(isVisPanelStranieri());
		comp.setRenderProblematicaSogg(isVisProblematicaSoggetto());
		
		comp.setIdCaricoA(cs.getIdCaricoA());
		comp.setIdInviante(cs.getInvianteId());
		comp.setIdInviatoA(cs.getInviatoAId());
		comp.setIdProblematica(cs.getProblematicaId());
		comp.setIdProblematicaNucleo(cs.getProblematicaNucleoId());
		comp.valorizzaFormazioneLavoroDaCS(cs);
		
	/*	comp.setIdTipologiaFam(cs.getTipologiaFamiliareId());
		comp.setIdGrVulnerabile(cs.getGrVulnerabileId());
		comp.setCodStatus(cs.getStatus());
		comp.setCodPermesso(cs.getPermesso());
		comp.setStranieroNonAcc("1".equals(cs.getStranieroNonAcc()));*/
		
		
		comp.setSostegno(fillComponente(cs.getSostegnoComponente(), cs.getSostegnoDenominazione(), cs.getSostegnoIndirizzo(), cs.getSostegnoCitta(), cs.getSostegnoTel(),cs.getDataInizioApp()));
		comp.setCuratela(fillComponente(cs.getCuratelaComponente(), cs.getCuratelaDenominazione(), cs.getCuratelaIndirizzo(), cs.getCuratelaCitta(), cs.getCuratelaTel(),cs.getDataInizioApp()));
		comp.setTutela(fillComponente(cs.getTutelaComponente(), cs.getTutelaDenominazione(), cs.getTutelaIndirizzo(), cs.getTutelaCitta(), cs.getTutelaTel(),cs.getDataInizioApp()));
		comp.setAffServSociali(cs.getAffidamento()!=null ? cs.getAffidamento() : false);
		
		
		comp.setAutosufficienza(cs.getAutosufficienza());
		/*if(cs.getPatologia()!=null && cs.getPatologia().startsWith("Altro:")){
			comp.setPatologia("Altro");
			comp.setPatologiaAltro(cs.getPatologia().replaceFirst("Altro:", ""));
		}else
			comp.setPatologia(cs.getPatologia());*/
		//comp.setInterventiNucleo("1".equals(cs.getInterventiSuNucleo()));
		//comp.setInterventiTipo(cs.getTipoInterventi());
		/*if(cs.getnMinori() != null)
			comp.setnMinori(cs.getnMinori().intValue());*/
		
		comp.setDataInizio(cs.getDataInizioApp());
		comp.setDataFine(cs.getDataFineApp());
		comp.setId(cs.getId());
	
		this.verificaCittadinanza(comp);
	
		if(comp.isStranieriRequired()){
			IStranieri straMan;
			try {
				straMan = StranieriManBaseBean.initByModel(cs.getStraniero());
			if(straMan!=null)
				comp.setStranieriMan(straMan);
			comp.getStranieriMan().setIdCaso(this.casoId);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		if(isVisPanelStranieri()){
			IAbitazione abiMan;
			try {
				abiMan = AbitazioneManBaseBean.initByModel(cs.getAbitazione());
			if(abiMan!=null)
				comp.setAbitazioneMan(abiMan);
			comp.getAbitazioneMan().setIdCaso(this.casoId);
			} catch (Exception e) {
				logger.error(e);
			}
			
			IFamConviventi famMan;
			try {
				famMan = FamiliariManBaseBean.initByModel(cs.getFamiliariConviventi());
			if(famMan!=null)
				comp.setFamConviventiMan(famMan);
			comp.getFamConviventiMan().setIdCaso(this.casoId);
			} catch (Exception e) {
				logger.error(e);
			}
			
		}
		return comp;
		
	}
	
	private ComponenteAltroMan fillComponente(CsAComponente comp, String denom, String indirizzo, String citta, String tel, Date dtRif){
		
		ComponenteAltroMan componente = new ComponenteAltroMan(this.getSoggettoId());
		
		//Valorizzo dati componente familiare
		componente.setCompIndirizzo(indirizzo);
		componente.setCompCitta(citta);
		componente.setCompDenominazione(denom);
		componente.setCompTelefono(tel);
		if(citta!=null){
			int index = citta.lastIndexOf('-');
			String scitta = citta.substring(0,index);
			String sprov = citta.substring(index+1);
			componente.setComuneResidenzaMan(scitta, sprov);
		}
		componente.setIdComponente(comp!=null ? comp.getId() : null);
		componente.setDtRif(dtRif);
		return componente;
	}
	
	
	@Override
	public boolean validaComponenti() {
		boolean ok = true;
		
		for(ValiditaCompBaseBean comp: listaComponenti) {
			DatiSocialiComp disComp = (DatiSocialiComp) comp;
				
			if(CsUiCompBaseBean.isVisProblematicaSoggetto() && (disComp.getIdProblematica() == null || disComp.getIdProblematica().intValue() == 0)) {
				ok = false;
				this.addErrorCampiObbligatori(nomeTab, "Problematica", "");
			}

			if(!disComp.getFormLavoroMan().validaData())
				ok=false;
			
			if(CsUiCompBaseBean.isVisPanelStranieri()){
				if(disComp.isStranieriRequired() &&  !disComp.getStranieriMan().validaData())
					ok=false;
				
				if(!disComp.getAbitazioneMan().validaData())
					ok=false;
				
				if(!disComp.getFamConviventiMan().validaData())
					ok=false;
			}
				
		/*if(disComp.getIdStesuraRelPer() == null || disComp.getIdStesuraRelPer().intValue() == 0) {
				ok = false;
				addError("Il campo Stesura relazioni per è obbligatorio", "");
			}*/
		}
		
		return ok;
		
	}

	@Override
	public String getCodiceTab() {
		return "DS";
	}
	
	@Override
	public CsADatiSociali salvaJsonCollegati(ValiditaCompBaseBean obj, Object csobj){
		DatiSocialiComp comp = (DatiSocialiComp) obj;
		CsADatiSociali cs = (CsADatiSociali) csobj;
		if(CsUiCompBaseBean.isVisPanelStranieri()){
			if(comp.isStranieriRequired()){
				comp.getStranieriMan().save();
				CsDValutazione valStra = comp.getStranieriMan().getCurrentModel();
				cs.setStraniero(valStra);
			}else
				cs.setStraniero(null);
			
			comp.getAbitazioneMan().save();
			cs.setAbitazione(comp.getAbitazioneMan().getCurrentModel());
			
			comp.getFamConviventiMan().save();
			cs.setFamiliariConviventi(comp.getFamConviventiMan().getCurrentModel());
		}else{
			cs.setAbitazione(null);
			cs.setStraniero(null);
			cs.setFamiliariConviventi(null);
		}
		return cs;
		
	}
	
	@Override
	public DatiSocialiComp duplicaJsonCollegati(ValiditaCompBaseBean origine, ValiditaCompBaseBean destinazione){
		DatiSocialiComp compO = (DatiSocialiComp)origine;
		DatiSocialiComp compD = (DatiSocialiComp)destinazione;
		
		if(CsUiCompBaseBean.isVisPanelStranieri()){
			IStranieri straO = compO.getStranieriMan();
			IAbitazione abiO = compO.getAbitazioneMan();
			IFamConviventi famO = compO.getFamConviventiMan();
			
			IStranieri straD = StranieriManBaseBean.initByVersion(null);
			IAbitazione abiD = AbitazioneManBaseBean.initByVersion(null);
			IFamConviventi famD = FamiliariManBaseBean.initByVersion(null);
			
			try{
				
				straD = StranieriManBaseBean.init(straO);				
				abiD = AbitazioneManBaseBean.init(abiO);
				famD = FamiliariManBaseBean.init(famO);
				
			}catch(Exception e){
				logger.error("Errore duplicazione JSON");
			}
			
			compD.setStranieriMan(straD);
			compD.setAbitazioneMan(abiD);
			compD.setFamConviventiMan(famD);
			
			compD.getStranieriMan().setIdCaso(this.casoId);
			compD.getAbitazioneMan().setIdCaso(this.casoId);
			compD.getFamConviventiMan().setIdCaso(this.casoId);
		}
		return compD;
	}

	
	@Override
	public void eliminaJsonCollegati(ValiditaCompBaseBean obj){
		DatiSocialiComp comp = (DatiSocialiComp)obj;
		comp.getStranieriMan().elimina();
		comp.getAbitazioneMan().elimina();
		comp.getFamConviventiMan().elimina();
	}

	@Override
	protected boolean validaNumeroSituazioniAperte() {
		if(this.getNumeroSituazioniAperte()>1) {
			addWarningFromProperties("warn.numero.situazioniDS.aperte");
			return false;
		}
		return true;
	}
}
