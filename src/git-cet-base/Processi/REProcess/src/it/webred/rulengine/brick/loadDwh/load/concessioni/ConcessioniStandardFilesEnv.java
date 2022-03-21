package it.webred.rulengine.brick.loadDwh.load.concessioni;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.exception.RulEngineException;


public class ConcessioniStandardFilesEnv<T extends TestataStandardFileBean> extends it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvStandardFiles<T> {

	public ConcessioniStandardFilesEnv(String connectionName, Context ctx) throws RulEngineException {
		super("3",connectionName, ctx);
		// TODO Auto-generated constructor stub
	}
	public String RE_CONCESSIONI_A = getProperty("tableA.name");
	public String RE_CONCESSIONI_B = getProperty("tableB.name");
	public String RE_CONCESSIONI_C = getProperty("tableC.name");
	public String RE_CONCESSIONI_D = getProperty("tableD.name");
	public String RE_CONCESSIONI_E = getProperty("tableE.name");
	
	protected String RE_CONCESSIONI_A_IDX = getProperty("tableA.idx");
	protected String RE_CONCESSIONI_B_IDX = getProperty("tableB.idx");
	protected String RE_CONCESSIONI_C_IDX = getProperty("tableC.idx");
	protected String RE_CONCESSIONI_D_IDX = getProperty("tableD.idx");
	protected String RE_CONCESSIONI_E_IDX = getProperty("tableE.idx");
	
	protected String createTableA = getProperty("tableA.create_table");
	protected String createTableB = getProperty("tableB.create_table");
	protected String createTableC = getProperty("tableC.create_table");
	protected String createTableD = getProperty("tableD.create_table");
	protected String createTableE = getProperty("tableE.create_table");
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());

	


	@Override
	public String getPercorsoFilesSpec() {
		return dirFiles;
	}





	


	
}
