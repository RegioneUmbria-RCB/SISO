package it.webred.ct.data.access.basic.imu.dto;

import java.util.List;

public class ValImmobileDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String descCat;
	private String cod;
	private Double rendita;
	private Double renditaRivalutata;
	private Double valore;
	private Double quotaPoss;
	private String mesiPoss;
	private Double dovuto;
	private Double dovutoMenoDetrazione;
	private Double detrazione;
	private String storico;
	public Double getRendita() {
		return rendita;
	}
	public void setRendita(Double rendita) {
		this.rendita = rendita;
	}
	public Double getRenditaRivalutata() {
		return renditaRivalutata;
	}
	public void setRenditaRivalutata(Double renditaRivalutata) {
		this.renditaRivalutata = renditaRivalutata;
	}
	public Double getValore() {
		return valore;
	}
	public void setValore(Double valore) {
		this.valore = valore;
	}
	public Double getQuotaPoss() {
		return quotaPoss;
	}
	public void setQuotaPoss(Double quotaPoss) {
		this.quotaPoss = quotaPoss;
	}
	public String getMesiPoss() {
		return mesiPoss;
	}
	public void setMesiPoss(String mesiPoss) {
		this.mesiPoss = mesiPoss;
	}
	public Double getDovuto() {
		return dovuto;
	}
	public void setDovuto(Double dovuto) {
		this.dovuto = dovuto;
	}
	public Double getDovutoMenoDetrazione() {
		return dovutoMenoDetrazione;
	}
	public void setDovutoMenoDetrazione(Double dovutoMenoDetrazione) {
		this.dovutoMenoDetrazione = dovutoMenoDetrazione;
	}
	public Double getDetrazione() {
		return detrazione;
	}
	public void setDetrazione(Double detrazione) {
		this.detrazione = detrazione;
	}
	public String getStorico() {
		return storico;
	}
	public void setStorico(String storico) {
		this.storico = storico;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDescCat() {
		return descCat;
	}
	public void setDescCat(String descCat) {
		this.descCat = descCat;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public boolean isVuoto(){
		 
		boolean valido = (rendita!=null && rendita>0)||
        		(renditaRivalutata!=null && renditaRivalutata>0)||
        		(valore!=null && valore>0)||
        		(quotaPoss!=null && quotaPoss>0)||
        		(dovutoMenoDetrazione!=null && dovutoMenoDetrazione>0)||
        		(detrazione!=null && detrazione>0)||
        		(mesiPoss!=null && !"0".equalsIgnoreCase(mesiPoss));
        		
		return !valido;
		
	}
	
	
		
}
