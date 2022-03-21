package it.webred.ct.data.access.basic.pgt;

import it.webred.ct.data.access.basic.pgt.dto.CataloghiDataIn;
import it.webred.ct.data.access.basic.pgt.dto.TemaDTO;
import it.webred.ct.data.access.basic.pgt.dto.RicercaPgtDTO;
import it.webred.ct.data.access.basic.pgt.dto.VisLayerSqlDTO;
import it.webred.ct.data.model.pgt.PgtSqlDecoLayer;
import it.webred.ct.data.model.pgt.PgtSqlInfoLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayer;
import it.webred.ct.data.model.pgt.PgtSqlVisLayer;

import java.util.Hashtable;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface PGTService {
	public final static String KEY_HASH_COLONNE="KEY_HASH_COLONNE";
	public final static String KEY_HASH_VALORI="KEY_HASH_VALORI";

	public Hashtable<String,Object> getDatiPgtLayer(RicercaPgtDTO rp) ;
	public List<PgtSqlLayer> getListaLayersByTipo(RicercaPgtDTO rp) ;
	public List<PgtSqlLayer> getListaLayersDownloadable (RicercaPgtDTO rp) ;
	public PgtSqlLayer getLayerByPK(RicercaPgtDTO rp);
	public void executeStatement(RicercaPgtDTO rp );
    public boolean isTableExisting(RicercaPgtDTO rp );   
	
	//
	public List<PgtSqlLayer> getListaLayer(CataloghiDataIn dto);
	
	public List<String> getListaColonne(CataloghiDataIn dto);
	
	public List<TemaDTO> getListaTemi(CataloghiDataIn dto);

	public void persistLayer(CataloghiDataIn dto);

	public void mergeLayer(CataloghiDataIn dto);

	public void mergeDecoLayer(CataloghiDataIn dto);

	public void persistDecoLayer(CataloghiDataIn dto);

	public List<PgtSqlLayer> getListaLayerWithoutDeco(CataloghiDataIn dto);

	public PgtSqlLayer getLayerByTable(CataloghiDataIn dto);
	
	public Long getMaxIdLayer(CataloghiDataIn dto);

	public List<PgtSqlDecoLayer> getListaDecoLayer(CataloghiDataIn dataIn);
	
	public void eliminaLayer(CataloghiDataIn dataIn) ;
	
	public PgtSqlInfoLayer getInfoLayerByCol(CataloghiDataIn dataIn);
	
	public void mergeInfoLayer(CataloghiDataIn dataIn);
	
	public List<VisLayerSqlDTO> getListaSqlTemplate(CataloghiDataIn dataIn);
	
	public void salvaSqlVisLayer(CataloghiDataIn dataIn);
	
	public List<VisLayerSqlDTO> getListaSqlVisLayerByApp(CataloghiDataIn dataIn);
	public PgtSqlVisLayer getSqlVisLayerByLayerApp(CataloghiDataIn dataIn);
	
	public List<Object[]> getListaSql(CataloghiDataIn dataIn); 
	
}
