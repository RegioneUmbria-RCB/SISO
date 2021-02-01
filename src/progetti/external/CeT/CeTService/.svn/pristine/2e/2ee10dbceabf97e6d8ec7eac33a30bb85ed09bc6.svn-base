package it.webred.ct.data.model.pgt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the PGT_SQL_LAYER database table.
 * 
 */
@Entity
@Table(name="PGT_SQL_LAYER")
public class PgtSqlLayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgtSqlLayerPK id;

	@Column(name="DES_LAYER")
	private String desLayer;

	@Column(name="TIPO_LAYER")
	private String tipoLayer;
	
	@Column(name="NAME_COL_SHAPE")
	private String nameColShape;

	@Column(name="SQL_LAYER")
	private String sqlLayer;
	
	@Column(name="NAME_TABLE")
	private String nameTable;
	
	@Column(name="NAME_COL_TEMA")
	private String nameColTema;
	
	@Column(name="NAME_COL_INFO")
	private String nameColInfo;
	
	@Column(name="DESCR_COL_INFO")
	private String descrColInfo;
	
	@Column(name="NAME_COL_TEMA_DESCR")
	private String nameColTemaDescr;
	
	@Column(name="NAME_COL_ID")
	private String nameColId;
	
	@Column(name="SQL_DECO")
	private String sqlDeco;
	
	@Column(name="NOME_FILE")
	private String nomeFile;
	
	private String plencode;

	private String pldecode;
	
	@Column(name="PLDECODE_DESCR")
	private String pldecodeDescr;
	
	@Column(name="SHAPE_TYPE")
	private String shapeType;

	@Column(name="FLG_DOWNLOAD")
	private String flgDownload;
	
	@Column(name="FLG_PUBLISH")
	private String flgPublish;
	
	@Column(name="FLG_HIDE_INFO")
	private String flgHideInfo;
	
	@Column(name="OPACITY")
	private String opacity;
	
	@Column(name="DATA_ACQUISIZIONE")
	private Date dataAcquisizione = null;
	
    public PgtSqlLayer() {
    }

    public String getTipoLayer() {
		return tipoLayer;
	}
	public void setTipoLayer(String tipoLayer) {
		this.tipoLayer = tipoLayer;
	}

	public String getDesLayer() {
		return this.desLayer;
	}

	public void setDesLayer(String desLayer) {
		this.desLayer = desLayer;
	}

	public String getNameColShape() {
		return this.nameColShape;
	}

	public void setNameColShape(String nameColShape) {
		this.nameColShape = nameColShape;
	}

	public String getSqlLayer() {
		return this.sqlLayer;
	}

	public void setSqlLayer(String sqlLayer) {
		if(sqlLayer!=null && !"".equals(sqlLayer))
			this.sqlLayer = sqlLayer;
		else
			this.sqlLayer= "SELECT * FROM "+this.nameTable;
	}

	public String getNameTable() {
		return nameTable;
	}

	public void setNameTable(String nameTable) {
		this.nameTable = nameTable;
	}

	public String getNameColTema() {
		return nameColTema;
	}

	public void setNameColTema(String nameColTema) {
		this.nameColTema = nameColTema;
	}

	public String getNameColId() {
		return nameColId;
	}

	public void setNameColId(String nameColId) {
		this.nameColId = nameColId;
	}

	public String getSqlDeco() {
		return sqlDeco;
	}

	public void setSqlDeco(String sqlDeco) {
		this.sqlDeco = sqlDeco;
	}

	public PgtSqlLayerPK getId() {
		return id;
	}

	public void setId(PgtSqlLayerPK id) {
		this.id = id;
	}

	public String getNameColTemaDescr() {
		return nameColTemaDescr;
	}

	public void setNameColTemaDescr(String nameColTemaDescr) {
		this.nameColTemaDescr = nameColTemaDescr;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getPlencode() {
		return plencode;
	}

	public void setPlencode(String plencode) {
		this.plencode = plencode;
	}

	public String getPldecode() {
		return pldecode;
	}

	public void setPldecode(String pldecode) {
		this.pldecode = pldecode;
	}

	public String getShapeType() {
		return shapeType;
	}

	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}

	public String getPldecodeDescr() {
		return pldecodeDescr;
	}

	public void setPldecodeDescr(String pldecodeDescr) {
		this.pldecodeDescr = pldecodeDescr;
	}

	public String getFlgDownload() {
		return flgDownload;
	}

	public void setFlgDownload(String flgDownload) {
		this.flgDownload = flgDownload;
	}

	public String getFlgPublish() {
		return flgPublish;
	}

	public void setFlgPublish(String flgPublish) {
		this.flgPublish = flgPublish;
	}

	public String getOpacity() {
		return opacity;
	}

	public void setOpacity(String opacity) {
		this.opacity = opacity;
	}

	public String getNameColInfo() {
		return nameColInfo;
	}

	public void setNameColInfo(String nameColInfo) {
		this.nameColInfo = nameColInfo;
	}

	public String getDescrColInfo() {
		return descrColInfo;
	}

	public void setDescrColInfo(String descrColInfo) {
		this.descrColInfo = descrColInfo;
	}

	public String getFlgHideInfo() {
		return flgHideInfo;
	}

	public void setFlgHideInfo(String flgHideInfo) {
		this.flgHideInfo = flgHideInfo;
	}

	public Date getDataAcquisizione() {
		return dataAcquisizione;
	}

	public void setDataAcquisizione(Date dataAcquisizione) {
		this.dataAcquisizione = dataAcquisizione;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}