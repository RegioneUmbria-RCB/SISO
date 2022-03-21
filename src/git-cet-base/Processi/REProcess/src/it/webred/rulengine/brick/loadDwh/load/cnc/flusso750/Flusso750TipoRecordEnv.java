/**
 * F.A 04/ago/2010 09.02.41 
 * 
 * 
 */
package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.bean.CNCTestata;
import it.webred.rulengine.brick.loadDwh.load.gas.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;

/**
 * @author Francesco
 *
 */
public class Flusso750TipoRecordEnv <T extends CNCTestata> extends EnvImportFilesWithTipoRecord<T> {
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	protected String tableR00C = getProperty("tableR00C.name");
	protected String tableR99C = getProperty("tableR99C.name");
	protected String tableR3A = getProperty("tableR3A.name");
	protected String tableR3Z = getProperty("tableR3Z.name");
	protected String tableR5A = getProperty("tableR5A.name");
	protected String tableR5B = getProperty("tableR5B.name");
	protected String tableR5Z = getProperty("tableR5Z.name");
	protected String tableR7A = getProperty("tableR7A.name");
	protected String tableR7B = getProperty("tableR7B.name");
	protected String tableR7C = getProperty("tableR7C.name");
	protected String tableR7D = getProperty("tableR7D.name");
	protected String tableR7E = getProperty("tableR7E.name");
	protected String tableR7F = getProperty("tableR7F.name");
	protected String tableR7G = getProperty("tableR7G.name");
	protected String tableR7H = getProperty("tableR7H.name");
	protected String tableR7K = getProperty("tableR7K.name");
	
	
	protected String createTableR00C = getProperty("tableR00C.create_table");
	protected String createTableR99C = getProperty("tableR99C.create_table");
	protected String createTableR3A = getProperty("tableR3A.create_table");
	protected String createTableR3Z = getProperty("tableR3Z.create_table");
	protected String createTableR5A = getProperty("tableR5A.create_table");
	protected String createTableR5B = getProperty("tableR5B.create_table");
	protected String createTableR5Z = getProperty("tableR5Z.create_table");
	protected String createTableR7A = getProperty("tableR7A.create_table");
	protected String createTableR7B = getProperty("tableR7B.create_table");
	protected String createTableR7C = getProperty("tableR7C.create_table");
	protected String createTableR7D = getProperty("tableR7D.create_table");
	protected String createTableR7E = getProperty("tableR7E.create_table");
	protected String createTableR7F = getProperty("tableR7F.create_table");
	protected String createTableR7G = getProperty("tableR7G.create_table");
	protected String createTableR7H = getProperty("tableR7H.create_table");
	protected String createTableR7K = getProperty("tableR7K.create_table");
	
	
	
	protected String createR00CIdx = getProperty("tableR00C.create_idx");
	
	public Flusso750TipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("15", connectionName, ctx);		
	}

	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}


}
