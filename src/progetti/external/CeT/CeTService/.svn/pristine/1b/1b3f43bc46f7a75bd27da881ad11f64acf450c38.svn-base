package it.webred.ct.data.access.basic.catasto.dao;

import it.webred.ct.data.access.basic.catasto.CatastoServiceException;
import it.webred.ct.data.access.basic.catasto.dto.CatastoSearchCriteria;
import it.webred.ct.data.access.basic.catasto.dto.CoordBaseDTO;
import it.webred.ct.data.access.basic.catasto.dto.DataVariazioneImmobileDTO;
import it.webred.ct.data.access.basic.catasto.dto.DettaglioCatastaleImmobileDTO;
import it.webred.ct.data.access.basic.catasto.dto.ImmobileBaseDTO;
import it.webred.ct.data.access.basic.catasto.dto.ImmobiliAccatastatiOutDTO;
import it.webred.ct.data.access.basic.catasto.dto.IndirizzoDTO;
import it.webred.ct.data.access.basic.catasto.dto.InfoPerCategoriaDTO;
import it.webred.ct.data.access.basic.catasto.dto.PartDTO;
import it.webred.ct.data.access.basic.catasto.dto.ParticellaInfoDTO;
import it.webred.ct.data.access.basic.catasto.dto.ParticellaKeyDTO;
import it.webred.ct.data.access.basic.catasto.dto.PlanimetriaComma340DTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaSoggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.SintesiImmobileDTO;
import it.webred.ct.data.access.basic.catasto.dto.SoggettoCatastoDTO;
import it.webred.ct.data.access.basic.catasto.dto.SoggettoDTO;
import it.webred.ct.data.access.basic.catasto.dto.TerrenoDerivazioneDTO;
import it.webred.ct.data.access.basic.catasto.dto.TerrenoPerSoggDTO;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.StringheVie;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.model.catasto.ConsDecoTab;
import it.webred.ct.data.model.catasto.ConsSoggTab;
import it.webred.ct.data.model.catasto.LoadCatUiuId;
import it.webred.ct.data.model.catasto.SitRepTarsu;
import it.webred.ct.data.model.catasto.Siticivi;
import it.webred.ct.data.model.catasto.Siticomu;
import it.webred.ct.data.model.catasto.SiticonduzImmAll;
import it.webred.ct.data.model.catasto.Sitideco;
import it.webred.ct.data.model.catasto.Sitidstr;
import it.webred.ct.data.model.catasto.SitiediVani;
import it.webred.ct.data.model.catasto.Sitipart;
import it.webred.ct.data.model.catasto.Sitisuolo;
import it.webred.ct.data.model.catasto.Sititrkc;
import it.webred.ct.data.model.catasto.Sitiuiu;
import it.webred.ct.data.model.cnc.common.CodiceEnteCredBenef;
import it.webred.utils.GenericTuples;
import it.webred.utils.gis.Coordinates;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;



   
public class CatastoJPAImpl extends CatastoBaseDAO implements CatastoDAO {

	private final int SUGGESTION_MAX_RESULT = 15;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat sdf_bar = new SimpleDateFormat("dd/MM/yyyy");
	private final String ANNOTAZIONE_AUTO = "CREATO AUTOMATICAMENTE IN FASE DI IMPORTAZIONE UIU";
	
	
	@Override
	public String[] getLatitudineLongitudine(String foglio, String particella, String codEnte){
		
		String[] latLon = {"0","0"};
		String sqlLatLong = "SELECT DISTINCT t.y lat, t.x lon" 
			    +"    FROM sitipart_3d p, siticomu c, "
			    +"    TABLE (sdo_util.getvertices (sdo_cs.transform (p.shape_pp, "
			    +"                                                  MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 1313328, 2820083, 0.0050),MDSYS.SDO_DIM_ELEMENT('Y', 3930191, 5220322.5, 0.0050)), "
			    +"                                                 8307 "
			    +"                                                ) "
			    +"                              ) "
			    +"        ) t "
			    +"    WHERE (C.COD_NAZIONALE = '"+codEnte+"' OR C.CODI_FISC_LUNA= '"+codEnte+"' )" 
			    +"    AND P.COD_NAZIONALE = C.CODI_FISC_LUNA " 
			    +"    AND P.FOGLIO = '"+StringUtils.cleanLeftPad(foglio,'0')+"' "
			    +"    AND P.PARTICELLA = LPAD('"+particella+"',5,'0')";
		
		logger.debug("getLatitudineLongitudine - Foglio["+foglio+"], Particella["+particella+"], CodEnte["+codEnte+"]");
		logger.debug("SQL["+sqlLatLong+"]");
		
		if (foglio!= null && !foglio.equals("-") && particella!= null && !particella.equals("0000-") &&  !foglio.trim().equals("") && particella!= null && !particella.equals("    0")){
			
		try{
			Query q = manager_diogene.createNativeQuery(sqlLatLong);
		
			List<Object[]> coord = q.getResultList();
			
			logger.debug("Result getLatitudineLongitudine "+coord.size()+"]");
			if (coord.size()>0) {
				Object[] obj = coord.get(0);
				latLon[0]=obj[0].toString();
				latLon[1]=obj[1].toString();
			}
			
		}catch(Exception e){
			logger.error("", e);
			throw new CatastoServiceException(e);
		}
		
		}
		
		return latLon;
	}
	
	@Override
	public String[] getLatitudineLongitudineFromXY(String x, String y) {
		
		String[] latLon = {"0","0"};
		String sqlLatLongFromXY = 
				"select t.y lat, t.x lon from  TABLE (sdo_util.getvertices (sdo_cs.transform ( mdsys.sdo_geometry(2001, ?, mdsys.SDO_POINT_TYPE (?, ?, NULL), NULL, NULL) ,  " 
		          +"  MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 1313328, 2820083, 0.0050),MDSYS.SDO_DIM_ELEMENT('Y', 3930191, 5220322.5, 0.0050)), "
		          +"  8307  " 
		          +" ) " 
		          +") " 
		          +") t"; 
		
		logger.debug("getLatitudeLongitudeFromXY - x["+x+"], y["+y+"]");
		logger.debug("SQL["+sqlLatLongFromXY+"]");
		
		if (x!=null && y!=null){
		
			try {
				
				Double xd = new Double(x);
				Double yd = new Double(y);
				Double sysCoordinate = new Double(Coordinates.getCoordSystem(xd,yd));
				logger.debug("Sistema di Coordinate:" + sysCoordinate);
				
				Query q = manager_diogene.createNativeQuery(sqlLatLongFromXY);
				q.setParameter(1, sysCoordinate);
				q.setParameter(2, xd);
				q.setParameter(3, yd);
					
				List<Object[]> coord = q.getResultList();
				
				logger.debug("Result getLatitudeLongitudeFromXY "+coord.size()+"]");
				if (coord.size()>0) {
					Object[] obj = coord.get(0);
					latLon[0]=obj[0].toString();
					latLon[1]=obj[1].toString();
				}
			
			}catch(Exception e){
				logger.error("", e);
				throw new CatastoServiceException(e);
			}
		
		}
		return latLon;
	}
	
	
	@Override
	public List<KeyValueDTO> getListaQualitaTerreni() {

		List<KeyValueDTO> result = new ArrayList<KeyValueDTO>();

		logger.debug("getListaQualitaTerreni");

		try {
			Query q = manager_diogene.createNamedQuery("SitiCodsQual.lista");

			List<Object[]> rs = q.getResultList();
			
			logger.debug("Result size ["+rs.size()+"]");
			
			for(Object[] o :rs){
				KeyValueDTO kv = new KeyValueDTO(o[0].toString(),(String)o[1]);
				
				result.add(kv);
			}
			

		}catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
	
		return result;
	}
	
	public String getDescQualitaTerreno(BigDecimal codiQual) {

		String descQual = null;

		logger.debug("getDescQualitaTerreno");

		try {
			Query q = manager_diogene.createNamedQuery("SitiCodsQual");
			q.setParameter("codiQual", codiQual);
			
			 descQual= (String)q.getSingleResult();
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		}catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
	
		return descQual;
	}
	
	public String getDescTipoDocumento(String codice) {
		
		String desc = null;

		logger.debug("getDescTipoDocumento");

		try {
			Query q = manager_diogene.createNamedQuery("ConsDecoTab.decodeTipoDocumento");
			q.setParameter("codice", codice);
			
			ConsDecoTab descQual= (ConsDecoTab)q.getSingleResult();
			if(descQual!=null)
				desc = descQual.getDescription();
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		}catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
	
		return desc;
	}
	
	public List<Sitideco> getListaCategorieImmobile(RicercaOggettoCatDTO ro){
		List<Sitideco> result = new ArrayList<Sitideco>();
		try {			
			logger.debug("ESTRAZIONE LISTA CATEGORIE CATASTO");
			Query q;
			String codCategoria = ro.getCodCategoria();
			if (codCategoria == null) {
				q = manager_diogene.createNamedQuery("Sitideco.getAllCategorieSitiuiu");
			} else {
				q = manager_diogene.createNamedQuery("Sitideco.getCategorieSitiuiuLike");
				q.setParameter("categoriaSitiuiu", codCategoria);
			}
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
		
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		
		
		return result;
	}

	
	public List<Sitidstr> getListaVie(RicercaOggettoCatDTO ro) {

		List<Sitidstr> result = new ArrayList<Sitidstr>();
		String codNazionale = ro.getCodEnte().trim().toUpperCase();
		String via = ro.getVia().trim().toUpperCase();

		logger.debug("LISTA VIE CATASTO [" +
				"codNazionale: "+codNazionale+", " +
				"descVia: "+via+"]");

		try {
			Query q = manager_diogene.createNamedQuery("Sitidstr.getListaVieLike");

			q.setParameter("cod_nazionale", codNazionale);
			q.setParameter("via", via);
			q.setMaxResults(SUGGESTION_MAX_RESULT);
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return result;

	}

	public List<Sitidstr> getListaVieByCodiceVia(RicercaOggettoCatDTO ro) {

		List<Sitidstr> result = new ArrayList<Sitidstr>();
		String codNazionale = ro.getCodEnte().trim().toUpperCase();
		String codVia = ro.getCodiceVia().trim().toUpperCase();

		logger.debug("LISTA VIE CATASTO BY CODICE VIA [" +
				"codNazionale: "+codNazionale+", " +
				"codiceVia: "+ codVia+"]");

		try {
			Query q = manager_diogene.createNamedQuery("Sitidstr.getListaVieByCodiceVia");

			q.setParameter("cod_nazionale", codNazionale);
			q.setParameter("codiceVia", codVia);
			q.setMaxResults(SUGGESTION_MAX_RESULT);
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return result;

	}

	public List<Siticivi> getListaIndirizzi(RicercaOggettoCatDTO ro) {

		List<Siticivi> result = new ArrayList<Siticivi>();
		String codNazionale = ro.getCodEnte();
		BigDecimal pkIdStra = ro.getPkIdStra();
		String paramCivico = ro.getCivico();

		logger.debug("getListaIndirizzi [" +
				"codNazionale: "+codNazionale+", " +
				"pkIdStra: "+pkIdStra+", " +
				"civico: "+paramCivico+"]");
		Query q;
		
		try {
			if (paramCivico == null) {
				q = manager_diogene.createNamedQuery("Siticivi.getListaCivici");
				q.setParameter("codNazionale", codNazionale);
				q.setParameter("pkIdStra", pkIdStra);
			} else {
				q = manager_diogene.createNamedQuery("Siticivi.getListaCiviciLike");
				q.setParameter("codNazionale", codNazionale);
				q.setParameter("pkIdStra", pkIdStra);
				q.setParameter("civico", paramCivico);
			}
			
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return result;
	}
	
	public List<ConsSoggTab> getListaSoggettiF(RicercaSoggettoCatDTO rs) {

		List<ConsSoggTab> result = new ArrayList<ConsSoggTab>();
	
		try {
		
		if(rs.getCognome()!=null){
			
			String paramCognome = rs.getCognome().trim().toUpperCase();
			
			logger.debug("getListaSoggettiF CATASTO [Cognome: "+paramCognome+"]");
			
			if(rs.getNome()!=null && rs.getDtNas()!=null){
				
				String paramNome = rs.getNome().trim().toUpperCase();
				String paramDtNasc = sdf.format(rs.getDtNas());
				
				logger.debug("getListaSoggettiF CATASTO [Nome: "+paramNome+"]");
				logger.debug("getListaSoggettiF CATASTO [Data nascita: "+paramDtNasc+"]");
				
				Query q = manager_diogene.createNamedQuery("ConsSoggTab.getListaSoggettiByCognomeNomeDtNasc");
				q.setParameter("cognome", paramCognome );
				q.setParameter("nome", paramNome);
				q.setParameter("dtNasc", paramDtNasc);
				result = q.getResultList();
				
			}else{
			
				Query q = manager_diogene.createNamedQuery("ConsSoggTab.getListaSoggettiByCognome");
	
				q.setParameter("cognome", paramCognome);
				q.setMaxResults(SUGGESTION_MAX_RESULT);
				result = q.getResultList();
				
				}
		}
	
		logger.debug("Result size ["+result.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return result;

	}
	
	public List<ConsSoggTab> getListaSoggettiG(RicercaSoggettoCatDTO rs) {

		List<ConsSoggTab> result = new ArrayList<ConsSoggTab>();
		String paramDenom = rs.getDenom().trim().toUpperCase();

	
		logger.debug("getListaSoggettiG CATASTO [Denominazione: "+paramDenom+"]");
		
		try {
			Query q = manager_diogene.createNamedQuery("ConsSoggTab.getListaSoggettiByDenominazione");

			q.setParameter("denominazione", paramDenom);
			q.setMaxResults(SUGGESTION_MAX_RESULT);
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return result;

	}
	
	public List<ConsSoggTab> getSoggettoByCF(RicercaSoggettoCatDTO rs) {

		List<ConsSoggTab> result = new ArrayList<ConsSoggTab>();
		String paramCF = rs.getCodFis().trim().toUpperCase();

		logger.debug("getSoggettoByCF [CF: "+paramCF+"]");
		
		try {
			Query q = manager_diogene.createNamedQuery("ConsSoggTab.getSoggettoByCF");

			q.setParameter("codfisc", paramCF);
			result = q.getResultList();
			q.setMaxResults(this.SUGGESTION_MAX_RESULT);
			
			logger.debug("Result size ["+result.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return result;

	}//-------------------------------------------------------------------------
	
	public List<ConsSoggTab> getSoggettiByPIVA(RicercaSoggettoCatDTO rs) {

		List<ConsSoggTab> result = new ArrayList<ConsSoggTab>();
		ConsSoggTab soggetto = null;
		String paramPIVA = rs.getPartIva().trim().toUpperCase();

		logger.debug("getSoggettoByPIVA CATASTO[pIVA"+paramPIVA+"]");

		try {
			Query q = manager_diogene.createNamedQuery("ConsSoggTab.getSoggettoByPIVA");
			if (rs.getLimit() != null && rs.getLimit()>0)
					q.setFirstResult(0).setMaxResults(rs.getLimit().intValue());
			q.setParameter("piva", paramPIVA);
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return result;

	}//-------------------------------------------------------------------------
	
	public ConsSoggTab getSoggettoByPIVA(RicercaSoggettoCatDTO rs) {

		List<ConsSoggTab> result = new ArrayList<ConsSoggTab>();
		ConsSoggTab soggetto = null;
		String paramPIVA = rs.getPartIva().trim().toUpperCase();

		logger.debug("getSoggettoByPIVA CATASTO[pIVA"+paramPIVA+"]");

		try {
			Query q = manager_diogene.createNamedQuery("ConsSoggTab.getSoggettoByPIVA");

			q.setParameter("piva", paramPIVA);
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");

			if (result.size() > 0)
				soggetto = result.get(0);

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return soggetto;

	}//-------------------------------------------------------------------------
	
	@Override
	public Long getCatastoRecordCount(RicercaOggettoCatDTO ro) {

		Long ol = 0L;

		try {

			String sql = (new CatastoQueryBuilder(ro.getCriteria())).createQuery(true);
			
			logger.debug("COUNT LISTA IMMOBILI CATASTO - SQL["+sql+"]");
			
			if (sql != null) {
				
				Query q = manager_diogene.createNativeQuery(sql);
				Object o = q.getSingleResult();
				ol = new Long(((BigDecimal) o).longValue());
				
				logger.debug("COUNT LISTA IMMOBILI CATASTO ["+ol+"]");
				
			}
				
		} catch (NoResultException nre) {
			
			logger.warn("Result size [0] " + nre.getMessage());

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}

		return ol;
	}
	
	public List<Siticomu> getListaSiticomu(String descr) {

		List<Siticomu> result = new ArrayList<Siticomu>();
	
		try {
			logger.debug("LISTA COMUNI[descrizione: "+descr+"]");
			Query q = manager_diogene.createNamedQuery("Siticomu.getListaSitiComuLikeDescr");
			q.setParameter("descr", descr.trim().toUpperCase());
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		return result;

	}
	
	@Override
	public List<Sitiuiu> getListaImmobiliByFPS_PostDtRif(RicercaOggettoCatDTO roc){
		List<Sitiuiu> result = new ArrayList<Sitiuiu>();
		Query q = null;
		
		try {
			logger.debug("LISTA SITIUIU POST DATA foglio["+roc.getFoglio()+"];" +
					"particella["+roc.getParticella()+"];" +
					"unimm["+roc.getUnimm()+"]" +
					"data["+roc.getDtVal()+"]");
			
			BigDecimal foglio = new BigDecimal(StringUtils.cleanLeftPad(roc.getFoglio(),'0'));
			BigDecimal unimm = null;
			if(roc.getUnimm()!= null && roc.getUnimm().trim().length()>0)
				unimm = new BigDecimal(StringUtils.cleanLeftPad(roc.getUnimm(),'0'));
			else
				unimm = new BigDecimal(0);
			
			q = manager_diogene.createNamedQuery("Sitiuiu.getListaSitiuiuByFPS_PostDtRif");
				
			q.setParameter("foglio", foglio );
			q.setParameter("particella", roc.getParticella());
			q.setParameter("unimm", unimm);
			q.setParameter("dtRif", roc.getDtVal());
			result = q.getResultList();
		
			logger.debug("Result size ["+result.size()+"]");
			
		} catch(NumberFormatException nfe){
		} catch(Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
			return result;
	}
	
	
	@Override
	public List<Sitiuiu> getListaImmobiliByFPS(RicercaOggettoCatDTO csc){
		List<Sitiuiu> result = new ArrayList<Sitiuiu>();
		Query q = null;
		Date dtVal = csc.getDtVal();
		try {
			logger.debug("LISTA SITIUIU foglio["+csc.getFoglio()+"];" +
					"particella["+csc.getParticella()+"];" +
					"unimm["+csc.getUnimm()+"]DtVal["+dtVal+"]");
			
			BigDecimal foglio = new BigDecimal(StringUtils.cleanLeftPad(csc.getFoglio(),'0'));
			BigDecimal unimm = null;
			if(csc.getUnimm()!= null && csc.getUnimm().trim().length()>0)
				unimm = new BigDecimal(StringUtils.cleanLeftPad(csc.getUnimm(),'0'));
			else
				unimm = new BigDecimal(0);
			
			if(dtVal==null)
				q = manager_diogene.createNamedQuery("Sitiuiu.getListaSitiuiuByFPS");
			else{
				q = manager_diogene.createNamedQuery("Sitiuiu.getListaSitiuiuByFPSAllaData");
				q.setParameter("dtRif", dtVal);
			}
			
			q.setParameter("foglio", foglio );
			q.setParameter("particella", csc.getParticella());
			q.setParameter("unimm", unimm);
			if (csc.getLimit() != null && csc.getLimit()>0)
				q.setFirstResult(0).setMaxResults(csc.getLimit().intValue());
			result = q.getResultList();
		
			logger.debug("Result size ["+result.size()+"]");
			
		} catch(NumberFormatException nfe){
		} catch(Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
			return result;
	}
	
	@Override
	public List<Sitiuiu> getListaImmobiliByFP(RicercaOggettoCatDTO ro){
		List<Sitiuiu> result = new ArrayList<Sitiuiu>();
		Query q = null;
		Date dtVal = ro.getDtVal();
		try {
			logger.debug("LISTA SITIUIU foglio["+ro.getFoglio()+"];" +
					"particella["+ro.getParticella()+"]DtVal["+dtVal+"]");
			
			BigDecimal foglio = new BigDecimal(StringUtils.cleanLeftPad(ro.getFoglio(),'0'));
			if(dtVal==null)
				q = manager_diogene.createNamedQuery("Sitiuiu.getListaSitiuiuByFP");
			else{
				q = manager_diogene.createNamedQuery("Sitiuiu.getListaSitiuiuByFPAllaData");
				q.setParameter("dtRif", dtVal);
			}
			
			q.setParameter("foglio", foglio );
			q.setParameter("particella", ro.getParticella());
			if (ro.getLimit() != null && ro.getLimit()>0)
				q.setMaxResults(ro.getLimit().intValue());
			result = q.getResultList();
		
			logger.debug("Result size ["+result.size()+"]");
			
		} catch(NumberFormatException nfe){
		} catch(Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
			return result;
	}
	

	public List<SintesiImmobileDTO> getListaImmobili(RicercaOggettoCatDTO ro) {

		ArrayList<SintesiImmobileDTO> listImm = new ArrayList<SintesiImmobileDTO>();

		try {

			String sql = (new CatastoQueryBuilder(ro.getCriteria())).createQuery(false);
			
			logger.debug("LISTA IMMOBILI CATASTO SQL["+sql+"]");

			if (sql != null) {
				Query q = manager_diogene.createNativeQuery(sql);
				
				if(ro.getStartm()!=null)
					q.setFirstResult(ro.getStartm().intValue());
				if(ro.getNumberRecord()!=null)
					q.setMaxResults(ro.getNumberRecord().intValue());

				List<Object[]> result = q.getResultList();
				logger.debug("Result size ["+result.size()+"]");

				for (Object[] rs : result) {
					Object val = null;
					int index = -1;
					SintesiImmobileDTO imm = new SintesiImmobileDTO();
					
					val = rs[++index];
					String pkIdUiu = val==null ? null : val.toString().trim();
					val = rs[++index];
					String comune = val==null ? null : val.toString().trim();
					val = rs[++index];
					String foglio = val.toString().trim();
					val = rs[++index];
					String particella = val.toString();
					
					while(particella.charAt(0) == '0' && particella.length()>1) 
						particella = particella.substring(1);
					
					val = rs[++index];
					String sub = val ==null ? null : val.toString().trim();
					val = rs[++index];
					String unimm =  val ==null ? null :  val.toString().trim();
					val = rs[++index];
					Date dataInizioVal =  val ==null ? null : (Date)val;
					val = rs[++index];
					Date dataFineVal =  val ==null ? null : (Date) val;
					val = rs[++index];
					String codCategoria =  val ==null ? null : val.toString().trim();
					

					imm.setIdImmobile(pkIdUiu);
					imm.setComune(comune);
					imm.setFoglio(foglio);
					imm.setParticella(particella);
					imm.setUnimm(unimm);
					imm.setDataInizioVal(dataInizioVal);
					imm.setDataFineVal(dataFineVal);
					imm.setCodCategoria(codCategoria);

					RicercaOggettoCatDTO roCat = new RicercaOggettoCatDTO();
					roCat.setEnteId(comune);
					roCat.setCodEnte(comune);
					roCat.setIdUiu(pkIdUiu);
					roCat.setFoglio(foglio);
					roCat.setParticella(particella);
					roCat.setUnimm(unimm);
					roCat.setDtVal(dataInizioVal);
					
					// Ricerco tutti gli indirizzi associati all'immobile
					List<IndirizzoDTO> indirizzi = this.getListaIndirizziImmobileByID(roCat);
					imm.setIndirizzi(indirizzi);

					// Ricerco tutti i soggetti associati all'immobile
					List<SoggettoDTO> soggetti = this.getListaSoggImmobileByID(roCat);
					imm.setSoggetti(soggetti);

					//Imposta la varifica da comma 340
					Boolean nonANorma = this.isImmobileNonANorma(roCat);
					imm.setNonANorma(nonANorma);
					
					
					// TODO: Estrae le coordinate per le mappe

					/*
					 * GenericTuples.T2<String,String> coord = null; try { coord
					 * = getLatitudeLongitude(imm.getFoglio(),
					 * imm.getParticella(), imm.getComune()); } catch (Exception
					 * e) { } if (coord!=null) {
					 * imm.setLatitudine(coord.firstObj);
					 * imm.setLongitudine(coord.secondObj); }
					 */

					listImm.add(imm);
				}	
			}
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}

		return listImm;
	}
	
	@Override
	public List<ParticellaKeyDTO> getListaParticelle(RicercaOggettoCatDTO ro) {
		ArrayList<ParticellaKeyDTO> listaParticelle = new ArrayList<ParticellaKeyDTO>();

		try {
			String sql = (new CatastoQueryBuilder(ro.getCriteria())).createQueryParticelle(false);
			logger.debug("CatastoJPAImpl-getListaParticelle(). SQL["+sql+"]");

			if (sql != null) {
				Query q = manager_diogene.createNativeQuery(sql);
				if (ro.getNumberRecord()!=null && ro.getStartm()!=null) {
					int startm = ro.getStartm().intValue();
					int numberRecord = ro.getNumberRecord().intValue();
					if(numberRecord!=0){
						q.setFirstResult(startm);
						q.setMaxResults(numberRecord);
					}
				}
				List<Object[]> result = q.getResultList();
				for (Object[] ele:result){
					ParticellaKeyDTO partKey = new ParticellaKeyDTO();
					if (ele[0]==null)	
						partKey.setIdSezc("-");
					else	
						partKey.setIdSezc((String)ele[0]);
					
					partKey.setFoglio(""+ ele[1]==null ? null : ele[1].toString());
					partKey.setParticella( ele[2]==null ? null :  ele[2].toString());
					listaParticelle.add(partKey);
				}
				logger.debug("Result size ["+result.size()+"]");
			}		
		}catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaParticelle;
			
	}
	public List<IndirizzoDTO> getListaIndirizziImmobileByID(RicercaOggettoCatDTO ro) {

		List<Object[]> result = new ArrayList<Object[]>();
		List<IndirizzoDTO> indirizzi = new ArrayList<IndirizzoDTO>();

		try {
			//String sql = (new CatastoQueryBuilder()).getSQL_LISTA_INDIRIZZI_BY_UNIMM();
			String codNazionale = ro.getCodEnte();
			String idUiu = ro.getIdUiu();
			
			logger.debug("RICERCA INDIRIZZI UIU [pkIdUiu: " + idUiu + "];" +
											   "[codNazionale: " + codNazionale + "]");
/*			logger.debug("SQL["+sql+"]");
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter(1, idUiu);
			q.setParameter(2, codNazionale);
			q.setParameter(3, codNazionale);*/
			
			Query q = manager_diogene.createNamedQuery("Sitidstr_SitiCivi.getListaIndirizziByIdUiu");
			q.setParameter("codNazionale", codNazionale);
			q.setParameter("pkIdUiu", idUiu);
			
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");

			for (Object[] rs : result) {

				IndirizzoDTO indirizzo = new IndirizzoDTO();
				indirizzo.setStrada(rs[0]==null ? null : rs[0].toString().trim());
				indirizzo.setNumCivico(rs[1]==null ? null : rs[1].toString().trim());
				indirizzi.add(indirizzo);
			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return indirizzi;

	}
	
	
	@Override
	public List<SoggettoCatastoDTO> getListaSoggTerrenoByFPS(RicercaOggettoCatDTO ro){
		List<SoggettoCatastoDTO> soggetti = new ArrayList<SoggettoCatastoDTO>();
		
		try{
			
			logger.debug("Ricerca Titolarita a Catasto...");
			
			String codEnte = ro.getEnteId();
			String foglio = ro.getFoglio();
			String particella=ro.getParticella();
			String sub = (ro.getUnimm()!=null && ro.getUnimm().length()>0 ? StringUtils.cleanLeftPad(ro.getUnimm(), '0') : " ");
			
			
			logger.debug("Parametro Cod.Nazionale:" + codEnte);
			logger.debug("Parametro Foglio:" + foglio);
			logger.debug("Parametro Particella:" + particella);
			logger.debug("Parametro Subalterno:" + sub);
			
			String sql = 
					"SELECT DISTINCT " +
					"cu.tipo_Titolo codTitolo, deco.description titolo, cu.perc_Poss perc, cu.data_Inizio dtInizio, cu.data_Fine dtFine , s.* " +
					"FROM Cons_Sogg_Tab s, Cons_Deco_Tab deco, Cons_Ufre_Tab cu " +
					"WHERE deco.tablename = 'CONS_ATTI_TAB' AND deco.fieldname = 'TIPO_DOCUMENTO' AND deco.code = cu.tipo_Documento " +
					"AND cu.cod_Nazionale = '"+codEnte+"' AND cu.foglio = TO_NUMBER("+foglio+") " +
					"AND cu.particella = LPAD('"+particella+"',5,'0') AND NVL(cu.sub,' ') = '"+sub+"' " +
					"AND (s.data_Fine IS NULL OR s.data_Fine >= sysdate) AND s.pk_Cuaa = cu.pk_Cuaa " +
					"UNION ALL " +
					"SELECT DISTINCT " +
					"cc.tipo_Titolo codTitolo, deco.description titolo, cc.perc_Poss perc, cc.data_Inizio dtInizio, cc.data_Fine dtFine , s.* " +
					"FROM Cons_Sogg_Tab s, Cons_Deco_Tab deco, Cons_Cons_Tab cc " +
					"WHERE deco.tablename = 'CONS_ATTI_TAB' AND deco.fieldname = 'TIPO_DOCUMENTO' AND deco.code = cc.tipo_Documento " +
					"AND cc.cod_Nazionale = '"+codEnte+"' AND cc.foglio = TO_NUMBER("+foglio+") " +
					"AND cc.particella = LPAD('"+particella+"',5,'0') AND NVL(cc.sub,' ') =  '"+sub+"' " +
					"AND (s.data_Fine IS NULL OR s.data_Fine >= sysdate)  AND s.pk_Cuaa = cc.pk_Cuaa " +
					"ORDER BY dtFine DESC ";
			
			logger.debug("SQL["+sql+"]");
			
			Query q = manager_diogene.createNativeQuery(sql);
			List<Object[]> result = q.getResultList();
			logger.debug("Result:"+ result.size());
			
			for(Object[] res: result){
				SoggettoCatastoDTO soggetto = new SoggettoCatastoDTO();
				
				soggetto.setTipoTitolo((String)res[0]);
				soggetto.setTitolo((String)res[1]);
				soggetto.setPercPoss((BigDecimal)res[2]);
				soggetto.setDataInizioPossesso((Date)res[3]);
				soggetto.setDataFinePossesso((Date)res[4]);
				
				ConsSoggTab cs = new ConsSoggTab();
				cs.setPkid((BigDecimal)res[5]);
				cs.setCodiFisc((String)res[6]);
				cs.setCodiPiva((String)res[7]);
				cs.setRagiSoci((String)res[8]);
				cs.setNome((String)res[9]);
				cs.setSesso((String)res[10]);
				cs.setDataNasc((Date)res[11]);
				cs.setDataMorte((Date)res[12]);
				cs.setComuNasc((String)res[13]);
				cs.setResiComu((String)res[14]);
				cs.setNaturaGiuridica((BigDecimal)res[15]);
				cs.setFlagPersFisica((String)res[16]);
				cs.setPkCuaa((BigDecimal)res[17]);
				
				soggetto.setConsSoggTab(cs);
				
				soggetti.add(soggetto);
			}
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return soggetti;
	}
	
	
	@Override
	public List<Date[]> getIntervalliProprietaUiuByFPS(RicercaOggettoCatDTO ro){
		
		List<Date[]> intervalli = new ArrayList<Date[]>();
		try{
			
			logger.debug("Ricerca getIntervalliProprietaUiuByFPS a Catasto...");
			
			logger.debug("Parametro Cod.Nazionale:" + ro.getEnteId());
			logger.debug("Parametro Foglio:" + ro.getFoglio());
			logger.debug("Parametro Particella:" + ro.getParticella());
			logger.debug("Parametro Subalterno:" + ro.getUnimm());
			
			
			Query q = manager_diogene.createNamedQuery("ConsSoggTab.getIntervalliProprietaUiuByFPS");
			q.setParameter("codEnte", ro.getEnteId());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ro.getFoglio(),'0'));
			q.setParameter("particella", ro.getParticella());
			q.setParameter("unimm", StringUtils.cleanLeftPad(ro.getUnimm(),'0'));
			List<Object[]> result = q.getResultList();
			for(Object[] obj : result){
				String dtIni = obj[0].toString();
				String dtFin = obj[1].toString();
				
				Date[] intervallo = { sdf_bar.parse(dtIni) , sdf_bar.parse(dtFin)};
				intervalli.add(intervallo);
			}
			logger.debug("Result:"+ intervalli.size());
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return intervalli;
	}
	
	@Override
	public List<Date[]> getIntervalliProprietaTerrByFPS(RicercaOggettoCatDTO ro){
		
		List<Date[]> intervalli = new ArrayList<Date[]>();
		try{
			
			logger.debug("Ricerca getIntervalliProprietaTerrByFPS a Catasto...");
			
			logger.debug("Parametro Cod.Nazionale:" + ro.getEnteId());
			logger.debug("Parametro Foglio:" + ro.getFoglio());
			logger.debug("Parametro Particella:" + ro.getParticella());
			logger.debug("Parametro Subalterno:" + ro.getUnimm());
			
			
			Query q = manager_diogene.createNamedQuery("ConsSoggTab.getIntervalliProprietaTerrByFPS");
			q.setParameter("codEnte", ro.getEnteId());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ro.getFoglio(),'0'));
			q.setParameter("particella", ro.getParticella());
			q.setParameter("unimm", StringUtils.cleanLeftPad(ro.getUnimm(),'0'));
			List<Object[]> result = q.getResultList();
			for(Object[] obj : result){
				
				String dtIni = obj[0].toString();
				String dtFin = obj[1].toString();
				
				Date[] intervallo = { sdf_bar.parse(dtIni) , sdf_bar.parse(dtFin)};
				intervalli.add(intervallo);
			}
			
			logger.debug("Result:"+ intervalli.size());
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return intervalli;
	}
	
	@Override
	public List<SoggettoCatastoDTO> getListaSoggImmobileByFPS(RicercaOggettoCatDTO ro){
		List<SoggettoCatastoDTO> soggetti = new ArrayList<SoggettoCatastoDTO>();
		
		try{
			
			logger.debug("Ricerca Titolarita a Catasto...");
			
			logger.debug("Parametro Cod.Nazionale:" + ro.getEnteId());
			logger.debug("Parametro Foglio:" + ro.getFoglio());
			logger.debug("Parametro Particella:" + ro.getParticella());
			logger.debug("Parametro Subalterno:" + ro.getUnimm());
			
			
			Query q = manager_diogene.createNamedQuery("ConsSoggTab.getTitolariUiuByFPS");
			q.setParameter("codEnte", ro.getEnteId());
			q.setParameter("foglio", StringUtils.cleanLeftPad(ro.getFoglio(),'0'));
			q.setParameter("particella", ro.getParticella());
			q.setParameter("unimm", StringUtils.cleanLeftPad(ro.getUnimm(),'0'));
			if (ro.getLimit() != null && ro.getLimit()>0){
				q.setFirstResult(0);
				q.setMaxResults(ro.getLimit());
			}
			List<Object[]> result = q.getResultList();
			logger.debug("Result:"+ result.size());
			
			for(Object[] res: result){
				SoggettoCatastoDTO soggetto = new SoggettoCatastoDTO();
				
				soggetto.setTipoTitolo((String)res[0]);
				soggetto.setTitolo((String)res[1]);
				soggetto.setPercPoss((BigDecimal)res[2]);
				soggetto.setDataInizioPossesso((Date)res[3]);
				soggetto.setDataFinePossesso((Date)res[4]);
				soggetto.setConsSoggTab((ConsSoggTab)res[5]);
				soggetti.add(soggetto);
			}
			
		}catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return soggetti;
	}
	
	@Override
	public List<SoggettoDTO> getListaSoggImmobileByID(RicercaOggettoCatDTO ro) {

		List<Object[]> listSogg = new ArrayList<Object[]>();
		List<SoggettoDTO> soggetti = new ArrayList<SoggettoDTO>();

		try {

			String sql = (new CatastoQueryBuilder()).getSQL_LISTA_SOGGETTI_BY_UNIMM();
			String codNazionale = ro.getCodEnte();
			String idUiu = ro.getIdUiu();
			
			logger.debug("RICERCA SOGGETTI UIU [pkIdUiu: " + idUiu + "]");
			logger.debug("SQL [" + sql + "]");
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter(1, idUiu);
			q.setParameter(2, codNazionale);
			q.setParameter(3, codNazionale);
			
			listSogg = q.getResultList();
			
			logger.debug("Result size ["+listSogg.size()+"]");

			for (Object[] rsSogg : listSogg) {
				int index = -1;
				Object val = null;
				val = rsSogg[++index];
				String tipo_sogg = val ==null ? null : val.toString().trim();
				val = rsSogg[++index];
				String titolo_sogg = val ==null ? null : val.toString().trim();
				val = rsSogg[++index];
				String cognome_ragi_soci = val ==null ? null : val.toString().trim();
				val = rsSogg[++index];
				String nome = val ==null ? null : val.toString().trim();
				val = rsSogg[++index];
				String cf = val ==null ? null : val.toString().trim();
				val = rsSogg[++index];
				String piva = val ==null ? null : val.toString().trim();
				Date dataInizioPoss = (Date) rsSogg[++index];
				Date dataFinePoss = (Date) rsSogg[++index];
				BigDecimal quota = (BigDecimal)rsSogg[++index];
				Date dataFineValUiu = (Date) rsSogg[++index];
				String tipoDocumento = (String)rsSogg[++index];

				SoggettoDTO soggetto = new SoggettoDTO();
				soggetto.setTipoDocumento(tipoDocumento);
				soggetto.setTipo(tipo_sogg);
				soggetto.setTitolo(titolo_sogg);
				
				soggetto.setCognomeSoggetto("-");
				soggetto.setDenominazioneSoggetto("-");

				
				if ("P".equalsIgnoreCase(tipo_sogg)|| (cf != null && !"-".equals(cf) )) 
					soggetto.setCognomeSoggetto(cognome_ragi_soci);
				else
					soggetto.setDenominazioneSoggetto(cognome_ragi_soci);

				soggetto.setNomeSoggetto(nome);
				soggetto.setCfSoggetto(cf);
				soggetto.setPivaSoggetto(piva);

				soggetto.setDataInizioPoss(dataInizioPoss);
				soggetto.setDataFinePoss(dataFinePoss);
				soggetto.setUltimoValido(dataFinePoss.compareTo(dataFineValUiu) >= 0);
				soggetto.setQuota(quota);
				soggetti.add(soggetto);
			}

		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		return soggetti;

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DettaglioCatastaleImmobileDTO getDettaglioImmobile(RicercaOggettoCatDTO ro) {

		DettaglioCatastaleImmobileDTO immobile = new DettaglioCatastaleImmobileDTO();
		String codNazionale = ro.getCodEnte();
		String idUiu = ro.getIdUiu();

		try {

			// Esecuzione Query per estrazione dati catastali immobile
			logger.debug("RICERCA IMMOBILE [" +
					"CodNazionale: "+ codNazionale +", " +
					"pkIdUiu: "+idUiu+"]");
			
			Query q0 = manager_diogene.createNamedQuery("Sitiuiu.getDettaglioImmobileByID");
			q0.setParameter("codNazionaleComu", codNazionale);
			q0.setParameter("idUiu", idUiu);
			Sitiuiu uiu = (Sitiuiu) q0.getSingleResult();
			immobile.setImmobile(uiu);
			
			// Estrazione sezione
			ro.setCodEnte(uiu.getCodNazionaleUrba()); //gestisco il multisezione
			Siticomu comu = this.getSitiComu(ro);
			if(comu!=null){
				immobile.setSezione(comu.getIdSezc());
			ro.setCodEnte(codNazionale); //ripristino il cod. belfiore

			// Estrazione categoria
			RicercaOggettoCatDTO roCodCat = new RicercaOggettoCatDTO();
			roCodCat.setCodCategoria(uiu.getCategoria());
			Sitideco deco = this.getSitideco(roCodCat);
			if(deco!=null)
				immobile.setDescrizioneCategoria(deco.getDescription());

			// Aggiunge la lista di indirizzi SIT al DTO
			List<IndirizzoDTO> localizzazioneSIT = this.getListaIndirizziImmobileByID(ro);
			immobile.setLocalizzazioneSIT(localizzazioneSIT);

			// Aggiunge la lista dei soggetti associati all'immobile
			List<SoggettoDTO> soggetti = this.getListaSoggImmobileByID(ro);
			immobile.setSoggetti(soggetti);
			
			String foglio = Long.toString(uiu.getId().getFoglio());
			String particella = uiu.getId().getParticella();
			String unimm = Long.toString(uiu.getId().getUnimm());
			
			// Estrae la lista delle occorrenze dello stesso immobile con
			// date di validità diverse			
			RicercaOggettoCatDTO roDtVar = new RicercaOggettoCatDTO();
			roDtVar.setFoglio(foglio);
			roDtVar.setParticella(particella);
			roDtVar.setUnimm(unimm);
			roDtVar.setIdUiu(idUiu);
			List<DataVariazioneImmobileDTO> altreDateValidita = this.getDateVariazioniImmobile(roDtVar);
			immobile.setAltreDateValidita(altreDateValidita);
			
			//Estrae le informazioni associabili tramite parametri catastali
			
			
			RicercaOggettoCatDTO roCat = new RicercaOggettoCatDTO(codNazionale, foglio, particella, unimm, null, idUiu);
			RicercaOggettoCatDTO roSitReptarsu = new RicercaOggettoCatDTO(codNazionale, foglio, particella, unimm, uiu.getDataInizioVal(), idUiu);
			
			if (
					(foglio != null && !foglio.equals(""))&& 
					(particella != null && !particella.equals("")) && 
					(unimm != null && !unimm.equals(""))
				)

			{
				
				//Verifica la regolarità dell'immobile dal record TARSU
				Boolean nonANorma = this.isImmobileNonANorma(roSitReptarsu);
				immobile.setNonANorma(nonANorma);
				
				//Aggiunge il report completo TARSU
				List<SitRepTarsu> repTarsu = this.getReportTarsuList(roSitReptarsu);
				immobile.setRepTarsu(repTarsu);
				
				
				// Estrazione Dettaglio Vani Comma 340
				List<SitiediVani> vani = this.getDettaglioVaniC340(roCat);
				immobile.setDettaglioVaniComma340(vani);

				// Estrazione planimetrie comma 340
				List<PlanimetriaComma340DTO> planimetrieC340;
				planimetrieC340 = this.getPlanimetrieComma340(roCat);
				immobile.setPlanimetrieComma340(planimetrieC340);
				
				//Estrazione Localizzazione CAT
				RicercaOggettoCatDTO roLoc = new RicercaOggettoCatDTO(codNazionale, foglio, particella, unimm, null);
				List<IndirizzoDTO> localizzazioneCatasto = this.getLocalizzazioneCatastale(roLoc);
				immobile.setLocalizzazioneCatasto(localizzazioneCatasto);
			}
			}
			
		} catch (NoResultException nre) {
			logger.warn("Result size [0] " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}

		return immobile;
	}
	
	@Override
	public Siticomu getSitiComu(RicercaOggettoCatDTO ro) {

		Siticomu comu = null;
		String codNazionale = ro.getCodEnte();
		
		try {
			
			logger.debug("RICERCA SITICOMU [codNazionale: "+codNazionale+"]");
			
			Query qs = manager_diogene.createNamedQuery("Siticomu.getSitiComuByCodNazionale");
			qs.setParameter("cod_nazionale_1", codNazionale);
			qs.setParameter("cod_nazionale_2", codNazionale);
			//comu = (Siticomu) qs.getSingleResult();
			
			List<Siticomu> result = qs.getResultList();
			if (result!=null && result.size()>0)
				comu =(Siticomu)(result.get(0));
			
		} catch (NoResultException nre) {
			logger.warn("Result size [0] " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return comu;
	}
	@Override
	public List<Siticomu> getSiticomuSezioni(RicercaOggettoCatDTO ro) {
		List<Siticomu> lista = null;
		String codNazionale = ro.getCodEnte();
		try {
			logger.debug("RICERCA SITICOMU SEZIONI [codNazionale: "+codNazionale+"]");
			Query qs = manager_diogene.createNamedQuery("Siticomu.getSitiComuByCodiFiscLuna");
			qs.setParameter("codNazionale", codNazionale);
			lista =(List<Siticomu>)(qs.getResultList());
		} catch (NoResultException nre) {
			logger.warn("Result size [0] " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		return lista;
	}
	
	public Sitideco getSitideco(RicercaOggettoCatDTO ro) {

		Sitideco deco = null;
		String codCategoria = ro.getCodCategoria();
		
		try {
			
			logger.debug("RICERCA DESC CATEGORIA[CodCategoria: "+codCategoria+"]");
			Query qc = manager_diogene.createNamedQuery("Sitideco.getCategoriaSitiuiu");
			qc.setParameter("categoriaSitiuiu", codCategoria);
			deco = (Sitideco) qc.getSingleResult();
			
		} catch (NoResultException nre) {
			logger.warn("Result size [0] " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return deco;
	}
	
	public List<SitiediVani> getDettaglioVaniC340(RicercaOggettoCatDTO ro){
		
		List<SitiediVani> result = new ArrayList<SitiediVani>();
		String codNazionale = ro.getCodEnte();
		String foglio = ro.getFoglio();
		String particella = ro.getParticella();
		String unimm = ro.getUnimm();
		
		try{
			
			
			Date dtVal=null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (ro.getDtVal()==null) {
				try {
					dtVal=sdf.parse("31/12/9999");
				}catch(ParseException pe) {}
			}else
				dtVal =ro.getDtVal();
			logger.debug("RICERCA SUPERFICI C340 [" +
					"Foglio: "+foglio+", " +
					"Particella: "+particella+", " +
					"Subalterno: "+unimm+", " +
					"dtVal: "+dtVal.toString()+"]");
			Query q = manager_diogene.createNamedQuery("SitiediVani.getDettaglioVaniC340ByFPS");
			q.setParameter("codNazionale",codNazionale);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			q.setParameter("subalterno", unimm);
			q.setParameter("dtVal", dtVal);
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");
	
		}catch(Throwable t){
			logger.error("", t);
			throw new CatastoServiceException(t);
		}

		return result;
		
	}
	public List<SitiediVani> getDettaglioVaniC340AllaData(RicercaOggettoCatDTO ro){
		
		List<SitiediVani> result = new ArrayList<SitiediVani>();
		String codNazionale = ro.getCodEnte();
		String foglio = ro.getFoglio();
		String particella = ro.getParticella();
		String unimm = ro.getUnimm();
		
		try{
			Date dtVal=null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (ro.getDtVal()==null) {
				try {
					dtVal=sdf.parse("31/12/9999");
				}catch(ParseException pe) {}
			}else
				dtVal =ro.getDtVal();
			logger.debug("RICERCA SUPERFICI C340 [" +
					"Foglio: "+foglio+", " +
					"Particella: "+particella+", " +
					"Subalterno: "+unimm+", " +
					"dtVal: "+dtVal.toString()+"]");
			Query q = manager_diogene.createNamedQuery("SitiediVani.getDettaglioVaniC340ByFPSAllaData");
			q.setParameter("codNazionale",codNazionale);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			q.setParameter("subalterno", unimm);
			q.setParameter("dtVal", dtVal);
			result = q.getResultList();
			
			logger.debug("Result size ["+result.size()+"]");
	
		}catch(Throwable t){
			logger.error("", t);
			throw new CatastoServiceException(t);
		}

		return result;
		
	}
	public List<DataVariazioneImmobileDTO> getDateVariazioniImmobile(RicercaOggettoCatDTO ro) {

		List<DataVariazioneImmobileDTO> variazioni = new ArrayList<DataVariazioneImmobileDTO>();	
		
		try{
			
			Long foglio = new Long(ro.getFoglio());
			String particella = ro.getParticella();
			Long unimm = new Long(ro.getUnimm());
			BigDecimal pkIdUiu = new BigDecimal(ro.getIdUiu());
			
			logger.debug("RICERCA ALTRE DATE VALIDITA'UIU [" +
					"pkIdUiu: "+pkIdUiu+", " +
					"Foglio: "+foglio+", " +
					"Particella: "+particella+", " +
					"Subalterno: "+unimm+"]");
			
			Query q = manager_diogene.createNamedQuery("Sitiuiu.getImmobiliAltreDateValiditaByFPS_ID");
			int index = 0;
			q.setParameter("foglio",foglio );
			q.setParameter("particella",particella );
			q.setParameter("unimm", unimm);
			q.setParameter("idUiu", pkIdUiu);
			List<Sitiuiu> result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
	
			for (Sitiuiu res : result) {
	
				DataVariazioneImmobileDTO var = new DataVariazioneImmobileDTO();
				var.setIdUiu(res.getPkidUiu().toString());
				var.setDataInizioVal((Date) res.getDataInizioVal());
				var.setDataFineVal((Date) res.getId().getDataFineVal());
	
				variazioni.add(var);
			}
		
		}catch(Throwable t){
			logger.error("", t);
			throw new CatastoServiceException(t);
		}

		return variazioni;

	}

	public List<PlanimetriaComma340DTO> getPlanimetrieComma340(RicercaOggettoCatDTO ro){

		List<PlanimetriaComma340DTO> planimetrieComma340 = new ArrayList<PlanimetriaComma340DTO>();
		String codNazionale = ro.getCodEnte();
		String foglio = ro.getFoglio();
		String particella = ro.getParticella();
		String subalterno = ro.getUnimm();
		
		logger.debug("RICERCA PLANIMETRIE C340 [" +
				"Foglio: "+foglio+", " +
				"Particella: "+particella+", " +
				"Subalterno: "+subalterno+"]");

			try {
				
				List<Object[]> result = new ArrayList<Object[]>();
				
				
				Query q = manager_diogene.createNativeQuery((new CatastoQueryBuilder()).getSQL_PLANIMETRIE_C340());
				q.setParameter(1, foglio);
				q.setParameter(2, particella);
				q.setParameter(3, subalterno);
				q.setParameter(4, codNazionale);
				result = q.getResultList();
				
				logger.debug("Result size ["+result.size()+"]");

				for (Object[] rs : result){
					PlanimetriaComma340DTO planimetria = new PlanimetriaComma340DTO();
					planimetria.setPrefissoNomeFile((String) rs[0]);
					planimetria.setSubalterno((String) rs[1]);
					planimetrieComma340.add(planimetria);
				}

			} catch (Throwable t) {
				logger.error("", t);
				throw new CatastoServiceException(t);
			}
		
		return planimetrieComma340;
	}
	
	public List<PlanimetriaComma340DTO> getPlanimetrieComma340SezFglNum(RicercaOggettoCatDTO ro){

		List<PlanimetriaComma340DTO> planimetrieComma340 = new ArrayList<PlanimetriaComma340DTO>();
		String codNazionale = ro.getCodEnte();
		String sezione = ro.getSezione();
		String foglio = ro.getFoglio();
		String particella = ro.getParticella();
		
		logger.debug("RICERCA PLANIMETRIE C340 [" +
				"Sezione: "+sezione+", " +
				"Foglio: "+foglio+", " +
				"Particella: "+particella+"]");

			try {
				
				List<Object[]> result = new ArrayList<Object[]>();
				
				
				Query q = manager_diogene.createNativeQuery((new CatastoQueryBuilder()).getSQL_PLANIMETRIE_C340_SEZFGLNUM());
				q.setParameter(1, sezione);
				q.setParameter(2, foglio);
				q.setParameter(3, particella);				
				q.setParameter(4, codNazionale);
				result = q.getResultList();
				
				logger.debug("Result size ["+result.size()+"]");

				for (Object[] rs : result){
					PlanimetriaComma340DTO planimetria = new PlanimetriaComma340DTO();
					planimetria.setPrefissoNomeFile((String) rs[8]);
					planimetria.setSubalterno((String) rs[7]);
					planimetrieComma340.add(planimetria);
				}

			} catch (Throwable t) {
				logger.error("", t);
				throw new CatastoServiceException(t);
			}
		
		return planimetrieComma340;
	}
	
	protected Boolean isImmobileNonANorma(RicercaOggettoCatDTO ro){
		Boolean nonANorma = null;
		BigDecimal val = null;
		
		SitRepTarsu repTarsu = this.getReportTarsu(ro);
		
		if(repTarsu!=null)
    		val = repTarsu.getFC340();
		
		if(val!=null)
			nonANorma = val.equals(new BigDecimal("0"))? true : false;
		
		//nonANorma = ((val!=null && (val.compareTo(new BigDecimal("0")))==0) ? true : nonANorma);
			
		logger.debug("Immobile non a norma ["+nonANorma+"]");
	
		return nonANorma;
	}
	
	protected SitRepTarsu getReportTarsu(RicercaOggettoCatDTO ro){
		SitRepTarsu repTarsu = null;
		
		List<SitRepTarsu> result = this.getReportTarsuList(ro);
		
		if (result.size() != 0) {
			repTarsu = result.get(0);
		}
		
		return repTarsu;
	}
	
	public List<SitRepTarsu> getReportTarsuList(RicercaOggettoCatDTO ro){		
		List<SitRepTarsu> result = new ArrayList<SitRepTarsu>();
		//String idUiu = ro.getIdUiu();
		String foglio = ro.getFoglio();
		String particella = ro.getParticella();
		String unimm = ro.getUnimm();
		String dataInizioVal = sdf.format(ro.getDtVal());
		
		try{			
			logger.debug("ESTRAZIONE REPORT TARSU [Foglio:"+foglio+"]" +
					     "[Particella:"+particella+"]" +
					     "[Unimm:"+unimm+"]" +
					     "[DataInizioVal:"+ dataInizioVal +"]");
			
			Query q = manager_diogene.createNamedQuery("SitRepTarsu.getReportTarsuByFPSData");
			//q.setParameter("idUiu", idUiu);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			q.setParameter("unimm", unimm);
			q.setParameter("dtVal", dataInizioVal);
			result = q.getResultList();
			
			logger.debug("COMPLETATA ESTRAZIONE REPORT TARSU PER FPSData = " + foglio + "|" + particella+"|" + unimm + "|" + dataInizioVal + "; RECORD TROVATI: " + result.size());	
		}catch(NoResultException nre){
			logger.warn("Result size [0] " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		
		return result;
	}	

	public ArrayList<IndirizzoDTO> getLocalizzazioneCatastale(RicercaOggettoCatDTO ro) {

		ArrayList<IndirizzoDTO> localizzazioneCAT = new ArrayList<IndirizzoDTO>();
		String codEnte = ro.getCodEnte();
		String foglio = ro.getFoglio();
		String particella = ro.getParticella();
		String subalterno =  ro.getUnimm();
		logger.debug("RICERCA LISTA INDIRIZZI LOCALIZZAZIONE CATASTALE [" +
				"CodNazionale: "+codEnte+", " +
				"Foglio: "+foglio+"]"+", " +
				"Particella: "+particella+"]"+", " +
				"Subalterno: "+subalterno+"]");
		
		try{
			
			boolean ricercaUnimm = subalterno!=null && subalterno.trim().length()>0;
			
			String sql = (new CatastoQueryBuilder()).getSQL_LOCALIZZAZIONE_CATASTALE(false, ricercaUnimm);
			
			logger.debug("getLocalizzazioneCatastale - SQL["+sql+"]");
			
			if(sql!=null){
				Query q = manager_diogene.createNativeQuery(sql);
				if (ro.getLimit() != null && ro.getLimit()>0){
					q.setFirstResult(0);
					q.setMaxResults(ro.getLimit());
				}
				q.setParameter("codEnte", codEnte);
				q.setParameter("foglio", foglio);
				q.setParameter("particella", particella);
				
				if(ricercaUnimm)
					q.setParameter("unimm", subalterno);
	
				List<Object[]> result = q.getResultList();
				logger.debug("Result size ["+result.size()+"]");
				
				for (Object[] rs : result) {
		
					String strada = (String) rs[0];
					String numCivico = (String) rs[1];
					Integer idImmobile = ((BigDecimal) rs[2]).intValue();
					Integer progressivo = ((BigDecimal) rs[3]).intValue();
					Integer seq = ((BigDecimal) rs[4]).intValue();
					String sezione = (String) rs[5];
					
					IndirizzoDTO loc = new IndirizzoDTO();
					loc.setStrada(strada);
					loc.setNumCivico(numCivico);
					loc.setIdImmobile(idImmobile);
					loc.setProgressivo(progressivo);
					loc.setSeq(seq);
					loc.setSezione(sezione);
					
					localizzazioneCAT.add(loc);
				}
			}
		}catch(Throwable t){
			logger.error("", t);
			throw new CatastoServiceException(t);
		}

		return localizzazioneCAT;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<Sitiuiu> getListaUiAllaData(RicercaOggettoCatDTO ro) {
		String codEnte = ro.getCodEnte(); 
		Date dtVal = ro.getDtVal();
		String foglio = ro.getFoglio().trim();
		String particella = ro.getParticella().trim();
		List<Sitiuiu>  listaUiu = null;
		String sezione = ro.getSezione();
		if (sezione == null)
			sezione="";
		logger.debug("LISTA U.I. ALLA DATA [ENTE: "+codEnte+"]; "+ "[SEZIONE: "+sezione+"];" + "[FOGLIO: "+foglio+"]; " + "[PARTIC.: "+particella+"]; " + "[DATA_VAL: "+dtVal +"]; ");
		try {
			Query q = manager_diogene.createNamedQuery("Sitiuiu.getListaUiAllaData");
			q.setParameter("codEnte", codEnte);
			q.setParameter("sezione", sezione);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			if (dtVal ==null)
				dtVal =new Date();
			q.setParameter("dtVal", dtVal);
			listaUiu = (List<Sitiuiu>)q.getResultList();
    		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaUiu;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<IndirizzoDTO> getListaIndirizziPart(RicercaOggettoCatDTO ro) {
		List<Object[]> result = new ArrayList<Object[]>();
		List<IndirizzoDTO> indirizzi = new ArrayList<IndirizzoDTO>();

		try {
			String sql = (new CatastoQueryBuilder()).getSQL_LISTA_INDIRIZZI_BY_FP();
			String codNazionale = ro.getCodEnte();
			String idSez = ro.getSezione();
			if (idSez==null)
				idSez="";

			String foglio = ro.getFoglio().trim();
			String particella = ro.getParticella().trim();
			logger.debug("getListaIndirizziPart - SQL["+sql+"]");
			Query q = manager_diogene.createNativeQuery(sql);
			if (ro.getLimit() != null && ro.getLimit()>0){
				q.setFirstResult(0);
				q.setMaxResults(ro.getLimit());
			}
			q.setParameter(1, codNazionale);
			q.setParameter(2, idSez);
			q.setParameter(3, foglio);
			q.setParameter(4, particella);
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
			for (Object[] rs : result) {
				IndirizzoDTO indirizzo = new IndirizzoDTO();
				String prefisso = rs[0]==null?null:rs[0].toString().trim(); 
				String ind = rs[1]==null?null:rs[1].toString().trim(); 
				indirizzo.setStrada(prefisso + " " + ind);
				indirizzo.setNumCivico(rs[2].toString().trim());
				indirizzo.setIdCivico(rs[3]==null?null: rs[3].toString());
				indirizzi.add(indirizzo);
			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return indirizzi;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<IndirizzoDTO> getListaIndirizziPartAllaData(RicercaOggettoCatDTO ro) {
		List<Object[]> result = new ArrayList<Object[]>();
		List<IndirizzoDTO> indirizzi = new ArrayList<IndirizzoDTO>();

		try {
			String sql = (new CatastoQueryBuilder()).getSQL_LISTA_INDIRIZZI_BY_FPeDATA();
			String codNazionale = ro.getCodEnte();
			String idSez = ro.getSezione();
			if (idSez==null)
				idSez="";
			Date dtVal = ro.getDtVal();
			if (dtVal==null)
				dtVal = new Date();
			String foglio = ro.getFoglio().trim();
			String particella = ro.getParticella().trim();
			logger.debug("getListaIndirizziPartAllaData - SQL["+sql+"]");
			Query q = manager_diogene.createNativeQuery(sql);
			if (ro.getLimit() != null && ro.getLimit()>0){
				q.setFirstResult(0);
				q.setMaxResults(ro.getLimit());
			}
			q.setParameter(1, codNazionale);
			q.setParameter(2, idSez);
			q.setParameter(3, foglio);
			q.setParameter(4, particella);
			q.setParameter(5, dtVal);
			q.setParameter(6, dtVal);
			result = q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
			for (Object[] rs : result) {
				IndirizzoDTO indirizzo = new IndirizzoDTO();
				String prefisso = rs[0]==null?null:rs[0].toString().trim(); 
				String ind = rs[1]==null?null:rs[1].toString().trim(); 
				indirizzo.setStrada(prefisso + " " + ind);
				indirizzo.setNumCivico(rs[2].toString().trim());
				indirizzo.setIdCivico(rs[3]==null?null: rs[3].toString());
				indirizzi.add(indirizzo);
			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return indirizzi;
	}//-------------------------------------------------------------------------
	
	@Override
	public Sitiuiu getDatiUiAllaData(RicercaOggettoCatDTO ro) {
		String codEnte = ro.getCodEnte(); 
		Date dtVal = ro.getDtVal();
		String foglio = ro.getFoglio()!=null ? ro.getFoglio().trim() : "" ;
		String particella = ro.getParticella()!=null ? ro.getParticella().trim() : "";
		String unimm = ro.getUnimm()!=null ? ro.getUnimm().trim() : "";
		Sitiuiu uiu = null;
		List<Sitiuiu> listaUiu=new ArrayList<Sitiuiu>();
		String sezione = ro.getSezione();
		if (sezione == null)
			sezione="";
		logger.debug("DATI U.I. ALLA DATA [ENTE: "+codEnte+"]; "+ "[SEZIONE: "+sezione+"];" + "[FOGLIO: "+foglio+"]; " + "[PARTIC.: "+particella+"]; " + "[UNIMM: "+unimm+"]; "+ "[DATA_VAL: "+dtVal.toString()+"]; ");
		
		BigDecimal bdFoglio = null;
		BigDecimal bdUnimm = null;
		try{
			
			bdFoglio = new BigDecimal(foglio);
			bdUnimm = new BigDecimal(unimm);
			
		}catch(Exception e){
			logger.warn("DATI U.I. ALLA DATA - Impossibile ricercare U.I. in Sitiuiu il foglio o il subalterno non è un valore numerico.");
			return null;
		}
		
		
		try {
			Query q = manager_diogene.createNamedQuery("Sitiuiu.getDatiUiAllaData");
			q.setParameter("codEnte", codEnte);
			q.setParameter("sezione", sezione);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			q.setParameter("unimm", unimm);
			q.setParameter("dtVal", dtVal);
			//uiu = (Sitiuiu)q.getSingleResult();
			listaUiu =(List<Sitiuiu>)q.getResultList();
			logger.debug("Sitiuiu righe trovate" + listaUiu.size());
			if (listaUiu.size()==1)
				uiu=listaUiu.get(0);
			if (listaUiu.size() > 1) {
				for (Sitiuiu ele:listaUiu) {
					if (ele.getId().getSub()==null) {
						uiu=ele;
						break;
					}
					if (ele.getId().getSub().trim().equals("")) {
						uiu=ele;
						break;
					}
					uiu=ele;
				}
			}
				
    		
		}
		catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return uiu;

	}
	@Override
	public SiticonduzImmAll getDatiBySoggUiAllaData(RicercaOggettoCatDTO ro, RicercaSoggettoCatDTO rs) {
		
		SiticonduzImmAll imm = null;
		String codEnte = rs.getCodEnte();
		BigDecimal idSogg = rs.getIdSogg();
		String foglio = ro.getFoglio().trim();
		String particella = ro.getParticella().trim();
		String unimm = ro.getUnimm().trim();
		String sezione = ro.getSezione();
		if(sezione==null)
			sezione="";
		Date dtVal = rs.getDtVal();
		logger.debug("DATI TITOLARITA' IMMOBILE PER ID_SOGG E UI ALLA DATA " +
				"[CODENTE: "+codEnte+"];" + "[IDSOGG: "+idSogg+"]" + "[DATA_VAL: "+dtVal+"]" +
				"[sez/f/p/s: "+sezione+"/"+ foglio+"/" +particella + "/" +unimm + "]" );
		try {
			Query q = manager_diogene.createNamedQuery("SiticonduzImmAll.getDatiBySoggUiAllaData");
			q.setParameter("codEnte", codEnte);
			q.setParameter("idSogg", idSogg.longValue());
			q.setParameter("sezione", sezione);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			q.setParameter("unimm", unimm);
			q.setParameter("dtVal", dtVal);
			imm =(SiticonduzImmAll) q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return imm;
	}
	public List<SiticonduzImmAll> getDatiBySoggFabbricatoAllaData(RicercaOggettoCatDTO ro, RicercaSoggettoCatDTO rs) {
		
		List<SiticonduzImmAll> lista = new ArrayList<SiticonduzImmAll> ();
		String codEnte = rs.getCodEnte();
		BigDecimal idSogg = rs.getIdSogg();
		String foglio = ro.getFoglio().trim();
		String particella = ro.getParticella().trim();
	    String sezione = ro.getSezione();
		if(sezione==null)
			sezione="";
		Date dtVal = rs.getDtVal();
		if (dtVal==null)
			dtVal=new Date();	
		logger.debug("DATI TITOLARITA' IMMOBILE PER ID_SOGG E FABBRICATO ALLA DATA " +
				"[CODENTE: "+codEnte+"];" + "[IDSOGG: "+idSogg+"]" + "[DATA_VAL: "+dtVal+"]" +
				"[sez/f/p: "+sezione+"/"+ foglio+"/" +particella + "]" );
		try {
			Query q = manager_diogene.createNamedQuery("SiticonduzImmAll.getDatiBySoggFabbricatoAllaData");
			q.setParameter("codEnte", codEnte);
			q.setParameter("idSogg", idSogg.longValue());
			q.setParameter("sezione", sezione);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			q.setParameter("dtVal", dtVal);
			lista =(List<SiticonduzImmAll>) q.getResultList(); 
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return lista;
	}
	@Override
	public List<BigDecimal> getIdSoggByCF(RicercaSoggettoCatDTO rs) {
		List<BigDecimal>  listaIdSogg = null;
		String codFis = rs.getCodFis();
		Date dtVal = rs.getDtVal();
		codFis= codFis.trim().toUpperCase();
		logger.debug("SOGG_ID PER COD_FIS[CODFIS: "+codFis+"]");
		try {
			Query q =null;
			q = manager_diogene.createNamedQuery("AnagSoggetti.getIdByCF");
			q.setParameter("codFiscale", codFis);
			listaIdSogg = q.getResultList();
    		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaIdSogg;
	}

	public BigDecimal getIdSoggByCFAllaData(RicercaSoggettoCatDTO rs) {
		BigDecimal idSogg = null;
		String codFis = rs.getCodFis();
		Date dtVal = rs.getDtVal();
		codFis= codFis.trim().toUpperCase();
		logger.debug("SOGG_ID PER COD_FIS[CODFIS: "+codFis+"]; [DATA VAL: " + rs.getDtVal() + "]");
		try {
			Query q =null;
			q = manager_diogene.createNamedQuery("AnagSoggetti.getIdByCFAllaData");
			q.setParameter("codFiscale", codFis);
			q.setParameter("dtVal", dtVal);
			idSogg = (BigDecimal)q.getSingleResult();
    		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return idSogg;
	}
	@Override
	public List<BigDecimal> getIdSoggByDatiAnag(RicercaSoggettoCatDTO rs) { 
		List<BigDecimal> listaIdSogg = null;
	   logger.debug("SOGG_ID PER DATI ANAGRAFICI[DENOMINAZIONE: "+rs.getDenom()+"]; [DT.NASCITA: "+rs.getDtNas()+"]; [DATA VAL: " + rs.getDtVal() + "]");
		try {
			Query q = null;
			if (rs.getDtVal() !=null)
				q=manager_diogene.createNamedQuery("AnagSoggetti.getIdPFByDatiAnaAllaData");
			else
				q=manager_diogene.createNamedQuery("AnagSoggetti.getIdPFByDatiAna");
			q.setParameter("denom", rs.getDenom());
			q.setParameter("dtNas", rs.getDtNas());
			q.setParameter("flPF", "P");
			if (rs.getDtVal() !=null)
				q.setParameter("dtVal", rs.getDtVal());
			listaIdSogg = q.getResultList();
    		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaIdSogg;
	}

	public  List<BigDecimal> getIdSoggByPI(RicercaSoggettoCatDTO rs) {
		 List<BigDecimal> listaIdSogg = null;
		String partIva = rs.getPartIva();
		Date dtVal = rs.getDtVal();
		logger.debug("SOGG_ID PER PART_IVA_ALL_DATA[PART_IVA: "+partIva+"];[DATA: " + dtVal + "]" );
		try {
			Query q = null;
			q=manager_diogene.createNamedQuery("AnagSoggetti.getIdByPI");
			q.setParameter("partIva", partIva);
			listaIdSogg = q.getResultList();
   		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaIdSogg;
	}
	
	public BigDecimal getIdSoggByPIAllaData(RicercaSoggettoCatDTO rs) {
		BigDecimal idSogg = null;
		String partIva = rs.getPartIva();
		Date dtVal = rs.getDtVal();
		logger.debug("SOGG_ID PER PART_IVA_ALL_DATA[PART_IVA: "+partIva+"];[DATA: " + dtVal + "]" );
		try {
			Query q = null;
			q=manager_diogene.createNamedQuery("AnagSoggetti.getIdByPIAllaData");
			q.setParameter("partIva", partIva);
			q.setParameter("dtVal", dtVal);
			idSogg = (BigDecimal)q.getSingleResult();
   		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return idSogg;
	}
	public List<BigDecimal> getIdSoggPGByDatiAnag(RicercaSoggettoCatDTO rs) {
		//ricerca della persona giuridica per dati anagrafici
		List<BigDecimal> listaIdSogg = null;
		
		logger.debug("SOGG_ID PER DATI ANAGRAFICI[DENOMINAZIONE: "+rs.getDenom()+"];[DATA VAL: " + rs.getDtVal() + "]");
		try {
			Query q = null;
			if (rs.getDtVal() !=null)
				q=manager_diogene.createNamedQuery("AnagSoggetti.getIdPGByDatiAnaAllaData");
			else
				q=manager_diogene.createNamedQuery("AnagSoggetti.getIdPGByDatiAna");
			q.setParameter("denom", rs.getDenom());
			q.setParameter("flPG", "G");
			if (rs.getDtVal() !=null)
				q.setParameter("dtVal", rs.getDtVal());
			listaIdSogg = q.getResultList();
    		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaIdSogg;
	}
	@Override
	public ConsSoggTab getSoggettoBYID(RicercaSoggettoCatDTO rs) {
		ConsSoggTab soggetto = null;
		logger.debug("CatastoJPAImp-getSoggPG. pkid:" + rs.getPkid());

		try {
			Query q = manager_diogene.createNamedQuery("ConsSoggTab.getSoggettoByID");
			q.setParameter("pkid", rs.getPkid());
			soggetto = (ConsSoggTab)q.getSingleResult();
		} catch (NoResultException nre) {
			logger.debug("NON TROVATO.CatastoJPAImp-getSoggPG. pkid:" + rs.getPkid());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return soggetto;
	}
	@Override
	public List<ConsSoggTab> getSoggettoByPkCuaa(RicercaSoggettoCatDTO rs) {
		List<ConsSoggTab> lista= null;
		logger.debug("CatastoJPAImp-getSoggettoByPkCuaa. pkCuaa: " + rs.getIdSogg());
		try {
			Query q = manager_diogene.createNamedQuery("ConsSoggTab.getSoggettoByPkCuaa");
			q.setParameter("pkCuaa", rs.getIdSogg());
			lista = (List<ConsSoggTab>)q.getResultList();
			logger.debug("CatastoJPAImp-getSoggettoByPkCuaa. N.righe: " + lista.size());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return lista;
	}
	
	@Override
	public List<SiticonduzImmAll> getImmobiliByIdSogg(RicercaSoggettoCatDTO rs) {
		List<SiticonduzImmAll> listaImm = null;
		String codEnte = rs.getCodEnte();
		BigDecimal idSogg = rs.getIdSogg();
		Date dtRifDa = rs.getDtRifDa();
		Date dtRifA = rs.getDtRifA();
		if (dtRifDa ==null || dtRifA == null)
			logger.debug("IMMOBILI PER IDSOGG:[CODENTE: "+codEnte+"];" + "[IDSOGG: "+idSogg+"]" );
		else
			logger.debug("IMMOBILI PER IDSOGG NELL'ARCO DI TEMPO COMPRESO FRA LE DATE:[CODENTE: "+codEnte+"];" + "[IDSOGG: "+idSogg+"]" + "[DATA DA: "+ dtRifDa+"]" +  "[DATA A : "+dtRifA+"]" );
		try {
			Query q = null;
			if (dtRifDa ==null || dtRifA == null)
				q=manager_diogene.createNamedQuery("SiticonduzImmAll.getImmByIdSogg");
			else	
				q=manager_diogene.createNamedQuery("SiticonduzImmAll.getImmByIdSoggInRangeDate");
			q.setParameter("codEnte", codEnte);
			q.setParameter("idSogg", idSogg.longValue());
			if (dtRifDa != null && dtRifA != null) {
				q.setParameter("dtRifDa", dtRifDa);
				q.setParameter("dtRifA", dtRifA);
			}
			listaImm = q.getResultList();
       		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaImm;
	}
	
	@Override
	public List<SiticonduzImmAll> getImmobiliByIdSoggAllaData(RicercaSoggettoCatDTO rs) {
		List<SiticonduzImmAll> listaImm = null;
		String codEnte = rs.getCodEnte();
		BigDecimal idSogg = rs.getIdSogg();
		Date dtVal = rs.getDtVal();
		logger.debug("IMMOBILI PER IDSOGG ALLA DATA:[CODENTE: "+codEnte+"];" + "[IDSOGG: "+idSogg+"]" + "[DATA_VAL: "+dtVal+"]");
		try {
			Query q = manager_diogene.createNamedQuery("SiticonduzImmAll.getImmByIdSoggAllaData");
			q.setParameter("codEnte", codEnte);
			q.setParameter("idSogg", idSogg.longValue());
			q.setParameter("dtVal", dtVal);
			listaImm = q.getResultList();
       		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaImm;
	}
	
	@Override
	public List<TerrenoPerSoggDTO> getTerreniByIdSogg(RicercaSoggettoCatDTO rs) {
		String codEnte = rs.getCodEnte();
		BigDecimal idSogg = rs.getIdSogg();
		List<TerrenoPerSoggDTO> listaTerreni = new ArrayList<TerrenoPerSoggDTO>();
		TerrenoPerSoggDTO terreno=null;
		Sititrkc sititrkc=null; 
		String sezione=null;
		Date dtIniPos=null;
		Date dtFinPos=null; 
		String codTitolo=null;
		String titolo = null;
		logger.debug("TERRENI PER IDSOGG[CODENTE: "+codEnte+"];" + "[IDSOGG: "+idSogg+"]"  );
		Date dtFinFtz=new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {dtFinFtz= df.parse("31/12/9999");}catch(java.text.ParseException pe){}
		try {
			Query q = manager_diogene.createNamedQuery("Sititrkc_ConsCons.getTerreniByIdSogg");
			q.setParameter("idSogg", idSogg.longValue());
			q.setParameter("dtFinFtz", dtFinFtz);
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			List<Object[]> res = q.getResultList();
			for(Object[] eleRes: res){
				sititrkc=(Sititrkc )eleRes[0];
				sezione= (String)eleRes[1];
				dtIniPos= (Date)eleRes[2];
				dtFinPos= (Date)eleRes[3];
				codTitolo = (String)eleRes[4];
				if(codTitolo!=null)
					titolo = this.getDescTipoDocumento(codTitolo);
				titolo = titolo!=null ? titolo : "PROPRIETARIO";
				terreno= valTerrenoPerSoggDTO(sititrkc, sezione, dtIniPos, dtFinPos, titolo, idSogg);
				listaTerreni.add(terreno);
			}
			//da cons_Ufre_tab
			q = manager_diogene.createNamedQuery("Sititrkc_ConsUfre.getTerreniByIdSogg");
			q.setParameter("idSogg", idSogg.longValue());
			q.setParameter("dtFinFtz", dtFinFtz);
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			res = q.getResultList();
			for(Object[] eleRes: res){
				sititrkc=(Sititrkc )eleRes[0];
				sezione= (String)eleRes[1];
				dtIniPos= (Date)eleRes[2];
				dtFinPos= (Date)eleRes[3];
				codTitolo = (String)eleRes[4];
				if(codTitolo!=null)
					titolo = this.getDescTipoDocumento(codTitolo);
				titolo = titolo!=null ? titolo : "ALTRO";
				terreno= valTerrenoPerSoggDTO(sititrkc, sezione, dtIniPos, dtFinPos, titolo, idSogg);
				listaTerreni.add(terreno);
			}
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		if (listaTerreni.size() == 0)
			listaTerreni=null;
		return listaTerreni;
	}

	
	
	@Override
	public List<TerrenoPerSoggDTO> getTerreniByIdSoggAllaData(RicercaSoggettoCatDTO rs) {
		String codEnte = rs.getCodEnte();
		BigDecimal idSogg = rs.getIdSogg();
		Date dtVal = rs.getDtVal();
		List<TerrenoPerSoggDTO> listaTerreni = new ArrayList<TerrenoPerSoggDTO>();
		TerrenoPerSoggDTO terreno=null;
		Sititrkc sititrkc=null; 
		String sezione=null;
		Date dtIniPos=null;
		Date dtFinPos=null; 
		String codTitolo=null;
		String titolo = null;
		BigDecimal percPoss = null;
		logger.debug("TERRENI PER IDSOGG-DATA: [CODENTE: "+codEnte+"];" + "[IDSOGG: "+idSogg+"]" + "[DATA_VAL: "+dtVal+"]");
		Date dtFinFtz=new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {dtFinFtz= df.parse("31/12/9999");}catch(java.text.ParseException pe){}
		try {
			Query q = manager_diogene.createNamedQuery("Sititrkc_ConsCons.getTerreniByIdSoggAllaData");
			q.setParameter("idSogg", idSogg.longValue());
			q.setParameter("dtVal", dtVal);
			q.setParameter("dtFinFtz", dtFinFtz);
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			List<Object[]> res = q.getResultList();
			for(Object[] eleRes: res){
				sititrkc=(Sititrkc )eleRes[0];
				sezione= (String)eleRes[1];
				dtIniPos= (Date)eleRes[2];
				dtFinPos= (Date)eleRes[3];
				codTitolo = (String)eleRes[4];
				if(codTitolo!=null)
					titolo = this.getDescTipoDocumento(codTitolo);
				titolo = titolo!=null ? titolo : "PROPRIETARIO";
				terreno= valTerrenoPerSoggDTO(sititrkc, sezione, dtIniPos, dtFinPos, titolo,idSogg);
				percPoss = (BigDecimal)eleRes[5];
				terreno.setPercPoss(percPoss);
				listaTerreni.add(terreno);
			}
			//da cons_Ufre_tab
			q = manager_diogene.createNamedQuery("Sititrkc_ConsUfre.getTerreniByIdSoggAllaData");
			q.setParameter("idSogg", idSogg.longValue());
			q.setParameter("dtVal", dtVal);
			q.setParameter("dtFinFtz", dtFinFtz);
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			res = q.getResultList();
			for(Object[] eleRes: res){
				sititrkc=(Sititrkc )eleRes[0];
				sezione= (String)eleRes[1];
				dtIniPos= (Date)eleRes[2];
				dtFinPos= (Date)eleRes[3];
				codTitolo = (String)eleRes[4];
				if(codTitolo!=null)
					titolo = this.getDescTipoDocumento(codTitolo);
				titolo = titolo!=null ? titolo : "ALTRO";
				terreno= valTerrenoPerSoggDTO(sititrkc, sezione, dtIniPos, dtFinPos, titolo,idSogg);
				percPoss = (BigDecimal)eleRes[5];
				terreno.setPercPoss(percPoss);
				listaTerreni.add(terreno);
			}
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		if (listaTerreni.size() == 0)
			listaTerreni=null;
		return listaTerreni;
	}

	@Override
	public List<SiticonduzImmAll> getImmobiliByIdSoggCedutiInRangeDate(RicercaSoggettoCatDTO rs) {
		String codEnte = rs.getCodEnte();
		BigDecimal idSogg = rs.getIdSogg();
		Date dtRifDa = rs.getDtRifDa();
		Date dtRifA = rs.getDtRifA();
		List<SiticonduzImmAll> listaImm = null;
		logger.debug("IMMOBILI PER IDSOGG[CODENTE: "+codEnte+"];" + "[IDSOGG: "+idSogg+"]" + "[DATA_DA: "+dtRifDa+"]"+"[DATA_A: "+dtRifA+"]");
		try {
			Query q = manager_diogene.createNamedQuery("SiticonduzImmAll.getImmByIdSoggCedutiInRangeDate");
			q.setParameter("codEnte", codEnte);
			q.setParameter("idSogg", idSogg.longValue());
			q.setParameter("dtRifDa", dtRifDa);
			q.setParameter("dtRifA", dtRifA);
			listaImm = q.getResultList();
       		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaImm;
	}

	@Override
	public List<TerrenoPerSoggDTO> getTerreniByIdSoggCedutiInRangeDate(RicercaSoggettoCatDTO rs){
		String codEnte = rs.getCodEnte();
		BigDecimal idSogg = rs.getIdSogg();
		Date dtRifDa = rs.getDtRifDa();
		Date dtRifA = rs.getDtRifA();
		List<TerrenoPerSoggDTO> listaTerreni = new ArrayList<TerrenoPerSoggDTO>();
		TerrenoPerSoggDTO terreno=null;
		Sititrkc sititrkc=null; 
		String sezione=null;
		Date dtIniPos=null;
		Date dtFinPos=null; 
		String codTitolo=null;
		String titolo = null;
		BigDecimal percPoss=null;
		logger.debug("TERRENI PER IDSOGG_CEDUTI IN RANGE[CODENTE: "+codEnte+"];" + "[IDSOGG: "+idSogg+"]" + "[DATA_DA: "+dtRifDa+"]" + "[DATA_A: "+dtRifA+"]");
		Date dtFinFtz=new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {dtFinFtz= df.parse("31/12/9999");}catch(java.text.ParseException pe){}
		try {
			Query q = manager_diogene.createNamedQuery("Sititrkc_ConsCons.getTerreniByIdSoggCedutiInRangeDate");
			q.setParameter("idSogg", idSogg.longValue());
			q.setParameter("dtRifDa", dtRifDa);
			q.setParameter("dtRifA", dtRifA);
			q.setParameter("dtFinFtz", dtFinFtz);
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			List<Object[]> res = q.getResultList();
			for(Object[] eleRes: res){
				sititrkc=(Sititrkc )eleRes[0];
				sezione= (String)eleRes[1];
				dtIniPos= (Date)eleRes[2];
				dtFinPos= (Date)eleRes[3];
				codTitolo = (String)eleRes[4];
				if(codTitolo!=null)
					titolo = this.getDescTipoDocumento(codTitolo);
				titolo = titolo!=null ? titolo : "PROPRIETARIO";
				terreno= valTerrenoPerSoggDTO(sititrkc, sezione, dtIniPos, dtFinPos, titolo,idSogg);
				percPoss = (BigDecimal)eleRes[5];
				terreno.setPercPoss(percPoss);
				listaTerreni.add(terreno);
			}
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		if (listaTerreni.size() == 0)
			listaTerreni=null;
		return listaTerreni;
	}
	
	@Override
	public List<Sititrkc> getListaStoricoTerreniByFPS(RicercaOggettoCatDTO ro) {
		List<Sititrkc> listaTerreni = null;
		String codEnte = ro.getCodEnte();
		if(codEnte == null)
			codEnte = ro.getEnteId();
		String sezione = ro.getSezione();
		String foglio = StringUtils.cleanLeftPad(ro.getFoglio(),'0');
		String particella = ro.getParticella();
		String sub = ro.getSub();
	
		logger.debug("STORICO TERRENI PER FPS:[CODENTE: "+codEnte+"];" + 
		"[SEZIONE: "+sezione + 
		"[FOGLIO: "+foglio+"]; " +
		"[PARTICELLA: "+particella+"]" +
		"[SUB: "+sub+"]");
		
		try {
			Query q = manager_diogene.createNamedQuery("Sititrkc.getStoricoTerreniByFPS");
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			q.setParameter("codNazionale", codEnte);
			q.setParameter("sezione", sezione);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			q.setParameter("sub", sub);
			
			listaTerreni = q.getResultList();
			for (Sititrkc sititrkc: listaTerreni){
				BigDecimal codiQual= sititrkc.getQualCat();
				
				if (codiQual != null)
					sititrkc.setDescQualita(this.getDescQualitaTerreno(codiQual));
					
			}
       		
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaTerreni;
	}
	
	@Override
	public List<SoggettoDTO> getListaTitolariAttualiTerreno(RicercaOggettoCatDTO ro) {
		String sql = new CatastoQueryBuilder().getSQL_LISTA_TITOLARI_TERRENO(true);
		logger.debug("Ricerca Titolari Attuali Terreno");
		return getListaSoggettiTerreno(ro, sql);
	}

	@Override
	public List<SoggettoDTO> getListaTitolariStoriciTerreno(RicercaOggettoCatDTO ro) {
		String sql = new CatastoQueryBuilder().getSQL_LISTA_TITOLARI_TERRENO(false);
		logger.debug("Ricerca Titolari Storici Terreno");
		return getListaSoggettiTerreno(ro, sql);
	}
	
	@Override
	public List<SoggettoDTO> getListaAltriSoggAttualiTerreno(RicercaOggettoCatDTO ro) {
		String sql = new CatastoQueryBuilder().getSQL_LISTA_ALTRI_SOGGETTI_TERRENO(true);
		logger.debug("Ricerca Altri Soggetti Attuali Terreno");
		return getListaSoggettiTerreno(ro, sql);
	}

	@Override
	public List<SoggettoDTO> getListaAltriSoggStoriciTerreno(RicercaOggettoCatDTO ro) {
		String sql = new CatastoQueryBuilder().getSQL_LISTA_ALTRI_SOGGETTI_TERRENO(false);
		logger.debug("Ricerca Altri Soggetti Storici Terreno");
		return getListaSoggettiTerreno(ro, sql);
	}
	
	private List<SoggettoDTO> getListaSoggettiTerreno(RicercaOggettoCatDTO ro, String sql) {
		logger.debug("CatastoJPAImpl.getListaSoggettiTerreno(). SQL: " + sql); 
		List<SoggettoDTO> lista = new  ArrayList<SoggettoDTO>();
		String sezione = ro.getSezione();
		if(sezione == null)
			sezione ="";
		else
			sezione = sezione .trim();
		try {
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter(1,ro.getCodEnte());
			q.setParameter(2, sezione);
			q.setParameter(3, ro.getFoglio().trim());
			q.setParameter(4, ro.getParticella().trim());
			
			//Data Fine Validità del Terreno
			String dtFineValTerr = "99991231";
			if(ro.getDtVal()!=null){
				dtFineValTerr = sdf.format(ro.getDtVal());
			}
			q.setParameter(5,dtFineValTerr);
			
			logger.debug("CatastoJPAImpl.getListaSoggettiTerreno(). PARMS: COD_ENTE: " + ro.getCodEnte() +  ";SEZ." + sezione + "; FOGLIO: " + ro.getFoglio().trim() + "; PART: " + ro.getParticella().trim()+ "; DT.VAL: " + ro.getDtVal()); 
			List<Object[]> res = q.getResultList();
			logger.debug("CatastoJPAImpl.getListaSoggettiTerreno(). NUME. RIGHE: " +res.size());
			SoggettoDTO sogg=null; ConsSoggTab consSoggTab =null; 
			BigDecimal pkCuaa = null; Date dtIniPos= null;Date dtFinPos=null ;
			BigDecimal percPoss = null; String tipoTitolo = null; String tipoDocumento = null;
			for(Object[] eleRes: res){
				pkCuaa = (BigDecimal)eleRes[0];
				dtIniPos= (Date)eleRes[1];
				dtFinPos= (Date)eleRes[2];
				percPoss = (BigDecimal)eleRes[3];
				tipoTitolo = (String)eleRes[4];
				tipoDocumento = (String)eleRes[5];
				RicercaSoggettoCatDTO rs = new RicercaSoggettoCatDTO();
				rs.setEnteId(ro.getEnteId());
				rs.setIdSogg(pkCuaa);
				List<ConsSoggTab> listaSogg = getSoggettoByPkCuaa(rs);
				if (listaSogg != null && listaSogg.size() >0 )	 {
					logger.debug("soggetto identificato");
					consSoggTab = listaSogg.get(0);
					sogg = valSoggetto(consSoggTab);	
				}
				else {
					logger.debug("soggetto NON identificato");
					sogg = new SoggettoDTO();
					sogg.setTipo("");
					sogg.setDenominazioneSoggetto("NON IDENTIFICATO");
				}
				sogg.setDataInizioPoss(dtIniPos);
				sogg.setDataFinePoss(dtFinPos);
				sogg.setQuota(percPoss);
				
				if (tipoTitolo==null)
					tipoTitolo="";
				sogg.setTitolo(tipoTitolo.equals("1")? "PROPRIETARIO" :"ALTRO");
				sogg.setTipoDocumento(tipoDocumento);
				lista.add(sogg);
			}
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		} 
		return lista;
	}
	
	private TerrenoPerSoggDTO valTerrenoPerSoggDTO (Sititrkc sititrkc, String sezione ,Date dtIniPos, Date dtFinPos, String titolo, BigDecimal pkCuaa ) {
		TerrenoPerSoggDTO terreno = new TerrenoPerSoggDTO();
		terreno.setSezione(sezione);
		terreno.setDtIniPos(dtIniPos);
		terreno.setDtFinPos(dtFinPos);
		terreno.setFoglio(sititrkc.getId().getFoglio());
		terreno.setParticella(sititrkc.getId().getParticella());
		terreno.setSubalterno(sititrkc.getId().getSub());
		terreno.setPartita(sititrkc.getPartita());
		terreno.setSuperficie(sititrkc.getAreaPart());  
		terreno.setQualita(sititrkc.getQualCat());
		terreno.setClasse(sititrkc.getClasseTerreno());
		terreno.setRedditoAgrario(sititrkc.getRedditoAgrario());
		terreno.setRedditoDominicale(sititrkc.getRedditoDominicale());
		terreno.setDataFinVal(sititrkc.getId().getDataFine()); 
		terreno.setTitolo(titolo);
		
		terreno.setDataIniVal(sititrkc.getDataAggi());
		terreno.setPkCuaa(pkCuaa);
		
		return terreno;
	}
	
	private SoggettoDTO valSoggetto(ConsSoggTab consSoggTab) {
		SoggettoDTO sogg = new SoggettoDTO();
		sogg.setTipo(consSoggTab.getFlagPersFisica());
		sogg.setDenominazioneSoggetto(consSoggTab.getRagiSoci());
		if (consSoggTab.getFlagPersFisica().equals("G")) {
			sogg.setPivaSoggetto(consSoggTab.getCodiPiva());
		}else {
			sogg.setCfSoggetto(consSoggTab.getCodiFisc());
			sogg.setCognomeSoggetto(consSoggTab.getRagiSoci());
			sogg.setNomeSoggetto(consSoggTab.getNome());
		}
		return sogg;
	}

	protected boolean isCampoValido(String field){
		
		boolean valido = false;
		if(field!=null){
			field = field.trim();
			valido =  
			!field.isEmpty() && 
			!field.equals(".") &&
			!field.equals("&") &&
			!field.equals("-") ;
		}
		
		return valido;
	}

	
	public Sitidstr getViaByPrefissoDescr(RicercaCivicoDTO rc){
		Sitidstr strada = null;
		
		List<String> listaPrefissi = StringheVie.getToponimiDecoded(rc.getToponimoVia());
		if(listaPrefissi.size()==0)
			listaPrefissi.add(rc.getToponimoVia());
		
		//Estrae la strada dal catasto partendo da: toponimo, descrizione
		try {
			
			logger.info("RICERCA VIA [toponimo: "+rc.getToponimoVia() +"," +
									 "listaPrefissi: "+listaPrefissi +"," +
									 "descrizione: "+rc.getDescrizioneVia()+ "]");
			
			Query qs = manager_diogene.createNamedQuery("Sitidstr.getViaByPrefissoDescr");
			qs.setParameter("codNazionale", rc.getEnteId());
			qs.setParameter("listaPrefissi", listaPrefissi);
			qs.setParameter("descrizione", rc.getDescrizioneVia());
			List<Sitidstr> result =  qs.getResultList();
			if(result.size()>0)
				strada = result.get(0);
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		return strada;
	}
	
	public Siticivi getCivico(RicercaCivicoDTO rc){
		Siticivi civico = null;
		//Estrae il civico dal catasto partendo da: pkIdStra, num.Civico
		
		try {
			
			logger.info("RICERCA CIVICO [pkIdStra: "+rc.getIdVia() +"," +
										 "civico: " +rc.getCivico()+ "]");
			
			Query qs = manager_diogene.createNamedQuery("Siticivi.getCivico");
			qs.setParameter("codNazionale", rc.getEnteId());
			qs.setParameter("pkIdStra", rc.getIdVia());
			qs.setParameter("civico", rc.getCivico());
			List<Siticivi> result =  qs.getResultList();
			if(result.size()>0)
				civico = result.get(0);
			
		} catch (NoResultException nre) {
			logger.warn("Result size [0] " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return civico;
	}
	
	public List<LoadCatUiuId> getLoadCatUiuIdCollegati(RicercaOggettoCatDTO ro){
		List<LoadCatUiuId> lista = new ArrayList<LoadCatUiuId>();
		
		logger.debug("RICERCA UIU COLLEGATE...");
		logger.debug("Param CodEnte:" + ro.getEnteId());
		logger.debug("Param Foglio:" + ro.getFoglio());
		logger.debug("Param Particella:" + ro.getParticella());
		logger.debug("Param Subalterno:" + ro.getUnimm());
		
		try {
		
			Query qs = manager_diogene.createNamedQuery("LoadCatUiuId.getLoadCatUiuIdCollegate");
			qs.setParameter("codEnte", ro.getEnteId());
			qs.setParameter("foglio", ro.getFoglio());
			qs.setParameter("particella", ro.getParticella());
			qs.setParameter("subalterno", ro.getUnimm());
			
			lista = qs.getResultList();
			
			logger.debug("RICERCA UIU COLLEGATE result["+ lista.size()+"]");
			
			
		} catch (NoResultException nre) {
			logger.warn("Result size [0] " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return lista;
	}
	
	
	
/*	public DatiCivicoCatastoDTO getDatiCivicoCatasto(RicercaCivicoDTO rc){
		DatiCivicoCatastoDTO datiCivico = new DatiCivicoCatastoDTO();
	
		Sitidstr via = this.getViaByPrefissoDescr(rc);
		if(via == null){
			logger.info("Via non presente");
		}else{
			BigDecimal idVia = via.getPkidStra();
			logger.info("IdVia:"+idVia);
			rc.setIdVia(idVia.toString());
			
			Siticivi civico = this.getCivico(rc);
			if(civico==null){
				logger.info("Civico non presente");
			}else{
				BigDecimal idCivico = civico.getPkidCivi();
				logger.info("IdCivico:"+idCivico);
				
				//Utilizza ID della strada e del civico per recuperare la lista di Particelle che insistono sul civico
				CatastoSearchCriteria criteria = new CatastoSearchCriteria();
				criteria.setIdVia(idVia.toString());
				criteria.setIdCivico(idCivico.toString());
				criteria.setCodNazionale(rc.getEnteId());
				
				RicercaOggettoCatDTO roc = new RicercaOggettoCatDTO(criteria);
				roc.setCodEnte(rc.getEnteId());
				
				//Lista titolari su civico, alla data
				RicercaCivicoDTO rcc = new RicercaCivicoDTO();
				rcc.setEnteId(rc.getEnteId());
				rcc.setIdCivico(idCivico.toString());
				rcc.setDataRif(rc.getDataRif());
				List<ConsSoggTab> anagIdTitolari = this.getListaTitolariSuCivico(rcc);
				logger.info("Num.Titolari al Civico: " + anagIdTitolari.size());
				
				//Lista delle particelle catastali corrispondenti al civico
				List<ParticellaKeyDTO> particelle = this.getListaParticelle(roc);
				logger.info("Num.Particelle:"+particelle.size());
				
				List<ParticellaInfoDTO> infoParticelle = new ArrayList<ParticellaInfoDTO>();
				
				for(ParticellaKeyDTO p: particelle){
					ParticellaInfoDTO info = new ParticellaInfoDTO();
					
					roc.setSezione(p.getIdSezc());
					roc.setFoglio(p.getFoglio());
					roc.setParticella(p.getParticella());
					
					//Lista civici su cui insiste la particella
					List<IndirizzoDTO> civici = this.getListaIndirizziPartAllaData(roc);
					logger.info("Num.Indirizzi al Civico: " + civici.size());
					
					//Lista uiu della particella, alla data
					List<Sitiuiu> listaUiu = this.getListaUiAllaData(roc);
					logger.info("Num.Uiu: " + listaUiu.size());
					
					List<String> subalterni = new ArrayList<String>();
					HashMap<String,Integer> countUiuCategoria = new HashMap<String,Integer>();
					HashMap<String,BigDecimal> sumConsCategoria = new HashMap<String,BigDecimal>();
					for(Sitiuiu uiu : listaUiu){
					
						//Lista subalterni che insistono sulla particella
						Long unimm = uiu.getId().getUnimm();
						subalterni.add(Long.toString(unimm));
						
						String categoria = uiu.getCategoria();
						//Numero di unità immobiliari per classificazione catastale presenti sulla particella
						Integer count = countUiuCategoria.get(categoria);
						if(count == null)
							count = 0;
						count++;
						countUiuCategoria.put(categoria, count);
				
						//Totale della consistenza (mq) risultanti per classificazione catastale presenti sulla particella
						BigDecimal sum = sumConsCategoria.get(categoria)!= null ? sumConsCategoria.get(categoria) : new BigDecimal(0);
						BigDecimal consistenza = uiu.getConsistenza()!=null ? uiu.getConsistenza() : new BigDecimal(0);
					
						sum = sum.add(consistenza);
						sumConsCategoria.put(categoria, sum);
						
					}
					
					List<InfoPerCategoriaDTO> infoCategoria = new ArrayList<InfoPerCategoriaDTO>();
					Iterator countIt = countUiuCategoria.keySet().iterator();
					while(countIt.hasNext()){
						InfoPerCategoriaDTO infoCat = new InfoPerCategoriaDTO();
						String categoria = (String)countIt.next();
						infoCat.setCategoria(categoria);
						infoCat.setCountUiu(countUiuCategoria.get(categoria));
						infoCat.setConsistenzaTot(sumConsCategoria.get(categoria));
						infoCategoria.add(infoCat);
					}
					
					info.setIdSezc(p.getIdSezc());
					info.setFoglio(p.getFoglio());
					info.setParticella(p.getParticella());
					info.setIndirizzi(civici.toArray(new IndirizzoDTO[civici.size()]));
					info.setSubalterni(subalterni.toArray(new String[subalterni.size()]));
					info.setInfoPerCategoria(infoCategoria.toArray(new InfoPerCategoriaDTO[infoCategoria.size()]));
					infoParticelle.add(info);
				}
				datiCivico.setTitolariSuCivico(anagIdTitolari);
				datiCivico.setInfoParticelleCivico(infoParticelle);
			}
		}
		
		return datiCivico;
	}*/
	

	
	public List<ParticellaInfoDTO> getDatiCivicoCatasto(RicercaCivicoDTO rc){
		List<ParticellaInfoDTO> infoParticelle = new ArrayList<ParticellaInfoDTO>();
		
		BigDecimal pkIdStra = new BigDecimal(rc.getIdVia());
		logger.debug("pkIdStra["+pkIdStra+"]");
		
		Siticivi civico = this.getCivico(rc);
		if(civico==null){
			logger.info("Civico non presente");
		}else{
			BigDecimal idCivico = civico.getPkidCivi();
			logger.info("IdCivico:"+idCivico);
		
			//Utilizza ID della strada e del civico per recuperare la lista di Particelle che insistono sul civico
			CatastoSearchCriteria criteria = new CatastoSearchCriteria();
			criteria.setIdVia(rc.getIdVia());
			criteria.setIdCivico(rc.getIdCivico());
			criteria.setCodNazionale(rc.getEnteId());
					
			RicercaOggettoCatDTO roc = new RicercaOggettoCatDTO(criteria);
			roc.setCodEnte(rc.getEnteId());
					
			//Lista delle particelle catastali corrispondenti al civico
			List<ParticellaKeyDTO> particelle = this.getListaParticelle(roc);
			logger.info("Num.Particelle:"+particelle.size());
						
			for(ParticellaKeyDTO p: particelle){
				ParticellaInfoDTO info = new ParticellaInfoDTO();
				
				roc.setSezione(p.getIdSezc());
				roc.setFoglio(p.getFoglio());
				roc.setParticella(p.getParticella());
				
				//Lista civici su cui insiste la particella
				List<IndirizzoDTO> civici = this.getListaIndirizziPartAllaData(roc);
				logger.info("Num.Indirizzi al Civico: " + civici.size());
				
				//Lista uiu della particella, alla data
				List<Sitiuiu> listaUiu = this.getListaUiAllaData(roc);
				logger.info("Num.Uiu: " + listaUiu.size());
				
				List<String> subalterni = new ArrayList<String>();
				HashMap<String,Integer> countUiuCategoria = new HashMap<String,Integer>();
				HashMap<String,BigDecimal> sumConsCategoria = new HashMap<String,BigDecimal>();
				for(Sitiuiu uiu : listaUiu){
				
					//Lista subalterni che insistono sulla particella
					Long unimm = uiu.getId().getUnimm();
					subalterni.add(Long.toString(unimm));
					
					String categoria = uiu.getCategoria();
					//Numero di unità immobiliari per classificazione catastale presenti sulla particella
					Integer count = countUiuCategoria.get(categoria);
					if(count == null)
						count = 0;
					count++;
					countUiuCategoria.put(categoria, count);
			
					//Totale della consistenza (mq) risultanti per classificazione catastale presenti sulla particella
					BigDecimal sum = sumConsCategoria.get(categoria)!= null ? sumConsCategoria.get(categoria) : new BigDecimal(0);
					BigDecimal consistenza = uiu.getConsistenza()!=null ? uiu.getConsistenza() : new BigDecimal(0);
				
					sum = sum.add(consistenza);
					sumConsCategoria.put(categoria, sum);
					
				}
				
				List<InfoPerCategoriaDTO> infoCategoria = new ArrayList<InfoPerCategoriaDTO>();
				Iterator countIt = countUiuCategoria.keySet().iterator();
				while(countIt.hasNext()){
					InfoPerCategoriaDTO infoCat = new InfoPerCategoriaDTO();
					String categoria = (String)countIt.next();
					infoCat.setCategoria(categoria);
					infoCat.setCountUiu(countUiuCategoria.get(categoria));
					infoCat.setConsistenzaTot(sumConsCategoria.get(categoria));
					infoCategoria.add(infoCat);
				}
				
				info.setIdSezc(p.getIdSezc());
				info.setFoglio(p.getFoglio());
				info.setParticella(p.getParticella());
				info.setIndirizzi(civici.toArray(new IndirizzoDTO[civici.size()]));
				info.setSubalterni(subalterni.toArray(new String[subalterni.size()]));
				info.setInfoPerCategoria(infoCategoria.toArray(new InfoPerCategoriaDTO[infoCategoria.size()]));
				infoParticelle.add(info);
			}
		}
		
		return infoParticelle;
	}
	
	private List<ConsSoggTab> getTitolariSuCivicoByPkIdCivico(RicercaCivicoDTO rc){
		
		List<ConsSoggTab> titolari = new ArrayList<ConsSoggTab>();
		Date dataRif = rc.getDataRif();
		if(dataRif == null)
			dataRif = new Date();
		
		logger.debug("RICERCA Titolari su Civico " +
				"[pkIdCivico: "+rc.getIdCivico() +"];" +
				"[dataRif: "   +dataRif+ "]");
		
		try{
			
			Query qs = manager_diogene.createNamedQuery("ConsSoggTab.getTitolariSuCivico");
			qs.setParameter("pkIdCivico", rc.getIdCivico());
			qs.setParameter("dtRif", dataRif);
			titolari =  qs.getResultList();
			
			logger.info("Num.Titolari al Civico: " + titolari.size());
			
		} catch (NoResultException nre) {
			logger.warn("Result size [0] " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return titolari;
		
	}
	
	public List<ConsSoggTab> getTitolariSuCivicoByPkIdStraCivico(RicercaCivicoDTO rc){
		
		List<ConsSoggTab> titolari = new ArrayList<ConsSoggTab>();
		Date dataRif = rc.getDataRif();
		if(dataRif == null)
			dataRif = new Date();
		
		logger.debug("RICERCA Titolari su Civico " +
				"[pkidStra: "+rc.getIdVia() +"];" +
				"[civico: "    +rc.getCivico()+"];" +
				"[dataRif: "   +dataRif+ "]");
		
		try{
			
			Query qs = manager_diogene.createNamedQuery("ConsSoggTab.getTitolariSuCivicoByPkIdStraCivico");
			qs.setParameter("pkIdStra", rc.getIdVia());
			qs.setParameter("civico", rc.getCivico());
			qs.setParameter("dtRif", dataRif);
			titolari =  qs.getResultList();
			
			logger.debug("Result Size["+titolari.size()+"]");
			logger.info("Num.Titolari al Civico: " + titolari.size());
			
		} catch (NoResultException nre) {
			logger.warn("Result size [0] " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("",t);
			throw new CatastoServiceException(t);
		}
		
		return titolari;
		
	}


	
	@Override
	public List<Sitiuiu> getListaUIByParticella(RicercaOggettoCatDTO ro) {
		String codEnte = ro.getCodEnte(); 
		String foglio = ro.getFoglio().trim();
		String particella = ro.getParticella().trim();
		List<Sitiuiu>  listaUiu = new ArrayList<Sitiuiu>();
		String sezione = ro.getSezione();
		if (sezione == null)
			sezione="";
		logger.debug("getListaUIByParticella()-LISTA U.I. [ENTE: "+codEnte+"]; "+ "[SEZIONE: "+sezione+"];" + "[FOGLIO: "+foglio+"]; " + "[PARTIC.: "+particella+"]; ");
		try {
			Query q = manager_diogene.createNamedQuery("Sitiuiu.listaUIByParticella");
			q.setParameter("codEnte", codEnte);
			q.setParameter("sezione", sezione);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			listaUiu = (List<Sitiuiu>)q.getResultList();
    		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		return listaUiu;
	}
	
	@Override
	public List<Sititrkc> getListaTerreniByFP(RicercaOggettoCatDTO ro) {
		List<Sititrkc> listaTerreni = null;
		String codEnte = ro.getCodEnte(); 
		if(codEnte == null)
			codEnte = ro.getEnteId();
		
		String sezione = ro.getSezione();
		if (sezione == null) 
			sezione="";
	
		String foglio = StringUtils.cleanLeftPad(ro.getFoglio(),'0');
		String particella = ro.getParticella();
		Date dtVal = ro.getDtVal();
		
		Query q = null;
		logger.debug("TERRENI PER F,P ALLA DATA:"
				+ "[CODENTE: "+codEnte+"] [SEZIONE: "+sezione + "] [FOGLIO: "+foglio+"] [PARTICELLA: "+particella+"] [DATA_VAL: "+dtVal+"]");
		try {
			if(dtVal==null)
				q = manager_diogene.createNamedQuery("Sititrkc.getTerreniByFP");
			else{
				q = manager_diogene.createNamedQuery("Sititrkc.getTerreniByFPAllaData");
				q.setParameter("dtVal", dtVal);
			}
			q.setParameter("codNazionale", codEnte);
			q.setParameter("sezione", sezione);
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			listaTerreni = q.getResultList();
			for (Sititrkc sititrkc: listaTerreni){
				BigDecimal codiQual= sititrkc.getQualCat();
				
				if (codiQual != null)
					sititrkc.setDescQualita(this.getDescQualitaTerreno(codiQual));
				
			}
       		
		}catch (Throwable t) {
			logger.error("CatastoJPAImpl.getListaTerreniByFP", t);
			throw new CatastoServiceException(t);
		}
		return listaTerreni;
	}
	
	@Override
	public List<Sitipart> getParticelleSitipart(PartDTO dto) {
		List<Sitipart> res = new ArrayList<Sitipart>();
		
		try {
			logger.info("Recupero lista particelle sitipart");
			Query q = manager_diogene.createNamedQuery("SpProf.getParticellaSitipart");
			q.setParameter("foglio", dto.getFoglio());
			q.setParameter("particella", dto.getParticella());
			q.setParameter("codiFiscLuna", dto.getCodiFiscLuna());
			q.setParameter("maxdate", dto.getMaxdate());
			
			res = q.getResultList();
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		
		return res;
	}

	@Override
	public List<Sitisuolo> getParticelleSitisuolo(PartDTO dto) {
		List<Sitisuolo> res = new ArrayList<Sitisuolo>();
		
		try {
			logger.info("Recupero lista particelle sitisuolo");
			Query q = manager_diogene.createNamedQuery("SpProf.getParticellaSitisuolo");
			q.setParameter("foglio", dto.getFoglio());
			q.setParameter("particella", dto.getParticella());
			q.setParameter("codiFiscLuna", dto.getCodiFiscLuna());
			q.setParameter("maxdate", dto.getMaxdate());
			
			res = q.getResultList();
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		
		return res;
	}
	

	public List<ImmobiliAccatastatiOutDTO> getImmobiliAccatastatiByPkCuaa(RicercaSoggettoCatDTO input){
		
		List<ImmobiliAccatastatiOutDTO> result = new ArrayList<ImmobiliAccatastatiOutDTO>();
		
		try{
			
			//Se non trova il soggetto non esegue la seconda query 
			if(input.getIdSogg()!=null){
				String sql2 = (new CatastoQueryBuilder().createQueryImmobiliAccatastati_Step2(input));
				logger.debug("CatastoJPAImpl-getImmobiliAccatastatiByPkCuaa() PK_CUAA["+input.getIdSogg()+"]");
				logger.debug("CatastoJPAImpl-getImmobiliAccatastatiByPkCuaa() SQL["+sql2+"]");
	
				if (sql2 != null) {
					Query q = manager_diogene.createNativeQuery(sql2);
			
					List<Object[]> rs  = q.getResultList();
					
					for (Object[] o: rs){
						ImmobiliAccatastatiOutDTO dto = new ImmobiliAccatastatiOutDTO();
						
							//REGISTRARE OGGETTI IN DTO
							dto.setPkCuaa(input.getIdSogg());
							
							dto.setSezione(getNullString(o[0]));
							dto.setFoglio(getNullString(o[1]));
							dto.setNumero(getNullString(o[2]));
							dto.setSubalterno(getNullString(o[3]));
							dto.setSub(getNullString(o[4]));
							dto.setPartitaCatastale(getNullString(o[5]));
							dto.setCodiceVia(getNullString(o[6]));
							dto.setDescrizioneVia(getNullString(o[7]));
							dto.setCivico(getNullString(o[8]));
							//dto.setIndirizzoCatastale(getNullString(o[9]));  --> Ricavare da metodo esterno
							dto.setIndirizzoCatastale(getNullString(this.getIndirizzoCatastaleUiu(input.getEnteId(), dto.getFoglio(), dto.getNumero(), dto.getSubalterno())));
							dto.setPercentualePossesso(getNullString(o[10]));
							dto.setTipoTitolo(getNullString(o[11]));
							dto.setDesTipoTitolo(getNullString(o[12]));
							dto.setCategoria(getNullString(o[13]));
							dto.setClasse(getNullString(o[14]));
							dto.setConsistenza(getNullString(o[15]));
							dto.setRendita(getNullString(o[16]));
							dto.setSupCat(getNullString(o[17]));
							dto.setTipoEvento(getNullString(o[18]));
							dto.setDataInizioUiu(o[19]!=null ? sdf_bar.format(o[19]) : "");
							dto.setDataFineUiu(o[20]!=null ? sdf_bar.format(o[20]) : "");
							dto.setDataInizioTit(o[21]!=null ? sdf_bar.format(o[21]) : "");
							dto.setDataFineTit(o[22]!=null ? sdf_bar.format(o[22]) : "");
							dto.setTitNoCod(getNullString(o[23]));
						
						result.add(dto);
					}
				}
			}
		
		}catch(Throwable t) {
			logger.error("Eccezione in getImmobiliAccatastatiByDatiSoggetto:", t);
			throw new CatastoServiceException(t);
		}
		
		return result;
		
	}
	
	public List<TerrenoPerSoggDTO> getTerreniAccatastatiByPkCuaa(RicercaSoggettoCatDTO input){
		
		List<TerrenoPerSoggDTO> result = new ArrayList<TerrenoPerSoggDTO>();
		
		try{
			
			//Se non trova il soggetto non esegue la seconda query 
			if(input.getIdSogg()!=null){
				String sql2 = new CatastoQueryBuilder().createQueryTerreniAccatastati_Step2(input);
				
				logger.debug("CatastoJPAImpl-getTerreniAccatastatiByPkCuaa() PK_CUAA["+input.getIdSogg()+"]");
				logger.debug("CatastoJPAImpl-getTerreniAccatastatiByPkCuaa() SQL["+sql2+"]");
	
				if (sql2 != null) {
					Query q = manager_diogene.createNativeQuery(sql2);
			
					List<Object[]> rs  = q.getResultList();
					
					for (Object[] o: rs){
						TerrenoPerSoggDTO dto = new TerrenoPerSoggDTO();
						
							//REGISTRARE OGGETTI IN DTO
							dto.setPkCuaa(input.getIdSogg());
							
							dto.setSezione(getNullString(o[0]));
							BigDecimal foglio = (BigDecimal)o[1];
							dto.setFoglio(Long.valueOf(foglio.toString()));
							dto.setParticella(getNullString(o[2]));
							dto.setSubalterno(getNullString(o[3]));
							dto.setPartita(getNullString(o[4]));
							dto.setPercPoss((BigDecimal)o[5]);
							dto.setTipoTitolo(getNullString(o[6]));
							dto.setDesTipoTitolo(getNullString(o[7]));
							dto.setQualita((BigDecimal)o[8]);
							dto.setDescQualita((String)o[9]);
							dto.setClasse(getNullString(o[10]));
							dto.setSuperficie((BigDecimal)o[11]);
							dto.setRendita((BigDecimal)o[12]);
							dto.setRedditoDominicale((BigDecimal)o[13]);
							dto.setRedditoAgrario((BigDecimal)o[14]);
							dto.setDataIniVal((Date)o[15]);
							dto.setDataFinVal((Date)o[16]);
							dto.setDtIniPos((Date)o[17]);
							dto.setDtFinPos((Date)o[18]);
							dto.setTitNoCod(getNullString(o[19]));
						
						result.add(dto);
					}
				}
			}
		
		}catch(Throwable t) {
			logger.error("Eccezione in getTerreniAccatastatiByDatiSoggetto:", t);
			throw new CatastoServiceException(t);
		}
		
		return result;
		
	}
	
	public List<ImmobiliAccatastatiOutDTO> getImmobiliAccatastatiByDatiSoggetto(RicercaSoggettoCatDTO input){
		
		List<ImmobiliAccatastatiOutDTO> result = new ArrayList<ImmobiliAccatastatiOutDTO>();
		
		try {
			
			logger.debug("Recupero immobili da soggetto");
			String sql1 = (new CatastoQueryBuilder().createQueryPkcuaaSoggetti(input));
			logger.debug("CatastoJPAImpl-getImmobiliAccatastatiByDatiSoggetto(). SQL_Step1["+sql1+"]");
			if(sql1!=null){
				
				Query q1 = manager_diogene.createNativeQuery(sql1);
				List<BigDecimal> rs1  =  q1.getResultList();
				
				if(!rs1.isEmpty()){
					for(BigDecimal pkcuaa : rs1){
						input.setIdSogg(pkcuaa);
						result.addAll(getImmobiliAccatastatiByPkCuaa(input));
					}	
				}
			}
			
		}catch(Throwable t) {
			logger.error("Eccezione in getImmobiliAccatastatiByDatiSoggetto:", t);
			throw new CatastoServiceException(t);
		}
		
		return result;
		
	}//-------------------------------------------------------------------------
	
	public List<TerrenoPerSoggDTO> getTerreniAccatastatiByDatiSoggetto(RicercaSoggettoCatDTO input){
			
			List<TerrenoPerSoggDTO> result = new ArrayList<TerrenoPerSoggDTO>();
			
			try {
				
				logger.debug("Recupero terreni da soggetto");
				String sql1 = (new CatastoQueryBuilder().createQueryPkcuaaSoggetti(input));
				logger.debug("CatastoJPAImpl-getTerreniAccatastatiByDatiSoggetto(). SQL_Step1["+sql1+"]");
				if(sql1!=null){
					
					Query q1 = manager_diogene.createNativeQuery(sql1);
					List<BigDecimal> rs1  =  q1.getResultList();
					
					if(!rs1.isEmpty()){
						for(BigDecimal pkcuaa : rs1){
							input.setIdSogg(pkcuaa);
							result.addAll(getTerreniAccatastatiByPkCuaa(input));
						}
					}
				}
				
			}catch(Throwable t) {
				logger.error("Eccezione in getTerreniAccatastatiByDatiSoggetto:", t);
				throw new CatastoServiceException(t);
			}
			
			return result;
			
		}
	
	public String getRegimeImmobili(String codEnte, String foglio, String particella, String sub, String unimm, BigDecimal pkCuaa){
		
		String regime = "";
		try {
			
			logger.debug("Recupero regime di titolarità");
			String sql = new CatastoQueryBuilder().getRegimeImmobili_SQL();
			logger.debug("CatastoJPAImpl-getRegimeImmobili() SQL["+sql+"]");
			logger.debug("codEnte ["+codEnte+"]");
			logger.debug("foglio ["+foglio+"]");
			logger.debug("particella ["+particella+"]");
			logger.debug("sub ["+sub+"]");
			logger.debug("unimm ["+unimm+"]");
			logger.debug("pkCuaa ["+pkCuaa+"]");
			
			
			if(sql!=null){
				
				Query q = manager_diogene.createNativeQuery(sql);
				q.setParameter("codEnte", codEnte);
				q.setParameter("foglio", foglio);
				q.setParameter("particella", particella);
				q.setParameter("sub", sub);
				q.setParameter("unimm", unimm);
				q.setParameter("pkCuaa", pkCuaa);

				List<Object[]> rs  =  q.getResultList();
				
				if(!rs.isEmpty()){
					regime = rs.get(0)[5]==null?null:rs.get(0)[5].toString();
					logger.debug("Regime trovato." + regime);
				}else{
					logger.debug("Nessun regime trovato.");
				}
			}
			
		}catch(Throwable t) {
			logger.error("Eccezione in getRegimeImmobili:", t);
			throw new CatastoServiceException(t);
		}
		
		return regime;
		
	}
	
	public String getRegimeTerreni(String codEnte, String foglio, String particella, String sub, BigDecimal pkCuaa){
		
		String regime = "";
		try {
			
			logger.debug("Recupero regime di titolarità");
			String sql = new CatastoQueryBuilder().getRegimeTerreni_SQL();
			logger.debug("CatastoJPAImpl-getRegimeTerreni() SQL["+sql+"]");
			if(sql!=null){
				
				Query q = manager_diogene.createNativeQuery(sql);
				q.setParameter("codEnte", codEnte);
				q.setParameter("foglio", foglio);
				q.setParameter("particella", particella);
				q.setParameter("sub", sub);
				q.setParameter("pkCuaa", pkCuaa);
				
				List<Object[]> rs  =  q.getResultList();
				
				if(!rs.isEmpty()){
					regime = rs.get(0)[4]==null?null:rs.get(0)[4].toString();
					logger.debug("Regime trovato." + regime);
				}else{
					logger.debug("Nessun regime trovato.");
				}
			}
			
		}catch(Throwable t) {
			logger.error("Eccezione in getRegimeTerreni:", t);
			throw new CatastoServiceException(t);
		}
		
		return regime;
		
	}
	
	public String getSoggettoCollegatoImmobili(String codEnte, String foglio, String particella, String sub, String unimm, BigDecimal pkCuaa){
		
		String sogg = "-";
		try {
			
			logger.debug("Recupero soggetto collegato");
			String sql = new CatastoQueryBuilder().getSoggettoCollegatoImmobili_SQL();
			logger.debug("CatastoJPAImpl-getSoggettoCollegatoImmobili() SQL["+sql+"]");
			logger.debug("codEnte ["+codEnte+"]");
			logger.debug("foglio ["+foglio+"]");
			logger.debug("particella ["+particella+"]");
			logger.debug("sub ["+sub+"]");
			logger.debug("unimm ["+unimm+"]");
			logger.debug("pkCuaa ["+pkCuaa+"]");
			
			if(sql!=null){
				
				Query q = manager_diogene.createNativeQuery(sql);
				q.setParameter("codEnte", codEnte);
				q.setParameter("foglio", foglio);
				q.setParameter("particella", particella);
				q.setParameter("sub", sub);
				q.setParameter("unimm", unimm);
				q.setParameter("pkCuaa", pkCuaa);

				List<Object[]> rs  =  q.getResultList();
				
				if(!rs.isEmpty()){
					if (rs.get(0)[0] != null) {
						sogg = rs.get(0)[0].toString();
					}
					if (rs.get(0)[1] != null) {
						if (sogg.equals("-")) {
							sogg = rs.get(0)[1].toString();
						} else {
							sogg += (" " + rs.get(0)[1].toString());
						}						
					}					
					logger.debug("Soggetto collegato trovato." + sogg);
				}else{
					logger.debug("Nessun soggetto collegato trovato.");
				}
			}
			
		}catch(Throwable t) {
			logger.error("Eccezione in getSoggettoCollegatoImmobili:", t);
			throw new CatastoServiceException(t);
		}
		
		return sogg;
		
	}
	
	public String getSoggettoCollegatoTerreni(String codEnte, String foglio, String particella, String sub, BigDecimal pkCuaa){
		
		String sogg = "-";
		try {
			
			logger.debug("Recupero soggetto collegato");
			String sql = new CatastoQueryBuilder().getSoggettoCollegatoTerreni_SQL();
			logger.debug("CatastoJPAImpl-getSoggettoCollegatoTerreni() SQL["+sql+"]");
			if(sql!=null){
				
				Query q = manager_diogene.createNativeQuery(sql);
				q.setParameter("codEnte", codEnte);
				q.setParameter("foglio", foglio);
				q.setParameter("particella", particella);
				q.setParameter("sub", sub);
				q.setParameter("pkCuaa", pkCuaa);
				
				List<Object[]> rs  =  q.getResultList();
				
				if(!rs.isEmpty()){
					if (rs.get(0)[0] != null) {
						sogg = rs.get(0)[0].toString();
					}
					if (rs.get(0)[1] != null) {
						if (sogg.equals("-")) {
							sogg = rs.get(0)[1].toString();
						} else {
							sogg += (" " + rs.get(0)[1].toString());
						}						
					}
					logger.debug("Soggetto collegato trovato." + sogg);
				}else{
					logger.debug("Nessun soggetto collegato trovato.");
				}
			}
			
		}catch(Throwable t) {
			logger.error("Eccezione in getSoggettoCollegatoTerreni:", t);
			throw new CatastoServiceException(t);
		}
		
		return sogg;
		
	}
	
	@Override
	public List<TerrenoDerivazioneDTO> getTerreniGeneratori(String codEnte, BigDecimal ideMutaIniOrig){
		List<TerrenoDerivazioneDTO> lst = new ArrayList<TerrenoDerivazioneDTO>();
		
		try{
			
			logger.debug("getTerreniGeneratori ideMutaIniOrig["+ideMutaIniOrig+"]");
			Query q = manager_diogene.createNamedQuery("Sititrkc.getTerreniGeneratori");
			q.setParameter("codEnte", codEnte);
			q.setParameter("ideMutaIniDerivato", ideMutaIniOrig);
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			
			List<Object[]> res = q.getResultList();
			logger.debug("getTerreniGeneratori - ResultSize["+res.size()+"]");
			for(Object[] o : res ){
				TerrenoDerivazioneDTO td = new TerrenoDerivazioneDTO();
				
				td.setChiave((BigDecimal)o[0]);
				td.setCodNazionale((String)o[1]);
				td.setSezione((String)o[2]);
				td.setFoglio((Long)o[3]);
				td.setParticella((String)o[4]);
				td.setSubalterno((String)o[5]);
				td.setDataIniVal((Date)o[6]);
				td.setDataFinVal((Date)o[7]);
				td.setIdeMutaIni((BigDecimal)o[8]);
				td.setIdeMutaFine((BigDecimal)o[9]);
				
				lst.add(td);
			}
			
		}catch(Throwable t) {
			logger.error("Eccezione in getTerreniDerivati:", t);
			throw new CatastoServiceException(t);
			
		}
		
		return lst;
	}

	
	
	@Override
	public List<TerrenoDerivazioneDTO> getTerreniDerivati(String codEnte, BigDecimal ideMutaFineOrig){
		List<TerrenoDerivazioneDTO> lst = new ArrayList<TerrenoDerivazioneDTO>();
		
		try{
			
			logger.info("getTerreniDerivati ideMutaFineOrig["+ideMutaFineOrig+"]");
			Query q = manager_diogene.createNamedQuery("Sititrkc.getTerreniDerivati");
			q.setParameter("codEnte", codEnte);
			q.setParameter("ideMutaFineOrig", ideMutaFineOrig);
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			
			List<Object[]> res = q.getResultList();
			logger.debug("getTerreniDerivati - ResultSize["+res.size()+"]");
			for(Object[] o : res ){
				TerrenoDerivazioneDTO td = new TerrenoDerivazioneDTO();
				
				td.setChiave((BigDecimal)o[0]);
				td.setCodNazionale((String)o[1]);
				td.setSezione((String)o[2]);
				td.setFoglio((Long)o[3]);
				td.setParticella((String)o[4]);
				td.setSubalterno((String)o[5]);
				td.setDataIniVal((Date)o[6]);
				td.setDataFinVal((Date)o[7]);
				td.setIdeMutaIni((BigDecimal)o[8]);
				td.setIdeMutaFine((BigDecimal)o[9]);
				
				lst.add(td);
			}
			
		}catch(Throwable t) {
			logger.error("Eccezione in getTerreniDerivati:", t);
			throw new CatastoServiceException(t);
			
		}
		
		return lst;
	}

	
	public String getIndirizzoCatastaleUiu(String codEnte, String foglio, String particella, String unimm){
		
		String indirizzo = "";
		try {
			
			logger.debug("Recupero Indirizzo Catastale");
			String sql = new CatastoQueryBuilder().getSQL_LOCALIZZAZIONE_CATASTALE(true, true);
			logger.debug("CatastoJPAImpl-getIndirizzoCatastaleUiu() SQL["+sql+"]");
			if(sql!=null){
				
				Query q = manager_diogene.createNativeQuery(sql);
				q.setParameter("codEnte", codEnte);
				q.setParameter("foglio", foglio);
				q.setParameter("particella", particella);
				q.setParameter("unimm", unimm);
			
				List<String> rs  =  q.getResultList();
				
				if(!rs.isEmpty()){
					indirizzo = rs.get(0);
					logger.debug("Indirizzo trovato." + indirizzo);
				}else{
					logger.debug("Nessun indirizzo trovato.");
				}
			} 
			
		}catch(Throwable t) {
			logger.error("Eccezione in getIndirizzoCatastaleUiu:", t);
			throw new CatastoServiceException(t);
			
		}
		
		return indirizzo;
		
	}

	@Override
	public Date[] getMinMaxDateValUiu(RicercaOggettoCatDTO rc){
		
		Date[] data = new Date[2];
		String unimm = rc.getUnimm()!=null && rc.getUnimm().trim().length()>0 ? rc.getUnimm() : "0" ;
		
		logger.debug("getMinMaxDateValUiu Cod.Nazionale["+rc.getCodEnte()+"] Foglio["+rc.getFoglio()+"]Particella["+rc.getParticella()+"]Sub["+rc.getSub()+"]Unimm["+rc.getUnimm()+"]");
		
		try {
			Query q = manager_diogene.createNamedQuery("Sitiuiu.getMinMaxDateValUiu");

			q.setParameter("codNazionale", rc.getCodEnte());
			q.setParameter("foglio", rc.getFoglio());
			q.setParameter("particella", rc.getParticella());
			q.setParameter("sub", rc.getSub());
			q.setParameter("unimm", unimm);
			
			Object[] result = (Object[]) q.getSingleResult();
			if(result!=null){
				data[0]=(Date)result[0];
				data[1]=(Date)result[1];
			}
			
		}catch (NoResultException t){
			logger.warn("getMinMaxDateValUiu - NoResultException", t);
			
		} catch (Throwable t) {
			logger.error("getMinMaxDateValUiu", t);
			throw new CatastoServiceException(t);
		}
		return data;
	}
	
	@Override
	public Date[] getMinMaxDateValTerreno(RicercaOggettoCatDTO rc){
		Date[] data = new Date[2];
		
		String codEnte =  rc.getCodEnte()!=null ?  rc.getCodEnte() : rc.getEnteId();
		logger.debug("getMinMaxDateValTerreno Cod.Nazionale["+codEnte+"] Foglio["+rc.getFoglio()+"]Particella["+rc.getParticella()+"]Sub["+rc.getSub()+"]");
		
		
		try {
			Query q = manager_diogene.createNamedQuery("Sititrkc.getMinMaxDateValTerreno");

			q.setParameter("codNazionale", codEnte);
			q.setParameter("foglio", rc.getFoglio());
			q.setParameter("particella", rc.getParticella());
			q.setParameter("sub", rc.getSub());
			q.setParameter("annotazione", this.ANNOTAZIONE_AUTO);
			
			Object[] result = (Object[]) q.getSingleResult();
			if(result!=null){
				data[0]=(Date)result[0];
				data[1]=(Date)result[1];
				
			}
			
		}catch (NoResultException t){
			logger.error("getMinMaxDateValTerreno", t);
			
		}catch (Throwable t) {
			logger.error("getMinMaxDateValTerreno", t);
			throw new CatastoServiceException(t);
		}
		return data;
	}
	
	
	private String getNullString(Object o){
		String s = "";
		
		try{
			
			s= o!=null ? o.toString() : "";
		
		}catch(Exception e){
			logger.error("Eccezione in getNullString:", e);
			throw new CatastoServiceException(e);	
		}
	
		return s;
	}//-------------------------------------------------------------------------
	
	public List<CoordBaseDTO> getCoordUiByTopo(RicercaCivicoDTO rc){
		
		List<CoordBaseDTO> lstCoord = new ArrayList<CoordBaseDTO>();
		try {
			
			logger.debug("Recupero FP da toponomastica (=sedime, indirizzo, civico, esponente) ");
			String sql = new CatastoQueryBuilder().createQueryCoordUiByTopo(rc);
			logger.debug("CatastoJPAImpl-getCoordUiByTopo() SQL["+sql+"]");
			
			if(sql!=null){
				
				Query q = manager_diogene.createNativeQuery(sql).setFirstResult(0).setMaxResults(100);
			
				List<Object[]> rs  =  q.getResultList();
				
				if(!rs.isEmpty()){
					logger.debug("Coordinate trovate");
					
					Iterator<Object[]> itRs = rs.iterator();
					while(itRs.hasNext()){
						Object[] objs = itRs.next();
						CoordBaseDTO cb = new CoordBaseDTO();
						BigDecimal fog = (BigDecimal)objs[0];
						String par = (String)objs[1]; 
						cb.setFoglio( fog.toString() );
						cb.setNumero( par );
						
						lstCoord.add(cb);
					}
					
				}else{
					logger.debug("Nessun Coordinata trovata");
					
				}
			} 
			
		}catch(Throwable t) {
			logger.error("Eccezione in getCoordUiByTopo:", t);
			throw new CatastoServiceException(t);
		}
		
		return lstCoord;
	}//-------------------------------------------------------------------------
	
	public List<Object[]> getListaImmobiliByParams(RicercaOggettoCatDTO rc){

		List<Object[]> lstImmos = new ArrayList<Object[]>();
		try {
			
			logger.debug("Recupero Unita Immo da parametri vari (=sezione, foglio, mappale, subalterno, ...) ");
			String hql = new CatastoQueryBuilder().createQueryListaImmobiliByParams(rc);
			logger.debug("CatastoJPAImpl-getListaImmobiliByParams() HQL["+hql+"]");
			
			if(hql!=null){

				Query q = manager_diogene.createQuery(hql);
				if (rc.getLimit() != null && rc.getLimit()>0){
					q.setFirstResult(0);
					q.setMaxResults(rc.getLimit());
				}

				List<Object[]> rs  =  q.getResultList();

				if(!rs.isEmpty()){
					logger.debug("Coordinate trovate");
					
					Iterator<Object[]> itRs = rs.iterator();
					while(itRs.hasNext()){
						Object[] objs = itRs.next();
//						CoordBaseDTO cb = new CoordBaseDTO();
//						BigDecimal fog = (BigDecimal)objs[0];
//						String par = (String)objs[1]; 
//						cb.setFoglio( fog.toString() );
//						cb.setNumero( par );

						lstImmos.add(objs);
					}
				}else{
					logger.debug("Lista Immobili vuota");
				}
			} 
			
		}catch(Throwable t) {
			logger.error("Eccezione in getListaImmobiliByParams:", t);
			throw new CatastoServiceException(t);
		}
		
		return lstImmos;
	}//-------------------------------------------------------------------------
	
	/**Ricerco un immobile in catasto urbano e graffati per foglio e particella*/
	public List<ImmobileBaseDTO> getPrincipalieGraffatiByFP(RicercaOggettoCatDTO roc){
		List<ImmobileBaseDTO> lst = new ArrayList<ImmobileBaseDTO>();
		try {	
			 logger.debug("CatastoJPAImpl.getListaGraffatiByFP: "+
					"cod.Ente["+roc.getCodEnte()+"];" +
			 		"foglio["+roc.getFoglio()+"];" +
					"particella["+roc.getParticella()+"]");
			 String sql = new CatastoQueryBuilder().getListaImmobiliEGraffati_SQL();
			 logger.debug("CatastoJPAImpl.getListaGraffatiByFP ["+sql+"]");
			 Query q = manager_diogene.createNativeQuery(sql);		
			 q.setParameter("codEnte", roc.getCodEnte());
			 q.setParameter("foglio", roc.getFoglio());
			 q.setParameter("particella", roc.getParticella());
			
			 List<Object[]> res = q.getResultList();
			 for(Object[] o : res){
				 ImmobileBaseDTO i = new ImmobileBaseDTO();
				 i.setCodNazionale((String)o[0]);
				 i.setCodiFiscLuna((String)o[1]);
				 i.setSez((String)o[2]);
				 i.setFoglio((String)o[3]);
				 i.setParticella((String)o[4]);
				 i.setSub((String)o[5]);
				 i.setUnimm((String)o[6]);
				 i.setGraffato(o[7]!=null && "Y".equalsIgnoreCase(o[7].toString()) ? true : false);
				 lst.add(i);
			 }	
		
		}catch(Throwable t) {
			logger.error("Eccezione in getPrincipalieGraffatiByFP:", t);
			throw new CatastoServiceException(t);
		}
		
		return lst;
	}
	
	/**Lista dei graffati all'unità principale (passata a parametro)*/
	public List<ImmobileBaseDTO> getListaGraffatiPrincipale(RicercaOggettoCatDTO roc){
		List<ImmobileBaseDTO> lst = new ArrayList<ImmobileBaseDTO>();
		try {
					
			 logger.debug("CatastoJPAImpl.getListaGraffatiPrincipale: "+
					"cod.Ente["+roc.getCodEnte()+"];" +
			 		"foglio["+roc.getFoglio()+"];" +
					"particella["+roc.getParticella()+"];" +
					"unimm["+roc.getUnimm()+"]");
			 
			 Query q = manager_diogene.createNamedQuery("LoadCatUiuId.getListaGraffatiPrincipale");		
			 q.setParameter("codEnte", roc.getCodEnte());
			 q.setParameter("foglio", roc.getFoglio());
			 q.setParameter("particella", roc.getParticella());
			 q.setParameter("subalterno", roc.getUnimm());
			 
			 List<Object[]> res = q.getResultList();
			 for(Object[] o : res){
				 ImmobileBaseDTO i = new ImmobileBaseDTO();
				 i.setCodNazionale((String)o[0]);
				 i.setCodiFiscLuna((String)o[1]);
				 i.setSez((String)o[2]);
				 i.setFoglio((String)o[3]);
				 i.setParticella((String)o[4]);
				 i.setSub((String)o[5]);
				 i.setUnimm((String)o[6]);
				 lst.add(i);
			
			 }	
			 
		}catch(Throwable t) {
			logger.error("Eccezione in getListaGraffatiPrincipale:", t);
			throw new CatastoServiceException(t);
		}
		
		return lst;
	}
	
	/**Unità principale collegata alla graffata (passata a parametro)*/
	public ImmobileBaseDTO getPrincipaleDellaGraffata(RicercaOggettoCatDTO roc){
		try {
					
			 logger.debug("CatastoJPAImpl.getPrincipaleDellaGraffata: "+
					"cod.Ente["+roc.getCodEnte()+"];" +
			 		"foglio["+roc.getFoglio()+"];" +
					"particella["+roc.getParticella()+"];" +
					"unimm["+roc.getUnimm()+"]");
			 
			 Query q = manager_diogene.createNamedQuery("LoadCatUiuId.getPrincipaleGraffata");		
			 q.setParameter("codEnte", roc.getCodEnte());
			 q.setParameter("foglio", roc.getFoglio());
			 q.setParameter("particella", roc.getParticella());
			 q.setParameter("subalterno", roc.getUnimm());
			 
			 List<Object[]> res = q.getResultList();
			 if(res.size()>0){
				 Object[] o = res.get(0);
				 
				 ImmobileBaseDTO i = new ImmobileBaseDTO();
				 i.setCodNazionale((String)o[0]);
				 i.setSez((String)o[1]);
				 i.setFoglio((String)o[2]);
				 i.setParticella((String)o[3]);
				 i.setSub((String)o[4]);
				 i.setUnimm((String)o[5]);
				 
				return i;
			 }else 
				 return null;	
			 
		}catch(Throwable t) {
			logger.error("Eccezione in getPrincipaleDellaGraffata:", t);
			throw new CatastoServiceException(t);
		}
		
	}//-------------------------------------------------------------------------
	
	public List<Sititrkc> getListaTerreniByParams(RicercaOggettoCatDTO rc){

		List<Sititrkc> lstTerre = new ArrayList<Sititrkc>();
		try {
			
			logger.debug("Recupero Unita Terreni da parametri vari (=sezione, foglio, particella, subalterno, ...) ");
			String hql = new CatastoQueryBuilder().createQueryListaTerreniByParams(rc);
			logger.debug("CatastoJPAImpl-getListaTerreniByParams() HQL["+hql+"]");
			
			if(hql!=null){

				Query q = manager_diogene.createQuery(hql);
				if (rc.getLimit() != null && rc.getLimit()>0){
					q.setFirstResult(0);
					q.setMaxResults(rc.getLimit());
				}

				lstTerre = q.getResultList();
				for (Sititrkc sititrkc: lstTerre){
					BigDecimal codiQual= sititrkc.getQualCat();
					
					if (codiQual != null)
						sititrkc.setDescQualita(this.getDescQualitaTerreno(codiQual));
						
				}
			} 
			
		}catch(Throwable t) {
			logger.error("Eccezione in getListaTerreniByParams:", t);
			throw new CatastoServiceException(t);
		}
		
		return lstTerre;
	}//-------------------------------------------------------------------------
	
	
	
}
