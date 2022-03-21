package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.util.Date;

import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.TipoXml;


public class SitEnelUtenza extends TabellaDwh
{

	private String codiceFiscaleErogante  ;
	private String codiceUtenza	 ;
	private String codiceMerceologico ;
	private String annoRiferimentoDati;
	private DataDwh dataPrimaAttivazione	 ;
	private String tipoUtenza	 ;
	private String comuneAmmUbicazione	 ;
	private String provinciaAmmUbicazione	 ;
	private String capUbicazione;	
	
	private String comuneCatastale;
	private String codiceComuneCatastale;
	private String indirizzoUbicazione;
	private String tipoUnita;
	private String sezione;
	private String foglio;
	private String particella;
	private String estensioneParticella;
	private String tipoParticella;
	private String subalterno;
	private String codiceAssenzaCatasto;
	private BigDecimal mesiFatturazione;
	private BigDecimal kwhFatturati;
	private String segnoImporto;
	private BigDecimal spesaAnnua;
	private String tipologiaFornitura ;
	private Relazione idExtEnelUtente = new Relazione(SitEnelUtente.class,new ChiaveEsterna());

	
	@Override
	public String getValueForCtrHash() {
		
		
			return 	 codiceFiscaleErogante+ codiceUtenza + 
			codiceMerceologico + annoRiferimentoDati +
			dataPrimaAttivazione.getDataFormattata() +
			tipoUtenza + comuneAmmUbicazione + provinciaAmmUbicazione +
			capUbicazione + comuneCatastale +
			codiceComuneCatastale+indirizzoUbicazione+tipoUnita+
			sezione+foglio+particella+estensioneParticella+tipoParticella+
			subalterno+codiceAssenzaCatasto+mesiFatturazione+kwhFatturati+
			segnoImporto+spesaAnnua+tipologiaFornitura+idExtEnelUtente;
	}
	
	
	public String getCodiceComuneCatastale() {
		return codiceComuneCatastale;
	}


	public void setCodiceComuneCatastale(String codiceComuneCatastale) {
		this.codiceComuneCatastale = codiceComuneCatastale;
	}


	public Relazione getIdExtEnelUtente() {
		return idExtEnelUtente;
	}


	public void setIdExtEnelUtente(ChiaveEsterna IdExtEnelUtente)
	{
		Relazione r = new Relazione(SitEnelUtente.class,IdExtEnelUtente);
		this.idExtEnelUtente = r;	
	}

	
	public String getCodiceFiscaleErogante() {
		return codiceFiscaleErogante;
	}



	public void setCodiceFiscaleErogante(String codiceFiscaleErogante) {
		this.codiceFiscaleErogante = codiceFiscaleErogante;
	}



	public String getCodiceUtenza() {
		return codiceUtenza;
	}



	public void setCodiceUtenza(String codiceUtenza) {
		this.codiceUtenza = codiceUtenza;
	}



	public String getCodiceMerceologico() {
		return codiceMerceologico;
	}



	public void setCodiceMerceologico(String codiceMerceologico) {
		this.codiceMerceologico = codiceMerceologico;
	}



	public String getAnnoRiferimentoDati() {
		return annoRiferimentoDati;
	}



	public void setAnnoRiferimentoDati(String annoRiferimentoDati) {
		this.annoRiferimentoDati = annoRiferimentoDati;
	}



	public DataDwh getDataPrimaAttivazione() {
		return dataPrimaAttivazione;
	}



	public void setDataPrimaAttivazione(DataDwh dataPrimaAttivazione) {
		this.dataPrimaAttivazione = dataPrimaAttivazione;
	}



	public String getTipoUtenza() {
		return tipoUtenza;
	}



	public void setTipoUtenza(String tipoUtenza) {
		this.tipoUtenza = tipoUtenza;
	}



	public String getComuneAmmUbicazione() {
		return comuneAmmUbicazione;
	}



	public void setComuneAmmUbicazione(String comuneAmmUbicazione) {
		this.comuneAmmUbicazione = comuneAmmUbicazione;
	}



	public String getProvinciaAmmUbicazione() {
		return provinciaAmmUbicazione;
	}



	public void setProvinciaAmmUbicazione(String provinciaAmmUbicazione) {
		this.provinciaAmmUbicazione = provinciaAmmUbicazione;
	}



	public String getCapUbicazione() {
		return capUbicazione;
	}



	public void setCapUbicazione(String capUbicazione) {
		this.capUbicazione = capUbicazione;
	}



	public String getComuneCatastale() {
		return comuneCatastale;
	}



	public void setComuneCatastale(String comuneCatastale) {
		this.comuneCatastale = comuneCatastale;
	}






	public String getIndirizzoUbicazione() {
		return indirizzoUbicazione;
	}



	public void setIndirizzoUbicazione(String indirizzoUbicazione) {
		this.indirizzoUbicazione = indirizzoUbicazione;
	}



	public String getTipoUnita() {
		return tipoUnita;
	}



	public void setTipoUnita(String tipoUnita) {
		this.tipoUnita = tipoUnita;
	}



	public String getSezione() {
		return sezione;
	}



	public void setSezione(String sezione) {
		this.sezione = sezione;
	}



	public String getFoglio() {
		return foglio;
	}



	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}



	public String getParticella() {
		return particella;
	}



	public void setParticella(String particella) {
		this.particella = particella;
	}



	public String getEstensioneParticella() {
		return estensioneParticella;
	}



	public void setEstensioneParticella(String estensioneParticella) {
		this.estensioneParticella = estensioneParticella;
	}



	public String getTipoParticella() {
		return tipoParticella;
	}



	public void setTipoParticella(String tipoParticella) {
		this.tipoParticella = tipoParticella;
	}



	public String getSubalterno() {
		return subalterno;
	}



	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}



	public String getCodiceAssenzaCatasto() {
		return codiceAssenzaCatasto;
	}



	public void setCodiceAssenzaCatasto(String codiceAssenzaCatasto) {
		this.codiceAssenzaCatasto = codiceAssenzaCatasto;
	}









	public BigDecimal getMesiFatturazione() {
		return mesiFatturazione;
	}


	public void setMesiFatturazione(BigDecimal mesiFatturazione) {
		this.mesiFatturazione = mesiFatturazione;
	}


	public BigDecimal getKwhFatturati() {
		return kwhFatturati;
	}



	public void setKwhFatturati(BigDecimal kwhFatturati) {
		this.kwhFatturati = kwhFatturati;
	}



	public String getSegnoImporto() {
		return segnoImporto;
	}



	public void setSegnoImporto(String segnoImporto) {
		this.segnoImporto = segnoImporto;
	}



	public BigDecimal getSpesaAnnua() {
		return spesaAnnua;
	}



	public void setSpesaAnnua(BigDecimal spesaAnnua) {
		this.spesaAnnua = spesaAnnua;
	}



	public String getTipologiaFornitura() {
		return tipologiaFornitura;
	}



	public void setTipologiaFornitura(String tipologiaFornitura) {
		this.tipologiaFornitura = tipologiaFornitura;
	}



	
	
	
	








}
