package it.webred.ss.web.bean.util;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.Scheda;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.jsf.manbean.ConsensoPrivacyMan;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsInterventiSchede;
import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaAccessoInviante;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.data.model.tb.CsOSettoreLIGHT;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.wizard.Accesso;
import it.webred.ss.web.bean.wizard.DiarioSociale;
import it.webred.ss.web.bean.wizard.Nota;
import it.webred.ss.web.bean.wizard.ServiziRichiestiInterventiCustomBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

@ManagedBean
@ViewScoped
public class ViewSchedaBean extends SegretariatoSocBaseBean {
	
	//accesso orig
	private Accesso accessoOrig;
	
	// accesso
	private Date data;
	private String descrizione;
	private String operatore;
	private String modalita;
	private String interlocutore;
	private String tipoScheda;
	private String motivo;

	private PuntoContatto puntoContatto;
	// segnalante
	private boolean hideSegnalante;
	private String cognomeNomeSegnalante;
	private String ente;
	private String ruolo;
	private String relazione;
	private String telCelSegnalante;
	private String emailSegnalante;
	private String indirizzo;
	private Date dataNascitaSegnalante;
	private String sessoSegnalante;
	private String statoCivileSegnalante;
	private String comuneNascitaSegnalante;
	
	// segnalato
	private String cognomeNomeSegnalato;
	private Date dataNascita;
	private String comuneNascita;
	private String sesso;
	private String cf;
	private String statoCivile;
	private String cittadinanza;
	private String cittadinanza2;
	private String alias;
	
	private String residenza;
	private String domicilio;
	private String telCel;
	private String email;
	private String medico;
	private String tesseraSanitaria;
	private boolean stp;
	private boolean invalidita=false;
	private String  percInvalidita;
	
	private FormazioneLavoroMan formLavoroSegnalato;
	
	private IStranieri stranieriMan;
	private IAbitazione abitazioneMan;
	private IFamConviventi famConviventiMan;
	
	private ServiziRichiestiInterventiCustomBean serviziRichiestiInterventiCustomBean;  //SISO-438 
	
	
	private ConsensoPrivacyMan consensoMan;

	// riferimento
	private List <SsSchedaRiferimento> listaRiferimenti = new ArrayList<SsSchedaRiferimento>();//SISO-947
	
	// motivazione
	private List<String> selectedMotivazioni = new ArrayList<String>();
	private String motivazioneAltro;
	
	
	// interventi
	private List<String> selectedInterventi = new ArrayList<String>();
	private String interventiAltro;
	// diario
	private DiarioSociale diarioSociale = new DiarioSociale();
	
	private String indietroButtonLink;
	
	public ViewSchedaBean(){
		this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		
		String selectedScheda = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
		if(selectedScheda==null) return;
		
		SsSchedaSessionBeanRemote schedaService;
		
		try {
			
			indietroButtonLink = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("previousPage");
			
			schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(new Long(selectedScheda));
			SsScheda s = schedaService.readScheda(dto);
			
			// dati accesso
			SsSchedaAccesso a = s.getAccesso();
			data = a.getData();
			operatore = a.getOperatore();
			puntoContatto = new PuntoContatto();
			puntoContatto.initFromModel(a.getSsRelUffPcontOrg());
			modalita = a.getModalita();
			dto.setObj(s.getTipo());
			SsTipoScheda tipoBean = confService.readTipoSchedaById(dto);
			tipoScheda = tipoBean!=null ? tipoBean.getTipo() : null;
			descrizione = a.getDescrizione();
			String accompagnatore = a.getAccompagnatore()!=null ? a.getAccompagnatore() : "non specificato";
			interlocutore = a.getInterlocutore();
			interlocutore += (a.getUtenteAccompagnato()!=null && a.getUtenteAccompagnato()) ? " (accompagnato da: "+accompagnatore+" )" : "";
			interlocutore += (a.getUtentePresenteInformato()!=null && a.getUtentePresenteInformato()) ? " (utente presente o informato)" :"";
	
			CsOSettoreLIGHT settore = a.getSettoreInviante();
			String inviante = settore!=null ? format(settore.getNome()) : "";
			
			motivo = a.getMotivo();
			motivo += settore!=null ? " "+inviante : "";
			motivo += a.getMotivoDesc()!=null ? ": "+a.getMotivoDesc() : "";
			
			// dati segnalante
			SsSchedaSegnalante segnalante = s.getSegnalante();
			if(segnalante != null){
				hideSegnalante = false;
				it.webred.cs.csa.ejb.dto.BaseDTO dtoCS = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(dtoCS );
				

				dtoCS.setObj(segnalante.getCsOSettoreId());
				CsOSettore settEnte = configurationCsEnteBean.getSettoreById(dtoCS);
				
				if(segnalante.getCognome() != null || segnalante.getNome() != null)
					cognomeNomeSegnalante = (format(segnalante.getCognome())  + " " + format(segnalante.getNome()) ).toUpperCase();
				ente = settEnte!=null ? settEnte.getNome() : segnalante.getEnte_servizio();
				
				
				telCelSegnalante = segnalante.getTelefono() != null ? format(segnalante.getTelefono()) : ""; 
				if(segnalante.getCel() != null){
				    telCelSegnalante += !telCelSegnalante.isEmpty() ?  " / " : ""; 
				    telCelSegnalante += format(segnalante.getCel());
				}
				 
				emailSegnalante = segnalante.getEmail()!=null ? segnalante.getEmail() : "";
				if(segnalante.getVia()!=null || segnalante.getComune()!=null)
					indirizzo = format(segnalante.getVia())+", "+format(segnalante.getStampaDesComuneResidenza());
				
				dataNascitaSegnalante = segnalante.getDataNascita();
				sessoSegnalante = segnalante.getSesso();
				//SISO-906 -Specifica del parente quando affidatario
				relazione = segnalante.getTbRelazione()!=null ? format(segnalante.getTbRelazione().getDescrizione().concat(segnalante.getAffidatario()? " - Affidatario" : "")) : "";
				statoCivileSegnalante = segnalante.getTbStatoCivile() != null ? segnalante.getTbStatoCivile().getDescrizione() : "";
				
				comuneNascitaSegnalante = segnalante.getComuneNascitaDes() + " (" + segnalante.getProvNascitaCod() + ") " + 
					segnalante.getStatoNascitaDes() != null ? segnalante.getStatoNascitaDes() : "";
			}else
				if(Scheda.Interlocutori.UTENTE.equalsIgnoreCase(a.getInterlocutore())) 
					hideSegnalante=true;	
			
			// dati segnalato
			dto.setObj(s.getSegnalato());			
			SsSchedaSegnalato segnalato = schedaService.readSegnalatoById(dto);
			SsAnagrafica anagrafica = segnalato.getAnagrafica();
		
			cognomeNomeSegnalato = (anagrafica.getCognome()  + " " +  anagrafica.getNome()).toUpperCase();
			dataNascita = anagrafica.getData_nascita();
			comuneNascita = anagrafica.getLuogoDiNascita();
			sesso = anagrafica.getSesso();
			cf = anagrafica.getCf();
			statoCivile = anagrafica.getStato_civile();
			cittadinanza = anagrafica.getCittadinanza();
			cittadinanza2 = format(anagrafica.getCittadinanza2());
			alias = anagrafica.getAlias();
			
			if(anagrafica.getTbCittadinanzaAcq()!=null)
				cittadinanza += " ("+ anagrafica.getTbCittadinanzaAcq().getDescrizione()+")";
			
			residenza = segnalato.getSenzaFissaDimora()!=null && segnalato.getSenzaFissaDimora() ? DataModelCostanti.SENZA_FISSA_DIMORA+ " " : "";
			residenza += segnalato.getResidenza()!=null ? segnalato.getResidenza().getStampaDesIndirizzo() : "";
			
			domicilio = segnalato.getDomicilio()!=null ? segnalato.getDomicilio().getStampaDesIndirizzo() : "";
			domicilio += segnalato.getNoteDomicilio()!=null ? " ("+segnalato.getNoteDomicilio()+")" : "";
			
			telCel = segnalato.getTelefono() != null ? format(segnalato.getTelefono()) : ""; 
			telCel += segnalato.getTitolareTelefono()!=null ?  " ("+format(segnalato.getTitolareTelefono())+")" : "";
			if(segnalato.getCel() != null){
				telCel += !telCel.isEmpty() ?  " / " : ""; 
				telCel += format(segnalato.getCel());
				telCel += segnalato.getTitolareCellulare()!=null ?  " ("+format(segnalato.getTitolareCellulare())+")" : "";
			}
			
			email = segnalato.getEmail();
			email += segnalato.getTitolareEmail()!=null ?  " ("+format(segnalato.getTitolareEmail())+")" : "";
			medico = segnalato.getMedico();
			tesseraSanitaria = segnalato.getTessera_sanitaria();
			stp = segnalato.getStp()!=null ? segnalato.getStp() : false;
			invalidita = segnalato.getInvalidita()!=null;
			percInvalidita = segnalato.getInvalidita()!=null ? segnalato.getInvalidita().toString() : "";
			
			formLavoroSegnalato = new FormazioneLavoroMan();
			formLavoroSegnalato.setIdCondLavorativa(segnalato.getLavoro()!=null ? new BigDecimal(segnalato.getLavoro()) : null);
			formLavoroSegnalato.setIdProfessione(segnalato.getProfessione()!=null ? new BigDecimal(segnalato.getProfessione()) : null);
			formLavoroSegnalato.setIdTitoloStudio(segnalato.getTitoloStudioId());
			formLavoroSegnalato.setIdSettoreImpiego(segnalato.getSettImpiegoId());
			
			/*TAB UTENTE*/
			stranieriMan = getSchedaJsonStranieri(s.getId());
			abitazioneMan = getSchedaJsonAbitazione(s.getId());
			famConviventiMan = getSchedaJsonFamConviventi(s.getId());
			
			/*TAB SERVIZI*/
			serviziRichiestiInterventiCustomBean = new ServiziRichiestiInterventiCustomBean();  //SISO-438 
			serviziRichiestiInterventiCustomBean.loadManJsonServiziRichiesti(s, segnalato);  
			
			/*Valorizzazione Informazioni Sottoscrizione Privacy*/
			boolean beneficiarioRdC=this.verificaPresenzaRdC(cf);
			consensoMan = new ConsensoPrivacyMan(cf, a.getSsRelUffPcontOrg().getSsOOrganizzazione().getId(), anagrafica.isAnonimo(), beneficiarioRdC);
			
			////SISO-947 dati riferimenti
			SsSchedaRiferimento r = s.getRiferimento();
			SsSchedaRiferimento r2 = s.getRiferimento2();
			SsSchedaRiferimento r3 = s.getRiferimento3();
			
			listaRiferimenti = new ArrayList<SsSchedaRiferimento>();
			
			listaRiferimenti.add(r);
			listaRiferimenti.add(r2);
			listaRiferimenti.add(r3);
			
			// dati motivazione
        	fillUserData(dto);
        	dto.setObj(s.getMotivazione());
        	List<SsMotivazioniSchede> motivi = schedaService.readMotivazioniScheda(dto);
        	for(SsMotivazioniSchede motivoScheda: motivi){
        		SsMotivazione m = motivoScheda.getMotivazione();
        		selectedMotivazioni.add(m.getClassificazione().getDescrizione()+" - "+m.getMotivo());
        	}
        	motivazioneAltro = s.getMotivazione().getAltro();
        	
        	// dati interventi
        	fillUserData(dto);
        	dto.setObj(s.getInterventi());
        	List<SsInterventiSchede> tempInterventi = schedaService.readInterventiScheda(dto);
        	for(SsInterventiSchede interventoScheda: tempInterventi)
        		selectedInterventi.add(interventoScheda.getIntervento().getIntervento());
        	interventiAltro = s.getInterventi().getAltro();
        	
        	// dati diario sociale
        	fillUserData(dto);
        	dto.setObj(anagrafica.getCf());
        	List<SsAnagrafica> anagrafiche = schedaService.readAnagraficheByCf(dto);
        	
        	for(SsAnagrafica ana: anagrafiche){
        		dto.setObj(ana);
        		dto.setObj2(a.getSsRelUffPcontOrg().getSsOOrganizzazione().getId());
        		List<SsDiario> diari = schedaService.readDiarioSoggettoEnte(dto);
            	List<Nota> note = loadNoteDiarioAccessibili(diari, a.getOperatore(), null);
            	diarioSociale.populateNote(note);
        	}
        	
        	//Dati accesso orig (se scheda inviata)
        	accessoOrig = null;
        	SsSchedaAccessoInviante schedaOriginale= recuperaSsSchedaAccessoInvianteFromSsScheda(s);
			if(schedaOriginale!=null && schedaOriginale.getId()!=null && schedaOriginale.getId()>-1)
			{
				// inizializza accessoOrig tramite la schedaInviante recuperata
				accessoOrig = new Accesso();
				accessoOrig.initFromModelAccessoInviante(schedaOriginale);
			}
       
		} catch (Exception e) {
			addError("lettura.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void goBack() {
		try {
			if (indietroButtonLink == null || indietroButtonLink.isEmpty())
				indietroButtonLink = "home.faces";
			FacesContext.getCurrentInstance().getExternalContext().redirect(indietroButtonLink);
		} catch (IOException e) {
			logger.error(e);
		}
	}

   public String titoloTabRiferimento(SsSchedaRiferimento riferimento, Integer numRiferimento){
		String titolo = "Riferimento " + Integer.toString(numRiferimento);
					
		if (riferimento != null) {
			if (!StringUtils.isBlank(riferimento.getNome()) && !StringUtils.isBlank(riferimento.getNome()))
				titolo = riferimento.getCognome() + " " + riferimento.getNome();
		}
		return titolo;
		
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getModalita() {
		return modalita;
	}

	public void setModalita(String modalita) {
		this.modalita = modalita;
	}

	public String getInterlocutore() {
		return interlocutore;
	}

	public void setInterlocutore(String interlocutore) {
		this.interlocutore = interlocutore;
	}

	public String getCognomeNomeSegnalante() {
		return cognomeNomeSegnalante;
	}

	public void setCognomeNomeSegnalante(String cognomeNomeSegnalante) {
		this.cognomeNomeSegnalante = cognomeNomeSegnalante;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getRelazione() {
		return relazione;
	}

	public void setRelazione(String relazione) {
		this.relazione = relazione;
	}

	public String getTelCelSegnalante() {
		return telCelSegnalante;
	}

	public void setTelCelSegnalante(String telCel) {
		this.telCelSegnalante = telCel;
	}

	public String getEmailSegnalante() {
		return emailSegnalante;
	}

	public void setEmailSegnalante(String email) {
		this.emailSegnalante = email;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCognomeNomeSegnalato() {
		return cognomeNomeSegnalato;
	}

	public void setCognomeNomeSegnalato(String cognomeNomeSegnalato) {
		this.cognomeNomeSegnalato = cognomeNomeSegnalato;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getStatoCivile() {
		return statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getTelCel() {
		return telCel;
	}

	public void setTelCel(String telCel) {
		this.telCel = telCel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

	public String getTesseraSanitaria() {
		return tesseraSanitaria;
	}

	public void setTesseraSanitaria(String tesseraSanitaria) {
		this.tesseraSanitaria = tesseraSanitaria;
	}

	public List<String> getSelectedMotivazioni() {
		return selectedMotivazioni;
	}

	public void setSelectedMotivazioni(List<String> selectedMotivazioni) {
		this.selectedMotivazioni = selectedMotivazioni;
	}

	public String getMotivazioneAltro() {
		return motivazioneAltro;
	}

	public void setMotivazioneAltro(String motivazioneAltro) {
		this.motivazioneAltro = motivazioneAltro;
	}

	public List<String> getSelectedInterventi() {
		return selectedInterventi;
	}

	public void setSelectedInterventi(List<String> selectedInterventi) {
		this.selectedInterventi = selectedInterventi;
	}

	public String getInterventiAltro() {
		return interventiAltro;
	}

	public void setInterventiAltro(String interventiAltro) {
		this.interventiAltro = interventiAltro;
	}

	public String getTipoScheda() {
		return tipoScheda;
	}

	public void setTipoScheda(String tipoScheda) {
		this.tipoScheda = tipoScheda;
	}

	public DiarioSociale getDiarioSociale() {
		return diarioSociale;
	}

	public void setDiarioSociale(DiarioSociale diarioSociale) {
		this.diarioSociale = diarioSociale;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public PuntoContatto getPuntoContatto() {
		return puntoContatto;
	}

	public void setPuntoContatto(PuntoContatto puntoContatto) {
		this.puntoContatto = puntoContatto;
	}

	public String getCittadinanza2() {
		return cittadinanza2;
	}

	public void setCittadinanza2(String cittadinanza2) {
		this.cittadinanza2 = cittadinanza2;
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

	public boolean isStp() {
		return stp;
	}

	public boolean isInvalidita() {
		return invalidita;
	}

	public String getPercInvalidita() {
		return percInvalidita;
	}

	public void setStp(boolean stp) {
		this.stp = stp;
	}

	public void setInvalidita(boolean invalidita) {
		this.invalidita = invalidita;
	}

	public void setPercInvalidita(String percInvalidita) {
		this.percInvalidita = percInvalidita;
	}

	public Date getDataNascitaSegnalante() {
		return dataNascitaSegnalante;
	}

	public void setDataNascitaSegnalante(Date dataNascitaSegnalante) {
		this.dataNascitaSegnalante = dataNascitaSegnalante;
	}

	public String getSessoSegnalante() {
		return sessoSegnalante;
	}

	public void setSessoSegnalante(String sessoSegnalante) {
		this.sessoSegnalante = sessoSegnalante;
	}

	public String getStatoCivileSegnalante() {
		return statoCivileSegnalante;
	}

	public void setStatoCivileSegnalante(String statoCivileSegnalante) {
		this.statoCivileSegnalante = statoCivileSegnalante;
	}

	public String getComuneNascitaSegnalante() {
		return comuneNascitaSegnalante;
	}

	public void setComuneNascitaSegnalante(String comuneNascitaSegnalante) {
		this.comuneNascitaSegnalante = comuneNascitaSegnalante;
	}

	public Accesso getAccessoOrig() {
		return accessoOrig;
	}


	public void setAccessoOrig(Accesso accessoOrig) {
		this.accessoOrig = accessoOrig;
	}


	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public ServiziRichiestiInterventiCustomBean getServiziRichiestiInterventiCustomBean() {
		return serviziRichiestiInterventiCustomBean;
	}

	public void setServiziRichiestiInterventiCustomBean(
			ServiziRichiestiInterventiCustomBean serviziRichiestiInterventiCustomBean) {
		this.serviziRichiestiInterventiCustomBean = serviziRichiestiInterventiCustomBean;
	}

	public boolean isHideSegnalante() {
		return hideSegnalante;
	}

	public void setHideSegnalante(boolean hideSegnalante) {
		this.hideSegnalante = hideSegnalante;
	}
	//SISO-947
	public List<SsSchedaRiferimento> getListaRiferimenti() {
		return listaRiferimenti;
	}

	public void setListaRiferimenti(List<SsSchedaRiferimento> listaRiferimenti) {
		this.listaRiferimenti = listaRiferimenti;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public ConsensoPrivacyMan getConsensoMan() {
		return consensoMan;
	}

	public void setConsensoMan(ConsensoPrivacyMan consensoMan) {
		this.consensoMan = consensoMan;
	}
}
