package it.webred.ct.data.access.basic.docfa.dto;

import it.webred.ct.data.model.docfa.DocfaDatiCensuari;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocfaDatiCensuariDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date dataRegistrazione;
	private String dataFornituraStr;
	private String dataFornituraStr2;
	private DocfaDatiCensuari datiCensuari;
	
	SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

	public Date getDataRegistrazione(){
		dataRegistrazione = null;
		try{
			dataRegistrazione = yyyyMMdd.parse(datiCensuari.getDataRegistrazione());
		}catch(Throwable t) {}
		return dataRegistrazione;
	}
	
	public DocfaDatiCensuari getDatiCensuari() {
		return datiCensuari;
	}

	public void setDatiCensuari(DocfaDatiCensuari datiCensuari) {
		this.datiCensuari = datiCensuari;
	}

	public String getDataFornituraStr() {
		dataFornituraStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataFornituraStr =  sdf.format(datiCensuari.getFornitura());
		}
		catch(Throwable t) {}
		return dataFornituraStr;
	}
	
	public String getDataFornituraStr2() {
		dataFornituraStr2 = "";
		try {
			dataFornituraStr2 = yyyyMMdd.format(datiCensuari.getFornitura());
		}
		catch(Throwable t) {}
		return dataFornituraStr2;
	}
	
}
