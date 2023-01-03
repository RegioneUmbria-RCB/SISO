package it.webred.cs.csa.ejb.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.sina.SinaEsegDTO;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsDSinaLIGHT;

@Remote
public interface AccessTableSinaSessionBeanRemote {

	public SinaEsegDTO getSinaById(BaseDTO dto);
	
	public SinaEsegDTO getSinaByDiarioId(BaseDTO dto);

	public Long saveSina(SinaEsegDTO dto);
	
	public void deleteSina(BaseDTO dto);

	public List<SinaEsegDTO> findDiarioSinaByMastId(BaseDTO dto); //SISO-783
	
	public HashMap<Long,CsDSinaLIGHT> getSinaByMastIds(BaseDTO dto); //SISO-784
	
	public List<SinaEsegDTO> findSinaByCaso(BaseDTO dto); //SISO-783
	
	public Date findMinDateSinaCollegatiByMastId(BaseDTO dto); //SISO-783

	public List<ArTbPrestazioniInps> getPrestazioniInpsSina(BaseDTO dto); //Sottoinsieme della lista prestazioni INPS (per il SINA)
	
	public List<SinaEsegDTO> findSinaCollegabiliByCf(BaseDTO dto); //SISO-783

	public SinaEsegDTO clonaSinaById(BaseDTO dto);

	public boolean canDeleteSina(BaseDTO dto);

}
