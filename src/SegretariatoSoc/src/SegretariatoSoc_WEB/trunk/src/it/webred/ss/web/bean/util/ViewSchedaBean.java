package it.webred.ss.web.bean.util;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.json.OrientamentoLavoro.IOrientamentoLavoro;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.intermediazione.IIntermediazioneAb;
import it.webred.cs.json.mediazioneculturale.IMediazioneCult;
import it.webred.cs.json.orientamentoistruzione.IOrientamentoIstruzione;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsInterventiSchede;
import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaPrivacy;
import it.webred.ss.data.model.SsSchedaPrivacyPK;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.wizard.DiarioSociale;
import it.webred.ss.web.bean.wizard.Nota;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ViewSchedaBean extends SegretariatoSocBaseBean {
	
	// accesso
	private Date data;
	private String operatore;
	private String modalita;
	private String interlocutore;
	private String tipoScheda;
	private String motivo;
	private PuntoContatto puntoContatto;
	// segnalante
	private String cognomeNomeSegnalante;
	private String ente;
	private String ruolo;
	private String relazione;
	private String telCelSegnalante;
	private String emailSegnalante;
	private String indirizzo;
	private String inviante;
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
	
	private IIntermediazioneAb intermediazioneAbMan;
	private IOrientamentoLavoro orientamentoLavoro;
	private IMediazioneCult mediazioneCultMan;
	private IOrientamentoIstruzione orientamentoIstruzioneMan;
	
	private String privacy;

	// riferimento
	private String cognomeNomeRiferimento;
	private String parentelaRiferimento;
	private String problemiRiferimento;
	private String telefonoRiferimento;
	private String recapitoRiferimento;
	private String celRiferimento;
	private String emailRiferimento;
	private Date dataNascitaRiferimento;
	private String sessoRiferimento;
	private String statoCivileRiferimento;
	private String comuneNascitaRiferimento;
	
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
		String selectedScheda = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
		SsSchedaSessionBeanRemote schedaService;
		AccessTableConfigurazioneSessionBeanRemote configService;
		try {
			
			indietroButtonLink = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("previousPage");
			
			schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			configService = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");

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
			interlocutore = a.getInterlocutore();
			dto.setObj(s.getTipo());
			tipoScheda = schedaService.readTipoSchedaById(dto).getTipo();
			setMotivo(a.getMotivo());
			
			// dati segnalante
			SsSchedaSegnalante segnalante = s.getSegnalante();
			if(segnalante != null){
				
				it.webred.cs.csa.ejb.dto.BaseDTO dtoCS = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(dtoCS );
				
				dtoCS.setObj(segnalante.getCodStatoCivile());
				CsTbStatoCivile csTbStatoCivile = configService.getStatoCivileByCodice(dtoCS);
				
				dtoCS.setObj(segnalante.getRelazioneId());
				CsTbTipoRapportoCon csTbTipoRelazine = configService.getTipoRapportoConByCodice(dtoCS);
				
				
				if(segnalante.getCognome() != null || segnalante.getNome() != null)
					cognomeNomeSegnalante = (format(segnalante.getCognome())  + " " + format(segnalante.getNome()) ).toUpperCase();
				ente = segnalante.getEnte_servizio();
				
				if(segnalante.getTelefono() != null || segnalante.getCel() != null)
					telCelSegnalante = format(segnalante.getTelefono()) + " / " + format(segnalante.getCel());
				emailSegnalante = segnalante.getEmail();
				if(segnalante.getVia()!=null || segnalante.getComune()!=null)
					indirizzo = format(segnalante.getVia())+", "+format(getDescrizioneComune(segnalante.getComune()));
				CsOSettoreBASIC settore = getSettore(segnalante.getInviato_da());
				inviante = settore!=null ? format(settore.getNome()) : "";
				
				dataNascitaSegnalante = segnalante.getDataNascita();
				sessoSegnalante = segnalante.getSesso();
				
				relazione = csTbTipoRelazine!=null ? format(csTbTipoRelazine.getDescrizione()) : "";
				statoCivileSegnalante = csTbStatoCivile != null ? csTbStatoCivile.getDescrizione() : "";
				
				comuneNascitaSegnalante = segnalante.getComuneNascitaDes() + " (" + segnalante.getProvNascitaCod() + ") " + 
					segnalante.getStatoNascitaDes() != null ? segnalante.getStatoNascitaDes() : "";
			}			
			
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
			
			CsTbCittadinanzaAcq cittAcq = this.getCittadinanzaAcq(anagrafica.getCittadinanzaAcq());
			if(cittAcq!=null)
				cittadinanza += " ("+ cittAcq.getDescrizione()+")";
			
			residenza = this.getDescrizioneIndirizzo(segnalato.getResidenza());
			residenza += segnalato.getStrutturaAccoglienza()!=null ? " - (Struttura di accoglienza: "+segnalato.getStrutturaAccoglienza()+") " :"";
			
			domicilio = this.getDescrizioneIndirizzo(segnalato.getDomicilio());
			
			if(segnalato.getTelefono() != null || segnalato.getCel() != null)
				telCel = format(segnalato.getTelefono()) + " / " + format(segnalato.getCel());
			email = segnalato.getEmail();
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
			intermediazioneAbMan = getSchedaJsonIntermediazioneAb(s.getId());
			orientamentoLavoro= getSchedaJsonOrientamentoLavoro(s.getId());
			mediazioneCultMan = getSchedaJsonMediazioneCult(s.getId());
			orientamentoIstruzioneMan = getSchedaJsonOrientamentoIstruzione(s.getId());
			
			/*Valorizzazione Informazioni Sottoscrizione Privacy*/
			SsSchedaPrivacyPK pk = new SsSchedaPrivacyPK();
			pk.setCf(cf);
			pk.setOrganizzazioneId(a.getSsRelUffPcontOrg().getSsOOrganizzazione().getId());
			dto.setObj(pk);
			
			SsSchedaPrivacy sp = schedaService.findSchedaPrivacyById(dto);
			this.privacy = (sp!=null && sp.getDtIns()!=null) ? this.getMessaggioPrivacy(sp.getDtIns()) : null;
			
			
			// dati riferimento
			SsSchedaRiferimento r = s.getRiferimento();
			if(r != null){
				it.webred.cs.csa.ejb.dto.BaseDTO dtoCS = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(dtoCS );
				dtoCS.setObj(r.getCodStatoCivile());
				CsTbStatoCivile csTbStatoCivile = configService.getStatoCivileByCodice(dtoCS);
				
				dtoCS.setObj(r.getRelazioneId());
				CsTbTipoRapportoCon csTbTipoRelazine = configService.getTipoRapportoConByCodice(dtoCS);
				
				if(r.getCognome()!=null || r.getNome()!=null)
					cognomeNomeRiferimento = (format(r.getCognome()) + " " + format(r.getNome())).toUpperCase();
				
				problemiRiferimento = r.getProblemi_desc();
				telefonoRiferimento = r.getTelefono();
				recapitoRiferimento = r.getRecapito();
				celRiferimento = r.getCel();
				emailRiferimento = r.getEmail();
				dataNascitaRiferimento = r.getDataNascita();
				sessoRiferimento = r.getSesso();
				statoCivileRiferimento = csTbStatoCivile!=null ? csTbStatoCivile.getDescrizione() : "";
				parentelaRiferimento = csTbTipoRelazine!=null ? csTbTipoRelazine.getDescrizione() : "";
				comuneNascitaRiferimento = r.getComuneNascitaDes() + " (" + r.getProvNascitaCod() + ") " 
						+ r.getStatoNascitaDes() != null ? r.getStatoNascitaDes() : "";
			}		
			
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
        		List<SsDiario> diario = schedaService.readDiarioSociale(dto);
            	List<Nota> note = new ArrayList<Nota>();
            	for(SsDiario nota: diario)
            		if(canReadNotaDiario(nota))
            			note.add(new Nota(nota, this.getCognomeNomeUtente(nota.getAutore())));
            	diarioSociale.populateNote(note);
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
	
	private boolean canReadNotaDiario(SsDiario nota){
		if(nota.getPubblica())
			return true;
		if(nota.getAutore().equals(this.getUserNameOperatore()))
			return true;
		if(canReadDiario() && nota.getEnte().getId()==this.getPreselectedPContatto().getOrganizzazione().getId())
			return true;
		
		return false;
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

	public IIntermediazioneAb getIntermediazioneAbMan() {
		return intermediazioneAbMan;
	}

	public IOrientamentoIstruzione getOrientamentoIstruzioneMan() {
		return orientamentoIstruzioneMan;
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

	public String getInviante() {
		return inviante;
	}

	public void setInviante(String inviante) {
		this.inviante = inviante;
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
	public String getCognomeNomeRiferimento() {
		return cognomeNomeRiferimento;
	}

	public void setCognomeNomeRiferimento(String cognomeNomeRiferimento) {
		this.cognomeNomeRiferimento = cognomeNomeRiferimento;
	}

	public String getParentelaRiferimento() {
		return parentelaRiferimento;
	}

	public void setParentelaRiferimento(String parentelaRiferimento) {
		this.parentelaRiferimento = parentelaRiferimento;
	}

	public String getProblemiRiferimento() {
		return problemiRiferimento;
	}

	public void setProblemiRiferimento(String problemiRiferimento) {
		this.problemiRiferimento = problemiRiferimento;
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

	public void setIntermediazioneAbMan(IIntermediazioneAb intermediazioneAbMan) {
		this.intermediazioneAbMan = intermediazioneAbMan;
	}

	public void setOrientamentoIstruzioneMan(IOrientamentoIstruzione orientamentoIstruzioneMan) {
		this.orientamentoIstruzioneMan = orientamentoIstruzioneMan;
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

	public IOrientamentoLavoro getOrientamentoLavoro() {
		return orientamentoLavoro;
	}

	public void setOrientamentoLavoro(IOrientamentoLavoro orientamentoLavoro) {
		this.orientamentoLavoro = orientamentoLavoro;
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

	public IMediazioneCult getMediazioneCultMan() {
		return mediazioneCultMan;
	}

	public void setMediazioneCultMan(IMediazioneCult mediazioneCultMan) {
		this.mediazioneCultMan = mediazioneCultMan;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getTelefonoRiferimento() {
		return telefonoRiferimento;
	}

	public void setTelefonoRiferimento(String telefonoRiferimento) {
		this.telefonoRiferimento = telefonoRiferimento;
	}


	public String getRecapitoRiferimento() {
		return recapitoRiferimento;
	}

	public void setRecapitoRiferimento(String recapitoRiferimento) {
		this.recapitoRiferimento = recapitoRiferimento;
	}

	public String getCelRiferimento() {
		return celRiferimento;
	}

	public void setCelRiferimento(String celRiferimento) {
		this.celRiferimento = celRiferimento;
	}

	public String getEmailRiferimento() {
		return emailRiferimento;
	}

	public void setEmailRiferimento(String emailRiferimento) {
		this.emailRiferimento = emailRiferimento;
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

	public Date getDataNascitaRiferimento() {
		return dataNascitaRiferimento;
	}

	public void setDataNascitaRiferimento(Date dataNascitaRiferimento) {
		this.dataNascitaRiferimento = dataNascitaRiferimento;
	}

	public String getSessoRiferimento() {
		return sessoRiferimento;
	}

	public void setSessoRiferimento(String sessoRiferimento) {
		this.sessoRiferimento = sessoRiferimento;
	}

	public String getStatoCivileRiferimento() {
		return statoCivileRiferimento;
	}

	public void setStatoCivileRiferimento(String statoCivileRiferimento) {
		this.statoCivileRiferimento = statoCivileRiferimento;
	}

	public String getComuneNascitaRiferimento() {
		return comuneNascitaRiferimento;
	}

	public void setComuneNascitaRiferimento(String comuneNascitaRiferimento) {
		this.comuneNascitaRiferimento = comuneNascitaRiferimento;
	}

}
