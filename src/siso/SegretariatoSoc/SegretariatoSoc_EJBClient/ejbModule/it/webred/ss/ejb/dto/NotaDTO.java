package it.webred.ss.ejb.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.webred.ss.data.model.SsDiario;

public class NotaDTO implements Serializable {

	private final String MSG_NON_AUTORIZZATO = "** L'operatore corrente non è autorizzato a leggere il contenuto della nota **"; 
	
	private boolean canRead;
	private boolean canDelete;
	
	private Long id;
	private String nota;
	private Date data;
	private Long anagraficaId;
	
	//OPERATORE
	private String opUsername;
	private String opDenominazione;
	
	//ENTE
	private Long orgId;
	private String orgDenominazione;
	private String orgBelfiore;
	
	private Boolean pubblica;
	
	public NotaDTO() {
		this.canRead = true;
	}
	
	public NotaDTO(SsDiario d, String denominazione) {
		super();
		this.id = d.getId();
		this.nota = d.getNota();
		this.data = d.getData();
		this.opUsername = d.getAutore();
		this.opDenominazione = denominazione;
		this.orgId = d.getEnte().getId();
		this.orgDenominazione = d.getEnte().getNome();
		this.orgBelfiore = d.getEnte().getCodRouting();
		this.pubblica = d.getPubblica();
		this.anagraficaId = d.getSoggetto().getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getOpUsername() {
		return opUsername;
	}

	public void setOpUsername(String opUsername) {
		this.opUsername = opUsername;
	}

	public String getOpDenominazione() {
		return opDenominazione;
	}

	public void setOpDenominazione(String opDenominazione) {
		this.opDenominazione = opDenominazione;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgDenominazione() {
		return orgDenominazione;
	}

	public void setOrgDenominazione(String orgDenominazione) {
		this.orgDenominazione = orgDenominazione;
	}

	public String getOrgBelfiore() {
		return orgBelfiore;
	}

	public void setOrgBelfiore(String orgBelfiore) {
		this.orgBelfiore = orgBelfiore;
	}

	public Boolean getPubblica() {
		return pubblica;
	}

	public void setPubblica(Boolean pubblica) {
		this.pubblica = pubblica;
	}

	public boolean isCanRead() {
		return canRead;
	}

	public void setCanRead(boolean canRead) {
		this.canRead = canRead;
	}

	public String getNotaNonAutorizzata() {
		return this.MSG_NON_AUTORIZZATO;
	}
	
	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public String getLabelTipologia() {
		String tipologia = pubblica ? "pubblica" : "privata";
		return tipologia;
	}
	
	public String getFormattedData(){
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
	}

	public Long getAnagraficaId() {
		return anagraficaId;
	}

	public void setAnagraficaId(Long anagraficaId) {
		this.anagraficaId = anagraficaId;
	}
	
}
