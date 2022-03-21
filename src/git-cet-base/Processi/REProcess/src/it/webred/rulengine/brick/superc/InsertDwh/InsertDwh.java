package it.webred.rulengine.brick.superc.InsertDwh;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

@Deprecated
public class InsertDwh {

	private static final org.apache.log4j.Logger log = Logger.getLogger(InsertDwh.class.getName());

	
	 /**
     * @param launch
     * @param bc
     * @param connectionName
     * @param ctx
     * @param rs
     * @param abnormals - anomalie che vengono da fuori alle quali va aggiunta una eventuale anomalia su questo metodo
     * @throws Exception
     */
    public static void launchInserimento(Connection con , EnvInsertDwh env,  CommandLauncher launch, BeanCommand bc, String connectionName, Context ctx, ResultSet rs, List<RAbNormal> abnormals) throws Exception {
		
    	ArrayList<LinkedHashMap<String, Object>> rows = env.getRighe(rs);
    	Iterator<LinkedHashMap<String, Object>> itrRows = rows.iterator();
        while (itrRows.hasNext()) {
        	LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			String[] nomeCampiChiave = env.getNomeCampiChiave();
			try {
    		    	params = itrRows.next();
    				launch.runCommand(bc.getRCommand().getCodCommand(), params, connectionName, ctx, con);
    				
    				env.executeSqlPostInsertRecord(con, params);
    		                        
    				
    				
    		} catch (Exception e) {
    			RAbNormal rabn = new RAbNormal();
    			rabn.setEntity(env.getNomeTabellaOrigine());
    			rabn.setMessage(e.getMessage());
    			String chiave = rs.getObject(nomeCampiChiave[0])!=null?rs.getObject(nomeCampiChiave[0]).toString():null;
    			for (int i = 1; i < nomeCampiChiave.length; i++) {
    				chiave += "|";
    				chiave += rs.getObject(nomeCampiChiave[i]).toString()!=null?rs.getObject(nomeCampiChiave[i]).toString():null;
				}    
    			rabn.setKey(chiave);
    			rabn.setLivelloAnomalia(1);
    			
    			rabn.setMessageDate(new Timestamp(new java.util.Date().getTime()));

    			log.error("Errore inserimento al record " + chiave, e);
    	        abnormals.add(rabn);

    		}

    		Object[] valoriChiavi = new Object[nomeCampiChiave.length];
			for (int i = 0; i < nomeCampiChiave.length; i++) {
				valoriChiavi[i]= rs.getObject(nomeCampiChiave[i]);
			}   
    		updateFlagElaborato(env, con ,valoriChiavi, (Timestamp)params.get("DT_EXP_DATO") );

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
				st.setObject(i+1, valore);
			}  
			st.setTimestamp(valoriCampiChiave.length+1, dtExpDato);
			st.executeUpdate();
		} catch (SQLException e1) {
			log.error("Errore set flag elaborato table " + env.getNomeTabellaOrigine()  );
			throw new RulEngineException("set flag elaborato table table " + env.getNomeTabellaOrigine() , e1);
		} catch (Exception e1) {
			log.error("Errore set flag elaborato table " + env.getNomeTabellaOrigine()  );
			throw new RulEngineException("set flag elaborato table table " + env.getNomeTabellaOrigine() , e1);
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
