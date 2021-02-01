package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata;
import it.webred.rulengine.exception.RulEngineException;

public abstract class EnvImportFilesWithTipoRecord<T extends Testata> extends EnvImport {

	public EnvImportFilesWithTipoRecord(String fonte, String connectionName, Context ctx)
			throws RulEngineException {
		super(fonte, connectionName, ctx);
		// TODO Auto-generated constructor stub
	}

	private T testata;
	
	public T getTestata() {
		return testata;
	}

	public void setTestata(T testata) {
		this.testata = testata;
	}


	
}
