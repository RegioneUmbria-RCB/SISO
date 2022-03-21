package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ISEEMin implements Serializable {

	private List<ComponenteMinorenne> componentiMinoorenni = new ArrayList<ComponenteMinorenne>();

	public List<ComponenteMinorenne> getComponentiMinoorenni() {
		return componentiMinoorenni;
	}

	public void setComponentiMinoorenni(
			List<ComponenteMinorenne> componentiMinoorenni) {
		this.componentiMinoorenni = componentiMinoorenni;
	}
	
}
