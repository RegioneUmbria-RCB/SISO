package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO;
import it.webred.cs.data.model.view.VMobiCasi;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableInterventoErogazioneSessionBeanRemote {
	
	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findErogazioniOrganizzazione(BaseDTO dto) ;
  
	public List<VMobiCasi> findCasiByUsernameOperatore(BaseDTO dto) ;
	
	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findCasiRicercaSoggetto(BaseDTO dto) ;
	
	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findErogazioniSoggettoSelezionato(BaseDTO dto);

	public void uploadDatiMobile(BaseDTO dto) throws Exception;

	public void verificaLoadingMobileStaging(CeTBaseObject cet);
}
