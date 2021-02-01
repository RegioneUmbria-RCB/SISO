package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class SitRuoloTarsu extends TabellaDwh{

	private Integer cu;
	private String anno;
	private String tipo;
	
	//Dati contribuente
	private String nominativoContrib;
	private String codfisc;
	private String indirizzo;
	private String cap;
	private String comune;
	private String prov;
	private String dom;
	private String estero;
	private String iban;
	
	//Dati Pagamento
	private BigDecimal totNetto;
	private BigDecimal percEca;
	private BigDecimal addEca;
	private BigDecimal percMaggEca;
	private BigDecimal maggEca;
	private BigDecimal percTribProv;
	private BigDecimal tribProv;
	private BigDecimal totale;
	private BigDecimal interessi;
	private BigDecimal sanzione;
	
	private BigDecimal importoNotifica;
	private DataDwh dataNotifica;
	private DataDwh dataAcc;
	private String numProvv;
	
	private String sgrAnnoSucc;
	private String supAnno;
	private String supAnnoSucc;

	private String tipoDoc;
	
	private BigDecimal accontoTarsuAnno;
	

	@Override
	public String getValueForCtrHash()
	{
		return 
				this.getIdOrig()+this.codfisc + this.anno + this.tipo;
	}



	public String getCodfisc() {
		return codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public BigDecimal getAccontoTarsuAnno() {
		return accontoTarsuAnno;
	}

	public void setAccontoTarsuAnno(BigDecimal accontoTarsuAnno) {
		this.accontoTarsuAnno = accontoTarsuAnno;
	}

	public BigDecimal getAddEca() {
		return addEca;
	}

	public void setAddEca(BigDecimal addEca) {
		this.addEca = addEca;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
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

	public String getDom() {
		return dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
	}

	public String getEstero() {
		return estero;
	}

	public void setEstero(String estero) {
		this.estero = estero;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BigDecimal getImportoNotifica() {
		return importoNotifica;
	}

	public void setImportoNotifica(BigDecimal importoNotifica) {
		this.importoNotifica = importoNotifica;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public BigDecimal getInteressi() {
		return interessi;
	}

	public void setInteressi(BigDecimal interessi) {
		this.interessi = interessi;
	}

	public BigDecimal getMaggEca() {
		return maggEca;
	}

	public void setMaggEca(BigDecimal maggEca) {
		this.maggEca = maggEca;
	}

	public String getNominativoContrib() {
		return nominativoContrib;
	}

	public void setNominativoContrib(String nominativoContrib) {
		this.nominativoContrib = nominativoContrib;
	}

	public BigDecimal getPercEca() {
		return percEca;
	}

	public void setPercEca(BigDecimal percEca) {
		this.percEca = percEca;
	}

	public BigDecimal getPercMaggEca() {
		return percMaggEca;
	}

	public void setPercMaggEca(BigDecimal percMaggEca) {
		this.percMaggEca = percMaggEca;
	}

	public BigDecimal getPercTribProv() {
		return percTribProv;
	}

	public void setPercTribProv(BigDecimal percTribProv) {
		this.percTribProv = percTribProv;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public BigDecimal getSanzione() {
		return sanzione;
	}

	public void setSanzione(BigDecimal sanzione) {
		this.sanzione = sanzione;
	}

	public String getSgrAnnoSucc() {
		return sgrAnnoSucc;
	}

	public void setSgrAnnoSucc(String sgrAnnoSucc) {
		this.sgrAnnoSucc = sgrAnnoSucc;
	}

	public String getSupAnno() {
		return supAnno;
	}

	public void setSupAnno(String supAnno) {
		this.supAnno = supAnno;
	}

	public String getSupAnnoSucc() {
		return supAnnoSucc;
	}

	public void setSupAnnoSucc(String supAnnoSucc) {
		this.supAnnoSucc = supAnnoSucc;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public BigDecimal getTotNetto() {
		return totNetto;
	}

	public void setTotNetto(BigDecimal totNetto) {
		this.totNetto = totNetto;
	}

	public BigDecimal getTotale() {
		return totale;
	}

	public void setTotale(BigDecimal totale) {
		this.totale = totale;
	}

	public BigDecimal getTribProv() {
		return tribProv;
	}

	public void setTribProv(BigDecimal tribProv) {
		this.tribProv = tribProv;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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



	public Integer getCu() {
		return cu;
	}



	public void setCu(Integer cu) {
		this.cu = cu;
	}
	
}
