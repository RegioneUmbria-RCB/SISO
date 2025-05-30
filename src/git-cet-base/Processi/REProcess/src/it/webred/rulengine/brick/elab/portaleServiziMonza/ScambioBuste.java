package it.webred.rulengine.brick.elab.portaleServiziMonza;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import sun.misc.BASE64Encoder;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.portaleservizi.ejb.scheduler.SchedulerDataBean;
import it.webred.portaleservizi.ejb.scheduler.scambiobuste.ScambioBusteSessionFacade;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class ScambioBuste extends Command implements Rule, PortaleBridge {
	private static final Logger log = Logger.getLogger(ScambioBuste.class
			.getName());
	private Connection conn = null;

	private static final String paramKeyDir = "dir.conf.ps.buste";
	private static final String paramKeyJndi = "ps.jndi.lookup";
	private static final String paramApp = "PortaleServizi";

	private ApplicationService applicationService;
	private ParameterService parameterService;
	private ScambioBusteSessionFacade scambioBusteService;
	private String enteID;

	public ScambioBuste(BeanCommand bc) {
		super(bc);
	}

	public ScambioBuste(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.debug("PortaleServiziMonza Scambio Buste run()");
		CommandAck retAck = null;
		enteID = ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		try {
			
			applicationService = (ApplicationService) ServiceLocator.getInstance()
					.getService("CT_Service", "CT_Config_Manager", "ApplicationServiceBean");
			parameterService = (ParameterService) ServiceLocator.getInstance()
					.getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			
			AmInstance ist = applicationService.getInstanceByApplicationComune(paramApp, enteID);
			
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setComune(enteID);
			criteria.setKey(paramKeyDir);
			criteria.setInstance(ist.getName());
			AmKeyValueExt param = parameterService.getAmKeyValueExt(criteria);
			String dir = param.getValueConf();
			criteria.setKey(paramKeyJndi);
			param = parameterService.getAmKeyValueExt(criteria);
			
			if(param == null || param.getValueConf().equals("")){
				log.debug("Comando ScambioBuste: inizio lookup Service in context locale");
				scambioBusteService = (ScambioBusteSessionFacade) ServiceLocator
						.getInstance().getService("PortaleServiziScheduler", "PortaleServiziSchedulerEJB", "ScambioBusteSessionFacadeBean");
			}else{
				String jndi = param.getValueConf();
				if (USE_PORTALE_BRIDGE) {
					return goToPortaleByBridge(ist, dir, enteID);
				}
				Hashtable<String, String> hash = new Hashtable<String, String>();
				hash.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
				hash.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
				hash.put("java.naming.provider.url", jndi);
				InitialContext _context = new InitialContext(hash);
				log.debug("Comando ScambioBuste: inizio lookup Service con jndi: " + jndi);
				String ear = "PortaleServiziScheduler";
				String module = "PortaleServiziSchedulerEJB";
				String ejbName = "ScambioBusteSessionFacadeBean";
				scambioBusteService = (ScambioBusteSessionFacade) _context.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
			}
			
			SchedulerDataBean dataIn = new SchedulerDataBean();
			dataIn.setConfigFilePath(dir);
			dataIn.setCodEnte(enteID);
			
			log.debug("Comando ScambioBuste: Start");
			scambioBusteService.start(dataIn);

		} catch (Exception e) {
			log.error("ERRORE " + e, e);
			return new ErrorAck(e.getMessage());
		}
		retAck = new ApplicationAck("ESECUZIONE OK");
		return retAck;
	}
	
	public String getBridgeUrlPattern() {		
		return "/BridgeEntrypoint?mode=scambioBuste";
	}
	
	public CommandAck goToPortaleByBridge(AmInstance ist, String dir, String enteID) {
		String url = ist.getUrl() + getBridgeUrlPattern();
		final WebClient webClient = new WebClient();
		try {
			webClient.addRequestHeader("dir", dir);
			webClient.addRequestHeader("enteID", enteID);
			HashMap<String, String> login = getXmlProperties(dir);
			webClient.addRequestHeader("userName", login.get("userName"));
			String password = login.get("password");
			BASE64Encoder encrypt = new BASE64Encoder();
			String encPassword = encrypt.encode(password.getBytes());
			webClient.addRequestHeader("password", encPassword);
			HtmlPage page = webClient.getPage(url);
			if (page.getElementById("errMsg") != null) {
				String errMsg = page.getElementById("errMsg").getTextContent();
				log.error("ERRORE " + errMsg);
				return new ErrorAck(errMsg);
			}
		} catch (Exception e) {
			log.error("ERRORE " + e, e);
			return new ErrorAck(e.getMessage());
		} finally {
		    webClient.close();
		}
		try {
			//pausa di 3 minuti
			Thread.sleep(180000);
		} catch (Exception e) {
			log.warn("ATTENZIONE: " + e, e);
		}
		return new ApplicationAck("ESECUZIONE OK");
	}
	
	protected HashMap<String, String> getXmlProperties(String dir) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        InputStream is = new FileInputStream(new File(dir));
        Document doc = factory.newDocumentBuilder().parse(is);
        Node ftpConfig = doc.getElementsByTagName("FtpConfig").item(0);
        String userName = ((Element)ftpConfig).getElementsByTagName("UserName").item(0).getTextContent();
        String password = ((Element)ftpConfig).getElementsByTagName("Password").item(0).getTextContent();
        HashMap<String, String> props = new HashMap<String, String>();
        props.put("userName", userName);
        props.put("password", password);
        return props;
	}
	

	
}
