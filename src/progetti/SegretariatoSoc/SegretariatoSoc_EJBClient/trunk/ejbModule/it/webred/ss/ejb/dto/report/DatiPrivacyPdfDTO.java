package it.webred.ss.ejb.dto.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatiPrivacyPdfDTO implements Serializable{

	public final String EMPTY = "";
	
	private boolean renderAccesso=true;
	private String accessoData=EMPTY;
	private String accessoOperatore=EMPTY;
	private String accessoInterlocutore=EMPTY;
	private String accessoMotivo=EMPTY;
	private String accessoComune = "";
	
	//segnalante
	private boolean renderSegnalante=true;
 	private String segnalanteCognome=EMPTY;
	private String segnalanteNome=EMPTY;
	private String segnalanteTel=EMPTY;
	private String segnalanteCel=EMPTY;
	private String segnalanteSesso=EMPTY;
	private String segnalanteLuogo_nascita=EMPTY;
	private String segnalanteData_nascita=EMPTY;
	private String segnalanteStatoCivile=EMPTY;
	private String segnalanteEmail=EMPTY;
	private String segnalanteResidenza=EMPTY;
	private String segnalanteRelazione=EMPTY;
	
	private boolean anonimo;
	private String nome=EMPTY;
	private String cognome=EMPTY;
	private String data_nascita=EMPTY;
	private String luogo_nascita=EMPTY;
	private String sesso=EMPTY;
	private String tel=EMPTY;
	private String cel=EMPTY;
	private String email=EMPTY;
	private String residenza=EMPTY;
	private String medico=EMPTY;
	private String cf=EMPTY;

	private boolean renderRiferimenti=true;
	private List<RiferimentoPdfDTO> lstRiferimenti;
	
	public DatiPrivacyPdfDTO() {
		lstRiferimenti = new ArrayList<RiferimentoPdfDTO>();
	}
	
	public String getAccessoData() {
		return accessoData;
	}
	public void setAccessoData(String accessoData) {
		this.accessoData = accessoData;
	}
	public String getAccessoOperatore() {
		return accessoOperatore;
	}
	public void setAccessoOperatore(String accessoOperatore) {
		this.accessoOperatore = accessoOperatore;
	}
	public String getAccessoInterlocutore() {
		return accessoInterlocutore;
	}
	public void setAccessoInterlocutore(String accessoInterlocutore) {
		this.accessoInterlocutore = accessoInterlocutore;
	}
	public String getAccessoMotivo() {
		return accessoMotivo;
	}
	public void setAccessoMotivo(String accessoMotivo) {
		this.accessoMotivo = accessoMotivo;
	}
	public String getSegnalanteCognome() {
		return segnalanteCognome;
	}
	public void setSegnalanteCognome(String segnalanteCognome) {
		this.segnalanteCognome = segnalanteCognome;
	}
	public String getSegnalanteNome() {
		return segnalanteNome;
	}
	public void setSegnalanteNome(String segnalanteNome) {
		this.segnalanteNome = segnalanteNome;
	}
	public String getSegnalanteTel() {
		return segnalanteTel;
	}
	public void setSegnalanteTel(String segnalanteTel) {
		this.segnalanteTel = segnalanteTel;
	}
	public String getSegnalanteCel() {
		return segnalanteCel;
	}
	public void setSegnalanteCel(String segnalanteCel) {
		this.segnalanteCel = segnalanteCel;
	}
	public String getSegnalanteSesso() {
		return segnalanteSesso;
	}
	public void setSegnalanteSesso(String segnalanteSesso) {
		this.segnalanteSesso = segnalanteSesso;
	}
	public String getSegnalanteLuogo_nascita() {
		return segnalanteLuogo_nascita;
	}
	public void setSegnalanteLuogo_nascita(String segnalanteLuogo_nascita) {
		this.segnalanteLuogo_nascita = segnalanteLuogo_nascita;
	}
	public String getSegnalanteData_nascita() {
		return segnalanteData_nascita;
	}
	public void setSegnalanteData_nascita(String segnalanteData_nascita) {
		this.segnalanteData_nascita = segnalanteData_nascita;
	}
	public String getSegnalanteEmail() {
		return segnalanteEmail;
	}
	public void setSegnalanteEmail(String segnalanteEmail) {
		this.segnalanteEmail = segnalanteEmail;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getData_nascita() {
		return data_nascita;
	}
	public void setData_nascita(String data_nascita) {
		this.data_nascita = data_nascita;
	}
	public String getLuogo_nascita() {
		return luogo_nascita;
	}
	public void setLuogo_nascita(String luogo_nascita) {
		this.luogo_nascita = luogo_nascita;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCel() {
		return cel;
	}
	public void setCel(String cel) {
		this.cel = cel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSegnalanteStatoCivile() {
		return segnalanteStatoCivile;
	}
	public void setSegnalanteStatoCivile(String segnalanteStatoCivile) {
		this.segnalanteStatoCivile = segnalanteStatoCivile;
	}
	public String getSegnalanteResidenza() {
		return segnalanteResidenza;
	}
	public void setSegnalanteResidenza(String segnalanteResidenza) {
		this.segnalanteResidenza = segnalanteResidenza;
	}
	public String getSegnalanteRelazione() {
		return segnalanteRelazione;
	}
	public void setSegnalanteRelazione(String segnalanteRelazione) {
		this.segnalanteRelazione = segnalanteRelazione;
	}
	public boolean isAnonimo() {
		return anonimo;
	}
	public void setAnonimo(boolean anonimo) {
		this.anonimo = anonimo;
	}
	public String getResidenza() {
		return residenza;
	}
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}
	public String getMedico() {
		return medico;
	}
	public void setMedico(String medico) {
		this.medico = medico;
	}
	public List<RiferimentoPdfDTO> getLstRiferimenti() {
		return lstRiferimenti;
	}
	public void setLstRiferimenti(List<RiferimentoPdfDTO> lstRiferimenti) {
		this.lstRiferimenti = lstRiferimenti;
	}
	public String getAccessoComune() {
		return accessoComune;
	}
	public void setAccessoComune(String accessoComune) {
		this.accessoComune = accessoComune;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}

	public boolean isRenderAccesso() {
		return renderAccesso;
	}

	public void setRenderAccesso(boolean renderAccesso) {
		this.renderAccesso = renderAccesso;
	}

	public boolean isRenderSegnalante() {
		return renderSegnalante;
	}

	public void setRenderSegnalante(boolean renderSegnalante) {
		this.renderSegnalante = renderSegnalante;
	}

	public boolean isRenderRiferimenti() {
		return renderRiferimenti;
	}

	public void setRenderRiferimenti(boolean renderRiferimenti) {
		this.renderRiferimenti = renderRiferimenti;
	}
	
}
