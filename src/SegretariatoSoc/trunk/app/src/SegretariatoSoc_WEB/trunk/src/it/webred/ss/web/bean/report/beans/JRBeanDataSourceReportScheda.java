package it.webred.ss.web.bean.report.beans;

import it.webred.cs.json.familiariConviventi.DatiSocialiFamiliariConviventiPdfDTO;
import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomBaseBean;

import java.util.ArrayList;
import java.util.List;

public class JRBeanDataSourceReportScheda {

	private DatiSocialiFamiliariConviventiPdfDTO familiariEConviventi = new DatiSocialiFamiliariConviventiPdfDTO();
	private PresenzaInItalia presenzaInItalia = new PresenzaInItalia();
	private List<ServizioRichiestoCustomBaseBean> serviziRichiestiCustomList = new ArrayList<ServizioRichiestoCustomBaseBean>();

	
	public DatiSocialiFamiliariConviventiPdfDTO getFamiliariEConviventi() {
		return familiariEConviventi;
	}



	public void setFamiliariEConviventi(
			DatiSocialiFamiliariConviventiPdfDTO familiariEConviventi) {
		this.familiariEConviventi = familiariEConviventi;
	}



	public PresenzaInItalia getPresenzaInItalia() {
		return presenzaInItalia;
	}



	public void setPresenzaInItalia(PresenzaInItalia presenzaInItalia) {
		this.presenzaInItalia = presenzaInItalia;
	}



	public List<ServizioRichiestoCustomBaseBean> getServiziRichiestiCustomList() {
		return serviziRichiestiCustomList;
	}



	public void setServiziRichiestiCustomList(
			List<ServizioRichiestoCustomBaseBean> serviziRichiestiCustomList) {
		this.serviziRichiestiCustomList = serviziRichiestiCustomList;
	}




}
