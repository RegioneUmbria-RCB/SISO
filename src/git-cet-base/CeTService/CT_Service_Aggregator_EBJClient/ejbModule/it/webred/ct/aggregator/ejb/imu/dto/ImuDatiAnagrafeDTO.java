package it.webred.ct.aggregator.ejb.imu.dto;

public class ImuDatiAnagrafeDTO extends ImuBaseDTO{

	private static final long serialVersionUID = 1L;
	
	private String codFisc="";
	private String cog="";
	private String nom="";
	private String datNas="";
	private String comNas="";
	private String provNas="";
	
	private int ricercaCod=1;
	
	public int getRicercaCod() {
		return ricercaCod;
	}
	public void setRicercaCod(int ricercaCod) {
		this.ricercaCod = ricercaCod;
	}
	
	public String getCog() {
		return cog;
	}
	public String getNom() {
		return nom;
	}
	public String getDatNas() {
		return datNas;
	}
	public String getComNas() {
		return comNas;
	}
	public String getProvNas() {
		return provNas;
	}
	public void setCog(String cog) {
		this.cog = cog;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setDatNas(String datNas) {
		this.datNas = datNas;
	}
	public void setComNas(String comNas) {
		this.comNas = comNas;
	}
	public void setProvNas(String provNas) {
		this.provNas = provNas;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	
	public String stampaRecord(){
		String r =  codFisc +"|"+ cog+"|"+nom+"|"+datNas+"|"+comNas+"|"+provNas+"|"+ricercaCod+"|"+super.stampaRecord();
		return r;
	}
	
}
