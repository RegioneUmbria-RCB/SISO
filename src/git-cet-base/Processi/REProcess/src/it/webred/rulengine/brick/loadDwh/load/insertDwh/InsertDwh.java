package it.webred.rulengine.brick.loadDwh.load.insertDwh;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.SitLicenzeCommercioDao;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.FatalCommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class InsertDwh {

	private static final org.apache.log4j.Logger log = Logger.getLogger(InsertDwh.class.getName());

	

    public static void launchInserimento(Connection con , EnvInsertDwh env,  CommandLauncher launch, Command cmd, String connectionName, Context ctx, ResultSet rs, List<RAbNormal> abnormals, AmKeyValueExt amkvext) throws Exception {

    	ArrayList<RigaToInsert> rows = env.getRighe(rs);

    	//controllo per valore del parametro: flag.data.validita.dato
		String tableName = env.nomeTabellaOrigine;
		String flagDtValDato = "";
		String campoDtInizioVal = "";
		String campoDtFineVal = "";
		String amkvextValue = amkvext != null? amkvext.getValueConf(): null;
		
		if(amkvextValue != null && !"".equals(amkvextValue) && amkvextValue.contains(tableName)){
			String[] valoriAllTable = amkvextValue.split("\\|", -1);
			for(int i = 0; i < valoriAllTable.length ; i++){
				String valoriTable = valoriAllTable[i];
				if(valoriTable.contains(tableName)){
					
					String[] valoriCampi = valoriTable.split(",", -1);
					flagDtValDato = valoriCampi[1];
					campoDtInizioVal = valoriCampi[2];
					campoDtFineVal = valoriCampi[3];
					
				}
			}
			
		}
    	
    	for (RigaToInsert rti : rows) {
           	LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			String[] nomeCampiChiave = env.getNomeCampiChiave();
			try {
	
					params = rti.getParams();
					if(!"".equals(campoDtInizioVal))
						params.put("DT_INI_VAL_DATO", params.get(campoDtInizioVal));
					if(!"".equals(campoDtFineVal))
						params.put("DT_FINE_VAL_DATO", params.get(campoDtFineVal));
					if(!"".equals(flagDtValDato))
						params.put("FLAG_DT_VAL_DATO", Integer.parseInt(flagDtValDato));
    				
					
					launch.runCommand(cmd.getBeanCommand().getRCommand().getCodCommand(), 
										params, connectionName, ctx, con);
    				
    				env.executeSqlPostInsertRecord(con, params);
    		                        
    				
    				
    		} 
    		catch (FatalCommandException e) {
    			
    			log.error("Errore grave cercando di inserire i dati in DWH " + " in"  + tableName,e);
    			throw e;

       		}
			catch (Exception e) {
    			
    	   			RAbNormal rabn = new RAbNormal();
        			rabn.setEntity(env.getNomeTabellaOrigine());
        			rabn.setMessage(e.getMessage());
        			if (nomeCampiChiave!=null) {
	        			String chiave = rs.getObject(nomeCampiChiave[0])!=null?rs.getObject(nomeCampiChiave[0]).toString():null;
	        			for (int i = 1; i < nomeCampiChiave.length; i++) {
	        				chiave += "|";
	        				chiave += rs.getObject(nomeCampiChiave[i])!=null?rs.getObject(nomeCampiChiave[i]).toString():null;
	    				}    
	        			rabn.setKey(chiave);
        			} else {
	        			rabn.setKey(params!=null?params.toString().substring(0,200):null);
        			}
        			rabn.setLivelloAnomalia(1);
        			
        			rabn.setMessageDate(new Timestamp(new java.util.Date().getTime()));

        			log.error("Errore inserimento al record " + rabn.getKey()+ " in "  + tableName, e);
        	        abnormals.add(rabn);
     		} 

    		// non posso settare flag elaborato se non ci sono chiavi
    		if (nomeCampiChiave!=null) {
        	try {
            		Object[] valoriChiavi = new Object[nomeCampiChiave.length];
        			boolean chiaviNulle=false;
            		for (int i = 0; i < nomeCampiChiave.length; i++) {
            			Object o = rs.getObject(nomeCampiChiave[i]);
            			if (o==null) {
            				chiaviNulle=true;
            				break;
            			}
        				valoriChiavi[i]= o;
        			}   
            		if (!chiaviNulle)
            			updateFlagElaborato(env, con ,valoriChiavi, (Timestamp)params.get("DT_EXP_DATO") );
        		} catch (Exception e) {
        			log.error("Errore su settaggio FlagElaborato ",e);
        			throw new RulEngineException("Errore su settaggio FlagElaborato ",e);
    			}
    		}    		
    	}


    }
    
	private static void updateFlagElaborato(EnvInsertDwh env,  Connection con, Object[] valoriCampiChiave, Timestamp dtExpDato) throws RulEngineException {
		PreparedStatement st = null;
		try {
			String sqlUpd = env.getSqlUpdateFlagElaborato();
			// durante l'inserimento di alcune tabelle, tipo quelle di relazione
			// mi ritrovo che la chiave originale non è valorizzata
			// dunque getSqlUpdateFlagElaborato dovrebbe tornare null
			// perché per quelle tabelle non torno sul dato originale a fare upd della riga
			if (sqlUpd==null)
				return;
			st = con.prepareStatement(sqlUpd);
			for (int i = 0; i < valoriCampiChiave.length; i++) {
				Object valore = valoriCampiChiave[i];
				if (valore==null)
					st.setNull(i+1,Types.VARCHAR );
				else
					st.setObject(i+1, valore);
			}  
			st.setTimestamp(valoriCampiChiave.length+1, dtExpDato);
			st.executeUpdate();
		} catch (SQLException e1) {
			log.error("Errore set flag elaborato table " + env.getNomeTabellaOrigine()  );
			throw new RulEngineException("set flag elaborato table " + env.getNomeTabellaOrigine() , e1);
		} catch (Exception e1) {
			log.error("Errore set flag elaborato table " + env.getNomeTabellaOrigine()  );
			throw new RulEngineException("set flag elaborato table " + env.getNomeTabellaOrigine() , e1);
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
	}
	
}
