package it.webred.rulengine.brick.loadDwh.load.pubblicita;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvStandardFiles;
import it.webred.rulengine.exception.RulEngineException;

public class PubblicitaTipoRecordEnv <T extends TestataStandardFileBean> extends EnvStandardFiles<T> {
	
	public PubblicitaTipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("27", connectionName, ctx);
		// TODO Auto-generated constructor stub
	}
	
	public String RE_PUBBLICITA_A = getProperty("tableA.name");
	public String RE_PUBBLICITA_A_IDX = getProperty("tableA.idx");
	public String RE_PUBBLICITA_B = getProperty("tableB.name");
	public String RE_PUBBLICITA_B_IDX = getProperty("tableB.idx");
		
	protected String createTableA = getProperty("tableA.create_table");
	protected String createTableB = getProperty("tableB.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());

	@Override
	public String getPercorsoFilesSpec() {
		// TODO Auto-generated method stub
		return dirFiles;
	}
	
}
