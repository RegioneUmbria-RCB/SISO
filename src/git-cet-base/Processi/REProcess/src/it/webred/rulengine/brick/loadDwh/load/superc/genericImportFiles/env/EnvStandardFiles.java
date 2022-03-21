package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public abstract  class EnvStandardFiles<T extends TestataStandardFileBean> extends EnvImportFilesWithTipoRecord<T> {



	public EnvStandardFiles(String fonte, String connectionName, Context ctx)
			throws RulEngineException {
		super(fonte, connectionName, ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getPercorsoFiles() {
		return getPercorsoFilesSpec();
		
	}

	public abstract String getPercorsoFilesSpec();

}
