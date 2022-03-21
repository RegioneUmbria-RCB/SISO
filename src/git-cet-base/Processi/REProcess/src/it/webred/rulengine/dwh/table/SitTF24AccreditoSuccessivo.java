package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;

public class SitTF24AccreditoSuccessivo extends TabellaDwh {

    private Relazione idExtTestata = new Relazione(SitTF24Testata.class,new ChiaveEsterna());
	
    private String annoMeseRif = "";
    private String codEnte;
    private DataDwh dtProduzioneMandato;
    private BigDecimal progMandato;
    private DataDwh dtRif;
    private BigDecimal progRif;
	private DataDwh dtBonifico;
	private String codValuta;	
	private BigDecimal impAccredito;
	private String motivoMandato;
	private String cro;
    private DataDwh dtFinalizzazione;
    private String esitoAccredito = "";
    private String codiceMovimento = "";
    private String tipoImposta;

    
	@Override
	public String getValueForCtrHash(){
		return this.getIdOrig().getValore()+this.codEnte+
				(this.dtProduzioneMandato!=null ? this.dtProduzioneMandato.getDataFormattata():"")+progMandato+
				(this.dtRif!=null ? this.dtRif.getDataFormattata():"")+progRif+
				(this.dtBonifico!=null ? this.dtBonifico.getDataFormattata():"")+
				this.codValuta+
				impAccredito.toString()+
				this.motivoMandato+
				this.cro+
				(this.dtFinalizzazione!=null ? this.dtFinalizzazione.getDataFormattata():"")+
				this.esitoAccredito+
				this.codiceMovimento+
				this.tipoImposta;
	}

	public Relazione getIdExtTestata() {
		return idExtTestata;
	}

	public void setIdExtTestata(ChiaveEsterna idExtF24Testata) {
		
		Relazione r = new Relazione(SitTF24Testata.class,idExtF24Testata);
		this.idExtTestata = r;
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

	public DataDwh getDtProduzioneMandato() {
		return dtProduzioneMandato;
	}

	public void setDtProduzioneMandato(DataDwh dtProduzioneMandato) {
		this.dtProduzioneMandato = dtProduzioneMandato;
	}

	public BigDecimal getProgMandato() {
		return progMandato;
	}

	public void setProgMandato(BigDecimal progMandato) {
		this.progMandato = progMandato;
	}

	public DataDwh getDtRif() {
		return dtRif;
	}

	public void setDtRif(DataDwh dtRif) {
		this.dtRif = dtRif;
	}

	public BigDecimal getProgRif() {
		return progRif;
	}

	public void setProgRif(BigDecimal progRif) {
		this.progRif = progRif;
	}

	public String getMotivoMandato() {
		return motivoMandato;
	}

	public void setMotivoMandato(String motivoMandato) {
		this.motivoMandato = motivoMandato;
	}


	

}
