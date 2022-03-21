package it.webred.rulengine.diagnostics.bean;

public class CivicoBean {
	private Long pkidStra;
	private String prefissoVia;
	private String descrVia;
	private String civico;
	private Long pkidCivi;
	public Long getPkidStra() {
		return pkidStra;
	}
	public void setPkidStra(Long pkidStra) {
		this.pkidStra = pkidStra;
	}
	public String getPrefissoVia() {
		return prefissoVia;
	}
	public void setPrefissoVia(String prefissoVia) {
		this.prefissoVia = prefissoVia;
	}
	public String getDescrVia() {
		return descrVia;
	}
	public void setDescrVia(String descrVia) {
		this.descrVia = descrVia;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public Long getPkidCivi() {
		return pkidCivi;
	}
	public void setPkidCivi(Long pkidCivi) {
		this.pkidCivi = pkidCivi;
	}
	
	public String toString()  {
		return descrVia + ", " + civico;
	}
}
