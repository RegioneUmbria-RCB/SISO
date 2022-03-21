package it.webred.ct.service.indice.web.common;

import java.io.IOException;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.service.indice.web.IndiceBaseBean;
import org.apache.log4j.Logger;

public class UserBean extends IndiceBaseBean {

	public ComuneService comuneService = (ComuneService) getEjb("CT_Service",
			"CT_Config_Manager", "ComuneServiceBean");

	private static Logger logger = Logger.getLogger("indice.log");

	private String username;

	private String ente;

	public void doLogout() {

		getRequest().getSession().invalidate();
		try {
			getResponse().sendRedirect(getRequest().getContextPath() + "/");
		} catch (IOException e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}

	public String getUsername() {
		username = getRequest().getUserPrincipal().getName();
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEnte() {

		String es = getRequest().getParameter("es");
		if (es != null && !es.equals("") && getUser() != null) {
			AmComune am = comuneService.getComune(getUser().getCurrentEnte());
			ente = am != null ? am.getDescrizione() : "";
			ente = ente.substring(0, 1).toUpperCase()
					+ ente.substring(1).toLowerCase();
		}
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

}
