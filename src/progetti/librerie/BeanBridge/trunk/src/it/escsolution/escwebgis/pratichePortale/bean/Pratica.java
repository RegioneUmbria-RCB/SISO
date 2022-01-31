package it.escsolution.escwebgis.pratichePortale.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Pratica extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String idRichiedente;
	String idFruitore;
	String utenteDestinatario;
	String dataRichiesta;
	String nomeCreatore;
	String cognomeCreatore;
	String codFisCreatore;
	String dataCreazione;
	String utenteModificaId;
	String dataModifica;
	String tipo;
	String sottotipo;
	String nomeStato;
	String nomeRichiedente;
	String cognomeRichiedente;
	String codFisRichiedente;
	String nomeFruitore;
	String cognomeFruitore;
	String codFisFruitore;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUtenteDestinatario() {
		return utenteDestinatario;
	}
	public void setUtenteDestinatario(String utenteDestinatario) {
		this.utenteDestinatario = utenteDestinatario;
	}
	public String getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(String dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public String getNomeCreatore() {
		return nomeCreatore;
	}
	public void setNomeCreatore(String nomeCreatore) {
		this.nomeCreatore = nomeCreatore;
	}
	public String getCognomeCreatore() {
		return cognomeCreatore;
	}
	public void setCognomeCreatore(String cognomeCreatore) {
		this.cognomeCreatore = cognomeCreatore;
	}
	public String getCodFisCreatore() {
		return codFisCreatore;
	}
	public void setCodFisCreatore(String codFisCreatore) {
		this.codFisCreatore = codFisCreatore;
	}
	public String getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public String getUtenteModificaId() {
		return utenteModificaId;
	}
	public void setUtenteModificaId(String utenteModificaId) {
		this.utenteModificaId = utenteModificaId;
	}
	public String getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(String dataModifica) {
		this.dataModifica = dataModifica;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSottotipo() {
		return sottotipo;
	}
	public void setSottotipo(String sottotipo) {
		this.sottotipo = sottotipo;
	}
	public String getNomeStato() {
		return nomeStato;
	}
	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}
	public String getNomeRichiedente() {
		return nomeRichiedente;
	}
	public void setNomeRichiedente(String nomeRichiedente) {
		this.nomeRichiedente = nomeRichiedente;
	}
	public String getCognomeRichiedente() {
		return cognomeRichiedente;
	}
	public void setCognomeRichiedente(String cognomeRichiedente) {
		this.cognomeRichiedente = cognomeRichiedente;
	}
	public String getCodFisRichiedente() {
		return codFisRichiedente;
	}
	public void setCodFisRichiedente(String codFisRichiedente) {
		this.codFisRichiedente = codFisRichiedente;
	}
	public String getNomeFruitore() {
		return nomeFruitore;
	}
	public void setNomeFruitore(String nomeFruitore) {
		this.nomeFruitore = nomeFruitore;
	}
	public String getCognomeFruitore() {
		return cognomeFruitore;
	}
	public void setCognomeFruitore(String cognomeFruitore) {
		this.cognomeFruitore = cognomeFruitore;
	}
	public String getCodFisFruitore() {
		return codFisFruitore;
	}
	public void setCodFisFruitore(String codFisFruitore) {
		this.codFisFruitore = codFisFruitore;
	}
	public String getIdRichiedente() {
		return idRichiedente;
	}
	public void setIdRichiedente(String idRichiedente) {
		this.idRichiedente = idRichiedente;
	}
	public String getIdFruitore() {
		return idFruitore;
	}
	public void setIdFruitore(String idFruitore) {
		this.idFruitore = idFruitore;
	}
	
}
