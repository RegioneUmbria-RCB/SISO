package it.webred.ct.data.access.basic.versamenti.iciDM.dao;

import it.webred.ct.data.access.basic.versamenti.iciDM.VersIciDmServiceException;
import it.webred.ct.data.model.versamenti.iciDM.SitTIciDmAnag;
import it.webred.ct.data.model.versamenti.iciDM.SitTIciDmVers;
import it.webred.ct.data.model.versamenti.iciDM.SitTIciDmViolazione;

import java.util.List;

public interface VersIciDmDAO {

	public List<SitTIciDmVers> getListaVersamentiByCodFis(String codFiscale)
			throws VersIciDmServiceException;
	
	public List<SitTIciDmVers> getListaVersamentiByCodFisAnno(String codFiscale, String anno)
			throws VersIciDmServiceException;

	public List<SitTIciDmViolazione> getListaViolazioniByCodFis(String codFiscale)
			throws VersIciDmServiceException;
	
	public String getDescrizioneByCodValue (String colonna, String valore)
			throws VersIciDmServiceException;

	public SitTIciDmAnag getSoggByIdExt(String idExtAn) 
			throws VersIciDmServiceException;

	public SitTIciDmVers getVersamentoById(String id);

	public SitTIciDmViolazione getViolazioneById(String id);
}
