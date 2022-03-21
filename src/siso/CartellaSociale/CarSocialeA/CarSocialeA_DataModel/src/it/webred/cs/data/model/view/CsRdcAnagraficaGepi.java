package it.webred.cs.data.model.view;

import it.webred.cs.data.model.CsASoggettoLAZY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CS_RDC_ANAGRAFICA_GEPI")
public class CsRdcAnagraficaGepi implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String cf;
	
	private String cognome;
	
	private String nome;

	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	@Column(name="NAS_COMUNE_COD")
	private String nasComuneCod;
	
	@Column(name="RES_COMUNE_COD")
	private String resComuneCod;
	
	@Column(name="RES_INDIRIZZO")
	private String resIndirizzo;
	
	private String nazionalita;
	
	private String sesso;
	
	private String telefono;
	
	private String cellulare;
	
	private String email;
	
	private Boolean richiedente;
	
	@Column(name="DATA_PRESENTAZIONE_DOMANDA")
	private Date dataPresentazioneDomanda;
	
	@Column(name="ID_DOMANDA")
	private String idDomanda;
	
	//uni-directional one-to-one association to CsASoggetto
	@ManyToOne
	@JoinColumn(name="SOGGETTO_ID", insertable=false, updatable=false)
	private CsASoggettoLAZY csASoggetto;
	
	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

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

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getNasComuneCod() {
		return nasComuneCod;
	}

	public void setNasComuneCod(String nasComuneCod) {
		this.nasComuneCod = nasComuneCod;
	}

	public String getResComuneCod() {
		return resComuneCod;
	}

	public void setResComuneCod(String resComuneCod) {
		this.resComuneCod = resComuneCod;
	}

	public String getResIndirizzo() {
		return resIndirizzo;
	}

	public void setResIndirizzo(String resIndirizzo) {
		this.resIndirizzo = resIndirizzo;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public Boolean getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(Boolean richiedente) {
		this.richiedente = richiedente;
	}

	public Date getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}

	public void setDataPresentazioneDomanda(Date dataPresentazioneDomanda) {
		this.dataPresentazioneDomanda = dataPresentazioneDomanda;
	}

	public String getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
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
	
}