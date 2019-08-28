package it.webred.cs.data.model;

import it.webred.cs.data.DataModelCostanti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_I_PS")
@NamedQuery(name="CsIPs.findAll", query="SELECT c FROM CsIPs c")
public class CsIPs implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CS_I_PS_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_PS_ID_GENERATOR")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="INT_ESEG_MAST_ID")
	private CsIInterventoEsegMast csIInterventoEsegMast; 
	
	@Column(name="BENEFICIARIO_LUOGO_NASCITA")
	private String benefLuogoNascita;
	
	@Column(name="BENEFICIARIO_SESSO")
	private Integer benefSesso;
	
	@Column(name="BENEFICIARIO_COD_CITTADI")
	private Integer benefCittadinanza; 
	
	@Column(name="BENEFICIARIO_REGIONE_RES")
	private String benefRegione;
	
	@Column(name="BENEFICIARIO_COMUNE_RES")
	private String benefComune;
	
	@Column(name="BENEFICIARIO_NAZIONE_RES")
	private String benefNazione;
	
	@Column(name="PREFIX_PROT_DSU")
	private String prefixProtDSU;
	
	@Column(name="NUM_PROT_DSU")
	private String numProtDSU;
	
	@Column(name="PROG_PROT_DSU")
	private String progProtDSU;
	
	@Column(name="ANNO_PROT_DSU")
	private Integer annoProtDSU;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_DSU")
	private Date dataDSU;
	
	@Column
	private String carattere;
			
	@Column(name="COD_PRESTAZIONE")
	private String codPrestazione;
	
	@Column(name="DENOM_PRESTAZIONE")
	private String denomPrestazione;
	 
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	

	@Column(name="FLAG_PROVA_MEZZI")
	private Integer flagProvaMezzi;

	@Column(name="FLAG_IN_CARICO")
	private Boolean flagInCarico;
	
	
	public CsIPs(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	} 

	public String getBenefLuogoNascita() {
		return benefLuogoNascita;
	}

	public void setBenefLuogoNascita(String benefLuogoNascita) {
		this.benefLuogoNascita = benefLuogoNascita;
	}

	public Integer getBenefSesso() {
		return benefSesso;
	}

	public void setBenefSesso(Integer benefSesso) {
		this.benefSesso = benefSesso;
	}

	public Integer getBenefCittadinanza() {
		return benefCittadinanza;
	}

	public void setBenefCittadinanza(Integer benefCittadinanza) {
		this.benefCittadinanza = benefCittadinanza;
	} 

	public String getBenefRegione() {
		return benefRegione;
	}

	public void setBenefRegione(String benefRegione) {
		this.benefRegione = benefRegione;
	}

	public String getBenefComune() {
		return benefComune;
	}

	public void setBenefComune(String benefComune) {
		this.benefComune = benefComune;
	}

	public String getBenefNazione() {
		return benefNazione;
	}

	public void setBenefNazione(String benefNazione) {
		this.benefNazione = benefNazione;
	}

	public String getNumProtDSU() {
		return numProtDSU;
	}

	public void setNumProtDSU(String numProtDSU) {
		this.numProtDSU = numProtDSU;
	}

	public Integer getAnnoProtDSU() {
		return annoProtDSU;
	}

	public void setAnnoProtDSU(Integer annoProtDSU) {
		this.annoProtDSU = annoProtDSU;
		setPrefixProtDSU(DataModelCostanti.CSIPs.PREFIX_PROT_DSU+ annoProtDSU);
	}

	public Date getDataDSU() {
		return dataDSU;
	}

	public void setDataDSU(Date dataDSU) {
		this.dataDSU = dataDSU;
	}

	public String getCodPrestazione() {
		return codPrestazione;
	}

	public void setCodPrestazione(String codPrestazione) {
		this.codPrestazione = codPrestazione;
	}

	public String getDenomPrestazione() {
		return denomPrestazione;
	}

	public void setDenomPrestazione(String denomPrestazione) {
		this.denomPrestazione = denomPrestazione;
	} 

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getCarattere() {
		return carattere;
	}

	public void setCarattere(String carattere) {
		this.carattere = carattere;
	}

	public CsIInterventoEsegMast getCsIInterventoEsegMast() {
		return csIInterventoEsegMast;
	}

	public void setCsIInterventoEsegMast(CsIInterventoEsegMast csIInterventoEsegMast) {
		this.csIInterventoEsegMast = csIInterventoEsegMast;
	}

	public Integer getFlagProvaMezzi() {
		return flagProvaMezzi;
	}

	public void setFlagProvaMezzi(Integer flagProvaMezzi) {
		this.flagProvaMezzi = flagProvaMezzi;
	}

	public Boolean getFlagInCarico() {
		return flagInCarico;
	}

	public void setFlagInCarico(Boolean flagInCarico) {
		this.flagInCarico = flagInCarico;
	}

	public String getPrefixProtDSU() {
		return prefixProtDSU;
	}

	public void setPrefixProtDSU(String prefixProtDSU) {
		this.prefixProtDSU = prefixProtDSU;
	}

	public String getProgProtDSU() {
		if(this.getPrefixProtDSU()!=null && (this.progProtDSU==null || this.progProtDSU.isEmpty()))
			this.progProtDSU="00";
		return progProtDSU;
	}

	public void setProgProtDSU(String progProtDSU) {
		this.progProtDSU = progProtDSU;
	}
	
	public boolean isSituazioneEconomicaVerificata() {  
		if (this.getFlagProvaMezzi()!=null) { 
			return this.getFlagProvaMezzi() ==  DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_ISEE_E_PROVA_MEZZI  || 
				   this.getFlagProvaMezzi() ==  DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_ISEE_NON_PROVA_MEZZI ;
		} else
			return false;
	}
	
}
