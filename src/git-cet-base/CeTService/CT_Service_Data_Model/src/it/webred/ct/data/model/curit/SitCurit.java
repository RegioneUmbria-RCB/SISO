package it.webred.ct.data.model.curit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="SIT_CURIT")
public class SitCurit  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="ID_EXT")
	private String idExt;
	
	@Column(name="ID_ORIG")
	private String idOrig;
	
	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;	
	
	@Column(name="IDENTIFICATIVO_IMPIANTO")
	private String identificativoImpianto = "";
	
	@Column(name="GENERATORI_NUMERO")
	private BigDecimal generatoriNumero = null;
	
	@Column(name="POTENZA_IMPIANTO_RISC")
	private BigDecimal potenzaImpiantoRisc = null;
	
	@Column(name="POTENZA_IMPIANTO_ACS")
	private BigDecimal potenzaImpiantoAcs = null;
	
	@Column(name="POTENZA_IMPIANTO_RAFF")
	private BigDecimal potenzaImpiantoRaff = null;
	
	@Column(name="UBICAZIONE_TOPONIMO")
	private String ubicazioneToponimo = "";
	
	@Column(name="UBICAZIONE_INDIRIZZO")
	private String ubicazioneIndirizzo = "";
	
	@Column(name="UBICAZIONE_CIVICO")
	private String ubicazioneCivico = "";
	
	@Column(name="UBICAZIONE_COMUNE")
	private String ubicazioneComune = "";
	
	@Column(name="UBICAZIONE_PROVINCIA")
	private String ubicazioneProvincia = "";
	
	@Column(name="UBICAZIONE_CAP")
	private String ubicazioneCap = "";
	
	@Column(name="UBICAZIONE_CODICE_ISTAT")
	private String ubicazioneCodiceIstat = "";
	
	@Column(name="CATASTO_SEZIONE")
	private String catastoSezione = "";
	
	@Column(name="CATASTO_FOGLIO")
	private String catastoFoglio = "";
	
	@Column(name="CATASTO_PARTICELLA")
	private String catastoParticella = "";
	
	@Column(name="CATASTO_SUBALTERNO")
	private String catastoSubalterno = "";
	
	@Column(name="EDIFICIO_CATEGORIA")
	private String edificioCategoria = "";
	
	@Column(name="VOLUMETRIA_RISC")
	private BigDecimal volumetriaRisc = null;
	
	@Column(name="VOLUMETRIA_RAFF")
	private BigDecimal volumetriaRaff = null;
	
	@Column(name="REGOLAZIONE")
	private String regolazione = "";
	
	@Column(name="CONTABILIZZAZIONE")
	private String contabilizzazione = "";
	
	@Column(name="EMISSIONE")
	private String emissione = "";
	
	@Column(name="APE_PRESENZA")
	private String apePresenza = "";
	
	@Column(name="APE_CODICE")
	private String apeCodice = "";
	
	@Column(name="GENERATORE_CATEGORIA")
	private String generatoreCategoria = "";
	
	@Column(name="GENERATORE_PROGRESSIVO")
	private String generatoreProgressivo = "";
	
	@Column(name="GENERATORE_POTENZA")
	private BigDecimal generatorePotenza = null;
	
	@Column(name="GENERATORE_SERVIZI")
	private String generatoreServizi = "";
	
	@Column(name="GENERATORE_FABBRICANTE")
	private String generatoreFabbricante = "";
	
	@Column(name="GENERATORE_TIPOLOGIA")
	private String generatoreTipologia = "";
	
	@Column(name="GENERATORE_COMBUSTIBILE")
	private String generatoreCombustibile = "";
	
	@Temporal(TemporalType.DATE)
	@Column(name="GENERATORE_DATA_INST")
	private Date generatoreDataInst;
	            
	@Column(name="GENERATORE_ANNO_INST")
	private BigDecimal generatoreAnnoInst = null;
	
	@Column(name="GENERATORE_TECNOLOGIA")
	private String generatoreTecnologia = "";
	
	@Column(name="GENERATORE_REND_NOM")
	private BigDecimal generatoreRendNom = null;
	
	@Temporal(TemporalType.DATE)
	@Column(name="RAPPORTO_DI_CONTROLLO_DATA")
	private Date rapportoDiControlloData;  

	@Column(name="RAPPORTO_DI_CONTROLLO_ANNO")
	private BigDecimal rapportoDiControlloAnno = null;
	
	@Column(name="RAP_DI_CONTROLLO_ESITO")
	private String rapDiControlloEsito = "";
	
	@Column(name="RAP_DI_CONTROLLO_RENDIMENTO")
	private BigDecimal rapDiControlloRendimento = null;
	
	@Column(name="RAP_DI_CONTROLLO_BACHARACH")
	private String rapDiControlloBacharach = "";
	
	@Column(name="ISPEZIONE_AUT_COMP")
	private String ispezioneAutComp = "";
	
	@Temporal(TemporalType.DATE)
	@Column(name="ISPEZIONE_DATA")
	private Date ispezioneData;  

	@Column(name="ISPEZIONE_ESITO")
	private String ispezioneEsito = "";
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_FILE_CSV")
	private Date dtFileCsv;
	
	@Column(name="PROVENIENZA")
	private String provenienza = "";
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_VAL")
	private Date dtInizioVal;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@Column(name="CTR_HASH")
	private String ctrHash;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_DATO")
	private Date dtInizioDato;	

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_DATO")
	private Date dtFineDato;

    @Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;
    
    @Column(name="PROCESSID")
    private String processid;
	
	@Transient
	private String chiave = "";
	

    public SitCurit() {
    }//-------------------------------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdExt() {
		return idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getIdOrig() {
		return idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public BigDecimal getFkEnteSorgente() {
		return fkEnteSorgente;
	}

	public void setFkEnteSorgente(BigDecimal fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}

	public String getIdentificativoImpianto() {
		return identificativoImpianto;
	}

	public void setIdentificativoImpianto(String identificativoImpianto) {
		this.identificativoImpianto = identificativoImpianto;
	}

	public BigDecimal getGeneratoriNumero() {
		return generatoriNumero;
	}

	public void setGeneratoriNumero(BigDecimal generatoriNumero) {
		this.generatoriNumero = generatoriNumero;
	}

	public BigDecimal getPotenzaImpiantoRisc() {
		return potenzaImpiantoRisc;
	}

	public void setPotenzaImpiantoRisc(BigDecimal potenzaImpiantoRisc) {
		this.potenzaImpiantoRisc = potenzaImpiantoRisc;
	}

	public BigDecimal getPotenzaImpiantoAcs() {
		return potenzaImpiantoAcs;
	}

	public void setPotenzaImpiantoAcs(BigDecimal potenzaImpiantoAcs) {
		this.potenzaImpiantoAcs = potenzaImpiantoAcs;
	}

	public BigDecimal getPotenzaImpiantoRaff() {
		return potenzaImpiantoRaff;
	}

	public void setPotenzaImpiantoRaff(BigDecimal potenzaImpiantoRaff) {
		this.potenzaImpiantoRaff = potenzaImpiantoRaff;
	}

	public String getUbicazioneToponimo() {
		return ubicazioneToponimo;
	}

	public void setUbicazioneToponimo(String ubicazioneToponimo) {
		this.ubicazioneToponimo = ubicazioneToponimo;
	}

	public String getUbicazioneIndirizzo() {
		return ubicazioneIndirizzo;
	}

	public void setUbicazioneIndirizzo(String ubicazioneIndirizzo) {
		this.ubicazioneIndirizzo = ubicazioneIndirizzo;
	}

	public String getUbicazioneCivico() {
		return ubicazioneCivico;
	}

	public void setUbicazioneCivico(String ubicazioneCivico) {
		this.ubicazioneCivico = ubicazioneCivico;
	}

	public String getUbicazioneComune() {
		return ubicazioneComune;
	}

	public void setUbicazioneComune(String ubicazioneComune) {
		this.ubicazioneComune = ubicazioneComune;
	}

	public String getUbicazioneProvincia() {
		return ubicazioneProvincia;
	}

	public void setUbicazioneProvincia(String ubicazioneProvincia) {
		this.ubicazioneProvincia = ubicazioneProvincia;
	}

	public String getUbicazioneCap() {
		return ubicazioneCap;
	}

	public void setUbicazioneCap(String ubicazioneCap) {
		this.ubicazioneCap = ubicazioneCap;
	}

	public String getUbicazioneCodiceIstat() {
		return ubicazioneCodiceIstat;
	}

	public void setUbicazioneCodiceIstat(String ubicazioneCodiceIstat) {
		this.ubicazioneCodiceIstat = ubicazioneCodiceIstat;
	}

	public String getCatastoSezione() {
		return catastoSezione;
	}

	public void setCatastoSezione(String catastoSezione) {
		this.catastoSezione = catastoSezione;
	}

	public String getCatastoFoglio() {
		return catastoFoglio;
	}

	public void setCatastoFoglio(String catastoFoglio) {
		this.catastoFoglio = catastoFoglio;
	}

	public String getCatastoParticella() {
		return catastoParticella;
	}

	public void setCatastoParticella(String catastoParticella) {
		this.catastoParticella = catastoParticella;
	}

	public String getCatastoSubalterno() {
		return catastoSubalterno;
	}

	public void setCatastoSubalterno(String catastoSubalterno) {
		this.catastoSubalterno = catastoSubalterno;
	}

	public String getEdificioCategoria() {
		return edificioCategoria;
	}

	public void setEdificioCategoria(String edificioCategoria) {
		this.edificioCategoria = edificioCategoria;
	}

	public BigDecimal getVolumetriaRisc() {
		return volumetriaRisc;
	}

	public void setVolumetriaRisc(BigDecimal volumetriaRisc) {
		this.volumetriaRisc = volumetriaRisc;
	}

	public BigDecimal getVolumetriaRaff() {
		return volumetriaRaff;
	}

	public void setVolumetriaRaff(BigDecimal volumetriaRaff) {
		this.volumetriaRaff = volumetriaRaff;
	}

	public String getRegolazione() {
		return regolazione;
	}

	public void setRegolazione(String regolazione) {
		this.regolazione = regolazione;
	}

	public String getContabilizzazione() {
		return contabilizzazione;
	}

	public void setContabilizzazione(String contabilizzazione) {
		this.contabilizzazione = contabilizzazione;
	}

	public String getEmissione() {
		return emissione;
	}

	public void setEmissione(String emissione) {
		this.emissione = emissione;
	}

	public String getApePresenza() {
		return apePresenza;
	}

	public void setApePresenza(String apePresenza) {
		this.apePresenza = apePresenza;
	}

	public String getApeCodice() {
		return apeCodice;
	}

	public void setApeCodice(String apeCodice) {
		this.apeCodice = apeCodice;
	}

	public String getGeneratoreCategoria() {
		return generatoreCategoria;
	}

	public void setGeneratoreCategoria(String generatoreCategoria) {
		this.generatoreCategoria = generatoreCategoria;
	}

	public String getGeneratoreProgressivo() {
		return generatoreProgressivo;
	}

	public void setGeneratoreProgressivo(String generatoreProgressivo) {
		this.generatoreProgressivo = generatoreProgressivo;
	}

	public BigDecimal getGeneratorePotenza() {
		return generatorePotenza;
	}

	public void setGeneratorePotenza(BigDecimal generatorePotenza) {
		this.generatorePotenza = generatorePotenza;
	}

	public String getGeneratoreServizi() {
		return generatoreServizi;
	}

	public void setGeneratoreServizi(String generatoreServizi) {
		this.generatoreServizi = generatoreServizi;
	}

	public String getGeneratoreFabbricante() {
		return generatoreFabbricante;
	}

	public void setGeneratoreFabbricante(String generatoreFabbricante) {
		this.generatoreFabbricante = generatoreFabbricante;
	}

	public String getGeneratoreTipologia() {
		return generatoreTipologia;
	}

	public void setGeneratoreTipologia(String generatoreTipologia) {
		this.generatoreTipologia = generatoreTipologia;
	}

	public String getGeneratoreCombustibile() {
		return generatoreCombustibile;
	}

	public void setGeneratoreCombustibile(String generatoreCombustibile) {
		this.generatoreCombustibile = generatoreCombustibile;
	}

	public Date getGeneratoreDataInst() {
		return generatoreDataInst;
	}

	public void setGeneratoreDataInst(Date generatoreDataInst) {
		this.generatoreDataInst = generatoreDataInst;
	}

	public BigDecimal getGeneratoreAnnoInst() {
		return generatoreAnnoInst;
	}

	public void setGeneratoreAnnoInst(BigDecimal generatoreAnnoInst) {
		this.generatoreAnnoInst = generatoreAnnoInst;
	}

	public String getGeneratoreTecnologia() {
		return generatoreTecnologia;
	}

	public void setGeneratoreTecnologia(String generatoreTecnologia) {
		this.generatoreTecnologia = generatoreTecnologia;
	}

	public BigDecimal getGeneratoreRendNom() {
		return generatoreRendNom;
	}

	public void setGeneratoreRendNom(BigDecimal generatoreRendNom) {
		this.generatoreRendNom = generatoreRendNom;
	}

	public Date getRapportoDiControlloData() {
		return rapportoDiControlloData;
	}

	public void setRapportoDiControlloData(Date rapportoDiControlloData) {
		this.rapportoDiControlloData = rapportoDiControlloData;
	}

	public BigDecimal getRapportoDiControlloAnno() {
		return rapportoDiControlloAnno;
	}

	public void setRapportoDiControlloAnno(BigDecimal rapportoDiControlloAnno) {
		this.rapportoDiControlloAnno = rapportoDiControlloAnno;
	}

	public String getRapDiControlloEsito() {
		return rapDiControlloEsito;
	}

	public void setRapDiControlloEsito(String rapDiControlloEsito) {
		this.rapDiControlloEsito = rapDiControlloEsito;
	}

	public BigDecimal getRapDiControlloRendimento() {
		return rapDiControlloRendimento;
	}

	public void setRapDiControlloRendimento(BigDecimal rapDiControlloRendimento) {
		this.rapDiControlloRendimento = rapDiControlloRendimento;
	}

	public String getRapDiControlloBacharach() {
		return rapDiControlloBacharach;
	}

	public void setRapDiControlloBacharach(String rapDiControlloBacharach) {
		this.rapDiControlloBacharach = rapDiControlloBacharach;
	}

	public String getIspezioneAutComp() {
		return ispezioneAutComp;
	}

	public void setIspezioneAutComp(String ispezioneAutComp) {
		this.ispezioneAutComp = ispezioneAutComp;
	}

	public Date getIspezioneData() {
		return ispezioneData;
	}

	public void setIspezioneData(Date ispezioneData) {
		this.ispezioneData = ispezioneData;
	}

	public String getIspezioneEsito() {
		return ispezioneEsito;
	}

	public void setIspezioneEsito(String ispezioneEsito) {
		this.ispezioneEsito = ispezioneEsito;
	}

	public Date getDtFileCsv() {
		return dtFileCsv;
	}

	public void setDtFileCsv(Date dtFileCsv) {
		this.dtFileCsv = dtFileCsv;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public Date getDtInizioVal() {
		return dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}

	public Date getDtFineVal() {
		return dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public Date getDtExpDato() {
		return dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getCtrHash() {
		return ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDtInizioDato() {
		return dtInizioDato;
	}

	public void setDtInizioDato(Date dtInizioDato) {
		this.dtInizioDato = dtInizioDato;
	}

	public Date getDtFineDato() {
		return dtFineDato;
	}

	public void setDtFineDato(Date dtFineDato) {
		this.dtFineDato = dtFineDato;
	}

	public BigDecimal getFlagDtValDato() {
		return flagDtValDato;
	}

	public void setFlagDtValDato(BigDecimal flagDtValDato) {
		this.flagDtValDato = flagDtValDato;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getChiave() {
		return "" + this.getId() ;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getIndirizzo() {
		String indirizzo = this.getUbicazioneToponimo() == null ? "" : this.getUbicazioneToponimo().trim();
		if (this.getUbicazioneIndirizzo() != null) {
			if (!indirizzo.equals("")) {
				indirizzo += " ";
			}
			indirizzo += this.getUbicazioneIndirizzo().trim();
		}
		if (this.getUbicazioneCivico() != null) {
			if (!indirizzo.equals("")) {
				indirizzo += ", ";
			}
			indirizzo += this.getUbicazioneCivico().trim();
		}
		return indirizzo.equals("") ? "-" : indirizzo;
	}

}
