package it.webred.ss.web.bean.wizard;

import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.util.Organizzazione;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Nota {
	
	private Long id;
	private String operatore;
	private String descOperatore;
	private Organizzazione ente;
	private Date data;
	private String nota = "";
	private Boolean pubblica;
	private String tipologia;
	
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
	
	
	
	public String getFormattedData() {
		formattedData = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
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
		data = new Date();
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

	public String getTipologia() {
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
	

}
