package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;
import it.webred.rulengine.dwh.def.TipoXml;


public class SitAcquaUtenze extends TabellaDwh
{

	private Relazione  idExtUtente = new Relazione(SitAcquaUtente.class, new ChiaveEsterna());
	private String codServizio;
	private String descrCategoria;
	private String qualificaTitolare;
	private String tipologia;
	private String tipoContratto;
	private DataDwh dtUtenza;
	private String ragSocUbicazione;
	private String viaUbicazione;
	private String civicoUbicazione;
	private String capUbicazione;
	private String comuneUbicazione;
	private String tipologiaUi;
	private BigDecimal mesiFatturazione;
	private BigDecimal consumoMedio;
	private BigDecimal stacco;
	private BigDecimal giro;
	private BigDecimal fatturato;

	public Relazione getIdExtUtente()
	{
		return idExtUtente;
	}
	public void setIdExtUtente(ChiaveEsterna IdExtUtente)
	{
		Relazione r = new Relazione(SitAcquaUtente.class,IdExtUtente);
		this.idExtUtente = r;	
	}

	public String getCodServizio() {
		return codServizio;
	}

	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}

	public String getDescrCategoria() {
		return descrCategoria;
	}

	public void setDescrCategoria(String descrCategoria) {
		this.descrCategoria = descrCategoria;
	}

	public String getQualificaTitolare() {
		return qualificaTitolare;
	}

	public void setQualificaTitolare(String qualificaTitolare) {
		this.qualificaTitolare = qualificaTitolare;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getTipoContratto() {
		return tipoContratto;
	}

	public void setTipoContratto(String tipoContratto) {
		this.tipoContratto = tipoContratto;
	}

	public DataDwh getDtUtenza() {
		return dtUtenza;
	}

	public void setDtUtenza(DataDwh dtUtenza) {
		this.dtUtenza = dtUtenza;
	}

	public String getRagSocUbicazione() {
		return ragSocUbicazione;
	}

	public void setRagSocUbicazione(String ragSocUbicazione) {
		this.ragSocUbicazione = ragSocUbicazione;
	}

	public String getViaUbicazione() {
		return viaUbicazione;
	}

	public void setViaUbicazione(String viaUbicazione) {
		this.viaUbicazione = viaUbicazione;
	}

	public String getCivicoUbicazione() {
		return civicoUbicazione;
	}

	public void setCivicoUbicazione(String civicoUbicazione) {
		this.civicoUbicazione = civicoUbicazione;
	}

	public String getCapUbicazione() {
		return capUbicazione;
	}

	public void setCapUbicazione(String capUbicazione) {
		this.capUbicazione = capUbicazione;
	}

	public String getComuneUbicazione() {
		return comuneUbicazione;
	}

	public void setComuneUbicazione(String comuneUbicazione) {
		this.comuneUbicazione = comuneUbicazione;
	}

	public String getTipologiaUi() {
		return tipologiaUi;
	}

	public void setTipologiaUi(String tipologiaUi) {
		this.tipologiaUi = tipologiaUi;
	}

	public BigDecimal getMesiFatturazione() {
		return mesiFatturazione;
	}

	public void setMesiFatturazione(BigDecimal mesiFatturazione) {
		this.mesiFatturazione = mesiFatturazione;
	}

	public BigDecimal getConsumoMedio() {
		return consumoMedio;
	}

	public void setConsumoMedio(BigDecimal consumoMedio) {
		this.consumoMedio = consumoMedio;
	}

	public BigDecimal getStacco() {
		return stacco;
	}

	public void setStacco(BigDecimal stacco) {
		this.stacco = stacco;
	}

	public BigDecimal getGiro() {
		return giro;
	}

	public void setGiro(BigDecimal giro) {
		this.giro = giro;
	}

	public BigDecimal getFatturato() {
		return fatturato;
	}

	public void setFatturato(BigDecimal fatturato) {
		this.fatturato = fatturato;
	}

	@Override
	public String getValueForCtrHash()
	{
		return 	 codServizio  +
				(viaUbicazione!=null?viaUbicazione:"")+
		 (civicoUbicazione!=null?civicoUbicazione:"")+
		 (mesiFatturazione!=null?mesiFatturazione.toString():"")+
		 (consumoMedio!=null?consumoMedio.toString():"")+
		 (stacco!=null?stacco.toString():"")+
		 (giro!=null?giro.toString():"");
	}
	
}
