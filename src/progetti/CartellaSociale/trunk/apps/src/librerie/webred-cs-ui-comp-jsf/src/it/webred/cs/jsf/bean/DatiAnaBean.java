package it.webred.cs.jsf.bean;

import it.webred.cs.csa.utils.StringUtils;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.jsf.manbean.common.CommonDatiAnaBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.jsf.bean.SessoBean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;

@ManagedBean
@NoneScoped
public class DatiAnaBean implements Serializable, CommonDatiAnaBean {	

	private static final long serialVersionUID = 1L;
	
	private String cognome;
	private String nome;
	private Date dataNascita;
	private SessoBean datiSesso = new SessoBean();;
	private String codiceFiscale;
	private String telefono;
	private String cellulare;
	private String email;
	private String cittadinanza;
	private Long cittadinanzaAcq;
	private String cittadinanza2;
	private Date dataAperturaFascFam;
	
	private String titTelefono;
	private String titCellulare;
	private String titEmail;
	
	private String idOrigWs;
	
	public String getCognome() {
		return StringUtils.capitalize(cognome);
	}
	
	public void setCognome(String cognome) {
		this.cognome = StringUtils.capitalize(cognome);
	}

	public String getNome() {
		return StringUtils.capitalize(nome);
	}

	public void setNome(String nome) {
		this.nome = StringUtils.capitalize(nome);
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}	

	public String getCodiceFiscale() {
		return StringUtils.uppercase(codiceFiscale);
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = StringUtils.uppercase(codiceFiscale);
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

	public Long getCittadinanzaAcq() {
		return cittadinanzaAcq;
	}

	public void setCittadinanzaAcq(Long cittadinanzaAcq) {
		this.cittadinanzaAcq = cittadinanzaAcq;
	}

	public String getCittadinanza2() {
		return cittadinanza2;
	}

	public void setCittadinanza2(String cittadinanza2) {
		this.cittadinanza2 = cittadinanza2;
	}

	public SessoBean getDatiSesso() {
		return datiSesso;
	}

	public void setDatiSesso(SessoBean datiSesso) {
		this.datiSesso = datiSesso;
	}

	public Date getDataAperturaFascFam() {
		return dataAperturaFascFam;
	}

	public void setDataAperturaFascFam(Date dataAperturaFascFam) {
		this.dataAperturaFascFam = dataAperturaFascFam;
	}

	public String getTitTelefono() {
		return titTelefono;
	}

	public String getTitCellulare() {
		return titCellulare;
	}

	public String getTitEmail() {
		return titEmail;
	}

	public void setTitTelefono(String titTelefono) {
		this.titTelefono = titTelefono;
	}

	public void setTitCellulare(String titCellulare) {
		this.titCellulare = titCellulare;
	}

	public void setTitEmail(String titEmail) {
		this.titEmail = titEmail;
	}

	public String getIdOrigWs() {
		return idOrigWs;
	}

	public void setIdOrigWs(String idOrigWs) {
		this.idOrigWs = idOrigWs;
	}
	public boolean isAnonimo() {
		return  "ANONIMO".equalsIgnoreCase(this.codiceFiscale) ||
				"ANONIMO".equalsIgnoreCase(this.cognome) ||
				"ANONIMO".equalsIgnoreCase(this.nome);
	}

}
