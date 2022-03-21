package it.webred.rulengine.brick.loadDwh.load.pubblicita.bean;

public class Testata implements it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata {
	
	private String belfiore;
	private String comune;
	private String data;
	
	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getProvenienza() {
		System.out.println("TESTATA : PUBBL");
		return "PUBBL";
	}

}

