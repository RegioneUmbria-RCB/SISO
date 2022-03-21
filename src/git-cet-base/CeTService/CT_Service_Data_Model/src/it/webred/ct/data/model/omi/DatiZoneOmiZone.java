package it.webred.ct.data.model.omi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="DATI_ZONE_OMI_ZONE")
public class DatiZoneOmiZone implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="ANNO")
	private String anno = "";
	
	@Column(name="SEMESTRE")
	private String semestre = "";
	
	@Column(name="AREA_TERRITORIALE")
	private String areaTerritoriale = "";
	
	@Column(name="REGIONE")
	private String regione = "";
	
	@Column(name="PROV")
	private String prov = "";
	
	@Column(name="COMUNE_ISTAT")
	private String comuneIstat = "";
	
	@Column(name="COMUNE_CAT")
	private String comuneCat = "";
	
	@Column(name="SEZ")
	private String sez = "";
	
	@Column(name="COMUNE_AMM")
	private String comuneAmm = "";
	
	@Column(name="COMUNE_DESCRIZIONE")
	private String comuneDescrizione = "";
	
	@Column(name="FASCIA")
	private String fascia = "";
	
	@Column(name="ZONA_DESCR")
	private String zonaDescr = "";
	
	@Column(name="ZONA")
	private String zona = "";
	
	@Column(name="LINK_ZONA")
	private String linkZona = "";
	
	@Column(name="COD_TIP_PREV")
	private String codTipPrev = "";
	
	@Column(name="DESCR_TIP_PREV")
	private String descrTipPrev = "";
	
	@Column(name="STATO_PREV")
	private String statoPrev = "";
	
	@Column(name="MICROZONA")
	private String microzona = "";
	
	@Transient
	private String chiave = "";
	
	private DatiZoneOmi datiZoneOmi;
	
	private ShpZoneOmi shpZoneOmi;
	

    public DatiZoneOmiZone() {
    }
    
   	public String getAnno() {
		return anno;
	}
   	
	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}	
	
	public String getAreaTerritoriale() {
		return areaTerritoriale;
	}

	public void setAreaTerritoriale(String areaTerritoriale) {
		this.areaTerritoriale = areaTerritoriale;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getComuneIstat() {
		return comuneIstat;
	}

	public void setComuneIstat(String comuneIstat) {
		this.comuneIstat = comuneIstat;
	}

	public String getComuneCat() {
		return comuneCat;
	}

	public void setComuneCat(String comuneCat) {
		this.comuneCat = comuneCat;
	}

	public String getSez() {
		return sez;
	}

	public void setSez(String sez) {
		this.sez = sez;
	}

	public String getComuneAmm() {
		return comuneAmm;
	}

	public void setComuneAmm(String comuneAmm) {
		this.comuneAmm = comuneAmm;
	}

	public String getComuneDescrizione() {
		return comuneDescrizione;
	}

	public void setComuneDescrizione(String comuneDescrizione) {
		this.comuneDescrizione = comuneDescrizione;
	}

	public String getFascia() {
		return fascia;
	}

	public void setFascia(String fascia) {
		this.fascia = fascia;
	}

	public String getZonaDescr() {
		return zonaDescr;
	}

	public void setZonaDescr(String zonaDescr) {
		this.zonaDescr = zonaDescr;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getLinkZona() {
		return linkZona;
	}

	public void setLinkZona(String linkZona) {
		this.linkZona = linkZona;
	}

	public String getCodTipPrev() {
		return codTipPrev;
	}

	public void setCodTipPrev(String codTipPrev) {
		this.codTipPrev = codTipPrev;
	}

	public String getDescrTipPrev() {
		return descrTipPrev;
	}

	public void setDescrTipPrev(String descrTipPrev) {
		this.descrTipPrev = descrTipPrev;
	}

	public String getStatoPrev() {
		return statoPrev;
	}

	public void setStatoPrev(String statoPrev) {
		this.statoPrev = statoPrev;
	}

	public String getMicrozona() {
		return microzona;
	}

	public void setMicrozona(String microzona) {
		this.microzona = microzona;
	}

	public String getChiave() {
		return this.getAnno() + "|" + this.getSemestre() + "|" + this.getZona() + "|" + this.getCodTipPrev();
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

	public ShpZoneOmi getShpZoneOmi() {
		if (shpZoneOmi == null) shpZoneOmi = new ShpZoneOmi();
		return shpZoneOmi;
	}

	public void setShpZoneOmi(ShpZoneOmi shpZoneOmi) {
		this.shpZoneOmi = shpZoneOmi;
	}
	
}
