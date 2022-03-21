package it.webred.ct.data.model.omi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="DATI_ZONE_OMI")
public class DatiZoneOmi implements Serializable {
	
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
	
	@Column(name="ZONA")
	private String zona = "";
	
	@Column(name="LINK_ZONA")
	private String linkZona = "";
	
	@Column(name="COD_TIP")
	private String codTip= "";
	
	@Column(name="DESCR_TIPOLOGIA")
	private String descrTipologia = "";
	
	@Column(name="STATO")
	private String stato = "";
	
	@Column(name="STATO_PREV")
	private String statoPrev = "";
	
	@Column(name="COMPR_MIN")
	private String comprMin = "";
	
	@Column(name="COMPR_MAX")
	private String comprMax = "";
	
	@Column(name="SUP_NL_COMPR")
	private String supNlCompr = "";
	
	@Column(name="LOC_MIN")
	private String locMin = "";
	
	@Column(name="LOC_MAX")
	private String locMax = "";
	
	@Column(name="SUP_NL_LOC")
	private String supNlLoc = "";
	
	@Transient
	private String chiave = "";
	
	private DatiZoneOmiZone datiZoneOmiZone;
	
	private ShpZoneOmi shpZoneOmi;
	
	public DatiZoneOmi() {
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

	public String getCodTip() {
		return codTip;
	}

	public void setCodTip(String codTip) {
		this.codTip = codTip;
	}

	public String getDescrTipologia() {
		return descrTipologia;
	}

	public void setDescrTipologia(String descrTipologia) {
		this.descrTipologia = descrTipologia;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getStatoPrev() {
		return statoPrev;
	}

	public void setStatoPrev(String statoPrev) {
		this.statoPrev = statoPrev;
	}

	public String getComprMin() {
		return comprMin;
	}

	public void setComprMin(String comprMin) {
		this.comprMin = comprMin;
	}

	public String getComprMax() {
		return comprMax;
	}

	public void setComprMax(String comprMax) {
		this.comprMax = comprMax;
	}

	public String getSupNlCompr() {
		return supNlCompr;
	}

	public void setSupNlCompr(String supNlCompr) {
		this.supNlCompr = supNlCompr;
	}

	public String getLocMin() {
		return locMin;
	}

	public void setLocMin(String locMin) {
		this.locMin = locMin;
	}

	public String getLocMax() {
		return locMax;
	}

	public void setLocMax(String locMax) {
		this.locMax = locMax;
	}

	public String getSupNlLoc() {
		return supNlLoc;
	}

	public void setSupNlLoc(String supNlLoc) {
		this.supNlLoc = supNlLoc;
	}

	public String getChiave() {
		return this.getAnno() + "|" + this.getSemestre() + "|" + this.getZona() + "|" + this.getCodTip();
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DatiZoneOmiZone getDatiZoneOmiZone() {
		if (datiZoneOmiZone == null) datiZoneOmiZone = new DatiZoneOmiZone();
		return datiZoneOmiZone;
	}

	public void setDatiZoneOmiZone(DatiZoneOmiZone datiZoneOmiZone) {
		this.datiZoneOmiZone = datiZoneOmiZone;
	}

	public ShpZoneOmi getShpZoneOmi() {
		if (shpZoneOmi == null) shpZoneOmi = new ShpZoneOmi();
		return shpZoneOmi;
	}

	public void setShpZoneOmi(ShpZoneOmi shpZoneOmi) {
		this.shpZoneOmi = shpZoneOmi;
	}
	
}
