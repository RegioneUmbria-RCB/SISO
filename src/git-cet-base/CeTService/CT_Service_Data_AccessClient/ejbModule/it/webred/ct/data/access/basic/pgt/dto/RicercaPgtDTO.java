package it.webred.ct.data.access.basic.pgt.dto;

import it.webred.ct.data.model.pgt.PgtSqlVisLayer;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class RicercaPgtDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private String codNazionale;  
	private long id;
	private String desLayer;
	private String tipoLayer;
	private String standard;
	private String sezione;
	private Integer foglio;
	private String particella;
	private String flgDownload;
	private String statementSql;
	private boolean createIndexSDO;
	private boolean dropTable;

	//Lista parametri per la query alfanumerica dell'applicazione
	private List<Object> listaParams = new ArrayList<Object>();
	
	/*
	 * parametri per lo statement
	 */
	private String nameTable;
	private String sdoColumn;
	
	private String owner;
	
	private PgtSqlVisLayer visLayer;
	
	
	public String getCodNazionale() {
		return codNazionale;
	}
	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTipoLayer() {
		return tipoLayer;
	}
	public void setTipoLayer(String tipoLayer) {
		this.tipoLayer = tipoLayer;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getDesLayer() {
		return desLayer;
	}
	public void setDesLayer(String desLayer) {
		this.desLayer = desLayer;
	}
	
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public Integer getFoglio() {
		return foglio;
	}
	public void setFoglio(Integer foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getFlgDownload() {
		return flgDownload;
	}
	public void setFlgDownload(String flgDownload) {
		this.flgDownload = flgDownload;
	}
	
	public String getStatementSql() {
		return statementSql;
	}
	public void setStatementSql(String statementSql) {
		this.statementSql = statementSql;
	}
	public String getNameTable() {
		return nameTable;
	}
	public void setNameTable(String nameTable) {
		this.nameTable = nameTable;
	}
	public String getSdoColumn() {
		return sdoColumn;
	}
	public void setSdoColumn(String sdoColumn) {
		this.sdoColumn = sdoColumn;
	}
	public boolean isCreateIndexSDO() {
		return createIndexSDO;
	}
	public void setCreateIndexSDO(boolean createIndexSDO) {
		this.createIndexSDO = createIndexSDO;
	}
	public boolean isDropTable() {
		return dropTable;
	}
	public void setDropTable(boolean dropTable) {
		this.dropTable = dropTable;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public PgtSqlVisLayer getVisLayer() {
		return visLayer;
	}
	public void setVisLayer(PgtSqlVisLayer visLayer) {
		this.visLayer = visLayer;
	}
	
	public List<Object> getListaParams() {
		return listaParams;
	}
	public void setListaParams(List<Object> listaParams) {
		this.listaParams = listaParams;
	}
	public void addParam(Object o){
		this.listaParams.add(o);
	}

}
