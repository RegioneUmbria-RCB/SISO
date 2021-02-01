package it.webred.rulengine.brick.loadDwh.load.superc.concrete;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.concessioni.v1.Env;
import it.webred.rulengine.brick.loadDwh.load.concessioni.v1.ImportConcessioni;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportStandardFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.exception.RulEngineException;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public abstract class ConcreteImportFactory {

	
	
	private String connectionName;
	private Context ctx;
	private static HashMap<String,PreNormalizzaFilter > filter = new HashMap<String,PreNormalizzaFilter >() ;

	
	public ConcreteImportFactory(String connectionName, Context ctx) {
		this.connectionName = connectionName;
		this.ctx = ctx;
		

		
	}
	
	
	public Connection getConn() {
		return ctx.getConnection(connectionName);
	}
	public Context getCtx() {
		return ctx;
	}
	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	public void setConnectionName(String connectionNname) {
		this.connectionName = connectionNname;
	}

	public String getConnectionName() {
		return connectionName;
	}

	
	public static <T extends ImportFiles> ConcreteImport<ConcreteImportEnv> getConcreteImport(T i) throws RulEngineException {
		

		if (i instanceof ImportFiles<?>) {
			EnvImport envImport = i.getEnv();

			
			PreNormalizzaFilter pnistance = null;
			try {
				String ente = (String)envImport.getCtx().getDeclarativeType("RULENGINE.CODENTE").getValue();
				if (filter.containsKey(ente))
					pnistance =  filter.get(ente);
				
				String s  = null;
				try {
					String propName = "preNormalizzaFilter."+ente;
					s = i.getEnv().getProperty(propName);
					
					if (s!=null) {
						Class<PreNormalizzaFilter> pn = (Class<PreNormalizzaFilter>) Class.forName(s);
						pnistance =  pn.newInstance();
						filter.put(ente, pnistance);
					} else
						pnistance= null;
					
					
					
				} catch (Exception e) {
					throw new RulEngineException("Impossibile reperire classe di PreNormalizzaFilter " + s,e);
				}
			} finally {
				
			}
			
			
			
			String s = null;
			String version=null; 
			if (i instanceof ImportStandardFiles<?>) { 
				version = ((ImportStandardFiles)i).getVersioneTracciato();
				//rimuovo i zero iniziali
				if(version!=null && version.startsWith("0"))
					version = version.replaceFirst("^0+(?!$)", "");
			}
			
			try {
				String propName = "Caricatore.class."+version;
				if (version==null)
					propName = "Caricatore.class.noversion";
				 s = i.getEnv().getProperty(propName);
				Class<ConcreteImport<ConcreteImportEnv>> ci = (Class<ConcreteImport<ConcreteImportEnv>>) Class.forName(s);
				ConcreteImport<ConcreteImportEnv> ciIstance =  ci.newInstance();
				ciIstance.setEnv(envImport);
				
				ciIstance.setFilter(pnistance);
				
				return ciIstance;
			} catch (Exception e) {
				throw new RulEngineException("Impossibile reperire classe di Import " + s,e);
			}
			
		} else 
			throw new RulEngineException("Nessun componente per l'import dei dati nel formato indicato:" + i );
		
		
	}

	
	
	
	
	
}
