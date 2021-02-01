package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;

import oracle.sql.CLOB;

public class RDemanioTitoli extends TabellaDwhMultiProv{
	
	private Relazione idExtBene = new Relazione(RDemanioBene.class, new ChiaveEsterna());
	private String annotazioni = "";
	private String annoAcquisizione = "";
	private String chiaveBene = "";
	private String chiave1 = "";
	private String chiave2;
	private String chiave3;
	private String chiave4;
	private String chiave5;
	private CLOB docpropContraenti = null;
	private String docpropNumero = "";
	private String docpropOrigine = "";
	private CLOB docpropPagamento = null;
	private CLOB docpropPatticond = null;
	private CLOB docpropServatt = null;
	private CLOB docpropServpass = null;
	private String idTitolo = "";
	private String normativaUtilizzata = "";
	private CLOB oggetto = null;
	private String strumentoCostruttivo = "";
	private String tipo = "";
	private String tipoAcquisizione = "";	
	private String tipoDirittoReale = "";
	private DataDwh titData = null;
	private String titRegAtti = "";
	private DataDwh titRegData = null;
	private String titRegNumero = "";
	private String titRegSerie = "";
	private String titRegUfficio = "";
	private String titRegVolume = "";
	private DataDwh trascrData = null;
	private String trascrLocalita = "";
	private String trascrNum = "";
	private String trascrNumordine = "";

	@Override
	public String getValueForCtrHash() 
	{
		oracle.sql.CLOB contraenti = (oracle.sql.CLOB)docpropContraenti;
		oracle.sql.CLOB pagamento = (oracle.sql.CLOB)docpropPagamento;
		oracle.sql.CLOB patticond = (oracle.sql.CLOB)docpropPatticond;
		oracle.sql.CLOB servatt = (oracle.sql.CLOB)docpropServatt;
		oracle.sql.CLOB servpass = (oracle.sql.CLOB)docpropServpass;
		oracle.sql.CLOB ogg = (oracle.sql.CLOB)oggetto;
		
		String hash =  this.getProvenienza() + annotazioni + annoAcquisizione + this.ClobToString(contraenti) + docpropNumero + docpropOrigine + this.ClobToString(pagamento) + this.ClobToString(patticond) + 
				this.ClobToString(servatt) + this.ClobToString(servpass) + idTitolo + normativaUtilizzata + this.ClobToString(ogg) + strumentoCostruttivo + tipo + tipoAcquisizione + tipoDirittoReale +
				titData.getValore() + titRegAtti + titRegData.getValore() + titRegNumero + titRegSerie + titRegUfficio + titRegVolume + trascrData.getValore() + trascrLocalita + trascrNum + trascrNumordine +
				this.getIdOrig().getValore() +  chiaveBene +  chiave1 +  chiave2 +  chiave3 +  chiave4 +  chiave5;
		return hash;
	}//-------------------------------------------------------------------------

	private String ClobToString(oracle.sql.CLOB clobObject )
	{
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
	}//-------------------------------------------------------------------------

	public String getAnnotazioni() {
		return annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public String getAnnoAcquisizione() {
		return annoAcquisizione;
	}

	public void setAnnoAcquisizione(String annoAcquisizione) {
		this.annoAcquisizione = annoAcquisizione;
	}

	public String getChiave1() {
		return chiave1;
	}

	public void setChiave1(String chiave1) {
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

	public CLOB getDocpropContraenti() {
		return docpropContraenti;
	}

	public void setDocpropContraenti(CLOB docpropContraenti) {
		this.docpropContraenti = docpropContraenti;
	}

	public String getDocpropNumero() {
		return docpropNumero;
	}

	public void setDocpropNumero(String docpropNumero) {
		this.docpropNumero = docpropNumero;
	}

	public String getDocpropOrigine() {
		return docpropOrigine;
	}

	public void setDocpropOrigine(String docpropOrigine) {
		this.docpropOrigine = docpropOrigine;
	}

	public CLOB getDocpropPagamento() {
		return docpropPagamento;
	}

	public void setDocpropPagamento(CLOB docpropPagamento) {
		this.docpropPagamento = docpropPagamento;
	}

	public CLOB getDocpropPatticond() {
		return docpropPatticond;
	}

	public void setDocpropPatticond(CLOB docpropPatticond) {
		this.docpropPatticond = docpropPatticond;
	}

	public CLOB getDocpropServatt() {
		return docpropServatt;
	}

	public void setDocpropServatt(CLOB docpropServatt) {
		this.docpropServatt = docpropServatt;
	}

	public CLOB getDocpropServpass() {
		return docpropServpass;
	}

	public void setDocpropServpass(CLOB docpropServpass) {
		this.docpropServpass = docpropServpass;
	}

	public String getIdTitolo() {
		return idTitolo;
	}

	public void setIdTitolo(String idTitolo) {
		this.idTitolo = idTitolo;
	}

	public String getNormativaUtilizzata() {
		return normativaUtilizzata;
	}

	public void setNormativaUtilizzata(String normativaUtilizzata) {
		this.normativaUtilizzata = normativaUtilizzata;
	}

	public CLOB getOggetto() {
		return oggetto;
	}

	public void setOggetto(CLOB oggetto) {
		this.oggetto = oggetto;
	}

	public String getStrumentoCostruttivo() {
		return strumentoCostruttivo;
	}

	public void setStrumentoCostruttivo(String strumentoCostruttivo) {
		this.strumentoCostruttivo = strumentoCostruttivo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoAcquisizione() {
		return tipoAcquisizione;
	}

	public void setTipoAcquisizione(String tipoAcquisizione) {
		this.tipoAcquisizione = tipoAcquisizione;
	}

	public String getTipoDirittoReale() {
		return tipoDirittoReale;
	}

	public void setTipoDirittoReale(String tipoDirittoReale) {
		this.tipoDirittoReale = tipoDirittoReale;
	}

	public DataDwh getTitData() {
		return titData;
	}

	public void setTitData(DataDwh titData) {
		this.titData = titData;
	}

	public String getTitRegAtti() {
		return titRegAtti;
	}

	public void setTitRegAtti(String titRegAtti) {
		this.titRegAtti = titRegAtti;
	}

	public DataDwh getTitRegData() {
		return titRegData;
	}

	public void setTitRegData(DataDwh titRegData) {
		this.titRegData = titRegData;
	}

	public String getTitRegNumero() {
		return titRegNumero;
	}

	public void setTitRegNumero(String titRegNumero) {
		this.titRegNumero = titRegNumero;
	}

	public String getTitRegSerie() {
		return titRegSerie;
	}

	public void setTitRegSerie(String titRegSerie) {
		this.titRegSerie = titRegSerie;
	}

	public String getTitRegUfficio() {
		return titRegUfficio;
	}

	public void setTitRegUfficio(String titRegUfficio) {
		this.titRegUfficio = titRegUfficio;
	}

	public String getTitRegVolume() {
		return titRegVolume;
	}

	public void setTitRegVolume(String titRegVolume) {
		this.titRegVolume = titRegVolume;
	}

	public DataDwh getTrascrData() {
		return trascrData;
	}

	public void setTrascrData(DataDwh trascrData) {
		this.trascrData = trascrData;
	}

	public String getTrascrLocalita() {
		return trascrLocalita;
	}

	public void setTrascrLocalita(String trascrLocalita) {
		this.trascrLocalita = trascrLocalita;
	}

	public String getTrascrNum() {
		return trascrNum;
	}

	public void setTrascrNum(String trascrNum) {
		this.trascrNum = trascrNum;
	}

	public String getTrascrNumordine() {
		return trascrNumordine;
	}

	public void setTrascrNumordine(String trascrNumordine) {
		this.trascrNumordine = trascrNumordine;
	}

	public Relazione getIdExtBene() {
		return idExtBene;
	}

	public void setIdExtBene(Relazione idExtBene) {
		this.idExtBene = idExtBene;
	}
	
	public void setIdExtBene(ChiaveEsterna idExt) {
		Relazione r = new Relazione(RDemanioBene.class, idExt);
		this.idExtBene = r;
	}

	public String getChiaveBene() {
		return chiaveBene;
	}

	public void setChiaveBene(String chiaveBene) {
		this.chiaveBene = chiaveBene;
	}

	
}
