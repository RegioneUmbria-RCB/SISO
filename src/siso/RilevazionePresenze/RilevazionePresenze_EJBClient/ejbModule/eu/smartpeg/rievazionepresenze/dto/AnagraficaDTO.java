package eu.smartpeg.rievazionepresenze.dto;

import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class AnagraficaDTO implements Serializable {

		private Long id;

		private String cf;

		private String cittadinanza;

		private String cognome;

		private String nome;
		
		private String sesso;
		
		private Date dataNascita;
		
		private AmTabComuni comuneNascita;
		
		private AmTabNazioni nazioneNascita;
		
		private AmTabComuni comuneResidenza;
		
		private String indirizzoResidenza;
		
		private AmTabComuni comuneDomicilio;
		
		private String indirizzoDomicilio;
		
		private Long idArea;
		private Long idTitoloStudio;
		private Long idVulnerabilita;
		private Long idCondizioneLavorativa;
		private Long idStruttura;
		private Long idAreaStruttura;
		private String unitaAbitativa;

		public String getIndirizzoResidenza() {
			return indirizzoResidenza;
		}

		public void setIndirizzoResidenza(String indirizzoResidenza) {
			this.indirizzoResidenza = indirizzoResidenza;
		}

		public AmTabComuni getComuneDomicilio() {
			return comuneDomicilio;
		}

		public void setComuneDomicilio(AmTabComuni comuneDomicilio) {
			this.comuneDomicilio = comuneDomicilio;
		}

		public String getIndirizzoDomicilio() {
			return indirizzoDomicilio;
		}

		public void setIndirizzoDomicilio(String indirizzoDomicilio) {
			this.indirizzoDomicilio = indirizzoDomicilio;
		}
		
		public AmTabComuni getComuneNascita() {
			return comuneNascita;
		}

		public void setComuneNascita(AmTabComuni comuneNascita) {
			this.comuneNascita = comuneNascita;
		}

		public AmTabNazioni getNazioneNascita() {
			return nazioneNascita;
		}

		public void setNazioneNascita(AmTabNazioni nazioneNascita) {
			this.nazioneNascita = nazioneNascita;
		}

		public AmTabComuni getComuneResidenza() {
			return comuneResidenza;
		}

		public void setComuneResidenza(AmTabComuni comuneResidenza) {
			this.comuneResidenza = comuneResidenza;
		}


		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCf() {
			return cf;
		}

		public void setCf(String cf) {
			this.cf = cf;
		}

		public String getCittadinanza() {
			return cittadinanza;
		}

		public void setCittadinanza(String cittadinanza) {
			this.cittadinanza = cittadinanza;
		}

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

		public Date getDataNascita() {
			return dataNascita;
		}

		public void setDataNascita(Date dataNascita) {
			this.dataNascita = dataNascita;
		}

		public Long getIdTitoloStudio() {
			return idTitoloStudio;
		}

		public void setIdTitoloStudio(Long idTitoloStudio) {
			this.idTitoloStudio = idTitoloStudio;
		}

		public Long getIdVulnerabilita() {
			return idVulnerabilita;
		}

		public void setIdVulnerabilita(Long idVulnerabilita) {
			this.idVulnerabilita = idVulnerabilita;
		}

		public Long getIdCondizioneLavorativa() {
			return idCondizioneLavorativa;
		}

		public void setIdCondizioneLavorativa(Long idCondizioneLavorativa) {
			this.idCondizioneLavorativa = idCondizioneLavorativa;
		}

		public Long getIdStruttura() {
			return idStruttura;
		}

		public void setIdStruttura(Long idStruttura) {
			this.idStruttura = idStruttura;
		}

		public Long getIdAreaStruttura() {
			return idAreaStruttura;
		}

		public void setIdAreaStruttura(Long idAreaStruttura) {
			this.idAreaStruttura = idAreaStruttura;
		}

		public String getUnitaAbitativa() {
			return unitaAbitativa;
		}

		public void setUnitaAbitativa(String unitaAbitativa) {
			this.unitaAbitativa = unitaAbitativa;
		}

		public Long getIdArea() {
			return idArea;
		}

		public void setIdArea(Long idArea) {
			this.idArea = idArea;
		}
		
}
