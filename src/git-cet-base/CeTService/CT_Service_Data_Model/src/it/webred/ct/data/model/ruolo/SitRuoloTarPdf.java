package it.webred.ct.data.model.ruolo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_RUOLO_TAR_PDF database table.
 * 
 */
@Entity
@Table(name="SIT_RUOLO_TAR_PDF")
public class SitRuoloTarPdf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private BigDecimal anno;

	private String codfisc;

	private BigDecimal cu;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ELAB")
	private Date dtElab;

	private BigDecimal mese;

	@Column(name="NOME_FILE")
	private String nomeFile;

	public SitRuoloTarPdf() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public String getCodfisc() {
		return this.codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public BigDecimal getCu() {
		return this.cu;
	}

	public void setCu(BigDecimal cu) {
		this.cu = cu;
	}

	public Date getDtElab() {
		return this.dtElab;
	}

	public void setDtElab(Date dtElab) {
		this.dtElab = dtElab;
	}

	public BigDecimal getMese() {
		return this.mese;
	}

	public void setMese(BigDecimal mese) {
		this.mese = mese;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

}