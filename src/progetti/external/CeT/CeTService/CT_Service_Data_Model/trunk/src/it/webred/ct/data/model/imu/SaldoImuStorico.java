package it.webred.ct.data.model.imu;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SALDO_IMU_STORICO database table.
 * 
 */
@Entity
@Table(name="SALDO_IMU_STORICO")
public class SaldoImuStorico implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SaldoImuStoricoPK id;

	private String belfiore;

	private String cognome;

	@Column(name="COMU_NASC")
	private String comuNasc;

	@Column(name="COMU_RES")
	private String comuRes;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	private String iban;

	@Column(name="IND_RES")
	private String indRes;

	private String nome;

	@Column(name="PROV_NASC")
	private String provNasc;

	@Column(name="PROV_RES")
	private String provRes;

	private String sesso;

    @Lob()
	private String xml;

    public SaldoImuStorico() {
    }

	public SaldoImuStoricoPK getId() {
		return this.id;
	}

	public void setId(SaldoImuStoricoPK id) {
		this.id = id;
	}
	
	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComuNasc() {
		return this.comuNasc;
	}

	public void setComuNasc(String comuNasc) {
		this.comuNasc = comuNasc;
	}

	public String getComuRes() {
		return this.comuRes;
	}

	public void setComuRes(String comuRes) {
		this.comuRes = comuRes;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getIndRes() {
		return this.indRes;
	}

	public void setIndRes(String indRes) {
		this.indRes = indRes;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProvNasc() {
		return this.provNasc;
	}

	public void setProvNasc(String provNasc) {
		this.provNasc = provNasc;
	}

	public String getProvRes() {
		return this.provRes;
	}

	public void setProvRes(String provRes) {
		this.provRes = provRes;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getXml() {
		return this.xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}