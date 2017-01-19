package it.webred.ss.web.bean.wizard;

import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.web.bean.util.PuntoContatto;

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
		}
		
	}
	
	public PuntoContatto getPuntoContatto() {
		return puntoContatto;
	}

	public void setPuntoContatto(PuntoContatto puntoContatto) {
		this.puntoContatto = puntoContatto;
	}

}
