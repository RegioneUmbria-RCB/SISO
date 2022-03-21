package config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ChainConfiguration {
	
	private static Logger log = Logger.getLogger(ChainConfiguration.class.getName());
	
	private static ChainConfiguration me;
	
	
	//info di input per caricamento file properties
	private String codiceComando;
	
	//informazioni configurazione chain jelly
	private Integer nofParams;
	private Map<String,String> params;
	
	public ChainConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public static ChainConfiguration getInstance(String codiceComando) {
		if(me == null) {
			me = new ChainConfiguration();			
		}
		
		me.codiceComando = codiceComando;
		
		return me;
	}
	
	
	public void loadConfiguration() {
		log.info("Acquisizione file di configurazione della catena jelly "+codiceComando);
		
		try {
			InputStream is = ChainConfiguration.class.getResourceAsStream("/config/CHAINS/"+codiceComando.toLowerCase()+".properties");
			Properties p = new Properties();
			p.load(is);
			me.nofParams = new Integer(p.getProperty("rengine.jchain.in.nof"));
			log.debug("Numero di parametri trovati: "+me.nofParams);
			
			//caricamento mappa dei parametri
			if(me.nofParams > 0) {
				me.params = new HashMap<String,String>();
				
				for(int i=1; i<=me.nofParams; i++) {
					log.debug("[Parametro "+i+"] key: "+p.getProperty("rengine.jchain.in."+i+".key")+
								" - value: "+p.getProperty("rengine.jchain.in."+i+".value"));
					me.params.put(p.getProperty("rengine.jchain.in."+i+".key"), 
									p.getProperty("rengine.jchain.in."+i+".value"));	
				}
			}
			
			p.clear();
		}catch(Exception e) {
			log.error("Eccezione load file di configurazione: "+e.getMessage(),e);
		}
		
	}


	public Integer getNofParams() {
		return nofParams;
	}


	public void setNofParams(Integer nofParams) {
		this.nofParams = nofParams;
	}


	public Map getParams() {
		return params;
	}


	public void setParams(Map params) {
		this.params = params;
	}
}
