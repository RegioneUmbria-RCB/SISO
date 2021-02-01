package it.webred.ct.data.access.basic.docfa;

import it.webred.ct.data.access.aggregator.elaborazioni.dto.ZonaDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.docfa.dao.DocfaDAO;
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
import it.webred.ct.data.model.docfa.DocfaInParteDueH;
import it.webred.ct.data.model.docfa.DocfaIntestati;
import it.webred.ct.data.model.docfa.DocfaPlanimetrie;
import it.webred.ct.data.model.docfa.DocfaUiu;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;
  
@Stateless
public class DocfaServiceBean extends DocfaBaseService implements DocfaService {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DocfaDAO docfaDAO;
	
	public List<Date> getListaForniture(RicercaOggettoDocfaDTO ro){
		return docfaDAO.getListaForniture();
	}
	
	public List<String> getSuggestionVieDocfaUiu(RicercaCivicoDTO rc){
		return docfaDAO.getSuggestionVieDocfaUiu(rc.getDescrizioneVia());
	}
	
	public List<String> getSuggestionCiviciByViaDocfaUiu(RicercaCivicoDTO rc){
		return docfaDAO.getSuggestionCiviciByViaDocfaUiu(rc.getDescrizioneVia());
	}
	public List<String> getSuggestionDichiarante(RicercaSoggettoDTO rs){
		return docfaDAO.getSuggestionDichiarante(rs.getDenom());
		
	}
	
	public List<DocfaUiu> getListaDocfaUiuByUI(RicercaOggettoDocfaDTO ro){
		return docfaDAO.getListaDocfaUiuByUI(ro);
	}
	
	public List<DocfaDatiCensuari> getListaDocfaImmobile(RicercaOggettoDocfaDTO ro) {		
		return docfaDAO.getListaDocfaImmobile(ro);
	}
	
	public List<DatiDocfaDTO> getListaDatiDocfaUI(RicercaOggettoDocfaDTO ro) {		
		if (ro.getUnimm()!=null && !ro.getUnimm().equals(""))
			return docfaDAO.getListaDatiDocfaUiu(ro);
		else
			return docfaDAO.getListaDatiDocfaFPSubNonVal(ro);
	}
	
	public List<DocfaDichiaranti> getDichiaranti(RicercaOggettoDocfaDTO dataIn){
		Date fornitura = dataIn.getFornitura();
		String protocollo = dataIn.getProtocollo();
		return docfaDAO.getDichiaranti(fornitura, protocollo);
	}

	public List<DocfaIntestati> getIntestati(RicercaOggettoDocfaDTO dataIn){
		
		Date fornitura = dataIn.getFornitura();
		String protocollo = dataIn.getProtocollo();
		return docfaDAO.getIntestati(fornitura, protocollo);
	}
	
	
	public DocfaInParteDueH getDatiMetriciABC(RicercaOggettoDocfaDTO ro){
		return docfaDAO.getDatiMetriciABC(ro);
	}
	
	public List<DocfaDatiMetrici> getDatiMetrici(RicercaOggettoDocfaDTO ro){
		return docfaDAO.getDatiMetrici(ro);
	}
	
	
	public List<DocfaPlanimetrie> getPlanimetrieDocfa(RicercaOggettoDocfaDTO ro){
		return docfaDAO.getPlanimetrieDocfa(ro);
	}
	

	public List<DocfaPlanimetrie> getPlanimetriePerDocfa(RicercaOggettoDocfaDTO ro){
		return docfaDAO.getPlanimetriePerDocfa(ro);
	}
	
	public List<DocfaPlanimetrie> getPlanimetriePerSezFglNum(RicercaOggettoDocfaDTO ro) {
		return docfaDAO.getPlanimetriePerSezFglNum(ro);
	}
	
	public List<DocfaDatiCensuari> getDocfaDatiCensuariPerNomePlan(RicercaOggettoDocfaDTO ro) {
		return docfaDAO.getDocfaDatiCensuariPerNomePlan(ro);
	}
	
	@Override
	public List<DatiGeneraliDocfaDTO> getListaDatiDocfaFabbricato(RicercaOggettoDocfaDTO ro) {
		return docfaDAO.getListaDatiGeneraliFabbricato(ro);
	}
	
	@Override
	public List<DatiGeneraliDocfaDTO> getListaDatiDocfaImmobile(RicercaOggettoDocfaDTO ro) {
		return docfaDAO.getListaDatiGeneraliByImmobile(ro);
	}
	
	public List<DocfaUiu> getListaDocfaUiu(RicercaOggettoDocfaDTO ro) {
		return docfaDAO.getListaDocfaUiu(ro);
	}
	
	@Override
	public List<DocfaDatiCensuariDTO> getListaDatiCensuari(RicercaOggettoDocfaDTO rod) {
		List<DocfaDatiCensuariDTO> listadto = new ArrayList<DocfaDatiCensuariDTO> ();
		String protocollo = rod.getProtocollo();
		Date fornitura = rod.getFornitura();
		if(protocollo!=null && fornitura!=null){
			List<DocfaDatiCensuari> lista = docfaDAO.getListaDatiCensuariDocfa(fornitura, protocollo);
			for(DocfaDatiCensuari ddc:lista){
				DocfaDatiCensuariDTO dto = new DocfaDatiCensuariDTO();
				dto.setDatiCensuari(ddc);
				listadto.add(dto);
			}
		}
		return listadto;
	}

	@Override
	public List<DocfaDatiGenerali> getDocfaDatiGenerali(RicercaOggettoDocfaDTO rod){
		List<DocfaDatiGenerali> list = new ArrayList<DocfaDatiGenerali>();
		
		String protocollo = rod.getProtocollo();
		Date fornitura = rod.getFornitura();
		if(protocollo!=null && fornitura!=null)
			list =  docfaDAO.getDocfaDatiGenerali(fornitura, protocollo);
		return list;
	}
		
	@Override
	public List<DocfaInParteUnoDTO> getListaDocfaInParteUno(RicercaOggettoDocfaDTO rod){
		List<DocfaInParteUnoDTO> lista = new ArrayList<DocfaInParteUnoDTO>();
		
		String protocollo = rod.getProtocollo();
		Date fornitura = rod.getFornitura();
		if(protocollo!=null && fornitura!=null)
			lista = docfaDAO.getListaDocfaInParteUno(fornitura,protocollo);
		
		return lista;
		
	}
	
	@Override
	public List<BeniNonCensDTO> getListaBeniNonCensibili(RicercaOggettoDocfaDTO rod){
		List<BeniNonCensDTO> lista = new ArrayList<BeniNonCensDTO>();
		
		String protocollo = rod.getProtocollo();
		Date fornitura = rod.getFornitura();
		if(protocollo!=null && fornitura!=null)
			lista = docfaDAO.getListaBeniNonCensibili(fornitura,protocollo);
		
		return lista;
		
	}
	
	@Override
	public List<OperatoreDTO> getListaOperatori(RicercaOggettoDocfaDTO rod){
		List<OperatoreDTO> lista = new ArrayList<OperatoreDTO>();
		
		String protocollo = rod.getProtocollo();
		Date fornitura = rod.getFornitura();
		if(protocollo!=null && fornitura!=null)
			lista = docfaDAO.getListaOperatori(fornitura, protocollo);
		
		return lista;
		
	}
	
	@Override
	public DettaglioDocfaDTO getDettaglioDocfa(RicercaOggettoDocfaDTO rod) {
		DettaglioDocfaDTO docfa = new DettaglioDocfaDTO();
		String protocollo = rod.getProtocollo();
		Date fornitura = rod.getFornitura();
		if(protocollo!=null && fornitura!=null){
			List<DocfaDatiGenerali> dg = docfaDAO.getDocfaDatiGenerali(fornitura, protocollo);
			docfa.setDatiGenerali((dg!=null && dg.size()>0) ? dg.get(0):null);
			
			docfa.setAnnotazioni(docfaDAO.getAnnotazioni(fornitura, protocollo));
			docfa.setDichiaranti(docfaDAO.getDichiaranti(fornitura, protocollo));
			docfa.setIntestati(docfaDAO.getIntestati(fornitura, protocollo));
			docfa.setDatiCensuari(getListaDatiCensuari(rod));
			docfa.setListaUiu(docfaDAO.getListaDocfaUiu(fornitura, protocollo));
			docfa.setListaParteDueH(docfaDAO.getListaDocfaInParteDue(protocollo, fornitura));
		
		}
		
		return docfa;
	}

	@Override
	public List<FoglioMicrozonaDTO> getFoglioMicrozona(ZonaDTO zonaDTO) {
		if(zonaDTO.isRicZcZonaOmi())
			return docfaDAO.getFoglioMicrozona(zonaDTO.getFoglio(), zonaDTO.getZonaCensuaria());
		else
			return docfaDAO.getFoglioMicrozona(zonaDTO.getFoglio());
	}

	@Override
	public List<String> getDocfaFogliMicrozona_ListaFogli(CeTBaseObject o) {
		return docfaDAO.getDocfaFogliMicrozona_ListaFogli();
	}
	
	@Override
	public Date[] getLastSitPlanimetrie(RicercaOggettoDocfaDTO rod) {
		return docfaDAO.getLastSitPlanimetrie(rod);
	}
	
	//GITOUT WS 4.0
	public List<DocfaDatiCensuari> getDocfaDatiCensuari(RicercaOggettoDocfaDTO ro) {		
		return docfaDAO.getDocfaDatiCensuari(ro);
	}//-------------------------------------------------------------------------
	
//	public List<DocfaValori> getDocfaValori(RicercaOggettoDocfaDTO ro){
//		
//		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
//		criteria.setKey("docfa.modalita.calcolo.valore.commerciale");
//		criteria.setComune(ro.getEnteId());
//		criteria.setSection("param.comune");
//		
//		try
//		{
//			ParameterService parameterService= (ParameterService)ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");
//			if (parameterService==null)
//			{
//				
//			}
//			
//			List<AmKeyValueDTO> l = parameterService.getListaKeyValue(criteria);
//			if (l!=null && l.size()>0){
//				String modalita = ((AmKeyValueDTO)l.get(0)).getAmKeyValueExt().getValueConf();
//				
//				if (modalita != null && !modalita.trim().equalsIgnoreCase("") && modalita.trim().equalsIgnoreCase("KML_SHAPE")){
//					docfaDAO.getDocfaValoriByZonaOmi(zonaOmi, tipolEdilizia, stato);
//				}else{
//					docfaDAO.getDocfaValori(null, tipolEdilizia, stato);					
//				}
//
//				
//				return null;
//			}else
//			{
//				logger.debug(" LISTA RITORNO DA parameterService.getListaKeyValue VUOTA");
//				return null;
//			}
//		}
//		catch (Exception ex)
//		{
//			logger.error(" doGetListaKeyValue ERRORE = " + ex.getMessage(),ex);
//			return null;
//		}		
//		
//	}//-------------------------------------------------------------------------
	

	
}
