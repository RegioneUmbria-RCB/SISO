package it.webred.cs.data.model.sal;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CS_PAI_SAL database table.
 * 
 */
@Entity
@Table(name = "CS_PAI_SAL")
public class CsPaiSAL {


	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "CS_PAI_SAL_ID_GENERATOR", sequenceName = "SQ_PAI_SAL", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_PAI_SAL_ID_GENERATOR")
	private Long id;

	@Column(name = "DIARIO_PAI_ID")
	private Long diarioPaiId;

	@Column(name = "ALTRI_ELE_FORMATIVI")
	private String altriEleFormativi;

	@Temporal(TemporalType.DATE)
	@Column(name = "ANNO_ULTIMA_ESPERIENZA")
	private Date annoUltimaEsperienza;

	@Column(name = "ATTITUDINI_PERSONALI")
	private String attitudiniPersonali;

	@Column(name = "CARATTERISTICHE_PERSONALI")
	private String caratteristichePersonali;

	@Column(name = "COMPETENZE_CAPACITA")
	private String competenzeCapacita;

	@Column(name = "COMPETENZE_CAPACITA_ACQUISITE")
	private String competenzeCapacitaAcquisite;

	@Column(name = "DESIDERI_ASPETTATIVE")
	private String desideriAspettative;

	@Column(name = "DETTAGLIO_ESPERIENZA")
	private String dettaglioEsperienza;

	@Column(name = "DURATA_ESPERIENZA")
	private String durataEsperienza;

	@Column(name = "DURATA_PERIODO_PROVA")
	private String durataPeriodoProva;
	
	@Column(name = "TUTOR_CONTESTO")
	private String tutorContesto;

	@Column(name = "MANSIONE")
	private String mansione;
	
	@Column(name = "ESITO")
	private String esito;
	
	@Column(name = "ESPERIENZE")
	private String esperienze;

	@Column(name = "ID_TITOLO_STUDIO")
	private Long idTitoloStudio;

	@Column(name = "INVIANTE")
	private String inviante;

	@Column(name = "ISCRIZIONE_CPI")
	private String iscrizioneCpi;

	@Column(name = "LEGGE104_92")
	private String legge10492;

	@Column(name = "LEGGE68_99")
	private String legge6899;

	@Column(name = "NOME_CONTESTO")
	private String nomeContesto;

	@Column(name = "NOTE_CV_LAVORATIVO")
	private String noteCvLavorativo;

	@Column(name = "NOTE_CV_SCOLASTICO")
	private String noteCvScolastico;

	@Column(name = "OPERATORE_ACCOMP")
	private String operatoreAccomp;

	@Column(name = "PERIODO_PROVA")
	private String periodoProva;

	@Column(name = "PRESA_CARICO_INVIANTE")
	private String presaCaricoInviante;

	@Column(name = "PRESENZA_TUTOR_CONTESTO")
	private String presenzaTutorContesto;

	@Column(name = "PROPOSTA_PRES_FAMIGLIA")
	private String propostaPresFamiglia;

	@Column(name = "QUESTIONI_PERS_FAM")
	private String questioniPersFam;

	@Column(name = "RICHIEDENTE_ASILO")
	private String richiedenteAsilo;

	@Column(name = "RIFERIMENTO_INVIANTE")
	private String riferimentoInviante;

	@Column(name = "SOGGETTI_COINVOLTI")
	private String soggettiCoinvolti;

	@Column(name = "TEMPO_PIENO_PARZIALE")
	private Long tempoPienoParziale;

	@Column(name = "TIPO_ESPERIENZA")
	private String tipoEsperienza;

	@Column(name = "TIPO_SOGGETTO")
	private String tipoSoggetto;

	@Column(name = "VICINANZA_ABITAZIONE")
	private String vicinanzaAbitazione;

	@Column(name = "ELEMENTI_VERIFICA")
	private String elementiVerifica;

	@OneToMany(mappedBy = "paiSal", cascade = CascadeType.ALL)
	private List<CsPaiSALFase> fasiSAL;

	@OneToMany(mappedBy = "paiSal",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CsPaiSalStorico> storicoSal;


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAltriEleFormativi() {
		return this.altriEleFormativi;
	}

	public void setAltriEleFormativi(String altriEleFormativi) {
		this.altriEleFormativi = altriEleFormativi;
	}

	public Date getAnnoUltimaEsperienza() {
		return this.annoUltimaEsperienza;
	}

	public void setAnnoUltimaEsperienza(Date annoUltimaEsperienza) {
		this.annoUltimaEsperienza = annoUltimaEsperienza;
	}

	public String getAttitudiniPersonali() {
		return this.attitudiniPersonali;
	}

	public void setAttitudiniPersonali(String attitudiniPersonali) {
		this.attitudiniPersonali = attitudiniPersonali;
	}

	public String getCaratteristichePersonali() {
		return this.caratteristichePersonali;
	}

	public void setCaratteristichePersonali(String caratteristichePersonali) {
		this.caratteristichePersonali = caratteristichePersonali;
	}

	public String getCompetenzeCapacita() {
		return this.competenzeCapacita;
	}

	public void setCompetenzeCapacita(String competenzeCapacita) {
		this.competenzeCapacita = competenzeCapacita;
	}

	public String getCompetenzeCapacitaAcquisite() {
		return this.competenzeCapacitaAcquisite;
	}

	public void setCompetenzeCapacitaAcquisite(String competenzeCapacitaAcquisite) {
		this.competenzeCapacitaAcquisite = competenzeCapacitaAcquisite;
	}

	public String getDesideriAspettative() {
		return this.desideriAspettative;
	}

	public void setDesideriAspettative(String desideriAspettative) {
		this.desideriAspettative = desideriAspettative;
	}

	public String getDettaglioEsperienza() {
		return this.dettaglioEsperienza;
	}

	public void setDettaglioEsperienza(String dettaglioEsperienza) {
		this.dettaglioEsperienza = dettaglioEsperienza;
	}

	public String getDurataEsperienza() {
		return this.durataEsperienza;
	}

	public void setDurataEsperienza(String durataEsperienza) {
		this.durataEsperienza = durataEsperienza;
	}

	public String getDurataPeriodoProva() {
		return this.durataPeriodoProva;
	}

	public void setDurataPeriodoProva(String durataPeriodoProva) {
		this.durataPeriodoProva = durataPeriodoProva;
	}

	public String getEsito() {
		return this.esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getEsperienze() {
		return this.esperienze;
	}

	public void setEsperienze(String esperienze) {
		this.esperienze = esperienze;
	}

	public String getInviante() {
		return this.inviante;
	}

	public void setInviante(String inviante) {
		this.inviante = inviante;
	}

	public String getNomeContesto() {
		return this.nomeContesto;
	}

	public void setNomeContesto(String nomeContesto) {
		this.nomeContesto = nomeContesto;
	}

	public String getNoteCvLavorativo() {
		return this.noteCvLavorativo;
	}

	public void setNoteCvLavorativo(String noteCvLavorativo) {
		this.noteCvLavorativo = noteCvLavorativo;
	}

	public String getNoteCvScolastico() {
		return this.noteCvScolastico;
	}

	public void setNoteCvScolastico(String noteCvScolastico) {
		this.noteCvScolastico = noteCvScolastico;
	}

	public String getOperatoreAccomp() {
		return this.operatoreAccomp;
	}

	public void setOperatoreAccomp(String operatoreAccomp) {
		this.operatoreAccomp = operatoreAccomp;
	}

	public String getPresenzaTutorContesto() {
		return presenzaTutorContesto;
	}

	public void setPresenzaTutorContesto(String presenzaTutorContesto) {
		this.presenzaTutorContesto = presenzaTutorContesto;
	}

	public String getQuestioniPersFam() {
		return this.questioniPersFam;
	}

	public void setQuestioniPersFam(String questioniPersFam) {
		this.questioniPersFam = questioniPersFam;
	}

	public String getRiferimentoInviante() {
		return this.riferimentoInviante;
	}

	public void setRiferimentoInviante(String riferimentoInviante) {
		this.riferimentoInviante = riferimentoInviante;
	}

	public String getSoggettiCoinvolti() {
		return this.soggettiCoinvolti;
	}

	public void setSoggettiCoinvolti(String soggettiCoinvolti) {
		this.soggettiCoinvolti = soggettiCoinvolti;
	}

	public String getTipoEsperienza() {
		return this.tipoEsperienza;
	}

	public void setTipoEsperienza(String tipoEsperienza) {
		this.tipoEsperienza = tipoEsperienza;
	}

	public String getTipoSoggetto() {
		return this.tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getVicinanzaAbitazione() {
		return vicinanzaAbitazione;
	}

	public void setVicinanzaAbitazione(String vicinanzaAbitazione) {
		this.vicinanzaAbitazione = vicinanzaAbitazione;
	}

	public String getElementiVerifica() {
		return elementiVerifica;
	}

	public void setElementiVerifica(String elementiVerifica) {
		this.elementiVerifica = elementiVerifica;
	}

	public List<CsPaiSalStorico> getStoricoSal() {
		return storicoSal;
	}

	public void setStoricoSal(List<CsPaiSalStorico> storicoSal) {
		this.storicoSal = storicoSal;
	}

	public List<CsPaiSALFase> getFasiSAL() {
		return fasiSAL;
	}

	public void setFasiSAL(List<CsPaiSALFase> fasiSAL) {
		this.fasiSAL = fasiSAL;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}

	public String getIscrizioneCpi() {
		return iscrizioneCpi;
	}

	public void setIscrizioneCpi(String iscrizioneCpi) {
		this.iscrizioneCpi = iscrizioneCpi;
	}

	public String getLegge10492() {
		return legge10492;
	}

	public void setLegge10492(String legge10492) {
		this.legge10492 = legge10492;
	}

	public String getLegge6899() {
		return legge6899;
	}

	public void setLegge6899(String legge6899) {
		this.legge6899 = legge6899;
	}

	public String getPeriodoProva() {
		return periodoProva;
	}

	public void setPeriodoProva(String periodoProva) {
		this.periodoProva = periodoProva;
	}

	public String getPresaCaricoInviante() {
		return presaCaricoInviante;
	}

	public void setPresaCaricoInviante(String presaCaricoInviante) {
		this.presaCaricoInviante = presaCaricoInviante;
	}

	public String getPropostaPresFamiglia() {
		return propostaPresFamiglia;
	}

	public void setPropostaPresFamiglia(String propostaPresFamiglia) {
		this.propostaPresFamiglia = propostaPresFamiglia;
	}

	public String getRichiedenteAsilo() {
		return richiedenteAsilo;
	}

	public void setRichiedenteAsilo(String richiedenteAsilo) {
		this.richiedenteAsilo = richiedenteAsilo;
	}

	public Long getTempoPienoParziale() {
		return tempoPienoParziale;
	}

	public void setTempoPienoParziale(Long tempoPienoParziale) {
		this.tempoPienoParziale = tempoPienoParziale;
	}

	public Long getIdTitoloStudio() {
		return idTitoloStudio;
	}

	public void setIdTitoloStudio(Long idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
	}

	public String getTutorContesto() {
		return tutorContesto;
	}

	public void setTutorContesto(String tutorContesto) {
		this.tutorContesto = tutorContesto;
	}

	public String getMansione() {
		return mansione;
	}

	public void setMansione(String mansione) {
		this.mansione = mansione;
	}

}
