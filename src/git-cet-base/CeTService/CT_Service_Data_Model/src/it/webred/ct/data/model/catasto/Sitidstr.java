package it.webred.ct.data.model.catasto;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITIDSTR database table.
 * 
 */

@Entity
@IndiceEntity(sorgente="4")
public class Sitidstr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="COD_NAZIONALE")
	private String codNazionale;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;
    
	private String nome;

	@Column(name="NOME_ALT")
	private String nomeAlt;

	private String note;

	private String numero;

	@Id
	@Column(name="PKID_STRA")
	@IndiceKey(pos="1")
	private BigDecimal pkidStra;

	private String prefisso;

	@Column(name="PREFISSO_ALT")
	private String prefissoAlt;

	private String tipo;

	private String utente;

    public Sitidstr() {
    }

	public String getCodNazionale() {
		return this.codNazionale;
	}

	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}

	public Date getDataFineVal() {
		return this.dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeAlt() {
		return this.nomeAlt;
	}

	public void setNomeAlt(String nomeAlt) {
		this.nomeAlt = nomeAlt;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getPkidStra() {
		return this.pkidStra;
	}

	public void setPkidStra(BigDecimal pkidStra) {
		this.pkidStra = pkidStra;
	}

	public String getPrefisso() {
		return this.prefisso;
	}

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	public String getPrefissoAlt() {
		return this.prefissoAlt;
	}

	public void setPrefissoAlt(String prefissoAlt) {
		this.prefissoAlt = prefissoAlt;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUtente() {
		return this.utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

}