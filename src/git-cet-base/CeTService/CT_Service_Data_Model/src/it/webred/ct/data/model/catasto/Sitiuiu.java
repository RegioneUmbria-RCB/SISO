package it.webred.ct.data.model.catasto;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITIUIU database table.
 * 
 */
@Entity
@IndiceEntity(sorgente="4")
public class Sitiuiu implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitiuiuPK id;
	
	@IndiceKey(pos="1")
    @Column(name="PKID_UIU",insertable=false,updatable=false)
	private BigDecimal pkidUiu;
		
	private String annotazioni;

	@Column(name="ATTO_FIN")
	private BigDecimal attoFin;

	@Column(name="ATTO_INI")
	private BigDecimal attoIni;

	private String categoria;

	private String classe;

	@Column(name="COD_NAZIONALE_URBA")
	private String codNazionaleUrba;

	private BigDecimal consistenza;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

   /*  
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_LAV")
	private Date dataLav;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_ORIG")
	private Date dtFineOrig;

   @Temporal( TemporalType.DATE)
	@Column(name="DT_TOM")
	private Date dtTom;*/

	private String edificio;

	@Column(name="FLAG_PROVENIENZA")
	private String flagProvenienza;

	@Column(name="FOGLIO_URBA")
	private BigDecimal foglioUrba;

	@Column(name="ID_TRASF")
	private BigDecimal idTrasf;

	@Column(name="ID_TRASF_FINE")
	private BigDecimal idTrasfFine;

	@Column(name="IDE_IMMO")
	private BigDecimal ideImmo;

	@Column(name="IDE_MUTA_FINE")
	private BigDecimal ideMutaFine;

	@Column(name="IDE_MUTA_INI")
	private BigDecimal ideMutaIni;

	private String interno;

	private String microzona;

	@Column(name="MILLESIMI_TERRENO")
	private BigDecimal millesimiTerreno;

	private String note;

	@Column(name="PARTICELLA_URBA")
	private String particellaUrba;

	private String partita;

	private String piano;

	private BigDecimal rendita;

	/*@Column(name="RENDITA_ORIG")
	private BigDecimal renditaOrig;*/

	private String scala;

	private BigDecimal sorgente;

	@Column(name="SUB_URBA")
	private String subUrba;

	@Column(name="SUP_CAT")
	private BigDecimal supCat;

	@Column(name="SUPE_MODELLI")
	private BigDecimal supeModelli;

	private String utente;

	@Column(name="UTENTE_FINE")
	private String utenteFine;

	@Column(name="VALIDATO_DA_CAT")
	private String validatoDaCat;

	@Column(name="VALIDATO_DA_CAT_FIN")
	private String validatoDaCatFin;

	private String zona;

    public Sitiuiu() {
    }

    public SitiuiuPK getId() {
		return this.id;
	}

	public void setId(SitiuiuPK id) {
		this.id = id;
	}
	 
	public String getAnnotazioni() {
		return this.annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public BigDecimal getAttoFin() {
		return this.attoFin;
	}

	public void setAttoFin(BigDecimal attoFin) {
		this.attoFin = attoFin;
	}

	public BigDecimal getAttoIni() {
		return this.attoIni;
	}

	public void setAttoIni(BigDecimal attoIni) {
		this.attoIni = attoIni;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getClasse() {
		return this.classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getCodNazionaleUrba() {
		return this.codNazionaleUrba;
	}

	public void setCodNazionaleUrba(String codNazionaleUrba) {
		this.codNazionaleUrba = codNazionaleUrba;
	}

	public BigDecimal getConsistenza() {
		return this.consistenza;
	}

	public void setConsistenza(BigDecimal consistenza) {
		this.consistenza = consistenza;
	}

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}
	/*
	public Date getDataLav() {
		return this.dataLav;
	}

	public void setDataLav(Date dataLav) {
		this.dataLav = dataLav;
	}

	public Date getDtFineOrig() {
		return this.dtFineOrig;
	}

	public void setDtFineOrig(Date dtFineOrig) {
		this.dtFineOrig = dtFineOrig;
	}

	public Date getDtTom() {
		return this.dtTom;
	}

	public void setDtTom(Date dtTom) {
		this.dtTom = dtTom;
	}*/

	public String getEdificio() {
		return this.edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public String getFlagProvenienza() {
		return this.flagProvenienza;
	}

	public void setFlagProvenienza(String flagProvenienza) {
		this.flagProvenienza = flagProvenienza;
	}

	public BigDecimal getFoglioUrba() {
		return this.foglioUrba;
	}

	public void setFoglioUrba(BigDecimal foglioUrba) {
		this.foglioUrba = foglioUrba;
	}

	public BigDecimal getIdTrasf() {
		return this.idTrasf;
	}

	public void setIdTrasf(BigDecimal idTrasf) {
		this.idTrasf = idTrasf;
	}

	public BigDecimal getIdTrasfFine() {
		return this.idTrasfFine;
	}

	public void setIdTrasfFine(BigDecimal idTrasfFine) {
		this.idTrasfFine = idTrasfFine;
	}

	public BigDecimal getIdeImmo() {
		return this.ideImmo;
	}

	public void setIdeImmo(BigDecimal ideImmo) {
		this.ideImmo = ideImmo;
	}

	public BigDecimal getIdeMutaFine() {
		return this.ideMutaFine;
	}

	public void setIdeMutaFine(BigDecimal ideMutaFine) {
		this.ideMutaFine = ideMutaFine;
	}

	public BigDecimal getIdeMutaIni() {
		return this.ideMutaIni;
	}

	public void setIdeMutaIni(BigDecimal ideMutaIni) {
		this.ideMutaIni = ideMutaIni;
	}

	public String getInterno() {
		return this.interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getMicrozona() {
		return this.microzona;
	}

	public void setMicrozona(String microzona) {
		this.microzona = microzona;
	}

	public BigDecimal getMillesimiTerreno() {
		return this.millesimiTerreno;
	}

	public void setMillesimiTerreno(BigDecimal millesimiTerreno) {
		this.millesimiTerreno = millesimiTerreno;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getParticellaUrba() {
		return this.particellaUrba;
	}

	public void setParticellaUrba(String particellaUrba) {
		this.particellaUrba = particellaUrba;
	}

	public String getPartita() {
		return this.partita;
	}

	public void setPartita(String partita) {
		this.partita = partita;
	}

	public String getPiano() {
		return this.piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public BigDecimal getRendita() {
		return this.rendita;
	}

	public void setRendita(BigDecimal rendita) {
		this.rendita = rendita;
	}

	/*public BigDecimal getRenditaOrig() {
		return this.renditaOrig;
	}

	public void setRenditaOrig(BigDecimal renditaOrig) {
		this.renditaOrig = renditaOrig;
	}*/

	public String getScala() {
		return this.scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public BigDecimal getSorgente() {
		return this.sorgente;
	}

	public void setSorgente(BigDecimal sorgente) {
		this.sorgente = sorgente;
	}

	public String getSubUrba() {
		return this.subUrba;
	}

	public void setSubUrba(String subUrba) {
		this.subUrba = subUrba;
	}

	public BigDecimal getSupCat() {
		return this.supCat;
	}

	public void setSupCat(BigDecimal supCat) {
		this.supCat = supCat;
	}

	public BigDecimal getSupeModelli() {
		return this.supeModelli;
	}

	public void setSupeModelli(BigDecimal supeModelli) {
		this.supeModelli = supeModelli;
	}

	public String getUtente() {
		return this.utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getUtenteFine() {
		return this.utenteFine;
	}

	public void setUtenteFine(String utenteFine) {
		this.utenteFine = utenteFine;
	}

	public String getValidatoDaCat() {
		return this.validatoDaCat;
	}

	public void setValidatoDaCat(String validatoDaCat) {
		this.validatoDaCat = validatoDaCat;
	}

	public String getValidatoDaCatFin() {
		return this.validatoDaCatFin;
	}

	public void setValidatoDaCatFin(String validatoDaCatFin) {
		this.validatoDaCatFin = validatoDaCatFin;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public void setPkidUiu(BigDecimal pkidUiu) {
		this.id.setPkidUiu(pkidUiu);
	}
	public BigDecimal getPkidUiu() {
		return this.id.getPkidUiu();
	}
		
}