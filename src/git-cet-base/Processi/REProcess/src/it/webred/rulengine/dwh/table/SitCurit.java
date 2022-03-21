package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.math.BigDecimal;

public class SitCurit extends TabellaDwhMultiProv
{
	
	private String identificativoImpianto;
	private BigDecimal generatoriNumero;
	private BigDecimal potenzaImpiantoRisc;
	private BigDecimal potenzaImpiantoAcs;
	private BigDecimal potenzaImpiantoRaff;
	private String ubicazioneToponimo;
	private String ubicazioneIndirizzo;
	private String ubicazioneCivico;
	private String ubicazioneComune;
	private String ubicazioneProvincia;
	private String ubicazioneCap;
	private String ubicazioneCodiceIstat;
	private String catastoSezione;
	private String catastoFoglio;
	private String catastoParticella;
	private String catastoSubalterno;
	private String edificioCategoria;
	private BigDecimal volumetriaRisc;
	private BigDecimal volumetriaRaff;
	private String regolazione;
	private String contabilizzazione;
	private String emissione;
	private String apePresenza;
	private String apeCodice;
	private String generatoreCategoria;
	private String generatoreProgressivo;
	private BigDecimal generatorePotenza;
	private String generatoreServizi;
	private String generatoreFabbricante;
	private String generatoreTipologia;
	private String generatoreCombustibile;
	private DataDwh generatoreDataInst;
	private BigDecimal generatoreAnnoInst;
	private String generatoreTecnologia;
	private BigDecimal generatoreRendNom;
	private DataDwh rapportoDiControlloData;
	private BigDecimal rapportoDiControlloAnno;
	private String rapDiControlloEsito;
	private BigDecimal rapDiControlloRendimento;
	private String rapDiControlloBacharach;
	private String ispezioneAutComp;
	private DataDwh ispezioneData;
	private String ispezioneEsito;
	private DataDwh dtFileCsv;
	
	@Override
	public String getValueForCtrHash(){
		return identificativoImpianto +
		generatoriNumero +
		potenzaImpiantoRisc +
		potenzaImpiantoAcs +
		potenzaImpiantoRaff +
		ubicazioneToponimo +
		ubicazioneIndirizzo +
		ubicazioneCivico +
		ubicazioneComune +
		ubicazioneProvincia +
		ubicazioneCap +
		ubicazioneCodiceIstat +
		catastoSezione +
		catastoFoglio +
		catastoParticella +
		catastoSubalterno +
		edificioCategoria +
		volumetriaRisc +
		volumetriaRaff +
		regolazione +
		contabilizzazione +
		emissione +
		apePresenza +
		apeCodice +
		generatoreCategoria +
		generatoreProgressivo +
		generatorePotenza +
		generatoreServizi +
		generatoreFabbricante +
		generatoreTipologia +
		generatoreCombustibile +
		generatoreDataInst +
		generatoreAnnoInst +
		generatoreTecnologia +
		generatoreRendNom +
		rapportoDiControlloData +
		rapportoDiControlloAnno +
		rapDiControlloEsito +
		rapDiControlloRendimento +
		rapDiControlloBacharach +
		ispezioneAutComp +
		ispezioneData +
		ispezioneEsito+
		dtFileCsv;
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

	public DataDwh getGeneratoreDataInst() {
		return generatoreDataInst;
	}

	public void setGeneratoreDataInst(DataDwh generatoreDataInst) {
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

	public DataDwh getRapportoDiControlloData() {
		return rapportoDiControlloData;
	}

	public void setRapportoDiControlloData(DataDwh rapportoDiControlloData) {
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

	public DataDwh getIspezioneData() {
		return ispezioneData;
	}

	public void setIspezioneData(DataDwh ispezioneData) {
		this.ispezioneData = ispezioneData;
	}

	public String getIspezioneEsito() {
		return ispezioneEsito;
	}

	public void setIspezioneEsito(String ispezioneEsito) {
		this.ispezioneEsito = ispezioneEsito;
	}

	public DataDwh getDtFileCsv() {
		return dtFileCsv;
	}

	public void setDtFileCsv(DataDwh dtFileCsv) {
		this.dtFileCsv = dtFileCsv;
	}
	
}
