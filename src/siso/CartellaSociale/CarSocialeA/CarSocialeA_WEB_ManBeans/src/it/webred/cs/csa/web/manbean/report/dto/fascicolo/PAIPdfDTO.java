package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

public class PAIPdfDTO extends ReportPdfDTO {
	
	private String tipoProgetto = EMPTY_VALUE;
	private String progetto = EMPTY_VALUE;
	private String dtAttivazione = EMPTY_VALUE;
	private String dtChiusuraPrevista = EMPTY_VALUE;
	private String obiettiviBreveTer = EMPTY_VALUE;
	private String obiettiviMedioTer = EMPTY_VALUE;
	private String SUBREPORT_DIR;
	private String obiettiviLungoTer = EMPTY_VALUE;
	private String raggiuntiBreve = EMPTY_VALUE;
	private String raggiuntiMedio = EMPTY_VALUE;
	private String raggiuntiLungo = EMPTY_VALUE;
	private String verificaOgni = EMPTY_VALUE;
	private String dtUltimoMonitor = EMPTY_VALUE;
	private String motivazioniProgetto = EMPTY_VALUE;
	//private String dtNuovoMonitor = EMPTY_VALUE; al salva diventa la data ultimo monitoraggio
	
	private JRBeanCollectionDataSource attivitaCollegate;
	private JRBeanCollectionDataSource erogazioniPai;
	private JRBeanCollectionDataSource affido;
	
	
	public String getSUBREPORT_DIR() {
		return SUBREPORT_DIR;
	}

	public void setSUBREPORT_DIR(String sUBREPORT_DIR) {
		SUBREPORT_DIR = sUBREPORT_DIR;
	}

	public String getTipoProgetto() {
		return tipoProgetto;
	}

	public void setTipoProgetto(String tipoProgetto) {
		this.tipoProgetto = tipoProgetto;
	}

	public String getProgetto() {
		return progetto;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}

	public String getDtAttivazione() {
		return dtAttivazione;
	}

	public void setDtAttivazione(String dtAttivazione) {
		this.dtAttivazione = dtAttivazione;
	}

	public String getDtChiusuraPrevista() {
		return dtChiusuraPrevista;
	}

	public void setDtChiusuraPrevista(String dtChiusuraPrevista) {
		this.dtChiusuraPrevista = dtChiusuraPrevista;
	}

	public String getObiettiviBreveTer() {
		return obiettiviBreveTer;
	}

	public void setObiettiviBreveTer(String obiettiviBreveTer) {
		this.obiettiviBreveTer = obiettiviBreveTer;
	}

	public String getObiettiviMedioTer() {
		return obiettiviMedioTer;
	}

	public void setObiettiviMedioTer(String obiettiviMedioTer) {
		this.obiettiviMedioTer = obiettiviMedioTer;
	}

	public String getObiettiviLungoTer() {
		return obiettiviLungoTer;
	}

	public void setObiettiviLungoTer(String obiettiviLungoTer) {
		this.obiettiviLungoTer = obiettiviLungoTer;
	}

	public String getRaggiuntiBreve() {
		return raggiuntiBreve;
	}

	public void setRaggiuntiBreve(String raggiuntiBreve) {
		this.raggiuntiBreve = raggiuntiBreve;
	}

	public String getRaggiuntiMedio() {
		return raggiuntiMedio;
	}

	public void setRaggiuntiMedio(String raggiuntiMedio) {
		this.raggiuntiMedio = raggiuntiMedio;
	}

	public String getRaggiuntiLungo() {
		return raggiuntiLungo;
	}

	public void setRaggiuntiLungo(String raggiuntiLungo) {
		this.raggiuntiLungo = raggiuntiLungo;
	}

	public String getVerificaOgni() {
		return verificaOgni;
	}

	public void setVerificaOgni(String verificaOgni) {
		this.verificaOgni = verificaOgni;
	}

	public String getDtUltimoMonitor() {
		return dtUltimoMonitor;
	}

	public void setDtUltimoMonitor(String dtUltimoMonitor) {
		this.dtUltimoMonitor = dtUltimoMonitor;
	}
    
	public String getMotivazioniProgetto() {
		return motivazioniProgetto;
	}

	public void setMotivazioniProgetto(String motivazioniProgetto) {
		this.motivazioniProgetto = motivazioniProgetto;
	}
	
//	public String getDtNuovoMonitor() {
//		return dtNuovoMonitor;
//	}
//
//	public void setDtNuovoMonitor(String dtNuovoMonitor) {
//		this.dtNuovoMonitor = dtNuovoMonitor;
//	}

	public JRBeanCollectionDataSource getAttivitaCollegate() {
		return attivitaCollegate;
	}

	public void setAttivitaCollegate(JRBeanCollectionDataSource attivitaCollegate) {
		this.attivitaCollegate = attivitaCollegate;
	}

	public JRBeanCollectionDataSource getErogazioniPai() {
		return erogazioniPai;
	}

	public void setErogazioniPai(JRBeanCollectionDataSource erogazioniPai) {
		this.erogazioniPai = erogazioniPai;
	}

	public JRBeanCollectionDataSource getAffido() {
		return affido;
	}

	public void setAffido(JRBeanCollectionDataSource affido) {
		this.affido = affido;
	}

}