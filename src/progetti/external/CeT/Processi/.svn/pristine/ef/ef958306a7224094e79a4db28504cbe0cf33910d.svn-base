package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class SitRuoloTares extends TabellaDwh{

	private BigDecimal cu;
	private String anno;
	private String tipo;
	
	//Dati contribuente
	private String nominativoContrib;
	private String cognomeRagsoc;
	private String nome;
	private String codfisc;
	private String sesso;
	private String comuneNasc;
	private DataDwh dataNasc;
	private String provNasc;
	
	private String indirizzo;
	private String cap;
	private String comune;
	private String prov;
	private String estero;
	
	private Integer nimm;
	
	public void setDatiContribuente(String nomeCompleto, String cognome, String nome, String cf, String sesso, String comuNasc, 
			DataDwh dataNasc, String provNasc, 
			String indirizzo, String cap, String comune, String prov, String estero){
		this.nominativoContrib=nomeCompleto;
		this.cognomeRagsoc=cognome;
		this.nome=nome;
		this.codfisc=cf;
		this.sesso=sesso;
		this.comuneNasc= comuNasc;
		this.provNasc=provNasc;
		this.dataNasc=dataNasc;
		this.indirizzo=indirizzo;
		this.cap=cap;
		this.comune=comune;
		this.prov=prov;
		this.estero=estero;
	}
	
	//Dati Pagamento
	private BigDecimal interessi;
	private BigDecimal sanzione;
	private BigDecimal importoNotifica;
	private DataDwh dataNotifica;
	private DataDwh dataAcc;
	private String numProvv;
	private String flagAdesione;
	private String numFatt;
	private DataDwh dataFatt;
	
	private BigDecimal totNetto;
	private BigDecimal percIva;
	private BigDecimal addIva;
	private BigDecimal percTribProv;
	private BigDecimal tribProv;
	private BigDecimal totale;
	private BigDecimal totaleEnte;
	private BigDecimal totaleStato;

	private BigDecimal tribNettoComune;
	private BigDecimal impNettoAcconto;
	private BigDecimal differenzaNetto;
	private BigDecimal tribProvinciale;
	private BigDecimal differenzaLordo;
	private BigDecimal attPro;
	private BigDecimal attComune;
	private BigDecimal oldPro;
	private BigDecimal oldTot;

	private String trQuotaTia;
	private String sgrAnno;
	private String supAnnoPrec;
	private String supAnno;

	private String tipoDoc;
	
	@Override
	public String getValueForCtrHash()
	{
		return 
				this.getIdOrig().toString()+this.codfisc + this.anno + this.tipo;
	}

	public BigDecimal getCu() {
		return cu;
	}

	public String getNominativoContrib() {
		return nominativoContrib;
	}

	public void setNominativoContrib(String nominativoContrib) {
		this.nominativoContrib = nominativoContrib;
	}

	public void setCu(BigDecimal cu) {
		this.cu = cu;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCognomeRagsoc() {
		return cognomeRagsoc;
	}

	public void setCognomeRagsoc(String cognomeRagsoc) {
		this.cognomeRagsoc = cognomeRagsoc;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodfisc() {
		return codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getComuneNasc() {
		return comuneNasc;
	}

	public void setComuneNasc(String comuneNasc) {
		this.comuneNasc = comuneNasc;
	}

	public DataDwh getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(DataDwh dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getProvNasc() {
		return provNasc;
	}

	public void setProvNasc(String provNasc) {
		this.provNasc = provNasc;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getEstero() {
		return estero;
	}

	public void setEstero(String estero) {
		this.estero = estero;
	}

	public Integer getNimm() {
		return nimm;
	}

	public void setNimm(Integer nimm) {
		this.nimm = nimm;
	}

	public BigDecimal getInteressi() {
		return interessi;
	}

	public void setInteressi(BigDecimal interessi) {
		this.interessi = interessi;
	}

	public BigDecimal getSanzione() {
		return sanzione;
	}

	public void setSanzione(BigDecimal sanzione) {
		this.sanzione = sanzione;
	}

	public BigDecimal getImportoNotifica() {
		return importoNotifica;
	}

	public void setImportoNotifica(BigDecimal importoNotifica) {
		this.importoNotifica = importoNotifica;
	}

	public DataDwh getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(DataDwh dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public DataDwh getDataAcc() {
		return dataAcc;
	}

	public void setDataAcc(DataDwh dataAcc) {
		this.dataAcc = dataAcc;
	}

	public String getNumProvv() {
		return numProvv;
	}

	public void setNumProvv(String numProvv) {
		this.numProvv = numProvv;
	}

	public String getFlagAdesione() {
		return flagAdesione;
	}

	public void setFlagAdesione(String flagAdesione) {
		this.flagAdesione = flagAdesione;
	}

	public BigDecimal getTotNetto() {
		return totNetto;
	}

	public void setTotNetto(BigDecimal totNetto) {
		this.totNetto = totNetto;
	}

	public BigDecimal getPercIva() {
		return percIva;
	}

	public void setPercIva(BigDecimal percIva) {
		this.percIva = percIva;
	}

	public BigDecimal getAddIva() {
		return addIva;
	}

	public void setAddIva(BigDecimal addIva) {
		this.addIva = addIva;
	}

	public BigDecimal getPercTribProv() {
		return percTribProv;
	}

	public void setPercTribProv(BigDecimal percTribProv) {
		this.percTribProv = percTribProv;
	}

	public BigDecimal getTribProv() {
		return tribProv;
	}

	public void setTribProv(BigDecimal tribProv) {
		this.tribProv = tribProv;
	}

	public BigDecimal getTotale() {
		return totale;
	}

	public void setTotale(BigDecimal totale) {
		this.totale = totale;
	}

	public BigDecimal getTotaleEnte() {
		return totaleEnte;
	}

	public void setTotaleEnte(BigDecimal totaleEnte) {
		this.totaleEnte = totaleEnte;
	}

	public BigDecimal getTotaleStato() {
		return totaleStato;
	}

	public void setTotaleStato(BigDecimal totaleStato) {
		this.totaleStato = totaleStato;
	}

	public BigDecimal getTribNettoComune() {
		return tribNettoComune;
	}

	public void setTribNettoComune(BigDecimal tribNettoComune) {
		this.tribNettoComune = tribNettoComune;
	}

	public BigDecimal getImpNettoAcconto() {
		return impNettoAcconto;
	}

	public void setImpNettoAcconto(BigDecimal impNettoAcconto) {
		this.impNettoAcconto = impNettoAcconto;
	}

	public BigDecimal getDifferenzaNetto() {
		return differenzaNetto;
	}

	public void setDifferenzaNetto(BigDecimal differenzaNetto) {
		this.differenzaNetto = differenzaNetto;
	}

	public BigDecimal getTribProvinciale() {
		return tribProvinciale;
	}

	public void setTribProvinciale(BigDecimal tribProvinciale) {
		this.tribProvinciale = tribProvinciale;
	}

	public BigDecimal getDifferenzaLordo() {
		return differenzaLordo;
	}

	public void setDifferenzaLordo(BigDecimal differenzaLordo) {
		this.differenzaLordo = differenzaLordo;
	}

	public BigDecimal getAttPro() {
		return attPro;
	}

	public void setAttPro(BigDecimal attPro) {
		this.attPro = attPro;
	}

	public BigDecimal getAttComune() {
		return attComune;
	}

	public void setAttComune(BigDecimal attComune) {
		this.attComune = attComune;
	}

	public BigDecimal getOldPro() {
		return oldPro;
	}

	public void setOldPro(BigDecimal oldPro) {
		this.oldPro = oldPro;
	}

	public BigDecimal getOldTot() {
		return oldTot;
	}

	public void setOldTot(BigDecimal oldTot) {
		this.oldTot = oldTot;
	}

	public String getTrQuotaTia() {
		return trQuotaTia;
	}

	public void setTrQuotaTia(String trQuotaTia) {
		this.trQuotaTia = trQuotaTia;
	}

	public String getSgrAnno() {
		return sgrAnno;
	}

	public void setSgrAnno(String sgrAnno) {
		this.sgrAnno = sgrAnno;
	}

	public String getSupAnnoPrec() {
		return supAnnoPrec;
	}

	public void setSupAnnoPrec(String supAnnoPrec) {
		this.supAnnoPrec = supAnnoPrec;
	}

	public String getSupAnno() {
		return supAnno;
	}

	public void setSupAnno(String supAnno) {
		this.supAnno = supAnno;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getNumFatt() {
		return numFatt;
	}

	public void setNumFatt(String numFatt) {
		this.numFatt = numFatt;
	}

	public DataDwh getDataFatt() {
		return dataFatt;
	}

	public void setDataFatt(DataDwh dataFatt) {
		this.dataFatt = dataFatt;
	}
	
}
