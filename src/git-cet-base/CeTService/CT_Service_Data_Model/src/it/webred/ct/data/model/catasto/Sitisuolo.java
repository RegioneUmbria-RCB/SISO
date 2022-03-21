package it.webred.ct.data.model.catasto;

import it.webred.ct.data.spatial.JGeometryType;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITISUOLO database table.
 * 
 */
@Entity
public class Sitisuolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitisuoloPK id;

	private String allegato;

	@Column(name="ANNO_FOTO")
	private Long annoFoto;

	@Column(name="AREA_COLT")
	private BigDecimal areaColt;

	private Long campagna;

	@Column(name="COD_COLTURA")
	private Long codColtura;

	@Column(name="COD_UTIL")
	private Long codUtil;

	@Column(name="COD_VARIETA")
	private Long codVarieta;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_AGGIORNAMENTO")
	private Date dataAggiornamento;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_LAV")
	private Date dataLav;

	private Long flagcarico;

	private Long gruppo;

	@Column(name="ID_TRASF")
	private Long idTrasf;

	@Column(name="ID_TRASF_FINE")
	private Long idTrasfFine;

	private Long istatp;

	@Column(name="MESE_FOTO")
	private Long meseFoto;

	@Column(name="SE_ROW_ID")
	private Long seRowId;
	
	private JGeometryType shape;

	private Long sorgente;

	@Column(name="STATO_COLT")
	private Long statoColt;

	@Column(name="STATO_PROP")
	private Long statoProp;

	private String sviluppo;

	private BigDecimal tara;

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

    public Sitisuolo() {
    }

	public SitisuoloPK getId() {
		return this.id;
	}

	public void setId(SitisuoloPK id) {
		this.id = id;
	}
	
	public String getAllegato() {
		return this.allegato;
	}

	public void setAllegato(String allegato) {
		this.allegato = allegato;
	}

	public Long getAnnoFoto() {
		return this.annoFoto;
	}

	public void setAnnoFoto(Long annoFoto) {
		this.annoFoto = annoFoto;
	}

	public BigDecimal getAreaColt() {
		return this.areaColt;
	}

	public void setAreaColt(BigDecimal areaColt) {
		this.areaColt = areaColt;
	}

	public Long getCampagna() {
		return this.campagna;
	}

	public void setCampagna(Long campagna) {
		this.campagna = campagna;
	}

	public Long getCodColtura() {
		return this.codColtura;
	}

	public void setCodColtura(Long codColtura) {
		this.codColtura = codColtura;
	}

	public Long getCodUtil() {
		return this.codUtil;
	}

	public void setCodUtil(Long codUtil) {
		this.codUtil = codUtil;
	}

	public Long getCodVarieta() {
		return this.codVarieta;
	}

	public void setCodVarieta(Long codVarieta) {
		this.codVarieta = codVarieta;
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

	public Long getFlagcarico() {
		return this.flagcarico;
	}

	public void setFlagcarico(Long flagcarico) {
		this.flagcarico = flagcarico;
	}

	public Long getGruppo() {
		return this.gruppo;
	}

	public void setGruppo(Long gruppo) {
		this.gruppo = gruppo;
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

	public Long getMeseFoto() {
		return this.meseFoto;
	}

	public void setMeseFoto(Long meseFoto) {
		this.meseFoto = meseFoto;
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

	public Long getStatoColt() {
		return this.statoColt;
	}

	public void setStatoColt(Long statoColt) {
		this.statoColt = statoColt;
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

	public BigDecimal getTara() {
		return this.tara;
	}

	public void setTara(BigDecimal tara) {
		this.tara = tara;
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