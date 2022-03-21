package it.siso.isee.obj;

import java.io.Serializable;

/***
 * Elemento facoltativo che è ripetuto tante 
 * volte quanti rapporti finanziari possiede il soggetto.
 * @author franc
 *
 */
public class Rapporto implements Serializable {
	
	/***
	 *  01  Conti correnti
		 02  Rapporto di conto deposito titoli e/o obbligazioni
		 03  Conto deposito a risparmio libero/vincolato-->
		 04  Rapporto fiduciario ex legge n. 1966 del 1939
		 05  Gestione collettiva del risparmio
		 06  Gestione patrimoniale
		 07  Certificati di deposito e buoni fruttiferi
		 09  Conto  terzi individuale/globale
		 23  Prodotti finanziari emessi da imprese di assicurazione
		 99  Altro
	 */
	private String tipoRapporto;
	private String identificativo;
	
	/***
	 * CodiceFiscale dell’operatore finanziario del rapporto. Tipo Dati: unsignedInt.
	 */
	private String codiceFiscaleOperatore;
	
	private String descrizioneOperatoreFinanziario;
	//Saldo al 31 dicembre del rapporto finanziario. Tipo Dati:
	private Integer saldo3112;
	//Consistenza media del rapporto finanziario. Tipo Dati:
	private Integer consistenzaMedia;
	
	//Valore del rapporto finanziario.
	private Integer valore;
	
	//Data di inizio del rapporto finanziario. Tipo Dati: Data. Formato: AAAA-MM-GG
	private String dataInizio;
	
	//Data di fine del rapporto finanziario. Tipo Dati: Data. Formato: AAAA-MM-GG
	private String dataFine;
	
	public String getTipoRapporto() {
		return tipoRapporto;
	}
	public void setTipoRapporto(String tipoRapporto) {
		this.tipoRapporto = tipoRapporto;
	}
	public String getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public String getCodiceFiscaleOperatore() {
		return codiceFiscaleOperatore;
	}
	public void setCodiceFiscaleOperatore(String codiceFiscaleOperatore) {
		this.codiceFiscaleOperatore = codiceFiscaleOperatore;
	}
	public String getDescrizioneOperatoreFinanziario() {
		return descrizioneOperatoreFinanziario;
	}
	public void setDescrizioneOperatoreFinanziario(
			String descrizioneOperatoreFinanziario) {
		this.descrizioneOperatoreFinanziario = descrizioneOperatoreFinanziario;
	}
	public Integer getSaldo3112() {
		return saldo3112;
	}
	public void setSaldo3112(Integer saldo3112) {
		this.saldo3112 = saldo3112;
	}
	public Integer getConsistenzaMedia() {
		return consistenzaMedia;
	}
	public void setConsistenzaMedia(Integer consistenzaMedia) {
		this.consistenzaMedia = consistenzaMedia;
	}
	public Integer getValore() {
		return valore;
	}
	public void setValore(Integer valore) {
		this.valore = valore;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	
}
