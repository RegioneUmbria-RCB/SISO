package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;

import oracle.sql.CLOB;

public class RDemanioBene extends TabellaDwhMultiProv 
{
	
	private BigDecimal pkOrig;
	private String chiavePadre;
	private BigDecimal chiave1;
	private String chiave2;
	private String chiave3;
	private String chiave4;
	private String chiave5;
	private String codEcografico;
	private BigDecimal codTipoBene;
	private String desTipoBene;
	private BigDecimal numParti;
	private CLOB descrizione;
	private CLOB note;
	private String tipo;
	
	
	@Override
	public String getValueForCtrHash()
	{
		oracle.sql.CLOB desc = (oracle.sql.CLOB)descrizione;
		oracle.sql.CLOB not = (oracle.sql.CLOB)note;
		
		String hash =  numParti + this.getIdOrig().getValore() +  chiavePadre +  pkOrig.toString() +
				chiave1 +  chiave2 +  chiave3 +  chiave4 +  chiave5 +  codEcografico +  codTipoBene +  desTipoBene + 
				this.ClobToString(desc) +"@"+ this.ClobToString(not)+this.tipo;
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


	public BigDecimal getNumParti() {
		return numParti;
	}


	public void setNumParti(BigDecimal numParti) {
		this.numParti = numParti;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
