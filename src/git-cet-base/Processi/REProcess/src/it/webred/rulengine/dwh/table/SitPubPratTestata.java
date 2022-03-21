package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SitPubPratTestata extends TabellaDwhMultiProv {

	private String tipoPratica ;
	private	BigDecimal numPratica ;
	private	String annoPratica;
	private	String descrizione;
	private	Timestamp dtInizio;
	private	Timestamp dtDecTermini;
	private	String provvedimento;
	
	
	public String getTipoPratica() {
		return tipoPratica;
	}


	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}


	public String getAnnoPratica() {
		return annoPratica;
	}



	public void setAnnoPratica(String annoPratica) {
		this.annoPratica = annoPratica;
	}



	public String getDescrizione() {
		return descrizione;
	}



	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}



	public Timestamp getDtInizio() {
		return dtInizio;
	}



	public void setDtInizio(Timestamp dtInizio) {
		this.dtInizio = dtInizio;
	}



	public Timestamp getDtDecTermini() {
		return dtDecTermini;
	}



	public void setDtDecTermini(Timestamp dtDecTermini) {
		this.dtDecTermini = dtDecTermini;
	}



	public String getProvvedimento() {
		return provvedimento;
	}



	public void setProvvedimento(String provvedimento) {
		this.provvedimento = provvedimento;
	}

	public BigDecimal getNumPratica() {
		return numPratica;
	}


	public void setNumPratica(BigDecimal numPratica) {
		this.numPratica = numPratica;
	}





	@Override
	public String getValueForCtrHash()
	{
		return this.tipoPratica +
		this.descrizione +
		this.numPratica.toString() +
		this.provvedimento +
		this.getProvenienza();
	}

}
