package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.data.model.ArRelClassememoPresInps;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgAttrOption;
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
import it.webred.cs.data.model.CsIPasti;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIResiAdulti;
import it.webred.cs.data.model.CsIResiMinore;
import it.webred.cs.data.model.CsISchedaLavoro;
import it.webred.cs.data.model.CsISemiResiMin;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.data.model.VTipiInterventoUsati;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableInterventoSessionBeanRemote {

	public List<CsCTipoIntervento> findAllTipiIntervento(BaseDTO dto);

	public List<CsCTipoIntervento> findTipiInterventoCatSoc(BaseDTO dto);

	public List<CsCTipoIntervento> findTipiInterventoSettoreCatSoc(InterventoDTO dto);

	public CsIIntervento getInterventoById(BaseDTO dto) throws Exception;

	public CsFlgIntervento getFoglioInterventoById(BaseDTO dto) throws Exception;

	public CsCTipoIntervento getTipoInterventoById(BaseDTO dto) throws Exception;

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

	public CsCfgIntEseg findConfigIntErogByTipoIntervento(BaseDTO bDto);

	public CsIInterventoEseg getErogazioniEseguiteHistory(BaseDTO bDto);

	public List<CsCfgIntEsegStato> getListaIntEsegStatoByTipiStato(BaseDTO bDto);

	public CsIInterventoEseg getInterventoEsegById(BaseDTO bDto);

	public List<CsIInterventoEseg> getInterventoEsegByIntervento(BaseDTO bDto);
	
	public List<CsIInterventoEseg> getInterventoEsegByMasterId(BaseDTO bDto);
	
	public List<ErogazioneMasterDTO> searchListaErogInterventiBySettore(ErogazioniSearchCriteria bDto);

	public int countListaErogInterventiBySettore(ErogazioniSearchCriteria bDto);

	public void aggiungiInterventoEseguito(BaseDTO dto);

	//frida
	public void aggiungiInterventoEseguitoMast(BaseDTO dto);
	
	public void rimuoviInterventoEseguitoMast(BaseDTO dto);

	public void eliminaInterventoEsegStorico(BaseDTO dto);

	public CsCTipoIntervento findTipiInterventoById(BaseDTO dto);

	public List<VGerrarchiaServizi> findAllNodesTipoIntervento(BaseDTO dto);

	//frida
	public List<VLineaFin> findAllOrigineFinanziamenti(BaseDTO dto);

	public List<VTipiInterventoUsati> findAllInterventiRecenti(BaseDTO dto);

	public List<CsCTipoInterventoCustom> findTipiIntCustom(BaseDTO dto);

	public CsCTipoInterventoCustom saveNewCsCTipoInterventoCustom(BaseDTO dto);

	public void refreshTipoInterventoView(BaseDTO dto);

	public CsCTipoInterventoCustom findTipiInterventoCustomById(BaseDTO dto);

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

	public void rimuoviBeneficiariMaster(BaseDTO dto);
	
	public CsIInterventoEsegMast getErogazioneMasterById(BaseDTO dto);
	
	public CsIQuota salvaQuota(BaseDTO dto);

	public void salvaRifInterventoToPai(BaseDTO dto) throws Exception;
	
	public List<CsIInterventoEsegMastSogg> findSoggettiErogazioneSenzaCaso(BaseDTO dto);
	
	public void updateSoggettoErogazione(BaseDTO dto);
	
	public List<ArRelClassememoPresInps> findArRelClassememoPresInpbyTipoInterventoId(BaseDTO dto);

	public ArTbPrestazioniInps findArTbPrestazioniInpsByCodice(BaseDTO dto); 

	public boolean esisteInterventoErogatoByCF(BaseDTO dto);

	public List<ErogazioneBaseDTO> getListaInterventiErogatiByCF(BaseDTO dto);

	public List<InterventoBaseDTO> getListaInfoInterventiByCaso(BaseDTO dto);

	public CsIInterventoEsegMast getCsIInterventoEsegMastByByInterventoId(BaseDTO bDto);  //SISO-500  

	public List<CsCfgAttrOption> findCsCfgAttrOptions(BaseDTO bdto);

	public CsFlgIntervento getPrimoFoglioAmministrativo(BaseDTO dto);

}