package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitCConcDatiTec extends TabellaDwh {

	private RelazioneToMasterTable idExtCConcessioni = new RelazioneToMasterTable(SitCConcessioni.class,new ChiaveEsterna());
	private String destUso;
	private BigDecimal supEffLotto;
	private BigDecimal supCoperta;
	private BigDecimal volTotale;
	private Integer vani;
	private Integer numAbitazioni;
	private DataDwh dtAgibilita;
	
	public Relazione getIdExtCConcessioni()
	{
		return idExtCConcessioni;
	}

	public void setIdExtCConcessioni(ChiaveEsterna idExtCConcessioni)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitCConcessioni.class,idExtCConcessioni);
		this.idExtCConcessioni = r;	
	}

	public String getDestUso() {
		return destUso;
	}

	public void setDestUso(String destUso) {
		this.destUso = destUso;
	}

	public BigDecimal getSupEffLotto() {
		return supEffLotto;
	}

	public void setSupEffLotto(BigDecimal supEffLotto) {
		this.supEffLotto = supEffLotto;
	}

	public BigDecimal getSupCoperta() {
		return supCoperta;
	}

	public void setSupCoperta(BigDecimal supCoperta) {
		this.supCoperta = supCoperta;
	}

	public BigDecimal getVolTotale() {
		return volTotale;
	}

	public void setVolTotale(BigDecimal volTotale) {
		this.volTotale = volTotale;
	}

	public Integer getVani() {
		return vani;
	}

	public void setVani(Integer vani) {
		this.vani = vani;
	}

	public Integer getNumAbitazioni() {
		return numAbitazioni;
	}

	public void setNumAbitazioni(Integer numAbitazioni) {
		this.numAbitazioni = numAbitazioni;
	}

	public DataDwh getDtAgibilita() {
		return dtAgibilita;
	}

	public void setDtAgibilita(DataDwh dtAgibilita) {
		this.dtAgibilita = dtAgibilita;
	}
	
	public String getValueForCtrHash() {		
		return (String)idExtCConcessioni.getRelazione().getValore();
	}
	
}
