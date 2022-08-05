package it.webred.cs.csa.web.manbean.scheda.sociali;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruInputDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruResultDTO;
import it.webred.cs.csa.ejb.dto.siru.StampaFseDTO;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaCompUtils;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.DatiSociali.TIPO_PROTEZIONE_GIURIDICA;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsAProtezioneGiuridica;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.jsf.bean.DatiAnaBean;
import it.webred.cs.jsf.bean.ProtezioneGiuridicaBean;
import it.webred.cs.jsf.interfaces.IDatiSociali;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.jsf.manbean.por.DatiPorMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.abitazione.AbitazioneManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.FamiliariManBaseBean;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ss.data.model.SsSchedaSegnalato;

public class DatiSocialiComp extends SchedaValiditaCompUtils implements IDatiSociali{
	
	private String VER_MAX = "";
	private boolean abilitaInfoStranieri;
	private boolean renderProblematicaSogg;
	
	//private BigDecimal idTipologiaFam;
	//private Integer nMinori;
	private BigDecimal idInviante;
	private BigDecimal idInviatoA;
	private BigDecimal idProblematica;
	private BigDecimal idProblematicaNucleo;
	
	private BigDecimal idCaricoA;

	private boolean interventiNucleo;
	private String interventiTipo;
	private String autosufficienza;
	private String patologia;
	private String patologiaAltro;
	
	private BigDecimal idTipoContratto;

	private List<SelectItem> lstInviante;
	private List<SelectItem> lstInviatoA;
	private List<SelectItem> lstCaricoA;
	
	private List<SelectItem> lstProblematiche;
	private List<SelectItem> lstProblematicheNucleo;
	private List<SelectItem> lstStesuraRelPer;

	private List<SelectItem> lstTipoContratto;
	
	private FormazioneLavoroMan formLavoroMan;
	
	private DatiPorMan datiPorMan;

	private StampaFseDTO datiProgettoBean;
	private Long idXStampa  = 0l;
	private Long idCs;
	
	private boolean stranieriRequired=false;
	
	private IStranieri  stranieriMan;
	private IAbitazione abitazioneMan;
	private IFamConviventi famConviventiMan;
	
	/*Dati Tutela*/
	private List<CsAComponente> listaComponenti;
	private Hashtable<String,ProtezioneGiuridicaBean> protezioneGiuridica;
	private boolean affServSociali=false;  
	
	public DatiSocialiComp(Long idSoggetto, Long idCaso){
		super();
	
		/*Riuso la lista di parenti per ottimizzare i tempi di caricamento*/
		listaComponenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
		init(idSoggetto);
		
		try {
			stranieriMan  = StranieriManBaseBean.initByVersion(VER_MAX);
			abitazioneMan = AbitazioneManBaseBean.initByVersion(VER_MAX);
			famConviventiMan = FamiliariManBaseBean.initByVersion(VER_MAX);
			if(idCaso!=null){
				stranieriMan.setIdCaso(idCaso);
				abitazioneMan.setIdCaso(idCaso);
				famConviventiMan.setIdCaso(idCaso);
			}
			
			this.initDatiPorMan(null, idCaso, null);
			
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public DatiSocialiComp(Long idSoggetto, Long idCaso, Date dtInizio, Date dtFine){
		super();
		setDataInizio(dtInizio);
		setDataFine(dtFine);
		
		/*Riuso la lista di parenti per ottimizzare i tempi di caricamento*/
		listaComponenti = CsUiCompBaseBean.caricaParenti(idSoggetto, getDataFine());
		
		init(idSoggetto);
	}
	
	public void init(Long idSoggetto){
		stranieriRequired=false;
		formLavoroMan = new FormazioneLavoroMan();
		
		this.protezioneGiuridica = new Hashtable<String, ProtezioneGiuridicaBean>();
		ProtezioneGiuridicaBean sostegno = new ProtezioneGiuridicaBean(idSoggetto, TIPO_PROTEZIONE_GIURIDICA.SOSTEGNO.getCodice(), listaComponenti);
		ProtezioneGiuridicaBean curatela = new ProtezioneGiuridicaBean(idSoggetto, TIPO_PROTEZIONE_GIURIDICA.CURATELA.getCodice(), listaComponenti);
		ProtezioneGiuridicaBean tutela = new ProtezioneGiuridicaBean(idSoggetto, TIPO_PROTEZIONE_GIURIDICA.TUTELA.getCodice(), listaComponenti);
		protezioneGiuridica.put(TIPO_PROTEZIONE_GIURIDICA.SOSTEGNO.getCodice(),sostegno);
		protezioneGiuridica.put(TIPO_PROTEZIONE_GIURIDICA.TUTELA.getCodice(),tutela);
		protezioneGiuridica.put(TIPO_PROTEZIONE_GIURIDICA.CURATELA.getCodice(),curatela);
	}

	@Override
	public boolean isInterventiNucleo() {
		return interventiNucleo;
	}

	public void setInterventiNucleo(boolean interventiNucleo) {
		this.interventiNucleo = interventiNucleo;
	}

	@Override
	public String getInterventiTipo() {
		return interventiTipo;
	}

	public void setInterventiTipo(String interventiTipo) {
		this.interventiTipo = interventiTipo;
	}

	@Override
	public BigDecimal getIdInviante() {
		return idInviante;
	}

	public void setIdInviante(BigDecimal idInviante) {
		this.idInviante = idInviante;
	}

	@Override
	public BigDecimal getIdInviatoA() {
		return idInviatoA;
	}

	public void setIdInviatoA(BigDecimal idInviatoA) {
		this.idInviatoA = idInviatoA;
	}

	@Override
	public BigDecimal getIdProblematica() {
		return idProblematica;
	}

	public void setIdProblematica(BigDecimal idProblematica) {
		this.idProblematica = idProblematica;
	}


	public void setLstInviante(List<SelectItem> lstInviante) {
		this.lstInviante = lstInviante;
	}
	
	@Override
	public List<SelectItem> getLstCaricoA() {
		return lstCaricoA;
	}
	
	@Override
	public List<SelectItem> getLstInviatoA() {
		return lstInviatoA;
	}
	
	@Override
	public List<SelectItem> getLstInviante() {
		return lstInviante;
	}
	
	@Override
	public List<SelectItem> getLstProblematiche() {
		return lstProblematiche;
	}

	public void setLstProblematiche(List<SelectItem> lstProblematiche) {
		this.lstProblematiche = lstProblematiche;
	}

	@Override
	public List<SelectItem> getLstStesuraRelPer() {
		return lstStesuraRelPer;
	}

	public void setLstStesuraRelPer(List<SelectItem> lstStesuraRelPer) {
		this.lstStesuraRelPer = lstStesuraRelPer;
	}

	@Override
	public String getAutosufficienza() {
		return autosufficienza;
	}

	public void setAutosufficienza(String autosufficienza) {
		this.autosufficienza = autosufficienza;
	}

	@Override
	public BigDecimal getIdTipoContratto() {
		return idTipoContratto;
	}

	public void setIdTipoContratto(BigDecimal idTipoContratto) {
		this.idTipoContratto = idTipoContratto;
	}

	@Override
	public List<SelectItem> getLstTipoContratto() {
		return lstTipoContratto;
	}

	public void setLstTipoContratto(List<SelectItem> lstTipoContratto) {
		this.lstTipoContratto = lstTipoContratto;
	}
	
	public String getPatologia() {
		return patologia;
	}

	public void setPatologia(String patologia) {
		this.patologia = patologia;
	}

	public String getPatologiaAltro() {
		return patologiaAltro;
	}

	public void setPatologiaAltro(String patologiaAltro) {
		this.patologiaAltro = patologiaAltro;
	}

	public BigDecimal getIdCaricoA() {
		return idCaricoA;
	}

	public void setIdCaricoA(BigDecimal idCaricoA) {
		this.idCaricoA = idCaricoA;
	}

	public void setLstCaricoA(List<SelectItem> lstCaricoA) {
		this.lstCaricoA = lstCaricoA;
	}


	public void setLstInviatoA(List<SelectItem> lstInviatoA) {
		this.lstInviatoA = lstInviatoA;
	}

	@Override
	public BigDecimal getIdProblematicaNucleo() {
		return idProblematicaNucleo;
	}

	public void setIdProblematicaNucleo(BigDecimal idProblematicaNucleo) {
		this.idProblematicaNucleo = idProblematicaNucleo;
	}

	@Override
	public List<SelectItem> getLstProblematicheNucleo() {	
		return lstProblematicheNucleo;
	}

	public FormazioneLavoroMan getFormLavoroMan() {
		return formLavoroMan;
	}

	public void setFormLavoroMan(FormazioneLavoroMan formLavoroMan) {
		this.formLavoroMan = formLavoroMan;
	}

	public void valorizzaFormazioneLavoroDaCS(CsADatiSociali cs) {
		formLavoroMan.setIdProfessione(cs.getProfessioneId());
		formLavoroMan.setIdTitoloStudio(cs.getTitoloStudioId());
		formLavoroMan.setIdSettoreImpiego(cs.getSettImpiegoId());
		formLavoroMan.setIdCondLavorativa(cs.getCondLavorativaId());
	}
	
	public void valorizzaFormazioneLavoroDaUDC(SsSchedaSegnalato segnalato) {
		formLavoroMan.setIdProfessione(segnalato.getProfessione()!=null ? new BigDecimal(segnalato.getProfessione()) : null);
		formLavoroMan.setIdTitoloStudio(segnalato.getTitoloStudioId());
		formLavoroMan.setIdSettoreImpiego(segnalato.getSettImpiegoId());
		formLavoroMan.setIdCondLavorativa(segnalato.getLavoro()!=null ? new BigDecimal(segnalato.getLavoro()) : null);
	}
		
	public void valorizzaDatiPorDaUDC(CsExtraFseDatiLavoro sdl){
		if(sdl!=null){
			this.datiPorMan.getCsCDatiLavoro().setAnnoConseguimentoTitoloStu(sdl.getAnnoConseguimentoTitoloStu());
			this.datiPorMan.getCsCDatiLavoro().setAzCf(sdl.getAzCf());
			this.datiPorMan.getCsCDatiLavoro().setAzCodAteco(sdl.getAzCodAteco());
			this.datiPorMan.getCsCDatiLavoro().setAzComuneCod(sdl.getAzComuneCod());
			this.datiPorMan.getCsCDatiLavoro().setAzComuneDes(sdl.getAzComuneDes());
			this.datiPorMan.getCsCDatiLavoro().setAzFormaGiuridica(sdl.getAzFormaGiuridica());
			this.datiPorMan.getCsCDatiLavoro().setAzPi(sdl.getAzPi());
			this.datiPorMan.getCsCDatiLavoro().setAzProv(sdl.getAzProv());
			this.datiPorMan.getCsCDatiLavoro().setAzRagioneSociale(sdl.getAzRagioneSociale());
			this.datiPorMan.getCsCDatiLavoro().setAzVia(sdl.getAzVia());
			this.datiPorMan.getCsCDatiLavoro().setComunicaVul(sdl.getComunicaVul());
			this.datiPorMan.getCsCDatiLavoro().setDescDimAzienda(sdl.getDescDimAzienda());
			this.datiPorMan.getCsCDatiLavoro().setDescOrarioLavoro(sdl.getDescOrarioLavoro());
			this.datiPorMan.getCsCDatiLavoro().setDescTipoLavoro(sdl.getDescTipoLavoro());
			this.datiPorMan.getCsCDatiLavoro().setDurataRicLavoroId(sdl.getDurataRicLavoroId());
			this.datiPorMan.getCsCDatiLavoro().setFlagAltroCorso(sdl.getFlagAltroCorso());
			this.datiPorMan.getCsCDatiLavoro().setFlagResDom(sdl.getFlagResDom());
			this.datiPorMan.getCsCDatiLavoro().setIban(sdl.getIban());
			this.datiPorMan.getCsCDatiLavoro().setDtSottoscrizione(sdl.getDtSottoscrizione());
		   
			ArFfProgetto p = sdl.getProgetto();
			if (p!=null) {
				this.datiPorMan.setIdProgetto(sdl.getProgetto().getId());
				this.datiPorMan.onChangeProgetto();
			}
			
			ArFfProgettoAttivita a = sdl.getProgettoAttivita();
			if (a!=null) {
				this.datiPorMan.getCsCDatiLavoro().setProgettoAttivita(a);
				this.datiPorMan.setIdSottocorso(a.getId());
			}
			this.datiPorMan.loadCodiceForm();
		}
		this.onChangeCondLavoro();
		this.onChangeGruppoVulnerabile();
	}
	
	public void valorizzaFormazioneLavoroJpa(CsADatiSociali cs) {
		cs.setProfessioneId(nullZeroValue(formLavoroMan.getIdProfessione()));
		cs.setCondLavorativaId(nullZeroValue(formLavoroMan.getIdCondLavorativa()));
		cs.setSettImpiegoId(nullZeroValue(formLavoroMan.getIdSettoreImpiego()));
		cs.setTitoloStudioId(formLavoroMan.getIdTitoloStudio());
	}
	
	private BigDecimal nullZeroValue(BigDecimal b){
		if(b!=null && b.longValue()>0)
			return b;
		else
			return null;
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

	public boolean isStranieriRequired() {
		return stranieriRequired;
	}

	public void setStranieriRequired(boolean stranieriRequired) {
		this.stranieriRequired = stranieriRequired;
	}

	public boolean isAbilitaInfoStranieri() {
		return abilitaInfoStranieri;
	}

	public void setAbilitaInfoStranieri(boolean abilitaInfoStranieri) {
		this.abilitaInfoStranieri = abilitaInfoStranieri;
	}
	
	@Override
	public void onChangeCondLavoro(){
		if(datiPorMan!=null)
			this.datiPorMan.changeCondizioneLavorativa(formLavoroMan.getIdCondLavorativa());
	}
	
	@Override
	public void onChangeGruppoVulnerabile(){
		if(datiPorMan!=null)
			this.datiPorMan.changeGruppoVulnerabile(this.famConviventiMan.getGruppoVulnerabile());
	}
	
	@Override
	public void onChangeTitoloStudio(){}
	//Non Serve?
	public void onChangeProgetti(){}

	public IFamConviventi getFamConviventiMan() {
		return famConviventiMan;
	}

	public void setFamConviventiMan(IFamConviventi famConviventiMan) {
		this.famConviventiMan = famConviventiMan;
	}

	@Override
	public boolean isAffServSociali() {
		return affServSociali;
	}

	public void setAffServSociali(boolean affServSociali) {
		this.affServSociali = affServSociali;
	}

	public boolean isRenderProblematicaSogg() {
		return renderProblematicaSogg;
	}

	public void setRenderProblematicaSogg(boolean renderProblematicaSogg) {
		this.renderProblematicaSogg = renderProblematicaSogg;
	}
	
	public void aggiornaIdCaso(Long idCaso){
		if(idCaso!=null){
			stranieriMan.setIdCaso(idCaso);
			abitazioneMan.setIdCaso(idCaso);
			famConviventiMan.setIdCaso(idCaso);
		}
	}

	public void setLstProblematicheNucleo(List<SelectItem> lstProblematicheNucleo) {
		this.lstProblematicheNucleo = lstProblematicheNucleo;
	}
	
	public List<CsAComponente> getListaComponenti() {
		return listaComponenti;
	}

	public void setListaComponenti(List<CsAComponente> listaComponenti) {
		this.listaComponenti = listaComponenti;
	}
	
	public void fillProtezioneGiuridica(Set<CsAProtezioneGiuridica> lista, Long soggettoId, boolean copia){
		if(this.protezioneGiuridica==null) 
			this.protezioneGiuridica = new Hashtable<String, ProtezioneGiuridicaBean>();
		
		
		if(lista!=null) {
			for(CsAProtezioneGiuridica jpa : lista) {
				ProtezioneGiuridicaBean bean = new ProtezioneGiuridicaBean(soggettoId, jpa.getTipo(), this.listaComponenti);
				String citta = jpa.getCitta();
				
				//Valorizzo dati componente familiare
				bean.getComponente().setCompIndirizzo(jpa.getIndirizzo());
				bean.getComponente().setCompCitta(citta);
				bean.getComponente().setCompDenominazione(jpa.getDenominazione());
				bean.getComponente().setCompTelefono(jpa.getTelefono());
				if(citta!=null){
					int index = citta.lastIndexOf('-');
					String scitta = citta.substring(0,index);
					String sprov = citta.substring(index+1);
					bean.getComponente().setComuneResidenzaMan(scitta, sprov);
				}
				CsAComponente parente = jpa.getComponente();
				bean.getComponente().setIdComponente(parente!=null ? parente.getId() : null);
				bean.getComponente().setDtRif(this.getDataFine()); /*Era impostato a dataInizio, cambiare?!*/
				
				if(!copia) bean.setId(jpa.getId());
				bean.setDataDecreto(jpa.getDataDecreto());
				bean.setNumDecreto(jpa.getNumDecreto());
				
				protezioneGiuridica.put(jpa.getTipo(), bean);
			}
		}
	}
	
	public void initAbitazioneMan(CsDValutazione v, Long casoId){
		try {
		   if(v!=null && this.abilitaInfoStranieri)
			   abitazioneMan = AbitazioneManBaseBean.initByModel(v);
		   else
			   abitazioneMan = AbitazioneManBaseBean.initByVersion(VER_MAX); 
		   abitazioneMan.setIdCaso(casoId);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public void initStranieriMan(CsDValutazione v, Long casoId){
		try {
		   if(v!=null && stranieriRequired)
			   stranieriMan = StranieriManBaseBean.initByModel(v);
		   else
			   stranieriMan = StranieriManBaseBean.initByVersion(VER_MAX); 
		   stranieriMan.setIdCaso(casoId);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public void initFamiliariMan(CsDValutazione v, Long casoId){
		try {
		   if(v!=null && this.abilitaInfoStranieri)
			   famConviventiMan = FamiliariManBaseBean.initByModel(v);
		   else
			   famConviventiMan = FamiliariManBaseBean.initByVersion(VER_MAX); 
		   famConviventiMan.setIdCaso(casoId);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public DatiPorMan getDatiPorMan() {
		return datiPorMan;
	}

	public void setDatiPorMan(DatiPorMan datiPorMan) {
		this.datiPorMan = datiPorMan;
	}
	
	public Long getIdCs() {
		return idCs;
	}

	public void setIdCs(Long idCs) {
		this.idCs = idCs;
	}
		
	public void initDatiPorMan(CsExtraFseDatiLavoro dl, Long casoId, Long dsId) {
		try {
			
		  if(this.isVisualizzaModuloPorCs()){
			    SchedaBean schedaBean = (SchedaBean) getBeanReference("schedaBean");
				String cod = schedaBean.getPresaInCaricoBean().getCasoInfo().getCodEnteSegnalante();

				datiPorMan = new DatiPorMan(cod, casoId, dl, dsId, this.formLavoroMan.getIdCondLavorativa(), this.famConviventiMan.getGruppoVulnerabile());
		  }
			
		} catch (Exception e1) {
			logger.error("Attenzione impossibile caricare i DATI POR", e1);
		}
	}
	
	public void inizializzaEStampaModelloPOR() {
		SchedaBean s = (SchedaBean)getBeanReference("schedaBean");
		DatiAnaBean ana = s.getAnagraficaBean().getDatiAnaBean();
		List<String> valida = this.datiPorMan.aggiornaEntityXStampa();
		if(valida!=null) {
			this.datiPorMan.showWarningDialog();
			return;
		}
		
		List<String> valRecapito = datiPorMan.validaRecapiti(ana.getTelefono(), ana.getCellulare(), ana.getEmail());
		boolean okRecapiti = valRecapito.isEmpty();
		StringBuilder errorRec = new StringBuilder();
		int i = 0;
		for(String msg: valRecapito) {
			errorRec.append(msg);
			if(i<valRecapito.size()-1)
				errorRec.append(", ");
			i++;
		}
		if(!okRecapiti){
			this.addWarning("Anagrafica: valori recapito non validi ai fini POR-FSE", errorRec.toString());
			return;
		}

		
		idXStampa = this.datiPorMan.getIdXStampa();
		
		this.datiProgettoBean = new StampaFseDTO();

		this.datiProgettoBean.setCognome(ana.getCognome());
		this.datiProgettoBean.setNome(ana.getNome());
		this.datiProgettoBean.setCodiceFiscale(ana.getCodiceFiscale());
		this.datiProgettoBean.setCittadinanza(ana.getCittadinanza());
		this.datiProgettoBean.setTelefono(ana.getTelefono());
		this.datiProgettoBean.setCellulare(ana.getCellulare());
		this.datiProgettoBean.setEmail(ana.getEmail());
		this.datiProgettoBean.setSesso(ana.getDatiSesso()!=null ? ana.getDatiSesso().getSesso() : "");
		
		this.datiProgettoBean.setDataNascita(ddMMyyyy.format(ana.getDataNascita()));
		
		String annon = "";
		if(ana.getDataNascita()!=null) {
			try {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(ana.getDataNascita());
						annon = Integer.toString(calendar.get(Calendar.YEAR));
			} catch (Exception e) {
			}
		}
		this.datiProgettoBean.setAnnoNascita(annon);
		this.datiProgettoBean.setLuogoNascita(s.getAnagraficaBean().getComuneNazioneNascitaMan().getDescrizioneLuogoDiNascita());
		
		CsAIndirizzo residenza = s.getAnagraficaBean().getResidenzaCsaMan().getIndirizzoResidenzaAttivo();
		if(residenza!=null){
			AmTabComuni tb = luoghiService.getComuneItaByIstat(residenza.getCsAAnaIndirizzo().getComCod());
			if(tb!=null){
				this.datiProgettoBean.setCapResidenza(tb.getCap());
				this.datiProgettoBean.setComuneResidenza(tb.getDenominazione());
				this.datiProgettoBean.setSiglaProvResidenza(tb.getSiglaProv());
			}
			this.datiProgettoBean.setViaResidenza(residenza.getCsAAnaIndirizzo().getLabelIndirizzo());
		}else{
			logger.warn("Informazioni residenza non trovate ");
		}
		
		CsAIndirizzo domicilio = s.getAnagraficaBean().getResidenzaCsaMan().getIndirizzoDomicilioAttivo();
		if(domicilio!=null){
			AmTabComuni tb = luoghiService.getComuneItaByIstat(domicilio.getCsAAnaIndirizzo().getComCod());
			if(tb!=null){
				this.datiProgettoBean.setDomicilioCap(tb.getCap());
				this.datiProgettoBean.setDomicilioComune(tb.getDenominazione());
				this.datiProgettoBean.setDomicilioSiglaProv(tb.getSiglaProv());
			}
			this.datiProgettoBean.setViaDomicilio(domicilio.getCsAAnaIndirizzo().getLabelIndirizzo());
		}else{
			logger.warn("Informazioni domicilio non trovate ");
		}
		
		this.datiPorMan.valorizzaStampa(datiProgettoBean);
		
		this.datiProgettoBean.setTitoloStudio(this.formLavoroMan.getTitoloStudioIstat());
		this.datiProgettoBean.setTitoloStudioTooltip(this.formLavoroMan.getTitoloStudioTooltip());

		if(this.famConviventiMan.getGruppoVulnerabile()!=null){
			this.datiProgettoBean.setDescrizioneVulnerabile(this.famConviventiMan.getGruppoVulnerabile().getTooltip());
			this.datiProgettoBean.setIdVulnerabile(this.famConviventiMan.getGruppoVulnerabile().getId());
			logger.info("ID gruppo vulnerabile "+this.datiProgettoBean.getIdVulnerabile());
			logger.info("Descrizione gruppo vulnerabile "+this.datiProgettoBean.getDescrizioneVulnerabile());	
		}

		this.stampaModelloPOR(this.datiProgettoBean);
		
	}
	
	public void stampaModelloPOR(StampaFseDTO datiProgettoBean){
		this.datiProgettoBean = datiProgettoBean;
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(datiProgettoBean);
		dto.setObj2(datiPorMan.getMappaCampiFse());
		List<String> msg = datiPorService.validaStampa(dto);
				
		if(!msg.isEmpty()){
			String s  = "<ul>";
			for(String sm : msg) s+= "<li>"+ sm.replace("'", "&#39;") +"</li>";
			s+="</ul>";	
			this.addWarningDialog("Validazione campi stampa POR", s);
			RequestContext.getCurrentInstance().execute("PF('wVdlgStampaPorFSE"+idXStampa+"').hide()");
			RequestContext.getCurrentInstance().update("idWvDlgStampaPorFSE"+idXStampa);
			return;
		}
		//TODO: nella stampa forse va impostato il casoID
		RequestContext.getCurrentInstance().execute("PF('wVdlgStampaPorFSE"+idXStampa+"').show()");
		RequestContext.getCurrentInstance().update("idWvDlgStampaPorFSE"+idXStampa);
		//chiamare la stampa 
	}

	public void chiamaStampa(){
		RequestContext.getCurrentInstance().execute("PF('wVdlgStampaPor"+idXStampa+"').hide();");
		
		ReportBean bean = (ReportBean)CsUiCompBaseBean.getReferencedBean("ReportBean");
		if(bean == null)//Se non è gia stato chiamato lo inizializzo
			bean = new ReportBean(); 
		bean.esportaModelloPor(datiProgettoBean);
	}
	
	public boolean validaDatiPor() {
		boolean ok = true;
		if (this.datiPorMan.isRenderFSE()) {
			boolean okpor =  this.datiPorMan.valida();
			
			//Non inserisco il controllo di validazione sul Gruppo Vulnerabile perchè già controllato e obbligatorio nei dati sociali
			if(!okpor) {
				this.datiPorMan.showWarning();
				return okpor;
			} 
			
			// SISO 1306 - valido POR SIRU
			SchedaBean s = (SchedaBean) getBeanReference("schedaBean");
			DatiAnaBean ana = s.getAnagraficaBean().getDatiAnaBean();

			List<String> valRecapito = datiPorMan.validaRecapiti(ana.getTelefono(), ana.getCellulare(), ana.getEmail());
			boolean okRecapiti = valRecapito.isEmpty();
			StringBuilder errorRec = new StringBuilder();
			int i = 0;
			for(String msg: valRecapito) {
				errorRec.append(msg);
				if(i<valRecapito.size()-1)
					errorRec.append(", ");
				i++;
			}
			if(!okRecapiti)
				this.addWarning("Anagrafica - valori recapito non validi ai fini POR-FSE", errorRec.toString());


			ok = okpor && okRecapiti;
			
			
			SiruInputDTO pds = new SiruInputDTO();
			pds.setCittadinanza(ana.getCittadinanza());
			pds.setCodiceFiscale(ana.getCodiceFiscale());
			pds.setSesso(ana.getDatiSesso().getSesso());
			pds.setDataNascita(ana.getDataNascita());
			
			pds.setFlagResDom(this.datiPorMan.getDescFlagResDom());
			
			CsAIndirizzo residenza = s.getAnagraficaBean().getResidenzaCsaMan().getIndirizzoResidenzaAttivo();
			if(residenza!=null){
				pds.setCodIstatComuneResidenza(residenza.getCsAAnaIndirizzo().getComCod());
			}else{
				logger.warn("Informazioni residenza non trovate ");
			}

			CsAIndirizzo domicilio = s.getAnagraficaBean().getResidenzaCsaMan().getIndirizzoDomicilioAttivo();
			if(domicilio!=null){
				pds.setCodIstatComuneDomicilio(domicilio.getCsAAnaIndirizzo().getComCod());
			}else{
				logger.warn("Informazioni domicilio non trovate ");
			}
			
			if(datiPorMan.isComunicaVul()) {
				pds.setGrpVulnerabilita(this.famConviventiMan.getGruppoVulnerabile().getId());
			}else{
				if(this.isModuloPorMarche())
					pds.setGrpVulnerabilita(DataModelCostanti.GrVulnerabile.NON_COMUNICA_VULNERABILITA);
			}
			
			if(s.getAnagraficaBean().getComuneNazioneNascitaMan().isComune())
				pds.setComuneNascitaCod(s.getAnagraficaBean().getComuneNazioneNascitaMan().getComuneMan().getComune().getCodIstatComune());
			else
				pds.setStatoNascitaCod(s.getAnagraficaBean().getComuneNazioneNascitaMan().getNazioneNascitaMan().getNazione().getCodIstatNazione());
			
			pds.setIdTitoloStudio(this.formLavoroMan.getIdTitoloStudio().toString());
			BigDecimal cl = this.formLavoroMan.getIdCondLavorativa();
			if(cl!=null){
				BaseDTO dto1 = new BaseDTO();
				fillEnte(dto1);
				dto1.setObj(cl.toString());
				CsTbCondLavoro conLav = confService.getCondLavoroById(dto1);
				pds.setCsTbIngMercato(conLav.getCsTbIngMercato());
			}
			
			pds.setAzCodAteco(this.datiPorMan.getCsCDatiLavoro().getAzCodAteco());
			pds.setDescDimAzienda(this.datiPorMan.getCsCDatiLavoro().getDescDimAzienda());
			pds.setAzFormaGiuridica(this.datiPorMan.getCsCDatiLavoro().getAzFormaGiuridica());
			pds.setDescOrarioLavoro(this.datiPorMan.getCsCDatiLavoro().getDescOrarioLavoro());
			pds.setDescTipoLavoro(this.datiPorMan.getCsCDatiLavoro().getDescTipoLavoro());
			pds.setAzPi(this.datiPorMan.getCsCDatiLavoro().getAzPi());
			pds.setAzCf(this.datiPorMan.getCsCDatiLavoro().getAzCf());
			pds.setAzRagioneSociale(this.datiPorMan.getCsCDatiLavoro().getAzRagioneSociale());
			pds.setAzVia(this.datiPorMan.getCsCDatiLavoro().getAzVia());
			pds.setDurataRicLavoroId(this.datiPorMan.getCsCDatiLavoro().getDurataRicLavoroId());
			pds.setAzComuneCod(this.datiPorMan.getCsCDatiLavoro().getAzComuneCod());

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(pds);
			dto.setObj2(this.datiPorMan.getMappaCampiFse());
			SiruResultDTO val = datiPorService.validaSiru(dto);
			if(val.getErrori()!=null&&val.getErrori().size()>0) {
				for(String sert: val.getErrori()) {
					this.addWarning("Errore in validazione campi FSE ", sert);
				}
				ok=false;
			} else
				datiPorMan.getCsCDatiLavoro().getMaster().setSiru(val.getSiruExtra());
		}
		return ok;
	}

	@Override
	public List<ProtezioneGiuridicaBean> getProtezioneGiuridica() {
		List<ProtezioneGiuridicaBean> out = new ArrayList<ProtezioneGiuridicaBean>(protezioneGiuridica.values());
		return out;
	}
}
