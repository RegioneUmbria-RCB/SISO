package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RisorsePdfDTO extends StoricoPdfDTO {

	private JRBeanCollectionDataSource parenti;
	private String SUBREPORT_DIR;

	public JRBeanCollectionDataSource getParenti() {
		return parenti;
	}

	public void setParenti(JRBeanCollectionDataSource parenti) {
		this.parenti = parenti;
	}

	public String getSUBREPORT_DIR() {
		return SUBREPORT_DIR;
	}

	public void setSUBREPORT_DIR(String sUBREPORT_DIR) {
		SUBREPORT_DIR = sUBREPORT_DIR;
	}

}
