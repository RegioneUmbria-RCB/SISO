package it.webred.rulengine.brick.loadDwh.load.gas.bean;





public class Testata implements  it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata {

	private String identificativoFornitura;
	private String progressivoFornitura;
	private String dataFornitura;
	private String filler;
	private String areaADisposizione;
	private String carattereFineRiga;
	
	public String getIdentificativoFornitura() {
		return identificativoFornitura;
	}
	public void setIdentificativoFornitura(String identificativoFornitura) {
		this.identificativoFornitura = identificativoFornitura;
	}
	public String getProgressivoFornitura() {
		return progressivoFornitura;
	}
	public void setProgressivoFornitura(String progressivoFornitura) {
		this.progressivoFornitura = progressivoFornitura;
	}
	public String getDataFornitura() {
		return dataFornitura;
	}
	public void setDataFornitura(String dataFornitura) {
		this.dataFornitura = dataFornitura;
	}
	public String getFiller() {
		return filler;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}
	public String getAreaADisposizione() {
		return areaADisposizione;
	}
	public void setAreaADisposizione(String areaADisposizione) {
		this.areaADisposizione = areaADisposizione;
	}
	public String getCarattereFineRiga() {
		return carattereFineRiga;
	}
	public void setCarattereFineRiga(String carattereFineRiga) {
		this.carattereFineRiga = carattereFineRiga;
	}
	public String getProvenienza() {
		return "GAS";
	}

}
