package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;

import oracle.sql.CLOB;

public class RDemanioTitoli extends TabellaDwh 
{
	private BigDecimal pkOrig;
	private String chiavePadre;
	private BigDecimal chiave1;
	private String chiave2;
	private String chiave3;
	private String chiave4;
	private String chiave5;
	private String codEcografico;
	private BigDecimal codInventario;
	private String codTipo;
	private String desTipo;
	private String codCartella;
	private String desCartella;
	private BigDecimal codTipoBene;
	private String desTipoBene;
	private BigDecimal codTipoProprieta;
	private String desTipoProprieta;
	private BigDecimal codTipoUso;
	private String desTipoUso;
	private CLOB descrizione;
	private CLOB note;
	private BigDecimal codCatInventariale;
	private String desCatInventariale;
	private String codCategoria;
	private String desCategoria;
	private String codSottoCategoria;
	private String desSottoCategoria;
	private String qualita;
	private String quotaProprieta;
	private BigDecimal flVendita;
	private String flCongelato;
	private String flAnticoPossesso;
	private DataDwh dataVendita;
	private String annoAcq;
	private String annoCostr;
	private String annoRistr;
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
	private String mefTipologia;
	private String mefUtilizzo;
	private String mefFinalita;
	private String mefNaturaGiuridica;
	private BigDecimal numParti;
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
	private String provenienza;
	private String tipo;

	
	@Override
	public String getValueForCtrHash()
	{
		oracle.sql.CLOB desc = (oracle.sql.CLOB)descrizione;
		oracle.sql.CLOB not = (oracle.sql.CLOB)note;
		
		String hash =  provenienza + metriQ + numVani + valCatastale + valAcquisto + supCommerciale + numPianiI + numPianiF + volumeF + volumeI + rendCatas +
				supCoSe + supOper + supLoc + supTotSlp + supTot + supCop + volumeTot + numParti + mefNaturaGiuridica + mefFinalita + mefUtilizzo + mefTipologia+ 
				dataAgg.getValore() + dataIns.getValore() + dataRil.getValore() + statoCensimento + 
				dataCensimento.getValore() + numBox + totUnitaComunali + totUnita + totUsiDiversi + totAbitativa +
				valInventariale + annoRistr + annoCostr + annoAcq + dataVendita.getValore() + flAnticoPossesso + flCongelato + flVendita + quotaProprieta +
				qualita + codSottoCategoria + codCategoria + codCatInventariale + this.getIdOrig().getValore() +  chiavePadre +  pkOrig.toString() +
				chiave1 +  chiave2 +  chiave3 +  chiave4 +  chiave5 +  codEcografico +  codInventario +  codTipo +  desTipo +  tipo +
				codCartella +  desCartella +  codTipoBene +  desTipoBene +  codTipoProprieta +  desTipoProprieta +  codTipoUso +  desTipoUso + this.ClobToString(desc) +"@"+ this.ClobToString(not);
		return hash;
	}


	private String ClobToString(oracle.sql.CLOB clobObject ){
		String s = "";
		InputStream in;
		StringBuilder sb = new StringBuilder();
		try {
			if(clobObject!=null){
			 	Reader reader = clobObject.getCharacterStream();
		        BufferedReader br = new BufferedReader(reader);
		        String line;
		        while(null != (line = br.readLine())) 
		            sb.append(line);
		        
		        s = sb.toString();
			}
		
		} catch (Exception e) {
			log.error(e);
		}
		
		return s;
		
	}



	public BigDecimal getPkOrig() {
		return pkOrig;
	}






	public void setPkOrig(BigDecimal pkOrig) {
		this.pkOrig = pkOrig;
	}






	public String getChiavePadre() {
		return chiavePadre;
	}


	public void setChiavePadre(String chiavePadre) {
		this.chiavePadre = chiavePadre;
	}


	public BigDecimal getChiave1() {
		return chiave1;
	}


	public void setChiave1(BigDecimal chiave1) {
		this.chiave1 = chiave1;
	}


	public String getChiave2() {
		return chiave2;
	}


	public void setChiave2(String chiave2) {
		this.chiave2 = chiave2;
	}


	public String getChiave3() {
		return chiave3;
	}


	public void setChiave3(String chiave3) {
		this.chiave3 = chiave3;
	}


	public String getChiave4() {
		return chiave4;
	}


	public void setChiave4(String chiave4) {
		this.chiave4 = chiave4;
	}


	public String getChiave5() {
		return chiave5;
	}


	public void setChiave5(String chiave5) {
		this.chiave5 = chiave5;
	}


	public String getCodEcografico() {
		return codEcografico;
	}


	public void setCodEcografico(String codEcografico) {
		this.codEcografico = codEcografico;
	}


	
	public BigDecimal getCodInventario() {
		return codInventario;
	}


	public void setCodInventario(BigDecimal codInventario) {
		this.codInventario = codInventario;
	}


	public String getCodTipo() {
		return codTipo;
	}


	public void setCodTipo(String codTipo) {
		this.codTipo = codTipo;
	}


	public String getDesTipo() {
		return desTipo;
	}


	public void setDesTipo(String desTipo) {
		this.desTipo = desTipo;
	}


	public String getCodCartella() {
		return codCartella;
	}


	public void setCodCartella(String codCartella) {
		this.codCartella = codCartella;
	}


	public String getDesCartella() {
		return desCartella;
	}


	public void setDesCartella(String desCartella) {
		this.desCartella = desCartella;
	}


	public BigDecimal getCodTipoBene() {
		return codTipoBene;
	}


	public void setCodTipoBene(BigDecimal codTipoBene) {
		this.codTipoBene = codTipoBene;
	}


	public String getDesTipoBene() {
		return desTipoBene;
	}


	public void setDesTipoBene(String desTipoBene) {
		this.desTipoBene = desTipoBene;
	}


	public BigDecimal getCodTipoProprieta() {
		return codTipoProprieta;
	}


	public void setCodTipoProprieta(BigDecimal codTipoProprieta) {
		this.codTipoProprieta = codTipoProprieta;
	}


	public String getDesTipoProprieta() {
		return desTipoProprieta;
	}


	public void setDesTipoProprieta(String desTipoProprieta) {
		this.desTipoProprieta = desTipoProprieta;
	}

	public String getDesTipoUso() {
		return desTipoUso;
	}


	public void setDesTipoUso(String desTipoUso) {
		this.desTipoUso = desTipoUso;
	}


	public CLOB getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(CLOB descrizione) {
		this.descrizione = descrizione;
	}


	public CLOB getNote() {
		return note;
	}


	public void setNote(CLOB note) {
		this.note = note;
	}


	public BigDecimal getCodCatInventariale() {
		return codCatInventariale;
	}


	public void setCodCatInventariale(BigDecimal codCatInventariale) {
		this.codCatInventariale = codCatInventariale;
	}


	public String getDesCatInventariale() {
		return desCatInventariale;
	}


	public void setDesCatInventariale(String desCatInventariale) {
		this.desCatInventariale = desCatInventariale;
	}


	public String getCodCategoria() {
		return codCategoria;
	}


	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}


	public String getDesCategoria() {
		return desCategoria;
	}


	public void setDesCategoria(String desCategoria) {
		this.desCategoria = desCategoria;
	}


	public String getCodSottoCategoria() {
		return codSottoCategoria;
	}


	public void setCodSottoCategoria(String codSottoCategoria) {
		this.codSottoCategoria = codSottoCategoria;
	}


	public String getDesSottoCategoria() {
		return desSottoCategoria;
	}


	public void setDesSottoCategoria(String desSottoCategoria) {
		this.desSottoCategoria = desSottoCategoria;
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


	public BigDecimal getFlVendita() {
		return flVendita;
	}


	public void setFlVendita(BigDecimal flVendita) {
		this.flVendita = flVendita;
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

	public String getAnnoAcq() {
		return annoAcq;
	}


	public void setAnnoAcq(String annoAcq) {
		this.annoAcq = annoAcq;
	}


	public String getAnnoCostr() {
		return annoCostr;
	}


	public void setAnnoCostr(String annoCostr) {
		this.annoCostr = annoCostr;
	}


	public String getAnnoRistr() {
		return annoRistr;
	}


	public void setAnnoRistr(String annoRistr) {
		this.annoRistr = annoRistr;
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


	public BigDecimal getStatoCensimento() {
		return statoCensimento;
	}


	public void setStatoCensimento(BigDecimal statoCensimento) {
		this.statoCensimento = statoCensimento;
	}



	public String getMefTipologia() {
		return mefTipologia;
	}


	public void setMefTipologia(String mefTipologia) {
		this.mefTipologia = mefTipologia;
	}


	public String getMefUtilizzo() {
		return mefUtilizzo;
	}


	public void setMefUtilizzo(String mefUtilizzo) {
		this.mefUtilizzo = mefUtilizzo;
	}


	public String getMefFinalita() {
		return mefFinalita;
	}


	public void setMefFinalita(String mefFinalita) {
		this.mefFinalita = mefFinalita;
	}


	public String getMefNaturaGiuridica() {
		return mefNaturaGiuridica;
	}


	public void setMefNaturaGiuridica(String mefNaturaGiuridica) {
		this.mefNaturaGiuridica = mefNaturaGiuridica;
	}


	

	public BigDecimal getNumParti() {
		return numParti;
	}


	public void setNumParti(BigDecimal numParti) {
		this.numParti = numParti;
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


	public BigDecimal getNumPianiF() {
		return numPianiF;
	}


	public void setNumPianiF(BigDecimal numPianiF) {
		this.numPianiF = numPianiF;
	}

	public String getProvenienza() {
		return provenienza;
	}


	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}


	public BigDecimal getCodTipoUso() {
		return codTipoUso;
	}


	public void setCodTipoUso(BigDecimal codTipoUso) {
		this.codTipoUso = codTipoUso;
	}


	public BigDecimal getVolumeTot() {
		return volumeTot;
	}


	public void setVolumeTot(BigDecimal volumeTot) {
		this.volumeTot = volumeTot;
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


	public DataDwh getDataVendita() {
		return dataVendita;
	}

	public void setDataVendita(DataDwh dataVendita) {
		this.dataVendita = dataVendita;
	}

	public DataDwh getDataCensimento() {
		return dataCensimento;
	}

	public void setDataCensimento(DataDwh dataCensimento) {
		this.dataCensimento = dataCensimento;
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


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
