package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * SISO-538
 * The persistent class for the V_EROG_EXPORT_HELP database table.
 * 
 */
@Entity
@Table(name="V_EROG_EXPORT_HELP") 
@NamedQueries({
    @NamedQuery  (name="VErogExportHelp.findAll",
                	query="SELECT v FROM VErogExportHelp v"),
    @NamedQuery(name="VErogExportHelp.findByIds",
                query="SELECT v FROM VErogExportHelp v where v.id in (:ids)"),
})  
public class VErogExportHelp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private BigDecimal id;
	
	
	@Column(name="CATEGORIA_SOCIALE_ID")
	private BigDecimal categoriaSocialeId;

	@Column(name="CODICE_FIN1")
	private String codiceFin1;

	@Column(name="COMPART_ALTRE")
	private BigDecimal compartAltre;

	@Column(name="COMPART_SSN")
	private BigDecimal compartSsn;

	@Column(name="COMPART_SSN_DETTAGLIO")
	private BigDecimal compartSsnDettaglio;

	@Column(name="COMPART_UTENTI")
	private BigDecimal compartUtenti;

	@Column(name="COMPART_UTENTI_DETTAGLIO")
	private BigDecimal compartUtentiDettaglio;

	@Column(name="DESC_INTERVENTO_ESEG")
	private String descInterventoEseg;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="FF_ORIGINE_ID")
	private BigDecimal ffOrigineId;

	@Column(name="FF_PROGETTO_DESCRIZIONE")
	private String ffProgettoDescrizione;

	@Column(name="FLAG_CARATTERE_PRESTAZIONE")
	private String flagCaratterePrestazione;

	@Column(name="FLAG_COMPART_CALC")
	private BigDecimal flagCompartCalc;

	@Column(name="FLAG_EROGAZIONE_CHIUSA")
	private String flagErogazioneChiusa;

	@Column(name="FLAG_SPESA_CALC")
	private BigDecimal flagSpesaCalc;

	@Column(name="INTERVENTO_ID")
	private BigDecimal interventoId;

	@Temporal(TemporalType.DATE)
	@Column(name="MAX_DATA_EROGAZIONE")
	private Date maxDataErogazione;

	@Temporal(TemporalType.DATE)
	@Column(name="MAX_DATA_EROGAZIONE_A")
	private Date maxDataErogazioneA;

	@Temporal(TemporalType.DATE)
	@Column(name="MIN_DATA_EROGAZIONE")
	private Date minDataErogazione;

	@Temporal(TemporalType.DATE)
	@Column(name="MIN_DATA_EROGAZIONE_A")
	private Date minDataErogazioneA;

	@Column(name="N_RIGHE_EROGATE_TIPOE")
	private BigDecimal nRigheErogateTipoe;

	@Column(name="NOTE_ALTRE_COMPART")
	private String noteAltreCompart;

	@Column(name="OPERATORE_SETTORE_ID")
	private BigDecimal operatoreSettoreId;

	@Column(name="PERC_GESTITA_ENTE")
	private BigDecimal percGestitaEnte;

	@Column(name="PROT_DOM_PREST")
	private String protDomPrest;

	@Column(name="QUOTA_ID")
	private BigDecimal quotaId;

	@Column(name="SETTORE_EROGANTE_ID")
	private BigDecimal settoreEroganteId;

	@Column(name="SETTORE_TITOLARE_ID")
	private BigDecimal settoreTitolareId;

	private BigDecimal spesa;

	@Column(name="SPESA_DETTAGLIO")
	private BigDecimal spesaDettaglio;

	@Column(name="TIPO_BENEFICIARIO")
	private String tipoBeneficiario;

	@Column(name="TIPO_EXPORT")
	private String tipoExport;

	@Column(name="TIPO_INTERVENTO_CUSTOM_ID")
	private BigDecimal tipoInterventoCustomId;

	@Column(name="TIPO_INTERVENTO_ID")
	private BigDecimal tipoInterventoId;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	@Column(name="VALORE_GESTITA_ENTE")
	private BigDecimal valoreGestitaEnte;

	@Column(name="VALORE_GESTITA_ENTE_CALC")
	private BigDecimal valoreGestitaEnteCalc;

	@Column(name="COD_PRESTAZIONE")
	private String codPrestazione;
	
	public VErogExportHelp() {
	}

	public BigDecimal getCategoriaSocialeId() {
		return this.categoriaSocialeId;
	}

	public void setCategoriaSocialeId(BigDecimal categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}

	public String getCodiceFin1() {
		return this.codiceFin1;
	}

	public void setCodiceFin1(String codiceFin1) {
		this.codiceFin1 = codiceFin1;
	}

	public BigDecimal getCompartAltre() {
		return this.compartAltre;
	}

	public void setCompartAltre(BigDecimal compartAltre) {
		this.compartAltre = compartAltre;
	}

	public BigDecimal getCompartSsn() {
		return this.compartSsn;
	}

	public void setCompartSsn(BigDecimal compartSsn) {
		this.compartSsn = compartSsn;
	}

	public BigDecimal getCompartSsnDettaglio() {
		return this.compartSsnDettaglio;
	}

	public void setCompartSsnDettaglio(BigDecimal compartSsnDettaglio) {
		this.compartSsnDettaglio = compartSsnDettaglio;
	}

	public BigDecimal getCompartUtenti() {
		return this.compartUtenti;
	}

	public void setCompartUtenti(BigDecimal compartUtenti) {
		this.compartUtenti = compartUtenti;
	}

	public BigDecimal getCompartUtentiDettaglio() {
		return this.compartUtentiDettaglio;
	}

	public void setCompartUtentiDettaglio(BigDecimal compartUtentiDettaglio) {
		this.compartUtentiDettaglio = compartUtentiDettaglio;
	}

	public String getDescInterventoEseg() {
		return this.descInterventoEseg;
	}

	public void setDescInterventoEseg(String descInterventoEseg) {
		this.descInterventoEseg = descInterventoEseg;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public BigDecimal getFfOrigineId() {
		return this.ffOrigineId;
	}

	public void setFfOrigineId(BigDecimal ffOrigineId) {
		this.ffOrigineId = ffOrigineId;
	}

	public String getFfProgettoDescrizione() {
		return this.ffProgettoDescrizione;
	}

	public void setFfProgettoDescrizione(String ffProgettoDescrizione) {
		this.ffProgettoDescrizione = ffProgettoDescrizione;
	}

	public String getFlagCaratterePrestazione() {
		return this.flagCaratterePrestazione;
	}

	public void setFlagCaratterePrestazione(String flagCaratterePrestazione) {
		this.flagCaratterePrestazione = flagCaratterePrestazione;
	}

	public BigDecimal getFlagCompartCalc() {
		return this.flagCompartCalc;
	}

	public void setFlagCompartCalc(BigDecimal flagCompartCalc) {
		this.flagCompartCalc = flagCompartCalc;
	}

	public String getFlagErogazioneChiusa() {
		return this.flagErogazioneChiusa;
	}

	public void setFlagErogazioneChiusa(String flagErogazioneChiusa) {
		this.flagErogazioneChiusa = flagErogazioneChiusa;
	}

	public BigDecimal getFlagSpesaCalc() {
		return this.flagSpesaCalc;
	}

	public void setFlagSpesaCalc(BigDecimal flagSpesaCalc) {
		this.flagSpesaCalc = flagSpesaCalc;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(BigDecimal interventoId) {
		this.interventoId = interventoId;
	}

	public Date getMaxDataErogazione() {
		return this.maxDataErogazione;
	}

	public void setMaxDataErogazione(Date maxDataErogazione) {
		this.maxDataErogazione = maxDataErogazione;
	}

	public Date getMaxDataErogazioneA() {
		return this.maxDataErogazioneA;
	}

	public void setMaxDataErogazioneA(Date maxDataErogazioneA) {
		this.maxDataErogazioneA = maxDataErogazioneA;
	}

	public Date getMinDataErogazione() {
		return this.minDataErogazione;
	}

	public void setMinDataErogazione(Date minDataErogazione) {
		this.minDataErogazione = minDataErogazione;
	}

	public Date getMinDataErogazioneA() {
		return this.minDataErogazioneA;
	}

	public void setMinDataErogazioneA(Date minDataErogazioneA) {
		this.minDataErogazioneA = minDataErogazioneA;
	}

	public BigDecimal getNRigheErogateTipoe() {
		return this.nRigheErogateTipoe;
	}

	public void setNRigheErogateTipoe(BigDecimal nRigheErogateTipoe) {
		this.nRigheErogateTipoe = nRigheErogateTipoe;
	}

	public String getNoteAltreCompart() {
		return this.noteAltreCompart;
	}

	public void setNoteAltreCompart(String noteAltreCompart) {
		this.noteAltreCompart = noteAltreCompart;
	}

	public BigDecimal getOperatoreSettoreId() {
		return this.operatoreSettoreId;
	}

	public void setOperatoreSettoreId(BigDecimal operatoreSettoreId) {
		this.operatoreSettoreId = operatoreSettoreId;
	}

	public BigDecimal getPercGestitaEnte() {
		return this.percGestitaEnte;
	}

	public void setPercGestitaEnte(BigDecimal percGestitaEnte) {
		this.percGestitaEnte = percGestitaEnte;
	}

	public String getProtDomPrest() {
		return this.protDomPrest;
	}

	public void setProtDomPrest(String protDomPrest) {
		this.protDomPrest = protDomPrest;
	}

	public BigDecimal getQuotaId() {
		return this.quotaId;
	}

	public void setQuotaId(BigDecimal quotaId) {
		this.quotaId = quotaId;
	}

	public BigDecimal getSettoreEroganteId() {
		return this.settoreEroganteId;
	}

	public void setSettoreEroganteId(BigDecimal settoreEroganteId) {
		this.settoreEroganteId = settoreEroganteId;
	}

	public BigDecimal getSettoreTitolareId() {
		return this.settoreTitolareId;
	}

	public void setSettoreTitolareId(BigDecimal settoreTitolareId) {
		this.settoreTitolareId = settoreTitolareId;
	}

	public BigDecimal getSpesa() {
		return this.spesa;
	}

	public void setSpesa(BigDecimal spesa) {
		this.spesa = spesa;
	}

	public BigDecimal getSpesaDettaglio() {
		return this.spesaDettaglio;
	}

	public void setSpesaDettaglio(BigDecimal spesaDettaglio) {
		this.spesaDettaglio = spesaDettaglio;
	}

	public String getTipoBeneficiario() {
		return this.tipoBeneficiario;
	}

	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	public String getTipoExport() {
		return this.tipoExport;
	}

	public void setTipoExport(String tipoExport) {
		this.tipoExport = tipoExport;
	}

	public BigDecimal getTipoInterventoCustomId() {
		return this.tipoInterventoCustomId;
	}

	public void setTipoInterventoCustomId(BigDecimal tipoInterventoCustomId) {
		this.tipoInterventoCustomId = tipoInterventoCustomId;
	}

	public BigDecimal getTipoInterventoId() {
		return this.tipoInterventoId;
	}

	public void setTipoInterventoId(BigDecimal tipoInterventoId) {
		this.tipoInterventoId = tipoInterventoId;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public BigDecimal getValoreGestitaEnte() {
		return this.valoreGestitaEnte;
	}

	public void setValoreGestitaEnte(BigDecimal valoreGestitaEnte) {
		this.valoreGestitaEnte = valoreGestitaEnte;
	}

	public BigDecimal getValoreGestitaEnteCalc() {
		return this.valoreGestitaEnteCalc;
	}

	public void setValoreGestitaEnteCalc(BigDecimal valoreGestitaEnteCalc) {
		this.valoreGestitaEnteCalc = valoreGestitaEnteCalc;
	}
    //SISO-1162
	public String getCodPrestazione() {
		return codPrestazione;
	}

	public void setCodPrestazione(String codPrestazione) {
		this.codPrestazione = codPrestazione;
	}
	//FINE SISO-1162
}