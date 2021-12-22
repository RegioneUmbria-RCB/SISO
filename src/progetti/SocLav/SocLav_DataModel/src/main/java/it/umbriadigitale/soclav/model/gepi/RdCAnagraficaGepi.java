package it.umbriadigitale.soclav.model.gepi;




import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RDC_ANAGRAFICA_GEPI")
public class RdCAnagraficaGepi implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	private String cf;
	 
	
	private String sesso;
	
	@Column(name="ID_DOMANDA")
	private String idDomanda;
	
	@Column(name="DATA_PRESENTAZIONE_DOMANDA")
	private Date dataPresDomanda;
	
	@Column(name="AMBITO_APPARTENENZA")
	private String ambitoAppartenenza;
	
	
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
 
	
	@Column(name="NOME_COMPLETO")
	private String nomeCompleto;
	
 
	
	@Column(name = "NAS_COMUNE_COD")
	private String nasComuneCod;
	
	@Column(name = "NAS_COMUNE_DES")
	private String nasComuneDes;
	
	@Column(name = "RES_COMUNE_COD")
	private String resComuneCod;
	
	@Column(name = "RES_INDIRIZZO")
	private String resIndirizzo;
	
 
	
	@Column(name = "NAZIONALITA")
	private String nazionalita;
	
	@Column(name = "TELEFONO")
	private String telefono;
	
	@Column(name = "CELLULARE")
	private String cellulare;
	
	
	@Column(name = "EMAIL")
	private String email;

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}
 

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
 

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
 

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}

	public Date getDataPresDomanda() {
		return dataPresDomanda;
	}

	public void setDataPresDomanda(Date dataPresDomanda) {
		this.dataPresDomanda = dataPresDomanda;
	}

	public String getAmbitoAppartenenza() {
		return ambitoAppartenenza;
	}

	public void setAmbitoAppartenenza(String ambitoAppartenenza) {
		this.ambitoAppartenenza = ambitoAppartenenza;
	}

	public String getNasComuneCod() {
		return nasComuneCod;
	}

	public void setNasComuneCod(String nasComuneCod) {
		this.nasComuneCod = nasComuneCod;
	}

	public String getNasComuneDes() {
		return nasComuneDes;
	}

	public void setNasComuneDes(String nasComuneDes) {
		this.nasComuneDes = nasComuneDes;
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

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	

	
}
