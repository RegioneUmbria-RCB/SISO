package it.webred.ct.data.access.basic.diagnostiche.tarsu.dao;

import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheDataIn;
import it.webred.ct.data.access.basic.diagnostiche.dao.DiagnosticheBaseDAO;
import it.webred.ct.data.access.basic.diagnostiche.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaIciTarsuDTO;
import it.webred.ct.data.access.basic.diagnostiche.ici.DiagnosticheIciServiceException;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.DiagnosticheTarServiceException;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.dto.SitTTarOggettoDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.diagnostiche.DocfaTarReport;
import it.webred.ct.data.model.diagnostiche.DocfaTarReportSogg;
import it.webred.ct.data.model.locazioni.LocazioniA;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class DocfaTarReportJPAImpl extends DiagnosticheBaseDAO implements
		DocfaTarReportDAO {
	
	private static final long serialVersionUID = 1L;


	@Override
	public List<Date> getListaForniture(){
		List<Date> lista = new ArrayList<Date>();
		
		try {
			Query q = manager_diogene.createNamedQuery("DocfaTarReport.getForniture");
			lista =  q.getResultList();

	} catch (Throwable t) {
		throw new DiagnosticheIciServiceException(t);
	}
		
		return lista;
	}
	
	@Override
	public DocfaTarReport getDocfaTarReportById(String id)throws DiagnosticheTarServiceException {
		DocfaTarReport rep = new DocfaTarReport();
		
		try {
				Query q = manager_diogene.createNamedQuery("DocfaTarReport.getById").setParameter("id", id);
				rep = (DocfaTarReport) q.getSingleResult();
				return getReportDescAnomalie(rep);
						
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}

	}
	
	private DocfaTarReport getReportDescAnomalie(DocfaTarReport rep){
		try{
		String anomalie = getDocfaDescAnomalieById(rep.getCodAnomalie());
			if(anomalie!=null){
				if(rep.getAnnotazioni()!=null)
					anomalie +=  rep.getAnnotazioni();
				rep.setAnnotazioni(anomalie);
		}
		}catch(NoResultException e){}
		return rep;
	}
	
	@Override
	public List<Object> getSuggestionVie(String via)
			throws DiagnosticheTarServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaTarReport.getVie").setParameter("via", via.toUpperCase()).setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}

	}
	
	@Override
	public List<Object> getSuggestionCognomi(String cognome)
			throws DiagnosticheTarServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getCognomi").setParameter("cognome", cognome.toUpperCase()).setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}

	}
	
	@Override
	public List<Object> getSuggestionNomi(String nome)
			throws DiagnosticheTarServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getNomi").setParameter("nome", nome.toUpperCase()).setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}

	}
	
	@Override
	public List<Object> getSuggestionCodFisc(String codFisc)
			throws DiagnosticheTarServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getCodFisc").setParameter("codFisc", codFisc.toUpperCase()).setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}

	}
	
	@Override
	public List<DocfaTarReport> searchReport(RicercaDocfaReportDTO rs)throws DiagnosticheTarServiceException {
		
		ArrayList<DocfaTarReport> repsAnomalie = new ArrayList<DocfaTarReport>();	
		
		try {

			String sql = (new DocfaTarReportQueryBuilder(rs)).createQuery(false);

			Query q = manager_diogene.createQuery(sql);
			if (rs.getStartm() != 0 || rs.getNumberRecord() != 0) {
				q.setFirstResult(rs.getStartm());
				q.setMaxResults(rs.getNumberRecord());
			}

			ArrayList<DocfaTarReport> reps = (ArrayList<DocfaTarReport>) q.getResultList();
			for(int i=0; i<reps.size(); i++)
			    repsAnomalie.add(getReportDescAnomalie(reps.get(i)));
			 
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return repsAnomalie;
	}
	
	@Override
	public List<DocfaTarReportSogg> getReportSogg(String id)
			throws DiagnosticheTarServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getSoggetti").setParameter("report", id);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}

	}
	
	@Override
	public List<DocfaTarReportSogg> getReportSoggTitolari(String id)
			throws DiagnosticheTarServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getSoggettiTitolari").setParameter("report", id);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}

	}
	
	@Override
	public Long searchReportCount(RicercaDocfaReportDTO rs)
			throws DiagnosticheTarServiceException {
				
		try {

			String sql = (new DocfaTarReportQueryBuilder(rs))
					.createQuery(true);

			Query q = manager_diogene.createQuery(sql);
			Long l = (Long) q.getSingleResult();
			return l == null? new Long(0): l;
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
	}
	
	@Override
	public List<Object[]> countDocfaFornitura()
			throws DiagnosticheTarServiceException {
						
		try {

			Query q = manager_diogene.createNamedQuery("DocfaTarReport.getCount");
			return q.getResultList();
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
	}
	

	
	@Override
	public List<IndirizzoDTO> getIndirizziCatasto(RicercaDocfaReportDTO rs)
			throws DiagnosticheTarServiceException {
						
		List<IndirizzoDTO> listaDto = new ArrayList<IndirizzoDTO>();
		try {

			Query q = manager_diogene.createNamedQuery("IndirizzoDTO.getIndirizziCatasto");
			q.setParameter("ente", rs.getEnte());
			q.setParameter("foglio", new Long(rs.getFoglio()).longValue());
			q.setParameter("particella", rs.getParticella());
			if(rs.getUnimm() != null)
				q.setParameter("sub", new Long(rs.getUnimm()).longValue());
			else q.setParameter("sub", new Long(0).longValue());
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				IndirizzoDTO dto = new IndirizzoDTO();
				dto.setIndirizzo(o[1] + " " + o[2]);
				dto.setCivico((String) o[3]);
				dto.setDataInizioVal((Date) o[4]);
				dto.setDataFineVal((Date) o[5]);
				
				listaDto.add(dto);
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<IndirizzoDTO> getIndirizziTar(RicercaDocfaReportDTO rs)
			throws DiagnosticheTarServiceException {
						
		List<IndirizzoDTO> listaDto = new ArrayList<IndirizzoDTO>();
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Query q = manager_diogene.createNamedQuery("IndirizzoDTO.getIndirizziTarsu");
			q.setParameter("foglio", rs.getFoglio());
			q.setParameter("particella", rs.getParticella());
			q.setParameter("sub", rs.getUnimm() != null? rs.getUnimm(): "0000");
			List<Object[]> lista = q.getResultList();
			
			for(Object[] o: lista){
				IndirizzoDTO dto = new IndirizzoDTO();
				dto.setIndirizzo((String) o[0]);
				dto.setCivico((String) o[1]);
				Date dataInizio = (Date) o[2];
				Date dataFine = (Date) o[3];
				
				if (dataInizio == null)
					dataInizio = sdf.parse("00010101");
				if (dataInizio.before(rs.getDataRegistrazioneDocfa()))
					dto.setAnteDocfa(true);

				listaDto.add(dto);
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}
		
/*	@Override
	public List<SitTTarOggettoDTO> getTarDocfa(RicercaDocfaReportDTO ricercaDto)
			throws DiagnosticheTarServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {

			Query q = manager_diogene.createNamedQuery("DocfaTarReport.getTarsu");
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getUnimm() != null? ricercaDto.getUnimm(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}*/
	
	
	
	/*@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheTarServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {

			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getTarAnte");
			q.setParameter("catpkid", ricercaDto.getCatPkid());
			q.setParameter("datadocfa", ricercaDto.getDataDocfa());
			q.setParameter("foglio", ricercaDto.getFoglio());
			q.setParameter("numero", ricercaDto.getParticella());
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheTarServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {

			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getTarPost.1");
			q.setParameter("catpkid", ricercaDto.getCatPkid());
			q.setParameter("datadocfa", ricercaDto.getDataDocfa());
			int annoDocfaPlus = new Integer(ricercaDto.getDataDocfa().substring(0, 4)).intValue() +1;
			q.setParameter("gendopodatadocfa", String.valueOf(annoDocfaPlus) + "0120");
			q.setParameter("foglio", ricercaDto.getFoglio());
			q.setParameter("numero", ricercaDto.getParticella());
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
			List<Object[]> lista = q.getResultList();
			if(lista.size() == 0){
				q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getTarPost.2");
				q.setParameter("catpkid", ricercaDto.getCatPkid());
				q.setParameter("datadocfa", ricercaDto.getDataDocfa());
				q.setParameter("gendopodatadocfa", String.valueOf(annoDocfaPlus) + "0120");
				q.setParameter("foglio", ricercaDto.getFoglio());
				q.setParameter("numero", ricercaDto.getParticella());
				q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
				lista = q.getResultList();
			}
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}*/
	
	
	public List<SitDPersona> getFamiliariCatSoggAllaData(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException{
		
		try{
			logger.debug("Ricerca Familiari dei Soggetti Catastali alla data...");
			logger.debug("Parametro Data Riferimento:" + ricercaDto.getDataRif());
			logger.debug("Parametro IdDwh Soggetto Catasto:" + ricercaDto.getIdDwhOrigineSogg());
			
			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getFamiliariAllaData");
			q.setParameter("enteSorgenteOrigine", 4L);
			q.setParameter("progOrigine", 3L);
			q.setParameter("dataRif", ricercaDto.getDataRif());
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
					
			List<SitDPersona> result =  q.getResultList();
			logger.debug("Result:" + result.size());
			return result;
			
			
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
	}
	
	public List<SitDPersona> getFamiliariTarSoggAllaData(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException{
		
		try{
			logger.debug("Ricerca Familiari dei Soggetti Tarsu alla data...");
			logger.debug("Parametro Data Riferimento:" + ricercaDto.getDataRif());
			logger.debug("Parametro IdDwh Soggetto Tarsu:" + ricercaDto.getIdDwhOrigineSogg());
		
			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getFamiliariAllaData");
			q.setParameter("enteSorgenteOrigine", 2L);
			q.setParameter("progOrigine", 4L);
			q.setParameter("dataRif", ricercaDto.getDataRif());
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
					
			return q.getResultList();
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
	}
	
	private RicercaIciTarsuDTO getRicercaIciTarsuDTOFam(RicercaIciTarsuDTO ricercaIciTarsuDTO){
		RicercaIciTarsuDTO ricercaDtoFam = new RicercaIciTarsuDTO();
		ricercaDtoFam.setDataRif(ricercaIciTarsuDTO.getDataRif());
		ricercaDtoFam.setFoglio(ricercaIciTarsuDTO.getFoglio());
		ricercaDtoFam.setParticella(ricercaIciTarsuDTO.getParticella());
		ricercaDtoFam.setSub(ricercaIciTarsuDTO.getSub());
		ricercaDtoFam.setIdDwhOrigineCiv(ricercaIciTarsuDTO.getIdDwhOrigineCiv());
		
		ricercaDtoFam.setEnteSorgenteOrigine(1L);
		ricercaDtoFam.setProgOrigine(1L);
		
		return ricercaDtoFam;
	}
	
	private void logParamsRicercaIciTarsuDTO(RicercaIciTarsuDTO ricercaDtoFam){
		logger.info("Data Docfa: " + ricercaDtoFam.getDataRif());
		logger.info("Foglio: " + ricercaDtoFam.getFoglio());
		logger.info("Particella: " + ricercaDtoFam.getParticella());
		logger.info("Subalterno: " + ricercaDtoFam.getSub());
		logger.info("IdDwhOrigineCivico: " + ricercaDtoFam.getIdDwhOrigineCiv());
		logger.info("IdDwhOrigineSogg: " + ricercaDtoFam.getIdDwhOrigineSogg());
		logger.info("Ente Sorgente: " + ricercaDtoFam.getEnteSorgenteOrigine());
		logger.info("Prog Sorgente: " + ricercaDtoFam.getProgOrigine());
	}
	
	private boolean addCodice(List<String> ids, String nuovoId){
		boolean esiste = false;
		Iterator<String> i = ids.iterator();
		while(i.hasNext() && !esiste){
			String id = i.next();
			if(id.equals(nuovoId))
				esiste = true;
		}

		if(!esiste)
			ids.add(nuovoId);
		
		logger.info("Lunghezza idsTar:" + ids.size());
		
		return !esiste;
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaByFamiliariUiu(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException {
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		List<String> idsTar = new ArrayList<String>();
		try{
			logger.info("Ricerca Dichiarazioni Tarsu da Familiare Ante Docfa per UIU...");
			RicercaIciTarsuDTO ricercaDtoFam = this.getRicercaIciTarsuDTOFam(ricercaDto);
			List<SitDPersona> listaFamiliari = getFamiliariCatSoggAllaData(ricercaDto);
			
			for(SitDPersona p: listaFamiliari){
				ricercaDtoFam.setIdDwhOrigineSogg(p.getId());
				List<SitTTarOggettoDTO> listTar = getTarAnteDocfaBySoggettoUiu(ricercaDtoFam);
				for(SitTTarOggettoDTO tar : listTar){
					if(addCodice(idsTar, tar.getSitTTarOggetto().getId()))
						listaDto.add(tar);
				}
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaByFamiliariUiu(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException {
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		List<String> idsTar = new ArrayList<String>();
		try{
			logger.info("Ricerca Dichiarazioni Tarsu da Familiare Post Docfa per UIU...");
			RicercaIciTarsuDTO ricercaDtoFam = this.getRicercaIciTarsuDTOFam(ricercaDto);
			List<SitDPersona> listaFamiliari = getFamiliariCatSoggAllaData(ricercaDto);
			
			for(SitDPersona p: listaFamiliari){
				ricercaDtoFam.setIdDwhOrigineSogg(p.getId());
				List<SitTTarOggettoDTO> listTar = getTarPostDocfaBySoggettoUiu(ricercaDtoFam);
				for(SitTTarOggettoDTO tar : listTar){
					if(addCodice(idsTar, tar.getSitTTarOggetto().getId()))
						listaDto.add(tar);
				}
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
	}

	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaByFamiliariCiv(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException {
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		List<String> idsTar = new ArrayList<String>();
		try{
			
			logger.info("Ricerca Dichiarazioni Tarsu da Familiare Ante Docfa per Civico...");
			RicercaIciTarsuDTO ricercaDtoFam = this.getRicercaIciTarsuDTOFam(ricercaDto);
			List<SitDPersona> listaFamiliari = getFamiliariCatSoggAllaData(ricercaDto);
			
			for(SitDPersona p: listaFamiliari){
				ricercaDtoFam.setIdDwhOrigineSogg(p.getId());
				List<SitTTarOggettoDTO> listTar = getTarAnteDocfaBySoggettoCiv(ricercaDtoFam);
				for(SitTTarOggettoDTO tar : listTar){
					if(addCodice(idsTar, tar.getSitTTarOggetto().getId()))
						listaDto.add(tar);
				}
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaByFamiliariCiv(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException {
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		List<String> idsTar = new ArrayList<String>();
		try{
			logger.info("Ricerca Dichiarazioni Tarsu da Familiare Post Docfa per Civico...");
			RicercaIciTarsuDTO ricercaDtoFam = this.getRicercaIciTarsuDTOFam(ricercaDto);
			List<SitDPersona> listaFamiliari = getFamiliariCatSoggAllaData(ricercaDto);
			
			for(SitDPersona p: listaFamiliari){
				ricercaDtoFam.setIdDwhOrigineSogg(p.getId());
				List<SitTTarOggettoDTO> listTar = getTarPostDocfaBySoggettoCiv(ricercaDtoFam);
				for(SitTTarOggettoDTO tar : listTar){
					if(addCodice(idsTar, tar.getSitTTarOggetto().getId()))
						listaDto.add(tar);
				}
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
	}

	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaByFamiliariUiuUnd(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException {
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		List<String> idsTar = new ArrayList<String>();
		try{
			logger.info("Ricerca Dichiarazioni Tarsu da Familiare Ante Docfa per UIU Indefinita...");
			RicercaIciTarsuDTO ricercaDtoFam = this.getRicercaIciTarsuDTOFam(ricercaDto);
			
			List<SitDPersona> listaFamiliari = getFamiliariCatSoggAllaData(ricercaDto);
			
			for(SitDPersona p: listaFamiliari){
				ricercaDtoFam.setIdDwhOrigineSogg(p.getId());
				List<SitTTarOggettoDTO> listTar = getTarAnteDocfaBySoggettoUiuUnd(ricercaDtoFam);
				for(SitTTarOggettoDTO tar : listTar){
					if(addCodice(idsTar, tar.getSitTTarOggetto().getId()))
						listaDto.add(tar);
				}
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaByFamiliariUiuUnd(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException {
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		List<String> idsTar = new ArrayList<String>();
		try{
			logger.info("Ricerca Dichiarazioni Tarsu da Familiare Post Docfa per UIU Indefinita...");
			RicercaIciTarsuDTO ricercaDtoFam = this.getRicercaIciTarsuDTOFam(ricercaDto);
			
			List<SitDPersona> listaFamiliari = getFamiliariCatSoggAllaData(ricercaDto);
			
			for(SitDPersona p: listaFamiliari){
			
				ricercaDtoFam.setIdDwhOrigineSogg(p.getId());
				List<SitTTarOggettoDTO> listTar = getTarPostDocfaBySoggettoUiuUnd(ricercaDtoFam);
				for(SitTTarOggettoDTO tar : listTar){
					if(addCodice(idsTar, tar.getSitTTarOggetto().getId()))
						listaDto.add(tar);
				}
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
	}

	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaBySoggettoUiu(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {

			logger.info("Ricerca Dichiarazioni Tarsu da Soggetto Ante Docfa per UIU...");
			
			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getTarAnteUiu");
			q.setParameter("enteSorgenteOrigine", ricercaDto.getEnteSorgenteOrigine());
			q.setParameter("progOrigine", ricercaDto.getProgOrigine());
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("dataDocfa", ricercaDto.getDataRif());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni TARSU:"+ lista.size());
			
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaBySoggettoUiu(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheTarServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {
			int annoDocfaPlus = new Integer(ricercaDto.getDataRif().substring(0, 4)).intValue() +1;

			logger.info("Ricerca Dichiarazioni Tarsu da Soggetto Post Docfa per UIU...");
			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getTarPostUiu");
			q.setParameter("enteSorgenteOrigine", ricercaDto.getEnteSorgenteOrigine());
			q.setParameter("progOrigine", ricercaDto.getProgOrigine());
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("dataDocfa", ricercaDto.getDataRif());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
			
			List<Object[]> lista = q.getResultList();
			
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni TARSU:"+ lista.size());
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaBySoggettoUiuUnd(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {
			logger.info("Ricerca Dichiarazioni Tarsu da Soggetto Ante Docfa per UIU indefinita...");
			
			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getTarAnteUiuUnd");
			q.setParameter("enteSorgenteOrigine", ricercaDto.getEnteSorgenteOrigine());
			q.setParameter("progOrigine", ricercaDto.getProgOrigine());
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("dataDocfa", ricercaDto.getDataRif());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");

			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni TARSU:"+ lista.size());
		
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaBySoggettoUiuUnd(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheTarServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {
			int annoDocfaPlus = new Integer(ricercaDto.getDataRif().substring(0, 4)).intValue() +1;

			logger.info("Ricerca Dichiarazioni Tarsu da Soggetto Post Docfa per UIU indefinita...");
			
			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getTarPostUiuUnd");
			q.setParameter("enteSorgenteOrigine", ricercaDto.getEnteSorgenteOrigine());
			q.setParameter("progOrigine", ricercaDto.getProgOrigine());
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("dataDocfa", ricercaDto.getDataRif());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
			
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni TARSU:"+ lista.size());
		
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarAnteDocfaBySoggettoCiv(RicercaIciTarsuDTO ricercaDto) throws DiagnosticheTarServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {

			logger.info("Ricerca Dichiarazioni Tarsu da Soggetto Ante Docfa per Civico...");
			
			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getTarAnteCiv");
			
			logger.info("Parametro Ente Sorgente:" + ricercaDto.getEnteSorgenteOrigine());
			logger.info("Parametro Prog:" + ricercaDto.getProgOrigine());
			logger.info("Parametro IdSogg:" + ricercaDto.getIdDwhOrigineSogg());
			logger.info("Parametro Data:" + ricercaDto.getDataRif());
			logger.info("Parametro IdCiv:" + ricercaDto.getIdDwhOrigineCiv());
			
			q.setParameter("enteSorgenteOrigine", ricercaDto.getEnteSorgenteOrigine());
			q.setParameter("progOrigine", ricercaDto.getProgOrigine());
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("dataDocfa", ricercaDto.getDataRif());
			q.setParameter("idDwhOrigineCiv", ricercaDto.getIdDwhOrigineCiv());
			
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni TARSU:"+ lista.size());
		
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTTarOggettoDTO> getTarPostDocfaBySoggettoCiv(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheTarServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {
			int annoDocfaPlus = new Integer(ricercaDto.getDataRif().substring(0, 4)).intValue() +1;
			
			logger.info("Ricerca Dichiarazioni Tarsu da Soggetto Post Docfa per Civico...");
			
			logger.info("Parametro Ente Sorgente:" + ricercaDto.getEnteSorgenteOrigine());
			logger.info("Parametro Prog:" + ricercaDto.getProgOrigine());
			logger.info("Parametro IdSogg:" + ricercaDto.getIdDwhOrigineSogg());
			logger.info("Parametro Data:" + ricercaDto.getDataRif());
			logger.info("Parametro IdCiv:" + ricercaDto.getIdDwhOrigineCiv());

			Query q = manager_diogene.createNamedQuery("DocfaTarReportSogg.getTarPostCiv");
			q.setParameter("enteSorgenteOrigine", ricercaDto.getEnteSorgenteOrigine());
			q.setParameter("progOrigine", ricercaDto.getProgOrigine());
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("dataDocfa", ricercaDto.getDataRif());
			q.setParameter("idDwhOrigineCiv", ricercaDto.getIdDwhOrigineCiv());
			
			List<Object[]> lista = q.getResultList();
			
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni TARSU:"+ lista.size());
		
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<LocazioniA> getLocazioniDaTarsuBySoggCivicoData(DiagnosticheDataIn dataIn){
		
		logger.debug("Ricerca Locazioni da Tarsu per Soggetto, Civico alla data della dichiarazione...");
		
		logger.debug("Parametro idExt Oggetto Tarsu:" + (String)dataIn.getObj());
		logger.debug("Parametro dtIniTarsu:" + (Date)dataIn.getObj2());
		logger.debug("Parametro idSoggTarsu:" + (String)dataIn.getObj3());
		
		Query q = manager_diogene.createNamedQuery("LocazioniA.getLocazioniDaTarsuBySoggCivicoData");
		q.setParameter("idExtTarsu", (String)dataIn.getObj());
		q.setParameter("dtIniTarsu", (Date)dataIn.getObj2());
		q.setParameter("idSoggTarsu", (String)dataIn.getObj3());
		List<LocazioniA> locazioni = q.getResultList();
		
		return locazioni;
	}
	
	public String getTitolaritaDaTarsuBySoggUiuData(RicercaOggettoCatDTO rcat, String idDwhTarsuSogg){
		String titolo = null;
		
		logger.debug("Ricerca Titolarità a Catasto da Tarsu per Soggetto, Par.Catastali alla data della dichiarazione...");
		
		logger.debug("Parametro Cod.Nazionale:" + rcat.getCodEnte());
		logger.debug("Parametro Foglio:" + rcat.getFoglio());
		logger.debug("Parametro Particella:" + rcat.getParticella());
		logger.debug("Parametro Subalterno:" + rcat.getUnimm());
		logger.debug("Parametro dtIniTarsu:" + rcat.getDtVal());
		logger.debug("Parametro idSoggTarsu:" + idDwhTarsuSogg);
		
		Query q = manager_diogene.createNamedQuery("ConsDecoTab.getTitolaritaDaTarsuBySoggUiuData");
		q.setParameter("codEnte", rcat.getCodEnte());
		q.setParameter("foglio", StringUtils.cleanLeftPad(rcat.getFoglio(),'0'));
		q.setParameter("particella", rcat.getParticella());
		q.setParameter("unimm", StringUtils.cleanLeftPad(rcat.getUnimm(),'0'));
		q.setParameter("dtRef", rcat.getDtVal());
		q.setParameter("idSoggTarsu", idDwhTarsuSogg);
		List<String> titolarita = (List<String>)q.getResultList();
		
		logger.debug("Result:"+ titolarita.size());
		
		if(titolarita.size()>0)
			titolo = titolarita.get(0);
		
		return titolo;
	}
	
	public List<String> getListaIdDwhSoggAnagFromCatasto(RicercaIciTarsuDTO ro){
		
		logger.debug("Ricerca Codici Anagrafici dei Titolari a Catasto...");
		logger.debug("Parametro IdDwh Soggetto Catasto:" + ro.getIdDwhOrigineSogg());
		
		Query q = manager_diogene.createNamedQuery("Indice.getAnagrafeDaSoggCat");
		q.setParameter("idDwhSoggCat", ro.getIdDwhOrigineSogg());
		List<String> result =  q.getResultList();
		logger.debug("Result:"+ result.size());
		return result;
	}

	
	@Override
	public List<DocfaTarReport> getListaByDocfaTipo(RicercaOggettoDocfaDTO rd) {
		List<DocfaTarReport> result = new ArrayList<DocfaTarReport>();
		try{
			
			logger.debug("Ricerca Docfa in base al tipo...");
			
			logger.debug("Parametro fornitura:" + rd.getFornitura());
			logger.debug("Parametro protocollo:" + rd.getProtocollo());
			logger.debug("Parametro tipo:" + rd.getTipoOperDocfa());
			
			Query q = manager_diogene.createNamedQuery("DocfaTarReport.getListaByDocfaTipo");

			q.setParameter("fornitura", rd.getFornitura());
			q.setParameter("protocollo", rd.getProtocollo());
			
			if(rd.getTipoOperDocfa().equals("S")) 
				q.setParameter("tipo", "C");
			else 
				q.setParameter("tipo", "S");
			
			return result = q.getResultList();
			
		}catch(NoResultException t){
			throw new DiagnosticheIciServiceException(t);
		}
	}
	
}
