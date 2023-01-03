package it.webred.rulengine.brick.loadDwh.load.schedemartelli;

import it.webred.rulengine.Context;

import it.webred.rulengine.brick.loadDwh.load.schedemartelli.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;


public class SchedeMartelliTipoRecordEnv<T extends Testata> extends EnvImportFilesWithTipoRecord<T> {

	public String RE_SCHEDE_MARTELLI_UNO = getProperty("tableUNO.name");
	public String RE_SCHEDE_MARTELLI_IDX = getProperty("tableUNO.idx");
	
	protected String createTableUNO = getProperty("tableUNO.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public SchedeMartelliTipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("55",connectionName, ctx);
	}
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

	
}
