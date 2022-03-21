package it.webred.ct.data.model.datitecnicifabbricato;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the DATI_TEC_FABBR_DOCS database table.
 * 
 */
@Entity
@Table(name="DATI_TEC_FABBR_DOC")
public class DocumentiTecniciFabbricato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="ID_DATI")
	private long idDati;

	@Column(name="NOME_DOC")
	private String nomeDoc;
	
	@Column(name="NOME_FILE_DOC")
	private String nomeFileDoc;
	
	@Column(name="TIPO_DATI")
	private String tipoDati;

	@Column(name="TIPO_DOC")
	private String tipoDoc;

    public DocumentiTecniciFabbricato() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdDati() {
		return idDati;
	}

	public void setIdDati(long idDati) {
		this.idDati = idDati;
	}

	public String getNomeDoc() {
		return this.nomeDoc;
	}

	public void setNomeDoc(String nomeDoc) {
		this.nomeDoc = nomeDoc;
	}

	public String getTipoDati() {
		return this.tipoDati;
	}

	public void setTipoDati(String tipoDati) {
		this.tipoDati = tipoDati;
	}

	public String getTipoDoc() {
		return this.tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getNomeFileDoc() {
		return nomeFileDoc;
	}

	public void setNomeFileDoc(String nomeFileDoc) {
		this.nomeFileDoc = nomeFileDoc;
	}

}