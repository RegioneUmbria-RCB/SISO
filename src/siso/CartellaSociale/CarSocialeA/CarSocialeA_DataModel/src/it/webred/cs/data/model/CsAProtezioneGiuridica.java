package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name="CS_A_PROTEZIONE_GIURIDICA")
public class CsAProtezioneGiuridica implements Serializable {
	
	private static final long serialVersionUID = -6508827039482051925L;

	@Id
	@SequenceGenerator(name="CS_A_PROTEZIONE_GIURIDICA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_PROTEZIONE_GIURIDICA_ID_GENERATOR")
	private Long id;

	@ManyToOne
	@JoinColumn(name="COMPONENTE_ID")
	private CsAComponente componente;
	
	@Column(name="CITTA")
	private String citta;

	@Column(name="DENOMINAZIONE")
	private String denominazione;

	@Column(name="INDIRIZZO")
	private String indirizzo;

	@Column(name="TELEFONO")
	private String telefono;
	
	@Column(name="NUM_DECRETO")
	private String numDecreto;
	
	@Column(name="DATA_DECRETO")
	private Date dataDecreto;
	
	private String tipo;
	
	@ManyToOne 
	@JoinColumn(name="DATI_SOCIALI_ID")
	private CsADatiSociali csADatiSociali; 
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsAComponente getComponente() {
		return componente;
	}

	public void setComponente(CsAComponente componente) {
		this.componente = componente;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNumDecreto() {
		return numDecreto;
	}

	public void setNumDecreto(String numDecreto) {
		this.numDecreto = numDecreto;
	}

	public Date getDataDecreto() {
		return dataDecreto;
	}

	public void setDataDecreto(Date dataDecreto) {
		this.dataDecreto = dataDecreto;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public CsADatiSociali getCsADatiSociali() {
		return csADatiSociali;
	}

	public void setCsADatiSociali(CsADatiSociali csADatiSociali) {
		this.csADatiSociali = csADatiSociali;
	}

	public boolean isEmpty() {
		boolean empty = StringUtils.isBlank(citta) && 
						StringUtils.isBlank(denominazione) && 
						StringUtils.isBlank(telefono) && 
						StringUtils.isBlank(indirizzo) &&
						StringUtils.isBlank(numDecreto) &&
						componente == null && dataDecreto == null;
		return empty;
	}
}