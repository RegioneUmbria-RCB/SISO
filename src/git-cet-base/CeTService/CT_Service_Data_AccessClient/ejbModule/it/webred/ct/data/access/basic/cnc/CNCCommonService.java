package it.webred.ct.data.access.basic.cnc;

import javax.ejb.Remote;

@Remote
public interface CNCCommonService {

	public String getCodiceTipoEntrataDescr(CNCDTO dto);
	
	public String getCodiceUfficioDescr(CNCDTO dto);

	public String getCodiceUfficioDescrDaPartita(CNCDTO dto);
	
	public String getCodiceUfficioDescrFull(CNCDTO dto);  

	public String getCodiceEnte(CNCDTO dto);  
}
