package it.webred.ss.web.bean.util;

import it.webred.ss.data.model.SsUfficio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class Ufficio implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Long totaleSchede;
	private Long schedeInSospeso;
	private Long schedeInCarico;
	private Long schedeEliminate;
	private Long schedeRicevute;
	private List<SelectItem> listaPContatto;

	private String templateMailInvio;
	private String email;
	private String tel;

	private boolean reqStranieri;
	private boolean reqBisogni;
	private boolean reqServizi;
	private boolean visInterventi;
	private boolean visInviiEnte;
	private boolean visColCsoc;
	private boolean visColInterventi;

	public Ufficio(SsUfficio ufficio, Long totaleRegistrate,
			Long schedeInSospeso, Long inCarico, Long schedeEliminate, Long schedeRicevute) {
		this.id = ufficio.getId();
		this.nome = ufficio.getNome();
		this.templateMailInvio = ufficio.getTemplateMailInvio();
		this.email = ufficio.getEmail();
		this.tel = ufficio.getTel();

		this.reqStranieri = ufficio.getReqStranieri() != null 	&& ufficio.getReqStranieri().booleanValue();
		this.reqBisogni = ufficio.getReqBisogni() != null 		&& ufficio.getReqBisogni().booleanValue();
		this.reqServizi = ufficio.getReqServizi() != null		&& ufficio.getReqServizi().booleanValue();
		this.visInterventi = ufficio.getVisInterventi() != null	&& ufficio.getVisInterventi().booleanValue();
		this.visInviiEnte = ufficio.getVisInviiEnte() != null	&& ufficio.getVisInviiEnte().booleanValue();
		this.visColCsoc = ufficio.getVisColCsoc()!=null 		&& ufficio.getVisColCsoc().booleanValue();
		this.visColInterventi = ufficio.getVisColInterventi()!=null && ufficio.getVisColInterventi().booleanValue();
		
		this.listaPContatto = new ArrayList<SelectItem>();

		
		this.totaleSchede = totaleRegistrate;
		this.schedeInSospeso = schedeInSospeso;
		this.schedeInCarico = inCarico;
		this.schedeEliminate = schedeEliminate;
		this.schedeRicevute = schedeRicevute;
	}

	public Ufficio() {
		this.listaPContatto = new ArrayList<SelectItem>();
	}

	public Ufficio(SsUfficio ufficio) {
		super();
		this.id = ufficio.getId();
		this.nome = ufficio.getNome();
		this.templateMailInvio = ufficio.getTemplateMailInvio();
		this.email = ufficio.getEmail();
		this.tel = ufficio.getTel();

		this.reqStranieri = ufficio.getReqStranieri() != null
				&& ufficio.getReqStranieri().booleanValue();
		this.reqBisogni = ufficio.getReqBisogni() != null
				&& ufficio.getReqBisogni().booleanValue();
		this.reqServizi = ufficio.getReqServizi() != null
				&& ufficio.getReqServizi().booleanValue();
		this.visInterventi = ufficio.getVisInterventi() != null
				&& ufficio.getVisInterventi().booleanValue();
		this.visInviiEnte = ufficio.getVisInviiEnte() != null
				&& ufficio.getVisInviiEnte().booleanValue();
		this.visColCsoc = ufficio.getVisColCsoc()!=null 		&& ufficio.getVisColCsoc().booleanValue();
		this.visColInterventi = ufficio.getVisColInterventi()!=null && ufficio.getVisColInterventi().booleanValue();
		
		this.listaPContatto = new ArrayList<SelectItem>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTotaleSchede() {
		return totaleSchede;
	}

	public void setTotaleSchede(Long totaleSchede) {
		this.totaleSchede = totaleSchede;
	}

	public Long getSchedeInSospeso() {
		return schedeInSospeso;
	}

	public void setSchedeInSospeso(Long schedeInSospeso) {
		this.schedeInSospeso = schedeInSospeso;
	}

	public Long getSchedeInCarico() {
		return schedeInCarico;
	}

	public void setSchedeInCarico(Long schedeInCarico) {
		this.schedeInCarico = schedeInCarico;
	}

	public Long getSchedeEliminate() {
		return schedeEliminate;
	}

	public void setSchedeEliminate(Long schedeEliminate) {
		this.schedeEliminate = schedeEliminate;
	}

	public List<SelectItem> getListaPContatto() {
		return listaPContatto;
	}

	public void setListaPContatto(List<SelectItem> listaPContatto) {
		this.listaPContatto = listaPContatto;
	}

	public void addPuntoContatto(SelectItem si) {
		this.listaPContatto.add(si);
	}

	public boolean isReqStranieri() {
		return reqStranieri;
	}

	public boolean isReqBisogni() {
		return reqBisogni;
	}

	public boolean isReqServizi() {
		return reqServizi;
	}

	public void setReqStranieri(boolean reqStranieri) {
		this.reqStranieri = reqStranieri;
	}

	public void setReqBisogni(boolean reqBisogni) {
		this.reqBisogni = reqBisogni;
	}

	public void setReqServizi(boolean reqServizi) {
		this.reqServizi = reqServizi;
	}

	public boolean isVisInterventi() {
		return visInterventi;
	}

	public void setVisInterventi(boolean visInterventi) {
		this.visInterventi = visInterventi;
	}

	public String getTemplateMailInvio() {
		return templateMailInvio;
	}

	public void setTemplateMailInvio(String templateMailInvio) {
		this.templateMailInvio = templateMailInvio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public boolean isVisInviiEnte() {
		return visInviiEnte;
	}

	public void setVisInviiEnte(boolean visInviiEnte) {
		this.visInviiEnte = visInviiEnte;
	}

	public Long getSchedeRicevute() {
		return schedeRicevute;
	}

	public void setSchedeRicevute(Long schedeRicevute) {
		this.schedeRicevute = schedeRicevute;
	}

	public boolean isVisColCsoc() {
		return visColCsoc;
	}

	public void setVisColCsoc(boolean visColCsoc) {
		this.visColCsoc = visColCsoc;
	}

	public boolean isVisColInterventi() {
		return visColInterventi;
	}

	public void setVisColInterventi(boolean visColInterventi) {
		this.visColInterventi = visColInterventi;
	}
	
}
