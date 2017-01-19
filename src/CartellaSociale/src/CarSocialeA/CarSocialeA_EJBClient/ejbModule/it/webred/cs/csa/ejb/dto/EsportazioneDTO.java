package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EsportazioneDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private BigDecimal interventoId;
	private long interventoEsegId;
	private long interventoEsegMastId;
	
	//CsIPs	
	private Integer benefAnnoNascita;	
	private String benefLuogoNascita;		
	private Integer benefSesso;		
	private Integer benefCittadinanza;		
//	private Integer benefSecCittadinanza;		
	private String benefRegione;		
	private String benefComune;		
	private String benefNazione;	
	private String numProtDSU;	
	private Integer annoProtDSU;	
	private Date dataDSU;		
	private String carattere;	
	private String codPrestazione;	
	private String denomPrestazione;	
	private String protDomPrest;		
//	private BigDecimal sogliaISEE;		
	
	//da CsIInterventoEseg oppure da CsIInterventoEsegMast a seconda di dove sono valorizzati
	
	private String soggettoCodiceFiscale; 		
	private String soggettoCognome;
	private String soggettoNome;	
	private BigDecimal spesa;	
	private BigDecimal compartAltre;
	private BigDecimal compartSsn;
	private BigDecimal compartUtenti;	
	private BigDecimal percGestitaEnte;	
	private BigDecimal valoreGestitaEnte;
	private Date dataEsecuzione;
	private String enteOperatoreErogante;
	private String nomeOperatoreErog;
	private String note;	
	private String noteAltreCompart;
	private String prestazioneProtocEnte;
	private long idCsInterventoEsegMast;
	
	
	
	private String nomeBeneficiario;  

	public Integer getBenefAnnoNascita() {
		return benefAnnoNascita;
	}

	public void setBenefAnnoNascita(Integer benefAnnoNascita) {
		this.benefAnnoNascita = benefAnnoNascita;
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

//	public Integer getBenefSecCittadinanza() {
//		return benefSecCittadinanza;
//	}
//
//	public void setBenefSecCittadinanza(Integer benefSecCittadinanza) {
//		this.benefSecCittadinanza = benefSecCittadinanza;
//	}

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

	public String getProtDomPrest() {
		return protDomPrest;
	}

	public void setProtDomPrest(String protDomPrest) {
		this.protDomPrest = protDomPrest;
	}

//	public BigDecimal getSogliaISEE() {
//		return sogliaISEE;
//	}
//
//	public void setSogliaISEE(BigDecimal sogliaISEE) {
//		this.sogliaISEE = sogliaISEE;
//	}

	public String getSoggettoCodiceFiscale() {
		return soggettoCodiceFiscale;
	}

	public void setSoggettoCodiceFiscale(String soggettoCodiceFiscale) {
		this.soggettoCodiceFiscale = soggettoCodiceFiscale;
	}

	public String getSoggettoCognome() {
		return soggettoCognome;
	}

	public void setSoggettoCognome(String soggettoCognome) {
		this.soggettoCognome = soggettoCognome;
	}

	public String getSoggettoNome() {
		return soggettoNome;
	}

	public void setSoggettoNome(String soggettoNome) {
		this.soggettoNome = soggettoNome;
	}

	public BigDecimal getSpesa() {
		return spesa;
	}

	public void setSpesa(BigDecimal spesa) {
		this.spesa = spesa;
	}

	public BigDecimal getCompartAltre() {
		return compartAltre;
	}

	public void setCompartAltre(BigDecimal compartAltre) {
		this.compartAltre = compartAltre;
	}

	public BigDecimal getCompartSsn() {
		return compartSsn;
	}

	public void setCompartSsn(BigDecimal compartSsn) {
		this.compartSsn = compartSsn;
	}

	public BigDecimal getCompartUtenti() {
		return compartUtenti;
	}

	public void setCompartUtenti(BigDecimal compartUtenti) {
		this.compartUtenti = compartUtenti;
	}

	public BigDecimal getPercGestitaEnte() {
		return percGestitaEnte;
	}

	public void setPercGestitaEnte(BigDecimal percGestitaEnte) {
		this.percGestitaEnte = percGestitaEnte;
	}

	public BigDecimal getValoreGestitaEnte() {
		return valoreGestitaEnte;
	}

	public void setValoreGestitaEnte(BigDecimal valoreGestitaEnte) {
		this.valoreGestitaEnte = valoreGestitaEnte;
	}

	public Date getDataEsecuzione() {
		return dataEsecuzione;
	}

	public void setDataEsecuzione(Date dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}

	public String getEnteOperatoreErogante() {
		return enteOperatoreErogante;
	}

	public void setEnteOperatoreErogante(String enteOperatoreErogante) {
		this.enteOperatoreErogante = enteOperatoreErogante;
	}

	public String getNomeOperatoreErog() {
		return nomeOperatoreErog;
	}

	public void setNomeOperatoreErog(String nomeOperatoreErog) {
		this.nomeOperatoreErog = nomeOperatoreErog;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNoteAltreCompart() {
		return noteAltreCompart;
	}

	public void setNoteAltreCompart(String noteAltreCompart) {
		this.noteAltreCompart = noteAltreCompart;
	}

	public String getCarattere() {
		return carattere;
	}

	public void setCarattere(String carattere) {
		this.carattere = carattere;
	}
	
	public String getNomeBeneficiario() {
		StringBuilder sBuilder= new StringBuilder();
		sBuilder.append(soggettoCognome);
		sBuilder.append(" ");
		sBuilder.append(soggettoNome);
		return nomeBeneficiario= sBuilder.toString();
	}

	public void setNomeBeneficiario(String nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}

	public String getPrestazioneProtocEnte() {
		return prestazioneProtocEnte;
	}

	public void setPrestazioneProtocEnte(String prestazioneProtocEnte) {
		this.prestazioneProtocEnte = prestazioneProtocEnte;
	}

	public long getIdCsInterventoEsegMast() {
		return idCsInterventoEsegMast;
	}

	public void setIdCsInterventoEsegMast(long idCsInterventoEsegMast) {
		this.idCsInterventoEsegMast = idCsInterventoEsegMast;
	}

	public BigDecimal getInterventoId() {
		return interventoId;
	}

	public void setInterventoId(BigDecimal interventoId) {
		this.interventoId = interventoId;
	}

	public long getInterventoEsegId() {
		return interventoEsegId;
	}

	public void setInterventoEsegId(long interventoEsegId) {
		this.interventoEsegId = interventoEsegId;
	}

	public long getInterventoEsegMastId() {
		return interventoEsegMastId;
	}

	public void setInterventoEsegMastId(long interventoEsegMastId) {
		this.interventoEsegMastId = interventoEsegMastId;
	}		
		
	
	
}
