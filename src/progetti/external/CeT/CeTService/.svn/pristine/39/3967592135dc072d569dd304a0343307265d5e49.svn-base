package it.webred.ct.data.access.basic.compravendite.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.compravendite.CompravenditeMUIException;
import it.webred.ct.data.access.basic.compravendite.CompravenditeMUIQueryBuilder;
import it.webred.ct.data.access.basic.compravendite.dto.RicercaCompravenditeDTO;
import it.webred.ct.data.access.basic.compravendite.dto.SoggettoCompravenditeDTO;
import it.webred.ct.data.model.compravendite.MuiFabbricatiIdentifica;
import it.webred.ct.data.model.compravendite.MuiNotaTras;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class CompravenditeMuiJPAImpl extends CTServiceBaseDAO implements CompravenditeMuiDAO {
	
	private static final long serialVersionUID = 5800207566138012724L;
	
	@Override
	public List<MuiNotaTras> getListaNoteByFPS(RicercaOggettoCatDTO rc)		throws CompravenditeMUIException {
		logger.debug("Compravendite - getListaNoteByFPS() [SEZIONE: "+rc.getSezione()+"];" + "[FOGLIO: "+rc.getFoglio()+"]; " + "[PARTIC.: "+rc.getParticella()+"];[SUB.: "+rc.getUnimm()+"])");
		List<MuiNotaTras>  listaNote=new ArrayList<MuiNotaTras>();
		try {
			Query q= null;
			String sezione = rc.getSezione();
			String sub = rc.getUnimm();
			if (sezione==null) {
				if (sub==null)
					q=manager_diogene.createNamedQuery("Join_FabbrIden_Nota_FabbrInfo.getNotaByFPSubNonVal");
				else {
					q=manager_diogene.createNamedQuery("Join_FabbrIden_Nota_FabbrInfo.getNotaByFPS");
					q.setParameter("sub",sub.trim());
				}
			}
			else {
				if (sub==null)
					q=manager_diogene.createNamedQuery("Join_FabbrIden_Nota_FabbrInfo.getNotaBySezFPSubNonVal");
				else	{
					q=manager_diogene.createNamedQuery("Join_FabbrIden_Nota_FabbrInfo.getNotaBySezFPS");
					q.setParameter("sub",sub.trim());
				}
				q.setParameter("sezione",sezione.trim());
			}
			q.setParameter("foglio",rc.getFoglio().trim());
			q.setParameter("particella",rc.getParticella().trim());
			listaNote = (List<MuiNotaTras> ) q.getResultList();
			logger.debug("Result size ["+listaNote.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
		return listaNote;
	}
	@Override
	public List<MuiNotaTras> getListaNoteTerrenoByFP(RicercaOggettoCatDTO rc) throws CompravenditeMUIException {
		logger.debug("Compravendite - getListaNoteTerrenoByFP() [SEZIONE: "+rc.getSezione()+"];" + "[FOGLIO: "+rc.getFoglio()+"]; " + "[PARTIC.: "+rc.getParticella()+"]");
		List<MuiNotaTras>  listaNote=new ArrayList<MuiNotaTras>();
		try {
			String sezione = rc.getSezione();
			Query q=null;
			if (sezione==null) {
				q= manager_diogene.createNamedQuery("Join_Nota_FabbrInfo.getNotaTerrenoByFP");
			}else {
				q = manager_diogene.createNamedQuery("Join_Nota_FabbrInfo.getNotaTerrenoBySezFP");
				sezione = sezione.trim();
				q.setParameter("sezione",sezione);	
			}
			q.setParameter("foglio",rc.getFoglio().trim());
			q.setParameter("particella",rc.getParticella().trim());
			listaNote = (List<MuiNotaTras> ) q.getResultList();
			logger.debug("Result size ["+listaNote.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
		return listaNote;
	}
	
	@Override
	public List<SoggettoCompravenditeDTO> getListaSoggettiNota(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		List<SoggettoCompravenditeDTO>  lista=new ArrayList<SoggettoCompravenditeDTO>();
		try {
			
			//String sql = (new CompravenditeMUIQueryBuilder()).getSQL_SOGGETTI_NOTA();
			String sql = new CompravenditeMUIQueryBuilder().createQueryListaSoggettiNotaByCriteria(rc);
			logger.debug("CompravenditeMUIJPAImpl - getListaSoggettiNota. SQL: " + sql + " PARMS: [" + rc.getIidNota() + ", " + rc.getTipoSoggetto() + ", " + rc.getIdentificativoSoggetto() + "]");
			
			Query q = manager_diogene.createNativeQuery(sql);
			if (rc.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(rc.getLimit());
			
			//q.setParameter(1, rc.getIidNota());
			
			List<Object[]> result= q.getResultList();		
			logger.debug("Result size ["+result.size()+"]");
			for (Object[] rs : result) {
				int index=-1;
				SoggettoCompravenditeDTO sogg = new SoggettoCompravenditeDTO();
				sogg.setTipoSoggetto((String)rs[++index]);
				sogg.setCognome((String)rs[++index]);
				sogg.setNome((String)rs[++index]);
				sogg.setCodiceFiscale((String)rs[++index]);
				sogg.setDenominazione((String)rs[++index]);
				sogg.setCodiceFiscaleG((String)rs[++index]);
				sogg.setFlagTipoTitolContro((String)rs[++index]);
				sogg.setFlagTipoTitolFavore((String)rs[++index]);
				sogg.setScCodiceDiritto((String)rs[++index]);
				sogg.setScQuotaNumeratore((String)rs[++index]);
				sogg.setScQuotaDenominatore((String)rs[++index]);
				sogg.setSfCodiceDiritto((String)rs[++index]);
				sogg.setSfQuotaNumeratore((String)rs[++index]);
				sogg.setSfQuotaDenominatore((String)rs[++index]);
				sogg.setSede((String)rs[++index]);
				sogg.setSesso((String)rs[++index]);
				sogg.setDataNascita((String)rs[++index]);
				sogg.setLuogoNascita((String)rs[++index]);
				BigDecimal notaIid = (BigDecimal)rs[++index]; 
				sogg.setIidNota( notaIid!=null?notaIid.toString():"" );
				
				lista.add(sogg);
			}

		}catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
	
		return lista;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<MuiNotaTras> getListaNoteByFP(RicercaOggettoCatDTO rc)	throws CompravenditeMUIException {
		logger.debug("Compravendite - getListaNoteByFP() [SEZIONE: "+rc.getSezione()+"];" + "[FOGLIO: "+rc.getFoglio()+"]; " + "[PARTIC.: "+rc.getParticella()+"])");
		List<MuiNotaTras>  listaNote=new ArrayList<MuiNotaTras>();
		try {
			Query q= null;
			String sezione = rc.getSezione();
			
			if (sezione==null) {
					q=manager_diogene.createNamedQuery("Join_FabbrIden_Nota_FabbrInfo.getNotaByFP");
				}
			else {
					q=manager_diogene.createNamedQuery("Join_FabbrIden_Nota_FabbrInfo.getNotaBySezFP");
					q.setParameter("sezione",sezione.trim());
			}
			q.setParameter("foglio",rc.getFoglio().trim());
			q.setParameter("particella",rc.getParticella().trim());
			listaNote = (List<MuiNotaTras> ) q.getResultList();
			logger.debug("Result size ["+listaNote.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
		return listaNote;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<MuiFabbricatiIdentifica> getListaUIByNotaFabbr(RicercaCompravenditeDTO rc)	throws CompravenditeMUIException {
		logger.debug("Compravendite - getListaUIByNotaFabbr() [SEZIONE: "+rc.getSezione()+"];" + "[FOGLIO: "+rc.getFoglio()+"]; " + "[PARTIC.: "+rc.getParticella()+"];[IID_FORNITURA: "+rc.getIidFornitura()  + "[IID_NOTA.: "+rc.getIidNota()+"]");
		List<MuiFabbricatiIdentifica>  lista=new ArrayList<MuiFabbricatiIdentifica>();
		try {
			String sezione = rc.getSezione();
			if (sezione ==null)
				sezione="";
			Query q= manager_diogene.createNamedQuery("FabbrIden.getListaUIByNotaFabbr");
			q.setParameter("sezione",sezione.trim());
			q.setParameter("foglio",rc.getFoglio().trim());
			q.setParameter("particella",rc.getParticella().trim());
			q.setParameter("iidFornitura",rc.getIidFornitura());
			q.setParameter("iidNota",rc.getIidNota());
			
			lista= (List<MuiFabbricatiIdentifica> ) q.getResultList();
			logger.debug("Result size ["+lista.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<MuiNotaTras> getListaNoteByCriteria(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		List<MuiNotaTras>  lista = new ArrayList<MuiNotaTras>();
		try {
			
			String hql = new CompravenditeMUIQueryBuilder().createQueryListaNoteByCriteria(rc);
			logger.debug("CompravenditeMUIJPAImpl - createQueryListaNoteByCriteria. HQL: " + hql + " PARMS: [" + rc.getIidNota() + "]");
			
			Query q = manager_diogene.createQuery(hql);
			if (rc.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(rc.getLimit());
			
			lista = q.getResultList();		
			logger.debug("Result size ["+lista.size()+"]");

		}catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
	
		return lista;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<Object[]> getFabbricatiByParams(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		List<Object[]>  lista = new ArrayList<Object[]>();
		try {
			
			String hql = new CompravenditeMUIQueryBuilder().createQueryFabbricatiByParams(rc);
			logger.debug("CompravenditeMUIJPAImpl - createQueryFabbricatiByParams. HQL: " + hql + " PARMS: [iidNota: " + rc.getIidNota() + ", sezione: " + rc.getSezione() + ", foglio: " + rc.getFoglio() + ", particella: " + rc.getParticella() + ", subalterno: " + rc.getSub() + " ]");
			
			Query q = manager_diogene.createQuery(hql);
			if (rc.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(rc.getLimit());
			
			lista = q.getResultList();		
			logger.debug("Result size ["+lista.size()+"]");

		}catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
	
		return lista;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<Object[]> getTerreniByParams(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		List<Object[]>  lista = new ArrayList<Object[]>();
		try {
			
			String hql = new CompravenditeMUIQueryBuilder().createQueryTerreniByParams(rc);
			logger.debug("CompravenditeMUIJPAImpl - createQueryTerreniByParams. HQL: " + hql + " PARMS: [" + rc.getIidNota() + "]");
			
			Query q = manager_diogene.createQuery(hql);
			if (rc.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(rc.getLimit());

			lista = q.getResultList();		
			logger.debug("Result size ["+lista.size()+"]");

		}catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
	
		return lista;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<Object[]> getSoggettiByParams(RicercaCompravenditeDTO rc) throws CompravenditeMUIException {
		List<Object[]>  lista = new ArrayList<Object[]>();
		try {
			
			String sql = new CompravenditeMUIQueryBuilder().createQuerySoggettiByParams(rc);
			logger.debug("CompravenditeMUIJPAImpl - createQuerySoggettiByParams. SQL: " + sql + " PARMS: [iidNota: " + rc.getIidNota() + ", tipologia: " + rc.getTipoSoggetto() + ", identificativo: " + rc.getIdentificativoSoggetto() + "]");
			
			Query q = manager_diogene.createNativeQuery(sql);
			if (rc.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(rc.getLimit());
			
			lista = q.getResultList();		
			logger.debug("Result size ["+lista.size()+"]");

		}catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
	
		return lista;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<MuiNotaTras> getListaNoteByCoord(RicercaOggettoCatDTO rc) throws CompravenditeMUIException {
		logger.debug("Compravendite - getListaNoteByCoord() [SEZIONE: "+rc.getSezione()+"];" + "[FOGLIO: "+rc.getFoglio()+"]; " + "[PARTIC.: "+rc.getParticella()+"]; " + "[SUBALTERNO: "+rc.getUnimm()+"]; ");
		List<MuiNotaTras>  listaNote = new ArrayList<MuiNotaTras>();
		try {
			String hql = new CompravenditeMUIQueryBuilder().createQueryListaNoteByCoord(rc);
			Query q = manager_diogene.createQuery(hql);
			if (rc.getLimit() > 0)
				q.setFirstResult(0).setMaxResults(rc.getLimit());

			listaNote = q.getResultList();	
			if (listaNote != null )
				logger.debug("Result size ["+listaNote.size()+"]");

		} catch (Throwable t) {
			logger.error("", t);
			throw new CompravenditeMUIException (t);
		}
		return listaNote;
		
	}//-------------------------------------------------------------------------

	
}
