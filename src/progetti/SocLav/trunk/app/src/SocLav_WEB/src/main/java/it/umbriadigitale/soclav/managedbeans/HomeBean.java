package it.umbriadigitale.soclav.managedbeans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


@ManagedBean(name = "homeBean")
@ViewScoped
public class HomeBean extends BaseBean {

	public final Logger logger = Logger.getLogger(this.getClass());

	private Date dataAggiornamentoGepi;
	private Date dataAggiornamentoANPAL;

	
	@PostConstruct
	public void init() {
		logger.debug("Inizializzazione Home Bean : versione " + webAppConfig.getAppConfig().getVersion());
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
		String tokenRead = req.getParameter("token");
		if(tokenRead != null)
			logger.debug("TOKE RICEVUTO DAL RACCORDO...  " + tokenRead );
		
		//TODO:recuperare data aggiornamento
		dataAggiornamentoGepi = new Date();
		dataAggiornamentoANPAL = new Date();
		
		
	}


	public Date getDataAggiornamentoGepi() {
		return dataAggiornamentoGepi;
	}


	public void setDataAggiornamentoGepi(Date dataAggiornamentoGepi) {
		this.dataAggiornamentoGepi = dataAggiornamentoGepi;
	}


	public Date getDataAggiornamentoANPAL() {
		return dataAggiornamentoANPAL;
	}


	public void setDataAggiornamentoANPAL(Date dataAggiornamentoANPAL) {
		this.dataAggiornamentoANPAL = dataAggiornamentoANPAL;
	}

}
