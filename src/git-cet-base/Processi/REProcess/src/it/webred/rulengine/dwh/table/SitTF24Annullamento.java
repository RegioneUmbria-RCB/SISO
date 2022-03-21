package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SitTF24Annullamento extends TabellaDwh {

    private Relazione idExtTestata = new Relazione(SitTF24Testata.class,new ChiaveEsterna());
	
	private DataDwh dtFornitura;
	private BigDecimal progFornitura;
	private DataDwh dtRipartOrig;
	private BigDecimal progRipartOrig;
	private DataDwh dtBonificoOrig;
	private String codEnteRd;
	private String cf;
	private DataDwh dtRiscossione;
	private String codEnteCom;
	private String codTributo;
	private BigDecimal annoRif;
	private String codValuta;
	private BigDecimal impDebito;
	private BigDecimal impCredito;
	private String tipoOperazione;
	private DataDwh dtOperazione;
    private String tipoImposta;
 
	@Override
	public String getValueForCtrHash()
	{
		return this.dtFornitura.getDataFormattata()+this.progFornitura+this.dtRipartOrig.getDataFormattata()+this.progRipartOrig+
				this.dtBonificoOrig.getDataFormattata()+this.codEnteRd+this.getCf()+this.dtRiscossione.getDataFormattata()+
				this.codEnteCom +this.codTributo+this.annoRif.toString()+this.codValuta+this.impCredito.toString()+this.impDebito.toString()+
				this.tipoOperazione+this.dtOperazione.getDataFormattata()+this.tipoImposta;
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

	

	public DataDwh getDtRipartOrig() {
		return dtRipartOrig;
	}

	public void setDtRipartOrig(DataDwh dtRipartOrig) {
		this.dtRipartOrig = dtRipartOrig;
	}

	
	public BigDecimal getProgFornitura() {
		return progFornitura;
	}

	public void setProgFornitura(BigDecimal progFornitura) {
		this.progFornitura = progFornitura;
	}

	public BigDecimal getProgRipartOrig() {
		return progRipartOrig;
	}

	public void setProgRipartOrig(BigDecimal progRipartOrig) {
		this.progRipartOrig = progRipartOrig;
	}

	public DataDwh getDtBonificoOrig() {
		return dtBonificoOrig;
	}

	public void setDtBonificoOrig(DataDwh dtBonificoOrig) {
		this.dtBonificoOrig = dtBonificoOrig;
	}

	public String getCodEnteRd() {
		return codEnteRd;
	}

	public void setCodEnteRd(String codEnteRd) {
		this.codEnteRd = codEnteRd;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public DataDwh getDtRiscossione() {
		return dtRiscossione;
	}

	public void setDtRiscossione(DataDwh dtRiscossione) {
		this.dtRiscossione = dtRiscossione;
	}

	public String getCodEnteCom() {
		return codEnteCom;
	}

	public void setCodEnteCom(String codEnteCom) {
		this.codEnteCom = codEnteCom;
	}

	public String getCodTributo() {
		return codTributo;
	}

	public void setCodTributo(String codTributo) {
		this.codTributo = codTributo;
	}

	public BigDecimal getAnnoRif() {
		return annoRif;
	}

	public void setAnnoRif(BigDecimal annoRif) {
		this.annoRif = annoRif;
	}

	public String getCodValuta() {
		return codValuta;
	}

	public void setCodValuta(String codValuta) {
		this.codValuta = codValuta;
	}

	public BigDecimal getImpDebito() {
		return impDebito;
	}

	public void setImpDebito(BigDecimal impDebito) {
		this.impDebito = impDebito;
	}

	public BigDecimal getImpCredito() {
		return impCredito;
	}

	public void setImpCredito(BigDecimal impCredito) {
		this.impCredito = impCredito;
	}

	public String getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public DataDwh getDtOperazione() {
		return dtOperazione;
	}

	public void setDtOperazione(DataDwh dtOperazione) {
		this.dtOperazione = dtOperazione;
	}

	public String getTipoImposta() {
		return tipoImposta;
	}

	public void setTipoImposta(String tipoImposta) {
		this.tipoImposta = tipoImposta;
	}

	public void setIdExtTestata(Relazione idExtTestata) {
		this.idExtTestata = idExtTestata;
	}

}
