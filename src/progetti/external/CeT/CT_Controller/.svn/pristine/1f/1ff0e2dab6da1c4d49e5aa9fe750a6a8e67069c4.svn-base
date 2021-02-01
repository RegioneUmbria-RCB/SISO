package it.webred.ct.rulengine.web.bean.diag;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.dto.ParamChainDTO;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.web.bean.diag.command.thread.DiagnosticCommandThread;
import it.webred.ct.rulengine.web.bean.diag.command.thread.exception.DiagnosticCommandException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import config.ChainConfiguration;

/**
 * 
 * @author Paolo Barola
 *
 */
public class DLauncherBean extends DBaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(DLauncherBean.class.getName());
	
	
	private String codiceComando;
	private String diagType;
	private String belfiore;
	
	private String esitoMessage;
	
	private String diagLogPage = "/jsp/protected/empty.xhtml";
	
	private Integer nofparams;
	private List<ParamChainDTO> params = null;
	private ParamChainDTO par;
	
	private int currentCarIndex;
	
	
	@PostConstruct
	public void init() {
		HttpServletRequest req = getRequest();
		
		if(req.getParameter("codiceComando") != null) {
			codiceComando = req.getParameter("codiceComando");	
			logger.info("[Init diag monitor] codiceComando: "+codiceComando);
			
			//recupero info parametri comando jelly
			ChainConfiguration cc = ChainConfiguration.getInstance(codiceComando);
			cc.loadConfiguration();
			
			nofparams = cc.getNofParams();
			logger.debug("nofparams: "+nofparams);
			
			params = new ArrayList<ParamChainDTO>();
			if(nofparams > 0) {				
				Set keys = cc.getParams().keySet();
				Iterator it = keys.iterator();
				while(it.hasNext()) {
					String key = (String)it.next();
					String value = (String)cc.getParams().get(key);
					params.add(new ParamChainDTO(key,value));
				}
			}
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
	
	
	
	public void doExecuteSingle(){
		logger.info("Esecuzione thread diagnostico ["+codiceComando+"/"+belfiore+"]");
		
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
			
			HashMap<String,String> m = new HashMap<String,String>(); 
			//passaggio parametri nel contesto dento l'oggetto map
			for(int i=0; i<params.size(); i++) {
				String parname = params.get(i).getNomeParam();
				String parval = params.get(i).getValoreParam();
				logger.debug(parname+": "+parval);
				if((parval == null || parval.equals("")) && !parname.equals("Eventuali report comunali addizionali(espressi tramite Codice Belfiore e divisi da ',')")) {
					super.addErrorMessage("diag.requied.field", parname);
					throw new DiagnosticCommandException("Validazione campi parametri: campo obbligatorio non impostato !",null);
				}
				
				m.put(parname, parval);
			}
			
			t.setDiagParams(m);
			
			logger.info("Lancio thread Command di diagnostica launch");
			new Thread(new DiagnosticCommandThread(t)).start();
			logger.info("Thread Diag Command in esecuzione");
			
			//gestione azioni post elaborazione comando  
			if(rCommand.getCodCommand().equals("S-REP-RED")){
				//report redditi analitici: eseguo download
				ParameterSearchCriteria criteria = new ParameterSearchCriteria();
				criteria.setKey("dir.files.dati");
				AmKeyValueExt param = paramService.getAmKeyValueExt(criteria);
				String filePath = param.getValueConf() + "repRedd_"+ t.getStartTime().getTime() +".xls";
				doDownloadReportRedditi(filePath,params.get(0).getValoreParam());
				
			}else{
				String urlRedirect = (String)req.getSession().getAttribute("urlRedirect");
				esitoMessage = "dia.single.launch.code.ok";
				urlRedirect += "?ctlrmsg="+esitoMessage;
					
				logger.debug("Redirecting to "+urlRedirect+" ...");
				getResponse().sendRedirect(urlRedirect);
			}
			
		}catch(DiagnosticCommandException dce) {
			logger.warn(dce.getMessage());
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

	private void doDownloadReportRedditi(String filePath, String anno) throws InterruptedException{
		File f = new File(filePath);
		
		while(!f.exists() || f.length() < 1){
			//aspetto l'elaborazione del report
			Thread.sleep(1000L);
		}
		if (f.exists()) {
			Thread.sleep(1000L);
			logger.info("Downloading file " + f.getName() + "...");
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			int DEFAULT_BUFFER_SIZE = 10240;
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			
			try {

				bis = new BufferedInputStream(new FileInputStream(f),
						DEFAULT_BUFFER_SIZE);

				response.setContentType("application/download");
				response
						.setHeader("Content-Length", String.valueOf(f.length()));
				response.setHeader("Content-Disposition",
						"attachment; filename=\"report_redditi_" + anno + ".xls\"");
				bos = new BufferedOutputStream(response.getOutputStream(),
						DEFAULT_BUFFER_SIZE);

				byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
				int length;
				while ((length = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, length);
				}

				bos.flush();

			} catch (Throwable tl) {
				logger.error(tl.getMessage(),tl);
			} finally {
				close(bos);
				close(bis);
			}
			facesContext.responseComplete();
			f.delete();
		}
	}
	
	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
	
	public String getCodiceComando() {
		return codiceComando;
	}

	public void setCodiceComando(String codiceComando) {
		this.codiceComando = codiceComando;
	}

	public String getDiagType() {
		return diagType;
	}

	public void setDiagType(String diagType) {
		this.diagType = diagType;
	}

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public String getEsitoMessage() {
		return esitoMessage;
	}

	public void setEsitoMessage(String esitoMessage) {
		this.esitoMessage = esitoMessage;
	}

	public String getDiagLogPage() {
		return diagLogPage;
	}

	public void setDiagLogPage(String diagLogPage) {
		this.diagLogPage = diagLogPage;
	}


	public Integer getNofparams() {
		return nofparams;
	}


	public void setNofparams(Integer nofparams) {
		this.nofparams = nofparams;
	}


	public List<ParamChainDTO> getParams() {
		return params;
	}


	public void setParams(List<ParamChainDTO> params) {
		this.params = params;
	}


	public int getCurrentCarIndex() {
		return currentCarIndex;
	}


	public void setCurrentCarIndex(int currentCarIndex) {
		this.currentCarIndex = currentCarIndex;
	}


	public ParamChainDTO getPar() {
		return par;
	}


	public void setPar(ParamChainDTO par) {
		this.par = par;
	}
}
