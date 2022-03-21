package it.webred.ct.data.model.redditi;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RED_DESCRIZIONE database table.
 * 
 */
@Entity
@Table(name="RED_DESCRIZIONE")
public class RedDescrizione implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RedDescrizionePK id;
	
	@Column(name="ANNO_IMPOSTA")
	private String annoImposta;

	@Column(name="DIC_CORRETTIVA")
	private String dicCorrettiva;

	@Column(name="DIC_INTEGRATIVA")
	private String dicIntegrativa;

	@Column(name="FLAG_VALUTA")
	private String flagValuta;

	@Column(name="STATO_DICHIARAZIONE")
	private String statoDichiarazione;

	public RedDescrizione() {
    }

	public String getAnnoImposta() {
		return this.annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public String getDicCorrettiva() {
		return this.dicCorrettiva;
	}

	public void setDicCorrettiva(String dicCorrettiva) {
		this.dicCorrettiva = dicCorrettiva;
	}

	public String getDicIntegrativa() {
		return this.dicIntegrativa;
	}

	public void setDicIntegrativa(String dicIntegrativa) {
		this.dicIntegrativa = dicIntegrativa;
	}

	public String getFlagValuta() {
		return this.flagValuta;
	}

	public void setFlagValuta(String flagValuta) {
		this.flagValuta = flagValuta;
	}

	public String getStatoDichiarazione() {
		return this.statoDichiarazione;
	}

	public void setStatoDichiarazione(String statoDichiarazione) {
		this.statoDichiarazione = statoDichiarazione;
	}

	public RedDescrizionePK getId() {
		return id;
	}

	public void setId(RedDescrizionePK id) {
		this.id = id;
	}

}