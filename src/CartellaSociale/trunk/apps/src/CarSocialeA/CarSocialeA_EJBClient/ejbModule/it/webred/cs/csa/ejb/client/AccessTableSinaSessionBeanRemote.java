package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinaEsegDTO;

import javax.ejb.Remote;

@Remote
public interface AccessTableSinaSessionBeanRemote {

	//public CsDSina newSina(BaseDTO dto);

	public SinaEsegDTO getSinaById(BaseDTO dto);
	
	public SinaEsegDTO getSinaByDiarioId(BaseDTO dto);

	public SinaEsegDTO saveSina(SinaEsegDTO dto);

	public void deleteSina(BaseDTO dto);

	public SinaEsegDTO saveNewSina(SinaEsegDTO dto);
	
	//public List<ArTbPrestazioniInps> getPrestazioniInpsSina();
}
