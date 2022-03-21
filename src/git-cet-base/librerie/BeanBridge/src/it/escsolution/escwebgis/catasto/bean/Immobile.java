/*
 * Created on 9-apr-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.escsolution.escwebgis.catasto.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Immobile extends EscObject implements Serializable {
	
	private String comune;
	private String sezione;
	private String foglio;
	private String numero;
	private String subalterno;
	private String unimm;
	
	private String categoria;
	private String codCategoria;
	
	private String vani; //consistenza
	
	private String superficie;
	private String rendita;
	private String zona;
	private String microzona;
	
	private String stato;
	private String particella;
	private String partita;
	private String piano;
	private String indirizzo;
	private String pkIndirizzo;
	private String civico;
	private String pkCivico;
	
	private String pkUiu;
	
	private String chiave;
	private String classe;
	
	private String titolo;
	private String dataFineVal;
	private String dataInizioVal;
	private String dataFinePos;
	
	private BigDecimal percPoss;

	long recordAttuale;
	long recordTotali;
	
	//String latitudine;
	//String longitudine;
	
	private boolean evidenza;
	private String annotazione;
	
	private Integer ideMutaIni;
	private Integer ideMutaFine;
	
	private String listaTitolari;
	
	private String pkId;
	
	private String graffato;
	private String chiaveGraffato;
	private boolean princGraffati;
	
	private String datesForKey;
	
	//Dati Informativi Atto 
	private String protNotifica;
	private String dataNotifica;
	private String codAttoIni;
	private String descAttoIni;
	private String codAttoFine;
	private String descAttoFine;
	private String codClassamento;
	private String descClassamento;
	
	//DatiMetrici
	private DatiMetrici datiMetrici = new DatiMetrici();
	
//	public String getLatitudine() {
//		return latitudine;
//	}
//
//	public void setLatitudine(String latitudine) {
//		this.latitudine = latitudine;
//	}

	/**
	 * 
	 */
	public Immobile() {
		comune = "";
		sezione = "";
		foglio = "";
		numero = "";
		subalterno = "";
		unimm = "";
		
		categoria = "";
		codCategoria = "";
		vani = "";
		
		superficie = "";
		rendita = ""; 
		zona = "";
		microzona = "";
		
		stato = "";
		particella = "";
		partita = "";
		piano = "";
		indirizzo = "";
		pkIndirizzo = "";
		civico = "";
		pkCivico = "";
		
		chiave = "";
		classe = "";
		titolo ="";
		pkUiu = "";
		dataFineVal = "";
		dataInizioVal = "";
		dataFinePos = "";
	}

	/**
	 * @return
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @return
	 */
	public String getComune() {
		return comune;
	}
	
	/**
	 * @return
	 */
	public String getSezione() {
		return sezione;
	}

	/**
	 * @return
	 */
	public String getFoglio() {
		return foglio;
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
	public String getPartita() {
		return partita;
	}

	/**
	 * @return
	 */
	public String getRendita() {
		return rendita;
	}

	/**
	 * @return
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @return
	 */
	public String getSubalterno() {
		return subalterno;
	}

	/**
	 * @return
	 */
	public String getSuperficie() {
		return superficie;
	}

	/**
	 * @return
	 */
	public String getVani() {
		return vani;
	}

	/**
	 * @return
	 */
	public String getZona() {
		return zona;
	}

	/**
	 * @param string
	 */
	public void setCategoria(String string) {
		categoria = string;
	}

	/**
	 * @param string
	 */
	public void setComune(String string) {
		comune = string;
	}
	
	/**
	 * @param string
	 */
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	/**
	 * @param string
	 */
	public void setFoglio(String string) {
		foglio = string;
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
	public void setPartita(String string) {
		partita = string;
	}

	/**
	 * @param string
	 */
	public void setRendita(String string) {
		rendita = string;
	}

	/**
	 * @param string
	 */
	public void setStato(String string) {
		stato = string;
	}

	/**
	 * @param string
	 */
	public void setSubalterno(String string) {
		subalterno = string;
	}

	/**
	 * @param string
	 */
	public void setSuperficie(String string) {
		superficie = string;
	}



	public String getProtNotifica() {
		return protNotifica;
	}

	public void setProtNotifica(String protNotifica) {
		this.protNotifica = protNotifica;
	}

	public String getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(String dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public String getCodAttoIni() {
		return codAttoIni;
	}

	public void setCodAttoIni(String codAttoIni) {
		this.codAttoIni = codAttoIni;
	}

	public String getDescAttoIni() {
		return descAttoIni;
	}

	public void setDescAttoIni(String descAttoIni) {
		this.descAttoIni = descAttoIni;
	}

	public String getCodAttoFine() {
		return codAttoFine;
	}

	public void setCodAttoFine(String codAttoFine) {
		this.codAttoFine = codAttoFine;
	}

	public String getDescAttoFine() {
		return descAttoFine;
	}

	public void setDescAttoFine(String descAttoFine) {
		this.descAttoFine = descAttoFine;
	}

	
	public String getCodClassamento() {
		return codClassamento;
	}

	public void setCodClassamento(String codClassamento) {
		this.codClassamento = codClassamento;
	}

	public String getDescClassamento() {
		descClassamento = "";
		if(codClassamento!=null ){
			if(!" ".equals(codClassamento)){
			int codice = Integer.valueOf(codClassamento);
			switch(codice){
				case 1 : descClassamento = "Classamento proposto dalla parte"; break;
				case 2 : descClassamento = "Classamento proposto dalla parte e validato da ufficio"; break;
				case 3 : descClassamento = "Classamento automatico (attribuito in sostituzione del classamento proposto)"; break;
				case 4 : descClassamento = "Classamento rettificato (in sostituzione del classamento proposto)"; break;
				case 5 : descClassamento = "Classamento proposto divenuto definitivo per decorrenza termini"; break;
				default : descClassamento ="-";
			};
		
		}else
			descClassamento="Residuale su uiu antecedenti DOCFA";
		}
		
		return descClassamento;
	}


	/**
	 * @param string
	 */
	public void setVani(String string) {
		vani = string;
	}

	/**
	 * @param string
	 */
	public void setZona(String string) {
		zona = string;
	}

	/**
	 * @return
	 */
	public String getCodCategoria() {
		return codCategoria;
	}

	/**
	 * @param string
	 */
	public void setCodCategoria(String string) {
		codCategoria = string;
	}

	/**
	 * @return
	 */
	public String getChiave() {
		if (chiave != null && !chiave.trim().equals("")) {
			return chiave;
		}
		return ""+comune+"|"+foglio+"|"+numero+"|"+subalterno+"|"+unimm+"|"+
		(datesForKey == null ? ((dataFineVal.equalsIgnoreCase("ATTUALE") ? "31/12/9999" : dataFineVal) + "|" +dataInizioVal)
		: datesForKey);
	}

	/**
	 * @param string
	 */
	public void setChiave(String string) {
		chiave = string;
	}

	/**
	 * @return
	 */
	public String getClasse() {
		return classe;
	}

	/**
	 * @param string
	 */
	public void setClasse(String string) {
		classe = string;
	}

	/**
	 * @return
	 */
	public long getRecordAttuale() {
		return recordAttuale;
	}

	/**
	 * @return
	 */
	public long getRecordTotali() {
		return recordTotali;
	}

	/**
	 * @param l
	 */
	public void setRecordAttuale(long l) {
		recordAttuale = l;
	}

	/**
	 * @param l
	 */
	public void setRecordTotali(long l) {
		recordTotali = l;
	}

	/**
	 * @return Returns the unimm.
	 */
	public String getUnimm() {
		return unimm;
	}
	/**
	 * @param unimm The unimm to set.
	 */
	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}
	/**
	 * @return Returns the pkUiu.
	 */
	public String getPkUiu() {
		return pkUiu;
	}
	/**
	 * @param pkUiu The pkUiu to set.
	 */
	public void setPkUiu(String pkUiu) {
		this.pkUiu = pkUiu;
	}
	/**
	 * @return Returns the civico.
	 */
	public String getCivico() {
		return civico;
	}
	/**
	 * @return Returns the indirizzo.
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	/**
	 * @return Returns the microzona.
	 */
	public String getMicrozona() {
		return microzona;
	}
	/**
	 * @return Returns the piano.
	 */
	public String getPiano() {
		return piano;
	}
	/**
	 * @return Returns the pkCivico.
	 */
	public String getPkCivico() {
		return pkCivico;
	}
	/**
	 * @return Returns the pkIndirizzo.
	 */
	public String getPkIndirizzo() {
		return pkIndirizzo;
	}
	/**
	 * @param civico The civico to set.
	 */
	public void setCivico(String civico) {
		this.civico = civico;
	}
	/**
	 * @param indirizzo The indirizzo to set.
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	/**
	 * @param microzona The microzona to set.
	 */
	public void setMicrozona(String microzona) {
		this.microzona = microzona;
	}
	/**
	 * @param piano The piano to set.
	 */
	public void setPiano(String piano) {
		this.piano = piano;
	}
	/**
	 * @param pkCivico The pkCivico to set.
	 */
	public void setPkCivico(String pkCivico) {
		this.pkCivico = pkCivico;
	}
	/**
	 * @param pkIndirizzo The pkIndirizzo to set.
	 */
	public void setPkIndirizzo(String pkIndirizzo) {
		this.pkIndirizzo = pkIndirizzo;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDataFineVal() {
		return dataFineVal;
	}
	public void setDataFineVal(String dataFineVal) {
		this.dataFineVal = dataFineVal;
	}
	public String getDataInizioVal() {
		return dataInizioVal;
	}
	public void setDataInizioVal(String dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}
	public String getDataFinePos() {
		return dataFinePos;
	}
	public void setDataFinePos(String dataFinePos) {
		this.dataFinePos = dataFinePos;
	}

	public boolean isEvidenza() {
		return evidenza;
	}

	public void setEvidenza(boolean evidenza) {
		this.evidenza = evidenza;
	}

	public String getAnnotazione() {
		return annotazione;
	}

	public void setAnnotazione(String annotazione) {
		this.annotazione = annotazione;
	}

	public Integer getIdeMutaIni() {
		return ideMutaIni;
	}

	public void setIdeMutaIni(Integer ideMutaIni) {
		this.ideMutaIni = ideMutaIni;
	}

	public Integer getIdeMutaFine() {
		return ideMutaFine;
	}

	public void setIdeMutaFine(Integer ideMutaFine) {
		this.ideMutaFine = ideMutaFine;
	}

	public String getListaTitolari() {
		return listaTitolari;
	}

	public void setListaTitolari(String listaTitolari) {
		this.listaTitolari = listaTitolari;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getGraffato() {
		return graffato;
	}

	public void setGraffato(String graffato) {
		this.graffato = graffato;
	}

	public String getChiaveGraffato() {
		return chiaveGraffato;
	}

	public void setChiaveGraffato(String chiaveGraffato) {
		this.chiaveGraffato = chiaveGraffato;
	}
	
	public boolean isPrincGraffati() {
		return princGraffati;
	}

	public void setPrincGraffati(boolean princGraffati) {
		this.princGraffati = princGraffati;
	}

	public String getDatesForKey() {
		return datesForKey;
	}

	public void setDatesForKey(String datesForKey) {
		this.datesForKey = datesForKey;
	}

	
	public BigDecimal getPercPoss() {
		return percPoss;
	}

	public void setPercPoss(BigDecimal percPoss) {
		this.percPoss = percPoss;
	}
	
	public String getIdFonte() {
		return "4";
	}
	
	public String getTipoFonte() {
		return "IMMOBILI";
	}

	public String getDiaKey() {
		if (diaKey != null && !diaKey.equals("")) {
			return diaKey;
		}
		diaKey = "";
		if (foglio != null && !foglio.equals("") && numero!=null && !numero.equals("")) {
			diaKey += lpad(foglio,4)+"|"+numero;
		}
		return diaKey;
	}
	

	private String lpad(String s, int num){
		while(s.length()!=num)
			s = "0"+s;
			
		return s;
	}

	public DatiMetrici getDatiMetrici() {
		return datiMetrici;
	}

	public void setDatiMetrici(DatiMetrici datiMetrici) {
		this.datiMetrici = datiMetrici;
	}
	
}
