package it.webred.rulengine.type.impl;

import java.sql.Timestamp;
import java.util.HashMap;

import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.Chiave;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.CtrHash;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.def.DeclarativeType;
import it.webred.rulengine.type.def.TypeFactory.TypeConstructor;



public class TypeFactory extends it.webred.rulengine.type.def.TypeFactory
{
	
	private static TypeFactory me = new TypeFactory();
	
	static {
		
		// costruttori a uso interno
		dataTypeList.put(DataDwh.class,(me.new DataDwhConstructor()));
		dataTypeList.put(ChiaveOriginale.class,(me.new ChiaveOriginaleConstructor()));
		dataTypeList.put(Identificativo.class,(me.new IdentificativoConstructor()));
		dataTypeList.put(Relazione.class,(me.new RelazioneConstructor()));
		dataTypeList.put(RelazioneToMasterTable.class,(me.new RelazioneToMasterTable()));
		dataTypeList.put(ChiaveEsterna.class,(me.new ChiaveEsternaConstructor()));
		dataTypeList.put(DtExpDato.class,(me.new DtExpDatoConstructor()));
		dataTypeList.put(DtIniDato.class,(me.new DtIniDatoConstructor()));
		dataTypeList.put(DtFineDato.class,(me.new DtFineDatoConstructor()));
		dataTypeList.put(DtFineVal.class,(me.new DtFineValConstructor()));
		dataTypeList.put(DtIniVal.class,(me.new DtIniValConstructor()));
		dataTypeList.put(ProcessId.class,(me.new ProcessIdConstructor()));
		dataTypeList.put(CtrHash.class,(me.new CtrHashConstructor()));
		dataTypeList.put(Chiave.class,(me.new ChiaveConstructor()));
		
		
	}
	
	
	
	/**
	 * Restituisce una relazione Dwh , data la chiave e l'fkEntSorgente
	 * @param key
	 * @param fkEnteSorgente
	 * @return
	 * @throws Exception 
	 */	
	public static Object getRelazioneType(Object key, Object fkEnteSorgente) throws RulEngineException 
	{
			RelazioneConstructor constructor = (RelazioneConstructor)getConstructor(Relazione.class.getName());
			return constructor.construct(key,fkEnteSorgente );
	}	
	
	
	public static Object getType(Object obj, String type) throws RulEngineException {
		
		if (type==null || obj==null)
			if (type!=null && type.indexOf("it.webred.rulengine.dwh.def")==-1) // per i miei tipi so come comportarmi
				return null;

		
			TypeConstructor constructor = getConstructor(type);
			
			// se il tipo non ha un costruttore stringa , ma l'obj passato è una stringa allora scateno un errore
			if(!constructor.haveStringConstructor() && obj instanceof java.lang.String)
				throw new RulEngineException("Si sta cercando di costruire attraverso una stringa un tipo di dato " + type);
	
			return constructor.construct(obj);

	}
	
	public static Object getType(Object obj, String type, HashMap<String,DeclarativeType> var) throws Exception 
	{
		if (type==null || obj==null)
			return null;
		
		TypeConstructor constructor = getConstructor(type);
		
		// se il tipo non ha un costruttore stringa , ma l'obj passato è una stringa allora scateno un errore
		if(!constructor.haveStringConstructor() && obj instanceof java.lang.String)
			throw new Exception("Si sta cercando di costruire attraverso una stringa un tipo di dato " + type);

		Object ret = constructor.construct(obj,var);
		if (ret instanceof ComplexParam)
			((ComplexParam)ret).setVars(var);
		
		return ret;

	}
	
	
	
	/*
	 * I N N E R   C L A S S E S
	 */
	
	
	public class ChiaveEsternaConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof ChiaveEsterna)
				return obj;
			ChiaveEsterna ce = new ChiaveEsterna();
			ce.setValore(obj);
			return ce;
			
		}
		public boolean haveStringConstructor()
		{
			return true;
		}
		public String getDescription()
		{
			return "ChiaveEsterna  - (uso interno)";
		}

	}
	
	
	
	public class ChiaveConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof ChiaveEsterna)
				return obj;
			Chiave c = new Chiave();
			c.setValore((String) obj);
			return c;
			
		}
		public boolean haveStringConstructor()
		{
			return true;
		}
		public String getDescription()
		{
			return "Chiave  - (uso interno)";
		}

	}
	
	
	
	public class ProcessIdConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof ProcessId)
				return obj;
			ProcessId p = new ProcessId((String)obj);
			return p;
			
		}
		public boolean haveStringConstructor()
		{
			return true;
		}
		public String getDescription()
		{
			return "ProcessId  - (uso interno)";
		}

	}
		
	
	
	public class CtrHashConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof CtrHash)
				return obj;
			CtrHash a = new CtrHash();
			return a;
			
		}
		public boolean haveStringConstructor()
		{
			return true;
		}
		public String getDescription()
		{
			return "CtrHash  - (uso interno)";
		}

	}
	
	
	
	
	public class RelazioneToMasterTable extends TypeConstructor {

		public Object construct(Object obj, Object fkEnteSorgente) throws RulEngineException
		{
			if (obj instanceof RelazioneToMasterTable)
				return obj;
			
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore((String)obj);
				
			ChiaveEsterna ce = new ChiaveEsterna();
			if (fkEnteSorgente instanceof Identificativo)
				ce.setValore(co, (Identificativo) fkEnteSorgente);
			else
				ce.setValore(co, DwhUtils.getIdentificativo((Integer)fkEnteSorgente));
			return ce;
		}
		
		public Object construct(Object obj) throws RulEngineException
		{
			throw new RulEngineException("Costuttore con un singolo parametro non valido per il tipo RelazioneToMasterTable");
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "RelazioneToMasterTable  - Compatibile Dwh (uso interno)";
		}

	}	
	
	
	
	public class RelazioneConstructor extends TypeConstructor {

		public Object construct(Object obj, Object fkEnteSorgente) throws RulEngineException
		{
			if (obj instanceof Relazione)
				return obj;
			
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore((String)obj);
				
			ChiaveEsterna ce = new ChiaveEsterna();
			if (fkEnteSorgente instanceof Identificativo)
				ce.setValore(co, (Identificativo) fkEnteSorgente);
			else
				ce.setValore(co, DwhUtils.getIdentificativo((Integer)fkEnteSorgente));
			return ce;
		}
		
		public Object construct(Object obj) throws RulEngineException
		{
			throw new RulEngineException("Costuttore con un singolo parametro non valido per il tipo Relazione");
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "Relazione  - Compatibile Dwh (uso interno)";
		}

	}		
	
	
	public class DataDwhConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof DataDwh)
				return obj;
			
			return DwhUtils.getDataDwh(new DataDwh(),(Timestamp)obj);
			
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "Data Compatibile Dwh (uso interno)";
		}

	}	
	
	
	
	public class IdentificativoConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof Identificativo)
				return obj;
			
			return DwhUtils.getIdentificativo(obj);
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "Identficativo Numerico  - Compatibile Dwh (uso interno)";
		}

	}		
	
	
	
	
	public class DtExpDatoConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof DtExpDato)
				return obj;
			else 
				return (DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),(Timestamp)obj);
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "DtExpDato (uso interno)";
		}

	}	
	
	
	
	public class DtIniDatoConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof DtIniDato)
				return obj;
			else 
				return (DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),(Timestamp)obj);
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "DtIniDato (uso interno)";
		}

	}
	
	
	
	public class DtFineValConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof DtFineVal)
				return obj;
			else 
				return (DtFineVal)DwhUtils.getDataDwh(new DtFineVal(),(Timestamp)obj);
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "DtFineVal (uso interno)";
		}

	}
	
	
	
	public class DtIniValConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof DtIniVal)
				return obj;
			else 
				return (DtIniVal)DwhUtils.getDataDwh(new DtIniVal(),(Timestamp)obj);
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "DtIniVal (uso interno)";
		}

	}
	
	
	
	public class DtFineDatoConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof DtFineDato)
				return obj;
			else 
				return (DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),(Timestamp)obj);
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "DtFineDato (uso interno)";
		}

	}
	
	
	
	public class ChiaveOriginaleConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof ChiaveOriginale)
				return obj;
			else {
				ChiaveOriginale co = new ChiaveOriginale();
				String idOrig = (String)obj;
				co.setValore(idOrig);
				return co;
			}
		}
		public boolean haveStringConstructor()
		{
			return true;
		}
		public String getDescription()
		{
			return "Chiave Originale  Dwh (uso interno)";
		}

	}	
	

	
	
}




