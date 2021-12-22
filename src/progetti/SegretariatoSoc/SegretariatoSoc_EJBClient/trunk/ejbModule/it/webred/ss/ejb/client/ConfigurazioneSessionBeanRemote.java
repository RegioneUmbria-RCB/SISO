package it.webred.ss.ejb.client;

import java.util.List;

import javax.ejb.Remote;

import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.dto.BaseDTO;

@Remote
public interface ConfigurazioneSessionBeanRemote {

	public void salvaUfficio(BaseDTO dto);

	public void salvaPuntoContatto(BaseDTO dto);

	public void eliminaUfficio(BaseDTO dto);

	public List<SsOOrganizzazione> getOrganizzazioniAccesso(CeTBaseObject cet);

	public List<SsUfficio> getUffici(CeTBaseObject cet);

	public List<SsPuntoContatto> getPuntiContatto(CeTBaseObject cet);

	public List<SsRelUffPcontOrg> getRelazioni(CeTBaseObject cet);

	public List<SsRelUffPcontOrg> verificaRelazioni(SsRelUffPcontOrg r);

	public void attivaRelazione(BaseDTO dto);
	
	public void disattivaRelazione(BaseDTO dto);

	public void eliminaPuntoContatto(BaseDTO dto);

	public void eliminaRelazione(BaseDTO dto);

	public void gestisciAttivazioneUfficio(BaseDTO dto);
	
	public void gestisciAttivazionePuntoContatto(BaseDTO dto);



}
