package it.webred.ct.data.model.successioni;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SUCCESSIONI_D")
public class SuccessioniD implements Serializable{

	private static final long serialVersionUID = -1165385358114748182L;
	
	@EmbeddedId
	private SuccessioniPK id = null;
	
	@Column(name="PROGRESSIVO_IMMOBILE")
	private BigDecimal progressivoImmobile = null;

	@Column(name="PROGRESSIVO_EREDE")
	private BigDecimal progressivoErede = null;
	
	@Column(name="NUMERATORE_QUOTA")
	private BigDecimal numeratoreQuota = null;
	
	@Column(name="DENOMINATORE_QUOTA")
	private BigDecimal denominatoreQuota = null;
	
	@Column(name="AGEVOLAZIONE_1_CASA")
	private String agevolazione1Casa = "";
	
	@Column(name="TIPO_RECORD")
	private String tipoRecord = "";
	
	public SuccessioniD() {
	}//-------------------------------------------------------------------------

	public SuccessioniPK getId() {
		return id;
	}

	public void setId(SuccessioniPK id) {
		this.id = id;
	}

	public BigDecimal getProgressivoImmobile() {
		return progressivoImmobile;
	}

	public void setProgressivoImmobile(BigDecimal progressivoImmobile) {
		this.progressivoImmobile = progressivoImmobile;
	}

	public BigDecimal getProgressivoErede() {
		return progressivoErede;
	}

	public void setProgressivoErede(BigDecimal progressivoErede) {
		this.progressivoErede = progressivoErede;
	}

	public BigDecimal getNumeratoreQuota() {
		return numeratoreQuota;
	}

	public void setNumeratoreQuota(BigDecimal numeratoreQuota) {
		this.numeratoreQuota = numeratoreQuota;
	}

	public BigDecimal getDenominatoreQuota() {
		return denominatoreQuota;
	}

	public void setDenominatoreQuota(BigDecimal denominatoreQuota) {
		this.denominatoreQuota = denominatoreQuota;
	}

	public String getAgevolazione1Casa() {
		return agevolazione1Casa;
	}

	public void setAgevolazione1Casa(String agevolazione1Casa) {
		this.agevolazione1Casa = agevolazione1Casa;
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
