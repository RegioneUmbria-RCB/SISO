package it.webred.cs.csa.ejb.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.webred.cs.data.DataModelCostanti.CSIPs.FLAG_IN_CARICO;
import it.webred.cs.data.model.CsCTipoIntervento;

/**
 * 
 * bean che tiene i dati da stampare nell'xml di esportazione delle erogazioni
 * 
 */
public class EsportazioneDTO extends EsportazioneSpesaDTO {

	private static final long serialVersionUID = 1L;

	private BigDecimal interventoId;
	private Long interventoEsegId;
	private Long interventoEsegMastId;

	// CsIPs
	private Integer benefAnnoNascita;
	private String benefLuogoNascita;
	private Integer benefSesso;
	private Integer benefCittadinanza;
	//SISO-962
	private Integer benefSecCittadinanza;
	
	private String benefRegione;
	private String benefComune;
	private String benefNazione;
	private String numProtDSU;
	private Integer annoProtDSU;
	private Date dataDSU;
	private String carattere;
	private String codPrestazione;
	private String denomPrestazione;
	private Boolean picPrestazione=false;
	private String protDomPrest;
	// private BigDecimal sogliaISEE;

	// da CsIInterventoEseg oppure da CsIInterventoEsegMast a seconda di dove
	// sono valorizzati

	private String soggettoCodiceFiscale;
	private String soggettoCognome;
	private String soggettoNome;
	private String tipoBeneficiario;
	private String frequenza;
	
	
	private String enteOperatoreErogante;
	private String nomeOperatoreErog;
	private String note;
	private String noteAltreCompart;
	private String prestazioneProtocEnte;
	// SISO-538 private long idCsInterventoEsegMast;
	
	private Date dataEsecuzione;
	private Date dataEsecuzioneA; // SISO-538
	private Date dataEvento;
	
	// INIZIO SISO-538 campi utili nel caso di erogazioni periodiche
	protected Date dataInizio;
	protected Date dataFine;
	protected int periodoErogazione;
	protected BigDecimal importoMensile;
	
	// FINE SISO-538

	// SISO-657 - rif id BX202 nuovo schema flusso INPS
	private BigDecimal presenzaProvaMezzi;
	private BigDecimal presaInCarico;
	private BigDecimal categoriaSocialeId;
	// ~-- SISO-657

	/* SISO-719 */
	private String categoriaSocialeDescrizione;
	// SISO-784 SINA
	private Long mobilita;
	private Long attivitaVitaQuotidiana;
	private Long disturbiAreaCognitiva;
	private Long disturbiComportamentali;
	private Long necessitaCureSanitarie;
	private Long areaReddituale;
	private Long areaSupporto;
	private Long fonteDerivazioneValutazione;
	private Long strumentoValutazione;
	private List<String> invCiv = new ArrayList<String>();
	private Long fonteDerivazioneInvalidita;
	private List<String> codiciPrestazione = new ArrayList<String>();
	private Boolean isSinaCollegato = false;
	//** mod. SISO-886 **//
	private Boolean isSinaFlagValutaDopo = false;
	
	private Long tipoInterventoId;
	private String tipoInterventoDesc;
	private CsCTipoIntervento tipoInterventoCustom;
	
	//SISO-806
	private Long unitaMisura;
	private BigDecimal valQuota;
	String oreServizioMensile;
	//FINE SISO_806
	
	
	@SuppressWarnings("unused")
	// in realtà è usato
	private String nomeBeneficiario;

	
	
	public Integer getBenefSecCittadinanza() {
		return benefSecCittadinanza;
	}

	public void setBenefSecCittadinanza(Integer benefSecCittadinanza) {
		this.benefSecCittadinanza = benefSecCittadinanza;
	}

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

	// public Integer getBenefSecCittadinanza() {
	// return benefSecCittadinanza;
	// }
	//
	// public void setBenefSecCittadinanza(Integer benefSecCittadinanza) {
	// this.benefSecCittadinanza = benefSecCittadinanza;
	// }

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

	// public BigDecimal getSogliaISEE() {
	// return sogliaISEE;
	// }
	//
	// public void setSogliaISEE(BigDecimal sogliaISEE) {
	// this.sogliaISEE = sogliaISEE;
	// }

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

	// public BigDecimal getSpesa() {
	// return spesa;
	// }
	//
	// public void setSpesa(BigDecimal spesa) {
	// this.spesa = spesa;
	// }


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
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(soggettoCognome);
		sBuilder.append(" ");
		sBuilder.append(soggettoNome);
		return nomeBeneficiario = sBuilder.toString();
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

	// public long getIdCsInterventoEsegMast() {
	// return idCsInterventoEsegMast;
	// }
	//
	// public void setIdCsInterventoEsegMast(long idCsInterventoEsegMast) {
	// this.idCsInterventoEsegMast = idCsInterventoEsegMast;
	// }

	public BigDecimal getInterventoId() {
		return interventoId;
	}

	public void setInterventoId(BigDecimal interventoId) {
		this.interventoId = interventoId;
	}

	public Long getInterventoEsegId() {
		return interventoEsegId;
	}

	public void setInterventoEsegId(Long interventoEsegId) {
		this.interventoEsegId = interventoEsegId;
	}

	public Long getInterventoEsegMastId() {
		return interventoEsegMastId;
	}

	public void setInterventoEsegMastId(long interventoEsegMastId) {
		this.interventoEsegMastId = interventoEsegMastId;
	}

	// inizio SISO-538
	public Date getDataEsecuzioneA() {
		return dataEsecuzioneA;
	}
	public void setDataEsecuzioneA(Date dataEsecuzioneA) {
		this.dataEsecuzioneA = dataEsecuzioneA;
	}
/*	
	public Date getMinDataEsecuzione() {
		return dataEsecuzioneA;
	}*/
	public Date getMaxDataEsecuzione() {
		return dataEsecuzioneA==null?dataEsecuzione:dataEsecuzioneA;
	}
	
	public Date getDataEsecuzione() {
		return dataEsecuzione;
	}

	public void setDataEsecuzione(Date dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}

	public BigDecimal getPresenzaProvaMezzi() {
		return presenzaProvaMezzi;
	}

	public void setPresenzaProvaMezzi(BigDecimal presenzaProvaMezzi) {
		this.presenzaProvaMezzi = presenzaProvaMezzi;
	}

	public BigDecimal getPresaInCarico() {
		return presaInCarico;
	}

	public void setPresaInCarico(BigDecimal presaInCarico) {
		this.presaInCarico = presaInCarico;
	}

	public BigDecimal getCategoriaSocialeId() {
		return categoriaSocialeId;
	}

	public void setCategoriaSocialeId(BigDecimal categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}

	public String getCategoriaSocialeDescrizione() {
		return categoriaSocialeDescrizione;
	}

	public void setCategoriaSocialeDescrizione(String categoriaSocialeDescrizione) {
		this.categoriaSocialeDescrizione = categoriaSocialeDescrizione;
	}
	public Long getMobilita() {
		return mobilita;
	}

	public void setMobilita(Long mobilita) {
		this.mobilita = mobilita;
	}

	public Long getAttivitaVitaQuotidiana() {
		return attivitaVitaQuotidiana;
	}

	public void setAttivitaVitaQuotidiana(Long attivitaVitaQuotidiana) {
		this.attivitaVitaQuotidiana = attivitaVitaQuotidiana;
	}

	public Long getDisturbiAreaCognitiva() {
		return disturbiAreaCognitiva;
	}

	public void setDisturbiAreaCognitiva(Long disturbiAreaCognitiva) {
		this.disturbiAreaCognitiva = disturbiAreaCognitiva;
	}

	public Long getDisturbiComportamentali() {
		return disturbiComportamentali;
	}

	public void setDisturbiComportamentali(Long disturbiComportamentali) {
		this.disturbiComportamentali = disturbiComportamentali;
	}

	public Long getNecessitaCureSanitarie() {
		return necessitaCureSanitarie;
	}

	public void setNecessitaCureSanitarie(Long necessitaCureSanitarie) {
		this.necessitaCureSanitarie = necessitaCureSanitarie;
	}

	public Long getAreaReddituale() {
		return areaReddituale;
	}

	public void setAreaReddituale(Long areaReddituale) {
		this.areaReddituale = areaReddituale;
	}

	public Long getAreaSupporto() {
		return areaSupporto;
	}

	public void setAreaSupporto(Long areaSupporto) {
		this.areaSupporto = areaSupporto;
	}

	public Long getFonteDerivazioneValutazione() {
		return fonteDerivazioneValutazione;
	}

	public void setFonteDerivazioneValutazione(Long fonteDerivazioneValutazione) {
		this.fonteDerivazioneValutazione = fonteDerivazioneValutazione;
	}

	public Long getStrumentoValutazione() {
		return strumentoValutazione;
	}

	public void setStrumentoValutazione(Long strumentoValutazione) {
		this.strumentoValutazione = strumentoValutazione;
	}

	public List<String> getInvCiv() {
		return invCiv;
	}

	public void setInvCiv(List<String> invCiv) {
		this.invCiv = invCiv;
	}

	public Long getFonteDerivazioneInvalidita() {
		return fonteDerivazioneInvalidita;
	}

	public void setFonteDerivazioneInvalidita(Long fonteDerivazioneInvalidita) {
		this.fonteDerivazioneInvalidita = fonteDerivazioneInvalidita;
	}

	public void setInterventoEsegMastId(Long interventoEsegMastId) {
		this.interventoEsegMastId = interventoEsegMastId;
	}

	public List<String> getCodiciPrestazione() {
		return codiciPrestazione;
	}

	public void setCodiciPrestazione(List<String> codiciPrestazione) {
		this.codiciPrestazione = codiciPrestazione;
	}

	public Boolean getIsSinaCollegato() {
		return isSinaCollegato;
	}

	public void setIsSinaCollegato(Boolean isSinaCollegato) {
		this.isSinaCollegato = isSinaCollegato;
	}
	
	public Boolean getIsSinaFlagValutaDopo() {
		return isSinaFlagValutaDopo;
	}

	public void setIsSinaFlagValutaDopo(Boolean isSinaFlagValutaDopo) {
		this.isSinaFlagValutaDopo = isSinaFlagValutaDopo;
	}
	
	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public int getPeriodoErogazione() {
		return periodoErogazione;
	}

	public void setPeriodoErogazione(int periodoErogazione) {
		this.periodoErogazione = periodoErogazione;
	}

	public BigDecimal getImportoMensile() {
		return importoMensile;
	}

	public void setImportoMensile(BigDecimal importoMensile) {
		this.importoMensile = importoMensile;
	}
	
	public Long getTipoInterventoId() {
		return tipoInterventoId;
	}

	public void setTipoInterventoId(Long tipoInterventoId) {
		this.tipoInterventoId = tipoInterventoId;
	}

	public String getTipoInterventoDesc() {
		return tipoInterventoDesc;
	}

	public void setTipoInterventoDesc(String tipoInterventoDesc) {
		this.tipoInterventoDesc = tipoInterventoDesc;
	}

	public CsCTipoIntervento getTipoInterventoCustom() {
		return tipoInterventoCustom;
	}

	public void setTipoInterventoCustom(CsCTipoIntervento tipoInterventoCustom) {
		this.tipoInterventoCustom = tipoInterventoCustom;
	}

	//SISO-806
	public Long getUnitaMisura() {
		return unitaMisura;
	}

	public void setUnitaMisura(Long unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	public BigDecimal getValQuota() {
		return valQuota;
	}

	public void setValQuota(BigDecimal valQuota) {
		this.valQuota = valQuota;
	}
	
	public String getOreServizioMensile() {
		return oreServizioMensile;
	}

	public void setOreServizioMensile(String oreServizioMensile) {
		this.oreServizioMensile = oreServizioMensile;
	}
	
	//SISO-806


	@Override
	public String toString() {
		return "EsportazioneDTO [interventoId=" + interventoId
				+ ", interventoEsegId=" + interventoEsegId
				+ ", interventoEsegMastId=" + interventoEsegMastId
				+ ", benefAnnoNascita=" + benefAnnoNascita
				+ ", benefLuogoNascita=" + benefLuogoNascita 
				+ ", benefSesso="+ benefSesso 
				+ ", benefCittadinanza=" + benefCittadinanza
				+ ", benefRegione=" + benefRegione 
				+ ", benefComune="+ benefComune 
				+ ", benefNazione=" + benefNazione
				+ ", numProtDSU=" + numProtDSU + ", annoProtDSU=" + annoProtDSU
				+ ", dataDSU=" + dataDSU 
				+ ", carattere=" + carattere
				+ ", codPrestazione=" + codPrestazione 
				+ ", denomPrestazione="+ denomPrestazione 
				+ ", protDomPrest=" + protDomPrest
				+ ", soggettoCodiceFiscale=" + soggettoCodiceFiscale
				+ ", soggettoCognome=" + soggettoCognome 
				+ ", soggettoNome="+ soggettoNome 
				+ ", compartAltre=" + compartAltre
				+ ", compartSsn=" + compartSsn 
				+ ", compartUtenti="+ compartUtenti 
				+ ", percGestitaEnte=" + percGestitaEnte
				+ ", valoreGestitaEnte=" + valoreGestitaEnte
				+ ", enteOperatoreErogante=" + enteOperatoreErogante
				+ ", nomeOperatoreErog=" + nomeOperatoreErog 
				+ ", note=" + note
				+ ", noteAltreCompart=" + noteAltreCompart
				+ ", prestazioneProtocEnte=" + prestazioneProtocEnte
				+ ", dataInizio="+ dataInizio 
				+ ", dataFine=" + dataFine
				+ ", dataEsecuzione=" + dataEsecuzione
				+ ", dataEsecuzioneA=" + dataEsecuzioneA 
				+ ", dataEvento=" + dataEvento 
				+ ", periodoErogazione=" + periodoErogazione
				+ ", importoMensile=" + importoMensile 
				+ ", spesaTestata="+ spesaTestata 
				+ ", spesaDettaglio=" + spesaDettaglio
				+ ", presenzaProvaMezzi=" + presenzaProvaMezzi
				+ ", presaInCarico=" + presaInCarico 
				+ ", categoriaSocialeId="+ categoriaSocialeId 
				+ ", categoriaSocialeDescrizione="+ categoriaSocialeDescrizione
				+ ", nomeBeneficiario=" + nomeBeneficiario
				+ ", unitaMisura=" + unitaMisura
				+ ", valQuota=" + valQuota
				+ ", oreServizioMensile=" + oreServizioMensile
				+ ", tipoBeneficiario=" + tipoBeneficiario
				+ ", frequenza=" + frequenza
				+ "]";
	}
	// fine SISO-538

	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	public String getFrequenza() {
		return frequenza;
	}

	public void setFrequenza(String frequenza) {
		this.frequenza = frequenza;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public Boolean getPicPrestazione() {
		return picPrestazione;
	}

	public void setPicPrestazione(Boolean picPrestazione) {
		this.picPrestazione = picPrestazione;
	}

	public Boolean getRenderMessaggioPrestazionePic() {
		boolean render = false;
		if(this.presaInCarico!=null && this.picPrestazione) {
			render =  this.picPrestazione && FLAG_IN_CARICO.NO.getCodice()==presaInCarico.intValue();
		}
		return render;
	}
}
