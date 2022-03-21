package it.webred.rulengine.dwh;


import it.webred.rulengine.Utils;
import it.webred.rulengine.dwh.def.Campo;
import it.webred.rulengine.dwh.def.Chiave;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;
import it.webred.rulengine.dwh.def.Tabella;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.SitCConcessioniCatasto;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.utils.GenericTuples;
import it.webred.utils.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.ResultSetDynaClass;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.lang.ArrayUtils;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class DwhUtils
{
	
	private static final Logger log = Logger.getLogger(DwhUtils.class.getName());

	// contiene tutti i nomi dei metodi set della tabella
	private static LinkedHashMap<Class<? extends Tabella>,HashMap<String,Object>> setsTabella= new LinkedHashMap<Class<? extends Tabella>,HashMap<String,Object>>();
	
	// contiene dei bean dinamici le cui properties sono tutti i set che ha la tabella in chiave
	private static LinkedHashMap<Class<? extends Tabella>,DynaBean> dynaBeanTabella= new LinkedHashMap<Class<? extends Tabella>,DynaBean>();
	
	public static DataDwh getDataDwh(DataDwh d, Timestamp t) {
		try {
			d.setValore(t);
			return d;
		} catch (Exception e)  {
			return null;
		}
	}
	
	
	
	public static Map<String, Object> rsToMap(ResultSet rs) throws SQLException {
		RowProcessor rp = new BasicRowProcessor();
		return rp.toMap(rs);
	}
	
	public static Identificativo getIdentificativo(Object i) {
		Identificativo id = new Identificativo();
		id.setValore(i);
		return id;
	}
	
	public static ChiaveEsterna getChiaveEsterna(String k) {
		ChiaveEsterna ce = new ChiaveEsterna();
		ce.setValore(k);
		return ce;
	}
	

	  /**
	 * @param con
	 * @param sqlStatement
	 * @return
	 * @throws Exception
	 */
	public static synchronized ResultSet executeQuery( Connection con, String sqlStatement )
	     throws Exception {  
	  
	    try {
	      Statement s = con.createStatement ( );
	      ResultSet rs = s.executeQuery( sqlStatement ); // returns exactly one ResultSet object
	      
	      
	      return rs;

	    } catch ( SQLException e ) {
	      throw ( e );
	    }
	  }
	
	/**
	 * @param t
	 * Rstituisce i campi di una tabella in formato fields Java
	 * @throws Exception
	 */
	public static PropertyDescriptor[] getBeanFields(Tabella t)  {		

		PropertyDescriptor[] a = PropertyUtils.getPropertyDescriptors(t);
		return a;
	}

	
	/**
	 * @param t - Tabella della quale voglio i fields
	 * @return Una hashmao , come chiave si ha il nome del metodo set in formato java e come valore sempre null
	 * @throws Exception 
	 */
	public static GenericTuples.T2<DynaBean,HashMap<String,Object>> getDynaInfoTable(Tabella t, boolean comprendiChiaveId) throws Exception {
			
		HashMap<String,Object> sets = setsTabella.get(t.getClass());
		if (sets==null)
		{
			sets = new HashMap<String,Object>();
			Method[] mts = null;
			Class cc = t.getClass();  

			while (cc != null)  
			{ 
				Method[] mms = cc.getMethods();
				mts = (Method[])ArrayUtils.addAll(mts, mms);
				cc = cc.getSuperclass();
			}
			// la hash serve per togliere la duplicazione di eventuali doppi metodi set sullo stesso campo
			for(Method m :mts )
			{ 
				String mname = m.getName();
				String campoLower =  mname.substring(3).substring(0, 1).toLowerCase() +  mname.substring(3).substring(1,  mname.substring(3).length());
				if (mname.substring(0,3).equals("set"))
				{
					Class c = t.getClass();  
					Field[] fields = null;
					while (c != null)  
					{  
						Field[] fields1 = c.getDeclaredFields();
						fields = (Field[])ArrayUtils.addAll(fields, fields1);
						c = c.getSuperclass();
					}
					for(Field f : fields) {
						if (f.getName().equalsIgnoreCase(campoLower)) {
							
							if (!comprendiChiaveId && f.getType().equals(Chiave.class))
							{
								break;
							} else {
								sets.put(mname,null);
								break;
							}
						}
					}
						
				}
					
			}
			setsTabella.put(t.getClass(),sets);
		} 

		
		// ogni tabella ha un dynabean corrispondente
		DynaBean dynaBean = dynaBeanTabella.get(t.getClass());
		if (dynaBean==null) {
			DynaProperty[] props = new DynaProperty[sets.size()];
			Set es = sets.entrySet();
			Iterator it = es.iterator();
			int i = 0;
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String campo = ((String)entry.getKey()).substring(3);
				campo = campo.substring(0, 1).toLowerCase() +  campo.substring(1,  campo.length());

				// devo trovare il tipo della proprietà alla quale si riferisce il set
				String campoUpper =  campo.substring(0, 1).toUpperCase() +  campo.substring(1,  campo.length());
				Method gmeth;
				try
				{
					gmeth = t.getClass().getMethod("get" + campoUpper, new Class[0]);
				}
				catch (Exception e)
				{
					throw e;
				}
				
				
				Class tipoCampo = null;
				boolean isCampo = false;
            	for (Class c : gmeth.getReturnType().getInterfaces()) {
            		if (c.equals(Campo.class))
            			isCampo = true;
            	}
				if (gmeth.getReturnType().equals(Relazione.class) || isCampo )
					tipoCampo = Object.class;
				else
					tipoCampo = gmeth.getReturnType();
				
				props[i] = new DynaProperty(campo, tipoCampo);
				i++;
			}
			BasicDynaClass dynaClass = new BasicDynaClass("ClasseSetsTabella", null, props);		
			
			try
			{
				dynaBean = dynaClass.newInstance();
			}
			catch (Exception e)
			{
				throw e;
			}
			if (dynaBean!=null) {
				dynaBeanTabella.put(t.getClass(),dynaBean);
			}
		}

			 
		
		return GenericTuples.t2(dynaBean,sets);
		
	}
	
	
	/**
	 * Metodo che ritorna una proprietà da un file 
	 * che ha coe nome ilnomedellaclasse.properties
	 * @param propName
	 * @return
	 */
	public static String getProperty(Class clazz, String propName) {
		return Utils.getProperty(clazz, propName);
	}
	
	/**
	 * Torna il nome del campo sql e il suo valore per l'update (preso dal parametro tabella)
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public static GenericTuples.T2<String,LinkedHashMap<String,Object>> prepareUpdate(Tabella t) throws Exception 
	{ 

		HashMap sets = getDynaInfoTable(t,false).secondObj;
		
		LinkedHashMap<String,Object> valori = new LinkedHashMap<String,Object>();
		String updateString = null;
		Set entries = sets.entrySet();
		Iterator it = entries.iterator();
	    while (it.hasNext()) {
	    	Map.Entry entry = (Map.Entry) it.next();
			String mname = (String)entry.getKey();
			
				if (updateString==null) {
					updateString = "UPDATE " + StringUtils.JavaName2NomeCampo(t.getClass().getSimpleName()) + " SET ";
				} else
					updateString = updateString + "," ;
					
				String campo = mname.substring(3);
				if (StringUtils.JavaName2NomeCampo(campo).equalsIgnoreCase("DT_INIZIO_VAL"))
					updateString = updateString + "DT_INIZIO_VAL = DT_INIZIO_VAL";
				else if (StringUtils.JavaName2NomeCampo(campo).equalsIgnoreCase("DT_INS"))
					updateString = updateString + "DT_INS = DT_INS";
				else if (StringUtils.JavaName2NomeCampo(campo).equalsIgnoreCase("DT_MOD"))
					updateString = updateString +  StringUtils.JavaName2NomeCampo(campo) + "=" + "SYSDATE";
				else {
					updateString = updateString +  StringUtils.JavaName2NomeCampo(campo) + "=" + "?";
					String campoUpper =  campo.substring(0, 1).toUpperCase() + campo.substring(1, campo.length());
					Method gmeth = t.getClass().getMethod("get" + campoUpper, new Class[0]);
					valori.put(StringUtils.JavaName2NomeCampo(campo),gmeth.invoke(t,new Object[0]));
				}
		}
		
		return GenericTuples.t2(updateString,valori);


	}

	
	/**
	 * setta le proprietà del dynabean sulla tabella (QUELE CON NOME UGUALE)
	 * @param db
	 * @param t
	 * @param nomeCampiDinamiciStileDb -  true se le proprietà del dynabean sono con l'undescore
	 * @throws Exception 
	 */
	private  static void setProperties(DynaBean db, Tabella t, boolean nomeCampiDinamiciStileDb) throws Exception {
		DynaClass dc = db.getDynaClass();
		DynaProperty[] dps = dc.getDynaProperties();
	
		for(int i=0;i<dps.length;i++){
            DynaProperty dp = dps[i];
            String nomeP = dp.getName();
            if (nomeCampiDinamiciStileDb)
            	nomeP = StringUtils.nomeCampo2JavaName(nomeP);
            
            try
			{

            	String nomePUpper =  nomeP.substring(0, 1).toUpperCase() + nomeP.substring(1, nomeP.length());


            	Method gmeth = t.getClass().getMethod("get" + nomePUpper, new Class[0]);
            	
            	Method smeth;
            	boolean campo = false;
            	for (Class c : gmeth.getReturnType().getInterfaces()) {
            		if (c.equals(Campo.class))
            			campo = true;
            	}
            	if (gmeth.getReturnType().equals(Relazione.class)) {
            		Relazione r = (Relazione)gmeth.invoke(t,new Object[0]);
            		Campo campoRelazione = r.getRelazione();
            		campoRelazione.setValore(db.get(dp.getName()));
            		smeth = t.getClass().getMethod("set" + nomePUpper, new Class[] { campoRelazione.getClass() });
					smeth.invoke(t, new Object[] {campoRelazione});
            	} else if (campo==true) {
            		Campo c = (Campo)gmeth.getReturnType().newInstance();
            		c.setValore(db.get(dp.getName()));
						smeth = t.getClass().getMethod("set" + nomePUpper, new Class[] { gmeth.getReturnType() });
						smeth.invoke(t, new Object[] {c});
            	} else {
    				smeth = t.getClass().getMethod("set" + nomePUpper, new Class[] { dp.getType() });
        			smeth.invoke(t, new Object[] {db.get(dp.getName())});
            	}
            	
				
					
				
			}
			catch (Exception e)
			{
				log.warn("Non posibile settare i valori di un bean da database",e);
			}

        }
	}

	
	public static LinkedHashSet<Tabella> resultsetToTable(ResultSet rs, Class<? extends Tabella> c) throws Exception {
		LinkedHashSet<Tabella> tabs = new LinkedHashSet<Tabella>();
		try
		{
			
			while (rs.next()) {

				Tabella t = c.newInstance();
				DynaBean bean = getDynaInfoTable(t,false).firstObj;
				HashMap<String,Object> sets = getDynaInfoTable(t, false).secondObj;
				Set es = sets.entrySet();
				Iterator it = es.iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					String nomeCampoJava =  ((String)entry.getKey()).substring(3);
					nomeCampoJava = nomeCampoJava.substring(0, 1).toLowerCase() + nomeCampoJava.substring(1, nomeCampoJava.length());
					String nomeCampoDb = StringUtils.JavaName2NomeCampo(nomeCampoJava);
					Object o = rs.getObject(nomeCampoDb);
					bean.set(nomeCampoJava,o);
					setProperties(bean,t,false);
				}
				tabs.add(t);
			}

				return tabs;


			}
		catch (Exception e)
		{
			log.error("Errore conversione resultset to bean",e);
			throw e;
		}

	}
	
	
	

	
	/**
	 * Restituisce una lista di oggetti Tabella - dato un Resultset e la classe che rappresenta la tabella 
	 * @param rs
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashSet<Tabella> resultsetToTableVerySlowMethod(ResultSet rs, Class<? extends Tabella> c) throws Exception {
		LinkedHashSet<Tabella> tabs = new LinkedHashSet<Tabella>();
		try
		{
			//if (!rs.next())
			//	return tabs;

			  ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
		      Iterator itr = rsdc.iterator();
		      
		      tabs = new LinkedHashSet<Tabella>();
		      while(itr.hasNext()) {
		        DynaBean bean = (DynaBean)itr.next();
		        Object t;
					t = c.newInstance();
					setProperties(bean, (Tabella)t,true);
			        tabs.add((Tabella)t);
		      }
			}
		catch (Exception e)
		{
			log.error("Errore conversione resultset to bean",e);
			throw e;
		}
	      return tabs;

	}
	
	
	
	/**
	 * Dato un complexparam costruisce il tipo di tato TipoXml 
	 * con gli attributi del civico
	 * @param civico_composto
	 * @return
	 * @throws Exception
	 */
	public static TipoXml composeCivicoComposto(ComplexParam civico_composto) throws Exception {
		Element root = new Element("civicocomposto");
		HashMap<String, ComplexParamP> m = civico_composto.getParams();
		for (Map.Entry<String, ComplexParamP> item : m.entrySet())
		{
			String paramVal = null;
			try {
				paramVal = (String) item.getValue().getValore(); // marcoric testare TypeFactory.getType(item.getValue().getValore(), item.getValue().getType());
			} catch (Exception e) {
				throw new Exception("Si sta cercando di valorizzare il civico con un valore non stringa");
			}
			Element att = new Element("att");
			att.setAttribute("tipo", item.getValue().getName());
			att.setAttribute("valore", paramVal==null?"":paramVal);
		    root.addContent(att);
		}
	
		Document doc = new Document(root);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Format outformat = Format.getPrettyFormat();
		outformat.setEncoding(ComplexParam.ENCODING);

		XMLOutputter out = new XMLOutputter(outformat);
		out.output(doc,baos);
		TipoXml xml = new TipoXml();
		xml.setValore(baos.toByteArray());
		return xml;
	}
	
	public static void main(String[] args) {
		
		
		try
		{
			
			ArrayList<Method> masterClasses = new ArrayList<Method>();
			PropertyDescriptor[] pd = DwhUtils.getBeanFields(new SitCConcessioniCatasto());
			for (int i = 0; i < pd.length; i++) {
				PropertyDescriptor p = pd[i];
				Class c = p.getPropertyType();
				if (Relazione.class.getName().equals(c.getName())) {
					Method m = p.getReadMethod();
					if (m==null)
						continue;
					Object o = m.invoke(new SitCConcessioniCatasto());
					if (o instanceof RelazioneToMasterTable) {
						masterClasses.add(m);
					}
						
				}
				
			}
			
			SitCConcessioniCatasto tabella = new SitCConcessioniCatasto();
			ChiaveEsterna ce = new ChiaveEsterna();
			ce.setValore("sssss");
			tabella.setIdExtCConcessioni(ce);
			for (Method method : masterClasses) {
				try {
					Relazione rel = (Relazione) method.invoke(tabella);
					String chiaveEsternaMaster = (String) rel.getRelazione().getValore();
				} catch (Exception e) {
					log.error(e);
				}
				
			}
			int u= 0;
			
		}
		catch (Exception e)
		{
			log.error(e);
		}
		
		
	}
}
