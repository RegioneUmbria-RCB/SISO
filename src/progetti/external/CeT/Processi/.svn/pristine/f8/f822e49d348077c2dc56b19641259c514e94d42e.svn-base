package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;

import oracle.sql.CLOB;

public class RDemanioBeneInfo extends TabellaDwhMultiProv 
{
	private String qualita;
	private String quotaProprieta;
	private String flCongelato;
	private String flAnticoPossesso;
	private BigDecimal valInventariale;
	private BigDecimal totAbitativa;
	private BigDecimal totUsiDiversi;
	private BigDecimal totUnita;
	private BigDecimal totUnitaComunali;
	private BigDecimal numBox;
	private DataDwh dataCensimento;
	private BigDecimal statoCensimento;
	private DataDwh dataRil;
	private DataDwh dataIns;
	private DataDwh dataAgg;
	private BigDecimal volumeTot;
	private BigDecimal supCop;
	private BigDecimal supTot;
	private BigDecimal supTotSlp;
	private BigDecimal supLoc;
	private BigDecimal supOper;
	private BigDecimal supCoSe;
	private BigDecimal numPianiF;
	private String numPianiI;
	private BigDecimal rendCatas;
	private BigDecimal volumeI;
	private BigDecimal volumeF;
	private BigDecimal supCommerciale;
	private BigDecimal valAcquisto;
	private BigDecimal valCatastale;
	private BigDecimal numVani;
	private BigDecimal metriQ;
	private String tipo;

	
	@Override
	public String getValueForCtrHash()
	{
		
		String hash =  this.getProvenienza() + metriQ + numVani + valCatastale + valAcquisto + supCommerciale + numPianiI + numPianiF + volumeF + volumeI + rendCatas +
				supCoSe + supOper + supLoc + supTotSlp + supTot + supCop + volumeTot +
				dataAgg.getValore() + dataIns.getValore() + dataRil.getValore() + statoCensimento + 
				dataCensimento.getValore() + numBox + totUnitaComunali + totUnita + totUsiDiversi + totAbitativa +
				valInventariale + flAnticoPossesso + flCongelato + quotaProprieta +
				qualita +  this.getIdOrig().getValore() + tipo;
		return hash;
	}


	public String getQualita() {
		return qualita;
	}


	public void setQualita(String qualita) {
		this.qualita = qualita;
	}


	public String getQuotaProprieta() {
		return quotaProprieta;
	}


	public void setQuotaProprieta(String quotaProprieta) {
		this.quotaProprieta = quotaProprieta;
	}


	public String getFlCongelato() {
		return flCongelato;
	}


	public void setFlCongelato(String flCongelato) {
		this.flCongelato = flCongelato;
	}


	public String getFlAnticoPossesso() {
		return flAnticoPossesso;
	}


	public void setFlAnticoPossesso(String flAnticoPossesso) {
		this.flAnticoPossesso = flAnticoPossesso;
	}


	public BigDecimal getValInventariale() {
		return valInventariale;
	}


	public void setValInventariale(BigDecimal valInventariale) {
		this.valInventariale = valInventariale;
	}


	public BigDecimal getTotAbitativa() {
		return totAbitativa;
	}


	public void setTotAbitativa(BigDecimal totAbitativa) {
		this.totAbitativa = totAbitativa;
	}


	public BigDecimal getTotUsiDiversi() {
		return totUsiDiversi;
	}


	public void setTotUsiDiversi(BigDecimal totUsiDiversi) {
		this.totUsiDiversi = totUsiDiversi;
	}


	public BigDecimal getTotUnita() {
		return totUnita;
	}


	public void setTotUnita(BigDecimal totUnita) {
		this.totUnita = totUnita;
	}


	public BigDecimal getTotUnitaComunali() {
		return totUnitaComunali;
	}


	public void setTotUnitaComunali(BigDecimal totUnitaComunali) {
		this.totUnitaComunali = totUnitaComunali;
	}


	public BigDecimal getNumBox() {
		return numBox;
	}


	public void setNumBox(BigDecimal numBox) {
		this.numBox = numBox;
	}


	public DataDwh getDataCensimento() {
		return dataCensimento;
	}


	public void setDataCensimento(DataDwh dataCensimento) {
		this.dataCensimento = dataCensimento;
	}


	public BigDecimal getStatoCensimento() {
		return statoCensimento;
	}


	public void setStatoCensimento(BigDecimal statoCensimento) {
		this.statoCensimento = statoCensimento;
	}


	public DataDwh getDataRil() {
		return dataRil;
	}


	public void setDataRil(DataDwh dataRil) {
		this.dataRil = dataRil;
	}


	public DataDwh getDataIns() {
		return dataIns;
	}


	public void setDataIns(DataDwh dataIns) {
		this.dataIns = dataIns;
	}


	public DataDwh getDataAgg() {
		return dataAgg;
	}


	public void setDataAgg(DataDwh dataAgg) {
		this.dataAgg = dataAgg;
	}


	public BigDecimal getVolumeTot() {
		return volumeTot;
	}


	public void setVolumeTot(BigDecimal volumeTot) {
		this.volumeTot = volumeTot;
	}


	public BigDecimal getSupCop() {
		return supCop;
	}


	public void setSupCop(BigDecimal supCop) {
		this.supCop = supCop;
	}


	public BigDecimal getSupTot() {
		return supTot;
	}


	public void setSupTot(BigDecimal supTot) {
		this.supTot = supTot;
	}


	public BigDecimal getSupTotSlp() {
		return supTotSlp;
	}


	public void setSupTotSlp(BigDecimal supTotSlp) {
		this.supTotSlp = supTotSlp;
	}


	public BigDecimal getSupLoc() {
		return supLoc;
	}


	public void setSupLoc(BigDecimal supLoc) {
		this.supLoc = supLoc;
	}


	public BigDecimal getSupOper() {
		return supOper;
	}


	public void setSupOper(BigDecimal supOper) {
		this.supOper = supOper;
	}


	public BigDecimal getSupCoSe() {
		return supCoSe;
	}


	public void setSupCoSe(BigDecimal supCoSe) {
		this.supCoSe = supCoSe;
	}


	public BigDecimal getNumPianiF() {
		return numPianiF;
	}


	public void setNumPianiF(BigDecimal numPianiF) {
		this.numPianiF = numPianiF;
	}


	public String getNumPianiI() {
		return numPianiI;
	}


	public void setNumPianiI(String numPianiI) {
		this.numPianiI = numPianiI;
	}


	public BigDecimal getRendCatas() {
		return rendCatas;
	}


	public void setRendCatas(BigDecimal rendCatas) {
		this.rendCatas = rendCatas;
	}


	public BigDecimal getVolumeI() {
		return volumeI;
	}


	public void setVolumeI(BigDecimal volumeI) {
		this.volumeI = volumeI;
	}


	public BigDecimal getVolumeF() {
		return volumeF;
	}


	public void setVolumeF(BigDecimal volumeF) {
		this.volumeF = volumeF;
	}


	public BigDecimal getSupCommerciale() {
		return supCommerciale;
	}


	public void setSupCommerciale(BigDecimal supCommerciale) {
		this.supCommerciale = supCommerciale;
	}


	public BigDecimal getValAcquisto() {
		return valAcquisto;
	}


	public void setValAcquisto(BigDecimal valAcquisto) {
		this.valAcquisto = valAcquisto;
	}


	public BigDecimal getValCatastale() {
		return valCatastale;
	}


	public void setValCatastale(BigDecimal valCatastale) {
		this.valCatastale = valCatastale;
	}


	public BigDecimal getNumVani() {
		return numVani;
	}


	public void setNumVani(BigDecimal numVani) {
		this.numVani = numVani;
	}


	public BigDecimal getMetriQ() {
		return metriQ;
	}


	public void setMetriQ(BigDecimal metriQ) {
		this.metriQ = metriQ;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
