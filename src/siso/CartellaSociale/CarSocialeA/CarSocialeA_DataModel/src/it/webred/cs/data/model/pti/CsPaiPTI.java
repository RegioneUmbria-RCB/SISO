package it.webred.cs.data.model.pti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CS_PAI_PTI database table.
 * 
 */
@Entity
@Table(name = "CS_PAI_PTI")
public class CsPaiPTI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2755378314172065359L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "PTI_ID_GENERATOR", sequenceName = "SQ_PAI_PTI", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PTI_ID_GENERATOR")
	private Long id;

	@Column(name = "DIARIO_PAI_ID")
	private Long diarioPaiId;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_REDAZIONE_PTI")
	private Date dataRedazione;

	@Column(name = "FLG_EMERGENZA")
	private Boolean flgEmergenza;

	@Column(name = "TIPO_STRUTTURA")
	private Long tipoStruttura;

	@Column(name = "ID_STRUTTURA")
	private Long idStruttura;

	@Column(name = "FLG_COND_PRES_ADULTI")
	private Boolean flgCondVerifPresenzaAdulti;

	@Column(name = "FLG_COINV_FAM")
	private Boolean flgCoinvFamiglia;

	@Column(name = "FLG_DISABILITA")
	private Boolean flgDisabilita;

	@Temporal(TemporalType.DATE)
	@Column(name = "PERIODO_INS_PIAN_DA")
	private Date periodoInsPianificazioneDa;

	@Temporal(TemporalType.DATE)
	@Column(name = "PERIODO_INS_PIAN_A")
	private Date periodoInsPianificazioneA;

	@Column(name = "FLG_INTERVENTI_DISABILI")
	private Boolean flgInterventiDisabili;

	@Column(name = "FLG_GRAVIDANZA")
	private Boolean flgGravidanza;

	@Column(name = "FLG_NEONATO")
	private Boolean flgNeonato;

	@Column(name = "FLG_AREA_PENALE")
	private Boolean flgAreaPenale;

	@Column(name = "ID_CASE_MANAGER")
	private Long idCaseManager;

	@Column(name = "FLG_PROR_RICH_MAGG")
	private Boolean flgProrRichMagg;

	@Column(name = "FLG_PROR_LIMITE_ETA")
	private Boolean flgProrLimiteEta;

	@Column(name = "FLG_ESISTE_EDU_PEDA")
	private Boolean flgEsisteEduPeda;

	@Column(name = "FLG_INVIO_SEGN_TM")
	private Boolean flgInvioSegnTM;

	@Column(name = "DIARIO_SINBA_ID")
	private Long diarioSinbaId;

	@Column(name = "COD_ROUTING")
	private String codRouting;

	@Column(name = "TIPO_MINORE")
	private Long tipoMinore;
	
	@OneToMany(mappedBy = "paiPTI", cascade = CascadeType.ALL)
	private List<CsPaiPTIFase> fasiPTI;

	@OneToMany(mappedBy = "paiPTI", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<RichiestaDisponibilitaPaiPti> richiesteDisponibilita = new ArrayList<RichiestaDisponibilitaPaiPti>();

	@OneToMany(mappedBy = "paiPTI", cascade = CascadeType.ALL)
	private List<CsPaiPtiDocumento> documenti = new ArrayList<CsPaiPtiDocumento>();

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

	public Date getDataRedazione() {
		return dataRedazione;
	}

	public void setDataRedazione(Date dataRedazione) {
		this.dataRedazione = dataRedazione;
	}

	public Boolean getFlgEmergenza() {
		return flgEmergenza;
	}

	public void setFlgEmergenza(Boolean flgEmergenza) {
		this.flgEmergenza = flgEmergenza;
	}

	public Long getTipoStruttura() {
		return tipoStruttura;
	}

	public void setTipoStruttura(Long tipoStruttura) {
		this.tipoStruttura = tipoStruttura;
	}

	public Long getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(Long idStruttura) {
		this.idStruttura = idStruttura;
	}

	public Boolean getFlgCondVerifPresenzaAdulti() {
		return flgCondVerifPresenzaAdulti;
	}

	public void setFlgCondVerifPresenzaAdulti(Boolean flgCondVerifPresenzaAdulti) {
		this.flgCondVerifPresenzaAdulti = flgCondVerifPresenzaAdulti;
	}

	public Boolean getFlgCoinvFamiglia() {
		return flgCoinvFamiglia;
	}

	public void setFlgCoinvFamiglia(Boolean flgCoinvFamiglia) {
		this.flgCoinvFamiglia = flgCoinvFamiglia;
	}

	public Boolean getFlgDisabilita() {
		return flgDisabilita;
	}

	public void setFlgDisabilita(Boolean flgDisabilita) {
		this.flgDisabilita = flgDisabilita;
	}

	public Date getPeriodoInsPianificazioneDa() {
		return periodoInsPianificazioneDa;
	}

	public void setPeriodoInsPianificazioneDa(Date periodoInsPianificazioneDa) {
		this.periodoInsPianificazioneDa = periodoInsPianificazioneDa;
	}

	public Date getPeriodoInsPianificazioneA() {
		return periodoInsPianificazioneA;
	}

	public void setPeriodoInsPianificazioneA(Date periodoInsPianificazioneA) {
		this.periodoInsPianificazioneA = periodoInsPianificazioneA;
	}

	public Boolean getFlgInterventiDisabili() {
		return flgInterventiDisabili;
	}

	public void setFlgInterventiDisabili(Boolean flgInterventiDisabili) {
		this.flgInterventiDisabili = flgInterventiDisabili;
	}

	public Boolean getFlgGravidanza() {
		return flgGravidanza;
	}

	public void setFlgGravidanza(Boolean flgGravidanza) {
		this.flgGravidanza = flgGravidanza;
	}

	public Boolean getFlgNeonato() {
		return flgNeonato;
	}

	public void setFlgNeonato(Boolean flgNeonato) {
		this.flgNeonato = flgNeonato;
	}

	public Boolean getFlgAreaPenale() {
		return flgAreaPenale;
	}

	public void setFlgAreaPenale(Boolean flgAreaPenale) {
		this.flgAreaPenale = flgAreaPenale;
	}

	public Long getIdCaseManager() {
		return idCaseManager;
	}

	public void setIdCaseManager(Long idCaseManager) {
		this.idCaseManager = idCaseManager;
	}

	public Boolean getFlgProrRichMagg() {
		return flgProrRichMagg;
	}

	public void setFlgProrRichMagg(Boolean flgProrRichMagg) {
		this.flgProrRichMagg = flgProrRichMagg;
	}

	public Boolean getFlgProrLimiteEta() {
		return flgProrLimiteEta;
	}

	public void setFlgProrLimiteEta(Boolean flgProrLimiteEta) {
		this.flgProrLimiteEta = flgProrLimiteEta;
	}

	public Boolean getFlgEsisteEduPeda() {
		return flgEsisteEduPeda;
	}

	public void setFlgEsisteEduPeda(Boolean flgEsisteEduPeda) {
		this.flgEsisteEduPeda = flgEsisteEduPeda;
	}

	public Boolean getFlgInvioSegnTM() {
		return flgInvioSegnTM;
	}

	public void setFlgInvioSegnTM(Boolean flgInvioSegnTM) {
		this.flgInvioSegnTM = flgInvioSegnTM;
	}

	public List<CsPaiPTIFase> getFasiPTI() {
		return fasiPTI;
	}

	public void setFasiPTI(List<CsPaiPTIFase> fasiPTI) {
		this.fasiPTI = fasiPTI;
	}

	public void addFasePTI(CsPaiPTIFase fase) {
		if (fasiPTI == null) {
			fasiPTI = new ArrayList<CsPaiPTIFase>();
		}
		fase.setPaiPTI(this);
		fasiPTI.add(fase);
	}

	public Long getDiarioSinbaId() {
		return diarioSinbaId;
	}

	public void setDiarioSinbaId(Long diarioSinbaId) {
		this.diarioSinbaId = diarioSinbaId;
	}

	public List<RichiestaDisponibilitaPaiPti> getRichiesteDisponibilita() {
		return richiesteDisponibilita;
	}

	public void setRichiesteDisponibilita(List<RichiestaDisponibilitaPaiPti> richiesteDisponibilita) {
		this.richiesteDisponibilita = richiesteDisponibilita;
	}

	public void addRichiestaDisponibilita(RichiestaDisponibilitaPaiPti rich) {
		if (richiesteDisponibilita == null) {
			richiesteDisponibilita = new ArrayList<RichiestaDisponibilitaPaiPti>();
		}
		rich.setPaiPTI(this);
		richiesteDisponibilita.add(rich);
	}

	public List<CsPaiPtiDocumento> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<CsPaiPtiDocumento> documenti) {
		this.documenti = documenti;
	}

	public void addDocumento(CsPaiPtiDocumento doc) {
		if (documenti == null) {
			documenti = new ArrayList<CsPaiPtiDocumento>();
		}
		doc.setPaiPTI(this);
		documenti.add(doc);
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public Long getTipoMinore() {
		return tipoMinore;
	}

	public void setTipoMinore(Long tipoMinore) {
		this.tipoMinore = tipoMinore;
	}


}
