package it.webred.ss.web.bean.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import it.webred.cs.csa.ejb.client.AccessTableDatiPorSessionBeanRemote;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.Scheda;
import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.manbean.ConsensoPrivacyMan;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.jsf.manbean.por.DatiPorSchedaMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsAnagrafica;
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
import it.webred.ss.ejb.dto.NotaDTO;
import it.webred.ss.ejb.dto.SchedaUdcDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.wizard.Accesso;
import it.webred.ss.web.bean.wizard.ServiziRichiestiInterventiCustomBean;

/**
 * 
 * <h1>ViewSchedaBean.java</h1>
 *
 * <p>
 * </p>
 *
 * @since 1.26.12
 * @version 1.0.1
 * 
 * @lastUpdate 2025-11-12 - DDV
 */
@ManagedBean
@ViewScoped
public class ViewSchedaBean extends SegretariatoSocBaseBean {

	private AccessTableDatiPorSessionBeanRemote porService = (AccessTableDatiPorSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDatiPorSessionBean");
	
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
	private boolean invalidita = false;
	private String percInvalidita;
	
	private FormazioneLavoroMan formLavoroSegnalato;

	private DatiPorSchedaMan iDatiPor;
	
	private IStranieri stranieriMan;
	private IAbitazione abitazioneMan;
	private IFamConviventi famConviventiMan;
	
	private ServiziRichiestiInterventiCustomBean serviziRichiestiInterventiCustomBean; //SISO-438 
	
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
	private List<NotaDTO> diarioSociale = new ArrayList<NotaDTO>();
	
	private String indietroButtonLink;
	
	/**
	 * 
	 * <h1>ViewSchedaBean</h1>
	 *
	 * <p>
	 * </p>
	 *
	 * @since 1.26.12
	 * @version 1.0.1
	 * 
	 * @lastUpdate 2025-11-12 - DDV
	 */
	public ViewSchedaBean() {
		
		this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		
		String selectedScheda = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
		if (selectedScheda == null)
			return;
		
		SsSchedaSessionBeanRemote schedaService;
		
		try {
			
			this.indietroButtonLink = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("previousPage");
			
			schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			//ID ORGANIZZAZIONE CORRENTE
			dto.setOrganizzazione(getPreselectedPContatto().getOrganizzazione().getId());
			dto.setObj(new Long(selectedScheda));
			dto.setObj2(canReadDiario());
			SchedaUdcDTO schedaUdcDTO = schedaService.loadSchedaUdcCompleta(dto);
			
			SsScheda ssScheda = schedaUdcDTO.getScheda();
			
			// dati accesso
			SsSchedaAccesso ssSchedaAccesso = ssScheda.getAccesso();
			this.data = ssSchedaAccesso.getData();
			this.operatore = ssSchedaAccesso.getOperatore();
			
			this.puntoContatto = new PuntoContatto();
			this.puntoContatto.initFromModel(ssSchedaAccesso.getSsRelUffPcontOrg());
			this.modalita = ssSchedaAccesso.getModalita();
			
			dto.setObj(ssScheda.getTipo());
			SsTipoScheda tipoBean = this.confService.readTipoSchedaById(dto);
			this.tipoScheda = tipoBean != null ? tipoBean.getTipo() : null;
			this.descrizione = ssSchedaAccesso.getDescrizione();
			
			String accompagnatore = ssSchedaAccesso.getAccompagnatore() != null ? ssSchedaAccesso.getAccompagnatore() : "non specificato";
			this.interlocutore = ssSchedaAccesso.getInterlocutore();
			this.interlocutore += (ssSchedaAccesso.getUtenteAccompagnato() != null && ssSchedaAccesso.getUtenteAccompagnato()) ? " (accompagnato da: " + accompagnatore + " )" : "";
			this.interlocutore += (ssSchedaAccesso.getUtentePresenteInformato() != null && ssSchedaAccesso.getUtentePresenteInformato()) ? " (utente presente o informato)" :"";
	
			CsOSettoreLIGHT settore = ssSchedaAccesso.getSettoreInviante();
			String inviante = settore != null ? format(settore.getNome()) : "";
			
			this.motivo = ssSchedaAccesso.getMotivo();
			this.motivo += settore != null ? " " + inviante : "";
			this.motivo += ssSchedaAccesso.getMotivoDesc() != null ? ": " + ssSchedaAccesso.getMotivoDesc() : "";
			
			// dati segnalante
			SsSchedaSegnalante segnalante = ssScheda.getSegnalante();
			if (segnalante != null) {
				this.hideSegnalante = false;
				it.webred.cs.csa.ejb.dto.BaseDTO dtoCS = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(dtoCS );

				dtoCS.setObj(segnalante.getCsOSettoreId());
				CsOSettore settEnte = this.configurationCsEnteBean.getSettoreById(dtoCS);
				
				if (segnalante.getCognome() != null || segnalante.getNome() != null)
					this.cognomeNomeSegnalante = (format(segnalante.getCognome()) + " " + format(segnalante.getNome())).toUpperCase();
				this.ente = settEnte != null ? settEnte.getNome() : segnalante.getEnte_servizio();
				
				this.telCelSegnalante = segnalante.getTelefono() != null ? format(segnalante.getTelefono()) : "";
				if (segnalante.getCel() != null) {
					this.telCelSegnalante += !this.telCelSegnalante.isEmpty() ? " / " : "";
					this.telCelSegnalante += format(segnalante.getCel());
				}
				
				this.emailSegnalante = segnalante.getEmail() != null ? segnalante.getEmail() : "";
				if (segnalante.getVia() != null || segnalante.getComune() != null)
					this.indirizzo = format(segnalante.getVia()) + ", " + format(segnalante.getStampaDesComuneResidenza());
				
				this.dataNascitaSegnalante = segnalante.getDataNascita();
				this.sessoSegnalante = segnalante.getSesso();
				//SISO-906 -Specifica del parente quando affidatario
				this.relazione = segnalante.getTbRelazione() != null
						? format(segnalante.getTbRelazione().getDescrizione().concat(segnalante.getAffidatario() ? " - Affidatario" : ""))
						: "";
				this.statoCivileSegnalante = segnalante.getTbStatoCivile() != null ? segnalante.getTbStatoCivile().getDescrizione() : "";
				
				this.comuneNascitaSegnalante = segnalante.getComuneNascitaDes() + " (" + segnalante.getProvNascitaCod() + ") " + 
					segnalante.getStatoNascitaDes() != null ? segnalante.getStatoNascitaDes() : "";
			} else
				if (Scheda.Interlocutori.UTENTE.equalsIgnoreCase(ssSchedaAccesso.getInterlocutore())) 
					this.hideSegnalante = true;	
			
			// dati segnalato
			dto.setObj(ssScheda.getSegnalato());			
			SsSchedaSegnalato segnalato = schedaService.readSegnalatoById(dto);
			SsAnagrafica anagrafica = segnalato.getAnagrafica();
		
			this.cognomeNomeSegnalato = (anagrafica.getCognome() + " " + anagrafica.getNome()).toUpperCase();
			this.dataNascita = anagrafica.getData_nascita();
			this.comuneNascita = anagrafica.getLuogoDiNascita();
			this.sesso = anagrafica.getSesso();
			this.cf = anagrafica.getCf();
			this.statoCivile = anagrafica.getStato_civile();
			this.cittadinanza = anagrafica.getCittadinanza();
			this.cittadinanza2 = format(anagrafica.getCittadinanza2());
			this.alias = anagrafica.getAlias();
			
			if (anagrafica.getTbCittadinanzaAcq() != null)
				this.cittadinanza += " (" + anagrafica.getTbCittadinanzaAcq().getDescrizione() + ")";
			
			this.residenza = segnalato.getSenzaFissaDimora() != null && segnalato.getSenzaFissaDimora() ? DataModelCostanti.SENZA_FISSA_DIMORA + " " : "";
			this.residenza += segnalato.getResidenza() != null ? segnalato.getResidenza().getStampaDesIndirizzo() : "";
			
			this.domicilio = segnalato.getDomicilio() != null ? segnalato.getDomicilio().getStampaDesIndirizzo() : "";
			this.domicilio += segnalato.getNoteDomicilio() != null ? " (" + segnalato.getNoteDomicilio() + ")" : "";
			
			this.telCel = segnalato.getTelefono() != null ? format(segnalato.getTelefono()) : "";
			this.telCel += segnalato.getTitolareTelefono() != null ? " (" + format(segnalato.getTitolareTelefono()) + ")" : "";
			if (segnalato.getCel() != null) {
				this.telCel += !this.telCel.isEmpty() ? " / " : "";
				this.telCel += format(segnalato.getCel());
				this.telCel += segnalato.getTitolareCellulare() != null ? " (" + format(segnalato.getTitolareCellulare()) + ")" : "";
			}
			
			this.email = segnalato.getEmail();
			this.email += segnalato.getTitolareEmail() != null ? " (" + format(segnalato.getTitolareEmail()) + ")" : "";
			this.medico = segnalato.getMedico();
			this.tesseraSanitaria = segnalato.getTessera_sanitaria();
			this.stp = segnalato.getStp() != null ? segnalato.getStp() : false;
			this.invalidita = segnalato.getInvalidita() != null;
			this.percInvalidita = segnalato.getInvalidita() != null ? segnalato.getInvalidita().toString() : "";
			
			this.formLavoroSegnalato = new FormazioneLavoroMan();
			this.formLavoroSegnalato.setIdCondLavorativa(segnalato.getCondLavoroId());
			this.formLavoroSegnalato.setIdProfessione(segnalato.getProfessioneId());
			this.formLavoroSegnalato.setIdTitoloStudio(segnalato.getTitoloStudioId());
			this.formLavoroSegnalato.setIdSettoreImpiego(segnalato.getSettImpiegoId());
			
			/* TAB UTENTE */
			this.stranieriMan = getSchedaJsonStranieri(ssScheda.getId());
			this.abitazioneMan = getSchedaJsonAbitazione(ssScheda.getId());
			this.famConviventiMan = getSchedaJsonFamConviventi(ssScheda.getId());
			
			/* TAB SERVIZI */
			//SISO-438 
			this.serviziRichiestiInterventiCustomBean = new ServiziRichiestiInterventiCustomBean();
			this.serviziRichiestiInterventiCustomBean.loadManJsonServiziRichiesti(ssScheda, segnalato);
			
			/* Valorizzazione Informazioni Sottoscrizione Privacy */
			boolean beneficiarioRdC = this.verificaPresenzaRdC(this.cf);
			this.consensoMan = new ConsensoPrivacyMan
					( this.cf
					, ssSchedaAccesso.getSsRelUffPcontOrg().getSsOOrganizzazione().getId()
					, anagrafica.isAnonimo()
					, beneficiarioRdC
					);
			
			//SISO-947 dati riferimenti
			SsSchedaRiferimento ssSchedaRiferimento = ssScheda.getRiferimento();
			SsSchedaRiferimento ssSchedaRiferimento2 = ssScheda.getRiferimento2();
			SsSchedaRiferimento ssSchedaRiferimento3 = ssScheda.getRiferimento3();
			
			this.listaRiferimenti = new ArrayList<SsSchedaRiferimento>();
			
			this.listaRiferimenti.add(ssSchedaRiferimento);
			this.listaRiferimenti.add(ssSchedaRiferimento2);
			this.listaRiferimenti.add(ssSchedaRiferimento3);
			
			// dati motivazione
			this.selectedMotivazioni = schedaUdcDTO.getListaMotivazioni();
			this.motivazioneAltro = ssScheda.getMotivazione().getAltro();
			
			// dati interventi
			this.selectedInterventi = schedaUdcDTO.getListaInterventi();
			this.interventiAltro = ssScheda.getInterventi().getAltro();
			
			// dati diario sociale
			this.diarioSociale = schedaUdcDTO.getNoteDiario();
		
			// Dati accesso orig (se scheda inviata)
			this.accessoOrig = null;
			SsSchedaAccessoInviante schedaOriginale = recuperaSsSchedaAccessoInvianteFromSsScheda(ssScheda);
			if (schedaOriginale != null && schedaOriginale.getId() != null && schedaOriginale.getId() > -1) {
				// inizializza accessoOrig tramite la schedaInviante recuperata
				this.accessoOrig = new Accesso();
				this.accessoOrig.initFromModelAccessoInviante(schedaOriginale);
			}
			
			// Tab Progetto Richiesto
			if (ssScheda != null) {
				Long schedaId = ssScheda.getId();
				
				it.webred.cs.csa.ejb.dto.BaseDTO baseDTOCs = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillEnte(baseDTOCs);
				baseDTOCs.setObj(schedaId);
				
				String enteId = baseDTOCs.getEnteId();
				BigDecimal idCondizioneLavorativa = this.formLavoroSegnalato.getIdCondLavorativa();
				
				CsExtraFseDatiLavoro csExtraFseDatiLavoro = this.porService.findDatiPorUdcBySchedaId(baseDTOCs);
				if (csExtraFseDatiLavoro != null) {
					this.iDatiPor = new DatiPorSchedaMan(csExtraFseDatiLavoro, enteId, idCondizioneLavorativa);
				} else {
					this.iDatiPor = new DatiPorSchedaMan(enteId, idCondizioneLavorativa);
				}
			}
			
		} catch (Exception e) {
			addError("lettura.error");
			logger.error("Errore ViewSchedaBean: " + e.getMessage(), e);
		}
	}
	
	public void goBack() {
		try {
			if (this.indietroButtonLink == null || this.indietroButtonLink.isEmpty())
				this.indietroButtonLink = "home.faces";
			FacesContext.getCurrentInstance().getExternalContext().redirect(this.indietroButtonLink);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public String titoloTabRiferimento(SsSchedaRiferimento riferimento, Integer numRiferimento) {
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

	public List<NotaDTO> getDiarioSociale() {
		return diarioSociale;
	}

	public void setDiarioSociale(List<NotaDTO> diarioSociale) {
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

	public void setServiziRichiestiInterventiCustomBean(ServiziRichiestiInterventiCustomBean serviziRichiestiInterventiCustomBean) {
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
	
	public DatiPorSchedaMan getiDatiPor() {
		return iDatiPor;
	}

	public void setiDatiPor(DatiPorSchedaMan iDatiPor) {
		this.iDatiPor = iDatiPor;
	}
	
}
