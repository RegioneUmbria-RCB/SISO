package it.webred.ct.aggregator.ejb.imu.dto;

public class ImuDatiElaborazioneDTO extends ImuBaseDTO{
	
	private static final long serialVersionUID = 1L;

	private String codFisc="";
	private String json="";
	
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	public String stampaRecord(){
		return   codFisc+"|"+json+"|"+super.stampaRecord();
	}
	
}
