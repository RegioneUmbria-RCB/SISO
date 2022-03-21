package it.webred.rulengine.brick.loadDwh.load.licenzecommerciali;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvStandardFiles;
import it.webred.rulengine.exception.RulEngineException;


public class LicenzeCommercialiTipoRecordEnv<T extends TestataStandardFileBean> extends EnvStandardFiles<T> {
	
	public LicenzeCommercialiTipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("13", connectionName, ctx);
		// TODO Auto-generated constructor stub
	}
	
	public String RE_LICENZECOMMERCIALI_A = getProperty("tableA.name");
	public String RE_LICENZECOMMERCIALI_A_IDX = getProperty("tableA.idx");
	public String RE_LICENZECOMMERCIALI_B = getProperty("tableB.name");
	public String RE_LICENZECOMMERCIALI_B_IDX = getProperty("tableB.idx");
		
	protected String createTableA = getProperty("tableA.create_table");
	protected String createTableB = getProperty("tableB.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());

	@Override
	public String getPercorsoFilesSpec() {
		// TODO Auto-generated method stub
		return dirFiles;
	}
	
}

