package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ISEEUniStudAttratto extends ISEEAgg implements Serializable{

	private List<ElementoNucleo> elementiNucleoAttratto;

	public List<ElementoNucleo> getElementiNucleoAttratto() {
		return elementiNucleoAttratto;
	}

	 public void addElementoNucleo(ElementoNucleo elementoNucleo) {
		 this.elementiNucleoAttratto = this.elementiNucleoAttratto == null ? new ArrayList<ElementoNucleo>() : this.elementiNucleoAttratto;
		 this.elementiNucleoAttratto.add(elementoNucleo);
	 }
	
	
}
