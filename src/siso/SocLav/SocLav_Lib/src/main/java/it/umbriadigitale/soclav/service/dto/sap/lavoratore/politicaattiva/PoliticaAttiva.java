package it.umbriadigitale.soclav.service.dto.sap.lavoratore.politicaattiva;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class PoliticaAttiva implements Serializable {
	
/*
 * 	<politiche_attive>
		<tipo_attivita>N00</tipo_attivita>
		<titolo_denominazione>0000156B44Q</titolo_denominazione>
		<data_proposta>2017-09-27</data_proposta>
		<data>2017-09-27</data>
		<data_fine>2017-09-27</data_fine>
		<descrizione>Convalida DID Online</descrizione>
		<titolo_progetto>05</titolo_progetto>
		<codice_ente_promotore>H501N000001</codice_ente_promotore>
		<identificativo_politica>0000156B44Q</identificativo_politica>
		<indice_profiling>0.907135570</indice_profiling>
		<identificativo_presa_in_carico>110000441DH</identificativo_presa_in_carico>
		<ultimo_evento>    
			<evento>02</evento>    
			<data_evento>2017-09-27</data_evento>
		</ultimo_evento>
	</politiche_attive>
	
	<politiche_attive>
		<tipo_attivita>RC1</tipo_attivita>
		<titolo_denominazione>Reddito di Cittadinanza</titolo_denominazione>
		<data_proposta>2019-01-21</data_proposta>
		<data>2019-04-17</data>
		<data_fine>2020-10-16</data_fine>
		<durata>18</durata>
		<tipologia_durata>M</tipologia_durata>
		<descrizione>INPS-RDC-2019-000000-R</descrizione>
		<titolo_progetto>08</titolo_progetto>
		<codice_ente_promotore>L191C000446</codice_ente_promotore>
		<identificativo_politica>110002B600C</identificativo_politica>
		<indice_profiling>0.840747612</indice_profiling>
		<ultimo_evento>    <evento>01</evento>    <data_evento>2019-01-21</data_evento>    <descrizione_evento>Proposta</descrizione_evento></ultimo_evento>
	</politiche_attive>
 * */
	private String tipo_attivita;
	private String titolo_denominazione;
	
	private Date data_proposta;
	private Date data;
	private Date data_fine;
	private String durata;
	private String tipologia_durata;
	private String descrizione;
	private String titolo_progetto;
	private String codice_ente_promotore;
	private String identificativo_politica;
	private String indice_profiling;
	private String identificativo_presa_in_carico;
	private Evento ultimo_evento;
	
	private String desTipo_attivita;
	private String desTipologia_durata;
	private String desTitolo_progetto;
	private String des_ente_promotore;
	
	public String getTipo_attivita() {
		return tipo_attivita;
	}
	public void setTipo_attivita(String tipo_attivita) {
		this.tipo_attivita = tipo_attivita;
	}
	public String getTitolo_denominazione() {
		return titolo_denominazione;
	}
	public void setTitolo_denominazione(String titolo_denominazione) {
		this.titolo_denominazione = titolo_denominazione;
	}
	public Date getData_proposta() {
		return data_proposta;
	}
	public void setData_proposta(Date data_proposta) {
		this.data_proposta = data_proposta;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getData_fine() {
		return data_fine;
	}
	public void setData_fine(Date data_fine) {
		this.data_fine = data_fine;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getTitolo_progetto() {
		return titolo_progetto;
	}
	public void setTitolo_progetto(String titolo_progetto) {
		this.titolo_progetto = titolo_progetto;
	}
	public String getCodice_ente_promotore() {
		return codice_ente_promotore;
	}
	public void setCodice_ente_promotore(String codice_ente_promotore) {
		this.codice_ente_promotore = codice_ente_promotore;
	}
	public String getIdentificativo_politica() {
		return identificativo_politica;
	}
	public void setIdentificativo_politica(String identificativo_politica) {
		this.identificativo_politica = identificativo_politica;
	}
	public String getIndice_profiling() {
		return indice_profiling;
	}
	public void setIndice_profiling(String indice_profiling) {
		this.indice_profiling = indice_profiling;
	}
	public String getIdentificativo_presa_in_carico() {
		return identificativo_presa_in_carico;
	}
	public void setIdentificativo_presa_in_carico(String identificativo_presa_in_carico) {
		this.identificativo_presa_in_carico = identificativo_presa_in_carico;
	}
	public Evento getUltimo_evento() {
		return ultimo_evento;
	}
	public void setUltimo_evento(Evento ultimo_evento) {
		this.ultimo_evento = ultimo_evento;
	}
	public String getDurata() {
		return durata;
	}
	public void setDurata(String durata) {
		this.durata = durata;
	}
	public String getTipologia_durata() {
		return tipologia_durata;
	}
	public void setTipologia_durata(String tipologia_durata) {
		this.tipologia_durata = tipologia_durata;
	}
	public String getDesTipologia_durata() {
		return desTipologia_durata;
	}
	public void setDesTipologia_durata(String desTipologia_durata) {
		this.desTipologia_durata = desTipologia_durata;
	}
	public String getDesTitolo_progetto() {
		return desTitolo_progetto;
	}
	public void setDesTitolo_progetto(String desTitolo_progetto) {
		this.desTitolo_progetto = desTitolo_progetto;
	}
	public String getDesTipo_attivita() {
		return desTipo_attivita;
	}
	public void setDesTipo_attivita(String desTipo_attivita) {
		this.desTipo_attivita = desTipo_attivita;
	}
	public String getDes_ente_promotore() {
		return des_ente_promotore;
	}
	public void setDes_ente_promotore(String des_ente_promotore) {
		this.des_ente_promotore = des_ente_promotore;
	}
	
}
