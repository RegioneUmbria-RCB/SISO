package it.webred.ct.data.model.catasto;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CONS_SOGG_TAB database table.
 * 
 */


@Entity
@Table(name="CONS_SOGG_TAB")
@IndiceEntity(sorgente="4")
public class ConsSoggTab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CODI_FISC")
	private String codiFisc;

	@Column(name="CODI_PIVA")
	private String codiPiva;

	@Column(name="COMU_NASC")
	private String comuNasc;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE")
	private Date dataFine;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO")
	private Date dataInizio;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_MORTE")
	private Date dataMorte;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_NASC")
	private Date dataNasc;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_REGISTRAZIONE")
	private Date dataRegistrazione;

	@Column(name="FLAG_PERS_FISICA")
	private String flagPersFisica;

	@Column(name="NATURA_GIURIDICA")
	private BigDecimal naturaGiuridica;

	private String nome;

	@Column(name="PK_CUAA")
	private BigDecimal pkCuaa;

	@Id
	@IndiceKey(pos="1")
	private BigDecimal pkid;

	@Column(name="RAGI_SOCI")
	private String ragiSoci;

	@Column(name="RESI_COMU")
	private String resiComu;

	private String sesso;

    @Temporal( TemporalType.DATE)
	private Date timestamp;

	private String utente;

    public ConsSoggTab() {
    }

	public String getCodiFisc() {
		return this.codiFisc;
	}

	public void setCodiFisc(String codiFisc) {
		this.codiFisc = codiFisc;
	}

	public String getCodiPiva() {
		return this.codiPiva;
	}

	public void setCodiPiva(String codiPiva) {
		this.codiPiva = codiPiva;
	}

	public String getComuNasc() {
		return this.comuNasc;
	}

	public void setComuNasc(String comuNasc) {
		this.comuNasc = comuNasc;
	}

	public Date getDataFine() {
		return this.dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Date getDataInizio() {
		return this.dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataMorte() {
		return this.dataMorte;
	}

	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}

	public Date getDataNasc() {
		return this.dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Date getDataRegistrazione() {
		return this.dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getFlagPersFisica() {
		return this.flagPersFisica;
	}

	public void setFlagPersFisica(String flagPersFisica) {
		this.flagPersFisica = flagPersFisica;
	}

	public BigDecimal getNaturaGiuridica() {
		return this.naturaGiuridica;
	}

	public void setNaturaGiuridica(BigDecimal naturaGiuridica) {
		this.naturaGiuridica = naturaGiuridica;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPkCuaa() {
		return this.pkCuaa;
	}

	public void setPkCuaa(BigDecimal pkCuaa) {
		this.pkCuaa = pkCuaa;
	}

	public BigDecimal getPkid() {
		return this.pkid;
	}

	public void setPkid(BigDecimal pkid) {
		this.pkid = pkid;
	}

	public String getRagiSoci() {
		return this.ragiSoci;
	}

	public void setRagiSoci(String ragiSoci) {
		this.ragiSoci = ragiSoci;
	}

	public String getResiComu() {
		return this.resiComu;
	}

	public void setResiComu(String resiComu) {
		this.resiComu = resiComu;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getUtente() {
		return this.utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

}