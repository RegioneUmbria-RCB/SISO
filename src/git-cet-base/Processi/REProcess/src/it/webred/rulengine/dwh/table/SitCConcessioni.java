package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.TipoXml;


public class SitCConcessioni extends TabellaDwhMultiProv
{

	private String concessioneNumero;
	private String progressivoNumero;
	private String progressivoAnno;
	private DataDwh protocolloData;
	private String protocolloNumero;
	private String tipoIntervento;
	private String oggetto;
	private String procedimento;
	private String zona;
	private DataDwh dataRilascio;
	private DataDwh dataInizioLavori;
	private DataDwh dataFineLavori;
	private DataDwh dataProrogaLavori;
	private String esito;
	private String posizioneCodice;
	private String posizioneDescrizione;
	private DataDwh posizioneData;
	
	


	
	public String getPosizioneCodice() {
		return posizioneCodice;
	}




	public void setPosizioneCodice(String posizioneCodice) {
		this.posizioneCodice = posizioneCodice;
	}




	public String getPosizioneDescrizione() {
		return posizioneDescrizione;
	}




	public void setPosizioneDescrizione(String posizioneDescrizione) {
		this.posizioneDescrizione = posizioneDescrizione;
	}




	public DataDwh getPosizioneData() {
		return posizioneData;
	}




	public void setPosizioneData(DataDwh posizioneData) {
		this.posizioneData = posizioneData;
	}







	public String getEsito() {
		return esito;
	}




	public void setEsito(String esito) {
		this.esito = esito;
	}




	public String getConcessioneNumero() {
		return concessioneNumero;
	}




	public void setConcessioneNumero(String concessioneNumero) {
		this.concessioneNumero = concessioneNumero;
	}




	public DataDwh getDataFineLavori() {
		return dataFineLavori;
	}




	public void setDataFineLavori(DataDwh dataFineLavori) {
		this.dataFineLavori = dataFineLavori;
	}




	public DataDwh getDataInizioLavori() {
		return dataInizioLavori;
	}




	public void setDataInizioLavori(DataDwh dataInizioLavori) {
		this.dataInizioLavori = dataInizioLavori;
	}




	public DataDwh getDataRilascio() {
		return dataRilascio;
	}




	public void setDataRilascio(DataDwh dataRilascio) {
		this.dataRilascio = dataRilascio;
	}








	public String getOggetto() {
		return oggetto;
	}




	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}




	public String getProcedimento() {
		return procedimento;
	}




	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}




	public String getProgressivoAnno() {
		return progressivoAnno;
	}




	public void setProgressivoAnno(String progressivoAnno) {
		this.progressivoAnno = progressivoAnno;
	}




	public String getProgressivoNumero() {
		return progressivoNumero;
	}




	public void setProgressivoNumero(String progressivoNumero) {
		this.progressivoNumero = progressivoNumero;
	}




	public DataDwh getProtocolloData() {
		return protocolloData;
	}




	public void setProtocolloData(DataDwh protocolloData) {
		this.protocolloData = protocolloData;
	}




	public String getProtocolloNumero() {
		return protocolloNumero;
	}




	public void setProtocolloNumero(String protocolloNumero) {
		this.protocolloNumero = protocolloNumero;
	}




	public String getTipoIntervento() {
		return tipoIntervento;
	}




	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}




	public String getZona() {
		return zona;
	}




	public void setZona(String zona) {
		this.zona = zona;
	}

	// per la natura delle concessioni edilizie, una concessione viene aggiornata continuamente e contiene via via la storia
	public String getValueForCtrHash()
	{
		return 	concessioneNumero+oggetto+procedimento+progressivoAnno+progressivoNumero+protocolloNumero+tipoIntervento+zona+dataFineLavori.getDataFormattata()+dataInizioLavori.getDataFormattata()+dataRilascio.getDataFormattata()+protocolloData.getDataFormattata()+dataProrogaLavori.getDataFormattata()+getProvenienza()+esito+posizioneCodice+posizioneDescrizione+posizioneData.getDataFormattata();
	}


	public DataDwh getDataProrogaLavori() {
		return dataProrogaLavori;
	}

	public void setDataProrogaLavori(DataDwh dataProrogaLavori) {
		this.dataProrogaLavori = dataProrogaLavori;
	}





}
