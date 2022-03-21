/**
 * F.A 04/ago/2010 09.02.41 
 * 
 * 
 */
package it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.bean.CNCTestata;
import it.webred.rulengine.brick.loadDwh.load.gas.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;

/**
 * @author Francesco
 *
 */
public class FlussoStatoRiscTipoRecordEnv <T extends CNCTestata> extends EnvImportFilesWithTipoRecord<T> {
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	protected String tableFR00C = getProperty("tableFR00C.name");
	protected String tableFR99C = getProperty("tableFR99C.name");
	protected String tableFR0 = getProperty("tableFR0.name");
	protected String tableFR3 = getProperty("tableFR3.name");
	protected String tableFR4 = getProperty("tableFR4.name");
	protected String tableFR5 = getProperty("tableFR5.name");
	
	
	protected String createTableFR00C = getProperty("tableFR00C.create_table");
	protected String createTableFR99C = getProperty("tableFR99C.create_table");
	protected String createTableFR0 = getProperty("tableFR0.create_table");
	protected String createTableFR3 = getProperty("tableFR3.create_table");
	protected String createTableFR4 = getProperty("tableFR4.create_table");
	protected String createTableFR5 = getProperty("tableFR5.create_table");

	//protected String createR00CIdx = getProperty("tableR00C.create_idx");
	
	public FlussoStatoRiscTipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("15", connectionName, ctx);		
	}

	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}


}
