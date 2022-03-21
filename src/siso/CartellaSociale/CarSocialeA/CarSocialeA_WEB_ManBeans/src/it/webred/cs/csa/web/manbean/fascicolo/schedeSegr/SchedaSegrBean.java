package it.webred.cs.csa.web.manbean.fascicolo.schedeSegr;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiSchedeSegr;
import it.webred.cs.data.DataModelCostanti.Scheda;
import it.webred.cs.data.DataModelCostanti.TabUDC;
import it.webred.cs.data.DataModelCostanti.TipoDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsSchedeAltraProvenienza;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.interfaces.ISchedaSegr;
import it.webred.cs.jsf.manbean.ConsensoPrivacyMan;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.OrientamentoLavoro.IOrientamentoLavoro;
import it.webred.cs.json.OrientamentoLavoro.OrientamentoLavoroManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.intermediazione.IIntermediazioneAb;
import it.webred.cs.json.intermediazione.IntermediazioneManBaseBean;
import it.webred.cs.json.mediazioneculturale.IMediazioneCult;
import it.webred.cs.json.mediazioneculturale.MediazioneCultManBaseBean;
import it.webred.cs.json.orientamentoistruzione.IOrientamentoIstruzione;
import it.webred.cs.json.orientamentoistruzione.OrientamentoIstruzioneManBaseBean;
import it.webred.cs.json.serviziorichiestocustom.IServizioRichiestoCustom;
import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomManBaseBean;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.SchedaUdcDTO;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class SchedaSegrBean extends CsUiCompBaseBean implements ISchedaSegr {
	
	protected HashMap<String, String> mappaLabelUDC;
	private SchedaUdcDTO scheda;
	
	private FormazioneLavoroMan formLavoroSegnalato;
	private IStranieri stranieriMan;
	private IAbitazione abitazioneMan;
	private IFamConviventi famConviventiMan;
	
	private CsSchedeAltraProvenienza vistaCasiAltri;	// SISO-938
	
	private ConsensoPrivacyMan consensoPrivacyMan;

	private List<ISchedaValutazione> lstServiziRichiesti;// SISO-438-Possibilità di allegare documenti in UdC
	
	private CsTbCittadinanzaAcq cittadinanzaAcq;
	
	private AmTabComuni comuneSegnalante;
	
	private SsSchedaSessionBeanRemote ssSchedaSegrService = (SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	private	AccessTableSchedaSegrSessionBeanRemote schedaSegrService = (AccessTableSchedaSegrSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
	
	public void initialize(Long sId) {
		
		logger.debug("*** INIZIO chiamata SchedaSegrBean.initialize");
		mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		scheda = null;
		vistaCasiAltri = null;
		comuneSegnalante = new AmTabComuni();
		
/*		lavoroSegnalato = new CsTbCondLavoro();
		professioneSegnalato = new CsTbProfessione();
		tipoFamigliaSegnalato = new CsTbTipologiaFamiliare();*/

		//Serve per la stampa
		if(sId!=null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(sId);
			CsSsSchedaSegr csss = schedaSegrService.findSchedaSegrCreataByIdAnagrafica(dto);
			if(csss != null){
				if(csss.getProvenienza().equals(DataModelCostanti.SchedaSegr.PROVENIENZA_SS)) 
					caricaDettagliSchedaSegr(csss.getSchedaId());
				else 
					caricaDettagliAltri(csss.getSchedaId(), csss.getProvenienza());
			}
		}else{
			logger.warn("SchedaSegrBean- initialize() - IdAnagrafica non valorizzato.");
		}
		
		logger.debug("*** FINE chiamata SchedaSegrBean.initialize");
	}
	
	// SISO-938: action Info per PROVENIENZA == 'SS'
	public void caricaDettagliSchedaSegr(Long idSchedaSegr) {
		logger.debug("INIT caricaDettagliSchedaSegr "+idSchedaSegr);
		try {
			it.webred.ss.ejb.dto.BaseDTO bDto = new it.webred.ss.ejb.dto.BaseDTO();
			fillEnte(bDto);
			bDto.setObj(idSchedaSegr);
			/*Per ricerca noteDiario*/
			bDto.setOrganizzazione(getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getId());
			bDto.setObj2(canReadDiarioSS());
			scheda = ssSchedaSegrService.loadSchedaUdcCompleta(bDto);

			if (scheda != null) {
				if (scheda.getScheda()!=null && 
					scheda.getScheda().getSegnalante() != null && 
					scheda.getScheda().getSegnalante().getComune() != null) {
					comuneSegnalante = luoghiService.getComuneItaByIstat(scheda.getScheda().getSegnalante().getComune());
				}

				SsSchedaSegnalato segnalato = scheda.getSegnalato();
				if (segnalato != null && segnalato.getAnagrafica()!=null) {
					
					formLavoroSegnalato = new FormazioneLavoroMan();
					formLavoroSegnalato.setIdCondLavorativa(segnalato.getLavoro()!=null ? new BigDecimal(segnalato.getLavoro()) : null);
					formLavoroSegnalato.setIdProfessione(segnalato.getProfessione()!=null ? new BigDecimal(segnalato.getProfessione()) : null);
					formLavoroSegnalato.setIdTitoloStudio(segnalato.getTitoloStudioId());
					formLavoroSegnalato.setIdSettoreImpiego(segnalato.getSettImpiegoId());
					
					stranieriMan = super.getSchedaJsonStranieri(idSchedaSegr);
					abitazioneMan = super.getSchedaJsonAbitazione(idSchedaSegr);
					famConviventiMan = super.getSchedaJsonFamConviventi(idSchedaSegr);
							
					List<CsDValutazione> res = getSchedeJsonInterventiCustom(scheda.getScheda());
					lstServiziRichiesti = new ArrayList<ISchedaValutazione>();
					for(CsDValutazione val : res){

						if(TipoDiario.INTERMEDIAZIONE_AB_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IIntermediazioneAb intermediazioneAbMan = (IIntermediazioneAb)IntermediazioneManBaseBean.initByModel(val);
							if (intermediazioneAbMan!=null) {
								lstServiziRichiesti.add(intermediazioneAbMan);
							}
						}
						if(TipoDiario.ORIENTAMENTO_LAVORO_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IOrientamentoLavoro orientamentoLavMan = (IOrientamentoLavoro)OrientamentoLavoroManBaseBean.initByModel(val);
							if (orientamentoLavMan!=null) {
								lstServiziRichiesti.add(orientamentoLavMan);
							}
						}
						if(TipoDiario.MEDIAZIONE_CULT_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IMediazioneCult mediazioneCultMan = MediazioneCultManBaseBean.initByModel(val);
							if (mediazioneCultMan!=null) {
								lstServiziRichiesti.add(mediazioneCultMan);
							} 
						}
						if(TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IOrientamentoIstruzione orientamentoIstruzioneMan = (IOrientamentoIstruzione) OrientamentoIstruzioneManBaseBean.initByModel(val);
							if (orientamentoIstruzioneMan!=null) {
								lstServiziRichiesti.add(orientamentoIstruzioneMan);
							}
						}
						if(TipoDiario.RICHIESTA_SERVIZIO_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IServizioRichiestoCustom iServizioRichiestoCustom = 
									(IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean.initByModel(val, null);
							lstServiziRichiesti.add(iServizioRichiestoCustom);
						}
					}
					
			       // tipoFamigliaSegnalato = this.valorizzaTipoFamiglia(ssSchedaSegnalato.getTipologia_familiare());

			        cittadinanzaAcq = this.valorizzaCittadinanzaAcq(segnalato.getAnagrafica().getCittadinanzaAcq());
				
			        Long orgAccessoId = scheda.getScheda().getAccesso().getSsRelUffPcontOrg().getId().getOrganizzazioneId();
			        boolean beneficiarioRdC = this.verificaBeneficiarioRdC(segnalato.getAnagrafica().getCf());
					consensoPrivacyMan = new ConsensoPrivacyMan(segnalato.getAnagrafica().getCf(), orgAccessoId, segnalato.getAnagrafica().isAnonimo(), beneficiarioRdC);
					consensoPrivacyMan.setSchedaUdcId(idSchedaSegr);
				}
			
			}else{
				logger.warn("Nessuna Scheda UdC con id:"+idSchedaSegr);
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		logger.debug("END caricaDettagliSchedaSegr "+idSchedaSegr);
	}
	
	// SISO-938: action Info per PROVENIENZA == 'SS'
	public void caricaDettagliAltri(Long id, String provenienza) {
		BaseDTO csDto = new BaseDTO();
		fillEnte(csDto);

		csDto.setObj(id);
		csDto.setObj2(provenienza);

		vistaCasiAltri = schedaSegrService.findVistaCasiAltriBySchedaIdProvenienza(csDto);
	}
		
	private boolean canReadNotaDiario(SsDiario nota, String operatoreAccesso){
		return canReadNotaDiario(nota, operatoreAccesso, getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getId());
	}
	
	
	private boolean canReadNotaDiario(SsDiario nota, String operatoreAccesso, Long organizzazioneId){
		String opCorrente = getCurrentOpSettore().getCsOOperatore().getUsername();
		if(nota.getPubblica()) //la nota è pubblica
			return true;
		
		//responsabile dell'organizzazione in cui è stata inserita la nota
		if(isResponsabileSsEnte(nota.getEnte().getCodRouting()))
			return true;
		
		//l'utente che ha scritto la nota è l'operatore corrente
		if(nota.getAutore().equals(opCorrente))
			return true;
		
		//l'operatore che risulta registrato in SS_SCHEDA_ACCESSO è l'utente corrente
		if(operatoreAccesso.equals(opCorrente)) 
			return true;

		//l'operatore possiede il permesso di leggere i DIARI in UDC e si è loggato con la stessa organizzazione di creazione della nota
		if(canReadDiarioSS() && nota.getEnte().getId()== organizzazioneId)
			return true;
		
		return false;
	}
	
	public boolean isRenderSchedaSegr() {
		return checkPermesso(PermessiSchedeSegr.ITEM, PermessiSchedeSegr.VISUALIZZA_SCHEDE_SEGR) && scheda.getScheda() != null;
	}

	public CsSchedeAltraProvenienza getVistaCasiAltri() {
		return vistaCasiAltri;
	}
	
	public void setVistaCasiAltri(CsSchedeAltraProvenienza vistaCasiAltri) {
		this.vistaCasiAltri = vistaCasiAltri;
	}

	@Override
	public SsSchedaSegnalato getSsSchedaSegnalato() {
		return scheda!=null ?  scheda.getSegnalato() : null;
	}

	@Override
	public AmTabComuni getComuneSegnalante() {
		return comuneSegnalante;
	}

	public void setComuneSegnalante(AmTabComuni comuneSegnalante) {
		this.comuneSegnalante = comuneSegnalante;
	}

	private CsTbCittadinanzaAcq valorizzaCittadinanzaAcq(Long  id){
    	try{
    	if(id!=null){
    		BaseDTO d = new BaseDTO();
    		CsUiCompBaseBean.fillEnte(d);
    		d.setObj(id);
    		return confService.getCittadinanzaAcqById(d);
    	}
		
    	} catch(Exception e) {
    		CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
    	return new CsTbCittadinanzaAcq();
    }
	
	public CsTbCittadinanzaAcq getCittadinanzaAcq() {
		return cittadinanzaAcq;
	}

	public void setCittadinanzaAcq(CsTbCittadinanzaAcq cittadinanzaAcq) {
		this.cittadinanzaAcq = cittadinanzaAcq;
	}

	public FormazioneLavoroMan getFormLavoroSegnalato() {
		return formLavoroSegnalato;
	}

	public void setFormLavoroSegnalato(FormazioneLavoroMan formLavoroSegnalato) {
		this.formLavoroSegnalato = formLavoroSegnalato;
	}

	public IStranieri getStranieriMan() {
		return stranieriMan;
	}

	public void setStranieriMan(IStranieri stranieriMan) {
		this.stranieriMan = stranieriMan;
	}

	public IAbitazione getAbitazioneMan() {
		return abitazioneMan;
	}

	public void setAbitazioneMan(IAbitazione abitazioneMan) {
		this.abitazioneMan = abitazioneMan;
	}

	public IFamConviventi getFamConviventiMan() {
		return famConviventiMan;
	}

	public void setFamConviventiMan(IFamConviventi famConviventiMan) {
		this.famConviventiMan = famConviventiMan;
	}
    
	@Override
	public String getStatoCivileSegnalante() {
		return getDescrizioneStatoCivile(scheda.getScheda().getSegnalante().getCodStatoCivile());
	}

	@Override
	public String getStatoCivileRiferimento() {
		return getDescrizioneStatoCivile(scheda.getScheda().getRiferimento().getCodStatoCivile());
	}
	
	@Override
	public String getRelazioneSegnalante() {
		return getDescrizioneRelazione(scheda.getScheda().getSegnalante().getRelazioneId());
	}

	@Override
	public String getRelazioneRiferimento() {
		return getDescrizioneRelazione(scheda.getScheda().getRiferimento().getRelazioneId());
	}

	@Override
	public String getInviatoDaAccesso(){
		return getSettore(getSsScheda()!=null ? getSsScheda().getAccesso().getInviato_da() : null);
	}

	private String getDescrizioneStatoCivile(String codStatoCivile) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if(codStatoCivile!=null){
			dto.setObj(codStatoCivile);
			CsTbStatoCivile csTbStatoCivile = confService.getStatoCivileByCodice(dto);
			return csTbStatoCivile!=null ? csTbStatoCivile.getDescrizione() : "";
		}else return "";
	}
	
	private String getSettore(String codice) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if(codice!=null){
			dto.setObj(new Long(codice));
			CsOSettore tb = confEnteService.getSettoreById(dto);
			return tb!=null ? tb.getNome() : "";
		}
		return null;
	}
	
	private String getDescrizioneRelazione(Long codice) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if(codice!=null){
			dto.setObj(codice);
			CsTbTipoRapportoCon tb = confService.getTipoRapportoConByCodice(dto);
			return tb!=null ? tb.getDescrizione() : "";
		}else return "";
	}
	
	private CsOSettore getDescrizioneSettore(Long codice) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if(codice!=null){
			dto.setObj(codice);
			return confEnteService.getSettoreById(dto);
		}
		return null;
	}
	
	public String getMSG_INFO_NOTA_PRIVATA() {
		String msg = "Le note private possono essere visualizzare dall'utente corrente solo se: ";
		msg +="<ul>";
		msg+="<li>é il responsabile dell'organizzazione in cui è stata inserita la nota</li>";
		msg+="<li>ha creato la scheda</li>";
		msg+="<li>ha inserito la nota</li>";
		msg+="<li>posside il permesso di leggere i diari ed è attualmente autenticato nella stessa oganizzazione in cui è stata inserita la nota.</li>";
		msg+="</ul>";
		return msg;
		
	}

	@Override
	public String getEnteSegnalante() {
		CsOSettore s = getDescrizioneSettore(getSsScheda().getSegnalante().getCsOSettoreId());
		return s!=null ? s.getNome() : getSsScheda().getSegnalante().getEnte_servizio();
	}

	@Override
	public boolean isHideSegnalante() {
		return scheda!=null && getSsScheda()!=null && 
			   Scheda.Interlocutori.UTENTE.equalsIgnoreCase(getSsScheda().getAccesso().getInterlocutore()) && getSsScheda().getSegnalante()==null;
	}

	public String titoloTabRiferimento(SsSchedaRiferimento riferimento, Integer numRiferimento){
		String titolo = "Riferimento " + Integer.toString(numRiferimento);
					
		if (riferimento != null) {
			if (riferimento.getNome() != null
					&& !riferimento.getNome().trim().equals("")
					&& riferimento.getCognome() != null
					&& !riferimento.getCognome().trim().equals("")) {

				titolo = riferimento.getCognome() + " " + riferimento.getNome();
			}
		}
		return titolo;
		
	}
	
	public String relazioneRif(SsSchedaRiferimento riferimento) {
		String relazione = "";
		if (riferimento != null) {
			relazione = getDescrizioneRelazione(riferimento.getRelazioneId());
		}
		return relazione;
	}
	
	public String statoCivileRif(SsSchedaRiferimento riferimento) {
		String statoCivile = "";
		if (riferimento != null) {
			statoCivile = getDescrizioneStatoCivile(riferimento.getCodStatoCivile());
		}
		return statoCivile;
	}
	
	@Override
	public String getLabelAccesso(){
		if(mappaLabelUDC==null) mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.ACCESSO_TAB);
	}
	@Override
	public String getLabelSegnalante(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.SEGNALANTE_TAB);
	}
	@Override
	public String getLabelSegnalato(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.SEGNALATO_TAB);
	}
	@Override
	public String getLabelRiferimento(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.RIFERIMENTO_TAB);
	}
	@Override
	public String getLabelMotivazione(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.MOTIVAZIONE_TAB);
	}
	@Override
	public String getLabelInterventi(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.INTERVENTI_TAB);
	}
	@Override
	public String getLabelChiusura(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.CHIUSURA_TAB);
	}

	public ConsensoPrivacyMan getConsensoPrivacyMan() {
		return consensoPrivacyMan;
	}

	public void setConsensoPrivacyMan(ConsensoPrivacyMan consensoPrivacyMan) {
		this.consensoPrivacyMan = consensoPrivacyMan;
	}
	
	public StreamedContent getFilePrivacy(){
		DatiPrivacyPdfDTO dati = null;
		if(this.scheda!=null && this.scheda.getDatiPrivacyPDF()!=null) {
			dati = scheda.getDatiPrivacyPDF();
		}
		ReportBean bean = (ReportBean)CsUiCompBaseBean.getReferencedBean("ReportBean");
		if(bean==null) bean = new ReportBean(); 
		
		return bean.getStampaPrivacy(dati, this.getLabelSegnalante(), this.getLabelSegnalato(), this.getLabelRiferimento());
	}

	public List<ISchedaValutazione> getLstServiziRichiesti() {
		return lstServiziRichiesti;
	}

	public void setLstServiziRichiesti(List<ISchedaValutazione> lstServiziRichiesti) {
		this.lstServiziRichiesti = lstServiziRichiesti;
	}
	
	@Override
	public boolean servizioRendered(ISchedaValutazione schedaValutazione, String tipo){
		if (tipo.equals("IIntermediazioneAb") && schedaValutazione instanceof IIntermediazioneAb ) {
			return true;
		}
		if (tipo.equals("IOrientamentoLavoro") && schedaValutazione instanceof IOrientamentoLavoro ) {
			return true;
		}
		if (tipo.equals("IMediazioneCult") && schedaValutazione instanceof IMediazioneCult ) {
			return true;
		}
		if (tipo.equals("IOrientamentoIstruzione") && schedaValutazione instanceof IOrientamentoIstruzione ) {
			return true;
		}
		if (tipo.equals("IServizioRichiestoCustom") && schedaValutazione instanceof IServizioRichiestoCustom) {
			return true;
		}
		return false; 
	}

	@Override
	public SchedaUdcDTO getScheda() {
		return scheda;
	}

	public void setScheda(SchedaUdcDTO scheda) {
		this.scheda = scheda;
	}

	@Override
	public SsScheda getSsScheda() {
		return scheda!=null ? scheda.getScheda() : null;
	} 
}
