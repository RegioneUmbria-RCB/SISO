package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazionePrestazioneDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InformativaDTO;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioSintesiDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruResultDTO;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgIntEseg;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIAdmAdh;
import it.webred.cs.data.model.CsIAffidoFam;
import it.webred.cs.data.model.CsIBuonoSoc;
import it.webred.cs.data.model.CsICentrod;
import it.webred.cs.data.model.CsIContrEco;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIPasti;
import it.webred.cs.data.model.CsIPs;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIResiAdulti;
import it.webred.cs.data.model.CsIResiMinore;
import it.webred.cs.data.model.CsISchedaLavoro;
import it.webred.cs.data.model.CsISemiResiMin;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.VTipiInterventoUsati;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableInterventoSessionBeanRemote {

	public CsIIntervento getInterventoById(BaseDTO dto) throws Exception;

	public CsFlgIntervento getFoglioInterventoById(BaseDTO dto) throws Exception;

	public CsIIntervento salvaIntervento(BaseDTO dto) throws Exception;

	public CsFlgIntervento salvaFoglioAmministrativo(InterventoDTO dto) throws Exception;

	public List<CsIIntervento> getListaInterventiByCaso(BaseDTO dto);

	public Integer countInterventiByCaso(BaseDTO dto) throws Exception;

	public void deleteFoglioAmministrativo(BaseDTO b);

	public void deleteIntervento(BaseDTO b) throws Exception;

	public void saveRelRelazioneTipoint(BaseDTO b);

	public void deleteRelRelazioneTipointByIdRelazione(BaseDTO b);

	public List<CsIInterventoEseg> getInterventoEsegByMasterId(BaseDTO bDto);
	
	public List<ErogazioneDettaglioSintesiDTO> getSintesiErogazioniByInterventoId(BaseDTO bDto);
	
	public List<ErogazioneMasterDTO> searchListaErogInterventi(ErogazioniSearchCriteria bDto);

	public int countListaErogInterventiBySettore(ErogazioniSearchCriteria bDto);

	//frida
	public CsIInterventoEsegMast salvaInterventoEseguitoMast(BaseDTO dto);
	
	public void rimuoviInterventoEseguitoMast(BaseDTO dto) throws Exception;

	public void eliminaInterventoEsegStorico(BaseDTO dto);

	public CsCTipoInterventoCustom saveNewCsCTipoInterventoCustom(BaseDTO dto);

	public void refreshTipoInterventoView(BaseDTO dto);

	public CsICentrod findCentroDiurnoById(BaseDTO dto);

	public CsIContrEco findContributoEconomicoById(BaseDTO dto);

	public CsIPasti findPastiById(BaseDTO dto);

	public CsIVouchersad findVouherSadById(BaseDTO dto);

	public CsIBuonoSoc findBuonoSocialeById(BaseDTO dto);

	public CsIResiMinore findResiMinoreById(BaseDTO dto);

	public CsIResiAdulti findResiAdultiById(BaseDTO dto);

	public CsIAffidoFam findAffidoById(BaseDTO dto);

	public CsIAdmAdh findAdmById(BaseDTO dto);

	public CsISemiResiMin findSemiResMinoreById(BaseDTO dto);

	public CsISchedaLavoro findSchedaLavoroById(BaseDTO dto);

	public CsIInterventoPr getProgettoByMasterId(BaseDTO dto);
	
	public CsIQuota salvaQuota(BaseDTO dto);
	
	public CsIInterventoPr salvaProgettoIntervento(BaseDTO dto);

	public void salvaRifInterventoToPai(BaseDTO dto) throws Exception;
	
	public List<CsIInterventoEsegMastSogg> findSoggettiErogazioneSenzaCaso(BaseDTO dto);
	
	public List<CsIInterventoEsegMastSogg> findSoggettiErogazioneConCaso(BaseDTO bDto);
	
	public void updateSoggettoErogazione(BaseDTO dto);
	
	public boolean esisteInterventoErogatoByCF(BaseDTO dto);

	public List<ErogazioneBaseDTO> getListaInterventiErogatiByCF(BaseDTO dto);

	public List<InterventoBaseDTO> getListaInfoInterventiByCaso(BaseDTO dto);
	
	public CsIInterventoEsegMast getCsIInterventoEsegMastByInterventoId(BaseDTO bDto);  //SISO-500  
	
	public Long getCsIInterventoEsegMastIdByInterventoId(BaseDTO bDto);  
	
	public CsIInterventoEsegMast getCsIInterventoEsegMastById(BaseDTO bDto);  //SISO-822  

	public void gestisciAlertErogazioni(BaseDTO bdto) throws CarSocialeServiceException;
	
	public void salvaRifErogazioneToPai(BaseDTO bdto) throws Exception;
	
	public void eliminaExports(BaseDTO dto); //SISO - 884
	
	public  SiruResultDTO validaSiru(BaseDTO dto);
	
	public List<CsIInterventoEsegMastSogg> getBeneficiari (BaseDTO dto);
	//SISO-972
	public List<CsIInterventoEsegMastSogg> getBeneficiariByCF (BaseDTO dto);

	public List<CsIInterventoEsegMastSogg> getBeneficiariErogazione(BaseDTO dto);
	//SISO-972 Fine
	
	public List<ErogazionePrestazioneDTO> recuperaListaErogazioneDuplicateByCf(BaseDTO bdto);

	//SISO-1131
	public List<CsTbProgettoAltro> findProgettiAltro();		
	public List<CsTbProgettoAltro> findProgettiAltroPerDesc(BaseDTO dto);
	public CsTbProgettoAltro salvaProgettoAltro(BaseDTO dto);
	
	public CsIPs getCsIPsByInterventoId(BaseDTO bDto);
	
	public boolean verificaUsoArFonte(BaseDTO dto);
}