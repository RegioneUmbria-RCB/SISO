package it.webred.ct.proc.ario.aggregatori;

import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.StringUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class TipoAggregatore
{
	protected static final Logger log = Logger.getLogger(TipoAggregatore.class.getName());


	//public static Properties propCrit = null;
	protected Connection connectionForLongResultset;
	private static Properties prop = null;
		
	public abstract void aggrega(String codEnte, HashParametriConfBean paramConfBean) throws AggregatoreException;
	
	public abstract void setConnectionForLongResultset(Connection connectionForLongResultset) throws AggregatoreException;
	
	
	/**
	 * Metodi non astratti
	 */	
	
	//Metodo per il recupero del file aggregatori.properties contenente le query
	public String getProperty(String propName) throws Exception {
		
		if (prop!=null)
			return prop.getProperty(propName);	
		else {
			//Caricamento del file di properties degli aggregatori		
			prop = new Properties();
	        InputStream is = null;
	        try{
		        try {
		            is = this.getClass().getResourceAsStream("/sql/aggregatori.sql");
		            prop.load(is);                     
		        }catch(Exception e) {
		            log.error("Eccezione caricamento file property ", e);
		            return null;
		        }
		        
		        String p = prop.getProperty(propName);
				return p;
		        
	        }finally {
				is.close();
			}  			
		}
	}
		
	
	/*//Metodo per il recupero del file criteriValutabili.properties contenente i criteri valutabili per le fonti
	public int getCriterioMax(Object[] fonte) throws Exception {
		
		//Caricamento del file di properties	
        propCrit = new Properties();
        InputStream isCrit = null;
        try{
	        try {
	        	isCrit = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/aggregatori/criteriValutabiliSoggetti.properties");
	            propCrit.load(isCrit);                     
	        }catch(Exception e) {
	            log.error("Eccezione in lettura file di properties criteriValutabili: "+e.getMessage());
	            return 0;
	        }
	        
	        BigDecimal ente = (BigDecimal)fonte[0];
	        BigDecimal progr = (BigDecimal)fonte[1];
	        
	        String propName = "criterio." + ente + "." + progr;
	        
	        String p = propCrit.getProperty(propName);
			
	        int ratingMax = Integer.parseInt(p);
	        
	        return ratingMax;
	        
        }catch (Exception e) {
        	log.error("Eccezione in caricamento percentuale criterio massimo: " + e.getMessage());
        	throw new Exception("Eccezione in caricamento percentuale criterio massimo: " + e.getMessage());
        }finally {
        	isCrit.close();
		}  				
	}*/
	
	
	//Metodo per il calcolo dell'hash
	public String getCtrHash(String stringa) 
	{
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("SHA");
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error("getCtrHash",e);
			
		}

		md.update(stringa.getBytes());


		byte[] b = md.digest();
		
		return new String(StringUtils.toHexString(b));
		
	}
	
	protected boolean isZero(String valore) {
		try {
			BigDecimal subZero = new BigDecimal(valore);
			if (BigDecimal.ZERO.compareTo(subZero)==0)
				return true;
			else
				return false;
		} catch (Exception e) {
			log.warn("Errore valutazione valore " + valore);
			return false;
		}
	}
	
	
	
}
