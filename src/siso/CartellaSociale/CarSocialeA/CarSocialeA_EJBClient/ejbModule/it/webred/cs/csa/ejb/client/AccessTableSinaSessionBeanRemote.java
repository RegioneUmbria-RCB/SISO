package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinaEsegDTO;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsDSinaLIGHT;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableSinaSessionBeanRemote {

	public SinaEsegDTO getSinaById(BaseDTO dto);
	
	public SinaEsegDTO getSinaByDiarioId(BaseDTO dto);

	public Long saveSina(SinaEsegDTO dto);
	
	public void deleteSina(BaseDTO dto);

	public List<CsDSina> findDiarioSinaByMastId(BaseDTO dto); //SISO-783
	
	public HashMap<Long,CsDSinaLIGHT> getSinaByMastIds(BaseDTO dto); //SISO-784
	
	public List<CsDSina> findSinaByCaso(BaseDTO dto); //SISO-783
	
	public Date findMinDateSinaCollegatiByMastId(BaseDTO dto); //SISO-783

	public List<ArTbPrestazioniInps> getPrestazioniInpsSina(BaseDTO dto); //Sottoinsieme della lista prestazioni INPS (per il SINA)
	
	public List<CsDSina> findSinaCollegabiliByCf(BaseDTO dto); //SISO-783

	public SinaEsegDTO clonaSinaById(BaseDTO dto);

}
