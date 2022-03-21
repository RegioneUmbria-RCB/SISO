package it.webred.ct.data.access.basic.diagnostiche.ici;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheDataIn;
import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheServiceException;
import it.webred.ct.data.access.basic.diagnostiche.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaIciTarsuDTO;
import it.webred.ct.data.access.basic.diagnostiche.ici.DiagnosticheIciServiceException;
import it.webred.ct.data.access.basic.diagnostiche.ici.DocfaIciReportService;
import it.webred.ct.data.access.basic.diagnostiche.ici.dao.DocfaIciReportDAO;
import it.webred.ct.data.access.basic.diagnostiche.ici.dto.SitTIciOggettoDTO;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.DiagnosticheTarServiceException;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.access.basic.ici.dto.SoggettoIciDTO;
import it.webred.ct.data.model.diagnostiche.DocfaAnomalie;
import it.webred.ct.data.model.diagnostiche.DocfaIciReport;
import it.webred.ct.data.model.diagnostiche.DocfaIciReportSogg;
import it.webred.ct.data.model.docfa.DocfaDichiaranti;
import it.webred.ct.data.model.docfa.DocfaIntestati;
import it.webred.ct.support.audit.AuditStateless;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class DocfaIciReportServiceBean extends CTServiceBaseBean implements
		DocfaIciReportService {

	@Autowired
	private DocfaIciReportDAO docfaIciReportDAO;

	@Override
	public List<Date> getListaForniture(DiagnosticheDataIn dataIn)
		throws DiagnosticheIciServiceException{
		return docfaIciReportDAO.getListaForniture();
	}
	
	@Override
	@Interceptors(AuditStateless.class)
	public DocfaIciReport getDocfaIciReportById(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		String id = (String) dataIn.getObj();
		return docfaIciReportDAO.getDocfaIciReportById(id);
	}

	@Override
	public List<Object> getSuggestionVie(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		String via = (String) dataIn.getObj();
		return docfaIciReportDAO.getSuggestionVie(via);
	}

	@Override
	public List<Object> getSuggestionCognomi(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		String cognome = (String) dataIn.getObj();
		return docfaIciReportDAO.getSuggestionCognomi(cognome);
	}

	@Override
	public List<Object> getSuggestionNomi(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		String nome = (String) dataIn.getObj();
		return docfaIciReportDAO.getSuggestionNomi(nome);
	}

	@Override
	public List<Object> getSuggestionCodFisc(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		String codFisc = (String) dataIn.getObj();
		return docfaIciReportDAO.getSuggestionCodFisc(codFisc);
	}

	@Override
	@Interceptors(AuditStateless.class)
	public List<DocfaIciReport> searchReport(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		RicercaDocfaReportDTO rs = dataIn.getRicerca();
		return docfaIciReportDAO.searchReport(rs);
	}

	@Override
	public List<DocfaIciReportSogg> getReportSogg(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		String id = (String) dataIn.getObj();
		return docfaIciReportDAO.getReportSogg(id);
	}

	@Override
	public List<DocfaIciReportSogg> getReportSoggTitolari(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException {
		String id = (String) dataIn.getObj();
		return docfaIciReportDAO.getReportSoggTitolari(id);
	}

	@Override
	public Long searchReportCount(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		RicercaDocfaReportDTO rs = dataIn.getRicerca();
		return docfaIciReportDAO.searchReportCount(rs);
	}

	@Override
	public List<Object[]> countDocfaFornitura(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		return docfaIciReportDAO.countDocfaFornitura();
	}

/*	@Override
	public List<DocfaDichiaranti> getDichiaranti(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		Date fornitura = (Date) dataIn.getObj();
		String protocollo = (String) dataIn.getObj2();
		return docfaIciReportDAO.getDichiaranti(fornitura, protocollo);
	}

	@Override
	public List<DocfaIntestati> getIntestati(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		
		Date fornitura = (Date) dataIn.getObj();
		String protocollo = (String) dataIn.getObj2();
		
		return docfaIciReportDAO.getIntestati(fornitura, protocollo);
	}*/

	@Override
	public List<SitTIciOggettoDTO> getIciDocfa(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		RicercaDocfaReportDTO dto = dataIn.getRicerca();
		return docfaIciReportDAO.getIciDocfa(dto);
	}

	@Override
	public List<SitTIciOggettoDTO> getIciAnteDocfaBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaIciReportDAO.getIciAnteDocfaBySoggetto(dto);
	}

	@Override
	public List<SitTIciOggettoDTO> getIciPostDocfaBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaIciReportDAO.getIciPostDocfaBySoggetto(dto);
	}
	
	@Override
	public List<SitTIciOggettoDTO> getIciAnteDocfaUiuBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaIciReportDAO.getIciAnteDocfaUiuBySoggetto(dto);
	}

	@Override
	public List<SitTIciOggettoDTO> getIciPostDocfaUiuBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaIciReportDAO.getIciPostDocfaUiuBySoggetto(dto);
	}
	
	@Override
	public List<SitTIciOggettoDTO> getIciAnteDocfaCivBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaIciReportDAO.getIciAnteDocfaCivBySoggetto(dto);
	}

	@Override
	public List<SitTIciOggettoDTO> getIciPostDocfaCivBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException {
		RicercaIciTarsuDTO dto = dataIn.getRicercaIciTarsuDto();
		return docfaIciReportDAO.getIciPostDocfaCivBySoggetto(dto);
	}

	@Override
	public List<IndirizzoDTO> getIndirizziCatasto(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		RicercaDocfaReportDTO rs = dataIn.getRicerca();
		return docfaIciReportDAO.getIndirizziCatasto(rs);
	}

	@Override
	public List<IndirizzoDTO> getIndirizziIci(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		RicercaDocfaReportDTO rs = dataIn.getRicerca();
		return docfaIciReportDAO.getIndirizziIci(rs);
	}

	@Override
	public List<SoggettoIciDTO> getSoggettiIci(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException {
		String idExt = (String) dataIn.getObj();
		return docfaIciReportDAO.getSoggettiIci(idExt);
	}

	@Override
	public List<DocfaAnomalie> getListaAnomalie(DiagnosticheDataIn dataIn)
			throws DiagnosticheServiceException {
		return docfaIciReportDAO.getListaAnomalie("I");
	}

	@Override
	public List<DocfaIciReport> getQuadroRendite(
			RicercaOggettoDocfaDTO ricercaDocfaDto) {
		return docfaIciReportDAO.getListaByDocfaTipo(ricercaDocfaDto);
	}

}
