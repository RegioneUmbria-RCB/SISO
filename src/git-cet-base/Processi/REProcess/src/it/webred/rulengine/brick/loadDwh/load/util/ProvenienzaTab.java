package it.webred.rulengine.brick.loadDwh.load.util;

import java.util.ArrayList;

public class ProvenienzaTab {
	
	private boolean tabConProvenienza=false;
	private boolean tabConProvenienzaValorizzata=false;
	private ArrayList<String> provenienza = new ArrayList<String>();
	private boolean stessaProvenienza=true;
	
	
	public boolean isTabConProvenienza() {
		return tabConProvenienza;
	}
	public void setTabConProvenienza(boolean tabConProvenienza) {
		this.tabConProvenienza = tabConProvenienza;
	}
	public boolean isTabConProvenienzaValorizzata() {
		return tabConProvenienzaValorizzata;
	}
	public void setTabConProvenienzaValorizzata(boolean tabConProvenienzaValorizzata) {
		this.tabConProvenienzaValorizzata = tabConProvenienzaValorizzata;
	}
	public ArrayList<String> getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(ArrayList<String> provenienza) {
		this.provenienza = provenienza;
	}
	public boolean isStessaProvenienza() {
		return stessaProvenienza;
	}
	public void setStessaProvenienza(boolean stessaProvenienza) {
		this.stessaProvenienza = stessaProvenienza;
	}
	
	
	
}
