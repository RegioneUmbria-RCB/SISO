package it.escsolution.escwebgis.anagrafe.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Anagrafe extends EscObject implements Serializable {
	
	private String id;
	private String idExt;
	private String codAnagrafe;
	private String tipoSoggetto;
	private String famiglie;
	private String sezElettorali;
	private String codFiscale;
	private String cognome;
	private String nome;
	private String dataNascita;
	private String sesso;
	private String comuniNascita;
	private String cittadinanze;
	private String comuni;
	private String dataCaricamento;
	private String descrComuneNascita;
	private String civici;
	private String residenza;
	private String codiceNazionale;
	private String comuneMorte;
	private String dtMorte;
	private String comuneEmigrazione;
	private String dtEmigrazione;
	private String comuneImmigrazione;
	private String dataImmigrazione;
	private String posizioneAnagrafica;
	private String statoCivile;
	private String idCiv;
	private String idVia;
	private String descPrefissoVia;
	private String indirizzoEmi;
	private String residenzaFormat;
	//private String residenzaLink;

	
	public String getPosizioneAnagrafica() {
		return posizioneAnagrafica;
	}


	public void setPosizioneAnagrafica(String posizioneAnagrafica) {
		this.posizioneAnagrafica = posizioneAnagrafica;
	}


	public String getCodiceNazionale() {
		return codiceNazionale;
	}


	public void setCodiceNazionale(String codiceNazionale) {
		this.codiceNazionale = codiceNazionale;
	}


	public Anagrafe(){
		
		codAnagrafe = "";
		tipoSoggetto = "";
		famiglie = "";
		sezElettorali = "";
		codFiscale = "";
		cognome = "";
		nome = "";
		dataNascita = "";
		sesso = "";
		comuniNascita = "";
		cittadinanze = "";
		comuni = "";
		dataCaricamento = "";
		descrComuneNascita = "";
		civici = "";
		
	    }
	
	
	/**
	 * @return
	 */
	
	public String getChiave() {
			return id;
		}
		
	public String getCittadinanze() {
		return cittadinanze;
	}

	
	/**
	 * @return Returns the residenza.
	 */
	public String getResidenza() {
		return residenza;
	}
	/**
	 * @param residenza The residenza to set.
	 */
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}
	/**
	 * @return
	 */
	public String getCivici() {
		return civici;
	}

	/**
	 * @return
	 */
	public String getCodAnagrafe() {
		return codAnagrafe;
	}

	/**
	 * @return
	 */
	public String getCodFiscale() {
		return codFiscale;
	}

	/**
	 * @return
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @return
	 */
	public String getComuni() {
		return comuni;
	}

	/**
	 * @return
	 */
	public String getComuniNascita() {
		return comuniNascita;
	}

	/**
	 * @return
	 */
	public String getDataCaricamento() {
		return dataCaricamento;
	}

	/**
	 * @return
	 */
	public String getDataNascita() {
		return dataNascita;
	}

	/**
	 * @return
	 */
	public String getDescrComuneNascita() {
		return descrComuneNascita;
	}

	/**
	 * @return
	 */
	public String getFamiglie() {
		return famiglie;
	}

	/**
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return
	 */
	public String getSesso() {
		return sesso;
	}

	/**
	 * @return
	 */
	public String getSezElettorali() {
		return sezElettorali;
	}

	/**
	 * @return
	 */
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	/**
	 * @param string
	 */
	public void setCittadinanze(String string) {
		cittadinanze = string;
	}

	/**
	 * @param string
	 */
	public void setCivici(String string) {
		civici = string;
	}

	/**
	 * @param string
	 */
	public void setCodAnagrafe(String string) {
		codAnagrafe = string;
	}

	/**
	 * @param string
	 */
	public void setCodFiscale(String string) {
		codFiscale = string;
	}

	/**
	 * @param string
	 */
	public void setCognome(String string) {
		cognome = string;
	}

	/**
	 * @param string
	 */
	public void setComuni(String string) {
		comuni = string;
	}

	/**
	 * @param string
	 */
	public void setComuniNascita(String string) {
		comuniNascita = string;
	}

	/**
	 * @param string
	 */
	public void setDataCaricamento(String string) {
		dataCaricamento = string;
	}

	/**
	 * @param string
	 */
	public void setDataNascita(String string) {
		dataNascita = string;
	}

	/**
	 * @param string
	 */
	public void setDescrComuneNascita(String string) {
		descrComuneNascita = string;
	}

	/**
	 * @param string
	 */
	public void setFamiglie(String string) {
		famiglie = string;
	}

	/**
	 * @param string
	 */
	public void setNome(String string) {
		nome = string;
	}

	/**
	 * @param string
	 */
	public void setSesso(String string) {
		sesso = string;
	}

	/**
	 * @param string
	 */
	public void setSezElettorali(String string) {
		sezElettorali = string;
	}

	/**
	 * @param string
	 */
	public void setTipoSoggetto(String string) {
		tipoSoggetto = string;
	}


	public String getIdExt() {
		return idExt;
	}


	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getComuneImmigrazione() {
		return comuneImmigrazione;
	}


	public void setComuneImmigrazione(String comuneImmigrazione) {
		this.comuneImmigrazione = comuneImmigrazione;
	}


	public String getComuneMorte() {
		return comuneMorte;
	}


	public void setComuneMorte(String comuneMorte) {
		this.comuneMorte = comuneMorte;
	}


	public String getDataImmigrazione() {
		return dataImmigrazione;
	}


	public void setDataImmigrazione(String dataImmigrazione) {
		this.dataImmigrazione = dataImmigrazione;
	}


	public String getDtMorte() {
		return dtMorte;
	}


	public void setDtMorte(String dtMorte) {
		this.dtMorte = dtMorte;
	}


	public String getComuneEmigrazione() {
		return comuneEmigrazione;
	}


	public void setComuneEmigrazione(String comuneEmigrazione) {
		this.comuneEmigrazione = comuneEmigrazione;
	}


	public String getDtEmigrazione() {
		return dtEmigrazione;
	}


	public void setDtEmigrazione(String dtEmigrazione) {
		this.dtEmigrazione = dtEmigrazione;
	}


	public String getStatoCivile() {
		return statoCivile;
	}


	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}


	public String getIdCiv() {
		return idCiv;
	}


	public void setIdCiv(String idCiv) {
		this.idCiv = idCiv;
	}


	public String getIdVia() {
		return idVia;
	}


	public void setIdVia(String idVia) {
		this.idVia = idVia;
	}


	public String getDescPrefissoVia() {
		return descPrefissoVia;
	}


	public void setDescPrefissoVia(String descPrefissoVia) {
		this.descPrefissoVia = descPrefissoVia;
	}
	
	public String getIdFonte() {
		return "1";
	}

	public String getTipoFonte() {
		return "PERSONE";
	}

	public String getIndirizzoEmi() {
		return indirizzoEmi;
	}


	public void setIndirizzoEmi(String indirizzoEmi) {
		this.indirizzoEmi = indirizzoEmi;
	}


	public String getDiaKey() {
		if (diaKey != null && !diaKey.equals("")) {
			return diaKey;
		}
		diaKey = "";
		if (codAnagrafe != null && !codAnagrafe.equals("")) {
			diaKey += codAnagrafe;
		}
		diaKey += ",";
		if (codFiscale != null && !codFiscale.equals("")) {
			diaKey += codFiscale;
		}
		return diaKey;
	}


	public String getResidenzaFormat() {
		return residenzaFormat;
	}


	public void setResidenzaFormat(String residenzaFormat) {
		this.residenzaFormat = residenzaFormat;
	}

	
//	public String getResidenzaLink() {
//		return residenzaLink;
//	}
//
//
//	public void setResidenzaLink(String residenzaLink) {
//		this.residenzaLink = residenzaLink;
//	}
	
	//coordinate catastali e altri dati nuovo tracciato Milano
	
	private LinkedHashMap<String, AnagrafeCoordCat> datiCC;
	
	public class AnagrafeCoordCat {
		
		private String descrizioneVia;
		private String tipoCivico;
		private String lotto;
		private String isolato;
		private String scala;
		private String numInt;
		private String piano;
		private String cap;
		private String cittadinanza;
		private String titoloStudio;
		private String statoCivile;
		private String flgIntestatarioScheda;
		private String codEventoVariazione;
		private String foglio;
		private String mappale;
		private String subalterno;
		private String nomeFile;
		private String numeroCivico;
		private String barrato;
		
		public AnagrafeCoordCat() {
			super();
		}
		
		public AnagrafeCoordCat(String descrizioneVia,
				String tipoCivico,
				String lotto,
				String isolato,
				String scala,
				String numInt,
				String piano,
				String cap,
				String cittadinanza,
				String titoloStudio,
				String statoCivile,
				String flgIntestatarioScheda,
				String codEventoVariazione,
				String foglio,
				String mappale,
				String subalterno,
				String nomeFile,
				String numeroCivico,
				String barrato) {			
					super();
					
					this.descrizioneVia = descrizioneVia;
					this.tipoCivico = tipoCivico;
					this.lotto = lotto;
					this.isolato = isolato;
					this.scala = scala;
					this.numInt = numInt;
					this.piano = piano;
					this.cap = cap;
					this.cittadinanza = cittadinanza;
					this.titoloStudio = titoloStudio;
					this.statoCivile = statoCivile;
					this.flgIntestatarioScheda = flgIntestatarioScheda;
					this.codEventoVariazione = codEventoVariazione;
					this.foglio = foglio;
					this.mappale = mappale;
					this.subalterno = subalterno;
					this.nomeFile = nomeFile;
					this.numeroCivico = numeroCivico;
					this.barrato = barrato;
		}
		

		public String getDescrizioneVia() {
			return descrizioneVia;
		}


		public String getTipoCivico() {
			return tipoCivico;
		}


		public String getLotto() {
			return lotto;
		}


		public String getIsolato() {
			return isolato;
		}


		public String getScala() {
			return scala;
		}


		public String getNumInt() {
			return numInt;
		}


		public String getPiano() {
			return piano;
		}


		public String getCap() {
			return cap;
		}


		public String getCittadinanza() {
			return cittadinanza;
		}


		public String getTitoloStudio() {
			return titoloStudio;
		}


		public String getStatoCivile() {
			return statoCivile;
		}
		

		public String getFlgIntestatarioScheda() {
			return flgIntestatarioScheda;
		}


		public String getCodEventoVariazione() {
			return codEventoVariazione;
		}


		public String getFoglio() {
			return foglio;
		}


		public String getMappale() {
			return mappale;
		}


		public String getSubalterno() {
			return subalterno;
		}


		public String getNomeFile() {
			return nomeFile;
		}


		public String getNumeroCivico() {
			return numeroCivico;
		}


		public String getBarrato() {
			return barrato;
		}

	}	
	
	public LinkedHashMap<String, AnagrafeCoordCat> getDatiCC() {
		return datiCC;
	}

	public AnagrafeCoordCat getDatoCC(String key) {
		return datiCC == null ? null : datiCC.get(key);
	}
	
	public void addDatoCC(String descrizioneVia,
			String tipoCivico,
			String lotto,
			String isolato,
			String scala,
			String numInt,
			String piano,
			String cap,
			String cittadinanza,
			String titoloStudio,
			String statoCivile,
			String flgIntestatarioScheda,
			String codEventoVariazione,
			String foglio,
			String mappale,
			String subalterno,
			String nomeFile,
			String numeroCivico,
			String barrato) {
		if (datiCC == null) {
			datiCC = new LinkedHashMap<String, AnagrafeCoordCat>();
		}
		datiCC.put(nomeFile, new AnagrafeCoordCat(descrizioneVia,
					tipoCivico,
					lotto,
					isolato,
					scala,
					numInt,
					piano,
					cap,
					cittadinanza,
					titoloStudio,
					statoCivile,
					flgIntestatarioScheda,
					codEventoVariazione,
					foglio,
					mappale,
					subalterno,
					nomeFile,
					numeroCivico,
					barrato
				)
		);
	}
	
}
