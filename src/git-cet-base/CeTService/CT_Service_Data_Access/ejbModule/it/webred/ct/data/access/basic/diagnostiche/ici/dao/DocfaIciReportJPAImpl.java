package it.webred.ct.data.access.basic.diagnostiche.ici.dao;

import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.diagnostiche.dao.DiagnosticheBaseDAO;
import it.webred.ct.data.access.basic.diagnostiche.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaIciTarsuDTO;
import it.webred.ct.data.access.basic.diagnostiche.ici.DiagnosticheIciServiceException;
import it.webred.ct.data.access.basic.diagnostiche.ici.dto.SitTIciOggettoDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.access.basic.ici.dto.SoggettoIciDTO;
import it.webred.ct.data.model.diagnostiche.DocfaIciReport;
import it.webred.ct.data.model.diagnostiche.DocfaIciReportSogg;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.ici.SitTIciSogg;
import it.webred.ct.data.model.ici.SitTIciVia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class DocfaIciReportJPAImpl extends DiagnosticheBaseDAO implements
		DocfaIciReportDAO {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Date> getListaForniture(){
		List<Date> lista = new ArrayList<Date>();
		
		try {
			Query q = manager_diogene.createNamedQuery("DocfaIciReport.getForniture");
			lista =  q.getResultList();

	} catch (Throwable t) {
		throw new DiagnosticheIciServiceException(t);
	}
		
		return lista;
	}
	
	
	@Override
	public DocfaIciReport getDocfaIciReportById(String id)throws DiagnosticheIciServiceException {
		DocfaIciReport rep = new DocfaIciReport();
		String anomalie = "";
		try {
				logger.debug("DocfaIciReport.getById:" + id );
				Query q = manager_diogene.createNamedQuery("DocfaIciReport.getById").setParameter("id", id);
				logger.debug("createNamedQuery DocfaIciReport.getById:" + id );
				rep = (DocfaIciReport) q.getSingleResult();
				logger.debug("rep=" + rep.getCodAnomalie());
				return getReportDescAnomalie(rep);
	
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
	}
	
	private DocfaIciReport getReportDescAnomalie(DocfaIciReport rep){
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
			throws DiagnosticheIciServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaIciReport.getVie").setParameter("via", via.toUpperCase()).setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}

	}
	
	@Override
	public List<Object> getSuggestionCognomi(String cognome)
			throws DiagnosticheIciServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getCognomi").setParameter("cognome", cognome.toUpperCase()).setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}

	}
	
	@Override
	public List<Object> getSuggestionNomi(String nome)
			throws DiagnosticheIciServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getNomi").setParameter("nome", nome.toUpperCase()).setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}

	}
	
	@Override
	public List<Object> getSuggestionCodFisc(String codFisc)
			throws DiagnosticheIciServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getCodFisc").setParameter("codFisc", codFisc.toUpperCase()).setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}

	}
	
	@Override
	public List<DocfaIciReport> searchReport(RicercaDocfaReportDTO rs)throws DiagnosticheIciServiceException {
		
		ArrayList<DocfaIciReport> repsAnomalie = new ArrayList<DocfaIciReport>();
		
		try {

			String sql = (new DocfaIciReportQueryBuilder(rs)).createQuery(false);
			logger.debug("Ricerca Report Ici SQL["+sql+"]");
			
			Query q = manager_diogene.createQuery(sql);
			if (rs.getStartm() != 0 || rs.getNumberRecord() != 0) {
				q.setFirstResult(rs.getStartm());
				q.setMaxResults(rs.getNumberRecord());
			}
			
			ArrayList<DocfaIciReport> reps = (ArrayList<DocfaIciReport>) q.getResultList();
			
			logger.info("Search Report Result["+reps.size()+"]"); 
			
			for(int i=0; i<reps.size(); i++)
			    repsAnomalie.add(getReportDescAnomalie(reps.get(i)));
			 
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return repsAnomalie;
		
	}
	
	@Override
	public List<DocfaIciReportSogg> getReportSogg(String id)
			throws DiagnosticheIciServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getSoggetti").setParameter("report", id);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}

	}
	
	@Override
	public List<DocfaIciReportSogg> getReportSoggTitolari(String id)
			throws DiagnosticheIciServiceException {
				
		try {

				Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getSoggettiTitolari").setParameter("report", id);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}

	}
	
	@Override
	public Long searchReportCount(RicercaDocfaReportDTO rs)
			throws DiagnosticheIciServiceException {
				
		try {

			String sql = (new DocfaIciReportQueryBuilder(rs))
					.createQuery(true);

			Query q = manager_diogene.createQuery(sql);
			Long l = (Long) q.getSingleResult();
			return l == null? new Long(0): l;
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
	}
	
	@Override
	public List<Object[]> countDocfaFornitura()
			throws DiagnosticheIciServiceException {
						
		try {

			Query q = manager_diogene.createNamedQuery("DocfaIciReport.getCount");
			return q.getResultList();
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
	}
	

	
	@Override
	public List<SitTIciOggettoDTO> getIciDocfa(RicercaDocfaReportDTO ricercaDto)
			throws DiagnosticheIciServiceException {
						
		List<SitTIciOggettoDTO> listaDto = new ArrayList<SitTIciOggettoDTO>();
		try {

			Query q = manager_diogene.createNamedQuery("DocfaIciReport.getIci");
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getUnimm() != null? ricercaDto.getUnimm(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTIciOggettoDTO dto = new SitTIciOggettoDTO();
				dto.setSitTIciOggetto((SitTIciOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
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
	
	@Override
	public List<SitTIciOggettoDTO> getIciAnteDocfaBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException {
						
		List<SitTIciOggettoDTO> listaDto = new ArrayList<SitTIciOggettoDTO>();
		try {

			logger.info("Ricerca Dichiarazioni Ici da Soggetto Ante Docfa per Uiu indefinita...");
			Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getIciAnte");
			q.setParameter("catpkid", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("datadocfa", ricercaDto.getDataRif());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTIciOggettoDTO dto = new SitTIciOggettoDTO();
				dto.setSitTIciOggetto((SitTIciOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni ICI:"+ lista.size());
			
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTIciOggettoDTO> getIciPostDocfaBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException {
						
		List<SitTIciOggettoDTO> listaDto = new ArrayList<SitTIciOggettoDTO>();
		try {
			logger.info("Ricerca Dichiarazioni Ici da Soggetto Post Docfa per Uiu indefinita...");
			Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getIciPost");
			q.setParameter("catpkid", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("datadocfa", ricercaDto.getDataRif());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTIciOggettoDTO dto = new SitTIciOggettoDTO();
				dto.setSitTIciOggetto((SitTIciOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni ICI:"+ lista.size());
			
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTIciOggettoDTO> getIciAnteDocfaUiuBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException {
						
		List<SitTIciOggettoDTO> listaDto = new ArrayList<SitTIciOggettoDTO>();
		try {
			logger.info("Ricerca Dichiarazioni Ici da Soggetto Ante Docfa per Uiu...");
			Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getIciAnteUiu");
			q.setParameter("catpkid", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("datadocfa", ricercaDto.getDataRif());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTIciOggettoDTO dto = new SitTIciOggettoDTO();
				dto.setSitTIciOggetto((SitTIciOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni ICI:"+ lista.size());
		
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTIciOggettoDTO> getIciPostDocfaUiuBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException {
						
		List<SitTIciOggettoDTO> listaDto = new ArrayList<SitTIciOggettoDTO>();
		try {
			logger.info("Ricerca Dichiarazioni Ici da Soggetto Post Docfa per Uiu...");
			Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getIciPostUiu");
			q.setParameter("catpkid", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("datadocfa", ricercaDto.getDataRif());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ricercaDto.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ricercaDto.getParticella(),'0'));
			q.setParameter("sub", ricercaDto.getSub() != null? ricercaDto.getSub(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTIciOggettoDTO dto = new SitTIciOggettoDTO();
				dto.setSitTIciOggetto((SitTIciOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni ICI:"+ lista.size());
		
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTIciOggettoDTO> getIciAnteDocfaCivBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException {
						
		List<SitTIciOggettoDTO> listaDto = new ArrayList<SitTIciOggettoDTO>();
		try {
			logger.info("Ricerca Dichiarazioni Ici da Soggetto Ante Docfa per Civico...");
			Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getIciAnteCiv");
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("datadocfa", ricercaDto.getDataRif());
			q.setParameter("idDwhOrigineCiv", ricercaDto.getIdDwhOrigineCiv());
			
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTIciOggettoDTO dto = new SitTIciOggettoDTO();
				dto.setSitTIciOggetto((SitTIciOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni ICI:"+ lista.size());
		
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<SitTIciOggettoDTO> getIciPostDocfaCivBySoggetto(RicercaIciTarsuDTO ricercaDto)
			throws DiagnosticheIciServiceException {
						
		List<SitTIciOggettoDTO> listaDto = new ArrayList<SitTIciOggettoDTO>();
		try {
			logger.info("Ricerca Dichiarazioni Ici da Soggetto Post Docfa per Civico...");
			Query q = manager_diogene.createNamedQuery("DocfaIciReportSogg.getIciPostCiv");
			q.setParameter("idDwhOrigineSogg", ricercaDto.getIdDwhOrigineSogg());
			q.setParameter("datadocfa", ricercaDto.getDataRif());
			q.setParameter("idDwhOrigineCiv", ricercaDto.getIdDwhOrigineCiv());
			
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTIciOggettoDTO dto = new SitTIciOggettoDTO();
				dto.setSitTIciOggetto((SitTIciOggetto) o[0]);
				dto.setVia((String) o[1]);
				
				listaDto.add(dto);
			}
			
			this.logParamsRicercaIciTarsuDTO(ricercaDto);
			logger.info("Num. dichiarazioni ICI:"+ lista.size());
		
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<IndirizzoDTO> getIndirizziCatasto(RicercaDocfaReportDTO rs)
			throws DiagnosticheIciServiceException {
						
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
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
	}
	
	@Override
	public List<IndirizzoDTO> getIndirizziIci(RicercaDocfaReportDTO rs)
			throws DiagnosticheIciServiceException {
						
		List<IndirizzoDTO> listaDto = new ArrayList<IndirizzoDTO>();
		try {

			Query q = manager_diogene.createNamedQuery("IndirizzoDTO.getIndirizziIci");
			q.setParameter("foglio", rs.getFoglio());
			q.setParameter("particella", rs.getParticella());
			q.setParameter("sub", rs.getUnimm() != null? rs.getUnimm(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				IndirizzoDTO dto = new IndirizzoDTO();
				dto.setIndirizzo((String) o[0]);
				dto.setCivico((String) o[1]);
				dto.setAnno((String) o[2]);
				
				listaDto.add(dto);
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
	}

	@Override
	public List<SoggettoIciDTO> getSoggettiIci(String idExt)
			throws DiagnosticheIciServiceException {
						
		List<SoggettoIciDTO> listaDto = new ArrayList<SoggettoIciDTO>();
		try {

			Query q = manager_diogene.createNamedQuery("SoggettoIciDTO.getSoggettiIci");
			q.setParameter("idExt", idExt);
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SoggettoIciDTO dto = new SoggettoIciDTO();
				dto.setTitolo((String) o[0]);
				dto.setSitTIciSogg((SitTIciSogg) o[1]);
				
				dto.setDescViaResidenza(this.getDescViaResSoggetto(dto.getSitTIciSogg().getIdExtViaRes(),dto.getSitTIciSogg().getNumCivExt()));
				
				listaDto.add(dto);
			}
			
		} catch (Throwable t) {
			throw new DiagnosticheIciServiceException(t);
		}
		
		return listaDto;
		
	}
	
	private String getDescViaResSoggetto(String idExtViaRes, String civico){
		String desc = null;
		
		try{
			
			Query qv = manager_diogene.createNamedQuery("SitTIciVia.getViaByIdExt");

			//Recuper la descrizione della via di residenza del contribuente tarsu
			qv.setParameter("idExt", idExtViaRes);
			SitTIciVia viaRes = (SitTIciVia)qv.getSingleResult();
			
			desc = viaRes.getDescrizione();
			if(civico!=null)
				desc += " " + StringUtils.cleanLeftPad(civico, '0');
			
		}catch(NoResultException nre){
			logger.warn("Nessuna Via Trovata per: [idExtViaRes:"+idExtViaRes+"]");
		}
		
		return desc;
	}


	@Override
	public List<DocfaIciReport> getListaByDocfaTipo(RicercaOggettoDocfaDTO rd) {
		List<DocfaIciReport> result = new ArrayList<DocfaIciReport>();
		try{
			
			Query q = manager_diogene.createNamedQuery("DocfaIciReport.getListaByDocfaTipo");

			//Recuper la descrizione della via di residenza del contribuente tarsu
			q.setParameter("fornitura", rd.getFornitura());
			q.setParameter("protocollo", rd.getProtocollo());
			String dataReg = rd.getDataRegistrazione();
			/*if(dataReg==null)
				dataReg = "";	
			q.setParameter("dataRegistrazione",dataReg);*/
			
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
