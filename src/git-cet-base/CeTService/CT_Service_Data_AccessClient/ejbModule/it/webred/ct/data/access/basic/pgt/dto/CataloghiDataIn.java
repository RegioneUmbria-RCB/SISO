package it.webred.ct.data.access.basic.pgt.dto;

import java.util.List;

import it.webred.ct.data.model.pgt.PgtSqlDecoLayer;
import it.webred.ct.data.model.pgt.PgtSqlInfoLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayerPK;
import it.webred.ct.data.model.pgt.PgtSqlVisLayer;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class CataloghiDataIn extends CeTBaseObject {

	private String table;
	private String colonnaTema;
	private String descrTema;
	private PgtSqlLayerPK pkLayer;
	private PgtSqlLayer pgtSqlLayer; 
	private PgtSqlDecoLayer pgtSqlDecoLayer;
	private String nomeApp;
	private String qry;
	
	public String getNomeApp() {
		return nomeApp;
	}

	public void setNomeApp(String nomeApp) {
		this.nomeApp = nomeApp;
	}

	private boolean decoExist;
	
	private List<PgtSqlDecoLayer> listPgtSqlDecoLayer;
	private List<PgtSqlInfoLayer> listPgtSqlInfoLayer;
	private List<PgtSqlVisLayer> listPgtSqlVisLayer;
	
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getColonnaTema() {
		return colonnaTema;
	}

	public void setColonnaTema(String colonnaTema) {
		this.colonnaTema = colonnaTema;
	}

	public String getIdLayer() {
		return pkLayer.getId()!=null ? pkLayer.getId().toString() : "";
	}


	public PgtSqlLayer getPgtSqlLayer() {
		return pgtSqlLayer;
	}

	public void setPgtSqlLayer(PgtSqlLayer pgtSqlLayer) {
		this.pgtSqlLayer = pgtSqlLayer;
	}

	public PgtSqlDecoLayer getPgtSqlDecoLayer() {
		return pgtSqlDecoLayer;
	}

	public void setPgtSqlDecoLayer(PgtSqlDecoLayer pgtSqlDecoLayer) {
		this.pgtSqlDecoLayer = pgtSqlDecoLayer;
	}

	public String getDescrTema() {
		return descrTema;
	}

	public void setDescrTema(String descrTema) {
		this.descrTema = descrTema;
	}

	public boolean isDecoExist() {
		return decoExist;
	}

	public void setDecoExist(boolean decoExist) {
		this.decoExist = decoExist;
	}

	public List<PgtSqlDecoLayer> getListPgtSqlDecoLayer() {
		return listPgtSqlDecoLayer;
	}

	public void setListPgtSqlDecoLayer(List<PgtSqlDecoLayer> listPgtSqlDecoLayer) {
		this.listPgtSqlDecoLayer = listPgtSqlDecoLayer;
	}

	public String getStandard() {
		return pkLayer.getStandard();
	}

	

	public List<PgtSqlInfoLayer> getListPgtSqlInfoLayer() {
		return listPgtSqlInfoLayer;
	}

	public void setListPgtSqlInfoLayer(List<PgtSqlInfoLayer> listPgtSqlInfoLayer) {
		this.listPgtSqlInfoLayer = listPgtSqlInfoLayer;
	}

	public List<PgtSqlVisLayer> getListPgtSqlVisLayer() {
		return listPgtSqlVisLayer;
	}

	public void setListPgtSqlVisLayer(List<PgtSqlVisLayer> listPgtSqlVisLayer) {
		this.listPgtSqlVisLayer = listPgtSqlVisLayer;
	}

	public PgtSqlLayerPK getPkLayer() {
		return pkLayer;
	}

	public void setPkLayer(PgtSqlLayerPK pkLayer) {
		this.pkLayer = pkLayer;
	}

	public String getQry() {
		return qry;
	}

	public void setQry(String qry) {
		this.qry = qry;
	}

}
