package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SitTF24Accredito extends TabellaDwh {

    private Relazione idExtTestata = new Relazione(SitTF24Testata.class,new ChiaveEsterna());
	
	private DataDwh dtFornitura;
	private BigDecimal progFornitura;
	private DataDwh dtRipartizione;
	private BigDecimal progRipartizione;
	private DataDwh dtBonifico;
	private String codEnte;
	private String codValuta;
	private BigDecimal impAccredito;
    private String tipoImposta;
    private String annoMeseRif = "";
    private String cro = "";
    private DataDwh dtFinalizzazione;
    private String esitoAccredito = "";
    private String codiceMovimento = "";
    
	@Override
	public String getValueForCtrHash(){
		return this.getIdOrig().getValore()+codEnte+codValuta+impAccredito.toString()+tipoImposta+this.annoMeseRif+this.cro+this.esitoAccredito+this.codiceMovimento;
	}

	public Relazione getIdExtTestata() {
		return idExtTestata;
	}

	public void setIdExtTestata(ChiaveEsterna idExtF24Testata) {
		
		Relazione r = new Relazione(SitTF24Testata.class,idExtF24Testata);
		this.idExtTestata = r;
	}


	public DataDwh getDtFornitura() {
		return dtFornitura;
	}

	public void setDtFornitura(DataDwh dtFornitura) {
		this.dtFornitura = dtFornitura;
	}

	

	public DataDwh getDtRipartizione() {
		return dtRipartizione;
	}

	public void setDtRipartizione(DataDwh dtRipartizione) {
		this.dtRipartizione = dtRipartizione;
	}

	

	public DataDwh getDtBonifico() {
		return dtBonifico;
	}

	public void setDtBonifico(DataDwh dtBonifico) {
		this.dtBonifico = dtBonifico;
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public String getCodValuta() {
		return codValuta;
	}

	public void setCodValuta(String codValuta) {
		this.codValuta = codValuta;
	}

	public BigDecimal getImpAccredito() {
		return impAccredito;
	}

	public void setImpAccredito(BigDecimal impAccredito) {
		this.impAccredito = impAccredito;
	}

	public String getTipoImposta() {
		return tipoImposta;
	}

	public void setTipoImposta(String tipoImposta) {
		this.tipoImposta = tipoImposta;
	}

	public BigDecimal getProgFornitura() {
		return progFornitura;
	}

	public void setProgFornitura(BigDecimal progFornitura) {
		this.progFornitura = progFornitura;
	}

	public BigDecimal getProgRipartizione() {
		return progRipartizione;
	}

	public void setProgRipartizione(BigDecimal progRipartizione) {
		this.progRipartizione = progRipartizione;
	}

	public String getAnnoMeseRif() {
		return annoMeseRif;
	}

	public void setAnnoMeseRif(String annoMeseRif) {
		this.annoMeseRif = annoMeseRif;
	}

	public String getCro() {
		return cro;
	}

	public void setCro(String cro) {
		this.cro = cro;
	}

	public DataDwh getDtFinalizzazione() {
		return dtFinalizzazione;
	}

	public void setDtFinalizzazione(DataDwh dtFinalizzazione) {
		this.dtFinalizzazione = dtFinalizzazione;
	}

	public String getEsitoAccredito() {
		return esitoAccredito;
	}

	public void setEsitoAccredito(String esitoAccredito) {
		this.esitoAccredito = esitoAccredito;
	}

	public String getCodiceMovimento() {
		return codiceMovimento;
	}

	public void setCodiceMovimento(String codiceMovimento) {
		this.codiceMovimento = codiceMovimento;
	}

	public void setIdExtTestata(Relazione idExtTestata) {
		this.idExtTestata = idExtTestata;
	}


	

}
