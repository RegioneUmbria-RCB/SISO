package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SitTF24Recupero extends TabellaDwh {
	
    private Relazione idExtTestata = new Relazione(SitTF24Testata.class,new ChiaveEsterna());
	
	private DataDwh dtFornitura;
	private BigDecimal progFornitura;
	private DataDwh dtRipartizione;
	private BigDecimal progRipartizione;
	private String codEnteCom;
	private String codValuta;
	private BigDecimal impRecupero;
	private String annoMeseRipOrig;
	
	private String dtBonifico;
	private String progRipOrig;
	private String dtBonificoOrig;
	private String tipoImposta;
	private String tipoRec;
	private String descTipoRec;
	private String annoMeseRif = "";

	@Override
	public String getValueForCtrHash()
	{
		return this.dtFornitura.getDataFormattata()+this.progFornitura.toString()+this.dtRipartizione.getDataFormattata()+
			   this.progRipartizione.toString()+this.codEnteCom+this.codValuta+
			  (this.impRecupero!=null ? this.impRecupero.toString():"")+
			  this.annoMeseRipOrig+this.tipoImposta+
			  this.dtBonifico+this.progRipOrig+this.dtBonificoOrig+this.tipoRec+this.descTipoRec+this.annoMeseRif;
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


	public String getCodEnteCom() {
		return codEnteCom;
	}


	public void setCodEnteCom(String codEnte) {
		this.codEnteCom = codEnte;
	}


	public String getCodValuta() {
		return codValuta;
	}


	public void setCodValuta(String codValuta) {
		this.codValuta = codValuta;
	}


	public BigDecimal getImpRecupero() {
		return impRecupero;
	}


	public void setImpRecupero(BigDecimal impRecupero) {
		this.impRecupero = impRecupero;
	}


	public void setIdExtTestata(Relazione idExtTestata) {
		this.idExtTestata = idExtTestata;
	}


	public String getTipoImposta() {
		return tipoImposta;
	}


	public void setTipoImposta(String tipoImposta) {
		this.tipoImposta = tipoImposta;
	}


	public String getAnnoMeseRipOrig() {
		return annoMeseRipOrig;
	}


	public void setAnnoMeseRipOrig(String annoMeseRipOrig) {
		this.annoMeseRipOrig = annoMeseRipOrig;
	}


	public String getProgRipOrig() {
		return progRipOrig;
	}

	public String getDtBonifico() {
		return dtBonifico;
	}


	public void setDtBonifico(String dtBonifico) {
		this.dtBonifico = dtBonifico;
	}


	public String getDtBonificoOrig() {
		return dtBonificoOrig;
	}


	public void setDtBonificoOrig(String dtBonificoOrig) {
		this.dtBonificoOrig = dtBonificoOrig;
	}


	public void setProgRipOrig(String progRipOrig) {
		this.progRipOrig = progRipOrig;
	}

	public String getTipoRec() {
		return tipoRec;
	}


	public void setTipoRec(String tipoRec) {
		this.tipoRec = tipoRec;
	}


	public String getDescTipoRec() {
		return descTipoRec;
	}


	public void setDescTipoRec(String descTipoRec) {
		this.descTipoRec = descTipoRec;
	}


	public String getAnnoMeseRif() {
		return annoMeseRif;
	}


	public void setAnnoMeseRif(String annoMeseRif) {
		this.annoMeseRif = annoMeseRif;
	}

}
