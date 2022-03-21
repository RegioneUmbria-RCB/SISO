package eu.smartpeg.rievazionepresenze.dto.pai;

import java.io.Serializable;
import java.util.List;

public class MinoreInStruttura implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RichiestaDisponibilitaPaiPtiDTO richDisp;
	private List<ArCsPaiPtiConsuntiDTO> listaConsuntivazioni;

	public RichiestaDisponibilitaPaiPtiDTO getRichDisp() {
		return richDisp;
	}

	public void setRichDisp(RichiestaDisponibilitaPaiPtiDTO richDisp) {
		this.richDisp = richDisp;
	}

	public List<ArCsPaiPtiConsuntiDTO> getListaConsuntivazioni() {
		return listaConsuntivazioni;
	}

	public void setListaConsuntivazioni(List<ArCsPaiPtiConsuntiDTO> listaConsuntivazioni) {
		this.listaConsuntivazioni = listaConsuntivazioni;
	}

}
