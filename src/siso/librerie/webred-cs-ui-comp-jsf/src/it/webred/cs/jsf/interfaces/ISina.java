package it.webred.cs.jsf.interfaces;

import it.webred.cs.csa.ejb.dto.sina.SinaEsegDTO;

public interface ISina {

	public void copiaSina(Long id);

	public String getLabelPanelCorrente();

	public void elimina(SinaEsegDTO sina);

	public Boolean isSinaSelected(SinaEsegDTO coll);
}
