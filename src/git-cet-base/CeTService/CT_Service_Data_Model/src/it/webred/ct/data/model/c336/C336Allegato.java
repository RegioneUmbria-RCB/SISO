package it.webred.ct.data.model.c336;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the S_C336_ALLEGATO database table.
 * 
 */
@Entity
@Table(name="S_C336_ALLEGATO")
public class C336Allegato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ALLEGATO")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="c336AllegatoSeq")
	@SequenceGenerator(name="c336AllegatoSeq", sequenceName="S_C336_ALLEGATO_SEQ")
	private Long idAllegato;

	@Column(name="COD_TIPO_DOC")
	private String codTipoDoc;

	private String descrizione;

	@Column(name="EXT_FILE")
	private String extFile;

	@Column(name="ID_PRATICA")
	private Long idPratica;

	@Column(name="MIME_TYPE_FILE")
	private String mimeTypeFile;

	@Column(name="NOME_FILE")
	private String nomeFile;

	@Transient
	private String desTipDoc;

	@Transient
	private String fileImg;
	
	public C336Allegato() {
    }
	
	public Long getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}

	public Long getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}
	
	public String getCodTipoDoc() {
		return this.codTipoDoc;
	}

	public void setCodTipoDoc(String codTipoDoc) {
		this.codTipoDoc = codTipoDoc;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getExtFile() {
		return this.extFile;
	}

	public void setExtFile(String extFile) {
		this.extFile = extFile;
	}

	public String getMimeTypeFile() {
		return this.mimeTypeFile;
	}

	public void setMimeTypeFile(String mimeTypeFile) {
		this.mimeTypeFile = mimeTypeFile;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getDesTipDoc() {
		return desTipDoc;
	}

	public void setDesTipDoc(String desTipDoc) {
		this.desTipDoc = desTipDoc;
	}

	public String getFileImg() {
		return fileImg;
	}

	public void setFileImg(String fileImg) {
		this.fileImg = fileImg;
	}

}