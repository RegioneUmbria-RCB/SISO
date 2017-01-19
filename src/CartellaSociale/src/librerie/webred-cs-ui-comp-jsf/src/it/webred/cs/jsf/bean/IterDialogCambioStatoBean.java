package it.webred.cs.jsf.bean;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.StatoTransizione;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsCfgItStatoAttr;
import it.webred.cs.data.model.CsCfgItTransizione;
import it.webred.cs.data.model.CsDDiarioBASIC;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.client.anag.model.SiancPazientePazienteBean.DataNascita;
import it.webred.utilities.CommonUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.apache.commons.lang.time.DateUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

public class IterDialogCambioStatoBean extends IterBaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<IterStatoAttrBean>  iterStatoAttrs;
	private List<IterStatoTransizioneBean>  statoSuccessivoAziones;
	private String datiExtLabel;
	private String statoDatiLabel;
	
	private long idCaso;
	private String opUsername;
	private String opRuolo;
	private String alertUrl;
	private boolean notificaSettoreSegnalante;
	private Date dataInserimento;
	private boolean datiRendered;
	private boolean datiExtRendered;
	private boolean operatorePanelRendered;
	private boolean enteRendered;
	private boolean settoreRendered;
	private boolean operatoreRendered;
	
	private long idSettore;
	private String nota;
	private String nomeStatoLastStep;
	
	private List<SelectItem> entes;
	private List<SelectItem> settores;
	private List<SelectItem> operatores;
	private Long enteSelezionato;
	private Long settoreSelezionato;
	private Long operatoreSelezionato;
	
	private CsItStep itLastStep;
	private Date dataLastStep;
	private boolean mostraDatiObbligatori;
	
	public void initialize(long idCaso, String opUsername, long idSettore, String opRuolo, String alertUrl, boolean notificaSettoreSegnalante) {
		try {
			this.idCaso = idCaso;
			this.opUsername = opUsername;
			this.opRuolo = opRuolo;
			this.alertUrl = alertUrl;
			this.notificaSettoreSegnalante = notificaSettoreSegnalante;
			this.idSettore = idSettore;
					
			this.datiRendered = false;
			this.datiExtRendered = false;
			this.operatorePanelRendered = false;
			this.enteRendered = false;
			this.settoreRendered = false;
			this.operatoreRendered = false;
			this.dataInserimento = new Date();
			this.mostraDatiObbligatori = false;
			
			AccessTableIterStepSessionBeanRemote iterSessionBean = getIterSessioBean();
			
			IterDTO itDto = new IterDTO();
			fillEnte(itDto);
			itDto.setIdCaso(idCaso);
			itLastStep = iterSessionBean.getLastIterStepByCaso(itDto);
			
			BaseDTO diarioDto = new BaseDTO();
			fillEnte(diarioDto);
			diarioDto.setObj( itLastStep.getCsDDiarioId() );
			CsDDiarioBASIC diarioBasic = diarioService.findDiarioBASICById( diarioDto );
			dataLastStep = diarioBasic.getDtMod();
			if( dataLastStep == null )
				dataLastStep = diarioBasic.getDtIns();
			
			if(itLastStep != null){
	
				this.nomeStatoLastStep = itLastStep.getCsCfgItStato().getNome();
				
				statoSuccessivoAziones = new LinkedList<IterStatoTransizioneBean>();
				
				itDto.setIdStato(itLastStep.getCsCfgItStato().getId());
				itDto.setOpRuolo(opRuolo);
				List<CsCfgItTransizione> listaTransizione =  iterSessionBean.getTransizionesByStatoRuolo(itDto);
				for (CsCfgItTransizione itTrans : listaTransizione )
					statoSuccessivoAziones.add( new IterStatoTransizioneBean( itTrans ) );
				
				initializeComboOperatore();
				
				//Reset attribute list
				iterStatoAttrs = new LinkedList<IterStatoAttrBean>();
				enteSelezionato = 0L;
				settoreSelezionato = 0L;
				operatoreSelezionato = 0L;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorDialog("Errore nell'inizializzazione", "Inizializzazione non riuscita!");
		}
	}
	
	protected void initializeComboOperatore(){
		try {
			AccessTableOperatoreSessionBeanRemote operatoreSessionBean = getOperatoreSessioBean();

			OperatoreDTO opDto = new OperatoreDTO();
			fillEnte(opDto);
			opDto.setIdSettore(idSettore);
			CsOSettoreBASIC settoreOperatore = operatoreSessionBean
					.findSettoreBASICById(opDto);

			caricaEnti();

			caricaSettori(settoreOperatore.getCsOOrganizzazioneId());

			caricaOperatore(idSettore);

			enteSelezionato = 0L;
			settoreSelezionato = 0L;
			operatoreSelezionato = 0L;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorDialog("Errore nell'inizializzazione del campo operatore", "Inizializzazione combo cambio operatore non riuscita!");
		}
	}
	
	protected void inizializzaStatoAttr(long idStato){
		try {
			AccessTableIterStepSessionBeanRemote iterSessionBean = getIterSessioBean();
			
			IterDTO itDto = new IterDTO();
			fillEnte(itDto);
			itDto.setIdStato(idStato);
			CsCfgItStato itStato = iterSessionBean.findStatoById( itDto );
			
			datiExtLabel = itStato.getSezioneAttributiLabel();
			statoDatiLabel = itStato.getDatiLabel();
			
			iterStatoAttrs = new LinkedList<IterStatoAttrBean>();
			
			
			for( CsCfgItStatoAttr  itStatoAttr : itStato.getCsCfgItStatoAttrs())
				iterStatoAttrs.add( new IterStatoAttrBean(itStatoAttr.getCsCfgAttr()) );
				
			datiRendered = true;
			if(iterStatoAttrs.size() > 0)
				datiExtRendered = true;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorDialog("Errore nell'inizializzazione degli attributi dello stato!", "Inizializzazione attributi non riuscita!");
		}
	}
	
	public boolean hasAttributi( Long idStatoSuccessivo ) throws NamingException {
		inizializzaStatoAttr( idStatoSuccessivo );
		return iterStatoAttrs.size() > 0;
	}
	
	public void inizializzaNuovoStato(Long idStatoSuccessivo) throws NamingException {
		hasAttributi(idStatoSuccessivo);
		checkRenderOperatore(idStatoSuccessivo);
		mostraDatiObbligatori = true;
	}
	
	public boolean salvaStato ( Long idStatoSuccessivo ){
		try {
			
			if( !checkValiditaDataCambio(dataInserimento) )
				return false;
			
			if( isToday(dataInserimento) )
				dataInserimento = new Date();
				
			if(idStatoSuccessivo.equals(DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO)) {
				AccessTableCasoSessionBeanRemote casoSessionBean = getCasoSessioBean();
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
				dto.setObj(opSettore.getCsOOperatore().getId());
				dto.setObj2(opSettore.getCsOSettore().getId());
				CsOOperatoreTipoOperatore opTipoOp = casoSessionBean.findOperatoreTipoOperatoreByOpSettore(dto);
				//se è "preso in carico" controllo che l'operatore abbia un tipo op associato 
				if(opTipoOp == null) {
					addErrorDialog("Salvataggio non effettuato", "La sua utenza non è associata a nessun TIPO OPERATORE per il settore scelto");
					return false;
				}
				
				//Recupero le cat.sociali configurate per il settore corrente
				BaseDTO bDto = new BaseDTO();
				fillEnte(bDto);
				bDto.setObj(opSettore.getCsOSettore().getId());

				AccessTableCatSocialeSessionBeanRemote catSocialeService = (AccessTableCatSocialeSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCatSocialeSessionBean");
				List<CsRelSettoreCatsoc> listaCatSociali = catSocialeService.findRelSettoreCatsocBySettore(bDto);
				
				//Verifico che una delle categorie sociali attive, a cui appartiene il soggetto sia gestita dal settore corrente
				Set<CsRelSettoreCatsoc> lstrcs = new HashSet<CsRelSettoreCatsoc>(listaCatSociali);
				Iterator<CsRelSettoreCatsoc> it = lstrcs.iterator();
				
				
				AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
				dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(itLastStep.getCsACaso().getCsASoggetto().getAnagraficaId());
				List<CsASoggettoCategoriaSocLAZY> lista = soggettoService.getSoggettoCategorieAttualiBySoggetto(dto);
				List<Long> catsocAttive = new ArrayList<Long>();
				String descrCS = "";
				for(CsASoggettoCategoriaSocLAZY cs: lista) {
					if(!cs.getId().getDataFineApp().before(new Date())){
						catsocAttive.add(cs.getCsCCategoriaSociale().getId());
						descrCS=cs.getCsCCategoriaSociale().getDescrizione()+", ";
					}
				}
				descrCS = descrCS.substring(0, descrCS.lastIndexOf(", "));
			
				boolean matchCatSoc=false;
				while(it.hasNext() && !matchCatSoc){
					CsRelSettoreCatsoc rel = it.next();
					if(rel.getAbilitato()!=null && rel.getAbilitato().intValue()==1 && catsocAttive.contains(rel.getCsCCategoriaSociale().getId()))
						matchCatSoc=true;
				}
				if(!matchCatSoc){
					addErrorDialog("Salvataggio non effettuato", "Le categorie sociali del caso ("+descrCS+") non sono gestite dal settore corrente. Prima di prendere in carico il caso, assegnare le cat.sociali corrette");
					return false;
				}
				
			}
			
			HashMap<Long, Object> attrNewStato = new HashMap<Long, Object>();
			
			for (IterStatoAttrBean it : iterStatoAttrs)
				attrNewStato.put(it.getIdAttr(), it.getValore());

			IterDTO itDto = new IterDTO();
			fillEnte(itDto);
			itDto.setIdCaso(idCaso);
			itDto.setIdStato(idStatoSuccessivo);
			itDto.setNomeOperatore(opUsername);
			itDto.setIdSettore(idSettore);
			itDto.setIdOpTo(operatoreSelezionato);
			itDto.setIdSettTo(settoreSelezionato);
			itDto.setIdOrgTo(enteSelezionato);
			itDto.setNota(nota);
			itDto.setAttrNewStato(attrNewStato);
			itDto.setAlertUrl(alertUrl);
			itDto.setNotificaSettoreSegnalante(notificaSettoreSegnalante);
			itDto.setDataInserimento(dataInserimento);
			boolean bSaved = getIterSessioBean().addIterStep(itDto);
			if( !bSaved ) {
				addErrorDialog("Attenzione", "Errore durante il salvataggio");
			}
			
			// Reset Nota
			nota = ""; 
			mostraDatiObbligatori = false;
			
			return bSaved;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorDialog("Errore nel salvataggio", "Salvataggio non riuscito!");
		}
		
		return false;
	}

	public boolean checkRenderOperatore(Long idStatoSuccessivo) throws NamingException {

		// se lo stato successivo è chiudi(2) allora non mi serve nessun
		// operatore da renderizzare
		if (idStatoSuccessivo == 2) {
			datiRendered = true;

		} else {

			for (IterStatoTransizioneBean itTrans : statoSuccessivoAziones) {
				if (itTrans.getIdStatoA().equals(idStatoSuccessivo)) {
					enteRendered = !itTrans.getStatoOrganizzazione().equals(DataModelCostanti.StatoTransizione.Enum.IGNORA);
					settoreRendered = !itTrans.getStatoSettore().equals(DataModelCostanti.StatoTransizione.Enum.IGNORA);
					operatoreRendered = !itTrans.getStatoOperatore().equals(DataModelCostanti.StatoTransizione.Enum.IGNORA);
					break;
				}
			}

			datiRendered = enteRendered || settoreRendered || operatoreRendered;

		}
		operatorePanelRendered = datiRendered;

		return datiRendered;
	}
	
	protected boolean validaTransizioneEnte( IterStatoTransizioneBean itTrans ) {

		StatoTransizione.Enum eStato = itTrans.getStatoOrganizzazione(); 
		if( eStato.equals( StatoTransizione.Enum.OBBLIGATORIO ) )
		{
			if( !(enteSelezionato != null && enteSelezionato > 0L) )
			{
				addErrorDialog("Attenzione", "Selezionare un ente");
				return false;
			}
		}
		else if( eStato.equals( StatoTransizione.Enum.IGNORA ) )
		{
			enteSelezionato = 0L;
		}
		
		return true;
	}
	
	protected boolean validaTransizioneSettore( IterStatoTransizioneBean itTrans ) {

		StatoTransizione.Enum eStato = itTrans.getStatoSettore();
		
		if( eStato.equals( StatoTransizione.Enum.OBBLIGATORIO ) )
		{
			if( !(settoreSelezionato != null && settoreSelezionato > 0L) ) 
			{
				addErrorDialog("Attenzione", "Selezionare un settore");
				return false;
			}
		}
		else if( eStato.equals( StatoTransizione.Enum.IGNORA ) )
		{
			settoreSelezionato = 0L;
		}
		
		return true;
	}
	
	protected boolean validaTransizioneOperatore( IterStatoTransizioneBean itTrans ) {

		StatoTransizione.Enum eStato = itTrans.getStatoOperatore();

		if( eStato.equals( StatoTransizione.Enum.OBBLIGATORIO ) )
		{
			if( !(operatoreSelezionato != null && operatoreSelezionato > 0L) ) {
				addErrorDialog("Attenzione", "Selezionare un operatore");
				return false;
			}
		}
		else if( eStato.equals( StatoTransizione.Enum.IGNORA ) )
		{
			operatoreSelezionato = 0L;
		}
		
		return true;
	}
	
	public boolean validaTransizione( Long idStatoSuccessivo ) {
		for( IterStatoTransizioneBean itTrans : statoSuccessivoAziones )
		{
			if( idStatoSuccessivo.equals( itTrans.getIdStatoA() ) )
			{
				boolean isValid = true;
				isValid &= validaTransizioneEnte( itTrans );
				isValid &= validaTransizioneSettore( itTrans );
				isValid &= validaTransizioneOperatore( itTrans );
			    return isValid;
			}
		}
	    
	    return true;
	}

	protected Object getValoreAttributo( Long id )
	{
		for( IterStatoAttrBean it : iterStatoAttrs )
			if( it.getIdAttr().equals(id) )
				return it.getValore();
		
		return null;
	}
	
	public boolean validaAttributi( Long idStatoSuccessivo ) throws NamingException {
		try {
			
			IterDTO itDto = new IterDTO();
			fillEnte(itDto);
			itDto.setIdStato(idStatoSuccessivo);
			CsCfgItStato itStato = getIterSessioBean().findStatoById( itDto );
			
			List<String> errorList = new LinkedList<String>();
			for (CsCfgItStatoAttr its : itStato.getCsCfgItStatoAttrs() ) {
				CsCfgAttr it = its.getCsCfgAttr();
				boolean bAttrObbl = !CommonUtils.isEmptyString ( it.getMessaggioObbligatorio() );
				
				Object attributoVal = getValoreAttributo(it.getId());
				if( bAttrObbl && attributoVal == null)
					errorList.add(it.getMessaggioObbligatorio());
			}
			
			if( errorList.size() > 0 ) {
				addErrorDialog("Attenzione", CommonUtils.Join("<br/>", errorList.toArray()));
				return false;
			}
			
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorDialog("Errore nella validazione degli attributi", "Validazione attributi non riuscito!");
		}
		
		return true;
	}
	
	public void handleEnteChange() throws NamingException  {
		caricaSettori(enteSelezionato);
	}
	
	public void handleSettoreChange() throws NamingException  {
		caricaOperatore(settoreSelezionato);
	}
	
	protected void caricaEnti() throws NamingException {
		try {
			AccessTableConfigurazioneSessionBeanRemote confService = getConfigurazioneSessioBean();
		
			CeTBaseObject opDto = new CeTBaseObject();
			fillEnte(opDto);
			entes = new LinkedList<SelectItem>();
			for (CsOOrganizzazione it : confService.getOrganizzazioniBelfiore(opDto)) {
				entes.add(new SelectItem(it.getId(), it.getNome()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorDialog("Errore nel caricamento degli Enti", "Caricamento Enti non riuscito !");
		}
	}

	protected void caricaSettori(long enteId) throws NamingException {
		try {
			if( enteId != 0L ) {
				AccessTableOperatoreSessionBeanRemote operatoreSessionBean = getOperatoreSessioBean();
		
				OperatoreDTO opDto = new OperatoreDTO();
				fillEnte(opDto);
				opDto.setIdOrganizzazione(enteId);
				settores = new LinkedList<SelectItem>();
				for (CsOSettoreBASIC it : operatoreSessionBean.findSettoreBASICByOrganizzazione(opDto) ) {
					SelectItem si = new SelectItem( it.getId(), it.getNome());
					si.setDisabled(!it.getAbilitato());
					settores.add(si);	
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorDialog("Errore nel caricamento dei Settori", "Caricamento Settori non riuscito!");
		}
	}
	
	protected void caricaOperatore(long settoreId) throws NamingException {
		try {
			if (settoreId != 0L) {
				AccessTableOperatoreSessionBeanRemote operatoreSessionBean = getOperatoreSessioBean();
		
				OperatoreDTO opDto = new OperatoreDTO();
				fillEnte(opDto);
				opDto.setIdSettore(settoreId);
				operatores = new LinkedList<SelectItem>();
				for (CsOOperatoreSettore it : operatoreSessionBean.findOperatoreSettoreBySettore(opDto) )
					operatores.add(new SelectItem( it.getCsOOperatore().getId(), getDenominazioneOperatore(it.getCsOOperatore())));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorDialog("Errore nel caricamento degli Operatori", "Caricamento Operatori non riuscito!");
		}
	}
	
	protected boolean isToday(Date dataInserimento){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(dataInserimento);
		cal2.setTime(new Date());
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);		
		return sameDay;
	}
	
	protected boolean checkValiditaDataCambio(Date dataInserimento) {
		if( dataInserimento == null ){
			addErrorDialog("Data del cambio di stato obbligatoria", "Inserire una data del cambio di stato");
			return false;
		}
		
		//Alessandro Feriani: Da rimuovere
		return true;
		
//		if( datainserimento.before( datalaststep ) || datainserimento.before( datalaststep ) ) {
//	        simpledateformat formatter = new simpledateformat("dd/mm/yyyy");
//			addErrorDialog("data del cambio di stato non valida", "la data non può essere inferiore al " + formatter.format(datalaststep));
//			return false;
//		}
//		
//		return true;
	}
	
	public void onDataCambioStatoSelect(SelectEvent event) {
        Date nuovaDataInserimento = (Date)event.getObject();
        checkValiditaDataCambio(nuovaDataInserimento);
    }
			
	public List<IterStatoAttrBean> getIterStatoAttrs() {
		return iterStatoAttrs;
	}

	public List<IterStatoTransizioneBean> getStatoSuccessivoAziones() {
		return statoSuccessivoAziones;
	}

	public String getDatiExtLabel() {
		return datiExtLabel;
	}

	public String getStatoDatiLabel() {
		return statoDatiLabel;
	}
	
	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public List<SelectItem> getEntes() {
		return entes;
	}

	public List<SelectItem> getSettores() {
		return settores;
	}

	public List<SelectItem> getOperatores() {
		return operatores;
	}

	public Long getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(Long enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public Long getSettoreSelezionato() {
		return settoreSelezionato;
	}

	public void setSettoreSelezionato(Long settoreSelezionato) {
		this.settoreSelezionato = settoreSelezionato;
		
	}

	public Long getOperatoreSelezionato() {
		return operatoreSelezionato;
	}

	public void setOperatoreSelezionato(Long operatoreSelezionato) {
		this.operatoreSelezionato = operatoreSelezionato;
	}

	public boolean isDatiRendered() {
		return datiRendered;
	}


	public boolean isDatiExtRendered() {
		return datiExtRendered;
	}

	public boolean isOperatorePanelRendered() {
		return operatorePanelRendered;
	}
	
	public boolean isEnteRendered() {
		return enteRendered;
	}

	public boolean isSettoreRendered() {
		return settoreRendered;
	}

	public boolean isOperatoreRendered() {
		return operatoreRendered;
	}

	public String getNomeStatoLastStep() {
		return nomeStatoLastStep;
	}	
	
	public void setDatiExtRendered(boolean datiExtRendered) {
		this.datiExtRendered = datiExtRendered;
	}

	public long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(long idCaso) {
		this.idCaso = idCaso;
	}

	public String getOpUsername() {
		return opUsername;
	}

	public void setOpUsername(String opUsername) {
		this.opUsername = opUsername;
	}

	public String getOpRuolo() {
		return opRuolo;
	}

	public void setOpRuolo(String opRuolo) {
		this.opRuolo = opRuolo;
	}

	public String getAlertUrl() {
		return alertUrl;
	}

	public void setAlertUrl(String alertUrl) {
		this.alertUrl = alertUrl;
	}

	public boolean isNotificaSettoreSegnalante() {
		return notificaSettoreSegnalante;
	}

	public void setNotificaSettoreSegnalante(boolean notificaSettoreSegnalante) {
		this.notificaSettoreSegnalante = notificaSettoreSegnalante;
	}

	public long getIdSettore() {
		return idSettore;
	}

	public void setIdSettore(long idSettore) {
		this.idSettore = idSettore;
	}

	public CsItStep getItLastStep() {
		return itLastStep;
	}

	public void setItLastStep(CsItStep itLastStep) {
		this.itLastStep = itLastStep;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setIterStatoAttrs(List<IterStatoAttrBean> iterStatoAttrs) {
		this.iterStatoAttrs = iterStatoAttrs;
	}

	public void setStatoSuccessivoAziones(List<IterStatoTransizioneBean> statoSuccessivoAziones) {
		this.statoSuccessivoAziones = statoSuccessivoAziones;
	}

	public void setDatiExtLabel(String datiExtLabel) {
		this.datiExtLabel = datiExtLabel;
	}

	public void setStatoDatiLabel(String statoDatiLabel) {
		this.statoDatiLabel = statoDatiLabel;
	}

	public void setDatiRendered(boolean datiRendered) {
		this.datiRendered = datiRendered;
	}

	public void setOperatorePanelRendered(boolean operatorePanelRendered) {
		this.operatorePanelRendered = operatorePanelRendered;
	}

	public void setEnteRendered(boolean enteRendered) {
		this.enteRendered = enteRendered;
	}

	public void setSettoreRendered(boolean settoreRendered) {
		this.settoreRendered = settoreRendered;
	}

	public void setOperatoreRendered(boolean operatoreRendered) {
		this.operatoreRendered = operatoreRendered;
	}

	public void setNomeStatoLastStep(String nomeStatoLastStep) {
		this.nomeStatoLastStep = nomeStatoLastStep;
	}

	public void setEntes(List<SelectItem> entes) {
		this.entes = entes;
	}

	public void setSettores(List<SelectItem> settores) {
		this.settores = settores;
	}

	public void setOperatores(List<SelectItem> operatores) {
		this.operatores = operatores;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public boolean isMostraDatiObbligatori() {
		return mostraDatiObbligatori;
	}
	
	
}
