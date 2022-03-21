package it.webred.rulengine.dwh.table;

import java.util.Date;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.TipoXml;


public class SitAcquaUtente extends TabellaDwh
{

	private String cognome;
	private String nome;
	private String sesso;
	private DataDwh dtNascita;
	private String comuneNascita;
	private String prNascita;
	private String codFiscale;
	private String denominazione;
	private String partIva;
	private String viaResidenza;
	private String civicoResidenza;
	private String capResidenza;
	private String comuneResidenza;
	private String prResidenza;
	private String telefono;
	private String faxEmail;
	
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public DataDwh getDtNascita() {
		return dtNascita;
	}

	public void setDtNascita(DataDwh dtNascita) {
		this.dtNascita = dtNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getPrNascita() {
		return prNascita;
	}

	public void setPrNascita(String prNascita) {
		this.prNascita = prNascita;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public String getCapResidenza() {
		return capResidenza;
	}

	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getPartIva() {
		return partIva;
	}

	public void setPartIva(String partIva) {
		this.partIva = partIva;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}

	public String getCivicoResidenza() {
		return civicoResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getPrResidenza() {
		return prResidenza;
	}

	public void setPrResidenza(String prResidenza) {
		this.prResidenza = prResidenza;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFaxEmail() {
		return faxEmail;
	}

	public void setFaxEmail(String faxEmail) {
		this.faxEmail = faxEmail;
	}

	@Override
	public String getValueForCtrHash()
	{
		return 	 (cognome!=null?cognome:"") +
				(nome!=null?nome:"")+
		 (codFiscale!=null?codFiscale:"")+
		 (denominazione!=null?denominazione:"")+
		 (partIva!=null?partIva:"")+
		 (viaResidenza!=null?viaResidenza:"")+
		 (civicoResidenza!=null?civicoResidenza:"");
	}
	
}
