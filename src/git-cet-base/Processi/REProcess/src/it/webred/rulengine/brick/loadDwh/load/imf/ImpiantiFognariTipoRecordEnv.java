package it.webred.rulengine.brick.loadDwh.load.imf;

import it.webred.rulengine.Context;


import it.webred.rulengine.brick.loadDwh.load.imf.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;


public class ImpiantiFognariTipoRecordEnv<T extends Testata> extends EnvImportFilesWithTipoRecord<T> {

	public String RE_IMPIANTI_FOGNARI_UNO = getProperty("tableUNO.name");
	public String RE_IMPIANTI_FOGNARI_IDX = getProperty("tableUNO.idx");
	
	protected String createTableUNO = getProperty("tableUNO.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public ImpiantiFognariTipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("54",connectionName, ctx);
	}
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

	
}
