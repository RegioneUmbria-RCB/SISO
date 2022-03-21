package it.webred.ct.data.access.basic.pgt.dao;

import it.webred.ct.data.access.basic.pgt.dto.CataloghiDataIn;
import it.webred.ct.data.access.basic.pgt.dto.TemaDTO;
import it.webred.ct.data.access.basic.pgt.dto.VisLayerSqlDTO;
import it.webred.ct.data.access.basic.pgt.PgtServiceException;
import it.webred.ct.data.access.basic.pgt.dto.DatoPgtDTO;
import it.webred.ct.data.access.basic.pgt.dto.RicercaPgtDTO;
import it.webred.ct.data.model.pgt.PgtSqlDecoLayer;
import it.webred.ct.data.model.pgt.PgtSqlInfoLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayerPK;
import it.webred.ct.data.model.pgt.PgtSqlVisLayer;
import it.webred.ct.data.model.pgt.PgtSqlVisLayerPK;



import java.util.Hashtable;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

public interface PgtDAO {
	public PgtSqlLayer getLayerByPK(RicercaPgtDTO rp) throws PgtServiceException;
	public List<PgtSqlLayer> getListaLayersByTipo(RicercaPgtDTO rp) throws PgtServiceException;
    public Hashtable<String,Object> getHTDatiPgt(RicercaPgtDTO rp, PgtSqlLayer pgtSqlLayer)  throws PgtServiceException;
    public List<PgtSqlLayer> getListaLayersByFlgDownload(RicercaPgtDTO rp) throws PgtServiceException;
    public void executeCreateIndexSDO(RicercaPgtDTO rp )throws PgtServiceException;
    public void executeCallableStatement1Param(RicercaPgtDTO rp )throws PgtServiceException;    
    public boolean isTableExisting(RicercaPgtDTO rp )throws PgtServiceException;    
    //
    public List<PgtSqlLayer> getListaLayer(CataloghiDataIn dto);
	
	public List<PgtSqlDecoLayer> getListaDecoLayerByIdLayer(CataloghiDataIn dto);
	
	public List<String> getListaColonne(CataloghiDataIn dto);
	
	public List<TemaDTO> getListaTemi(CataloghiDataIn dto);

	public void persistLayer(PgtSqlLayer layer);

	public void mergeLayer(PgtSqlLayer layer);

	public void mergeDecoLayer(PgtSqlDecoLayer layer);

	public void persistDecoLayer(PgtSqlDecoLayer layer);

	public List<PgtSqlLayer> getListaLayerWithoutDeco(CataloghiDataIn dto);

	public PgtSqlLayer getLayerByTable(CataloghiDataIn dto);

	public Long getMaxIdLayer(CataloghiDataIn dto);

	public List<PgtSqlDecoLayer> getListaDecoLayer(CataloghiDataIn dataIn);
	
	public void removeDecoLayer(PgtSqlDecoLayer decoLayer);
	
	public void removeLayer(PgtSqlLayer layer);
	
	public List<PgtSqlInfoLayer> getListaInfoLayerByIdLayer(CataloghiDataIn dto);
	
	public PgtSqlInfoLayer getInfoLayerByCol(CataloghiDataIn dataIn);
	
	public void removeInfoLayer(PgtSqlInfoLayer layer);
	
	public void persistInfoLayer(PgtSqlInfoLayer layer);
	
	/*Gestione Template SQL per visualizzazione cataloghi da altre applicazioni*/
	
	public List<VisLayerSqlDTO> getListaSqlTemplate(PgtSqlLayerPK pk);
	
	public void mergeSqlVisLayer(PgtSqlVisLayer vis);
	public void persistSqlVisLayer(PgtSqlVisLayer vis);
	public void removePgtSqlVisLayer(PgtSqlVisLayerPK id);
	
	public PgtSqlVisLayer getSqlVisLayer(PgtSqlVisLayerPK pk);
	public List<PgtSqlVisLayer> getListaPgtSqlVisLayerByIdLayer(PgtSqlLayerPK pk);
	public List<PgtSqlVisLayer> getListaPgtSqlVisLayerByApp(String nomeApp);
	public PgtSqlVisLayer getSqlVisLayerByLayerApp(PgtSqlLayerPK pk, String nomeApp);

	public List<Object[]> getListaSql(CataloghiDataIn dataIn);
	
}
