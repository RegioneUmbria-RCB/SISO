package it.siso.isee.obj;

import java.io.Serializable;

public class ComponenteMinorenne implements Serializable{

	private AnagraficaAgg attributi;
	private String iseeOrd;
	
	private String iseeNonCalcolabile;
	
	private ISEEAgg iseeMinAttr;
	
	private ISEEAgg iseeMinAgg;

	public AnagraficaAgg getAttributi() {
		return attributi;
	}

	public void setAttributi(AnagraficaAgg attributi) {
		this.attributi = attributi;
	}

	public String getIseeOrd() {
		return iseeOrd;
	}

	public void setIseeOrd(String iseeOrd) {
		this.iseeOrd = iseeOrd;
	}

	public String getIseeNonCalcolabile() {
		return iseeNonCalcolabile;
	}

	public void setIseeNonCalcolabile(String iseeNonCalcolabile) {
		this.iseeNonCalcolabile = iseeNonCalcolabile;
	}

	public ISEEAgg getIseeMinAttr() {
		return iseeMinAttr;
	}

	public void setIseeMinAttr(ISEEAgg iseeMinAttr) {
		this.iseeMinAttr = iseeMinAttr;
	}

	public ISEEAgg getIseeMinAgg() {
		return iseeMinAgg;
	}

	public void setIseeMinAgg(ISEEAgg iseeMinAgg) {
		this.iseeMinAgg = iseeMinAgg;
	}
	
	
	 
	
	
}
