package it.webred.ss.web.bean.wizard;

import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.web.bean.util.Organizzazione;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Nota {
	
	private final String MSG_NOTA_PRIVATA="Operatore corrente non autorizzato a visualizzare il contenuto della nota privata";
	private Long id;
	private String operatore;
	private String descOperatore;
	private Organizzazione ente;
	private Date data;
	private String nota = "";
	private Boolean pubblica;
	private String tipologia;
	private boolean canRead;
	private boolean canDelete;
	
	private String formattedData;
	
	public Nota(){
		
	}
	
	public Nota(String operatore, Date data, String nota){
		this.operatore = operatore;
		this.data = data;
		this.nota = nota;
	}
	
	public Nota(SsDiario diario, String descOperatore){
		id = diario.getId();
		operatore = diario.getAutore();
		ente = new Organizzazione(diario.getEnte());
		data = diario.getData();
		nota = diario.getNota();
		pubblica = diario.getPubblica();
		this.descOperatore = descOperatore;
	}
	
	public Nota(SsDiario diario, String descOperatore, boolean canRead, boolean canDelete){
		this(diario, descOperatore);
		this.canRead = canRead;
		this.canDelete = canDelete;
	}
	
	
	public String getFormattedData() {
		formattedData = data!=null ? new SimpleDateFormat("dd/MM/yyyy").format(data) : null;
		return formattedData;
	}

	public void setFormattedData(String formattedData) {
		this.formattedData = formattedData;
	}

	public Boolean getPubblica() {
		return pubblica;
	}

	public void setPubblica(Boolean pubblica) {
		this.pubblica = pubblica;
	}

	public void setAuthor(String operatore, Organizzazione ente){
		if(data==null) data = new Date();
		this.operatore = operatore;
		this.ente = ente;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Organizzazione getEnte() {
		return ente;
	}

	public void setEnte(Organizzazione ente) {
		this.ente = ente;
	}

	public String getOperatore() {
		return operatore;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}

	public boolean isEmpty() {
		return nota.isEmpty();
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public void fillModel(SsDiario model) {
		model.setId(id);
		model.setAutore(operatore);
		model.setData(data);
		SsOOrganizzazione o = new SsOOrganizzazione();
		ente.fillModel(o);
		model.setEnte(o);
		model.setPubblica(pubblica);
		model.setNota(nota);
	}

	public void fillModel(SsDiario model, SsAnagrafica anaModel) {
		fillModel(model);
		model.setSoggetto(anaModel);
	}

	public String getLabelTipologia() {
		tipologia = pubblica ? "pubblica" : "privata";
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getDescOperatore() {
		return descOperatore;
	}

	public void setDescOperatore(String descOperatore) {
		this.descOperatore = descOperatore;
	}

	public String getTipologia() {
		return tipologia;
	}

	public boolean isCanRead() {
		return canRead;
	}

	public void setCanRead(boolean canRead) {
		this.canRead = canRead;
	}

	public String getMSG_NOTA_PRIVATA() {
		return MSG_NOTA_PRIVATA;
	}
	
	
}
