package it.escsolution.escwebgis.catasto.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;

public class DatiMetrici extends EscObject implements Serializable {

	//Stima superficie catastale (A+B+C)*0,8
	private String supTarsuABC;
	
	//Dati Sitiedi_uiu_ext 
	private String codStatoCat;
	private String descStatoCat;
	private String supCatTarsu;
	private String supDPR138;
	private String vanoMedioDPR138;
	
	public String getCodStatoCat() {
		return codStatoCat;
	}

	public void setCodStatoCat(String codStatoCat) {
		this.codStatoCat = codStatoCat;
	}

	public String getSupCatTarsu() {
		return supCatTarsu;
	}

	public void setSupCatTarsu(String supCatTarsu) {
		this.supCatTarsu = supCatTarsu;
	}

	public String getDescStatoCat() {
		
		descStatoCat = "";
		if(codStatoCat!=null ){
			if(!" ".equals(codStatoCat)){
	
			int codice = Integer.valueOf(codStatoCat);
			switch(codice){
				case 1 : descStatoCat = "Superficie calcolata"; break;
				case 2 : descStatoCat = "Superficie non calcolabile"; break;
				case 3 : descStatoCat = "Superficie in corso di definizione"; break;
				case 4 : descStatoCat = "Planimetria non presente in atti"; break;
				default : descStatoCat ="-";
			};
		
		}else
			descStatoCat="-";
		}
		
		return descStatoCat;
	}

	public String getSupDPR138() {
		return supDPR138;
	}

	public void setSupDPR138(String supDPR138) {
		this.supDPR138 = supDPR138;
	}

	public String getVanoMedioDPR138() {
		return vanoMedioDPR138;
	}

	public void setVanoMedioDPR138(String vanoMedioDPR138) {
		this.vanoMedioDPR138 = vanoMedioDPR138;
	}

	public void setDescStatoCat(String descStatoCat) {
		this.descStatoCat = descStatoCat;
	}

	public String getSupTarsuABC() {
		return supTarsuABC;
	}

	public void setSupTarsuABC(String supTarsuABC) {
		this.supTarsuABC = supTarsuABC;
	}
	
}
