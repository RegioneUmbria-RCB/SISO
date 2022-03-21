package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;
import it.webred.cs.json.familiariConviventi.DatiSocialiFamiliariConviventiPdfDTO;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DatiSocialiPdfDTO extends StoricoPdfDTO {

	private String SUBREPORT_DIR;
	private String tipologiaFamiliare = EMPTY_VALUE;
	private String minoriPresenti = EMPTY_VALUE;
	private String problematica = EMPTY_VALUE;
	private String problematicaNucleo = EMPTY_VALUE;
	private String titoloStudio = EMPTY_VALUE;

	private DatiSocialiAbitazionePdfDTO datiSocialiAbitazionePdfDTO;
	private DatiSocialiFamiliariConviventiPdfDTO datiSocialiFamiliariConviventiPdfDTO;
	
	private JRBeanCollectionDataSource stranieri;


	public String getTipologiaFamiliare() {
		return tipologiaFamiliare;
	}



	public void setTipologiaFamiliare(String tipologiaFamiliare) {
		this.tipologiaFamiliare = format(tipologiaFamiliare);
	}



	public String getMinoriPresenti() {
		return minoriPresenti;
	}



	public void setMinoriPresenti(String minoriPresenti) {
		this.minoriPresenti = format(minoriPresenti);
	}



	public String getProblematica() {
		return problematica;
	}



	public void setProblematica(String problematica) {
		this.problematica = format(problematica);
	}



	public String getTitoloStudio() {
		return titoloStudio;
	}



	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = format(titoloStudio);
	}



	public String getProblematicaNucleo() {
		return problematicaNucleo;
	}



	public void setProblematicaNucleo(String problematicaNucleo) {
		this.problematicaNucleo = problematicaNucleo;
	}



	public DatiSocialiAbitazionePdfDTO getDatiSocialiAbitazionePdfDTO() {
		return datiSocialiAbitazionePdfDTO;
	}



	public void setDatiSocialiAbitazionePdfDTO(DatiSocialiAbitazionePdfDTO datiSocialiAbitazionePdfDTO) {
		this.datiSocialiAbitazionePdfDTO = datiSocialiAbitazionePdfDTO;
	}



	public DatiSocialiFamiliariConviventiPdfDTO getDatiSocialiFamiliariConviventiPdfDTO() {
		return datiSocialiFamiliariConviventiPdfDTO;
	}



	public void setDatiSocialiFamiliariConviventiPdfDTO(
			DatiSocialiFamiliariConviventiPdfDTO datiSocialiFamiliariConviventiPdfDTO) {
		this.datiSocialiFamiliariConviventiPdfDTO = datiSocialiFamiliariConviventiPdfDTO;
	}


	public String getSUBREPORT_DIR() {
		return SUBREPORT_DIR;
	}



	public void setSUBREPORT_DIR(String sUBREPORT_DIR) {
		SUBREPORT_DIR = sUBREPORT_DIR;
	}



	public JRBeanCollectionDataSource getStranieri() {
		return stranieri;
	}



	public void setStranieri(JRBeanCollectionDataSource stranieri) {
		this.stranieri = stranieri;
	}


}
