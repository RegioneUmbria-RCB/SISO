package it.escsolution.escwebgis.cosapNew.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CosapContrib extends EscObject implements Serializable {

	String id;
	String tipoPersona;
	String descTipoPersona;
	String nome;
	String cogDenom;
	String codiceFiscale;
	String partitaIva;
	String dtNascita;
	String codBelfioreNascita;
	String descComuneNascita;
	String codBelfioreResidenza;
	String descComuneResidenza;
	String capResidenza;
	String codiceVia;
	String sedime;
	String indirizzo;
	String civico;
	String dtIscrArchivio;
	String dtCostitSoggetto;
	
	ArrayList<CosapTassa> tasse;
	
	
	public String getChiave() {
		return id;
	}
	
	public String getCapResidenza() {
		return capResidenza;
	}
	
	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getDescTipoPersona() {
		return descTipoPersona;
	}

	public void setDescTipoPersona(String descTipoPersona) {
		this.descTipoPersona = descTipoPersona;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCogDenom() {
		return cogDenom;
	}

	public void setCogDenom(String cogDenom) {
		this.cogDenom = cogDenom;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getDtNascita() {
		return dtNascita;
	}

	public void setDtNascita(String dtNascita) {
		this.dtNascita = dtNascita;
	}

	public String getCodBelfioreNascita() {
		return codBelfioreNascita;
	}

	public void setCodBelfioreNascita(String codBelfioreNascita) {
		this.codBelfioreNascita = codBelfioreNascita;
	}

	public String getDescComuneNascita() {
		return descComuneNascita;
	}

	public void setDescComuneNascita(String descComuneNascita) {
		this.descComuneNascita = descComuneNascita;
	}

	public String getCodBelfioreResidenza() {
		return codBelfioreResidenza;
	}

	public void setCodBelfioreResidenza(String codBelfioreResidenza) {
		this.codBelfioreResidenza = codBelfioreResidenza;
	}

	public String getDescComuneResidenza() {
		return descComuneResidenza;
	}

	public void setDescComuneResidenza(String descComuneResidenza) {
		this.descComuneResidenza = descComuneResidenza;
	}

	public String getCodiceVia() {
		return codiceVia;
	}

	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}

	public String getSedime() {
		return sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getDtIscrArchivio() {
		return dtIscrArchivio;
	}

	public void setDtIscrArchivio(String dtIscrArchivio) {
		this.dtIscrArchivio = dtIscrArchivio;
	}

	public String getDtCostitSoggetto() {
		return dtCostitSoggetto;
	}

	public void setDtCostitSoggetto(String dtCostitSoggetto) {
		this.dtCostitSoggetto = dtCostitSoggetto;
	}

	public ArrayList<CosapTassa> getTasse() {
		return tasse;
	}

	public void setTasse(ArrayList<CosapTassa> tasse) {
		this.tasse = tasse;
	}
	
	public String getIndirizzoCompleto() {
		String indirizzoCompleto = sedime == null || sedime.equals("-") ? "" : sedime.trim();
		if (indirizzo != null && !indirizzo.equals("") && !indirizzo.equals("-")) {
			if (!indirizzoCompleto.equals("")) {
				indirizzoCompleto += " ";
			}
			indirizzoCompleto += indirizzo;
		}
		if (civico != null && !civico.equals("") && !civico.equals("-")) {
			if (!indirizzoCompleto.equals("")) {
				indirizzoCompleto += ", ";
			}
			indirizzoCompleto += civico;
		}
		return indirizzoCompleto;
	}
	
}
