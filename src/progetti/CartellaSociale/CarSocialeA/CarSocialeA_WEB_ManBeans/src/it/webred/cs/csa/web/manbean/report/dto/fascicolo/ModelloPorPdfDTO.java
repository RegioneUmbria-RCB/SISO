package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

public class ModelloPorPdfDTO extends ReportPdfDTO {
	
	private String cognome = "";
	private String nome = "";
	private String sesso = "";
	private	String luogoNascita = "";
	private String dataNascita = "";
	private String residenza = "";
	private String resid_cap = "";
	private String resid_sigla_prov = "";
	private String resid_via = "";
	private String domicilio = "";
	private String dom_cap = "";
	private String dom_sigla_prov = "";
	private String dom_via = "";
	private String codFiscale = "";
	private String cittadinanza = "";
	private String telefono = "";
	private String cellulare = "";
	private String email = "";
	
	private String titolo_studio = "";
	private String condizione_lavoro = "";
	private String durata_ric_lavoro = "";
	
	private String cond_vulnerabilita = "";
	
	private String nome_prog = "";
	private String attuatore_prog = ""; // lo richiedo con una dialog non viene salvato nel db!
	private String cod_prog = "";
	private String attivita = "";
	private String dataSottoscrizione = "";
	
	private String imagePath = "";
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getResidenza() {
		return residenza;
	}
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}
	public String getResid_cap() {
		return resid_cap;
	}
	public void setResid_cap(String resid_cap) {
		this.resid_cap = resid_cap;
	}
	public String getResid_sigla_prov() {
		return resid_sigla_prov;
	}
	public void setResid_sigla_prov(String resid_sigla_prov) {
		this.resid_sigla_prov = resid_sigla_prov;
	}
	public String getResid_via() {
		return resid_via;
	}
	public void setResid_via(String resid_via) {
		this.resid_via = resid_via;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getDom_cap() {
		return dom_cap;
	}
	public void setDom_cap(String dom_cap) {
		this.dom_cap = dom_cap;
	}
	public String getDom_sigla_prov() {
		return dom_sigla_prov;
	}
	public void setDom_sigla_prov(String dom_sigla_prov) {
		this.dom_sigla_prov = dom_sigla_prov;
	}
	public String getDom_via() {
		return dom_via;
	}
	public void setDom_via(String dom_via) {
		this.dom_via = dom_via;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitolo_studio() {
		return titolo_studio;
	}
	public void setTitolo_studio(String titolo_studio) {
		this.titolo_studio = titolo_studio;
	}
	public String getCondizione_lavoro() {
		return condizione_lavoro;
	}
	public void setCondizione_lavoro(String condizione_lavoro) {
		this.condizione_lavoro = condizione_lavoro;
	}
	public String getDurata_ric_lavoro() {
		return durata_ric_lavoro;
	}
	public void setDurata_ric_lavoro(String durata_ric_lavoro) {
		this.durata_ric_lavoro = durata_ric_lavoro;
	}
	public String getCond_vulnerabilita() {
		return cond_vulnerabilita;
	}
	public void setCond_vulnerabilita(String cond_vulnerabilita) {
		this.cond_vulnerabilita = cond_vulnerabilita;
	}
	public String getNome_prog() {
		return nome_prog;
	}
	public void setNome_prog(String nome_prog) {
		this.nome_prog = nome_prog;
	}
	public String getAttuatore_prog() {
		return attuatore_prog;
	}
	public void setAttuatore_prog(String attuatore_prog) {
		this.attuatore_prog = attuatore_prog;
	}
	public String getCod_prog() {
		return cod_prog;
	}
	public void setCod_prog(String cod_prog) {
		this.cod_prog = cod_prog;
	}
	public String getAttivita() {
		return attivita;
	}
	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDataSottoscrizione() {
		return dataSottoscrizione;
	}
	public void setDataSottoscrizione(String dataSottoscrizione) {
		this.dataSottoscrizione = dataSottoscrizione;
	}
	
}