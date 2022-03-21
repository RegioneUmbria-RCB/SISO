package it.escsolution.escwebgis.tributiNew.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SoggettiTributiNew extends EscObject implements Serializable {

	private String chiave;
	private String idExt;
	private String idOrig;
	private String idOrigSoggU;	
	private String codFisc;
	private String partIva;
	private String cogDenom;
	private String nome;
	private String denominazione;
	private String sesso;
	private String tipSogg;
	private String dtNsc;
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
	private String idExtViaRes;
	private String numCivRes;
	private String espCivRes;
	private String scalaRes;
	private String pianoRes;
	private String internoRes;
	private String indResExt;
	private String numCivExt;	
	private String tmsAgg;
	private boolean flgTrf;
	private String tmsBon;
	private String titolo;
	private String provenienza;
	private String tributo;
	
	private String indResAna;
	private String comResAna;
	private String indSiatel;
	private String comSiatel;
	
	private boolean accorpamento;
	
	private ArrayList<String> idOrigs;
	private ArrayList<String> tipSoggs;
	private ArrayList<String> codFiscs;
	private ArrayList<String> partIvas;
	private ArrayList<String> cogDenoms;
	private ArrayList<String> nomes;
	private ArrayList<String> denominaziones;
	private ArrayList<String> desIndRess;
	private ArrayList<String> desComRess;
	private ArrayList<String> tributos;
	private ArrayList<String> provenienzas;
	
	private String id;
	
	
	public String getChiave() {
		return chiave;
	}
	
	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getIdExt() {
		return idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getIdOrig() {
		return idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

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

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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

	public String getDtNsc() {
		return dtNsc;
	}

	public void setDtNsc(String dtNsc) {
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

	public String getIdExtViaRes() {
		return idExtViaRes;
	}

	public void setIdExtViaRes(String idExtViaRes) {
		this.idExtViaRes = idExtViaRes;
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

	public String getTmsAgg() {
		return tmsAgg;
	}

	public void setTmsAgg(String tmsAgg) {
		this.tmsAgg = tmsAgg;
	}

	public boolean isFlgTrf() {
		return flgTrf;
	}

	public void setFlgTrf(boolean flgTrf) {
		this.flgTrf = flgTrf;
	}

	public String getTmsBon() {
		return tmsBon;
	}

	public void setTmsBon(String tmsBon) {
		this.tmsBon = tmsBon;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getTributo() {
		return tributo;
	}

	public void setTributo(String tributo) {
		this.tributo = tributo;
	}

	public String getIndResAna() {
		return indResAna;
	}

	public void setIndResAna(String indResAna) {
		this.indResAna = indResAna;
	}

	public String getComResAna() {
		return comResAna;
	}

	public void setComResAna(String comResAna) {
		this.comResAna = comResAna;
	}

	public String getIndSiatel() {
		return indSiatel;
	}

	public void setIndSiatel(String indSiatel) {
		this.indSiatel = indSiatel;
	}

	public String getComSiatel() {
		return comSiatel;
	}

	public void setComSiatel(String comSiatel) {
		this.comSiatel = comSiatel;
	}

	public boolean isAccorpamento() {
		return accorpamento;
	}

	public void setAccorpamento(boolean accorpamento) {
		this.accorpamento = accorpamento;
	}

	public ArrayList<String> getIdOrigs() {
		return idOrigs;
	}

	public void setIdOrigs(ArrayList<String> idOrigs) {
		this.idOrigs = idOrigs;
	}

	public ArrayList<String> getTipSoggs() {
		return tipSoggs;
	}

	public void setTipSoggs(ArrayList<String> tipSoggs) {
		this.tipSoggs = tipSoggs;
	}

	public ArrayList<String> getCodFiscs() {
		return codFiscs;
	}

	public void setCodFiscs(ArrayList<String> codFiscs) {
		this.codFiscs = codFiscs;
	}

	public ArrayList<String> getPartIvas() {
		return partIvas;
	}

	public void setPartIvas(ArrayList<String> partIvas) {
		this.partIvas = partIvas;
	}

	public ArrayList<String> getCogDenoms() {
		return cogDenoms;
	}

	public void setCogDenoms(ArrayList<String> cogDenoms) {
		this.cogDenoms = cogDenoms;
	}

	public ArrayList<String> getNomes() {
		return nomes;
	}

	public void setNomes(ArrayList<String> nomes) {
		this.nomes = nomes;
	}

	public ArrayList<String> getDenominaziones() {
		return denominaziones;
	}

	public void setDenominaziones(ArrayList<String> denominaziones) {
		this.denominaziones = denominaziones;
	}

	public ArrayList<String> getDesIndRess() {
		return desIndRess;
	}

	public void setDesIndRess(ArrayList<String> desIndRess) {
		this.desIndRess = desIndRess;
	}

	public ArrayList<String> getTributos() {
		return tributos;
	}

	public void setTributos(ArrayList<String> tributos) {
		this.tributos = tributos;
	}

	public ArrayList<String> getProvenienzas() {
		return provenienzas;
	}

	public void setProvenienzas(ArrayList<String> provenienzas) {
		this.provenienzas = provenienzas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public ArrayList<String> getDesComRess() {
		return desComRess;
	}

	public void setDesComRess(ArrayList<String> desComRess) {
		this.desComRess = desComRess;
	}
	
}
