package it.webred.ct.data.access.basic.pgt;

import it.webred.ct.data.access.basic.pgt.dto.CataloghiDataIn;
import it.webred.ct.data.access.basic.pgt.dto.TemaDTO;
import it.webred.ct.data.access.basic.pgt.dto.VisLayerSqlDTO;
import it.webred.ct.data.access.basic.pgt.dao.PgtDAO;
import it.webred.ct.data.access.basic.pgt.dto.DatoPgtDTO;
import it.webred.ct.data.access.basic.pgt.dto.RicercaPgtDTO;
import it.webred.ct.data.model.pgt.PgtSqlDecoLayer;
import it.webred.ct.data.model.pgt.PgtSqlInfoLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayerPK;
import it.webred.ct.data.model.pgt.PgtSqlVisLayer;
import it.webred.ct.data.model.pgt.PgtSqlVisLayerPK;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class PGTServiceBean implements PGTService {
	protected Logger logger = Logger.getLogger("ctservice.log");
	@Autowired
	private PgtDAO pgtDAO;
	@Override
	public Hashtable<String,Object> getDatiPgtLayer(RicercaPgtDTO rp)  {
		
		Hashtable<String,Object> ht=new Hashtable<String,Object>();
		List<DatoPgtDTO> lista = new ArrayList<DatoPgtDTO>();
		PgtSqlLayer pgtSqlLayer = pgtDAO.getLayerByPK(rp);
		if (pgtSqlLayer==null)
			return ht;
		
		PgtSqlVisLayer sqlVis = rp.getVisLayer();
		
		//Visualizzo solo le righe presenti 
		if(sqlVis!=null && "Y".equalsIgnoreCase(sqlVis.getVisualizza()))
			ht=pgtDAO.getHTDatiPgt(rp, pgtSqlLayer);
		
		return ht;
	}
	
	@Override
	public PgtSqlVisLayer getSqlVisLayerByLayerApp(CataloghiDataIn dataIn){
		/*PgtSqlLayerPK pk = new PgtSqlLayerPK();
		pk.setId(Long.parseLong(dataIn.getIdLayer()));
		pk.setStandard(dataIn.getStandard());*/
		PgtSqlLayerPK pk = dataIn.getPkLayer();
		return pgtDAO.getSqlVisLayerByLayerApp(pk, dataIn.getNomeApp());
	}
	
	@Override
	public List<PgtSqlLayer> getListaLayersByTipo(RicercaPgtDTO rp) {
		List<PgtSqlLayer> lista= new ArrayList<PgtSqlLayer>();
		lista = pgtDAO.getListaLayersByTipo(rp);
		return lista;
	} 
	@Override
	public List<PgtSqlLayer> getListaLayersDownloadable(RicercaPgtDTO rp) {
		rp.setFlgDownload("Y");
		return pgtDAO.getListaLayersByFlgDownload(rp);
	}
	
	@Override
	public PgtSqlLayer getLayerByPK(RicercaPgtDTO rp) {
		return pgtDAO.getLayerByPK(rp);
	}
	
	@Override
	public void executeStatement(RicercaPgtDTO rp) {
		if (rp.isCreateIndexSDO())
			pgtDAO.executeCreateIndexSDO(rp);
		else if (rp.isDropTable())
			pgtDAO.executeCallableStatement1Param(rp);
		
	}
	//
	@Override
	public List<PgtSqlLayer> getListaLayer(CataloghiDataIn dataIn) {
		return pgtDAO.getListaLayer(dataIn);
	}
	
	@Override
	public List<PgtSqlLayer> getListaLayerWithoutDeco(CataloghiDataIn dataIn) {
		return pgtDAO.getListaLayerWithoutDeco(dataIn);
	}
	
	@Override
	public List<String> getListaColonne(CataloghiDataIn dataIn){
		return pgtDAO.getListaColonne(dataIn);
	}
	
	@Override
	public List<TemaDTO> getListaTemi(CataloghiDataIn dataIn){
		return pgtDAO.getListaTemi(dataIn);
	}
	
	@Override
	public PgtSqlLayer getLayerByTable(CataloghiDataIn dataIn){
		return pgtDAO.getLayerByTable(dataIn);
	}
	
	@Override
	public Long getMaxIdLayer(CataloghiDataIn dataIn){
		return pgtDAO.getMaxIdLayer(dataIn);
	}
	
	@Override
	public List<PgtSqlDecoLayer> getListaDecoLayer(CataloghiDataIn dataIn){
		return pgtDAO.getListaDecoLayer(dataIn);
	}
	
	@Override
	public PgtSqlInfoLayer getInfoLayerByCol(CataloghiDataIn dataIn){
		return pgtDAO.getInfoLayerByCol(dataIn);
	}
	
	@Override
	public void persistLayer(CataloghiDataIn dataIn){
		pgtDAO.persistLayer(dataIn.getPgtSqlLayer());
	}

	@Override
	public void mergeLayer(CataloghiDataIn dataIn){
		pgtDAO.mergeLayer(dataIn.getPgtSqlLayer());
	}
	
	@Override
	public void salvaSqlVisLayer(CataloghiDataIn dataIn){
		List<PgtSqlVisLayer> lista =  dataIn.getListPgtSqlVisLayer();
		for(PgtSqlVisLayer v:lista){
			/*PgtSqlVisLayer vFound = pgtDAO.getSqlVisLayer(v.getId());
			if(vFound!=null)*/
				pgtDAO.mergeSqlVisLayer(v);
			/*else
				pgtDAO.persistSqlVisLayer(v);*/
		}
	}
	
	@Override
	public void mergeDecoLayer(CataloghiDataIn dataIn){
		CataloghiDataIn di = new  CataloghiDataIn();
		di.setEnteId(dataIn.getEnteId());
		di.setUserId(dataIn.getUserId());
		
		PgtSqlLayerPK pk = new PgtSqlLayerPK();
		pk.setId(dataIn.getListPgtSqlDecoLayer().get(0).getIdLayer().longValue());
		pk.setStandard(dataIn.getListPgtSqlDecoLayer().get(0).getStandard());
		
		di.setPkLayer(pk);
		
		List<PgtSqlDecoLayer> listaDecoLayerOLD = pgtDAO.getListaDecoLayerByIdLayer(di);
		if (listaDecoLayerOLD != null) {
			for (PgtSqlDecoLayer decoLayer :  listaDecoLayerOLD) {
				pgtDAO.removeDecoLayer(decoLayer);
			}	
		}
		for (PgtSqlDecoLayer decoLayer :  dataIn.getListPgtSqlDecoLayer()) {
			decoLayer.setId(null);
			pgtDAO.persistDecoLayer(decoLayer);
		}
		
	}

	@Override
	public void persistDecoLayer(CataloghiDataIn dataIn){
		for (PgtSqlDecoLayer decoLayer :  dataIn.getListPgtSqlDecoLayer()) {
			pgtDAO.persistDecoLayer(decoLayer);
		}
		
	}
	@Override
	public void eliminaLayer(CataloghiDataIn dataIn) {
		dataIn.setPkLayer(dataIn.getPgtSqlLayer().getId());
		List<PgtSqlDecoLayer> listaDecoLayerOLD = pgtDAO.getListaDecoLayerByIdLayer(dataIn);
		if (listaDecoLayerOLD != null) {
			for (PgtSqlDecoLayer decoLayer :  listaDecoLayerOLD) {
				pgtDAO.removeDecoLayer(decoLayer);
			}	
		}
		
		PgtSqlLayerPK pk = dataIn.getPkLayer();
		List<PgtSqlVisLayer> lstSqlLayer = pgtDAO.getListaPgtSqlVisLayerByIdLayer(pk);
		for(PgtSqlVisLayer sqllay : lstSqlLayer){
			pgtDAO.removePgtSqlVisLayer(sqllay.getId());
		}
		
		pgtDAO.removeLayer(dataIn.getPgtSqlLayer());
		
	}
	
	@Override 
	public List<VisLayerSqlDTO> getListaSqlVisLayerByApp(CataloghiDataIn dataIn){
		
		List<VisLayerSqlDTO> lista = new ArrayList<VisLayerSqlDTO>();
		
		List<PgtSqlVisLayer> lst = pgtDAO.getListaPgtSqlVisLayerByApp(dataIn.getNomeApp());
		
		for(PgtSqlVisLayer l : lst){
			VisLayerSqlDTO dto = new VisLayerSqlDTO();
			dto.setModalita(l.getModInterrogazione());
			dto.setSqlLayer(l.getSqlLayer());
			dto.setVisualizza((l.getVisualizza()!=null && "Y".equals(l.getVisualizza())) ? true : false);
			dto.setApplicazione(dataIn.getNomeApp());
			dto.setIdTemplate(l.getId().getIdTmpl());
			
			RicercaPgtDTO rp = new RicercaPgtDTO();
			Long id = l.getId().getIdLayer();
			rp.setId(id);
			rp.setStandard(l.getId().getStndLayer());
			PgtSqlLayer pgt = pgtDAO.getLayerByPK(rp);
			dto.setLayer(pgt);
			
			lista.add(dto);
		}
		
		return lista;
		
	}
	
	@Override
	public void mergeInfoLayer(CataloghiDataIn dataIn){
		CataloghiDataIn di = new  CataloghiDataIn();
		di.setEnteId(dataIn.getEnteId());
		di.setUserId(dataIn.getUserId());
		
		PgtSqlLayerPK pk = new PgtSqlLayerPK();
		pk.setId(dataIn.getListPgtSqlInfoLayer().get(0).getIdLayer().longValue());
		pk.setStandard(dataIn.getListPgtSqlInfoLayer().get(0).getStandard());
		
		di.setPkLayer(pk);
		
		List<PgtSqlInfoLayer> listaInfoLayerOLD = pgtDAO.getListaInfoLayerByIdLayer(di);
		if (listaInfoLayerOLD != null) {
			for (PgtSqlInfoLayer infoLayer :  listaInfoLayerOLD) {
				pgtDAO.removeInfoLayer(infoLayer);
			}	
		}
		for (PgtSqlInfoLayer infoLayer :  dataIn.getListPgtSqlInfoLayer()) {
			pgtDAO.persistInfoLayer(infoLayer);
		}
		
	}
	
	@Override
	public boolean isTableExisting(RicercaPgtDTO rp) {
		return pgtDAO.isTableExisting(rp);
	}
	
	@Override
	public List<VisLayerSqlDTO> getListaSqlTemplate(CataloghiDataIn dataIn) {
		return pgtDAO.getListaSqlTemplate(dataIn.getPkLayer());
	}

	@Override
	public List<Object[]> getListaSql(CataloghiDataIn dataIn) {
		return pgtDAO.getListaSql( dataIn );
	}
}
