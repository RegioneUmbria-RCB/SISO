package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ISEEUniStudAttrattoCompAttratta implements Serializable {

	private List<NucleoFamiliare> nucleoFamiliareAttratto = new ArrayList<NucleoFamiliare>();
	private ISEEAgg iseeAgg; 
	 
 
	public void addNucleoFamiliareDTO(NucleoFamiliare nucleoFamiliareDTO) {
		this.nucleoFamiliareAttratto.add(nucleoFamiliareDTO);
	}


	public List<NucleoFamiliare> getNucleoFamiliareAttratto() {
		return nucleoFamiliareAttratto;
	}


	public void setNucleoFamiliareAttratto(
			List<NucleoFamiliare> nucleoFamiliareAttratto) {
		this.nucleoFamiliareAttratto = nucleoFamiliareAttratto;
	}


	public ISEEAgg getIseeAgg() {
		return iseeAgg;
	}


	public void setIseeAgg(ISEEAgg iseeAgg) {
		this.iseeAgg = iseeAgg;
	}
	 
	 
}
