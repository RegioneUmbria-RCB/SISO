package it.webred.ct.data.access.basic.tarsu.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.DiagnosticheTarServiceException;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.dto.SitTTarOggettoDTO;
import it.webred.ct.data.access.basic.ici.IciServiceException;
import it.webred.ct.data.access.basic.tarsu.TarsuOggQueryBuilder;
import it.webred.ct.data.access.basic.tarsu.TarsuServiceException;
import it.webred.ct.data.access.basic.tarsu.TarsuSoggQueryBuilder;
import it.webred.ct.data.access.basic.tarsu.dto.InformativaTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.OggettoTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaOggettoTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaOggettoTarsuParCatDTO;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaSoggettoTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaViaTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.SintesiDichiarazioneTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.SoggettoTarsuDTO;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;
import it.webred.ct.data.model.tarsu.SitTTarSogg;
import it.webred.ct.data.model.tarsu.SitTTarVia;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
 

public class TarsuJPAImpl extends CTServiceBaseDAO implements TarsuDAO {
	 
	@Override
	public List<String> getListaProvenienzaTarsu(){
		List<String> result = new ArrayList<String>();
		try {
			Query q = manager_diogene.createNamedQuery("SitTTarOggetto.getListaProvenienza");
			result = (List<String>) q.getResultList();
			
			
		}catch (NoResultException nre) {
			logger.warn("getListaProvenienza - No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new IciServiceException(t);
		}
	
		return result;
	}
	
	
	public List<SitTTarSogg> getListaSoggettiF(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException {
		 
		List<SitTTarSogg> result = new ArrayList<SitTTarSogg>();
		String paramCognome = rs.getCognome().trim().toUpperCase();
		
		logger.debug("LISTA SOGGETTI F TARSU ["+paramCognome+"]");
			 
		try{
			
			Query q = manager_diogene.createNamedQuery("SitTTarSogg.getListaSoggettiByCognome");
			q.setParameter("cognome", paramCognome);
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");
			
		}catch(Throwable t) {
			
			throw new TarsuServiceException(t);
		}
		return result;
		
	}
	
	public List<SitTTarSogg> getListaSoggettiG(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException {
		
		List<SitTTarSogg> result = new ArrayList<SitTTarSogg>();
		String paramDenom = rs.getDenom().trim().toUpperCase();
		
		logger.debug("LISTA SOGGETTI G TARSU [Denominazione: "+paramDenom+"]");
			
		try{
			Query q = manager_diogene.createNamedQuery("SitTTarSogg.getListaSoggettiByDenominazione");
			q.setParameter("denominazione", paramDenom);
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
					
		}catch(Throwable t) {
			throw new TarsuServiceException(t);
		}
		return result;
		
	}
	
	public List<SitTTarSogg> getSoggettoByCF(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException {
		
		List<SitTTarSogg> result = new ArrayList<SitTTarSogg>();
		String paramCF = rs.getCodFis().trim().toUpperCase();
		
		logger.debug("LISTA SOGGETTI F TARSU [CF: "+paramCF+"]");
			
		try{
			Query q = manager_diogene.createNamedQuery("SitTTarSogg.getSoggettoByCF");
			q.setParameter("codfisc", paramCF);
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
					
		}catch(Throwable t) {
			throw new TarsuServiceException(t);
		}
		return result;
		
	}
	
	public List<SitTTarSogg> getSoggettoByPIVA(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException {
		
		List<SitTTarSogg> result = new ArrayList<SitTTarSogg>();
		String paramPIVA = rs.getParIva().trim().toUpperCase();
		
		logger.debug("LISTA SOGGETTI G TARSU [PIVA: "+paramPIVA+"]");
			
		try{
			Query q = manager_diogene.createNamedQuery("SitTTarSogg.getSoggettoByPIVA");
	
			q.setParameter("piva", paramPIVA);
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
		
		}catch(Throwable t) {
			throw new TarsuServiceException(t);
		}
		return result;
		
	}
	
	@Override
	public Long getDichTarsuRecordCount(RicercaOggettoTarsuDTO ro)  throws TarsuServiceException {
		try {
			
			String sql = ro.getSql();
			Query q = manager_diogene.createNativeQuery(sql);
			Object o = q.getSingleResult();
			
			return (Long) o;
			
		}catch(Throwable t) {
			throw new TarsuServiceException(t);
		}
	}
	
	@Override
	public List<SintesiDichiarazioneTarsuDTO> getListaDichiarazioniTarsu(RicercaOggettoTarsuDTO ro) {
		
		ArrayList<SintesiDichiarazioneTarsuDTO> result = new ArrayList<SintesiDichiarazioneTarsuDTO>();
		
		try {
			
			String sql = ro.getSql();
			int startm = ro.getStartm();
			int numberRecord = ro.getNumberRecord();
			
			Query q = manager_diogene.createNativeQuery(sql);
			q.setFirstResult(startm);
			q.setMaxResults(numberRecord);
			
			List<Object[]> list = q.getResultList();
			
			for(Object[] rs:list){
				
				SintesiDichiarazioneTarsuDTO dich = new SintesiDichiarazioneTarsuDTO();
				
				//TODO: Scansione lista e assegnazione a DTO
				
				result.add(dich);
			}
		}catch(Throwable t) {
			throw new TarsuServiceException(t);
		}
															
		return result;
	}

	public List<SoggettoTarsuDTO> getListaSoggettiDichiarazioneTarsu(RicercaOggettoTarsuDTO ro)  throws TarsuServiceException {
		
		List<SoggettoTarsuDTO> soggetti = new ArrayList<SoggettoTarsuDTO>();
		
		try{
			
			soggetti.addAll(this.getListaDichiarantiTarsu(ro));
			soggetti.addAll(this.getListaContribuentiTarsu(ro));
			soggetti.addAll(this.getListaAltriSoggettiTarsu(ro));
			
			
		}catch(Throwable t) {
			throw new TarsuServiceException(t);
		}
		
		return soggetti;
		
	}
	
	protected List<SoggettoTarsuDTO> getListaAltriSoggettiTarsu(RicercaOggettoTarsuDTO ro)  throws TarsuServiceException {
			
		List<SoggettoTarsuDTO> soggetti = new ArrayList<SoggettoTarsuDTO>();
		SoggettoTarsuDTO soggetto;
		
		String idExtOgg = ro.getIdExtOgg();
		
		try{
			//Ricerca i soggetti ulteriori
			logger.info("RICERCA ALTRI SOGGETTI TARSU [idExtOgg"+idExtOgg+"]");
			Query qult = manager_diogene.createNamedQuery("SitTTarSogg.getUlterioriSoggettiBy_IdExtOggRsu");
			qult.setParameter("idExtOggRsu", idExtOgg);
			List<Object[]> ultSogg = qult.getResultList();
			logger.debug("Result size ["+ultSogg.size()+"]");
			
			for(Object[] ult: ultSogg){
				
				soggetto = new SoggettoTarsuDTO();
				soggetto.setTitolo(ult[0].toString());
				soggetto.setSoggetto((SitTTarSogg)ult[1]);
				
				String descViaRes = getDescViaResidenzaSoggetto(ro.getEnteId(),soggetto.getSoggetto().getIdExtViaRes(),soggetto.getSoggetto().getNumCivExt());
				soggetto.setDescViaResidenza(descViaRes);
				
				soggetti.add(soggetto);
			}
			
		}catch(Throwable t) {
			throw new TarsuServiceException(t);
		}
		
		return soggetti;
		
	}
		
	protected List<SoggettoTarsuDTO> getListaDichiarantiTarsu(RicercaOggettoTarsuDTO ro)  throws TarsuServiceException {
			
		List<SoggettoTarsuDTO> soggetti = new ArrayList<SoggettoTarsuDTO>();
		SoggettoTarsuDTO soggetto;
		
		String idExtOgg = ro.getIdExtOgg();
		
		try{
			
			//Ricerca i dichiaranti
			logger.info("RICERCA SOGGETTI DICHIARANTI TARSU [idExtOgg"+idExtOgg+"]");
			Query qdic = manager_diogene.createNamedQuery("SitTTarSogg.getListaDichiarantiBy_IdExtOggRsu");
			qdic.setParameter("idExtOggRsu", idExtOgg);
			List<SitTTarSogg> dichiaranti = qdic.getResultList();
			logger.debug("Result size ["+dichiaranti.size()+"]");
			
			for(SitTTarSogg dic: dichiaranti){
				
				soggetto = new SoggettoTarsuDTO();
				soggetto.setSoggetto(dic);
				soggetto.setTitolo("Dichiarante");
				
				String descViaRes = getDescViaResidenzaSoggetto(ro.getEnteId(),dic.getIdExtViaRes(),dic.getNumCivExt());
				soggetto.setDescViaResidenza(descViaRes);
				
				soggetti.add(soggetto);
			}
			
		}catch(Throwable t) {
			throw new TarsuServiceException(t);
		}
		
		return soggetti;
		
	}
	
	protected List<SoggettoTarsuDTO> getListaContribuentiTarsu(RicercaOggettoTarsuDTO ro)  throws TarsuServiceException {
	
		List<SoggettoTarsuDTO> soggetti = new ArrayList<SoggettoTarsuDTO>();
		SoggettoTarsuDTO soggetto;
		
		String idExtOgg = ro.getIdExtOgg();
		
		try{
			
			//Ricerca i contribuenti
			logger.debug("RICERCA SOGGETTI CONTRIBUENTI TARSU [idExtOgg"+idExtOgg+"]");
			Query qcnt = manager_diogene.createNamedQuery("SitTTarSogg.getListaContribuentiBy_IdExtOggRsu");
			qcnt.setParameter("idExtOggRsu", idExtOgg);
			List<SitTTarSogg> contrib = qcnt.getResultList();
			logger.debug("Result size ["+contrib.size()+"]");
			
			for(SitTTarSogg cnt: contrib){
				
				soggetto = new SoggettoTarsuDTO();
				soggetto.setSoggetto(cnt);
				soggetto.setTitolo("Contribuente");
				
				String descViaRes = getDescViaResidenzaSoggetto(ro.getEnteId(),cnt.getIdExtViaRes(),cnt.getNumCivExt());
				soggetto.setDescViaResidenza(descViaRes);
				
				soggetti.add(soggetto);
			}
			
		}catch(Throwable t) {
			throw new TarsuServiceException(t);
		}
		
		return soggetti;
	
	}
	
	protected String getDescViaResidenzaSoggetto(String idEnte, String idExtViaRes, String civico){
		String desc = "";
		
		RicercaViaTarsuDTO rv = new RicercaViaTarsuDTO();
		rv.getDatiVia().setIdExt(idExtViaRes);
		rv.setEnteId(idEnte);
		SitTTarVia viaRes = getViaByIdExt(rv);
		
		if(viaRes!=null)
			desc = viaRes.getDescrizione()== null ? "" : viaRes.getDescrizione();
		
		if(desc.length()>0 && civico!=null){
			String lcivico = StringUtils.cleanLeftPad(civico, '0');
			desc = lcivico.equalsIgnoreCase("0")? desc : desc + " " + lcivico ;
		}
		
		return desc;
	}
	

	
	public List<SitTTarOggetto> getListaDichiarazioniTarsu(RicercaOggettoTarsuParCatDTO ro)  throws TarsuServiceException {
		
		List<SitTTarOggetto> listaOggetti = new ArrayList<SitTTarOggetto>();
		String foglio = ro.getFoglio();
		String particella = ro.getParticella();
		String unimm = ro.getSubalterno();
		
		if
			(
					(foglio != null && !foglio.equals("")) &&
					(particella != null && !particella.equals("")) &&
					(unimm != null && !unimm.equals(""))
			)
			
			{
			try{
			
			logger.debug("RICERCA DICHIARAZIONI TARSU [" +
					"Foglio: "+foglio+", " +
					"Particella: "+particella+", " +
					"Subalterno: "+unimm+"]");
			
					Query q0 =null;
					if (ro.getSezione() == null || ro.getSezione().equals(""))
						q0 = manager_diogene.createNamedQuery("SitTTarOggetto.getListaOggettiTARSUBYFPS");
					else
						q0 = manager_diogene.createNamedQuery("SitTTarOggetto.getOggettiBYSFPS");
					q0.setParameter("foglio", foglio);
					q0.setParameter("particella", particella);
					q0.setParameter("unimm", unimm);
					if (ro.getSezione() != null && !ro.getSezione().equals(""))
						q0.setParameter("sez", ro.getSezione());
					listaOggetti = q0.getResultList();
					logger.debug("Result size ["+listaOggetti.size()+"]");
			
			}catch(Throwable t) {
				logger.error("", t);
				throw new TarsuServiceException(t);
			}
			
		}else{
			String message = "Parametri non validi per la ricerca TARSU [" +
			"Foglio: "+foglio+", " +
			"Particella: "+particella+", " +
			"Subalterno: "+unimm+"]";
			logger.warn(message);
			//throw new TarsuServiceException(message);	
		}
		
		return listaOggetti;
	}
	
	public List<InformativaTarsuDTO> getListaInformativaTarsu(RicercaOggettoTarsuDTO ro)  throws TarsuServiceException {
		
		List<InformativaTarsuDTO> infolista = new ArrayList<InformativaTarsuDTO>();
		RicercaOggettoTarsuParCatDTO roCat = new RicercaOggettoTarsuParCatDTO();
		roCat.setSezione(ro.getSezione());
		roCat.setFoglio(ro.getFoglio());
		roCat.setParticella(ro.getParticella());
		roCat.setSubalterno(ro.getUnimm());		
		List<SitTTarOggetto> lista = this.getListaDichiarazioniTarsu(roCat);
		boolean loadSoggetti = ro.isLoadSoggetti();
		
		for(SitTTarOggetto o: lista){
			InformativaTarsuDTO info = new InformativaTarsuDTO();
			info.setOggettoTarsu(o);
			if(loadSoggetti)
				this.setSoggettiDichiarazioneTARSU(info);
			
			infolista.add(info);
		}
		
		return infolista;
	}
	
	
	public InformativaTarsuDTO getInformativaTarsu(RicercaOggettoTarsuDTO ro) throws TarsuServiceException {
			
		InformativaTarsuDTO info = new InformativaTarsuDTO();
		String chiave = ro.getChiave();
		boolean loadSoggetti = ro.isLoadSoggetti();	
		
		try {
		
			Query q0 = manager_diogene.createNamedQuery("SitTTarOggetto.getDettaglioOggettoTARSU");
    		q0.setParameter(1, chiave);
    		SitTTarOggetto oggettoTarsu = (SitTTarOggetto)q0.getSingleResult();
    		
			info.setOggettoTarsu(oggettoTarsu);
			if(loadSoggetti)
				this.setSoggettiDichiarazioneTARSU(info);
			
		}catch(NoResultException nre){
			logger.warn("Result size [0] " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);
		}
		return info;
		
	}

	protected InformativaTarsuDTO setSoggettiDichiarazioneTARSU(InformativaTarsuDTO info)  throws TarsuServiceException {
		SitTTarOggetto o = info.getOggettoTarsu();
		
		if(o!=null){
			String idExtOgg = o.getIdExt();
			RicercaOggettoTarsuDTO ro = new RicercaOggettoTarsuDTO();
			ro.setIdExtOgg(idExtOgg);
			
			info.setDichiaranti(this.getListaDichiarantiTarsu(ro));
			info.setContribuenti(this.getListaContribuentiTarsu(ro));
			info.setUlterioriSoggetti(this.getListaAltriSoggettiTarsu(ro));
		}
		return info;
	}
	@Override
	public List<SitTTarSogg> searchSoggetto(RicercaSoggettoTarsuDTO rs)  throws TarsuServiceException  {
		List<SitTTarSogg> lista=null;
		try{
			logger.debug("TarsuJPAImp.searchSoggetto()");
			String sql = (new TarsuSoggQueryBuilder(rs)).createQuery(false);
			logger.debug("searchSoggettoTarsu. SQL: " + sql);
			Query q = manager_diogene.createQuery(sql);
			if (rs.getTipoRicerca() != null && rs.getTipoRicerca().equals("all")) {
				lista = (List<SitTTarSogg> )q.getResultList();
				logger.debug("Result size ["+lista.size()+"]");
			}
			else {
				List<Object[]> res = q.getResultList();
				logger.debug("Result size ["+res.size()+"]");
				lista = new ArrayList<SitTTarSogg>();
				for (Object[] ele : res) {
					SitTTarSogg sogg = new SitTTarSogg(); 
					sogg.setTipSogg(rs.getTipoSogg());
					if (rs.getTipoSogg().equals("F"))  {
						if (ele[0] != null)
							sogg.setCogDenom(ele[0].toString().trim());
						if (ele[1] != null)
							sogg.setNome(ele[1].toString().trim());
						if (ele[1] != null)
							sogg.setDtNsc((Date)ele[2]);
						if (ele[3] != null)
							sogg.setCodFisc(ele[3].toString().trim());
						if (ele[4] != null)
							sogg.setCodCmnNsc(ele[4].toString().trim());
						if (ele[5] != null)
							sogg.setDesComNsc(ele[5].toString().trim());
					}else {
						if (ele[0] != null) 	
							sogg.setCogDenom(ele[0].toString().trim());
						if (ele[1] != null)
							sogg.setPartIva(ele[1].toString().trim());
					}
					lista.add(sogg);
				}
			}
		}catch (NoResultException nre) {
			logger.warn("getListaSoggetti - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);
		}
		return lista;
	}

	@Override
	public SitTTarSogg getSoggettoById(RicercaSoggettoTarsuDTO rs)  throws TarsuServiceException {
		SitTTarSogg sogg= null;
		logger.debug("TarsuJPAImpl.getSoggettoById. Id sogg: " + rs.getIdSoggTarsu());
		try{
			Query q = manager_diogene.createNamedQuery("SitTTarSogg.getSoggettoById");
			q.setParameter("id", rs.getIdSoggTarsu());
			sogg = (SitTTarSogg)q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("TarsuJPAImpl.getSoggettoById - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);
		}
		return sogg;
	}

	@Override
	public List<OggettoTarsuDTO> getListaOggettiByIdSogg(RicercaSoggettoTarsuDTO rs)  throws TarsuServiceException {
		String idSogg = rs.getIdSoggTarsu();
		List<OggettoTarsuDTO> oggettiTarsu = new ArrayList<OggettoTarsuDTO>();
		OggettoTarsuDTO	ogg;  
		logger.debug("LISTA OGGETTI TARSU - ID SOGGETTO["+idSogg+"]");
			
		try{
			Query q = manager_diogene.createNamedQuery("SitTTarOggetto.getOggettiByIdSogg");
			q.setParameter("idSogg", idSogg);
			List<Object[]> res = q.getResultList();
			logger.debug("Result size ["+res.size()+"]");
			
			for(Object[] eleRes: res){
				ogg = new OggettoTarsuDTO(eleRes[1].toString(), (SitTTarOggetto)eleRes[0]);
				String str = ogg.getOggettoTarsu().getSez();
				if (str==null )
					ogg.getOggettoTarsu().setSez("");
				else
					ogg.getOggettoTarsu().setSez(str.trim());
				str = ogg.getOggettoTarsu().getFoglio();
				if (str==null || str.equals("") || Integer.parseInt(str) == 0 )
					ogg.getOggettoTarsu().setFoglio("");
				else
					ogg.getOggettoTarsu().setFoglio(str.trim());
				str = ogg.getOggettoTarsu().getNumero();
				if (str==null || str.equals("") || Integer.parseInt(str) == 0 )
					ogg.getOggettoTarsu().setNumero("");
				else
					ogg.getOggettoTarsu().setNumero(str.trim());
				str = ogg.getOggettoTarsu().getSub();
				if (str==null || str.equals("") || Integer.parseInt(str) == 0 )
					ogg.getOggettoTarsu().setSub("");
				else
					ogg.getOggettoTarsu().setSub(str.trim());
				oggettiTarsu.add(ogg);
			}
				
		}catch (NoResultException nre) {
			logger.warn("getListaOggettiByIdSogg - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);
		}
		return oggettiTarsu;
	}

	@Override
	public SitTTarVia getViaByIdExt(RicercaViaTarsuDTO rv)  throws TarsuServiceException {
		SitTTarVia via =null;
		try {
			logger.info("TarsuJPAImpl.getViaByIdExt - IdExt:  ["+rv.getDatiVia().getIdExt()+"]");
			Query q = manager_diogene.createNamedQuery("SitTTarVia.getViaByIdExt");
			q.setParameter("idExt", rv.getDatiVia().getIdExt());	
			via = (SitTTarVia) q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("TarsuJPAImpl.getViaByIdExt - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);
		}
		return via;
	}

	@Override
	public List<SitTTarOggetto> getListaOggettiByProvFabbricato(RicercaOggettoDTO ro) 	throws TarsuServiceException {
		List<SitTTarOggetto> lista = new ArrayList<SitTTarOggetto>();
		logger.debug("LISTA OGGETTI TARSU PER FABBRICATO - SEZ["+ro.getSezione()+"];FOGLIO[" + ro.getFoglio()+ "];PARTICELLA["+ ro.getParticella()+"]");
		String sez = ro.getSezione();
		Query q =null;
		try{
			if (sez!=null && !sez.equals("")) {
				q = manager_diogene.createNamedQuery("SitTTarOggetto.getOggettiBYProvenSezFabbricato");
				q.setParameter("sez", sez);
			}
			else
				q = manager_diogene.createNamedQuery("SitTTarOggetto.getOggettiBYProvenFabbricato");
			q.setParameter("provenienza", ro.getProvenienza().trim());
			q.setParameter("foglio", ro.getFoglio().trim());
			q.setParameter("particella", ro.getParticella().trim());
			lista =(List<SitTTarOggetto>)q.getResultList();
			logger.debug("Result size ["+lista.size()+"]");
		}catch (NoResultException nre) {
			logger.warn("getListaOggettiByFabbricato - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);
		}
		return lista;
	}
	
	public List<SitTTarOggetto> getListaOggettiByUI(RicercaOggettoDTO ro) throws TarsuServiceException {
		List<SitTTarOggetto> lista = new ArrayList<SitTTarOggetto>();
		logger.debug("LISTA OGGETTI TARSU PER U.I. - SEZ["+ro.getSezione()+"];FOGLIO[" + ro.getFoglio()+ "];PARTICELLA["+ ro.getParticella()+ "];SUB["+ ro.getSub()+"]");
		String sez = ro.getSezione();
		Query q =null;
		try{
			if (sez!=null && !sez.equals("")) {
				q = manager_diogene.createNamedQuery("SitTTarOggetto.getOggettiBYProvenienzaSezFPS");
				q.setParameter("sezione", sez);
			}
			else
				q = manager_diogene.createNamedQuery("SitTTarOggetto.getOggettiBYProvenienzaFPS");
			q.setParameter("provenienza", ro.getProvenienza().trim());
			q.setParameter("foglio", ro.getFoglio().trim());
			q.setParameter("particella", ro.getParticella().trim());
			q.setParameter("unimm", ro.getSub().trim());
			lista =(List<SitTTarOggetto>)q.getResultList();
			logger.debug("Result size ["+lista.size()+"]");
		}catch (NoResultException nre) {
			logger.warn("getListaOggettiByUI - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);
		}
		return lista;
	}

	@Override
	public List<SitTTarOggetto> getListaOggettiAiCiviciTarsu(RicercaOggettoTarsuDTO ro)	throws TarsuServiceException {
		List<SitTTarOggetto> lista = new ArrayList<SitTTarOggetto>();
		logger.debug("getOggettiAiCiviciTarsu -  LISTA OGGETTI TARSU PER CIVICI TARSU-ID-CIV N. ELE CIVICI : " + ro.getListaCivTarsu().size());
		if (ro.getListaCivTarsu().size() > 0)
			logger.debug("getOggettiAiCiviciTarsu -  LISTA OGGETTI TARSU PER CIVICI TARSU-ID-CIV ELE 0: " + ro.getListaCivTarsu().get(0).getId());
		Query q =null;
		try{
			String sql = (new TarsuOggQueryBuilder(ro)).createQueryOggettoVia();
			logger.debug("getOggettiAiCiviciTarsu - SQL: " + sql);
			q = manager_diogene.createQuery(sql);
			lista =(List<SitTTarOggetto>)q.getResultList();
			logger.debug("Result size ["+lista.size()+"]");
		}catch (NoResultException nre) {
			logger.warn("getOggettiAiCiviciTarsu - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);
		}
		return lista;
	}
	
	
	public SitTTarOggetto getOggettoByIdExt(RicercaOggettoTarsuDTO ro){
		try{
		Query q = manager_diogene.createNamedQuery("SitTTarOggetto.getOggettoByIdExt");
		q.setParameter("idExt", ro.getIdExtOgg());
		
		return (SitTTarOggetto)q.getSingleResult();
		
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);		
		}
		}

	@Override
	public List<SitTTarOggettoDTO> getListaTarsuUiu(RicercaOggettoDTO ro)throws TarsuServiceException {
						
		List<SitTTarOggettoDTO> listaDto = new ArrayList<SitTTarOggettoDTO>();
		try {
			logger.debug("LISTA OGGETTI TARSU PER UIU - " +
					"FOGLIO[" + ro.getFoglio()+ "];" +
					"PARTICELLA["+ ro.getParticella()+"];" +
					"UNIMM["+ ro.getSub()+"]");
			
			Query q = manager_diogene.createNamedQuery("SitTTarOggetto.getListaTarsuUiu");
			q.setParameter("foglio", StringUtils.cleanLeftPad(ro.getFoglio(),'0'));
			q.setParameter("numero", StringUtils.cleanLeftPad(ro.getParticella(),'0'));
			q.setParameter("sub", ro.getSub() != null? ro.getSub(): "0000");
			List<Object[]> lista = q.getResultList();
			for(Object[] o: lista){
				SitTTarOggettoDTO dto = new SitTTarOggettoDTO();
				dto.setSitTTarOggetto((SitTTarOggetto) o[0]);
				dto.setVia((String) o[1]);
				listaDto.add(dto);
			}
			
			logger.debug("LISTA OGGETTI TARSU PER UIU - Result[" +listaDto.size() + "]" );
			
		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
		
		return listaDto;
		
	}


	@Override
	public List<SitTTarOggetto> getListaOggettiByListaIdOggDWh(List<SitTTarOggetto> listaKey) throws TarsuServiceException {
		List<SitTTarOggetto> lista = new ArrayList<SitTTarOggetto>();
		String sql = "SELECT o from SitTTarOggetto o WHERE o.id IN (";
		String inClause="";
		int i = 0;
		for (SitTTarOggetto ele: listaKey) {
			if (i >0)
				inClause += ",";
			inClause +="'" + ele.getId() + "'";
			i++;
		}
		sql += inClause;
		sql += ") ORDER BY o.datFin DESC";
		logger.debug("getListaOggettiByListaIdOggDWh() sql: " + sql );
		try{
			Query q = manager_diogene.createQuery(sql);
			lista= ( List<SitTTarOggetto>) q.getResultList(); 		
		}catch(Throwable t) {
			logger.error("", t);
			throw new TarsuServiceException(t);
		}
		return lista;
	}
	
	
	
}
