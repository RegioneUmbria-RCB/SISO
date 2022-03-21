package it.webred.rulengine.brick.loadDwh.load.planimetrieComma340;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;


public class PlanimetrieComma340Env extends EnvImport {
	
	public PlanimetrieComma340Env(String connectionName, Context ctx) throws RulEngineException {
		super("16", connectionName, ctx);
		// TODO Auto-generated constructor stub
	}
	
	public String RE_PLANIMETRIE_COMMA340_A = getProperty("tableA.name");
	public String RE_PLANIMETRIE_COMMA340_A_IDX = getProperty("tableA.idx");
		
	protected String createTableA = getProperty("tableA.create_table");
	protected String deleteALL = getProperty("tableA.deleteALL");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());

	@Override
	public String getPercorsoFiles() {
		// TODO Auto-generated method stub
		return dirFiles;
	}
	
}

