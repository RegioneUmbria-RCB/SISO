package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitTIciSogg extends TabellaDwhMultiProv {
		
	private String idOrigSoggU;
	private String codFisc;
	private String partIva;
	private String cogDenom;
	private String nome;
	private String sesso;
	private String tipSogg;
	private DataDwh dtNsc;
	private String codIstCmnNsc;
	private String codBlfrCmnNsc;
	private String codCmnNsc;
	private String desComNsc;
	private String capComNsc;
	private String siglaProvNsc;
	private String desProvNsc;
	private String codStatoNsc;
	private String desStatoNsc;	
	private String codIstCmnRes;
	private String codBlfrCmnRes;
	private String codCmnRes;
	private String desComRes;
	private String capComRes;
	private String siglaProvRes;
	private String desProvRes;
	private String codStatoRes;
	private String desStatoRes;
	private String desIndRes;
	private Relazione idExtViaRes = new Relazione(SitTIciVia.class, new ChiaveEsterna());
	private String numCivRes;
	private String espCivRes;
	private String scalaRes;
	private String pianoRes;
	private String internoRes;
	private String indResExt;
	private String numCivExt;
	private DataDwh tmsAgg;
	private String flgTrf;
	private DataDwh tmsBon;
	
	public String getIdOrigSoggU() {
		return idOrigSoggU;
	}

	public void setIdOrigSoggU(String idOrigSoggU) {
		this.idOrigSoggU = idOrigSoggU;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getPartIva() {
		return partIva;
	}

	public void setPartIva(String partIva) {
		this.partIva = partIva;
	}

	public String getCogDenom() {
		return cogDenom;
	}

	public void setCogDenom(String cogDenom) {
		this.cogDenom = cogDenom;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTipSogg() {
		return tipSogg;
	}

	public void setTipSogg(String tipSogg) {
		this.tipSogg = tipSogg;
	}

	public DataDwh getDtNsc() {
		return dtNsc;
	}

	public void setDtNsc(DataDwh dtNsc) {
		this.dtNsc = dtNsc;
	}

	public String getCodIstCmnNsc() {
		return codIstCmnNsc;
	}

	public void setCodIstCmnNsc(String codIstCmnNsc) {
		this.codIstCmnNsc = codIstCmnNsc;
	}

	public String getCodBlfrCmnNsc() {
		return codBlfrCmnNsc;
	}

	public void setCodBlfrCmnNsc(String codBlfrCmnNsc) {
		this.codBlfrCmnNsc = codBlfrCmnNsc;
	}

	public String getCodCmnNsc() {
		return codCmnNsc;
	}

	public void setCodCmnNsc(String codCmnNsc) {
		this.codCmnNsc = codCmnNsc;
	}

	public String getDesComNsc() {
		return desComNsc;
	}

	public void setDesComNsc(String desComNsc) {
		this.desComNsc = desComNsc;
	}

	public String getCapComNsc() {
		return capComNsc;
	}

	public void setCapComNsc(String capComNsc) {
		this.capComNsc = capComNsc;
	}

	public String getSiglaProvNsc() {
		return siglaProvNsc;
	}

	public void setSiglaProvNsc(String siglaProvNsc) {
		this.siglaProvNsc = siglaProvNsc;
	}

	public String getDesProvNsc() {
		return desProvNsc;
	}

	public void setDesProvNsc(String desProvNsc) {
		this.desProvNsc = desProvNsc;
	}

	public String getCodStatoNsc() {
		return codStatoNsc;
	}

	public void setCodStatoNsc(String codStatoNsc) {
		this.codStatoNsc = codStatoNsc;
	}

	public String getDesStatoNsc() {
		return desStatoNsc;
	}

	public void setDesStatoNsc(String desStatoNsc) {
		this.desStatoNsc = desStatoNsc;
	}

	public String getCodIstCmnRes() {
		return codIstCmnRes;
	}

	public void setCodIstCmnRes(String codIstCmnRes) {
		this.codIstCmnRes = codIstCmnRes;
	}

	public String getCodBlfrCmnRes() {
		return codBlfrCmnRes;
	}

	public void setCodBlfrCmnRes(String codBlfrCmnRes) {
		this.codBlfrCmnRes = codBlfrCmnRes;
	}

	public String getCodCmnRes() {
		return codCmnRes;
	}

	public void setCodCmnRes(String codCmnRes) {
		this.codCmnRes = codCmnRes;
	}

	public String getDesComRes() {
		return desComRes;
	}

	public void setDesComRes(String desComRes) {
		this.desComRes = desComRes;
	}

	public String getCapComRes() {
		return capComRes;
	}

	public void setCapComRes(String capComRes) {
		this.capComRes = capComRes;
	}

	public String getSiglaProvRes() {
		return siglaProvRes;
	}

	public void setSiglaProvRes(String siglaProvRes) {
		this.siglaProvRes = siglaProvRes;
	}

	public String getDesProvRes() {
		return desProvRes;
	}

	public void setDesProvRes(String desProvRes) {
		this.desProvRes = desProvRes;
	}

	public String getCodStatoRes() {
		return codStatoRes;
	}

	public void setCodStatoRes(String codStatoRes) {
		this.codStatoRes = codStatoRes;
	}

	public String getDesStatoRes() {
		return desStatoRes;
	}

	public void setDesStatoRes(String desStatoRes) {
		this.desStatoRes = desStatoRes;
	}

	public String getDesIndRes() {
		return desIndRes;
	}

	public void setDesIndRes(String desIndRes) {
		this.desIndRes = desIndRes;
	}

	public Relazione getIdExtViaRes() {
		return idExtViaRes;
	}

	public void setIdExtViaRes(ChiaveEsterna idExtViaRes)
	{
		Relazione r = new Relazione(SitTIciVia.class, idExtViaRes);
		this.idExtViaRes = r;
	}

	public String getNumCivRes() {
		return numCivRes;
	}

	public void setNumCivRes(String numCivRes) {
		this.numCivRes = numCivRes;
	}

	public String getEspCivRes() {
		return espCivRes;
	}

	public void setEspCivRes(String espCivRes) {
		this.espCivRes = espCivRes;
	}

	public String getScalaRes() {
		return scalaRes;
	}

	public void setScalaRes(String scalaRes) {
		this.scalaRes = scalaRes;
	}

	public String getPianoRes() {
		return pianoRes;
	}

	public void setPianoRes(String pianoRes) {
		this.pianoRes = pianoRes;
	}

	public String getInternoRes() {
		return internoRes;
	}

	public void setInternoRes(String internoRes) {
		this.internoRes = internoRes;
	}

	public String getIndResExt() {
		return indResExt;
	}

	public void setIndResExt(String indResExt) {
		this.indResExt = indResExt;
	}

	public String getNumCivExt() {
		return numCivExt;
	}

	public void setNumCivExt(String numCivExt) {
		this.numCivExt = numCivExt;
	}

	public DataDwh getTmsAgg() {
		return tmsAgg;
	}

	public void setTmsAgg(DataDwh tmsAgg) {
		this.tmsAgg = tmsAgg;
	}

	public String getFlgTrf() {
		return flgTrf;
	}

	public void setFlgTrf(String flgTrf) {
		this.flgTrf = flgTrf;
	}

	public DataDwh getTmsBon() {
		return tmsBon;
	}

	public void setTmsBon(DataDwh tmsBon) {
		this.tmsBon = tmsBon;
	}

	public String getValueForCtrHash() {		
		return this.idOrigSoggU +
		this.codFisc +
		this.partIva +
		this.cogDenom +
		this.nome +
		this.sesso +
		this.tipSogg +
		this.dtNsc.getDataFormattata() +
		this.codIstCmnNsc +
		this.codBlfrCmnNsc +
		this.codCmnNsc +
		this.desComNsc +
		this.capComNsc +
		this.siglaProvNsc +
		this.desProvNsc +
		this.codStatoNsc +
		this.desStatoNsc +	
		this.codIstCmnRes +
		this.codBlfrCmnRes +
		this.codCmnRes +
		this.desComRes +
		this.capComRes +
		this.siglaProvRes +
		this.desProvRes +
		this.codStatoRes +
		this.desStatoRes +
		this.desIndRes +
		(String)this.idExtViaRes.getRelazione().getValore() +
		this.numCivRes +
		this.espCivRes +
		this.scalaRes +
		this.pianoRes +
		this.internoRes +
		this.indResExt +
		this.numCivExt +
		this.tmsAgg.getDataFormattata() +
		this.flgTrf +
		this.tmsBon.getDataFormattata();
	}
	
}
