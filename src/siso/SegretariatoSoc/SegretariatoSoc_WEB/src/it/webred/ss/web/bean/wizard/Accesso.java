package it.webred.ss.web.bean.wizard;

import it.webred.cs.data.DataModelCostanti.Scheda;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaAccessoInviante;
import it.webred.ss.web.bean.util.Organizzazione;
import it.webred.ss.web.bean.util.PuntoContatto;
import it.webred.ss.web.bean.util.Ufficio;

import java.util.Date;

public class Accesso {
	
	private Long id;
	private Date dataAccesso;
	private String operatore;
	private String descrizione;
	private String modalitaAccesso;
	private String interlocutore;
	
	private String motivo;
	private String motivoDesc;
	private PuntoContatto puntoContatto;

	private Boolean utenteAccompagnato;			//SISO-346
	private Boolean utentePresenteInformato;;	//SISO-346
	private Long inviante;					//SISO-346
	private String accompagnatore;				//SISO-346
	private String accompagnatorePopup;				//SISO-346
	
	
	
	
	public Accesso(){
		dataAccesso = new Date();
		puntoContatto = new PuntoContatto();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataAccesso() {
		return dataAccesso;
	}
	public void setDataAccesso(Date dataAccesso) {
		this.dataAccesso = dataAccesso;
	}
	public String getOperatore() {
		return operatore;
	}
	public void setOperatore(String nomeOperatore) {
		this.operatore = nomeOperatore;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getModalitaAccesso() {
		return modalitaAccesso;
	}
	public void setModalitaAccesso(String modalitaAccesso) {
		this.modalitaAccesso = modalitaAccesso;
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
	

	public void fillModel(SsSchedaAccesso accessoModel) {
		accessoModel.setId(id);
		accessoModel.setData(dataAccesso);
		accessoModel.setOperatore(operatore);
		accessoModel.setModalita(modalitaAccesso);
		accessoModel.setInterlocutore(interlocutore);
		accessoModel.setDescrizione(descrizione);
		accessoModel.setMotivo(this.motivo);
		accessoModel.setMotivoDesc(this.motivoDesc);
		//INIZIO SISO-346
		
		boolean isUtente = Scheda.Interlocutori.UTENTE.equalsIgnoreCase(getInterlocutore());
		accessoModel.setUtenteAccompagnato(isUtente ? this.utenteAccompagnato : null);
		accessoModel.setUtentePresenteInformato(!isUtente ? this.utentePresenteInformato : null);
				

		accessoModel.setInviatoDa(inviante!=null && inviante>0 ? inviante : null);	
		if (accessoModel.getUtenteAccompagnato()!=null && accessoModel.getUtenteAccompagnato()==true) {
			accessoModel.setAccompagnatore(accompagnatore);	 
		}
		//FINE SISO-346
	}

	public void initFromModel(SsSchedaAccesso accesso) {
		if(accesso != null){
			id = accesso.getId();
			dataAccesso = accesso.getData();
			operatore = accesso.getOperatore();
			descrizione = accesso.getDescrizione();
			modalitaAccesso = accesso.getModalita();
			interlocutore = accesso.getInterlocutore();
			motivo = accesso.getMotivo();
			motivoDesc = accesso.getMotivoDesc();
			puntoContatto.initFromModel(accesso.getSsRelUffPcontOrg());
			utenteAccompagnato = accesso.getUtenteAccompagnato();
			utentePresenteInformato = accesso.getUtentePresenteInformato();
			inviante = accesso.getInviatoDa();			//SISO-346
			accompagnatore = accesso.getAccompagnatore();			//SISO-346
		}
		
	}
	
	public void initFromModelAccessoInviante(SsSchedaAccessoInviante accesso) {
		if(accesso != null){
			id = accesso.getId();
			dataAccesso = accesso.getOrigDataAccesso();
			operatore = accesso.getOrigCognomeOperatore()+" "+accesso.getOrigNomeOperatore() ;
			descrizione = "---";
			modalitaAccesso = accesso.getOrigAccessoModalita();
			interlocutore = accesso.getOrigAccessoIneterlocutore();
			motivo = accesso.getOrigAccessoMotivo();
			motivoDesc = "---";
			puntoContatto=new PuntoContatto();
			Organizzazione organizzazione=new Organizzazione();
			organizzazione.setNome(accesso.getOrigDescOrganizzazione());
			organizzazione.setId(accesso.getOrigIdOrganizzazione());
			puntoContatto.setOrganizzazione(organizzazione);
			Ufficio ufficio=new Ufficio();
			ufficio.setNome(accesso.getOrigNomeUfficio());
			ufficio.setId(accesso.getOrigIdUfficio());
			puntoContatto.setUfficio(ufficio);
			puntoContatto.setNomePContatto(accesso.getOrigPuntoContatto());
		}
		
	}
	
	public PuntoContatto getPuntoContatto() {
		return puntoContatto;
	}

	public void setPuntoContatto(PuntoContatto puntoContatto) {
		this.puntoContatto = puntoContatto;
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

	public String getAccompagnatore() {
		return accompagnatore;
	}

	public Long getInviante() {
		return inviante;
	}

	public void setInviante(Long inviante) {
		this.inviante = inviante;
	}

	public void setAccompagnatore(String accompagnatore) {
		this.accompagnatore = accompagnatore;
	}

	public String getAccompagnatorePopup() {
		return accompagnatorePopup;
	}

	public void setAccompagnatorePopup(String accompagnatorePopup) {
		this.accompagnatorePopup = accompagnatorePopup;
	}

	public String getInterlocutoreDesc(){
		String accompagnatore = this.accompagnatore!=null ? this.accompagnatore : "non specificato";
		String interlocutore = this.interlocutore;
		interlocutore += (this.utenteAccompagnato!=null && utenteAccompagnato)? " (accompagnato da: "+accompagnatore+" )" : "";
		interlocutore += (this.utentePresenteInformato!=null && utentePresenteInformato) ? " (utente presente o informato)" :"";
		
		return interlocutore;
  }	 
  
	public String getMotivoDesc(CsOSettoreBASIC settore){
		String inviante = settore!=null ? settore.getNome() : "";
		String motivo = this.motivo;
		motivo += settore!=null ? " "+inviante : "";
		motivo += this.motivoDesc!=null ? ": "+this.motivoDesc : "";
		return motivo;
  }
}
