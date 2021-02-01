package it.webred.rulengine.brick.loadDwh.load.acqua.tributaria;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.acqua.tributaria.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;


public class AcquaTipoRecordEnv<T extends Testata> extends EnvImportFilesWithTipoRecord<T> {

	public AcquaTipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("30",connectionName, ctx);
		// TODO Auto-generated constructor stub
	}
	public String RE_ACQUA_A = getProperty("tableA.name");
	
	protected String RE_ACQUA_A_IDX = getProperty("tableA.idx");
	
	protected String createTableA = getProperty("tableA.create_table");
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());

	
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}


	
}
