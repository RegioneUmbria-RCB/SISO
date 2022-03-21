package it.webred.ct.data.model.omi;

import it.webred.ct.data.spatial.JGeometryType;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="SHP_ZONE_OMI")
public class ShpZoneOmi implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="NAME_1")
	private String name1 = "";
	
	@Column(name="DESCRIPTIO")
	private String descriptio = "";
	
	@Column(name="LINK_ZONA")
	private String linkZona = "";
	
	@Column(name="CODCOM")
	private String codCom = "";
	
	@Column(name="CODZONA")
	private String codZona = "";
	
	@Column(name="ZONA_OMI")
	private String zonaOmi = "";
	
	@Column(name="ANNO_SEM")
	private String annoSem = "";	
	
	@Column(name="COD_NAZ")
	private String codNaz = "";
	
	@Column(name="FIELD_1")
	private String field1 = "";
	
	@Column(name="PK_ID")
	private BigDecimal pkId = null;

	private JGeometryType shape;
	
	@Transient
	private String chiave = "";
	
	private DatiZoneOmi datiZoneOmi;
	
	private DatiZoneOmiZone datiZoneOmiZone;
	
	public ShpZoneOmi() {
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getDescriptio() {
		return descriptio;
	}

	public void setDescriptio(String descriptio) {
		this.descriptio = descriptio;
	}

	public String getLinkZona() {
		return linkZona;
	}

	public void setLinkZona(String linkZona) {
		this.linkZona = linkZona;
	}

	public String getCodCom() {
		return codCom;
	}

	public void setCodCom(String codCom) {
		this.codCom = codCom;
	}

	public String getCodZona() {
		return codZona;
	}

	public void setCodZona(String codZona) {
		this.codZona = codZona;
	}

	public String getZonaOmi() {
		return zonaOmi;
	}

	public void setZonaOmi(String zonaOmi) {
		this.zonaOmi = zonaOmi;
	}

	public String getCodNaz() {
		return codNaz;
	}

	public void setCodNaz(String codNaz) {
		this.codNaz = codNaz;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public BigDecimal getPkId() {
		return pkId;
	}

	public void setPkId(BigDecimal pkId) {
		this.pkId = pkId;
	}

	public JGeometryType getShape() {
		return shape;
	}

	public void setShape(JGeometryType shape) {
		this.shape = shape;
	}

	public String getAnnoSem() {
		return annoSem;
	}

	public void setAnnoSem(String annoSem) {
		this.annoSem = annoSem;
	}

	public String getChiave() {
		return this.getAnnoSem() + "|" + this.getZonaOmi();
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public DatiZoneOmi getDatiZoneOmi() {
		if (datiZoneOmi == null) datiZoneOmi = new DatiZoneOmi();
		return datiZoneOmi;
	}

	public void setDatiZoneOmi(DatiZoneOmi datiZoneOmi) {
		this.datiZoneOmi = datiZoneOmi;
	}

	public DatiZoneOmiZone getDatiZoneOmiZone() {
		if (datiZoneOmiZone == null) datiZoneOmiZone = new DatiZoneOmiZone();
		return datiZoneOmiZone;
	}

	public void setDatiZoneOmiZone(DatiZoneOmiZone datiZoneOmiZone) {
		this.datiZoneOmiZone = datiZoneOmiZone;
	}
	
}

