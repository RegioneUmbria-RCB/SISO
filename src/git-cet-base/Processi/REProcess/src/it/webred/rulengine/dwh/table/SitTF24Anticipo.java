package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SitTF24Anticipo extends TabellaDwh {
	
    private Relazione idExtTestata = new Relazione(SitTF24Testata.class,new ChiaveEsterna());
	
	private DataDwh dtFornitura;
	private BigDecimal progFornitura;
	private DataDwh dtRipartizione;
	private BigDecimal progRipartizione;
	private DataDwh dtBonifico;
	private String codEnteCom;
	private String codValuta;
	private BigDecimal impAnticipo;
    private String tipoImposta;
    private String annoMeseRif = "";
    private String campoUsoInterno = "";
   
	@Override
	public String getValueForCtrHash()
	{
		return this.dtFornitura.getDataFormattata()+this.getProgFornitura()+this.dtRipartizione.getDataFormattata()+
			  (this.dtBonifico!=null ? this.dtBonifico.getDataFormattata():"")+this.codEnteCom+this.codValuta+
			  (this.impAnticipo!=null ? this.impAnticipo.toString():"")+this.tipoImposta+this.annoMeseRif+this.campoUsoInterno;
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


	public DataDwh getDtBonifico() {
		return dtBonifico;
	}
	public void setDtBonifico(DataDwh dtBonifico) {
		this.dtBonifico = dtBonifico;
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
	public BigDecimal getImpAnticipo() {
		return impAnticipo;
	}
	public void setImpAnticipo(BigDecimal impAnticipo) {
		this.impAnticipo = impAnticipo;
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


	public String getAnnoMeseRif() {
		return annoMeseRif;
	}


	public void setAnnoMeseRif(String annoMeseRif) {
		this.annoMeseRif = annoMeseRif;
	}


	public String getCampoUsoInterno() {
		return campoUsoInterno;
	}


	public void setCampoUsoInterno(String campoUsoInterno) {
		this.campoUsoInterno = campoUsoInterno;
	}


	
	

}
