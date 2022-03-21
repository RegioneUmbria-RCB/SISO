package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;

public class SitTF24Versamenti extends TabellaDwh {

    private Relazione idExtTestata = new Relazione(SitTF24Testata.class,new ChiaveEsterna());
	
	private DataDwh dtFornitura;
	private BigDecimal progFornitura;
	private DataDwh dtRipartizione;
	private BigDecimal progRipartizione;
	private DataDwh dtBonifico;
	private BigDecimal progDelega;
	private BigDecimal progRiga;
	private String codEnteRd;
	private String tipoEnteRd;
	private String cab;
	private String cf;
	private BigDecimal flagErrCf;
	private DataDwh dtRiscossione;
	private String codEnteCom;
	private String codTributo;
	private BigDecimal flagErrCt;
	private BigDecimal rateazione;
	private BigDecimal annoRif;
	private BigDecimal flagErrAr;
	private String codValuta;
	private BigDecimal impDebito;
	private BigDecimal impCredito;
	private BigDecimal ravvedimento;
	private BigDecimal varImmIciImu;
	private BigDecimal acconto;
	private BigDecimal saldo;
    private BigDecimal numFabbIciImu;
    private BigDecimal flagErrIciImu;	
    private BigDecimal detrazione;
    private String denominazione;
    private String nome;
    private String sesso;
    private String dtNascita;
    private String comuneStato;
    private String provincia;
    private String tipoImposta;
    private String cf2;
    private String codIdCf2;  
    private String idOperazione;
    private String annoMeseRif = "";


	@Override
	public String getValueForCtrHash()
	{
		return this.dtFornitura.getDataFormattata()+this.progFornitura+this.dtRipartizione.getDataFormattata()+this.progRipartizione+this.dtBonifico.getDataFormattata()+
				this.progDelega+this.progRiga+getString(this.codEnteRd)+getString(this.tipoEnteRd)+getString(this.cab)+
				getString(this.cf)+this.flagErrCf+this.dtRiscossione.getDataFormattata()+getString(this.codEnteCom)+getString(this.codTributo)+
				this.flagErrCt+getString(this.rateazione)+getString(this.annoRif)+this.flagErrAr+getString(this.codValuta)+getString(this.impDebito)+
				getString(this.impCredito)+getString(this.ravvedimento)+getString(this.varImmIciImu)+getString(this.acconto)+getString(this.saldo)+
				this.flagErrIciImu+getString(this.detrazione)+getString(this.denominazione)+getString(this.denominazione)+getString(this.nome)+getString(this.sesso)+
				getString(this.dtNascita)+getString(this.comuneStato)+getString(this.provincia)+getString(this.tipoImposta)+
				getString(this.cf2)+getString(this.codIdCf2)+getString(this.idOperazione)+getString(this.annoMeseRif);
	}


	private String getString(Object o){
		return o!=null ? o.toString():"";
	}


	public Relazione getIdExtTestata() {
		return idExtTestata;
	}


	public DataDwh getDtFornitura() {
		return dtFornitura;
	}


	public void setDtFornitura(DataDwh dtFornitura) {
		this.dtFornitura = dtFornitura;
	}


	


	public BigDecimal getProgFornitura() {
		return progFornitura;
	}


	public void setProgFornitura(BigDecimal progFornitura) {
		this.progFornitura = progFornitura;
	}


	public DataDwh getDtRipartizione() {
		return dtRipartizione;
	}


	public void setDtRipartizione(DataDwh dtRipartizione) {
		this.dtRipartizione = dtRipartizione;
	}


	public BigDecimal getProgRipartizione() {
		return progRipartizione;
	}


	public void setProgRipartizione(BigDecimal progRipartizione) {
		this.progRipartizione = progRipartizione;
	}


	public DataDwh getDtBonifico() {
		return dtBonifico;
	}


	public void setDtBonifico(DataDwh dtBonifico) {
		this.dtBonifico = dtBonifico;
	}


	public BigDecimal getProgDelega() {
		return progDelega;
	}


	public void setProgDelega(BigDecimal progDelega) {
		this.progDelega = progDelega;
	}


	public BigDecimal getProgRiga() {
		return progRiga;
	}


	public void setProgRiga(BigDecimal progRiga) {
		this.progRiga = progRiga;
	}


	public String getCodEnteRd() {
		return codEnteRd;
	}


	public void setCodEnteRd(String codEnteRd) {
		this.codEnteRd = codEnteRd;
	}


	public String getTipoEnteRd() {
		return tipoEnteRd;
	}


	public void setTipoEnteRd(String tipoEnteRd) {
		this.tipoEnteRd = tipoEnteRd;
	}


	public String getCab() {
		return cab;
	}


	public void setCab(String cab) {
		this.cab = cab;
	}


	public String getCf() {
		return cf;
	}


	public void setCf(String cf) {
		this.cf = cf;
	}


	public BigDecimal getFlagErrCf() {
		return flagErrCf;
	}


	public void setFlagErrCf(BigDecimal flagErrCf) {
		this.flagErrCf = flagErrCf;
	}


	public DataDwh getDtRiscossione() {
		return dtRiscossione;
	}


	public void setDtRiscossione(DataDwh dtRiscossione) {
		this.dtRiscossione = dtRiscossione;
	}


	public String getCodEnteCom() {
		return codEnteCom;
	}


	public void setCodEnteCom(String codEnteCom) {
		this.codEnteCom = codEnteCom;
	}


	public String getCodTributo() {
		return codTributo;
	}


	public void setCodTributo(String codTributo) {
		this.codTributo = codTributo;
	}


	public BigDecimal getFlagErrCt() {
		return flagErrCt;
	}


	public void setFlagErrCt(BigDecimal flagErrCt) {
		this.flagErrCt = flagErrCt;
	}


	public BigDecimal getRateazione() {
		return rateazione;
	}


	public void setRateazione(BigDecimal rateazione) {
		this.rateazione = rateazione;
	}


	public BigDecimal getAnnoRif() {
		return annoRif;
	}


	public void setAnnoRif(BigDecimal annoRif) {
		this.annoRif = annoRif;
	}


	public BigDecimal getFlagErrAr() {
		return flagErrAr;
	}


	public void setFlagErrAr(BigDecimal flagErrAr) {
		this.flagErrAr = flagErrAr;
	}


	public String getCodValuta() {
		return codValuta;
	}


	public void setCodValuta(String codValuta) {
		this.codValuta = codValuta;
	}


	public BigDecimal getImpDebito() {
		return impDebito;
	}


	public void setImpDebito(BigDecimal impDebito) {
		this.impDebito = impDebito;
	}


	public BigDecimal getImpCredito() {
		return impCredito;
	}


	public void setImpCredito(BigDecimal impCredito) {
		this.impCredito = impCredito;
	}


	public BigDecimal getRavvedimento() {
		return ravvedimento;
	}


	public void setRavvedimento(BigDecimal ravvedimento) {
		this.ravvedimento = ravvedimento;
	}


	public BigDecimal getVarImmIciImu() {
		return varImmIciImu;
	}


	public void setVarImmIciImu(BigDecimal varImmIciImu) {
		this.varImmIciImu = varImmIciImu;
	}


	public BigDecimal getAcconto() {
		return acconto;
	}


	public void setAcconto(BigDecimal acconto) {
		this.acconto = acconto;
	}


	public BigDecimal getSaldo() {
		return saldo;
	}


	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}


	public BigDecimal getNumFabbIciImu() {
		return numFabbIciImu;
	}


	public void setNumFabbIciImu(BigDecimal numFabbIciImu) {
		this.numFabbIciImu = numFabbIciImu;
	}


	public BigDecimal getFlagErrIciImu() {
		return flagErrIciImu;
	}


	public void setFlagErrIciImu(BigDecimal flagErrIciImu) {
		this.flagErrIciImu = flagErrIciImu;
	}


	public BigDecimal getDetrazione() {
		return detrazione;
	}


	public void setDetrazione(BigDecimal detrazione) {
		this.detrazione = detrazione;
	}


	public String getDenominazione() {
		return denominazione;
	}


	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSesso() {
		return sesso;
	}


	public void setSesso(String sesso) {
		this.sesso = sesso;
	}


	public String getDtNascita() {
		return dtNascita;
	}


	public void setDtNascita(String dtNascita) {
		this.dtNascita = dtNascita;
	}


	public String getComuneStato() {
		return comuneStato;
	}


	public void setComuneStato(String comuneStato) {
		this.comuneStato = comuneStato;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getTipoImposta() {
		return tipoImposta;
	}


	public void setTipoImposta(String tipoImposta) {
		this.tipoImposta = tipoImposta;
	}


	public String getCf2() {
		return cf2;
	}


	public void setCf2(String cf2) {
		this.cf2 = cf2;
	}


	public String getCodIdCf2() {
		return codIdCf2;
	}


	public void setCodIdCf2(String codIdCf2) {
		this.codIdCf2 = codIdCf2;
	}


	public void setIdExtTestata(ChiaveEsterna idExtF24Testata) {
		Relazione r = new Relazione(SitTF24Testata.class,idExtF24Testata);
		this.idExtTestata = r;
		
	}


	public String getIdOperazione() {
		return idOperazione;
	}


	public void setIdOperazione(String idOperazione) {
		this.idOperazione = idOperazione;
	}


	public void setIdExtTestata(Relazione idExtTestata) {
		this.idExtTestata = idExtTestata;
	}


	public String getAnnoMeseRif() {
		return annoMeseRif;
	}


	public void setAnnoMeseRif(String annoMeseRif) {
		this.annoMeseRif = annoMeseRif;
	}
	
	
}
