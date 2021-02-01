/**
 * F.A 04/ago/2010 09.02.41 
 * 
 * 
 */
package it.webred.rulengine.brick.loadDwh.load.cnc.flusso290;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.bean.CNCTestata;
import it.webred.rulengine.brick.loadDwh.load.gas.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;

/**
 * @author Francesco
 *
 */
public class Flusso290TipoRecordEnv <T extends CNCTestata> extends EnvImportFilesWithTipoRecord<T> {
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	protected String N1table = getProperty("tableN1.name");
	protected String N2table = getProperty("tableN2.name");
	protected String N3table = getProperty("tableN3.name");
	protected String N4table = getProperty("tableN4.name");
	protected String N5table = getProperty("tableN5.name");
	protected String N9table = getProperty("tableN9.name");
	protected String N0table = getProperty("tableN0.name");
	
	protected String createTableN0 = getProperty("tableN0.create_table");
	protected String createTableN1 = getProperty("tableN1.create_table");
	protected String createTableN2 = getProperty("tableN2.create_table");
	protected String createTableN3 = getProperty("tableN3.create_table");
	protected String createTableN4 = getProperty("tableN4.create_table");
	protected String createTableN5 = getProperty("tableN5.create_table");
	protected String createTableN9 = getProperty("tableN9.create_table");
	protected String createN0Idx = getProperty("tableN0.create_idx");
	
	public Flusso290TipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("15", connectionName, ctx);		
	}

	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}


}
