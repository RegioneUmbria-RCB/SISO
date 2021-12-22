package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ISEEUniStudAttrattoCompAggiuntiva extends ISEEAgg implements Serializable {

	 
	private List<NucleoFamiliare> nucleoFamiliareAttratto = new ArrayList<NucleoFamiliare>();
	private ISEEAgg iseeAgg; 
	
	public ISEEAgg getIseeAgg() {
		return iseeAgg;
	}
	public void setIseeAgg(ISEEAgg iseeAgg) {
		this.iseeAgg = iseeAgg;
	}
	public List<NucleoFamiliare> getNucleoFamiliareAttratto() {
		return nucleoFamiliareAttratto;
	}
	public void setNucleoFamiliareAttratto(
			List<NucleoFamiliare> nucleoFamiliareAttratto) {
		this.nucleoFamiliareAttratto = nucleoFamiliareAttratto;
	}
	
	public void addNucleoFamiliareAttratto(
			 NucleoFamiliare  nucleoFamiliareAttratto) {
		this.nucleoFamiliareAttratto.add(nucleoFamiliareAttratto);
	}
	 
	
	
}
