package it.webred.rulengine.brick.loadDwh.load.rette.cancellazioni;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.gas.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class BolletteCancEnv extends EnvImport {

	public String RE_RTT_CANC_BOLLETTE_1_0 = getProperty("table1_0.name");
	public String RE_RTT_CANC_BOLLETTE_IDX = getProperty("table1_0.idx");
	
		
	protected String createTable1_0 = getProperty("table1_0.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public BolletteCancEnv(String connectionName, Context ctx) throws RulEngineException {
		super("18",connectionName, ctx);
	}
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
