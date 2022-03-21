package it.webred.ct.data.access.basic.diagnostiche.ici.dao;

import it.webred.ct.data.access.basic.diagnostiche.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaIciTarsuDTO;
import it.webred.ct.data.access.basic.diagnostiche.ici.DiagnosticheIciServiceException;
import it.webred.ct.data.access.basic.diagnostiche.ici.dto.SitTIciOggettoDTO;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.DiagnosticheTarServiceException;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.access.basic.ici.dto.SoggettoIciDTO;
import it.webred.ct.data.model.diagnostiche.DocfaAnomalie;
import it.webred.ct.data.model.diagnostiche.DocfaIciReport;
import it.webred.ct.data.model.diagnostiche.DocfaIciReportSogg;
import it.webred.ct.data.model.docfa.DocfaDichiaranti;
import it.webred.ct.data.model.docfa.DocfaIntestati;

import java.util.Date;
import java.util.List;

public interface DocfaIciReportDAO {

	public List<Date> getListaForniture()
			throws DiagnosticheIciServiceException;
	
	public DocfaIciReport getDocfaIciReportById(String id)
			throws DiagnosticheIciServiceException;

	public List<Object> getSuggestionVie(String via)
			throws DiagnosticheIciServiceException;

	public List<Object> getSuggestionCognomi(String via)
			throws DiagnosticheIciServiceException;

	public List<Object> getSuggestionNomi(String via)
			throws DiagnosticheIciServiceException;

	public List<Object> getSuggestionCodFisc(String via)
			throws DiagnosticheIciServiceException;

	public List<DocfaIciReport> searchReport(RicercaDocfaReportDTO rs)
			throws DiagnosticheIciServiceException;

	public List<DocfaIciReportSogg> getReportSogg(String id)
			throws DiagnosticheIciServiceException;

	public List<DocfaIciReportSogg> getReportSoggTitolari(String id)
			throws DiagnosticheIciServiceException;

	public Long searchReportCount(RicercaDocfaReportDTO rs)
			throws DiagnosticheIciServiceException;

	public List<Object[]> countDocfaFornitura()
			throws DiagnosticheIciServiceException;

/*	public List<DocfaDichiaranti> getDichiaranti(Date fornitura,
			String protocollo) throws DiagnosticheIciServiceException;

	public List<DocfaIntestati> getIntestati(Date fornitura, String protocollo)
			throws DiagnosticheIciServiceException;*/

	public List<SitTIciOggettoDTO> getIciDocfa(RicercaDocfaReportDTO dto)
			throws DiagnosticheIciServiceException;

	List<IndirizzoDTO> getIndirizziCatasto(RicercaDocfaReportDTO rs)
			throws DiagnosticheIciServiceException;

	List<IndirizzoDTO> getIndirizziIci(RicercaDocfaReportDTO rs)
			throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciPostDocfaBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciAnteDocfaBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException;

	List<SoggettoIciDTO> getSoggettiIci(String idExt)
			throws DiagnosticheIciServiceException;
	
	List<DocfaAnomalie> getListaAnomalie(String tipo)
			throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciPostDocfaUiuBySoggetto(
			RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciAnteDocfaUiuBySoggetto(
			RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciAnteDocfaCivBySoggetto(
			RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException;

	List<SitTIciOggettoDTO> getIciPostDocfaCivBySoggetto(
			RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException;
	
	String getDocfaDescAnomalieById(String sCodAnomalie)
			throws DiagnosticheIciServiceException;

	public List<DocfaIciReport> getListaByDocfaTipo(
			RicercaOggettoDocfaDTO ricercaDocfaDto);
	
}
