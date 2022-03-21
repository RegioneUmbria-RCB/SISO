package it.webred.cs.csa.ejb.dto.erogazioni;

import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.model.CsCTipoIntervento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ErogazioneMasterDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Date dataRichiestaIntervento;
	private Date dataUltimaErogazione;
	private String statoUltimaErogazione;
	private Long diarioId;
	private String statoUltimoFlg;
	private Date dataUltimoFlg;
	private Long idIntervento = null;
	private Long idInterventoEseg = null;
	private Long idInterventoEsegMaster = null;
	
	private CsCTipoIntervento tipoIntervento;
	private BigDecimal IdCategoriaSociale;
	private String descCategoriaSociale;
	private Long idTipoInterventoCustom;
	private String tipoInterventoCustom;
	//SISO-1162
	private String codicePrestazioneInps;
	private String denominazionePrestazioneInps;
	private String prestazioneInps;
	//FINE SISO-1162
	private String tipoBeneficiario;
	
	private List<SoggettoErogazioneBean> beneficiari;
	
	private String descrIntervento;
	private SpesaDTO spesa;
	private CompartecipazioneDTO compartecipazioni;
	private String lineaFinanziamento; 
	private String codFinanziamento;
	
	//SISO-748
	private Long diarioPaiId;
	
	//SISO-812
	private Long settoreSecondoLivello;
	
	
	private String settoreTitolare;
	private String settoreErogante;
	private String settoreGestore;
	private String descrizioneProgetto;
	private Boolean servizioAmbito=false;
	private Boolean stampaPOR=false;
	private String sommaUnitaMisura;
	private List<ErogazioneDettaglioDTO> listaErogazioniDettaglio;
	HashMap<Long, ErogStatoCfgDTO>  mappaStatiTipoIntervento;

	public Date getDataRichiestaIntervento() {
		return dataRichiestaIntervento;
	}

	public Date getDataUltimaErogazione() {
		return dataUltimaErogazione;
	}

	public String getStatoUltimaErogazione() {
		return statoUltimaErogazione;
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

/*	public List<SoggettoErogazioneBean> getBeneficiari() {
		return beneficiari;
	}*/

	public void setBeneficiari(List<SoggettoErogazioneBean> beneficiari) {
		this.beneficiari = beneficiari;
	}

	public BigDecimal getIdCategoriaSociale() {
		return IdCategoriaSociale;
	}

	public void setIdCategoriaSociale(BigDecimal idCategoriaSociale) {
		IdCategoriaSociale = idCategoriaSociale;
	}

	public SoggettoErogazioneBean getBeneficiarioRiferimento(){
		for(SoggettoErogazioneBean s : this.beneficiari){
			if(s.isRiferimento()) return s;
		}
		return null;
	}
	
	public List<SoggettoErogazioneBean> getBeneficiariNonRiferimento(){
		List<SoggettoErogazioneBean>  lst = new ArrayList<SoggettoErogazioneBean> ();
		for(SoggettoErogazioneBean s : this.beneficiari){
			if(!s.isRiferimento()) lst.add(s);
		}
		return lst;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}

	public String getDescCategoriaSociale() {
		return descCategoriaSociale;
	}

	public void setDescCategoriaSociale(String descCategoriaSociale) {
		this.descCategoriaSociale = descCategoriaSociale;
	}

	public Long getSettoreSecondoLivello() {
		return settoreSecondoLivello;
	}

	public void setSettoreSecondoLivello(Long settoreSecondoLivello) {
		this.settoreSecondoLivello = settoreSecondoLivello;
	}
	//SISO-1162
	public String getCodicePrestazioneInps() {
		return codicePrestazioneInps;
	}

	public void setCodicePrestazioneInps(String codicePrestazioneInps) {
		this.codicePrestazioneInps = codicePrestazioneInps;
	}

	public String getDenominazionePrestazioneInps() {
		return denominazionePrestazioneInps;
	}

	public void setDenominazionePrestazioneInps(String denominazionePrestazioneInps) {
		this.denominazionePrestazioneInps = denominazionePrestazioneInps;
	}

	public String getPrestazioneInps() {
		if (this.getCodicePrestazioneInps().equals(this.getDenominazionePrestazioneInps()))
			this.prestazioneInps =  this.getCodicePrestazioneInps();
		else
			this.prestazioneInps = this.getCodicePrestazioneInps() + " " + this.getDenominazionePrestazioneInps();
		
		return prestazioneInps;
	}

	public void setPrestazioneInps(String prestazioneInps) {
		this.prestazioneInps = prestazioneInps;
	}

	public String getSommaUnitaMisura() {
		return sommaUnitaMisura;
	}

	public void setSommaUnitaMisura(String sommaUnitaMisura) {
		this.sommaUnitaMisura = sommaUnitaMisura;
	}

	public List<ErogazioneDettaglioDTO> getListaErogazioniDettaglio() {
		return listaErogazioniDettaglio;
	}

	public void setListaErogazioniDettaglio(
			List<ErogazioneDettaglioDTO> listaErogazioniDettaglio) {
		this.listaErogazioniDettaglio = listaErogazioniDettaglio;
	}

	public List<SoggettoErogazioneBean> getBeneficiari() {
		return beneficiari;
	}
	
	public ErogazioneDettaglioDTO getLastErogazioneDettaglio(){
		if (this.listaErogazioniDettaglio != null && !this.listaErogazioniDettaglio.isEmpty() )
			return listaErogazioniDettaglio.get(0);
		return null;
	}

	public String getSettoreTitolare() {
		return settoreTitolare;
	}

	public void setSettoreTitolare(String settoreTitolare) {
		this.settoreTitolare = settoreTitolare;
	}

	public String getSettoreErogante() {
		return settoreErogante;
	}

	public void setSettoreErogante(String settoreErogante) {
		this.settoreErogante = settoreErogante;
	}

	public String getSettoreGestore() {
		return settoreGestore;
	}

	public void setSettoreGestore(String settoreGestore) {
		this.settoreGestore = settoreGestore;
	}

	public Boolean getServizioAmbito() {
		return servizioAmbito;
	}

	public void setServizioAmbito(Boolean servizioAmbito) {
		this.servizioAmbito = servizioAmbito;
	}

	public Boolean getStampaPOR() {
		return stampaPOR;
	}

	public void setStampaPOR(Boolean stampaPOR) {
		this.stampaPOR = stampaPOR;
	}

	public String getDescrizioneProgetto() {
		return descrizioneProgetto;
	}

	public void setDescrizioneProgetto(String descrizioneProgetto) {
		this.descrizioneProgetto = descrizioneProgetto;
	}

	public HashMap<Long, ErogStatoCfgDTO> getMappaStatiTipoIntervento() {
		return mappaStatiTipoIntervento;
	}

	public void setMappaStatiTipoIntervento(
			HashMap<Long, ErogStatoCfgDTO> mappaStatiTipoIntervento) {
		this.mappaStatiTipoIntervento = mappaStatiTipoIntervento;
	}
	
	
	//FINE SISO-1162
}
