package it.escsolution.escwebgis.toponomastica.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;

public class Civico extends EscObject implements Serializable {


		private String chiave;
		private String comune;
		private String codCivico;
		private String numero;
		private String lettera;
		private String destinazioneUso;
		private String descrCivico;
		private String sedime;
		private String strada;
		private String particella;
		private String foglio;
		private String accesso;
		private String accesibilita;
		private String sezCensimento;
		private String dataAttivazione;
		private String numeroProcedimento;
		private String exCivico;
		private String localita;
		private String serie;
		private String note; 
		private String pk_sequ_civico;
		private String cod_nazionale;
		
		private String latitudine;
		private String longitudine;
		
		private Integer idImm;
		private Integer progressivo;
		private Integer seq;
		private String codiFiscLuna;
		private String sezione;
		
		
		public Civico(){
			
			chiave="";
			codCivico="";
			comune="";
			numero="";
			lettera="";
			destinazioneUso="";
			descrCivico="";
			strada="";
			particella="";
			foglio = "";
			accesso = "";
			accesibilita = "";
			sezCensimento = "";
			dataAttivazione = "";
			numeroProcedimento = "";
			exCivico = "";
			localita = "";
			serie = "";
			note = "";
			pk_sequ_civico="";
			
			 idImm=new Integer(0);
			progressivo=new Integer(0);
			seq=new Integer(0);
			codiFiscLuna="";
			sezione="";
		}
		
		/**
		 * @return Returns the sedime.
		 */
		public String getSedime() {
			return sedime;
		}
		/**
		 * @param sedime The sedime to set.
		 */
		public void setSedime(String sedime) {
			this.sedime = sedime;
		}
		/**
		 * @return
		 */
		public String getChiave() {
			return chiave;
		}

		/**
		 * @return
		 */
		public String getCodCivico() {
			return codCivico;
		}

		/**
		 * @return
		 */
		public String getDescrCivico() {
			return descrCivico;
		}

		/**
		 * @return
		 */
		public String getDestinazioneUso() {
			return destinazioneUso;
		}

		/**
		 * @return
		 */
		public String getLettera() {
			return lettera;
		}

		
		/**
		 * @return
		 */
		public String getNumero() {
			return numero;
		}


		/**
		 * @return
		 */
		public String getParticella() {
			return particella;
		}

		/**
		 * @return
		 */
		public String getStrada() {
			return strada;
		}

		
		/**
		 * @param string
		 */
		public void setChiave(String string) {
			chiave = string;
		}

		/**
		 * @param string
		 */
		public void setCodCivico(String string) {
			codCivico = string;
		}

		/**
		 * @param string
		 */
		public void setDescrCivico(String string) {
			descrCivico = string;
		}

		/**
		 * @param string
		 */
		public void setDestinazioneUso(String string) {
			destinazioneUso = string;
		}

		/**
		 * @param string
		 */
		public void setLettera(String string) {
			lettera = string;
		}

		/**
		 * @param string
		 */
		public void setNumero(String string) {
			numero = string;
		}

		
		/**
		 * @param string
		 */
		public void setParticella(String string) {
			particella = string;
		}

		/**
		 * @param string
		 */
		public void setStrada(String string) {
			strada = string;
		}

		/**
		 * @return
		 */
		public String getComune() {
			return comune;
		}

		/**
		 * @param string
		 */
		public void setComune(String string) {
			comune = string;
		}

		/**
		 * @return
		 */
		public String getFoglio() {
			return foglio;
		}

		/**
		 * @param string
		 */
		public void setFoglio(String string) {
			foglio = string;
		}

		/**
		 * @return
		 */
		public String getAccesibilita() {
			return accesibilita;
		}

		/**
		 * @return
		 */
		public String getAccesso() {
			return accesso;
		}

		/**
		 * @return
		 */
		public String getDataAttivazione() {
			return dataAttivazione;
		}

		/**
		 * @return
		 */
		public String getExCivico() {
			return exCivico;
		}

		/**
		 * @return
		 */
		public String getLocalita() {
			return localita;
		}

		/**
		 * @return
		 */
		public String getNumeroProcedimento() {
			return numeroProcedimento;
		}

		/**
		 * @return
		 */
		public String getSerie() {
			return serie;
		}

		/**
		 * @return
		 */
		public String getSezCensimento() {
			return sezCensimento;
		}

		/**
		 * @param string
		 */
		public void setAccesibilita(String string) {
			accesibilita = string;
		}

		/**
		 * @param string
		 */
		public void setAccesso(String string) {
			accesso = string;
		}

		/**
		 * @param string
		 */
		public void setDataAttivazione(String string) {
			dataAttivazione = string;
		}

		/**
		 * @param string
		 */
		public void setExCivico(String string) {
			exCivico = string;
		}

		/**
		 * @param string
		 */
		public void setLocalita(String string) {
			localita = string;
		}

		/**
		 * @param string
		 */
		public void setNumeroProcedimento(String string) {
			numeroProcedimento = string;
		}

		/**
		 * @param string
		 */
		public void setSerie(String string) {
			serie = string;
		}

		/**
		 * @param string
		 */
		public void setSezCensimento(String string) {
			sezCensimento = string;
		}

		/**
		 * @return
		 */
		public String getNote() {
			return note;
		}

		/**
		 * @param string
		 */
		public void setNote(String string) {
			note = string;
		}

		public String getPk_sequ_civico() {
			return pk_sequ_civico;
		}

		public void setPk_sequ_civico(String pk_sequ_civico) {
			this.pk_sequ_civico = pk_sequ_civico;
		}

		public String getCod_nazionale()
		{
			return cod_nazionale;
		}

		public void setCod_nazionale(String cod_nazionale)
		{
			this.cod_nazionale = cod_nazionale;
		}

		public String getLatitudine() {
			return latitudine;
		}

		public void setLatitudine(String latitudine) {
			this.latitudine = latitudine;
		}

		public String getLongitudine() {
			return longitudine;
		}

		public void setLongitudine(String longitudine) {
			this.longitudine = longitudine;
		}

		public Integer getIdImm() {
			return idImm;
		}

		public void setIdImm(Integer idImm) {
			this.idImm = idImm;
		}

		public Integer getProgressivo() {
			return progressivo;
		}

		public void setProgressivo(Integer progressivo) {
			this.progressivo = progressivo;
		}

		public Integer getSeq() {
			return seq;
		}

		public void setSeq(Integer seq) {
			this.seq = seq;
		}

		public String getCodiFiscLuna() {
			return codiFiscLuna;
		}

		public void setCodiFiscLuna(String codiFiscLuna) {
			this.codiFiscLuna = codiFiscLuna;
		}

		public String getSezione() {
			return sezione;
		}

		public void setSezione(String sezione) {
			this.sezione = sezione;
		}
		
		public String getIdFonte() {
			return "4";
		}

		public String getTipoFonte() {
			return "TOPONOMASTICA COMUNALE";
		}

		public String getDiaKey() {
			if (diaKey != null && !diaKey.equals("")) {
				return diaKey;
			}
			diaKey = "";
			if (chiave != null && !chiave.equals("")) {
				diaKey += chiave;
			}
			return diaKey;
		}
		

}
