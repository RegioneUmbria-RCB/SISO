package it.webred.ct.data.access.basic.traffico;

import it.webred.ct.data.model.traffico.SitTrffMulte;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface TrafficoService {

	/**
	 * Recupera le multe collegate al codice fiscale
	 * 
	 * @param il
	 *            codice fiscale sarsettato su obj
	 * @param il
	 *            num verbale sar settato su obj2
	 * @param la
	 *            targa sarsettata su obj3
	 * @return lista oggetti SitTrffMulte
	 */
	public List<SitTrffMulte> getListaMulteByCriteria(TrafficoDataIn dataIn)
			throws TrafficoServiceException;

	public boolean checkMulta(TrafficoDataIn dataIn)
			throws TrafficoServiceException;
}
