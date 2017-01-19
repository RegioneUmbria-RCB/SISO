package it.webred.cs.csa.web.manbean.report.dto.foglioAmm;

import it.webred.cs.csa.web.manbean.report.dto.ReportPdfDTO;

public class FoglioAmmPdfDTO extends ReportPdfDTO {

	//soggetto
	private String denominazione = EMPTY_VALUE;
	private String dataNascita = EMPTY_VALUE;
	private String cf = EMPTY_VALUE;
	private String comuneNascita = EMPTY_VALUE;
	private String cittadinanza = EMPTY_VALUE;
	private String telefono = EMPTY_VALUE;
	
	//intervento
	private String tipoIntervento = EMPTY_VALUE;
	private String modalita = EMPTY_VALUE;
	private String unaTantum = EMPTY_VALUE;
	private String catSociale = EMPTY_VALUE;
	private String settRichiedente = EMPTY_VALUE;
	private String note = EMPTY_VALUE;
	private String dataInizio = EMPTY_VALUE;
	private String dataFine = EMPTY_VALUE;
	
	//foglio amm
	private String tipoFoglio = EMPTY_VALUE;
	private String dataAmministrativa = EMPTY_VALUE;
	private String relazione = EMPTY_VALUE;
	private String dettOperazione = EMPTY_VALUE;
	private String descrDettOperazione = EMPTY_VALUE;
	private String attDal = EMPTY_VALUE;
	private String attAl = EMPTY_VALUE;
	private String respinto = EMPTY_VALUE;
	private String motivazione = EMPTY_VALUE;
	private String rifDenominazione = EMPTY_VALUE;
	private String rifIndirizzo = EMPTY_VALUE;
	private String rifLuogo = EMPTY_VALUE;
	private String rifTelefono = EMPTY_VALUE;
	
	public String getTipoIntervento() {
		return tipoIntervento;
	}
	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = format(tipoIntervento);
	}
	public String getModalita() {
		return modalita;
	}
	public void setModalita(String modalita) {
		this.modalita = format(modalita);
	}
	public String getUnaTantum() {
		return unaTantum;
	}
	public void setUnaTantum(String unaTantum) {
		this.unaTantum = format(unaTantum);
	}
	public String getCatSociale() {
		return catSociale;
	}
	public String getComuneNascita() {
		return comuneNascita;
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setCatSociale(String catSociale) {
		this.catSociale = format(catSociale);
	}
	public String getSettRichiedente() {
		return settRichiedente;
	}
	public void setSettRichiedente(String settRichiedente) {
		this.settRichiedente = format(settRichiedente);
	}
	
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = format(dataInizio);
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = format(dataFine);
	}
	public String getTipoFoglio() {
		return tipoFoglio;
	}
	public void setTipoFoglio(String tipoFoglio) {
		this.tipoFoglio = format(tipoFoglio);
	}
	public String getDataAmministrativa() {
		return dataAmministrativa;
	}
	public void setDataAmministrativa(String dataAmm) {
		this.dataAmministrativa = format(dataAmm);
	}
	public String getRelazione() {
		return relazione;
	}
	public void setRelazione(String relazione) {
		this.relazione = format(relazione);
	}
	public String getAttDal() {
		return attDal;
	}
	public void setAttDal(String attDal) {
		this.attDal = format(attDal);
	}
	public String getAttAl() {
		return attAl;
	}
	public void setAttAl(String attAl) {
		this.attAl = format(attAl);
	}
	public String getRespinto() {
		return respinto;
	}
	public void setRespinto(String respinto) {
		this.respinto = format(respinto);
	}
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = format(motivazione);
	}
	public String getRifDenominazione() {
		return rifDenominazione;
	}
	public void setRifDenominazione(String rifDenominazione) {
		this.rifDenominazione = format(rifDenominazione);
	}
	public String getRifIndirizzo() {
		return rifIndirizzo;
	}
	public void setRifIndirizzo(String rifIndirizzo) {
		this.rifIndirizzo = format(rifIndirizzo);
	}
	public String getRifLuogo() {
		return rifLuogo;
	}
	public void setRifLuogo(String rifLuogo) {
		this.rifLuogo = format(rifLuogo);
	}
	public String getRifTelefono() {
		return rifTelefono;
	}
	public void setRifTelefono(String rifTelefono) {
		this.rifTelefono = format(rifTelefono);
	}
	public String getDettOperazione() {
		return dettOperazione;
	}
	public void setDettOperazione(String dettOperazione) {
		this.dettOperazione = format(dettOperazione);
	}
	public String getDescrDettOperazione() {
		return descrDettOperazione;
	}
	public void setDescrDettOperazione(String descrDettOperazione) {
		this.descrDettOperazione = format(descrDettOperazione);
	}
	
	public String getDenominazione() {
		return denominazione;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public String getCf() {
		return cf;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
