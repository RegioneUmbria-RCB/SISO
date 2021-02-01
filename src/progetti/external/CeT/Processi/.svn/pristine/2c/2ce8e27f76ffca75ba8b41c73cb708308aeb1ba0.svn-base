package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.AbstractTabellaDwhDao;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.TabellaDwhDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneFkEnteSorgente;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.SitEnteSorgente;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandUtil;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.impl.TypeFactory;

import it.webred.utils.StringUtils;
import it.webred.utils.GenericTuples.T2;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;


public abstract class AbstractLoaderCommand extends DbCommand implements Rule {

	private static final Logger log = Logger.getLogger(AbstractLoaderCommand.class);
	protected Class<? extends TabellaDwh> tabellaDwhClass;

		
	
	public AbstractLoaderCommand(BeanCommand bc,Properties jrulecfg) {
		super(bc,jrulecfg);
	}


	public CommandAck runWithConnection(Context ctx, Connection conn) throws CommandException {

		try {

			ProcessId pid = new ProcessId(ctx.getProcessID());
		} catch (Exception e) {
			log.error("AbstractLoaderCommand Problema", e);
			throw new CommandException(e);
		} finally {
			
		}
		
		
		return runWithConnectionSpec(ctx, conn);

	}

	/**
	 * Metodo del quale si può fare override .
	 * Questo metodo utilizza le reflection per leggere i campi del bean e poi
	 * inserire nella corrispondente tabella del db.
	 * Per queto motivo potrebbe essere più lento di un metodo costruito apposta su uno specifico bean
	 * 
	 * @param ctx1 -
	 *            Context locale che contiene e restituisce i parametri
	 *            specifici di una rule.
	 */
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
	throws CommandException {

		TabellaDwh tab = null;
		try {
			tab = getTabellaDwhInstance(ctx);
		
		
		HashMap<String ,Object> fieldsValue = new HashMap<String, Object>();
		if (parametriIn==null)
			parametriIn = this.getParametersIn(_jrulecfg);
		
		for (Iterator iterator = parametriIn.iterator(); iterator.hasNext();) {
			RRuleParamIn paramIn = (RRuleParamIn) iterator.next();
			Object ctxParam = TypeFactory.getType(ctx.get(paramIn.getDescr()), paramIn.getType(), null);
			
			// nome campo nel bean 
			String fieldName =StringUtils.nomeCampo2JavaName(paramIn.getDescr());
			fieldsValue.put(fieldName, ctxParam);
			
		}
		if(fieldsValue.get("fkEnteSorgente")==null || fieldsValue.get("dtExpDato")==null || fieldsValue.get("flagDtValDato")==null)
			return new RejectAck(tab.getClass().getName() + "Dati obbligatori non forniti");
		if(((Integer)fieldsValue.get("flagDtValDato")).intValue() != 0 && ((Integer)fieldsValue.get("flagDtValDato")).intValue() != 1)
			return new RejectAck("flag_dt_val_dato non valido");
		ChiaveOriginale co =  new ChiaveOriginale();
		
		String idOrig = (String)fieldsValue.get("idOrig");
		co.setValore(idOrig);
		fieldsValue.put("idOrig", co);
		
		
		DtExpDato dtExpDato = ((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),(Timestamp)fieldsValue.get("dtExpDato")));
		fieldsValue.put("dtExpDato",dtExpDato);
		DtIniDato dtInizioDato = ((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),(Timestamp)fieldsValue.get("dtIniValDato")));
		fieldsValue.put("dtInizioDato", dtInizioDato);
		DtFineDato dtFineDato = ((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),(Timestamp)fieldsValue.get("dtFineValDato")));
		fieldsValue.put("dtFineDato", dtFineDato);
		BigDecimal flagDtValDato = (new BigDecimal((Integer)fieldsValue.get("flagDtValDato")));
		fieldsValue.put("flagDtValDato", flagDtValDato);
		Identificativo idFkEnteSorgente = (DwhUtils.getIdentificativo((Integer)fieldsValue.get("fkEnteSorgente")));
		RelazioneFkEnteSorgente fkEnteSorgente = new RelazioneFkEnteSorgente(SitEnteSorgente.class, idFkEnteSorgente, ctx.getBelfiore());
		fieldsValue.put("fkEnteSorgente", fkEnteSorgente );
		
		setAllFields(tab.getClass(), tab, true, fieldsValue, fkEnteSorgente);
		
		TabellaDwhDao dao = (TabellaDwhDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(Integer.parseInt(fkEnteSorgente.getRelazione().getValore().toString()))); 

		
		
		boolean salvato = dao.save(ctx.getBelfiore());

		
		} catch (DaoException e) {
			log.error("Eccezione Dao",e);
			throw new CommandException(e);
		} catch (Exception e) {
			log.error("Eccezione cercando di valorizzare il bean " + tab!=null?tab.getClass().getName():"TAB NULL!!!!",e);
			throw new CommandException(e);
		}

		return null;
		
	}
	
	
	
		
	/**
	 * Metodo che istanzia un oggetto della classe specificata dall'attributo
	 * tabellaDwhClass e ne valorizza il processId con il corrispondente valore
	 * nel Context passato a parametro
	 * 
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	protected TabellaDwh getTabellaDwhInstance(Context ctx) throws Exception {
		TabellaDwh retVal = tabellaDwhClass.newInstance();
		ProcessId processId = new ProcessId(ctx.getProcessID());
		retVal.setProcessid(processId);
		return retVal;
	}

	
	public static void main(String[] args) {
		try {

			
		} catch (Exception e) {
			log.error(e);
		}
		
	}
	

	
	private void setAllFields(Class cl, Object instance, boolean recurseToParents, HashMap<String, Object> valori, RelazioneFkEnteSorgente fkEnteSorgente) 
	throws Exception
	{
		//Field[] fields = cl.getDeclaredFields();
		Field[] fields = getClassFields(cl);
		
		AccessibleObject.setAccessible(fields, true);
		try
		{
			for (Field field : fields) {
				if (((field.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) == 0) && !TabellaDwh.notModificableFields.containsKey(field.getName()))
				{
					// indica di quanti parametri e' il metodo trovato con la reflection
					int numParam= 1;
					try {
						Object val = null;
						Object val2 = null;
						
						java.lang.reflect.Method m = null;
						String mName = "set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
						if (Relazione.class.getName().equals(field.getType().getName())) {
							if (valori.get(field.getName()) instanceof Identificativo) { 
								m = getClassMethod(cl, mName, Identificativo.class); // cl.getMethod(mName, Identificativo.class);
								val = valori.get(field.getName());
							} else
								m = getClassMethod(cl, mName, ChiaveEsterna.class); // cl.getMethod(mName, ChiaveEsterna.class);
						}
						if (RelazioneFkEnteSorgente.class.getName().equals(field.getType().getName())) {
								m = getClassMethod(cl, mName, Identificativo.class, String.class); // Identificativo, belfiore su relazione fkEnteSorgente
								RelazioneFkEnteSorgente rel = (RelazioneFkEnteSorgente)valori.get(field.getName());
								val = (Identificativo) rel.getRelazione();
								val2 = rel.getBelfiore();
								numParam= 2;
						}						
						if (val==null){
							 if (Relazione.class.getName().equals(field.getType().getName()))
								val = TypeFactory.getRelazioneType(valori.get(field.getName()), fkEnteSorgente.getRelazione());
							 else
								val = TypeFactory.getType(valori.get(field.getName()), field.getType().getName());
						}

						if (m==null)  {
							field.set(instance, val);
						} else {
							if (numParam==1)
								m.invoke(instance, val);
							else 
								m.invoke(instance, val, val2);
								
						}
						
						
						
					}
					catch (IllegalArgumentException e) 
					{
						log.error("Field " + field.getName() + " Valore=" + valori.get(field.getName()) + " - ",e);
						throw e;
					} catch (RulEngineException e) {
						log.error("Field " + field.getName() + " Valore=" + valori.get(field.getName()) + " - ",e );
						throw new Exception(e);
					}

				}
			}
		}
		catch (IllegalAccessException e) {throw e;}
		
		if (recurseToParents && cl.getSuperclass() != null)
			setAllFields(cl.getSuperclass(), instance, recurseToParents, valori, fkEnteSorgente);
	}
	

	protected Object getRRuleParameterIn(List parametriIn, Context ctx, int index){
		Object obj = ctx.get(((RRuleParamIn) parametriIn.get(index)).getDescr());
		
		//System.out.println("Rule " + index + " " + obj);
		
		return obj; 
	}
	
}
