package it.webred.rulengine.brick.reperimento.executor.logic;


import it.webred.rulengine.Utils;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;
//import org.jboss.resource.adapter.jdbc.WrapperDataSource;

public class BasicLogic {

private static final Logger log = Logger.getLogger(BasicLogic.class.getName());
	
	public static final String CONTROLLER_CONFIG = "CONTROLLER_CONFIG";
	public static final String CONTROLLER_APP_PROP = "CONTROLLER_APP_PROP";
	public static final String CONTROLLER_HM_APP = "CONTROLLER_HM_APP";
	public static final String CONTROLLER_PROP_KEY_COD_ENTE = "codEnte";
	public static final String CONTROLLER_PROP_KEY_AM_PROFILER = "AMProfiler";
	public static final String CONTROLLER_PROP_KEY_DB_URL = "dbUrl";
	public static final String CONTROLLER_PROP_KEY_DB_USER = "dbUser";
	public static final String CONTROLLER_PROP_KEY_DB_PWD = "dbPassword";
	public static final String CONTROLLER_HM_APP_KEY_DIOGENE = "diogene";
	public static final String CONTROLLER_HM_APP_KEY_CARONTE = "Caronte";
	public static final String CONTROLLER_HM_APP_KEY_RULENGINE = "RulEngine";
	public static final String CONTROLLER_HM_APP_KEY_DIOGENEDB = "DiogeneDB";
	public static final String CONTROLLER_HM_APP_KEY_MANAGER = "manager";
	public static final String CONTROLLER_HM_APP_KEY_DIRITTI_SUI_DATI = "Diritti sui Dati";
	public static final String CONTROLLER_HM_APP_KEY_VIRGILIO = "Virgilio";	
	
	public static final String TIPO_VUOTO = "VUOTO";
	public static final String TIPO_NUM = "NUM";
	public static final String TIPO_DOUBLE = "DOUBLE";
	public static final String TIPO_EURO = "EURO";
	
	public static final HashMap<String, String> TIPI_DATA = new HashMap<String, String>();
	public static final String TIPO_FLAG = "FLAG";
	
	public static final String ERRORE = "ERRORE";
	public static final String DIOGENE_DS = "diogene";
	
	private Throwable logicThrowable;
	
	private HashMap<String, Object> configurazione;
	
	static {
		TIPI_DATA.put("YYMMDD", "YYMMDD");
		TIPI_DATA.put("YYYY/MM/DD", "YYYY/MM/DD");
		TIPI_DATA.put("YYYY-MM-DD", "YYYY-MM-DD");
		TIPI_DATA.put("DDMMYYYY", "DDMMYYYY");
		TIPI_DATA.put("YYYYMMDD", "YYYYMMDD");
	}
	
	
	
	public BasicLogic() {
		setConfigurazione(getConfigurazione());
	}
	
	public BasicLogic(HttpSession sessione) {
		Object objConfig = sessione.getAttribute(CONTROLLER_CONFIG);
		if (objConfig == null) {
			setConfigurazione(getConfigurazione());
			return;
		}
		HashMap hmConfig = (HashMap)objConfig;
		HashMap<String, Object> hmGenericsConfig = new HashMap<String, Object>();
		Iterator it = hmConfig.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			hmGenericsConfig.put(key, hmConfig.get(key));
		}
		setConfigurazione(hmGenericsConfig);
	}
	
	public BasicLogic(HashMap<String, Object> configurazione) {
		setConfigurazione(configurazione);
	}
	
	public static Connection getConnection() throws SQLException, NamingException {
		return getConnection("REngineDS");
	}
	
	public static Connection getConnection(String datasource ) throws SQLException, NamingException {
		Context initContext = new InitialContext();
		//Context envContext = (Context) initContext.lookup("java:/comp/env");
		//DataSource ds = (DataSource) envContext.lookup("jdbc/" + datasource);
		// TODO
		DataSource ds = (DataSource)initContext.lookup("java:/jdbc/"+datasource);
		Connection conn = ds.getConnection();
		return conn;
	}
	
	public HashMap<String, Object> getConfigurazione() {
		if (configurazione != null)
			return configurazione;
		configurazione = new HashMap<String, Object>();
		try{
			Properties props = new Properties();
			props.load(this.getClass().getResourceAsStream("/controller.properties"));
			configurazione.put(CONTROLLER_APP_PROP, props);
			HashMap<String, String> hmApp = new HashMap<String, String>();
			
			if (props.getProperty(CONTROLLER_HM_APP_KEY_CARONTE) != null) {
				hmApp.put(CONTROLLER_HM_APP_KEY_CARONTE, props.getProperty(CONTROLLER_HM_APP_KEY_CARONTE));
			}
			
			/*
			if (props.getProperty(CONTROLLER_PROP_KEY_AM_PROFILER) != null) {
				//il percorso di AMProfiler è nel file .properties
				hmApp.put(CONTROLLER_PROP_KEY_AM_PROFILER, props.getProperty(CONTROLLER_PROP_KEY_AM_PROFILER));
				
				//gli altri percorsi sono restituiti da un Web Service di AMProfiler
				HashMap<String, String> hmAppWS = getHmAppWS(hmApp.get(CONTROLLER_PROP_KEY_AM_PROFILER), props.getProperty(CONTROLLER_PROP_KEY_COD_ENTE));
				Iterator it = hmAppWS.keySet().iterator();
				while(it.hasNext()) {
					String key = (String)it.next();
					hmApp.put(key, hmAppWS.get(key));
				}
			}else{
				// il percorso di AMProfiler non è nel file .properties
				// gli altri percorsi sono nel file .properties
				if (props.getProperty(CONTROLLER_HM_APP_KEY_DIOGENE) != null) {
					hmApp.put(CONTROLLER_HM_APP_KEY_DIOGENE, props.getProperty(CONTROLLER_HM_APP_KEY_DIOGENE));
				}
				if (props.getProperty(CONTROLLER_HM_APP_KEY_CARONTE) != null) {
					hmApp.put(CONTROLLER_HM_APP_KEY_CARONTE, props.getProperty(CONTROLLER_HM_APP_KEY_CARONTE));
				}
				if (props.getProperty(CONTROLLER_HM_APP_KEY_RULENGINE) != null) {
					hmApp.put(CONTROLLER_HM_APP_KEY_RULENGINE, props.getProperty(CONTROLLER_HM_APP_KEY_RULENGINE));
				}
				if (props.getProperty(CONTROLLER_HM_APP_KEY_DIOGENEDB) != null) {
					hmApp.put(CONTROLLER_HM_APP_KEY_DIOGENEDB, props.getProperty(CONTROLLER_HM_APP_KEY_DIOGENEDB));
				}
				if (props.getProperty(CONTROLLER_HM_APP_KEY_MANAGER) != null) {
					hmApp.put(CONTROLLER_HM_APP_KEY_MANAGER, props.getProperty(CONTROLLER_HM_APP_KEY_MANAGER));
				}
				//chiave non valida per il file .properties (contiene spazi, sostituiti nel file .properties dal carattere '_')
				String key = CONTROLLER_HM_APP_KEY_DIRITTI_SUI_DATI.replaceAll(" ", "_");
				if (props.getProperty(key) != null) {
					hmApp.put(CONTROLLER_HM_APP_KEY_DIRITTI_SUI_DATI, props.getProperty(key));
				}
				if (props.getProperty(CONTROLLER_HM_APP_KEY_VIRGILIO) != null) {
					hmApp.put(CONTROLLER_HM_APP_KEY_VIRGILIO, props.getProperty(CONTROLLER_HM_APP_KEY_VIRGILIO));
				}				
			}
			*/
			
			configurazione.put(CONTROLLER_HM_APP, hmApp);
		}catch(Exception e) {
			log.debug(e);
		}
		return configurazione;
	}
	
	public HashMap<String, Object> getConfigurazione(String codEnte) {
		HashMap<String, Object> configurazione = new HashMap<String, Object>();
		try{
			HashMap<String, String> hmApp = new HashMap<String, String>();
			hmApp.put(CONTROLLER_HM_APP_KEY_CARONTE, Utils.getConfigProperty("caronte.url", codEnte));
			configurazione.put(CONTROLLER_HM_APP, hmApp);
		}catch(Exception e) {
			log.debug(e);
		}
		return configurazione;
	}
	
	private HashMap<String, String> getHmAppWS(String AMUrl, String codEnte) throws Exception {
		HashMap<String, String> retVal = new HashMap<String, String>();
		String WSPath = "/services/Services";
		String nameWS = AMUrl + WSPath;
		URL endPointWS = new URL(nameWS);
		// inizializzazione WS
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.removeAllParameters();
		call.setUsername("user1");
		call.setPassword("pippo");
		// configurazione parametri WS
		call.setTargetEndpointAddress(endPointWS);
		call.addParameter("ente", XMLType.XSD_STRING, ParameterMode.IN);
		call.setOperationName("getListaUrl");
		call.setReturnType(XMLType.XSD_ANY);		
		// invocazione WS
		HashMap risultato = null;
		try {
			risultato = (HashMap) call.invoke(new Object[]{ codEnte });
		} catch (AxisFault af) {
			throw af;
		}
		Iterator it = risultato.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			retVal.put(key, (String)risultato.get(key));
		}		
		return retVal;
	}
	
	public void updateCampo(ResultSet rs, String campo, Object valore) throws Exception {
		try {
			if (valore==null)
					rs.updateNull(campo);
			else {
				if (valore instanceof String)
					rs.updateString(campo, (String)valore);
				else if (valore instanceof Long)
					rs.updateLong(campo, (Long)valore);
				else if (valore instanceof Integer)
					rs.updateInt(campo, (Integer)valore);
				else if (valore instanceof Timestamp)
					rs.updateTimestamp(campo, (Timestamp)valore);
				else throw new Exception(valore.getClass() + " non supportato da controller!");
			}
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public void setConfigurazione(HashMap<String, Object> configurazione) {
		this.configurazione = configurazione;
	}
	
	protected static String tornaValoreRS(ResultSet rs, String colonna, String tipo) throws Exception
	{
		try
		{
			String s = null;
			s = rs.getString(colonna);

			if (s == null && tipo != null)
				if (tipo.equals(TIPO_VUOTO))
					s = "";
			if (s == null)
				return s = "-";

			if (tipo != null)
				if (tipo.equals(TIPO_NUM))
				{
					s = new Integer(s).toString();
				}
				else if (tipo.equals(TIPO_DOUBLE))
				{
					s = new Double(s).toString();
				}
				else if(tipo.equals(TIPO_EURO))
				{
					NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALY);
					nf.setMinimumFractionDigits(2);
					nf.setMaximumFractionDigits(2);
					s = nf.format(new Double(s));
				}
				else if (tipo.equalsIgnoreCase(TIPI_DATA.get("YYMMDD")))
				{
					s = s.substring(4) + "/" + s.substring(2, 4) + "/" + s.substring(0, 2);
				}
				else if (tipo.equalsIgnoreCase(TIPI_DATA.get("YYYY/MM/DD")))
				{
					s = s.substring(8) + "/" + s.substring(5, 7) + "/" + s.substring(0, 4);
				}
				else if (tipo.equalsIgnoreCase(TIPI_DATA.get("YYYY-MM-DD")))
				{
					s = s.substring(8,10) + "/" + s.substring(5, 7) + "/" + s.substring(0, 4);
				}
				else if (tipo.equalsIgnoreCase(TIPI_DATA.get("DDMMYYYY")))
				{
					s = s.substring(0, 2) + "/" + s.substring(2, 4) + "/" + s.substring(4, 8);
				}
				else if (tipo.equalsIgnoreCase(TIPI_DATA.get("YYYYMMDD")))
				{
					s = s.substring(6, 8) + "/" + s.substring(4, 6) + "/" + s.substring(0, 4);
				}			
				else if (tipo.equalsIgnoreCase(TIPO_FLAG))
				{
					if (s.equals("0"))
						s = "NO";
					else
						s = "SI";
				}
			return s;
		}
		catch (Exception e)
		{
			return "-";
		}
	}

	public Throwable getLogicThrowable()
	{
		return logicThrowable;
	}

	public void setLogicThrowable(Throwable logicThrowable)
	{
		this.logicThrowable = logicThrowable;
	}
	

	
	/** Metodo che formatta la data parametro secondo il pattern parametro. 
	 * Se pattern è null oppure uguale a "", restituisce il toString() del getTime() (millisecondi) della data.
	 * @param date
	 * @param pattern
	 * @return
	 */
	protected String formattaData(java.util.Date date, String pattern) {
		if (pattern == null || pattern.equals("")) {
			return "" + date.getTime();
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	
	
	/**
	 * Il metodo permetyte di ricevere un Long da un Object
	 * Un null in caso di errore
	 * @return
	 */
	public static Long getLong(String o) {
		try {
			Long l = new Long(o);
			return l;
		} catch (Exception e) {
			return null;
		}
		
	}
}
