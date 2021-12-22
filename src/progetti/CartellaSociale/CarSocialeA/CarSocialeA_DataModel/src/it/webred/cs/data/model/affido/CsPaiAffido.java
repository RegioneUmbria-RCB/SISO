package it.webred.cs.data.model.affido;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="CS_PAI_AFFIDO")
public class CsPaiAffido {

	@Id
	@Column(name="ID")
	@SequenceGenerator(name="CS_PAI_AFFIDO_ID_GENERATOR", sequenceName="SQ_PAI_AFFIDO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_PAI_AFFIDO_ID_GENERATOR")
	private Long id;
	
	@Column(name="DIARIO_PAI_ID")
	private Long diarioPaiId;
	
	@Column(name="NUMERO_DECRETO")
	private String numeroDecreto;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_DECRETO")
	private Date dataDecreto;
	
	@Column(name="CODICE_FORME_AFFIDAMENTO")
	private String codiceFormeAffidamento;
	
	@Column(name="CODICE_COLLOCAMENTO")
	private String codiceCollocamento;
	
	@Column(name="CODICE_ENTITA_AFFIDO")
	private String codiceEntitaAffido;
	
	@Column(name="ENTITA_AFFIDO_PARZIALE")
	private String descEntitaAffidoParziale;
	
	@Column(name="CODICE_TIPO_ACCOGLIENZA")
	private String codiceTipoAccoglienza; 
	
	@Column(name="CODICE_NATURA_ACCOGLIENZA")
	private String codiceNaturaAccoglienza;
	
	//SISO-981 Inizio
	@Column(name="CODICE_AUTORITA_PROVVEDIMENTO")
	private String codiceAutoritaProvvedimento;
	@Column(name="CODICE_INSERIMENTO_RES")
	private String codiceInserimentoResidenziale;
	
	//SISO-981 Fine	

	@Column(name="AFFIDAMENTO_PROFESSIONALE")
	private Boolean affidamentoProfessionale;
	
	@Column(name="CODICE_ADOTTABILE")
	private String codiceAdottabile;
	
	@Column(name="PRESENZA_RETI_FAMIGLIE")
	private Boolean presenzaRetiDiFamiglie;
	
	@Column(name="CODICE_FREQUENZA_CONTATTI")
	private String codiceFrequenzaContattiMinore;
	
	@Column(name="CODICE_CONV_ORIG_AFF")
	private String codiceConvivenzaOrigineAffidataria;
	
	@Column(name="IMPEGNO_FAMIGLIA_ORIG")
	private String impegnoFamigliaOrigine;
	
	@Column(name="IMPEGNO_FAMIGLIA_AFF")
	private String impegnoFamigliaAffidataria;
	
	@Column(name="IMPEGNO_MINORE")
	private String impegnoMinore;
	
	@Column(name="IMPEGNO_SERV_SOCIALE")
	private String impegnoServizioSociale;
	
	@Column(name="IMPEGNO_ALTRI_SOGG")
	private String impegnoAltriSoggetti;
	
	@Column(name="CODICE_TIPO_AFFIDO")
	private String codiceTipoAffido;
	//Inizio SISO-1172
	@Column(name="AFFIDAMENTO_LUNGO_TERMINE") 
	private Boolean affidamentoLungoTermine;
	
	@Column(name="MODALITA_RAPPORTO_ORIG") 
	private String modalitaRapportoNucleoOrigine;
	
	@Column(name="CODICE_FREQUENZA_CONTATTI_AFF") 
	private String codiceFrequenzaContattiAff;
	
	@Column(name="MODALITA_RAPPORTO_AFF") 
	private String modalitaRapportoOrigAff;
	
	@Column(name="MODALITA_RAPPORTO_ESP_ORIG") 
	private String modalitaRappEsperienzaOrig;
	
	@Column(name="MODALITA_RAPPORTO_ESP_AFF") 
	private String modalitaRappEsperienzaFamAff;
	
	@Column(name="NOTE_GEST_ASPETTI_SANITA") 
	private String noteGestioneSanita;	

	@Column(name="PRESENZA_TUTORE") 
	private Boolean presenzaTutore;
	
	@Column(name="PRESENZA_CURATORE") 
	private Boolean presenzaCuratore;
	//Fine SISO-1172
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FAM_ORIG_ID")
	private CsPaiAffidoFamigliaOrigine famigliaOrigine;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FAM_AFF_ID")
	private CsPaiAffidoFamigliaAffidataria  famigliaAffidataria;
	
	@OneToMany(mappedBy="affido", cascade=CascadeType.ALL)
	private List<CsPaiAffidoStato> statiAffido;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="AFFIDO_ID", referencedColumnName="ID")
	private List<CsPaiAffidoSoggetto> soggettiAffido;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}

	public String getNumeroDecreto() {
		return numeroDecreto;
	}

	public void setNumeroDecreto(String numeroDecreto) {
		this.numeroDecreto = numeroDecreto;
	}

	public Date getDataDecreto() {
		return dataDecreto;
	}

	public void setDataDecreto(Date dataDecreto) {
		this.dataDecreto = dataDecreto;
	}

	public String getCodiceFormeAffidamento() {
		return codiceFormeAffidamento;
	}

	public void setCodiceFormeAffidamento(String codiceFormeAffidamento) {
		this.codiceFormeAffidamento = codiceFormeAffidamento;
	}

	public String getCodiceCollocamento() {
		return codiceCollocamento;
	}

	public void setCodiceCollocamento(String codiceCollocamento) {
		this.codiceCollocamento = codiceCollocamento;
	}

	public String getCodiceEntitaAffido() {
		return codiceEntitaAffido;
	}

	public void setCodiceEntitaAffido(String codiceEntitaAffido) {
		this.codiceEntitaAffido = codiceEntitaAffido;
	}

	public String getDescEntitaAffidoParziale() {
		return descEntitaAffidoParziale;
	}

	public void setDescEntitaAffidoParziale(String descEntitaAffidoParziale) {
		this.descEntitaAffidoParziale = descEntitaAffidoParziale;
	}

	public String getCodiceTipoAccoglienza() {
		return codiceTipoAccoglienza;
	}

	public void setCodiceTipoAccoglienza(String codiceTipoAccoglienza) {
		this.codiceTipoAccoglienza = codiceTipoAccoglienza;
	}

	public String getCodiceNaturaAccoglienza() {
		return codiceNaturaAccoglienza;
	}

	public void setCodiceNaturaAccoglienza(String codiceNaturaAccoglienza) {
		this.codiceNaturaAccoglienza = codiceNaturaAccoglienza;
	}

	public Boolean getAffidamentoProfessionale() {
		return affidamentoProfessionale;
	}
	
	//SISO-981 Inizio
	public String getCodiceAutoritaProvvedimento() {
		return codiceAutoritaProvvedimento;
	}

	public void setCodiceAutoritaProvvedimento(String codiceAutoritaProvvedimento) {
		this.codiceAutoritaProvvedimento = codiceAutoritaProvvedimento;
	}
	public String getCodiceInserimentoResidenziale() {
		return codiceInserimentoResidenziale;
	}

	public void setCodiceInserimentoResidenziale(
			String codiceInserimentoResidenziale) {
		this.codiceInserimentoResidenziale = codiceInserimentoResidenziale;
	}
	
	//SISO-981 Fine



	public void setAffidamentoProfessionale(Boolean affidamentoProfessionale) {
		this.affidamentoProfessionale = affidamentoProfessionale;
	}

	public String getCodiceAdottabile() {
		return codiceAdottabile;
	}

	public void setCodiceAdottabile(String codiceAdottabile) {
		this.codiceAdottabile = codiceAdottabile;
	}

	public Boolean getPresenzaRetiDiFamiglie() {
		return presenzaRetiDiFamiglie;
	}

	public void setPresenzaRetiDiFamiglie(Boolean presenzaRetiDiFamiglie) {
		this.presenzaRetiDiFamiglie = presenzaRetiDiFamiglie;
	}

	public String getCodiceFrequenzaContattiMinore() {
		return codiceFrequenzaContattiMinore;
	}

	public void setCodiceFrequenzaContattiMinore(
			String codiceFrequenzaContattiMinore) {
		this.codiceFrequenzaContattiMinore = codiceFrequenzaContattiMinore;
	}

	public String getCodiceConvivenzaOrigineAffidataria() {
		return codiceConvivenzaOrigineAffidataria;
	}

	public void setCodiceConvivenzaOrigineAffidataria(
			String codiceConvivenzaOrigineAffidataria) {
		this.codiceConvivenzaOrigineAffidataria = codiceConvivenzaOrigineAffidataria;
	}

	public CsPaiAffidoFamigliaOrigine getFamigliaOrigine() {
		return famigliaOrigine;
	}

	public void setFamigliaOrigine(CsPaiAffidoFamigliaOrigine famigliaOrigine) {
		this.famigliaOrigine = famigliaOrigine;
	}

	public CsPaiAffidoFamigliaAffidataria getFamigliaAffidataria() {
		return famigliaAffidataria;
	}

	public void setFamigliaAffidataria(
			CsPaiAffidoFamigliaAffidataria famigliaAffidataria) {
		this.famigliaAffidataria = famigliaAffidataria;
	}

	public List<CsPaiAffidoStato> getStatiAffido() {
		return statiAffido;
	}

	public void setStatiAffido(List<CsPaiAffidoStato> statiAffido) {
		this.statiAffido = statiAffido;
	}

	public String getImpegnoFamigliaOrigine() {
		return impegnoFamigliaOrigine;
	}

	public void setImpegnoFamigliaOrigine(String impegnoFamigliaOrigine) {
		this.impegnoFamigliaOrigine = impegnoFamigliaOrigine;
	}

	public String getImpegnoFamigliaAffidataria() {
		return impegnoFamigliaAffidataria;
	}

	public void setImpegnoFamigliaAffidataria(String impegnoFamigliaAffidataria) {
		this.impegnoFamigliaAffidataria = impegnoFamigliaAffidataria;
	}

	public String getImpegnoMinore() {
		return impegnoMinore;
	}

	public void setImpegnoMinore(String impegnoMinore) {
		this.impegnoMinore = impegnoMinore;
	}

	public String getImpegnoServizioSociale() {
		return impegnoServizioSociale;
	}

	public void setImpegnoServizioSociale(String impegnoServizioSociale) {
		this.impegnoServizioSociale = impegnoServizioSociale;
	}

	public String getImpegnoAltriSoggetti() {
		return impegnoAltriSoggetti;
	}

	public void setImpegnoAltriSoggetti(String impegnoAltriSoggetti) {
		this.impegnoAltriSoggetti = impegnoAltriSoggetti;
	}

	public String getCodiceTipoAffido() {
		return codiceTipoAffido;
	}

	public void setCodiceTipoAffido(String codiceTipoAffido) {
		this.codiceTipoAffido = codiceTipoAffido;
	}

	public List<CsPaiAffidoSoggetto> getSoggettiAffido() {
		return soggettiAffido;
	}

	public void setSoggettiAffido(List<CsPaiAffidoSoggetto> soggettiAffido) {
		this.soggettiAffido = soggettiAffido;
	}
	//SISO-1172

	public Boolean getAffidamentoLungoTermine() {
		return affidamentoLungoTermine;
	}

	public void setAffidamentoLungoTermine(Boolean affidamentoLungoTermine) {
		this.affidamentoLungoTermine = affidamentoLungoTermine;
	}

	public String getModalitaRapportoNucleoOrigine() {
		return modalitaRapportoNucleoOrigine;
	}

	public void setModalitaRapportoNucleoOrigine(
			String modalitaRapportoNucleoOrigine) {
		this.modalitaRapportoNucleoOrigine = modalitaRapportoNucleoOrigine;
	}

	public String getCodiceFrequenzaContattiAff() {
		return codiceFrequenzaContattiAff;
	}

	public void setCodiceFrequenzaContattiAff(String codiceFrequenzaContattiAff) {
		this.codiceFrequenzaContattiAff = codiceFrequenzaContattiAff;
	}

	public String getModalitaRapportoOrigAff() {
		return modalitaRapportoOrigAff;
	}

	public void setModalitaRapportoOrigAff(String modalitaRapportoOrigAff) {
		this.modalitaRapportoOrigAff = modalitaRapportoOrigAff;
	}

	public String getModalitaRappEsperienzaOrig() {
		return modalitaRappEsperienzaOrig;
	}

	public void setModalitaRappEsperienzaOrig(String modalitaRappEsperienzaOrig) {
		this.modalitaRappEsperienzaOrig = modalitaRappEsperienzaOrig;
	}

	public String getModalitaRappEsperienzaFamAff() {
		return modalitaRappEsperienzaFamAff;
	}

	public void setModalitaRappEsperienzaFamAff(String modalitaRappEsperienzaFamAff) {
		this.modalitaRappEsperienzaFamAff = modalitaRappEsperienzaFamAff;
	}

	public String getNoteGestioneSanita() {
		return noteGestioneSanita;
	}

	public void setNoteGestioneSanita(String noteGestioneSanita) {
		this.noteGestioneSanita = noteGestioneSanita;
	}

	public Boolean getPresenzaTutore() {
		return presenzaTutore;
	}

	public void setPresenzaTutore(Boolean presenzaTutore) {
		this.presenzaTutore = presenzaTutore;
	}

	public Boolean getPresenzaCuratore() {
		return presenzaCuratore;
	}

	public void setPresenzaCuratore(Boolean presenzaCuratore) {
		this.presenzaCuratore = presenzaCuratore;
	}
	//FINE SISO-1172
}
