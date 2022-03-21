package it.webred.ct.proc.ario.bean;

public class Oggetto {

	private String foglio;
	private String particella;
	private String sub;
	private String anomalia;
	
	
	//Costruttore
	public Oggetto(String f, String p, String s) {
		
		this.foglio = f;
		this.particella = p;
		this.sub = s;
		
	}
	
	
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}


	public String getAnomalia() {
		return anomalia;
	}


	public void setAnomalia(String anomalia) {
		this.anomalia = anomalia;
	}
	
}
