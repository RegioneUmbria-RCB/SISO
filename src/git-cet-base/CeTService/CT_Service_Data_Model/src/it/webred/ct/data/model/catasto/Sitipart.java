package it.webred.ct.data.model.catasto;

import it.webred.ct.data.spatial.JGeometryType;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITIPART database table.
 * 
 */
@Entity
public class Sitipart implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitipartPK id;

	private String allegato;

	@Column(name="AREA_PART")
	private BigDecimal areaPart;

	@Column(name="COD_DIGI")
	private Long codDigi;

	@Column(name="COD_EDIF")
	private Long codEdif;

	private Long contrasto;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_AGGIORNAMENTO")
	private Date dataAggiornamento;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_LAV")
	private Date dataLav;

	@Column(name="DELTA_X_FOTO")
	private BigDecimal deltaXFoto;

	@Column(name="DELTA_Y_FOTO")
	private BigDecimal deltaYFoto;

	private String flag1;

	private String flag2;

	private String flag3;

	private Long flagcarico;

	@Column(name="ID_TRASF")
	private Long idTrasf;

	@Column(name="ID_TRASF_FINE")
	private Long idTrasfFine;

	private Long istatp;

	private Long luminosita;

	private String note;

	@Column(name="SE_ROW_ID")
	private Long seRowId;
	
	
	private JGeometryType shape;

	private Long sorgente;

	@Column(name="STATO_PROP")
	private Long statoProp;

	private String sviluppo;

	private Long tavola;

	private String utente;

	@Column(name="UTENTE_FINE")
	private String utenteFine;

	@Column(name="UTENTE_PROP")
	private String utenteProp;

	@Column(name="UTENTE_PROP_FINE")
	private String utentePropFine;

	@Column(name="VALI_PROD")
	private Long valiProd;

    public Sitipart() {
    }

	public SitipartPK getId() {
		return this.id;
	}

	public void setId(SitipartPK id) {
		this.id = id;
	}
	
	public String getAllegato() {
		return this.allegato;
	}

	public void setAllegato(String allegato) {
		this.allegato = allegato;
	}

	public BigDecimal getAreaPart() {
		return this.areaPart;
	}

	public void setAreaPart(BigDecimal areaPart) {
		this.areaPart = areaPart;
	}

	public Long getCodDigi() {
		return this.codDigi;
	}

	public void setCodDigi(Long codDigi) {
		this.codDigi = codDigi;
	}

	public Long getCodEdif() {
		return this.codEdif;
	}

	public void setCodEdif(Long codEdif) {
		this.codEdif = codEdif;
	}

	public Long getContrasto() {
		return this.contrasto;
	}

	public void setContrasto(Long contrasto) {
		this.contrasto = contrasto;
	}

	public Date getDataAggiornamento() {
		return this.dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public Date getDataLav() {
		return this.dataLav;
	}

	public void setDataLav(Date dataLav) {
		this.dataLav = dataLav;
	}

	public BigDecimal getDeltaXFoto() {
		return this.deltaXFoto;
	}

	public void setDeltaXFoto(BigDecimal deltaXFoto) {
		this.deltaXFoto = deltaXFoto;
	}

	public BigDecimal getDeltaYFoto() {
		return this.deltaYFoto;
	}

	public void setDeltaYFoto(BigDecimal deltaYFoto) {
		this.deltaYFoto = deltaYFoto;
	}

	public String getFlag1() {
		return this.flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return this.flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	public String getFlag3() {
		return this.flag3;
	}

	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	public Long getFlagcarico() {
		return this.flagcarico;
	}

	public void setFlagcarico(Long flagcarico) {
		this.flagcarico = flagcarico;
	}

	public Long getIdTrasf() {
		return this.idTrasf;
	}

	public void setIdTrasf(Long idTrasf) {
		this.idTrasf = idTrasf;
	}

	public Long getIdTrasfFine() {
		return this.idTrasfFine;
	}

	public void setIdTrasfFine(Long idTrasfFine) {
		this.idTrasfFine = idTrasfFine;
	}

	public Long getIstatp() {
		return this.istatp;
	}

	public void setIstatp(Long istatp) {
		this.istatp = istatp;
	}

	public Long getLuminosita() {
		return this.luminosita;
	}

	public void setLuminosita(Long luminosita) {
		this.luminosita = luminosita;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getSeRowId() {
		return this.seRowId;
	}

	public void setSeRowId(Long seRowId) {
		this.seRowId = seRowId;
	}

	public JGeometryType getShape() {
		return this.shape;
	}

	public void setShape(JGeometryType shape) {
		this.shape = shape;
	}

	public Long getSorgente() {
		return this.sorgente;
	}

	public void setSorgente(Long sorgente) {
		this.sorgente = sorgente;
	}

	public Long getStatoProp() {
		return this.statoProp;
	}

	public void setStatoProp(Long statoProp) {
		this.statoProp = statoProp;
	}

	public String getSviluppo() {
		return this.sviluppo;
	}

	public void setSviluppo(String sviluppo) {
		this.sviluppo = sviluppo;
	}

	public Long getTavola() {
		return this.tavola;
	}

	public void setTavola(Long tavola) {
		this.tavola = tavola;
	}

	public String getUtente() {
		return this.utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getUtenteFine() {
		return this.utenteFine;
	}

	public void setUtenteFine(String utenteFine) {
		this.utenteFine = utenteFine;
	}

	public String getUtenteProp() {
		return this.utenteProp;
	}

	public void setUtenteProp(String utenteProp) {
		this.utenteProp = utenteProp;
	}

	public String getUtentePropFine() {
		return this.utentePropFine;
	}

	public void setUtentePropFine(String utentePropFine) {
		this.utentePropFine = utentePropFine;
	}

	public Long getValiProd() {
		return this.valiProd;
	}

	public void setValiProd(Long valiProd) {
		this.valiProd = valiProd;
	}

}