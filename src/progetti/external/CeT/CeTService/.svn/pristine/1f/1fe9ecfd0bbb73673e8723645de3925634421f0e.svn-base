package it.webred.ct.data.access.basic.pgt.dao;

import it.webred.ct.data.access.basic.pgt.dto.CataloghiDataIn;
import it.webred.ct.data.access.basic.pgt.dto.TemaDTO;
import it.webred.ct.data.access.basic.pgt.dto.VisLayerSqlDTO;
import it.webred.ct.data.access.basic.common.CommonServiceException;
import it.webred.ct.data.access.basic.pgt.PGTService;
import it.webred.ct.data.access.basic.pgt.PgtServiceException;
import it.webred.ct.data.access.basic.pgt.dto.RicercaPgtDTO;
import it.webred.ct.data.access.basic.pgt.dto.DatoPgtDTO;
import it.webred.ct.data.model.pgt.PgtSqlDecoLayer;
import it.webred.ct.data.model.pgt.PgtSqlInfoLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayerPK;
import it.webred.ct.data.model.pgt.PgtSqlVisLayer;
import it.webred.ct.data.model.pgt.PgtSqlVisLayerPK;
import it.webred.ct.data.model.pgt.PgtSqlVisTemplate;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.Query;


public class PgtJPAImpl extends PgtBaseDAO implements PgtDAO {
	
	@Override
	public PgtSqlLayer getLayerByPK(RicercaPgtDTO rp) throws PgtServiceException {
		PgtSqlLayer ele=null;
		logger.debug("getLayerByPK - PARAMETRI[ID:"+ rp.getId()+"]; [STANDARD:"+ rp.getStandard()+"]");
		try {
			Query q =manager_diogene.createNamedQuery("Pgt.getLayerByPK");
			q.setParameter("id", rp.getId());
			q.setParameter("standard", rp.getStandard());
			ele =(PgtSqlLayer) q.getSingleResult();
			logger.debug("TROVATO PGT_SQl_LAYER:"+ele.getDesLayer());
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
		return ele;
	}
	@Override
	public List<PgtSqlLayer> getListaLayersByTipo(RicercaPgtDTO rp) throws PgtServiceException {
		List<PgtSqlLayer> lista= new ArrayList<PgtSqlLayer>();
		logger.debug("getListPgtSqlLayer - PARAMETRI[COD_TIP_LAYER:"+ rp.getTipoLayer()+"]");
		try {
			Query q =manager_diogene.createNamedQuery("Pgt.getLayersByTipo");
			q.setParameter("tipoLayer", rp.getTipoLayer());
			lista = (List<PgtSqlLayer>)q.getResultList();
			if (lista.size() > 0)
				logger.warn("TROVATI DATI PGT_SQL_LAYER");
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
		return lista;
	}

	
	@Override
	public Hashtable<String,Object> getHTDatiPgt(RicercaPgtDTO rp, PgtSqlLayer pgtSqlLayer) throws PgtServiceException {
		Hashtable<String,Object> ht = new Hashtable<String,Object>();
		List<DatoPgtDTO> listaDati = new ArrayList<DatoPgtDTO> ();
		List listaValori=new ArrayList();
		String nomeColShape = pgtSqlLayer.getNameColShape(); 
		String mask = "00000";		    
		// left padding by zeros...
	    String particella = mask.substring(0 , mask.length() - rp.getParticella().length()) + rp.getParticella();
		String sql ="";
		PgtSqlVisLayer sqlVis = rp.getVisLayer();
		
		PreparedStatement ps = null;
	    Connection con = null;
	    ResultSet rs = null;
	    try {
	    	
	    	con = dataSource.getConnection();
			if(sqlVis!=null && "A".equalsIgnoreCase(sqlVis.getModInterrogazione())){
				sql = sqlVis.getSqlLayer();
				 
				ps = con.prepareStatement(sql);
				int j=1;
				for(Object o : rp.getListaParams()){
					ps.setObject(j, o);
					j++;
				}
				
			}else{
				sql = "SELECT C.ID_SEZC AS SEZIONE, L.* FROM (" + pgtSqlLayer.getSqlLayer() + ") L, SITIPART S, SITICOMU C ";
			    sql += " WHERE C.CODI_FISC_LUNA='"+ rp.getCodNazionale()+ "' AND C.COD_NAZIONALE=S.COD_NAZIONALE ";
			    sql += " AND SDO_RELATE (L." +nomeColShape + ", sdo_geom.sdo_buffer (S.shape, 1, 0.05),'MASK=ANYINTERACT') = 'TRUE' " ;
		        if (rp.getSezione() != null && !rp.getSezione().equals(""))
		        	sql+=" AND C.ID_SEZC= '" + rp.getSezione().trim() + "'";
		        sql += " AND S.FOGLIO= ?  AND S.PARTICELLA= ? ";
		        
		        ps = con.prepareStatement(sql);
		        ps.setInt(1, rp.getFoglio());
		        ps.setString(2,particella);
			}
	        
	        
	        logger.debug("Layer: "+pgtSqlLayer.getDesLayer()+" - SQL DA ESEGUIRE: " + sql);
       
	        rs = ps.executeQuery();
	        if (rs==null)
	    	   return ht;
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int numTotCols = rsmd.getColumnCount();
	       	DatoPgtDTO ele=null;
	       	int indexShape=-1;
        	for (int i=1; i<=numTotCols ; i++ ) {
      		   ele = new DatoPgtDTO();
      		   if (rsmd.getColumnName(i).toLowerCase().equals(nomeColShape.toLowerCase())) {//la colonna shape non la considero (JBOSS NON RIESCE A RECUPERARNE I DATI DAI METADATI..)
      			   indexShape=i;
      			   continue;
      		   }
	    	   ele.setNomeDato(rsmd.getColumnName(i));
	    	   ele.setTipoOracleDato(rsmd.getColumnTypeName(i));
	    	   ele.setTipoJavaDato(rsmd.getColumnClassName(i));
	    	   listaDati.add(ele);
	    	}
        	Object valore = null;
        	List<Object> rigaValori=null;
 	        while (rs.next()) {
	           	rigaValori= new ArrayList<Object>();
	        	for (int i=1; i<=numTotCols ; i++ ) {
	        		valore = new Object();
	        		if (i==indexShape)
	        			continue;
	        		if (rs.getObject(i) != null)
	        			valore = rs.getObject(i) ;
	        		else
	        			valore="";
	        		rigaValori.add(valore);
	        	}
	        	listaValori.add(rigaValori);
	        }
 	        logger.debug("PgtJPAImpl. N. righe trovate -->" + listaValori.size());
 	        ht.put(PGTService.KEY_HASH_COLONNE, listaDati);
 	        ht.put(PGTService.KEY_HASH_VALORI, listaValori);
	     
	    }
	    catch (SQLException sqle)
	    {
	       logger.error("ERRORE SQL: ", sqle);
	       throw new PgtServiceException();
	    }
	    finally
	    {
	    	try { if (rs != null) rs.close(); } catch (Exception e) {}
	    	try { if (ps != null) ps.close(); } catch (Exception e) {}
	    	try { if (con != null) con.close(); } catch (Exception e) {}
	    }    

		return ht;
	}
	@Override
	public List<PgtSqlLayer> getListaLayersByFlgDownload(RicercaPgtDTO rp) 		throws PgtServiceException {
		List<PgtSqlLayer> lista= new ArrayList<PgtSqlLayer>();
		logger.debug("getListaLayersByFlgDownload - PARAMETRI[FLG_DOWNLOAD:"+ rp.getFlgDownload()+"]");
		try {
			Query q =manager_diogene.createNamedQuery("Pgt.getLayersByFlgDownload");
			q.setParameter("flgDownload", rp.getFlgDownload());
			lista = (List<PgtSqlLayer>)q.getResultList();
			if (lista.size() > 0)
				logger.debug("getListaLayersByFlgDownload()--->n.ele: " + lista.size());
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
		return lista;
	}
	@Override
	public void executeCreateIndexSDO(RicercaPgtDTO rp)	throws PgtServiceException {
		logger.debug("executeCallableStatement - stmt["+rp.getStatementSql() +"];TABELLA[" +rp.getNameTable() + "];SDO-COL[" +rp.getSdoColumn());
		Connection con = null;
		CallableStatement cstmt=null; 
	    try {
	    	con = dataSource.getConnection();
	  		cstmt = con.prepareCall(rp.getStatementSql());	
			cstmt.setString(1, rp.getNameTable());
			cstmt.setString(2, rp.getSdoColumn());
			cstmt.setNull(3,java.sql.Types.VARCHAR);
			cstmt.setString(4, rp.getNameTable()+"_SDX");
            cstmt.execute();
	    }
	    catch (SQLException sqle)
	    {
	       logger.error("ERRORE SQL: ", sqle);
	       throw new PgtServiceException();
	    }
	    finally
	    {
	      	try { if (con != null) con.close(); } catch (Exception e) {}
	    	try { if (cstmt != null) cstmt.close(); } catch (Exception e) {}
	    }    

	}
	@Override
	public void executeCallableStatement1Param(RicercaPgtDTO rp)	throws PgtServiceException {
		logger.debug("executeCallableStatement - stmt["+rp.getStatementSql() +"];TABELLA[" +rp.getNameTable());
		Connection con = null;
		CallableStatement cstmt=null; 
	    try {
	    	con = dataSource.getConnection();
	  		cstmt = con.prepareCall(rp.getStatementSql());	
			cstmt.setString(1, rp.getNameTable());
		    cstmt.execute();
	    }
	    catch (SQLException sqle)
	    {
	       logger.error("ERRORE SQL: ", sqle);
	       throw new PgtServiceException();
	    }
	    finally
	    {
	      	try { if (con != null) con.close(); } catch (Exception e) {}
	    	try { if (cstmt != null) cstmt.close(); } catch (Exception e) {}
	    }    
		
	}
	
	@Override
	public boolean isTableExisting(RicercaPgtDTO rp) throws PgtServiceException {
		boolean exists=false;
		try {
			logger.debug("VERIFICA ESISTENZA TABELLA " + rp.getOwner() +"." + rp.getNameTable());
			Query q;
			String sql = "SELECT * FROM ALL_CATALOG WHERE OWNER='" +rp.getOwner() +"' AND TABLE_NAME='" + rp.getNameTable().toUpperCase() +"'";
			logger.debug("esegue: " + sql);
			q = manager_diogene.createNativeQuery(sql);
			List<Object[]> result = q.getResultList();
			logger.debug("Result size [" + result.size() + "]");
			if (result!= null && result.size() > 0)
				exists=true;
			return exists;
		}
		catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}
	
	//
	@Override
	public List<String> getListaColonne(CataloghiDataIn dataIn) {
		List<String> result = new ArrayList<String>();
		try {
			logger.debug("ESTRAZIONE LISTA COLONNE TABELLA: " + dataIn.getTable());
			Query q;
			q = manager_diogene
					.createNativeQuery("select distinct column_name from cols where table_name = '"
							+ dataIn.getTable().toUpperCase()
							+ "' order by column_name");
			logger.debug("Result size [" + result.size() + "]");
			return q.getResultList();

		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}//-------------------------------------------------------------------------
	
	@Override
	public List<Object[]> getListaSql(CataloghiDataIn dataIn) {
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			logger.debug("QUERY NATIVA: " + dataIn.getQry());
			Query q = manager_diogene.createNativeQuery( dataIn.getQry() );
			
			result = q.getResultList();
			
			logger.debug("Result size [" + result.size() + "]");
			
			return result;

		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}//-------------------------------------------------------------------------
	

	@Override
	public List<TemaDTO> getListaTemi(CataloghiDataIn dataIn) {
		List<TemaDTO> result = new ArrayList<TemaDTO>();
		PgtSqlLayerPK pk = dataIn.getPkLayer();
		try {

			if (dataIn.isDecoExist()) {
				
				logger.debug("ESTRAZIONE LISTA DECO LAYER: "+ dataIn.getTable() + "id/std:" + pk.getId() + "/" + pk.getStandard());
				Query q;
				q = manager_diogene.createNamedQuery("Pgt.getDecoLayersByIdLayer");
				q.setParameter("idLayer", pk.getId());
				q.setParameter("standard", pk.getStandard());
				List<PgtSqlDecoLayer> lista = q.getResultList();
				for (PgtSqlDecoLayer decoLayer : lista) {
					TemaDTO temaDto = new TemaDTO();
					temaDto.setPgtSqlDecoLayer(decoLayer);
					result.add(temaDto);
				}
				logger.debug("LISTA DECO LAYER - N.ELE: " + lista.size() );
				
			} else {
				logger.debug("ESTRAZIONE LISTA COLONNE TABELLA: "
						+ dataIn.getTable());
				Query q;
				q = manager_diogene.createNativeQuery("select "
						+ dataIn.getColonnaTema()
						+ " codTema, count("
						+ dataIn.getColonnaTema()
						+ ") as occorrenze "
						+ (dataIn.getDescrTema() != null ? ", "
								+ dataIn.getDescrTema() + " descTema": "")
						+ " from "
						+ dataIn.getTable()
						+ " group by "
						+ dataIn.getColonnaTema()
						+ (dataIn.getDescrTema() != null ? ", "
								+ dataIn.getDescrTema() : "")
						+ " order by occorrenze desc");

				logger.debug("QUERY LISTA COLONNE TABELLA: "
						+ "select "
						+ dataIn.getColonnaTema()
						+ " codTema , count("
						+ dataIn.getColonnaTema()
						+ ") as occorrenze "
						+ (dataIn.getDescrTema() != null ? ", "
								+ dataIn.getDescrTema() + " descTema": "")
						+ " from "
						+ dataIn.getTable()
						+ " group by "
						+ dataIn.getColonnaTema()
						+ (dataIn.getDescrTema() != null ? ", "
								+ dataIn.getDescrTema() : "")
						+ " order by occorrenze desc");

				List<Object[]> lista = q.getResultList();

				for (Object[] obj : lista) {
					TemaDTO temaDto = new TemaDTO();
					PgtSqlDecoLayer decoLayer = new PgtSqlDecoLayer();
					decoLayer.setIdLayer(pk.getId());
					if (obj[0] != null && (obj[0] instanceof Number || obj[0] instanceof String || obj[0] instanceof java.util.Date))
						decoLayer.setCodut( obj[0].toString());
					decoLayer.setOccorrenze(((BigDecimal) obj[1]));
					if (dataIn.getDescrTema() != null) {
						if (obj[2] != null && (obj[2] instanceof Number || obj[2] instanceof String || obj[2] instanceof java.util.Date))
							//decoLayer.setDescrizione((String) obj[2]);
							//ALE
							decoLayer.setDescrizione(obj[2].toString());
					}
						
					temaDto.setPgtSqlDecoLayer(decoLayer);
					result.add(temaDto);
				}
			}

			return result;

		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}

	@Override
	public List<PgtSqlLayer> getListaLayer(CataloghiDataIn dataIn) {
		try {
			logger.debug("ESTRAZIONE LISTA PGT_SQL_LAYER");
			Query q;
			q = manager_diogene.createNamedQuery("Pgt.getLayer");
			return q.getResultList();

		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}

	@Override
	public List<PgtSqlLayer> getListaLayerWithoutDeco(CataloghiDataIn dataIn) {
		try {
			logger.debug("ESTRAZIONE LISTA PGT_SQL_LAYER SENZA DECO");
			Query q;
			q = manager_diogene.createNamedQuery("Pgt.getLayersWithoutDeco");
			return q.getResultList();

		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}
	
	@Override
	public PgtSqlInfoLayer getInfoLayerByCol(CataloghiDataIn dataIn) {
		PgtSqlInfoLayer ret = null;
		PgtSqlLayerPK pk = dataIn.getPkLayer();
		try {
			logger.debug("ESTRAZIONE PGT_SQL_INFO_LAYER DA NOME COLONNA");
			Query q;
			q = manager_diogene.createNamedQuery("Pgt.getInfoLayerByCol");
			q.setParameter("col", dataIn.getColonnaTema());
			q.setParameter("idLayer", pk.getId());
			q.setParameter("standard", pk.getStandard());
			List<PgtSqlInfoLayer> lista = q.getResultList();
			if(lista.size() > 0)
				ret = lista.get(0);
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
		
		return ret;

	}

	@Override
	public PgtSqlLayer getLayerByTable(CataloghiDataIn dataIn) {
		List<PgtSqlLayer> result = new ArrayList<PgtSqlLayer>();
		try {
			logger.debug("ESTRAZIONE PGT_SQL_LAYER BY TABLE");
			Query q;
			q = manager_diogene.createNamedQuery("Pgt.getLayerByTable")
					.setParameter("table", dataIn.getTable());
			result = q.getResultList();
			if (result.size() > 0)
				return result.get(0);

		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

		return null;

	}

	@Override
	public Long getMaxIdLayer(CataloghiDataIn dataIn) {

		try {
			logger.debug("ESTRAZIONE MAX ID PGT_SQL_LAYER");
			Query q;
			q = manager_diogene.createNamedQuery("Pgt.getMaxIdLayer");
			Long max = (Long) q.getSingleResult();
			if(max == null)
				max = new Long(1);
			return max;

		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}
	
	@Override
	public List<PgtSqlDecoLayer> getListaDecoLayer(CataloghiDataIn dataIn) {
		try {
			logger.debug("ESTRAZIONE LISTA PGT_SQL_DECO_LAYER");
			Query q;
			q = manager_diogene.createNamedQuery("Pgt.getDecoLayers");
			return q.getResultList();

		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}

	@Override
	public void mergeLayer(PgtSqlLayer layer) {

		try {
			manager_diogene.merge(layer);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}

	@Override
	public void persistLayer(PgtSqlLayer layer) {

		try {
			manager_diogene.persist(layer);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
	}

	@Override
	public void mergeDecoLayer(PgtSqlDecoLayer layer) {

		try {
			manager_diogene.merge(layer);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}

	}

	@Override
	public void persistDecoLayer(PgtSqlDecoLayer layer) {
		logger.debug("INSERIMENTO RIGA DECO LAYER ID: "+ layer.getId());
		try {
			manager_diogene.persist(layer);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
	}

	@Override
	public void removeDecoLayer(PgtSqlDecoLayer layer) {
		logger.debug("CANCELLAZIONE RIGA DECO LAYER ID: "+ layer.getId());
		try {
			PgtSqlDecoLayer lay = manager_diogene.getReference(PgtSqlDecoLayer.class, layer.getId());
			manager_diogene.detach(lay);
			manager_diogene.remove(lay);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
		
	}

	@Override
	public List<PgtSqlDecoLayer> getListaDecoLayerByIdLayer(CataloghiDataIn dto) {
		List<PgtSqlDecoLayer> lista = new ArrayList<PgtSqlDecoLayer>();
		PgtSqlLayerPK pk = dto.getPkLayer();
		try {
			logger.debug("ESTRAZIONE LISTA DECO LAYER BY ID: "+  pk.getId() + "-" + pk.getStandard() );
			Query q;
			q = manager_diogene.createNamedQuery("Pgt.getDecoLayersByIdLayer")
				.setParameter("idLayer", pk.getId());
			q.setParameter("standard", pk.getStandard());
			lista = q.getResultList();
			logger.debug("N.ELE "+ lista.size());
			return lista;
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
	}
	
	@Override
	public List<PgtSqlInfoLayer> getListaInfoLayerByIdLayer(CataloghiDataIn dto) {
		List<PgtSqlInfoLayer> lista = new ArrayList<PgtSqlInfoLayer>();
		PgtSqlLayerPK pk = dto.getPkLayer();
		try {
			logger.debug("ESTRAZIONE LISTA INFO LAYER BY ID: "+  pk.getId() + "-" + pk.getStandard() );
			Query q;
			q = manager_diogene.createNamedQuery("Pgt.getInfoLayersByIdLayer")
				.setParameter("idLayer", pk.getId());
			q.setParameter("standard", pk.getStandard());
			lista = q.getResultList();
			logger.debug("N.ELE "+ lista.size());
			return lista;
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
	}
	
	@Override
	public void removeLayer(PgtSqlLayer layer) {
		logger.debug("CANCELLAZIONE RIGA LAYER ID: "+ layer.getId());
		try {
			PgtSqlLayer lay = manager_diogene.getReference(PgtSqlLayer.class, layer.getId());
			manager_diogene.detach(lay);
			manager_diogene.remove(lay);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
		
	}
	
	@Override
	public void persistInfoLayer(PgtSqlInfoLayer layer) {
		logger.debug("INSERIMENTO RIGA INFO LAYER ID: "+ layer.getId());
		try {
			manager_diogene.persist(layer);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
	}

	@Override
	public void removeInfoLayer(PgtSqlInfoLayer layer) {
		logger.debug("CANCELLAZIONE RIGA INFO LAYER ID: "+ layer.getId());
		try {
			PgtSqlInfoLayer lay = manager_diogene.getReference(PgtSqlInfoLayer.class, layer.getId());
			manager_diogene.detach(lay);
			manager_diogene.remove(lay);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
		
	}
	
	/*Gestione Template SQL*/
	@Override
	public List<VisLayerSqlDTO> getListaSqlTemplate(PgtSqlLayerPK pkl) {
		List<VisLayerSqlDTO> lista = new ArrayList<VisLayerSqlDTO>();
		try {
			logger.debug("ESTRAZIONE LISTA PGT_SQL_VIS_LAYER");
			Query q1;
			Query q2;
			q1 = manager_diogene.createNamedQuery("Pgt.getListaSqlVisTemplate");
		
			List<PgtSqlVisTemplate> lstTmp = q1.getResultList();
			
			for(PgtSqlVisTemplate t : lstTmp){
				VisLayerSqlDTO dto = new VisLayerSqlDTO();
				dto.setIdTemplate(t.getId());
				dto.setApplicazione(t.getDescrApp());
				dto.setSqlTemplate(t.getSqlTemplate());
				
				//Per i nuovi cataloghi l'id non è ancora definito
				if(pkl!=null & pkl.getId()!=null && pkl.getStandard()!=null){
					PgtSqlVisLayerPK pk = new PgtSqlVisLayerPK();
					pk.setIdLayer(pkl.getId());
					pk.setIdTmpl(dto.getIdTemplate());
					pk.setStndLayer(pkl.getStandard());
					
					PgtSqlVisLayer lay = this.getSqlVisLayer(pk);
					if(lay!=null){
						dto.setSqlLayer(lay.getSqlLayer());
						dto.setModalita(lay.getModInterrogazione());
						dto.setVisualizza("Y".equals(lay.getVisualizza())? true : false);
					}
				}
				
				//Imposto di default la modalità cartografica
				if(dto.getModalita()==null)
					dto.setModalita("C");
				
				if(dto.getVisualizza()==null)
					dto.setVisualizza(false);
				
				lista.add(dto);
			}
				
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
		
		return lista;

	}

	@Override
	public List<PgtSqlVisLayer> getListaPgtSqlVisLayerByIdLayer(PgtSqlLayerPK pk){
		
		Query q2 = manager_diogene.createNamedQuery("Pgt.getListaSqlVisLayerByIdLayer");
		q2.setParameter("idLayer", pk.getId());
		q2.setParameter("standard",pk.getStandard());
		return q2.getResultList();
		
	}
	
	@Override
	public List<PgtSqlVisLayer> getListaPgtSqlVisLayerByApp(String nomeApp){
		
		Query q2 = manager_diogene.createNamedQuery("Pgt.getListaSqlVisLayerByApp");
		q2.setParameter("nomeApp", nomeApp);
		return q2.getResultList();
		
	}

	@Override
	public void mergeSqlVisLayer(PgtSqlVisLayer vis) {
		logger.debug("INSERIMENTO RIGA SQL LAYER ID: "+ vis.getId());
		try {
			manager_diogene.merge(vis);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
	}
	
	@Override
	public void persistSqlVisLayer(PgtSqlVisLayer vis) {
		logger.debug("INSERIMENTO RIGA SQL LAYER ID: "+ vis.getId());
		try {
			manager_diogene.persist(vis);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
	}
	
	@Override
	public PgtSqlVisLayer getSqlVisLayer(PgtSqlVisLayerPK pk) {
		logger.debug("GET LAYER ID: "+ pk);
		try {
			return manager_diogene.find(PgtSqlVisLayer.class, pk);
		} catch (Throwable t) {
			logger.warn("PgtSqlVisLayer non trovato ["+pk+"]", t);
			return null;
		}
	}
	
	@Override
	public void removePgtSqlVisLayer(PgtSqlVisLayerPK id) {
		logger.debug("CANCELLAZIONE RIGA SQL VIS LAYER ID - LayerId:"+ id.getIdLayer()+" TemplateId:"+id.getIdTmpl());
		try {
			PgtSqlVisLayer lay = manager_diogene.getReference(PgtSqlVisLayer.class, id);
			manager_diogene.detach(lay);
			manager_diogene.remove(lay);
		} catch (Throwable t) {
			logger.error("", t);
			throw new PgtServiceException (t);
		}
		
	}
	@Override
	public PgtSqlVisLayer getSqlVisLayerByLayerApp(PgtSqlLayerPK pk, String nomeApp) {
		logger.debug("getSqlVisLayerByLayerApp - LayerId:"+ pk.getId()+"Standard:"+pk.getStandard()+" NomeApp:"+nomeApp);
		try{
			Query q2 = manager_diogene.createNamedQuery("Pgt.getSqlVisLayerByLayerApp");
			q2.setParameter("nomeApp", nomeApp);
			q2.setParameter("idLayer", pk.getId());
			q2.setParameter("standard", pk.getStandard());
			List<PgtSqlVisLayer> rs = (List<PgtSqlVisLayer>) q2.getResultList();
			
			if(rs.size()>0)
				return rs.get(0);
			
		}catch(Throwable t){
			throw new PgtServiceException (t);
		}
		
		return null;
	}
	
	
}
