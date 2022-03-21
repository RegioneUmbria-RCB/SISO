package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SitTF24IdAccredito extends TabellaDwh {
	
    private Relazione idExtTestata = new Relazione(SitTF24Testata.class,new ChiaveEsterna());
	
	private DataDwh dtFornitura;
	private BigDecimal progFornitura;
	private String codEnteCom;
	private String codValuta;
	private BigDecimal impAccredito;
	private BigDecimal cro;
	private DataDwh dtAccredito;
	private DataDwh dtRipartOrig;
	private BigDecimal progRipartOrig;
	private DataDwh dtBonificoOrig;
	private String tipoImposta;
	private String iban;
	private String sezContoTu;
	private BigDecimal numContoTu;
	private BigDecimal codMovimento;
	
	@Override
	public String getValueForCtrHash()
	{
		String s = this.dtFornitura.getDataFormattata()+this.progFornitura+this.codEnteCom+this.codValuta;
		s+=this.impAccredito.toString()+
				(this.cro!=null ? this.cro.toString():"")+
				this.dtAccredito.getDataFormattata()+
				this.dtRipartOrig.getDataFormattata()+
				this.progRipartOrig+
				this.dtBonificoOrig.getDataFormattata()+
				this.tipoImposta+
				(this.iban!=null?this.iban:"")+
				(this.sezContoTu!=null?this.sezContoTu:"")+
				(this.numContoTu!=null? this.numContoTu.toString():"")+
				(this.codMovimento!=null? this.numContoTu.toString():"");
				
		return s;
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





	public String getCodEnteCom() {
		return codEnteCom;
	}


	public void setCodEnteCom(String codEnteCom) {
		this.codEnteCom = codEnteCom;
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


	public BigDecimal getCro() {
		return cro;
	}


	public void setCro(BigDecimal cro) {
		this.cro = cro;
	}


	public DataDwh getDtAccredito() {
		return dtAccredito;
	}


	public void setDtAccredito(DataDwh dtAccredito) {
		this.dtAccredito = dtAccredito;
	}


	public DataDwh getDtRipartOrig() {
		return dtRipartOrig;
	}


	public void setDtRipartOrig(DataDwh dtRipartOrig) {
		this.dtRipartOrig = dtRipartOrig;
	}


	public DataDwh getDtBonificoOrig() {
		return dtBonificoOrig;
	}


	public void setDtBonificoOrig(DataDwh dtBonificoOrig) {
		this.dtBonificoOrig = dtBonificoOrig;
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


	public BigDecimal getProgRipartOrig() {
		return progRipartOrig;
	}


	public void setProgRipartOrig(BigDecimal progRipartOrig) {
		this.progRipartOrig = progRipartOrig;
	}


	public String getIban() {
		return iban;
	}


	public void setIban(String iban) {
		this.iban = iban;
	}


	public String getSezContoTu() {
		return sezContoTu;
	}


	public void setSezContoTu(String sezContoTu) {
		this.sezContoTu = sezContoTu;
	}


	public BigDecimal getNumContoTu() {
		return numContoTu;
	}


	public void setNumContoTu(BigDecimal numContoTu) {
		this.numContoTu = numContoTu;
	}


	public BigDecimal getCodMovimento() {
		return codMovimento;
	}


	public void setCodMovimento(BigDecimal codMovimento) {
		this.codMovimento = codMovimento;
	}


	public void setIdExtTestata(Relazione idExtTestata) {
		this.idExtTestata = idExtTestata;
	}


}
