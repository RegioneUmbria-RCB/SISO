package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCFA_ANNOTAZIONI database table.
 * 
 */
@Entity
@Table(name="DOCFA_ANNOTAZIONI")
public class DocfaAnnotazioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfaAnnotazioniPK id;
	
	private String annotazioni;

	private String causale;

	@Column(name="DATA_ANNOTA")
	private String dataAnnota;

	@Column(name="DATA_REGISTRAZIONE")
	private String dataRegistrazione;

	@Column(name="ESTREMI_ATTO_02")
	private String estremiAtto02;

	@Column(name="ESTREMI_ATTO_03")
	private String estremiAtto03;

	@Column(name="PRESENZA_ESTREMI")
	private BigDecimal presenzaEstremi;

	

	@Column(name="PROG_UIU_A")
	private String progUiuA;

	@Column(name="PROG_UIU_DA")
	private String progUiuDa;
	
	private String provincia;

	private String repertorio;

	private String rogante;

	private String sede;

	private String volume;

    public DocfaAnnotazioni() {
    }

	public String getAnnotazioni() {
		return this.annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public String getCausale() {
		return this.causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getDataAnnota() {
		return this.dataAnnota;
	}

	public void setDataAnnota(String dataAnnota) {
		this.dataAnnota = dataAnnota;
	}

	public String getDataRegistrazione() {
		return this.dataRegistrazione;
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getEstremiAtto02() {
		return this.estremiAtto02;
	}

	public void setEstremiAtto02(String estremiAtto02) {
		this.estremiAtto02 = estremiAtto02;
	}

	public String getEstremiAtto03() {
		return this.estremiAtto03;
	}

	public void setEstremiAtto03(String estremiAtto03) {
		this.estremiAtto03 = estremiAtto03;
	}

	public BigDecimal getPresenzaEstremi() {
		return this.presenzaEstremi;
	}

	public void setPresenzaEstremi(BigDecimal presenzaEstremi) {
		this.presenzaEstremi = presenzaEstremi;
	}

	public String getProgUiuA() {
		return this.progUiuA;
	}

	public void setProgUiuA(String progUiuA) {
		this.progUiuA = progUiuA;
	}

	public String getProgUiuDa() {
		return this.progUiuDa;
	}

	public void setProgUiuDa(String progUiuDa) {
		this.progUiuDa = progUiuDa;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getRepertorio() {
		return this.repertorio;
	}

	public void setRepertorio(String repertorio) {
		this.repertorio = repertorio;
	}

	public String getRogante() {
		return this.rogante;
	}

	public void setRogante(String rogante) {
		this.rogante = rogante;
	}

	public String getSede() {
		return this.sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getVolume() {
		return this.volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public void setId(DocfaAnnotazioniPK id) {
		this.id = id;
	}

	public DocfaAnnotazioniPK getId() {
		return id;
	}

}