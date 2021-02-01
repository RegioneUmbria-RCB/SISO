package it.webred.cs.csa.ejb.client;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazionePrestazioneDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InformativaDTO;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.SiruResultDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.ArRelClassememoPresInps;
import it.webred.cs.data.model.ArRelIntCustomIstat;
import it.webred.cs.data.model.ArRelIntCustomPresInps;
import it.webred.cs.data.model.ArTClasse;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
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
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIResiAdulti;
import it.webred.cs.data.model.CsIResiMinore;
import it.webred.cs.data.model.CsISchedaLavoro;
import it.webred.cs.data.model.CsISemiResiMin;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.VArCTariffa;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.data.model.VServiziCustom;
import it.webred.cs.data.model.VTipiInterventoUsati;
import it.webred.ct.support.datarouter.CeTBaseObject;

@Remote
public interface AccessTableInterventoSessionBeanRemote {

	public List<CsCTipoIntervento> findAllTipiIntervento(CeTBaseObject dto);
	
	public List<CsCTipoIntervento> findTipiInterventoAbilitati(CeTBaseObject dto);

	public List<CsCTipoIntervento> findTipiInterventoCatSoc(BaseDTO dto);

	public List<CsCTipoIntervento> findTipiInterventoSettoreCatSoc(InterventoDTO dto);

	public CsIIntervento getInterventoById(BaseDTO dto) throws Exception;

	public CsFlgIntervento getFoglioInterventoById(BaseDTO dto) throws Exception;

	public CsCTipoIntervento getTipoInterventoById(BaseDTO dto);

	public CsIIntervento salvaIntervento(BaseDTO dto) throws Exception;

	public CsFlgIntervento salvaFoglioAmministrativo(InterventoDTO dto) throws Exception;

	public List<CsIIntervento> getListaInterventiByCaso(BaseDTO dto) throws Exception;

	public Integer countInterventiByCaso(BaseDTO dto) throws Exception;

	public CsRelSettCsocTipoInter findRelSettCsocTipoInterById(BaseDTO bdto);

	public void deleteFoglioAmministrativo(BaseDTO b);

	public void deleteIntervento(BaseDTO b) throws Exception;

	public void saveRelRelazioneTipoint(BaseDTO b);

	public void deleteRelRelazioneTipointByIdRelazione(BaseDTO b);

	public CsCfgIntEseg findConfigurazioneInterventiEseguitiById(BaseDTO bDto);

	//public CsCfgIntEseg findConfigIntErogByTipoIntervento(BaseDTO bDto);
	
	public HashMap<Long, ErogStatoCfgDTO> findConfigIntEsegByTipoIntervento(BaseDTO bDto);

	public CsIInterventoEseg getErogazioniEseguiteHistory(BaseDTO bDto);

	public List<CsCfgIntEsegStato> getListaIntEsegStatoByTipiStato(BaseDTO bDto);

	public CsIInterventoEseg getInterventoEsegById(BaseDTO bDto);
	
	public List<CsIInterventoEseg> getInterventoEsegByMasterId(BaseDTO bDto);
	
	public List<ErogazioneMasterDTO> searchListaErogInterventi(ErogazioniSearchCriteria bDto);

	public int countListaErogInterventiBySettore(ErogazioniSearchCriteria bDto);

	public CsIInterventoEseg aggiungiInterventoEseguito(BaseDTO dto);

	//frida
	public CsIInterventoEsegMast salvaInterventoEseguitoMast(BaseDTO dto);
	
	public void rimuoviInterventoEseguitoMast(BaseDTO dto) throws Exception;

	public void eliminaInterventoEsegStorico(BaseDTO dto);

	public List<VGerrarchiaServizi> findAllNodesTipoIntervento(BaseDTO dto);

	//frida
	public List<VLineaFin> findAllOrigineFinanziamenti(BaseDTO dto);

	public List<VTipiInterventoUsati> findAllInterventiRecenti(BaseDTO dto);

	public List<CsCTipoInterventoCustom> findTipiIntCustom(CeTBaseObject dto);
	
	//SISO-1160 Inizio
	public List<it.webred.cs.csa.ejb.dto.rest.InterventoDTO> findTipiIntCustomConfigurazione(BaseDTO dto);
//Fine
	public CsCTipoInterventoCustom saveNewCsCTipoInterventoCustom(BaseDTO dto);

	public void refreshTipoInterventoView(BaseDTO dto);

	public CsCTipoInterventoCustom findTipoInterventoCustomById(BaseDTO dto);

	public CsCCategoriaSociale findCatSocialeByDescrizione(BaseDTO dto);

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

	public List<KeyValueDTO> findTipiInterventoRecenti(BaseDTO dto);

	public List<KeyValueDTO> findTipiInterventoCustomRecenti(BaseDTO dto);

	public CsIInterventoEsegMast getErogazioneMasterById(BaseDTO dto);
	
	public CsIInterventoPr getProgettoByMasterId(BaseDTO dto);
	
	public CsIQuota salvaQuota(BaseDTO dto);
	
	public CsIInterventoPr salvaProgettoIntervento(BaseDTO dto);

	public void salvaRifInterventoToPai(BaseDTO dto) throws Exception;
	
	public List<CsIInterventoEsegMastSogg> findSoggettiErogazioneSenzaCaso(BaseDTO dto);
	
	public List<CsIInterventoEsegMastSogg> findSoggettiErogazioneConCaso(BaseDTO bDto);
	
	public void updateSoggettoErogazione(BaseDTO dto);
	
	public List<ArRelClassememoPresInps> findArRelClassememoPresInpbyTipoInterventoId(BaseDTO dto);

	public boolean esisteInterventoErogatoByCF(BaseDTO dto);

	public List<ErogazioneBaseDTO> getListaInterventiErogatiByCF(BaseDTO dto);

	public List<InterventoBaseDTO> getListaInfoInterventiByCaso(BaseDTO dto);

	public CsIInterventoEsegMast getCsIInterventoEsegMastByByInterventoId(BaseDTO bDto);  //SISO-500  
	
	public CsIInterventoEsegMast getCsIInterventoEsegMastById(BaseDTO bDto);  //SISO-822  

	public List<CsCfgAttrOption> findCsCfgAttrOptions(BaseDTO bdto);

	public CsFlgIntervento getPrimoFoglioAmministrativo(BaseDTO dto); 

	public List<ArFfProgetto> findProgettiByBelfioreOrganizzazione(BaseDTO dto);		//SISO-522 - modificato SISO-575

	public CsCfgAttrUnitaMisura findAttrUnitaMisura(BaseDTO dto); 

	public InformativaDTO findInformativa(BaseDTO dto);

	public void gestisciAlertErogazioni(BaseDTO bdto) throws Exception;
	
	public void salvaRifErogazioneToPai(BaseDTO bdto) throws Exception;
	public List<ArFfProgettoAttivita> findSottocorsi(BaseDTO dto); //SISO-790
	
	public  SiruResultDTO validaSiru(BaseDTO dto);
	
	public void eliminaExports(BaseDTO dto); //SISO - 884
	
	public List<CsIInterventoEsegMastSogg> getBeneficiari (BaseDTO dto);
	//SISO-972
	public List<CsIInterventoEsegMastSogg> getBeneficiariByCF (BaseDTO dto);

	public List<CsIInterventoEsegMastSogg> getBeneficiariErogazione(BaseDTO dto);
	//SISO-972 Fine
	
	public List<ErogazionePrestazioneDTO> recuperaListaErogazioneDuplicateByCf(
			BaseDTO bdto);
	
	//SISO-1110 Inizio
	public List<VServiziCustom> findAreaTInterventoById( BaseDTO dto);
	public List<VServiziCustom> findAllServiziCustoByInterventoAndAreatId(BaseDTO dto);
	public List<ArRelIntCustomPresInps> findArRelIntCustomPresInpbyTipoInterventoId (BaseDTO dto);
	public List<VServiziCustom> findDettaglioInterventobyAreaTId(BaseDTO dto);
	public List<ArRelIntCustomIstat> findInterventoIstatByInterventoCustom(BaseDTO dto);//DA CANCELLARE
	public List<VServiziCustom> findAreaTInterventoByIdeAreaTSoggetto(BaseDTO dto);
	public List<ArTClasse> findInterventoIstatByCodice(BaseDTO dto);

	
	
	//SISO-1110 Fine
	//SISO-1162
	public List<KeyValueDTO> findTipiInterventoInps(BaseDTO dto);

	//SISO-1131
	public List<CsTbProgettoAltro> findProgettiAltro();		
	public List<CsTbProgettoAltro> findProgettiAltroPerDesc(BaseDTO dto);
	public CsTbProgettoAltro salvaProgettoAltro(BaseDTO dto);
	
	//SIO-469
	public List<VArCTariffa> findTariffe(BaseDTO dto);
}