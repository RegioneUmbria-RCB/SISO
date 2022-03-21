package it.webred.ct.data.access.basic.diagnostiche.tarsu;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheDataIn;
import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheServiceException;
import it.webred.ct.data.access.basic.diagnostiche.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaIciTarsuDTO;
import it.webred.ct.data.access.basic.diagnostiche.ici.dto.QuadroTarsuDTO;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.dao.DocfaTarReportDAO;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.dto.SitTTarOggettoDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.access.basic.tarsu.dao.TarsuDAO;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaOggettoTarsuDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.diagnostiche.DocfaAnomalie;
import it.webred.ct.data.model.diagnostiche.DocfaTarReport;
import it.webred.ct.data.model.diagnostiche.DocfaTarReportSogg;
import it.webred.ct.data.model.locazioni.LocazioniA;
import it.webred.ct.support.audit.AuditStateless;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class DocfaTarReportServiceBean extends CTServiceBaseBean implements
		DocfaTarReportService {

	@Autowired
	private DocfaTarReportDAO docfaTarReportDAO;
	@Autowired
	private TarsuDAO tarsuDAO;
	
	private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public List<Date> getListaForniture(DiagnosticheDataIn dataIn)
		throws DiagnosticheTarServiceException{
		return docfaTarReportDAO.getListaForniture();
	}
	
	@Override
	@Interceptors(AuditStateless.class)
	public DocfaTarReport getDocfaTarReportById(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		String id = (String) dataIn.getObj();
		return docfaTarReportDAO.getDocfaTarReportById(id);
	}

	@Override
	public List<Object> getSuggestionVie(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		String via = (String) dataIn.getObj();
		return docfaTarReportDAO.getSuggestionVie(via);
	}

	@Override
	public List<Object> getSuggestionCognomi(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		String cognome = (String) dataIn.getObj();
		return docfaTarReportDAO.getSuggestionCognomi(cognome);
	}

	@Override
	public List<Object> getSuggestionNomi(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		String nome = (String) dataIn.getObj();
		return docfaTarReportDAO.getSuggestionNomi(nome);
	}

	@Override
	public List<Object> getSuggestionCodFisc(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		String codFisc = (String) dataIn.getObj();
		return docfaTarReportDAO.getSuggestionCodFisc(codFisc);
	}

	@Override
	@Interceptors(AuditStateless.class)
	public List<DocfaTarReport> searchReport(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		RicercaDocfaReportDTO rs = dataIn.getRicerca();
		return docfaTarReportDAO.searchReport(rs);
	}

	@Override
	public List<DocfaTarReportSogg> getReportSogg(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		String id = (String) dataIn.getObj();
		return docfaTarReportDAO.getReportSogg(id);
	}

	@Override
	public List<DocfaTarReportSogg> getReportSoggTitolari(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		String id = (String) dataIn.getObj();
		return docfaTarReportDAO.getReportSoggTitolari(id);
	}

	@Override
	public Long searchReportCount(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		RicercaDocfaReportDTO rs = dataIn.getRicerca();
		return docfaTarReportDAO.searchReportCount(rs);
	}

	@Override
	public List<Object[]> countDocfaFornitura(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		return docfaTarReportDAO.countDocfaFornitura();
	}


	@Override
	public List<IndirizzoDTO> getIndirizziCatasto(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		RicercaDocfaReportDTO rs = dataIn.getRicerca();
		return docfaTarReportDAO.getIndirizziCatasto(rs);
	}
	
	@Override
	public List<IndirizzoDTO> getIndirizziTarsu(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		RicercaDocfaReportDTO rs = dataIn.getRicerca();
		return docfaTarReportDAO.getIndirizziTar(rs);
	}
	
	
	/*@Override
	public List<SitTTarOggettoDTO> getTarDocfa(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException {
		RicercaDocfaReportDTO dto = dataIn.getRicerca();
		return docfaTarReportDAO.getTarDocfa(dto);
	}*/
	
	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaBySoggettoUiu(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarAnteDocfaBySoggettoUiu(dto);
	}

	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaBySoggettoUiu(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarPostDocfaBySoggettoUiu(dto);
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaBySoggettoUiuUnd(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarAnteDocfaBySoggettoUiuUnd(dto);
	}

	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaBySoggettoUiuUnd(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarPostDocfaBySoggettoUiuUnd(dto);
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaBySoggettoCiv(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarAnteDocfaBySoggettoCiv(dto);
	}

	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaBySoggettoCiv(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarPostDocfaBySoggettoCiv(dto);
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaByFamiliariUiu(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarAnteDocfaByFamiliariUiu(dto);
	}

	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaByFamiliariUiu(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarPostDocfaByFamiliariUiu(dto);
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaByFamiliariUiuUnd(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarAnteDocfaByFamiliariUiuUnd(dto);
	}

	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaByFamiliariUiuUnd(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarPostDocfaByFamiliariUiuUnd(dto);
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaByFamiliariCiv(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarAnteDocfaByFamiliariCiv(dto);
	}

	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaByFamiliariCiv(
			DiagnosticheDataIn dataIn) throws DiagnosticheTarServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaTarReportDAO.getTarPostDocfaByFamiliariCiv(dto);
	}


	@Override
	public List<DocfaAnomalie> getListaAnomalie(DiagnosticheDataIn dataIn)
			throws DiagnosticheServiceException {
		return docfaTarReportDAO.getListaAnomalie("T");
	}
	
	@Override
	public List<LocazioniA> getLocazioniDaTarsuBySoggCivicoData(DiagnosticheDataIn dataIn)
			throws DiagnosticheServiceException {
		return docfaTarReportDAO.getLocazioniDaTarsuBySoggCivicoData(dataIn);
	}
	
	@Override
	public String getTitolaritaDaTarsuBySoggUiuData(DiagnosticheDataIn dataIn)
			throws DiagnosticheTarServiceException{
		String idDwhTarsuSogg = (String)dataIn.getObj();
		RicercaOggettoCatDTO ricercaOggettoCat = dataIn.getRicercaOggettoCatDTO();
		return docfaTarReportDAO.getTitolaritaDaTarsuBySoggUiuData(ricercaOggettoCat,idDwhTarsuSogg);
	}
	
	@Override
	public List<SitDPersona> getFamiliariCatSoggAllaData(RicercaIciTarsuDTO ricercaDto) 
		throws DiagnosticheTarServiceException{
		return docfaTarReportDAO.getFamiliariCatSoggAllaData(ricercaDto);
	}
	
	@Override
	public List<SitDPersona> getFamiliariTarSoggAllaData(RicercaIciTarsuDTO ricercaDto) 
		throws DiagnosticheTarServiceException{
		return docfaTarReportDAO.getFamiliariTarSoggAllaData(ricercaDto);
	}
	
	@Override
	 public List<String> getListaIdDwhSoggAnagFromCatasto(RicercaIciTarsuDTO ro)
 		throws DiagnosticheTarServiceException{
		return docfaTarReportDAO.getListaIdDwhSoggAnagFromCatasto(ro);
	 }

	@Override
	public List<QuadroTarsuDTO> getQuadroSuperfici(RicercaOggettoDocfaDTO ricercaDocfaDto) throws DiagnosticheTarServiceException {
		
		List<QuadroTarsuDTO> result = new ArrayList<QuadroTarsuDTO>(); 
		
		logger.debug("Estrazioni superfici QUADRO TARSU...........................................");
		
		try{
		
		for(DocfaTarReport rep : docfaTarReportDAO.getListaByDocfaTipo(ricercaDocfaDto)){
			QuadroTarsuDTO quadro = new QuadroTarsuDTO();
			quadro.setReport(rep);
			
			List<SitTTarOggettoDTO> listaAnte = new ArrayList<SitTTarOggettoDTO>();
			List<SitTTarOggettoDTO> listaPost = new ArrayList<SitTTarOggettoDTO>();
			BigDecimal superficie = null;
			if (rep.getDataDocfa() != null) {
				Date dataRif = rep.getDataDocfaToDate();
				
				RicercaOggettoDTO ro = new RicercaOggettoDTO();
				ro.setFoglio(rep.getFoglio());
				ro.setParticella(rep.getParticella());
				ro.setSub(rep.getSubalterno());
				List<SitTTarOggettoDTO> listTar = tarsuDAO.getListaTarsuUiu(ro);
				
				for (SitTTarOggettoDTO dto : listTar) {
					Date dataIni = dto.getSitTTarOggetto().getDatIni();
					if (dataIni == null)
						dataIni = yyyyMMdd.parse("00010101");
					
					if (dataIni.before(dataRif))
						listaAnte.add(dto);
					else if(dataIni.compareTo(dataRif)>=0)
						listaPost.add(dto);
				}
			}else{
				logger.debug("Data Registrazione NULL");
			}
				
			//In base al tipo seleziono l'ultima superficie dichiarata (precedente)
			//o la superficie attuale (ultima dich.tarsu)
			List<SitTTarOggettoDTO> listaTarsu = null;
			if("S".equalsIgnoreCase(ricercaDocfaDto.getTipoOperDocfa()))
				listaTarsu = getDichiarazioneUltima(listaAnte);
			else if("C".equalsIgnoreCase(ricercaDocfaDto.getTipoOperDocfa()))
				listaTarsu = getDichiarazioneUltima(listaPost);
			
			quadro.setSupDichTarsu(listaTarsu);
			result.add(quadro);
		}
		
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return result;
	}
	
	private List<SitTTarOggettoDTO> getDichiarazioneUltima(List<SitTTarOggettoDTO> lista ){
		List<SitTTarOggettoDTO> listaTarsu = new ArrayList<SitTTarOggettoDTO>();
		
		if(lista.size()>=1){	
			//TODO:Seleziono l'ultima dichiarazioni più recenti, prima del docfa
			SitTTarOggettoDTO tarsuFirst = lista.get(0);
			Date datIniFirst = tarsuFirst.getSitTTarOggetto().getDatIni();
			listaTarsu.add(tarsuFirst);
			
			boolean finito = false;
			int i = 1;
			while(!finito && i<lista.size()){
				SitTTarOggettoDTO tarsu = lista.get(i);
				if(datIniFirst.compareTo(tarsu.getSitTTarOggetto().getDatIni())==0)
					listaTarsu.add(tarsu);
				else
					finito = true;
				i++;
			}
			
		}
		return listaTarsu;
	 
	}
		
	
}
