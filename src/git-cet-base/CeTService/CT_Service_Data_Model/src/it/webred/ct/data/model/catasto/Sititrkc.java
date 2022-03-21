package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITITRKC database table.
 * 
 */
@Entity
public class Sititrkc implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SititrkcPK id;

	private String annotazioni;

	@Column(name="AREA_PART")
	private BigDecimal areaPart;

	@Column(name="ATTO_FIN")
	private BigDecimal attoFin;

	@Column(name="ATTO_INI")
	private BigDecimal attoIni;

	@Column(name="CLASSE_TERRENO")
	private String classeTerreno;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_AGGI")
	private Date dataAggi;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;

	private BigDecimal denominatore;

	@Column(name="FLAG_PROVENIENZA")
	private String flagProvenienza;

	@Column(name="ID_FRAZ")
	private BigDecimal idFraz;

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

	private String istatp;

	private String partita;

	@Column(name="QUAL_CAT")
	private BigDecimal qualCat;

	@Column(name="REDDITO_AGRARIO")
	private BigDecimal redditoAgrario;

	@Column(name="REDDITO_DOMINICALE")
	private BigDecimal redditoDominicale;

	private BigDecimal rendita;

	@Column(name="SIGLA_PROV")
	private String siglaProv;

	private BigDecimal sorgente;

	@Column(name="SUB_UC")
	private String subUc;

	private String utente;

	@Column(name="UTENTE_FINE")
	private String utenteFine;

	private BigDecimal valida;

	@Column(name="VALIDATO_DA_CAT")
	private String validatoDaCat;

	@Column(name="VALIDATO_DA_CAT_FIN")
	private String validatoDaCatFin;
	
	@Transient
	private String descQualita;

    public Sititrkc() {
    }

	public SititrkcPK getId() {
		return this.id;
	}

	public void setId(SititrkcPK id) {
		this.id = id;
	}
	
	public String getAnnotazioni() {
		return this.annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public BigDecimal getAreaPart() {
		return this.areaPart;
	}

	public void setAreaPart(BigDecimal areaPart) {
		this.areaPart = areaPart;
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

	public String getClasseTerreno() {
		return this.classeTerreno;
	}

	public void setClasseTerreno(String classeTerreno) {
		this.classeTerreno = classeTerreno;
	}

	public Date getDataAggi() {
		return this.dataAggi;
	}

	public void setDataAggi(Date dataAggi) {
		this.dataAggi = dataAggi;
	}

	public Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public BigDecimal getDenominatore() {
		return this.denominatore;
	}

	public void setDenominatore(BigDecimal denominatore) {
		this.denominatore = denominatore;
	}

	public String getFlagProvenienza() {
		return this.flagProvenienza;
	}

	public void setFlagProvenienza(String flagProvenienza) {
		this.flagProvenienza = flagProvenienza;
	}

	public BigDecimal getIdFraz() {
		return this.idFraz;
	}

	public void setIdFraz(BigDecimal idFraz) {
		this.idFraz = idFraz;
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

	public String getIstatp() {
		return this.istatp;
	}

	public void setIstatp(String istatp) {
		this.istatp = istatp;
	}

	public String getPartita() {
		return this.partita;
	}

	public void setPartita(String partita) {
		this.partita = partita;
	}

	public BigDecimal getQualCat() {
		return this.qualCat;
	}

	public void setQualCat(BigDecimal qualCat) {
		this.qualCat = qualCat;
	}

	public BigDecimal getRedditoAgrario() {
		return this.redditoAgrario;
	}

	public void setRedditoAgrario(BigDecimal redditoAgrario) {
		this.redditoAgrario = redditoAgrario;
	}

	public BigDecimal getRedditoDominicale() {
		return this.redditoDominicale;
	}

	public void setRedditoDominicale(BigDecimal redditoDominicale) {
		this.redditoDominicale = redditoDominicale;
	}

	public BigDecimal getRendita() {
		return this.rendita;
	}

	public void setRendita(BigDecimal rendita) {
		this.rendita = rendita;
	}

	public String getSiglaProv() {
		return this.siglaProv;
	}

	public void setSiglaProv(String siglaProv) {
		this.siglaProv = siglaProv;
	}

	public BigDecimal getSorgente() {
		return this.sorgente;
	}

	public void setSorgente(BigDecimal sorgente) {
		this.sorgente = sorgente;
	}

	public String getSubUc() {
		return this.subUc;
	}

	public void setSubUc(String subUc) {
		this.subUc = subUc;
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

	public BigDecimal getValida() {
		return this.valida;
	}

	public void setValida(BigDecimal valida) {
		this.valida = valida;
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

	public String getDescQualita() {
		return descQualita;
	}

	public void setDescQualita(String descQualita) {
		this.descQualita = descQualita;
	}

}