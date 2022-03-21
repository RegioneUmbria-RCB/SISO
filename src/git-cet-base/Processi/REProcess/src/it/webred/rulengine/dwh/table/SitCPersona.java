package it.webred.rulengine.dwh.table;

import java.sql.Timestamp;

import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;



public class SitCPersona extends TabellaDwhMultiProv
{

	private String tipoSoggetto;
	private String tipoPersona;
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private String denominazione;

	private String titolo   ;
	private  DataDwh dataNascita ;
	private String comuneNascita ;
	private String provNascita  ;
	private String indirizzo ;
	private String cap ;
	private String comuneResidenza ;
	private String provResidenza ;
	private String telefono ;
	private String fax ;
	private String email ;
	private String piva ;
	private String indirizzoStudio ;
	private String capStudio ;
	private String comuneStudio ;
	private String provStudio ;
	private String albo ;
	private String ragsocDitta ;
	private String cfDitta ;
	private String piDitta ;
	private String indirizzoDitta ;
	private String capDitta ;
	private String comuneDitta ;
	private String provDitta ;
	private String qualita;	
	private String civico;	
	private  DataDwh dataIniRes ;

	
	

	
	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getValueForCtrHash() {
		
		return this.tipoSoggetto+
		this.tipoPersona+
		this.codiceFiscale+
		this.cognome+
		this.nome+
		this.denominazione+
		getProvenienza() +
		this.titolo   +
		this.dataNascita.getDataFormattata() +
		this.comuneNascita +
		this.provNascita  +
		this.indirizzo +
		this.cap +
		this.comuneResidenza +
		this.provResidenza +
		this.telefono +
		this.fax +
		this.email +
		this.piva +
		this.indirizzoStudio +
		this.capStudio +
		this.comuneStudio +
		this.provStudio +
		this.albo +
		this.ragsocDitta +
		this.cfDitta +
		this.piDitta +
		this.indirizzoDitta +
		this.capDitta +
		this.comuneDitta +
		this.provDitta +
		this.qualita+
		this.civico+
		this.dataIniRes.getDataFormattata() ;

	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}


	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public DataDwh getDataNascita ()
	{
		return dataNascita;
	}







	public DataDwh getDataIniRes() {
		return dataIniRes;
	}

	public void setDataIniRes(DataDwh dataIniRes) {
		this.dataIniRes = dataIniRes;
	}

	public void setDataNascita(DataDwh dataNascita )
	{
		this.dataNascita = dataNascita;
	}
	


	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getProvNascita() {
		return provNascita;
	}

	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getProvResidenza() {
		return provResidenza;
	}

	public void setProvResidenza(String provResidenza) {
		this.provResidenza = provResidenza;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getIndirizzoStudio() {
		return indirizzoStudio;
	}

	public void setIndirizzoStudio(String indirizzoStudio) {
		this.indirizzoStudio = indirizzoStudio;
	}

	public String getCapStudio() {
		return capStudio;
	}

	public void setCapStudio(String capStudio) {
		this.capStudio = capStudio;
	}

	public String getComuneStudio() {
		return comuneStudio;
	}

	public void setComuneStudio(String comuneStudio) {
		this.comuneStudio = comuneStudio;
	}

	public String getProvStudio() {
		return provStudio;
	}

	public void setProvStudio(String provStudio) {
		this.provStudio = provStudio;
	}

	public String getAlbo() {
		return albo;
	}

	public void setAlbo(String albo) {
		this.albo = albo;
	}

	public String getRagsocDitta() {
		return ragsocDitta;
	}

	public void setRagsocDitta(String ragsocDitta) {
		this.ragsocDitta = ragsocDitta;
	}

	public String getCfDitta() {
		return cfDitta;
	}

	public void setCfDitta(String cfDitta) {
		this.cfDitta = cfDitta;
	}

	public String getPiDitta() {
		return piDitta;
	}

	public void setPiDitta(String piDitta) {
		this.piDitta = piDitta;
	}

	public String getIndirizzoDitta() {
		return indirizzoDitta;
	}

	public void setIndirizzoDitta(String indirizzoDitta) {
		this.indirizzoDitta = indirizzoDitta;
	}

	public String getCapDitta() {
		return capDitta;
	}

	public void setCapDitta(String capDitta) {
		this.capDitta = capDitta;
	}

	public String getComuneDitta() {
		return comuneDitta;
	}

	public void setComuneDitta(String comuneDitta) {
		this.comuneDitta = comuneDitta;
	}

	public String getProvDitta() {
		return provDitta;
	}

	public void setProvDitta(String provDitta) {
		this.provDitta = provDitta;
	}

	public String getQualita() {
		return qualita;
	}

	public void setQualita(String qualita) {
		this.qualita = qualita;
	}


	
	


}
