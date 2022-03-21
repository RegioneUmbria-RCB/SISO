package it.webred.ct.data.access.basic.diagnostiche.dto;

import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;

public class RicercaDocfaReportSoggDTO extends RicercaSoggettoDTO {

	private static final long serialVersionUID = 1L;

	private String flgPresUiuPreDocfa;
	private String flgPresUiuPostDocfa;
	private String flgPresCivPreDocfa;
	private String flgPresCivPostDocfa;
	private String flgPresUndPreDocfa;
	private String flgPresUndPostDocfa;
	
	private String flgPresFamUiuPreDocfa;
	private String flgPresFamUiuPostDocfa;
	private String flgPresFamCivPreDocfa;
	private String flgPresFamCivPostDocfa;
	private String flgPresFamUndPreDocfa;
	private String flgPresFamUndPostDocfa;
	
	// Soggetto titolare alla data del docfa
	private String flgTitolareDataDocfa;

	// Soggetto titolare nell'anno del docfa, residente all'indirizzo del docfa
	// alla data del docfa
	private String flgResidIndDcfAllaData;

	// Soggetto titolare nell'anno del docfa, residente all'indirizzo catastale,
	// alla data del docfa
	private String flgResidIndCatAllaData;

	private String flgAnomali;


	public String getFlgTitolareDataDocfa() {
		return flgTitolareDataDocfa;
	}

	public void setFlgTitolareDataDocfa(String flgTitolareDataDocfa) {
		this.flgTitolareDataDocfa = flgTitolareDataDocfa;
	}

	public String getFlgResidIndDcfAllaData() {
		return flgResidIndDcfAllaData;
	}

	public void setFlgResidIndDcfAllaData(String flgResidIndDcfAllaData) {
		this.flgResidIndDcfAllaData = flgResidIndDcfAllaData;
	}

	public String getFlgResidIndCatAllaData() {
		return flgResidIndCatAllaData;
	}

	public void setFlgResidIndCatAllaData(String flgResidIndCatAllaData) {
		this.flgResidIndCatAllaData = flgResidIndCatAllaData;
	}

	public String getFlgAnomali() {
		return flgAnomali;
	}

	public void setFlgAnomali(String flgAnomali) {
		this.flgAnomali = flgAnomali;
	}

	public String getFlgPresUiuPreDocfa() {
		return flgPresUiuPreDocfa;
	}

	public void setFlgPresUiuPreDocfa(String flgPresUiuPreDocfa) {
		this.flgPresUiuPreDocfa = flgPresUiuPreDocfa;
	}

	public String getFlgPresUiuPostDocfa() {
		return flgPresUiuPostDocfa;
	}

	public void setFlgPresUiuPostDocfa(String flgPresUiuPostDocfa) {
		this.flgPresUiuPostDocfa = flgPresUiuPostDocfa;
	}

	public String getFlgPresCivPreDocfa() {
		return flgPresCivPreDocfa;
	}

	public void setFlgPresCivPreDocfa(String flgPresCivPreDocfa) {
		this.flgPresCivPreDocfa = flgPresCivPreDocfa;
	}

	public String getFlgPresCivPostDocfa() {
		return flgPresCivPostDocfa;
	}

	public void setFlgPresCivPostDocfa(String flgPresCivPostDocfa) {
		this.flgPresCivPostDocfa = flgPresCivPostDocfa;
	}

	public String getFlgPresUndPreDocfa() {
		return flgPresUndPreDocfa;
	}

	public void setFlgPresUndPreDocfa(String flgPresUndPreDocfa) {
		this.flgPresUndPreDocfa = flgPresUndPreDocfa;
	}

	public String getFlgPresUndPostDocfa() {
		return flgPresUndPostDocfa;
	}

	public void setFlgPresUndPostDocfa(String flgPresUndPostDocfa) {
		this.flgPresUndPostDocfa = flgPresUndPostDocfa;
	}

	public String getFlgPresFamUiuPreDocfa() {
		return flgPresFamUiuPreDocfa;
	}

	public void setFlgPresFamUiuPreDocfa(String flgPresFamUiuPreDocfa) {
		this.flgPresFamUiuPreDocfa = flgPresFamUiuPreDocfa;
	}

	public String getFlgPresFamUiuPostDocfa() {
		return flgPresFamUiuPostDocfa;
	}

	public void setFlgPresFamUiuPostDocfa(String flgPresFamUiuPostDocfa) {
		this.flgPresFamUiuPostDocfa = flgPresFamUiuPostDocfa;
	}

	public String getFlgPresFamCivPreDocfa() {
		return flgPresFamCivPreDocfa;
	}

	public void setFlgPresFamCivPreDocfa(String flgPresFamCivPreDocfa) {
		this.flgPresFamCivPreDocfa = flgPresFamCivPreDocfa;
	}

	public String getFlgPresFamCivPostDocfa() {
		return flgPresFamCivPostDocfa;
	}

	public void setFlgPresFamCivPostDocfa(String flgPresFamCivPostDocfa) {
		this.flgPresFamCivPostDocfa = flgPresFamCivPostDocfa;
	}

	public String getFlgPresFamUndPreDocfa() {
		return flgPresFamUndPreDocfa;
	}

	public void setFlgPresFamUndPreDocfa(String flgPresFamUndPreDocfa) {
		this.flgPresFamUndPreDocfa = flgPresFamUndPreDocfa;
	}

	public String getFlgPresFamUndPostDocfa() {
		return flgPresFamUndPostDocfa;
	}

	public void setFlgPresFamUndPostDocfa(String flgPresFamUndPostDocfa) {
		this.flgPresFamUndPostDocfa = flgPresFamUndPostDocfa;
	}

	public boolean notEmpty() {
		boolean notEmpty = false;
		if ((getCognome() != null && !"".equals(getCognome()))
				|| (getNome() != null && !"".equals(getNome()))
				|| (getParIva() != null && !"".equals(getParIva()))
				|| (getCodFis() != null && !"".equals(getCodFis()))
				|| (getTipoSogg() != null && !"".equals(getTipoSogg()))
				|| (getFlgAnomali() != null && !"".equals(getFlgAnomali()))
				|| (getFlgResidIndCatAllaData() != null && !"".equals(getFlgResidIndCatAllaData()))
				|| (getFlgResidIndDcfAllaData() != null && !"".equals(getFlgResidIndDcfAllaData()))
				|| (getFlgTitolareDataDocfa() != null && !"".equals(getFlgTitolareDataDocfa()))
				|| (getFlgPresUiuPreDocfa() != null && !"".equals(getFlgPresUiuPreDocfa()))
				|| (getFlgPresUiuPostDocfa() != null && !"".equals(getFlgPresUiuPostDocfa()))
				|| (getFlgPresCivPreDocfa() != null && !"".equals(getFlgPresCivPreDocfa()))
				|| (getFlgPresCivPostDocfa() != null && !"".equals(getFlgPresCivPostDocfa()))
				|| (getFlgPresUndPreDocfa() != null && !"".equals(getFlgPresUndPreDocfa()))
				|| (getFlgPresUndPostDocfa() != null && !"".equals(getFlgPresUndPostDocfa()))
				|| (getFlgPresFamUiuPreDocfa() != null && !"".equals(getFlgPresFamUiuPreDocfa()))
				|| (getFlgPresFamUiuPostDocfa() != null && !"".equals(getFlgPresFamUiuPostDocfa()))
				|| (getFlgPresFamCivPreDocfa() != null && !"".equals(getFlgPresFamCivPreDocfa()))
				|| (getFlgPresFamCivPostDocfa() != null && !"".equals(getFlgPresFamCivPostDocfa()))
				|| (getFlgPresFamUndPreDocfa() != null && !"".equals(getFlgPresFamUndPreDocfa()))
				|| (getFlgPresFamUndPostDocfa() != null && !"".equals(getFlgPresFamUndPostDocfa())))
			notEmpty = true;

		return notEmpty;
	}

}
