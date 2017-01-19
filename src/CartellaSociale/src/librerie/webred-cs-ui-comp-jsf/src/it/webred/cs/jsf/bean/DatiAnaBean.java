package it.webred.cs.jsf.bean;

import it.webred.cs.csa.utils.StringUtils;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.jsf.bean.SessoBean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;

@ManagedBean
@NoneScoped
public class DatiAnaBean implements Serializable {	

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


}
