package it.webred.ss.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.webred.ss.data.model.tb.CsOSettoreLIGHT;
import it.webred.ss.data.model.tb.SsOperatoreAnagrafica;


/**
 * The persistent class for the SS_SCHEDA_ACCESSO database table.
 * 
 */
@Entity
@Table(name="SS_SCHEDA_ACCESSO")
@NamedQuery(name="SsSchedaAccesso.findAll", query="SELECT c FROM SsSchedaAccesso c")
public class SsSchedaAccesso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_SCHEDA_ACCESSO_ID_GENERATOR", sequenceName="SQ_SS_SCHEDA_ACCESSO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_SCHEDA_ACCESSO_ID_GENERATOR")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA")//opzionale: se non presente assume che abbia lo stesso nome nella tabella
	private Date data;
	
	private String modalita;
	
	private String operatore;
	
	private String interlocutore;
	
	private String descrizione;
	
	private String motivo;
	
	@Column(name="MOTIVO_DESC")
	private String motivoDesc;

	@Column(name="UTENTE_ACCOMPAGNATO")
	private Boolean utenteAccompagnato;			//SISO-346
	

	@Column(name="UTENTE_PRESENTE_INFORMATO")
	private Boolean utentePresenteInformato;;	//SISO-346

	@Column(name="INVIATO_DA")
	private Long inviatoDa; //SISO-346
	private String accompagnatore; //SISO-346
	

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="REL_UPO_UFFICIO_ID", referencedColumnName="UFFICIO_ID"),
		@JoinColumn(name="REL_UPO_PUNTO_CONTATTO_ID", referencedColumnName="PUNTO_CONTATTO_ID"),
		@JoinColumn(name="REL_UPO_ORGANIZZAZIONE_ID", referencedColumnName="ORGANIZZAZIONE_ID")
		})
	private SsRelUffPcontOrg ssRelUffPcontOrg;
	
	@ManyToOne
	@JoinColumn(name="OPERATORE", referencedColumnName="OPERATORE", insertable=false, updatable=false)
	private SsOperatoreAnagrafica anagraficaOperatore;
	
	@ManyToOne
	@JoinColumn(name="inviato_da", insertable=false, updatable=false)
	private CsOSettoreLIGHT settoreInviante;
	
	public SsOperatoreAnagrafica getAnagraficaOperatore() {
		return anagraficaOperatore;
	}

	public void setAnagraficaOperatore(SsOperatoreAnagrafica anagraficaOperatore) {
		this.anagraficaOperatore = anagraficaOperatore;
	}

	public CsOSettoreLIGHT getSettoreInviante() {
		return settoreInviante;
	}

	public void setSettoreInviante(CsOSettoreLIGHT settoreInviante) {
		this.settoreInviante = settoreInviante;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getModalita() {
		return modalita;
	}

	public void setModalita(String modalita) {
		this.modalita = modalita;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getInterlocutore() {
		return interlocutore;
	}

	public void setInterlocutore(String interlocutore) {
		this.interlocutore = interlocutore;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getMotivoDesc() {
		return motivoDesc;
	}

	public void setMotivoDesc(String motivoDesc) {
		this.motivoDesc = motivoDesc;
	}

	public SsRelUffPcontOrg getSsRelUffPcontOrg() {
		return ssRelUffPcontOrg;
	}

	public void setSsRelUffPcontOrg(SsRelUffPcontOrg ssRelUffPcontOrg) {
		this.ssRelUffPcontOrg = ssRelUffPcontOrg;
	}

	public Boolean getUtenteAccompagnato() {
		return utenteAccompagnato;
	}

	public void setUtenteAccompagnato(Boolean utenteAccompagnato) {
		this.utenteAccompagnato = utenteAccompagnato;
	}

	public Boolean getUtentePresenteInformato() {
		return utentePresenteInformato;
	}

	public void setUtentePresenteInformato(Boolean utentePresenteInformato) {
		this.utentePresenteInformato = utentePresenteInformato;
	}

	public Long getInviatoDa() {
		return inviatoDa;
	}

	public void setInviatoDa(Long inviatoDa) {
		this.inviatoDa = inviatoDa;
	}

	public String getAccompagnatore() {
		return accompagnatore;
	}

	public void setAccompagnatore(String accompagnatore) {
		this.accompagnatore = accompagnatore;
	}
	
	//Metodi per recupero dati in formato testuale ai fini REPORT/VISUALIZZAZIONE
	public String getStampaDesInterlocutore(){
		String accompagnatore = this.accompagnatore!=null ? this.accompagnatore : "non specificato";
		String interlocutore = this.interlocutore;
		interlocutore += (this.utenteAccompagnato!=null && this.utenteAccompagnato)? " (accompagnato da: "+accompagnatore+" )" : "";
		interlocutore += (this.utentePresenteInformato!=null && this.utentePresenteInformato) ? " (utente presente o informato)" :"";
		
		return interlocutore;
	}	 

	public String getStampaDesMotivoDes(){
			String inviante = this.settoreInviante!=null ? settoreInviante.getNome() : "";
			
			String motivo = this.motivo;
			motivo += this.settoreInviante!=null ? " "+inviante : "";
			motivo += this.motivoDesc!=null ? ": "+this.motivoDesc: "";
			return motivo;
	}
	
	public String getStampaDesOperatore() {
		if(this.anagraficaOperatore!=null && this.anagraficaOperatore.getStampaDesc()!=null)
			return anagraficaOperatore.getStampaDesc();
		else
			return this.operatore;
	}
}