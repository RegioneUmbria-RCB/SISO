package it.webred.ct.proc.ario.bean;

public class Civico extends Indirizzo {

	private String civLiv1;
	private String civLiv2;
	private String civLiv3;
	private String anomalia;
	
	private String note;
	
	public Civico(Object sed, Object ind, String civico) {
		super(sed, ind);
		this.civLiv1 = civico;		
	}

	public Civico() {
		super();
	}

	public String getCivLiv1() {
		return civLiv1;
	}

	public void setCivLiv1(String civico) {
		this.civLiv1 = civico;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}





	public String getCivLiv2() {
		return civLiv2;
	}

	public void setCivLiv2(String civLiv2) {
		this.civLiv2 = civLiv2;
	}

	public String getCivLiv3() {
		return civLiv3;
	}

	public void setCivLiv3(String civLiv3) {
		this.civLiv3 = civLiv3;
	}

	public String getAnomalia() {
		return anomalia;
	}

	public void setAnomalia(String anomalia) {
		this.anomalia = anomalia;
	}
	

}
