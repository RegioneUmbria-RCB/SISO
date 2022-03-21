package it.webred.ct.data.access.basic.diagnostiche.ici;

import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheDataIn;
import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheService;
import it.webred.ct.data.access.basic.diagnostiche.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaIciTarsuDTO;
import it.webred.ct.data.access.basic.diagnostiche.ici.dto.SitTIciOggettoDTO;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.DiagnosticheTarServiceException;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.access.basic.ici.dto.SoggettoIciDTO;
import it.webred.ct.data.model.diagnostiche.DocfaIciReport;
import it.webred.ct.data.model.diagnostiche.DocfaIciReportSogg;
import it.webred.ct.data.model.docfa.DocfaDichiaranti;
import it.webred.ct.data.model.docfa.DocfaIntestati;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface DocfaIciReportService extends DiagnosticheService{

	public List<Date> getListaForniture(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public DocfaIciReport getDocfaIciReportById(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public List<Object> getSuggestionVie(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public List<Object> getSuggestionCognomi(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public List<Object> getSuggestionNomi(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public List<Object> getSuggestionCodFisc(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public List<DocfaIciReport> searchReport(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public List<DocfaIciReportSogg> getReportSogg(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public List<DocfaIciReportSogg> getReportSoggTitolari(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException;

	public Long searchReportCount(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public List<Object[]> countDocfaFornitura(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	public List<SitTIciOggettoDTO> getIciDocfa(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	List<IndirizzoDTO> getIndirizziCatasto(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	List<IndirizzoDTO> getIndirizziIci(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciAnteDocfaBySoggetto(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciPostDocfaBySoggetto(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	List<SoggettoIciDTO> getSoggettiIci(DiagnosticheDataIn dataIn)
			throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciAnteDocfaUiuBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciPostDocfaUiuBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciPostDocfaCivBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciAnteDocfaCivBySoggetto(
			DiagnosticheDataIn dataIn) throws DiagnosticheIciServiceException;

	public List<DocfaIciReport> getQuadroRendite(
			RicercaOggettoDocfaDTO ricercaDocfaDto);
}
