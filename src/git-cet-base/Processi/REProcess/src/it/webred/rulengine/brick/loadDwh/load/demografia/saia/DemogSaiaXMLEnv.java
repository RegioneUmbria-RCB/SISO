package it.webred.rulengine.brick.loadDwh.load.demografia.saia;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.gas.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class DemogSaiaXMLEnv extends EnvImport {

	public String RE_SIT_D_SAIA_1_0 = getProperty("table1_0.name");
	public String RE_SIT_D_SAIA_IDX = getProperty("table1_0.idx");
	
	public String RE_SIT_D_SAIA_PATENTI_1_0 = getProperty("tablepat1_0.name");
	public String RE_SIT_D_SAIA_PATENTI_IDX = getProperty("tablepat1_0.idx");
	
	public String RE_SIT_D_SAIA_VEICOLI_1_0 = getProperty("tablevei1_0.name");
	public String RE_SIT_D_SAIA_VEICOLI_IDX = getProperty("tablevei1_0.idx");
	
		
	protected String createTable1_0 = getProperty("table1_0.create_table");
	protected String createTablePat1_0 = getProperty("tablepat1_0.create_table");
	protected String createTableVei1_0 = getProperty("tablevei1_0.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public DemogSaiaXMLEnv(String connectionName, Context ctx) throws RulEngineException {
		super("1",connectionName, ctx);
	}
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
