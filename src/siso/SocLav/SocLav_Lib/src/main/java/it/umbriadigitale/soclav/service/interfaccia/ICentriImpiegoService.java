package it.umbriadigitale.soclav.service.interfaccia;

import java.util.List;

import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Risposta_servizio_RDC_Type;
import it.umbriadigitale.soclav.model.anpal.RdCAnpalBeneficiario;
import it.umbriadigitale.soclav.model.privacy.RdcConsensiLavToSoc;
import it.umbriadigitale.soclav.service.dto.anpal.AnpalDomandaDTO;
import it.umbriadigitale.soclav.service.dto.sap.Lavoratore;

public interface ICentriImpiegoService {

	public RdCAnpalBeneficiario save(RdCAnpalBeneficiario t);
	public RdCAnpalBeneficiario find(String protocollo, String cf);
	public List<AnpalDomandaDTO> search(List<String> entiAbilitati, int first, int size);
	public Integer count(List<String> entiAbilitati, int first, int size);
	public Lavoratore find(String codSap);
	public boolean canViewDatiSociale(String cf, String enteFrom);
	public RdcConsensiLavToSoc salvaConsenso(String cf, Boolean val, String enteTo);
	public String aggiornaNucleoFamiliare(Risposta_servizio_RDC_Type risposta, boolean estraiSAP);
	public void aggiornaFlussoANPAL();
}
