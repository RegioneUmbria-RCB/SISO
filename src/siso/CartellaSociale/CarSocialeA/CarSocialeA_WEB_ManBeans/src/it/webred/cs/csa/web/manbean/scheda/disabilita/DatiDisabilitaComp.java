package it.webred.cs.csa.web.manbean.scheda.disabilita;

import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaCompUtils;
import it.webred.cs.data.DataModelCostanti.AmParameterKey;
import it.webred.cs.data.model.CsTbDisabTipologia;
import it.webred.cs.jsf.interfaces.IDatiDisabilita;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.model.SelectItem;

public class DatiDisabilitaComp extends SchedaValiditaCompUtils implements IDatiDisabilita{

	private boolean PDF;
	private boolean DF;
	private boolean consulenzaCOP;
	private boolean nisValutazione;
	private boolean nisConsulenza;
	private boolean nisOrientamento;
	private String anno;
	private String certificatore;
	private String terapie;
	
	private BigDecimal idGravita;
	private BigDecimal idTipologia;
	private BigDecimal idEnte;
	
	private List<SelectItem> lstGravita;
	private List<SelectItem> lstTipologia;
	private List<CsTbDisabTipologia> lstCsTbDisabTipologia;
	private List<SelectItem> lstEnte;
	
	@Override
	public void reset() {
		
		super.reset();
		PDF = false;
		DF = false;
		consulenzaCOP = false;
		nisValutazione = false;
		nisConsulenza = false;
		nisOrientamento = false;
		anno = "";
		certificatore = "";
		terapie = "";
		idGravita = null;
		idTipologia = null;
		idEnte = null;
		
	}
	
	@Override
	public boolean isPDF() {
		return PDF;
	}

	public void setPDF(boolean pDF) {
		PDF = pDF;
	}

	@Override
	public boolean isDF() {
		return DF;
	}

	public void setDF(boolean dF) {
		DF = dF;
	}

	@Override
	public boolean isConsulenzaCOP() {
		return consulenzaCOP;
	}

	public void setConsulenzaCOP(boolean consulenzaCOP) {
		this.consulenzaCOP = consulenzaCOP;
	}

	@Override
	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	@Override
	public String getCertificatore() {
		return certificatore;
	}

	public void setCertificatore(String certificatore) {
		this.certificatore = certificatore;
	}

	@Override
	public String getTerapie() {
		return terapie;
	}

	public void setTerapie(String terapie) {
		this.terapie = terapie;
	}

	@Override
	public BigDecimal getIdGravita() {
		return idGravita;
	}

	public void setIdGravita(BigDecimal idGravita) {
		this.idGravita = idGravita;
	}

	@Override
	public BigDecimal getIdTipologia() {
		return idTipologia;
	}

	public void setIdTipologia(BigDecimal idTipologia) {
		this.idTipologia = idTipologia;
	}

	@Override
	public BigDecimal getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(BigDecimal idEnte) {
		this.idEnte = idEnte;
	}

	@Override
	public boolean isNisValutazione() {
		return nisValutazione;
	}

	public void setNisValutazione(boolean nisValutazione) {
		this.nisValutazione = nisValutazione;
	}

	@Override
	public boolean isNisConsulenza() {
		return nisConsulenza;
	}

	public void setNisConsulenza(boolean nisConsulenza) {
		this.nisConsulenza = nisConsulenza;
	}

	@Override
	public boolean isNisOrientamento() {
		return nisOrientamento;
	}

	public void setNisOrientamento(boolean nisOrientamento) {
		this.nisOrientamento = nisOrientamento;
	}

	@Override
	public List<SelectItem> getLstTipologia() {
		return lstTipologia;
	}

	public void setLstTipologia(List<SelectItem> lstTipologia) {
		this.lstTipologia = lstTipologia;
	}

	@Override
	public List<SelectItem> getLstEnte() {
		return lstEnte;
	}

	public void setLstEnte(List<SelectItem> lstEnte) {
		this.lstEnte = lstEnte;
	}

	@Override
	public List<SelectItem> getLstGravita() {
		return lstGravita;
	}
	
	public void setLstGravita(List<SelectItem> lstGravita) {
		this.lstGravita = lstGravita;
	}

	@Override
	public List<CsTbDisabTipologia> getLstCsTbDisabTipologia() {
		return lstCsTbDisabTipologia;
	}
	
	@Override
	public String getLabelNIS(){
		return getLabelTabDisabili(AmParameterKey.suffix_NIS);
	}
	
	@Override
	public String getLabelPDF(){
		return getLabelTabDisabili(AmParameterKey.suffix_PDF);
	}
	
	@Override
	public String getLabelDF(){
		return getLabelTabDisabili(AmParameterKey.suffix_DF);
	}
	
	@Override
	public String getLabelCOP(){
		return getLabelTabDisabili(AmParameterKey.suffix_COP);
	}

	@Override
	public boolean isRenderNIS() {
		return isRenderLabelTabDisabili(AmParameterKey.suffix_NIS);
	}

	@Override
	public boolean isRenderPDF() {
		return isRenderLabelTabDisabili(AmParameterKey.suffix_PDF);
	}

	@Override
	public boolean isRenderDF() {
		return isRenderLabelTabDisabili(AmParameterKey.suffix_DF);
	}

	@Override
	public boolean isRenderCOP() {
		return isRenderLabelTabDisabili(AmParameterKey.suffix_COP);
	}

	public void setLstCsTbDisabTipologia(List<CsTbDisabTipologia> lstCsTbDisabTipologia) {
		this.lstCsTbDisabTipologia = lstCsTbDisabTipologia;
	}
	
	
}
