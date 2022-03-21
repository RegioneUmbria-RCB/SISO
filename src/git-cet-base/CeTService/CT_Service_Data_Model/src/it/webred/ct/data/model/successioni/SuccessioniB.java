package it.webred.ct.data.model.successioni;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SUCCESSIONI_B")
public class SuccessioniB implements Serializable{

	private static final long serialVersionUID = -8830149951349934348L;

	@EmbeddedId
	private SuccessioniPK id = null;
	
	@Column(name="PROGRESSIVO_EREDE")
	private BigDecimal progressivoErede = null;
	
	@Column(name="CATEGORIA")
	private String categoria = null;

	@Column(name="CF_EREDE")
	private String cfErede = null;

	@Column(name="DENOMINAZIONE")
	private String denominazione = null;

	@Column(name="SESSO")
	private String sesso = null;

	@Column(name="CITTA_NASCITA")
	private String cittaNascita = null;

	@Column(name="PROV_NASCITA")
	private String provNascita = null;

	@Column(name="DATA_NASCITA")
	private String dataNascita = null;

	@Column(name="CITTA_RESIDENZA")
	private String cittaResidenza = null;

	@Column(name="PROV_RESIDENZA")
	private String provResidenza = null;

	@Column(name="INDIRIZZO_RESIDENZA")
	private String indirizzoResidenza = null;

	@Column(name="TIPO_RECORD")
	private String tipoRecord = null;


	public SuccessioniB() {
	}//-------------------------------------------------------------------------

	public SuccessioniPK getId() {
		return id;
	}

	public void setId(SuccessioniPK id) {
		this.id = id;
	}

	public BigDecimal getProgressivoErede() {
		return progressivoErede;
	}

	public void setProgressivoErede(BigDecimal progressivoErede) {
		this.progressivoErede = progressivoErede;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCfErede() {
		return cfErede;
	}

	public void setCfErede(String cfErede) {
		this.cfErede = cfErede;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCittaNascita() {
		return cittaNascita;
	}

	public void setCittaNascita(String cittaNascita) {
		this.cittaNascita = cittaNascita;
	}

	public String getProvNascita() {
		return provNascita;
	}

	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCittaResidenza() {
		return cittaResidenza;
	}

	public void setCittaResidenza(String cittaResidenza) {
		this.cittaResidenza = cittaResidenza;
	}

	public String getProvResidenza() {
		return provResidenza;
	}

	public void setProvResidenza(String provResidenza) {
		this.provResidenza = provResidenza;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getTipoRecord() {
		return tipoRecord;
	}

	public void setTipoRecord(String tipoRecord) {
		this.tipoRecord = tipoRecord;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
