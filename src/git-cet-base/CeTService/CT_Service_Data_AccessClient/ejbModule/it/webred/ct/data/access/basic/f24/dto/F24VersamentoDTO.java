
package it.webred.ct.data.access.basic.f24.dto;

import it.webred.ct.data.model.f24.SitTF24Versamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class F24VersamentoDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private SitTF24Versamenti versamento;
	private String descTipoTributo;
	private String descTipoCf2;
	private String descTipoImposta;
	private String descTipoEnteRd;
	private String descCodEnteRd;
	
	public void setVersamento(SitTF24Versamenti versamento) {
		this.versamento = versamento;
	}
	
	public String getChiave(){
		return versamento.getId();
	}

	public String getId() {
		return versamento.getId();
	}

	public BigDecimal getAcconto() {
		return versamento.getAcconto();
	}

	public BigDecimal getAnnoRif() {
		return versamento.getAnnoRif();
	}

	public String getCab() {
		return versamento.getCab();
	}

	public String getCf() {
		return versamento.getCf();
	}

	public String getCf2() {
		return versamento.getCf2();
	}

	public String getCodEnteCom() {
		return versamento.getCodEnteCom();
	}

	public String getCodEnteRd() {
		return versamento.getCodEnteRd();
	}

	public String getCodIdCf2() {
		return versamento.getCodIdCf2();
	}

	public String getCodTributo() {
		return versamento.getCodTributo();
	}

	public String getCodValuta() {
		return versamento.getCodValuta();
	}

	public String getComuneStato() {
		return versamento.getComuneStato();
	}

	public String getDenominazione() {
		return versamento.getDenominazione();
	}

	public BigDecimal getDetrazione() {
		return versamento.getDetrazione()!=null?versamento.getDetrazione().divide(new BigDecimal(100)):new BigDecimal(0);
	}

	public Date getDtBonifico() {
		return versamento.getDtBonifico();
	}

	public Date getDtFornitura() {
		return versamento.getDtFornitura();
	}

	public String getDtNascita() {
		return versamento.getDtNascita();
	}

	public Date getDtRipartizione() {
		return versamento.getDtRipartizione();
	}

	public Date getDtRiscossione() {
		return versamento.getDtRiscossione();
	}

	public BigDecimal getFlagErrAr() {
		return versamento.getFlagErrAr();
	}

	public BigDecimal getFlagErrCf() {
		return versamento.getFlagErrCf();
	}

	public BigDecimal getFlagErrCt() {
		return versamento.getFlagErrCt();
	}

	public BigDecimal getFlagErrIciImu() {
		return versamento.getFlagErrIciImu();
	}

	public String getIdExtTestata() {
		return versamento.getIdExtTestata();
	}

	public BigDecimal getImpCredito() {
		return versamento.getImpCredito().divide(new BigDecimal(100));
	}

	public BigDecimal getImpDebito() {
		return versamento.getImpDebito().divide(new BigDecimal(100));
	}

	public String getNome() {
		return versamento.getNome();
	}

	public BigDecimal getNumFabbIciImu() {
		return versamento.getNumFabbIciImu();
	}

	public String getDescTipoTributo() {
		return descTipoTributo;
	}

	public void setDescTipoTributo(String descTipoTributo) {
		this.descTipoTributo = descTipoTributo;
	}

	public String getDescTipoCf2() {
		return descTipoCf2;
	}

	public void setDescTipoCf2(String descTipoCf2) {
		this.descTipoCf2 = descTipoCf2;
	}

	public BigDecimal getProgDelega() {
		return versamento.getProgDelega();
	}

	public BigDecimal getProgFornitura() {
		return versamento.getProgFornitura();
	}

	public BigDecimal getProgRiga() {
		return versamento.getProgRiga();
	}

	public BigDecimal getProgRipartizione() {
		return versamento.getProgRipartizione();
	}

	public String getProvincia() {
		return versamento.getProvincia();
	}

	public BigDecimal getRateazione() {
		return versamento.getRateazione();
	}


	public BigDecimal getRavvedimento() {
		return versamento.getRavvedimento();
	}

	public BigDecimal getSaldo() {
		return versamento.getSaldo();
	}


	public String getSesso() {
		return versamento.getSesso();
	}

	public String getTipoEnteRd() {
		return versamento.getTipoEnteRd();
	}


	public String getTipoImposta() {
		return versamento.getTipoImposta();
	}

	public BigDecimal getVarImmIciImu() {
		return versamento.getVarImmIciImu();
	}

	public String getDescTipoEnteRd() {
		
		String imp = this.getTipoEnteRd();
		if("B".equalsIgnoreCase(imp))
			descTipoEnteRd = "Banca";
		else if("C".equalsIgnoreCase(imp))
			descTipoEnteRd = "Agenzia di riscossione";
		else if("P".equalsIgnoreCase(imp))
			descTipoEnteRd = "Agenzia Postale";
		else if("I".equalsIgnoreCase(imp))
			descTipoEnteRd = "Internet";
		else
			descTipoEnteRd= "";
		
		
		return descTipoEnteRd;
	}

	public void setDescTipoEnteRd(String descTipoEnteRd) {
		this.descTipoEnteRd = descTipoEnteRd;
	}

	public String getDescTipoImposta() {
		
		String imp = this.getTipoImposta();
		if("I".equalsIgnoreCase(imp))
			descTipoImposta = "ICI/IMU";
		else if("O".equalsIgnoreCase(imp))
			descTipoImposta = "TOSAP/COSAP";
		else if("T".equalsIgnoreCase(imp))
			descTipoImposta = "TARSU/TARIFFA";
		else if("S".equalsIgnoreCase(imp))
			descTipoImposta = "Tassa di scopo";
		else if("R".equalsIgnoreCase(imp))
			descTipoImposta = "Contributo/Imposta di soggiorno";
		else if("A".equalsIgnoreCase(imp))
			descTipoImposta = "TARES";
		else if("U".equalsIgnoreCase(imp))
			descTipoImposta = "TASI";
		else
			descTipoImposta= "";
		
		return descTipoImposta;
	}

	public void setDescTipoImposta(String descTipoImposta) {
		this.descTipoImposta = descTipoImposta;
	}

	public String getDescCodEnteRd() {
		
		String imp = this.getTipoEnteRd();
		if("B".equalsIgnoreCase(imp))
			descCodEnteRd = "Cod.ABI";
		else if("C".equalsIgnoreCase(imp))
			descCodEnteRd = "Cod.Ambito";
		else if("I".equalsIgnoreCase(imp) && "99999".equalsIgnoreCase(this.getCodEnteRd()))
			descCodEnteRd = "Codice delega Internet Agenzia delle Entrate con saldo a zero";
		else if("P".equalsIgnoreCase(imp) && "07601".equalsIgnoreCase(this.getCodEnteRd()))
			descCodEnteRd = "Cod.Poste";
		else
			descCodEnteRd= "Cod.";
		
		return descCodEnteRd;
	}

	public void setDescCodEnteRd(String descCodEnteRd) {
		this.descCodEnteRd = descCodEnteRd;
	}

}
