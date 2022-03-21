package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

public class AttivitaProfPdfDTO extends ReportPdfDTO {
	
	private String dtAttivita = EMPTY_VALUE;
	private String eseguitaRichiestaIndagine = EMPTY_VALUE;
	private String numOperatori = EMPTY_VALUE;
	private String oreImpiegateTot = EMPTY_VALUE;
	private String dtProssimaAttDal = EMPTY_VALUE;
	private String dtProssimaAttAl = EMPTY_VALUE;
	private String SUBREPORT_DIR;
	private String analisiPro = EMPTY_VALUE;
	private String macroattivita=EMPTY_VALUE;
	private String microattivita=EMPTY_VALUE;
	
	private JRBeanCollectionDataSource tipoAttivita_1;
	private JRBeanCollectionDataSource tipoAttivita_2;
	private JRBeanCollectionDataSource tipoAttivita_3;
	
	private JRBeanCollectionDataSource lstProblematiche; //SISO-1223
	private JRBeanCollectionDataSource lstInterventi; //SISO-1223
	
	public String getDtAttivita() {
		return dtAttivita;
	}
	public void setDtAttivita(String dtAttivita) {
		this.dtAttivita = dtAttivita;
	}
	public String getEseguitaRichiestaIndagine() {
		return eseguitaRichiestaIndagine;
	}
	public void setEseguitaRichiestaIndagine(String eseguitaRichiestaIndagine) {
		this.eseguitaRichiestaIndagine = eseguitaRichiestaIndagine;
	}
	public String getNumOperatori() {
		return numOperatori;
	}
	public void setNumOperatori(String numOperatori) {
		this.numOperatori = numOperatori;
	}
	public String getOreImpiegateTot() {
		return oreImpiegateTot;
	}
	public void setOreImpiegateTot(String oreImpiegateTot) {
		this.oreImpiegateTot = oreImpiegateTot;
	}
	public String getDtProssimaAttDal() {
		return dtProssimaAttDal;
	}
	public void setDtProssimaAttDal(String dtProssimaAttDal) {
		this.dtProssimaAttDal = dtProssimaAttDal;
	}
	public String getDtProssimaAttAl() {
		return dtProssimaAttAl;
	}
	public void setDtProssimaAttAl(String dtProssimaAttAl) {
		this.dtProssimaAttAl = dtProssimaAttAl;
	}
	public String getSUBREPORT_DIR() {
		return SUBREPORT_DIR;
	}

	public void setSUBREPORT_DIR(String sUBREPORT_DIR) {
		SUBREPORT_DIR = sUBREPORT_DIR;
	}

	public JRBeanCollectionDataSource getTipoAttivita_1() {
		return tipoAttivita_1;
	}
	public void setTipoAttivita_1(JRBeanCollectionDataSource tipoAttivita_1) {
		this.tipoAttivita_1 = tipoAttivita_1;
	}
	public JRBeanCollectionDataSource getTipoAttivita_2() {
		return tipoAttivita_2;
	}
	public void setTipoAttivita_2(JRBeanCollectionDataSource tipoAttivita_2) {
		this.tipoAttivita_2 = tipoAttivita_2;
	}
	public JRBeanCollectionDataSource getTipoAttivita_3() {
		return tipoAttivita_3;
	}
	public void setTipoAttivita_3(JRBeanCollectionDataSource tipoAttivita_3) {
		this.tipoAttivita_3 = tipoAttivita_3;
	}
	public String getAnalisiPro() {
		return analisiPro;
	}
	public void setAnalisiPro(String analisiPro) {
		this.analisiPro = analisiPro;
	}
	public String getMacroattivita() {
		return macroattivita;
	}
	public void setMacroattivita(String macroattivita) {
		this.macroattivita = macroattivita;
	}
	public String getMicroattivita() {
		return microattivita;
	}
	public void setMicroattivita(String microattivita) {
		this.microattivita = microattivita;
	}
	public JRBeanCollectionDataSource getLstProblematiche() {
		return lstProblematiche;
	}
	public void setLstProblematiche(JRBeanCollectionDataSource lstProblematiche) {
		this.lstProblematiche = lstProblematiche;
	}
	public JRBeanCollectionDataSource getLstInterventi() {
		return lstInterventi;
	}
	public void setLstInterventi(JRBeanCollectionDataSource lstInterventi) {
		this.lstInterventi = lstInterventi;
	}
	
}