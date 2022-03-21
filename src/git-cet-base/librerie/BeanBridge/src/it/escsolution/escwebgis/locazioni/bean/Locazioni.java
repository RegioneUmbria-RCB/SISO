/*
 * Created on 3-mag-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.escsolution.escwebgis.locazioni.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Locazioni extends EscObject implements Serializable{

	private String ufficio;
	private Integer anno;
	private String serie;
	private Integer numero;
	private String indirizzo;
	
	// aggiunti nuovo flusso locazioni 12/2008
	private Date dataRegistrazione;
	private Date dataStipula;
	private	String oggetto;
	private BigDecimal importoCanone;
	private String valutaCanone;
	private String tipoCanone;
	private Date dataInizio;
	private Date dataFine;

	
	private String chiave;
	private String tipoSoggetto;
	private String codFisc;
	private String Sesso;
	private String cittaNascita;
	private String provinciaNascita;
	private String dataNascita;
	private String cittaResidenza;
	private String provinciaResidenza;
	private String indirizzoResidenza;
	private String civicoResidenza;
	// aggiunti nuovo flusso locazioni 12/2008
	private Date dataSubentro;
	private Date dataCessione;
	//aggiunti per locazioni manuali
	private Integer sottonumero;
	private Integer progNegozio;
	private String codiceNegozio;
	
	private String progSoggetto;
	
	private String foglio;
	private String particella;
	private String subalterno;
	private String codFisProprietario;
	private String codFisInquilino;
	

	public Date getDataSubentro() {
		return dataSubentro;
	}
	public void setDataSubentro(Date dataSubentro) {
		this.dataSubentro = dataSubentro;
	}
	public Date getDataCessione() {
		return dataCessione;
	}
	public void setDataCessione(Date dataCessione) {
		this.dataCessione = dataCessione;
	}
	public Date getDataStipula() {
		return dataStipula;
	}
	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public BigDecimal getImportoCanone() {
		return importoCanone;
	}
	public void setImportoCanone(BigDecimal importoCanone) {
		this.importoCanone = importoCanone;
	}
	public Integer getAnno()
	{
		return anno;
	}
	public void setAnno(Integer anno)
	{
		this.anno = anno;
	}
	public String getIndirizzo()
	{
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo)
	{
		this.indirizzo = indirizzo;
	}
	public Integer getNumero()
	{
		return numero;
	}
	public void setNumero(Integer numero)
	{
		this.numero = numero;
	}
	public String getSerie()
	{
		return serie;
	}
	public void setSerie(String serie)
	{
		this.serie = serie;
	}
	public String getUfficio()
	{
		return ufficio;
	}
	public void setUfficio(String ufficio)
	{
		this.ufficio = ufficio;
	}
	public String getChiave()
	{
		return chiave;
	}
	public void setChiave(String chiave)
	{
		this.chiave = chiave;
	}
	public String getCittaNascita()
	{
		return cittaNascita;
	}
	public void setCittaNascita(String cittaNascita)
	{
		this.cittaNascita = cittaNascita;
	}
	public String getCittaResidenza()
	{
		return cittaResidenza;
	}
	public void setCittaResidenza(String cittaResidenza)
	{
		this.cittaResidenza = cittaResidenza;
	}
	public String getCivicoResidenza()
	{
		return civicoResidenza;
	}
	public void setCivicoResidenza(String civicoResidenza)
	{
		this.civicoResidenza = civicoResidenza;
	}
	public String getCodFisc()
	{
		return codFisc;
	}
	public void setCodFisc(String codFisc)
	{
		this.codFisc = codFisc;
	}
	public String getDataNascita()
	{
		return dataNascita;
	}
	public void setDataNascita(String dataNascita)
	{
		this.dataNascita = dataNascita;
	}
	public String getIndirizzoResidenza()
	{
		return indirizzoResidenza;
	}
	public void setIndirizzoResidenza(String indirizzoResidenza)
	{
		this.indirizzoResidenza = indirizzoResidenza;
	}
	public String getSesso()
	{
		return Sesso;
	}
	public void setSesso(String sesso)
	{
		Sesso = sesso;
	}
	public String getTipoSoggetto()
	{
		if(this.tipoSoggetto.equals("D"))
			return "PROPRIETARIO";
		else 
			return "INQUILINO";
	}
	public void setTipoSoggetto(String tipoSoggetto)
	{
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getProvinciaNascita()
	{
		return provinciaNascita;
	}
	public void setProvinciaNascita(String provinciaNascita)
	{
		this.provinciaNascita = provinciaNascita;
	}
	public String getProvinciaResidenza()
	{
		return provinciaResidenza;
	}
	public void setProvinciaResidenza(String provinciaResidenza)
	{
		this.provinciaResidenza = provinciaResidenza;
	}
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public String getValutaCanone() {
		return valutaCanone;
	}
	public void setValutaCanone(String valutaCanone) {
		this.valutaCanone = valutaCanone;
	}
	public String getTipoCanone() {
		return tipoCanone;
	}
	public void setTipoCanone(String tipoCanone) {
		this.tipoCanone = tipoCanone;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	public Integer getSottonumero() {
		return sottonumero;
	}
	public void setSottonumero(Integer sottonumero) {
		this.sottonumero = sottonumero;
	}
	public Integer getProgNegozio() {
		return progNegozio;
	}
	public void setProgNegozio(Integer progNegozio) {
		this.progNegozio = progNegozio;
	}
	public String getCodiceNegozio() {
		return codiceNegozio;
	}
	public void setCodiceNegozio(String codiceNegozio) {
		this.codiceNegozio = codiceNegozio;
	}
	public String getProgSoggetto() {
		return progSoggetto;
	}
	public void setProgSoggetto(String progSoggetto) {
		this.progSoggetto = progSoggetto;
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
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getCodFisProprietario() {
		return codFisProprietario;
	}
	public void setCodFisProprietario(String codFisProprietario) {
		this.codFisProprietario = codFisProprietario;
	}
	public String getCodFisInquilino() {
		return codFisInquilino;
	}
	public void setCodFisInquilino(String codFisInquilino) {
		this.codFisInquilino = codFisInquilino;
	}

}
