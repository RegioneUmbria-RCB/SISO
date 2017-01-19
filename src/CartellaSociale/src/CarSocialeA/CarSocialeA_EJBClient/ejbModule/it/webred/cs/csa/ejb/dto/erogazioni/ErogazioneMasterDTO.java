package it.webred.cs.csa.ejb.dto.erogazioni;

import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoIntervento;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ErogazioneMasterDTO implements Serializable {
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
	private Long idTipoInterventoCustom;
	private String tipoInterventoCustom;
	
	private String tipoBeneficiario;
	
	private List<SoggettoErogazioneBean> beneficiari;
	
	private String descrIntervento;
	private SpesaDTO spesa;
	private CompartecipazioneDTO compartecipazioni;
	private String lineaFinanziamento; 
	private String codFinanziamento;
	

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

	public Long getIdInterventoEsegMaster() {
		return idInterventoEsegMaster;
	}

	public void setIdInterventoEsegMaster(Long idInterventoEsegMaster) {
		this.idInterventoEsegMaster = idInterventoEsegMaster;
	}

	public Long getIdInterventoEseg() {
		return idInterventoEseg;
	}

	public String getDescrIntervento() {
		return descrIntervento;
	}

	public SpesaDTO getSpesa() {
		return spesa;
	}

	public CompartecipazioneDTO getCompartecipazioni() {
		return compartecipazioni;
	}

	public String getLineaFinanziamento() {
		return lineaFinanziamento;
	}

	public String getCodFinanziamento() {
		return codFinanziamento;
	}

	public String getTipoInterventoCustom() {
		return tipoInterventoCustom;
	}

	public void setIdInterventoEseg(Long idInterventoEseg) {
		this.idInterventoEseg = idInterventoEseg;
	}

	public void setDescrIntervento(String descrIntervento) {
		this.descrIntervento = descrIntervento;
	}

	public void setSpesa(SpesaDTO spesa) {
		this.spesa = spesa;
	}

	public void setCompartecipazioni(CompartecipazioneDTO compartecipazioni) {
		this.compartecipazioni = compartecipazioni;
	}

	public void setLineaFinanziamento(String lineaFinanziamento) {
		this.lineaFinanziamento = lineaFinanziamento;
	}

	public void setCodFinanziamento(String codFinanziamento) {
		this.codFinanziamento = codFinanziamento;
	}

	public void setTipoInterventoCustom(String tipoInterventoCustom) {
		this.tipoInterventoCustom = tipoInterventoCustom;
	}

	public Long getIdTipoInterventoCustom() {
		return idTipoInterventoCustom;
	}

	public void setIdTipoInterventoCustom(Long idTipoInterventoCustom) {
		this.idTipoInterventoCustom = idTipoInterventoCustom;
	}

	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	public List<SoggettoErogazioneBean> getBeneficiari() {
		return beneficiari;
	}

	public void setBeneficiari(List<SoggettoErogazioneBean> beneficiari) {
		this.beneficiari = beneficiari;
	}

	public SoggettoErogazioneBean getBeneficiarioRiferimento(){
		for(SoggettoErogazioneBean s : this.beneficiari){
			if(s.isRiferimento()) return s;
		}
		return null;
	}
}
