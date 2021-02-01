package it.webred.ct.data.access.basic.docfa;

import it.webred.ct.data.access.aggregator.elaborazioni.dto.ZonaDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.docfa.dto.BeniNonCensDTO;
import it.webred.ct.data.access.basic.docfa.dto.DatiDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.DatiGeneraliDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.DettaglioDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.DocfaDatiCensuariDTO;
import it.webred.ct.data.access.basic.docfa.dto.DocfaInParteUnoDTO;
import it.webred.ct.data.access.basic.docfa.dto.FoglioMicrozonaDTO;
import it.webred.ct.data.access.basic.docfa.dto.OperatoreDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.model.docfa.DocfaDatiCensuari;
import it.webred.ct.data.model.docfa.DocfaDatiGenerali;
import it.webred.ct.data.model.docfa.DocfaDatiMetrici;
import it.webred.ct.data.model.docfa.DocfaDichiaranti;
import it.webred.ct.data.model.docfa.DocfaFogliMicrozona;
import it.webred.ct.data.model.docfa.DocfaInParteDueH;
import it.webred.ct.data.model.docfa.DocfaIntestati;
import it.webred.ct.data.model.docfa.DocfaPlanimetrie;
import it.webred.ct.data.model.docfa.DocfaUiu;
import it.webred.ct.data.model.docfa.DocfaValori;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface DocfaService {
	
	public List<String> getSuggestionVieDocfaUiu(RicercaCivicoDTO rc);
	public List<String> getSuggestionCiviciByViaDocfaUiu(RicercaCivicoDTO rc);
	public List<String> getSuggestionDichiarante(RicercaSoggettoDTO rs);
	public List<Date> getListaForniture(RicercaOggettoDocfaDTO ro);
		
	public List<DocfaUiu> getListaDocfaUiuByUI(RicercaOggettoDocfaDTO ro);
	public List<DocfaDatiCensuari> getListaDocfaImmobile(RicercaOggettoDocfaDTO ro);
	public List<DatiDocfaDTO> getListaDatiDocfaUI(RicercaOggettoDocfaDTO ro);
	public List<DatiGeneraliDocfaDTO> getListaDatiDocfaFabbricato(RicercaOggettoDocfaDTO ro);
	public List<DatiGeneraliDocfaDTO> getListaDatiDocfaImmobile(RicercaOggettoDocfaDTO ro);
	public List<DocfaDichiaranti> getDichiaranti(RicercaOggettoDocfaDTO dataIn);
	public List<DocfaIntestati> getIntestati(RicercaOggettoDocfaDTO dataIn);
	public List<DocfaDatiMetrici> getDatiMetrici(RicercaOggettoDocfaDTO rs);
	public DocfaInParteDueH getDatiMetriciABC(RicercaOggettoDocfaDTO rs);
	public List<DocfaPlanimetrie> getPlanimetrieDocfa(RicercaOggettoDocfaDTO rs);
	public List<DocfaPlanimetrie> getPlanimetriePerDocfa(RicercaOggettoDocfaDTO rs);
	public List<DocfaPlanimetrie> getPlanimetriePerSezFglNum(RicercaOggettoDocfaDTO rs);
	public List<DocfaDatiCensuari> getDocfaDatiCensuariPerNomePlan(RicercaOggettoDocfaDTO rs);
	public List<DocfaUiu> getListaDocfaUiu(RicercaOggettoDocfaDTO ro) ;
	public List<DocfaDatiCensuariDTO> getListaDatiCensuari(RicercaOggettoDocfaDTO rod);
	public DettaglioDocfaDTO getDettaglioDocfa(RicercaOggettoDocfaDTO rod);

	public List<FoglioMicrozonaDTO> getFoglioMicrozona(ZonaDTO zonaDTO);
	public List<String> getDocfaFogliMicrozona_ListaFogli(CeTBaseObject o);
	public List<DocfaDatiGenerali> getDocfaDatiGenerali(RicercaOggettoDocfaDTO rs);
	public List<DocfaInParteUnoDTO> getListaDocfaInParteUno(RicercaOggettoDocfaDTO rod);
	public List<BeniNonCensDTO> getListaBeniNonCensibili(RicercaOggettoDocfaDTO rod);
	public List<OperatoreDTO> getListaOperatori(RicercaOggettoDocfaDTO rod);
	
	public Date[] getLastSitPlanimetrie(RicercaOggettoDocfaDTO rod);
	
	//GITOUT WS 4.0
	public List<DocfaDatiCensuari> getDocfaDatiCensuari(RicercaOggettoDocfaDTO ro);
//	public List<DocfaValori> getDocfaValori(RicercaOggettoDocfaDTO ro);
	
	
}
