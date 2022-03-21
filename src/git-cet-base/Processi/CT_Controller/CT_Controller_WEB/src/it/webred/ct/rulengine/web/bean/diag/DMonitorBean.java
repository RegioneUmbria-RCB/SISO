package it.webred.ct.rulengine.web.bean.diag;

import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.web.bean.diag.command.thread.DiagnosticCommandThread;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class DMonitorBean extends DBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(DMonitorBean.class.getName());
	
	
	private String codiceComando;
	private String diagType;
	private String belfiore;
	
	private String esitoMessage;
	
	private String diagLogPage = "/jsp/protected/empty.xhtml";
	
	
	
	@PostConstruct
	public void init() {
		HttpServletRequest req = getRequest();
		
		if(req.getParameter("codiceComando") != null) {
			codiceComando = req.getParameter("codiceComando");	
			logger.info("[Init diag monitor] codiceComando: "+codiceComando);
		}
		
		if(req.getParameter("diagType") != null) {
			diagType = req.getParameter("diagType");	
			logger.info("[Init diag monitor] diagType: "+diagType);
		}
		
		if(req.getParameter("belfiore") != null) {
			belfiore = req.getParameter("belfiore");	
			logger.info("[Init diag monitor] belfiore: "+belfiore);
		}
		
		if(req.getParameter("pathApp") != null) {
			String urlRedirect = req.getParameter("pathApp");	
			req.getSession().setAttribute("urlRedirect", urlRedirect);
			logger.info("[Init diag monitor] redirect url: "+urlRedirect);
		}
	}
	
	
	
	
	public void doExecuteGroup() {
		logger.info("Esecuzione thread diagnostico");
		
		HttpServletRequest req = getRequest();
		
		try {
			//preparazione oggetto task
			logger.info("Preparazione parametro task per thread Diag Command");
					
			//recupero comando da cod_command
			RCommand rCommand = recuperaComandoService.getRCommand(codiceComando);
			
			//recupero fd legate al cmd
			List<RFontedatiCommand> fdc = recuperaComandoService.getRCommandFDs(rCommand);
			
			Task t = new Task(belfiore,null,rCommand.getRCommandType().getId());
			t.setFreeObj(rCommand);
			t.setStartTime(Calendar.getInstance().getTime());
			t.setDescription("Thread Command di diagnostica launch");
			t.setTaskId(belfiore+"::"+codiceComando);
			
			if(fdc.size() == 1){
				t.setIdFonte(fdc.get(0).getId().getIdFonte()); //1 fd coinvolta				
			}
			
			logger.info("Lancio thread Command di diagnostica launch");
			new Thread(new DiagnosticCommandThread(t)).start();
			logger.info("Thread Diag Command in esecuzione");
			
			String urlRedirect = (String)req.getSession().getAttribute("urlRedirect");
			esitoMessage = "dia.group.launch.code.ok";
			urlRedirect += "?ctlrmsg="+esitoMessage;
			
			//TODO: gestione esito negativo: dia.group.launch.code.ko
			
			logger.debug("Redirecting to "+urlRedirect+" ...");
			getResponse().sendRedirect(urlRedirect);
			
		}catch (Exception e) {			
			logger.error("Eccezione: "+e.getMessage(),e);
		}
	}
	
	
	public void doReturn() {
		HttpServletRequest req = getRequest();
		
		try {
			String urlRedirect = (String)req.getSession().getAttribute("urlRedirect");
			logger.debug("Redirecting to "+urlRedirect+" ...");
			getResponse().sendRedirect(urlRedirect);
		}catch (Exception e) {			
			logger.error("Eccezione: "+e.getMessage(),e);
		}
	}

	public String getCodiceComando() {
		return codiceComando;
	}

	public void setCodiceComando(String codiceComando) {
		this.codiceComando = codiceComando;
	}

	

	public String getDiagLogPage() {
		return diagLogPage;
	}



	public void setDiagLogPage(String diagLogPage) {
		this.diagLogPage = diagLogPage;
	}

	public String getEsitoMessage() {
		return esitoMessage;
	}

	public void setEsitoMessage(String esitoMessage) {
		this.esitoMessage = esitoMessage;
	}


	public String getBelfiore() {
		return belfiore;
	}


	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}




	public String getDiagType() {
		return diagType;
	}




	public void setDiagType(String diagType) {
		this.diagType = diagType;
	}

}
