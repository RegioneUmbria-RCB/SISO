package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsFlgIntervento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ErogazioneDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Date dataRichiestaIntervento;
	private Date dataUltimaErogazione;
	private String statoUltimaErogazione;
	private String dettaglioTotaleErogazione;
	private Long diarioId;
	private String statoUltimoFlg;
	private Date dataUltimoFlg;
	private Long idIntervento = null;
	private Long idInterventoEseg = null;
	private Long idInterventoEsegMaster = null;
	private CsCTipoIntervento tipoIntervento;
	
	private CsASoggettoLAZY soggetto;
	private String cognome;
	private String nome;
	private String cf;
	
	//mk_osmosit

	private BigDecimal detailSpesa;
	private BigDecimal detailPercGest;
	private BigDecimal detailValGest;
	private BigDecimal detailCompartUtenti;
	private BigDecimal detailCompartSsn;
	private BigDecimal detailCompartAltre;
	private String detailNoteAltre;
	private String masterCognome;
	private String masterNome;
	private String masterCf;
	private String masterDescrIntervento;
	private BigDecimal masterSpesa;
	private BigDecimal masterPercGestitaEnte; 
	private BigDecimal masterValGestitaEnte; 
	private BigDecimal masterCompartUtenti; 
	private BigDecimal masterCompartSsn;
	private BigDecimal masterCompartAltre;
	private String masterNoteAltre; 
	private Long masterFFOrigineId; 
	private String masterCodFin1;
	private String tipoInterventoCustom;



	public BigDecimal getDetailSpesa() {
		return detailSpesa;
	}

	public void setDetailSpesa(BigDecimal detailSpesa) {
		this.detailSpesa = detailSpesa;
	}

	public BigDecimal getDetailPercGest() {
		return detailPercGest;
	}

	public void setDetailPercGest(BigDecimal detailPercGest) {
		this.detailPercGest = detailPercGest;
	}

	public BigDecimal getDetailValGest() {
		return detailValGest;
	}

	public void setDetailValGest(BigDecimal detailValGest) {
		this.detailValGest = detailValGest;
	}

	public BigDecimal getDetailCompartUtenti() {
		return detailCompartUtenti;
	}

	public void setDetailCompartUtenti(BigDecimal detailCompartUtenti) {
		this.detailCompartUtenti = detailCompartUtenti;
	}

	public BigDecimal getDetailCompartSsn() {
		return detailCompartSsn;
	}

	public void setDetailCompartSsn(BigDecimal detailCompartSsn) {
		this.detailCompartSsn = detailCompartSsn;
	}

	public BigDecimal getDetailCompartAltre() {
		return detailCompartAltre;
	}

	public void setDetailCompartAltre(BigDecimal detailCompartAltre) {
		this.detailCompartAltre = detailCompartAltre;
	}

	public String getDetailNoteAltre() {
		return detailNoteAltre;
	}

	public void setDetailNoteAltre(String detailNoteAltre) {
		this.detailNoteAltre = detailNoteAltre;
	}

	
	
	//mk_osmosit
	
	
	
	
	
	
	
	
	
	


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getDataRichiestaIntervento() {
		return dataRichiestaIntervento;
	}

	public Date getDataUltimaErogazione() {
		return dataUltimaErogazione;
	}

	public String getStatoUltimaErogazione() {
		return statoUltimaErogazione;
	}

	public String getDettaglioTotaleErogazione() {
		return dettaglioTotaleErogazione;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public CsASoggettoLAZY getSoggetto() {
		return soggetto;
	}

	public String getStatoUltimoFlg() {
		return statoUltimoFlg;
	}

	public Date getDataUltimoFlg() {
		return dataUltimoFlg;
	}

	public void setDataRichiestaIntervento(Date dataRichiestaIntervento) {
		this.dataRichiestaIntervento = dataRichiestaIntervento;
	}

	public void setDataUltimaErogazione(Date dataUltimaErogazione) {
		this.dataUltimaErogazione = dataUltimaErogazione;
	}

	public void setStatoUltimaErogazione(String statoUltimaErogazione) {
		this.statoUltimaErogazione = statoUltimaErogazione;
	}

	public void setDettaglioTotaleErogazione(String dettaglioTotaleErogazione) {
		this.dettaglioTotaleErogazione = dettaglioTotaleErogazione;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public void setSoggetto(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
	}

	public void setStatoUltimoFlg(String statoUltimoFlg) {
		this.statoUltimoFlg = statoUltimoFlg;
	}

	public void setDataUltimoFlg(Date dataUltimoFlg) {
		this.dataUltimoFlg = dataUltimoFlg;
	}

	public Long getIdIntervento() {
		return idIntervento;
	}

	public CsCTipoIntervento getTipoIntervento() {
		return tipoIntervento;
	}

	public void setIdIntervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}

	public void setTipoIntervento(CsCTipoIntervento tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}

	public String getCognome() {
		return cognome;
	}

	public String getNome() {
		return nome;
	}

	public String getCf() {
		return cf;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}
	
	public Long getIdInterventoEsegMaster() {
		return idInterventoEsegMaster;
	}

	public void setIdInterventoEsegMaster(Long idInterventoEsegMaster) {
		this.idInterventoEsegMaster = idInterventoEsegMaster;
	}

	public String getMasterCognome() {
		return masterCognome;
	}

	public void setMasterCognome(String masterCognome) {
		this.masterCognome = masterCognome;
	}

	public String getMasterNome() {
		return masterNome;
	}

	public void setMasterNome(String masterNome) {
		this.masterNome = masterNome;
	}

	public String getMasterCf() {
		return masterCf;
	}

	public void setMasterCf(String masterCf) {
		this.masterCf = masterCf;
	}

	public String getMasterDescrIntervento() {
		return masterDescrIntervento;
	}

	public void setMasterDescrIntervento(String masterDescrIntervento) {
		this.masterDescrIntervento = masterDescrIntervento;
	}

	public BigDecimal getMasterSpesa() {
		return masterSpesa;
	}

	public void setMasterSpesa(BigDecimal masterSpesa) {
		this.masterSpesa = masterSpesa;
	}

	public BigDecimal getMasterPercGestitaEnte() {
		return masterPercGestitaEnte;
	}

	public void setMasterPercGestitaEnte(BigDecimal masterPercGestitaEnte) {
		this.masterPercGestitaEnte = masterPercGestitaEnte;
	}

	public BigDecimal getMasterValGestitaEnte() {
		return masterValGestitaEnte;
	}

	public void setMasterValGestitaEnte(BigDecimal masterValGestitaEnte) {
		this.masterValGestitaEnte = masterValGestitaEnte;
	}

	public BigDecimal getMasterCompartUtenti() {
		return masterCompartUtenti;
	}

	public void setMasterCompartUtenti(BigDecimal masterCompartUtenti) {
		this.masterCompartUtenti = masterCompartUtenti;
	}

	public BigDecimal getMasterCompartSsn() {
		return masterCompartSsn;
	}

	public void setMasterCompartSsn(BigDecimal masterCompartSsn) {
		this.masterCompartSsn = masterCompartSsn;
	}

	public BigDecimal getMasterCompartAltre() {
		return masterCompartAltre;
	}

	public void setMasterCompartAltre(BigDecimal masterCompartAltre) {
		this.masterCompartAltre = masterCompartAltre;
	}

	public String getMasterNoteAltre() {
		return masterNoteAltre;
	}

	public void setMasterNoteAltre(String masterNoteAltre) {
		this.masterNoteAltre = masterNoteAltre;
	}

	public Long getMasterFFOrigineId() {
		return masterFFOrigineId;
	}

	public void setMasterFFOrigineId(Long masterFFOrigineId) {
		this.masterFFOrigineId = masterFFOrigineId;
	}

	public String getMasterCodFin1() {
		return masterCodFin1;
	}

	public void setMasterCodFin1(String masterCodFin1) {
		this.masterCodFin1 = masterCodFin1;
	}

	public String getTipoInterventoCustom() {
		return tipoInterventoCustom;
	}

	public void setTipoInterventoCustom(String tipoInterventoCustom) {
		this.tipoInterventoCustom = tipoInterventoCustom;
	}

	public Long getIdInterventoEseg() {
		return idInterventoEseg;
	}

	public void setIdInterventoEseg(Long idInterventoEseg) {
		this.idInterventoEseg = idInterventoEseg;
	}

	
}
