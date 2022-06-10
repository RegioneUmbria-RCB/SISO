package it.webred.rulengine.brick.loadDwh.load.sciajpe;

import it.webred.rulengine.Context;

import it.webred.rulengine.brick.loadDwh.load.sciajpe.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;


public class SciaJpeTipoRecordEnv<T extends Testata> extends EnvImportFilesWithTipoRecord<T> {

	public String RE_SCIA_JPE_UNO = getProperty("tableUNO.name");
	public String RE_SCIA_JPE_IDX = getProperty("tableUNO.idx");
	
	protected String createTableUNO = getProperty("tableUNO.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public SciaJpeTipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("53",connectionName, ctx);
	}
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

	
}
